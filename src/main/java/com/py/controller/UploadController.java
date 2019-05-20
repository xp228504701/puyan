package com.py.controller;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.py.util.CommonUtil;


@Controller
@RequestMapping("upload")
public class UploadController extends BaseController {
	
	/**
	 * 异步上传文件带监听
	 * @param request
	 * @return
	 * @throws FileUploadException 
	 */
	@RequestMapping("/uploadFile")
	@ResponseBody
	public Map<String,Object> uploadFileMonitor(HttpServletRequest request,@RequestParam("videoImage")MultipartFile file) throws FileUploadException{
		String name = request.getParameter("name");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String string = CommonUtil.saveFile(file);
			resultMap.put("state", 0);
			resultMap.put("link", string);
			
		} catch (Exception e) {
			resultMap.put("state", -5);
			e.printStackTrace();
		} 
		return resultMap;
	}
	
	
	/**
	 * 异步上传文件带监听
	 * @param request
	 * @return
	 * @throws FileUploadException 
	 */
	@RequestMapping("/uploadFileMonitor")
	@ResponseBody
	public Map<String,Object> uploadFileMonitor1(HttpServletRequest request) throws FileUploadException{
		String name=request.getParameter("name");
		return uploadFileMonitor(request,name);
	}
	
	
	/**
	 * 获取文件上传进度
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/getFileUploadSchedule")
	@ResponseBody
	public Map<String, Object> getFileUploadSchedule(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Session session = SecurityUtils.getSubject().getSession();
		Map<String, Object> map = new HashMap<String, Object>();
		Object percentage = session.getAttribute("percentage");
		if(percentage == null){
			map.put("percentage", "0%");
		}else{
			map.put("percentage", percentage);  
		} 
		if(null != percentage && "100%".equals(percentage.toString())){
			session.setAttribute("percentage", null);
		}
		return map;
	}
	
}
