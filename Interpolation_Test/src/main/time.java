package main;

public class time {
	private double days; //represents days since 12/30/1899
	
	private int hour;//gives the hr of the day in the range [0,24]
	private int min;
	private int sec;
	
	private double fs;
	
	public time(double d){
		days = d;
		int dwhole = (int)days;
		double dfrac = days - (double)dwhole;
		
		double hrs = 24*dfrac;
		hour = (int)hrs;
		double hfrac = hrs - (double)hour;
		
		double mn = hfrac*60;
		min = (int)mn;
		double mfrac = mn - (double)min;
		
		double sc = mfrac*60;
		sec = (int)sc;
		fs = sc - (double)sec;
		if(fs>=0.99)
			sec++;
	}
	
	public void print(){
		System.out.println("H: " + hour + " M: " + min + " S: " + sec + " FS: " + fs);
	}
	
	public int getMin(){
		return min;
	}
	
	public int getSec(){
		return sec;
	}
	
	public int getHour(){
		return hour;
	}

}
