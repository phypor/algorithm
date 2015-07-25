package org.kd.server.common.util;

import java.io.UnsupportedEncodingException;

public class StringUtil {

	public static String parseUTF_8To8859_1(String str)  {
		try {
			return new String((str).getBytes("UTF-8"), "8859_1");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
}
