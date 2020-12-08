package com.spring.upload.controller;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.upload.command.FileUploadCommand;
import com.sun.org.apache.regexp.internal.REUtil;

@Controller
public class FileUploadController {

	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	
	private final long FILE_SIZE = 1024*1024*5;
	
	@RequestMapping(value ="/multipartHttpServletRequest", method = RequestMethod.POST)
	public String uploadByMultipartHttpServletRequest(MultipartHttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception{
		String title = request.getParameter("title");
		MultipartFile multi  = request.getFile("file");

		if(fileCheck(multi, FILE_SIZE, response)) return null;
		
		Exception e = saveFile(multi, request, model);
		
		if (e!=null) {
			e.printStackTrace();
			return null;
		}
		
		
		model.addAttribute("title", title);
	    
			
		return "fileUploaded";
		
	}
	
	
	
	@RequestMapping(value ="/multipartFile", method = RequestMethod.POST, produces="text/plain;charset=utf-8")
	public String uploadByMultipartFile(@RequestParam(defaultValue = "제목없음") String title,
										@RequestParam("file") MultipartFile multi,
										HttpServletRequest request, HttpServletResponse response,
										Model model) throws Exception{

		
		
		if(fileCheck(multi, FILE_SIZE, response)) return null;
		
		Exception e = saveFile(multi, request, model);
		
		if (e!=null) {
			e.printStackTrace();
			return null;
		}
		
		
		model.addAttribute("title", title);
	    
			
		return "fileUploaded";
	}
	
	@RequestMapping(value = "/commandObject", method = RequestMethod.POST)
	public String uploadByCommandObject(FileUploadCommand command,
			 HttpServletRequest request,HttpServletResponse response,
				Model model) throws Exception{
	
		MultipartFile multi = command.getFile();
		String title = command.getTitle();
		
		if(!fileCheck(multi, FILE_SIZE, response)) return null;
		
		Exception e = saveFile(multi, request, model);
		
		if(e!=null) {
			e.printStackTrace();
			return null;
		}
		
		model.addAttribute("title", title);
		
		
		return "fileUploaded";
	}

	
	
	
	private Exception saveFile(MultipartFile multi, HttpServletRequest request,Model model) {
		Exception exception = null;
		try {
			/*파일 저장 폴더 설정*/
			String uploadPath = request.getSession().getServletContext().getRealPath("resources/upload");
			/*파일명 중복 방지 설정*/
			String fileName = UUID.randomUUID().toString().replace("-","") + "$$" + multi.getOriginalFilename();
			
			/*파일 경로 확인 및 생성*/
			File file = new File(uploadPath, fileName);
			
			if( !file.exists()) {
				file.mkdirs();
			}
			
			/*파일저장 */
			multi.transferTo(file);
			
			model.addAttribute("originalFileName", multi.getOriginalFilename());
		    model.addAttribute("uploadFileName", file.getName());
		    model.addAttribute("uploadPath", file.getAbsolutePath());
		} catch (Exception e) {
			exception = e;
		}
		return exception;
	}
	
	private boolean fileCheck(MultipartFile multi, long size, HttpServletResponse response) throws IOException, ServletException{
		boolean result = true;
		String message = "";
		
		/*파일 유무확인 */
		if(!multi.isEmpty()) {
			message = "파일이없습니다!";
			result = false;
		}
		
		/*용량제한 5 확인 */
		if(multi.getSize() > 1024 * 1024 *5) {
			message = "용량초과입니다!";
			result = false;
		}
		
		if(!result) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('"+  message+"');</script>");
			out.println("<script>history.go(-1)</script>");
		}
		
		return result;
	}
	
}
