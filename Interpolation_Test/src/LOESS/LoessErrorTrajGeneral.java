package LOESS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import main.error;
import main.writeToFile;

public class LoessErrorTrajGeneral {
	public static void main(String[] args){
		File actualDir = new File("C:/test/trajectory2/OriginalTrajectories");
		File[] actualList = actualDir.listFiles();
		
		File polyTrajFiles = new File("C:/test/trajectory2/Loess_Traj");
		File[] polyTraj = polyTrajFiles.listFiles();
		
		//IGNORE THE POLYNOMIAL VARIABLE NAMES, THIS FILE WAS COPIED FROM POLYTRAJERROR CLASS
		
		double[][] polynomError = new double[polyTraj.length][3];//stores the error, error squared, distance for each file
		
		String[] pFinalNames = new String[polyTraj.length];//stores the names
		
		//create an array to store the numerical identifier for each file (a 14 digit number in each filename)
		ArrayList<String> actualNames = new ArrayList<String>();
		
		for(File f:actualList){
			String name = f.getName();
			name = name.substring(0, 14);//gets the first fourteen characters
			actualNames.add(name);
		}

		for(int i=0; i<polyTraj.length; i++){
			if(polyTraj[i].isFile()){				
				String numIdentifier = polyTraj[i].getName();
				numIdentifier = numIdentifier.substring(0, 14);//remove the .plt file extension
				
				//provides the corresponding index in the nearest neighbor file list
				int indexP = i;
				int indexA = actualNames.indexOf(numIdentifier);
				
				if(indexA<0){
					System.out.println("Error indexNN is: " + indexP);
					System.out.println("i: " + i);
					System.out.println("Main: " + actualList[i].getName());
					System.out.println("identifier: " + numIdentifier);
					System.exit(0);
				}
				
				//get the lines of each
				ArrayList<String> actualLines = new ArrayList<>();
				
				try{
					BufferedReader bR = new BufferedReader(new FileReader(actualList[indexA]));
					
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
				
				ArrayList<String> polynomLines = new ArrayList<>();
				try{
					BufferedReader bR = new BufferedReader(new FileReader(polyTraj[indexP]));
					String line;
					int count = 0;
					while((line = bR.readLine()) != null){
						if(count>5)
							polynomLines.add(line);
						count++;
					}
					bR.close();				
				}catch(IOException ex){
					System.err.println("An IOException was caught");
					ex.printStackTrace();
				}
				String[] pArray = polynomLines.toArray(new String[polynomLines.size()]);
				
				//get the file name and use it to compute the gap length
				String fileName = polyTraj[indexP].getName();
				//gapAndPoints includes the gap length and number of interpolated points separated by underscore
				String gapAndPoints = fileName.substring(15, fileName.length()-15);
				String[] gpArray = gapAndPoints.split("_");
				int gapLength = Integer.parseInt(gpArray[0]);
				
				error polyCompare = new error(actualArray, pArray, pArray.length);
				polyCompare.calculate2(gapLength);
				polynomError[i][0] = polyCompare.returnErrCum();		
				polynomError[i][1] = polyCompare.returnErrCumSq();
				polynomError[i][2] = polyCompare.realDist();
				
				pFinalNames[i] = numIdentifier +
						"_" +
						fileName.substring(15, fileName.length()-15);//stores the name
				//also stores the number of points interpolated (last 3 to 5 characters)
				System.out.println("loop i: " + i);
				//20070921120306_50_13450_Cubic_Traj.plt
			}
		}
		
		//write to files
		//polynomial results
		String[] pResults = new String[polyTraj.length];
		for(int i=0; i<polyTraj.length; i++){
			pResults[i] =
			pFinalNames[i]
			+ "," + Double.toString(polynomError[i][0])
			+ "," + Double.toString(polynomError[i][1])
			+ "," + Double.toString(polynomError[i][2]);
		}
	
		String newName = "C:/test/trajectory2/Error_Analysis/LoessErrorTraj.plt";
		writeToFile copyNNresults = new writeToFile(pResults, newName);
		copyNNresults.write();
		System.out.print("Done");
	}
}
