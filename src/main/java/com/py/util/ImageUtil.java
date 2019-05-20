package com.py.util;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import sun.misc.BASE64Decoder;

/**
 * 文件上传
 * @author Jerry
 *
 */
public class ImageUtil {
	
	private static final String PATH = "C:\\upload\\";
	
	//通过base64上传文件
	public static String uploadFile(String file) throws IOException{
		OutputStream out=null;
		String filepath = ImageUtil.createFolder();
		String path="";
		path =ImageUtil.PATH + filepath.replace("/","\\") + "\\";
		String newFileName = System.currentTimeMillis()+".png";//新生成的图片
		BASE64Decoder decoder = new BASE64Decoder();
        try {
        	byte[] bytes = decoder.decodeBuffer(file);
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
        return "/upload/" + filepath + "/" + newFileName;
	}
	
	
	//通过MultipartFile上传文件
    public static String uploadFile(MultipartFile files){
    	String filepath = ImageUtil.createFolder();
		String path="";
		path =ImageUtil.PATH + filepath.replace("/","\\") + "\\";
    	String newFileName = System.currentTimeMillis()+".png";//新生成的图片
    	File file = new File(path, newFileName);
    	try {
    		files.transferTo(file);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
    	return "/upload/"+filepath+"/"+newFileName;
	}
    
    //通过文件url获取文件
    public static String uploadFileByUrl(String fileUrl){
    	OutputStream out=null;
		String filepath = ImageUtil.createFolder();
		String path="";
		path =ImageUtil.PATH + filepath.replace("/","\\") + "\\";
		String newFileName = System.currentTimeMillis()+".png";//新生成的图片
		 URL url = null;
	        try {
	            url = new URL(fileUrl);
	            DataInputStream dataInputStream = new DataInputStream(url.openStream());
	 
	            FileOutputStream fileOutputStream = new FileOutputStream(new File(path,newFileName));
	            ByteArrayOutputStream output = new ByteArrayOutputStream();
	 
	            byte[] buffer = new byte[1024];
	            int length;
	 
	            while ((length = dataInputStream.read(buffer)) > 0) {
	                output.write(buffer, 0, length);
	            }
	            fileOutputStream.write(output.toByteArray());
	            dataInputStream.close();
	            fileOutputStream.close();
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return "/upload/"+filepath+"/"+newFileName;
    }
    
    private static String createFolder(){
    	String filePath = new SimpleDateFormat("yyyy-MM").format(new Date()) +"/" +new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        File fileDirDay = new File((PATH.replace("\\","/") + filePath));
        if(!fileDirDay.exists()){
        	fileDirDay.mkdirs();
        }
        return filePath;
    }
    
    
    

}
