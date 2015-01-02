$(document).ready(function () {
	resumeList.init();
});
var resumeList = {
	init : function(){
		$("#addResume").click(resumeList.addResume);
		resumeList.myResume();
	},
	myResume : function(){
		$.ajax({
	        type:"GET", //请求方式  
	        url:"../user/resume.do?a=getMyResumes", //请求路径  
	        cache: false,
	        dataType: 'JSON',   //返回值类型  
	        success:function(data){
	        	if(!data||typeof Object.prototype.toString.call(data) == "[object Array]"||!data.length)return;
	        	var result = data[0];
	        	$(".count").html(result.total);
	        	//$(".current").html(result.curPage);
	        	$("#total").val(result.total);
	        	$("#curPage").val(result.curPage);
	        	$("#pageCount").val(result.pageCount);
	        	$("#length").val(result.length);
	        	$("#userName").val(result.userName);
	        	var messages = result.messages;
	    		if(!messages||typeof Object.prototype.toString.call(messages) == "[object Array]"||!messages.length)return;
				var l = messages.length;
				for (var j = 0; j < l; j++) {
					var resume = messages[j];
					
					var html = '<tr><td><div class="comm">' + resume.name +
					'</div><div class="date">' + resume.createTime +
					'</div></td><td>'+
					'<a style="cursor:pointer" onclick="resumeList.viewResume('+resume.id+');" class="red">预览</a><br />' +
					'<a style="cursor:pointer" onclick="resumeList.modifyResume('+resume.id+');" class="red">修改</a><br />' +
					'<a style="cursor:pointer" onclick="resumeList.deleteResume('+resume.id+');">删除</a></td></tr>';
					$("#mytbody").append(html);
				}
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				   alert("error="+errorThrown);
			}
	    });
	},
	addResume : function(){
		var type = $("#type").val();
		if(type == "resumeList" ){
			window.location.replace("../resume/resumeEdit.do?infoType=1" + "&type=" + type);
		} else if(type == "userResumeList"){
			window.location.replace("../resume/uesrResumeEdit.do?infoType=7" + "&type=" + type);
		}
	},
	deleteResume : function(id){
		$.messager.confirm('消息', '您确定要删除该简历？', function(r){
			if (r){
				var params = {
					"id": id
				};
				$.ajax({
					type: "POST",
					url: "../user/resume.do?a=deleteResume",
					data: $.param(params),
					dataType: "TEXT",
					success : function(data) {
						if(data != null && data == 'ok'){
							$.messager.confirm('消息', '删除简历成功！', function(r){
			    				if (r){
			    					window.location.href=window.location.href; 
			    				}
			    			});
						} else{
							$.messager.alert('消息','删除简历失败！'); 
						}
					}
				});
			}
		});
	},
	modifyResume : function(id){
		var type = $("#type").val();
		if(type == "resumeList" ){
			window.location.replace("../resume/resumeEdit.do?infoType=1&id=" + id  + "&type=" + type);
		} else if(type == "userResumeList"){
			window.location.replace("../resume/uesrResumeEdit.do?infoType=7&id=" + id  + "&type=" + type);
		}
	},
	viewResume : function(id){
		window.open("../resume/resumeDetail.do?infoType=1&id=" + id);
	}
}