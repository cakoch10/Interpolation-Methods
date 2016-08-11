package com.space.filling.curve;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

//import com.datastructure.BPlusTree;


public class Construct3DimCurve {
	
	static final int baseValue = 8;
	public int order = 0;
	int unitCount = 0;
	Cube boundCube = null;
	double stepLat;
	double stepLng;
	long stepTime;
	int[][][] mappingSchema = null;
	
	//public Construct2DimCurve(Rectangle rect)
	public Construct3DimCurve()
	{
		try {
			FileInputStream inputFile = new FileInputStream("3DBoundCube.dat");
			ObjectInputStream input = new ObjectInputStream(inputFile);
			this.boundCube = (Cube)input.readObject();
			input.close();
			inputFile.close();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		
		if (this.boundCube == null)
		{
			System.out.println("no bound cube has been constructed");
			return;
		}
		
		this.mappingSchema = this.readinMappingSchema();
		this.unitCount = mappingSchema.length;
		this.stepLat = (this.boundCube.upperLeftLat - this.boundCube.lowerRightLat)/ this.unitCount;
		this.stepLng = (this.boundCube.lowerRightLng - this.boundCube.upperLeftLng)/ this.unitCount;
		this.stepTime = (long) Math.ceil((double)(this.boundCube.endTime - this.boundCube.startTime)/ this.unitCount);;
	}
	
	public Construct3DimCurve(Cube boundCube, int order)
	{
		if (boundCube.upperLeftLat < boundCube.lowerRightLat || boundCube.lowerRightLng < boundCube.upperLeftLng || order < 0)
		{
			System.out.println("Error: rectangle's left and right latitude longitude values initialization error");
			return;
		}

		this.boundCube = boundCube;
		this.order = order;
		
		this.unitCount = (int) Math.pow(this.baseValue, this.order);
		this.unitCount = (int) Math.sqrt(this.unitCount);
		this.mappingSchema = new int[this.unitCount][this.unitCount][this.unitCount];

		this.stepLat = (this.boundCube.upperLeftLat - this.boundCube.lowerRightLat)/ this.unitCount;
		this.stepLng = (this.boundCube.lowerRightLng - this.boundCube.upperLeftLng)/ this.unitCount;
		this.stepTime = (long) Math.ceil((double)(this.boundCube.endTime - this.boundCube.startTime)/ this.unitCount);
		
		// write out bound rectangle
		if (this.boundCube == null)
			return;
		try {
			FileOutputStream output = new FileOutputStream("3DBoundCube.dat");
			ObjectOutputStream outStream = new ObjectOutputStream(output);
			outStream.writeObject(this.boundCube);
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
		
		int x, y, z, i, curState;
		String xCoordinate, yCoordinate, zCoordinate, derivedKey, curDerivedKey, nextDerivedKey;
		ArrayList<SerializedData> serializedData = new ArrayList<SerializedData>();
		SerializedData curSD = null;
		DataItem curDI = null;
		//Rectangle rect = new Rectangle();
		Cube cube = new Cube();
		
		Hashtable<Integer, String> int2StrKey = new Hashtable<Integer, String>();
		int2StrKey.put(0, "000");
		int2StrKey.put(1, "001");
		int2StrKey.put(2, "010");
		int2StrKey.put(3, "011");
		int2StrKey.put(4, "100");
		int2StrKey.put(5, "101");
		int2StrKey.put(6, "110");
		int2StrKey.put(7, "111");
		
		Hashtable<String, Integer> str2IntKey = new Hashtable<String, Integer>();
		str2IntKey.put("000", 0);
		str2IntKey.put("001", 1);
		str2IntKey.put("010", 2);
		str2IntKey.put("011", 3);
		str2IntKey.put("100", 4);
		str2IntKey.put("101", 5);
		str2IntKey.put("110", 6);
		str2IntKey.put("111", 7);
		
		StateDiagram[][] stateDiagram = StateDiagram.mapFrom3DimToOneDim();
		int shiftDigits = 3;
		boolean isLeft = true;
		
		for (x = 0; x < this.unitCount; x++)
		{
			for(y = 0; y < this.unitCount; y++)
			{
				for(z = 0; z < this.unitCount; z++)
				{
					cube.upperLeftLat = this.boundCube.lowerRightLat + ( y + 1 ) * this.stepLat; //.boundLowerRightLat
					cube.upperLeftLng = this.boundCube.upperLeftLng + x * this.stepLng; // .boundUpperLeftLng
					cube.lowerRightLat = cube.upperLeftLat - this.stepLat;
					cube.lowerRightLng = cube.upperLeftLng + this.stepLng;
					cube.startTime = this.boundCube.startTime + z * this.stepTime;
					cube.endTime = cube.startTime + this.stepTime;
					
					System.out.println(cube.upperLeftLat + ", " + cube.lowerRightLat + ", " + cube.upperLeftLng + ", " + cube.lowerRightLng + ", " +
										cube.startTime + ", " + cube.endTime);
					
					xCoordinate = this.getCoordinate(x);
					yCoordinate = this.getCoordinate(y);
					zCoordinate = this.getCoordinate(z);
					
					curState = 0;
					derivedKey = "";
					for(i = 0; i < this.order; i++)
					{
						curDerivedKey = xCoordinate.charAt(i) + "" + yCoordinate.charAt(i) + "" + zCoordinate.charAt(i);
						nextDerivedKey = stateDiagram[curState][str2IntKey.get(curDerivedKey)].nextDerivedKey;
						curState = stateDiagram[curState][str2IntKey.get(curDerivedKey)].nextState;
						derivedKey = MathOperation.shiftPosition(derivedKey, isLeft, shiftDigits);
						derivedKey = getNewKey(derivedKey, nextDerivedKey);
					}
					curSD = new SerializedData();
					curSD.key = MathOperation.binaryToDecimal(derivedKey);
					
					this.mappingSchema[x][y][z] = curSD.key;
					
					//System.out.println("x:" + xCoordinate + ", y: " + yCoordinate + ", " + derivedKey + ": " + curSD.key);
					int curi = 0;
					iterItems = items.iterator();
					while(iterItems.hasNext())
					{
						curi ++;
						curDI = iterItems.next();
						if(curDI.latitude < cube.upperLeftLat && curDI.latitude >= cube.lowerRightLat 
								&& curDI.longitude >= cube.upperLeftLng && curDI.longitude < cube.lowerRightLng
								&& curDI.dateTime >= cube.startTime && curDI.dateTime < cube.endTime)
						{
							curSD.dataItem.add(curDI);
							iterItems.remove();
						}
					}
					System.out.println(curi);
					serializedData.add(curSD);
				}
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
	
	
	
	public static void main(String args[])
	{
		String x = "00101", y = "10";

		Cube rt = new Cube();
		rt.upperLeftLat = 75;
		rt.upperLeftLng = -130;
		rt.lowerRightLat = -75;
		rt.lowerRightLng = 180;
		
		int order = 2;
		Construct3DimCurve constructCurve = new Construct3DimCurve(rt, order);
		System.out.println(constructCurve.getNewKey(x, y));
		ArrayList<DataItem> items = new ArrayList();
		DataItem dataItem = new DataItem();
		dataItem.value = 1;
		dataItem.latitude = -75;
		dataItem.longitude = -130;
		items.add(dataItem);
		dataItem = new DataItem();
		dataItem.value = 2;
		dataItem.latitude = -75;
		dataItem.longitude = -130;
		items.add(dataItem);
		dataItem = new DataItem();
		dataItem.value = 100;
		dataItem.latitude = -74.9999999;
		dataItem.longitude = -130;
		items.add(dataItem);
		constructCurve.serializeCurve(items);
	}
	
	
	public boolean writeOutMappingSchema()
	{
		if (this.mappingSchema == null)
			return false;
		try {
			FileOutputStream output = new FileOutputStream("3DMappingSchema.dat");
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
	
	private int[][][] readinMappingSchema()
	{
		int [][][] readData = null;
		try {
			FileInputStream inputFile = new FileInputStream("3DMappingSchema.dat");
			ObjectInputStream input = new ObjectInputStream(inputFile);
			try {
				readData = (int[][][])input.readObject();
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
	
	
	/*@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList searchCube(Cube cube, BPlusTree bPlusTree)
	{
		ArrayList docID = new ArrayList();
		ArrayList curData = null;
		int i, j, k;
		double upLat, downLat, leftLng, rightLng, startTime, endTime;
		long key;
		
		for (i = 0; i < this.unitCount; i++)
		{
			leftLng = this.boundCube.upperLeftLng + i * this.stepLng; // .boundUpperLeftLng
			rightLng = leftLng + this.stepLng;
			if(rightLng < cube.upperLeftLng || leftLng > cube.lowerRightLat)
				continue;
			for(j = 0; j < this.unitCount; j++)
			{
				downLat = this.boundCube.lowerRightLat + j * this.stepLat; // .boundLowerRightLat
				upLat = downLat + this.stepLat;
				if(upLat < cube.lowerRightLat || downLat > cube.upperLeftLat)
					continue;
				for (k = 0; k < this.unitCount; k++)
				{
					startTime = this.boundCube.startTime + k * this.stepTime;
					endTime = startTime + this.stepTime;
					if (startTime > cube.endTime || endTime < cube.startTime)
						continue;
					key = (long) this.mappingSchema[i][j][k];
					curData = bPlusTree.searchPoint(key);
					if(curData != null)
						docID.addAll(curData);
				}
			}
		}
		
		return docID;
	}*/
	
	
	
}
