package org.kd.server.web.controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.kd.server.beans.entity.Image;
import org.kd.server.beans.entity.ImageType;
import org.kd.server.beans.param.ImageConfig;
import org.kd.server.beans.param.PublicInfoConfig;
import org.kd.server.beans.param.RetMsg;
import org.kd.server.common.exception.BusinessException;
import org.kd.server.common.util.DateUtil;
import org.kd.server.service.ImageService;
import org.kd.server.service.ImageTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/*
 * zmy
 * 
 * 后台图片管理控制器
 */
@Controller
public class ImageManagerController {
	@Resource(name="imageService")
	private ImageService imageService;
	
	@Resource(name="imageTypeService")
	private ImageTypeService imageTypeService;
	
	//图片上传接口
	//@RequiresRoles("administrator")
    @RequestMapping(value="/image/uploadManageImage",method=RequestMethod.POST)
    public String uploadImage(@RequestParam("file")MultipartFile file,Image image,String sTime,String eTime,Long[] imgType,Model model) throws Exception{
    	
    	String realPath = System.getProperty("kdServer.root");  
    	realPath = realPath.endsWith("/")||realPath.endsWith("\\")?realPath.substring(0, realPath.length()-1):realPath;
    	
    	realPath = realPath.substring(0, realPath.lastIndexOf("\\")!=-1?realPath.lastIndexOf("\\")+1:realPath.lastIndexOf("/")+1);
    	realPath +=realPath.endsWith("/")?"kd_upload/backstageUploadImage/":"kd_upload\\backstageUploadImage\\";
    	
    	String newFileName = file.getOriginalFilename();
    	
    	if(newFileName.split("\\.").length==2){
    		newFileName = newFileName.substring(newFileName.lastIndexOf("."));
    		newFileName = DateUtil.formatDateByString(new Date(), "yyyyMMddhhmmssSSS")+newFileName;
    	}
    	
    	if(!("image/jpeg".equalsIgnoreCase(file.getContentType())||
            	"image/bmp".equalsIgnoreCase(file.getContentType())||
            	"image/gif".equalsIgnoreCase(file.getContentType())||
            	"image/png".equalsIgnoreCase(file.getContentType())||
            	"image/jpeg".equalsIgnoreCase(file.getContentType()))||file.getSize()>20000000){
    		
    		   throw new BusinessException(RetMsg.paramError,"上传文件格式不对，或超出大小(200M)");
    	}else{
               file.transferTo(new File(realPath+newFileName)); 
    	}

        image.setBeginTime(new SimpleDateFormat("yyyy-MM-dd").parse(sTime));
        image.setEndTime(new SimpleDateFormat("yyyy-MM-dd").parse(eTime));
        image.setImageType(new ImageType(imgType[0]));
        image.setName(newFileName);
        image.setEnable(true);
        image.setUrl("/upload/backstageUploadImage/"+newFileName);
        imageService.save(image);

        List<ImageType> imageTypeList = imageTypeService.getAll(ImageType.class);
    	
    	model.addAttribute("imageTypeList", imageTypeList);
    	model.addAttribute("uploadSuccImg",newFileName);
    	return "imageManager/imageUpload";
    }
    
    //进入图片上传页面方法
	 @RequiresRoles("administrator")
    @RequestMapping("/image/uploadImagePage")
    public String uploadImagePage(Model model) throws Exception{
    	List<ImageType> imageTypeList = imageTypeService.getAll(ImageType.class);
    	
    	model.addAttribute("imageTypeList", imageTypeList);
    	return "imageManager/imageUpload";
    }
    
    //删除上传图片接口
    @ResponseBody
    @RequiresRoles("administrator")
    @RequestMapping(value="/image/delImage",method=RequestMethod.POST)
    public boolean delImage(Long id) throws Exception{
    	imageService.delete(Image.class, id);
    	return true;
    }
    
    //进入展示上传图片列表页面
  @RequiresRoles("administrator")
    @RequestMapping("/image/uploadImageListPage")
    public String uploadImageListPage(Model model) throws Exception{
    	List<Image> imageList = imageService.getAll(Image.class);

    	model.addAttribute("imageList", imageList);
    	return "imageManager/imageList";
    }
    
