package com.space.filling.curve;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

/*
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
*/
//import com.datastructure.BPlusTree;


public class Construct2DimCurve_v2 {
	
	static final int baseValue = 4;
	public int order = 0;
	int unitCount = 0;
	Rectangle boundRect = null;
	double stepLat;
	double stepLng;
	int[][] mappingSchema = null;
	
	//public Construct2DimCurve(Rectangle rect)
	public Construct2DimCurve_v2()
	{
		try {
			FileInputStream inputFile = new FileInputStream("2DBoundRectangle.dat");
			ObjectInputStream input = new ObjectInputStream(inputFile);
			this.boundRect = (Rectangle)input.readObject();
			input.close();
			inputFile.close();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		
		if (this.boundRect == null)
		{
			System.out.println("no bound rectangle has been constructed");
			return;
		}
		
		this.mappingSchema = this.readinMappingSchema();
		this.unitCount = mappingSchema.length;
		this.stepLat = (this.boundRect.upperLeftLat - this.boundRect.lowerRightLat)/ this.unitCount;
		this.stepLng = (this.boundRect.lowerRightLng - this.boundRect.upperLeftLng)/ this.unitCount;
	}
	
	public Construct2DimCurve_v2(Rectangle boundRect, int order)
	{
		if (boundRect.upperLeftLat < boundRect.lowerRightLat || boundRect.lowerRightLng < boundRect.upperLeftLng || order < 0)
		{
			System.out.println("Error: rectangle's left and right latitude longitude values initialization error");
			return;
		}

		this.boundRect = boundRect;
		this.order = order;
		
		this.unitCount = (int) Math.pow(this.baseValue, this.order);
		this.unitCount = (int) Math.sqrt(this.unitCount);
		this.mappingSchema = new int[this.unitCount][this.unitCount];

		this.stepLat = (this.boundRect.upperLeftLat - this.boundRect.lowerRightLat)/ this.unitCount;
		this.stepLng = (this.boundRect.lowerRightLng - this.boundRect.upperLeftLng)/ this.unitCount;
		
		// write out bound rectangle
		if (this.boundRect == null)
			return;
		try {
			FileOutputStream output = new FileOutputStream("2DBoundRectangle.dat");
			ObjectOutputStream outStream = new ObjectOutputStream(output);
			outStream.writeObject(this.boundRect);
			outStream.flush();
			outStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// from down to up, from left to right. First cell is at the left down corner, then up to finish the first column.
	// then to the second,..., this.unitCount column
	public ArrayList<SerializedData> serializeCurve(ArrayList<DataItem> items)
	{
		if (items == null || items.size() < 0 )
		{
			System.out.println("no items or no cell");
			return null;
		}
		Iterator<DataItem> iterItems = null;
		
		int x, y, i, curState;
		String xCoordinate, yCoordinate, derivedKey, curDerivedKey, nextDerivedKey;
		ArrayList<SerializedData> serializedData = new ArrayList<SerializedData>();
		SerializedData curSD = null;
		DataItem curDI = null;
		Rectangle rect = new Rectangle();
		
		Hashtable<Integer, String> int2StrKey = new Hashtable<Integer, String>();
		int2StrKey.put(0, "00");
		int2StrKey.put(1, "01");
		int2StrKey.put(2, "10");
		int2StrKey.put(3, "11");
		
		Hashtable<String, Integer> str2IntKey = new Hashtable<String, Integer>();
		str2IntKey.put("00", 0);
		str2IntKey.put("01", 1);
		str2IntKey.put("10", 2);
		str2IntKey.put("11", 3);
		
		StateDiagram[][] stateDiagram = StateDiagram.mapFrom2DimToOneDim();
		int shiftDigits = 2;
		boolean isLeft = true;
		
		for (x = 0; x < unitCount; x++)
		{
			for(y = 0; y < unitCount; y++)
			{
				rect.upperLeftLat = this.boundRect.lowerRightLat + ( y + 1 ) * this.stepLat; //.boundLowerRightLat
				rect.upperLeftLng = this.boundRect.upperLeftLng + x * this.stepLng; // .boundUpperLeftLng
				rect.lowerRightLat = rect.upperLeftLat - this.stepLat;
				rect.lowerRightLng = rect.upperLeftLng + this.stepLng;
				
				//System.out.println(rect.upperLeftLat + ", " + rect.lowerRightLat + ", " + rect.upperLeftLng + ", " + rect.lowerRightLng);
				
				xCoordinate = this.getCoordinate(x);
				yCoordinate = this.getCoordinate(y);
				
				curState = 0;
				derivedKey = "";
				for(i = 0; i < this.order; i++)
				{
					curDerivedKey = xCoordinate.charAt(i) + "" + yCoordinate.charAt(i);
					nextDerivedKey = stateDiagram[curState][str2IntKey.get(curDerivedKey)].nextDerivedKey;
					curState = stateDiagram[curState][str2IntKey.get(curDerivedKey)].nextState;
					derivedKey = MathOperation.shiftPosition(derivedKey, isLeft, shiftDigits);
					derivedKey = getNewKey(derivedKey, nextDerivedKey);
				}
				curSD = new SerializedData();
				curSD.key = MathOperation.binaryToDecimal(derivedKey);
				
				this.mappingSchema[x][y] = curSD.key; 
				//System.out.println("x:" + xCoordinate + ", y: " + yCoordinate + ", " + derivedKey + ": " + curSD.key);
				int curi = 0;
				iterItems = items.iterator();
				while(iterItems.hasNext())
				{
					curi ++;
					curDI = iterItems.next();
					if(curDI.latitude < rect.upperLeftLat && curDI.latitude >= rect.lowerRightLat 
							&& curDI.longitude >= rect.upperLeftLng && curDI.longitude < rect.lowerRightLng)
					{
						curSD.dataItem.add(curDI);
						iterItems.remove();
						serializedData.add(curSD);
					}
				}
				//System.out.println(curi);
				//serializedData.add(curSD);
			}
		}
		return serializedData;
	}
	
	private String getNewKey(String curKey, String conKey)
	{
		String nextKey = null;
		if (curKey == null || conKey == null)
		{
			System.out.println("Error Occurs for Empty Values");
			return nextKey;
		}
		
		int curLen = curKey.length();
		int conLen = conKey.length();
		int curValue, conValue;
		
		nextKey = curKey.substring(0, curLen - conLen);
		curKey = curKey.substring(curLen - conLen, curLen);
		
		for(int i = 0; i < conLen; i++)
		{
			curValue = Integer.parseInt(curKey.charAt(i) + "");
			conValue = Integer.parseInt(conKey.charAt(i) + "");
			if (curValue > conValue)
				nextKey += curKey.charAt(i) + "";
			else
				nextKey += conKey.charAt(i) + "";
		}
		return nextKey;
	}
	
	private String getCoordinate(int value)
	{
		String coordinate = Integer.toBinaryString(value);
		while(coordinate.length() < this.order)
			coordinate = "0" + coordinate;
		return coordinate;
	}
	
	
	//this is normally the main file, it is modified to be run multiple times with different files
	public static void mainS(File inFile, double minLat, double maxLat, double minLon, double maxLon){
		//define grid size; construct grid
		Rectangle rt = new Rectangle();
		
/*	
	  	rt.upperLeftLat = 40.060994;
		rt.upperLeftLng = 116.302586;
		rt.lowerRightLat = 39.96028;
		rt.lowerRightLng = 116.429272;
*/
		
		rt.upperLeftLat = maxLat;
		rt.upperLeftLng = minLon;
		rt.lowerRightLat = minLat;
		rt.lowerRightLng = maxLon;
		
		
		Construct2DimCurve constructCurve = new Construct2DimCurve(rt, 4);
		
		//store lat/lon information into data items; put data items into items arraylist
		ArrayList<DataItem> items = new ArrayList<DataItem>();
		DataItem dataItem = new DataItem();
		dataItem.value = 1;
		
		String tempLat = "40,40.111085,40.051055,40.050986,40.050899,40.050768,40.050665,40.050619,40.050555,40.050471,40.050382,40.050337,40.050344,40.050348,40.050348,40.050351,40.050289,40.050211,40.050106,40.049986,40.04987,40.049765,40.049686,40.049591,40.049476,40.049338,40.04921";
		String tempLon = "116.398662,116.598661,116.398654,116.398654,116.39864,116.398655,116.398657,116.398654,116.398637,116.398638,116.398638,116.398632,116.398634,116.398634,116.398634,116.398635,116.398631,116.398619,116.398592,116.398595,116.398584,116.398585,116.398585,116.398584,116.398583,116.398586,116.398583";
		
		//===========================================================================================================
		
		/*File inName = new File("C:/test/Time Series/lat.txt");
		String lat = null;

		
		try{
			BufferedReader bR = new BufferedReader(new FileReader(inName));
			
			String line = "";
			
			while((line = bR.readLine()) != null){
				lat+=line;
				lat+=",";
			}
			lat = lat.substring(4, lat.length()-1);//removes initial null value and final comma
			
			bR.close();
		}catch(IOException ex) {
            System.err.println("An IOException was caught!");
            ex.printStackTrace();
        }
		
		
		inName = new File("C:/test/Time Series/long.txt");
		String lon = null;
		
		try{
			BufferedReader bR = new BufferedReader(new FileReader(inName));
			
			String line = "";
			
			while((line = bR.readLine()) != null){
				lon+=line;
				lon+=",";
			}
			lon = lon.substring(4, lon.length()-1);//removes initial null value
			bR.close();
		}catch(IOException ex) {
            System.err.println("An IOException was caught!");
            ex.printStackTrace();
        }*/
		
		ExtractLatLon testVel = new ExtractLatLon(inFile.getAbsolutePath(), true);
		tempLat = testVel.getLats();
		tempLon = testVel.getLons();
		
        
		
//		
//		tempLat = lat;
//		tempLon = lon;
//		
		//=================================================================================================================
		
		
		String[] latArray = tempLat.split(",");
		String[] lonArray = tempLon.split(",");
		double[] latvalArray = new double[latArray.length];
		double[] lonvalArray = new double[lonArray.length];
		
		String hilbertValues = null;
		
		for (int i = 0; i < latArray.length; i++){
			latvalArray[i] = Double.parseDouble(latArray[i]);
			lonvalArray[i] = Double.parseDouble(lonArray[i]);
			
			dataItem.latitude = Double.parseDouble(latArray[i]);
			dataItem.longitude = Double.parseDouble(lonArray[i]);
			items.add(dataItem);
			
			//calculate and store hilbert values
			ArrayList<SerializedData> sd = new ArrayList<SerializedData>();
			sd = constructCurve.serializeCurve(items);
			
			//return hilbert value if point is within rectangle
			int hil = 0;
			if(sd.size() > 0){
				hil = sd.get(0).key;
			}
			if(i==0)
				hilbertValues = Integer.toString(hil) + ",";
			else if(i==latArray.length-1)
				hilbertValues += Integer.toString(hil);
			else
				hilbertValues += Integer.toString(hil) + ",";
		}
		//write this string to a new file
		String[] hvals = hilbertValues.split(",");
		String name = inFile.getName().substring(0, inFile.getName().length()-4);
		writeToFile createFile = new writeToFile(hvals,
				"C:/test/trajectory2/OriginalTS/" + name + "_TS.plt");
		createFile.write();
	}
		
	public boolean writeOutMappingSchema()
	{
		if (this.mappingSchema == null)
			return false;
		try {
			FileOutputStream output = new FileOutputStream("2DMappingSchema.dat");
			ObjectOutputStream outStream = new ObjectOutputStream(output);
			outStream.writeObject(this.mappingSchema);
			outStream.flush();
			outStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	private int[][] readinMappingSchema()
	{
		int [][] readData = null;
		try {
			FileInputStream inputFile = new FileInputStream("2DMappingSchema.dat");
			ObjectInputStream input = new ObjectInputStream(inputFile);
			try {
				readData = (int[][])input.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				input.close();
				inputFile.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return readData;
	}
	
	
	//@SuppressWarnings({ "rawtypes", "unchecked" })
	/*public ArrayList searchMBR(Rectangle MBR, BPlusTree bPlusTree)
	{
		ArrayList docID = new ArrayList();
		ArrayList curData = null;
		int i, j;
		double upLat, downLat, leftLng, rightLng;
		long key;
		
		for (i = 0; i < this.unitCount; i++)
		{
			leftLng = this.boundRect.upperLeftLng + i * this.stepLng; // .boundUpperLeftLng
			rightLng = leftLng + this.stepLng;
			if(rightLng < MBR.upperLeftLng || leftLng > MBR.lowerRightLat)
				continue;
			for(j = 0; j < this.unitCount; j++)
			{
				downLat = this.boundRect.lowerRightLat + j * this.stepLat; // .boundLowerRightLat
				upLat = downLat + this.stepLat;
				if(upLat < MBR.lowerRightLat || downLat > MBR.upperLeftLat)
					continue;
				key = (long) this.mappingSchema[i][j];
				curData = bPlusTree.searchPoint(key);
				if(curData != null)
					docID.addAll(curData);
			}
		}
		
		return docID;
	}*/
	
	public static void main(String[] args){
		
		File f = new File("C:/test/trajectory2/OriginalTrajectories");
        File[] paths = f.listFiles();
        int count = 1;
        for(File inFile:paths){
        	ExtractLatLon testVel = new ExtractLatLon(inFile.getAbsolutePath(), true);
    		String tempLat = testVel.getLats();
    		String tempLon = testVel.getLons();
    		String[] latArray = tempLat.split(",");
    		String[] lonArray = tempLon.split(",");
    		double[] latvalArray = new double[latArray.length];
    		double[] lonvalArray = new double[lonArray.length];
    		
    		for (int i = 0; i < latArray.length; i++){
    			latvalArray[i] = Double.parseDouble(latArray[i]);
    			lonvalArray[i] = Double.parseDouble(lonArray[i]);
    		}
    		
    		double maxLat, minLat, maxLon, minLon;
    		
    		//get latitude boundary values
    		double highest = latvalArray[0];
    	    int highestIndex = 0;
    	    double lowest = latvalArray[0];
    	    int lowestIndex = 0;

    	    for (int s = 1; s < latvalArray.length; s++){
    	        double curValue = latvalArray[s];
    	        if (curValue > highest){
    	            highest = curValue;
    	            highestIndex = s;
    	        }
    	        if(curValue < lowest){
    	        	lowest = curValue;
    	        	lowestIndex = s;
    	        }
    	    }
    	    maxLat = highest + 0.001;
    	    minLat = lowest - 0.001;

    	    highest = lonvalArray[0];
    	    highestIndex = 0;
    	    lowest = lonvalArray[0];
    	    lowestIndex = 0;

    	    for (int s = 1; s < lonvalArray.length; s++){
    	        double curValue = lonvalArray[s];
    	        if (curValue > highest){
    	            highest = curValue;
    	            highestIndex = s;
    	        }
    	        if(curValue < lowest){
    	        	lowest = curValue;
    	        	lowestIndex = s;
    	        }
    	    }
    	    maxLon = highest + 0.001;
    	    minLon = lowest - 0.001;
    		
    	    mainS(inFile, minLat, maxLat, minLon, maxLon);
    	    System.out.println("Written" + count);
    	    count++;
        }
        System.out.println("Done");
	}
}












/*
This is the original code segment for converting trajectories with gaps to time series

File f = new File("C:/test/trajectory/Trajectory_with_gaps");
        File[] paths = f.listFiles();
        for(File inFile:paths){
        	ExtractLatLon testVel = new ExtractLatLon(inFile.getAbsolutePath(), true);
    		String tempLat = testVel.getLats();
    		String tempLon = testVel.getLons();
    		String[] latArray = tempLat.split(",");
    		String[] lonArray = tempLon.split(",");
    		double[] latvalArray = new double[latArray.length];
    		double[] lonvalArray = new double[lonArray.length];
    		
    		for (int i = 0; i < latArray.length; i++){
    			latvalArray[i] = Double.parseDouble(latArray[i]);
    			lonvalArray[i] = Double.parseDouble(lonArray[i]);
    		}
    		
    		double maxLat, minLat, maxLon, minLon;
    		
    		//get latitude boundary values
    		double highest = latvalArray[0];
    	    int highestIndex = 0;
    	    double lowest = latvalArray[0];
    	    int lowestIndex = 0;

    	    for (int s = 1; s < latvalArray.length; s++){
    	        double curValue = latvalArray[s];
    	        if (curValue > highest){
    	            highest = curValue;
    	            highestIndex = s;
    	        }
    	        if(curValue < lowest){
    	        	lowest = curValue;
    	        	lowestIndex = s;
    	        }
    	    }
    	    maxLat = highest + 0.001;
    	    minLat = lowest - 0.001;

    	    highest = lonvalArray[0];
    	    highestIndex = 0;
    	    lowest = lonvalArray[0];
    	    lowestIndex = 0;

    	    for (int s = 1; s < lonvalArray.length; s++){
    	        double curValue = lonvalArray[s];
    	        if (curValue > highest){
    	            highest = curValue;
    	            highestIndex = s;
    	        }
    	        if(curValue < lowest){
    	        	lowest = curValue;
    	        	lowestIndex = s;
    	        }
    	    }
    	    maxLon = highest + 0.001;
    	    minLon = lowest - 0.001;
    		
    	    mainS(inFile, minLat, maxLat, minLon, maxLon);
        }

 */
