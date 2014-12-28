$(function(){
	initAddrList();
	
	$("input[name='address']").each(function(i,n){
		if($(this).is(":checked")){
			$(this).trigger("click");
		}
	});
	if(price == 0){
		$("div.order-box").css("display","none");
		$("div.pay-detial .supdtl-head").css("display","none");
		$("div.pay-detial .supdtl-cont-top").css("display","none");
		$("div.pay-detial .order-detail").css("display","");
	}else{
		$("div.order-box").css("display","");
		$("div.pay-detial .supdtl-head").css("display","");
		$("div.pay-detial .supdtl-cont-top").css("display","");
		$("div.pay-detial .order-detail").css("display","none");
	}
	//回报内容 回车转为html换行
	$(".supdtl-cont span.morecon").html($(".supdtl-cont span.morecon").text().replace(/[\r\n]/g,"<br />"));
	
	$("#custom_price").on("keyup",function(){
		$("span#span_totalfee").text("￥");
		$("input#order_totalfee").val("");
		
		if (!$("#form").validationEngine("validate")){
			//验证提示
			$("#form").validationEngine("updatePromptsPosition");
			return false;
		}
		
		$("span#span_totalfee").text("￥"+$(this).val());
		$("input#order_totalfee").val($(this).val());
	});
	
	$("#form").validationEngine({
		autoPositionUpdate:false,//是否自动调整提示层的位置
		scroll:false,//屏幕自动滚动到第一个验证不通过的位置
		focusFirstField:false,//验证未通过时，是否给第一个不通过的控件获取焦点
		promptPosition:"topRight" //验证提示信息的位置，可设置为："topRight", "bottomLeft", "centerRight", "bottomRight" 
	});
});

function initAddrList(){
	$(".Js-addrList").on("mouseover",function(){
		$(this).css("backgroundColor","#ccc");
		$(this).find("td.edit").css("display","");
	}).on("mouseout",function(){
		$(this).css("backgroundColor","#FFF");
		$(this).find("td.edit").css("display","none");
	});
	
	$("input[name='address']").on("click",function(){
		$(".Js-addrList label").css("font-weight","normal").css("font-size","14px");
		$(this).closest("tr").find("label").css("font-weight","bolder").css("font-size","16px");
	});
	
	$("#Js-addr-new").click(function(){
		openAddressFormDlg("新增收件地址", getAddressFormHtml("",$("#pay_login_user_id").val()));
	});
	$("a.Js-addr-modify").on("click",function(){
		openAddressFormDlg("修改收件地址", getAddressFormHtml($(this).attr("data-id"),$("#pay_login_user_id").val()));
	});
	
	$(".supdtl-cont span.more").on("click",function(){
		var h = $(".supdtl-cont span.morecon").css("height");
		if(h == "45px"){
			h = "auto";
			$(this).text("隐藏");
		}else{
			h = "45px";
			$(this).text("显示全部");
		}
		$(".supdtl-cont span.morecon").css("height",h);
	});
}