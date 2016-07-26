package other;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import main.getNum;
import main.writeToFile;

public class Inaccuracy {
	public static void main(String[] args){
		File f = new File("c:/test/trajectory");
        
        // returns pathnames for files and directory
        File[] paths = f.listFiles();
        
        ArrayList<Double> TrajErrors = new ArrayList<Double>();//stores the trajectory errors
        ArrayList<Double> TSerrors = new ArrayList<Double>();//stores the time series errors
        ArrayList<Double> fSizes = new ArrayList<Double>();//will store the file sizes corresponding to error values
        
        for(File fe:paths){
        	double size = fe.length();
        	double kb = size/1024;
        	if(kb>=13.0 && kb<=16.0){
        		File in = new File("c:/test/trajectory/20081210075232.plt");
        		int len = 50;
        		
        		//get time series error
        		File ts = new File("c:/test/trajectory/Original_TS/20081210075232_TS.plt");
        		String inFile = null;
        		try{
        			BufferedReader bR = new BufferedReader(new FileReader(ts));
        			
        			String line;
        			int count = 0;
        			while((line = bR.readLine()) != null){
    					if(count==0)
    						inFile = line;
    					else
    						inFile += line;
    					count++;
    				}
        			//remove the last comma which isn't needed
        			inFile = inFile.substring(0, inFile.length()-1);
        			bR.close();
        		}catch(IOException ex) {
                    System.err.println("An IOException was caught!");
                    ex.printStackTrace();
                }
        		
        		String[] sHilberts = inFile.split(",");
        		int[] Hvals = new int[sHilberts.length];
        		
        		for(int i=0; i<sHilberts.length; i++)
        			Hvals[i] = Integer.parseInt(sHilberts[i]);
        		
        		double TSdistance = distanceTS(len, Hvals);
        		TSerrors.add(TSdistance);
        		
        		//get trajectory error
        		ArrayList<String> trajLines = new ArrayList<>();
				
				try{
					BufferedReader bR = new BufferedReader(new FileReader(in));
					
					String line;
					while((line = bR.readLine()) != null){
						trajLines.add(line);
					}
					bR.close();
				}catch(IOException ex){
		            System.err.println("An IOException was caught!");
		            ex.printStackTrace();
		        }
				String[] trajArray = trajLines.toArray(new String[trajLines.size()]);
				double trajError = distanceTraj(len, trajArray);
				TrajErrors.add(trajError);
				
				fSizes.add(kb);        		
        	}
        	else if(kb>=121.0 && kb<=125.0){
        		File in = new File("c:/test/trajectory/20081113035731.plt");
        		int len = 1050;
        		
        		//get time series error
        		File ts = new File("c:/test/trajectory/Original_TS/20081113035731_TS.plt");
        		String inFile = null;
        		try{
        			BufferedReader bR = new BufferedReader(new FileReader(ts));
        			
        			String line;
        			int count = 0;
        			while((line = bR.readLine()) != null){
    					if(count==0)
    						inFile = line;
    					else
    						inFile += line;
    					count++;
    				}
        			//remove the last comma which isn't needed
        			inFile = inFile.substring(0, inFile.length()-1);
        			bR.close();
        		}catch(IOException ex) {
                    System.err.println("An IOException was caught!");
                    ex.printStackTrace();
                }
        		
        		String[] sHilberts = inFile.split(",");
        		int[] Hvals = new int[sHilberts.length];
        		
        		for(int i=0; i<sHilberts.length; i++)
        			Hvals[i] = Integer.parseInt(sHilberts[i]);
        		
        		double TSdistance = distanceTS(len, Hvals);
        		TSerrors.add(TSdistance);
        		
        		//get trajectory error
        		ArrayList<String> trajLines = new ArrayList<>();
				
				try{
					BufferedReader bR = new BufferedReader(new FileReader(in));
					
					String line;
					while((line = bR.readLine()) != null){
						trajLines.add(line);
					}
					bR.close();
				}catch(IOException ex){
		            System.err.println("An IOException was caught!");
		            ex.printStackTrace();
		        }
				String[] trajArray = trajLines.toArray(new String[trajLines.size()]);
				double trajError = distanceTraj(len, trajArray);
				TrajErrors.add(trajError);
				
				fSizes.add(kb);  
        	}
        	else if(kb>=208.0 && kb<=214.0){
        		File in = new File("c:/test/trajectory/20081207161131.plt");
        		int len = 150;
        		
        		//get time series error
        		File ts = new File("c:/test/trajectory/Original_TS/20081207161131_TS.plt");
        		String inFile = null;
        		try{
        			BufferedReader bR = new BufferedReader(new FileReader(ts));
        			
        			String line;
        			int count = 0;
        			while((line = bR.readLine()) != null){
    					if(count==0)
    						inFile = line;
    					else
    						inFile += line;
    					count++;
    				}
        			//remove the last comma which isn't needed
        			inFile = inFile.substring(0, inFile.length()-1);
        			bR.close();
        		}catch(IOException ex) {
                    System.err.println("An IOException was caught!");
                    ex.printStackTrace();
                }
        		
        		String[] sHilberts = inFile.split(",");
        		int[] Hvals = new int[sHilberts.length];
        		
        		for(int i=0; i<sHilberts.length; i++)
        			Hvals[i] = Integer.parseInt(sHilberts[i]);
        		
        		double TSdistance = distanceTS(len, Hvals);
        		TSerrors.add(TSdistance);
        		
        		//get trajectory error
        		ArrayList<String> trajLines = new ArrayList<>();
				
				try{
					BufferedReader bR = new BufferedReader(new FileReader(in));
					
					String line;
					while((line = bR.readLine()) != null){
						trajLines.add(line);
					}
					bR.close();
				}catch(IOException ex){
		            System.err.println("An IOException was caught!");
		            ex.printStackTrace();
		        }
				String[] trajArray = trajLines.toArray(new String[trajLines.size()]);
				double trajError = distanceTraj(len, trajArray);
				TrajErrors.add(trajError);
				
				fSizes.add(kb);  
        	}
        	else if(kb>=329.0 && kb<=335.0){
        		File in = new File("c:/test/trajectory/20081105083259.plt");
        		int len = 1850;
        		
        		//get time series error
        		File ts = new File("c:/test/trajectory/Original_TS/20081105083259_TS.plt");
        		String inFile = null;
        		try{
        			BufferedReader bR = new BufferedReader(new FileReader(ts));
        			
        			String line;
        			int count = 0;
        			while((line = bR.readLine()) != null){
    					if(count==0)
    						inFile = line;
    					else
    						inFile += line;
    					count++;
    				}
        			//remove the last comma which isn't needed
        			inFile = inFile.substring(0, inFile.length()-1);
        			bR.close();
        		}catch(IOException ex) {
                    System.err.println("An IOException was caught!");
                    ex.printStackTrace();
                }
        		
        		String[] sHilberts = inFile.split(",");
        		int[] Hvals = new int[sHilberts.length];
        		
        		for(int i=0; i<sHilberts.length; i++)
        			Hvals[i] = Integer.parseInt(sHilberts[i]);
        		
        		double TSdistance = distanceTS(len, Hvals);
        		TSerrors.add(TSdistance);
        		
        		//get trajectory error
        		ArrayList<String> trajLines = new ArrayList<>();
				
				try{
					BufferedReader bR = new BufferedReader(new FileReader(in));
					
					String line;
					while((line = bR.readLine()) != null){
						trajLines.add(line);
					}
					bR.close();
				}catch(IOException ex){
		            System.err.println("An IOException was caught!");
		            ex.printStackTrace();
		        }
				String[] trajArray = trajLines.toArray(new String[trajLines.size()]);
				double trajError = distanceTraj(len, trajArray);
				TrajErrors.add(trajError);
				
				fSizes.add(kb);  
        	}
        	else if(kb>=455.0 && kb<=465.0){
        		File in = new File("c:/test/trajectory/20081119011305.plt");
        		int len = 250;
        		
        		//get time series error
        		File ts = new File("c:/test/trajectory/Original_TS/20081119011305_TS.plt");
        		String inFile = null;
        		try{
        			BufferedReader bR = new BufferedReader(new FileReader(ts));
        			
        			String line;
        			int count = 0;
        			while((line = bR.readLine()) != null){
    					if(count==0)
    						inFile = line;
    					else
    						inFile += line;
    					count++;
    				}
        			//remove the last comma which isn't needed
        			inFile = inFile.substring(0, inFile.length()-1);
        			bR.close();
        		}catch(IOException ex) {
                    System.err.println("An IOException was caught!");
                    ex.printStackTrace();
                }
        		
        		String[] sHilberts = inFile.split(",");
        		int[] Hvals = new int[sHilberts.length];
        		
        		for(int i=0; i<sHilberts.length; i++)
        			Hvals[i] = Integer.parseInt(sHilberts[i]);
        		
        		double TSdistance = distanceTS(len, Hvals);
        		TSerrors.add(TSdistance);
        		
        		//get trajectory error
        		ArrayList<String> trajLines = new ArrayList<>();
				
				try{
					BufferedReader bR = new BufferedReader(new FileReader(in));
					
					String line;
					while((line = bR.readLine()) != null){
						trajLines.add(line);
					}
					bR.close();
				}catch(IOException ex){
		            System.err.println("An IOException was caught!");
		            ex.printStackTrace();
		        }
				String[] trajArray = trajLines.toArray(new String[trajLines.size()]);
				double trajError = distanceTraj(len, trajArray);
				TrajErrors.add(trajError);
				
				fSizes.add(kb);  
        	}
        	else if(kb>=535.0 && kb<=545.0){
        		File in = new File("c:/test/trajectory/20090203100159.plt");
        		int len = 1750;
        		
        		//get time series error
        		File ts = new File("c:/test/trajectory/Original_TS/20090203100159_TS.plt");
        		String inFile = null;
        		try{
        			BufferedReader bR = new BufferedReader(new FileReader(ts));
        			
        			String line;
        			int count = 0;
        			while((line = bR.readLine()) != null){
    					if(count==0)
    						inFile = line;
    					else
    						inFile += line;
    					count++;
    				}
        			//remove the last comma which isn't needed
        			inFile = inFile.substring(0, inFile.length()-1);
        			bR.close();
        		}catch(IOException ex) {
                    System.err.println("An IOException was caught!");
                    ex.printStackTrace();
                }
        		
        		String[] sHilberts = inFile.split(",");
        		int[] Hvals = new int[sHilberts.length];
        		
        		for(int i=0; i<sHilberts.length; i++)
        			Hvals[i] = Integer.parseInt(sHilberts[i]);
        		
        		double TSdistance = distanceTS(len, Hvals);
        		TSerrors.add(TSdistance);
        		
        		//get trajectory error
        		ArrayList<String> trajLines = new ArrayList<>();
				
				try{
					BufferedReader bR = new BufferedReader(new FileReader(in));
					
					String line;
					while((line = bR.readLine()) != null){
						trajLines.add(line);
					}
					bR.close();
				}catch(IOException ex){
		            System.err.println("An IOException was caught!");
		            ex.printStackTrace();
		        }
				String[] trajArray = trajLines.toArray(new String[trajLines.size()]);
				double trajError = distanceTraj(len, trajArray);
				TrajErrors.add(trajError);
				
				fSizes.add(kb);  
        	}
        	else if(kb>=630.0 && kb<=650.0){
        		File in = new File("c:/test/trajectory/20090226005433.plt");
        		int len = 350;
        		
        		//get time series error
        		File ts = new File("c:/test/trajectory/Original_TS/20090226005433_TS.plt");
        		String inFile = null;
        		try{
        			BufferedReader bR = new BufferedReader(new FileReader(ts));
        			
        			String line;
        			int count = 0;
        			while((line = bR.readLine()) != null){
    					if(count==0)
    						inFile = line;
    					else
    						inFile += line;
    					count++;
    				}
        			//remove the last comma which isn't needed
        			inFile = inFile.substring(0, inFile.length()-1);
        			bR.close();
        		}catch(IOException ex) {
                    System.err.println("An IOException was caught!");
                    ex.printStackTrace();
                }
        		
        		String[] sHilberts = inFile.split(",");
        		int[] Hvals = new int[sHilberts.length];
        		
        		for(int i=0; i<sHilberts.length; i++)
        			Hvals[i] = Integer.parseInt(sHilberts[i]);
        		
        		double TSdistance = distanceTS(len, Hvals);
        		TSerrors.add(TSdistance);
        		
        		//get trajectory error
        		ArrayList<String> trajLines = new ArrayList<>();
				
				try{
					BufferedReader bR = new BufferedReader(new FileReader(in));
					
					String line;
					while((line = bR.readLine()) != null){
						trajLines.add(line);
					}
					bR.close();
				}catch(IOException ex){
		            System.err.println("An IOException was caught!");
		            ex.printStackTrace();
		        }
				String[] trajArray = trajLines.toArray(new String[trajLines.size()]);
				double trajError = distanceTraj(len, trajArray);
				TrajErrors.add(trajError);
				
				fSizes.add(kb);  
        	}
        	else if(kb>=760.0 && kb<=780.0){
        		File in = new File("c:/test/trajectory/20090131052455.plt");
        		int len = 1650;
        		
        		//get time series error
        		File ts = new File("c:/test/trajectory/Original_TS/20090131052455_TS.plt");
        		String inFile = null;
        		try{
        			BufferedReader bR = new BufferedReader(new FileReader(ts));
        			
        			String line;
        			int count = 0;
        			while((line = bR.readLine()) != null){
    					if(count==0)
    						inFile = line;
    					else
    						inFile += line;
    					count++;
    				}
        			//remove the last comma which isn't needed
        			inFile = inFile.substring(0, inFile.length()-1);
        			bR.close();
        		}catch(IOException ex) {
                    System.err.println("An IOException was caught!");
                    ex.printStackTrace();
                }
        		
        		String[] sHilberts = inFile.split(",");
        		int[] Hvals = new int[sHilberts.length];
        		
        		for(int i=0; i<sHilberts.length; i++)
        			Hvals[i] = Integer.parseInt(sHilberts[i]);
        		
        		double TSdistance = distanceTS(len, Hvals);
        		TSerrors.add(TSdistance);
        		
        		//get trajectory error
        		ArrayList<String> trajLines = new ArrayList<>();
				
				try{
					BufferedReader bR = new BufferedReader(new FileReader(in));
					
					String line;
					while((line = bR.readLine()) != null){
						trajLines.add(line);
					}
					bR.close();
				}catch(IOException ex){
		            System.err.println("An IOException was caught!");
		            ex.printStackTrace();
		        }
				String[] trajArray = trajLines.toArray(new String[trajLines.size()]);
				double trajError = distanceTraj(len, trajArray);
				TrajErrors.add(trajError);
				
				fSizes.add(kb);  
        	}
        	else if(kb>=840.0 && kb<=850.0){
        		File in = new File("c:/test/trajectory/20080927233805.plt");
        		int len = 450;
        		
        		//get time series error
        		File ts = new File("c:/test/trajectory/Original_TS/20080927233805_TS.plt");
        		String inFile = null;
        		try{
        			BufferedReader bR = new BufferedReader(new FileReader(ts));
        			
        			String line;
        			int count = 0;
        			while((line = bR.readLine()) != null){
    					if(count==0)
    						inFile = line;
    					else
    						inFile += line;
    					count++;
    				}
        			//remove the last comma which isn't needed
        			inFile = inFile.substring(0, inFile.length()-1);
        			bR.close();
        		}catch(IOException ex) {
                    System.err.println("An IOException was caught!");
                    ex.printStackTrace();
                }
        		
        		String[] sHilberts = inFile.split(",");
        		int[] Hvals = new int[sHilberts.length];
        		
        		for(int i=0; i<sHilberts.length; i++)
        			Hvals[i] = Integer.parseInt(sHilberts[i]);
        		
        		double TSdistance = distanceTS(len, Hvals);
        		TSerrors.add(TSdistance);
        		
        		//get trajectory error
        		ArrayList<String> trajLines = new ArrayList<>();
				
				try{
					BufferedReader bR = new BufferedReader(new FileReader(in));
					
					String line;
					while((line = bR.readLine()) != null){
						trajLines.add(line);
					}
					bR.close();
				}catch(IOException ex){
		            System.err.println("An IOException was caught!");
		            ex.printStackTrace();
		        }
				String[] trajArray = trajLines.toArray(new String[trajLines.size()]);
				double trajError = distanceTraj(len, trajArray);
				TrajErrors.add(trajError);
				
				fSizes.add(kb);  
        	}
        	else if(kb>=925.0 && kb<=930.0){
        		File in = new File("c:/test/trajectory/20081109055559.plt");
        		int len = 1550;
        		
        		//get time series error
        		File ts = new File("c:/test/trajectory/Original_TS/20081109055559_TS.plt");
        		String inFile = null;
        		try{
        			BufferedReader bR = new BufferedReader(new FileReader(ts));
        			
        			String line;
        			int count = 0;
        			while((line = bR.readLine()) != null){
    					if(count==0)
    						inFile = line;
    					else
    						inFile += line;
    					count++;
    				}
        			//remove the last comma which isn't needed
        			inFile = inFile.substring(0, inFile.length()-1);
        			bR.close();
        		}catch(IOException ex) {
                    System.err.println("An IOException was caught!");
                    ex.printStackTrace();
                }
        		
        		String[] sHilberts = inFile.split(",");
        		int[] Hvals = new int[sHilberts.length];
        		
        		for(int i=0; i<sHilberts.length; i++)
        			Hvals[i] = Integer.parseInt(sHilberts[i]);
        		
        		double TSdistance = distanceTS(len, Hvals);
        		TSerrors.add(TSdistance);
        		
        		//get trajectory error
        		ArrayList<String> trajLines = new ArrayList<>();
				
				try{
					BufferedReader bR = new BufferedReader(new FileReader(in));
					
					String line;
					while((line = bR.readLine()) != null){
						trajLines.add(line);
					}
					bR.close();
				}catch(IOException ex){
		            System.err.println("An IOException was caught!");
		            ex.printStackTrace();
		        }
				String[] trajArray = trajLines.toArray(new String[trajLines.size()]);
				double trajError = distanceTraj(len, trajArray);
				TrajErrors.add(trajError);
				
				fSizes.add(kb);  
        	}
        	else if(kb>=1090.0 && kb<=1100.0){
        		File in = new File("c:/test/trajectory/20090113112028.plt");
        		int len = 550;
        		
        		//get time series error
        		File ts = new File("c:/test/trajectory/Original_TS/20090113112028_TS.plt");
        		String inFile = null;
        		try{
        			BufferedReader bR = new BufferedReader(new FileReader(ts));
        			
        			String line;
        			int count = 0;
        			while((line = bR.readLine()) != null){
    					if(count==0)
    						inFile = line;
    					else
    						inFile += line;
    					count++;
    				}
        			//remove the last comma which isn't needed
        			inFile = inFile.substring(0, inFile.length()-1);
        			bR.close();
        		}catch(IOException ex) {
                    System.err.println("An IOException was caught!");
                    ex.printStackTrace();
                }
        		
        		String[] sHilberts = inFile.split(",");
        		int[] Hvals = new int[sHilberts.length];
        		
        		for(int i=0; i<sHilberts.length; i++)
        			Hvals[i] = Integer.parseInt(sHilberts[i]);
        		
        		double TSdistance = distanceTS(len, Hvals);
        		TSerrors.add(TSdistance);
        		
        		//get trajectory error
        		ArrayList<String> trajLines = new ArrayList<>();
				
				try{
					BufferedReader bR = new BufferedReader(new FileReader(in));
					
					String line;
					while((line = bR.readLine()) != null){
						trajLines.add(line);
					}
					bR.close();
				}catch(IOException ex){
		            System.err.println("An IOException was caught!");
		            ex.printStackTrace();
		        }
				String[] trajArray = trajLines.toArray(new String[trajLines.size()]);
				double trajError = distanceTraj(len, trajArray);
				TrajErrors.add(trajError);
				
				fSizes.add(kb);  
        	}
        	else if(kb>=1149.0 && kb<=1155.0){
        		File in = new File("c:/test/trajectory/20090323212145.plt");
        		int len = 1450;
        		
        		//get time series error
        		File ts = new File("c:/test/trajectory/Original_TS/20090323212145_TS.plt");
        		String inFile = null;
        		try{
        			BufferedReader bR = new BufferedReader(new FileReader(ts));
        			
        			String line;
        			int count = 0;
        			while((line = bR.readLine()) != null){
    					if(count==0)
    						inFile = line;
    					else
    						inFile += line;
    					count++;
    				}
        			//remove the last comma which isn't needed
        			inFile = inFile.substring(0, inFile.length()-1);
        			bR.close();
        		}catch(IOException ex) {
                    System.err.println("An IOException was caught!");
                    ex.printStackTrace();
                }
        		
        		String[] sHilberts = inFile.split(",");
        		int[] Hvals = new int[sHilberts.length];
        		
        		for(int i=0; i<sHilberts.length; i++)
        			Hvals[i] = Integer.parseInt(sHilberts[i]);
        		
        		double TSdistance = distanceTS(len, Hvals);
        		TSerrors.add(TSdistance);
        		
        		//get trajectory error
        		ArrayList<String> trajLines = new ArrayList<>();
				
				try{
					BufferedReader bR = new BufferedReader(new FileReader(in));
					
					String line;
					while((line = bR.readLine()) != null){
						trajLines.add(line);
					}
					bR.close();
				}catch(IOException ex){
		            System.err.println("An IOException was caught!");
		            ex.printStackTrace();
		        }
				String[] trajArray = trajLines.toArray(new String[trajLines.size()]);
				double trajError = distanceTraj(len, trajArray);
				TrajErrors.add(trajError);
				
				fSizes.add(kb);  
        	}
        	else if(kb>=1255.0 && kb<=1260.0){
        		File in = new File("c:/test/trajectory/20090117223222.plt");
        		int len = 650;
        		
        		//get time series error
        		File ts = new File("c:/test/trajectory/Original_TS/20090117223222_TS.plt");
        		String inFile = null;
        		try{
        			BufferedReader bR = new BufferedReader(new FileReader(ts));
        			
        			String line;
        			int count = 0;
        			while((line = bR.readLine()) != null){
    					if(count==0)
    						inFile = line;
    					else
    						inFile += line;
    					count++;
    				}
        			//remove the last comma which isn't needed
        			inFile = inFile.substring(0, inFile.length()-1);
        			bR.close();
        		}catch(IOException ex) {
                    System.err.println("An IOException was caught!");
                    ex.printStackTrace();
                }
        		
        		String[] sHilberts = inFile.split(",");
        		int[] Hvals = new int[sHilberts.length];
        		
        		for(int i=0; i<sHilberts.length; i++)
        			Hvals[i] = Integer.parseInt(sHilberts[i]);
        		
        		double TSdistance = distanceTS(len, Hvals);
        		TSerrors.add(TSdistance);
        		
        		//get trajectory error
        		ArrayList<String> trajLines = new ArrayList<>();
				
				try{
					BufferedReader bR = new BufferedReader(new FileReader(in));
					
					String line;
					while((line = bR.readLine()) != null){
						trajLines.add(line);
					}
					bR.close();
				}catch(IOException ex){
		            System.err.println("An IOException was caught!");
		            ex.printStackTrace();
		        }
				String[] trajArray = trajLines.toArray(new String[trajLines.size()]);
				double trajError = distanceTraj(len, trajArray);
				TrajErrors.add(trajError);
				
				fSizes.add(kb);  
        	}
        	else if(kb>=1515.0 && kb<=1525.0){
        		File in = new File("c:/test/trajectory/20110828143340.plt");
        		int len = 1350;
        		
        		//get time series error
        		File ts = new File("c:/test/trajectory/Original_TS/20110828143340_TS.plt");
        		String inFile = null;
        		try{
        			BufferedReader bR = new BufferedReader(new FileReader(ts));
        			
        			String line;
        			int count = 0;
        			while((line = bR.readLine()) != null){
    					if(count==0)
    						inFile = line;
    					else
    						inFile += line;
    					count++;
    				}
        			//remove the last comma which isn't needed
        			inFile = inFile.substring(0, inFile.length()-1);
        			bR.close();
        		}catch(IOException ex) {
                    System.err.println("An IOException was caught!");
                    ex.printStackTrace();
                }
        		
        		String[] sHilberts = inFile.split(",");
        		int[] Hvals = new int[sHilberts.length];
        		
        		for(int i=0; i<sHilberts.length; i++)
        			Hvals[i] = Integer.parseInt(sHilberts[i]);
        		
        		double TSdistance = distanceTS(len, Hvals);
        		TSerrors.add(TSdistance);
        		
        		//get trajectory error
        		ArrayList<String> trajLines = new ArrayList<>();
				
				try{
					BufferedReader bR = new BufferedReader(new FileReader(in));
					
					String line;
					while((line = bR.readLine()) != null){
						trajLines.add(line);
					}
					bR.close();
				}catch(IOException ex){
		            System.err.println("An IOException was caught!");
		            ex.printStackTrace();
		        }
				String[] trajArray = trajLines.toArray(new String[trajLines.size()]);
				double trajError = distanceTraj(len, trajArray);
				TrajErrors.add(trajError);
				
				fSizes.add(kb);  
        	}
        	else if(kb>=1615.0 && kb<=1625.0){
        		File in = new File("c:/test/trajectory/20110502000104.plt");
        		int len = 750;
        		
        		//get time series error
        		File ts = new File("c:/test/trajectory/Original_TS/20110502000104_TS.plt");
        		String inFile = null;
        		try{
        			BufferedReader bR = new BufferedReader(new FileReader(ts));
        			
        			String line;
        			int count = 0;
        			while((line = bR.readLine()) != null){
    					if(count==0)
    						inFile = line;
    					else
    						inFile += line;
    					count++;
    				}
        			//remove the last comma which isn't needed
        			inFile = inFile.substring(0, inFile.length()-1);
        			bR.close();
        		}catch(IOException ex) {
                    System.err.println("An IOException was caught!");
                    ex.printStackTrace();
                }
        		
        		String[] sHilberts = inFile.split(",");
        		int[] Hvals = new int[sHilberts.length];
        		
        		for(int i=0; i<sHilberts.length; i++)
        			Hvals[i] = Integer.parseInt(sHilberts[i]);
        		
        		double TSdistance = distanceTS(len, Hvals);
        		TSerrors.add(TSdistance);
        		
        		//get trajectory error
        		ArrayList<String> trajLines = new ArrayList<>();
				
				try{
					BufferedReader bR = new BufferedReader(new FileReader(in));
					
					String line;
					while((line = bR.readLine()) != null){
						trajLines.add(line);
					}
					bR.close();
				}catch(IOException ex){
		            System.err.println("An IOException was caught!");
		            ex.printStackTrace();
		        }
				String[] trajArray = trajLines.toArray(new String[trajLines.size()]);
				double trajError = distanceTraj(len, trajArray);
				TrajErrors.add(trajError);
				
				fSizes.add(kb);  
        	
        	}
        	else if(kb>=2214.0 && kb<=2220.0){
        		File in = new File("c:/test/trajectory/20081114124439.plt");
        		int len = 1250;
        		
        		//get time series error
        		File ts = new File("c:/test/trajectory/Original_TS/20081114124439_TS.plt");
        		String inFile = null;
        		try{
        			BufferedReader bR = new BufferedReader(new FileReader(ts));
        			
        			String line;
        			int count = 0;
        			while((line = bR.readLine()) != null){
    					if(count==0)
    						inFile = line;
    					else
    						inFile += line;
    					count++;
    				}
        			//remove the last comma which isn't needed
        			inFile = inFile.substring(0, inFile.length()-1);
        			bR.close();
        		}catch(IOException ex) {
                    System.err.println("An IOException was caught!");
                    ex.printStackTrace();
                }
        		
        		String[] sHilberts = inFile.split(",");
        		int[] Hvals = new int[sHilberts.length];
        		
        		for(int i=0; i<sHilberts.length; i++)
        			Hvals[i] = Integer.parseInt(sHilberts[i]);
        		
        		double TSdistance = distanceTS(len, Hvals);
        		TSerrors.add(TSdistance);
        		
        		//get trajectory error
        		ArrayList<String> trajLines = new ArrayList<>();
				
				try{
					BufferedReader bR = new BufferedReader(new FileReader(in));
					
					String line;
					while((line = bR.readLine()) != null){
						trajLines.add(line);
					}
					bR.close();
				}catch(IOException ex){
		            System.err.println("An IOException was caught!");
		            ex.printStackTrace();
		        }
				String[] trajArray = trajLines.toArray(new String[trajLines.size()]);
				double trajError = distanceTraj(len, trajArray);
				TrajErrors.add(trajError);
				
				fSizes.add(kb);  
        	}
        	else if(kb>=2425.0 && kb<=2435.0){
        		File in = new File("c:/test/trajectory/20110514111537.plt");
        		int len = 850;
        		
        		//get time series error
        		File ts = new File("c:/test/trajectory/Original_TS/20110514111537_TS.plt");
        		String inFile = null;
        		try{
        			BufferedReader bR = new BufferedReader(new FileReader(ts));
        			
        			String line;
        			int count = 0;
        			while((line = bR.readLine()) != null){
    					if(count==0)
    						inFile = line;
    					else
    						inFile += line;
    					count++;
    				}
        			//remove the last comma which isn't needed
        			inFile = inFile.substring(0, inFile.length()-1);
        			bR.close();
        		}catch(IOException ex) {
                    System.err.println("An IOException was caught!");
                    ex.printStackTrace();
                }
        		
        		String[] sHilberts = inFile.split(",");
        		int[] Hvals = new int[sHilberts.length];
        		
        		for(int i=0; i<sHilberts.length; i++)
        			Hvals[i] = Integer.parseInt(sHilberts[i]);
        		
        		double TSdistance = distanceTS(len, Hvals);
        		TSerrors.add(TSdistance);
        		
        		//get trajectory error
        		ArrayList<String> trajLines = new ArrayList<>();
				
				try{
					BufferedReader bR = new BufferedReader(new FileReader(in));
					
					String line;
					while((line = bR.readLine()) != null){
						trajLines.add(line);
					}
					bR.close();
				}catch(IOException ex){
		            System.err.println("An IOException was caught!");
		            ex.printStackTrace();
		        }
				String[] trajArray = trajLines.toArray(new String[trajLines.size()]);
				double trajError = distanceTraj(len, trajArray);
				TrajErrors.add(trajError);
				
				fSizes.add(kb);  
        	}
        	else if(kb>=2779.0 && kb<=2789.0){
        		File in = new File("c:/test/trajectory/20090930071934.plt");
        		int len = 1150;
        		
        		//get time series error
        		File ts = new File("c:/test/trajectory/Original_TS/20090930071934_TS.plt");
        		String inFile = null;
        		try{
        			BufferedReader bR = new BufferedReader(new FileReader(ts));
        			
        			String line;
        			int count = 0;
        			while((line = bR.readLine()) != null){
    					if(count==0)
    						inFile = line;
    					else
    						inFile += line;
    					count++;
    				}
        			//remove the last comma which isn't needed
        			inFile = inFile.substring(0, inFile.length()-1);
        			bR.close();
        		}catch(IOException ex) {
                    System.err.println("An IOException was caught!");
                    ex.printStackTrace();
                }
        		
        		String[] sHilberts = inFile.split(",");
        		int[] Hvals = new int[sHilberts.length];
        		
        		for(int i=0; i<sHilberts.length; i++)
        			Hvals[i] = Integer.parseInt(sHilberts[i]);
        		
        		double TSdistance = distanceTS(len, Hvals);
        		TSerrors.add(TSdistance);
        		
        		//get trajectory error
        		ArrayList<String> trajLines = new ArrayList<>();
				
				try{
					BufferedReader bR = new BufferedReader(new FileReader(in));
					
					String line;
					while((line = bR.readLine()) != null){
						trajLines.add(line);
					}
					bR.close();
				}catch(IOException ex){
		            System.err.println("An IOException was caught!");
		            ex.printStackTrace();
		        }
				String[] trajArray = trajLines.toArray(new String[trajLines.size()]);
				double trajError = distanceTraj(len, trajArray);
				TrajErrors.add(trajError);
				
				fSizes.add(kb);  
        	}
        	else if(kb>=3149.0 && kb<=3159.0){
        		File in = new File("c:/test/trajectory/20111119010003.plt");
        		int len = 950;
        		
        		//get time series error
        		File ts = new File("c:/test/trajectory/Original_TS/20111119010003_TS.plt");
        		String inFile = null;
        		try{
        			BufferedReader bR = new BufferedReader(new FileReader(ts));
        			
        			String line;
        			int count = 0;
        			while((line = bR.readLine()) != null){
    					if(count==0)
    						inFile = line;
    					else
    						inFile += line;
    					count++;
    				}
        			//remove the last comma which isn't needed
        			inFile = inFile.substring(0, inFile.length()-1);
        			bR.close();
        		}catch(IOException ex) {
                    System.err.println("An IOException was caught!");
                    ex.printStackTrace();
                }
        		
        		String[] sHilberts = inFile.split(",");
        		int[] Hvals = new int[sHilberts.length];
        		
        		for(int i=0; i<sHilberts.length; i++)
        			Hvals[i] = Integer.parseInt(sHilberts[i]);
        		
        		double TSdistance = distanceTS(len, Hvals);
        		TSerrors.add(TSdistance);
        		
        		//get trajectory error
        		ArrayList<String> trajLines = new ArrayList<>();
				
				try{
					BufferedReader bR = new BufferedReader(new FileReader(in));
					
					String line;
					while((line = bR.readLine()) != null){
						trajLines.add(line);
					}
					bR.close();
				}catch(IOException ex){
		            System.err.println("An IOException was caught!");
		            ex.printStackTrace();
		        }
				String[] trajArray = trajLines.toArray(new String[trajLines.size()]);
				double trajError = distanceTraj(len, trajArray);
				TrajErrors.add(trajError);
				
				fSizes.add(kb);  
        	}
        	else if(kb>=3475.0 && kb<=3485.0){
        		File in = new File("c:/test/trajectory/20090528211734.plt");
        		int len = 1950;
        		
        		//get time series error
        		File ts = new File("c:/test/trajectory/Original_TS/20090528211734_TS.plt");
        		String inFile = null;
        		try{
        			BufferedReader bR = new BufferedReader(new FileReader(ts));
        			
        			String line;
        			int count = 0;
        			while((line = bR.readLine()) != null){
    					if(count==0)
    						inFile = line;
    					else
    						inFile += line;
    					count++;
    				}
        			//remove the last comma which isn't needed
        			inFile = inFile.substring(0, inFile.length()-1);
        			bR.close();
        		}catch(IOException ex) {
                    System.err.println("An IOException was caught!");
                    ex.printStackTrace();
                }
        		
        		String[] sHilberts = inFile.split(",");
        		int[] Hvals = new int[sHilberts.length];
        		
        		for(int i=0; i<sHilberts.length; i++)
        			Hvals[i] = Integer.parseInt(sHilberts[i]);
        		
        		double TSdistance = distanceTS(len, Hvals);
        		TSerrors.add(TSdistance);
        		
        		//get trajectory error
        		ArrayList<String> trajLines = new ArrayList<>();
				
				try{
					BufferedReader bR = new BufferedReader(new FileReader(in));
					
					String line;
					while((line = bR.readLine()) != null){
						trajLines.add(line);
					}
					bR.close();
				}catch(IOException ex){
		            System.err.println("An IOException was caught!");
		            ex.printStackTrace();
		        }
				String[] trajArray = trajLines.toArray(new String[trajLines.size()]);
				double trajError = distanceTraj(len, trajArray);
				TrajErrors.add(trajError);
				
				fSizes.add(kb);  
        	}
        	
        	System.out.println("End");
        }
        //write results to a file
    	String[] write2file = new String[TrajErrors.size()];
    	for(int i=0; i<TrajErrors.size(); i++)
    		write2file[i] = TrajErrors.get(i).toString() + "," + TSerrors.get(i).toString() + "," + fSizes.get(i).toString();
    	String writePath = "C:/test/trajectory/Error_Analysis/totalDistance.plt";
    	writeToFile wr = new writeToFile(write2file, writePath);
    	wr.write();
	}
	
