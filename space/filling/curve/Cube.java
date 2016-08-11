package com.space.filling.curve;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Cube implements Serializable{
	private static final long serialVersionUID = 1L;
	public double upperLeftLat;
	public double upperLeftLng;
	public double lowerRightLat;
	public double lowerRightLng;
	public long startTime;
	public long endTime;
	
	public void setTimeBound(String startTime, String endTime)
	{
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = formatter.parse(startTime);
			this.startTime = date.getTime();
			date = formatter.parse(endTime);
			this.endTime = date.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
