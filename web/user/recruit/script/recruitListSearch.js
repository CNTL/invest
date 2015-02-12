$(document).ready(function () {
	optionsdd();
	$("#type").selectbox();
	$("#search").click(seach);
	initSelectItem();
	emcity();
});
function initSelectItem(){
	 
	 
	if($("#hDays").val()!="-1"){
		$("#selecteditem ul").append("<li data-value=\""+$("#hDays").val()+"\" data-id=\""+$("#hDays").attr("data-id")+"\"><span><strong>工作时长</strong>："+$("#Days div[data-value='"+$("#hDays").val()+"']").text()+"</span><span onclick=\"remvoeSelectItem();\" class=\"select_remove\">&nbsp;</span></li>");
		$("#Days").prev().trigger("click");
	}
	if($("#hDegree").val()!="-1"){
		$("#selecteditem ul").append("<li data-value=\""+$("#hDegree").val()+"\" data-id=\""+$("#hDegree").attr("data-id")+"\"><span><strong>最低学历</strong>："+$("#Degree div[data-value='"+$("#hDegree").val()+"']").text()+"</span><span onclick=\"remvoeSelectItem();\" class=\"select_remove\">&nbsp;</span></li>");
		$("#Degree").prev().trigger("click");
	}
	if($("#hJobType").val()!="-1"){
		$("#selecteditem ul").append("<li data-value=\""+$("#hJobType").val()+"\" data-id=\""+$("#hJobType").attr("data-id")+"\"><span><strong>工作性质</strong>："+$("#JobType div[data-value='"+$("#hJobType").val()+"']").text()+"</span><span onclick=\"remvoeSelectItem();\" class=\"select_remove\">&nbsp;</span></li>");
		$("#JobType").prev().trigger("click");
	}
	if($("#hPubTime").val()!="-1"){
		$("#selecteditem ul").append("<li data-value=\""+$("#hPubTime").val()+"\" data-id=\""+$("#hPubTime").attr("data-id")+"\"><span><strong>发布时间</strong>："+$("#PubTime div[data-value='"+$("#hPubTime").val()+"']").text()+"</span><span onclick=\"remvoeSelectItem();\" class=\"select_remove\">&nbsp;</span></li>");
		$("#PubTime").prev().trigger("click");
	}
	if($("#hcity").val()!=""){
		$("#workplaceSelect dd").removeClass("current");
		var ddcity = $("#box_expectCity dd[data-id='"+$("#hcity").val()+"']");
		if(ddcity.length>0){
			var cityname = "";
			cityname = ddcity.find("a").text();
			
			var citymoredd = $("#morecity").prev();
			if(citymoredd.attr("data-type")=="more"){
				citymoredd.attr("data-id",$("#hcity").val());
				citymoredd.find("a").text(cityname);
				citymoredd.addClass("current");
			}else{
				$("#morecity").before("<dd data-type=\"more\" class=\"current\"  data-id=\""+$("#hcity").val()+"\"><a>"+cityname+"</a> </dd> ");
			}
		}
		else{
			$("#workplaceSelect dd[data-id='"+$("#hcity").val()+"']").addClass("current");
			
		}
		 
		
	}

}

function emcity(){
	$("#morecity").click(function(){
		 
	     var top = $(this).position().top;
	     var left = $(this).position().left-($("#box_expectCity").width()/2);
	     
		$("#box_expectCity").css({"top":top,"left":left});
		$("#box_expectCity").show();
	});
}

function optionsdd(){
	$("#optionslist dd>div").hover(
	  function () {
	    $(this).addClass("hover");
	  },
	  function () {
	    $(this).removeClass("hover");
	  }
	);
	$("#optionslist dt").toggle(
	  function () {
		  $(this).find("em").addClass("transform");
	      $(this).parent("dl").addClass("slideUp");
	      $(this).next("dd").hide();
	  },
	  function () {
		  $(this).find("em").removeClass("transform");
		  $(this).parent("dl").removeClass("slideUp");
		 $(this).next("dd").show();
	  }
	);
	
	//设置
	$("#optionslist dd>div").each(function(i,n){
		$(this).click(function(){
			
			var dd = $(this).parent();
			var dt = dd.prev();
			if($("#selecteditem ul>li[data-id=\""+dd.attr("id")+"\"]").length>0){
				$("#selecteditem ul>li[data-id=\""+dd.attr("id")+"\"]").remove();
			}
			$("#selecteditem ul").append("<li data-value=\""+$(this).attr("data-value")+"\" data-id=\""+dd.attr("id")+"\"><span><strong>"+dt.text()+"</strong>："+$(this).html()+"</span><span onclick=\"remvoeSelectItem();\" class=\"select_remove\">&nbsp;</span></li>");
			//收缩
			dt.trigger("click");
			seach();
		});
		
	});
	
	$("#workplaceSelect dd[data-id]").each(function(i,n){
		$(this).click(function(){
			 
			$("#workplaceSelect dd").removeClass("current");
			$(this).addClass("current");
			seach();
		});
	});
	$("#workplaceSelect dd:first").addClass("current");
	
	$("#box_expectCity dd").each(function(i,n){
		$(this).click(function(){
			var ddmore = $("#workplaceSelect dd[data-id][data-type]");
			if(ddmore.length>0){
				ddmore.attr("data-id",$(this).attr("data-id"));
				ddmore.find("a").remove();
				ddmore.append($(this).find("a"));
			}else{
				$("#morecity").before("<dd data-type=\"more\"  data-id=\""+$(this).attr("data-id")+"\">"+$(this).find("a").html()+" </dd> ");
			}
		});
	});
	
	$("body").click(function(){
		if(!$(event.srcElement).hasClass("citymore_arrow")){
			$("#box_expectCity").hide();
		}
		
	});
	
}
function seach(){
	var rootUrl = "../recruit/ListMainSearch.do";
	var cityid = "-1";
	var a = $("#workplaceSelect .current");
	cityid = a.attr("data-id");
	var param = [];
	param.push("a="+$.query.get("a"));
	param.push("mainType="+$.query.get("mainType"));
	param.push("type="+$.query.get("type"));
	param.push("key="+$("#key").val());
	param.push("more=1");
	param.push("city="+cityid);
	 
	$("#selecteditem ul>li").each(function(i,n){
		param.push($(this).attr("data-id")+"="+$(this).attr("data-value"));
		 
	});
	
	var url = rootUrl+"?"+param.join("&");
	
    window.location.href=url;
	
}

function remvoeSelectItem(){
	$(event.srcElement).parent().remove();
	seach();
}