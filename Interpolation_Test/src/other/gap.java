package other;

public class gap {
	private String start;
	private String end;
	private int len;
	
	public gap(String s, String e, int l){
		start = s;
		end = e;
		len = l;
	}
	
	public String getStart(){
		return start;
	}
	
	public String getEnd(){
		return end;
	}
	
	public int getLen(){
		return len;
	}
	
	public void interpolate(){
		//passes information to the linInterp class which returns an ArrayList of strings (the intermediate points of the gap)
		
	}
}
