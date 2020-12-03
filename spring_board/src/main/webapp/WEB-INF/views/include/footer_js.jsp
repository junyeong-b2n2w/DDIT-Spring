<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>    
    
<script type="text/x-handlebars-template"  id="subMenu-list-template" >
{{#each .}}
	<li class="nav-item subMenu" >
      	<a href="javascript:goPage('{{murl }}','{{mcode}}');" class="nav-link">
          <i class="{{micon }}"></i>
             <p>{{mname }}</p>
        </a>
	</li>
{{/each}}
</script>

<script>
	var printData = function(subMenuArr, target, templateObject){
		var template = Handlebars.compile(templateObject.html());
		var html = template(subMenuArr);
		$('.subMenu').remove();
		target.append(html);
	}

	
	
	

	function subMenu(mCode){
		if(mCode != "M000000"){
			$.getJSON("<%=request.getContextPath()%>/common/subMenu.do?mCode="+mCode, function(data){
				printData(data, $('.subMenuList'), $('#subMenu-list-template'));
			});	
		}else{
			$('.subMenuList').html("");
		}
		
		
	}
	
	function goPage(url, mCode){
		//HTML5 브라우저에서 사용가능
		if(typeof(history.pushState) == 'function'){
			//현재주소를 가져온다
			var renewURL = location.href;
			//현재주소중 .do 뒷부분이 있다면 날려버린다
			renewURL = renewURL.substring(0, renewURL.indexOf(".do")+3);
			
			if( mCode != 'M000000'){
				renewURL += "?mCode=" + mCode;
			}
			
			//페이지를 리로드 하지 않고 페이지 주소만 변경할때 사용
			history.pushState(mCode, null, renewURL);
			
		}else{
			location.hash = "#" + mCode;
		}
		
		$('#if_list').attr("src", "<%=request.getContextPath()%>"+url);
	}
	
	goPage('${menu.murl}', '${menu.mcode}');
	subMenu('${menu.mcode}'.substring(0,3)+"0000");
	
</script>
