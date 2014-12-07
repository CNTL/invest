<%@ include file="../../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.tl.common.WebUtil"%>
<html>
<head>
	<title><c:out value="${proj.name}"/>--项目详细信息</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	
	<script type="text/javascript" src="../js/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="../js/jquery-easyui/easyloader.js"></script>
	<script type="text/javascript">var apphost = "<%=WebUtil.getRoot(request)%>";</script>
	<script type="text/javascript" src="../js/tl/tl.form.js"></script>
	<style type="text/css">
		td{font-size:14px;border: 1px solid #c6c6c6;line-height:28px;}
		td.title{width:80px;text-align:right;padding-right:20px;font-size:12px;background: #e7e7e7;}
		div.desc{height:360px;overflow-y:scroll;}
		.desc p{text-indent:2em;}
		.desc p img{max-width:608px;}
	</style>
</head>
<body>
	<form id="tlform" class="easyui-form" method="post">
		
	</form>
	<div id="main" style="width:100%;padding-top:20px;padding-left:20px;padding-right:20px;">
		<table style="width:100%;border: 1px solid #c6c6c6;">
			<tr>
				<td class="title">状态</td>
				<td style="width:250px;">
					<c:choose>
						<c:when test="${proj.approveStatus==0}">未审批</c:when>
						<c:when test="${proj.approveStatus==1}">已审批</c:when>
						<c:when test="${proj.approveStatus==2}">审批未通过</c:when>
						<c:otherwise>未知</c:otherwise>
					</c:choose>
					--
					<c:choose>
						<c:when test="${proj.status==0}">未开始</c:when>
						<c:when test="${proj.status==1}">众筹中</c:when>
						<c:when test="${proj.status==2}">众筹结束</c:when>
						<c:when test="${proj.status==3}">锁定</c:when>
						<c:otherwise>未知</c:otherwise>
					</c:choose>
					--
					<c:choose>
						<c:when test="${proj.deleted==0}">已删除</c:when>
						<c:when test="${proj.deleted==1}">未删除</c:when>
						<c:otherwise>未知</c:otherwise>
					</c:choose>
				</td>
				<td colspan="2" rowspan="6" style="padding:20px;">
					<c:choose>
						<c:when test="${proj.imgUrl==''}"><img style="width:232px;height:160px;border=0px;" src="../static/image/temp/pic2.png" /></c:when>
						<c:otherwise><img style="width:232px;height:160px;border=0px;" src="../<c:out value="${proj.imgUrl}"/>" /></c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td class="title">项目名称</td>
				<td style="width:360px;"><c:out value="${proj.name}"/></td>
			</tr>
			<tr>
				<td class="title">地域</td>
				<td><c:out value="${province.name}"/><c:out value="${city.name}"/><c:out value="${county.name}"/></td>
			</tr>
			<tr>
				<td class="title">目标</td>
				<td><c:out value="${proj.countDay}"/>天 筹集 ￥<c:out value="${proj.amountGoal}"/></td>
			</tr>
			<tr>
				<td class="title"></td>
				<td>已完成 ￥<c:out value="${proj.amountRaised}"/>（<c:out value="${finishPer}"/>%），剩余 <c:out value="${surplus}"/> 天</td>
			</tr>
			<tr>
				<td class="title">视频地址</td>
				<td><c:out value="${proj.videoUrl}"/></td>
			</tr>
			<tr>
				<td colspan="4" style="line-height:28px;background:#e7e7e7;">支持类型</td>
			</tr>
			<tr>
				<td colspan="4">
					<table style="width:100%;">
						<c:forEach var="mode" items="${modes}">
						<tr>
							<td>
								<c:choose>
									<c:when test="${mode.price<=0}">无私支持</c:when>
									<c:otherwise>
										支持￥<c:out value="${mode.price}"/>
										<br>
										已众筹：￥<c:out value="${mode.price*mode.countSupport}"/>
									</c:otherwise>
								</c:choose>
							</td>
							<td>
								已有 <span class="red"><c:out value="${mode.countSupport}"/></span> 位支持者
								<br />
								限额 <span class="red"><c:out value="${mode.countGoal}"/></span> 位
							</td>
							<td style="width:320px;">
								 <c:out value="${mode.returnContent}"/>
							</td>
							<td>
								<c:choose>
									<c:when test="${mode.freight<=0}">免运费</c:when>
									<c:otherwise><c:out value="${mode.freight}"/></c:otherwise>
								</c:choose>
								<br>
								<c:out value="${mode.returntime}"/>
							</td>
						</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
			<tr>
				<td class="title">简介</td>
				<td colspan="3"><c:out value="${proj.summary}"/></td>
			</tr>
			<tr>
				<td class="title" valign="top">详细信息</td>
				<td colspan="3">
					<div class="desc">
					<c:out escapeXml="false" value="${proj.content}"/>
					</div>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>