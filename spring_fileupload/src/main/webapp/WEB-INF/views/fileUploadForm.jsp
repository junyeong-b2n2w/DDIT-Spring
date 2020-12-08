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
	<h3>MultipartFile ���</h3>
	<form action="multipartFile" method="post" enctype="multipart/form-data">
		���� : <input type='text' name="title" /><br/>
		���� : <input type="file" name='file' /><br/>
		<input type="submit" value="����" />
	</form>
	
	<hr/>
	<h3>MultipartHttpServletRequest ���</h3>
	<form action="multipartHttpServletRequest" method="post" enctype="multipart/form-data">
		���� : <input type='text' name="title" /><br/>
		���� : <input type="file" name='file' /><br/>
		<input type="submit" value="����" />
	</form>
	
	<hr/>
	<h3>Ŀ�ǵ尴ü ���</h3>
	<form action="commandObject" method="post" enctype="multipart/form-data">
		���� : <input type='text' name="title" /><br/>
		���� : <input type="file" name='file' /><br/>
		<input type="submit" value="����" />
	</form>


</body>
</html>