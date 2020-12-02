package com.spring.test;

import java.sql.SQLException;
import java.util.Collection;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.spring.dto.MemberVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:com/spring/context/dataSource-context.xml")
public class TestSqlSession {

	@Autowired
	private SqlSession session;
	
	@Test
	public void sqlSessionTest() throws SQLException{
		Collection<String> mapNames=
			(Collection<String>)session.getConfiguration().getMappedStatementNames();
		
		Assert.assertTrue(mapNames.contains("Member-Mapper.selectMemberById"));
	}
	
	@Test
	public void selectMemberTest() throws SQLException{
		MemberVO member = session.selectOne("Member-Mapper.selectMemberById","mimi");
		Assert.assertEquals("mimi", member.getId());
	}
}
