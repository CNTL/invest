<%@ include file="../../include/Include.jsp"%>
 
<%@page pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
	<%@include file="../../inc/meta.inc"%>
	<%@include file="../inc/csslink.inc"%>
    <script type="text/javascript" src="../static/js/idangerous.swiper.min.js"></script>
    <script type="text/javascript" src="../static/js/common.js"></script>
    <style>
    .title span{
    float:right;color:#bababa;
    }
    </style>
    <script>
        var rootPath = "<%=com.tl.common.WebUtil.getRoot(request) %>";
        function mySwipePrev(type){
        	var curPage = $("#curPage" + type).val();
        	curPage = parseInt(curPage,10);
        	if(curPage == 1) return;
        	$("#curPage" + type).val(curPage - 1);
        	mySwipe(curPage - 1, type);
        }
		function mySwipeNext(type){
			var curPage = $("#curPage" + type).val();
        	curPage = parseInt(curPage,10);
        	var pageCount = $("#pageCount" + type).val();
        	pageCount = parseInt(pageCount,10);
			if(curPage == pageCount) return;
			$("#curPage" + type).val(curPage + 1);
			mySwipe(curPage + 1, type);
        }
		function mySwipe(curPage, type){
			$.ajax({
		        type:"GET", //请求方式  
		        url:"../user/PeopleMain.do?a=getPersons&curPage=" + curPage + "&type=" + type, //请求路径  
		        cache: false,
		        dataType: 'JSON',   //返回值类型  
		        success:function(data){
		        	if(data != null && data.length > 0){
			        	var messages = data.messages;
			    		if(!messages||typeof Object.prototype.toString.call(messages) == "[object Array]"||!messages.length)return;
		    			assemble(type, messages);
		    		}
		        } ,
				error:function (XMLHttpRequest, textStatus, errorThrown) {
					   alert(errorThrown);
				}
		    });
		}
		function assemble(type, result){
			$("#curDiv" + type).html("");
			var i = 0;
	    	$.each(result, function(i,item){
	    		var id = item.id;
	    		var name = item.name;
	    		var intro = item.intro;
	    		var head = item.head;
	    		var job = item.perJobName;
	    		if(head == null || head.length == 0){
	    			head = '../static/image/temp/pic2.png';
	    		} else {
	    			head = rootPath + head;
	    		}
	    		
	    		i++;
	    		var html = '';
	    		if(parseInt(i,10)%4 == 0){//取四个影人信息信息
	    			html += '<div class="box box_last">';
	    		} else{
	    			html += '<div class="box">';
	    		}
	    		html += '<div class="box_top"></div>' + 
		                '<div class="box_main project">' + 
		                '<div class="pic">' + 
		                	'<a href="../user/PeopleDetailMain.do?a=detail&mainType=4&id='+id+'">' + 
		                		'<img src="' + head + '" />' + 
		                  	'</a>' + 
		                  	'<span>影人</span>' + 
		                '</div>' + 
		                '<div>' + 
		                	'<div class="title">' + 
		                        '<a href="../user/PeopleDetailMain.do?a=detail&mainType=4&id='+id+'">'+
		                        name+'</a><span>'+job+'</span>' + 
		                    '</div>' + 
		                    '<div class="desc">' + intro + '</div>' + 
		                '</div>' + 
		                '<div class="tool">' + 
		                    '<a data-url="'+rootPath+'user/PeopleDetailMain.do?a=detail&mainType=4&id='+id+'" class="share">分享</a>' + 
		                    '<a href="'+rootPath+'user/PeopleDetailMain.do?a=detail&mainType=4&id='+id+'" class="view"></a>' + 
		                '</div>' + 
		            '</div>' + 
		            '<div class="box_bottom"></div>'; 
		            $("#curDiv" + type).append(html);
		            shareInfo();
	    	});
		}
    </script>
</head>

<body>
<div id="body-container" style="min-width:980px;">
	<%@include file="../../inc/header.inc"%>
	<div class="banner  hidden-xs">
        <img src="../static/image/banner3.png" />
    </div>
    <div id="test"></div>
    <div class="project_list">
	   	<c:forEach var="data" varStatus="status1" items="${datas}">
	   
	   	  <c:choose>
					<c:when test="${status1.index%2!=0}"> <div class="block2">	<div class="wrap"></c:when>
					<c:otherwise> <div class="block1">	</c:otherwise>
				</c:choose>
            <a class="left" style="cursor:pointer;display:none;" onclick="mySwipePrev(${data.perType})"></a>
            <a class="right" style="cursor:pointer;display:none;" onclick="mySwipeNext(${data.perType})"></a>
            <div class="top">
                <h2><c:out value="${data.perName}"/></h2>
                <div class="cate">
                    <a href="../user/PeopleMoreMain.do?a=queryPersons&mainType=4&type=<c:out value="${data.perType}"/>">更多 &gt;&gt;</a>
                </div>
                <div style="display:none">
               		<input type="text" id="curPage${data.perType}" name="curPage${data.perType}" value="<c:out value="${data.persons.curPage}"/>"/>	
               		<input type="text" id="total${data.perType}" name="total${data.perType}" value="<c:out value="${data.persons.total}"/>"/>
               		<input type="text" id="pageCount${data.perType}" name="pageCount${data.perType}" value="<c:out value="${data.persons.pageCount}"/>"/>
               	</div>
            </div>
             
            <div class="people_scroll">
                <div class="wrapper">
                    <div class="slide" id="curDiv${data.perType}">
                    	<c:forEach var="person" varStatus="status" items="${data.persons.messages}">
                    	<c:choose>
							<c:when test="${status.index%4==0}"><div class="box box_last"></c:when>
							<c:otherwise> <div class="box"></c:otherwise>
						</c:choose>
                            <div class="box_top"></div>
                            <div class="box_main project">
                                <div class="pic">
			                    	<a href="../user/PeopleDetailMain.do?a=detail&mainType=4&id=<c:out value="${person.id}"/>">
										<c:choose>
											<c:when test="${person.head=='' || person.head == null}"><img src="../static/image/temp/pic2.png" /></c:when>
											<c:otherwise><img src="../<c:out value="${person.head}"/>" /></c:otherwise>
										</c:choose>
			                      	</a>
			                      	<span>影人</span>
			                    </div>
                                <div>
                                	<div class="title">
				                        <a href="../user/PeopleDetailMain.do?a=detail&mainType=4&id=<c:out value="${person.id}"/>">
				                        <c:out value="${person.name}"/>
				                        </a>
				                        <span><c:out value="${person.perJobName}"/></span>
				                        
				                    </div>
				                    <div class="desc">
				                        
				                        <c:out escapeXml="false" value="${person.intro}"/>
				                    </div>
                                </div>
                                <div class="tool">
                                    <a data-url="<c:out value="${rootPath}"/>user/PeopleDetailMain.do?a=detail&mainType=4&id=<c:out value="${person.id}"/>" class="share">分享</a>
                                    <a href="<c:out value="${rootPath}"/>user/PeopleDetailMain.do?a=detail&mainType=4&id=<c:out value="${person.id}"/>" class="view"></a>
                                </div>
                            </div>
                            <div class="box_bottom"></div>
                        </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
        
 				  <c:choose>
					<c:when test="${status1.index%2!=0}"> </div></c:when>
					<c:otherwise></c:otherwise>
				     </c:choose>
        </c:forEach>
       </div>

	<%@include file="../../inc/footer.inc"%>
	</div>
</body>
</html>