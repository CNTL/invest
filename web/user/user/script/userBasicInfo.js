$(document).ready(function () {
	$(".menu-list").height("1200px");
	editorInit ();
	$("#degreeid").change(function(){
		$("#degree").val($("#degreeid :selected").text());
	});
	//初始化
	complete.init();
	$("#form").validationEngine({
		autoPositionUpdate:true,
		onValidationComplete:function(from,r){
			if (r){
				window.onbeforeunload = null;
				$("#btnSave").attr("disabled", true);
				complete.submit();
			}
		}
	});
	$.each($("#form :checkbox"),function(i,n){
		
		$(this).click(function(){
			var cbvalue = 1;
			if(!$(this).prop("checked")){
				cbvalue = 0;
			}
			var cbinput = $(this).attr("id").toString().replace("cb","");
			if($("#"+cbinput).length>0){
				$("#"+cbinput).val(cbvalue);
			}
		});
		
	});
	 
});
function selectrec(){
	var event = event.srcElement || event.target;
	$this = $(event);
	$this.toggleClass("selected");
	setRec();
}
function setRec(){
	var recs = $("#modal-body").find(".selected");
	var recIDs = [];
	var recNames = [];
	$.each(recs,function(i,n){
		recIDs.push($(this).attr("data-id"));
		recNames.push($(this).attr("data-name"));
	});
	$("#recIDs").val(recIDs.join(","));
	$("#recNames").val(recNames.join(","));
	$("#perjob").val(recNames.join(","));
}
var complete = {
		DEFAULT_PAIR : {key:"id",value:"name"},
	init : function(){
		$.ajax({
	        type:"GET", //请求方式  
	        url:"../user/user.do?a=findLogin", //请求路径  
	        cache: false,
	        dataType: 'JSON',   //返回值类型  
	        success:function(data){
	    		if(data != null){
	    			$("#code").val(data.code);
	    			$("#name_showcb").prop("checked",(data.name_show==1?true:false));
	    			$("#name_show").val(data.name_show);
	    			$("#name").val(data.name);
	    			$("#perNickName").val(data.perNickName);
	    			$("#perPostAddr").val(data.perPostAddr);
	    			$("#perPostCode").val(data.perPostCode);
	    			$("#gender").val(data.gender);
	    			$("#birthdate").val(data.birthdate);
	    			$("#intro_editer").val(data.intro);
	    			$("#intro_showcb").prop("checked",(data.intro_show==1?true:false));
	    			$("#intro_show").val(data.intro_show);
	    			$("#province").val(data.province);
	    			$("#perjob").val(data.perJobName);
	    			$("#recNames").val(data.perJobName);
	    			$("#recIDs").val(data.perJob);
	    			$("#point").html(data.point);
	    			$("#height").val(data.height);
	    			$("#height_showcb").prop("checked",(data.height_show==1?true:false));
	    			$("#height_show").val(data.height_show);
	    			$("#weight").val(data.weight);
	    			$("#weight_showcb").prop("checked",(data.weight_show==1?true:false));
	    			$("#weight_show").val(data.weight_show);
	    			$("#school").val(data.school);
	    			$("#school_showcb").prop("checked",(data.school_show==1?true:false));
	    			$("#school_show").val(data.school_show);
	    			$("#professional").val(data.professional);
	    			$("#professional_showcb").prop("checked",(data.professional_show==1?true:false));
	    			$("#professional_show").val(data.professional_show);
	    			$("#degreeid").val(data.degreeid);
	    			$("#degree").val(data.degree);
	    			$("#degree_showcb").prop("checked",(data.degree_show==1?true:false));
	    			$("#degree_show").val(data.degree_show);
	    			
	    			$.getJSON("../user/user.do?a=ageDatas", function(json){
	    				  if(json.ageTypes){
	    					  complete._setOptions("age",json.ageTypes,complete.DEFAULT_PAIR);
	    					  if(data.ageTypeID<=0){
	    						  $("#age option:first").prop("selected",true);
	    					  }
	    					  else{
	    						  $("#age").val(data.ageTypeID);
	    					  }
	    					  
	    					 
	    				  }
	    			});
	    			complete.setrecs(data.perJob);
	    			complete.changeProvince(data.city);
	    		}
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				   alert(errorThrown);
			}
	    });
		complete._setOptions("province",proj_datas.getProvinces(),complete.DEFAULT_PAIR);
	},
	setrecs:function(val){
		var recids = val.toString().split(",");
		 
		for(var i=0;i<recids.length;i++){
			$("#modal-body a[data-id='"+recids[i]+"']").addClass("selected")
		}
		
	},
	changeProvince : function(val){
		var cities = [];
		var pid = $("#province").val();
		cities = proj_datas.getCities(pid);
		complete._setOptions("city",cities,complete.DEFAULT_PAIR);
		if(val!=null&&val>0){
			$("#city").val(val);
		}
		
	},
	_setOptions : function(id, datas, pair) {
		var sel = document.getElementById(id);
		if (!sel) return;
		
		while (sel.options.length > 0)
			sel.options.remove(0);

		for (var i = 0; i < datas.length; i++) {
			var op = document.createElement("OPTION");
			op.value = datas[i][pair.key];
			op.text = datas[i][pair.value];
			sel.options.add(op);
		}
		$(sel).trigger("change");
	},
	submit : function(){
		$("#intro").val(CKEDITOR.instances.intro_editer.getData());
		$.ajax({
	        type:"POST", //请求方式  
	        url:"../user/user.do?a=userBasicInfo", //请求路径  
	        cache: false,
	        data:$('#form').serialize(),  //传参 
	        dataType: 'text',   //返回值类型  
	        success:function(data){
	    		if(data != null && data == 'ok'){
	    			$.messager.alert('消息','修改资料成功！');
	    		} else {
	    			$.messager.alert('修改失败',data);
	    		}
	    		$("#btnSave").attr("disabled", false);
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				   alert(errorThrown);
			}
	    });
	}
}
function editorInit (){
	var editor = CKEDITOR.replace("intro_editer",
			{
				toolbar :
				[
					//加粗     斜体，     下划线      穿过线      下标字        上标字
					['Bold','Italic','Underline','Strike','Subscript','Superscript'],
					//数字列表          实体列表            减小缩进    增大缩进
					['NumberedList','BulletedList','-','Outdent','Indent'],
					//左对齐             居中对齐          右对齐          两端对齐
					['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
					//超链接 取消超链接 锚点
					['Link','Unlink','Anchor'],
					//图片    flash    表格       水平线            表情       特殊字符        分页符
					['Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],
					'/',
					//样式       格式      字体    字体大小
					['Styles','Format','Font','FontSize'],
					//文本颜色     背景颜色
					['TextColor','BGColor'],
					//全屏           显示区块
					['Maximize', 'ShowBlocks','-']
				]
			}
		);
}