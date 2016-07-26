package main;
import java.io.*;

public class writeToFile {
	
	private String[] data;//includes the lines to be written
	private String name;//file location
	
	public writeToFile(String[] s, String n){
		data = s;
		name = n;
	}
	
	public void write(){
		File temporary = new File(name);
      
		try{
			FileWriter fstream = new FileWriter(temporary);
			BufferedWriter fw = new BufferedWriter(fstream);
			
			for(int i=0; i<data.length; i++){
				fw.write(data[i]);
				fw.newLine();
			}
			fw.close();
			fstream.close();
		}
		catch(IOException ex) {
            System.err.println("An IOException was caught!");
            ex.printStackTrace();
        }		
	}
}
