$(document).ready(function () {
	jobList.init();
	//初始化
	/*
	$("#form").validationEngine({
		autoPositionUpdate:true,
		onValidationComplete:function(from,r){
			if (r){
				window.onbeforeunload = null;
				$("#btnSave").attr("disabled", true);
				jobEdit.submit();
			}
		}
	});
	*/
});
var jobList = {
	init : function(){
		$(".subscribe").find("a").each(function(){
		    $(this).attr("style","cursor:pointer;text-transform:none;text-decoration:none;");
		});
		$("#type").selectbox();
		$("#search").click(jobList.search);
	},
	cityQuery : function(type, more, city){
		var paramData = {};
		paramData["recruitType"] = "view";
		paramData["mainType"] =  "3";
		paramData["type"] = type;
		paramData["key"] = $("#key").val();
		paramData["more"] = more;
		paramData["city"] = city;
		var params = {"param":JSON.stringify(paramData)};
		alert(params)
		$.ajax({
	        type:"POST", //请求方式  
	        url:"../recruit/ListMain.do?a=queryNew&city=" + city, //请求路径  
	        async:true, 
	        cache: false,
	        data: params,
	        dataType: 'text',   //返回值类型  
	        success:function(data){
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				alert("error="+errorThrown);
			}
	    });
	},
	addRecruit : function(){
		$.ajax({
	        type:"GET", //请求方式  
	        url:"../user/user.do?a=findLogin", //请求路径  
	        cache: false,
	        dataType: 'JSON',   //返回值类型  
	        success:function(data){
	    		if(data != null && data.orgFullname != null && data.orgFullname != ''){
	    			window.location.href = "../recruit/Edit.do?a=detail";
	    		} else {
	    			$.messager.confirm('消息', '请先完善资料！', function(r){
	    				if (r){
	    					window.location.href = "../org/DetailInfo.do";
	    				}
	    			});
	    		}
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				   alert("error="+errorThrown);
			}
	    });
	},
	change : function(obj) {
		$("#main").html("");
		if(obj.id=='queryNew'){
			$("#queryType").val('queryNew');
			var pageUrl = "../recruit/ListMain.do?a=queryNew&recruitType=view&mainType=3";
			window.location.href = pageUrl;
			//jobList.queryNew(0);
		} else if(obj.id=='queryHot'){
			$("#queryType").val('queryHot');
			var pageUrl = "../recruit/ListMain.do?a=queryHot&recruitType=view&mainType=3";
			window.location.href = pageUrl;
			//jobList.queryHot(0);
		}
		jobList.setClass(obj);
	},
	setClass : function (obj){
		$(obj).parent().find("a").each(function(){
		    $(this).removeClass("current");
		});
		$(obj).addClass("current");
	},
	search : function(){
		var type = $("#type").val();
		var key = $("#key").val();
		var pageUrl = "../recruit/ListMain.do?a=queryNew&recruitType=view&mainType=3&type=" + type + "&key=" + key;  
		window.location.href = pageUrl;
	}
}