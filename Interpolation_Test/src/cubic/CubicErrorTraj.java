package cubic;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import main.error;
import main.writeToFile;
	
public class CubicErrorTraj {
	public static void main(String[] args){
		File actualDir = new File("C:/test/trajectory/");
		File[] actualList = actualDir.listFiles();
		
		File polyTrajFiles = new File("C:/test/trajectory/Cubic_Traj");
		File[] polyTraj = polyTrajFiles.listFiles();
		
		//IGNORE THE POLYNOMIAL VARIABLE NAMES, THIS FILE WAS COPIED FROM POLYTRAJERROR CLASS
		
		double[][] polynomError = new double[polyTraj.length][3];//stores the error, error squared, distance for each file
		
		String[] pFinalNames = new String[polyTraj.length];//stores the names
		
		//create an array to store the numerical identifier for each file (a 14 digit number in each filename)
		ArrayList<String> polyNames = new ArrayList<String>();
		
		for(File f:polyTraj){
			String name = f.getName();
			name = name.substring(0, 14);//gets the first fourteen characters
			polyNames.add(name);
		}

		for(int i=0; i<actualList.length; i++){
			if(actualList[i].isFile()){				
				String numIdentifier = actualList[i].getName();
				numIdentifier = numIdentifier.substring(0, numIdentifier.length()-4);//remove the .plt file extension
				
				int indexP = polyNames.indexOf(numIdentifier);//provides the corresponding index in the nearest neighbor file list
				if(indexP<0){
					System.out.println("Error indexNN is: " + indexP);
					System.out.println("i: " + i);
					System.out.println("Main: " + actualList[i].getName());
					System.out.println("identifier: " + numIdentifier);
					System.exit(0);
				}
				
				//get the lines of each
				ArrayList<String> actualLines = new ArrayList<>();
				
				try{
					BufferedReader bR = new BufferedReader(new FileReader(actualList[i]));
					
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
				
				error polyCompare = new error(actualArray, pArray, pArray.length);
				polyCompare.calculate();
				polynomError[i][0] = polyCompare.returnErrCum();		
				polynomError[i][1] = polyCompare.returnErrCumSq();
				polynomError[i][2] = polyCompare.realDist();
				
				pFinalNames[i] = polyNames.get(indexP) +
						"_" +
						polyTraj[indexP].getName().substring(31, polyTraj[indexP].getName().length());//stores the name
				//also stores the number of points interpolated (last 3 to 5 characters)
				System.out.println("loop i: " + i);
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
	
		String newName = "C:/test/trajectory/Error_Analysis/CubicErrorTraj.plt";
		writeToFile copyNNresults = new writeToFile(pResults, newName);
		copyNNresults.write();
		System.out.print("Done");
	}
}


