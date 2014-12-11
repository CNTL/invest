var DEFAULT_PAIR = {key:"id",value:"name"};
$(document).ready(function () {
	
});

function getAddressInfo(id){
	var address = null;
	var dataUrl = "../org/AddressSubmit.do?action=get&id="+id;
	$.ajax({url: dataUrl, async:false, dataType:"json",
		success: function(datas) {
			address = datas;
		}
	});
	
	return address;
}

function getAddressFormHtml(id,userId){
	if(typeof(id)=="undefined" || !id) id = 0;
	var address = {"id":id,"userId":userId,"recipients":"","mphoneNo":"","telphoneNo":"",
					"provinceId":"","province":"","cityId":"","city":"","countyId":"","county":"",
					"detail":"","zipcode":"","type":0,"order":0,"deleted":0};
	if(id>0){
		var addr = getAddressInfo(id);
		if(addr != null) address = addr;
	}
	
	var html = "<form id=\"addressForm\" action=\"\" method=\"post\">";
	html += "<div class=\"job_add\" style=\"margin-top:20px;margin-bottom:20px;margin-left:50px;width:660px;\">";
	html += "<input type=\"hidden\" id=\"addr_id\" value=\""+address.id+"\" />";
	html += "<input type=\"hidden\" id=\"addr_userId\" value=\""+address.userId+"\" />";
	html += "<input type=\"hidden\" id=\"addr_type\" value=\""+address.type+"\" />";
	html += "<input type=\"hidden\" id=\"addr_order\" value=\""+address.order+"\" />";
	html += "<input type=\"hidden\" id=\"addr_deleted\" value=\""+address.deleted+"\" />";
	html += "<input type=\"hidden\" id=\"addr_telphoneNo\" value=\""+address.telphoneNo+"\" />";
	html += "<div class=\"input\"><label>收件人：</label><input type=\"text\" id=\"addr_userName\" name=\"addr_userName\" class=\"validate[required]\" style=\"width:136px;margin-right:10px;\" value=\""+address.recipients+"\" />";
	html += "<label>联系电话：</label><input type=\"text\" id=\"addr_mphoneNo\" name=\"addr_mphoneNo\" class=\"validate[required]\" style=\"width:126px;margin-right:10px;\" value=\""+address.mphoneNo+"\" /></div>";
	html += "<div class=\"input\"><label>地域：</label>";
	html += "<select id=\"addr_province\" name=\"addr_province\" class=\"validate[required]\" oldvalue=\""+address.provinceId+"\"><option value=\"\">省份</option></select>";
	html += "<select id=\"addr_city\" name=\"addr_city\" style=\"margin-left:10px;\" class=\"validate[required]\" oldvalue=\""+address.cityId+"\"><option value=\"\">城市</option></select>";
	html += "<select id=\"addr_county\" name=\"addr_county\" style=\"margin-left:10px;\" class=\"validate[required]\" oldvalue=\""+address.countyId+"\"><option value=\"\">地区</option></select>";
	html += "<input type=\"hidden\" id=\"addr_provinceName\" name=\"addr_provinceName\" value=\""+address.province+"\" />";
	html += "<input type=\"hidden\" id=\"addr_cityName\" name=\"addr_cityName\" value=\""+address.city+"\" />";
	html += "<input type=\"hidden\" id=\"addr_countyName\" name=\"addr_countyName\" value=\""+address.county+"\" />";
	html += "</div>";
	html += "<div class=\"input\"><label></label><input type=\"text\" id=\"addr_detail\" name=\"addr_detail\" class=\"validate[required]\" value=\""+address.detail+"\" /></div>";
	html += "<div class=\"input\"><label>邮编：</label><input type=\"text\" id=\"addr_zipcode\" name=\"addr_zipcode\" class=\"validate[required]\" value=\""+address.zipcode+"\" /></div>";
	html += "<div class=\"btn\"><input type=\"button\" id=\"btnAddressOK\" name=\"btnAddressOK\" value=\"确定\" style=\"width:100px;\" />";
	html += "<input type=\"button\" id=\"btnAddressCannel\" name=\"btnAddressCannel\" value=\"取消\" style=\"width:100px;margin-left:50px;\"/></div>";
	html += "</div>";
	html += "</form>";
	
	return html;
}
function _save(){
	var address = {"addr_id":$("#addr_id").val(),"addr_userId":$("#addr_userId").val(),"addr_userName":$("#addr_userName").val(),"addr_deleted":$("#addr_deleted").val(),
					"addr_mphoneNo":$("#addr_mphoneNo").val(),"addr_telphoneNo":"","addr_type":$("#addr_type").val(),"addr_order":$("#addr_order").val(),
					"addr_province":$("#addr_province").val(),"addr_provinceName":$("#addr_provinceName").val(),
					"addr_city":$("#addr_city").val(),"addr_cityName":$("#addr_cityName").val(),
					"addr_county":$("#addr_county").val(),"addr_countyName":$("#addr_countyName").val(),
					"addr_detail":$("#addr_detail").val(),"addr_zipcode":$("#addr_zipcode").val()};
	var dataUrl = "../org/AddressSubmit.do?action=update";
	var timestamp = new Date().getTime();
	$.ajax({url: dataUrl, async:true, dataType:"json",
		data:address,
		beforeSend:function(XMLHttpRequest){
			var bHeight = $(window).height();
			var loadingHtml = '<div id="refreshPage_cover_div_'+timestamp+'" style="z-index:999;position:absolute;background-color:#C8C8C8;width:100%; height:'+bHeight+'px; display: block; left: 0px;top: 0px; filter: alpha(opacity=40); -moz-opacity: 0.4; -khtml-opacity: 0.4;opacity: 0.4;">';
				loadingHtml += '<div id="refreshPage_loadingHTML_'+timestamp+'" style="background: none repeat scroll 0 0 #0088CC;border-radius: 10px 10px 10px 10px;left: 44%;top: 50%; position: absolute;  padding: 1%;text-align: center; width:10%;color:#FFF;"><img src="data:image/gif;base64,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" alt="正在加载数据..."><div><em>正在加载数据...</em></div></div>';
				loadingHtml += '</div>';
			$("body").append(loadingHtml);	
		},
		success: function(datas) {
			if(datas.success){
				window.location.reload();
			}
		},
		complete: function(XMLHttpRequest, textStatus){
			$("#refreshPage_cover_div_"+timestamp).remove();
		},
		error:function (XMLHttpRequest, textStatus, errorThrown) {
			$("#refreshPage_cover_div_"+timestamp).remove();
		}
	});
}

