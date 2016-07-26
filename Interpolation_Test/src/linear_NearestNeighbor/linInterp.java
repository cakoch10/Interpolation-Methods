package linear_NearestNeighbor;
import java.io.*;
import java.util.*;

public class linInterp {
	private boolean extracted=false;//keeps track of whether the extracted method has been called
	private String[] newPoints;
	private String start;
	private String end;
	private int len; //number of NEW points
	private boolean startEnd;//indicates whether to include the start and end strings
	
	private double startLat;
	private double startLon;
	
	private double[] lat;//will store all of the new latitude points
	private double[] lon;//will store all of the new longitude points
	
	private double endLat;
	private double endLon;
	
	private int alt;
	
	private double startTime;
	
	String date;
	String time;
		
	//private ArrayList<gap> points = new ArrayList<gap>();
	
//	public linInterp(ArrayList<gap> G){
//		points = new ArrayList<gap>(G);
//	}
	
	public linInterp(String s, String e, int l, boolean include_Start_End){
		start = s;
		end = e;
		len = l;
		if(include_Start_End){
			newPoints = new String[len+2];
			newPoints[0] = start;
			newPoints[len+1] = end;
		}
		else
			newPoints = new String[len];
		
		lat = new double[len];
		lon = new double[len];
	}
	
	
	//String start = "39.926367,116.336141,0,492,39747.1124074074,2008-10-26,02:41:52";
	
	public void extractInfo(){//extracts information from strings
		Scanner scan = new Scanner(start);
 	    scan.useDelimiter(",");
 	    startLat = scan.nextDouble();
 	    startLon = scan.nextDouble();
 	    int zero = scan.nextInt();
 	    scan.next();
 	    startTime = scan.nextDouble();
 	    date = scan.next();
 	    time = scan.next();
 	    scan.close();
 	    
 	    scan = new Scanner(end);
	    scan.useDelimiter(",");
	    endLat = scan.nextDouble();
	    endLon = scan.nextDouble();
	    zero = scan.nextInt();
	    scan.next();
	    double endTime = scan.nextDouble();
	    date = scan.next();
	    time = scan.next();
	    scan.close();
	    extracted = true;
	}
	
	public void interpolate(){//perform linear interpolation
		if(!extracted){
			System.out.println("Error. Extract method not called.");
			System.exit(0);
		}
		
		double slope = (endLon - startLon)/(endLat - startLat);
		double b = startLon - slope*startLat;
		
		double xdist = endLat - startLat;
		double partialD = xdist / (len+1);//how much to increment abscissa value

		double prevX = startLat;
		
		for(int i=0; i<len; i++){
			prevX += partialD;
			lat[i] = prevX;
			double yval = slope*prevX + b;
			lon[i] = yval;
		}
		
		if(startEnd){
			for(int i = 1; i < newPoints.length-1; i++){
				String s = lat[i-1] + "," + lon[i-1] + "," + 0 + "," + alt + "," + startTime + "," + date + "," + time;
				newPoints[i] = s;
			}
		}
		else{
			for(int i = 0; i < newPoints.length; i++){
				String s = lat[i] + "," + lon[i] + "," + 0 + "," + alt + "," + startTime + "," + date + "," + time;
				newPoints[i] = s;
			}
		}
		
	}
	
	public void NNinterpolate(){//perform nearest neighbor interpolation
		double half = (double)newPoints.length;
		half = half/2.0;
		int halfR = (int)half;//round the double to an integer
		
		if(startEnd){
			for(int i = 1; i < newPoints.length-1; i++){
				if(i<halfR){//use the start value
					String s = startLat + "," + startLon + "," + 0 + "," + alt + "," + startTime + "," + date + "," + time;
					newPoints[i] = s;
				}
				else{
					String s = endLat + "," + endLon + "," + 0 + "," + alt + "," + startTime + "," + date + "," + time;
					newPoints[i] = s;
				}
			}
		}
		else{
			for(int i = 0; i < newPoints.length; i++){
				if(i<halfR){//use the start value
					String s = startLat + "," + startLon + "," + 0 + "," + alt + "," + startTime + "," + date + "," + time;
					newPoints[i] = s;
				}
				else{
					String s = endLat + "," + endLon + "," + 0 + "," + alt + "," + startTime + "," + date + "," + time;
					newPoints[i] = s;
				}
			}
		}
	}
	
	public String[] getPoints(){
		return newPoints;
	}
}	

	
	
	
//	public static void main(String[] args){
//		
//		//first read in data from files
//		
//		//General steps to the program:
//		//Read in file that has the string "gaps" appended to it
//		//Read the first line of this file - which includes the lines with the coordinates to be interpolated
//		//for each interpolation, calculate speed and create linear function
//		//add interpolated points to an ArrayList
//		//create a new file with the same name but the string "linInterp" appended to it
//		//fill in all of the lines of data from the "gaps" file including the new data points into the new file
//		
//		 File f = null;
//	     File[] paths;
//	      
//	     try{      
//	         // create new file
//	         f = new File("c:/test");
//	         // returns pathnames for files and directory
//	         paths = f.listFiles();
//	         for(File n:paths){
//	        	 String name = n.getName();
//	        	 int s = name.length();
//	        	 name = name.substring(s-9, s);
//	        	 if(name.equals("_gaps.plt")){
//	    	         System.out.println("Begin");
//	        		 int gaps[][] = new int[10][2];
//	        		 BufferedReader bR = new BufferedReader(new FileReader(n));
//	        		 int count = 0;
//	        		 String line = null;
//	        		 while ((line = bR.readLine())!=null){
//	        			System.out.println(line);
//     	        	    Scanner scan = new Scanner(line);
//     	        	    //anything other than alphanumberic characters, 
//     	        	    //comma, dot or negative sign is skipped
//     	        	    scan.useDelimiter("[^\\p{Alnum},\\.-]");
//	        	        if(count == 0){//scan the first line to get the lines that were removed and their length
//	        	        	   for(int j=0; j<10; j++){
//	        	        	    	if(scan.hasNextInt()){
//	        	        	    		int one = scan.nextInt();
//	        	        	    		int two = scan.nextInt();
//	        	        	    		gaps[j][0]=one;//denotes the length of gap
//	        	        	    		gaps[j][1]=two;//denotes the first point that was removed
//	        	        	    	}
//	        	        	    }
//	        	        	}
//	        	        /*
//	        	         * interpolation:
//	        	         * once we get to a line that is one less than gap[][1] (the starting point of a gap)
//	        	         * 
//	        	         */
//	        	        else if(count>5){
//	        	        	
//	        	        }
//	        	        	scan.close();
//	        	        	count++;
//	        		 }
//	        		 bR.close();
//	        		 System.out.println("Done");
//	        	 }
//	         }
//	      }catch(Exception e){
//		         // if any error occurs
//		         e.printStackTrace();
//		      }
//		
//	}



/*
 	    scan = new Scanner(date);
 	    scan.useDelimiter("-");
 	    int year = scan.nextInt();
 	    int month = scan.nextInt();
 	    int day = scan.nextInt();
 	    scan.close();
 	   
 	    scan = new Scanner(time);
 	    scan.useDelimiter(":");
 	    int hour = scan.nextInt();
 	    int min = scan.nextInt();
 	    int sec = scan.nextInt();
 	    scan.close();
 */