package velocity_model;
import main.error;

public class optimize {
	private String pathname;//name of the path
	private int len;//length of the original file (with gap)
	private int[] gaps;//indices of the starting points of the gaps to be interpolated
	
	private String[] optimal_points;//array to store the optimal points
	
	
	public optimize(String path, int length, int[] g){
		pathname = path;
		len = length;
		gaps = g;
		
		
		
	}
	
	public String[] getLines(){
		return optimal_points;
	}
	
	public double compare(String[] actual, String[] interpolated){
		error e = new error(actual, interpolated, actual.length);
		return e.returnErrCum();
	}

}
