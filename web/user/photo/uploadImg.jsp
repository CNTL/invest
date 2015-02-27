<%@ include file="../../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
	<title>上传照片</title>
	<meta content="text/html;charset=utf-8" http-equiv="content-type">
	<%@include file="../inc/csslink.inc"%>
	<link rel="stylesheet" type="text/css" href="./css/upload.css"/>
	<link rel="stylesheet" type="text/css" href="./uploadify/uploadify.css"/>
	<%@include file="../inc/script.inc"%>
 
	<script type="text/javascript" src="./uploadify/swfupload.cookies.js"></script>
	 
	<script type="text/javascript" src="./uploadify/jquery.uploadify-3.1.js"></script>
	<script type="text/JavaScript">
	$(function(){
		upload();
	});
	function upload(){
		setTimeout(function(){
		var groupID = $("#groupID").val();
		var sessionid= '${pageContext.session.id}';
        $("#uploadify").uploadify({
            height: 30,
            swf: '../../user/photo/uploadify/uploadify.swf',
            uploader: '../../user/photo.do?a=uploadPhoto&groupID=' + groupID + '&jsessionid=' + sessionid,
            width: 120,
            cancelImg: '../js/plugin/uploadify-3.2.1/uploadify-cancel.png',
            buttonText: '选择图片',
            fileTypeExts: '*.gif;*.jpg;*.jpeg;*.png;*.*',
            fileSizeLimit: 0,
            removeCompleted: true,
            onUploadStart: function(file) {//上传开始时触发（每个文件触发一次）
            	//alert(file);
          	},
          	onUploadError : function(file,errorCode,errorMsg,errorString,swfuploadifyQueue) {//上传文件出错是触发（每个出错文件触发一次）
            	　　 alert( 'id: ' + file.id
            	　　+ ' - 索引: ' + file.index
            	　　+ ' - 文件名: ' + file.name
            	　　+ ' - 文件大小: ' + file.size
            	　　+ ' - 类型: ' + file.type
            	　　+ ' - 创建日期: ' + file.creationdate
            	　　+ ' - 修改日期: ' + file.modificationdate
            	　　+ ' - 文件状态: ' + file.filestatus
            	　　+ ' - 错误代码: ' + errorCode
            	　　+ ' - 错误描述: ' + errorMsg
            	　　+ ' - 简要错误描述: ' + errorString
            	　　+ ' - 出错的文件数: ' + swfuploadifyQueue.filesErrored
            	　　+ ' - 错误信息: ' + swfuploadifyQueue.errorMsg
            	　　+ ' - 要添加至队列的数量: ' + swfuploadifyQueue.filesSelected
            	　　+ ' - 添加至对立的数量: ' + swfuploadifyQueue.filesQueued
            	　　+ ' - 队列长度: ' + swfuploadifyQueue.queueLength);
            	 
            }
        });
		},10);
	}
	function confirmSave(){
		window.close();//关闭窗口
		window.opener.myReload();
		window.parent.myReload();
		window.opener = null;//为了不出现提示框
	}
	</script>
	<style type="text/css">
		.add {
	        position: absolute;
	        top: 45px;
	        left: 200px;
	        width: 100px;
	        height: 40px;
	        text-align: center;
	        line-height: 40px;
	        color: #fff;
	        border: 0;
	        font-size: 20px;
	        background-color: #4AC4EF;
	        text-decoration: none;
	        cursor:pointer;
	    }
	</style>
</head>
<body>
<form method="post" action="">
	<table class="table">
		<caption>上传照片</caption>
		<tr>
			<td>
				<input id="groupID" name="groupID" type="hidden" value="<c:out value="${param.groupID}"/>"/>
				<input type="file" name="uploadify" id="uploadify" />
				<input type="button" name="confirmBtn" id="confirmBtn" class="add" value="确定" onclick="confirmSave();"/>
			</td>
		</tr>
	</table>
</form>
</body>
</html>