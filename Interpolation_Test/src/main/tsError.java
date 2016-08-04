package main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class tsError{
	private String actual;
	private String interpolated;
	private int len;
	private double error = 0.0;
	private double errorSq = 0.0; //error squared
	
	public tsError(String filenameActual, String filenameInterpolated, boolean actualStyle, boolean interpolatedStyle, int gap){
		//the boolean values indicate whether the file has commas (when set to true) or lacks commas (when set to false)
		//gap indicates how many points were interpolated
		int gapLength = gap;
		
		String a = null;
		String in = null;
		
		File inActual = new File(filenameActual);
		try{
			BufferedReader bR = new BufferedReader(new FileReader(inActual));
			
			String line;
			int count = 0;
			if(actualStyle){//if this is true then the file already has commas
				while((line = bR.readLine()) != null){
					if(count==0)
						a = line;
					else
						a += line;
					count++;
				}
			}
			else{
				while((line = bR.readLine()) != null){
					if(count==0)
						a = line + ",";
					else
						a += line + ",";
					count++;
				}
				//remove the last comma which isn't needed
				a = a.substring(0, a.length()-1);
			}
			bR.close();
		}catch(IOException ex) {
            System.err.println("An IOException was caught!");
            ex.printStackTrace();
        }
		
		File inInterpolated = new File(filenameInterpolated);
		try{
			BufferedReader bR = new BufferedReader(new FileReader(inInterpolated));
			
			String line;
			int count = 0;
			if(interpolatedStyle){//if this is true then the file already has commas
				while((line = bR.readLine()) != null){
					if(count==0)
						in = line;
					else
						in += line;
					count++;
				}
			}
			else{
				while((line = bR.readLine()) != null){
					if(count==0)
						in = line + ",";
					else
						in += line + ",";
					count++;
				}
				//remove the last comma which isn't needed
				a = a.substring(0, a.length()-1);
			}
			bR.close();
		}catch(IOException ex) {
            System.err.println("An IOException was caught!");
            ex.printStackTrace();
        }
		
		actual = a;
		interpolated = in;
		String[] actual_vals = actual.split(",");
		String[] interpolated_vals = interpolated.split(",");
		len = actual_vals.length;
		if(actual_vals.length != interpolated_vals.length){
			System.out.println("Error: unequal lengths");
			System.out.println("Actual file: " + filenameActual + " length: " + actual_vals.length);
			System.out.println("Interpolated: " + filenameInterpolated + " length: " + interpolated_vals.length);
		}
		double[] aHvals = new double[len];
		double[] iHvals = new double[len];
		
		
		for(int i=0; i<(len); i++){
			aHvals[i] = Double.parseDouble(actual_vals[i]);
			iHvals[i] = Double.parseDouble(interpolated_vals[i]);
		}
		
		for(int i=10; i<(10+gapLength); i++){
			error += Math.abs(aHvals[i] - iHvals[i]);
			errorSq += Math.pow((aHvals[i] - iHvals[i]), 2);
		}
	}
	

	public double getErrorSq(){
		return errorSq;
	}
	
	public double getError(){
		return error;
	}
	
	public int getLen(){
		return len;
	}
	
}
