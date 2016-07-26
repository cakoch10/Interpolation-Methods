package linear_NearestNeighbor;
import java.io.File;
import java.util.*;

import main.tsError;
import main.writeToFile;

public class tsErrorMain {
	public static void main(String[] args){
		
		File actualDir = new File("C:/test/trajectory/Original_TS");
		File[] actualList = actualDir.listFiles();
		
		File linearTS = new File("C:/test/trajectory/Lin_TS");
		File[] linearTSList = linearTS.listFiles();
		
		File NNTS = new File("C:/test/trajectory/NN_TS");
		File[] NNTSList = NNTS.listFiles();
		
		double[][] linError = new double[linearTSList.length][3];//stores the error, error squared, length for each file
		double[][] nnError = new double[NNTSList.length][3];
		
		String[] nnFinalNames = new String[NNTSList.length];//stores the names
		String[] linFinalNames = new String[linearTSList.length];
		
		//create an array to store the numerical identifier for each file (a 14 digit number in each filename)
		ArrayList<String> nnNames = new ArrayList<String>();
		ArrayList<String> linNames = new ArrayList<String>();
		
		for(File f:NNTSList){
			String name = f.getName();
			name = name.substring(0, name.length()-15);//remove the _gaps_TS_NN.plt file ending
			nnNames.add(name);
		}
		for(File f:linearTSList){
			String name = f.getName();
			name = name.substring(0, name.length()-19);//remove the _gaps_TS_Linear.plt file ending
			linNames.add(name);
		}
		
		for(int i=0; i<actualList.length; i++){
			String actualPath = actualList[i].getPath();
			String numIdentifier = actualList[i].getName();
			numIdentifier = numIdentifier.substring(0, numIdentifier.length()-7);//remove the _TS.plt file extension
			
			int indexNN = nnNames.indexOf(numIdentifier);//provides the corresponding index in the nearest neighbor file list
			String NNinterpolatedPath = NNTSList[indexNN].getPath();
			
			int indexLin = linNames.indexOf(numIdentifier);
			String linPath = linearTSList[indexLin].getPath();
			
			tsError nnCompare = new tsError(actualPath, NNinterpolatedPath, true, false);
			nnError[i][0] = nnCompare.getError();
						
			nnError[i][1] = nnCompare.getErrorSq();
			nnError[i][2] = (double)nnCompare.getLen();
			
			
			nnFinalNames[i] = nnNames.get(indexNN);//stores the name
			
			
			tsError linCompare = new tsError(actualPath, linPath, true, false);
			linError[i][0] = linCompare.getError();
			linError[i][1] = linCompare.getErrorSq();
			linError[i][2] = (double)linCompare.getLen();
			
			System.out.println(Double.toString(linError[i][0]));
			
			linFinalNames[i] = linNames.get(indexLin);
		}
		
		//write to files
		//nearest neighbor results
		String[] nnResults = new String[NNTSList.length];
		for(int i=0; i<NNTSList.length; i++){
			nnResults[i] =
			nnFinalNames[i]
			+ "," + Double.toString(nnError[i][0])
			+ "," + Double.toString(nnError[i][1])
			+ "," + Double.toString(nnError[i][2]);
		}
	
		String newNameNN = "C:/test/trajectory/Error_Analysis/NNerror.plt";
		writeToFile copyNNresults = new writeToFile(nnResults, newNameNN);
		copyNNresults.write();
		
		String[] linResults = new String[linearTSList.length];
		for(int i=0; i<linearTSList.length; i++){
			linResults[i] =
			linFinalNames[i]
			+ "," + Double.toString(linError[i][0])
			+ "," + Double.toString(linError[i][1])
			+ "," + Double.toString(linError[i][2]);
		}
	
		String newNamelin = "C:/test/trajectory/Error_Analysis/Linerror.plt";
		writeToFile copyLinresults = new writeToFile(linResults, newNamelin);
		copyLinresults.write();
		System.out.print("Done");
	}
}
