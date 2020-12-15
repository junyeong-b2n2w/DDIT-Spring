<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>메인입니다.</h1>
	
	<button id="regist" onclick="location.href='member/regist';">회원등록</button><br/>
	<button id="logout">로그아웃</button>
	
	
	<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
	<script>
		var msg = "${msg}";
		if(msg!=""){
			alert(msg);
		}
		
		$('button#logout').on('click',function(){
			location.href="logout";
		});
	</script>
</body>
</html>




