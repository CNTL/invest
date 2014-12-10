$(function(){
	$(".project_view .main .content .desc img").css("max-width","640px");
	$(".project_view .main .content .desc p img").css("max-width","608px");
	$(".project_view .main .content .desc p").css("text-indent","2em").css("font-size","14px");
	
	$(".moneyFormat").each(function(i,n){
		var value = $(this).text();
		if(value){
			if(value.endWith(".00")){
				value = value.substring(0,value.length-3);
				$(this).text(formatMoney(value,0,"",",","."));
			}else{
				$(this).text(formatMoney(value,2,"",",","."));
			}
		}
	});
	
	$(".supportBtn").on("click",function(){
		var modeid = $(this).attr("modeid");
		var userId = $(this).attr("userid");
		if(userId <= 0){
			window.location.href = "../login.jsp";
		}else{
			window.location.href = "../project/Pay.do?id="+modeid;
		}
	});
});