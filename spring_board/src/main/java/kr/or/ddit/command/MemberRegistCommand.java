package kr.or.ddit.command;

import java.util.Arrays;

import kr.or.ddit.dto.MemberVO;

public class MemberRegistCommand {

	private String id;
	private String pwd;
	private String name;
	private String email;
	private String[] phone;
	private String authority;
	private String picture;
	
	
	@Override
	public String toString() {
		return "MemberRegistCommand [id=" + id + ", pwd=" + pwd + ", name=" + name + ", email=" + email + ", phone="
				+ Arrays.toString(phone) + ", authority=" + authority + ", picture=" + picture + "]";
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getPwd() {
		return pwd;
	}


	public void setPwd(String pwd) {
		this.pwd = pwd;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String[] getPhone() {
		return phone;
	}


	public void setPhone(String[] phone) {
		this.phone = phone;
	}


	public String getAuthority() {
		return authority;
	}


	public void setAuthority(String authority) {
		this.authority = authority;
	}


	public String getPicture() {
		return picture;
	}


	public void setPicture(String picture) {
		this.picture = picture;
	}


	public MemberVO toMemberVO() {
		String phone = ""; 
		for (String data : this.phone) {
			phone += data;
		}
		
		MemberVO member = new MemberVO();
		member.setId(id);
      member.setPwd(pwd);
      member.setName(name);
      member.setEmail(email);
      member.setPhone(phone);
      member.setAuthority(authority);
      member.setPicture(picture);
		
      return member;
      
	}
}
