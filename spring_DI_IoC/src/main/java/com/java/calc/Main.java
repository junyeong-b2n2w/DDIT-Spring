package com.java.calc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class Main {

	
	public static void main(String[] args) {
		 //Calculator cal = new Calculator();
		 
		 ApplicationContext ctx = new GenericXmlApplicationContext("classpath:com/spring/context/application-context.xml");
		 
		 
		 Calculator cal = ctx.getBean("cal", Calculator.class);
		 System.out.println(cal.sum(4, 5));
	}

}
