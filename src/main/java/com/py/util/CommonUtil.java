package com.py.util;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import sun.misc.BASE64Decoder;

public class CommonUtil {
	public static ConcurrentHashMap<String, Object> MSG_MAP = new ConcurrentHashMap<String, Object>();
	public static long MOBILECODEEXPIRETIME=15*60*1000;


	/**获取客户端真实IP地址*/  
	public static String getIpAddr(HttpServletRequest request) {  
	       String ip = request.getHeader("x-forwarded-for");  
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	           ip = request.getHeader("Proxy-Client-IP");  
	       }  
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	           ip = request.getHeader("WL-Proxy-Client-IP");  
	       }  
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	           ip = request.getRemoteAddr();  
	       }  
	       return ip;  
	  }




	/****
	 * 验证手机验证码***/
	public static String verifyMobileCode(String mobile,String mobileCode){
		if(mobile==null||mobileCode==null){
			return "请输入手机号和手机验证码";
		}
		SMSBean smsBean = (SMSBean) MSG_MAP.get(mobile);
		if(smsBean==null){
			return "请获取验证码";
		}
		if(!smsBean.getValue().equals(mobileCode)){
			return "验证码错误";
		}
		if( new Date().getTime()-smsBean.getSendDate().getTime() > MOBILECODEEXPIRETIME){
			return "验证码过期,请重新获取";
		}
		MSG_MAP.remove(mobile);
		return null;

	}



	public static String saveFile(MultipartFile pic) throws IllegalStateException, IOException{
		Date date = new Date();
		String path = "C:\\upload\\file\\";
		String filePath = DateUtil.formatTime(date, "yyyy-MM") +"/" +DateUtil.formatTime(date, "yyyy-MM-dd");
		File fileDirDay = new File((path.replace("\\","/") + filePath));
		if(!fileDirDay.exists()){
			fileDirDay.mkdirs();
		}
		path += filePath.replace("/","\\") + "\\";
		String originalFileName = pic.getOriginalFilename();
		// 新的图片名称
		String newFileName = UUIDUtils.getUUID()+ originalFileName.substring(originalFileName.lastIndexOf("."));

		// 临时的图片
		//File oldFile = new File(path+"oldFile"+originalFileName.substring(originalFileName.lastIndexOf(".")));
		//pic.transferTo(oldFile);

		// 新的图片
		File newFile = new File(path + newFileName);

		//ImageZipUtil.zipImageFile(oldFile,newFile,425,638,0.7f);
		// 将内存中的数据写入磁盘
        pic.transferTo(newFile);
		//oldFile.delete();

		return "/file/" + filePath + "/" + newFileName;
	}
	/**
	 * 
	 * 保存文件
	 * @param pic
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public static String saveFileNotTime(MultipartFile pic) throws IllegalStateException, IOException{
		//Date date = new Date();
		String path = "C:\\upload\\app\\";
		File fileDirDay = new File(path.replace("\\","/"));
		if(!fileDirDay.exists()){
			fileDirDay.mkdirs();
		}
		String originalFileName = pic.getOriginalFilename();
		
		File newFile = new File(path + originalFileName);
		// 将内存中的数据写入磁盘
		pic.transferTo(newFile);
		return "/pic/app/" + originalFileName;
	}
	
	public static String imgStr(String imgStr) throws IOException{
        OutputStream out=null;
        
        Date date = new Date();
        String path = "C:\\upload\\user\\";
        String filePath = DateUtil.formatTime(date, "yyyy-MM") +"/" +DateUtil.formatTime(date, "yyyy-MM-dd");
        File fileDirDay = new File((path.replace("\\","/") + filePath));
        if(!fileDirDay.exists()){
        	fileDirDay.mkdirs();
        }
        path += filePath.replace("/","\\") + "\\";
        String newFileName = UUIDUtils.getUUID()+".png";//新生成的图片  
        
        BASE64Decoder decoder = new BASE64Decoder();
        try {
        	byte[] bytes = decoder.decodeBuffer(imgStr);
            for(int i = 0; i < bytes.length; ++i){
    			if (bytes[i] < 0) {
    				bytes[i] += 256;
    			}
    		}
            out = new FileOutputStream(path + newFileName);
            out.write(bytes);  
            out.flush();
            out.close();
		} finally {
			if(out!=null)out.close();
		}
        return "/file/" + filePath + "/" + newFileName;
	}
	
	public static String videoStr(String imgStr) throws IOException{
        OutputStream out=null;
        
        Date date = new Date();
        String path = "C:\\upload\\";
        String filePath = DateUtil.formatTime(date, "yyyy-MM") +"/" +DateUtil.formatTime(date, "yyyy-MM-dd");
        File fileDirDay = new File((path.replace("\\","/") + filePath));
        if(!fileDirDay.exists()){
        	fileDirDay.mkdirs();
        }
        path += filePath.replace("/","\\") + "\\";
        String newFileName = UUIDUtils.getUUID()+".avi";//新生成的视频
        
        BASE64Decoder decoder = new BASE64Decoder();
        try {
        	byte[] bytes = decoder.decodeBuffer(imgStr);
            for(int i = 0; i < bytes.length; ++i){
    			if (bytes[i] < 0) {
    				bytes[i] += 256;
    			}
    		}
            out = new FileOutputStream(path + newFileName);
            out.write(bytes);  
            out.flush();
            out.close();
		} finally {
			if(out!=null)out.close();
		}
        return "/pic/" + filePath + "/" + newFileName;
	}

	
	/**
     * 将分转换为元 如：1 分= 0.01元
     * @param price
     * @return
     */
    public static String fenToYuan(String price){
        BigDecimal sourcePrice = new BigDecimal(price);//原始价格 单位元
        BigDecimal b2 = new BigDecimal("100");//将元换算为分的单位 100
        String tfee=Double.toString(sourcePrice.divide(b2).doubleValue());
        // 订单金额
        return tfee;
    }
	 
	
}
