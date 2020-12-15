package com.spring.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.dto.MemberVO;

@Repository
public class MemberDAOImpl implements MemberDAO{

	
	@Autowired
	private DataSource dataSource;

	@Override
	public void insertMember(MemberVO member) throws SQLException {

		Connection conn =null;
		
		PreparedStatement pstmt = null;
		
		try {
			conn= dataSource.getConnection();
			
			String sql = "insert into member (id,pwd,phone,email)"
					+ " value(?,?,?,?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPwd());
			pstmt.setString(3, member.getPhone());
			pstmt.setString(4, member.getEmail());
			
			
			pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			throw e;
			
		}finally {
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();
		}
		
		
	}

	@Override
	public MemberVO selectMember(String id) throws SQLException {
		
		Connection conn =null;
		
		Statement stmt = null;
		ResultSet rs = null;
		
		String sql = null;
		
		MemberVO member = null;
		
		try {
			conn= dataSource.getConnection();
			
			sql = "select * from member where id = '" + id + "'";
			stmt = conn.createStatement();
			rs=stmt.executeQuery(sql);
			
			if(rs.next()) {
				member = new MemberVO();
				member.setEmail(rs.getString("email"));
				member.setId(rs.getString("id"));
				member.setPhone(rs.getString("phone"));
				member.setPwd(rs.getString("pwd"));
			}
			
		} catch (SQLException e) {
			throw e;
			
		}finally {
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
			if(conn != null) conn.close();
		}
		
		
		
		return member;
	}

	
	
}
