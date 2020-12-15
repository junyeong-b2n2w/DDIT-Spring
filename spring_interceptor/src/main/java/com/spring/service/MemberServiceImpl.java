package com.spring.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dao.MemberDAO;
import com.spring.dto.MemberVO;

@Service("memberService")
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDAO memberDAO;
	
	@Override
	public MemberVO getMember(String id) throws SQLException {
		MemberVO member =  memberDAO.selectMember(id);

		return member;
	}

	@Override
	public void regist(MemberVO member) throws SQLException {

		memberDAO.insertMember(member);
	}

	
	
}
