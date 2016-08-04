package other;
import java.io.File;

public class testV {
	public static void main(String[] args){
		
		//CreateGaps nList = new CreateGaps();
		/*File f = new File("C:/test/trajectory/Trajectory_with_gaps");
        File[] paths = f.listFiles();
        Double[] sizes = new Double[paths.length];
        for(int i=0; i<paths.length; i++){
        	Double size = new Double(paths[i].length());
        	sizes[i] = size;
        }
        Double[] sizes_sorted = new Double[sizes.length];
        for(int i=0; i<sizes.length; i++){
        	sizes_sorted[i] = sizes[i];
        }
        
        Arrays.sort(sizes_sorted);
        
        File[] paths_sorted = new File[paths.length];//this array will order to files based on their sizes from least to greatest
        
        for(int i=0; i<paths.length; i++){
        	Double currVal = sizes_sorted[i];
        	int index = Arrays.asList(sizes).indexOf(currVal);
        	paths_sorted[i] = paths[index];
        }*/
		/*
		final WeightedObservedPoints obs = new WeightedObservedPoints();
		obs.add(-1.00, 2.021170021833143);
		obs.add(-0.99, 2.221135431136975);
		obs.add(-0.98, 2.09985277659314);
		obs.add(-0.97, 2.0211192647627025);
		// ... Lots of lines omitted ...
		obs.add(0.99, -2.4345814727089854);

		// Instantiate a third-degree polynomial fitter.
		final PolynomialCurveFitter fitter = PolynomialCurveFitter.create(3);

		// Retrieve fitted parameters (coefficients of the polynomial function).
		final double[] coeff = fitter.fit(obs.toList());
        
        
        for(double x:coeff){
        	System.out.println(x);
        }
        
        System.out.println(coeff[0] + 0.99*coeff[1] + 0.99*0.99*coeff[2] + 0.99*0.99*0.99*coeff[3]);
       
        */
		
		File linearTS = new File("C:/test/trajectory2/Cubic_TS");
			File[] linearTSList = linearTS.listFiles();
			for(int i=0; i<1000; i=i+100){
			String name = linearTSList[i].getName();
			name = name.substring(18, name.length()-11);//20070921120306_TS_100_Cubic_222281.plt
			String[] name2 = name.split("_");
			int gapLength = Integer.parseInt(name2[0]);
			System.out.println(gapLength);
			System.out.println(linearTSList[i].getName());
		}
        /*
        
        for(int i=0; i<paths.length; i++){
        	System.out.println(paths_sorted[i].getAbsolutePath());
        	System.out.println(paths_sorted[i].length());
        }
		*/
		/*tsError LinVsAct = new tsError("C:/test/Time Series/Actual_TS.txt", "C:/test/Time Series/LM_TS.txt");
		System.out.println(LinVsAct.getError());
		System.out.println("Squared: " + LinVsAct.getErrorSq());

		tsError VelVsAct = new tsError("C:/test/Time Series/Actual_TS.txt", "C:/test/Time Series/ip7_TS.txt");
		System.out.println(VelVsAct.getError());
		System.out.println("Squared: " + VelVsAct.getErrorSq());*/
	}

}
