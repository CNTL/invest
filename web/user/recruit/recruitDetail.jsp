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
</head>
<body>
	<%@include file="../../inc/header.inc"%>
    <div class="shadow"></div>
    <div class="job_view">
        <div class="main">
            <div class="title">
            	<input type="hidden" id="recruitID" name="recruitID" value="${recruit.id}"/>
                <span>${recruit.jobName}</span>
                <a style="cursor:pointer" class="favorite" onclick="resume.collect();"></a>
            </div>
            <div class="desc">
                <ul>
                <c:if test="${recruit.jobType==0}">
                 	<li>总价${recruit.salary}元</li>
                    <li>${recruit.provinceName}/${recruit.cityName}</li>
                    <li>时长${recruit.days}天</li>
                </c:if>
                
                <c:if test="${recruit.jobType==1}">
                 	<li>月薪${recruit.salary}元</li>
                    <li>${recruit.provinceName}/${recruit.cityName}</li>
                    <li>时长${recruit.days}个月</li>
                </c:if>
                   
                </ul>
            </div>
            <div class="date">
                	发布时间：${recruit.time}
 				<a id="report" class="report" href="#">举报该职位</a>
            </div>
            <div class="block">
                <h2>职位描述</h2>
                <div class="content">
                    ${recruit.content}
                </div>
            </div>
            <div class="tip">
                你还没有简历呢，你可以完善在线简历，也可上传附件简历直接投递
            </div>
            <a style="cursor:pointer" class="submit" id="btnSend">投递简历</a>
        </div>
        <div class="sider">
            <div class="desc">
                 
                 <div class="avatar">
                 	 <img src="${user.head}" class="img-circle" style="width:60px;height:60px;" />
                 </div>
                   
                
                <div class="title" style="margin-top:-65px;">
			    	${user.orgFullname}
                </div>
                <div class="clear"></div>
                <div class="intro">
                    <span>规模 ：  ${user.orgScaleName}</span><br/>
                    <span>主页 ： ${user.orgHomePage}</span><br /><br />
                    <span>工作地点 ：</span><br />
                    	${user.location}
                </div>
                <div class="map" style="width:250px;height:200px;" id="allmap">
                </div>
            </div>
            <div class="relate">
                <h2>相似职位</h2>
                <c:forEach var="msg" varStatus="status" items="${simiRecruits}">
                <div class="item">
                    <div class="pic">
                        <a href="../user/recruit.do?a=detail&mainType=3&id=${msg.id}">
	                        <c:choose>
								<c:when test="${msg.jobPictrue==''}"><img src="../static/image/temp/pic2.png" /></c:when>
								<c:otherwise><img src="../<c:out value="${msg.jobPictrue}"/>" /></c:otherwise>
							</c:choose>
						</a>
                    </div>
                    <div class="title">
                        <a href="../user/recruit.do?a=detail&mainType=3&id=${msg.id}">${msg.jobName}</a>
                        <span><c:out value="${msg.company}"/></span>
                    </div>
                    <div class="company">
                        <c:out value="${msg.company}"/>
                    </div>
                    <div class="info">
                        <ul>
                            <li><c:out value="${msg.salary}"/></li>
                            <li><c:out value="${msg.address}"/></li>
                            <li><c:out value="${msg.days}"/></li>
                        </ul>
                    </div>
                    <div class="clear"></div>
                </div>
                </c:forEach>
            </div>
        </div>
        <div class="clear"></div>
    </div>
    <div style="display:none">
    	<input type="text" id="location" name="location" value="${user.location}"/>
    </div>
    <div id="reportwarp" style="display:none;">
    	
      <form id="reportform" role="form" class="form-horizontal">
      <div>若你发现本职位存在违规现象，举报均为匿名，欢迎举报。</div>
         <div class="form-group">
		    <label for="inputEmail3" class="col-sm-2 control-label">举报原因:</label>
		    <div class="col-sm-10">
		       <select class="form-control" id="report_item">
				  <option>薪资不真实</option>
				  <option>工作经验不真实</option>
				  <option>学历要求不真实</option>
				  <option>公司信息不真实</option>
				  <option>其他</option>
				</select>
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="report_content" class="col-sm-2 control-label">详情描述:</label>
		    <div class="col-sm-10">
		       <textarea id="report_content" class="form-control" rows="3"></textarea>
		    </div>
		  </div>
         
      </form>
    </div>
    
	<!-- script -->
	<%@include file="../inc/script.inc"%>
	<!-- script -->
	
	<!-- footer -->
	<%@include file="../../inc/footer.inc"%>
	<!-- footer -->
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=6eea93095ae93db2c77be9ac910ff311"></script>
	<script type="text/javascript" src="../js/layer/layer.min.js"></script>
	<script type="text/javascript" src="../js/utils.js"></script>
	<script type="text/javascript" src="../user/recruit/script/recruitDetail.js"></script>
</body>
</html>