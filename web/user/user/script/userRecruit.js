 
$(function(){
	 
	$("#sl-recs").change(function(){
		 $("#list-recs").empty();
		loadUsers();
	});
	loadUsers();
	
	$("#rec-update").click(function(){
		var url = webroot+"recruit/Edit.do?a=detail&id="+$("#sl-recs").val();
		window.open (url,"编辑职位");
	});
	
	$("#rec-del").click(function(){
		if(confirm("你确定要删除吗？")){
			var url = "../recruit/ListMain.do?a=delRecruit&recid="+$("#sl-recs").val();
			$.get(url, function(data){
				   if(data.toString()=="ok"){
					   $("#sl-recs").find("option[value='"+$("#sl-recs").val()+"']").remove();
					   alert("删除成功。");
				   }
			});
		}
		
	});
	$("#rec-view").click(function(){
		var url = webroot+"recruit/DetailMain.do?a=detail&id="+$("#sl-recs").val();
		window.open (url,"预览职位");
	});
});

function loadUsers(){
	var recid = $("#sl-recs").val();
	
	var url = "../recruit/ListMain.do?a=getRecruitUser&recid="+recid;
	
	$.getJSON(url, function(json){
		  if(json.users.length>0){
			  $("#rec-count").text(json.users.length);
			 
			  $.each(json.users,function(i,n){
				  var li = $("<li></li>");
				  
				  li.append("<a target=\"_blank\" href=\""+webroot+"resume/resumeDetail.do?infoType=1&id="+n.resid+"\" title=\""+n.username+"\"><img style=\"width:100px;height:100px;\" src=\""+webroot+n.headurl+"\" alt=\""+n.username+"\" class=\"img-circle\"></a>");
				  
				  $("#list-recs").append(li);
			  });
		  }
	});
	 
}
