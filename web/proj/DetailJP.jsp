<%@ include file="../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="../inc/meta.inc"%>
	<link rel="stylesheet" type="text/css" href="../js/plugin/jquery-validate/css/validationEngine.jquery.css"/>
	<script type="text/javascript" src="../js/layer/layer.min.js"></script>
	<script type="text/javascript" src="../js/layer/extend/layer.ext.js"></script>
	<script type="text/javascript" src="../proj/script/detail.js"></script>
	<script type="text/javascript" src="../js/plugin/jquery-validate/jquery.validate.min.js"></script>
	<script type="text/javascript" src="../js/plugin/jquery-validate/js/jquery.validationEngine.js"></script>
	<script type="text/javascript" src="../js/plugin/jquery-validate/js/languages/jquery.validationEngine-zh_CN.js"></script>
	<script type="text/javascript">
		var webroot = "<c:out value="${rootPath}"/>";
		var curstage = "<c:out value="${curstage}"/>";
	</script>
	<style type="text/css">
		.project_view .main .content .progress li.s1 {
			background: url(../static/image/p1.png) no-repeat;
		}

		.project_view .main .content .progress li.s2 {
			margin-left: 70px;
			background: url(../static/image/p2.png) no-repeat;
		}

		.project_view .main .content .progress li.s3 {
			margin-left: 70px;
			background: url(../static/image/p3.png) no-repeat;
		}
		
		.project_view .main .content .progress li.s1_1 {
			background: url(../static/image/p1_1.png) no-repeat;
		}

		.project_view .main .content .progress li.s2_1 {
			margin-left: 70px;
			background: url(../static/image/p2_1.png) no-repeat;
		}

		.project_view .main .content .progress li.s3_1 {
			margin-left: 70px;
			background: url(../static/image/p3_1.png) no-repeat;
		}
		.project_view .sider .content .item{
			height:68px;
			width: 100%;
			padding: 10px 0;
			background-color: #F3F3F3;
			margin-bottom: 10px;
		}
		.project_view .sider .content .item .avatar {
			float: left;
			width: 50px;
			height: 50px;
			margin-left: 10px;
		}
		.project_view .sider .content .item .avatar img {
			width: 100%;
			height: 100%;
		}
		.project_view .sider .content .item .username {
			float: left;
			margin-left: 10px;
			height: 50px;
			line-height: 25px;
		}
		.project_view .sider .content .item .msg {
			float: right;
			display: inline-block;
			width: 50px;
			height: 20px;
			text-align: center;
			line-height: 20px;
			font-size: 12px;
			color: #fff;
			margin-right: 10px;
			margin-top: 15px;
			background: url(../image/s31.png) no-repeat;
		}
	</style>
