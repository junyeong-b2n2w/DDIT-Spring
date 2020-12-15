package com.spring.interceptor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.spring.dto.MemberVO;

import jdk.jfr.events.FileWriteEvent;

public class LoginUserLogInterceptor extends HandlerInterceptorAdapter {

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		String viewName = modelAndView.getViewName();
		
		if(!viewName.equals("redirect:main")) return;
		
		MemberVO loginUser = (MemberVO) request.getSession().getAttribute("loginUser");
		
		if(loginUser==null) return;
		
		//로그인 정보를 스트링으로 저장.
		String tag = "[login:user]";
		String log = tag
				+ loginUser.getId() + ","
				+ loginUser.getPhone() + ","
				+ loginUser.getEmail() + ","
				+ request.getRemoteAddr() + ","
				+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		
		
		//로그 파일 생성.
		String savePath="/Users/junyoung/Desktop";
		File file = new File(savePath);
		if(!file.exists()) {
			file.mkdirs();
		}
		
		String logFilePath = savePath + File.separator + "login_user_log.txt";
				
		BufferedWriter out = new BufferedWriter(new FileWriter(logFilePath, true));
		
		//로그를 기록 
		out.write(log);
		out.newLine();
		
		out.close();
		
	}
}
