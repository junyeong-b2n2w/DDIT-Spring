package com.spring.dao;

import java.sql.SQLException;

import com.spring.dto.MemberVO;

public interface MemberDAO {

	public void insertMember(MemberVO member) throws SQLException;
	
	public MemberVO selectMember(String id) throws SQLException;
	
}
