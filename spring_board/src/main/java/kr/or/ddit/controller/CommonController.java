package kr.or.ddit.controller;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.dto.MenuVO;
import kr.or.ddit.exception.InvalidPasswordException;
import kr.or.ddit.exception.NotFoundIDException;
import kr.or.ddit.service.MemberService;
import kr.or.ddit.service.MenuService;

@Controller
public class CommonController {
   @Autowired
   private MenuService menuService;
   
   @Autowired
   private MemberService service;
   
   @RequestMapping("/common/loginForm")
   public void loginForm() {}

   @RequestMapping(value="/common/login", method=RequestMethod.POST)
   public String login(String id, String pwd, HttpSession session) throws SQLException{
      String url = "redirect:/index.do";
      
      try {
         service.login(id, pwd, session);
      }catch (NotFoundIDException | InvalidPasswordException e) {
         url = "redirect:/";
         session.setAttribute("msg", e.getMessage());
      }
      return url;
   }
   
   @RequestMapping(value="/common/logout", method=RequestMethod.GET)
	public String logout(HttpSession session) {
		String url = "redirect:/";
	   session.invalidate();
		return url;
	}
   
   
   @RequestMapping(value="/index", method=RequestMethod.GET)
   public ModelAndView index(@RequestParam(defaultValue="M000000") String mCode, ModelAndView mnv)
   								throws SQLException{
      String url = "/common/indexPage";
      
      List<MenuVO> menuList = menuService.getMainMenuList();
	  MenuVO menu = menuService.getMenuByMcode(mCode);
      
      mnv.addObject("menuList", menuList);
      mnv.addObject("menu", menu);
      mnv.setViewName(url);
      
      return mnv;
   }
   
   @RequestMapping(value="/common/subMenu", method=RequestMethod.GET)
   @ResponseBody
   public ResponseEntity<List<MenuVO>> subMenu(String mCode)  {

	   ResponseEntity<List<MenuVO>> entity = null;
	   
	   try {
		   List<MenuVO> subMenu = menuService.getSubMenuList(mCode);
		   entity = new ResponseEntity<List<MenuVO> >( subMenu, HttpStatus.OK);
	   } catch (SQLException e) {
		   entity = new ResponseEntity<List<MenuVO>>(HttpStatus.INTERNAL_SERVER_ERROR);	
	   }
		
	   return entity;
   }
   
   @RequestMapping(value="/main", method=RequestMethod.GET)
   public String main()  {
	   String url = "/common/main";
	   return url;
   }
   
}