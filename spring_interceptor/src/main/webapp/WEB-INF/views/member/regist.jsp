<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원등록</title>
</head>
<body>
	<form action="regist" method="post">
		아이디 : <input type='text' name="id" /><br/>
		패스워드 : <input type='password' name="pwd" /><br/>
		이메일 : <input type='email' name="email" /><br/>
		전화번호 : <input type='text' name="phone" /><br/>
		
		<input type="submit" value="등 록" />
	</form>
</body>
</html>