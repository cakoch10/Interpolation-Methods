package velocity_model;
import main.getNum;

public class v {
	
	private double vel=0.0;//velocity in km per hour
	private String first;//first string
	private String second;//second string
	private double direction;//compass direction in degrees
	
	private double lat0;
	private double lon0;
	private double t0;
	private double lat1;
	private double lon1;
	private double t1;
	
	public v(String s1, String s2){
		first = s1;
		second = s2;
		
		getNum info1 = new getNum(first);
		info1.extract();
		lat0 = info1.getLat();
		lon0 = info1.getLon();
		t0 = info1.getTime();
		
		getNum info2 = new getNum(second);
		info2.extract();
		lat1 = info2.getLat();
		lon1 = info2.getLon();
		t1 = info2.getTime();
		
		double distance = dist(lon0, lat0, lon1, lat1);//gives the distance between the points in kilometers
		
		double tDays = t1 - t0;//gives the time difference in days
		double time = tDays*24;//we convert the time to hours
		
		vel = distance / time;
		
		direction = dir(lon0, lat0, lon1, lat1);		
	}
	
	
	public double getVel(){
		return vel;
	}
	
	public double getDir(){
		return direction;
	}
	
	public double dir(double lon0, double lat0, double lon1, double lat1){
		lon0 = Math.toRadians(lon0);
		lat0 = Math.toRadians(lat0);
		
		lon1 = Math.toRadians(lon1);
		lat1 = Math.toRadians(lat1);
		
		double d = lon1 - lon0;
		double part1 = Math.sin(d)*Math.cos(lat1);
		double part2 = Math.cos(lat0)*Math.sin(lat1) - Math.sin(lat0)*Math.cos(lat1)*Math.cos(d);
		
		return Math.toDegrees(Math.atan2(part1, part2));		
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
