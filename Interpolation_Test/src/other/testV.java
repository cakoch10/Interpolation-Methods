package other;
import java.io.File;
import java.util.*;

import org.apache.commons.math3.fitting.*;

import main.OrderPoints;

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
		
		
		
		Double[] x = {1.0, 3.2, 2.4};
		Double[] y = {5.2, 112.3, 1.1};
		OrderPoints test = new OrderPoints(x,y);
		System.out.println(Arrays.toString(test.getX()));
		System.out.println(Arrays.toString(test.getY()));
		
		System.out.println("\n Original: ");
		System.out.println(Arrays.toString(x));
		System.out.println(Arrays.toString(y));
		
		Double x1[] = new Double[2];
		x1[0] = new Double(2.1);
		x1[1] = new Double(5.2);
		System.out.println(x1[0]);
		
		
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
