<%@ include file="../../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@include file="../../inc/meta.inc"%>
	<script type="text/javascript" src="../js/json/json2.js"></script>
	<script type="text/javascript" src="../static/ckeditor/ckeditor.js"></script>
	<script type="text/javascript">
		var webroot = "<c:out value="${rootPath}"/>";
		$(function () {
			$(".sc_content").each(function(i,n){
				var id = $(this).attr("id");
				var editor = CKEDITOR.replace(id,
					{
						height : 80,
						toolbar :
						[
							//样式     格式     字体    字体大小  加粗   斜体
							['Bold','Italic'],//'Styles','Format','Font','FontSize',
							//表情
							['Smiley']
						]
					}
				);
			});
			
			$("#btnSave").on("click",function(){
				var contents = [];
				$(".comm .item").each(function(){
					var stage = $(this).attr("stage");
					var content = CKEDITOR.instances["sc_content_"+stage].getData();
					contents.push({"stage":stage,"content":content});
				});
				
				var param = {"projId":<c:out value="${proj.id}"/>,"schedules":contents};
				var paramData = {"param":JSON.stringify(param)};
				var loading = -1;				
				$.ajax({type: "POST", url: "../user/ProjectFetcher.do?action=submitstage", async:true, 
					data: paramData,
					dataType:"JSON",
					beforeSend:function(XMLHttpRequest){
						
					},
					success: function(msg){
						if (msg.success) {				
							$.messager.alert("信息","操作成功！");
						} else {
							var err = "保存失败";
					 
							$.messager.alert("信息",err);
						}
					},
					complete: function(XMLHttpRequest, textStatus){
						
					},
					error:function (XMLHttpRequest, textStatus, errorThrown) {
						
					}
				});
			});
        });
	</script>
</head>
<body>
	<div class="project_view" style="width:720px;margin:20px;">
       <div class="main">
			<div class="comm">
				<c:forEach var="stage" items="${stages}">
				<c:if test="${stage.stage.value==0}">
					<div stage="<c:out value="${stage.stage.id}"/>" class="item" style="padding-top:0px;">
						<div class="username" style="padding-left:53px;">
						
							<h1><c:out value="${stage.stage.name}"/></h1><c:out value="${stage.schedule.userName}"/>
						</div>
<!-- 						<div class="time" style="width:80px;line-height:25px;"> -->
<%-- 							<c:out value="${stage.schedule.created}"/> --%>
<!-- 						</div> -->
						<div class="desc" style="padding-top:0px;">
							<textarea class="sc_content" name="sc_content_<c:out value="${stage.stage.id}"/>" id="sc_content_<c:out value="${stage.stage.id}"/>">
								<c:out escapeXml="false" value="${stage.schedule.content}"/>
							</textarea>
						</div>
						<div class="clear"></div>
						<!--<span class="btn">评论(0)</span>-->
					</div>
				</c:if>
				<c:if test="${stage.stage.value>0 && proj.amountRaised>=proj.amountGoal}">
					<div stage="<c:out value="${stage.stage.id}"/>" class="item" style="padding-top:0px;">
						<div class="username" style="padding-left:53px;">
							
							<h1><c:out value="${stage.stage.name}"/></span></h1><c:out value="${stage.schedule.userName}"/>
						</div>
<!-- 						<div class="time" style="width:80px;line-height:25px;"> -->
<%-- 							<c:out value="${stage.schedule.created}"/> --%>
<!-- 						</div> -->
						<div class="desc" style="padding-top:0px;">
							<textarea class="sc_content" name="sc_content_<c:out value="${stage.stage.id}"/>" id="sc_content_<c:out value="${stage.stage.id}"/>">
								<c:out escapeXml="false" value="${stage.schedule.content}"/>
							</textarea>
						</div>
						<div class="clear"></div>
						<!--<span class="btn">评论(0)</span>-->
					</div>
				</c:if>
				</c:forEach>
			</div>
		</div>
	</div>
	<div id="toolbars" class="job_add" style="margin:0;text-align:center;border: 1px solid #D5D5D5;background:#EBEBEB;font-size: 14px;;line-height:35px;z-index:9999; position:fixed; bottom:0; left:0; width:100%; _position:absolute;_top: expression_r(documentElement.scrollTop + documentElement.clientHeight-this.offsetHeight); overflow:visible;">
		<div class="btn">
			<input type="button" id="btnSave" name="btnSave" value="保存"  class="btn btn-primary" />
		</div>
	</div>
</body>
</html>