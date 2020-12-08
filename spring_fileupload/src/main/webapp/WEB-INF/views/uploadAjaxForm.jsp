<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.fileDrop{
	width:100%;
	height:200px;
	border:1px dotted blue;	
}
small{
	margin-left:3px;
	font-weight:bold;
	color:gray;
}
</style>

</head>
<body>
	<h3>Ajax File Upload</h3>
	<div class="fileDrop"></div>
	
	<div class="uploadedList" ></div>
	
	<button type="button" id='submitBtn'>등록</button>
	
	<script src='https://code.jquery.com/jquery-1.12.4.min.js'></script>
	<script>
	$(this).on("dragenter dragover drop", function(event){
		//event.preventDefault();
		return false;
	});
	
	$(".fileDrop").on("dragenter dragover", function(event){
		return false;
	});
	
	$('.fileDrop').on("drop", function(event){
		//event.preventDefault();
		
		//alert("drop");
		
		var files=evnet.originalEvent.dataTransfer.files;
		
		for(var i = 0; files.length; i++){
			alert(files[i].name);
		}
		
		return false;
	})
	
	
	</script>
</body>