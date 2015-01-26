<%@ include file="../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="../inc/meta.inc"%>
	<link rel="stylesheet" type="text/css" href="../js/plugin/jquery-validate/css/validationEngine.jquery.css"/>
	<link rel="stylesheet" type="text/css" href="../proj/css/project.css" />
	<link rel="stylesheet" type="text/css" href="../js/plugin/uploadify-3.2.1/uploadify.css"/>
	<script type="text/javascript" src="../js/json/json2.js"></script>
	<script type="text/javascript" src="../static/ckeditor/ckeditor.js"></script>
	<script type="text/javascript" src="../js/plugin/uploadify-3.2.1/jquery.uploadify.js"></script>
	<script type="text/javascript" src="../js/plugin/jquery-validate/jquery.validate.min.js"></script>
	<script type="text/javascript" src="../js/plugin/jquery-validate/js/jquery.validationEngine.js"></script>
	<script type="text/javascript" src="../js/plugin/jquery-validate/js/languages/jquery.validationEngine-zh_CN.js"></script>
	<script type="text/javascript" src="../proj/script/datas.js"></script>
	<script type="text/javascript" src="../proj/script/project.js"></script>
	<script type="text/javascript" src="../js/layer/layer.min.js"></script>
	<script type="text/javascript">
		var webroot = "<c:out value="${rootPath}"/>";
	</script>
</head>
<body>
	<%@include file="../inc/header.inc"%>
	<div class="banner">
        <img src="../static/image/banner2.png" />
    </div>
	<div id="proj_step1" class="job_add" style="margin-bottom:200px;">
	   <h2>发起项目--基本信息</h2>
	   <div style="width:100%;margin-top:8px;border-top:3px #FFA1AC solid;margin-bottom:8px;"></div>
	   <div id="proj_coverIMG_div" style="display:none;position: absolute; z-index: 122; width: 400px; height: 320px;overflow:hidden;background:#fff;border:1px solid #C7C7C7;">
		</div>
		<form id="form1" action="" method="post">
		<input type="hidden" id="proj_id" name="proj_id" value="<c:out value="${proj_id}"/>" />
		<input type="hidden" id="proj_order" name="proj_order" value="0" />
		<div class="select">
			<input type="hidden" name="proj_type" id="proj_type" value="0" />
			<ul id="proj_type_select" style="margin-left: 80px;">
				<li data="0" class="current">个人</li>
				<li data="1">机构</li>
			</ul>
		</div>
	   <div class="input">
			<label>项目名称：<span style="color:red;">*</span></label><input type="text" id="proj_name" name="proj_name" value="" class="validate[maxSize[255],required]"/>
	   </div>
	   <div class="input">
			<label>地域：<span style="color:red;">*</span></label>
			<select id="proj_province" name="proj_province" class="validate[required]">
				<option value="">省份</option>
				<option value="1">选项一</option>
			</select>
			<select id="proj_city" name="proj_city" class="validate[required]">
				<option value="">城市</option>
				<option value="1">选项一</option>
			</select>
			<select id="proj_county" name="proj_county" class="validate[required]" style="display:none;">
				<option value="">地区</option>
				<option value="1">选项一</option>
			</select>
		</div>
		<div class="input">
			<label>
				<select id="proj_payType" name="proj_payType" style="color:#666;width: 105px;padding-left: 5px;">
					<option value="0">众筹金额</option>
					<option value="1">起拍金额</option>
				</select>
				<span style="color:red;">*</span>
			</label>
			<input type="text" id="proj_amountGoal" name="proj_amountGoal" value="" class="validate[required,custom[number]]" />
		</div>
		<div class="input">
			<label>众筹期限：</label>
			<select id="proj_timeType" name="proj_timeType" style="display:none;" class="validate[required]">
				<option value="1" selected="selected">天</option>
				<option value="2">日期</option>
			</select>
		   <select id="proj_countDay_sel" name="proj_countDay_sel" class="validate[required]">
				<option value="1" selected="selected">一个月</option>
				<option value="1.5">一个半月</option>
				<option value="2">两半月</option>
				<option value="3">三半月</option>
				<option value="0">其他</option>
		   </select>
			<input type="text" id="proj_countDay" style="width:210px;" name="proj_countDay" value="" class="validate[required,custom[number]]" />
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
			<input type="text" id="proj_videoURL" name="proj_videoURL" style="width:828px;" value="" />
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
				<textarea name="proj_content" id="proj_content"><c:out escapeXml="false" value="${proj.content}"/></textarea>
			</div>
			<div class="clear"></div>
		</div>
		<div style="padding-left:80px;font-size:18px;">
			<label for="proj_agreements">
				<input type="checkbox" id="proj_agreements" name="proj_agreements" checked="checked" style="width:18px;height:18px;" />
				提交信息即代表已经同意
				<a href="<c:out value="${rootPath}"/>help/Agreement.do" style="color:blue;" target="_blank">《项目发起协议》</a>及
				<a href="<c:out value="${rootPath}"/>help/Agreement.do" style="color:blue;" target="_blank">《注意事项》</a>
			</label>
		</div>
		<div class="btn">
			<input type="button" id="btnNext" name="btnNext" value="下一步" />
		</div>
		</form>
	</div>
	<div id="proj_step2" class="project_list" style="display:none; margin-bottom:200px;">
		<div style="height: 20px;">
			<h2>发起项目--回报设置</h2>
			<div id="proj_info" class="proj_info" style="font-size:18px">
				[长电影]世界末日2014 | 众筹金额：200000.00 | 众筹期限： 45天
			</div>
		</div>
		<div class="block1">
			<div class="box" id="box_new">
                <div class="box_top"></div>
                <div class="box_main project">
                    <div style="height:238px;text-align:center;padding-top:38px;">
							<a href="javascript:void();" id="proj_newmode">
								<img style="border:0px;" title="增加回报" src="../img/add_gift.png" />
								<br >
								<span style="font-size:18px;">增加回报</span>
							</a>
                    </div>
                    <div class="tool">
                    </div>
                </div>
                <div class="box_bottom"></div>
            </div>
		</div>
		<div class="clear"></div>
		
		<div style="padding-left:80px;font-size:18px;">
			<label for="proj_agreements">
				<input type="checkbox" id="proj_agreements" name="proj_agreements" checked="checked" style="width:18px;height:18px;" />
				提交信息即代表已经同意
				<a href="###" style="color:blue;">《项目发起协议》</a>及
				<a href="###" style="color:blue;">《注意事项》</a>
			</label>
		</div>
		<div class="btn">
			<input type="button" id="btnPre" name="btnPre" value="上一步" />
			<input type="button" id="btnSave" name="btnSave" value="提交信息" />
		</div>
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
					],
					filebrowserImageUploadUrl : webroot + 'CkEditorUpload.do?folder=upload|project' //固定路径
				}
			);
        });
    </script>
	<%@include file="../inc/footer.inc"%>
</body>
</html>