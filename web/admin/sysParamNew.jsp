<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<title>系统参数 </title>
	<meta name="description" content="">
	<meta name="author" content="">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<link rel="stylesheet" type="text/css" media="screen" href="../js/bootstrap/css/bootstrap.min.css">
 
</head>
<body class="">
<form class="form-horizontal" role="form">
  <div class="form-group">
    <label for="paramName" class="col-sm-4 control-label">参数名称：</label>
    <div class="col-sm-8">
      <input type="text" class="form-control" id="paramName" placeholder="请输入参数名称英文和数字组合。">
    </div>
  </div>
  <div class="form-group">
    <label for="paramVale" class="col-sm-4 control-label">参数值：</label>
    <div class="col-sm-8">
      <input type="text" class="form-control" id="paramVale" placeholder="请输入参数">
    </div>
  </div>
   <div class="form-group">
    <label for="paramVale" class="col-sm-4 control-label">参数描述：</label>
    <div class="col-sm-8">
      <textarea id="paramDetail" class="form-control" rows="3"  placeholder="请输入参数描述"></textarea>
    </div>
   
  </div>
  <div class="form-group">
    <div class="col-sm-offset-2 col-sm-10">
      <button type="button" id="btnOK" class="btn btn-default">保存</button>  <button id="btnCancel" type="button" class="btn btn-default">关闭</button>
    </div>
  </div>
</form>

<script src="../js/jquery/jquery.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		
		
	})
</script>
</body>

</html>