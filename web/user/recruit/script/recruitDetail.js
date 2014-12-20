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
var pagei = null;
var resume = {
		init : function(){
			//resume.closeMsg();
			$("#btnSend").click(resume.addResume);
		},
		addResume : function(){
			resume.openFormDlg("投递简历", resume.getFormHtml(""));
			resume.myResume();
		},
		openFormDlg : function(title,html){
			pagei = $.layer({
				type: 1,   //0-4的选择,
				title: title,
				maxmin: false,
				border: [10, 0.2, '#000'],
				closeBtn: [1, true],
				shadeClose: false,
				fix: true,
				zIndex : 1000,
				area: ['800px', '500px'],
				page: {
					html: html //此处放了防止html被解析，用了\转义，实际使用时可去掉
				}
			});
			$("#form").validationEngine("attach",{
				autoPositionUpdate:false,//是否自动调整提示层的位置
				scroll:false,//屏幕自动滚动到第一个验证不通过的位置
				focusFirstField:false,//验证未通过时，是否给第一个不通过的控件获取焦点
				promptPosition:"topRight" //验证提示信息的位置，可设置为："topRight", "bottomLeft", "centerRight", "bottomRight" 
			});
		},
		getFormHtml : function(){
			var id = $("#id").val();
			var html = '<div class="job_add">' +
							'<form class="setting-form" id="form" name="form" action="">' +
								'<input type="hidden" id="recruitID" name="recruitID" value="' + id + '"/>' +
								'<div class="input">' +
							        '<label for="resumeID">选择简历：</label>' +
							        '<select id="resumeID" name="resumeID" class="custform-select validate[maxSize[255],required]" style="width:400px">' +
							        '</select>' +
							    '</div>' +
							    '<div class="btn" style="margin-top:120px;">' +
							    	'<input style="width:100px; margin-left: 100px;" id="btnOK" name="btnOK" value="提交" type="button" onclick="resume.isPost();"/>' +
							    	'<input style="width:100px; margin-left: 150px;" id="btnCancel" name="btnCancel" value="取消" type="button" onclick="resume.btnCancel();"/>' +
							    '</div>' +
							'</form>' +
						'</div>';
			return html;
		},
		btnCancel: function(){
			if(pagei != null)
				layer.close(pagei);
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