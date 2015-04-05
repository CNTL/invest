<%@ include file="../../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<%@include file="../inc/csslink.inc"%>

<style>
	a {text-transform:none;text-decoration:none;} 
	a:hover{
		background:#FFB124;
		color:#fff !important;
		cursor:pointer;
	}
  </style>
  <link rel="stylesheet" type="text/css" href="../js/plugin/ProvinceCitySelect/jquery.ProvinceCitySelect.css"/>
 
</head>
<body>
<div id="body-container" style="min-width:980px;">
<%@include file="../../inc/header.inc"%>
<div class="banner hidden-xs">
    <img src="../static/image/banner1.png" />
</div>
<div style="display:none;">
<input type="text" id="city" name="city" value="<c:out value="${city}"/>"/>
<input type="text" id="more" name="more" value="<c:out value="${more}"/>"/>
</div>
<div class="job_list">
    <div class="sider">
    <c:forEach var="type" varStatus="status" items="${types}" >
    	<div class="cate">
	    	<h2>
	    	<a href="../recruit/ListMainSearch.do?a=queryNew&recruitType=view&mainType=3&type=0&key=${type.name}&more=1">
	    		<c:out value="${type.name}"/>
	    	</a>
	    	</h2>
		    <ul>
		    <c:forEach var="subType" varStatus="status" items="${type.subDics}" begin="0" end="9" step="1">
		    	<li>
		    	<a href="../recruit/ListMainSearch.do?a=queryNew&recruitType=view&mainType=3&type=0&key=${subType.name}&more=1">
		    		<c:out value="${subType.name}"/>
		    	</a>
		    	</li>
		    </c:forEach>
		    </ul>
		    <div class="clear"></div>
		    <div class="expand">
                <div class="blank"></div>
                <div class="item">
                	<h3>
                	<a href="../recruit/ListMainSearch.do?a=queryNew&recruitType=view&mainType=3&type=0&key=${type.name}&more=1}">
                		<c:out value="${type.name}"/>
                	</a>
                	</h3>
                	<ul>
                	<c:forEach var="subType" varStatus="status" items="${type.subDics}">
				    	<li>
				    	<a href="../recruit/ListMainSearch.do?a=queryNew&recruitType=view&mainType=3&type=0&key=${subType.name}&more=1">
				    		<c:out value="${subType.name}"/>
				    	</a>
				    	</li>
				    </c:forEach>
				    </ul>
                	<div class="clear"></div>
                </div>
        	</div>
	    </div>
    </c:forEach>
    	<div class="subscribe">
            <h2><a href="../recruit/uesrRecruitSubscibe.do?a=RecruitSubscibe&mainType=3">订阅职位</a></h2>
        </div>
        <div class="subscribe">
            <h2 ><span>地域筛选</span></h2>
            <c:forEach var="city" varStatus="status" items="${cities}" begin="0" end="10" step="1" >
            	<c:choose>
					<c:when test="${status.index%2==0}"><div class="item item_last"></c:when>
					<c:otherwise><div class="item"></c:otherwise>
				</c:choose>
				<c:if test="${city.level==0}">
				<a href="../recruit/ListMain.do?a=queryNew&recruitType=view&mainType=3&type=${type}&more=${more}&province=${city.id}">${city.name}</a>
				</c:if>
				<c:if test="${city.level==1}">
				<a href="../recruit/ListMain.do?a=queryNew&recruitType=view&mainType=3&type=${type}&more=${more}&city=${city.id}">${city.name}</a>
				</c:if>
	                
	            </div>
    		</c:forEach>
    		<div class="item" style="padding-left:30px;padding-top:5px;"><a  id="rec-more" href="javascript:;" style="background:transparent;"></a></div>
            <div class="clear"></div>
        </div>
    </div>
    <div class="main">
    <c:choose>
			<c:when test="${loginUser.id>0 }">
				<div class="top">
					<a href="../resume/myresume.do?infoType=1">我的简历</a>
			   </div>
			    <a class="add" style="cursor:pointer" onclick="jobList.addRecruit();">+ 发布职位</a>
			</c:when>
			<c:otherwise>
				<div class="top">
					<a href="../resume/myresume.do?infoType=1">我的简历</a>
			   </div>
		
			    <a class="add" style="cursor:pointer" href="../resume/myresume.do?infoType=1">+ 发布职位</a>
			</c:otherwise>
		</c:choose>
			
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
                     <input type="button" id="search" name="search" value="搜索" style="height:46px;" />
                 </div>
             </form>
             <div class="clear"></div>
         </div>
         <div class="sort">
         	<c:choose>
				<c:when test="${queryType eq 'queryHot'}">
					<a href="#" id="queryNew" onclick="jobList.change(this);">最新职位</a>
	             	<a class="current" href="#" id="queryHot" onclick="jobList.change(this);">热门职位</a>
				</c:when>
				<c:otherwise>
					<a class="current" href="#" id="queryNew" onclick="jobList.change(this);">最新职位</a>
	             	<a href="#" id="queryHot" onclick="jobList.change(this);">热门职位</a>
				</c:otherwise>
			</c:choose>
             
         </div>
         <!-- <div id="main">
         </div> -->
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
				<div class="more">
		         	<a href="../recruit/ListMain.do?a=queryNew&more=1&recruitType=view&mainType=3">查看更多</a>
		         </div>
			</c:otherwise>
		</c:choose>
     </div>
    <div class="clear"></div>
</div>
<!-- footer -->
<%@include file="../../inc/footer.inc"%>
<!-- footer -->    
<!-- script -->
<%@include file="../inc/script.inc"%>
<!-- script -->

<script>
	var typeFlag = "<%=request.getParameter("recruitType") %>";
	var rootPath = "<%=com.tl.common.WebUtil.getRoot(request) %>";
 </script>
<script type="text/javascript" src="../static/js/jQselect.js"></script>
<script type="text/javascript" src="../js/plugin/ProvinceCitySelect/jquery.ProvinceCitySelect.js"></script>
<script src = "../user/recruit/script/recruitList.js"></script>

</body>
</html>