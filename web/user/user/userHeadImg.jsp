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
</style>
</head>
<body>
<%@include file="../../inc/header.inc"%>
<div class="shadow"></div>
<div class="main clearfix">
	<div class="setting wrap">
			<%@include file="../inc/userHeader.inc"%>
			<div class="setting-detail">
				<div class="job_add">
				    <form name="picForm" id="picForm" action="../user/user.do?a=uploadImg" method="post" enctype="multipart/form-data" 
				    	onsubmit="return checkPic();" target="hidden_frame">
						<div style="margin:10px;">
				             <div>
				                 <div style="margin-top:10px;color: #555;line-height:150%;">请选择照片文件，支持jpg、jpeg、png、gif格式，大小不超过5M。建议尺寸：600 x 450px</div>
				                 <div class="input">
									<table style="width:100%;">
										<tr>
											<td valign="top" style="width:90px;">
												<label>上传头像：</label>
											</td>
											<td>
												<input type="file" name="uploadify" id="uploadify" />
												<input type="hidden" id="queueItemCount" name="queueItemCount" value="0" />
												<input type="hidden" id="headImg" name="headImg" value="" />
												<input type="hidden" id="uploadErrorMsg" name="uploadErrorMsg" value="" />
											</td>
										</tr>
									</table>
							   	</div>
				         </div>
				     </form>
				     <form name="form" id="form" action="../user/user.do?a=saveImg" method="post">
				        <div id="hide">
				            <div class="title"><b>当前头像</b></div>
				            <div id="coverIMG_div" style="position: absolute; z-index: 122; width:150px;height:150px;overflow:hidden;background:#fff;border:1px solid #C7C7C7;">
								<img id="nowPhoto" style="width: 150px; height: 150px; margin: 0px" src="../user/headImg/default.bmp"/>
							</div>
							<br><br><br><br><br><br><br><br>
				            <div class="btn" style="position: absolute;">
				            	<input type="hidden" name="MemberID" id="MemberID" value="<c:out value="${memberID}"/>" />
				                <input type="hidden" name="DocLibID" id="DocLibID" value="<c:out value="${docLibID}"/>" />
				                <input type="hidden" name="cut_pos" id="cut_pos" value="" />
				                <input type="hidden" name="cut_url" id="cut_url" value="" />
						    	<input type="submit" id="submit" value="保存头像">
						    </div>
				        </div>
				    </form>
				</div>
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
<!-- <script type="text/javascript" src="../user/user/script/ImageCopper.js"></script> -->
<script type="text/javascript" src="../js/utils.js"></script>
<script type="text/javascript" src="../user/user/script/userHeadImg.js"></script>
</body>
</html>