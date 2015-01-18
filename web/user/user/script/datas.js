var type_datas = {
	datas : null,
	ready : false,
	init : function(){
		var dataUrl = "../user/user.do?a=typeDatas";
		var loading = -1;
		$.ajax({url: dataUrl, async:true, dataType:"json",
			beforeSend:function(XMLHttpRequest){				
				loading = layer.msg("正在初始化数据...", 0, 16);
			},
			success: function(datas) {
				type_datas.datas = datas;
				type_datas.ready = true;
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