package utils;

public class UnicodeUtils {

	public static String string2Unicode(String string) {
		StringBuffer unicode = new StringBuffer();
		for (int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);
			unicode.append("\\u" + Integer.toHexString(c));
		}
		return unicode.toString();
	}

	public static String unicode2String(String unicode) {
		StringBuffer string = new StringBuffer();
		String[] hex = unicode.split("\\\\u");
		for (int i = 1; i < hex.length; i++) {
			int data = Integer.parseInt(hex[i], 16);
			string.append((char) data);
		}
		return string.toString();
	}

}
