package other;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import linear_NearestNeighbor.SimpleTS;
import main.writeToFile;

public class correction {
	public static void main(String[] args){
		File f = new File("C:/test/trajectory/correction");
        File[] paths = f.listFiles();
        Double[] sizes = new Double[paths.length];
        for(int i=0; i<paths.length; i++){
        	Double size = new Double(paths[i].length());
        	sizes[i] = size;
        }
        Double[] sizes_sorted = new Double[sizes.length];
        for(int i=0; i<sizes.length; i++){
        	sizes_sorted[i] = sizes[i];
        }
        
        Arrays.sort(sizes_sorted);
        
        File[] paths_sorted = new File[paths.length];//this array will order to files based on their sizes from least to greatest
        
        for(int i=0; i<paths.length; i++){
        	Double currVal = sizes_sorted[i];
        	int index = Arrays.asList(sizes).indexOf(currVal);
        	paths_sorted[i] = paths[index];
        }
		
        for(int i=0; i<paths_sorted.length; i++){//starts with the smallest file and moves to the larger files
        	File inF = paths_sorted[i];
        	
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
    		//this is based on the size
    		int num_points = 450;
    		
    		
    		//get an array of doubles from the string of file lines
    		String hilbert_values = lines.get(0);
    		for(int k=1; k<lines.size(); k++){
    			hilbert_values += lines.get(k);
    		}
    		String[] hArray = hilbert_values.split(",");
    		double[] hilbert_double = new double[hArray.length];
    		
    		for(int k=0; k<hArray.length; k++)
    			hilbert_double[k] = Double.parseDouble(hArray[k]);
    		
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
    		name = "C:/test/trajectory/correction/" + name.substring(0, name.length()-12)+"_gaps_TS_Linear.plt";
    		writeToFile write = new writeToFile(stringVals, name);
    		write.write();
    		System.out.println("END LOOP");
        }
	}

}
