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
<link href="../user/photo/css/photo.css" rel="stylesheet" type="text/css"/>
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
       
        	<div class="row">
        		<div class="col-md-4">
        			<input type="button" id="createGroup" name="createGroup" class="btn btn-info" value="创建图册"/>
        			<input type="hidden" id="opType" name="opType" value="edit"/>
        		</div>
        	</div>
        	<div class="row" id="row-form">
        	 <form class="form-horizontal" role="form"  id="form" name="form" action="">
        	
					<div class="form-group">
					    <label for="groupName" class="col-sm-3 control-label">相册名称：</label>
					    <div class="col-sm-6">
					    	<input type="hidden" id="id" name="id" value=""/>
					        <input type="text" id="groupName" name="groupName" class="form-control validate[maxSize[255],required]" value="">
					    </div>
					</div>
					
					<div class="form-group">
					    <label for="groupIntro" class="col-sm-3 control-label">相册描述：</label>
					    <div class="col-sm-6">
					        <textarea class="form-control  validate[maxSize[4000]]" rows="3" id="groupIntro" name="groupIntro" ></textarea>
					    </div>
					</div>
					
					<div class="form-group">
					    <label for="uploadify" class="col-sm-3 control-label">相册封面：</label>
					    <div class="col-sm-6">
					        <input type="file" name="uploadify" id="uploadify" />
					        <input type="hidden" id="queueItemCount" name="queueItemCount" value="0" />
					        <input type="hidden" id="groupPhoto" name="groupPhoto" value="" />
					        <input type="hidden" id="uploadErrorMsg" name="uploadErrorMsg" value="" />
					    </div>
					</div>
					
					<div class="form-group">
					    <label for="coverIMG_div" class="col-sm-3 control-label">封面缩略图：</label>
					    <div class="col-sm-6">
					        <div id="coverIMG_div"  style="display:none; z-index: 122; width:150px;height:150px;"></div>
					    </div>
					</div>
					
					 <div class="form-group">
				         <div class="col-sm-6 text-center">
				             <button type="submit" class="btn btn-primary"  id="btnOK" name="btnOK" >保存</button>
				         </div>
				         <div class="col-sm-6 text-center">
				             <button type="button" class="btn btn-primary" id="btnCancel" name="btnCancel" >取消</button>
				         </div>
				     </div>
				</form>
        	</div>
        	<br/>
        	<div class="row" id="row-list">
        	    <div class="project_list">
        			<div class="block1">
        			</div>
        		</div>
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
<script type="text/javascript">
var rootPath = "<%=com.tl.common.WebUtil.getRoot(request) %>";
</script>
<script type="text/javascript" src="../js/layer/layer.min.js"></script>
<script type="text/javascript" src="../js/plugin/uploadify-3.2.1/jquery.uploadify.js"></script>
<script type="text/javascript" src="../js/utils.js"></script>
<script type="text/javascript" src="../user/photo/script/photoGroupList.js"></script>
</body>
</html>