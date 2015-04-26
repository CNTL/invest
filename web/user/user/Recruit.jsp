<%@ include file="../../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   <%@include file="../../inc/meta.inc"%>
	 
	<script type="text/javascript">
		var webroot = "<c:out value="${rootPath}"/>";
		var menu = "<c:out value="${menu}"/>";
	</script>
	<style>
	#list-recs li{
		float:left;
		margin:10px;
	}
	</style>
</head>
<body>
	<%@include file="../../inc/header.inc"%>
	
	<div class="people_globaltop">
        <div class="wrap">
<!--             <a href="../org/BasicInfo.do?infoType=1&mainType=1" class="profile">个人设置</a> -->
            <div class="avavtar">
                <img style="border-radius: 50%;" src="<c:out value="${loginUser.head}"/>" />
            </div>
            <div class="info">
                <h2><c:out value="${loginUser.perNickName}"/>
					<span>
						<c:choose>
							<c:when test="${loginUser.type==0}">个人</c:when>
							<c:when test="${loginUser.type==2}">机构</c:when>
							<c:otherwise>未知</c:otherwise>
						</c:choose>
					</span>
				 </h2>
                <div class="desc">
                     
                    <c:out escapeXml="false" value="${loginUser.intro}"/>
                    <br />
                    姓名：<c:out value="${loginUser.name}"/><br />
                    <span style="display:none;">短信息</span>
                </div>
            </div>
            <div class="clear"></div>
            <ul class="nav">
				<li id="menu_1"><a class="current" href="<c:out value="${rootPath}"/>resume/myrecruit.do?a=myrecruit&infoType=1" class="current">职位收藏</a></li>
				<li id="menu_2"><a href="<c:out value="${rootPath}"/>resume/recruitresume.do?a=recruitresume&infoType=2">已投职位</a></li>
				<li id="menu_3"><a href="<c:out value="${rootPath}"/>recruit/Edit.do?a=detail&mainType=3">发布职位</a></li>
				<li id="menu_4"><a href="<c:out value="${rootPath}"/>user/userRecruitManager.do?mainType=3">职位管理</a></li>
            </ul>
		</div>
    </div>
			
	<div class="project_list">
        <div class="block1">
            <c:forEach var="msg" varStatus="status" items="${msg.messages}">
				<c:choose>
					<c:when test="${status.index%4==0}"><div class="box box_last"></c:when>
					<c:otherwise><div class="box"></c:otherwise>
				</c:choose>
                <div class="box_top"></div>
                <div class="box_main job">
                    <div class="pic">
                    	<c:choose>
							<c:when test="${recruitType=='edit'}">
								<a href="../user/recruit.do?a=edit&mainType=3&id=${msg.id}">
							</c:when>
							<c:otherwise>
								<a href="../user/recruit.do?a=detail&mainType=3&id=${msg.id}">
							</c:otherwise>
						</c:choose>
	                        <c:choose>
								<c:when test="${msg.jobPictrue==''}"><img src="../static/image/temp/pic2.png" /></c:when>
								<c:otherwise><img src="../<c:out value="${msg.jobPictrue}"/>" /></c:otherwise>
							</c:choose>
						</a>
                        <span>影聘</span>
                    </div>
                    <div>
                        <div class="title">
                            <c:choose>
								<c:when test="${recruitType=='edit'}">
									<a href="../user/recruit.do?a=edit&mainType=3&id=${msg.id}">${msg.jobName}</a>
								</c:when>
								<c:otherwise>
									<a href="../user/recruit.do?a=detail&mainType=3&id=${msg.id}">${msg.jobName}</a>
								</c:otherwise>
							</c:choose>
                            <span><c:out value="${msg.company}"/></span>
                        </div>
                        <div class="info">
                            <ul>
                               
                                 <c:if test="${msg.jobType==0}">
				                 	<li>总价${msg.salary}元</li>
				                    <li>${msg.provinceName}</li>
				                    <li>${msg.days}天</li>
				                </c:if>
				                
				                 <c:if test="${msg.jobType==1}">
				                 	<li>月薪${msg.salary}元</li>
				                    <li>${msg.provinceName}</li>
				                    <li>${msg.days}个月</li>
				                </c:if>
                                
                            </ul>
                        </div>
                        <div class="desc">
                            <span><c:out value="${msg.jobAttract}"/></span><br />
                           	 发布时间：<c:out value="${msg.createtime}"/><br />
                            	已投递简历人数：<c:out value="${msg.resumeNum}"/>人
                        </div>
                    </div>
                    <div class="tool">
                    	<button type="button" class="btn btn-default btn-sm" onclick="delRec(<c:out value="${msg.id}"/>);">
						  <span class="glyphicon glyphicon-remove"></span> 删除
						</button>
                    
                        <a href="#" class="view"></a>
                    </div>
                </div>
                <div class="box_bottom"></div>
            </div>
			</c:forEach>
            <div class="clear"></div>
        </div>
        <div class="pager">
			<span class="count" title="总记录数"><c:out value="${msg.total }"/> 条</span>
			<c:choose>
				<c:when test="${msg.curPage==1}"><a href="javascript:void();" class="prev">上一页</a></c:when>
				<c:otherwise><a href="../recruit/ListMain.do?a=<c:out value="${queryType}"/>&more=1&recruitType=view&mainType=3&curPage=<c:out value="${msg.curPage-1}"/>" class="prev">上一页</a></c:otherwise>
			</c:choose>
			<c:forEach var="x" begin="${msg.pageBegin}" end="${msg.pageEnd}">
				<c:choose>
					<c:when test="${msg.curPage==x}"><a href="javascript:void();" class="current"><c:out value="${x}"/></a></c:when>
					<c:otherwise><a href="../recruit/ListMain.do?a=<c:out value="${queryType}"/>&more=1&recruitType=view&mainType=3&curPage=<c:out value="${x}"/>"><c:out value="${x}"/></a></c:otherwise>
				</c:choose>
			</c:forEach>
			<c:choose>
				<c:when test="${msg.curPage==msg.pageCount}"><a href="javascript:void();" class="prev">下一页</a></c:when>
				<c:otherwise><a href="../recruit/ListMain.do?a=<c:out value="${queryType}"/>&more=1&recruitType=view&mainType=3&curPage=<c:out value="${msg.curPage+1}"/>" class="prev">下一页</a></c:otherwise>
			</c:choose>
		</div>
	</div>
<!-- script -->
<%@include file="../inc/script.inc"%>
<!-- script -->

<!-- footer -->
<%@include file="../../inc/footer.inc"%>
<!-- footer -->    
<script>
	var typeFlag = "<%=request.getParameter("recruitType") %>";
	var rootPath = "<%=com.tl.common.WebUtil.getRoot(request) %>";
 </script>
 
<script type="text/javascript" src="../static/js/jQselect.js"></script>
<script type="text/javascript">
function delRec(id){
	
	$.ajax({
        type:"GET", //请求方式  
        url:"../user/recruitResume.do?a=delrec&recid=" +id  , //请求路径  
        cache: false,
        dataType: 'TEXT',   //返回值类型  
        success:function(data){
        	if(data != null && data == "ok"){
    			
    			window.location.reload();

    		}else{
    			$.messager.alert('消息', "删除失败!");
    		}
        } ,
		error:function (XMLHttpRequest, textStatus, errorThrown) {
			   alert("error="+errorThrown);
		}
    });
	
}
</script>
</body>
</html>