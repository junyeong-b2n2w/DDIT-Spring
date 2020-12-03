<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>

<form name ="postForm">
	<input type="hidden" name="id" value="${member.id }"/>
</form>


<script> // 사진 이미지 불러오기

	var imageURL = "getPicture.do?picture=${member.picture}";
	
window.onload=function(){
	$('div#pictureView').css({'background-image':'url('+imageURL+')',
							  'background-position':'center',
							  'background-size':'cover',
							  'background-repeat':'no-repeat'});
	$('input').css('border',"none").prop("readonly",true);
		
	

	var form=$('form[name="postForm"]');
	
	$('button#modifyBtn').on('click', function(event){
		//alert("modify btn click");
		form.attr('action','modifyForm.do');
		form.submit();
	});
	$('button#deleteBtn').on('click', function(event){
// 		alert("delete btn click");
		form.attr('action','remove.do');
		form.submit();
	});
	$('button#stopBtn').on('click', function(event){
// 		alert("stop btn click");
		form.attr('action','stop.do');
		form.submit();
	});
	$('button#listBtn').on('click', function(event){
		alert("list btn click");
	});
}


</script>