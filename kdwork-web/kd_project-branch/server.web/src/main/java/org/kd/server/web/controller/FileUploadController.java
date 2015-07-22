package org.kd.server.web.controller;

import org.kd.server.beans.param.RetMsg;
import org.kd.server.common.exception.BusinessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FileUploadController {

	@ResponseBody
	@RequestMapping(value="/fileUpload",method=RequestMethod.POST)
	public Model fileUpload(Model model,@RequestParam(required=false,defaultValue = "0")int type,@RequestParam("file")MultipartFile[] files,HttpServletRequest request) throws Exception{
    	String realPath = System.getProperty("kdServer.root");  
    	
    	realPath = realPath.endsWith("/")||realPath.endsWith("\\")?realPath.substring(0, realPath.length()-1):realPath;
    	
    	realPath = realPath.substring(0, realPath.lastIndexOf("\\")!=-1?realPath.lastIndexOf("\\")+1:realPath.lastIndexOf("/")+1);
    	
    	String pathType = realPath.endsWith("/")?"/":"\\";
    	
    	realPath += "kd_upload"+pathType;
    	
    	String rootPath= "";
    	
    	switch (type) {
		case 1:
			rootPath = "appClient";
			break;
		default:
			rootPath = "publicFiles";
			break;
		}

    	realPath +=rootPath+pathType;
    	
    	List<String> fileList = new ArrayList<String>();
    	
    	if(files.length>0){
    		for(MultipartFile file:files){
    			if(file!=null&&file.getSize()>0){
			    	if(file.getSize()>200000000){
			    		   throw new BusinessException(RetMsg.paramError,"上传文件太大，不能超过200M");
			    	}else{
			               file.transferTo(new File(realPath+file.getOriginalFilename())); 
			    	}

			    	fileList.add(file.getOriginalFilename());
    			}
    		}
    	}
		
    	model.addAttribute("result",fileList);
    	return model;
    }
}
