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
<style type="text/css">
	.uploadbtn{
		width: 50px;
        height: 40px;
        line-height: 15px;
        border: 0;
        background-color: #4AC4EF;
        color: #fff;
        font-size: 14px;
        cursor: pointer;
	}
	.imgover{
		border:5px solid #ED615B;
	}
</style>
</head>
<body>
<%@include file="../../inc/header.inc"%>
<div class="shadow"></div>
<div class="container container-ex">
	<%@include file="../inc/userHeader.inc"%>
	<div class="row container-wapper">
	<%@include file="../inc/userHeaderMenu.inc"%>
	<div class="col-md-8">
        <div class="container-right">
        	<form class="form-horizontal" role="form"  name="picForm" id="picForm" action="../user/user.do?a=uploadImg" method="post" enctype="multipart/form-data" onsubmit="return checkPic();" target="hidden_frame">
        	 <div class="form-group">
                      <label for="code" class="col-sm-3 control-label">上传头像：</label>
                      <div class="col-sm-6">
                        
                        <input type="file" name="uploadify" id="uploadify" />
						<input type="hidden" id="queueItemCount" name="queueItemCount" value="0" />
						<input type="hidden" id="headImg" name="headImg" value="" />
						<input type="hidden" id="uploadErrorMsg" name="uploadErrorMsg" value="" />
                     </div>
                     <div class="col-sm-offset-2 col-sm-10">
						<span class="help-block" style=" padding-top: 10px;">请选择照片文件，支持jpg、jpeg、png、gif格式，大小不超过5M。建议尺寸：600 x 450px</span>
					</div>
             </div>
               <div class="form-group" id="img-list">
                      <label for="code" class="col-sm-3 control-label">系统头像：</label>
                      <div class="col-sm-3">
                          <img id="default1" class="img-circle" style="width: 150px; height: 150px;" src="../static/image/male_default.png"/>
                      </div>
                      <div class="col-sm-3">
                          <img id="default2" class="img-circle" style="width: 150px; height: 150px;" src="../static/image/female_default.png"/>
                      </div>
                  </div>
        	</form>
        	 <form class="form-horizontal" name="form" id="form" action="../user/user.do?a=saveImg" method="post">
        	  <div class="form-group">
        	     <label for="code" class="col-sm-3 control-label">当前头像：</label>
        	
        	 	<div id="hide" class="col-sm-6" >
				            <div id="coverIMG_div" style="position: absolute; z-index: 122; width:150px;height:150px;overflow:hidden;background:#fff;">
								<img id="nowPhoto" class="img-circle" style="width: 150px; height: 150px;" src="../static/image/temp/avatar2.png"/>
							</div>
							<br><br><br><br><br><br><br><br>
				            <div class="btn" style="position: absolute;">
				            	<input type="hidden" name="MemberID" id="MemberID" value="<c:out value="${memberID}"/>" />
				                <input type="hidden" name="DocLibID" id="DocLibID" value="<c:out value="${docLibID}"/>" />
				                <input type="hidden" name="cut_pos" id="cut_pos" value="" />
				                <input type="hidden" name="cut_url" id="cut_url" value="" />
						    </div>
				 </div>
        	    </div>
        	 <div class="form-group">
                     <div class="col-sm-12 text-center">
                         <button type="submit" class="btn btn-primary" id="submit">保存头像</button>
                     </div>
                 </div>
				        
			</form>
				    
        </div>
     </div>
	</div>
</div>

<!-- script -->
<%@include file="../inc/script.inc"%>
<!-- script -->

<!-- footer -->
<%@include file="../../inc/footer.inc"%>
<!-- footer -->
<script type="text/javascript" src="../user/user/script/drag.js"></script>
<script type="text/javascript">
var rootPath = "<%=com.tl.common.WebUtil.getRoot(request) %>";
</script>
<script type="text/javascript" src="../js/plugin/uploadify-3.2.1/jquery.uploadify.js"></script>
<script type="text/javascript" src="../js/utils.js"></script>
<script type="text/javascript" src="../user/user/script/userHeadImg.js"></script>
</body>
</html>