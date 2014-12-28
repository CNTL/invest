<%@ include file="../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="../inc/meta.inc"%>
	<link rel="stylesheet" type="text/css" href="../js/plugin/jquery-validate/css/validationEngine.jquery.css"/>
	<link rel="stylesheet" type="text/css" href="../proj/css/pay.css" />
	<script type="text/javascript" src="../js/plugin/jquery-validate/jquery.validate.min.js"></script>
	<script type="text/javascript" src="../js/plugin/jquery-validate/js/jquery.validationEngine.js"></script>
	<script type="text/javascript" src="../js/plugin/jquery-validate/js/languages/jquery.validationEngine-zh_CN.js"></script>
	<script type="text/javascript" src="../js/layer/layer.min.js"></script>
	<script type="text/javascript" src="../proj/script/datas.js" ></script>
	<script type="text/javascript" src="../user/user/script/address.js" ></script>
	<script type="text/javascript" src="../proj/script/pay.js"></script>
	<script type="text/javascript">
		var webroot = "<c:out value="${rootPath}"/>";
		var price = "<c:out value="${mode.price}"/>";
	</script>
</head>
<body>
	<%@include file="../inc/header.inc"%>
	<div class="shadow">
    </div>
	<div class="main clearfix">
		<div class="wrap-1024 clearfix">
		<c:choose>
		<c:when test="${error!=''}">
			<c:out value="${error}"/>
		</c:when>
		<c:otherwise>
		<form id="form" name="form" action="" method="post">
		<div class="order-box">
			<div id="confirm-order">
				<div class="pay-tit"><h3>确认收货地址</h3></div>
				<div id="consignee_list" class="pay-con padd20" style="padding-bottom:0px;">
					<div class="addes">
						<input type="hidden" id="pay_login_user_id" value="<c:out value="${loginUser.id}"/>" />
						<table class="dadatable">
							<colgroup>
								<col width="25">
								<col width="575">
								<col width="108">
							</colgroup>
							<tbody id="Js-addrContent">
								<c:forEach var="address" items="${addresses}">
								<tr data-id="<c:out value="${address.id}"/>" data-type="<c:out value="${address.type}"/>" class="Js-addrList">
									<td align="center">
										<c:choose>
											<c:when test="${address.id==defAddress}">
											<input type="radio" id="address_<c:out value="${address.id}"/>" name="address" value="<c:out value="${address.id}"/>" checked="checked" />
											</c:when>
											<c:otherwise>
											<input type="radio" id="address_<c:out value="${address.id}"/>" name="address" value="<c:out value="${address.id}"/>" />
											</c:otherwise>
										</c:choose>
									</td>
									<td class="ads-set">
										<label for="address_<c:out value="${address.id}"/>" style="display:inline;font-weight: normal;">
										<span class="street"><c:out value="${address.province}"/><c:out value="${address.city}"/><c:out value="${address.county}"/><c:out value="${address.detail}"/></span>
										<span><c:out value="${address.zipcode}"/></span> 
										<span><c:out value="${address.recipients}"/></span> 
										<span><c:out value="${address.mphoneNo}"/></span> 
										<c:if test="${address.type==1}">
										<span class="gray">(默认地址)</span>
										</c:if>
										</label>
									</td>
									<td class="edit" style="display: none;"><a data-id="<c:out value="${address.id}"/>" class="green Js-addr-modify" href="javascript:;">修改</a></td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
						<div class="mar-t10 Js-otheraddr pl-30" style="display:block;"> <a id="Js-addr-new" class="green" href="javascript:;">使用新地址</a></div>
					</div>
				</div>
			</div>
		</div>
		<div class="pay-detial">
			<div class="pay-tit"><h3>确认支持详情</h3></div>
			<div class="supdtl">
				<div class="supdtl-head">
					<span class="pos1">项目名称</span>
					<span class="pos2">回报内容</span>
					<span class="pos3">支持金额</span>
					<span class="pos4">配送费用</span>
					<span class="pos5">预计回报发送时间</span>
				</div>
				<div class="supdtl-cont">
					<div class="supdtl-cont-top">
						<span class="pos1">
							<a href="../project/Project.do?id=<c:out value="${proj.id}"/>" target="_blank">
							<c:choose>
								<c:when test="${proj.imgUrl==''}"><img alt="<c:out value="${proj.name}"/>" src="../static/image/temp/pic2.png" /></c:when>
								<c:otherwise><img style="max-width:120px;max-height:80px;" alt="<c:out value="${proj.name}"/>" src="../<c:out value="${proj.imgUrl}"/>" /></c:otherwise>
							</c:choose>
							</a><span><a href="../project/Project.do?id=<c:out value="${proj.id}"/>" target="_blank"><c:out value="${proj.name}"/></a></span></span>
						<span class="pos2"><span class="morecon"><c:out value="${mode.returnContent}"/><!--<br>
						（图为示意，仅供参考）--></span><span class="more" style="text-align:center;">显示全部</span></span>
						<span class="pos3">￥<c:out value="${mode.price}"/></span>
						<span class="pos4">
							<c:choose>
								<c:when test="${mode.freight=='0.00' || mode.freight=='0'}">免费</c:when>
								<c:otherwise>￥<c:out value="${mode.freight}"/></c:otherwise>
							</c:choose>
						</span>
						<span class="pos5"><c:out value="${mode.returntime}"/></span>
					</div>
					
					<div class="order-detail" style="padding: 20px;margin: 15px 0 0 0;">		
						<h6 style="font-size: 18px;padding: 5px 0 15px 0;font-weight: normal;margin: 0;">捐赠金额</h6>	
						<div class="mon-boo clearfix" style="position: relative;display: block;">
							<i class="s-money" style="background: url(../static/image/juanz.png) no-repeat;width: 15px;height: 24px;position: absolute;left: 15px;top: 15px;"></i>
							<input class="price how-much validate[required,custom[number]]" id="custom_price" name="custom_price" type="text" placeholder="请输入金额" maxlength="8"
							 style="float: left;width: 230px;height: 50px;line-height: 30px;border: 1px #fff solid;border-radius: 5px;outline: none;padding: 0 35px;color: #55acef;font-size: 24px;font-weight: bold;"/>
							<div class="m-progressdialog" style="display:none;"><!-- |digits|min -->
								<i class="mokl" style="left: 38%;">38%</i>
								<div class="m-progressdialog-grey">
								<div class="m-progressdialog-navy" data-ed="3000.0000" data-ing="108.0000" style="width: 38%;"></div>
								<div class="m-progressdialog-light" style="width: 4%;"></div>
								</div>
								<span>目前完成<span class="cur-count">4%</span></span>
							</div>
						</div>
					</div>
					
					<div class="supdtl-cont-bot">给项目发起人留言<textarea name="memo" id="memo" type="text"></textarea></div>
				</div>
			</div>
			<div><h3 class="hst">选择支付方式</h3></div>
			<div class="pay-wrap">
				<div class="pay-con" id="Js-select">
					<div class="pay-row" style="border-top-width: medium; border-top-style: none; display: block;">
						<div class="cashier-cach">
							<ul>
								<li class="alipay select">
									<span class="" value="alipay"></span>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="pay-submit">
				<div>总计金额：<span id="span_totalfee">￥<c:out value="${totalFee}"/></span></div>
				<input type="hidden" id="order_totalfee" name="order_totalfee" value="<c:out value="${totalFee}"/>" />
				<input type="submit" class="btn-effect-blue btn-sub w217" value="确认付款">
			</div>
		</div>
		</form>
		</c:otherwise>
		</c:choose>
		</div>
	</div>
	
	<%@include file="../inc/footer.inc"%>
</body>
</html>