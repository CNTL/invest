var proj_form = {
	proj_mode_temp_id:0,
	proj_modes : [],
	eventList : [
		{id:"proj_province",evt:"change", fn:"changeProvince"}, 
		{id:"proj_city",evt:"change", fn:"changeCity"}, 
		{id:"proj_countDay_sel",evt:"change", fn:"changeCountDaySel"},
		{id:"proj_payType",evt:"change",fn:"changePayType"}
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
		
		$("#btnNext").bind("click",proj_form["next"]);//绑定btnNext事件，不再执行changePayType
		$("#btnPre").bind("click",proj_form["pre"]);
		$("#btnSave").bind("click",proj_form["save"]);
		$("#proj_newmode").bind("click",proj_form["addMode"]);
		
		$("body").delegate("#mode_countGoal_free_chk","click",proj_form["freeCheckClick"]);
		$("body").delegate("#mode_price_free_chk","click",proj_form["freeCheckClick"]);
		$("body").delegate("#mode_freight_free_chk","click",proj_form["freeCheckClick"]);
				
		$("#form1").validationEngine({
			autoPositionUpdate:false
		});
		//proj_form.changePayType(); //已经绑定btnNext的按钮事件此处不再绑定
		proj_form.showEdit();
	},
	showEdit : function(){
		var proj_id = $("#proj_id").val();
		if(!proj_id || proj_id<=0) return;
		var dataUrl = "../project/ProjectFetcher.do?action=edit&id="+$("#proj_id").val();
		var loading = -1;
		$.ajax({url: dataUrl, async:true, dataType:"json",
			beforeSend:function(XMLHttpRequest){
				loading = layer.msg("正在初始化数据...", 0, 16);
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
				$("#proj_payType").val(datas.proj.payType).trigger("change");
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
				layer.close(loading);
			},
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				layer.close(loading);
				layer.alert('加载数据失败！', 3);
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
		var imgs = [];
		var mode = {"mode_id":id,"mode_projID":"","mode_imgURL":"","mode_name":"","mode_price":"","mode_countGoal":"",
					"mode_return":"","mode_freight":"","mode_returntime":"","mode_deleted":"0","mode_status":"1"};
		for(var i=0;i<proj_form.proj_modes.length;i++){
			if(proj_form.proj_modes[i].mode_id == id){
				mode = proj_form.proj_modes[i];
				imgs = mode.mode_imgURL.split(";");
			}
		}
		var mode_image_tr_display = "";
		var mode_image_upload_display = "";
		if(imgs.length>=3) mode_image_upload_display = "style=\"display:none;\"";
 		if(imgs.length<=0) mode_image_tr_display = "style=\"display:none;\"";
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
		html += "<div class=\"input\"><table style=\"width:100%;\"><tr id=\"mode_image_upload\" "+mode_image_upload_display+"><td valign=\"top\" style=\"width:90px;\"><label>回报图片：</label></td>";
		html += "	<td><input type=\"file\" class=\"uploadify\" name=\"mode_uploadify\" id=\"mode_uploadify\" />";
		html += "		<input type=\"hidden\" id=\"mode_queueItemCount\" name=\"mode_queueItemCount\" value=\"0\" />";
		html += "		<input type=\"hidden\" id=\"mode_imgURL\" name=\"mode_imgURL\" value=\""+mode.mode_imgURL+"\" />";
		html += "		<input type=\"hidden\" id=\"mode_imgURL_upload\" name=\"mode_imgURL_upload\" value=\"\" />";
		html += "		<input type=\"hidden\" id=\"mode_uploadErrorMsg\" name=\"mode_uploadErrorMsg\" value=\"\" />";
		html += "	</td></tr><tr id=\"mode_image_tr\" "+mode_image_tr_display+"><td></td><td id=\"mode_image_td\">";
		for(var i in imgs){
			if(imgs[i]!=""){
				html += "<div style=\"float:left;margin-left:10px;text-align:center;\"><img src=\""+webroot+imgs[i]+"\" style=\"border:0px;width:110px;height:68px;\"/><br/><a href=\"javascript:;\" data-src=\""+imgs[i]+"\" onclick=\"proj_form.delModeImage(this);\">删除</a></div>";
			}
		}
		html += "</td></tr></table></div>";
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
		if(modeImgURL=="") modeImgURL = "";
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
		var imgs = mode.mode_imgURL.split(";");
		var box_id = "box_"+mode.mode_id;
		var header = "<div class=\"box\" id=\""+box_id+"\">";
		var html = "<div class=\"box_top\"></div><div class=\"box_main project\"><div>";
		html += "<div class=\"title\"><span style=\"float:left;\">金额：<a href=\"javascript:void();\">"+mode.mode_price+"</a></span>";
		html += "<span style=\"float:right;\">限额：<a href=\"javascript:void();\">"+mode.mode_countGoal+"</a></span></div>";
		html += "<div class=\"pic\">";
		for(var i in imgs){
			if(imgs[i]!=""){
				html += "<img style=\"border:0px;width:70px;\" src=\""+webroot+imgs[i]+"\" />";
			}
		}
		html += "</div>";
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
		proj_form.initUploadify("mode_uploadify","mode_queueItemCount","mode_imgURL_upload","mode_uploadErrorMsg",true,proj_form.modeImageUploaded);
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
	delModeImage : function(el){
		var url = $(el).attr("data-src");
		var imgURLs = $(".proj_mode #mode_imgURL").val();
		var imgArr = imgURLs.split(";");
		var newUrls = "";
		for(var i in imgArr){
			if(imgArr[i]!=url){
				if(newUrls!="") newUrls += ";";
				newUrls += imgArr[i];
			}
		}
		$(".proj_mode #mode_imgURL").val(newUrls);
		$(el).closest("div").remove();
		$("#mode_image_upload").css("display","");
		if(newUrls == "")
			$("#mode_image_tr").css("display","none");
	},
	modeImageUploaded : function(){
		var imgURL = $(".proj_mode #mode_imgURL_upload").val();
		$("#mode_image_tr").css("display","");
		var imgHtml = "<div style=\"float:left;margin-left:10px;text-align:center;\">";
			imgHtml +="<img src=\""+webroot+imgURL+"\" style=\"border:0px;width:110px;height:68px;\"/>";
			imgHtml +="<br/><a href=\"javascript:;\" data-src=\""+imgURL+"\" onclick=\"proj_form.delModeImage(this);\">删除</a></div>";
		$("#mode_image_td").append(imgHtml);
		if($("#mode_image_td div").length>=3){
			$("#mode_image_upload").css("display","none");
		}
		
		var imgURLs = $(".proj_mode #mode_imgURL").val();
		if(imgURLs!="") imgURLs += ";";
		imgURLs += imgURL;
		$(".proj_mode #mode_imgURL").val(imgURLs)
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
		form["proj_payType"] = $("#proj_payType").val();
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
		var loading = -1;
		$.ajax({type: "POST", url: "../project/Submit.do", async:true, 
			data: paramData,
			dataType:"JSON",
			beforeSend:function(XMLHttpRequest){
				loading = layer.msg("正在保存项目信息...", 0, 16);
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
				layer.close(loading);
			},
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				layer.alert(errorThrown + ':' + textStatus,3);  // 错误处理
				layer.close(loading);
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
	saveAuction : function(){
		if(!proj_form._validStep1()) return;
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
	changePayType : function(){
		var t = $("#proj_payType").val();
		if(t == 0){			
			$("#btnNext").val("下一步");
			$('#btnNext').unbind("click",proj_form["saveAuction"]);
			$('#btnNext').bind("click",proj_form["next"]);
		}else if(t == 1){
			$("#btnNext").val("提交信息");
			$('#btnNext').unbind("click",proj_form["next"]);
			$('#btnNext').bind("click",proj_form["saveAuction"]);
		}
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
		//counties = proj_datas.getCounties(cid);
		//只到二级地区
		counties = [{"id":"0","name":"请选择"}];
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
				$(".uploadify-queue").css("display","none");
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
