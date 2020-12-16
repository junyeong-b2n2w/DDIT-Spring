package kr.or.ddit.interceptor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.or.ddit.dto.AttachVO;
import kr.or.ddit.dto.MemberVO;

public class AttachFileLogInterceptor extends HandlerInterceptorAdapter {

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		Map<String, Object> modelMap = modelAndView.getModel();
		List<AttachVO> attachList = (List<AttachVO>) modelMap.get("attachList");

		MemberVO loginUser = (MemberVO) request.getSession().getAttribute("loginUser");

		String log = "[File]" // [File]등록자ID,등록자 이름,날짜,{파일명 파일명 ....},경로
				+ loginUser.getId() + "," 
				+ loginUser.getName() + ","
				+ new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "," + "{";

		if (attachList != null) {
			for (AttachVO attach : attachList) {
				log += attach.getFileName() + " ";
			}
		}

		log += "}," + attachList.get(0).getUploadPath();

		//log file 저장
		String savePath = "d:\\log";
		if (!new File(savePath).exists()) {
			new File(savePath).mkdirs();
		}

		String logFilePath = savePath + File.separator + "attach_file_log.txt";

		BufferedWriter out = new BufferedWriter(new FileWriter(logFilePath, true));

		out.write(log);
		out.newLine();
		out.close();
	}

}
