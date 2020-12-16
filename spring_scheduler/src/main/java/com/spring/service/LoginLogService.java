package com.spring.service;

import java.util.List;

import com.spring.dto.LoginUserLogVO;

public interface LoginLogService {

	public void write(List<LoginUserLogVO> logList) throws Exception;
}
