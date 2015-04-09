<%@ include file="../../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   <%@include file="../../inc/meta.inc"%>
	<script type="text/javascript" src="<c:out value="${rootPath}"/>js/layer/layer.min.js"></script>
	<script type="text/javascript" src="<c:out value="${rootPath}"/>user/user/script/userRecruit.js"></script>
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
                    <c:out value="${loginUser.intro}"/><br />
                    姓名：<c:out value="${loginUser.name}"/><br />
                    <span style="display:none;">短信息</span>
                </div>
            </div>
            <div class="clear"></div>
            <ul class="nav">
				<li id="menu_1"><a href="<c:out value="${rootPath}"/>resume/myrecruit.do?a=myrecruit&infoType=1" >职位收藏</a></li>
				<li id="menu_2"><a href="<c:out value="${rootPath}"/>resume/recruitresume.do?a=recruitresume&infoType=2">已投职位</a></li>
				<li id="menu_3"><a href="<c:out value="${rootPath}"/>recruit/Edit.do?a=detail&mainType=3">发布职位</a></li>
				<li id="menu_4"><a class="current" href="<c:out value="${rootPath}"/>user/userRecruitManager.do?mainType=4">职位管理</a></li>
            </ul>
		</div>
    </div>
			
	<div class="people_profile">
		 
		<div class="content" style="float:left;width:100%;">
		<form class="form-horizontal">
		  <div class="form-group">
		    <label class="col-sm-2 control-label">发布的职位：</label>
		    <div class="col-sm-4">
		      <select id="sl-recs" class="form-control" style="width:200px;">
		      	<c:forEach var="rec" items="${recruitLists}">
					  <option data-pubtime="<c:out value="${rec.createtime}"/>" value="<c:out value="${rec.id}"/>">
					  	<c:out value="${rec.jobName}"/>
					  </option>
				</c:forEach>
			  </select>
		    </div>
		    <div class="col-sm-4 text-left">
		         <button id="rec-view" class="btn btn-primary" style="padding:6px 12px;" type="button">预览职位</button>
			     <button id="rec-update" class="btn btn-primary" style="padding:6px 12px;" type="button">编辑职位</button>
				 <button id="rec-del"    class="btn btn-danger" style="padding:6px 12px;" type="button">删除职位</button>
			 </div>
		  </div>
	    </form>
		</div>
		<div id="usercontainer">
		<div style="font-size:14px;font-weight:bold;">收到的简历：<span id="rec-count" style="color:red;">0</span>&nbsp;份</div>
			<ul id="list-recs">
 
			</ul>
		</div>
		<div class="clear"></div>
		
	</div>
	<%@include file="../../inc/footer.inc"%>
</body>
</html>