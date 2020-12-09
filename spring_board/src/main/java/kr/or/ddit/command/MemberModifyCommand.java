package kr.or.ddit.command;

import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.dto.MemberVO;

public class MemberModifyCommand {

	private String id;
	private String pwd;
	private String name;
	private String email;
	private String phone;
	private MultipartFile picture;
	private String oldPicture;
	private String uploadPicture;
	@Override
	public String toString() {
		return "MemberModifyCommand [id=" + id + ", pwd=" + pwd + ", name=" + name + ", email=" + email + ", phone="
				+ phone + ", picture=" + picture + ", oldPicture=" + oldPicture + ", uploadPicture=" + uploadPicture
				+ "]";
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public MultipartFile getPicture() {
		return picture;
	}
	public void setPicture(MultipartFile picture) {
		this.picture = picture;
	}
	public String getOldPicture() {
		return oldPicture;
	}
	public void setOldPicture(String oldPicture) {
		this.oldPicture = oldPicture;
	}
	public String getUploadPicture() {
		return uploadPicture;
	}
	public void setUploadPicture(String uploadPicture) {
		this.uploadPicture = uploadPicture;
	}
	
	public MemberVO toParseMember() {
		MemberVO member = new MemberVO();
	    member.setId(id);
	    member.setPwd(pwd);
	    member.setName(name);
	    member.setEmail(email);
	    member.setPhone(phone.replace("-", ""));
	    
	    return member;
	}
	
}
