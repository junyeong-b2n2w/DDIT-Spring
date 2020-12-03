package kr.or.ddit.dao;

import java.sql.SQLException;
import java.util.List;

import kr.or.ddit.command.SearchCriteria;
import kr.or.ddit.dto.ReplyVO;

public interface ReplyDAO {

	List<ReplyVO> selectReplyListPage(int bno, SearchCriteria cri) throws SQLException;
	
	int selectSeqNextValue() throws SQLException;
	
	int countReply(int bno) throws SQLException;
	
	void insertReply(ReplyVO reply) throws SQLException;
	
	void updateReply(ReplyVO reply) throws SQLException;
	
	void deleteReply(int rno) throws SQLException;
	
}
