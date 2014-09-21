var userset = {
	change : function(obj) {
		var url;
		if(obj.id=='infoEdit'){
			url = "userComplete.jsp";
		}else if(obj.id=='pwsEdit'){
			url = "userPwdChange.jsp";
		}else if(obj.id=='headEdit'){
			url = "userHeadImg.jsp";
		} else if(obj.id=='relAuth'){
			url = "userRelAuth.jsp";
		}
		$("#main").attr("src",url);
	}
};