<%@ page pageEncoding = "UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="ft"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0,  user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<title>招聘</title>
<meta name="keywords" content="">
<link rel="stylesheet" type="text/css" href="../css/userSetting.css">
<link rel="stylesheet" type="text/css" href="../css/userCenter.css">
<link rel="stylesheet" type="text/css" href="../../css/list.css">
<script type="text/javascript" src="../../js/jquery/jquery.min.js"></script>
<script type="text/javascript" src="../../common/head.js"></script>
</head>
<body>
<%@ include file="../../common/head.inc"%>
<div class="content">
	<div>
        <label for="jobName">${recruit.jobName}</label>
    </div>
    <br>
    <div>
       	 发布时间：${recruit.createtime}
    </div>
    <br>
    <hr>
    <div>
        <label for="content">职位描述：</label>
        ${recruit.content}
    </div>
</div>
</body>
</html>