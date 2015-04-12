var map = new BMap.Map("allmap");
$(document).ready(function(){
	var location = $("#location").val();
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
	
	resume.init();
	
	$("#report").click(function(){
		alert("举报");
	});
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
 
//收藏职位
var pagei = null;
var resume = {
		init : function(){
			//resume.closeMsg();
			$("#btnSend").click(resume.addResume);
		},
		isLogin : function(){
			var isLogin = false; 
			var userID = $("#userID").val();
			if(userID == null || userID.length == 0 || userID == 0){
				setCookie("loginCurrentUrl", window.location.href);
				window.location.href = "../user/loginMain.do"
			} else{
				isLogin = true;
			}
			return isLogin;
		},
		addResume : function(){
			var isLogin = resume.isLogin();
			if(isLogin)
				resume.post();
		},
		 
		 
		btnOK : function(){
			if(!resume._validForm()) return;
			resume.post();
			if(pagei != null)
				layer.close(pagei);
		},
		btnCancel: function(){
			if(pagei != null)
				layer.close(pagei);
		},
		_validForm : function() {
			if (!$("#form").validationEngine("validate")){
				//验证提示
				$("#form").validationEngine({scroll:false});
				return false;
			}
			return true;
		},
		collect : function(){//收藏该职位
			var isLogin = resume.isLogin();
			if(isLogin){
				$.ajax({
			        type:"GET", //请求方式  
			        url:"../user/recruitResume.do?a=collect&recruitID=" + $("#recruitID").val(), //请求路径  
			        cache: false,
			        dataType: 'TEXT',   //返回值类型  
			        success:function(data){
			    		if(data != null && data == "ok"){
			    			$.messager.alert('消息','收藏职位成功！');
			    		}else{
			    			$.messager.alert('消息', data);
			    		}
			        } ,
					error:function (XMLHttpRequest, textStatus, errorThrown) {
						   alert("error="+errorThrown);
					}
			    });
			}
		},
		post : function(){//投递简历
			$.ajax({
		        type:"GET", //请求方式  
		        url:"../user/recruitResume.do?a=post&recruitID=" + $("#recruitID").val()  , //请求路径  
		        cache: false,
		        dataType: 'TEXT',   //返回值类型  
		        success:function(data){
		        	if(data != null && data == "ok"){
		    			$.messager.alert('消息','投递简历成功！');
		    		}else{
		    			$.messager.alert('消息', data);
		    		}
		        } ,
				error:function (XMLHttpRequest, textStatus, errorThrown) {
					   alert("error="+errorThrown);
				}
		    });
		} 
}