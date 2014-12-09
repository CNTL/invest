var proj_form = {
	proj_mode_temp_id:0,
	proj_modes : [],
	eventList : [
		{id:"proj_province",evt:"change", fn:"changeProvince"}, 
		{id:"proj_city",evt:"change", fn:"changeCity"}, 
		{id:"proj_countDay_sel",evt:"change", fn:"changeCountDaySel"}
		],
	DEFAULT_PAIR : {key:"id",value:"name"},
	init : function(){
		//按事件表依次绑定
		for (var i = 0; i < proj_form.eventList.length; i++) {
			var h = proj_form.eventList[i];
			$("#" + h.id).bind(h.evt, proj_form[h.fn]);
		}
		
		var c_img_t = 40;//$("#proj_step1").offset().top+
		var c_img_l = 560;//$("#proj_step1").offset().left + 
		$("#proj_coverIMG_div").css("top",c_img_t+"px").css("left",c_img_l+"px").css("display","");
		
		proj_form.changeCountDaySel();
		proj_form.initProjTypes();
		//proj_form._setOptions("proj_type",proj_datas.getProjTypes(),proj_form.DEFAULT_PAIR);
		proj_form._setOptions("proj_province",proj_datas.getProvinces(),proj_form.DEFAULT_PAIR);
		
		proj_form.initUploadify("uploadify","queueItemCount","proj_imgURL","uploadErrorMsg",true,proj_form.imgUploaded);
		$("#btnNext").bind("click",proj_form["next"]);
		$("#btnPre").bind("click",proj_form["pre"]);
		$("#btnSave").bind("click",proj_form["save"]);
		$("#proj_newmode").bind("click",proj_form["addMode"]);
		
		$("body").delegate("#mode_countGoal_free_chk","click",proj_form["freeCheckClick"]);
		$("body").delegate("#mode_price_free_chk","click",proj_form["freeCheckClick"]);
		$("body").delegate("#mode_freight_free_chk","click",proj_form["freeCheckClick"]);
		
		$("#form1").validationEngine({
			autoPositionUpdate:false
		});
		
		proj_form.showEdit();
	},
	showEdit : function(){
		var proj_id = $("#proj_id").val();
		if(!proj_id || proj_id<=0) return;
		var dataUrl = "../project/ProjectFetcher.do?action=edit&id="+$("#proj_id").val();
		var timestamp = new Date().getTime();
		$.ajax({url: dataUrl, async:true, dataType:"json",
			beforeSend:function(XMLHttpRequest){
				var bHeight = $("body").height();
				var loadingHtml = '<div id="refreshPage_cover_div_'+timestamp+'" style="z-index:999;position:absolute;background-color:#C8C8C8;width:100%; height:'+bHeight+'px; display: block; left: 0px;top: 0px; filter: alpha(opacity=40); -moz-opacity: 0.4; -khtml-opacity: 0.4;opacity: 0.4;">';
					loadingHtml += '<div id="refreshPage_loadingHTML_'+timestamp+'" style="background: none repeat scroll 0 0 #0088CC;border-radius: 10px 10px 10px 10px;left: 44%;top: 50%; position: absolute;  padding: 1%;text-align: center; width:10%;color:#FFF;"><img src="data:image/gif;base64,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" alt="正在加载数据..."><div><em>正在加载数据...</em></div></div>';
					loadingHtml += '</div>';
				$("body").append(loadingHtml);	
			},
			success: function(datas) {
				$('#proj_type_select li').removeClass("current");
				$('#proj_type_select li').each(function(i,n){
					if($(this).attr("data") == datas.proj.type){
						$(this).addClass("current");
					}
				});
				$('#proj_type_select li.current').trigger("click");
				$("#proj_id").val(datas.proj.id);
				$("#proj_order").val(datas.proj.order);
				$("#proj_name").val(datas.proj.name);
				$("#proj_province").val(datas.proj.province).trigger("change");
				$("#proj_city").val(datas.proj.city).trigger("change");
				$("#proj_county").val(datas.proj.county);
				$("#proj_amountGoal").val(datas.proj.amountGoal);
				$("#proj_timeType").val(datas.proj.timeType);
				$("#proj_countDay").val(datas.proj.countDay);
				$("#proj_imgURL").val(datas.proj.imgUrl);
				proj_form.imgUploaded();
				$("#proj_videoURL").val(datas.proj.videoUrl);
				$("#proj_summary").val(datas.proj.summary);
				//CKEDITOR.instances.proj_content.setData(datas.proj.content);
				var countDay = datas.proj.countDay;
				var countMonth = countDay/30;
				if(countMonth != 1 && countMonth !=1.5 && countMonth !=2 && countMonth!=3){
					$("#proj_countDay_sel").val("0").trigger("change");
				}else{
					$("#proj_countDay_sel").val(countMonth).trigger("change");
				}
				for(var i=0;i<datas.modes.length;i++){
					var m = datas.modes[i];
					var mode = {"mode_id":m.id,"mode_projID":m.projId,"mode_imgURL":m.imgURL,
						"mode_name":m.name,"mode_price":m.price,
						"mode_countGoal":m.countGoal,"mode_return":m.returnContent,
						"mode_freight":m.freight,"mode_returntime":m.returntime,
						"mode_deleted":m.deleted,"mode_status":m.status};
					proj_form.updateModeList(mode);
				}
			},
			complete: function(XMLHttpRequest, textStatus){
				$("#refreshPage_cover_div_"+timestamp).remove();
			},
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				$("#refreshPage_cover_div_"+timestamp).remove();
			}
		});
	},
	freeCheckClick : function(){
		var ckID = $(this).attr("id");
		var id = proj_form._replaceAll(ckID,"_free_chk","");
		if($(this).is(":checked")){
			proj_form._setReadonly(id).val("0");
		}else{
			proj_form._removeReadonly(id).val("");
		}
	},
	_getModeHTML : function(id){
		var mode = {"mode_id":id,"mode_projID":"","mode_imgURL":"","mode_name":"","mode_price":"","mode_countGoal":"",
					"mode_return":"","mode_freight":"","mode_returntime":"","mode_deleted":"0","mode_status":"1"};
		for(var i=0;i<proj_form.proj_modes.length;i++){
			if(proj_form.proj_modes[i].mode_id == id){
				mode = proj_form.proj_modes[i];
				if(mode.mode_imgURL=="img/gift.png"){
					mode.mode_imgURL="";
				}
			}
		}
		var mode_image_tr_display = "";
		if(mode.mode_imgURL=="") mode_image_tr_display = "style=\"display:none;\"";
		var html = "<form id=\"form2\" action=\"\" method=\"post\">";
		html += "<div class=\"job_add proj_mode\" style=\"margin-bottom:20px;margin-left:50px;width:460px;\">";
		html += "<input type=\"hidden\" id=\"mode_id\" value=\""+mode.mode_id+"\" />";
		html += "<input type=\"hidden\" id=\"mode_projID\" value=\""+mode.mode_projID+"\" />";
		html += "<input type=\"hidden\" id=\"mode_name\" value=\""+mode.mode_name+"\" />";
		html += "<input type=\"hidden\" id=\"mode_deleted\" value=\""+mode.mode_deleted+"\" />";
		html += "<input type=\"hidden\" id=\"mode_status\" value=\""+mode.mode_status+"\" />";
		html += "<div class=\"input\"><label>支持金额：</label><input type=\"text\" id=\"mode_price\" name=\"mode_price\" class=\"validate[required,custom[number]]\" style=\"width:86px;margin-right:10px;\" value=\""+mode.mode_price+"\" /><label for=\"mode_price_free_chk\" style=\"display: inline-flex;width: 50px;text-align: left;height: 18px;line-height: 18px;margin-bottom: 0px;font-weight: normal;margin-right:10px;\"><input type=\"checkbox\" id=\"mode_price_free_chk\" style=\"width: 18px;height: 18px;line-height: 18px;padding: 0px;margin: 0px;\" />免费</label>";
		html += "<label>回报限额：</label><input type=\"text\" id=\"mode_countGoal\" name=\"mode_countGoal\" class=\"validate[required,custom[number]]\" style=\"width:76px;margin-right:10px;\" value=\""+mode.mode_countGoal+"\" /><label for=\"mode_countGoal_free_chk\" style=\"display: inline-flex;width: 50px;text-align: left;height: 18px;line-height: 18px;margin-bottom: 0px;font-weight: normal;\"><input type=\"checkbox\" id=\"mode_countGoal_free_chk\" style=\"width: 18px;height: 18px;line-height: 18px;padding: 0px;margin: 0px;\" />不限</label></div>";
		html += "<div class=\"input\"><table style=\"width:100%\"><tr><td valign=\"top\"><label>回报内容：</label></td><td><div class=\"text\"><textarea name=\"mode_return\" id=\"mode_return\" class=\"validate[required]\" style=\"width:380px;height:80px;\">"+mode.mode_return+"</textarea></div></td></tr></table></div>";
		html += "<div class=\"input\"><table style=\"width:100%;\"><tr><td valign=\"top\" style=\"width:90px;\"><label>封面图片：</label></td>";
		html += "	<td><input type=\"file\" class=\"uploadify\" name=\"mode_uploadify\" id=\"mode_uploadify\" />";
		html += "		<input type=\"hidden\" id=\"mode_queueItemCount\" name=\"mode_queueItemCount\" value=\"0\" />";
		html += "		<input type=\"hidden\" id=\"mode_imgURL\" name=\"mode_imgURL\" value=\""+mode.mode_imgURL+"\" />";
		html += "		<input type=\"hidden\" id=\"mode_uploadErrorMsg\" name=\"mode_uploadErrorMsg\" value=\"\" />";
		html += "	</td></tr><tr id=\"mode_image_tr\" "+mode_image_tr_display+"><td><a href=\"javascript:void();\" onclick=\"proj_form.delModeImage();\">删除</a></td><td><img id=\"mode_image\" src=\""+webroot+mode.mode_imgURL+"\" style=\"border:0px;width:380px;height:80px;\"/></td></tr></table></div>";
		html += "<div class=\"input\"><label>运费：</label><input type=\"text\" id=\"mode_freight\" name=\"mode_freight\" class=\"validate[required,custom[number]]\" style=\"width:300px;margin-right:10px;\" value=\""+mode.mode_freight+"\" /><label for=\"mode_freight_free_chk\" style=\"display: inline-flex;width: 50px;text-align: left;height: 18px;line-height: 18px;margin-bottom: 0px;font-weight: normal;\"><input type=\"checkbox\" id=\"mode_freight_free_chk\" style=\"width: 18px;height: 18px;line-height: 18px;padding: 0px;margin: 0px;\" />免费</label></div>";
		html += "<div class=\"input\"><label>回报时间：</label><input type=\"text\" id=\"mode_returntime\" name=\"mode_returntime\" class=\"validate[required]\" value=\""+mode.mode_returntime+"\" /></div>";
		html += "<div class=\"btn\"><input type=\"button\" id=\"btnModeOK\" name=\"btnModeOK\" value=\"确定\" style=\"width:100px;\" /><input type=\"button\" id=\"btnModeCannel\" name=\"btnModeCannel\" value=\"取消\" style=\"width:100px;margin-left:50px;\"/></div>";
		html += "</div>";
		html += "</form>";
		
		return html;
	},
	updateMode : function(){
		if(!proj_form._validStep2()) return false;
		var modeID = $(".proj_mode #mode_id").val();
		var modeProjID = $(".proj_mode #mode_projID").val();
		var modeImgURL = $(".proj_mode #mode_imgURL").val();
		if(modeImgURL=="") modeImgURL = "img/gift.png";
		if(!modeID || modeID=="" || modeID=="0"){
			proj_form.proj_mode_temp_id = proj_form.proj_mode_temp_id+1;
			modeID = "TEMP_"+proj_form.proj_mode_temp_id;
		}
				
		var mode = {"mode_id":modeID,"mode_projID":modeProjID,"mode_imgURL":modeImgURL,
					"mode_name":$(".proj_mode #mode_name").val(),"mode_price":$(".proj_mode #mode_price").val(),
					"mode_countGoal":$(".proj_mode #mode_countGoal").val(),"mode_return":$(".proj_mode #mode_return").val(),
					"mode_freight":$(".proj_mode #mode_freight").val(),"mode_returntime":$(".proj_mode #mode_returntime").val(),
					"mode_deleted":$(".proj_mode #mode_deleted").val(),"mode_status":$(".proj_mode #mode_status").val()};
		proj_form.updateModeList(mode);
	},
	updateModeList : function(mode){
		var mode_exist = false;
		for(var i=0;i<proj_form.proj_modes.length;i++){
			if(proj_form.proj_modes[i].mode_id == mode.mode_id){
				mode_exist = true;
				proj_form.proj_modes[i].mode_projID = mode.mode_projID;
				proj_form.proj_modes[i].mode_name = mode.mode_name;
				proj_form.proj_modes[i].mode_imgURL = mode.mode_imgURL;
				proj_form.proj_modes[i].mode_price = mode.mode_price;
				proj_form.proj_modes[i].mode_countGoal = mode.mode_countGoal;
				proj_form.proj_modes[i].mode_return = mode.mode_return;
				proj_form.proj_modes[i].mode_freight = mode.mode_freight;
				proj_form.proj_modes[i].mode_returntime = mode.mode_returntime;
				proj_form.proj_modes[i].mode_deleted = mode.mode_deleted;
				proj_form.proj_modes[i].mode_status = mode.mode_status;
			}
		}
		if(!mode_exist) proj_form.proj_modes.push(mode);
		var box_id = "box_"+mode.mode_id;
		var header = "<div class=\"box\" id=\""+box_id+"\">";
		var html = "<div class=\"box_top\"></div><div class=\"box_main project\"><div>";
		html += "<div class=\"title\"><span style=\"float:left;\">金额：<a href=\"javascript:void();\">"+mode.mode_price+"</a></span>";
		html += "<span style=\"float:right;\">限额：<a href=\"javascript:void();\">"+mode.mode_countGoal+"</a></span></div>";
		html += "<div class=\"pic\"><a href=\"javascript:void();\"><img src=\""+webroot+mode.mode_imgURL+"\" /></a></div>";
		html += "<div class=\"desc\" title=\""+mode.mode_return+"\">"+proj_form._replaceAll(mode.mode_return,"\n","<br />")+"</div>";
		html += "<div class=\"status\">回报运费：<span>"+mode.mode_freight+"<span></div>";
		html += "<div class=\"status\">回报时间：<span>"+mode.mode_returntime+"<span></div>";
		html += "</div><div class=\"tool\"><a href=\"javascript:void();\" onclick=\"proj_form.delMode('"+mode.mode_id+"')\" class=\"delete\"></a>";
		html += "<a href=\"javascript:void();\" onclick=\"proj_form.editMode('"+mode.mode_id+"')\" class=\"edit\"></a></div>";
		html += "</div><div class=\"box_bottom\"></div>";
		var end = "</div>";
		if(mode_exist && $(".project_list .block1 .box#"+box_id).length==1){
			$(".project_list .block1 .box#"+box_id).empty();
			$(".project_list .block1 .box#"+box_id).html(html);
		}else{
			$(".project_list .block1 .box#box_new").before(header+html+end);
		}
	},
	openModeDlg : function(title,html){
		var pagei = $.layer({
			type: 1,   //0-4的选择,
			title: title,
			maxmin: false,
			border: [10, 0.2, '#000'],
			closeBtn: [1, true],
			shadeClose: false,
			fix: true,
			zIndex : 1000,
			area: ['600px', '600px'],
			page: {
				html: html //此处放了防止html被解析，用了\转义，实际使用时可去掉
			}
		});
		$("#btnModeCannel").click(function(){
			if($(".formError").length>0){
				$(".formError").click();
			}
			layer.close(pagei);
		});
		$("#btnModeOK").click(function(){
			if(!proj_form._validStep2()) return;
			proj_form.updateMode()
			if($(".formError").length>0){
				$(".formError").click();
			}
			layer.close(pagei);
		});
		proj_form.initUploadify("mode_uploadify","mode_queueItemCount","mode_imgURL","mode_uploadErrorMsg",true,proj_form.modeImageUploaded);
		$("#form2").validationEngine("attach",{
			autoPositionUpdate:false,//是否自动调整提示层的位置
			scroll:false,//屏幕自动滚动到第一个验证不通过的位置
			focusFirstField:false,//验证未通过时，是否给第一个不通过的控件获取焦点
			promptPosition:"topRight" //验证提示信息的位置，可设置为："topRight", "bottomLeft", "centerRight", "bottomRight" 
		});
	},
	addMode : function(){
		proj_form.openModeDlg("新增", proj_form._getModeHTML(""));
	},
	editMode : function(id){
		proj_form.openModeDlg("修改", proj_form._getModeHTML(id));
	},
	delMode : function(id){
		var index = -1;
		for(var i=0;i<proj_form.proj_modes.length;i++){
			if(proj_form.proj_modes[i].mode_id == id){
				index = i;
			}
		}
		if(index>-1){
			var box_id = "box_"+id;
			proj_form.proj_modes.splice(index,1);
			$(".project_list .block1 .box#"+box_id).remove();
		}
	},
	delModeImage : function(){
		$(".proj_mode #mode_imgURL").val("");
		$("#mode_image_tr").css("display","none");
		$("#mode_image").attr("src","");
	},
	modeImageUploaded : function(){
		var imgURL = $(".proj_mode #mode_imgURL").val();
		$("#mode_image_tr").css("display","");
		$("#mode_image").attr("src",webroot+imgURL);
	},
	next : function(){
		if(!proj_form._validStep1()) return;
		$("#proj_step1").hide();
		$("#proj_step2").show();
		$("html,body").animate({scrollTop:$("#proj_step2").offset().top-20},0);
		var typeName = $('#proj_type_select li.current').text();
		$("#proj_info").html("["+typeName+"] "+$("#proj_name").val()+" | 众筹金额："+$("#proj_amountGoal").val()+" | 众筹期限："+$("#proj_countDay").val()+"天");
		
		layer.tips('点击可以新增支持模式...', $("a#proj_newmode"), {
			style: ['background-color:#78BA32; color:#fff', '#78BA32'],
			maxWidth:185,
			time: 5,
			closeBtn:[0, true]
		});
	},
	pre : function(){
		$("#proj_step1").show();
		$("#proj_step2").hide();
		$("html,body").animate({scrollTop:$("#proj_step1").offset().top-20},0);
	},
	_formData : function(){
		var form = {};
		form["proj_id"] = $("#proj_id").val();
		form["proj_type"] = $("#proj_type").val();
		form["proj_name"] = $("#proj_name").val();
		form["proj_province"] = $("#proj_province").val();
		form["proj_city"] = $("#proj_city").val();
		form["proj_county"] = $("#proj_county").val();
		form["proj_amountGoal"] = $("#proj_amountGoal").val();
		form["proj_timeType"] = $("#proj_timeType").val();
		form["proj_countDay_sel"] = $("#proj_countDay_sel").val();
		form["proj_countDay"] = $("#proj_countDay").val();
		form["proj_imgURL"] = $("#proj_imgURL").val();
		form["proj_videoURL"] = $("#proj_videoURL").val();
		form["proj_summary"] = $("#proj_summary").val();
		form["proj_content"] = CKEDITOR.instances.proj_content.getData();
		form["proj_order"] = $("#proj_order").val();
		
		var proj = {};
		proj["project"] = form;
		proj["modes"] = proj_form.proj_modes;
		return proj;
	},
	_validStep1 : function() {
		if (!$("#form1").validationEngine("validate")){
			//验证提示
			$("#form1").validationEngine("updatePromptsPosition");
			return false;
		}
		return true;
	},
	_validStep2 : function() {
		if (!$("#form2").validationEngine("validate")){
			//验证提示
			$("#form2").validationEngine({scroll:false});
			return false;
		}
		return true;
	},
	_save : function(){
		var paramData = {"param":JSON.stringify(proj_form._formData())};
		var timestamp = new Date().getTime();
		$.ajax({type: "POST", url: "../project/Submit.do", async:true, 
			data: paramData,
			dataType:"JSON",
			beforeSend:function(XMLHttpRequest){
				proj_form.ajaxBeforeSend(timestamp);
			},
			success: function(msg){
				if (msg.success) {				
					window.location.href = "../project/List.do";
				} else {
					var err = "保存失败";
					if(msg.errors.length>0){
						for(var i=0;i<msg.errors.length;i++){
							err = msg.errors[i].message+"\n";
						}
					}
					alert(err);
				}
			},
			complete: function(XMLHttpRequest, textStatus){
				proj_form.ajaxComplete(timestamp);
			},
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				alert(errorThrown + ':' + textStatus);  // 错误处理
				proj_form.ajaxComplete(timestamp);
			}
		});
	},
	save : function(){
		//$("#proj_imgURL").val("");
		//$("#uploadErrorMsg").val("");
		//var queueItemCount = parseInt($("#queueItemCount").val());
		//if(queueItemCount>1){
		//	alert("您只能选择一个要上传的附件!");
		//	return false;
		//}
		//if(queueItemCount==1){
		//	$('#uploadify').uploadify('upload');
		//}else{
			//alert("没有要上传的文件，直接保存！");
			//proj_form._save();
		//}
		
		proj_form._save();
	},
	initProjTypes : function(){
		$("#proj_type_select li").remove();
		var types = proj_datas.getProjTypes();
		for(var i=0;i<types.length;i++){
			var className = "";
			if(i == 0) className = " class=\"current\"";
			var li = "<li data=\""+types[i].id+"\""+className+">"+types[i].name+"</li>";
			$("#proj_type_select").append(li);
		}
		
		$('#proj_type_select>li').click(function () {
			$('#proj_type_select>li').removeClass('current');
			$(this).addClass('current');
			$('#proj_type').val($(this).attr('data'));
		});
		
		$('#proj_type_select li.current').trigger("click");
	},
	changeProvince : function(){
		var cities = [];
		var pid = $("#proj_province").val();
		cities = proj_datas.getCities(pid);
		proj_form._setOptions("proj_city",cities,proj_form.DEFAULT_PAIR);
	},
	changeCity : function(){
		var counties = [];
		var cid = $("#proj_city").val();
		counties = proj_datas.getCounties(cid);
		proj_form._setOptions("proj_county",counties,proj_form.DEFAULT_PAIR);
	},
	changeCountDaySel : function(){
		var selValue = $("#proj_countDay_sel").val();
		proj_form._setReadonly("proj_countDay");
		if(!selValue || selValue<=0){
			proj_form._removeReadonly("proj_countDay");
		}else{
			$("#proj_countDay").val(selValue*30).css("font-size","18px").css("text-align","center");
		}
	},
	imgUploaded : function(){
		$("#proj_coverIMG_div").empty();
		if($("#proj_imgURL").val()!=""){
			$("#proj_coverIMG_div").html("<img src=\""+webroot+$("#proj_imgURL").val()+"\" border=\"0\" style=\"width:400px;height:276px;\" />");
			$("#proj_coverIMG_div").append("<div style=\"width:100%;margin-top:10px;text-align:center;\"><a href=\"javascript:void();\" style=\"background: url(../img/delete.png) no-repeat left;padding-left: 20px;\" onclick=\"proj_form.delCoverImg();\">删除</a></div>");
		}
	},
	delCoverImg : function(){
		$("#proj_coverIMG_div").empty();
		$("#queueItemCount").val("0");
		$("#proj_imgURL").val("");
		$("#uploadErrorMsg").val("");
	},
	initUploadify : function(el,countCtrl,imgCtrl,errorCtrl,auto,successInvok){
		var sessionid= getCookie("JSESSIONID");
		$("#"+el).uploadify({
			scriptAccess:'always',
			auto:auto,
			height: 36,
			swf: '../js/plugin/uploadify-3.2.1/uploadify.swf',
			uploader: '../Upload.do?jsessionid=' + sessionid,
			width: 90,
			cancelImg: '../js/plugin/uploadify-3.2.1/uploadify-cancel.png',
			buttonText: '选择图片',
			'fileTypeDesc': '选择图片',
			'fileTypeExts': '*.jpg;*.png;*.bmp' ,
			fileSizeLimit: 0,
			removeCompleted: true,
			multi :false,//设置为true时可以上传多个文件。
			queueSizeLimit :2,//当允许多文件生成时，设置选择文件的个数，默认值：999 。
			'onUploadStart': function(file) {//上传开始时触发（每个文件触发一次）
				//动态更新设备额值 
				//deviceName  = device.val();
				//向后台传值 
				var formdata = {
				//	"deviceName":device.val(),
				//	"DocID":currentDocIDs,
				//	"DocLibID":currentDocLibIDs,
				//	"ATT_TOPIC":$("#topic").val(),
					"folder":"upload/project"
				};
				$("#"+el).uploadify("settings", "formData", formdata);  
			} ,
			onUploadSuccess : function(file,data,response) {//上传完成时触发（每个文件触发一次）
				if(data==""){
				}else{
					var d=data.replace("\n", ' ');
					var result = eval('('+data+')');
					if(result.success){
						$("#"+imgCtrl).val(result.path);
					}else{
						$("#"+errorCtrl).val(result.msg);
					}
				}
				$("#"+countCtrl).val("0");
				if(typeof(successInvok) == "function") successInvok();
			}, 
			onSelect : function(file) {//当每个文件添加至队列后触发 
			}, 
			onDialogClose : function(queueData) {
				var queueItemCount = queueData.queueLength;
				if(queueItemCount >1){
				   $('#'+el).uploadify('cancel');//删除第一个
				   queueItemCount = queueItemCount -1;
				}
				$("#"+countCtrl).val(queueItemCount);
			},
			onDialogOpen : function() {
			},
			onClearQueue : function(queueItemCount) {
			} ,
			onSelectError : proj_form.uploadify_onSelectError,
			onUploadError : proj_form.uploadify_onUploadError
		});
	},
	uploadify_onSelectError : function(file, errorCode, errorMsg) {
        var msgText = "上传失败\n";
        switch (errorCode) {
            case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:
                //this.queueData.errorMsg = "每次最多上传 " + this.settings.queueSizeLimit + "个文件";
                msgText += "每次最多上传 " + this.settings.queueSizeLimit + "个文件";
                break;
            case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
                msgText += "文件大小超过限制( " + this.settings.fileSizeLimit + " )";
                break;
            case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
                msgText += "文件大小为0";
                break;
            case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
                msgText += "文件格式不正确，仅限 " + this.settings.fileTypeExts;
                break;
            default:
                msgText += "错误代码：" + errorCode + "\n" + errorMsg;
        }
        alert(msgText);
		return false;
    },
	uploadify_onUploadError : function(file, errorCode, errorMsg, errorString) {
        // 手工取消不弹出提示
        if (errorCode == SWFUpload.UPLOAD_ERROR.FILE_CANCELLED
                || errorCode == SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED) {
            return;
        }
        var msgText = "上传失败\n";
        switch (errorCode) {
            case SWFUpload.UPLOAD_ERROR.HTTP_ERROR:
                msgText += "HTTP 错误\n" + errorMsg;
                break;
            case SWFUpload.UPLOAD_ERROR.MISSING_UPLOAD_URL:
                msgText += "上传文件丢失，请重新上传";
                break;
            case SWFUpload.UPLOAD_ERROR.IO_ERROR:
                msgText += "IO错误";
                break;
            case SWFUpload.UPLOAD_ERROR.SECURITY_ERROR:
                msgText += "安全性错误\n" + errorMsg;
                break;
            case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
                msgText += "每次最多上传 " + this.settings.uploadLimit + "个";
                break;
            case SWFUpload.UPLOAD_ERROR.UPLOAD_FAILED:
                msgText += errorMsg;
                break;
            case SWFUpload.UPLOAD_ERROR.SPECIFIED_FILE_ID_NOT_FOUND:
                msgText += "找不到指定文件，请重新操作";
                break;
            case SWFUpload.UPLOAD_ERROR.FILE_VALIDATION_FAILED:
                msgText += "参数错误";
                break;
            default:
                msgText += "文件:" + file.name + "\n错误码:" + errorCode + "\n"
                        + errorMsg + "\n" + errorString;
        }
        alert(msgText);
		return false;;
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
	_setReadonly : function(id) {
		var src = $("#" + id);
		if (src.length > 0) {
			src.prop("readonly", true);
			src[0].style.backgroundColor = "#cccccc";
		}
		
		return src;
	},
	_removeReadonly : function(id) {
		var src = $("#" + id);
		if (src.length > 0) {
			src.prop("readonly", false).removeClass("disabled");
			src.prop("disabled", false);
			src[0].style.backgroundColor = "white";
		}
		
		return src;
	},
	_replaceAll : function(str, sptr, sptr1){
		return str.replace(new RegExp(sptr,'gm'),sptr1)
	},
	ajaxBeforeSend : function(timestamp){
		var bHeight = $(window).height();
		var loadingHtml = '<div id="refreshPage_cover_div_'+timestamp+'" style="z-index:999;position:absolute;background-color:#C8C8C8;width:100%; height:'+bHeight+'px; display: block; left: 0px;top: 0px; filter: alpha(opacity=40); -moz-opacity: 0.4; -khtml-opacity: 0.4;opacity: 0.4;">';
			loadingHtml += '<div id="refreshPage_loadingHTML_'+timestamp+'" style="background: none repeat scroll 0 0 #0088CC;border-radius: 10px 10px 10px 10px;left: 44%;top: 50%; position: absolute;  padding: 1%;text-align: center; width:10%;color:#FFF;"><img src="data:image/gif;base64,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" alt="正在加载数据..."><div><em>正在加载数据...</em></div></div>';
			loadingHtml += '</div>';
		$("#warpMain").append(loadingHtml);	
	},
	ajaxComplete : function(timestamp){
		$("#refreshPage_cover_div_"+timestamp).remove();
	}
}

//--------------初始化---------------
$(function(){
	var init = function() {
		if (!proj_datas || !proj_datas.ready) {
			setTimeout(init, 100);
			return;
		}
		proj_form.init();
	}
	init();
});
