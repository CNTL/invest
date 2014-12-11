<%@ include file="../../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title><c:out value="${title}"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="<c:out value="${keywords}"/>" />
<meta name="description" content="<c:out value="${description}"/>" />
<%@include file="../inc/csslink.inc"%>
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
	.tb-void .tr-td td {
		vertical-align: top;
		padding-top: 10px;
		border: 1px solid #f2f2f2;
		padding: 10px 5px;
	}
	.job_add {
		width: 200px;
		margin: 0px auto 20px;
	}
	.job_add .input label{
		width : 90px;
	}
</style>
</head>
<body>
<%@include file="../../inc/header.inc"%>
<div class="banner">
    <img src="../static/image/banner2.png" />
</div>
<div class="main clearfix">
	<div class="setting wrap">
			<%@include file="../inc/userHeader.inc"%>
			
			<div class="setting-detail">
				<input type="hidden" id="login_user_id" name="login_user_id" value="<c:out value="${loginUser.id}"/>" />
				<div class="job_add">
					<div class="btn">
						<input type="button" id="newBtn" onclick="addAddress();" value="新增收件地址">
					</div>
				</div>
				<table class="tb-void">
					<thead>
						<tr>
							<th>收件人</th>
							<th>电话</th>
							<th>收件地址</th>
							<th>邮编</th>
							<th>操作</th>
						</tr>
					</thead>
					<c:forEach var="address" items="${addresses}">
					<tr class="tr-td tr-address">
						<td><c:out value="${address.recipients}"/></td>
						<td><c:out value="${address.mphoneNo}"/></td>
						<td><c:out value="${address.province}"/><c:out value="${address.city}"/><c:out value="${address.county}"/><c:out value="${address.detail}"/></td>
						<td><c:out value="${address.zipcode}"/></td>
						<td>
							<a href="javascript:void(0);" onclick="editAddress(<c:out value="${address.id}"/>,<c:out value="${loginUser.id}"/>);">修改</a> | 
							<a href="javascript:void(0);" onclick="delAddress(<c:out value="${address.id}"/>);">删除</a>
						</td>
					</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
</div>
<!-- script -->
<%@include file="../inc/script.inc"%>
<script type="text/javascript" src="../js/layer/layer.min.js"></script>
<script type="text/javascript" src="../proj/script/datas.js" ></script>
<script type="text/javascript" src="../user/user/script/address.js" ></script>
<!-- script -->

<!-- footer -->
<%@include file="../../inc/footer.inc"%>

</body>
</html>