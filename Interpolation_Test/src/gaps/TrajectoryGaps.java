package gaps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import main.writeToFile;

public class TrajectoryGaps {
	public static void main(String[] args){
		
		File f = new File("C:/test/trajectory2/OriginalTrajectories");
		
		//File f = new File("C:/test/trajectory2/OriginalTS");//this is for time series
        
        // returns pathnames for files and directory
        File[] paths = f.listFiles();
                
        for(File fe:paths){        	
        	for(int i=0; i<40; i++){
        		ArrayList<String> lines = new ArrayList<String>(getList(fe));
        		int num2remove = i*50 + 50;
        		String fullname = fe.getName();
        		String name = fullname.substring(0,fullname.length()-4);
        		ArrayList<String> removedLines = new ArrayList<String>(removeLines(lines, num2remove));
        		String[] arrayLines = list2array(removedLines);
        		writeToFile makeFile = new writeToFile(arrayLines, "C:/test/trajectory2/TS_Gaps/" +
        		name + "_" + String.valueOf(num2remove) + ".plt");
        		makeFile.write();
        	}
        }
	}
	
	public static ArrayList<String> getList(File n){//extract lines from file
		ArrayList<String> lines = new ArrayList<String>();
		try{
			BufferedReader bR = new BufferedReader(new FileReader(n));
			String line=null;
			while((line = bR.readLine()) != null)
				lines.add(line);
			bR.close();
		}catch(IOException ex){
			System.err.println("An IOException was caught!");
			ex.printStackTrace();
		}
		return lines;
	}
	
	public static ArrayList<String> removeLines(ArrayList<String> lines, int n){//remove n lines starting after the first 10 points
		ArrayList<String> firstHalf = new ArrayList<String>(lines.subList(0, 16));//takes into account the first 6 lines
		ArrayList<String> secondHalf = new ArrayList<String>(lines.subList(16+n, lines.size()));
		
		//ArrayList<String> firstHalf = new ArrayList<String>(lines.subList(0, 10));//this is for time series
		//ArrayList<String> secondHalf = new ArrayList<String>(lines.subList(10+n, lines.size()));
		
		ArrayList<String> newList = new ArrayList<String>(firstHalf);
		newList.addAll(secondHalf);
		return newList;
	}
	
	public static String[] list2array(ArrayList<String> s){
		String[] array = new String[s.size()];
		for(int i=0; i<s.size(); i++)
			array[i] = s.get(i);
		return array;
	}
}