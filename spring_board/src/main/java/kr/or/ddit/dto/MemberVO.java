package kr.or.ddit.dto;

import java.util.Date;

public class MemberVO {

	private String id; // 아이디

	private String pwd; // 비밀번호
	private String name; // 이름
	private String email; // 이메일
	private String phone; // 휴대전화번호
	private String picture; // 사진
	private String address; // 주소
	private Date regDate; // 입사일
	private String register; // 등록자
	private String authority; // 권한
	private int enabled; // 0퇴사 1재직 2휴직
	
	public MemberVO() {}
	
	public MemberVO(String id, String pwd, String name, String email, String phone) {
		super();
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.email = email;
		this.phone = phone;
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
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public String getRegister() {
		return register;
	}
	public void setRegister(String register) {
		this.register = register;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	@Override
	public String toString() {
		return "MemberVO [id=" + id + ", pwd=" + pwd + ", name=" + name + ", email=" + email + ", phone=" + phone
				+ ", picture=" + picture + ", address=" + address + ", regDate=" + regDate + ", register=" + register
				+ ", authority=" + authority + ", enabled=" + enabled + "]";
	}

	

}
