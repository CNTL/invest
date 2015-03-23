$(document).ready(function () {
	 
	 
	$("#list-rate li").each(function(i,n){
		$(this).click(function(){
			$("#list-rate li").removeClass("current");
			$(this).addClass("current");
			$("#sl-rate").val($(this).attr("title"));
		});
		
	});
	
	$("#list-city li").each(function(i,n){
		$(this).click(function(){
			$("#list-city li").removeClass("current");
			$(this).addClass("current");
			$("#hcityid").val($(this).attr("data-id"));
			$("#hcityname").val($(this).attr("data-name"));
		});
		
	});
	var firstcity = $("#list-city li:first");
	
	firstcity.addClass("current");
	$("#hcityid").val(firstcity.attr("data-id"));
	$("#hcityname").val(firstcity.attr("data-name"));
	 
	$("#btn-save").click(function(){
		if(formvalid()){
			var url = "../recruit/ListMain.do?a=updateRecruitSubscibe";
			var data = {
					name: $("#name").val(),
					userid:$("#userID").val(),
					mail:$("#email").val(),
					cityid:$("#hcityid").val(),
					cityname:$("#hcityname").val(),
					recid:$("#hrecid").val(),
					recname:$("#hrecname").val(),
					rate:$("#sl-rate").val()
			};
			$.get(url,data, function(data){
				  if(data=="ok"){
					  alert("职位订阅成功！")
					  window.location.href="../recruit/ListMain.do?a=queryNew&recruitType=view&mainType=3";
					  
				  }
				  else{
					  alert("职位订阅失败。")
				  }
			});
		}
	});
});

function formvalid(){
	$("#emailError").hide();
	$("#positionError").hide();
	$("#cityError").hide();
	var ret = true;
	if($("#name").val()==""){
		ret = false;
		alert("请输入订阅名称。")
		return ret;

	}
	
	if($("#email").val()!=""){
		 var dateRegEx = new RegExp(/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i);
	     if (!dateRegEx.test($("#email").val())) {
	       
	        $("#emailError").text("请输入正确的邮件格式。");
	        $("#emailError").show();
	        ret = false;
	        return ret;
	     }
	    

	}
	else{
		ret = false;
		$("#emailError").show();
		return ret;

	}
	 
	
	if($("#hcityid").val()==""){
		alert($("#hcityid").val());
		ret = false;
		$("#cityError").show();
		return ret;

	}
	if($("#hrecid").val()==""){
		ret = false;
		$("#positionError").show();
		return ret;

	}
	
	return ret;
}



function selectrec(){
	var event = event.srcElement || event.target;
    var id= $(event).attr("data-id");
    var name = $(event).attr("data-name");
    $("#sl-rec").text(name);
    $("#hrecid").val(id);
    $("#hrecname").val(name);
}