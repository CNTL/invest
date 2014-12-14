$(document).ready(function () {
	myVideo.init();
});
var pagei = null;
var myVideo = {
	init : function(){
		$("#createVideo").click(myVideo.addVideo);
		//$("#btnOK").click(myVideo.btnOK);
		//$("#btnCancel").click(myVideo.btnCancel);
		myVideo.initVideo();
	},
	initVideo : function(){
		var groupID = $("#groupID").val();
		$.ajax({
	        type:"GET", //请求方式  
	        url:"../user/video.do?a=getVideos&groupID=" + groupID, //请求路径  
	        cache: false,
	        dataType: 'JSON',   //返回值类型  
	        success:function(data){
	        	if(!data||typeof Object.prototype.toString.call(data) == "[object Array]"||!data.length)return;
	        	myVideo.assemble(data);
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				   alert(errorThrown);
			}
	    });
	},
	assemble : function(result) {
    	$.each(result, function(i,item){
    		var id = item.id;
    		var photo = item.photo;
    		var video = item.video;
    		if(photo == null || photo.length == 0)
    			photo = "../user/photo/img/framels_hover.jpg";
    		if(video == null || photo.length == 0)
    			video = "";
    		//添加图片的缩略图
    		$("#photos").append($("<div><a target='_black' href='" + video + "'><img name='photoList' id='" + id + "' src='"+photo+"'></a></div>"));
    		
    	});
    	$("#photos div:has(a)").addClass("thumb");
    	
    	$.each(result, function(i,item){
    		var myimg = new Image();
    		myimg.src = $("#photos a img").get(i).src;
    		//根据图片的比例（水平或者竖直），添加不同的样式
    		if(myimg.width > myimg.height)
    			$("#photos div:has(a):eq("+i+")").addClass("ls");
    		else
    			$("#photos div:has(a):eq("+i+")").addClass("pt");
    	});
	},
	saveVideo : function(){
		$.ajax({
	        type:"POST", //请求方式  
	        url:"../user/video.do?a=saveVideo", //请求路径  
	        cache: false,
	        data:$('#form').serialize(),  //传参 
	        dataType: 'text',   //返回值类型  
	        success:function(data){
	    		if(data != null && data.length > 0){
	    			$.messager.confirm('消息', '创建视频成功！', function(r){
	    				if (r){
	    					window.location.href="../user/VideoMa.do?infoType=7&groupID=" + data; 
	    				}
	    			});
	    		} else {
	    			$.messager.alert('创建视频失败！',data);
	    		}
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				$.messager.alert('创建视频组失败！',errorThrown);
			}
	    });
	},
	delVideo : function(id){
		$.ajax({
	        type:"GET", //请求方式  
	        url:"../user/video.do?a=delVideo&id=" + id, //请求路径  
	        cache: false,
	        dataType: 'TEXT',   //返回值类型  
	        success:function(data){
	        	if(data != null && data == 'ok'){
	    			$.messager.confirm('消息', '删除视频成功！', function(r){
	    				if (r){
	    					window.location.href=window.location.href; 
	    				}
	    			});
	    		} else {
	    			$.messager.alert('删除视频失败！',data);
	    		}
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				$.messager.alert('删除视频失败！',errorThrown);
			}
	    });
	},
	addVideo : function(){
		myVideo.openVideoFormDlg("新增", myVideo.getVideoFormHtml(""));
	},
	editVideo : function(id,userId){
		myVideo.openVideoFormDlg("修改", myVideo.getVideoFormHtml(id));
	},
	getVideoFormHtml : function(id){
		if(typeof(id)=="undefined" || !id) id = 0;
		if(id>0){
			myVideo.getVideoInfo(id);
		}
		var html = $("#editDiv").html();
		return html;
	},
	getVideoInfo : function(id){
		var video = null;
		var dataUrl = "../user/video.do?a=getVideoInfo&id="+id;
		$.ajax({url: dataUrl, async:false, dataType:"json",
			success: function(datas) {
				if(data != null && data.length == 1){
					$("#id").val(data[0].id);
					$("#groupID").val(data[0].groupId);
	    			$("#name").val(data[0].name);
	    			$("#photo").val(data[0].photo);
	    			$("#video").val(data[0].video);
	    			$("#intro").val(data[0].intro);
	    		}
			}
		});
		
		return video;
	},
	openVideoFormDlg : function(title,html){
		pagei = $.layer({
			type: 1,   //0-4的选择,
			title: title,
			maxmin: false,
			border: [10, 0.2, '#000'],
			closeBtn: [1, true],
			shadeClose: false,
			fix: true,
			zIndex : 1000,
			area: ['800px', '450px'],
			page: {
				html: html //此处放了防止html被解析，用了\转义，实际使用时可去掉
			}
		});
		//$("#btnCancel").click(video.btnCancel);
		//$("#btnOK").click(video.btnOK);
		$("#form").validationEngine("attach",{
			autoPositionUpdate:false,//是否自动调整提示层的位置
			scroll:false,//屏幕自动滚动到第一个验证不通过的位置
			focusFirstField:false,//验证未通过时，是否给第一个不通过的控件获取焦点
			promptPosition:"topRight" //验证提示信息的位置，可设置为："topRight", "bottomLeft", "centerRight", "bottomRight" 
		});
	},
	btnOK : function(){
		if(!myVideo._validForm()) return;
		myVideo.saveVideo();
		if(pagei != null)
			layer.close(pagei);
	},
	btnCancel: function(){
		if(pagei != null)
			layer.close(pagei);
	},
	_validForm : function() {
		if (!$("#form").validationEngine("validate")){
			//验证提示
			$("#form").validationEngine({scroll:false});
			return false;
		}
		return true;
	}
}