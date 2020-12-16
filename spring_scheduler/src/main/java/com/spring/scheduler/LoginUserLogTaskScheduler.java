package com.spring.scheduler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.spring.dto.LoginUserLogVO;
import com.spring.service.LoginLogService;

//@Component
public class LoginUserLogTaskScheduler {

	//@Autowired
	public LoginLogService logService;

	//@Scheduled(cron="*/10 * * * * *")
	public void testScheduler() {
		try {
			String filePath = "/Users/junyoung/Desktop/login_user_log.txt";
			FileReader reader = new FileReader(filePath);
			
			BufferedReader in = new BufferedReader(reader);
			
			String textLine = null;
			
			List<LoginUserLogVO> logList = new ArrayList<LoginUserLogVO>();
			while((textLine= in.readLine())!= null) {
				String[] logData =textLine.split(",");
				
				LoginUserLogVO logVO = new LoginUserLogVO();
				logVO.setId(logData[1]);
				logVO.setPhone(logData[2]);
				logVO.setEmail(logData[3]);
				logVO.setIpAddress(logData[4]);
				
				try {
					logVO.setIndate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(logData[5]));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				logList.add(logVO);
			}
			
			
			try {
				logService.write(logList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			in.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
