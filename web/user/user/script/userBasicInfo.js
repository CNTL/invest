$(document).ready(function () {
	 
	//初始化
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
function selectrec(){
	$this = $(event.srcElement);
	$this.toggleClass("selected");
	setRec();
}
function setRec(){
	var recs = $("#modal-body").find(".selected");
	var recIDs = [];
	var recNames = [];
	$.each(recs,function(i,n){
		recIDs.push($(this).attr("data-id"));
		recNames.push($(this).attr("data-name"));
	});
	$("#recIDs").val(recIDs.join(","));
	$("#recNames").val(recNames.join(","));
	$("#perjob").val(recNames.join(","));
}
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
	    			$("#perNickName").val(data.perNickName);
	    			$("#perPostAddr").val(data.perPostAddr);
	    			$("#perPostCode").val(data.perPostCode);
	    			$("#gender").val(data.gender);
	    			$("#birthdate").val(data.birthdate);
	    			$("#intro").val(data.intro);
	    			$("#province").val(data.province);
	    			$("#perjob").val(data.perJobName);
	    			$("#recNames").val(data.perJobName);
	    			$("#recIDs").val(data.perJob);
	    			$("#point").html(data.point);
	    			complete.setrecs(data.perJob);
	    			complete.changeProvince(data.city);
	    		}
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				   alert(errorThrown);
			}
	    });
		complete._setOptions("province",proj_datas.getProvinces(),complete.DEFAULT_PAIR);
	},
	setrecs:function(val){
		var recids = val.toString().split(",");
		 
		for(var i=0;i<recids.length;i++){
			$("#modal-body a[data-id='"+recids[i]+"']").addClass("selected")
		}
		
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
	        url:"../user/user.do?a=userBasicInfo", //请求路径  
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