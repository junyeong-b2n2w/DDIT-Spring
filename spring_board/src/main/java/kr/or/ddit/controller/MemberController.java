package kr.or.ddit.controller;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.or.ddit.command.SearchCriteria;
import kr.or.ddit.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	@RequestMapping("/main")
	public void main() {}

	@RequestMapping("/list")
	public ModelAndView list(SearchCriteria cri, ModelAndView mnv) throws SQLException{
		String url="member/list";
		
		Map<String, Object> dataMap = memberService.getSearchMemberList(cri);
		
		//안에 내용물을 꺼내서 하나나 담아줌 
		mnv.addAllObjects(dataMap);
		
		//맵형태로 넣어줌
		//mnv.addObject("dataMao", dataMap);
		
		mnv.setViewName(url);
		
		return mnv;
	}
	
}
