var type_datas = {
	datas : null,
	ready : false,
	init : function(){
		var dataUrl = "../user/recruit.do?a=datas";
		var loading = -1;
		$.ajax({url: dataUrl, async:true, dataType:"json",
			beforeSend:function(XMLHttpRequest){				
				
			},
			success: function(datas) {
				type_datas.datas = datas;
				type_datas.ready = true;
			},
			complete: function(XMLHttpRequest, textStatus){
				
			},
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				
			}
		});
	},
	getFirstTypes : function(){
		var firstTypes = [];
		for(var i=0;i<type_datas.datas.jobTypes.length;i++){
			if(type_datas.datas.jobTypes[i].pid==0){
				firstTypes.push(type_datas.datas.jobTypes[i]);
			}
		}
		return firstTypes;
	},
	getSecondTypes : function(pid){
		var secondTypes = [];
		if(!pid) return secondTypes;
		for(var i=0;i<type_datas.datas.jobTypes.length;i++){
			if(type_datas.datas.jobTypes[i].pid==pid){
				secondTypes.push(type_datas.datas.jobTypes[i]);
			}
		}
		return secondTypes;
	}
}
$(function(){
	type_datas.init();
});