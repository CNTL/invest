<%@ include file="../../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title></title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

	<link rel="stylesheet" type="text/css" href="../../js/plugin/jquery-ui/jquery-ui.min.css"/>
	<link rel="stylesheet" type="text/css" href="../../js/bootstrap/css/bootstrap.min.css"/>
    <script type="text/javascript" src="../../js/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="../../js/jquery/jquery-migrate.min.js"></script>
	<script type="text/javascript" src="../../js/plugin/jquery-ui/jquery-ui.min.js"></script>
	<script type="text/javascript" src="../../js/plugin/query/jquery.query.js"></script>
	<script type="text/javascript" src="../../js/plugin/jquery.cropzoom/jquery.cropzoom.js"></script>
	<style type="text/css">
	.modal-body{
		padding:0;
	}
	#main{padding:0;margin:0;border:0;}
.crop{width:680px; margin:20px auto; border:1px solid #d3d3d3; padding:4px; background:#fff}
#cropzoom_container{float:left; width:500px}
#preview{float:left; width:120px; height:120px; border:0px solid #fff; margin-left:10px; padding:4px; background:#fff;}
.page_btn{float:right; width:150px;  margin-top:20px; line-height:30px; text-align:center}
.clear{clear:both}
.btn{cursor:pointer}
</style>
<script type="text/javascript">
   $(function(){
	   var imgurl = $.query.get("path");
	    
	   getImageSize('../../'+imgurl);

    });
   function getImageSize(imgurl){
	    
	   $("<img/>").attr("src", imgurl)
	     .load(function() {
	         //获得图片的真实尺寸
	         var options = {
	     			width : 500,
	     			height : 360,
	     			bgColor: '#fff',//DIV层背景颜色
	     			enableRotation : false,
	     			enableZoom : true,
	     			selector : {
	     				w : 150,
	     				h : 200,
	     				showPositionsOnDrag : false,
	     				showDimetionsOnDrag : false,
	     				centered : true,
	     				bgInfoLayer : '#fff',
	     				borderColor : 'blue',
	     				animated : false,
	     				maxWidth : 120,
	     				maxHeight : 120,
	     				borderColorHover : '#333'
	     			},
	     			image : {
	     				source : imgurl,
	     				width : this.width,
	     				height : this.height,
	     				minZoom : 30,
	     				maxZoom : 150
	     			}
	     	};
	         
	        var cropzoom = $('#cropzoom_container').cropzoom( options );
	 		$("#crop").click(function() {
	 			cropzoom.send('../../user/photo.do?a=saveCorpImage', 'POST', {}, function(imgRet) {
	 				$("#generated").attr("src", "../../"+imgRet);
	 				window.parent.updateNewhead(imgRet);
	 			});
	 		});
	 		 
	     }); 
   }

</script>
</head>

<body>
	<div id="main">
		 
		<div class="crop">
			<div id="cropzoom_container"></div>
			<div id="preview">
				<img id="generated" style="width:120px;height:120px;border:2px solid #fff;" class="img-circle" src="../../js/plugin/jquery.cropzoom/head.gif" />
			</div>
			<div class="page_btn">
				<input type="button" class="btn btn-primary" id="crop" value="剪切照片" />
				 
			</div>
			<div class="clear"></div>
		</div>
		<h2 class="buttom_title"></h2>
	</div>	 
	 
</body>
</html>
