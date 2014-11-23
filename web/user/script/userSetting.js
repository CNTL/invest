var userset = {
	change : function(obj) {
		var url;
		if(obj.id=='infoEdit'){
			url = rootPath + "user/user/userBasicInfo.jsp";
		} else if(obj.id=='pwsEdit'){
			url = rootPath + "user/user/userPwdChange.jsp";
		} else if(obj.id=='headEdit'){
			url = rootPath + "user/user/userHeadImg.jsp";
		} else if(obj.id=='relAuth'){
			url = rootPath + "user/user/userRelAuth.jsp";
		} else if(obj.id=='photo'){
			url = rootPath + "user/photo/userPhoto.jsp";
		} else if(obj.id=='video'){
			url = rootPath + "user/video/userVideoList.jsp";
		} else if(obj.id=='resume'){
			url = rootPath + "user/resume/userResume.jsp";
		}
		userset.setClass(obj);
		$("#main").attr("src",url);
	},
	setClass : function (obj){
		$(obj).parent().parent().find("li").each(function(){
		    $(this).removeClass("select");
		});
		$(obj).parent().addClass("select");
	}
};