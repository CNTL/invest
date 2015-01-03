$(function(){
	shareInfo();
});

var bdshare = {
	text : "",
	desc : "",
	url : "",
	pic : "",
	init : function(){
		window._bd_share_config={
			"common":{"bdSnsKey":{"tsina":"renguangyong","tqq":"271798993","t163":"renguangyong","tsohu":"renguangyong"},
				"bdMini":"2",
				"bdMiniList":["tsina","qzone","weixin","renren","tqq","douban"],
				"bdStyle":"0",
				"bdSize":"16",
				"onBeforeClick" : function(cmd,config){
					config["bdText"] = $.trim(bdshare.text);
					config["bdUrl"] = $.trim(bdshare.url).replace(/\\/g,"/");
					config["bdPic"] = $.trim(bdshare.pic).replace(/\\/g,"/");
					config["bdDesc"] = $.trim(bdshare.desc);
					if(config["bdUrl"].indexOf(webroot)!=0){
						config["bdUrl"] = webroot + config["bdUrl"];
					}
					if(config["bdPic"].indexOf(webroot)!=0){
						config["bdPic"] = webroot + config["bdPic"];
					}
					return config;
				}},
			"share":{}
		};
		with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];
	}
};
function shareInfo(){
	$(".box .tool a.share").each(function(i,n){
		var html = "<div class=\"bdsharebuttonbox\" style=\"width:60px;float:left;\">";
		html += "<a href=\"javascript:void();\" class=\"bds_more share\" style=\"margin: 0px 0px 0px 10px;\" data-cmd=\"more\">分享</a>";
		html += "</div>";
		
		$(this).after(html);
		$(this).remove();
	});
	
//	$(".bdsharebuttonbox").on("mouseover",function(){
//		var m = $(this).closest("div.box_main");
//		bdshare.text = $(m).find("div.title").text();
//		bdshare.desc = $(m).find("div.desc").text();
//		bdshare.url = $(m).find("div.pic a").attr("href");
//		bdshare.pic = $(m).find("div.pic img").attr("src");
//	});
	bdshare.init();
}