var tl_msg = {
	init : function(){
		var html  = "<div id=\"proj_msg_div_msg\" style=\"position: absolute; z-index: 122; width: 420px; height: 180px;display: none;overflow:auto;background:#fff;border:1px solid #C7C7C7;overflow-y: auto;overflow-x: hidden;\" class=\"popup\">";
			html += "<div id=\"proj_msg_div_title\" style=\"padding:5px;\"><span id=\"proj_msg_title\" style=\"font-size:18px;font-weight:bolder;float:left;padding-left:10px;\"></span><span onclick=\"tl_msg.close();\" style=\"float:right;cursor:pointer;font-weight:bolder;font-size:16px;width:16px;height:16px;\">X</span></div>";
			html += "<div id=\"proj_msg_msgdiv\" style=\"padding: 20px; height: 120px; width: 100%;\"></div>";
			html += "<div id=\"proj_msg_msgBtnDiv\"";
			html += "	style=\"width: 100%; height: 30px;text-align: center;\">";
			html += "	<input type=\"button\" id=\"proj_msg_okBtn\"";
			html += "		value=\"&nbsp; &nbsp;确&nbsp; &nbsp;定&nbsp; &nbsp;\" class=\"btn\"";
			html += "		onclick=\"tl_msg.close();\" style=\"margin-right: 20px;\" />";
			html += "	<input type=\"button\" id=\"proj_msg_closeBtn\" style=\"display:none;\"";
			html += "		value=\"&nbsp; &nbsp;关&nbsp; &nbsp;闭&nbsp; &nbsp;\" class=\"btn\"";
			html += "		onclick=\"tl_msg.close();\" />";
			html += "</div>";
			html += "</div>";
			html += "<div class='div_full' id='proj_msg_parent_div'";
			html += "	style=\"z-index:100;height:100%;width:100%;position:absolute;background-color:#C8C8C8;display: none; opacity:20;filter:alpha(opacity=20);overflow:hidden;opacity: 0.2;MozOpacity:0.2;-moz-opacity:0.2;left:0px;top:0px;\">";
			html += "</div>";
		$("body").append(html);
		$(window).bind("scroll",tl_msg.sc);
	},
	dialog : function(title,html,width,height){
		$("#proj_msg_div_msg").css("display", "");
		$("#proj_msg_msgBtnDiv").css("display","");
		$("#proj_msg_msgBtnDiv").css("display","none");
		var msgH = height-60;
		$("#proj_msg_div_msg").css("width",width+"px");
		$("#proj_msg_div_msg").css("height",height+"px");
		$("#proj_msg_msgdiv").css("height",msgH+"px");
		tl_msg.sc();
		var bodyheight = document.getElementsByTagName('body')[0].clientHeight;
		var parent_div = document.getElementById("proj_msg_parent_div");
		parent_div.style.display = 'block';
		parent_div.style.height = parseInt(bodyheight) + 'px';
		$("#proj_msg_title").html(title);
		$("#proj_msg_msgdiv").html(html);
	},
	show : function(title,msg){
		$("#proj_msg_div_msg").css("display", "");
		$("#proj_msg_msgBtnDiv").css("display","");
		var width = 420,height=180;var msgH = height-60;
		$("#proj_msg_div_msg").css("width",width+"px");
		$("#proj_msg_div_msg").css("height",height+"px");
		$("#proj_msg_msgdiv").css("height",msgH+"px");
		tl_msg.sc();
		var bodyheight = document.getElementsByTagName('body')[0].clientHeight;
		var parent_div = document.getElementById("proj_msg_parent_div");
		parent_div.style.display = 'block';
		parent_div.style.height = parseInt(bodyheight) + 'px';
		$("#proj_msg_title").html(title);
		$("#proj_msg_msgdiv").html(msg);
	},
	close : function(){
		if($("#proj_msg_msgdiv .uploadify").length>0){
			$("#proj_msg_msgdiv .uploadify").each(function(i,n){
				var id = $(this).attr("id");
				$('#'+id).uploadify('destroy')
			});
		}
		$("#proj_msg_title").empty();
		$("#proj_msg_msgdiv").empty();
		$("#proj_msg_div_msg").css("display", "none");
		var parent_div = document.getElementById("proj_msg_parent_div");
		parent_div.style.display = 'none';
	},
	sc : function(){
		var t = (document.documentElement.scrollTop + document.body.scrollTop + (document.documentElement.clientHeight - document.getElementById("proj_msg_div_msg").offsetHeight) / 2);
		var l = (document.documentElement.scrollLeft + (document.documentElement.clientWidth - document.getElementById("proj_msg_div_msg").offsetWidth) / 2);
		document.getElementById("proj_msg_div_msg").style.top = t + "px";
		document.getElementById("proj_msg_div_msg").style.left = l + "px";
	},
	rm : function(){
		document.getElementById("proj_msg_div_msg").style.display = 'none';
		document.getElementById("proj_msg_parent_div").style.display = 'none';
	}
}

$(function(){
	tl_msg.init();
});