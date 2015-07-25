package org.kd.server.common.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author bojiehuang@163.com
 *
 */
public class TempletParseUtil {
	/**
	 * 替换格式{$XXXXX} ------ XXXXX Map
	 * @param filePath
	 * @param map
	 * @return
	 * @throws IOException
	 */
	public   static String parseFromMap(String filePath,Map<String ,String> map)  throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(filePath), "UTF-8"));
		String str = null;
		String text = "";
		while ((str = br.readLine()) != null) {
			text += str;
		}
		br.close();
	        Set<String> set = map.keySet();
	        Iterator<String> iter = set.iterator();
	        while(iter.hasNext()){
	        	String key = iter.next();
	        	text=text.replace("{$"+key+"}", map.get(key));	
	        }
		return text;
	}
}
