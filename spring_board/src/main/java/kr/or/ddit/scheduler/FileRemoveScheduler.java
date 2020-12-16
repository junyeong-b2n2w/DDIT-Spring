package kr.or.ddit.scheduler;

import java.io.File;
import java.sql.SQLException;

import kr.or.ddit.dto.NoticeVO;
import kr.or.ddit.service.BoardService;
import kr.or.ddit.service.NoticeService;
import kr.or.ddit.service.PdsService;
import kr.or.ddit.utils.ExceptionLoggerHelper;

public class FileRemoveScheduler {
	
	private NoticeService noticeService;
	private BoardService boardService;
	private PdsService pdsService;
	
	
	public void setNoticeService(NoticeService noticeService) {
		this.noticeService = noticeService;
	}
	public void setBoardService(BoardService boardService) {
		this.boardService = boardService;
	}
	public void setPdsService(PdsService pdsService) {
		this.pdsService = pdsService;
	}

	private String filePath;
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public void fileRemove() {
		
		boolean existFile = false;
			
		File dir = new File(filePath);
		File[] files = dir.listFiles();
		if(files!=null) {
			for(File file : files) {
				existFile = false;
				try {
					if(noticeService.findNotice(file.getName())!=null) existFile=existFile||true;
					if(boardService.findBoard(file.getName())!=null) existFile=existFile||true;
					if(pdsService.findPds(file.getName())!=null) existFile=existFile||true;
					
					if(existFile) {
						System.out.println(file.getName()+" 파일은 존재합니다.");
						continue;
					}else {
						System.out.println(file.getName()+" 파일은 존재하지 않습니다. 삭제합니다.");
						file.delete();
					}
					
				}catch(SQLException e) {
					e.printStackTrace();					
				}
				 
			}
		}
	}
}











