$(document).ready(function () {
	 
	$("#sl-city").popover({
		container:"body",
		title:"选择订阅城市",
		html:true,
		content:$("#morecity").html(),
		trigger:"click"
	});
	 
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
	var ret = true;
	if($("#name").val()==""){
		ret = false;
		alert("请输入订阅名称。")
		return ret;

	}
	
	if($("#email").val()!=""){
		 var dateRegEx = new RegExp(/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i);
	     if (!dateRegEx.test($("#email").val())) {
	        alert("请输入正确的邮件格式。")
	        ret = false;
	        return ret;
	     }
	    

	}
	else{
		ret = false;
		alert("请输入订阅邮件。")
		return ret;

	}
	
	if($("#hcityid").val()==""){
		alert($("#hcityid").val());
		ret = false;
		alert("请选择订阅城市。")
		return ret;

	}
	if($("#hrecid").val()==""){
		ret = false;
		alert("请选择订阅职位。")
		return ret;

	}
	
	return ret;
}

function selectcity(){
	 $("#sl-city").trigger("click");
    var id= $(event.srcElement).attr("data-id");
    var name = $(event.srcElement).attr("data-name");
    $("#city").text(name);
    $("#hcityid").val(id);
    $("#hcityname").val(name);
   
}

function selectrec(){
    var id= $(event.srcElement).attr("data-id");
    var name = $(event.srcElement).attr("data-name");
    $("#sl-rec").text(name);
    $("#hrecid").val(id);
    $("#hrecname").val(name);
}