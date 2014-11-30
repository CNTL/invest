<%@page pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<style type="text/css">
body, html{width: 100%;height: 100%;overflow: hidden;margin:0;}
#allmap {width: 100%;height: 100%;overflow: hidden;}
</style>
<link rel="stylesheet" type="text/css" href="../../js/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../../js/jquery-easyui/themes/icon.css">
<script type="text/javascript" src="../../js/jquery/jquery.min.js"></script>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=6eea93095ae93db2c77be9ac910ff311"></script>
<title>地址</title>
<style type="text/css">
.input input {
    width: 378px;
    height: 38px;
    line-height: 38px;
    border: 1px #E1E1E1 solid;
    padding: 0 10px;
}
.btn input {
    width: 200px;
    height: 40px;
    text-align: center;
    line-height: 40px;
    color: #fff;
    border: 0;
    font-size: 20px;
    background-color: #FFB124;
    cursor: pointer;
}
</style>
</head>
<body>
<div class="input id="map">
	<input id="where" style="width:500px" type="text" class="form-control" placeholder="请输入地点">
	<input type="button" style="width:100px" id="mapSearch" onclick="search()" value="从地图搜索">
</div>
<div id="allmap" class="flex-element"></div>
<script type="text/javascript">
var map = new BMap.Map("allmap");
$(document).ready(function(){
	var location = window.opener.document.getElementById("location").value;
	if(location != null && location != ""){
		$("#where").val(location);
		var myGeo = new BMap.Geocoder();
		myGeo.getPoint(location, function(point){
			if (point) {
				setMyPoint(point.lng,point.lat);
			}
		});
	} else {
		var myCity = new BMap.LocalCity();
		myCity.get(myFun);
	}
});
function setMyPoint(lng,lat){
	var point = new BMap.Point(lng,lat);
	map.centerAndZoom(point,15);
	map.enableScrollWheelZoom(); 
	
	var marker = new BMap.Marker(point);
	map.addOverlay(marker);
}
//设置标识
var gc = new BMap.Geocoder();  
var tempMarker = null;
var pt = null;
map.addEventListener("click", function(e){
    if(tempMarker != null){
		map.removeOverlay(tempMarker);
	}
	pt = e.point;
	//添加标记点
	tempMarker = new BMap.Marker(pt);
	map.addOverlay(tempMarker);
	tempMarker.enableDragging(); 
	gc.getLocation(e.point, function(rs){
		var addComp = rs.addressComponents;
	    var addr = addComp.province + "" + addComp.city + "" + addComp.district + "" + addComp.street + "" + addComp.streetNumber;
	    var lnglat = e.point.lng+","+e.point.lat;
	    window.opener.document.getElementById("location").value=addr;
	    window.opener.document.getElementById("coordinate").value=lnglat;
	    $.messager.confirm('消息', '设置公司地址成功，关闭地图页！', function(r){
			if (r){
				window.close(); 
			}
		});
	    //parent.setPoint(addr,lnglat);
	});
});
//关键字搜索 
function search(){
	var local = new BMap.LocalSearch(map, {
	  renderOptions:{map: map,autoViewport: true,selectFirstResult: false},pageCapacity: 8
	});
	local.search($("#where").val());
}
//根据ip设置地图中心
function myFun(result){
   var cityName = result.name;
   map.centerAndZoom(cityName,12);  
}

</script>
</body>
</html>