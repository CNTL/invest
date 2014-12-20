$(document).ready(function () {
	//初始化
	relAuth.init();
	$("#form").validationEngine({
		autoPositionUpdate:true,
		onValidationComplete:function(from,r){
			if (r){
				window.onbeforeunload = null;
				$("#btnSave").attr("disabled", true);
				relAuth.submit();
			}
		}
	});
});
var relAuth = {
	DEFAULT_PAIR : {key:"id",value:"name"},
	init : function(){
		relAuth.initJob();
		$.ajax({
	        type:"GET", //请求方式  
	        url:"../user/user.do?a=findLogin", //请求路径  
	        cache: false,
	        dataType: 'JSON',   //返回值类型  
	        success:function(data){
	    		if(data != null){
	    			$("#name").val(data.name);
	    			$("#province").val(data.province);//下拉框
	    			if(data.city != null && data.city.length > 0){
	    				load_city(data.city);
	    			}
	    			$("#perJob").val(data.perJob);//下拉框
	    			$("#perPhone").val(data.perPhone);
	    			$("#identityCard").val(data.identityCard);
	    			var bankcards = null;
	    			if(data.bankcards != null && data.bankcards.length > 0)
	    				bankcards = eval(data.bankcards);
	    			
	    			if(bankcards != null && bankcards.length > 0){
	    				$("#openingBanks").val(bankcards[0].openingBank);//银行卡开户行
		    			$("#bankNums").val(bankcards[0].bankNum);//银行卡号
	    			}
	    		}
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				   alert(errorThrown);
			}
	    });
	},
	initJob : function(){
		var dataUrl = "../user/userFetch.do?a=jobTypes";
		var loading = -1;
		$.ajax({url: dataUrl, async:true, dataType:"json",
			beforeSend:function(XMLHttpRequest){				
				loading = layer.msg("正在初始化数据...", 0, 16);
			},
			success: function(datas) {
				var curJobTypes = [];
				var jobTypes = datas.jobTypes;
				for(var i=0;i<jobTypes.length;i++){
					var jobType = jobTypes[i]; 
					if(jobType.pid==0){
						curJobTypes.push(jobType);
					}
				}
				relAuth._setOptions("perJob",curJobTypes,relAuth.DEFAULT_PAIR);
			},
			complete: function(XMLHttpRequest, textStatus){
				layer.close(loading);
			},
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				layer.close(loading);
				layer.alert('加载数据失败！', 3);
			}
		});
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
	        url:"../user/user.do?a=userRelAuth", //请求路径  
	        cache: false,
	        data:$('#form').serialize(),  //传参 
	        dataType: 'text',   //返回值类型  
	        success:function(data){
	    		if(data != null && data == 'ok'){
	    			$.messager.alert('消息','认证资料提交成功！');
	    		} else {
	    			$.messager.alert('认证资料提交失败！',data);
	    		}
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				   alert(errorThrown);
			}
	    });
	}
}