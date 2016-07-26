package cubic;
import java.io.File;
import java.util.ArrayList;

import main.tsError;
import main.writeToFile;

public class CubicErrorTS {
public static void main(String[] args){
		//IGNORE THE NAMES OF FILES, THIS WAS COPIED AND MODIFIED
		File actualDir = new File("C:/test/trajectory/Original_TS");
		File[] actualList = actualDir.listFiles();
		
		File linearTS = new File("C:/test/trajectory/Cubic_TS");
		File[] linearTSList = linearTS.listFiles();
		
		double[][] linError = new double[linearTSList.length][3];//stores the error, error squared, length for each file
		
		String[] linFinalNames = new String[linearTSList.length];
		
		//create an array to store the numerical identifier for each file (a 14 digit number in each filename)
		ArrayList<String> linNames = new ArrayList<String>();
		
		for(File f:linearTSList){
			String name = f.getName();
			name = name.substring(0, 14);//remove the _gaps_TS_Linear.plt file ending
			linNames.add(name);
		}
		
		for(int i=0; i<actualList.length; i++){
			String actualPath = actualList[i].getPath();
			String numIdentifier = actualList[i].getName();
			numIdentifier = numIdentifier.substring(0, numIdentifier.length()-7);//remove the _TS.plt file extension
			
			//provides the corresponding index in the nearest neighbor file list
			int indexLin = linNames.indexOf(numIdentifier);
			String linPath = linearTSList[indexLin].getPath();
			
			tsError linCompare = new tsError(actualPath, linPath, true, false);
			linError[i][0] = linCompare.getError();
			linError[i][1] = linCompare.getErrorSq();
			linError[i][2] = (double)linCompare.getLen();
			
			System.out.println(Double.toString(linError[i][0]));
			
			linFinalNames[i] = linNames.get(indexLin);
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
	
		String newNamelin = "C:/test/trajectory/Error_Analysis/CubicTS.plt";
		writeToFile copyLinresults = new writeToFile(linResults, newNamelin);
		copyLinresults.write();
		System.out.print("Done");
	}
}
