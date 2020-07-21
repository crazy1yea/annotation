package com.example.annotation.util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	
	/**
	 * html标签替换
	 * @param str
	 * @return
	 */
	public static String removeHtml(String str) {
		String result = "";
		if (str != null && !str.trim().equals("")) {
			String regexHtml = "<[^>]+>";
			Pattern pattern = Pattern.compile(regexHtml, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(str);
			// 过滤html标签
			result = matcher.replaceAll("");
		}
		return result;
	}
	/**
	 * 字符串中的空格替换
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		String result = "";
		if (str != null && !str.trim().equals("")) {
			String regex = "[\\s\\n\\t\\r]+";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(str);
			result = matcher.replaceAll("");
		}
		return result;
	}

	/**
	 * |â|Â |ï¸|â|Â|Â|Â|Â|Â|||||||
	 * 特殊空格替换
	 * @param str
	 * @return
	 */
	public static String replaceLabel(String str) {
		String result = "";
		if (str != null && !str.trim().equals("")) {
			try {
				str = replaceBlank(str);
				String label = new String("â\u0080\u0083Â ï¸\u008Fâ\u0080\u008BÂ\u0085Â\u0080Â\u0095Â\u0087Â\u0096"
						.getBytes("iso-8859-1"), "utf-8");
				String regex = "[" + label + "]+";
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(str);
				result = matcher.replaceAll("");
			} catch (UnsupportedEncodingException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		String str = "   ";
		System.out.println("|"+str+"|"+str.length());
		str = replaceBlank(str);
		System.out.println("|"+str+"|"+str.length());
		
		try {
			System.out.println(new String("â\u0080\u0083".getBytes("iso-8859-1"),"utf-8"));
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		}
	}
}
