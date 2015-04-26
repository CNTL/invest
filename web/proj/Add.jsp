<%@ include file="../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="../inc/meta.inc"%>
	<link rel="stylesheet" type="text/css" href="../js/plugin/jquery-validate/css/validationEngine.jquery.css"/>
	
	<link rel="stylesheet" type="text/css" href="../proj/css/project.css" />	
	<link rel="stylesheet" type="text/css" href="../js/plugin/uploadify-3.2.1/uploadify.css"/>
	<link rel="stylesheet" type="text/css" href="../js/plugin/lhgcalendar/lhgcalendar.bootstrap.css"/>
	<script type="text/javascript" src="../js/json/json2.js"></script>
	<script type="text/javascript" src="../static/ckeditor/ckeditor.js"></script>
	<script type="text/javascript" src="../js/plugin/uploadify-3.2.1/jquery.uploadify.js"></script>
	<script type="text/javascript" src="../js/plugin/jquery-validate/jquery.validate.min.js"></script>
	<script type="text/javascript" src="../js/plugin/jquery-validate/js/jquery.validationEngine.js"></script>
	<script type="text/javascript" src="../js/plugin/jquery-validate/js/languages/jquery.validationEngine-zh_CN.js"></script>
	<script type="text/javascript" src="../js/plugin/lhgcalendar/lhgcalendar.js"></script>
	<script type="text/javascript" src="../proj/script/datas.js"></script>
	<script type="text/javascript" src="../js/plugin/query/jquery.query.js"></script>
	<script type="text/javascript" src="../proj/script/project.js"></script>
	<script type="text/javascript" src="../js/layer/layer.min.js"></script>
	<script type="text/javascript">
		var webroot = "<c:out value="${rootPath}"/>";
		
	</script>
	<style>
	 label{
	 	font-size:16px;
	 }
	 #proj_type_select li{
	 	border-radius:4px;
	 }
	</style>
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
		<form id="form1" action="" method="post" class="form-horizontal">
		<input type="hidden" id="proj_id" name="proj_id" value="<c:out value="${proj_id}"/>" />
		<input type="hidden" id="proj_order" name="proj_order" value="0" />
		<div class="select">
			<input type="hidden" name="proj_type" id="proj_type" value="0" />
			<ul id="proj_type_select" style="margin-left: 80px;">
				<li data="0" class="current">个人</li>
				<li data="1">机构</li>
			</ul>
		</div>
	   <div class="form-group">
			  <label class="col-md-2 control-label" >项目名称：</label>
			  <div class="col-sm-4">
				<input type="text" id="proj_name" name="proj_name" value="" class="validate[maxSize[255],required] form-control"/>
			  </div>
			  <div class="col-sm-1">
			  	<span style="color:red;line-height:30px;">*</span>
			  </div>
	   </div>
	     <div class="form-group" id="bigmvsection" style="display:none;">
			  <label class="col-md-2 control-label" >长片阶段：</label>
			  <div class="col-sm-4">
				<select id="proj_bigmvsection" name="proj_bigmvsection" class="validate[required] form-control" >
					 
				</select>
			  </div>
			  <div class="col-sm-1">
			  	<span style="color:red;line-height:30px;">*</span>
			  </div>
	   </div>
	   <div class="form-group">
			<label  class="col-md-2 control-label">项目地域：</label>
			  <div class="col-sm-2">
				<select id="proj_province" name="proj_province" class="validate[required] form-control" >
					<option value="">省份</option>
					<option value="1">选项一</option>
				</select>
			</div>
			<div class="col-sm-2">
				<select id="proj_city" name="proj_city" class="validate[required] form-control">
					<option value="">城市</option>
					<option value="1">选项一</option>
				</select>
			</div>
			
				<select id="proj_county" name="proj_county" class="validate[required] form-control" style="display:none;">
					<option value="">地区</option>
					<option value="1">选项一</option>
				</select>
			
			<div class="col-sm-1">
			  	<span style="color:red;line-height:30px;">*</span>
			  </div>
		</div>
		 <div class="form-group">
		 	<label  class="col-md-2 control-label">项目类型：</label>
		 	<div class="col-sm-2">
			  	 <select id="proj_payType" name="proj_payType" class="form-control">
					<option value="0">众筹项目</option>
					<option value="1">竞拍项目</option>
				</select>
			</div>
			<div class="col-sm-1">
				<span style="color:red;line-height:30px;">*</span>
			</div>
		 </div>
		<div class="form-group">
			<label id="lbtype"  class="col-md-2 control-label">众筹金额：</label>
			<div class="col-sm-4">
			<input type="text" id="proj_amountGoal" name="proj_amountGoal" value="" class="validate[required,custom[number]] form-control" />
			</div>
			<div class="col-sm-1">
				<span style="color:red;line-height:30px;">*</span>
			</div>
		</div>
		<div id="div_investDate" class="form-group">
		    <label class="col-md-2 control-label">众筹期限：</label>
		 	<div class="col-sm-2" style="display:none;">
				<select id="proj_timeType" name="proj_timeType" style="display:none;" class="validate[required] form-control">
					<option value="1" selected="selected">天</option>
					<option value="2">日期</option>
				</select>
			</div>
			<div class="col-sm-2">
			   <select id="proj_countDay_sel" name="proj_countDay_sel" class="validate[required] form-control">
					<option value="1" selected="selected">一个月</option>
					<option value="1.5">一个半月</option>
					<option value="2">两个月</option>
					<option value="3">三个月</option>
					<option value="0">其他</option>
			   </select>
			</div>
			<div class="col-sm-1">
				<input type="text" id="proj_countDay" name="proj_countDay" value="" class="validate[required,custom[number]] form-control" />
				
			</div>
			<div class="col-sm-1">
			<span style="color:red;line-height:30px;">*</span>
			<span style="line-height:30px;">天</span>
			</div>
		</div>
		<div id="div_jpDate" class="form-group" style="display:none;">
			 
			<label class="col-md-2 control-label">起止时间：</label>
			<div class="col-md-2">
				<input type="text" id="proj_beginDate" style="width:160px;padding:1px;"  name="proj_beginDate" value="" class="validate[required] form-control" /> 
			</div>
			<label class="col-md-1 control-label text-center" style="width:10px;margin-left:10px;">-</label>
			<div class="col-md-2">
				<input type="text" id="proj_endDate"  style="width:160px;padding:1px;" name="proj_endDate" value="" class="validate[required] form-control" />
			</div>
			<div class="col-sm-1">
				<span style="color:red;line-height:30px;">*</span>
			</div>
		</div>
	   <div class="form-group">
		    <label class="col-md-2 control-label">封面图片：</label>
		    <div class="col-md-4">
	  			<input type="file" name="uploadify" id="uploadify" />
				<input type="hidden" id="queueItemCount" name="queueItemCount" value="0" />
				<input type="hidden" id="proj_imgURL" name="proj_imgURL" value="" />
				<input type="hidden" id="uploadErrorMsg" name="uploadErrorMsg" value="" />
		    </div>
		 
	   </div>
		<div class="form-group">
			 
			 <label class="col-md-2 control-label">视频地址：</label>
			 <div class="col-sm-9">
			 	<input type="text" id="proj_videoURL" name="proj_videoURL" class="form-control" value="" />
			 </div>
			
		</div>
		
		<div class="form-group"> 
			<label class="col-md-2 control-label">项目简介：</label>
			<div class=" col-md-9">
				<textarea name="proj_summary" id="proj_summary" class="form-control validate[required,minSize[40]]" rows="3"></textarea>
			</div>
			<div class="col-sm-1">
				<span style="color:red;line-height:30px;">*</span>
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-2"></div>
			<div class="col-sm-9">
			<span class="help-block">内容简介至少40个字符以上。</span>
			</div>
		</div>
		<div class="form-group">
			<label class="col-md-2 control-label">项目内容：</label>
			<div class="col-md-9">
				<textarea name="proj_content" id="proj_content"><c:out escapeXml="false" value="${proj.content}"/></textarea>
			</div>
			
		</div>
		
		<div class="form-group">
		        <label for="myVal" class="col-sm-7 control-label text-center">
		       		<input type="checkbox" id="proj_agreements" name="proj_agreements" checked="checked" /> 提交信息即代表已经同意
		       		<a target="_blank" href="<c:out value="${rootPath}"/>help/Agreement.do" style="color:#55acef;">《项目发起协议》</a>
		        </label>
		      </div>  
		 
		 
		<div class="form-group">
            <div class="col-sm-12 text-center">
				<input type="button" id="btnNext" name="btnNext" class="btn btn-primary" value="下一步" />
            </div>
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
		<div class="form-group">
            <div class="col-sm-12 text-center">
                <input type="button" id="btnPre" name="btnPre" class="btn btn-primary" value="上一步" />
				<input type="button" id="btnSave" name="btnSave" class="btn btn-primary" value="提交信息" />
            </div>
        </div>
		 
	</div>
	<input type="hidden" id="isRealName_h" name="isRealName_h" value="<c:out value="${isRealName}"/>" />
 
    <script type="text/javascript">
        $(function () {
        	//判断是否实名认证
        	var isrealName = $("#isRealName_h").val().toString();
        	if(isrealName!="1"){
        		$.messager.popup("发布项需要进行实名认证。请先进行实名认证。",function(){
        			window.location.href="../user/RelAuth.do?infoType=4";
        		});
        	}
        	
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