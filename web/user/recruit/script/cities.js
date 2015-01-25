var city_datas = {
	datas : null,
	ready : false,
	init : function(){
		var dataUrl = "../user/user.do?a=cities";
		var loading = -1;
		$.ajax({url: dataUrl, async:true, dataType:"json",
			beforeSend:function(XMLHttpRequest){				
				loading = layer.msg("正在初始化数据...", 0, 16);
			},
			success: function(datas) {
				city_datas.datas = datas;
				city_datas.ready = true;
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
	getCities : function(){
		var cities = [];
		for(var i=0;i<city_datas.datas.cities.length;i++){
			if(city_datas.datas.cities[i].pid==0){
				cities.push(city_datas.datas.cities[i]);
			}
		}
		return cities;
	}
}
$(function(){
	city_datas.init();
});