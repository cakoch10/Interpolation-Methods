package roughness;

import java.io.File;
import main.writeToFile;

public class roughnessGeneral {
	public static void main(String[] args){
		File actualDir = new File("C:/test/trajectory2/Trajectory_Gaps");
		File[] actualList = actualDir.listFiles();
		String[] results = new String[actualList.length];
		
		int count = 0;
		for(File f:actualList){
			calculateRoughness R = new calculateRoughness(f.getAbsolutePath());
			double result = R.getRoughness();
			results[count] = f.getName().substring(0, f.getName().length()-4) + "," + Double.toString(result);
			count++;
			System.out.println("Added results " + count);
		}
		
		writeToFile resultList = new writeToFile(results, "C:/test/trajectory2/Error_Analysis/roughness.plt");
		resultList.write();
	}
}