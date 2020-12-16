package com.spring.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.spring.dto.LoginUserLogVO;

public class LoginLogDAOImpl implements LoginLogDAO {

	
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public void insertLoginLog(LoginUserLogVO logVO) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "insert into login_log(id,phone,email,ipAddress, indate) values(?,?,?,?,?) ";
		
		try {
		
			
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, logVO.getId());
			pstmt.setString(2, logVO.getPhone());
			pstmt.setString(3, logVO.getEmail());
			pstmt.setString(4, logVO.getIpAddress());
			pstmt.setDate(5, new java.sql.Date(logVO.getIndate().getTime()));
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			throw e;
		}finally {
			if(pstmt != null) {
				pstmt.close();
			}
			if(conn != null) {
				conn.close();
			}
		}
		
		
	}

	@Override
	public void deleteAllLoginLog() throws SQLException {

		Connection conn = null;
		Statement stmt = null;
		String sql = " delete from login_log ";
		
		try {
		
			
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			
		} catch (Exception e) {
			throw e;
		}finally {
			if(stmt != null) {
				stmt.close();
			}
			if(conn != null) {
				conn.close();
			}
		}
		
		
	}

}