</head>
<body>
	<%@include file="../inc/header.inc"%>
	<div class="shadow">
    </div>
	<div class="project_view">
        <div class="main">
            <div class="content">
                <h2><c:out value="${proj.name}"/></h2>
                <div class="info" style="height:20px;">
					<div class="bdsharebuttonbox" style="width: 200px;float: left;"><a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a><a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a><a href="#" class="bds_renren" data-cmd="renren" title="分享到人人网"></a><a href="#" class="bds_douban" data-cmd="douban" title="分享到豆瓣网"></a><a href="#" class="bds_tqq" data-cmd="tqq" title="分享到腾讯微博"></a><a href="#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a></div>
					<div class="bdshare-button-style0-24" style="float:right;">
						<c:if test="${favorited==0}">
						<a href="javascript:void(0);" onclick="addFavorite(<c:out value="${proj.id}"/>);">收藏</a>
						</c:if>
						<c:if test="${favorited==1}">
						<a href="javascript:void(0);" >已收藏</a>
						</c:if>
					</div>
                </div>
                <div class="progress" style="display:none;">
                    <ul>
                        <li class="s1"></li>
                        <li class="s2"></li>
                        <li class="s3"></li>
                    </ul>
                </div>
                <div class="nav">
                    <ul>
                        <li><a href="javascript:void();" class="current">项目主页</a></li>
                        <!--<li><a href="Support.do?id=<c:out value="${proj.id}"/>">竞拍者(<c:out value="${proj.countSupport}"/>)</a></li>-->
                    </ul>
                </div>
                <div class="desc">
					<p style="text-align:center;"><c:out escapeXml="false" value="${proj.videoUrl}"/></p>
					<c:out escapeXml="false" value="${proj.content}"/>
                </div>
            </div>
            <div class="comm" style="display:none;">
                <h2>项目进度：</h2>
				  <c:forEach var="stage" items="${stages}">
                <div class="item">
                    <div class="username" style="padding-left:53px;">
                        <img src="../static/image/temp/avatar1.png" />
                        <span><c:out value="${stage.stage.name}"/></span><c:out value="${stage.schedule.userName}"/>
                    </div>
                    <div class="time" style="width:80px;line-height:25px;">
                        <c:out value="${stage.schedule.created}"/>
                    </div>
                    <div class="desc">
                        <c:out escapeXml="false" value="${stage.schedule.content}"/>
                    </div>
                    <div class="clear"></div>
                    <!--<span class="btn">评论(0)</span>-->
                </div>
				  </c:forEach>
            </div>
        </div>
        <div class="sider">
<!-- 			<div class="desc info" style="padding: 5px 5px;padding-top:15px;"> -->

					
<!-- 					<div class="avatar" ><span class="f">发起人</span><img style="border-radius: 50%;width:60px;height:60px;" src="../<c:out value="${user.head}"/>" /> <a style="color:#FF6254;" target="_blank" href="../user/PeopleDetailMain.do?a=detail&mainType=4&id=<c:out value="${user.id}"/>"><c:out value="${user.perNickName}"/></a></div> ->
 
<!-- 			</div> -->
			
            <div class="desc">
				<span class="status">
					<c:choose>
						<c:when test="${proj.status==0}">未开始</c:when>
						<c:when test="${proj.status==1}">竞拍中</c:when>
						<c:when test="${proj.status==2}">竞拍结束</c:when>
						<c:when test="${proj.status==3}">锁定</c:when>
						<c:otherwise>未知</c:otherwise>
					</c:choose>
				</span>
				<h2 style="margin-top:30px;">&nbsp;&nbsp;&nbsp;发起人：<div class="avatar" style="margin-left:10px;display:inline;"><img style="border-radius: 50%;width:60px;height:60px;" src="../<c:out value="${user.head}"/>" /> <a style="color:#FF6254;" target="_blank" href="../user/PeopleDetailMain.do?a=detail&mainType=4&id=<c:out value="${user.id}"/>"><c:out value="${user.perNickName}"/></a></div></h2>
				<h2>项目地域：&nbsp;&nbsp;<c:out value="${proj.provinceName}"/>/<c:out value="${proj.cityName}"/></h2>
                <h2>目前金额：</h2>
                <div class="money">￥ 
                <span class="moneyFormat">
	              <c:if test="${proj.amountRaised<=0}" >
	              	<c:out value="${proj.amountGoal}"/>
	              </c:if>
	              <c:if test="${proj.amountRaised>0}" >
	              	<c:out value="${proj.amountRaised}"/>
	              </c:if>
                </span></div>
                <div class="tip">
                    此项目在 <span><c:out value="${proj.endDateStr}"/></span> 结束<br />
                    起拍金额为 ¥<span class="moneyFormat"><c:out value="${proj.amountGoal}"/></span>
                </div>
