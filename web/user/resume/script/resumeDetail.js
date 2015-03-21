$(document).ready(function () {
	resume.init();
});
var resume = {
	init : function(){
		var id = $("#id").val();
		if(id == null && id.length == 0) return;
		
		$.ajax({
	        type:"GET", //请求方式  
	        url:"../user/resume.do?a=curResume&id=" + id, //请求路径  
	        cache: false,
	        dataType: 'JSON',   //返回值类型  
	        success:function(data){
	    		if(data != null && data!='' && data.length == 1){
	    			$("#name").html(data[0].name);
	    			$("#contentTxt").html(data[0].content);
	    			$("#height").html(data[0].height);
	    			$("#weight").html(data[0].weight);
	    			$("#school").html(data[0].school);
	    			$("#professional").html(data[0].professional);
	    			$("#degree").html(data[0].degree);
	    			$("#headuser").append($(".avatar").html());
	    			$("#username").html($("#hzusername").html());
	    			
	    			$.ajax({
	    		        type:"GET", //请求方式  
	    		        url:"../user/user.do?a=getUser&id="+data[0].userId, //请求路径  
	    		        cache: false,
	    		        dataType: 'JSON',   //返回值类型  
	    		        success:function(data){
	    		    		if(data != null){
	    		    			
	    		    			$("#gender").html((data.gender=="1")?"男":"女");
	    		    			$("#birthdate").html(data.birthdate);
	    		    			$("#username").html(data.name);
	    		    			$("#perPhone").html(data.perPhone);
	    		    			$("#perJob").html(data.perJobName);
	    		    		}
	    		        } ,
	    				error:function (XMLHttpRequest, textStatus, errorThrown) {
	    					  
	    				}
	    		    });
	    		}
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				   alert(errorThrown);
			}
	    });
	},
	imgUploaded : function(){
		var c_img_t = 525;
		var c_img_l = 300;
		$("#coverIMG_div").css("top",c_img_t+"px").css("left",c_img_l+"px").css("display","");
		$("#coverIMG_div").empty();
		var affix = $("#affix").val();
		if(affix != ""){
			var lastIndex = affix.lastIndexOf("\\");
			var name = affix.substring(lastIndex + 1, affix.length);
			$("#coverIMG_div").html("<a href=\"" + rootPath + affix +"\" border=\"0\" style=\"width:300px;height:40px;\" >"+name+"</a>");
		}
	}
}
