package cubic;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import main.ExtractLatLon;
import main.writeToFile;

public class CubicSplineMainTraj {
	
	public static void main(String[] args){
		//begin with trajectory data
				//get files with gaps
				File f = new File("C:/test/trajectory/Trajectory_with_gaps");
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
		        
		        File[] paths_sorted = new File[paths.length];//this array will order the files based on their sizes from least to greatest
		        
		        for(int i=0; i<paths.length; i++){
		        	Double currVal = sizes_sorted[i];
		        	int index = Arrays.asList(sizes).indexOf(currVal);
		        	paths_sorted[i] = paths[index];
		        }
				
		        for(int i=0; i<paths_sorted.length; i++){//starts with the smallest file and moves to the larger files
		        	File inF = paths_sorted[i];
		        	
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
		    		//this is based on the size
		    		int num_points=0;
		    		if(i==0)
		    			num_points = 50;
		    		else if(i==1)
		    			num_points = 1050;
		    		else if(i==2)
		    			num_points = 150;
		    		else if(i==3)
		    			num_points = 1850;
		    		else if(i==4)
		    			num_points = 1750;
		    		else if(i==5)
		    			num_points = 250;
		    		else if(i==6)
		    			num_points = 350;
		    		else if(i==7)
		    			num_points = 1650;
		    		else if(i==8)
		    			num_points = 450;
		    		else if(i==9)
		    			num_points = 1550;
		    		else if(i==10)
		    			num_points = 1450;
		    		else if(i==11)
		    			num_points = 550;
		    		else if(i==12)
		    			num_points = 650;
		    		else if(i==13)
		    			num_points = 1350;
		    		else if(i==14)
		    			num_points = 750;
		    		else if(i==15)
		    			num_points = 1250;
		    		else if(i==16)
		    			num_points = 850;
		    		else if(i==17)
		    			num_points = 1150;
		    		else if(i==18)
		    			num_points = 950;
		    		else if(i==19)
		    			num_points = 1950;
		    		
		    		
		    		
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
		    		name = "C:/test/trajectory/Cubic_Traj/" + name.substring(0, name.length()-4)+"_Cubic_Traj_"+ Integer.toString(nInts)+ ".plt";
		    		writeToFile write = new writeToFile(final_result, name);
		    		write.write();
		    		System.out.println("END LOOP");
		        }
	}

}