<!--                 <div class="progress"> -->
<%--                     <div class="now" style="width: <c:out value="${finishPer}"/>%;"></div> --%>
<!--                 </div> -->
<%--                 <div class="perc"><c:out value="${finishPer}"/>%</div> --%>
                <div class="clear"></div>
                <div class="info">
                    <ul>
                        <li><span><c:out value="${surplus}"/></span><br />剩余时间</li>
                        <li><span id="jpcount"><c:out value="${proj.countSupport}"/></span><br />竞拍者</li>
                        <li class="last"><span><c:out value="${proj.countLove}"/></span><br />收藏</li>
                    </ul>
                </div>
            </div>
			<div class="support">
				<div class="top">剩余时间</div>
				<div id="JP_CountDown" class="content" style="font-size:28px;font-weight:bolder;text-align:center;">
				</div>
				<script type="text/javascript">
				function displayCountDownTime(){
					if($("#JP_CountDown").length>0){
						var elt = document.getElementById("JP_CountDown");
						var beginTime = new Date("<c:out value="${proj.beginDateStr}"/>");
						var endTime = new Date("<c:out value="${proj.endDateStr}"/>");
						
						var now = new Date();
						var leftTime = endTime.getTime() -now.getTime();
						var beginDiffTime = now.getTime() - beginTime.getTime();
						if(beginDiffTime < 0){
							elt.innerHTML = "竞拍未开始<br /><div style='font-size:14px;font-weight:normal;margin-top:10px;'>开始时间：<c:out value="${proj.beginDateStr}"/></div>";
						}else if(leftTime<0){
							elt.innerHTML = "竞拍已结束";
							$("#clickprice").empty();
							$("#clickprice").remove();
						}
						else{						
							var ms = parseInt(leftTime%1000).toString();
							leftTime = parseInt(leftTime/1000);
							var o = Math.floor(leftTime / 3600);
							var d = Math.floor(o/24);
							var m = Math.floor(leftTime/60%60);
							var s = leftTime%60;
							if(o.toString().length==1){
								o = "0"+o.toString();
							}
							if(m.toString().length==1){
								m = "0"+m.toString();
							}
							if(s.toString().length==1){
								s = "0"+s.toString();
							}
							elt.innerHTML = o + "小时:" + m + "分:" + s + "秒:" + ms.charAt(0);
							setTimeout(displayCountDownTime,100);
						}
					}
				}
				displayCountDownTime();
				</script>
			</div>
			<div class="support" id="clickprice" style="margin-top:15px;">
				<div class="top">
					<c:choose>
					<c:when test="${proj.status==1}">我要竞拍</c:when>
					<c:otherwise></c:otherwise>
					</c:choose>
					<div class="count">已有 <span class="red"><c:out value="${proj.countSupport}"/></span> 次出价</div>
				</div>
				<c:if test="${proj.status==1}">
				<div class="content">
					<form id="jpForm" name="jpForm" action="" onSubmit="return false">
						<i class="s-money" style="font-size:36px;font-weight:bold;color: #FF6254;">¥</i>
						<input id="amountJP" name="amountJP" type="text" class="validate[required,custom[number]]" oldvalue="<c:out value="${proj.amountRaised}"/>" goal="<c:out value="${proj.amountGoal}"/>" value="" 
						style="margin-left:30px;width: 200px;height:40px;line-height:30px;border: 1px #FF6254 solid;border-radius: 4px;outline: none;padding: 0 10px;color: #FF6254;font-size: 16px;"/>
						<button class="btn btn-danger" style="width:120px;font-size:16px;font-weight:bold;" onclick="jingpai(<c:out value="${proj.id}"/>);">出价</button>
						<label for="anonymousJP" style="font-size:16px;line-height:32px;margin-left:50px;padding:10px;">
							<input type="checkbox" id="anonymousJP" name="anonymousJP"  style="width:18px;margin-right:10px;" />匿名
						</label>
					</form>
				</div>
				</c:if>
           </div>
           
            <c:if test="${supportCanpay.userId>0}" >
           <div class="support"  style="margin-top:15px;">
           		<div class="top">
					恭喜您已经竞拍成功
				</div>
				<div class="content text-center">
					<button class="btn btn-danger" style="width:200px;">立即支付</button>
					<p>请在48小时之内完成支付。</p>
					<p>最后支付时间：<c:out value="${supportCanpay.lastpaytimeStr}"/></p>
				</div>
           </div>
            </c:if>
			<div class="support" id="price-list" style="margin-top:15px;margin-bottom:15px;">
				<div id="jplist" class="top">出价记录</div>
				<div id="div_supportRecord" class="content" style="  overflow-y: auto;height: 360px;margin-left:5px;margin-right:5px;">
					<c:forEach var="support" items="${supports}">
					<div class="item">
						<div class="avatar">
							<c:choose>
								<c:when test="${support.userHead==''}"><img style="border-radius: 50%;" src="../static/image/temp/avatar1.png" /></c:when>
								<c:otherwise><img style="border-radius: 50%;" src="../<c:out value="${support.userHead}"/>" /></c:otherwise>
							</c:choose>
						</div>
						<div class="username" style="width:190px;">
							<a target="_blank" 
							<c:choose>
								<c:when test="${support.userHead==''}"></c:when>
								<c:otherwise>href="../user/PeopleDetailMain.do?a=detail&mainType=4&id=<c:out value="${support.userId}"/>"</c:otherwise>
							</c:choose>
							
							
							><c:out value="${support.userName}"/></a><br/>
							出价 <font color="#ff8290">￥<c:out value="${support.amount}"/></font> 元<br/>
