package kr.or.ddit.controller;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.or.ddit.dto.MenuVO;
import kr.or.ddit.service.MemberService;
import kr.or.ddit.service.MenuService;

@Controller
public class CommonController {
	
	@Autowired
	private MemberService service;
	
	@Autowired
	private MenuService menuService;
	
	@RequestMapping("/common/loginForm")
	public String loginForm(@RequestParam(defaultValue="0")String error,HttpServletResponse response) throws Exception {
		String url = "common/loginForm";
		
		if(error.equals("1")) {
			response.setStatus(302);
		}
		return url;
	}
	
	@RequestMapping("/security/accessDenied")
	public String accessDenied(HttpServletResponse response) {
		String url="security/accessDenied.open";
		
		response.setStatus(302);
		
		return url;		
	}
	
	@RequestMapping("/common/loginTimeOut")
	public void loginTimeOut(HttpServletRequest request, HttpServletResponse response)throws Exception{		
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		out.println("<script>");
		out.println("alert('세션이 만료되었습니다.\\n다시 로그인 하세요!');");
		out.println("location.href='"+request.getContextPath()+"';");
		out.println("</script>");
		out.close();
	}
	
	@RequestMapping("/common/loginExpired")
	public void loginExpired(HttpServletRequest request, HttpServletResponse response)throws Exception{
				
			
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		out.println("<script>");
		out.println("alert('중복 로그인이 확인되었습니다.\\n다시 로그인하면 다른 장치에서 로그인은 취소됩니다.!');");
		out.println("location.href='"+request.getContextPath()+"';");
		out.println("</script>");
		out.close();
	}
	
	/*@RequestMapping(value="/common/login",method=RequestMethod.POST)
	public String login(String id, String pwd, HttpSession session) throws SQLException{
		String url="redirect:/index.do";
		
		try {
			service.login(id, pwd, session);					
		} catch (NotFoundIDException | InvalidPasswordException e) {
			url="redirect:/";
			session.setAttribute("msg", e.getMessage());
		} 
		
		return url;
	}
	@RequestMapping(value="/common/logout",method=RequestMethod.GET)
	public String logout(HttpSession session){
		String url="redirect:/";
		session.invalidate();
		
		return url;
	}*/
	
	@RequestMapping(value="/main")
	public String main() {
		String url="/common/main.open";
		return url;
	}
	
	
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public ModelAndView index(@RequestParam(defaultValue="M000000")String mCode,ModelAndView mnv)
						throws SQLException{
		
		String url="/common/indexPage.page";
				
		List<MenuVO> menuList = menuService.getMainMenuList();
		MenuVO menu = menuService.getMenuByMcode(mCode);
		
		mnv.addObject("menuList",menuList);
		mnv.addObject("menu",menu);
		mnv.setViewName(url);
		
		return mnv;
	}
	
	@RequestMapping(value="/common/subMenu",method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<MenuVO>> subMenu(String mCode){
		
		ResponseEntity<List<MenuVO>> entity=null;
		
		try {
			List<MenuVO> subMenu =menuService.getSubMenuList(mCode);
			entity  = new ResponseEntity<List<MenuVO>>(subMenu,HttpStatus.OK);
		}catch(SQLException e) {
			entity = new ResponseEntity<List<MenuVO>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return entity;
		
	}
	
	
}


