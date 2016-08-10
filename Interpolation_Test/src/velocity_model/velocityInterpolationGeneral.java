package velocity_model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import main.writeToFile;

public class velocityInterpolationGeneral {
	public static void main(String[] args){
		//begin with trajectory data
		//get files with gaps
		File f = new File("C:/test/trajectory2/Trajectory_Gaps");
        File[] paths = f.listFiles();
        
        for(int i=0; i<paths.length; i++){
        	File inF = paths[i];
        	
        	ArrayList<String> lines = new ArrayList<String>();//stores all lines of the file including the first six
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
    		
    		int len = lines.size() - 6;
    		
    		velocityInterpolation model = new velocityInterpolation(inF.getAbsolutePath(), len);
    		
    		//calculate the number of points to interpolate
    		String sub = inF.getName().substring(15, inF.getName().length()-4);//20081026081229_2000
    		int num_points = Integer.parseInt(sub);
    		
    		//convert to array
    		int[] gaps = new int[1];
    		gaps[0] = 10;
    		
    		String[] results = model.beginInterpolation2(gaps, 0.001, num_points);
    		
    		//now to write to a new file
    		
    		String name = inF.getName();
    		name = "C:/test/trajectory2/Velocity/" + name;
    		writeToFile write = new writeToFile(results, name);
    		write.write();
    		System.out.println("END LOOP " + i);
        }
	}
}