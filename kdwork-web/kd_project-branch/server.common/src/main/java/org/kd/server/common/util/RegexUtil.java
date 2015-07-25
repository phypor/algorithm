package org.kd.server.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author bojiehuang@163.com
 *
 */
public class RegexUtil {

	/**
	 * 判断邮箱正则
	 * @param str
	 * @return
	 */
	public static boolean isEmail(String str){
		String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(str);
		return matcher.matches();
	}
	
}
