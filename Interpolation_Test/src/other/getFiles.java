package other;
import java.io.*;
import java.util.ArrayList;

public class getFiles {
		public static void main(String[] args){
		
	      File f = null;
	      File[] paths;
	      
	      try{      
	         // create new file
	         f = new File("c:/test");
	         
	         // returns pathnames for files and directory
	         paths = f.listFiles();
	         
	         File t = paths[1];
	         
	      //   FileReader fileReader = new FileReader(paths[1].getName());
	        BufferedReader bR = new BufferedReader(new FileReader(paths[2]));
	        
	        String n2 = paths[2].getPath();
	        System.out.println("PATH NAME: " + n2);

	        
	        
	        File temporary = new File(n2.substring(0, n2.length()-4)+"_gaps.plt");
	        FileWriter fstream = new FileWriter(temporary);
			BufferedWriter fw = new BufferedWriter(fstream);
	       
	        String line = null;
	 	    int count = 0;
	 	    ArrayList<Integer> times = new ArrayList<>();
	 	    
	 	    while ((line = bR.readLine()) != null){
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
	 	        	if(count == 6){
	 	        		System.out.println("Char is: "+ Character.getNumericValue(sub.charAt(0)));
	 	        		System.out.println("Sub is: "+sub);
	 	        	}
	 	        	times.add(n);
	 	    	}
	 	    	
	 	    	if(count == 0){
		 	    	fw.write("FIRST LINE");
	 	        	fw.newLine();
	 	    	}
	 	    	else{
		 	    	fw.write(line);
	 	        	fw.newLine();
	 	    	}
	 	    	
	 	    	count++;
	 	    }
	 	    fw.close();
	 	    bR.close();
	 	    fstream.close();
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
	 	    
	 	    
	 	    System.out.println("The count is: "+count);
	 	    
	 	   for(int i=0; i<times.size()-1; i++){
	 	    	System.out.println("THE DIFF IS: "+ diffs.get(i));
	 	    }
	         
	 	   
	 	   //create an algorithm for finding appropriate, potential gaps
	 	   int[][] gaps = new int[10][2];//an array to store gap locations
	 	   
	 	   //initialize all values to -1
	 	   for(int i =0; i<10; i++){
	 		   gaps[i][0]=-1;
	 		   gaps[i][1]=-1;
	 	   }
	 	   
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
	 	   
	 	   for(int i = 10; i>0; i--){
	 		   for(int k=0; k<diffs.size()-9; k++){
	 			  int sum = 0;
	 			  for(int j=k; j<i+k; j++){
	 				  sum+=diffs.get(j);
	 			  }
	 			  
	 			  if(sum<=5*i){//this means we have found a potential sequence to remove
	 				  //first we need to check whether the sequence overlaps one that is already taken
	 				  boolean free = check(k, i, gaps); //check whether a sequence of length i starting at k has been taken
	 				  if(free){
	 					  gaps[i-1][0]=i-1;//denotes length of gap
	 					  gaps[i-1][1]=k;//denotes starting point
	 				  }
	 			  }
	 		   }
	 	   }
	 	   
	 	   for(int i=0; i<10; i++){
	 		   if(gaps[i][0]==-1 || gaps[i][1]==-1)
	 			   System.out.println("ERROR, NOT ALL GAPS FOUND");
	 	   }
	 	   
	 	   
	 	   
	 	   
	        //System.out.println(t);
	         
	         // for each pathname in pathname array
	         for(File path:paths){
	            System.out.print(2); // prints file and directory paths
	         }
	         
	         /*
	         for(File path:paths){
	        	 gapRemove temp = new gapRemove(path);
	         }
	         */
	         
	      }catch(Exception e){
	         // if any error occurs
	         e.printStackTrace();
	      }
	      
	   }
		
		
		public static boolean check(int start, int leng, int[][] gaps){
			
			boolean checkVal = true;//is true unless an invalid case is found
			
			int maxVal = start+leng-1;
			int minVal = start;
		
			for(int i=0; i<10; i++){
				if(gaps[i][0]>=0){
					int localMax = gaps[i][1]+gaps[i][0];
					int localMin = gaps[i][1];//this means that the range [localMin, localMax] is taken
					if(maxVal>=localMin || minVal<=localMax)//check if the given value is inside the range of a previously determined gap
						checkVal = false;
				}
			}
			
	    	  return checkVal;
	    }
}