	public static double distanceTraj(int len, String[] real){//returns the total distance covered in the original trajectory
		
		double dist = 0.0;
		
		for(int i=16; i<(15+len); i++){
			getNum point = new getNum(real[i]);
			point.extract();
			double lat = point.getLat();
			double lon = point.getLon();
			
			getNum nextP = new getNum(real[i+1]);
			nextP.extract();
			double nlat = nextP.getLat();
			double nlon = nextP.getLon();
			
			dist+=dist(lon, lat, nlon, nlat);
		}
		return dist;
	}
	

	public static double dist(double x1, double y1, double x2, double y2){
		double distance = 0.0;
		final double r = 6371.0; //radius of earth in km
		
		x1 = Math.toRadians(x1);
		y1 = Math.toRadians(y1);
		
		x2 = Math.toRadians(x2);
		y2 = Math.toRadians(y2);
		
		double half1 = (y1-y2) / 2;
		double half2 = (x1-x2) / 2;
				
		double part1 = Math.sin(half1)*Math.sin(half1) + Math.cos(y1)*Math.cos(y1)*Math.sin(half2)*Math.sin(half2);
		double part2 = Math.sqrt(part1);
		distance = 2*r*Math.asin(part2);//distance is in km due to units of earth's radius
		
		return distance;
	}
	
	public static double distanceTS(int len, int[] hilberts){
		double d = 0.0;
		for(int i = 10; i<(9+len); i++){
			double initD = (double)hilberts[i] - (double)hilberts[i+1];
			d += Math.sqrt(1.0 + initD*initD);//apply distance formula
		}
		return d;
	}
}