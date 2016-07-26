package other;
import java.io.*;
import java.util.ArrayList;

public class passFiles {
	public static void main(String[] args){
		
	      File f = null;
	      File[] paths;
	      
	      try{      
	         // create new file
	         f = new File("c:/test");
	         
	         // returns pathnames for files and directory
	         paths = f.listFiles();
	         
	         for(File nFile:paths){
	        	 gapRemove modFile = new gapRemove(nFile);
	        	 modFile.compute_gaps();
	        	 if(modFile.getValidity()){
	        		 modFile.write();
	        	 }
	         }
	         
	      }catch(Exception e){
		         // if any error occurs
		         e.printStackTrace();
		      }
	}
}
