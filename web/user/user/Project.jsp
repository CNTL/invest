<%@ include file="../../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="../../inc/meta.inc"%>
	<link rel="stylesheet" type="text/css" href="../user/css/project.css" />
	<script type="text/javascript" src="../user/script/project.js"></script>
	<script type="text/javascript">
		var webroot = "<c:out value="${rootPath}"/>";
		var menu = "<c:out value="${menu}"/>";
	</script>
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
				<li id="menu_1"><a href="Project.do?m=1">发起的项目</a></li>
				<li id="menu_2"><a href="Project.do?m=2">支持的项目</a></li>
				<li id="menu_3"><a href="Project.do?m=3">收藏的项目</a></li>
            </ul>
		</div>
    </div>
			
	<div class="body-container">
		<div class="content" style="float:left;width:100%;">
			<c:choose>
			<c:when test="${menu==1 || menu==3}">
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
							开始日期：<c:out value="${proj.beginDateStr}"/><br />
							结束日期：<c:out value="${proj.endDateStr}"/>
						</td>
						<td>
							<c:if test="${proj.payType == 1}">
								起拍价<br />
							</c:if>
							<c:if test="${proj.payType == 0}">
								<c:out value="${proj.countDay}"/>天<br />
							</c:if>
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
									<c:when test="${proj.approveStatus==3}">审批未通过</c:when>
									<c:when test="${proj.status==0}">未开始</c:when>
									<c:when test="${proj.status==1}">
									<c:if test="${proj.payType == 0}">
									众筹中
									</c:if>
									<c:if test="${proj.payType == 1}">
									竞拍中
									</c:if>
									</c:when>
									<c:when test="${proj.status==2}">
									<c:if test="${proj.payType == 0}">
										<c:if test="${proj.finishPer>=100}">
												众筹成功
											</c:if>
											<c:if test="${proj.finishPer<100}">
												众筹失败
											</c:if>
									</c:if>
									<c:if test="${proj.payType == 1}">
									     <c:if test="${proj.finishPer>=100}">
												竞拍成功
											</c:if>
											<c:if test="${proj.finishPer<100}">
												竞拍失败
											</c:if>
									</c:if>
									
									</c:when>
									<c:when test="${proj.status==3}">锁定</c:when>
									<c:otherwise>未知</c:otherwise>
								</c:choose>
							</span>
						</td>
						<td>
							<c:choose>
							<c:when test="${menu==1}">
							<c:if test="${proj.deleted==0 && proj.status==0}">
							<a href="../project/Publish.do?id=<c:out value="${proj.id}"/>">修改</a>|
							<a href="javascript:void();" onclick="delProject(<c:out value="${proj.id}"/>)">删除</a>
							<br />							
							</c:if>
							<c:if test="${proj.payType == 0}">
							<a href="javascript:void();" onclick="setStage(<c:out value="${proj.id}"/>)">设置阶段</a>
							</c:if>
							<a href="javascript:void();" onclick="viewSupportList(<c:out value="${proj.id}"/>)">查看支持者</a>
							</c:when>
							
							<c:when test="${menu==3}">
								<a href="javascript:void();" onclick="delFavorite(<c:out value="${proj.id}"/>)">删除</a>
							</c:when>
							</c:choose>
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
							<span class="tcol1">订单编号：<c:out value="${proj.supportId}"/></span>
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
							<c:choose>
							<c:when test="${proj.payType==0}">支持：</c:when>
							<c:otherwise>出价：</c:otherwise>
							</c:choose>
							￥<span style="font-size:16px;color:red;" class="moneyFormat"><c:out value="${proj.amountSupport}"/></span>
						</td>
						<td>
							<c:out value="${proj.countDay}"/>天<br />
							￥<span class="moneyFormat"><c:out value="${proj.amountGoal}"/></span>
						</td>
						<td>
							剩余 <c:out value="${proj.surplus}"/><br />
							<c:choose>
							<c:when test="${proj.payType==1}">当前价格</c:when>
							<c:otherwise>已完成</c:otherwise>
							</c:choose>
							￥<span class="moneyFormat"><c:out value="${proj.amountRaised}"/></span><br />
							已达 <c:out value="${proj.finishPer}"/>%
						</td>
						<td>
							<span class="ftx-03">
								<c:choose>
									<c:when test="${proj.supportStatus==4}">交易关闭</c:when>
									<c:when test="${proj.supportStatus==2}">已支付</c:when>
									<c:when test="${proj.supportStatus==0}">
										<c:choose>
										<c:when test="${proj.payType==1}">
											<c:choose>
												<c:when test="${proj.status==2}">
													<a target="_blank" style="color: #ED5E58;" href="../order/Pay.do?id=<c:out value="${proj.supportId}"/>">
														未支付<br />请前往支付
													</a>
												</c:when>
												<c:when test="${proj.status==0}">未开始</c:when>
												<c:when test="${proj.status==1}">竞拍中</c:when>
												<c:when test="${proj.status==3}">锁定</c:when>
												<c:otherwise>未知</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
										<a target="_blank" style="color: #ED5E58;" href="../order/Pay.do?id=<c:out value="${proj.supportId}"/>">
											未支付<br />请前往支付
										</a>
										</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>未知</c:otherwise>
								</c:choose>
							</span>
						</td>
						<td>
							<c:if test="${proj.payType==0}">
							<a class="returncontent" data="<c:out value="${proj.returnContent}"/>" href="javascript:void();">回报内容</a><br />
							</c:if>
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
	
	<div id="prostage" style="display:none;width:100%;"></div>
	<div id="supportlist" style="display:none;width:100%;"></div>
	
	<%@include file="../../inc/footer.inc"%>
</body>
</html>