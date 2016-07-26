package main;
import java.util.Arrays;

public class OrderPoints {
	
	private Double[] x;
	private Double[] y;
	
	private Double[] xOrdered;
	private Double[] yOrdered;
	
	public OrderPoints(Double[] xvals, Double[] yvals){
		x = xvals;
		y = yvals;
		
		if(x.length != y.length){
			System.out.println("Error. Unequal lengths. OrderPoints");
			System.exit(0);
		}
		
		xOrdered = new Double[x.length];
		System.arraycopy(x, 0, xOrdered, 0, x.length);
		yOrdered = new Double[y.length];
		
		Arrays.sort(xOrdered);
		
		//order the y values
		for(int i=0; i<x.length; i++){
			Double currentVal = xOrdered[i];			
			int index = Arrays.asList(x).indexOf(currentVal);
			
			/*
			This is an alternative to the above code
			for(int k=0; k<x.length; k++){
				if(currentVal.equals(x[k])){
					index = k;
					break;
				}
			}
			*/
			if(index<0){
				System.out.println("Index is less than 0");
				System.exit(0);
			}
			yOrdered[i] = y[index];
		}
				
	}
	
	public Double[] getX(){
		return xOrdered;
	}
	
	public Double[] getY(){
		return yOrdered;
	}

}
