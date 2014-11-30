<%@ include file="../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="../inc/meta.inc"%>
	<link rel="stylesheet" type="text/css" href="../proj/css/project.css" />
	<link rel="stylesheet" type="text/css" href="../js/plugin/uploadify-3.2.1/uploadify.css"/>
	<script type="text/javascript" src="../static/ckeditor/ckeditor.js"></script>
	<script type="text/javascript" src="../js/plugin/uploadify-3.2.1/jquery.uploadify.js"></script>
	<script type="text/javascript" src="../proj/script/datas.js"></script>
	<script type="text/javascript" src="../proj/script/project.js"></script>
	<script type="text/javascript" src="../proj/script/msg.js"></script>
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
		<div style="padding-left:80px;font-size:18px;">
			<label for="proj_agreements">
				<input type="checkbox" id="proj_agreements" name="proj_agreements" checked="checked" style="width:18px;height:18px;" />
				提交信息即代表已经同意
				<a href="###" style="color:blue;">《项目发起协议》</a>及
				<a href="###" style="color:blue;">《注意事项》</a>
			</label>
		</div>
		<div class="btn">
			<input type="button" id="btnNext" name="btnNext" value="下一步" />
		</div>
	</div>
	<div id="proj_step2" class="project_list" style="display:none; margin-bottom:200px;">
		<div style="height: 20px;">
			<h2>发起项目--回报设置</h2>
			<div id="proj_info" class="proj_info" style="font-size:18px">
				[长电影]世界末日2014 | 众筹金额：200000.00 | 众筹期限： 45天
			</div>
		</div>
		<div class="block1">
			<div class="box">
                <div class="box_top"></div>
                <div class="box_main project">
                    <div>
                        <div class="title">
								<span style="float:left;">金额：<a href="javascript:void();">10000.00</a></span>
								<span style="float:right;">限额：<a href="javascript:void();">30</a></span>
                        </div>
							<div class="pic">
								<a href="javascript:void();"><img src="../img/gift.png" /></a>
							</div>
                        <div class="desc">
                            关注中国留守儿童生存状态,中国留守儿童5800万生存状态。需关注改编自徐皓峰的原著小说，小说从20年代到抗战前，讲述小道士何安下16岁修道下山的成长和习武经历，呈现太极与道、佛、儒等诸家的联系。徐皓峰是如今小有盛名的武侠小说家，也是《一代宗师》的编剧之一。
                        </div>
                        <div class="status">
                            回报运费：<span>免费<span>
                        </div>
							<div class="status">
                            回报时间：<span>项目成功结束后30天内<span>
                        </div>
                    </div>
                    <div class="tool">
                        <a href="#" class="delete"></a>
                        <a href="#" class="edit"></a>
                    </div>
                </div>
                <div class="box_bottom"></div>
            </div>
			<div class="box">
                <div class="box_top"></div>
                <div class="box_main project">
                    <div>
                        <div class="title">
								<span style="float:left;">金额：<a href="javascript:void();">10000.00</a></span>
								<span style="float:right;">限额：<a href="javascript:void();">30</a></span>
                        </div>
							<div class="pic">
								<a href="javascript:void();"><img src="../img/gift.png" /></a>
							</div>
                        <div class="desc">
                            关注中国留守儿童生存状态,中国留守儿童5800万生存状态。需关注改编自徐皓峰的原著小说，小说从20年代到抗战前，讲述小道士何安下16岁修道下山的成长和习武经历，呈现太极与道、佛、儒等诸家的联系。徐皓峰是如今小有盛名的武侠小说家，也是《一代宗师》的编剧之一。
                        </div>
                        <div class="status">
                            回报运费：<span>免费<span>
                        </div>
							<div class="status">
                            回报时间：<span>项目成功结束后30天内<span>
                        </div>
                    </div>
                    <div class="tool">
                        <a href="#" class="delete"></a>
                        <a href="#" class="edit"></a>
                    </div>
                </div>
                <div class="box_bottom"></div>
            </div>
			<div class="box">
                <div class="box_top"></div>
                <div class="box_main project">
                    <div>
                        <div class="title">
								<span style="float:left;">金额：<a href="javascript:void();">10000.00</a></span>
								<span style="float:right;">限额：<a href="javascript:void();">30</a></span>
                        </div>
							<div class="pic">
								<a href="javascript:void();"><img src="../img/gift.png" /></a>
							</div>
                        <div class="desc">
                            关注中国留守儿童生存状态,中国留守儿童5800万生存状态。需关注改编自徐皓峰的原著小说，小说从20年代到抗战前，讲述小道士何安下16岁修道下山的成长和习武经历，呈现太极与道、佛、儒等诸家的联系。徐皓峰是如今小有盛名的武侠小说家，也是《一代宗师》的编剧之一。
                        </div>
                        <div class="status">
                            回报运费：<span>免费<span>
                        </div>
							<div class="status">
                            回报时间：<span>项目成功结束后30天内<span>
                        </div>
                    </div>
                    <div class="tool">
                        <a href="#" class="delete"></a>
                        <a href="#" class="edit"></a>
                    </div>
                </div>
                <div class="box_bottom"></div>
            </div>
			<div class="box">
                <div class="box_top"></div>
                <div class="box_main project">
                    <div>
                        <div class="title">
								<span style="float:left;">金额：<a href="javascript:void();">10000.00</a></span>
								<span style="float:right;">限额：<a href="javascript:void();">30</a></span>
                        </div>
							<div class="pic">
								<a href="javascript:void();"><img src="../img/gift.png" /></a>
							</div>
                        <div class="desc">
                            关注中国留守儿童生存状态,中国留守儿童5800万生存状态。需关注改编自徐皓峰的原著小说，小说从20年代到抗战前，讲述小道士何安下16岁修道下山的成长和习武经历，呈现太极与道、佛、儒等诸家的联系。徐皓峰是如今小有盛名的武侠小说家，也是《一代宗师》的编剧之一。
                        </div>
                        <div class="status">
                            回报运费：<span>免费<span>
                        </div>
							<div class="status">
                            回报时间：<span>项目成功结束后30天内<span>
                        </div>
                    </div>
                    <div class="tool">
                        <a href="#" class="delete"></a>
                        <a href="#" class="edit"></a>
                    </div>
                </div>
                <div class="box_bottom"></div>
            </div>
			<div class="box">
                <div class="box_top"></div>
                <div class="box_main project">
                    <div style="height:188px;text-align:center;padding-top:38px;">
							<a href="javascript:void();" id="proj_newmode">
								<img style="border:0px;" src="../img/add_gift.png" />
							</a>
                    </div>
                    <div class="tool">
						<!--
                        <a href="#" class="delete"></a>
                        <a href="#" class="edit"></a>
						-->
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
					]
				}
			);
        });
    </script>
	<%@include file="../inc/footer.inc"%>
</body>
</html>