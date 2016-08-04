package linear_NearestNeighbor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import main.writeToFile;

public class linTS_General {
	public static void main(String[] args){
		File f = new File("C:/test/trajectory2/TS_Gaps");
        File[] paths = f.listFiles();
        
        for(int i=0; i<paths.length; i++){//starts with the smallest file and moves to the larger files
        	File inF = paths[i];
        	
        	ArrayList<String> lines = new ArrayList<String>();//stores all lines of the file
    		try{
    			BufferedReader bR = new BufferedReader(new FileReader(inF));
    			String line=null;
    			while((line = bR.readLine()) != null)
    				lines.add(line);
    			bR.close();
    		}catch(IOException ex){
    			System.err.println("An IOException was caught!");
    			ex.printStackTrace();
    		}
    		
    		//calculate the number of points to interpolate
    		String sub = inF.getName().substring(18, inF.getName().length()-4);
    		int num_points = Integer.parseInt(sub);
    		
    		//get an array of doubles from the string of file lines
    		double[] hilbert_double = new double[lines.size()];
    		
    		for(int k=0; k<lines.size(); k++)
    			hilbert_double[k] = Double.parseDouble(lines.get(k));
    		
    		int starthil = (int)hilbert_double[9];//first Hilbert value
    		int endhil = (int)hilbert_double[10];//second Hilbert value
    		
    		int firstIndex = 9;//first index occurs at the tenth Hilbert value
    		int lastIndex = 10 + num_points;//index of the last known Hilbert value
    		
    		SimpleTS interpolation = new SimpleTS(starthil, endhil, firstIndex, lastIndex);
    		interpolation.linearInterpolation();
    		double[] newValues = interpolation.getInterpolatedValues();
    		
    		double[] final_result = new double[newValues.length + hilbert_double.length];
    		System.arraycopy(newValues, 0, final_result, 10, num_points);//insert results into new array
    		System.arraycopy(hilbert_double, 0, final_result, 0, 10);
    		System.arraycopy(hilbert_double, 10, final_result, 10+num_points, hilbert_double.length-10);
    		
    		
    		
    		String[] stringVals = new String[final_result.length];
    		for(int k=0; k<final_result.length; k++){
    			stringVals[k] = Double.toString(final_result[k]);
    		}
    		
    		//now to write to a new file
    		String name = inF.getName();
    		name = "C:/test/trajectory2/Lin_TS/" + name.substring(0, name.length()-4)+"_Linear.plt";
    		writeToFile write = new writeToFile(stringVals, name);
    		write.write();
    		System.out.println("END LOOP "+i);
        }
	}
}
