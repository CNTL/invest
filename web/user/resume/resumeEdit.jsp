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
<link rel="stylesheet" type="text/css" href="../js/plugin/uploadify-3.2.1/uploadify.css"/>
<link rel="stylesheet" type="text/css" href="../user/resume/css/resume.css">
</head>
<body>
<%@include file="../../inc/header.inc"%>
<div class="shadow"></div>
<div class="people_globaltop">
    <div class="wrap">
        <ul class="nav">
            <c:forEach var="gm" items="${iMenus}">
        		<li><a href="<c:out value="${gm.url}"/>" class="<c:out value="${gm.className}"/>"><c:out value="${gm.name}"/></a></li>
			</c:forEach>
        </ul>
    </div>
</div>
<div class="body-container">
<br/>
<form class="form-horizontal" role="form"  id="form" name="form" action="">
        	
        		  <div class="form-group">
                      <label for="name" class="col-sm-2 control-label">简历名称：</label>
                      <div class="col-sm-9">
                           <input type="hidden" id="id" name="id" value="<c:out value="${id}"/>"/>
	        			   <input type="text" id="name" name="name" class="form-control validate[maxSize[255]]" value=""/>
                      </div>
                  </div>
                    <div class="form-group">
                      <label for="height" class="col-sm-2 control-label">身高：</label>
                      <div class="col-sm-2">
	        			   <input type="text" id="height" name="height" class="form-control validate[maxSize[3]]" value=""/> 
	        			   
                      </div>
	                <div class="col-sm-1">
	                	<label class="control-label">厘米</label>
	                </div>
	        		
                       
                  </div>
                    <div class="form-group">
                      <label for="weight" class="col-sm-2 control-label">体重：</label>
                      <div class="col-sm-2">
	        			   <input type="text" id="weight" name="weight" class="form-control validate[maxSize[3]]" value=""/>
	        			   
                      </div>
                       <div class="col-sm-1">
	                	<label class="control-label">公斤</label>
	                </div>
                  </div>
                   <div class="form-group">
                      <label for="school" class="col-sm-2 control-label">毕业学校：</label>
                      <div class="col-sm-9">
	        			   <input type="text" id="school" name="school" class="form-control validate[maxSize[255]]" value=""/>
                      </div>
                  </div>
                    <div class="form-group">
                      <label for="professional" class="col-sm-2 control-label">学习专业：</label>
                      <div class="col-sm-9">
	        			   <input type="text" id="professional" name="professional" class="form-control validate[maxSize[255]]" value=""/>
                      </div>
                  </div>
                   
                   <div class="form-group">
                    <label for="degreeid" class="col-sm-2 control-label">学历：</label>
                    <div class="col-sm-3">
                         <select id="degreeid" name="degreeid" class="form-control validate[maxSize[255],required]" >
					        <option value="2">高中</option>
					        <option value="3">大专</option>
					        <option value="4">本科</option>
					        <option value="5">硕士</option>
					        <option value="6">博士</option>
				        </select>
				        <input type="hidden" id="degree" name="degree" value="高中" />
                    </div>
                </div>
				  
                  
                  
                  <div class="form-group" id="divcontent">
                      <label for="name" class="col-sm-2 control-label">工作经历：</label>
                      <div class="col-sm-9">
                             <textarea  id="contentTxt" name="contentTxt" class="form-control validate[maxSize[4000]]"></textarea>
                 			<input type="hidden" id="content" name="content" value=""/>
                      </div>
                  </div>
                  
                   <div class="form-group" id="divfile">
                    <label for="identityCard" class="col-sm-2 control-label">简历附件：</label>
                    
					<input type="hidden" id="queueItemCount" name="queueItemCount" value="0" />
					<input type="hidden" id="affix" name="affix" value="" />
					<input type="hidden" id="uploadErrorMsg" name="uploadErrorMsg" value="" />
					<div class="col-sm-2">
						<input type="file" name="uploadify" id="uploadify" />
					</div>
                      <div class="col-sm-6">
                          <div id="coverIMG_div" style="display:none;width:150px;height:150px;overflow:hidden;background:#fff;border:1px solid #C7C7C7;"></div>
                      </div>
                  </div>
                  
                  
                  <div class="form-group">
                     <div class="col-sm-12 text-center">
                         <button type="submit" class="btn btn-primary" id="btnSave">保存信息</button>
                     </div>
                 </div>
                 
        	</form>
 
</div>
<div>
<input type="hidden" id="type" name="type" value="<c:out value="${type}"/>" />
</div>			
<!-- script -->
<%@include file="../inc/script.inc"%>
<!-- script -->

<!-- footer -->
<%@include file="../../inc/footer.inc"%>
<!-- footer -->
<script type="text/javascript">
var rootPath = "<%=com.tl.common.WebUtil.getRoot(request) %>";
</script>
<script type="text/javascript" src="../js/plugin/uploadify-3.2.1/jquery.uploadify.js"></script>
<script type="text/javascript" src="../static/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="../user/resume/script/userResume.js"></script>
</body>
</html>