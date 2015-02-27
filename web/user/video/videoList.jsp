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
<style>
 .thumbnail{
    width:288px;
    height:330px;
    float:left;
    border:1px solid #e3e3e3;
    margin:5px 0 5px 13px;
}
.thumbnail iframe{
    width:275px;
    height:268px;
}
 .thumbnail embed {
    width:275px;
    height:268px;
}
 .thumbnail p{
   text-align:center;
}
</style>
</head>
<body>
<%@include file="../../inc/header.inc"%>
<div class="shadow"></div>
<div class="container container-ex">
	<%@include file="../inc/userHeader.inc"%>
	<div class="row text-right">
		<div class="col-md-12" >
		<input id="photogroups" name="photogroups" type="button" class="btn btn-info" style="margin:10px;" value="视频组"/>
		<input id="createVideo" name="createVideo" type="button" class="btn btn-info" style="margin:10px;" value="添加视频"/>
		 
     	<input type="hidden" id="opType" name="opType" value="edit"/>
		<input id="groupID" name="groupID" type="hidden" value="<c:out value="${groupID}"/>"/>
		<input id="groupName" name="groupName" type="hidden" value="<c:out value="${groupName}"/>"/>
		
		</div>
     	
     </div>
	<div class="row container-wapper">
	<div class="row" id="row-form">
	<br/>
      	 <form class="form-horizontal" role="form"  id="form" name="form" action="">
      	
			<div class="form-group">
			    <label for="videoName" class="col-sm-3 control-label">视频名称：</label>
			    <div class="col-sm-6">
			    	<input type="hidden" id="id" name="id" value=""/>
			        <input type="text" id="videoName" name="videoName" class="form-control validate[maxSize[255],required]" value="">
			    </div>
			</div>
			
			<div class="form-group" style="display:none;">
			    <label for="uploadify" class="col-sm-3 control-label">视频图片：</label>
			    <div class="col-sm-6">
			        <input type="file" name="uploadify" id="uploadify" />
			        <input type="hidden" id="queueItemCount" name="queueItemCount" value="0" />
			        <input type="hidden" id="photo" name="photo" value="" />
			        <input type="hidden" id="uploadErrorMsg" name="uploadErrorMsg" value="" />
			    </div>
			</div>
			
			<div class="form-group" style="display:none;">
			    <label for="coverIMG_div" class="col-sm-3 control-label">封面缩略图：</label>
			    <div class="col-sm-6">
			        <div id="coverIMG_div"  style="display:none; z-index: 122; width:150px;height:150px;"></div>
			    </div>
			</div>
			
			<div class="form-group">
			    <label for="videoUrl" class="col-sm-3 control-label">视频地址：</label>
			    <div class="col-sm-6">
			         <input type="text" id="videoUrl" name="videoUrl" class="form-control validate[maxSize[500],required]" value="">
			    </div>
			</div>
			
			
			<div class="form-group" style="display:none;">
			    <label for="intro" class="col-sm-3 control-label">视频描述：</label>
			    <div class="col-sm-6">
			        <textarea class="form-control  validate[maxSize[4000]]" rows="3" id="intro" name="intro" ></textarea>
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
		     <div class="form-group" style="margin-left:150px;" >
		      <div class="col-sm-10">
				<h4>视频地址添加方法（以优酷视频为例,其他视频网站方法一样):</h4>
			     <img src="../static/image/videotip.png" style="width:90%;" alt="视频地址" />
			     </div>
			</div>
		</form>
		
     </div>
        	
	<div class="col-md-12" id="row-list">
        <div class="container-right" id="photo-list">
        	 
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
<script type="text/javascript" src="../user/video/script/videoList.js"></script>
</body>
</html>