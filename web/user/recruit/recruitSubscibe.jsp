<%@ include file="../../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<%@include file="../../inc/meta.inc"%>
<%@include file="../inc/csslink.inc"%>
<style>
.rectitle{
	font-size:18px;
	font-weight:bold;
	margin:10px 0;
}
.lb{
font-size: 14px;
}
.recitem a{
display: inline-block;
width: 100px;
height: 28px;
line-height: 28px;
font-size: 16px;
cursor:pointer;
text-decoration: none;
}
.recitem a:hover{
 background:#4AC4EF;
 color:#fff;
 text-decoration: none;
}
.clearfix {
	display: inline-block;
}
.s_radio_sp {
	list-style: none;
	margin: 0px;
	padding: 0px;
	width: 660px;
}
.s_radio {
	list-style: none;
	margin: 0px;
	padding: 0px;
	width: 660px;
}
.clearfix::after {
	height: 0px;
	clear: both;
	visibility: hidden;
	content: "";
}
.content_l h1 {
	margin: 0px 0px 20px;
	line-height: 42px;
	font-weight: normal;
	font-size:2em;
}
.s_form {
	font-size: 16px;
}
.s_form p {
	display:block;
	font-size:16px;
}
.s_form dl {
	background: rgb(250, 250, 250);
	padding: 20px 25px;
	margin-bottom: 20px;
}
.btn_big {
	background: rgb(13, 149, 114);
	margin: 15px 0px 5px;
	padding: 0px 40px;
	border: currentColor;
	border-image: none;
	height: 60px;
	color: rgb(255, 255, 255);
	line-height: 60px;
	overflow: hidden;
	font-size: 32px;
	float: left;
}

.btn_cancel {
	margin: 15px 0px 0px 10px;
	padding: 0px 40px;
	line-height: 60px;
	font-size: 32px;
	float: left;
}
.s_form dt {
	margin-bottom: 50px;
}
.s_form dd {
	clear: both;
}
.select {
	background: url("../../images/select.gif") no-repeat;
	
	margin: 20px 0px 0px;
	border: currentColor;
	border-image: none;
	width: 625px;
	height: 46px;
	text-align: left;
	color: rgb(169, 170, 165);
	padding-left: 12px;
	font-size-adjust: none;
	font-stretch: normal;
}

.dn {
	display: none;
}
#box_salary {
	background: rgb(255, 255, 255);
	padding: 15px 0px 0px 15px;
	border: 2px solid rgb(201, 203, 206);
	border-image: none;
	width: 606px;
	position: absolute;
	z-index: 99;
}
span.error {
	background: url("../images/error.png") no-repeat 0px 3px;
	margin: 5px 0px 10px;
	text-align: left;
	color: rgb(255, 106, 106);
	line-height: 22px;
	padding-left: 20px;
	font-size: 14px;
	display: block;
}
ul.reset {
	list-style: none;
	margin: 0px;
	padding: 0px;
}
#box_salary li {
	background: rgb(250, 250, 250);
	margin: 0px 13px 13px 0px;
	width: 138px;
	text-align: center;
	color: rgb(85, 85, 85);
	line-height: 38px;
	font-size: 18px;
	float: left;
	cursor: pointer;
}
.s_form h3 {
	background: #4AC4EF;
	margin: 0px 0px 0px -35px;
	padding: 0px 20px 0px 30px;
	height: 42px;
	color: rgb(255, 255, 255);
	line-height: 42px;
	font-size: 18px;
	font-weight: normal;
	float: left;
	position: relative;
}
.s_form h3 em {
	background: url("../static/image/s_arrow.png") no-repeat;
	left: 0px;
	top: 42px;
	width: 10px;
	height: 14px;
	position: absolute;
}
#box_industry {
	background: rgb(255, 255, 255);
	padding: 15px 0px 0px 15px;
	border: 2px solid rgb(201, 203, 206);
	border-image: none;
	width: 606px;
	position: absolute;
	z-index: 99;
}
#box_industry li {
	background: #4AC4EF;
	margin: 0px 13px 13px 0px;
	width: 138px;
	text-align: center;
	color: rgb(85, 85, 85);
	line-height: 38px;
	font-size: 18px;
	float: left;
	cursor: pointer;
}
.s_radio_sp li {
	background: rgb(255, 255, 255);
	margin: 15px 8px 0px 0px;
	border: 3px solid rgb(250, 250, 250);
	border-image: none;
	width: 77px;
	height: 77px;
	text-align: center;
	color: rgb(85, 85, 85);
	line-height: 77px;
	font-size: 18px;
	float: left;
	position: relative;
	cursor: pointer;
}
 
