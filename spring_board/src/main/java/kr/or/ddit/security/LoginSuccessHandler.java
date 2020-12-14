package kr.or.ddit.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import kr.or.ddit.dto.MemberVO;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
										Authentication authentication) throws ServletException, IOException {

		User user = (User) authentication.getDetails();

		MemberVO loginUser = user.getMemberVO();
		HttpSession session = request.getSession();
		session.setAttribute("loginUser", loginUser);
		//session.setMaxInactiveInterval(10);
		
		// cookie 발행
		String rememberMe = request.getParameter("rememberMe");
		String id = request.getParameter("id");
		
		if (rememberMe != null && rememberMe.equals("check")) {
			Cookie cookie = new Cookie("loginUser", id);
			cookie.setMaxAge(3 * 24 * 60 * 60);
			cookie.setPath("/");
			response.addCookie(cookie);
		}

		super.onAuthenticationSuccess(request, response, authentication);

	}

}
