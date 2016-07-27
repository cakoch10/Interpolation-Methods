package velocity_model;
import java.util.*;
import main.getNum;
import java.io.*;

public class velocityInterpolation {
	
	private File input;
	private int len;
	
	private double[] longitudes;
	private double[] latitudes;
	private double[] times;
	private double[] directions;
	private double[] velocities;
	
	private double vThreshold;//specifies the threshold to use when comparing velocities
	private ArrayList<String> interpolated_lines = new ArrayList<String>();//stores ONLY the interpolated lines
	
	velocityInterpolation(String path, int length){
		input = new File(path);
		longitudes = new double[length];
		latitudes = new double[length];
		times = new double[length];
		directions = new double[length];
		velocities = new double[length];
		len = length;
		String[] lines = new String[length];
		
		try{
			//get line values from .plt file
	        BufferedReader bR = new BufferedReader(new FileReader(input));
			
	        String line = null;
	        int count = 0;
	        
	        while ((line = bR.readLine())!=null){
	        	if(count>5){//skip the first six lines
	        		lines[count - 6] = line;
	        	}
	        	count++;
	        }
	        bR.close();
	        //extract values from lines
	        //some of these calls are redundant because the class v and the class getNum should be combined
	        for(int i = 0; i<len-1; i++){
	        	getNum first = new getNum(lines[i]);
	        	first.extract();
	        	latitudes[i] = first.getLat();
	        	longitudes[i] = first.getLon();
	        	times[i] = first.getTimeD();//store as the time of day in hours
	        		
	        	if(lines[i+1]==null){
	        		int out = i+1;
	        		System.out.println("ERROR. NULL AT POSITION: " + out);
	        		System.out.println("The total length is: " + len);
	        		System.out.println("The latitude is: " + latitudes[i]);
	        	}
	        	v getVelDir = new v(lines[i], lines[i+1]);
	        	directions[i] = getVelDir.getDir();//direction in degrees
	        	velocities[i] = getVelDir.getVel();
	        }
	      //get the final value whose direction and velocity are taken from the point adjacent to it
	        getNum final_val = new getNum(lines[len-1]);
	        final_val.extract();
	        latitudes[len-1] = final_val.getLat();
	        longitudes[len-1] = final_val.getLon();
	        times[len-1] = final_val.getTimeD();
	        directions[len-1] = directions[len-2];
	        velocities[len-1] = velocities[len-2];
		}
		catch(IOException ex) {
	            System.err.println("An IOException was caught!");
	            ex.printStackTrace();
	        }		
	}
	
	
	public String[] beginInterpolation(int[] gaps, double v){//an integer array with the indices of the first points of each gap
		int num_gaps = gaps.length;
		int[] num_points = new int[num_gaps];//this will store the length (as in number of points to be interpolated) of each gap
		vThreshold = v;
		
		int count = 0;
		for(int i:gaps){
			System.out.println("i is: " + i);
			double time_difference = times[i+1] - times[i];//time difference in hours
			System.out.println("time 1: " + times[i]);
			System.out.println("time 2: " + times[i+1]);
			System.out.println("Initial time difference: " + time_difference);
			time_difference = time_difference*60.0*60.0; //convert hours to seconds
			System.out.println("T_D: " + time_difference);
			double fractionalPart = time_difference/5.0;
			double ceiling = Math.floor(fractionalPart);
			int number_of_points = (int)ceiling - 1;
			//check if the number of seconds is divisible by 5 -- if this is the case, then the number_of_points value should be adjusted
			float tf = (float)time_difference;
			int check = Math.round(tf);
			if((Math.abs(tf - (double)check)) < 0.001) // if this is true, then the double is essentially the integer
				if((check%5) == 0)
					number_of_points = check/5 - 1;
			
			System.out.println("THE num of points: " + number_of_points);
			num_points[count] = number_of_points;
			count++;
		}
		
		//find the sum of the gap lengths
		int sum=0;
		for(int i:num_points)
			sum += i;
		String[] new_list = new String[sum+len];//create a list of strings to house the answer list
		
		System.out.println("THE SUM IS: " + sum);
		
		count = 0;//use this as a second index for keeping track of which gaps have been incorporated into the string
		//int count2 = 0;//uses this as a third index for keeping track of our location a gap being interpolated
		for(int i=0; i<new_list.length; i++){
			//we need to check if i is in the range (gaps[count], gaps[count]+num_points[count]]
			boolean is_outside;
			int next_gap = -1;//set to default values
			int next_gap_length = -1;//set to default values
			
			if(count>=gaps.length){
				is_outside=true;
			}
			else{
				next_gap = gaps[count];
				next_gap_length = num_points[count];
				is_outside = (i <= next_gap) || (i > (next_gap_length + next_gap));//returns true if i is outside the gap
			}
		
			if(is_outside){
				//if it's outside then we can just copy the contents from the list
				if(i>=latitudes.length){
					System.out.println("The value of i is: " + i);
					System.out.println("The length of latitudes is " + latitudes.length);
					System.out.println("The value of len is : " + len);
				}
				String lat = Double.toString(latitudes[i]);
				String lon = Double.toString(longitudes[i]);
				String time = Double.toString(times[i]);
				
				new_list[i] = lat + "," + lon + "," + "0,555," + time + ",2009-10-11,14:04:30";
			}
			else{
				//we need to interpolate
				//the nearest known neighbor on the lefthand side has an index of i-1
				//this method assumes that we have updated all of the arrays after each interpolation
				int match_index = getMatch(i-1);
				double[] interpolated_values = new double[5];
				interpolated_values = interpolate(match_index, i-1);
				//UPDATE THE VALUES
				update(interpolated_values, i-1);
				//System.out.println("lat: " + interpolated_values[0]);
	
				
				String lat = Double.toString(interpolated_values[0]);
				String lon = Double.toString(interpolated_values[1]);
				String time = Double.toString(interpolated_values[2]);
				
				new_list[i] = lat + "," + lon + "," + "0,555," + time + ",2009-10-11,14:04:30";
				
				System.out.println(new_list[i]);
				
				interpolated_lines.add(new_list[i]);
				
				//this next branch is necessary to update the indices of the beginning of future gaps that have not yet been interpolated
				if(i == (next_gap_length + next_gap)){//check to see if we've reached the end of this particualr gap
					for(int k = count+1; k < gaps.length; k++){
						gaps[k] = gaps[k] + num_points[count];
					}
					count++;
				}
				//count2++;
				/*if(count2 == num_points[count]-1){//if this is true, we have finished interpolating the particular gap at position count
					count2=0;
					count++;
				}*/
			}
		}
		return new_list;//the new list should be interpolated values AND original values
	}

