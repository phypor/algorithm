package org.kd.server.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
/**
 * 
 * @author bojieuhang@163.com
 *
 */
public class InetAddressUtil {
public static String getHostIP(){
	InetAddress netAddress = null;
	try {
		netAddress = InetAddress.getLocalHost();
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	String ip = netAddress.getHostAddress();
	return ip;
}
}
