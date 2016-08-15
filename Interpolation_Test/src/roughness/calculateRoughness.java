package roughness;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import velocity_model.v;

public class calculateRoughness {
	
	private File inFile;
	private double sum;
	private int size;
	
	public calculateRoughness(String path){
		inFile = new File(path);
		ArrayList<String> lines = new ArrayList<>();
		
		try{
			//get line values from .plt file
	        BufferedReader bR = new BufferedReader(new FileReader(inFile));
			
	        String line = null;
	        int count = 0;
	        
	        while ((line = bR.readLine())!=null){
	        	if(count>5){//skip the first six lines
	        		lines.add(line);
	        	}
	        	count++;
	        }
	        bR.close();
		}
		catch(IOException ex) {
	            System.err.println("An IOException was caught!");
	            ex.printStackTrace();
	        }
		sum = 0.0;
		for(int i=1; i<lines.size()-1; i++){
			v Dir = new v(lines.get(i-1), lines.get(i));
			v Dir2 = new v(lines.get(i), lines.get(i+1));
			sum += Math.abs((Dir.getDir() - Dir2.getDir()));
		}
		size = lines.size();
	}
	
	
	public double getRoughness(){
		double d = sum / (double)size;
		return d;
	}
}
