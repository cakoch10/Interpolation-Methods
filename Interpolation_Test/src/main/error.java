package main;
import java.util.Scanner;

public class error {
	private String[] real;//a list of strings of actual latitude and longitude trajectory points
	private String[] artificial;//a list of strings of interpolated values
	private int len;//the length of the real and artificial strings
	
	private boolean executed = false;//checks whether the calculate method has been executed
	
	private double[] err;//amount of error in km
	
	public error(String[] r, String[] a, int l){
		real = new String[l];
		real = r;
		artificial = new String[l];
		artificial = a;
		err = new double[l];
		len = l;
		if(r.length != a.length){
			System.out.println("Error, unequal lengths");
			System.out.println("Length of real: " + r.length + " Length of artificial: " + a.length);
			System.out.println("Actual: " + real[0]);
			System.out.println("Artificial: " + artificial[0]);
			System.exit(0);
		}
	}
	
	public void calculate(){//calculate the error for each line
		for(int i=0; i<len; i++){
			getNum point1 = new getNum(real[i]);
			point1.extract();
			getNum point2 = new getNum(artificial[i]);
			point2.extract();
			
			//get latitude and longitude values
			double x = point1.getLon();
			double y = point1.getLat();
			
			double x1 = point2.getLon();
			double y1 = point2.getLat();
			
			//implements the Haversine formula
			//calculates distance between the two points taking into consideration the spherical curvature of earth's surface
			final double r = 6371.0; //radius of earth in km
			
			x = Math.toRadians(x);
			y = Math.toRadians(y);
			
			x1 = Math.toRadians(x1);
			y1 = Math.toRadians(y1);
			
			double half1 = (y-y1) / 2;
			double half2 = (x-x1) / 2;
					
			double part1 = Math.sin(half1)*Math.sin(half1) + Math.cos(y)*Math.cos(y1)*Math.sin(half2)*Math.sin(half2);
			double part2 = Math.sqrt(part1);
			double distance = 2*r*Math.asin(part2);//distance is in km due to units of earth's radius
			
			if(Double.isNaN(distance)){
				System.out.println("x: " + x1);
				System.out.println("y: " + y1);
				System.out.println(part1);
				System.out.println(part2);
			}
			
			err[i] = distance;
		}
		
		executed = true;
	}
	
	public void calculate2(int gapLen){//calculate the error for each line
		for(int i=10; i<(gapLen+10); i++){
			getNum point1 = new getNum(real[i]);
			point1.extract();
			getNum point2 = new getNum(artificial[i]);
			point2.extract();
			
			//get latitude and longitude values
			double x = point1.getLon();
			double y = point1.getLat();
			
			double x1 = point2.getLon();
			double y1 = point2.getLat();
			
			//implements the Haversine formula
			//calculates distance between the two points taking into consideration the spherical curvature of earth's surface
			final double r = 6371.0; //radius of earth in km
			
			x = Math.toRadians(x);
			y = Math.toRadians(y);
			
			x1 = Math.toRadians(x1);
			y1 = Math.toRadians(y1);
			
			double half1 = (y-y1) / 2;
			double half2 = (x-x1) / 2;
					
			double part1 = Math.sin(half1)*Math.sin(half1) + Math.cos(y)*Math.cos(y1)*Math.sin(half2)*Math.sin(half2);
			double part2 = Math.sqrt(part1);
			double distance = 2*r*Math.asin(part2);//distance is in km due to units of earth's radius
			
			if(Double.isNaN(distance)){
				System.out.println("x: " + x1);
				System.out.println("y: " + y1);
				System.out.println(part1);
				System.out.println(part2);
			}
			
			err[i] = distance;
		}
		
		executed = true;
	}
		
	public double[] returnErr(){//returns the list of error values
		if(!executed){
			System.out.println("Error. Calculate method has not been executed.");
			System.exit(0);
		}
		
		return err;
	}
	
	public double returnErrCum(){//returns the total cumulative error
		if(!executed){
			System.out.println("Error. Calculate method has not been executed.");
			System.exit(0);
		}
		double sum = 0.0;
		for(int i=0; i<len; i++){
			sum += err[i];
			if(err[i]<0){
				System.out.println("Error is negative");
			}
			if(Double.isNaN(sum)){
				System.out.println("NaN at " + i + " with len of " + len);
				System.out.println(err[i]);
				System.exit(0);
			}
		}
		return sum;//total error in kilometers
	}
	
	
	public double returnErrCumSq(){//returns the total cumulative error squared
		if(!executed){
			System.out.println("Error. Calculate method has not been executed.");
			System.exit(0);
		}
		
		double sum = 0.0;
		for(int i=0; i<len; i++)
			sum = sum + err[i]*err[i];
			
		return sum;//total error in kilometers
	}
	
	
	public double realDist(){//returns the total distance covered in the original trajectory
		
		double dist = 0.0;
		
		for(int i=0; i<len-1; i++){
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
	
	public double dist(double x1, double y1, double x2, double y2){
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
}


/*
double startLat = scan.nextDouble();
 double startLon = scan.nextDouble();
 int zero = scan.nextInt();
 int alt = scan.nextInt();
 double startTime = scan.nextDouble();
 int year = scan.nextInt();
 int month = scan.nextInt();
 int day = scan.nextInt();
 int hour = scan.nextInt();
 int min = scan.nextInt();
 int sec = scan.nextInt();

 System.out.println(startLat);
 System.out.println(startLon);
 System.out.println(zero);
 System.out.println(alt);
 System.out.println(startTime);
 System.out.println(year);
 System.out.println(month);
 System.out.println(day);
 System.out.println(hour);
 System.out.println(min);
 System.out.println(sec);
 
 
 
 		//implements the Haversine formula
		final double r = 6371.0; //radius of earth in km
		
		double x = Math.toRadians(116.318417);
		double y = Math.toRadians(39.984702);
		
		double x1 = Math.toRadians(116.316911);
		double y1 = Math.toRadians(39.984568);
		
		double half1 = (y-y1) / 2;
		double half2 = (x-x1) / 2;
				
		double part1 = Math.sin(half1)*Math.sin(half1) + Math.cos(y)*Math.cos(y1)*Math.sin(half2)*Math.sin(half2);
		double part2 = Math.sqrt(part1);
		double distance = 2*r*Math.asin(part2);
		
		double test = 39747.1124074074+distance;
		
		//System.out.println(test);
		
		
		String start = "39.926367,116.336141,0,492,39747.1124074074,2008-10-26,02:41:52";
		
		Scanner scan = new Scanner(start);
		scan.useDelimiter(",");
		
double startLat = scan.nextDouble();
 double startLon = scan.nextDouble();
 int zero = scan.nextInt();
 int alt = scan.nextInt();
 double startTime = scan.nextDouble();
 String date = scan.next();
 String time = scan.next();

 System.out.println(startLat);
 System.out.println(startLon);
 System.out.println(zero);
 System.out.println(alt);
 System.out.println(startTime);
 System.out.println(date);
 System.out.println(time);
 
 scan.close();
 
 
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
 
 System.out.println(year);
 System.out.println(month);
 System.out.println(day);
 System.out.println(hour);
 System.out.println(min);
 System.out.println(sec);
		scan.close();
*/