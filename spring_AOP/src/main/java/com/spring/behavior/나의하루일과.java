package com.spring.behavior;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class 나의하루일과 {

	public static void main(String[] args) {

		ApplicationContext ctx = new GenericXmlApplicationContext("classpath:application-context.xml");
		
		행동 behvior = (행동) ctx.getBean("action");
		
		behvior.밥먹기();
		behvior.정신수양();
		behvior.밥먹기();
		behvior.공부하기();
		behvior.놀기();
		behvior.데이트();
		behvior.운동();
		behvior.밥먹기();
		behvior.잠자기();
		
	}

}
