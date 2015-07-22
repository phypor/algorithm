package org.kd.server.common.util;

import org.apache.log4j.Logger;

public class LogRecord {
	private static Logger info = Logger.getLogger("InfoLogger");  
    private static Logger error = Logger.getLogger("ErrorLogger");  
    public LogRecord(){}  
      
    /** 
     * 一般情况记录到/logs/infoLog.txt 
     */  
    public static void info(String infomation){  
        info.info(infomation);  
    }  
      
    /** 
     * 错误信息记录到/logs/errorLog.txt 
     */  
    public static void error(String infomation){  
        error.error(infomation);  
    }  
}
