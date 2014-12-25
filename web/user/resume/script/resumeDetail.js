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
	    			$("#affix").val(data[0].affix);
	    			resume.imgUploaded();
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
