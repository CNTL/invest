$(document).ready(function () {
	jobList.init();
});
var jobList = {
	init : function(){
		jobList.myRecruit(0);
	},
	myRecruit : function(){//我收藏的职位
		jobList.query("../user/resume.do?a=myRecruit&curPage=" + curPage);
	},
	recruitResume : function(){//已投递简历的职位
		jobList.query("../user/resume.do?a=recruitResume&curPage=" + curPage);
	},
	queryMore : function(){
		var queryType = $("#queryType").val();
		var curPage = $("#curPage").val();
		curPage = parseInt(curPage, 10) + 1;
		if(queryType=='myRecruit'){
			jobList.myRecruit(curPage);
		} else if(queryType=='recruitResume'){
			jobList.recruitResume(curPage);
		}
	},
	query : function(url){
		$.ajax({
	        type:"GET", //请求方式  
	        url: url, //请求路径  
	        cache: false,
	        dataType: 'JSON',   //返回值类型  
	        success:function(data){
	        	alert(data);
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
    		html += '<a href="../user/recruit.do?a=detail&mainType=3&id='+id+'">'+jobName+'</a>';
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
	}
}