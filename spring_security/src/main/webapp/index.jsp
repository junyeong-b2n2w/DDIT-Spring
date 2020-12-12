<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<a href="<c:url value='/home' />">/home</a>
<a href="<c:url value='/member' />">/member</a>
<a href="<c:url value='/manager' />">/manager</a>
<a href="<c:url value='/admin' />">/admin</a>

<a href="<%=request.getContextPath()%>/common/loginForm">login </a>
<a href="<%=request.getContextPath()%>/common/logout">logout </a>
</body>

<script>
	if('${message}')alert('${message}');

</script>
</html>
