package other;

import java.io.File;
import java.util.*;

import main.ExtractLatLon;
import main.writeToFile;

public class azimuth {        
	//calculate azimuth/relative distance between two points
	public static void main(String args[]){
		
		
		File f = new File("C:/test/trajectory2/OriginalTrajectories");
        File[] paths = f.listFiles();
        
        int count = 1;
        for(File inFile:paths){
        	ExtractLatLon testVel = new ExtractLatLon(inFile.getAbsolutePath());
        	String tempLat = testVel.getLats();
        	String tempLon = testVel.getLons();
        	ArrayList<Double> newAzimuths = getAzimuths(tempLat, tempLon);
        	
        	String namePath = "C:/test/trajectory2/azimuths/" + inFile.getName();
        	String[] azimuthString = new String[newAzimuths.size()];
        	//convert array of doubles to array of strings so it can be written to file
        	for(int i=0; i<newAzimuths.size(); i++)
        		azimuthString[i] = Double.toString(newAzimuths.get(i));
        	
        	writeToFile write = new writeToFile(azimuthString, namePath);
        	write.write();
        	System.out.println("Written " + count);
        	count++;
        }
        
        System.out.println("Done");
	}
	
	private static ArrayList<Double> getAzimuths(String lat, String lon){
		ArrayList<Double> azimuths = new ArrayList<Double>();
		//declare variables and lat/lon values
		double azi = 0.0;
		String tempLat = lat;
		String tempLon = lon;
		
		//convert lat/lon values into array of doubles
		String[] latArray = tempLat.split(",");
		String[] lonArray = tempLon.split(",");
		double[] latvalArray = new double[latArray.length];
		double[] lonvalArray = new double[lonArray.length];
		for(int i = 0; i < latArray.length; i++){
			latvalArray[i] = Double.parseDouble(latArray[i]);
			lonvalArray[i] = Double.parseDouble(lonArray[i]);
		}
		
		//calculate azimuth
		for(int j = 1; j < latArray.length; j++){
			//convert lat/lon values from degrees to radians
			double lat1 = Math.toRadians(latvalArray[j-1]);
			double lon1 = Math.toRadians(lonvalArray[j-1]);
			double lat2 = Math.toRadians(latvalArray[j]);
			double lon2 = Math.toRadians(lonvalArray[j]);
			
			//calculate azimuth
			double x = Math.cos(lat2)*Math.sin(lon2-lon1);
			double y = Math.cos(lat1)*Math.sin(lat2)-Math.sin(lat1)*Math.cos(lat2)*Math.cos(lon2-lon1);
			azi = Math.atan2(x, y);
			
			//convert back to degrees
			azimuths.add((azi * 180.0 / Math.PI + 360.0) % 360);
		}
		return azimuths;
	}
}