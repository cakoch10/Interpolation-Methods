package linear_NearestNeighbor;
public class SimpleTS {
	private int startHV;//first Hilbert value
	private int endHV;//end Hilbert value
	private int startI;//start Hilbert index
	private int endI;//end Hilbert index
	
	private int num_vals;//number of values to interpolate
	private double[] interpolated_values;
	
	public SimpleTS(int starth, int endh, int starti, int endi){
		startHV = starth;
		endHV = endh;
		startI = starti;
		endI = endi;
		
		num_vals = endI - startI - 1;//calculate the number of points to interpolate
		
		interpolated_values = new double[num_vals];
	}
	
	public void linearInterpolation(){
		double slope = (double)(endHV - startHV) / (double)(endI - startI);
		double b = (double)startHV - slope*((double)startI);
		
		for(int i = 0; i<num_vals; i++){
			int xval = startI + i + 1;
			interpolated_values[i] = slope*xval + b;
		}
	}
	
	public void nearestNeighbor(){
		for(int i = 0; i<num_vals; i++){
			if(i < (num_vals / 2))
				interpolated_values[i] = startHV;
			else
				interpolated_values[i] = endHV;
		}
		
	}
	
	public double[] getInterpolatedValues(){
		return interpolated_values;
	}

}