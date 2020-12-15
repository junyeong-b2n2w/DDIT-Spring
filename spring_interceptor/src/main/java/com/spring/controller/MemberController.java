package com.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.dto.MemberVO;
import com.spring.service.MemberService;

import sun.print.resources.serviceui;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MemberService service;
	
	@RequestMapping(value="/regist" , method=RequestMethod.GET)
	public void registGET() {}
	
	@RequestMapping(value="/regist" , method=RequestMethod.POST)
	public String registPOST(MemberVO member, Model model)throws Exception {
		
		String url = "redirect:/main";
		service.regist(member);
		
		model.addAttribute("registMember",member);
		
		return url;
		
	}
}
