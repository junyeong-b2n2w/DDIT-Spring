package kr.or.ddit.dao;

import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.dto.MemberVO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/kr/or/ddit/context/root-context.xml")
@Transactional
public class TestMemberDAOImpl {

	@Autowired
	private MemberDAO memberDAO;
	
	@Test
	public void testSelectMember () throws SQLException{
		String id = "mimi";
		
		MemberVO member = memberDAO.selectMemberById(id);
		
		Assert.assertEquals(id,member.getId());
	}
	
	@Test
	@Rollback
	public void testInsertMember () throws SQLException{
		
		MemberVO member = new MemberVO();
		
		member.setId("jiji");
		member.setAddress("12312");
		member.setEmail("123!@@#!");
		member.setName("kkkkk");
		member.setPicture("1231231");
		
		member.setPwd("12312312");
		member.setPhone("123123");
		
		memberDAO.insertMember(member);
		
	}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
}
