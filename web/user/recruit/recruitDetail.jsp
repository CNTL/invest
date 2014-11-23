<%@ page pageEncoding = "UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="ft"%>
<!DOCTYPE html>
<html>
<head>
<title>合众映画</title>
<meta charset="utf-8">
<meta name="keywords" content="合众映画" />
<meta name="description" content="合众映画" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="../css/userCenter.css">
<link rel="stylesheet" type="text/css" href="../../css/list.css">
<script type="text/javascript" src="../../js/jquery/jquery.min.js"></script>
</head>
<body>
<%@ include file="../common/userHead.inc"%>
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