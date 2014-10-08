$(document).ready(function () {
	//初始化
	headImg.init();
	$("#form").validationEngine({
		autoPositionUpdate:true,
		onValidationComplete:function(from,r){
			if (r){
				window.onbeforeunload = null;
				$("#submit").attr("disabled", true);
				headImg.submit();
			}
		}
	});
});
var headImg = {
	init : function(){
		$.ajax({
	        type:"GET", //请求方式  
	        url:"user.do?a=findLogin", //请求路径  
	        cache: false,
	        dataType: 'JSON',   //返回值类型  
	        success:function(data){
	    		if(data != null){
	    			var headSrc=data.head;
	    			if(headSrc != null && headSrc != "")
	    				document.getElementById('nowPhoto').src = headSrc;
	    		}
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				   alert(errorThrown);
			}
	    });
	},
	submit : function(){
		$.ajax({
	        type:"POST", //请求方式  
	        url:"user.do?a=saveImg", //请求路径  
	        cache: false,
	        data:$('#form').serialize(),  //传参 
	        dataType: 'text',   //返回值类型  
	        success:function(data){
	    		if(data != null && data == 'ok'){
	    			$.messager.alert('消息','上传头像成功！');
	    		} else {
	    			$.messager.alert('上传头像失败！',data);
	    		}
	    		$("#submit").attr("disabled", false);
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				   alert(errorThrown);
			}
	    });
	}
}

//校验上传文件
function checkPic() {
	var location = document.getElementById('headImg').value;
	if (location == "") {
		alert("请先选择图片文件");
		return false;
	}
	var point = location.lastIndexOf(".");
	var type = location.substr(point).toLowerCase() ;
	if (type == ".jpg" || type == ".gif" || type == ".png" || type == ".jpeg" || type == ".bmp") {
	//if (type == ".jpg") {
		img = document.createElement("img");
		img.src = location;
		if (img.fileSize > 1024000) {
			alert("图片尺寸请不要大于100KB");
			return false;
		} else {
			document.getElementById("nowDiv").style.display="none";
			return true;
		}
	} else {
		alert("只能上传jpg、jpeg、gif、png、bmp格式的图片");
		//alert("只能上传jpg格式的图片");
		return false;
	}
	document.getElementById("nowDiv").style.display="none";
	return true;
}
//图片上传后的回调函数
function callback(url, width, height) {
	alert(url)
	document.getElementById('cut_img').width = width;
	document.getElementById('cut_img').height = height;
	document.getElementById('cut_img').src = url;
	alert(document.getElementById('cut_img').src)
	document.getElementById('cut_url').value = url;
	document.getElementById('hide').style.display = '';
	imageinit();
	gripinit();
}
function hide(){
	document.getElementById('hide').style.display='none';
}