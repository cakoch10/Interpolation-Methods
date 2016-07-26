package main;
import java.io.*;
import java.util.*;

public class getNum {
	
	private String main;
	
	private double lat;
	private double lon;
	private double time;
	
	private boolean extracted = false;//check if extract method has been executed
	
	public getNum(String s){
		main = s;
	}
	
	public void extract(){
		if(main == null)
			System.out.println("Null argument: ERROR");
		Scanner scan = new Scanner(main);
		scan.useDelimiter(",");
		lat = scan.nextDouble();
		lon = scan.nextDouble();
		scan.next();
		scan.next();
		time = scan.nextDouble();
 	    scan.close();
 	    extracted = true;
	}
	
	public double getLat(){
		if(!extracted)
			System.out.println("ERROR EXTRACT NOT EXECUTED, lat");
		return lat;
	}
	
	public double getLon(){
		if(!extracted)
			System.out.println("ERROR EXTRACT NOT EXECUTED, lon");
		return lon;
	}
	public double getTime(){
		if(!extracted)
			System.out.println("ERROR EXTRACT NOT EXECUTED, time");
		return time;
	}
	
	public double getTimeD(int d){//returns the time of day in hours
		if(d==0){
			time nums = new time(time);
			double ntime = ((double)nums.getHour());
			ntime += (nums.getMin()/60.0);
			ntime += nums.getSec()/3600.0;
			return ntime;
		}
		else{
			time nums = new time(time);
			double ntime = ((double)nums.getHour());
			double y = (double)nums.getMin()/60.0;
			ntime += (nums.getMin()/60.0);
			ntime += nums.getSec()/3600.0;
			return ntime;
		}
	}

}