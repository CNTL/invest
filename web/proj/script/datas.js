var proj_datas = {
	datas : null,
	ready : false,
	init : function(){
		var dataUrl = "../project/ProjectFetcher.do?action=datas";
		var loading = -1;
		$.ajax({url: dataUrl, async:false, dataType:"json",
			beforeSend:function(XMLHttpRequest){				
				//loading = layer.msg("正在初始化数据...", 0, 16);
			},
			success: function(datas) {
				proj_datas.datas = datas;
				proj_datas.ready = true;
			},
			complete: function(XMLHttpRequest, textStatus){
				//layer.close(loading);
			},
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				//layer.close(loading);
				//layer.alert('加载数据失败！', 3);
			}
		});
	},
	getProjTypes : function(){
		return proj_datas.datas.ProjTypes;
	},
	getProvinces : function(){
		var provines = [];
		//provines.push({id:"",pid:"0",name:"=省份="});
		for(var i=0;i<proj_datas.datas.areas.length;i++){
			if(proj_datas.datas.areas[i].pid==0){
				provines.push(proj_datas.datas.areas[i]);
			}
		}
		return provines;
	},
	getCities : function(pid){
		var cities = [];
		//cities.push({id:"",pid:pid,name:"=城市="});
		if(!pid) return cities;
		for(var i=0;i<proj_datas.datas.areas.length;i++){
			if(proj_datas.datas.areas[i].pid==pid){
				cities.push(proj_datas.datas.areas[i]);
			}
		}
		return cities;
	},
	getCounties : function(cid){
		var counties = [];
		//counties.push({id:"",pid:cid,name:"=地区="});
		if(!cid) return counties;
		for(var i=0;i<proj_datas.datas.areas.length;i++){
			if(proj_datas.datas.areas[i].pid==cid){
				counties.push(proj_datas.datas.areas[i]);
			}
		}
		return counties;
	}
}
$(function(){
	proj_datas.init();
	var init = function() {
	
		if (!proj_datas.datas || !proj_datas.ready) {
			setTimeout(init, 100);
			return;
		}
		
	}
	init();
});