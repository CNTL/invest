$(document).ready(function () {
	jobList.init();
	//初始化
	/*
	$("#form").validationEngine({
		autoPositionUpdate:true,
		onValidationComplete:function(from,r){
			if (r){
				window.onbeforeunload = null;
				$("#btnSave").attr("disabled", true);
				jobEdit.submit();
			}
		}
	});
	*/
});
var jobList = {
	init : function(){
		$("#type").selectbox();
		//$(".add").click("jobList.addRecruit");
		jobList.queryNew();
	},
	addRecruit : function(){
		$.ajax({
	        type:"GET", //请求方式  
	        url:"../user/user.do?a=findLogin", //请求路径  
	        cache: false,
	        dataType: 'JSON',   //返回值类型  
	        success:function(data){
	        	alert(data.orgFullname);
	    		if(data != null && data.orgFullname != null && data.orgFullname != ''){
	    			window.location.href = "../recruit/Edit.do";
	    		} else {
	    			$.messager.confirm('消息', '请先完善资料！', function(r){
	    				if (r){
	    					window.location.href = "../recruit/Detail.do";
	    				}
	    			});
	    		}
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				   alert("error="+errorThrown);
			}
	    });
	},
	queryNew : function(){
		jobList.query("../user/recruit.do?a=queryNew&typeFlag=" + typeFlag);
	},
	queryHot : function(){
		jobList.query("../user/recruit.do?a=queryHot&typeFlag=" + typeFlag);
	},
	query : function(url){
		$.ajax({
	        type:"GET", //请求方式  
	        url: url, //请求路径  
	        cache: false,
	        dataType: 'JSON',   //返回值类型  
	        success:function(data){
	    		if(data != null){
	    			jobList.assemble(data);
	    		}
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				   alert(errorThrown);
			}
	    });
	},
	assemble : function(result){
		var main = $("#main"); 
		var i = 0;
    	$.each(result, function(i,item){
    		var id = item.id;
    		var jobName = item.jobName;
    		var jobPictrue = item.jobPictrue;
    		if(jobPictrue == null || jobPictrue.length == 0)
    			jobPictrue = '../static/image/temp/pic2.png';
    		var company = item.company;
    		var salary = item.salary;
    		var address = item.address;
    		var days = item.days;
    		var jobAttract = item.jobAttract;
    		var createtime = item.time;
    		var resumeNum = item.resumeNum;
    		i++;
    		var html = '';
    		if(parseInt(i)%3 == 0){//一行三个招聘信息
    			html += '<div class="box box_last">';
    		} else{
    			html += '<div class="box">';
    		}
    		html += '<div class="box_top"></div>' + 
            '<div class="box_main job">' + 
                '<div class="pic">' + 
                    '<a href="#"><img src="' + jobPictrue + '" /></a>' + 
                    '<span>影聘</span>' + 
                '</div>' + 
                '<div>' + 
                    '<div class="title">';
    		if(typeFlag == "edit"){
    			html += '<a href="../user/recruit.do?a=edit&mainType=3&id='+id+'">'+jobName+'</a>';
    		} else {
    			html += '<a href="../user/recruit.do?a=detail&mainType=3&id='+id+'">'+jobName+'</a>';
    		}
    		 
    		html +=  '<span>'+company+'</span>' + 
                    '</div>' + 
                    '<div class="info">' + 
                        '<ul>' + 
                            '<li>'+salary+'</li>' + 
                            '<li>'+address+'</li>' + 
                            '<li>'+days+'</li>' + 
                        '</ul>' + 
                    '</div>' + 
                    '<div class="desc">' + 
                        '<span>'+jobAttract+'</span><br />' + 
                        '发布时间：'+createtime+'<br />' + 
                        '已投递简历人数：' + resumeNum+ 
                    '</div>' + 
                '</div>' + 
                '<div class="tool">' + 
                    '<a href="#" class="share">分享</a>' + 
                    '<a href="#" class="view"></a>' + 
                '</div>' + 
            '</div>' + 
            '<div class="box_bottom"></div>' + 
        '</div>';
    		main.append(html);
    	});
	},
	change : function(obj) {
		$("#main").html("");
		if(obj.id=='queryNew'){
			jobList.queryNew();
		} else if(obj.id=='queryHot'){
			jobList.queryHot();
		}
		jobList.setClass(obj);
	},
	setClass : function (obj){
		$(obj).parent().find("a").each(function(){
		    $(this).removeClass("current");
		});
		$(obj).addClass("current");
	}
}