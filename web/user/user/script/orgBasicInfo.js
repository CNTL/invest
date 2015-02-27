$(document).ready(function () {
	//初始化
	var init = function() {
		if (!proj_datas || !proj_datas.ready) {
			setTimeout(init, 100);
			return;
		}
		 
	}
	init();
	complete.init();
	$("#form").validationEngine({
		autoPositionUpdate:true,
		onValidationComplete:function(from,r){
			if (r){
				window.onbeforeunload = null;
				$("#btnSave").attr("disabled", true);
				complete.submit();
			}
		}
	});
});
var complete = {
	DEFAULT_PAIR : {key:"id",value:"name"},
	init : function(){
		$.ajax({
	        type:"GET", //请求方式  
	        url:"../user/user.do?a=findLogin", //请求路径  
	        cache: false,
	        dataType: 'JSON',   //返回值类型  
	        success:function(data){
	    		if(data != null){
	    			$("#code").val(data.code);
	    			$("#orgShortname").val(data.orgShortname);
	    			$("#orgFullname").val(data.orgFullname);
	    			$("#intro").val(data.intro);
	    			$("#province").val(data.province);
	    			complete.changeProvince(data.city);
	    		}
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				   alert(errorThrown);
			}
	    });
		complete._setOptions("province",proj_datas.getProvinces(),complete.DEFAULT_PAIR);
	},
	changeProvince : function(val){
		var cities = [];
		var pid = $("#province").val();
		cities = proj_datas.getCities(pid);
		complete._setOptions("city",cities,complete.DEFAULT_PAIR);
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
	        url:"../user/user.do?a=orgBasicInfo", //请求路径  
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