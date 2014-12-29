$(document).ready(function () {
	//初始化
	photo.init();
});
var pagei = null;
var photo = {
	init : function(){
		//$("#showPhoto").hide();	//初始化时不显示大图
		$("#uploadPhoto").click(photo.uploadPhoto);
		//photo.initPhoto();
		var groupID = $("#groupID").val();
		$.ajax({
	        type:"GET", //请求方式  
	        url:"../user/photo.do?a=getPhotos&groupID=" + groupID, //请求路径  
	        cache: false,
	        dataType: 'JSON',   //返回值类型  
	        success:function(data){
	        	if(!data||typeof Object.prototype.toString.call(data) == "[object Array]"||!data.length)return;
	        	photo.assemble(data);
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				   alert(errorThrown);
			}
	    });
		/*
		var groupID = $("#groupID").val();
		$.ajax({
	        type:"GET", //请求方式  
	        url:"../user/photo.do?a=getPhotos&groupID=" + groupID, //请求路径  
	        cache: false,
	        dataType: 'JSON',   //返回值类型  
	        success:function(data){
	        	if(!data||typeof Object.prototype.toString.call(data) == "[object Array]"||!data.length)return;
	        	photo.assemble(data);
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				   alert(errorThrown);
			}
	    });
		*/
	},
	uploadPhoto : function(){
		var groupID = $("#groupID").val();
		window.open( "../user/photo/uploadImg.jsp?groupID=" + groupID, "_blank" ,
				"height=500,width=700,scrollbars=no,location=no");
	},
	submit : function(){
		$.ajax({
	        type:"POST", //请求方式  
	        url:"../user/photo.do?a=savePhotos", //请求路径  
	        cache: false,
	        data:$('#form').serialize(),  //传参 
	        dataType: 'text',   //返回值类型  
	        success:function(data){
	    		if(data != null && data == 'ok'){
	    			$.messager.alert('消息','上传照片成功！');
	    		} else {
	    			$.messager.alert('修改失败',data);
	    		}
	    		$("#btnSave").attr("disabled", false);
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				   alert(errorThrown);
			}
	    });
	},
	assemble : function(result) {
		$(".block1").html("");
    	$.each(result, function(i,item){
    		var id = item.id;
    		var photo = item.photo;
    		
    		if(photo == null || photo.length == 0)
    			photo = "user/photo/img/framels_hover.jpg";
    		//添加图片的缩略图
    		var prefix = '<div class="box" style="width:220px;">';
    		var suffix = '';
    		if((i + 1)%3==0) {
    			prefix = '<div class="box box_last" style="width:220px;">'; 
    			suffix = '<div class="clear"></div>';
    		}
    		var html = prefix +
				            '<div class="people" style="border: 1px #858585 solid;">' +
				                '<div id="imgs" class="pic" style="width:100%;">' +
				                    '<a><img name="photoList" id="' + id + '" src="'+rootPath+photo+'"/></a>' +
				                '</div>' +
				                '<div class="title">' +
				                    '<a style="text-decoration:none;">'+item.photoName+'</a>' +
				                '</div>' +
				                '<div class="tool">' +
				                	'<a class="view" title="编辑" style="cursor:pointer;background: url(../img/edit.png) no-repeat left;padding-left: 20px;" onclick="photo.editPhoto('+id+');"></a>' +
			                        '<a class="share" title="删除" style="cursor:pointer;background: url(../img/delete.png) no-repeat left;padding-left: 20px;" onclick="photo.delPhoto('+id+');"></a>' +
			                    '</div>' +
				            '</div>' +
				        '</div>' + suffix;
    		$(".block1").append(html);
    	});
	},
	getPrevPhoto : function(myID){
		var photo = [];
		var flag = false;
		$("img[name='photoList']").each(function(){
		    var curId =  $(this).attr("id");
		    if(curId == myID){//获取前一个元素
		    	flag = true;
		    } else if(!flag){//取前一个元素的值
		    	photo.id = curId;
				photo.src = $(this).attr("src");
		    }
		});
		return photo;
	},
	getNextPhoto : function(myID){
		var photo = [];
		var flag = false;
		$("img[name='photoList']").each(function(){
			var curId = $(this).attr("id");
			if(flag) {//获取后一个元素
				photo.id = curId;
				photo.src = $(this).attr("src");
		    }
		    if(curId == myID){
		    	flag = true;
		    } else {
		    	flag = false;
		    }
		});
		return photo;
	},
	delPhoto : function(id){
		alert(id)
		$.ajax({
	        type:"GET", //请求方式  
	        url:"../user/photo.do?a=delPhoto&id=" + id, //请求路径  
	        cache: false,
	        dataType: 'TEXT',   //返回值类型  
	        success:function(data){
	        	if(data != null && data == 'ok'){
	        		window.location.href=window.location.href; 
	    		} else {
	    			$.messager.alert('删除图片失败！',data);
	    		}
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				$.messager.alert('删除图片失败！',errorThrown);
			}
	    });
	},
	editPhoto : function(id){
		photo.openPhotoFormDlg("修改", photo.getPhotoFormHtml(id));
		if(id>0){
			photo.getPhotoInfo(id);
		}
	},
	openPhotoFormDlg : function(title,html){
		pagei = $.layer({
			type: 1,   //0-4的选择,
			title: title,
			maxmin: false,
			border: [10, 0.2, '#000'],
			closeBtn: [1, true],
			shadeClose: false,
			fix: true,
			zIndex : 1000,
			area: ['700px', '450px'],
			page: {
				html: html //此处放了防止html被解析，用了\转义，实际使用时可去掉
			}
		});
		$("#btnOK").click(photo.btnOK);
		$("#btnCancel").click(photo.btnCancel);
		/*$("#form").validationEngine("attach",{
			autoPositionUpdate:false,//是否自动调整提示层的位置
			scroll:false,//屏幕自动滚动到第一个验证不通过的位置
			focusFirstField:false,//验证未通过时，是否给第一个不通过的控件获取焦点
			promptPosition:"topRight" //验证提示信息的位置，可设置为："topRight", "bottomLeft", "centerRight", "bottomRight" 
		});*/
	},
	getPhotoFormHtml : function(id){
		var html = '<div class="job_add">' +
						'<form class="setting-form" id="form" name="form" action="">' +
							'<div class="input">' +
						        '<label for="photoName">图片名称：</label>' +
						        '<input type="hidden" id="id" name="id" value=""/>' +
						        '<input type="hidden" id="photo" name="photo" value=""/>' +
						        '<input type="text" id="photoName" name="photoName" class="form-control validate[maxSize[255],required]" value=""/>' +
						    '</div>' +
						    '<div class="input">' +
								'<label for="intro">图片描述：</label>' +
								'<textarea  id="intro" name="intro" class="form-control validate[maxSize[4000]]" style="width:400px;height:100px;" placeholder=""></textarea>' +
							'</div>' +
						   '<div id="coverIMG_div" style="display:none;position: absolute; z-index: 122; width:180px;height:150px;overflow:hidden;background:#fff;border:1px solid #C7C7C7;">' +
							'</div>' +
						    '<div class="btn" style="margin-top:170px;">' +
						    	'<input style="width:100px; margin-left: 100px;" id="btnOK" name="btnOK" value="提交" type="button"/>' +
						    	'<input style="width:100px; margin-left: 150px;" id="btnCancel" name="btnCancel" value="取消" type="button"/>' +
						    '</div>' +
						'</form>' +
					'</div>';
		return html;
	},
	getPhotoInfo : function(id){
		var dataUrl = "../user/photo.do?a=getPhotoInfo&id="+id;
		$.ajax({url: dataUrl, async:false, dataType:"json",
			success: function(data) {
				if(data != null){
					$("#id").val(data.id);
	    			$("#photoName").val(data.photoName);
	    			$("#intro").val(data.intro);
	    			$("#photo").val(data.photo);
	    			photo.imgUploaded();
	    		}
			}
		});
	},
	imgUploaded : function(){
		var c_img_t = 160;
		var c_img_l = 200;
		$("#coverIMG_div").css("top",c_img_t+"px").css("left",c_img_l+"px").css("display","");
		$("#coverIMG_div").empty();
		if($("#photo").val()!=""){
			$("#coverIMG_div").html("<img src=\""+rootPath+$("#photo").val()+"\" border=\"0\" style=\"width:180px;height:150px;\" />");
		}
	},
	btnOK : function(){
		photo.savePhoto();
		if(pagei != null)
			layer.close(pagei);
	},
	btnCancel: function(){
		if(pagei != null)
			layer.close(pagei);
	},
	savePhoto : function(){
		$.ajax({
	        type:"POST", //请求方式  
	        url:"../user/photo.do?a=updatePhoto", //请求路径  
	        cache: false,
	        data:$('#form').serialize(),  //传参 
	        dataType: 'text',   //返回值类型  
	        success:function(data){
	    		if(data != null && data.length > 0){
	    			$.messager.alert('消息',"修改成功！");
	    			window.location.href=window.location.href; 
	    		} else {
	    			$.messager.alert('修改失败！',data);
	    		}
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				$.messager.alert('修改失败！',errorThrown);
			}
	    });
	}
}
function myReload(){
	window.location.href=window.location.href;
}