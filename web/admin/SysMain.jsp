<%@ taglib uri="http://jakarta.apache.org/taglibs/i18n-1.0" prefix="i18n" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<i18n:bundle baseName="i18n.invest" changeResponseLocale="false"/>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<title>管理系统--电影众筹网</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<%@include file="inc/csslink.inc"%>
</head>
<body class="">
	<!-- 头部 -->
	<%@include file="inc/header.inc"%>
	<!-- 头部 -->
		 
	<!-- 导航 -->
    <%@include file="inc/sysnav.inc"%>
    <!-- 导航 -->
         
    <!-- MAIN PANEL -->
	<div id="main" role="main">
		<%@include file="inc/ribbon.inc"%>
		<!-- MAIN CONTENT -->
		<div id="content">
			系统管理
        </div>
		<!-- END MAIN CONTENT --> 
	</div>
	<!-- END MAIN PANEL -->

	 <!-- script -->
    <%@include file="inc/script.inc"%>
    <!-- script -->

    <!-- footer -->
    <%@include file="inc/footer.inc"%>
    <!-- footer -->
 	<script src="script/main.js"></script>
</body>

</html>