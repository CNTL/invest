$(function(){
	queryParam();
	setGroup();
});
function queryParam(){
	var type = $.query.get("type");
	$("#rows dl").hide();
	$("#rows dl[data-type='41']").show();
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

function 