<%@ include file="../../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title><c:out value="${title}"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="<c:out value="${keywords}"/>" />
<meta name="description" content="<c:out value="${description}"/>" />
<%@include file="../inc/csslink.inc"%>
</head>
<body>
	<%@include file="../../inc/header.inc"%>
    <div class="shadow"></div>
    <div class="job_view">
        <div class="main">
            <div class="title">
                <span>${recruit.jobName}</span>
                <a href="#" class="favorite"></a>
            </div>
            <div class="desc">
                <ul>
                    <li>${recruit.salary}</li>
                    <li>${user.city}</li>
                    <li>${recruit.days}</li>
                </ul>
            </div>
            <div class="date">
                发布时间：${recruit.time}
                <a href="#" class="report">举报该职位</a>
            </div>
            <div class="block">
                <h2>职位描述</h2>
                <div class="content">
                    ${recruit.content}
                </div>
            </div>
            <div class="tip">
                你还没有简历呢，你可以完善在线简历，也可上传附件简历直接投递
            </div>
            <a href="#" class="submit">投递简历</a>
        </div>
        <div class="sider">
            <div class="desc">
                <div class="pic">
                <!-- ../../static/image/temp/pic4.png -->
                    <img src="${user.head}" />
                </div>
                <div class="title">
			    	${user.orgFullname}
                </div>
                <div class="clear"></div>
                <div class="intro">
                    <span>规模 ：  ${user.orgScale}</span><br/>
                    <span>主页 ： ${user.orgHomePage}</span><br /><br />
                    <span>工作地点 ：</span><br />
                    	${user.location}
                </div>
                <div class="map" style="width:250px;height:200px;" id="allmap">
                </div>
            </div>
            <div class="relate">
                <h2>相似职位</h2>
                <div class="item">
                    <div class="pic">
                        <img src="static/image/temp/pic5.png" />
                    </div>
                    <div class="title">
                        <a href="#">化妆师</a>
                    </div>
                    <div class="company">
                        小马奔腾电影
                    </div>
                    <div class="info">
                        <ul>
                            <li>100K</li>
                            <li>北京</li>
                            <li>3个月</li>
                        </ul>
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="item">
                    <div class="pic">
                        <img src="static/image/temp/pic5.png" />
                    </div>
                    <div class="title">
                        <a href="#">化妆师</a>
                    </div>
                    <div class="company">
                        小马奔腾电影
                    </div>
                    <div class="info">
                        <ul>
                            <li>100K</li>
                            <li>北京</li>
                            <li>3个月</li>
                        </ul>
                    </div>
                    <div class="clear"></div>
                </div>
            </div>
        </div>
        <div class="clear"></div>
    </div>
	<!-- script -->
	<%@include file="../inc/script.inc"%>
	<!-- script -->
	
	<!-- footer -->
	<%@include file="../../inc/footer.inc"%>
	<!-- footer -->
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=6eea93095ae93db2c77be9ac910ff311"></script>
</body>
<script>
var map = new BMap.Map("allmap");
$(document).ready(function(){
	var location = '${user.location}';
	if(location != null && location != ""){
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
//根据ip设置地图中心
function myFun(result){
   var cityName = result.name;
   map.centerAndZoom(cityName,12);  
}
</script>
</html>