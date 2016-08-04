package linear_NearestNeighbor;

import java.io.File;
import java.util.ArrayList;

import main.tsError;
import main.writeToFile;

public class tsErrorGeneral {
	public static void main(String[] args){
		File actualDir = new File("C:/test/trajectory2/OriginalTS");//20070921120306_TS.plt
		File[] actualList = actualDir.listFiles();
		
		File linearTS = new File("C:/test/trajectory2/Lin_TS");//20070921120306_TS_50_Linear.plt
		File[] linearTSList = linearTS.listFiles();
		
		File NNTS = new File("C:/test/trajectory2/NN_TS");//20070921120306_TS_50_NN_TS.plt
		File[] NNTSList = NNTS.listFiles();
		
		double[][] linError = new double[linearTSList.length][3];//stores the error, error squared, length for each file
		double[][] nnError = new double[NNTSList.length][3];
		
		String[] nnFinalNames = new String[NNTSList.length];//stores the names
		String[] linFinalNames = new String[linearTSList.length];
		
		//create an array to store the numerical identifier for each file (a 14 digit number in each filename)
		ArrayList<String> nnNames = new ArrayList<String>();
		ArrayList<String> actNames = new ArrayList<String>();
		
		for(File f:NNTSList){
			String name = f.getName();
			name = name.substring(0, name.length()-10);//remove the file ending
			nnNames.add(name);
		}
		for(File f:actualList){
			String name = f.getName();
			name = name.substring(0, 14);//remove the file ending
			actNames.add(name);
		}
		
		for(int i=0; i<linearTSList.length; i++){
			String linPath = linearTSList[i].getPath();
			String numIdentifier = linearTSList[i].getName();
			String substring = numIdentifier.substring(18, numIdentifier.length()-11);//20070921120306_TS_50_Linear.plt
			int gapLength = Integer.parseInt(substring);
			
			String numIdentifier2 = numIdentifier.substring(0, numIdentifier.length()-11);
			numIdentifier = numIdentifier.substring(0, 14);
			
			//provides the corresponding index in the nearest neighbor file list
			int indexNN = nnNames.indexOf(numIdentifier2);
			if(indexNN<0){
				System.out.println("Error indexNN is: " + indexNN);
				System.out.println("i: " + i);
				System.out.println("Main: " + linearTSList[i].getName());
				System.out.println("identifier: " + numIdentifier);
			}
			String NNinterpolatedPath = NNTSList[indexNN].getPath();
			
			int indexAct = actNames.indexOf(numIdentifier);
			if(indexAct<0){
				System.out.println("Error indexLin is: " + indexAct);
				System.out.println("i: " + i);
				System.out.println("Main: " + linearTSList[i].getName());
				System.out.println("identifier: " + numIdentifier);
			}
			
			String actPath = actualList[indexAct].getPath();
			
			tsError nnCompare = new tsError(actPath, NNinterpolatedPath, false, false, gapLength);
			nnError[i][0] = nnCompare.getError();
			nnError[i][1] = nnCompare.getErrorSq();
			nnError[i][2] = (double)nnCompare.getLen();
			
			nnFinalNames[i] = nnNames.get(indexNN);//stores the name
			
			tsError linCompare = new tsError(actPath, linPath, false, false, gapLength);
			linError[i][0] = linCompare.getError();
			linError[i][1] = linCompare.getErrorSq();
			linError[i][2] = (double)linCompare.getLen();
			
			System.out.println(Double.toString(linError[i][0]) + " i: " + i);
			
			linFinalNames[i] = numIdentifier2;
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
	
		String newNameNN = "C:/test/trajectory2/Error_Analysis/NNerror.plt";
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
	
		String newNamelin = "C:/test/trajectory2/Error_Analysis/Linerror.plt";
		writeToFile copyLinresults = new writeToFile(linResults, newNamelin);
		copyLinresults.write();
		System.out.print("Done");
	}
}
