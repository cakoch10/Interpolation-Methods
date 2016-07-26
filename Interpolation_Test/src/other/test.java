package other;
import java.io.*;
import java.util.*;

import linear_NearestNeighbor.linInterp;
import main.error;
import main.writeToFile;

public class test {
	
	public static void main(String[] args){
		String one = "39.926367,116.336141,0,492,39747.1124074074,2008-10-26,02:41:52";
		String two = "39.933517,116.187223,0,33,39747.2503472222,2008-10-26,06:00:30";
		int ln = 2297;//this is how many lines we're dealing with
		
		File in = new File("C:/test/simple4.plt");
		
		String[] real = new String[ln];
		
		try{
			BufferedReader bR = new BufferedReader(new FileReader(in));
			
			String s = null;
			int c = 0;
			
			while((s = bR.readLine()) != null){
				real[c] = s;
				c++;
			}
			
			bR.close();
		}
		catch(IOException ex) {
            System.err.println("An IOException was caught!");
            ex.printStackTrace();
        }
		
		
		
		linInterp t1 = new linInterp(one, two, ln-2, true);
		t1.extractInfo();
		t1.interpolate();
		
		String[] newS = new String[ln];
		newS = t1.getPoints();
		
		//use error class
		error compare = new error(real, newS, ln);
		
		String write = "C:/test/simple_gaps4.plt";
		writeToFile add = new writeToFile(newS, write);
		add.write();
		
		compare.calculate();
		double total_Error = compare.returnErrCum();
		
		System.out.println("The total distance traveled is: " + compare.realDist());
		
		System.out.println("The total error in km is: " + total_Error);
		
		
		
		
//		for(String s:newS)
//			System.out.println(s);
//		System.out.println(" ");
//		for(String s:real)
//			System.out.println(s);
		
	}

}
