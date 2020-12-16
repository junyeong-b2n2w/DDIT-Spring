package com.spring.dao;

import java.sql.SQLException;

import com.spring.dto.LoginUserLogVO;

public interface LoginLogDAO {

	public void insertLoginLog(LoginUserLogVO logVO) throws SQLException;
	public void deleteAllLoginLog() throws SQLException;
	
}
