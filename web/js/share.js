$(function(){
	shareInfo();
});

function shareInfo(){
	$(".box .tool a.share").each(function(i,n){
		 $(this).click(function(){
			 var url = $(this).attr("data-url");
			 var box = $(this).closest(".box");
			 var text = box.find(".title").find("a").text();
			 window._bd_share_config.share[0].bdUrl =url;
			 window._bd_share_config.share[0].bdText =  text;
              var d = dialog({
            	  id:"shareddialog",
                  content: $("#sharedialog")[0],
                  padding:5,
                  quickClose: true// 点击空白处快速关闭
              });
              d.show($(this)[0]);

		 });
	});
}