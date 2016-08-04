package LOESS;

import java.util.ArrayList;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.interpolation.LoessInterpolator;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.interpolation.UnivariateInterpolator;

import main.OrderPoints;

public class LoessInterpolation {
	
	private String trajLat;//latitude values as a string separated by commas
	private String trajLon;//longitude values a string separated by commas
	private int gapLen;
	private double[] tsPoints;
	private int NumInterpolatedPoints;//gives the number of interpolated points (since some were excluded due to repitition
	
	private boolean checkTS = false;
	private boolean checkTraj = false;
	
	public LoessInterpolation(int gl){//constructor consists of a single integer representing the gapLen
		gapLen = gl;
	}
	
	public void setTraj(String lats, String lons){
		trajLat = lats;
		trajLon = lons;
		checkTraj=true;
	}
	
	public void setTS(double[] TS){
		tsPoints = TS;
		checkTS=true;
	}
	
	public String[] interpolateTraj(){//returns ONLY a list of the interpolated points
		
		if(!checkTraj){
			System.out.println("Error. Didn't call setTraj function");
			System.exit(0);
		}
		
		String[] lines = new String[gapLen];
		
		String[] latArray = trajLat.split(",");
		String[] lonArray = trajLon.split(",");
		Double[] latvalArray = new Double[latArray.length];
		Double[] lonvalArray = new Double[lonArray.length];
		
		for (int i = 0; i < latArray.length; i++){
			latvalArray[i] = new Double(Double.parseDouble(latArray[i]));
			lonvalArray[i] = new Double(Double.parseDouble(lonArray[i]));
		}
		
		//calculate interpolant
		
		//final WeightedObservedPoints obs = new WeightedObservedPoints();
		
		Double[] xordered = new Double[lonvalArray.length];
		Double[] yordered = new Double[latvalArray.length];
		/*
		x = lonvalArray.clone();//create clones so the original is unaffected
		y = latvalArray.clone();
		*/
		UnivariateInterpolator interpolator = new LoessInterpolator();//LOESS Interpolation
		
		//we need to order the pairs
		OrderPoints ord = new OrderPoints(lonvalArray,latvalArray);
		xordered = ord.getX();
		yordered = ord.getY();
		
		ArrayList<Double> xPoints = new ArrayList<>();
		ArrayList<Double> yPoints = new ArrayList<>();
		
		xPoints.add(xordered[0]);
		yPoints.add(yordered[0]);
		
		for(int i=1; i<lonvalArray.length; i++){
			if(!xordered[i].equals(xordered[i-1])){//this constraint is necessary to prevent repeated x values
				xPoints.add(xordered[i]);
				yPoints.add(yordered[i]);
			}
		}
		
		double[] xD = new double[xPoints.size()];		
		double[] yD = new double[yPoints.size()];
		
		for(int i=0; i<xD.length; i++){
			xD[i] = xPoints.get(i).doubleValue();
			yD[i] = yPoints.get(i).doubleValue();
			if(Double.isNaN(yD[i]) || Double.isNaN(xD[i])){
				System.out.println("NaN at x: " + xD[i] + " y: " + yD[i]);
			}
		}
		
		//create and store the interpolant
		UnivariateFunction function = interpolator.interpolate(xD, yD);
		
		NumInterpolatedPoints = xD.length;

		
		//in order to interpolate we get the first known longitude value (serving as the x value)
		
		double xinit = lonvalArray[9];
		double xfinal = lonvalArray[10];
		
		double xdist = xfinal-xinit;
		double increment = xdist / ((double)gapLen + 1.0);
		
		double lon = xinit;
		
		for(int i=0; i<gapLen; i++){
			lon += increment;
			
			double lat = function.value(lon);
			
			if(Double.isNaN(lat)){
				System.out.println("NaN at lon: " + lon);
				System.out.println("min lon is: " + xordered[0]);
				System.out.println("max lon is: " + xordered[xordered.length-1]);
				System.exit(0);
			}
			
			//record result, includes filler values to maintain structure of GeoLife data
			lines[i] = Double.toString(lat) + "," + Double.toString(lon) + ",0,661,39792.3281481481,2008-12-10,07:52:32";
		}
		return lines;
	}
	
	public int getNumInt(){//returns the number of interpolated points
		return NumInterpolatedPoints;
	}
	
	public double[] interpolateTS(){
		double[] vals = new double[gapLen];
		
		if(!checkTS){
			System.out.println("Error: setTS never called");
			System.exit(0);
		}
		
		//-----------------------------------------------------------
			
		Double[] initialXs = new Double[tsPoints.length];
		Double[] initialYs = new Double[tsPoints.length];
		
		//store time series as two arrays of Doubles		
		
		for(int i=0; i<tsPoints.length; i++){
			initialYs[i] = new Double(tsPoints[i]);
			if(i<10)
				initialXs[i] = new Double((double)i);
			else
				initialXs[i] = new Double((double)(i + gapLen));
		}
		
		//order the arrays
		
		OrderPoints ord = new OrderPoints(initialXs, initialYs);
		
		initialXs = ord.getX();
		initialYs = ord.getY();
		
		//remove duplicates (ensure x's are monotonically increasing)
		
		ArrayList<Double> xPoints = new ArrayList<>();
		ArrayList<Double> yPoints = new ArrayList<>();
		
		xPoints.add(initialXs[0]);
		yPoints.add(initialYs[0]);
		
		for(int i=1; i<initialXs.length; i++){
			if(!initialXs[i].equals(initialXs[i-1])){//this constraint is necessary to prevent repeated x values
				xPoints.add(initialXs[i]);
				yPoints.add(initialYs[i]);
			}
		}
		
		//store these values as type double
		
		double[] xD = new double[xPoints.size()];		
		double[] yD = new double[yPoints.size()];
		
		for(int i=0; i<xD.length; i++){
			xD[i] = xPoints.get(i).doubleValue();
			yD[i] = yPoints.get(i).doubleValue();
			if(Double.isNaN(yD[i]) || Double.isNaN(xD[i])){
				System.out.println("NaN at x: " + xD[i] + " y: " + yD[i]);
			}
		}
		
		UnivariateInterpolator interpolator = new LoessInterpolator();
		
		//create and store the interpolant
		UnivariateFunction function = interpolator.interpolate(xD, yD);
		
		NumInterpolatedPoints = xD.length;
		
		//--------------------------------------------------------
		
		//interpolate
		
		for(int i=0; i<gapLen; i++){
			int xval = i+10;
			double xdouble = (double)xval;
			double yval = function.value(xdouble);
			vals[i] = yval;
		}
		
		return vals;
	}
}