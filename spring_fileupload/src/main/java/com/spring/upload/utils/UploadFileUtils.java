package com.spring.upload.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;


public class UploadFileUtils {

	private static final Logger logger = LoggerFactory.getLogger(UploadFileUtils.class);

	// uploadFile 저장
	public static String uploadFile(String uploadPath, String originalName, byte[] fileData) throws Exception {
		
		//중복파일명 해결..
		UUID uid=UUID.randomUUID();
		String saveName=uid.toString().replace("-", "")+"_"+originalName;
		
		//저장 경로..
		String savePath=calcPath(uploadPath);
		
		//썸네일 이미지/파일...
		String thumbFileName=null;
		String formatName=originalName.substring(originalName.lastIndexOf(".")+1);
		
		
		//저장..
		File target=new File(savePath,saveName);
		FileCopyUtils.copy(fileData, target);
		
		logger.info("saved path : "+target.getAbsolutePath());
		
		if(MediaUtils.getMediaType(formatName)!=null){
			//썸네일 형태로 보여주기
			thumbFileName=makeThumbnail(uploadPath,savePath,saveName);
		}else{
			//텍스트 형태로 보여주기
			thumbFileName=makeIcon(uploadPath,savePath,saveName);
		}
	
		
		
		return thumbFileName;	
	}
	//아이콘 형태
	public static String makeIcon(String uploadPath, String path, String fileName) throws Exception{
		String iconName=path+File.separator+fileName;
		
		return iconName.substring(uploadPath.length()).
				replace(File.separatorChar, '/');
	}


	//썸네일 형태
	public static String makeThumbnail(String uploadPath,String savePath,String fileName)throws Exception{
		BufferedImage sourceImg=
				ImageIO.read(new File(savePath,fileName));
		
		BufferedImage destImg=Scalr.resize(sourceImg,Scalr.Method.AUTOMATIC,Scalr.Mode.FIT_TO_HEIGHT,100);
		
		String thumbnailName=savePath+File.separator+"s_"+fileName;
		File newFile=new File(thumbnailName);
		String formatName=fileName.substring(fileName.lastIndexOf(".")+1);
		ImageIO.write(destImg, formatName.toUpperCase(),newFile);
		
		return thumbnailName.substring(uploadPath.length()).
				replace(File.separatorChar,'/');
	}
	
	
	public static String calcPath(String uploadPath)throws Exception{
		Calendar cal=Calendar.getInstance();
		
		String yearPath=File.separator+cal.get(Calendar.YEAR);
		String monthPath=File.separator+new DecimalFormat("00").format(cal.get(Calendar.MONTH)+1);
		String datePath=File.separator+new DecimalFormat("00").format(cal.get(Calendar.DATE));
		
		String savePath=uploadPath+yearPath+monthPath+datePath;
		
		File file = new File(savePath);
		if(!file.exists()) {
			file.mkdirs();
		}
		
		logger.info(savePath);	
		
		return savePath;
	} 

}








