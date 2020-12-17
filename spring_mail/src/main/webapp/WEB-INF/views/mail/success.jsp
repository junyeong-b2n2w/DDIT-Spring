<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<ul>
	<li>받는사람 : ${result.reciver }</li>
	<li>보내는 사람 : ${result.sender }</li>
	<li>제목 : ${result.title }</li>
	<li>내용 : ${result.content }</li>
	<li>첨부파일 : ${result.uploadPath }</li>
</ul>
</body>
</html>