	public int getMatch(int index){//returns the index of the match value
		double vel1 = velocities[index];
		int matchIndex=0;
		
		double[] differences = new double[len];
				
		for(int i = 0; i<len; i++){
			if(i!=index){
				differences[i] = Math.abs(vel1 - velocities[i]);//difference in velocity
			}
			else{
				//set the difference at the known index to something ridiculously large so that it is not selected as a match
				//we wouldn't want the known index to be selected as a match because it would essentially be copying the
				//nearest neighbor's data every time the interpolation is performed
				differences[i] = Math.pow(10.0, 10.0);
			}
		}
		
		differences[len-1] = Math.pow(10.0, 10.0);//make sure the last point is not selected
		
		ArrayList<Integer> potentialMatches = new ArrayList<>();
		
		for(int i=0; i<len; i++){
			if(differences[i]<vThreshold){
				potentialMatches.add(i);
			}
		}
		
		if(potentialMatches.isEmpty()){
			//this means we need to find the point with the closest velocity, or smallest distance
			for(int i = 0; i<len; i++){
				if(differences[i]<differences[matchIndex]){
					matchIndex = i;
				}
			}
		}
		else if(potentialMatches.size() == 1){
			matchIndex = potentialMatches.get(0);
		}
		else{
			//otherwise, differentiate the points based on time values
			double[] timediffs = new double[potentialMatches.size()];
			int[] indexes = new int[potentialMatches.size()];//stores the index value corresponding to a time difference
			double t_act = times[index];//the time of the point of interest
			
			for(int i=0; i<potentialMatches.size(); i++){
				timediffs[i] = Math.abs(t_act - times[potentialMatches.get(i)]);
				indexes[i] = potentialMatches.get(i);
			}
			
			int currentMin = 0;
			for(int i=0; i<potentialMatches.size(); i++){
				if(timediffs[i]<timediffs[currentMin])
					currentMin = i;
			}
			matchIndex = indexes[currentMin];
		}
		
		return matchIndex;
	}
	
	
	public double acceleration(int index){
		double acceleration = 0.0;//acceleration is change in velocity over change in time (in this case it will be km/hr/hr
		
		if(index > len-3){//check to see if we're dealing with one of the last two points
			double v1 = velocities[index];
			double v2 = velocities[index-1];
			double change_in_v = v1 - v2;
			
			double t1 = times[index];
			double t2 = times[index-1];
			double change_in_t = t1 - t2;//difference in hours
			
			acceleration = change_in_v/change_in_t;
		}
		else{
			double v1 = velocities[index];
			double v2 = velocities[index+1];
			double change_in_v = v2 - v1;
			
			double t1 = times[index];
			double t2 = times[index+1];
			double change_in_t = t2 - t1;//difference in hours
			
			acceleration = change_in_v/change_in_t;
		}
		
		return acceleration;
	}
	
