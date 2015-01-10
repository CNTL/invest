$(document).ready(function () {
	$("#orgTrade").hide();
	$("#orgTradeSel").change(function(){
		$("#orgTrade").val($(this).val());
	});
	var init = function() {
		if (!proj_datas || !proj_datas.ready) {
			setTimeout(init, 100);
			return;
		}
		//初始化
		detail.init();
	}
	init();
});
var detail = {
	DEFAULT_PAIR : {key:"id",value:"name"},
	init : function(){
		$("#mapSearch").attr("onclick","getMap();");
		$.ajax({
	        type:"GET", //请求方式  
	        url:"../user/user.do?a=findLogin", //请求路径  
	        cache: false,
	        dataType: 'JSON',   //返回值类型  
	        success:function(data){
	    		if(data != null){
	    			$("#orgFullname").val(data.orgFullname);
	    			$("#location").val(data.location);
	    			$("#coordinate").val(data.coordinate);
	    			$("#orgNature").val(data.orgNature);
	    			$("#orgTrade").val(data.orgTrade);
	    			if(data.orgTrade.toString()=="国营"||data.orgTrade.toString()=="外商独资"||data.orgTrade.toString()=="中外合资"||data.orgTrade.toString()=="私营企业"){
	    				$("#orgTrade").hide();
	    				$("#orgTradeSel").show();
	    				$("#orgTradeSel option[value='"+data.orgTrade+"']").prop("selected", true);
	    			}
	    			else{
	    				$("#orgTrade").show();
	    				$("#orgTradeSel").hide();
	    			}
	    			
	    			$("#orgScale").val(data.orgScale);
	    			$("#orgHomePage").val(data.orgHomePage);
	    		}
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				   alert(errorThrown);
			}
	    });
		$("#form").validationEngine({
			autoPositionUpdate:true,
			onValidationComplete:function(from,r){
				if (r){
					window.onbeforeunload = null;
					$("#btnSave").attr("disabled", true);
					detail.submit();
				}
			}
		});
		detail._setOptions("province",proj_datas.getProvinces(),detail.DEFAULT_PAIR);
	},
	changeProvince : function(){
		var cities = [];
		var pid = $("#province").val();
		cities = proj_datas.getCities(pid);
		detail._setOptions("city",cities,detail.DEFAULT_PAIR);
	},
	_setOptions : function(id, datas, pair) {
		var sel = document.getElementById(id);
		if (!sel) return;
		
		while (sel.options.length > 0)
			sel.options.remove(0);

		for (var i = 0; i < datas.length; i++) {
			var op = document.createElement("OPTION");
			op.value = datas[i][pair.key];
			op.text = datas[i][pair.value];
			sel.options.add(op);
		}
		$(sel).trigger("change");
	},
	submit : function(){
		$.ajax({
	        type:"POST", //请求方式  
	        url:"../user/user.do?a=orgDetailInfo", //请求路径  
	        cache: false,
	        data:$('#form').serialize(),  //传参 
	        dataType: 'text',   //返回值类型  
	        success:function(data){
	    		if(data != null && data == 'ok'){
	    			$.messager.alert('消息','修改资料成功！');
	    		} else {
	    			$.messager.alert('修改失败',data);
	    		}
	    		$("#btnSave").attr("disabled", false);
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				   alert(errorThrown);
			}
	    });
	}
}
function getMap(){
	window.open("../user/common/MsgMap.jsp");
}