package com.gateway.tool;


public class IntegerUtils {
//	static int uuid;
	public static String Int2HexString(int num){
		StringBuilder sb = new StringBuilder();
		sb.append("0x");
		String numHex = Integer.toHexString(num).toUpperCase();
		int len = numHex.length();
		if(len < 4){
			String prefix = len == 3 ? "0" : (len == 2) ? "00" : "000";
			sb.append(prefix);
		}
		
		sb.append(numHex);
		
		return sb.toString();
	}

}