	public double slope(double dir){//returns the slope given the bearing in degrees
		if(dir>=0 && dir<180){
			double part1 = Math.PI*(90 - dir);
			double part2 = part1/180;
			double m = Math.tan(part2);		
			return m;
		}
		else{
			double part1 = Math.PI*(450-dir);
			double part2 = part1/180;
			double m = Math.tan(part2);
			return m;
		}
	}
	
	
	public double[] interpolate(int i_match, int i_neighbor){//indices of the match and neighbor
		//this function returns an array of size five in the form {latitude, longitude, time, direction, velocity}
		double acceleration = acceleration(i_match);
		double m = slope((directions[i_neighbor]+directionChange(i_match))%360.0);
		double init_long_x = longitudes[i_neighbor];
		double init_lat_y = latitudes[i_neighbor];
		double init_vel = velocities[i_neighbor];
		
		double hrs = 1.0/720.0;
		double total_distance = hrs*init_vel+((acceleration*Math.pow(hrs, 2.0))/2.0);//distance in km
				
		//calculate change in x
		double angle = refAngle((directions[i_neighbor]+directionChange(i_match))%360.0);
		angle = Math.toRadians(angle);
		double x_change = total_distance*Math.cos(angle);//note that this distance is in kilometers
		//we need to calculate the initial y value in km
		//we use the approximate conversion of 1 degree of latitude = 110.574 km
		double init_lat_y_km = init_lat_y*110.574;
		//calculate the new y value
		double new_lat_y_km = m*x_change + init_lat_y_km;
		//convert this value to degrees of latitude
		double new_lat = new_lat_y_km/110.574;
		
		//find new longitude
		//One degree of longitude is approximately 111.32*cos(lat) km
		//the new longitude is the original longitude + change in longitude
		//first we convert the change in longitude to degrees of longitude
		double lon_change_degrees = x_change / (111.32*Math.cos(Math.toRadians(new_lat)));
		double new_lon = lon_change_degrees + init_long_x;
		
		double new_time = times[i_neighbor] + hrs;//the new time is five seconds later (five seconds is 1/720 of an hour)
		double v_final = init_vel + acceleration*hrs;
		double new_direction = (directions[i_neighbor]+directionChange(i_match))%360.0;
		
		double[] values = new double[5];
		values[0] = new_lat;
		values[1] = new_lon;
		values[2] = new_time;
		values[3] = new_direction;
		values[4] = v_final;
		
		return values;
	}
	
	public double refAngle(double theta){//returns the reference angle for a given bearing in degrees
		if(theta<0.0)
			theta += 360.0;//make sure theta is positive
			
		if(theta>=0.0 && theta<180.0)
			theta = 90.0 - theta;
		else
			theta = 450.0 - theta;
		
		if(theta<0.0)
			theta += 360.0;//make sure theta is positive
		
		if(theta>=0.0 && theta <= 90.0)//quadrant I
			return theta;
		else if(theta>90.0 && theta<=180.0)//quadrant II
			return 180-theta;
		else if(theta>180.0 && theta<=270.0)//quadrant III
			return theta-180.0;
		else if(theta>270.0 && theta<=360.0)
			return 360.0-theta;
		else{
			System.out.println("ERROR THETA IS OUT OF BOUNDS");
			System.out.println("Theta is: " + theta);
			return theta;
		}
	}
	
	public double directionChange(int index){
		//returns the direction change
		//make sure that index is not pointing to the last recorded point
		if(index == len-1)
			System.out.println("ERROR: index too large");
		
		double change = directions[index+1] - directions[index];
		return change;
	}
	
	public void update(double[] update_values, int index){
		//update the arrays based on new update values with an insertion index
		//insert the new values right after the current value at the given index
		
		double[] temp_long = new double[longitudes.length+1];
		double[] temp_lat = new double[longitudes.length+1];
		double[] temp_times = new double[longitudes.length+1];
		double[] temp_direct = new double[longitudes.length+1];
		double[] temp_vel = new double[longitudes.length+1];
		
		for(int i=0; i<longitudes.length+1; i++){
			if(i == (index+1)){
				temp_lat[i] = update_values[0];
				temp_long[i] = update_values[1];
				temp_times[i] = update_values[2];
				temp_direct[i] = update_values[3];
				temp_vel[i] = update_values[4];
			}
			else if(i<index+1){
				temp_lat[i] = latitudes[i];
				temp_long[i] = longitudes[i];
				temp_times[i] = times[i];
				temp_direct[i] = directions[i];
				temp_vel[i] = velocities[i];
			}
			else{
				temp_lat[i] = latitudes[i-1];
				temp_long[i] = longitudes[i-1];
				temp_times[i] = times[i-1];
				temp_direct[i] = directions[i-1];
				temp_vel[i] = velocities[i-1];
			}
		}
		
		longitudes = new double[len+1];
		latitudes = new double[len+1];
		times = new double[len+1];
		directions = new double[len+1];
		velocities = new double[len+1];
		
		longitudes = temp_long;
		latitudes = temp_lat;
		times = temp_times;
		directions = temp_direct;
		velocities = temp_vel;
		
		len++;
	}
	
	public ArrayList<String> getInterpolatedLines(){
		return interpolated_lines;
	}
}
