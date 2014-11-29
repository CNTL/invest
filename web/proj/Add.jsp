<%@ include file="../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><c:out value="${title}"/></title>
	<meta name="keywords" content="<c:out value="${keywords}"/>" />
    <meta name="description" content="<c:out value="${description}"/>" />
    <link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" type="text/css" href="../static/css/reset.css" />
    <link rel="stylesheet" type="text/css" href="../static/css/index.css" />
	 <link rel="stylesheet" type="text/css" href="../proj/css/project.css" />
	 <link rel="stylesheet" type="text/css" href="../js/plugin/uploadify-3.2.1/uploadify.css"/>
    <script type="text/javascript" src="../static/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="../static/js/common.js"></script>
	<script type="text/javascript" src="../js/utils.js"></script>
	<script type="text/javascript" src="../static/ckeditor/ckeditor.js"></script>
	<script type="text/javascript" src="../js/plugin/uploadify-3.2.1/jquery.uploadify.js"></script>
	<script type="text/javascript" src="../proj/script/datas.js"></script>
	<script type="text/javascript" src="../proj/script/project.js"></script>
</head>
<body>
	<%@include file="../inc/header.inc"%>
	<div class="banner">
        <img src="../static/image/banner2.png" />
    </div>
	<div class="job_add">
        <a href="job_add.html" class="add">+ 发布职位</a>
        <form action="" method="POST">
            <h2>发起项目</h2>
			<div class="select">
				<input type="hidden" name="proj_type" id="proj_type" value="0" />
				<ul id="proj_type_select" style="margin-left: 80px;">
					<li data="0" class="current">个人</li>
					<li data="1">机构</li>
				</ul>
			</div>
            <div class="input">
                <label>项目名称：</label><input type="text" id="proj_name" name="proj_name" value="" />
            </div>
            <div class="input">
                <label>地域：</label>
                <select id="proj_province" name="proj_province">
                    <option value="">省份</option>
                    <option value="1">选项一</option>
                </select>
                <select id="proj_city" name="proj_city">
                    <option value="">城市</option>
                    <option value="1">选项一</option>
                </select>
                <select id="proj_county" name="proj_county">
                    <option value="">地区</option>
                    <option value="1">选项一</option>
                </select>
            </div>
            <div class="input">
				<label>众筹金额：</label>
				<input type="text" id="proj_amountGoal" name="proj_amountGoal" value="" />
            </div>
            <div class="input">
               <label>众筹期限：</label>
				<select id="proj_timeType" name="proj_timeType" style="display:none;">
					<option value="1" selected="selected">天</option>
					<option value="2">日期</option>
               </select>
			   <select id="proj_countDay_sel" name="proj_countDay_sel">
					<option value="1" selected="selected">一个月</option>
					<option value="1.5">一个半月</option>
					<option value="2">两半月</option>
					<option value="3">三半月</option>
					<option value="0">其他</option>
               </select>
				<input type="text" id="proj_countDay" style="width:210px;" name="proj_countDay" value="" />
				<span style="margin-left:10px;font-size:18px;">天</span>
            </div>
           <div class="input">
				<table style="width:100%;">
					<tr>
						<td valign="top" style="width:90px;">
							<label>封面图片：</label>
						</td>
						<td>
							<input type="file" name="uploadify" id="uploadify" />
							<input type="hidden" id="queueItemCount" name="queueItemCount" value="0" />
							<input type="hidden" id="proj_imgURL" name="proj_imgURL" value="" />
							<input type="hidden" id="uploadErrorMsg" name="uploadErrorMsg" value="" />
						</td>
					</tr>
				</table>
           </div>
			<div class="input">
				<label>视频地址：</label>
				<input type="text" id="proj_videoURL" name="proj_videoURL" style="width:848px;" value="" />
            </div>
			<div class="input">
                <label>项目简介：</label>
                <div class="text">
                    <textarea name="proj_summary" id="proj_summary" style="width:876px;height:100px;"></textarea>
                </div>
                <div class="clear"></div>
            </div>
            <div class="input">
                <label>项目内容：</label>
                <div class="text">
                    <textarea name="proj_content" id="proj_content"></textarea>
                </div>
                <div class="clear"></div>
            </div>
            <div class="btn">
                <input type="button" id="btnSave" name="btnSave" value="提交信息" />
            </div>
        </form>
    </div>
    
    <script type="text/javascript">
        $(function () {
            var editor = CKEDITOR.replace("proj_content",
				{
					toolbar :
					[
						//加粗     斜体，     下划线      穿过线      下标字        上标字
						['Bold','Italic','Underline','Strike','Subscript','Superscript'],
						//数字列表          实体列表            减小缩进    增大缩进
						['NumberedList','BulletedList','-','Outdent','Indent'],
						//左对齐             居中对齐          右对齐          两端对齐
						['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
						//超链接 取消超链接 锚点
						['Link','Unlink','Anchor'],
						//图片    flash    表格       水平线            表情       特殊字符        分页符
						['Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],
						'/',
						//样式       格式      字体    字体大小
						['Styles','Format','Font','FontSize'],
						//文本颜色     背景颜色
						['TextColor','BGColor'],
						//全屏           显示区块
						['Maximize', 'ShowBlocks','-']
					]
				}
			);
        });
    </script>
	<%@include file="../inc/footer.inc"%>
</body>
</html>