package cubic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import main.ExtractLatLon;
import main.writeToFile;

public class cubicTrajGeneral {
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
       
    		//calculate the number of points to interpolate
    		String sub = inF.getName().substring(15, inF.getName().length()-4);//20081026081229_2000
    		int num_points = Integer.parseInt(sub);
    		
    		//get the latitude and longitude
    		ExtractLatLon trajectory = new ExtractLatLon(inF.getAbsolutePath());
    		String lats = trajectory.getLats();
    		String lons = trajectory.getLons();
    		
    		SplineInterpolation i1 = new SplineInterpolation(num_points);
    		i1.setTraj(lats, lons);
    		
    		String[] results = new String[num_points];
    		results = i1.interpolateTraj();//array of interpolated points
    		
    		int nInts = i1.getNumInt();//the number of interpolated points
    		
    		String [] ArrayLines = lines.toArray(new String[lines.size()]);
    		
    		//we need to combine result and ArrayLines
    		String[] final_result = new String[results.length + ArrayLines.length];
    		System.arraycopy(results, 0, final_result, 16, num_points);//insert results into new array
    		System.arraycopy(ArrayLines, 0, final_result, 0, 16);
    		System.arraycopy(ArrayLines, 16, final_result, 16+num_points, ArrayLines.length-16);
    		
    		//now to write to a new file
    		
    		String name = inF.getName();
    		name = "C:/test/trajectory2/Cubic_Traj/" + name.substring(0, name.length()-4)
    			+"_"+Integer.toString(nInts)+ "_Cubic_Traj"+".plt";
    		writeToFile write = new writeToFile(final_result, name);
    		write.write();
    		System.out.println("END LOOP " + i);
        }
	}
}