package com.space.filling.curve;

public final class MathOperation {
	public static int binaryToDecimal(int binaryValue)
	{
		int decimalValue = 0;
		String strBinary = Integer.toString(binaryValue);
		System.out.println(strBinary);
		int len = strBinary.length();
		for (int i = 0; i < len; i++)
		{
			decimalValue = decimalValue + 
					(Integer.parseInt(strBinary.charAt(i) + "")) * ((int) Math.pow(2, i));
		}
		
		return decimalValue;
	}
	
	public static int binaryToDecimal(String strBinary)
	{
		int decimalValue = 0;
		//String strBinary = Integer.toString(binaryValue);
		
		int len = strBinary.length();
		for (int i = 0; i < len; i++)
		{
			decimalValue = decimalValue + 
					(Integer.parseInt(strBinary.charAt(len - i - 1) + "")) * ((int) Math.pow(2, i));
		}
		
		return decimalValue;
	}
	
	public static String shiftPosition(String startValue, boolean isLeft, int shiftDigits)
	{
		String resultValue = null;
		String shift = "";
		int i = 0;
		
		for (i = 0; i < shiftDigits; i++)
			shift = shift + "0"; 
		
		if (isLeft)
			resultValue = startValue + shift;
		else
		{
			startValue = startValue.substring(0, startValue.length() - shiftDigits);
			resultValue = shift + startValue;
		}
		
		return resultValue;
	}
	
	public static void main(String args[])
	{
		int x = 001;
		x = x << 2;
		System.out.println(MathOperation.binaryToDecimal(x));
		String startValue = "1010";
		boolean isLeft = true;
		int shiftDigits = 2;
		System.out.println(MathOperation.shiftPosition(startValue, isLeft, shiftDigits));
		startValue = MathOperation.shiftPosition(startValue, isLeft, shiftDigits);
		
		System.out.println(MathOperation.binaryToDecimal(startValue));
	}
}
