<%@ include file="../../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<%@include file="../inc/csslink.inc"%>
<link rel="stylesheet" type="text/css" media="screen" href="<c:out value="${rootPath}"/>js/bootstrap/css/bootstrap.min.css">
<style>
	a {text-transform:none;text-decoration:none;} 
	a:hover{
		background:#019875;
		color:#fff !important;
		cursor:pointer;
		font-size:14px !important;
	}
	*{
	 box-sizing:content-box;
	}
	#selected div {
		font-size: 16px;
		color: #393d3f;
		margin-bottom: 13px;
	}
	ul.reset {
		margin: 0;
		padding: 0;
		list-style: none;
	}
	ul.reset {
		margin: 0;
		padding: 0;
		list-style: none;
	}
	#selecteditem ul {
		overflow: hidden;
	}
	#selecteditem li {
		background: #FFB124;
		white-space: nowrap;
		color: #fff;
		margin-bottom: 13px;
		padding: 2px 8px;
		float: left;
		font-size:14px;
	}
	#selecteditem li span {
		float: left;
	}
	#selecteditem li span.select_remove {
		width: 9px;
		height: 9px;
		background: url(../img/xs.png) no-repeat;
		cursor: pointer;
		margin: 8px 0 0 12px;
	}
	.greybg {
		background: #fafafa;
		padding: 20px 20px 10px 20px;
		*padding-bottom:20px: ;
	}
	dl, dd {
		margin: 0;
	}
	#optionslist dt em{ height:0;width:0;overflow: hidden;font-size: 0;line-height: 0; border-width:6px 5px 0; border-style:solid dashed; border-color:#393d3f transparent transparent;-webkit-transition:all 0.4s ease 0s;-moz-transition:all 0.4s ease 0s;-ms-transition:all 0.4s ease 0s;transition:all 0.4s ease 0s; margin-left:10px;*margin-left:7px; position:absolute; margin-top:10px;}
    #optionslist dt em.transform{-webkit-transform:rotate(-90deg);-moz-transform:rotate(-90deg);-ms-transform:rotate(-90deg);transform: rotate(-90deg);-webkit-transition:all 0.4s ease 0s;-moz-transition:all 0.4s ease 0s;-ms-transition:all 0.4s ease 0s;transition:all 0.4s ease 0s;}
	#optionslist dl, #optionslist dt {
		margin-bottom: 10px;
	}
	#optionslist dt {
		font-size: 18px;
		color: #393d3f;
		cursor: pointer;
		position: relative;
		font-weight:normal;
	}
	#optionslist dd div {
		font-size:14px;
		padding: 2px 10px;
		margin-bottom: 5px;
		color: #555;
		cursor: pointer;
		-moz-transition: background-color 0.2s ease-out, color 0.1s ease-out;
		-webkit-transition: background-color 0.2s ease-out, color 0.1s ease-out;
		-ms-transition: background-color 0.2s ease-out, color 0.1s ease-out;
		transition: background-color 0.2s ease-out, color 0.1s ease-out;
	}
	#optionslist dd div:hover{color:#fff; background:#FFDA95;}
	#optionslist dt em {
		height: 0px;
		width: 0px;
		overflow: hidden;
		font-size: 0px;
		line-height: 0;
		border-width: 6px 5px 0;
		border-style: solid dashed;
		border-color: #393d3f transparent transparent;
		-webkit-transition: all 0.4s ease 0s;
		-moz-transition: all 0.4s ease 0s;
		-ms-transition: all 0.4s ease 0s;
		transition: all 0.4s ease 0s;
		margin-left: 10px;
		*margin-left:7px: ;
		position: absolute;
		margin-top: 10px;
	}
	.breakline{height:10px; background: url(../img/breakline.gif) repeat-x;margin-bottom:15px; }
	.workplace{ margin:0 0 15px; line-height:22px;overflow:hidden;}
	.workplace dt{float:left;font-size:14px;}
	.workplace dd,.workplace li{float:left; margin-left:5px;}
	.workplace dd.more,.workplace li.more{padding-right:15px;position:relative;}
	.workplace a{ color:#555; padding:2px 5px; cursor:pointer;font-size:14px;}
	.workplace a:hover{background:#FFB124; color:#fff;}
	.current a{background:#FFB124; color:#fff;padding:2px 5px; cursor:pointer;font-size:14px;}
	.moretransform{height:0;width:0;overflow: hidden;font-size: 0;line-height: 0; border-width:6px 5px 0; border-style:solid dashed; border-color:#393d3f transparent transparent;-webkit-transition:all 0.4s ease 0s;-moz-transition:all 0.4s ease 0s;-ms-transition:all 0.4s ease 0s;transition:all 0.4s ease 0s; margin-left:10px;*margin-left:7px; position:absolute; margin-top:10px;}
	 
	.workplace li.morecity {
		padding-right: 15px;
		position: relative;
	}
	.searchlist_expectCity{border:2px solid #c9cbce;color:#333 !important;width:300px; font-size:14px; background: #fff; position:absolute;z-index:10;float:right;margin:35px 0 0 144px;*float:left;*margin:35px 0 0 -545px;*padding-top:12px;}
    
   .workplace .searchlist_expectCity dt{margin:3px 0 !important;*left:0px !important;}
	 .searchlist_expectCity > span {
		width: 0px;
		height: 0px;
		font-size: 0px;
		overflow: hidden;
		position: absolute;
		
	}
	.searchlist_expectCity > span.bot {
		border-width: 10px;
		border-style: dashed dashed solid;
		border-color: transparent transparent #c8c8c8;
		right: 120px;
		*right:120px: ;
		top: -22px;
	}
	.searchlist_expectCity > span.top {
		border-width: 10px;
		border-style: dashed dashed solid;
		border-color: transparent transparent #ffffff;
		right: 120px;
		*right:120px: ;
		top: -19px;
	}
	#box_expectCity dl {
		min-height: 30px;
		margin: 3px 0;
		padding: 0;
		clear: both;
		overflow: hidden;
		line-height: 28px;
	}
	.workplace .searchlist_expectCity dt {
		margin: 3px 0 !important;
		*left:0px !important: ;
	}
 
	 
	 
	.triangle {
		height: 0px;
		width: 0px;
		overflow: hidden;
		font-size: 0px;
		line-height: 0;
		border-width: 6px 5px 0;
		border-style: solid dashed;
		border-color: #FFB124 transparent transparent;
		-webkit-transition: all 0.4s ease 0s;
		-moz-transition: all 0.4s ease 0s;
		-ms-transition: all 0.4s ease 0s;
		transition: all 0.4s ease 0s;
	}
	.citymore_arrow {
		position: absolute;
		right: 0px;
		top: 10px;
	}
	 
  </style>
   <link rel="stylesheet" type="text/css" href="../js/plugin/ProvinceCitySelect/jquery.ProvinceCitySelect.css"/>
</head>
<body>
<%@include file="../../inc/header.inc"%>
 
<div class="banner hidden-xs">
    <img src="../static/image/banner1.png" />
</div>
<div class="body-container">
 

<div style="display:none;">
<input type="text" id="city" name="city" value="<c:out value="${city}"/>"/>
<input type="text" id="more" name="more" value="<c:out value="${more}"/>"/>
</div>
<div class="job_list">
		<div class="sider">
			<div class="greybg" id="selecteditem">
				<div>已选择</div>
				<ul class="reset">
				</ul>
			</div>
			<div class="greybg" id="optionslist">
			    <dl>
					<dt>
						工作时长<em></em>
					</dt>
					<dd id="Days">
						<div data-value="10">10天</div>
						<div data-value="20">20天</div>
						<div data-value="30">30天</div>
						<div data-value="60">60天</div>
						<div data-value="90">90天</div>
						<div data-value="300">90天以上</div>
					</dd>
				</dl>
				<dl>
					<dt>
						最低学历 <em></em>
					</dt>
					<dd id="Degree">
			        <div data-value="2">高中</div>
			        <div data-value="3">大专</div>
			        <div data-value="4">本科</div>
			        <div data-value="5">硕士</div>
			        <div data-value="6">博士</div>
						 
					</dd>
				</dl>
				<dl>
					<dt>
						工作性质 <em></em>
					</dt>
					<dd id="JobType">
						<div data-value="1">全职</div>
						<div data-value="0">兼职</div>
					</dd>
				</dl>
				<dl>
					<dt>
						发布时间 <em></em>
					</dt>
					<dd id="PubTime">
						<div data-value="0">今天</div>
						<div data-value="3">3天内</div>
						<div data-value="7">一周内</div>
						<div data-value="30">一月内</div>
					</dd>
				</dl>
			</div>
			</div>
			<div class="main">
    
			
        <div class="search">
             <form action="" method="POST">
                 <div class="type">
                     <select name="type" id="type">
                     <c:choose>
						<c:when test="${type==1}">
							<option value="0">职位</option>
                        	<option value="1" selected>公司</option>
						</c:when>
						<c:otherwise>
							<option value="0" selected>职位</option>
                        	<option value="1">公司</option>
						</c:otherwise>
					 </c:choose>
                     </select>
                 </div>
                 <div class="key">
                     <input type="text" id="key" name="key" value="<c:out value="${key}"/>" placeholder="请输入你要搜索的职位，如“制片人”"/>
                 </div>
                 <div class="submit">
                     <input type="button" id="search" name="search" value="搜索" />
                 </div>
             </form>
             <div class="clear"></div>
         </div>
         <div class="breakline">
         
         </div>
         <div id="citylist">
         <dl class="workplace" id="workplaceSelect">
           <dt >工作城市：</dt>
           <dd data-id="-1"><a >全部</a> </dd>
          	<c:forEach var="city" varStatus="status" items="${cities}" begin="0" end="6" step="1" >
          	<c:choose>
					<c:when test="${city.pid==0}"><dd  data-pid="${city.id}" data-id="${city.id}"><a>${city.name}</a> </dd></c:when>
					<c:otherwise><dd  data-pid="${city.pid}.${city.id}"  data-id="${city.id}"><a>${city.name}</a> </dd></c:otherwise>
			</c:choose>
          	 
			</c:forEach>
			<dd class="morecity" id="morecity" style="padding-top:-5px;">
				<a  id="rec-more" href="javascript:;"></a>
		     </dd>   
			</dl>
			 
			
			
         </div> 
         <c:forEach var="msg" varStatus="status" items="${msg.messages}">
				<c:choose>
					<c:when test="${status.index%3==0}"><div class="box box_last"></c:when>
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
                                <li><c:out value="${msg.salary}"/></li>
                                <li><c:out value="${msg.cityName}"/></li>
                                <li><c:out value="${msg.days}"/>天</li>
                            </ul>
                        </div>
                        <div class="desc">
                            <span>职位诱惑：<c:out value="${msg.jobAttract}"/></span><br />
                           	 发布时间：<c:out value="${msg.createtime}"/><br />
                            	已投递简历人数：<c:out value="${msg.resumeNum}"/>人
                        </div>
                    </div>
                    <div class="tool">
                        <a data-url="<c:out value="${rootPath}"/>user/recruit.do?a=detail&mainType=3&id=${msg.id}" class="share">分享</a>
                        <a href="../user/recruit.do?a=detail&mainType=3&id=${msg.id}" class="view"></a>
                    </div>
                </div>
                <div class="box_bottom"></div>
            </div>
		</c:forEach>
         <div class="clear"></div>
         <c:choose>
			<c:when test="${more eq '1'}">
				<div class="pager">
					<span class="count" title="总记录数"><c:out value="${msg.total }"/> 条</span>
					<c:choose>
						<c:when test="${msg.curPage==1}"><a href="javascript:void();" class="prev">上一页</a></c:when>
						<c:otherwise><a href="../recruit/ListMain.do?a=<c:out value="${queryType}"/>&more=1&recruitType=view&mainType=3&type=${type}&key=${key}&curPage=<c:out value="${msg.curPage-1}"/>" class="prev">上一页</a></c:otherwise>
					</c:choose>
					<c:forEach var="x" begin="${msg.pageBegin}" end="${msg.pageEnd}">
						<c:choose>
							<c:when test="${msg.curPage==x}"><a href="javascript:void();" class="current"><c:out value="${x}"/></a></c:when>
							<c:otherwise><a href="../recruit/ListMain.do?a=<c:out value="${queryType}"/>&more=1&recruitType=view&mainType=3&type=${type}&key=${key}&curPage=<c:out value="${x}"/>"><c:out value="${x}"/></a></c:otherwise>
						</c:choose>
					</c:forEach>
					<c:choose>
						<c:when test="${msg.curPage==msg.pageCount}"><a href="javascript:void();" class="prev">下一页</a></c:when>
						<c:otherwise><a href="../recruit/ListMain.do?a=<c:out value="${queryType}"/>&more=1&recruitType=view&mainType=3&type=${type}&key=${key}&curPage=<c:out value="${msg.curPage+1}"/>" class="prev">下一页</a></c:otherwise>
					</c:choose>
				</div>
			</c:when>
			<c:otherwise>
				 
			</c:otherwise>
		</c:choose>
     </div>
    <div class="clear"></div>
</div>
</div>
<input type="hidden" id="hDays" data-id="Days" value="<c:out value="${Days}"/>" />
<input type="hidden" id="hDegree" data-id="Degree"  value="<c:out value="${Degree}"/>" />
<input type="hidden" id="hJobType" data-id="JobType"  value="<c:out value="${JobType}"/>" />
<input type="hidden" id="hPubTime" data-id="PubTime"  value="<c:out value="${PubTime}"/>" />
<input type="hidden" id="hcity" data-id="city"  value="<c:out value="${city}"/>" />
<input type="hidden" id="hcityname" data-id="city"  value="<c:out value="${cityname}"/>" />
<input type="hidden" id="hprovince" data-id="province"  value="<c:out value="${province}"/>" />



<!-- footer -->    
<!-- script -->
<%@include file="../inc/script.inc"%>
<!-- script -->

<script>
	var typeFlag = "<%=request.getParameter("recruitType") %>";
	var rootPath = "<c:out value="${rootPath}"/>";
 </script>

<script type="text/javascript" src="../static/js/jQselect.js"></script>
 <script type="text/javascript" src="../js/bootstrap/js/bootstrap.min.js"></script>
  <script type="text/javascript" src="../js/plugin/query/jquery.query.js"></script>
  <script type="text/javascript" src="../js/plugin/ProvinceCitySelect/jquery.ProvinceCitySelect.js"></script>
<script src = "../user/recruit/script/recruitListSearch.js"></script>
<!-- footer -->
<%@include file="../../inc/footer.inc"%>
</body>
</html>