function addAddress(){
	if($("table.tb-void tr.tr-address").length>=20){
		layer.alert("您最多只能添加 20 个收货地址",9);
		return false;
	}
	openAddressFormDlg("新增", getAddressFormHtml("",$("#login_user_id").val()));
}
function editAddress(id,userId){
	openAddressFormDlg("修改", getAddressFormHtml(id,userId));
}
function delAddress(id){
	var i = layer.confirm("确定要删除该收件地址吗？",function(){
		var dataUrl = "../org/AddressSubmit.do?action=del&id="+id;
		$.ajax({url: dataUrl, async:false, dataType:"json",
			success: function(datas) {
				if(datas.success){
					//layer.alert("操作成功", 9, function(){});
					window.location.reload();
				}else{
					layer.close(i);
					layer.alert("操作失败", 9);
				}
			}
		});
	})
}

function openAddressFormDlg (title,html){
	var pagei = $.layer({
		type: 1,   //0-4的选择,
		title: title,
		maxmin: false,
		border: [10, 0.2, '#000'],
		closeBtn: [1, true],
		shadeClose: false,
		fix: true,
		zIndex : 1000,
		area: ['600px', '380px'],
		page: {
			html: html //此处放了防止html被解析，用了\转义，实际使用时可去掉
		}
	})
	$("#btnAddressCannel").click(function(){
		if($(".formError").length>0){
			$(".formError").click();
		}
		layer.close(pagei);
	});
	$("#btnAddressOK").click(function(){
		if(!_validForm()) return;
		if($(".formError").length>0){
			$(".formError").click();
		}
		
		_save();
		
		layer.close(pagei);
	});
	$("#addr_province").change(changeProvince);
	$("#addr_city").change(changeCity);
	$("#addr_county").change(changeCounty);
	
	_setOptions("addr_province",proj_datas.getProvinces(),DEFAULT_PAIR);
	
	$("#addressForm").validationEngine("attach",{
		autoPositionUpdate:false,//是否自动调整提示层的位置
		scroll:false,//屏幕自动滚动到第一个验证不通过的位置
		focusFirstField:false,//验证未通过时，是否给第一个不通过的控件获取焦点
		promptPosition:"topRight" //验证提示信息的位置，可设置为："topRight", "bottomLeft", "centerRight", "bottomRight" 
	});
}

function _validForm() {
	if (!$("#addressForm").validationEngine("validate")){
		//验证提示
		$("#addressForm").validationEngine({scroll:false});
		return false;
	}
	return true;
}

function changeProvince(){
	var cities = [];
	var pid = $("#addr_province").val();
	if(!pid) $("#addr_provinceName").val("");
	else $("#addr_provinceName").val($("#addr_province option:selected").text());
	cities = proj_datas.getCities(pid);
	_setOptions("addr_city",cities,DEFAULT_PAIR);
}
function changeCity(){
	var counties = [];
	var cid = $("#addr_city").val();
	if(!cid) $("#addr_cityName").val("");
	else $("#addr_cityName").val($("#addr_city option:selected").text());
	counties = proj_datas.getCounties(cid);
	_setOptions("addr_county",counties,DEFAULT_PAIR);
}

function changeCounty(){
	var cid = $("#addr_county").val();
	if(!cid) $("#addr_countyName").val("");
	else $("#addr_countyName").val($("#addr_county option:selected").text());
}

function _setOptions(id, datas, pair) {
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
	var oldvalue = $(sel).attr("oldvalue");
	if(typeof(oldvalue)!=undefined && oldvalue!=""){
		$(sel).val(oldvalue);
		$(sel).attr("oldvalue","");
	}
	$(sel).trigger("change");
}