    public void Log(String value) {
		System.out.println(value);
		File file = new File("/mnt/webapps/kd/logs/KDServerAllLog.log");
		FileWriter fw = null;
		BufferedWriter writer = null;
		try {
			if (file.exists()) {
				fw = new FileWriter(file, true);
				writer = new BufferedWriter(fw);
				writer.write(value);
				writer.newLine();
				writer.flush();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			throw new IllegalArgumentException("�ļ���ɾ��");
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
				if (fw != null) {
					fw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
    
    @ResponseBody
    @RequestMapping(value="/image/uploadImage",method=RequestMethod.POST)
    public Model uploadHeadImg(Model model,@RequestParam(required=false,defaultValue = "0")int type,@RequestParam("file")MultipartFile[] files,HttpServletRequest request) throws Exception{
    	String realPath = System.getProperty("kdServer.root");  
    	Log("图片上传");
    	realPath = realPath.endsWith("/")||realPath.endsWith("\\")?realPath.substring(0, realPath.length()-1):realPath;
    	
    	realPath = realPath.substring(0, realPath.lastIndexOf("\\")!=-1?realPath.lastIndexOf("\\")+1:realPath.lastIndexOf("/")+1);
    	
    	String pathType = realPath.endsWith("/")?"/":"\\";
    	
    	realPath += "kd_upload"+pathType;
    	
    	String rootPath = "";
    	switch (type) {
		case 1:
			rootPath = "userHeadImage";
			break;
        case 2:
        	rootPath ="backstageUploadImage";
			break;
        case 3:
        	rootPath = "businessLicence";
			break;
		default:
			rootPath = "publicImage";
			break;
		}
    	realPath +=rootPath+pathType;
        Log("图片上传\n类型为 : "+realPath);

    	List<String> imgList = new ArrayList<String>();
    	
    	if(files.length>0){
    		for(MultipartFile file:files){
    			if(file!=null&&file.getSize()>0){
			    	String newFileName = file.getOriginalFilename();
			    	
			    	if(newFileName.split("\\.").length==2){
			    		newFileName = newFileName.substring(newFileName.lastIndexOf("."));
			    		newFileName = DateUtil.formatDateByString(new Date(), "yyyyMMddhhmmssSSS")+newFileName;
			    	}
			    	
			    	if(!("image/jpeg".equalsIgnoreCase(file.getContentType())||
			            	"image/bmp".equalsIgnoreCase(file.getContentType())||
			            	"image/gif".equalsIgnoreCase(file.getContentType())||
			            	"image/png".equalsIgnoreCase(file.getContentType()))||file.getSize()>20000000){
			    		
			    		   throw new BusinessException(RetMsg.paramError,"上传文件格式不对，或超出大小(200M)");
			    	}else{
			               file.transferTo(new File(realPath+newFileName)); 
			    	}
			    	String imgUrl = "/upload/"+rootPath+"/"+newFileName;
			    	Log(imgUrl);
			    	imgList.add(imgUrl);
    			}
    		}
    	}else{
    		Log("文件上传大小小于0");
    	}
		
    	model.addAttribute("result",imgList);
    	return model;
    }
    
    @ResponseBody
    @RequestMapping("/image/findByType")
    public Map<String, Object> findByType(@RequestParam(required=false,defaultValue = "1")int type,@RequestParam(required=false,defaultValue = "1") int page,@RequestParam(required=false,defaultValue = "10") int pageSize) throws Exception{
    	Map<String, Object> map = new HashMap<String, Object>();
    	
    	List<Image> imageList = null;
    	switch(type){
    	case 1:
    		imageList = imageService.findByTypeEnable(ImageConfig.SYGT, true, page, pageSize); //首页滚图
    		break;
    	case 2:
    		imageList = imageService.findByTypeEnable(ImageConfig.KXGT, true, page, pageSize); //快讯滚图
    		break;
    	case 3:
    		imageList = imageService.findByTypeEnable(ImageConfig.XXGT, true, page, pageSize); //消息滚图
    		break;
    		default:
    			break;
    	}
    	map.put("result", imageList);
    	map.put("status", PublicInfoConfig.SUCCESS_CODE);
    	map.put("msg", PublicInfoConfig.SUCCESS_MSG);

    	return map;
    }
    
}
