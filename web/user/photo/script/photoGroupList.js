$(document).ready(function () {
	photoGroup.init();
	$("#form").validationEngine({
		autoPositionUpdate:true,
		onValidationComplete:function(from,r){
			if (r){
				window.onbeforeunload = null;
				photoGroup.savePhotoGroup();
			}
		}
	});
});
var photoGroup = {
	init : function(){
		$("#createGroup").click(photoGroup.openMsg);
		photoGroup.closeMsg();
		$("#btnSend").click(photoGroup.openMsg);
		photoGroup.initGroup();
	},
	initGroup : function(){
		$.ajax({
	        type:"GET", //请求方式  
	        url:"../user/photo.do?a=getPhotoGroups", //请求路径  
	        cache: false,
	        dataType: 'JSON',   //返回值类型  
	        success:function(data){
	        	if(!data||typeof Object.prototype.toString.call(data) == "[object Array]"||!data.length)return;
	        	photoGroup.assemble(data);
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				   alert(errorThrown);
			}
	    });
	},
	assemble : function(result) {
    	$.each(result, function(i,item){
    		var id = item.id;
    		var photo = item.groupPhoto;
    		if(photo == null || photo.length == 0)
    			photo = "../user/photo/img/framels_hover.jpg";
    		//添加图片的缩略图
    		$("#photos").append($("<div><a href='#'><img onclick='photoGroup.clickThumb("+id+")' name='photoList' id='" + id + "' src='"+photo+"'></a></div>"));
    		
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
	clickThumb : function(id){
		window.location.href="../user/PhotoMa.do?infoType=6&groupID=" + id; 
	},
	openMsg : function(){
		$('#w').window('open');
	},
	closeMsg : function(){
		$("#groupName").val("");
		$("#groupIntro").val("");
		$("#groupHead").val("");
		$("#groupHeadF").val("");
		$('#w').window('close');
	},
	savePhotoGroup : function(){
		$.ajax({
	        type:"POST", //请求方式  
	        url:"../user/photo.do?a=savePhotoGroup", //请求路径  
	        cache: false,
	        data:$('#form').serialize(),  //传参 
	        dataType: 'text',   //返回值类型  
	        success:function(data){
	    		if(data != null && data.length > 0){
	    			$.messager.confirm('消息', '创建相册成功！', function(r){
	    				if (r){
	    					window.location.href="../user/PhotoGroupMa.do?infoType=6&groupID=" + data; 
	    				}
	    			});
	    		} else {
	    			$.messager.alert('创建相册失败！',data);
	    		}
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				$.messager.alert('发送失败！',errorThrown);
			}
	    });
	},
	delPhotoGroup : function(id){
		$.ajax({
	        type:"GET", //请求方式  
	        url:"../user/photo.do?a=delPhotoGroup&id=" + id, //请求路径  
	        cache: false,
	        dataType: 'TEXT',   //返回值类型  
	        success:function(data){
	        	if(data != null && data == 'ok'){
	    			$.messager.confirm('消息', '删除图册成功！', function(r){
	    				if (r){
	    					window.location.href=window.location.href; 
	    				}
	    			});
	    		} else {
	    			$.messager.alert('删除图册失败！',data);
	    		}
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				$.messager.alert('删除图册失败！',errorThrown);
			}
	    });
	}
}