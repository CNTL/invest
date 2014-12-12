<%@ include file="../../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="../../inc/meta.inc"%>
	<script type="text/javascript" src="../js/layer/layer.min.js"></script>
	<script type="text/javascript">
		var webroot = "<c:out value="${rootPath}"/>";
		$(function(){
			var menu = "<c:out value="${menu}"/>";
			var menuid = "menu_"+menu;
			$("#"+menuid+" a").addClass("current");
			
			$(".returncontent").on("mouseover",function(){
				layer.tips($(this).attr("data"), this,
					{style: ['background-color:#78BA32; color:#fff', '#78BA32'],
					maxWidth:250,
					guide:0,
					time: 0,
					closeBtn:false
					}
				)
			}).on("mouseout",function(){
				layer.closeTips()
			});
			
			$(".address").on("mouseover",function(){
				var html = "<span style=\"margin-right:8px;\">收件人:</span>"+$(this).attr("user")+"<br />";
				html += "<span style=\"margin-right:8px;\">联系电话:</span>"+$(this).attr("phone")+"<br />";
				html += "<span style=\"margin-right:8px;\">地址:</span>"+$(this).attr("address")+"<br />";
				html += "<span style=\"margin-right:8px;\">邮编:</span>"+$(this).attr("zipcode");
				layer.tips(html, this,
					{style: ['background-color:#78BA32; color:#fff', '#78BA32'],
					maxWidth:250,
					guide:0,
					time: 0,
					closeBtn:false
					}
				)
			}).on("mouseout",function(){
				layer.closeTips()
			});
			
			$(".moneyFormat").each(function(i,n){
				var value = $(this).text();
				if(value){
					if(value.endWith(".00")){
						value = value.substring(0,value.length-3);
						$(this).text(formatMoney(value,0,"",",","."));
					}else{
						$(this).text(formatMoney(value,2,"",",","."));
					}
				}
			});
		});
		function delProject(id){
			if(!id || id<=0) return;
			if(!window.confirm("确定要删除该项目么？？")){
				return false;
			}
			var dataUrl = "../project/ProjectFetcher.do?action=del&id="+id;
			var loading = -1;
			$.ajax({url: dataUrl, async:true, dataType:"json",
				beforeSend:function(XMLHttpRequest){
					loading = layer.msg("正在提交数据...", 0, 16);
				},
				success: function(datas) {
					if(datas.success){
						window.location.reload();
					}
				},
				complete: function(XMLHttpRequest, textStatus){
					layer.close(loading);
				},
				error:function (XMLHttpRequest, textStatus, errorThrown) {
					layer.close(loading);
					layer.alert('数据提交失败！', 3);
				}
			});
		}
	</script>
	<style type="text/css">
		.people_globaltop .wrap .nav li a{padding:0px;}
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
	<%@include file="../../inc/header.inc"%>
	
	<div class="people_globaltop">
        <div class="wrap">
            <a href="../org/BasicInfo.do?infoType=1&mainType=1" class="profile">个人设置</a>
            <div class="avavtar">
                <img src="<c:out value="${userAvatar}"/>" />
            </div>
            <div class="info">
                <h2><c:out value="${userName}"/>
					<span>
						<c:choose>
							<c:when test="${userType==0}">个人</c:when>
							<c:when test="${userType==2}">机构</c:when>
							<c:otherwise>未知</c:otherwise>
						</c:choose>
					</span>
				 </h2>
                <div class="desc">
                    <c:out value="${userIntro}"/><br />
                    姓名：<c:out value="${userName}"/><br />
                    <span>短信息</span>
                </div>
            </div>
            <div class="clear"></div>
            <ul class="nav">
                <li id="menu_1"><a href="Project.do?m=1">发起的项目</a></li>
				 <li id="menu_2"><a href="Project.do?m=2">支持的项目</a></li>
                <!--<li><a href="#">喜欢的项目</a></li>-->
            </ul>
		</div>
    </div>
			
	<div class="people_profile">
		<!--
		<div class="sider">
			<ul>
				<li id="menu_1"><a href="Project.do?m=1">发起的项目</a></li>
				<li id="menu_2"><a href="Project.do?m=2">支持的项目</a></li>
				<li><a href="Project.do?m=3">喜欢的项目</a></li>
			</ul>
		</div>
		-->
		<div class="content" style="float:left;width:100%;">
			<c:choose>
			<c:when test="${menu==1}">
			<table class="tb-void">
				<thead>
					<tr>
						<th>项目信息</th>
						<th>时间</th>
						<th>项目目标</th>
						<th>完成情况</th>
						<th>状态</th>
						<th>操作</th>
					</tr>
				</thead>
				<c:forEach var="proj" items="${projs}">
				<tbody id="tb-<c:out value="${proj.id}"/>">
					<tr class="tr-th">
						<td colspan="6">
							<span class="tcol1"><a href="../project/Project.do?id=<c:out value="${proj.id}"/>" target="_blank"><c:out value="${proj.name}"/></a></span>
							<span class="tcol2"><c:out value="${proj.typeName}"/></span>
							<span class="tcol3"></span>
						</td>
					</tr>
					<tr class="tr-td">
						<td>
							<div class="img-list">
								<a href="../project/Project.do?id=<c:out value="${proj.id}"/>" class="img-box" target="_blank">
									<c:choose>
										<c:when test="${proj.imgUrl==''}"><img src="../static/image/temp/pic2.png" /></c:when>
										<c:otherwise><img src="../<c:out value="${proj.imgUrl}"/>" /></c:otherwise>
									</c:choose>
								</a>
							</div>
						</td>
						<td style="padding:10px;text-align:left;">
							创建时间：<c:out value="${proj.created}"/><br />
							开始日期：<c:out value="${proj.beginDate}"/><br />
							结束日期：<c:out value="${proj.endDate}"/>
						</td>
						<td>
							<c:out value="${proj.countDay}"/>天<br />
							￥<span class="moneyFormat"><c:out value="${proj.amountGoal}"/></span>
						</td>
						<td>
							剩余 <c:out value="${proj.surplus}"/><br />
							已完成 ￥<span class="moneyFormat"><c:out value="${proj.amountRaised}"/></span><br />
							已达 <c:out value="${proj.finishPer}"/>%
						</td>
						<td>
							<span class="ftx-03">
								<c:choose>
									<c:when test="${proj.deleted==1}">已删除</c:when>
									<c:when test="${proj.approveStatus==0}">未审批</c:when>
									<c:when test="${proj.approveStatus==2}">审批未通过</c:when>
									<c:when test="${proj.status==0}">未开始</c:when>
									<c:when test="${proj.status==1}">众筹中</c:when>
									<c:when test="${proj.status==2}">众筹结束</c:when>
									<c:when test="${proj.status==3}">锁定</c:when>
									<c:otherwise>未知</c:otherwise>
								</c:choose>
							</span>
						</td>
						<td>
							<c:if test="${proj.deleted==0 && proj.status==0}">
							<a href="../project/Publish.do?id=<c:out value="${proj.id}"/>">修改</a>|
							<a href="javascript:void();" onclick="delProject(<c:out value="${proj.id}"/>)">删除</a>
							</c:if>
						</td>
					</tr>
				</tbody>
				</c:forEach>
			</table>
			</c:when>
			<c:when test="${menu==2}">
			<table class="tb-void">
				<thead>
					<tr>
						<th>项目信息</th>
						<th>支持时间</th>
						<th>项目目标</th>
						<th>完成情况</th>
						<th>状态</th>
						<th>操作</th>
					</tr>
				</thead>
				<c:forEach var="proj" items="${projs}">
				<tbody id="tb-<c:out value="${proj.id}"/>">
					<tr class="tr-th">
						<td colspan="6">
							<span class="tcol1">订单编号：<c:out value="${proj.isPaid}"/></span>
							<span class="tcol1"><a href="../project/Project.do?id=<c:out value="${proj.id}"/>" target="_blank"><c:out value="${proj.name}"/></a></span>
							<span class="tcol2"><c:out value="${proj.userName}"/></span>
							<span class="tcol3"></span>
						</td>
					</tr>
					<tr class="tr-td">
						<td>
							<div class="img-list">
								<a href="../project/Project.do?id=<c:out value="${proj.id}"/>" class="img-box" target="_blank">
									<c:choose>
										<c:when test="${proj.imgUrl==''}"><img src="../static/image/temp/pic2.png" /></c:when>
										<c:otherwise><img src="../<c:out value="${proj.imgUrl}"/>" /></c:otherwise>
									</c:choose>
								</a>
							</div>
						</td>
						<td style="padding:10px;text-align:left;">
							<c:out value="${proj.supportCreated}"/><br />
							支持：￥<span style="font-size:16px;color:red;" class="moneyFormat"><c:out value="${proj.amountSupport}"/></span>
						</td>
						<td>
							<c:out value="${proj.countDay}"/>天<br />
							￥<span class="moneyFormat"><c:out value="${proj.amountGoal}"/></span>
						</td>
						<td>
							剩余 <c:out value="${proj.surplus}"/><br />
							已完成 ￥<span class="moneyFormat"><c:out value="${proj.amountRaised}"/></span><br />
							已达 <c:out value="${proj.finishPer}"/>%
						</td>
						<td>
							<span class="ftx-03">
								<c:choose>
									<c:when test="${proj.isPaid==1}">已支付</c:when>
									<c:when test="${proj.isPaid==0}">
										<a target="_blank" style="color: #ED5E58;" href="../order/Pay.do?id=<c:out value="${proj.isPaid}"/>">
											未支付<br />请前往支付
										</a>
									</c:when>
									<c:otherwise>未知</c:otherwise>
								</c:choose>
							</span>
						</td>
						<td>
							<a class="returncontent" data="<c:out value="${proj.returnContent}"/>" href="javascript:void();">回报内容</a><br />
							<a class="address" user="<c:out value="${proj.recipients}"/>" phone="<c:out value="${proj.telphone}"/>"
								address="<c:out value="${proj.address}"/>" zipcode="<c:out value="${proj.zipcode}"/>" href="javascript:void();">送货地址</a>
						</td>
					</tr>
				</tbody>
				</c:forEach>
			</table>
			</c:when>
			<c:otherwise></c:otherwise>
			</c:choose>
		</div>
		<div class="clear"></div>
		<div class="pager">
			<span class="count" title="总记录数"><c:out value="${projCount}"/> 条</span>
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
	
	<%@include file="../../inc/footer.inc"%>
</body>
</html>