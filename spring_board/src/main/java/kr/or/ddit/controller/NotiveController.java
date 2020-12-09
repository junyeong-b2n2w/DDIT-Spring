package kr.or.ddit.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.or.ddit.command.SearchCriteria;
import kr.or.ddit.dto.NoticeVO;
import kr.or.ddit.service.NoticeService;

@Controller
@RequestMapping("/notice")
public class NotiveController {

	
	@Autowired
	private NoticeService noticeService;
	
	@RequestMapping("/main")
	public void main() {}
	
	@RequestMapping("/list")
	public ModelAndView list(SearchCriteria cri, ModelAndView mnv) throws SQLException{
		String url="notice/list";
		
		Map<String, Object> dataMap = noticeService.getNoticeList(cri);
		
		//안에 내용물을 꺼내서 하나나 담아줌 
		mnv.addAllObjects(dataMap);
		
		//맵형태로 넣어줌
		//mnv.addObject("dataMap", dataMap);
		
		mnv.setViewName(url);
		
		return mnv;
	}
	
	@RequestMapping("/registForm")
	public String registForm() {
		String url= "notice/regist";
		return url;
	}
	
	@RequestMapping(value="/regist", method=RequestMethod.POST)
	public void regist(NoticeVO notice, Model model, HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		
		
	
		noticeService.write(notice);
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("window.opener.location.reload(true);window.close();");
		out.println("</script>");
		out.close();
			
		
	}
	
	@RequestMapping(value="/detail", method=RequestMethod.GET)
    public ModelAndView detail(int nno,@RequestParam(defaultValue="")String from , ModelAndView mnv) throws SQLException, IOException {
      String url = "/notice/detail";
      
      NoticeVO notice = null;
      if (from.equals("modify")) {
    	  notice = noticeService.getNoticeModify(nno);
      }else {
      	  notice = noticeService.getNotice(nno);
      }
      mnv.addObject("notice",notice);
      mnv.setViewName(url);
      return mnv;
   }
	
	
	@RequestMapping(value="/modifyForm", method=RequestMethod.GET)
    public ModelAndView modifyForm(int nno, ModelAndView mnv) throws SQLException, IOException {
      String url = "/notice/modify";
      
      NoticeVO notice = noticeService.getNoticeModify(nno);
      
      mnv.addObject("notice",notice);
      mnv.setViewName(url);
      return mnv;
   }
	
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public void modify(NoticeVO notice, HttpServletResponse response)
			throws SQLException, IOException {
		
		noticeService.modify(notice);
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		out.println("<script>");
		out.println("window.opener.location.reload();");
		out.println("location.href='detail?nno="+notice.getNno()+"&from=modify';");
		out.println("</script>");
	}
	
	@RequestMapping(value="/remove", method=RequestMethod.POST)
	public void execute(int nno,  HttpServletResponse response)
			throws SQLException, IOException {
		
		noticeService.remove(nno);
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		out.println("<script>");
		out.println("window.opener.location.reload(true);");
		out.println("window.close();");
		out.println("</script>");
			
		
		
	}
	
	
}
