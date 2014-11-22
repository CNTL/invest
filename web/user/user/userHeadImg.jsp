<%@include file="../../include/IncludeTag.jsp"%>
<%@page pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传头像</title>
<link rel="stylesheet" type="text/css" href="../../js/plugin/jquery-validate/css/validationEngine.jquery.css"/>
<link rel="stylesheet" type="text/css" media="screen" href="../../js/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="../../js/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../../js/jquery-easyui/themes/icon.css">
<script type="text/javascript" src="../../js/jquery/jquery.min.js"></script>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/plugin/jquery-validate/js/jquery.validationEngine.js"></script>
<script type="text/javascript" src="../../js/plugin/jquery-validate/js/languages/jquery.validationEngine-zh_CN.js"></script>
<script type="text/javascript" src="script/drag.js"></script>
<script type="text/javascript">
var basePath = "<%=basePath %>";
</script>
<style type="text/css">
	body
	{
		font-size:12px;
		font-family:微软雅黑, 宋体;
	}
	.title
	{
	    border-bottom:solid 1px #ccc;	
	}
	.photocontainer
	{
		margin-top:10px;
		background:url("../../image/bg_120.gif") no-repeat left top;   
		padding:7px 12px 12px 7px;                                        
	}  
	.uploadtooltip
	{
		color: #555;	
	}
	.form-item input { width:90%;}
	.uploadbtn{float:right;margin-top:-35px;}
</style>
</head>
<body>
    <form name="picForm" id="picForm" action="../../user/user.do?a=uploadImg" method="post" enctype="multipart/form-data" 
    	onsubmit="return checkPic();" target="hidden_frame">
		<div style="margin:10px;">
             <div>
                 <div style="margin-top:10px;color: #555;line-height:150%;">请选择照片文件，支持jpg、jpeg、png、gif格式，大小不超过5M。建议尺寸：600 x 450px</div>
                 <div class="form-group form-item">
                 	<input type="file" id="headImg" name="headImg" class="form-control" />
                 	<button id="btnSave" type="submit" class="btn blue uploadbtn">
			           	<i onclick="">上传</i>
			        </button>
                 	<iframe name='hidden_frame' id="hidden_frame" style='display: none'></iframe>
			    </div>
             </div>
             <div id="nowDiv" style="margin-top:20px;">
             	<div class="title"><b>当前头像</b></div>
             	<div class="photocontainer"><img id="nowPhoto" style="width: 150px; height: 150px; margin: 0px" src="headImg/default.bmp"/></div>
             </div>
         </div>
     </form>
     <form name="form" id="form" action="user.do?a=saveImg" method="post" onsubmit="return getcutpos();">
        <div id="hide" style="display: none;margin:10px;">
            <div id="cut_div" style="border: 2px solid #888888; width: 284px; height: 266px; overflow: hidden; position: relative; top: 0px; left: 0px; margin: 4px; cursor: pointer;">
                <table
                    style="border-collapse: collapse; z-index: 10; filter: alpha(opacity = 75); position: relative; left: 0px; top: 0px; width: 284px; height: 266px; opacity: 0.75;"
                    cellspacing="0" cellpadding="0" border="0" unselectable="on">
                    <tr>
                        <td style="background: #cccccc; height: 73px;" colspan="3"></td>
                    </tr>
                    <tr>
                        <td style="background: #cccccc; width: 82px;"></td>
                        <td style="border: 1px solid #ffffff; width: 120px; height: 120px;"></td>
                        <td style="background: #cccccc; width: 82px;"></td>
                    </tr>
                    <tr>
                        <td style="background: #cccccc; height: 73px;" colspan="3"></td>
                    </tr>
                </table>
                <img id="cut_img" style="position: relative; top: -266px; left: 0px" src="file:///D:/a.jpg" />
            </div>
            <table cellspacing="0" cellpadding="0">
                <tr>
                    <td>
                        <img style="margin-top: 5px; cursor: pointer;" src="../../img/head/_h.gif" alt="图片缩小" onmouseover="this.src='../../img/head/_c.gif'"
                            onmouseout="this.src='../../img/head/_h.gif'" onclick="imageresize(false)" />
                    </td>
                    <td>
                        <img id="img_track"  style="width: 250px; height: 18px; margin-top: 5px" src="../../img/head/track.gif" />
                    </td>
                    <td>
                        <img style="margin-top: 5px; cursor: pointer;" src="../../img/head/+h.gif" alt="图片放大" onmouseover="this.src='../../img/head/+c.gif'"
                            onmouseout="this.src='../../img/head/+h.gif'" onclick="imageresize(true)" />
                    </td>
                </tr>
            </table>
            <img id="img_grip" src="../../img/head/grip.gif" 
                style="position: absolute; z-index: 100; left: -1000px; top: -1000px; cursor: pointer;"/>
            <div style="padding-top: 15px; padding-left: 5px;">
            	<input type="hidden" name="MemberID" id="MemberID" value="<c:out value="${memberID}"/>" />
                <input type="hidden" name="DocLibID" id="DocLibID" value="<c:out value="${docLibID}"/>" />
                <input type="hidden" name="cut_pos" id="cut_pos" value="" />
                <input type="hidden" name="cut_url" id="cut_url" value="" />
                <button id="submit" name="submit" type="submit" class="btn blue">
		           	<i onclick="">保存头像</i>
		        </button>
            </div>
        </div>
    </form>
    <script type="text/javascript" src="script/userHeadImg.js"></script>
    <script type="text/javascript" src="script/ImageCopper.js"></script>
</body>
</html>