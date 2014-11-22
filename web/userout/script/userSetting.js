var userset = {
	change : function(obj) {
		var url;
		if(obj.id=='infoEdit'){
			url = "user/userComplete.jsp";
		}else if(obj.id=='pwsEdit'){
			url = "user/userPwdChange.jsp";
		}else if(obj.id=='headEdit'){
			url = "user/userHeadImg.jsp";
		} else if(obj.id=='relAuth'){
			url = "user/userRelAuth.jsp";
		} else if(obj.id=='photo'){
			url = "photo/userPhoto.jsp";
		} else if(obj.id=='video'){
			url = "video/userVideoList.jsp";
		} else if(obj.id=='resume'){
			url = "resume/userResume.jsp";
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