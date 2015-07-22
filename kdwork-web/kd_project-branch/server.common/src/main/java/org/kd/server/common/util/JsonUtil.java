package org.kd.server.common.util;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	@SuppressWarnings("deprecation")
	public static String bean2Json(Object obj) throws IOException {  
        ObjectMapper mapper = new ObjectMapper();  
        StringWriter sw = new StringWriter();  
        JsonGenerator gen = new JsonFactory().createJsonGenerator(sw);  
        mapper.writeValue(gen, obj);  
        gen.close();  
        return sw.toString();  
    }  
  
    public static <T> T json2Bean(String jsonStr, Class<T> objClass)  
            throws JsonParseException, JsonMappingException, IOException {  
        ObjectMapper mapper = new ObjectMapper();  
        return mapper.readValue(jsonStr, objClass);  
    }  
}
