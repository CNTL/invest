$(function(){
	initAddrList();
	
	$("input[name='address']").each(function(i,n){
		if($(this).is(":checked")){
			$(this).trigger("click");
		}
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
	$("#Js-addr-modify").click(function(){
		openAddressFormDlg("修改收件地址", getAddressFormHtml($(this).attr("data-id"),$("#pay_login_user_id").val()));
	});
}