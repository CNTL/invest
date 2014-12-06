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
});

 function formatMoney(number, places, symbol, thousand, decimal) {
	number = number || 0;
	places = !isNaN(places = Math.abs(places)) ? places : 2;
	symbol = symbol !== undefined ? symbol : "$";
	thousand = thousand || ",";
	decimal = decimal || ".";
	var negative = number < 0 ? "-" : "",
		i = parseInt(number = Math.abs(+number || 0).toFixed(places), 10) + "",
		j = (j = i.length) > 3 ? j % 3 : 0;
	return symbol + negative + (j ? i.substr(0, j) + thousand : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + thousand) + (places ? decimal + Math.abs(number - i).toFixed(places).slice(2) : "");
}
String.prototype.endWith=function(str){
	if(str==null||str==""||this.length==0||str.length>this.length)
	  return false;
	if(this.substring(this.length-str.length)==str)
	  return true;
	else
	  return false;
	return true;
}