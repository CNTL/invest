$(function(){
	queryParam();
	setGroup();
	setFowardGroup();
});
function queryParam(){
	var type = $.query.get("type");
	$("#rows dl").hide();

	
 
}
function setGroup(){
	$("#taglist a").click(function(){
		
		var group = $(this).attr("data-group");
		if(group=="sex"){
			$("#taglist a[data-group='sex']").removeClass("ddselect");
			$("#taglist a[data-group='all']").removeClass("ddselect");
			$(this).addClass("ddselect");
		}else if(group == "age"){
			$("#taglist a[data-group='age']").removeClass("ddselect");
			$("#taglist a[data-group='all']").removeClass("ddselect");
			$(this).addClass("ddselect");
		}
		else{
			$("#taglist a").removeClass("ddselect");
			$(this).addClass("ddselect");
		}
	});
}

function setFowardGroup(){
	var type = $.query.get("type");
	$.getJSON("../user/PeopleMoreMain.do?a=getSeachItems&type="+type, function(json){
			var sb = [];
			$.each(json,function(i,n){
				sb.push("<dl class=\"dl-horizontal\" id=\"taglist"+n.id+"\" data-type=\""+n.id+"\">");
				sb.push("    <dt>");
				sb.push("        "+n.name+"：");
				sb.push("    </dt>");
				sb.push("    <dd>");
				$.each(n.subDics,function(p,d){
					if(d.name!="演员"){
						if(p==0){
							sb.push("        <a data-group=\"all\" id=\"0\" href=\"#\">");
							sb.push("            全部");
							sb.push("        </a>");
							sb.push("        |");
						}
						sb.push("        <a data-group=\"all\" id=\""+d.id+"\" href=\"#\">");
						sb.push("            "+d.name);
						sb.push("        </a>");
						sb.push("        |");
					}
					
				});
				sb.push("    </dd>");
				sb.push("</dl>");
			});
			if(sb.join('')==""){
				sb.push("<dl class=\"dl-horizontal\" id=\"taglist\" data-type=\"41\">");
				sb.push("                <dt>搜索条件：</dt>");
				sb.push("                <dd><a data-group=\"all\" href=\"#\">全部</a>|<a data-group=\"sex\" href=\"#\">男</a>|<a data-group=\"sex\" href=\"#\">女</a>|<a data-group=\"age\" href=\"#\">20岁以下</a>|<a data-group=\"age\" href=\"#\">20岁-30岁</a>|<a data-group=\"age\" href=\"#\">30岁以上</a>|</dd>");
				sb.push("</dl>");
			}
	 		$("#rows").append(sb.join(''));
		});
}

