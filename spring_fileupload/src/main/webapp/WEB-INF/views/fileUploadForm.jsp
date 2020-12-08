<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SpringMVC file upload</title>
</head>
<body>
	<h1>Spring FileUpload</h1>
	<hr/>
	<h3>MultipartFile 荤侩</h3>
	<form action="multipartFile" method="post" enctype="multipart/form-data">
		力格 : <input type='text' name="title" /><br/>
		颇老 : <input type="file" name='file' /><br/>
		<input type="submit" value="傈价" />
	</form>
	
	<hr/>
	<h3>MultipartHttpServletRequest 荤侩</h3>
	<form action="multipartHttpServletRequest" method="post" enctype="multipart/form-data">
		力格 : <input type='text' name="title" /><br/>
		颇老 : <input type="file" name='file' /><br/>
		<input type="submit" value="傈价" />
	</form>
	
	<hr/>
	<h3>目盖靛按眉 荤侩</h3>
	<form action="commandObject" method="post" enctype="multipart/form-data">
		力格 : <input type='text' name="title" /><br/>
		颇老 : <input type="file" name='file' /><br/>
		<input type="submit" value="傈价" />
	</form>


</body>
</html>