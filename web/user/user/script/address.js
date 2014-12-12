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
	var loading = -1;
	$.ajax({url: dataUrl, async:true, dataType:"json",
		data:address,
		beforeSend:function(XMLHttpRequest){
			loading = layer.msg("正在提交数据...", 0, 16);
		},
		success: function(datas) {
			if(datas.success){
				window.location.reload();
			}
		},
		complete: function(XMLHttpRequest, textStatus){
			layer.close(loading);
		},
		error:function (XMLHttpRequest, textStatus, errorThrown) {
			layer.close(loading);
			layer.alert('数据提交失败！', 3);
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