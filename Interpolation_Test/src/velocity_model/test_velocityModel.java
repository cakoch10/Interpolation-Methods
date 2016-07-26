package velocity_model;
import java.io.*;
import java.util.*;

import main.writeToFile;

public class test_velocityModel {
	public static void main(String args[]){
		String p = "C:/test/velocity_interpolation/test2/with_gaps.plt";
		int size = 20194;
		
		velocityInterpolation test1 = new velocityInterpolation(p, size);
		int[] g = new int[1];
		g[0] = 18660;
		
		
		String[] final_list;
		final_list = test1.beginInterpolation(g, 1.0);

		ArrayList<String> ipoints;//only the interpolated points
		ipoints = test1.getInterpolatedLines();
		
		String[] ipointsS = new String[ipoints.size()];
		
		for(int i=0; i<ipointsS.length; i++){
			ipointsS[i] = ipoints.get(i);
		}
		
		writeToFile interFile = new writeToFile(ipointsS, "C:/test/velocity_interpolation/test2/interpolated_points.plt");
		interFile.write();
		
		writeToFile copy = new writeToFile(final_list, "C:/test/velocity_interpolation/test2/model_vel.plt");
		copy.write();
		
	/*	
	    String[] actual_list = new String[1477];
		File input = new File("C:/test/velocity_interpolation/20081028003826.plt");

		try{
			BufferedReader bR = new BufferedReader(new FileReader(input));
			
			String line = null;
	        int count = 0;
	        
	        while ((line = bR.readLine())!=null){
	        	actual_list[count] = line;
	        	count++;
	        }
			
			bR.close();
		}catch(IOException ex) {
            System.err.println("An IOException was caught!");
            ex.printStackTrace();
        }
		String[] lin_model = new String[1477];
		
		input = new File("C:/test/velocity_interpolation/lin_model.plt");

		try{
			BufferedReader bR = new BufferedReader(new FileReader(input));
			
			String line = null;
	        int count = 0;
	        
	        while ((line = bR.readLine())!=null){
	        	lin_model[count] = line;
	        	count++;
	        	if(count==1477)
	        		break;
	        }
			
			bR.close();
		}catch(IOException ex) {
            System.err.println("An IOException was caught!");
            ex.printStackTrace();
        }
		
		error e1 = new error(actual_list, lin_model, 1477);
		e1.calculate();
		double e = e1.returnErrCum();
		System.out.println("Distance of error squared is: " + e);
		double distance = e1.realDist();
		System.out.println("Actual distance is: " + distance);*/
		
		/*String start = "40.004719,116.30742,0,-3,39749.1108333333,2008-10-28,02:39:36";
		String end = "40.004198,116.310097,0,32,39749.1140740741,2008-10-28,02:44:16";
		
		linInterp comparison = new linInterp(start, end, 55);
		comparison.extractInfo();
		comparison.interpolate();
		String[] with_interpolation = new String[57];
		with_interpolation = comparison.getPoints();
		for(String s:with_interpolation)
			System.out.println(s);*/
		
	}

}
