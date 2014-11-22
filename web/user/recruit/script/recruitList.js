$(document).ready(function(){
	self.init(getUrl());
	var length =  document.getElementById("length").value;
	if(length <= 0) getInitMsg();
});

function getInitMsg(){
	var clientHeight = $(document).height();
	var length = Math.round((parseInt(clientHeight) - 38 - 28)/76);
	document.getElementById("length").value = length;
	self.refresh4Page(0, getUrl());
}
function getUrl(){
	var length = document.getElementById("length").value;
	var theURL = "../../user/recruit.do?a=list&length=" + length;
	return theURL;
}
function dourl(url){
	location.href = url;
}