$(document).ready(function () {
	//初始化
	relAuth.init();
	$("#organizationBtn").click(function() {
		relAuth.ajaxFileUpload("organization");
    });
	$("#businessLicenseBtn").click(function() {
		relAuth.ajaxFileUpload("orgBusinessLicense");
    });
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
	init : function(){
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