<%-- 							<div class="pgs" data-price="<c:out value="${support.amount}"/>" style="width:1%;height:5px;background:#FFA1AC;"></div> --%>
							
						</div>
						<span class="msg" style="display:none;">发私信</span>
						
						<div class="clear"></div>
					</div>
					</c:forEach>
				</div>
				<script type="text/javascript">
				$(function(){
					refreshRecordCount($("#jpcount").text());
					if($("#JP_CountDown").text()=="竞拍已结束"){
						$("#clickprice").empty();
						$("#clickprice").remove();
					}
				});
				function refreshRecordCount(count){
					$("#jplist").html("出价记录&nbsp;&nbsp;<span style=\"color:#FF6254;\">["+count+"次]</span>");
				}
				
				function refreshRecordJP(){
					var dataUrl = "../project/ProjectFetcher.do?action=getjprecord&id=<c:out value="${proj.id}"/>";
					$.ajax({url: dataUrl, async:true, dataType:"json",
						success: function(datas) {
							if(datas.length>0){
								var html = "";
								refreshRecordCount(datas.length);
								for(var i=0;i<datas.length;i++){
									var data = datas[i];
									var headimg = "static/image/temp/avatar1.png";
									var url = "";
									html += "<div class=\"item\">";
									if(data.userHead.toString().length>0){
										headimg = data.userHead;
										url = " href=\"../user/PeopleDetailMain.do?a=detail&mainType=4&id="+data.userId+" \"";
									}
									html += "<div class=\"avatar\"><img style=\"border-radius: 50%;\" src=\"../"+headimg+"\" /></div>";
									html += "<div class=\"username\" style=\"width:190px;\">";
									html += "<a target=\"_blank\" "+url+">"+data.userName+"</a><br/>出价 <font color=\"#ff8290\">￥"+data.amount+"</font> 元<br/>";
									//html += "<div class=\"pgs\" data-price=\""+data.amount+"\" style=\"width:1%;height:5px;background:#FFA1AC;\"></div>";
									html += "</div>";
									html += "<span class=\"msg\" style=\"display:none;\">发私信</span>";
									html += "<div class=\"clear\"></div>";
									html += "</div>";
								};
								$("#div_supportRecord").html(html);
								setTimeout(refreshRecordJP,60000);
							}
						},
						error:function (XMLHttpRequest, textStatus, errorThrown) {
							var error = textStatus;
						}
					});
				}
				setTimeout(refreshRecordJP,10000);
				</script>
			</div>
		</div>
	</div>
	<div class="clear"></div>
    
	<%@include file="../inc/footer.inc"%>
</body>
</html>