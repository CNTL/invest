<%@ include file="../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="../inc/meta.inc"%>
	<script type="text/javascript" src="../js/layer/layer.min.js"></script>
	<script type="text/javascript">
		var webroot = "<c:out value="${rootPath}"/>";
	</script>
	<style type="text/css">
		.tb-void {
			line-height: 18px;
			text-align: center;
			border: 1px solid #f2f2f2;
			border-top: 0;
			color: #333;
			width: 100%;
		}
		.tb-void a {
			color: #005ea7;
			text-decoration: none;
		}
		.tb-void th {
			background: #e7e7e7;
			height: 32px;
			line-height: 32px;
			padding: 0 5px;
			text-align: center;
			font-weight: 400;
		}
		.tb-void tbody {
			display: table-row-group;
			vertical-align: middle;
			border-color: inherit;
		}
		.tb-void .tr-th{
			background: #f5f5f5;
		}
		.tb-void .tr-th td {
			text-align: left;
			padding-top: 4px;
			padding-bottom: 4px;
		}
		.tb-void .tr-th span {
			display: inline-block;
			margin-right: 10px;
			overflow: hidden;
			vertical-align: middle;
			height: 24px;
			line-height: 23px;
			float: left;
		}
		.tb-void .tr-th td .tcol1{
			margin-left:10px;
			font-size:14px;
		}
		.tb-void .tr-th td .tcol3{
			float:right;
			margin-right:10px;
			font-size:12px;
		}
		.tb-void .tr-td td {
			vertical-align: top;
			padding-top: 10px;
			border: 1px solid #f2f2f2;
			padding: 10px 5px;
		}
		.tb-void .img-list {
			text-align: left;
			width: 120px;
			overflow: hidden;
		}
		.tb-void .img-list .img-box {
			border: 1px solid #e1e1e1;
			float: left;
			margin-right: 4px;
			margin-bottom: 4px;
		}
		.tb-void .img-list img{
			width:116px;height:80px;border:0px;
		}
		.tb-void .ftx-03 {
			color: #999;
		}
	</style>
</head>
<body>
	<%@include file="../inc/header.inc"%>
	<div class="shadow">
    </div>
	<div class="main clearfix">
		<div class="wrap-1024 clearfix" style="width:1024px;margin-left:auto;margin-right:auto;margin-bottom:50px;margin-top:20px;">
			<table class="tb-void">
				<thead>
					<tr>
						<th style="text-align:left;font-size:16px;font-weight:900;">通知</th>
					</tr>
				</thead>
				<c:forEach var="notice" items="${notices}">
				<tbody id="tb-<c:out value="${notice.id}"/>">
					<tr class="tr-th">
						<td>
							<span class="tcol1"><a href="javascript:;"><c:out value="${notice.title}"/></a></span>
							<span class="tcol2"></span>
							<span class="tcol3"><c:out value="${notice.created}"/></span>
						</td>
					</tr>
					<tr>
						<td style="text-align:left;">
							<p style="text-indent: 2em;"><c:out value="${notice.content}"/></p>
						</td>
					</tr>
				</tbody>
				</c:forEach>
			</table>
		</div>
		<div class="clear"></div>
		<div class="pager">
			<span class="count" title="总记录数"><c:out value="${noticeCount}"/> 条</span>
			<c:choose>
				<c:when test="${page==1}"><a href="javascript:void();" class="prev">上一页</a></c:when>
				<c:otherwise><a href="Project.do?m=<c:out value="${menu}"/>&page=<c:out value="${page-1}"/>" class="prev">上一页</a></c:otherwise>
			</c:choose>
			<c:forEach var="x" begin="${pageBegin}" end="${pageEnd}">
				<c:choose>
					<c:when test="${page==x}"><a href="javascript:void();" class="current"><c:out value="${x}"/></a></c:when>
					<c:otherwise><a href="Project.do?m=<c:out value="${menu}"/>&page=<c:out value="${x}"/>"><c:out value="${x}"/></a></c:otherwise>
				</c:choose>
			</c:forEach>
			<c:choose>
				<c:when test="${page==pageCount}"><a href="javascript:void();" class="prev">下一页</a></c:when>
				<c:otherwise><a href="Project.do?m=<c:out value="${menu}"/>&page=<c:out value="${page+1}"/>" class="prev">下一页</a></c:otherwise>
			</c:choose>
		</div>
	</div>
	
	<%@include file="../inc/footer.inc"%>
</body>
</html>