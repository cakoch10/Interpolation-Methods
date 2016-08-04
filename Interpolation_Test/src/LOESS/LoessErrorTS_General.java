package LOESS;

import java.io.File;
import java.util.ArrayList;

import main.tsError;
import main.writeToFile;

public class LoessErrorTS_General {
	public static void main(String[] args){
		//IGNORE THE NAMES OF FILES, THIS WAS COPIED AND MODIFIED
		File actualDir = new File("C:/test/trajectory2/OriginalTS");
		File[] actualList = actualDir.listFiles();
		
		File linearTS = new File("C:/test/trajectory2/Loess_TS");
		File[] linearTSList = linearTS.listFiles();
		
		double[][] linError = new double[linearTSList.length][3];//stores the error, error squared, length for each file
		
		String[] linFinalNames = new String[linearTSList.length];
		
		//create an array to store the numerical identifier for each file (a 14 digit number in each filename)
		ArrayList<String> actualNames = new ArrayList<String>();
		
		for(File f:actualList){
			String name = f.getName();
			name = name.substring(0, 14);//remove the file ending
			actualNames.add(name);
		}
		
		for(int i=0; i<linearTSList.length; i++){
			String linPath = linearTSList[i].getPath();
			String name = linearTSList[i].getName();
			String numIdentifier = linearTSList[i].getName().substring(0, 14);//get the first 14 characters
			
			//provides the corresponding index in the nearest neighbor file list
			int indexLin = actualNames.indexOf(numIdentifier);
			String actualPath = actualList[indexLin].getPath();
			
			name = name.substring(18, name.length()-11);//20070921120306_TS_100_Cubic_222281.plt
			String[] name2 = name.split("_");
			int gapLength = Integer.parseInt(name2[0]);
			
			tsError linCompare = new tsError(actualPath, linPath, false, false, gapLength);
			linError[i][0] = linCompare.getError();
			linError[i][1] = linCompare.getErrorSq();
			linError[i][2] = (double)linCompare.getLen();
			
			linFinalNames[i] = linearTSList[i].getName().substring(0,
					linearTSList[i].getName().length()-4);//remove .plt ending
			System.out.println(i + " Error: " + linError[i][0]);
		}
		
		//write to files		
		String[] linResults = new String[linearTSList.length];
		for(int i=0; i<linearTSList.length; i++){
			linResults[i] =
			linFinalNames[i]
			+ "," + Double.toString(linError[i][0])
			+ "," + Double.toString(linError[i][1])
			+ "," + Double.toString(linError[i][2]);
		}

		String newNamelin = "C:/test/trajectory2/Error_Analysis/LoessTSError.plt";
		writeToFile copyLinresults = new writeToFile(linResults, newNamelin);
		copyLinresults.write();
		System.out.print("Done");
	}
}
