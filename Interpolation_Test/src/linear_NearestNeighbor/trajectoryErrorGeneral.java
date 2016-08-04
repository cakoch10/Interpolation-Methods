package linear_NearestNeighbor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import main.error;
import main.writeToFile;

public class trajectoryErrorGeneral {
	public static void main(String[] args){
		File actualDir = new File("C:/test/trajectory2/OriginalTrajectories");
		File[] actualList = actualDir.listFiles();
		
		File linearTS = new File("C:/test/trajectory2/LinInt_Traj");
		File[] linearTSList = linearTS.listFiles();//ignore the TS in the name. This code was copied from tsErrorMain
		
		File NNTS = new File("C:/test/trajectory2/NN_Traj");
		File[] NNTSList = NNTS.listFiles();
		
		double[][] linError = new double[linearTSList.length][3];//stores the error, error squared, distance for each file
		double[][] nnError = new double[NNTSList.length][3];
		
		String[] nnFinalNames = new String[NNTSList.length];//stores the names
		String[] linFinalNames = new String[linearTSList.length];
		
		//create an array to store the numerical identifier for each file (a 14 digit number in each filename)
		ArrayList<String> nnNames = new ArrayList<String>();
		ArrayList<String> actNames = new ArrayList<String>();
		
		for(File f:NNTSList){
			String name = f.getName();
			name = name.substring(0, name.length()-12);//remove the 20070921120306_50_NN_Traj.plt file ending
			nnNames.add(name);
		}
		for(File f:actualList){
			String name = f.getName();
			name = name.substring(0, 14);//remove the _gaps_LinInt_Traj.plt file ending
			actNames.add(name);
		}
		
		for(int i=0; i<linearTSList.length; i++){
			if(linearTSList[i].isFile()){				
				String numIdentifier = linearTSList[i].getName();
				//20070921120306_100_LinInt_Traj.plt
				String substring = numIdentifier.substring(15, numIdentifier.length()-16);
				int gapLength = Integer.parseInt(substring);
				
				String numIdentifier2 = numIdentifier.substring(0, numIdentifier.length()-16);
				
				numIdentifier = numIdentifier.substring(0, 14);//remove the .plt file extension
				
				int indexNN = nnNames.indexOf(numIdentifier2);//provides the corresponding index in the nearest neighbor file list
				if(indexNN<0){
					System.out.println("Error indexNN is: " + indexNN);
					System.out.println("i: " + i);
					System.out.println("Main: " + actualList[i].getName());
					System.out.println("identifier: " + numIdentifier);
				}
				
				int indexAct = actNames.indexOf(numIdentifier);
				if(indexAct<0){
					System.out.println("Error indexLin is: " + indexAct);
					System.out.println("i: " + i);
					System.out.println("Main: " + actualList[i].getName());
					System.out.println("identifier: " + numIdentifier);
				}
				
				//get the lines of each
				ArrayList<String> actualLines = new ArrayList<>();
				
				try{
					BufferedReader bR = new BufferedReader(new FileReader(actualList[indexAct]));
					
					String line;
					int count = 0;
					while((line = bR.readLine()) != null){
						if(count>5)
							actualLines.add(line);
						count++;
					}
					bR.close();
				}catch(IOException ex){
		            System.err.println("An IOException was caught!");
		            ex.printStackTrace();
		        }
				String[] actualArray = actualLines.toArray(new String[actualLines.size()]);
				
				ArrayList<String> nnInterpolated = new ArrayList<>();
				try{
					BufferedReader bR = new BufferedReader(new FileReader(NNTSList[indexNN]));
					
					String line;
					int count = 0;
					while((line = bR.readLine()) != null){
						if(count>5)
							nnInterpolated.add(line);
						count++;
					}
					bR.close();				
				}catch(IOException ex){
					System.err.println("An IOException was caught");
					ex.printStackTrace();
				}
				String[] nnArray = nnInterpolated.toArray(new String[nnInterpolated.size()]);
				
				error nnCompare = new error(actualArray, nnArray, nnArray.length);
				nnCompare.calculate2(gapLength);
				nnError[i][0] = nnCompare.returnErrCum();		
				nnError[i][1] = nnCompare.returnErrCumSq();
				nnError[i][2] = nnCompare.realDist();
				
				nnFinalNames[i] = nnNames.get(indexNN);//stores the name
				
				ArrayList<String> linInterpolated = new ArrayList<>();
				try{
					BufferedReader bR = new BufferedReader(new FileReader(linearTSList[i]));
					
					String line;
					int count = 0;
					while((line = bR.readLine()) != null){
						if(count>5)
							linInterpolated.add(line);
						count++;
					}
					bR.close();
				}catch(IOException ex){
					System.err.println("An IOExcetionp was caught");
					ex.printStackTrace();
				}
				String[] linArray = linInterpolated.toArray(new String[linInterpolated.size()]);
				
				error linCompare = new error(actualArray, linArray, linArray.length);
				linCompare.calculate2(gapLength);
				linError[i][0] = linCompare.returnErrCum();
				linError[i][1] = linCompare.returnErrCumSq();
				linError[i][2] = linCompare.realDist();
							
				linFinalNames[i] = linearTSList[i].getName();
				System.out.println("loop i: " + i);
			}
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
	
		String newNameNN = "C:/test/trajectory2/Error_Analysis/NNerrorTraj.plt";
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
	
		String newNamelin = "C:/test/trajectory2/Error_Analysis/LinerrorTraj.plt";
		writeToFile copyLinresults = new writeToFile(linResults, newNamelin);
		copyLinresults.write();
		System.out.print("Done");
	}
}
