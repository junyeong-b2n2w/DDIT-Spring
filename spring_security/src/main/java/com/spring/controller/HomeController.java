package com.spring.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/common/loginForm", method = RequestMethod.GET)
	public void loginForm() {
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public void home() {
	}
	
	@RequestMapping(value = "/member", method = RequestMethod.GET)
	public void member() {
	}
	
	@RequestMapping(value = "/manager", method = RequestMethod.GET)
	public void manager() {
	}
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public void admin() {
	}
	
	@RequestMapping(value = "/security/accessDenied", method = RequestMethod.GET)
	public void denie() {
	}
}
