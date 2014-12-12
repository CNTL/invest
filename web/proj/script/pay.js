$(function(){
	initAddrList();
	
	$("input[name='address']").each(function(i,n){
		if($(this).is(":checked")){
			$(this).trigger("click");
		}
	});
	
	//回报内容 回车转为html换行
	$(".supdtl-cont span.morecon").html($(".supdtl-cont span.morecon").text().replace(/[\r\n]/g,"<br />"));
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