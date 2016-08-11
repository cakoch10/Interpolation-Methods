import java.io.File;
import main.writeToFile;

public class getFileNames {
	public static void main(String[] args){
		File f = new File("C:/test/trajectory2/OriginalTrajectories");
		File[] paths = f.listFiles();
		String[] lines = new String[paths.length];
		int count = 0;
		
		for(File s: paths){
			double size = s.length();
			size = size / 1024.0;
			lines[count] = s.getName() + "\t" + Double.toString(size);
			count++;			
		}
		
		writeToFile writeNew = new writeToFile(lines, "C:/names.txt");
		writeNew.write();
	}
}