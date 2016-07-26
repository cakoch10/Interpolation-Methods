package other;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class gapRemove {
	
	private File inName = null;
	private static int[][] gaps = new int[10][2]; //storing the gaps, before any changes are made to the file
	private boolean isValid = true;//determines whether the file at hand has enough coordinates to create desired gaps
	
	public gapRemove(File n){
		inName = n;
		for(int i =0; i<10; i++){ //initialize all values to -1
			   gaps[i][0]=-1;
			   gaps[i][1]=-1;
		}
	}
	
	
	public boolean getValidity(){
		return isValid;
	}
	
	
	//parse the file to find any known gaps, beginning after line 6 and continuing through the file
	
	public void compute_gaps(){
		try{
        BufferedReader bR = new BufferedReader(new FileReader(inName));
		

        String line = null;
        int count = 0;
        ArrayList<Integer> times = new ArrayList<>();
        
        while ((line = bR.readLine())!=null){
        	int len = line.length();
        	
        	if(count>5){
        		//get final 8 characters
            	String sub = line.substring(len-8);
            	//convert string to a number of the form hhmmss, where h stands for hour, m is minute, and s is second
            	
            	int n = Character.getNumericValue(sub.charAt(0))*100000
            			+ Character.getNumericValue(sub.charAt(1))*10000//the char at 2 is a colon, so we skip it
            			+ Character.getNumericValue(sub.charAt(3))*1000
            			+ Character.getNumericValue(sub.charAt(4))*100//skip again
            			+ Character.getNumericValue(sub.charAt(6))*10
            			+ Character.getNumericValue(sub.charAt(7));
            	if(count==6){
            		System.out.println("Char is: "+ Character.getNumericValue(sub.charAt(0)));
            		System.out.println("Sub is: "+sub);
            	}
            	times.add(n);
        	}
        	
        	count++;
        }
        bR.close();
        

        //create an array of the differences of consecutive timestamps
        ArrayList<Integer> diffs = new ArrayList<>();
        
        for(int i=0; i<times.size()-1; i++){
        	int d = times.get(i+1) - times.get(i);
        	//correct case where there is a change in hour
        	if(d == 45){
        		int y = times.get(i+1) % 100;
        		int x = times.get(i) % 100;
        		if(x > 54 && y < 5){
        			y=y+60;
        			d = y-x;
        		}
        				
        	}
        	diffs.add(d);
        }
        
	 	//create an algorithm for finding appropriate, potential gaps

        //First though, it is necessary to take note of pre-existing gaps so that they will be ignored by the interpolating algorithm
        //a gap is any difference greater than 5 seconds

        //create a list of all locations of pre-existing gaps
        ArrayList<Integer> preGap = new ArrayList<>();//notes the first timestamp associated with a pre-existing gap

        
        for(int i=0; i<diffs.size(); i++){
     	   if(diffs.get(i)>5){
     		   preGap.add(i);
     	   }
        }

      //These values of the location of pre-existing gaps are written to the first line of the new file when it is made

      //Finding appropriate gaps:
      //we begin by searching for the larger potential gaps since they will be harder to find
      //a potential gap is essentially a long sequence of gaps under five seconds
      //we loop through each desired gap length (there are 10)
      
      //in general, given n consecutive diffs less than 5, we can create a valid gap length of size n-1

      for(int i = 10; i>0; i--){
      	   for(int k=0; k<diffs.size()-i-1; k++){//loop through the entire ArrayList of diffs, but avoid out of bounds error
      		  int sum = 0;
      		  for(int j=k; j<k+i; j++){//see if the elements at k through k+i form a consecutive list
      			  sum+=diffs.get(j);
      		  }
      		  if(i == 10 && k==0)
      			  System.out.println("SUM: "+sum);

      		  if(sum<=5*(i+1)){//this means we have found a potential sequence to remove
      			  //first we need to check whether the sequence overlaps one that is already taken
      			  boolean free = check(k+6+1, i-1); //check whether a sequence of length i-1 starting at k+6+1 has been taken
      			  if(free){
      				  gaps[i-1][0]=i-1;//denotes length of gap
      				  gaps[i-1][1]=k+6+1;
      				  //denotes starting point at line k+6 (since the first 6 lines are meaningless),
      				  //an additional 1 is necessary because we preserve the first and last points in the gap sequence
      				  System.out.println("FREE");
      				  //NOTE: the first line that needs to be removed for any gap length m is given by gaps[m-1][1],
      				  //the last line that needs to be removed is gaps[m-1][1]+gaps[m-1][0]
      				  break;
      			  }
      		  }
      	   }
      }

      for(int i=0; i<10; i++){
      	   if(gaps[i][0]==-1 || gaps[i][1]==-1){
      		 System.out.println("ERROR, NOT ALL GAPS FOUND");
      		 isValid = false;
      		 //delete the .plt file
//      		 boolean s = inName.delete();
      	   }
      }
	}
	catch(IOException ex) {
            System.err.println("An IOException was caught!");
            ex.printStackTrace();
        }

	}
	
	
	public void write(){
		
		//create an ordered list of file lines to be removed
		ArrayList<Integer> flines = new ArrayList<>();
		for(int i=0; i<10; i++){
			for(int k=0; k<i+1; k++){
				int val = gaps[i][1]+k;
				flines.add(val);
			}
		}//-------------------------------------------------------------------------------------------
		
		Collections.sort(flines);//sort array for when it's inserted into the header line of the new file
		
		try{
			BufferedReader bR = new BufferedReader(new FileReader(inName));

			String line = null;
			int count = 0;
			
			String n1 = inName.getPath();
			
			File temporary = new File(n1.substring(0, n1.length()-4)+"_gaps.plt");
			
	        FileWriter fstream = new FileWriter(temporary);
			BufferedWriter fw = new BufferedWriter(fstream);
			
			
			while ((line = bR.readLine())!=null){
				if(!flines.contains(count)){
					if(count!=0){
						fw.write(line);
						fw.newLine();
					}
					else{//write the gap lines on the first line
						String nl = "";
						for(int m=0; m<10; m++){
							nl += gaps[m][0];
							nl += " ";
							nl += gaps[m][1];
							nl += " ";
						}
						fw.write(nl);
						fw.newLine();
					}
				}
				count++;
			}
			
			bR.close();
			fw.close();
			fstream.close();
		}
		catch(IOException ex) {
            System.err.println("An IOException was caught!");
            ex.printStackTrace();
        }
	}
	
	
	//SOME BUG EXISTS IN THIS FUNCTION I THINK

	public boolean check(int start, int leng){//this means that the line at start through the line at start+leng is potentially going to be removed

		int maxVal = start+leng+2;//we want to preserve the points that directly border the gap (for constructing the linear function later on),
		//so we add one/subtract one as needed. We also add/subtract another because we need an extra point for the speed
		int minVal = start-2;
		
		if(minVal <= 7)
			return false;

		for(int i=0; i<10; i++){
			if(gaps[i][0]>=0){
				int localMax = gaps[i][1]+gaps[i][0]+2;
				int localMin = gaps[i][1]-2;//this means that the range [localMin, localMax] is taken
				
				boolean one = (minVal<localMin) && (maxVal<localMin);
				boolean two = (minVal>localMin) && (maxVal>localMax);
				
				if(!(one || two)){//check if the given value is inside the range of a previously determined gap
					return false;
				}
			}
		}
		return true;
	}
} 