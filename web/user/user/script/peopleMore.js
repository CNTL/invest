var firstUrl = "";
$(function(){
	queryParam();
	setFowardGroup();
	firstUrl = $.query.toString();
	 
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
				search();
			});
		});
	});
	
	var types = $.query.get("types");
	if(types){
		var rootUrl = url.replace(firstUrl,"");
		var dls = $("#rows a");
		var ids = types.split(",");
		$.each(ids,function(i,n){
			$("#rows a[data-group='item'][data-id='"+n+"']").addClass("aselect");
		});
		 
	}
	
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
			searchFirst();
		});
	});
	//设置已选择项
	var age = $.query.get("age");
	var gender = $.query.get("gender");
	if(age){
		$("#taglist a[data-group='age'][data-id='"+age+"']").addClass("aselect");
	}
	if(gender||gender>=0){
		$("#taglist a[data-group='sex'][data-id='"+gender+"']").addClass("aselect");
	}
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
						sb.push("        <a data-group=\"item\" data-id=\""+d.id+"\" href=\"#\">");
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
				sb.push("                <dd><a data-group=\"all\" href=\"#\">全部</a>|<a data-group=\"sex\" href=\"#\" data-id=\"1\">男</a>|<a data-group=\"sex\" data-id=\"0\" href=\"#\">女</a>|"+$("#ageItems").html()+"</dd>");
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

function searchFirst(){
	var dls = $("#rows dl");
	var as = dls.find(".aselect");
	var age = -1;
	var gender = -1;
	var all = -1;
	$.each(as,function(i,n){
		
		if($(this).attr("data-group")=="all"){
			all=0;
		}else if($(this).attr("data-group")=="sex"){
			gender = $(this).attr("data-id");
		}else{
			age = $(this).attr("data-id");
		}
	});
	
	var url = window.location.href.replace("#","");
	var rootUrl = url.replace(firstUrl,"");
	if(all!=-1){
		rootUrl = rootUrl+"?a=queryPersons&mainType="+$.query.get("mainType")+"&type="+$.query.get("type");
	}else{
		rootUrl = rootUrl+"?a=queryPersons&mainType="+$.query.get("mainType")+"&type="+$.query.get("type");
		if(age!=-1){
			rootUrl = rootUrl+"&age="+age;
		}
		if(gender!=-1){
			rootUrl = rootUrl +"&gender="+gender;
		}
	}
	//alert(rootUrl);
	window.location.href=rootUrl;
}
function search(){
	var url = window.location.href.replace("#","");
	var rootUrl = url.replace(firstUrl,"");
	var dls = $("#rows dl");
	var dl1 ;
	var dl2;
	
	if(dls.length>0&&dls.length==1){
		dl1 = dls.eq(0);
	}
	else if(dls.length>0&&dls.length==2){
		dl1 = dls.eq(0);
		dl2 = dls.eq(1);
	}
	var sb = [];
	if(dl1.length==1){
		 
		var as = dl1.find(".aselect");
		if(!dl1.find("a:first").hasClass("aselect")){
			$.each(as,function(i,n){
				sb.push($(this).attr("data-id"));
			});
		}
	}
	if(dl2){
		if(dl2.length==1){
			 
			var as = dl2.find(".aselect");
			if(!dl2.find("a:first").hasClass("aselect")){
				$.each(as,function(i,n){
					sb.push($(this).attr("data-id"));
				});
				 
			}
		}
	}
	
	var types = sb.join(",");
	if(types==""){
		rootUrl = rootUrl+"?a=queryPersons&mainType="+$.query.get("mainType")+"&type="+$.query.get("type");
	}
	else{
		rootUrl = rootUrl+"?a=queryPersons&mainType="+$.query.get("mainType")+"&type="+$.query.get("type")+"&types="+types;
	}
	//alert(rootUrl);
	window.location.href=rootUrl;
	
}

