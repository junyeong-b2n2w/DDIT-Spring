<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>

<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.7.6/handlebars.min.js"></script>
<script type="text/x-handlebars-template"  id="reply-list-template" >
{{#each .}}
<div class="replyLi" >
	<div class="image">
      			<img data-target="{{replyer}}" width="50px" src="{{getPicture replyer}}" class="img-circle elevation-2" alt="user Image" />
      		</div>

 	<div class="timeline-item" >
  		<span class="time">
    		<i class="fa fa-clock"></i>{{prettifyDate regDate}}
	 		<a class="btn btn-primary btn-xs" id="modifyReplyBtn" data-rno={{rno}}
				style="display:{{loginUserCheck replyer}};"
	    		data-replyer={{replyer}} data-toggle="modal" data-target="#modifyModal">Modify</a>
  		</span>
	
  		<h3 class="timeline-header"><strong style="display:none;">{{rno}}</strong>{{replyer}}</h3>
  		<div class="timeline-body" id="{{rno}}-replyText">{{replyText}} </div>
	</div>
</div>
{{/each}}	
</script>

<script>
window.onload=function(){
	var replyPage=1;
	
	
	getPage("<%=request.getContextPath()%>/replies/list.do?bno=${board.bno}&page="+replyPage);
	
	function getPage(pageInfo){
		$.getJSON(pageInfo,function(data){
			//alert(data.pageMaker.totalCount);
			printData(data.replyList, $('#repliesDiv'), $('#reply-list-template'));
			printPaging(data.pageMaker,$('#pagination'));
		});
	}
	
	$('.pagination').on('click','li a',function(event){
		event.preventDefault();
		replyPage=$(this).attr("href");
		getPage("<%=request.getContextPath()%>/replies/list.do?bno=${board.bno}&page="+replyPage);
	});
	
	$('#replyAddBtn').on('click',function(e){
		var replyer=$('#newReplyWriter').val();
		var replyText=$('#newReplyText').val();
		
		if(replyText==""){
			alert('댓글내용은 필수입니다.');
			$('newReplyText').focus().css("boarder-color","red").attr('placeholder','내용은 필수입니다.');
			return;
		}
	
		var data={
				"bno":"${board.bno}",
				"replyer":replyer,
				"replyText":replyText
		}
		//alert(JSON.stringify(data));
		
		$.ajax({
			url:"<%=request.getContextPath()%>/replies/add.do",
			type:"post",
			data:JSON.stringify(data),
			success:function(data){
					alert('댓글이 등록되었습니다.');
					getPage("<%=request.getContextPath()%>/replies/list.do?bno=${board.bno}&page="+data);
					$('#newReplyText').val("");
			},
			error:function(error){
				alert("댓글 등록을 실패했습니다.");
			}
		});
	});
	
	$('div.timeline').on('click','#modifyReplyBtn',function(){
		//alert('123');
		var rno = $(this).attr("data-rno");
		var replyer = $(this).attr("data-replyer");
		var replyText = $('#'+rno+'-replyText').text();
		
		$('#replytext').val(replyText);
		$('.modal-title').text(rno);
	});
	
	$('#replyModBtn').on('click', function(event){
		//alert("mod!");
		var rno=$('.modal-title').text();
		var replytext = $('#replytext').val();
		
		var sendData = {
				rno : rno,
				replyText : replytext
		}
		
		$.ajax({
			url:"<%=request.getContextPath()%>/replies/update.do",
			type:"post",
			data: JSON.stringify(sendData),
			success: function(result){
				alert("수정되었습니다.");
				getPage("<%=request.getContextPath()%>/replies/list.do?bno=${board.bno}&page="+replyPage);
			},
			error:function(error){
				alert("수정실패햇습니다.")
			},
			complete:function(){
				$('#modifyModal').modal('hide');
			}
		});
	});
	
	$('#replyDelBtn').on('click', function(event){
		var rno=$('.modal-title').text();
		
		var sendData = {
				bno:"${board.bno}",
				rno : rno,
				page : replyPage
		};
		
		$.ajax({
			url:"<%=request.getContextPath()%>/replies/remove.do",
			type:'post',
			data:JSON.stringify(sendData),
			success:function(page){
				alert("삭제되었습니다");
				replyPage=page;
				getPage("<%=request.getContextPath()%>/replies/list.do?bno=${board.bno}&page="+page);
			},
			error:function(error){
				alert("삭제 실패하였습니다.");
			},
			complete:function(){
				$('#modifyModal').modal('hide');
			}
		});
		

	
	});	
	
	
	
}

var printData=function(replyArr, target, templateObject){
	var template=Handlebars.compile(templateObject.html());
	var html = template(replyArr);
	$('.replyLi').remove();
	target.after(html);
}

Handlebars.registerHelper({
	"prettifyDate": function(timeValue){
	var dateObj=new Date(timeValue);
	var year=dateObj.getFullYear();
	var month = dateObj.getMonth()+1;
	var date=dateObj.getDate();
	return year+"/"+month+"/"+date;
	},
	"loginUserCheck":function(replyer){
		return "${loginUser.id}"==replyer ? "visible" : "none"; 
	},
	"getPicture":function(replyer){
		var data={id:replyer};
		var src="<%=request.getContextPath() %>/member/getPicture.do?picture=";
		var flag=false;
		$.getJSON("<%=request.getContextPath()%>/member/getMemberToJson.do",data,function(result){			
			if(result){
				src+=result.picture;	
			}else{
				src+="noImage.jpg";		
			}
			$('img[data-target="'+replyer+'"]').attr("src",src);
		});
	}
	
})
		

//reply pagination

var printPaging = function(pageMaker, target){
	
	var str="";
	
	if(pageMaker.prev){
		str+="<li class='page-item'><a class='page-link' href='"+(pageMaker.startPage-1)
			+"'> <i class='fas fa-angel-left'/> </a></li>";
	}
	for(var i = pageMaker.startPage; i<= pageMaker.endPage; i++){
		var strClass = pageMaker.cri.page == i ? 'active' : '';
		str+="<li class='page-item " + strClass +"'><a class='page-link' href='" + i + "'>"+
		i+"</a></li>";
	}
	if(pageMaker.next){
		str+="<li class='page-item'><a class='page-link' href='"+(pageMaker.endPage+1)
		+"'> <i class='fas fa-angel-right'/> </a></li>";
	}
	
	target.html(str);
	
}


</script>