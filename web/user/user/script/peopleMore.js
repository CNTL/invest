$(function(){
	queryParam();
	
	setFowardGroup();
	
	
});
function queryParam(){
	var type = $.query.get("type");
	$("#rows dl").hide();

	
 
}
function setGroup(){
	var type = $.query.get("type");
	var dls = $("#rows dl");
	$.each(dls,function(i,n){
		var dl = $(this);
		var as = dl.find("a");
		//遍历所有的A
		$.each(as,function(p,d){
			$(this).click(function(){
				if(p==0){
					as.removeClass("aselect");
					$(this).addClass("aselect");
				}
				else{
					$(this).toggleClass("aselect");
					dl.find("a:first").removeClass("aselect");
				}
			});
		});
	});
	
}
function setGroupFirst(){
	$("#taglist a").each(function(){
		$(this).click(function(){
			
			var group = $(this).attr("data-group");
			if(group=="sex"){
				$("#taglist a[data-group='sex']").removeClass("aselect");
				$("#taglist a[data-group='all']").removeClass("aselect");
				$(this).addClass("aselect");
			}else if(group == "age"){
				$("#taglist a[data-group='age']").removeClass("aselect");
				$("#taglist a[data-group='all']").removeClass("aselect");
				$(this).addClass("aselect");
				 
			}
			else{
				$("#taglist a").removeClass("aselect");
				$(this).addClass("aselect");
			}
		});
	});
}

function setFowardGroup(){
	var type = $.query.get("type");
	$.getJSON("../user/PeopleMoreMain.do?a=getSeachItems&type="+type, function(json){
			var sb = [];
			$.each(json,function(i,n){
				sb.push("<dl class=\"dl-horizontal\" id=\"taglist"+n.id+"\" data-id=\""+n.id+"\">");
				sb.push("    <dt>");
				sb.push("        "+n.name+"：");
				sb.push("    </dt>");
				sb.push("    <dd>");
				$.each(n.subDics,function(p,d){
					if(d.name!="演员"){
						if(p==0){
							sb.push("        <a data-group=\"all\" data-id=\"0\" href=\"#\">");
							sb.push("            全部");
							sb.push("        </a>");
							sb.push("        |");
						}
						sb.push("        <a data-group=\"all\" data-id=\""+d.id+"\" href=\"#\">");
						sb.push("            "+d.name);
						sb.push("        </a>");
						sb.push("        |");
					}
					
				});
				sb.push("    </dd>");
				sb.push("</dl>");
			});
			if(sb.join('')==""){
				sb.push("<dl class=\"dl-horizontal\" id=\"taglist\" data-id=\"41\">");
				sb.push("                <dt>搜索条件：</dt>");
				sb.push("                <dd><a data-group=\"all\" href=\"#\">全部</a>|<a data-group=\"sex\" href=\"#\">男</a>|<a data-group=\"sex\" href=\"#\">女</a>|<a data-group=\"age\" href=\"#\">20岁以下</a>|<a data-group=\"age\" href=\"#\">20岁-30岁</a>|<a data-group=\"age\" href=\"#\">30岁以上</a>|</dd>");
				sb.push("</dl>");
				$("#rows").append(sb.join(''));
				setGroupFirst();
			}
			else{
				$("#rows").append(sb.join(''));
		 		setGroup();
			}
			
			
		});
}

