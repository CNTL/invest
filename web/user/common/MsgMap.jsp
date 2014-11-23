<%@ page pageEncoding = "UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>百度地图API地点搜索-获取经纬度</title>
  <meta content="IE=edge" http-equiv="X-UA-Compatible" />
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0">
  <meta name="apple-mobile-web-app-capable" content="yes">
  <meta name="apple-mobile-web-app-status-bar-style" content="black">
  <meta name="format-detection" content="telephone=no">
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">

  <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=6eea93095ae93db2c77be9ac910ff311"></script>
  <script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
  <style>
    #container{
      height:400px;
      border:1px solid gray;
    }
  </style>
</head>
<body>
  <form action="" method="get">
    <label >地点：</label>
    <input id="where" name="where" type="text" >
    <input type="button" value="搜索" onClick="sear(document.getElementById('where').value);" />
    <div id="container"></div>
    <input id="lonlat" name="lonlat" type="hidden" value=""/>
    <input id="lat" name="lat" type="hidden" value=""/>
    <input id="lng" name="lng" type="hidden" value=""/>
    <!-- <input type="button" id="saveBtn" name="saveBtn" value="确定" onclick="getPoint();"/> -->
  </form>
  <!-- <h3>输入地点然后点击“地图上找”搜索，点击地图地点获取相应经纬度</h3> -->
  

<!--用script标签，本来返回的就是js文件，不要用$.getJSON了--> 

  <script type="text/javascript">
    var map = new BMap.Map("container");//在指定的容器内创建地图实例
    map.setDefaultCursor("crosshair");//设置地图默认的鼠标指针样式
    map.enableScrollWheelZoom();//启用滚轮放大缩小，默认禁用。
    map.centerAndZoom(new BMap.Point(116.124878, 24.309178), 13);
    map.addControl(new BMap.NavigationControl());
    var tempPoint = null,tempMarker = null;
    map.addEventListener("click", function(e){//地图单击事件
      if(tempMarker != null){
      	map.removeOverlay(tempMarker);
      }
      document.getElementById("lonlat").value = e.point.lng + ", " + e.point.lat;
      tempPoint = new BMap.Point(e.point.lng, e.point.lat);
      tempMarker = new BMap.Marker(tempPoint);
      map.addOverlay(tempMarker);
      $("#lat").val(e.point.lat);
      $("#lng").val(e.point.lng);
      doReverse(tempPoint);
    });
    var SAMPLE_POST_REVERSE = 'http://api.map.baidu.com/geocoder/v2/?ak=您的密钥&callback=renderReverse';
    var safe = '';
    var pois;
    function showReverseURL() {
    	var latitude = document.getElementById('lat').value;
    	var longitude = document.getElementById('lng').value;
    	safe = SAMPLE_POST_REVERSE;
    	safe +="&location="+latitude+","+longitude;
    	safe+="&output=json";
    };

    function doReverse(tempPoint) {	
    	var script = document.createElement('script');
        script.type = 'text/javascript';
        showReverseURL();
        var newURL = safe.replace('您的密钥', '6eea93095ae93db2c77be9ac910ff311');
        script.src = newURL;
        document.body.appendChild(script);
    }

    function renderReverse(response) {
    	if (response.status) {
    		var text = "无正确的返回结果:\n";
    		alert(text);
    		return;
    	}
    	var location = response.result.location;
    	var addr = response.result.formatted_address;
    	//$("#where").val(addr, location);
    	getPoint(addr, $("#lonlat").val());
    }
    
    function iploac(result){//根据IP设置地图中心
        var cityName = result.name;
        map.setCenter(cityName);
    }
    var myCity = new BMap.LocalCity();
    myCity.get(iploac);
    function sear(result){//地图搜索
      var local = new BMap.LocalSearch(map, {
        renderOptions:{map: map}
      });
      local.search(result);
    }
    var _gaq = _gaq || [];
    _gaq.push(['_setAccount', 'UA-12599330-14']);
    _gaq.push(['_trackPageview']);

    (function() {
      var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
      ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
      var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
    })();

    function getPoint(where, lonlat){
  	  parent.setPoint(where, lonlat);
    }
  </script>
</body>
</html>