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
<div class="body-container">
	<form class="form-horizontal" role="form"  id="form" name="form" action="">
        <input type="hidden" id="id" name="id" value="${recruit.id}"/>
		<input type="hidden" id="firstType_h" name="firstType_h" value="${recruit.firstType}"/>
		<input type="hidden" id="secondType_h" name="secondType_h" value="${recruit.secondType}"/>
		<input type="hidden" id="province_h" name="province_h" value="${recruit.province}"/>
		<input type="hidden" id="city_h" name="city_h" value="${recruit.city}"/>
		<input type="hidden" id="jobtype_h" name="jobtype_h" value="${recruit.jobType}"/>
		
      		  <div class="form-group">
                    <label for="jobName" class="col-sm-3 control-label">招聘主题：</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control validate[maxSize[255],required]" id="jobName" name="jobName" value="${recruit.jobName}" placeholder="招聘主题">
                    </div>
                    <div class="col-sm-1">
                    <span style="color:red;line-height:30px;">*</span>
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="firstType" class="col-sm-3 control-label">职位：</label>
                    <div class="col-sm-3">
			            <select id="firstType" name="firstType" class="form-control validate[required]"></select>
                    </div>
                    <div class="col-sm-3">
						<select id="secondType" name="secondType" class="form-control validate[required]"></select>
                    </div>
                     <div class="col-sm-1">
                    <span style="color:red;line-height:30px;">*</span>
                    </div>
                </div>
                <div class="form-group">
                      <label id="area" for="province" class="col-sm-3 control-label">工作城市：</label>
                      <div class="col-sm-3">
                           <select id="province" name="province" class="form-control validate[required]" onchange="jobEdit.changeProvince();">
								
							</select>
							 
                      </div>
                      <div class="col-sm-3">
                            
							<select id="city" name="city" class="form-control validate[required]">
							
							</select>
                      </div>
                       <div class="col-sm-1">
                    <span style="color:red;line-height:30px;">*</span>
                    </div>
                  </div>
                
                  <div class="form-group">
                    <label for="uploadify" class="col-sm-3 control-label">招聘图片：</label>
                    
						<input type="hidden" id="queueItemCount" name="queueItemCount" value="0" />
						<input type="hidden" id="jobPictrue" name="jobPictrue" value="${recruit.jobPictrue}" />
						<input type="hidden" id="uploadErrorMsg" name="uploadErrorMsg" value="" />
					<div class="col-sm-2">
						<input type="file" name="uploadify" id="uploadify" />
					</div>
                      <div class="col-sm-6">
                          <div id="coverIMG_div" style="display:none;width:150px;height:150px;overflow:hidden;background:#fff;border:1px solid #C7C7C7;"></div>
                      </div>
                     <div class="col-sm-1">
                    <span style="color:red;line-height:30px;">*</span>
                    </div>
                  </div>
                   <div class="form-group">
                    <label for="jobType" class="col-sm-3 control-label">薪酬类型：</label>
                    <div class="col-sm-3">
			            <select id="jobType" name="jobType" class="form-control validate[required]" onchange="jobEdit.changeJobType();">
			            	<option value="0" selected="selected">总价</option>
			            	<option value="1">月薪</option>
			            </select>
                    </div>
                     <div class="col-sm-1">
                    <span style="color:red;line-height:30px;">*</span>
                    </div>
                     
                </div>
                <div class="form-group">
                    <label for="jobName" class="col-sm-3 control-label">薪资待遇：</label>
                    <div class="col-sm-6">
                        <input class="form-control validate[maxSize[255]]" type="text" id="salary" name="salary" value="${recruit.salary}" placeholder="薪资待遇"/>
                    </div>
                     <div class="col-sm-1">
                    <span style="color:red;line-height:30px;">*</span>
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="jobName" class="col-sm-3 control-label">工作时长：</label>
                    <div class="col-sm-3">
                        <input class="form-control validate[maxSize[255]]" type="text" id="days" name="days" value="${recruit.days}" placeholder="工作时长"/>
                    </div>
                    <label id="jobLength" class="col-sm-1 control-label">（天）</label>
                     <div class="col-sm-1">
                    <span style="color:red;line-height:30px;">*</span>
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="jobName" class="col-sm-3 control-label">经验要求：</label>
                    <div class="col-sm-3">
                         <input class="form-control validate[maxSize[255]]" type="text" id="working" name="working" value="${recruit.working}" placeholder="经验要求"/>
                    </div>
                     <div class="col-sm-1">
                    <span style="color:red;line-height:30px;">*</span>
                    </div>
                </div>
                
                 <div class="form-group">
                    <label for="jobName" class="col-sm-3 control-label">学历要求：</label>
                    <div class="col-sm-3">
                         <select id="eduReq" name="eduReq" class="form-control validate[maxSize[255],required]" value="${recruit.eduReq}">
					        <option value="1">不限</option>
					        <option value="2">高中</option>
					        <option value="3">大专</option>
					        <option value="4">本科</option>
					        <option value="5">硕士</option>
					        <option value="6">博士</option>
				        </select>
                    </div>
                     <div class="col-sm-1">
                    <span style="color:red;line-height:30px;">*</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="isFulltime" class="col-sm-3 control-label">全职/兼职：</label>
                    <div class="col-sm-3">
                         <select id="isFulltime" name="isFulltime" class="form-control validate[maxSize[255],required]" value="${recruit.isFulltime}" >
					        <option value="1">全职</option>
					        <option value="2">兼职</option>
				        </select>
				        
                    </div>
                     <div class="col-sm-1">
                    <span style="color:red;line-height:30px;">*</span>
                    </div>
                </div>
                
                 <div class="form-group">
                    <label for="jobAttract" class="col-sm-3 control-label">职位诱惑：</label>
                    <div class="col-sm-6">
                         <input class="form-control validate[maxSize[255],required]" type="text" id="jobAttract" name="jobAttract" value="${recruit.jobAttract}" placeholder="职位诱惑"/>
                    </div>
                      <div class="col-sm-1">
                    <span style="color:red;line-height:30px;">*</span>
                    </div>
                </div>
                
                 <div class="form-group">
                    <label for="contentTxt" class="col-sm-3 control-label">招聘内容：</label>
                    <div class="col-sm-6">
					<textarea id="contentTxt" name="contentTxt" class="form-control validate[maxSize[4000]]"><c:out escapeXml="false" value="${recruit.content}"/></textarea>
                 	<input type="hidden" id="content" name="content" value=""/>
                    </div>
                     <div class="col-sm-1">
                    <span style="color:red;line-height:30px;">*</span>
                    </div>
                </div>
                  <div class="form-group">
                    <label for="linkman" class="col-sm-3 control-label">联系人：</label>
                    <div class="col-sm-6">
                         <input class="form-control validate[maxSize[255],required]" type="text" id="linkman" name="linkman" value="${recruit.linkman}" placeholder="联系人"/>
                    </div>
                     <div class="col-sm-1">
                    <span style="color:red;line-height:30px;">*</span>
                    </div>
                </div>
                     <div class="form-group">
                    <label for="linkPhone" class="col-sm-3 control-label">联系电话：</label>
                    <div class="col-sm-6">
                         <input class="form-control validate[maxSize[255],required]" type="text" id="linkPhone" name="linkPhone" value="${recruit.linkPhone}" placeholder="联系电话"/>
                    </div>
                     <div class="col-sm-1">
                    <span style="color:red;line-height:30px;">*</span>
                    </div>
                </div>
                    <div class="form-group">
                    <label for="linkPhone" class="col-sm-3 control-label">联系邮箱：</label>
                    <div class="col-sm-6">
                         <input class="form-control validate[maxSize[255],custom[email]]" type="text" id="linkEmail" name="linkEmail" value="${recruit.linkEmail}" placeholder="联系邮箱"/>
                    </div>
                     <div class="col-sm-1">
                    <span style="color:red;line-height:30px;">*</span>
                    </div>
                </div>
                <div class="form-group">
                   <div class="col-sm-12 text-center">
                       <button type="submit" class="btn btn-primary" id="btnSave">保存信息</button>
                   </div>
               </div>
               
      	</form>
	 
</div>
 

<!-- footer -->
<%@include file="../../inc/footer.inc"%>
<!-- footer -->

<!-- script -->
<%@include file="../inc/script.inc"%>
<!-- script -->
<script type="text/javascript">
var rootPath = "<%=com.tl.common.WebUtil.getRoot(request) %>";
</script>
<script type="text/javascript" charset="utf-8" src="../user/recruit/script/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="../ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="../ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="../js/plugin/uploadify-3.2.1/jquery.uploadify.js"></script>
<script type="text/javascript" src="../js/utils.js"></script>
<script type="text/javascript" src="../static/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="../js/json/json2.js"></script>
<script type="text/javascript" src="../proj/script/datas.js"></script>
<script type="text/javascript" src="../user/recruit/script/recruitEdit.js"></script>
<script type="text/javascript" src="../user/recruit/script/datas.js"></script>

</body>
</html>