.s_radio li {
	background: rgb(255, 255, 255);
	margin: 15px 8px 0px 0px;
	border: 3px solid rgb(250, 250, 250);
	border-image: none;
	width: 77px;
	height: 77px;
	text-align: center;
	color: rgb(85, 85, 85);
	line-height: 77px;
	font-size: 18px;
	float: left;
	position: relative;
	cursor: pointer;
}

.s_radio li.current {
	border: 3px solid rgb(147, 183, 187);
	border-image: none;
}
.s_radio li em {
	background: url("../static/image/s_choose.png") no-repeat;
	top: -1px;
	width: 31px;
	height: 32px;
	right: -1px;
	position: absolute;
}
:-ms-input-placeholder {
	color: rgb(181, 181, 181);
}

</style>
</head>
<body>
<%@include file="../../inc/header.inc"%>
<div class="shadow"></div>
<div class="body-container">

		<div class="clearfix">
			<div class="content_l">
				<h1>我的职位订阅</h1>
				<input id="orderCount" type="hidden" value="0">
				<form id="subForm">
				    <input type="hidden"  name="name" id="name" value="我的职位订阅-合众映画网 ">
					<input name="id" id="orderId" type="hidden">
					<div class="s_form">
						<p>筛选下面的职位订阅条件，实现精准匹配的个性化职位定制，我们帮你挑工作！</p>
						<dl>
							<dt>
								<h3>
									接收邮箱 <i class="rss_circle"></i>&nbsp; 发送周期 <em></em><span>（必填）</span>
								</h3>
							</dt>
							<dd >
							 <input class="form-control" name="email" id="email" type="text" placeholder="请输入接收邮箱" value=""> 
							 <span class="error" id="emailError" style="display: none;">请输入接收邮箱</span>
							</dd>
							<dd>
								<input name="sl-rate" id="sl-rate" type="hidden" value="7">

								<ul id="list-rate" class="s_radio clearfix">
									<li title="3">3天</li>
									<li title="7" class="current">7天<em></em></li>
								</ul>
								<span class="error" id="sendError" style="display: none;">请选择发送周期</span>
								
							</dd>
						</dl>
						
						<dl>
							<dt>
								<h3>
									职位名称 <em></em><span>（必填）</span>
								</h3>
							</dt>
							<dd>
								<span id="sl-rec" style="font-size:16px;margin-left:10px;"></span>
								<input class="select" id="select_job" data-toggle="modal" data-target="#myModal" type="button" value="请选择职位名称">
								
								<span class="error" id="positionError" style="display: none;">请选择职位名称</span>
								<input type="hidden" name="hrecid"  value="" id="hrecid" />
		  						<input type="hidden" name="hrecname"  value="" id="hrecname" />
							</dd>
						</dl>
						<dl>
							<dt>
								<h3>
									工作地点 <em></em><span>（必填）</span>
								</h3>
							</dt>
							<dd>
								 
								<input type="hidden" name="hcityid" value="" id="hcityid" />
		  						<input type="hidden" name="hcityname"  value="" id="hcityname" />
								<ul id="list-city" class="s_radio clearfix">
									<c:forEach var="city" varStatus="status" items="${cities}" begin="0"  step="1" >
									    <li data-id="${city.id}" data-name="${city.name}" title="${city.name}">${city.name}</li>
										 
									</c:forEach>
								</ul>
								<span class="error" id="cityError" style="display: none;">请选择工作地点
								</span>
							</dd>
						</dl>
						 <button type="button" id="btn-save" style="width:200px;" class="btn btn-success btn-lg">保存</button>
						 
					</div>
				</form>
			</div>
		</div>


	</div>
<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title rectitle" id="myModalLabel">订阅职位选择</h4>
      </div>
      <div class="modal-body" id="modal-body">
      <c:forEach var="type" varStatus="status" items="${types}" >
      <div class="rectitle"><c:out value="${type.name}"/></div>
      <div class="recitem">
       	<c:forEach var="subType" varStatus="status" items="${type.subDics}" >
	  		<a data-dismiss="modal" onclick="selectrec()" data-id="${subType.id}" data-name="${subType.name}">${subType.name}</a>
	 	</c:forEach>
	  </div>
      </c:forEach>
      </div>
    </div>
  </div>
</div>


<script>
	var typeFlag = "
<%=request.getParameter("recruitType") %>";
	var rootPath = "<%=com.tl.common.WebUtil.getRoot(request) %>";
</script>

 
<!-- script -->
<%@include file="../inc/script.inc"%>
<!-- script -->
 <script type="text/javascript" src="../js/bootstrap/js/bootstrap.min.js"></script>
<script src = "../user/recruit/script/recruitSubscibe.js"></script>
<!-- footer -->
<%@include file="../../inc/footer.inc"%>
<!-- footer -->   
</body>
</html>