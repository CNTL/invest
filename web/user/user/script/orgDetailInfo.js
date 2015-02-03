$(document).ready(function () {
	
	$("#orgNature").hide();
	$("#orgNatureSel").change(function(){
		$("#orgNature").val($(this).val());
		if($(this).val() == "其他"){
			$("#orgNature").show();
		}
		else{
			$("#orgNature").hide();
		}
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
	
	var type = $.query.get("type");
	 
	if(type==0){
		
		$("#lbcompany").text("项目(团队)全称：");
		$("#propty").hide();
		$("#lborgScale").text("团队规模：")
		$("#lborgHomePage").text("团队主页：")
	}
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
	    			$("#province").val(data.province);
	    			$("#orgNatureSel").val(data.orgNature);
	    			detail.changeProvince(data.city);
	    			
	    			if(data.orgNature==""||data.orgNature.toString()=="国营"||data.orgNature.toString()=="外商独资"||data.orgNature.toString()=="中外合资"||data.orgNature.toString()=="私营企业"){
	    				$("#orgNature").hide();
	    				$("#orgNatureSel").show();
	    				$("#orgNatureSel option[value='"+data.orgTrade+"']").prop("selected", true);
	    			}
	    			else{
	    				$("#orgNature").show();
	    				$("#orgNatureSel").hide();
	    			}
	    			
	    			$("#orgScale").val(data.orgScale);
	    			$("#orgHomePage").val(data.orgHomePage);
	    			$("#orgNature").val(data.orgNature);
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
	changeProvince : function(val){
		var cities = [];
		var pid = $("#province").val();
		cities = proj_datas.getCities(pid);
		detail._setOptions("city",cities,detail.DEFAULT_PAIR);
		if(val!=null&&val>0){
			$("#city").val(val);
		}
		
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