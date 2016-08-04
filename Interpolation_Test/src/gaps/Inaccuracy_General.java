package gaps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;

import main.getNum;
import main.writeToFile;

public class Inaccuracy_General {
		public static void main(String[] args){
			File f = new File("c:/test/trajectory2/OriginalTrajectories");
	        
	        // returns pathnames for files and directory
	        File[] paths = f.listFiles();
	        
	        ArrayList<Double> TrajErrors = new ArrayList<Double>();//stores the trajectory errors
	        ArrayList<Double> TSerrors = new ArrayList<Double>();//stores the time series errors
	        //ArrayList<Double> fSizes = new ArrayList<Double>();//will store the file sizes corresponding to error values
	        ArrayList<String> fNames = new ArrayList<String>();//will store the names of files including sizes
	        
	        //get the names of the time series files
	        File timeSeries = new File("C:/test/trajectory2/OriginalTS");
			File[] tsList = timeSeries.listFiles();
			
			ArrayList<String> tsNames = new ArrayList<>();
	        
	    	for(File fe:tsList){
				String name = fe.getName();
				name = name.substring(0, 14);//remove the file ending
				tsNames.add(name);
			}
	        
	        int c = 0;
	        for(File fe:paths){
	        	for(int i=50; i<=2000; i+=50){
	        		int len = i;
	        		String id = fe.getName().substring(0, 14);
	        		
	        		int tsIndex = tsNames.indexOf(id);//get the corresponding time series file
	        		File in = new File(fe.getAbsolutePath());
	        		File ts = new File(tsList[tsIndex].getAbsolutePath());
	        		ArrayList<Integer> getHvals = new ArrayList<>();
	        		
	        		try{
	        			BufferedReader bR = new BufferedReader(new FileReader(ts));
	        			
	        			String line;
	        			while((line = bR.readLine()) != null){
	    					getHvals.add(Integer.parseInt(line));
	    				}
	        			bR.close();
	        		}catch(IOException ex) {
	                    System.err.println("An IOException was caught!");
	                    ex.printStackTrace();
	                }
	        		
	        		int[] Hvals = new int[getHvals.size()];
	        		for(int k=0; k<getHvals.size(); k++)//convert ArrayList to array
	        			Hvals[k] = getHvals.get(k);
	        		
	        		double TSdistance = distanceTS(len, Hvals);
	        		TSerrors.add(TSdistance);
	        		
	        		//get trajectory error
	        		ArrayList<String> trajLines = new ArrayList<>();
					
					try{
						BufferedReader bR = new BufferedReader(new FileReader(in));
						
						String line;
						while((line = bR.readLine()) != null){
							trajLines.add(line);
						}
						bR.close();
					}catch(IOException ex){
			            System.err.println("An IOException was caught!");
			            ex.printStackTrace();
			        }
					String[] trajArray = trajLines.toArray(new String[trajLines.size()]);
					double trajError = distanceTraj(len, trajArray);
					TrajErrors.add(trajError);
					
					String nm = id +"_"+ Integer.toString(len);
					
					fNames.add(nm);
	        	}
	        	System.out.println("End at: " + c);
	        	c++;
	        }
	        //write results to a file
	    	String[] write2file = new String[TrajErrors.size()];
	    	for(int i=0; i<TrajErrors.size(); i++)
	    		write2file[i] = TrajErrors.get(i).toString() +
	    		"," + TSerrors.get(i).toString() +
	    		"," + fNames.get(i);
	    	
	    	String writePath = "C:/test/trajectory2/Error_Analysis/totalDistance.plt";
	    	writeToFile wr = new writeToFile(write2file, writePath);
	    	wr.write();
		}
		
		public static double distanceTraj(int len, String[] real){//returns the total distance covered in the original trajectory
			
			double dist = 0.0;
			
			for(int i=16; i<(15+len); i++){
				getNum point = new getNum(real[i]);
				point.extract();
				double lat = point.getLat();
				double lon = point.getLon();
				
				getNum nextP = new getNum(real[i+1]);
				nextP.extract();
				double nlat = nextP.getLat();
				double nlon = nextP.getLon();
				
				dist+=dist(lon, lat, nlon, nlat);
			}
			return dist;
		}
		

		public static double dist(double x1, double y1, double x2, double y2){
			double distance = 0.0;
			final double r = 6371.0; //radius of earth in km
			
			x1 = Math.toRadians(x1);
			y1 = Math.toRadians(y1);
			
			x2 = Math.toRadians(x2);
			y2 = Math.toRadians(y2);
			
			double half1 = (y1-y2) / 2;
			double half2 = (x1-x2) / 2;
					
			double part1 = Math.sin(half1)*Math.sin(half1) + Math.cos(y1)*Math.cos(y1)*Math.sin(half2)*Math.sin(half2);
			double part2 = Math.sqrt(part1);
			distance = 2*r*Math.asin(part2);//distance is in km due to units of earth's radius
			
			return distance;
		}
		
		public static double distanceTS(int len, int[] hilberts){
			double d = 0.0;
			for(int i = 10; i<(9+len); i++){
				double initD = (double)hilberts[i] - (double)hilberts[i+1];
				d += Math.sqrt(1.0 + initD*initD);//apply distance formula
			}
			return d;
		}
}
