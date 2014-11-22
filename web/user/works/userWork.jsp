<%@page pageEncoding="UTF-8"%>
<%
String basePath = request.getScheme() + 
	"://" + request.getServerName() + 
	":" + request.getServerPort() + 
	request.getContextPath() + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
    <title>上传个人作品</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
   <script type="text/javascript">
	    var rootURL = "<%=basePath %>";
    </script>
   <script type="text/javascript" charset="utf-8" src="script/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="../../ueditor/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="../../ueditor/lang/zh-cn/zh-cn.js"></script>
    <style type="text/css">
        .clear {
            clear: both;
        }
    </style>
</head>
<body>
<form id="form" name="form" method="post" action="user.do?a=works">
	<div class="form-group">
	    <label for="name"><i style="color:red;">*</i>作品名称</label>
	    <input class="form-control validate[required]" type="text" autocomplete="off" placeholder="请输入作品名称" id="name" name="name" />
	</div>
	<div>
	    <h1>作品内容</h1>
	    <script type="text/plain" id="MSG_Content" name="MSG_Content"></script>
	</div>
	<div class="form-actions">
	    <button id="btnSave" type="submit" class="btn blue pull-right">
	       	<i onclick="">保存</i>
	    </button>
	    <button id="btnCancel" type="button" onclick="window.close();" class="btn blue pull-right">
	       	<i>关闭</i>
	    </button>
	</div>
</form>

</body>
<script type="text/javascript">

    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    UE.getEditor('MSG_Content', { initialFrameHeight: 300 });
   

</script>
<script type="text/javascript" src="../../js/jquery/jquery.min.js"></script>
<script src="../../js/jquery/jquery-ui.min.js"></script>
<script src="script/userWork.js"></script>
</html>