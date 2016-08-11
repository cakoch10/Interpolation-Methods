package com.space.filling.curve;

import java.io.File;

public class TimeSeriesConversion {
	public static void main(String[] args){
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
    		
    	    Construct2DimCurve_v2.mainS(inFile, minLat, maxLat, minLon, maxLon);
    	    
    	    
    		//conversion.mainS(inFile, minLat, maxLat, minLon, maxLon);
        }
	}
}
