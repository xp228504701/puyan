package com.py.controller;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.shiro.SecurityUtils;

import com.py.bean.Admin;
import com.py.util.Utils;

import org.apache.commons.codec.binary.Base64;

public class BaseController {
	// ================= 分页用的参数====================
	protected int pageNum = 1; // 当前页
	protected int startIndex = 0; // 开始索引
	protected int pageSize = 10; // 每页显示多少条记录

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		if (pageNum > 1) {
			startIndex = ((pageNum - 1) * pageSize);
		}else {
			startIndex = 0;
		}
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}	
	//分页排序参数
	protected String orderTable="id";//排序列
	protected String orderStyle="DESC";//排序方式
	
	public String getOrderTable() {
		return orderTable;
	}
	public void setOrderTable(String orderTable) {
		this.orderTable = orderTable;
	}
	public String getOrderStyle() {
		return orderStyle;
	}
	public void setOrderStyle(String orderStyle) {
		this.orderStyle = orderStyle;
	}
	/**
	 * 接受前台传入的分页参数进行设置
	 * @param request
	 */
	protected void pageParameter(HttpServletRequest request) {
		//获取page和pagesize的值
		String pageStr =  request.getParameter("pageNum");
		String pagesizeStr =  request.getParameter("pageSize");
		if (pageStr != null && !"".equals(pageStr))	setPageNum(Integer.parseInt(pageStr));
		else pageNum = 1; setPageNum(pageNum);
		if (pagesizeStr != null && !"".equals(pagesizeStr)) setPageSize(Integer.parseInt(pagesizeStr));
		else pageSize = 10; setPageSize(pageSize);
	}
	/**
	 * 接受前台传入的分页参数进行设置
	 * 设置每页数据条数 num
	 * @param request
	 */
	protected void pageParameter(HttpServletRequest request,int num) {
		//获取page和pagesize的值
		String pageStr =  request.getParameter("pageNum");
		String pagesizeStr =  request.getParameter("pageSize");
		if (pageStr != null && !"".equals(pageStr))	setPageNum(Integer.parseInt(pageStr));
		else pageNum = 1; setPageNum(pageNum);
		if (pagesizeStr != null && !"".equals(pagesizeStr)) setPageSize(Integer.parseInt(pagesizeStr));
		else pageSize = num; setPageSize(pageSize);
	}
	/**
	 * 接受前台传入的分页参数进行设置(LayerUI)
	 * @param request
	 */
	protected void LayerPage(HttpServletRequest request) {
		//获取page和pagesize的值
		String pageStr =  request.getParameter("page");
		String pagesizeStr =  request.getParameter("limit");
		String pageTable =  request.getParameter("field");
		String pageStyle =  request.getParameter("order");
		if (pageStr != null && !"".equals(pageStr))	setPageNum(Integer.parseInt(pageStr));
		else pageNum = 1; setPageNum(pageNum);
		if (pagesizeStr != null && !"".equals(pagesizeStr)) setPageSize(Integer.parseInt(pagesizeStr));
		else pageSize = 10; setPageSize(pageSize);
		if (pageTable != null && !"".equals(pageTable))	setOrderTable(pageTable);
		else orderTable = "id"; setOrderTable(orderTable);
		if (pageStyle != null && !"".equals(pageStyle))	setOrderStyle(pageStyle);
		else orderStyle = "DESC"; setOrderStyle(orderStyle);
	}
	
	/**
	 * 获取当前用户
	 * @return
	 */
	public Admin getCurrentUser(){
		return (Admin) SecurityUtils.getSubject().getSession().getAttribute("currentUser");
	}
	
	/**
	 * 上传单个文件-- 需要form表单的name 0success -1请选择文件 -2上传目录不存在
	 * -3上传目录没有写权限-4上传文件大小超过限制-5上传文件扩展名是不允许的扩展名 -6上传失败
	 * 
	 * @throws FileUploadException
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, Object> uploadFile(HttpServletRequest request, String name) throws FileUploadException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 文件保存目录路径
		String savePath = Utils.getProperties("file_upload_path");
		// 定义允许上传的文件扩展名,仅图片
		String fileExtStr = "gif,jpg,jpeg,png,bmp";
		// 最大文件大小
		long maxSize = Long.parseLong(Utils.getProperties("file_upload_size"));
		// 创建文件夹
		File saveDirFile = new File(savePath);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
		// 检查文件
		if (!ServletFileUpload.isMultipartContent(request)) {
			resultMap.put("state", "-1");
			return resultMap;
		}
		// 检查目录
		File uploadDir = new File(savePath);
		if (!uploadDir.isDirectory()) {
			resultMap.put("state", "-2");
			return resultMap;
		}
		// 检查目录写权限
		if (!uploadDir.canWrite()) {
			resultMap.put("state", "-3");
			return resultMap;
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		List<FileItem> items = upload.parseRequest(request);
		Iterator<FileItem> itr = items.iterator();
		while (itr.hasNext()) {
			FileItem item = (FileItem) itr.next();
			if (!item.isFormField()) {
				String formName = item.getFieldName();
				// 文件的名字与from表单不一致 不上传
				if (!formName.equals(name)) {
					item.delete();// 删除临时文件 继续循环
					continue;
				}
				// 检查文件大小
				if (item.getSize() > maxSize) {
					resultMap.put("state", "-4");
					return resultMap;
				}
				resultMap.put("size", item.getSize());
				// 检查扩展名
				String fileName = item.getName();
				String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
				if (fileExtStr.indexOf(fileExt) < 0) {
					resultMap.put("state", "-5");
					return resultMap;
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				String newFileName = sdf.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
				try {
					File uploadedFile = new File(savePath, newFileName);
					item.write(uploadedFile);
				} catch (Exception e) {
					resultMap.put("state", "6");
					return resultMap;
				}
				resultMap.put("link",newFileName);
			}
		}
		resultMap.put("state", "0");
		return resultMap;
	}
	
	
	
	
	/**
	 * 上传单个文件带监听-- 需要form表单的name 
	 * 0success -1请选择文件 -2上传目录不存在 -3上传目录没有写权限-4上传文件大小超过限制-5上传文件扩展名是不允许的扩展名 -6上传失败
	 * @throws FileUploadException 
	 */
	@SuppressWarnings("unchecked")
	protected Map<String,Object> uploadFileMonitor(HttpServletRequest request,String name) throws FileUploadException{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		// 文件保存目录路径
		String savePath = Utils.getProperties("file_upload_path");
		// 定义允许上传的文件扩展名
		String fileExtStr = "gif,jpg,jpeg,png,bmp,swf,flv,swf,flv,mp4,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb,doc,docx,xls,xlsx,ppt,pptx,htm,html,txt,zip,rar,gz,bz2,7z,pdf";
		// 最大文件大小
		long maxSize = Long.parseLong(Utils.getProperties("file_upload_size"));
		// 创建文件夹
		File saveDirFile = new File(savePath);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
		// 检查文件
		if (!ServletFileUpload.isMultipartContent(request)) {
			resultMap.put("state", "-1");
			return resultMap;
		}
		// 检查目录
		File uploadDir = new File(savePath);
		if (!uploadDir.isDirectory()) {
			resultMap.put("state", "-2");
			return resultMap;
		}
		// 检查目录写权限
		if (!uploadDir.canWrite()) {
			resultMap.put("state", "-3");
			return resultMap;
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setRepository(new File(savePath)); 
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		
		//文件上传进度的监听
		/*UploadProgressListener listener=new UploadProgressListener(SecurityUtils.getSubject().getSession());
		upload.setProgressListener(listener);*/
		List<FileItem> items = upload.parseRequest(request);
		Iterator<FileItem> itr = items.iterator();
		while (itr.hasNext()) {
			FileItem item = (FileItem) itr.next();
			if (!item.isFormField()) {
				String formName = item.getFieldName();
				//文件的名字与from表单不一致 不上传
				if(!formName.equals(name)){
					item.delete();//删除临时文件 继续循环
					continue;
				}
				// 检查文件大小
				if (item.getSize() > maxSize) {
					resultMap.put("state", "-4");
					return resultMap;
				}
				resultMap.put("size", item.getSize());
				// 检查扩展名
				String fileName = item.getName();
				String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
				if (fileExtStr.indexOf(fileExt) < 0) {
					resultMap.put("state", "-5");
					return resultMap;
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				String newFileName = sdf.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
				String uploadFile = savePath+newFileName;
				try {
					File uploadedFile = new File(uploadFile);
					item.write(uploadedFile);
				} catch (Exception e) {
					e.printStackTrace();
					resultMap.put("state", "-6");
					return resultMap;
				}
				resultMap.put("link", newFileName);
			}
		}
		resultMap.put("state", "0");
		return resultMap;
	}
	
	
	
	
	
	/**
	 * base64转化为图片
	 * @param pic
	 * @return
	 * @throws Exception
	 */
	
	public String analysisPic(String pic) throws Exception{
		String newFileName = "";
		Base64 base = new Base64();
        byte[] decode = base.decode(pic);
        
        // 图片输出路径
        String savePath = Utils.getProperties("file_upload_path");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		newFileName = sdf.format(new Date()) + "_" + new Random().nextInt(1000) + ".jpg";
		File path = new File(savePath);
		if(!path.exists()){
			path.mkdirs();
		}
		File file = new File(savePath,newFileName);
		file.createNewFile();
        // 定义图片输入流
        InputStream fin = new ByteArrayInputStream(decode);
        // 定义图片输出流
        FileOutputStream fout = new FileOutputStream(file);
        // 写文件
        byte[] b = new byte[1024];
        int length=0;
		while((length = fin.read(b))>0){
		    fout.write(b, 0, length);
		}
        fin.close();
        fout.close();
		return newFileName;
	}
}
