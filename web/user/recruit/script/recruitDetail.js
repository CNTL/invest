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
				resume.openFormDlg("投递简历", resume.getFormHtml(""));
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
				area: ['800px', '300px'],
				page: {
					html: html //此处放了防止html被解析，用了\转义，实际使用时可去掉
				}
			});
			resume.myResume();
			$("#form").validationEngine("attach",{
				autoPositionUpdate:false,//是否自动调整提示层的位置
				scroll:false,//屏幕自动滚动到第一个验证不通过的位置
				focusFirstField:false,//验证未通过时，是否给第一个不通过的控件获取焦点
				promptPosition:"topRight" //验证提示信息的位置，可设置为："topRight", "bottomLeft", "centerRight", "bottomRight" 
			});
		},
		getFormHtml : function(){
			var id = $("#recruitID").val();
			var html = '<div class="job_add">' +
							'<form class="setting-form" id="form" name="form" action="">' +
								'<input type="hidden" id="recruitID" name="recruitID" value="' + id + '"/>' +
								'<div class="input">' +
							        '<label for="resumeID">选择简历：</label>' +
							        '<select id="resumeID" name="resumeID" class="custform-select validate[maxSize[255],required]" style="width:400px">' +
							        '</select>' +
							    '</div>' +
							    '<div class="btn" style="margin-top:120px;">' +
							    	'<input style="width:100px; margin-left: 100px;" id="btnOK" name="btnOK" value="提交" type="button" onclick="resume.btnOK();"/>' +
							    	'<input style="width:100px; margin-left: 150px;" id="btnCancel" name="btnCancel" value="取消" type="button" onclick="resume.btnCancel();"/>' +
							    '</div>' +
							'</form>' +
						'</div>';
			return html;
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
		        url:"../user/recruitResume.do?a=post&recruitID=" + $("#recruitID").val() + "&resumeID=" + $("#resumeID").val(), //请求路径  
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
		}, 
		myResume : function(){
			$.ajax({
		        type:"GET", //请求方式  
		        url:"../user/resume.do?a=sendResumes", //请求路径  
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