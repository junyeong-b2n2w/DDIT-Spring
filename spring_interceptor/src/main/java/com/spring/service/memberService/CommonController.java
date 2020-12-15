package com.spring.service.memberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.dto.MemberVO;
import com.spring.service.MemberService;

@Controller
public class CommonController {

	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public void loginGET(String id, String pwd) throws Exception{
		
	}
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String loginPOST(MemberVO loginReq, HttpSession session, RedirectAttributes rttr) throws Exception{
		
		String url = "redirect:main";
		String msg = null;
		
		MemberVO member = memberService.getMember(loginReq.getId());
		
		System.out.println(member);
		
		if(member.getId()!=null) {
			if(loginReq.getPwd().equals(member.getPwd())) { // 로그인 성공 
				session.setAttribute("loginUser", member);
				msg="로그인 성공했습니다. ";
			}else { // 패스워드 불일치 
				msg="패스워드가 일치하지 않습니다.";
				url="redirect:login";
			}
			
		}else { // 존재하지 않는 아이디 ;
			msg ="존재하지 않는 아이디입니다.";
			url ="redirect:login";
		}
		
		System.out.println(url);
		rttr.addFlashAttribute("msg",msg);
		return url;
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,
						HttpSession session) throws Exception{
		
		String url = "redirect:/login";
		
		session.invalidate();
		
		return url;
	}
	
	@RequestMapping("/main")
	public void main() throws Exception{}
	
	
}
