package com.spring.mail;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.tomcat.jni.Mmap;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.spring.command.SendMailCommand;

public class MimeAttachNotifier {
	private JavaMailSender mailSender;
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public void sendMail(SendMailCommand mail, String uploadPath) throws Exception{
		//메서지 생성
		MimeMessage message = mailSender.createMimeMessage();
		
		//메서지 작성 핼퍼
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "utf-8");
		
		//받는사람
		messageHelper.setTo(new InternetAddress(mail.getReceiver()));
		//보내는사람
		messageHelper.setFrom(mail.getSender(), "운영자");
		//제목
		messageHelper.setSubject(mail.getTitle());
		//내용
		messageHelper.setText(mail.getContent(),true);
		
		//첨부파일
		if(mail.getFile() != null && !mail.getFile().isEmpty()) {
			String fileName = mail.getFile().getOriginalFilename();
			DataSource dataSource = new FileDataSource(uploadPath+"/"+fileName);
			
			messageHelper.addAttachment(MimeUtility.encodeText(fileName,"utf-8","B"), dataSource);
			
		}
		
		mailSender.send(message);
		
	}
}
