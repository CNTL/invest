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