package com.space.filling.curve;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ExtractLatLon {
	private String filename;//file path name as a string
	private String latitudes;//stores the latitude values separated by commas
	private String longitudes;//stores the longitudes values separated by commas
	
	private boolean skipSix;//indicates whether the reader should skip the first six lines or not	
	
	private ArrayList<String> lines = new ArrayList<String>();
	
	public ExtractLatLon(String fname, boolean s){
		filename = fname;
		skipSix = s;
		
		File inName = new File(filename);
		
		try{
			BufferedReader bR = new BufferedReader(new FileReader(inName));
			
			String line;
			if(skipSix){//means we should skip the first six lines
				int count = 0;
				while((line = bR.readLine()) != null){
					if(count>5)
						lines.add(line);
					count++;
				}
			}
			else{
				while((line = bR.readLine()) != null){
					lines.add(line);
				}
			}
			
			bR.close();
		}catch(IOException ex) {
            System.err.println("An IOException was caught!");
            ex.printStackTrace();
        }
		
		getNum initial = new getNum(lines.get(0));
		System.out.println(lines.get(0));
		initial.extract();
		latitudes = Double.toString(initial.getLat());
		longitudes = Double.toString(initial.getLon());
		latitudes += ",";
		longitudes += ",";
		
		for(int i = 1; i<lines.size(); i++){//start at 1 since we already have the first values
			getNum lineInformation = new getNum(lines.get(i));
			lineInformation.extract();
			latitudes += Double.toString(lineInformation.getLat()) + ",";
			longitudes += Double.toString(lineInformation.getLon()) + ",";
		}
		
		latitudes = latitudes.substring(0, latitudes.length()-1);//removes the final comma
		longitudes = longitudes.substring(0, longitudes.length()-1);//removes the final comma
	}
	
	public String getLats(){
		return latitudes;
	}
	
	public String getLons(){
		return longitudes;
	}

}
