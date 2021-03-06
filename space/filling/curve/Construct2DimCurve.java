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


public class Construct2DimCurve {
	
	static final int baseValue = 4;
	public int order = 0;
	int unitCount = 0;
	Rectangle boundRect = null;
	double stepLat;
	double stepLng;
	int[][] mappingSchema = null;
	
	//public Construct2DimCurve(Rectangle rect)
	public Construct2DimCurve()
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
	
	public Construct2DimCurve(Rectangle boundRect, int order)
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
	
	
	
	public static void main(String args[])
	{
		String x = "00101", y = "10";

		Rectangle rt = new Rectangle();
		rt.upperLeftLat = 75;
		rt.upperLeftLng = -130;
		rt.lowerRightLat = -75;
		rt.lowerRightLng = 180;
		int order = 2;
		Construct2DimCurve constructCurve = new Construct2DimCurve(rt, order);
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
	
	
	
}
