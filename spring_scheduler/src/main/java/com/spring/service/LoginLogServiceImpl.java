package com.spring.service;

import java.util.List;

import com.spring.dao.LoginLogDAO;
import com.spring.dto.LoginUserLogVO;

public class LoginLogServiceImpl implements LoginLogService {

	private LoginLogDAO logDAO;
	public void setLogDAO(LoginLogDAO logDAO) {
		this.logDAO = logDAO;
	}
	
	@Override
	public void write(List<LoginUserLogVO> logList) throws Exception {
		logDAO.deleteAllLoginLog();
		
		for(LoginUserLogVO logVO : logList) {
			logDAO.insertLoginLog(logVO);
		}
		
	}
}
