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
</head>
<body>
<%@include file="../../inc/header.inc"%>
<div class="shadow"></div>
<div class="job_add">
	<form class="setting-form" id="form" name="form" action="">
		<!-- <h2>公司介绍</h2> --> 
		<div class="input">
	        <label for="jobName">招聘主题：</label>
	        <input class="form-control validate[maxSize[255],required]" type="text" id="jobName" name="jobName" value="${recruit.jobName}" placeholder="招聘主题"/>
	    </div>
	    <div class="input">
			<table style="width:100%;">
				<tr>
					<td valign="top" style="width:90px;">
						<label>招聘图片：</label>
					</td>
					<td>
						<input type="file" name="uploadify" id="uploadify" />
						<input type="hidden" id="queueItemCount" name="queueItemCount" value="0" />
						<input type="hidden" id="jobPictrue" name="jobPictrue" value="" />
						<input type="hidden" id="uploadErrorMsg" name="uploadErrorMsg" value="" />
					</td>
				</tr>
			</table>
	   </div>
	   <div id="coverIMG_div" style="display:none;position: absolute; z-index: 122; width:150px;height:150px;overflow:hidden;background:#fff;border:1px solid #C7C7C7;">
		</div>
	    <div class="input">
	        <label for="salary">薪资待遇：</label>
	        <input class="form-control validate[maxSize[255]]" type="text" id="salary" name="salary" value="${recruit.salary}" placeholder="薪资待遇"/>
	    </div>
	    <div class="input">
	        <label for="days">工作时长：</label>
	        <input class="form-control validate[maxSize[255]]" type="text" id="days" name="days" value="${recruit.days}" placeholder="工作时长"/>
	    </div>
	    <div class="input">
	        <label for="working">经验要求：</label>
	        <input class="form-control validate[maxSize[255]]" type="text" id="working" name="working" value="${recruit.working}" placeholder="经验要求"/>
	    </div>
	    <div class="input">
	        <label for="eduReq">学历要求：</label>
	        <select id="eduReq" name="eduReq" class="custform-select validate[maxSize[255],required]" value="${recruit.eduReq}" style="width:400px">
		        <option value="1">不限</option>
		        <option value="2">高中</option>
		        <option value="3">技校</option>
		        <option value="4">中专</option>
		        <option value="5">大专</option>
		        <option value="6">本科</option>
		        <option value="7">硕士</option>
		        <option value="8">博士</option>
	        </select>
	    </div>
	    <div class="input">
	        <label for="isFulltime">全职/兼职：</label>
	        <select id="isFulltime" name="isFulltime" class="custform-select validate[maxSize[255],required]" value="${recruit.isFulltime}" style="width:400px">
		        <option value="1">全职</option>
		        <option value="2">兼职</option>
	        </select>
	    </div>
	    <div class="input">
	        <label for="jobAttract">职位诱惑：</label>
	        <input class="form-control validate[maxSize[255],required]" type="text" id="jobAttract" name="jobAttract" value="${recruit.jobAttract}" placeholder="职位诱惑"/>
	    </div>
		<!-- 
		<div class="input">
			<label for="content">招聘内容:</label>
		    <script type="text/plain" id="content" name="content" style="margin-left:100px; width:700px;height:100px;">
 			${recruit.content}
			<br/>岗位职责：
			<br/><br/>任职资格：

			</script>
		</div>
		 -->
		<div class="input">
             <label>招聘内容：</label>
             <div class="text">
                 <textarea id="contentTxt" name="contentTxt" class="form-control validate[maxSize[4000]]"><c:out escapeXml="false" value="${recruit.content}"/></textarea>
                 <input type="hidden" id="content" name="content" value=""/>
             </div>
             <div class="clear"></div>
        </div>
	    <div class="input">
	        <label for="linkman">联系人：</label>
	        <input class="form-control validate[maxSize[255],required]" type="text" id="linkman" name="linkman" value="${recruit.linkman}" placeholder="联系人"/>
	    </div>
	    <div class="input">
	        <label for="linkPhone">联系电话：</label>
	        <input class="form-control validate[maxSize[255],required]" type="text" id="linkPhone" name="linkPhone" value="${recruit.linkPhone}" placeholder="联系电话"/>
	    </div>
	    <div class="input">
	        <label for="linkEmail">联系邮箱：</label>
	        <input class="form-control validate[maxSize[255],custom[email]]" type="text" id="linkEmail" name="linkEmail" value="${recruit.linkEmail}" placeholder="联系邮箱"/>
	    </div>
	    <div class="btn">
        	<input type="submit" id="btnSave" value="提交信息">
        </div>
	</form>
</div>
<!-- script -->
<%@include file="../inc/script.inc"%>
<!-- script -->

<!-- footer -->
<%@include file="../../inc/footer.inc"%>
<script type="text/javascript">
var rootPath = "<%=com.tl.common.WebUtil.getRoot(request) %>";
</script>
<!-- footer -->
<script type="text/javascript" charset="utf-8" src="../user/recruit/script/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="../ueditor/ueditor.all.min.js"> </script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8" src="../ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="../js/plugin/uploadify-3.2.1/jquery.uploadify.js"></script>
<script type="text/javascript" src="../js/utils.js"></script>
<script type="text/javascript" src="../static/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="../user/recruit/script/recruitEdit.js"></script>
</body>
</html>