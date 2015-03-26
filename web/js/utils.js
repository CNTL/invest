/** 对特殊字符和中文编码 */
var encodeSpecialCode = function(param1){
	if (!param1) return "";

	var res = "";
	for(var i = 0;i < param1.length;i ++){
		switch (param1.charCodeAt(i)){
			case 0x20://space
			case 0x3f://?
			case 0x23://#
			case 0x26://&
			case 0x22://"
			case 0x27://'
			case 0x2a://*
			case 0x3d://=
			case 0x5c:// \
			case 0x2f:// /
			case 0x2e:// .
			case 0x25:// .
				res += escape(param1.charAt(i));
				break;
			case 0x2b:
				res += "%2b";
				break;
			default:
				res += encodeURI(param1.charAt(i));
		}
	}
	return res;
},
/**在Cookie中设置某值，保持一个月*/
setCookie = function(sName, sValue){
	var date = new Date();
	date.setMonth(1 + parseInt(date.getMonth()));

	if (sValue == "") sValue = "0";
	document.cookie = sName + "=" + escape(sValue) + ";expires=" + date.toGMTString() + ";path=/";
},

/**从Cookie中提取某值*/
getCookie = function(strName){
	var theValue = "";
	var aCookie = document.cookie.split("; ");

	for (var i=0; i < aCookie.length; i++){
		var aCrumb = aCookie[i].split("=");
		if (strName == aCrumb[0]) theValue = unescape(aCrumb[1]);
	}
	if (theValue == "0") theValue = "";
	return theValue;
};

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

function isMobile(){
	 var flag = false;  
     var agent = navigator.userAgent.toLowerCase();  
     var keywords = [ "android", "iphone", "ipod", "ipad", "windows phone", "mqqbrowser" ];  
     
     //排除 Windows 桌面系统  
     if (!(agent.indexOf("windows nt") > -1) || (agent.indexOf("windows nt") > -1 && agent.indexOf("compatible; msie 9.0;") > -1)) {  
         //排除苹果桌面系统  
         if (!(agent.indexOf("windows nt") > -1) && !agent.indexOf("macintosh") > -1 ) {  
             for (var item in keywords) {  
                 if (agent.indexOf(item) > -1 ) {  
                     flag = true;  
                     break;  
                 }  
             }  
         }  
     }  
     return flag;
	
}


(function($){
	$.fn.wordLimit = function(num){	
		this.each(function(){	
			if(!num){
				var copyThis = $(this.cloneNode(true)).hide().css({
					'position': 'absolute',
					'width': 'auto',
					'overflow': 'visible'
				});	
				$(this).after(copyThis);
				if(copyThis.width()>$(this).width()){
					$(this).text($(this).text().substring(0,$(this).text().length-4));
					$(this).html($(this).html()+'...');
					copyThis.remove();
					$(this).wordLimit();
				}else{
					copyThis.remove(); //清除复制
					return;
				}	
			}else{
				var maxwidth=num;
				if($(this).text().length>maxwidth){
					$(this).text($(this).text().substring(0,maxwidth));
					$(this).html($(this).html()+'...');
				}
			}					 
		});
	}		  
})(jQuery);


