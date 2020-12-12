<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="message" value="안녕하세요" scope="session" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>home page</h1>
	<h3>${loginUser.name } 님 반갑습니다</h3>
	<br>
	<a href="<c:url value='/'/>">[/index로 가]</a>
</body>
</html>