package other;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import main.writeToFile;

public class CreateGaps {
	public CreateGaps(){
		
		File f = new File("c:/test/trajectory");
        
        // returns pathnames for files and directory
        File[] paths = f.listFiles();
                
        for(File fe:paths){
        	double size = fe.length();
        	double kb = size/1024;
        	if(kb>=13.0 && kb<=16.0){
        		File in = new File("c:/test/trajectory/20081210075232.plt");
        		ArrayList<String> lines = new ArrayList<String>(getList(in));
        		ArrayList<String> linesRemoved = new ArrayList<String>(removeLines(lines, 50));
        		String[] arrayLines = list2array(linesRemoved);
        		writeToFile makeFile = new writeToFile(arrayLines, "c:/test/trajectory/20081210075232" + "_gaps.plt");
        		makeFile.write();
        	}
        	else if(kb>=121.0 && kb<=125.0){
        		File in = new File("c:/test/trajectory/20081113035731.plt");
        		ArrayList<String> lines = new ArrayList<String>(getList(in));
        		ArrayList<String> linesRemoved = new ArrayList<String>(removeLines(lines, 1050));
        		String[] arrayLines = list2array(linesRemoved);
        		writeToFile makeFile = new writeToFile(arrayLines, "c:/test/trajectory/20081113035731" + "_gaps.plt");
        		makeFile.write();
        	}
        	else if(kb>=208.0 && kb<=214.0){
        		File in = new File("c:/test/trajectory/20081207161131.plt");
        		ArrayList<String> lines = new ArrayList<String>(getList(in));
        		ArrayList<String> linesRemoved = new ArrayList<String>(removeLines(lines, 150));
        		String[] arrayLines = list2array(linesRemoved);
        		writeToFile makeFile = new writeToFile(arrayLines, "c:/test/trajectory/20081207161131" + "_gaps.plt");
        		makeFile.write();
        	}
        	else if(kb>=329.0 && kb<=335.0){
        		File in = new File("c:/test/trajectory/20081105083259.plt");
        		ArrayList<String> lines = new ArrayList<String>(getList(in));
        		ArrayList<String> linesRemoved = new ArrayList<String>(removeLines(lines, 1850));
        		String[] arrayLines = list2array(linesRemoved);
        		writeToFile makeFile = new writeToFile(arrayLines, "c:/test/trajectory/20081105083259" + "_gaps.plt");
        		makeFile.write();
        	}
        	else if(kb>=455.0 && kb<=465.0){
        		File in = new File("c:/test/trajectory/20081119011305.plt");
        		ArrayList<String> lines = new ArrayList<String>(getList(in));
        		ArrayList<String> linesRemoved = new ArrayList<String>(removeLines(lines, 250));
        		String[] arrayLines = list2array(linesRemoved);
        		writeToFile makeFile = new writeToFile(arrayLines, "c:/test/trajectory/20081119011305" + "_gaps.plt");
        		makeFile.write();
        	}
        	else if(kb>=535.0 && kb<=545.0){
        		File in = new File("c:/test/trajectory/20090203100159.plt");
        		ArrayList<String> lines = new ArrayList<String>(getList(in));
        		ArrayList<String> linesRemoved = new ArrayList<String>(removeLines(lines, 1750));
        		String[] arrayLines = list2array(linesRemoved);
        		writeToFile makeFile = new writeToFile(arrayLines, "c:/test/trajectory/20090203100159" + "_gaps.plt");
        		makeFile.write();
        	}
        	else if(kb>=630.0 && kb<=650.0){
        		File in = new File("c:/test/trajectory/20090226005433.plt");
        		ArrayList<String> lines = new ArrayList<String>(getList(in));
        		ArrayList<String> linesRemoved = new ArrayList<String>(removeLines(lines, 350));
        		String[] arrayLines = list2array(linesRemoved);
        		writeToFile makeFile = new writeToFile(arrayLines, "c:/test/trajectory/20090226005433" + "_gaps.plt");
        		makeFile.write();
        	}
        	else if(kb>=760.0 && kb<=780.0){
        		File in = new File("c:/test/trajectory/20090131052455.plt");
        		ArrayList<String> lines = new ArrayList<String>(getList(in));
        		ArrayList<String> linesRemoved = new ArrayList<String>(removeLines(lines, 1650));
        		String[] arrayLines = list2array(linesRemoved);
        		writeToFile makeFile = new writeToFile(arrayLines, "c:/test/trajectory/20090131052455" + "_gaps.plt");
        		makeFile.write();
        	}
        	else if(kb>=840.0 && kb<=850.0){
        		File in = new File("c:/test/trajectory/20080927233805.plt");
        		ArrayList<String> lines = new ArrayList<String>(getList(in));
        		ArrayList<String> linesRemoved = new ArrayList<String>(removeLines(lines, 450));
        		String[] arrayLines = list2array(linesRemoved);
        		writeToFile makeFile = new writeToFile(arrayLines, "c:/test/trajectory/20080927233805" + "_gaps.plt");
        		makeFile.write();
        	}
        	else if(kb>=925.0 && kb<=930.0){
        		File in = new File("c:/test/trajectory/20081109055559.plt");
        		ArrayList<String> lines = new ArrayList<String>(getList(in));
        		ArrayList<String> linesRemoved = new ArrayList<String>(removeLines(lines, 1550));
        		String[] arrayLines = list2array(linesRemoved);
        		writeToFile makeFile = new writeToFile(arrayLines, "c:/test/trajectory/20081109055559" + "_gaps.plt");
        		makeFile.write();
        	}
        	else if(kb>=1090.0 && kb<=1100.0){
        		File in = new File("c:/test/trajectory/20090113112028.plt");
        		ArrayList<String> lines = new ArrayList<String>(getList(in));
        		ArrayList<String> linesRemoved = new ArrayList<String>(removeLines(lines, 550));
        		String[] arrayLines = list2array(linesRemoved);
        		writeToFile makeFile = new writeToFile(arrayLines, "c:/test/trajectory/20090113112028" + "_gaps.plt");
        		makeFile.write();
        	}
        	else if(kb>=1149.0 && kb<=1155.0){
        		File in = new File("c:/test/trajectory/20090323212145.plt");
        		ArrayList<String> lines = new ArrayList<String>(getList(in));
        		ArrayList<String> linesRemoved = new ArrayList<String>(removeLines(lines, 1450));
        		String[] arrayLines = list2array(linesRemoved);
        		writeToFile makeFile = new writeToFile(arrayLines, "c:/test/trajectory/20090323212145" + "_gaps.plt");
        		makeFile.write();
        	}
        	else if(kb>=1255.0 && kb<=1260.0){
        		File in = new File("c:/test/trajectory/20090117223222.plt");
        		ArrayList<String> lines = new ArrayList<String>(getList(in));
        		ArrayList<String> linesRemoved = new ArrayList<String>(removeLines(lines, 650));
        		String[] arrayLines = list2array(linesRemoved);
        		writeToFile makeFile = new writeToFile(arrayLines, "c:/test/trajectory/20090117223222" + "_gaps.plt");
        		makeFile.write();
        	}
        	else if(kb>=1515.0 && kb<=1525.0){
        		File in = new File("c:/test/trajectory/20110828143340.plt");
        		ArrayList<String> lines = new ArrayList<String>(getList(in));
        		ArrayList<String> linesRemoved = new ArrayList<String>(removeLines(lines, 1350));
        		String[] arrayLines = list2array(linesRemoved);
        		writeToFile makeFile = new writeToFile(arrayLines, "c:/test/trajectory/20110828143340" + "_gaps.plt");
        		makeFile.write();
        	}
        	else if(kb>=1615.0 && kb<=1625.0){
        		File in = new File("c:/test/trajectory/20110502000104.plt");
        		ArrayList<String> lines = new ArrayList<String>(getList(in));
        		ArrayList<String> linesRemoved = new ArrayList<String>(removeLines(lines, 750));
        		String[] arrayLines = list2array(linesRemoved);
        		writeToFile makeFile = new writeToFile(arrayLines, "c:/test/trajectory/20110502000104" + "_gaps.plt");
        		makeFile.write();
        	}
        	else if(kb>=2214.0 && kb<=2220.0){
        		File in = new File("c:/test/trajectory/20081114124439.plt");
        		ArrayList<String> lines = new ArrayList<String>(getList(in));
        		ArrayList<String> linesRemoved = new ArrayList<String>(removeLines(lines, 1250));
        		String[] arrayLines = list2array(linesRemoved);
        		writeToFile makeFile = new writeToFile(arrayLines, "c:/test/trajectory/20081114124439" + "_gaps.plt");
        		makeFile.write();
        	}
        	else if(kb>=2425.0 && kb<=2435.0){
        		File in = new File("c:/test/trajectory/20110514111537.plt");
        		ArrayList<String> lines = new ArrayList<String>(getList(in));
        		ArrayList<String> linesRemoved = new ArrayList<String>(removeLines(lines, 850));
        		String[] arrayLines = list2array(linesRemoved);
        		writeToFile makeFile = new writeToFile(arrayLines, "c:/test/trajectory/20110514111537" + "_gaps.plt");
        		makeFile.write();
        	}
        	else if(kb>=2779.0 && kb<=2789.0){
        		File in = new File("c:/test/trajectory/20090930071934.plt");
        		ArrayList<String> lines = new ArrayList<String>(getList(in));
        		ArrayList<String> linesRemoved = new ArrayList<String>(removeLines(lines, 1150));
        		String[] arrayLines = list2array(linesRemoved);
        		writeToFile makeFile = new writeToFile(arrayLines, "c:/test/trajectory/20090930071934" + "_gaps.plt");
        		makeFile.write();
        	}
        	else if(kb>=3149.0 && kb<=3159.0){
        		File in = new File("c:/test/trajectory/20111119010003.plt");
        		ArrayList<String> lines = new ArrayList<String>(getList(in));
        		ArrayList<String> linesRemoved = new ArrayList<String>(removeLines(lines, 950));
        		String[] arrayLines = list2array(linesRemoved);
        		writeToFile makeFile = new writeToFile(arrayLines, "c:/test/trajectory/20111119010003" + "_gaps.plt");
        		makeFile.write();
        	}
        	else if(kb>=3475.0 && kb<=3485.0){
        		File in = new File("c:/test/trajectory/20090528211734.plt");
        		ArrayList<String> lines = new ArrayList<String>(getList(in));
        		ArrayList<String> linesRemoved = new ArrayList<String>(removeLines(lines, 1950));
        		String[] arrayLines = list2array(linesRemoved);
        		writeToFile makeFile = new writeToFile(arrayLines, "c:/test/trajectory/20090528211734" + "_gaps.plt");
        		makeFile.write();
        	}   	
        }
	}
	
	public ArrayList<String> getList(File n){//extract lines from file
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
	
	public ArrayList<String> removeLines(ArrayList<String> lines, int n){//remove n lines starting after the first 10 points
		ArrayList<String> firstHalf = new ArrayList<String>(lines.subList(0, 16));//takes into account the first 6 lines
		ArrayList<String> secondHalf = new ArrayList<String>(lines.subList(16+n, lines.size()));
		ArrayList<String> newList = new ArrayList<String>(firstHalf);
		newList.addAll(secondHalf);
		return newList;
	}
	
	public String[] list2array(ArrayList<String> s){
		String[] array = new String[s.size()];
		for(int i=0; i<s.size(); i++)
			array[i] = s.get(i);
		return array;
	}
}