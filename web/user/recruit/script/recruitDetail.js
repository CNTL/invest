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
//收藏职位
var resume = {
		init : function(){
			resume.closeMsg();
			$("#btnSend").click(resume.openMsg);
		},
		openMsg : function(){
			var isPost = resume.isPost();
			if(!isPost){//如果未投递简历，执行投递简历操作
				resume.myResume();
				$('#w').window('open');
			}
		},
		closeMsg : function(){
			$("#resumeID").html("");
			$('#w').window('close');
		},
		isCollect : function(){//是否已收藏该职位
			var isCollect = false;
			$.ajax({
		        type:"GET", //请求方式  
		        url:"../user/recruit.do?a=isCollect", //请求路径  
		        cache: false,
		        dataType: 'TEXT',   //返回值类型  
		        success:function(data){
		    		if(data != null && data == true){
		    			$.messager.alert('消息','您已收藏该职位！');
		    			isCollect = true;
		    		} else {
		    			isCollect = false;
		    		}
		        } ,
				error:function (XMLHttpRequest, textStatus, errorThrown) {
					   alert("error="+errorThrown);
				}
		    });
			return isCollect;
		},
		collect : function(){//收藏该职位
			var isCollect = resume.isCollect();
			if(isCollect){//如果未收藏职位，执行收藏职位操作
				$.ajax({
			        type:"GET", //请求方式  
			        url:"../user/recruit.do?a=collect&recruitID=" + $("#recruitID").val(), //请求路径  
			        cache: false,
			        dataType: 'TEXT',   //返回值类型  
			        success:function(data){
			    		if(data != null && data == true){
			    			$.messager.alert('消息','收藏职位成功！');
			    		}
			        } ,
					error:function (XMLHttpRequest, textStatus, errorThrown) {
						   alert("error="+errorThrown);
					}
			    });
			}
		},
		isPost : function(){//是否已投递简历
			var isPost = false;
			$.ajax({
		        type:"GET", //请求方式  
		        url:"../user/recruit.do?a=isPost", //请求路径  
		        cache: false,
		        dataType: 'TEXT',   //返回值类型  
		        success:function(data){
		    		if(data != null && data == true){
		    			$.messager.alert('消息','该职位您已投递简历！');
		    			isPost = true;
		    		} else {
		    			isPost = false;
		    		}
		        } ,
				error:function (XMLHttpRequest, textStatus, errorThrown) {
					   alert("error="+errorThrown);
				}
		    });
			return isPost;
		},
		post : function(){//投递简历
			$.ajax({
		        type:"GET", //请求方式  
		        url:"../user/recruit.do?a=post&recruitID=" + $("#recruitID").val() + "&resumeID=" + $("#resumeID").val(), //请求路径  
		        cache: false,
		        dataType: 'TEXT',   //返回值类型  
		        success:function(data){
		    		if(data != null && data == true){
		    			$.messager.alert('消息','投递简历成功！');
		    		}
		        } ,
				error:function (XMLHttpRequest, textStatus, errorThrown) {
					   alert("error="+errorThrown);
				}
		    });
		}, 
		myResume : function(){
			$.ajax({
		        type:"GET", //请求方式  
		        url:"../user/resume.do?a=getMyResumes", //请求路径  
		        cache: false,
		        dataType: 'JSON',   //返回值类型  
		        success:function(result){
		    		if(!result||typeof Object.prototype.toString.call(result) == "[object Array]"||!result.length)return;
					var l = result.length;
					for (var j = 0; j < l; j++) {
						var resume = result[j];
						var option = '<option value="'+resume.id+'">'+resume.name+'</option>';
						$("#resumeID").append(option);
					}
		        } ,
				error:function (XMLHttpRequest, textStatus, errorThrown) {
					   alert("error="+errorThrown);
				}
		    });
		}
}