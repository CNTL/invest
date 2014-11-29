<%@ include file="../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><c:out value="${title}"/></title>
	<meta name="keywords" content="<c:out value="${keywords}"/>" />
    <meta name="description" content="<c:out value="${description}"/>" />
    <link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" type="text/css" href="../static/css/reset.css" />
    <link rel="stylesheet" type="text/css" href="../static/css/index.css" />
    <script type="text/javascript" src="../static/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="../static/js/common.js"></script>
	<script type="text/javascript" src="../static/ckeditor/ckeditor.js"></script>
</head>
<body>
	<%@include file="../inc/header.inc"%>
	<div class="banner">
        <img src="../static/image/banner2.png" />
    </div>
	<div class="job_add">
        <a href="job_add.html" class="add">+ 发布职位</a>
        <form action="" method="POST">
            <h2>公司介绍</h2>
            <div class="input">
                <label>招聘主题：</label><input type="text" name="username" value="" />
            </div>
            <div class="input">
                <label>公司地点：</label>
                <select name="scale">
                    <option value="0">省份</option>
                    <option value="1">选项一</option>
                </select>
                <select name="scale">
                    <option value="0">城市</option>
                    <option value="1">选项一</option>
                </select>
                <select name="scale">
                    <option value="0">地区</option>
                    <option value="1">选项一</option>
                </select>
            </div>
            <div class="input">
                <label>公司性质：</label><input type="text" name="username" value="" />
            </div>
            <div class="input">
                <label>所属行业：</label><input type="text" name="username" value="" />
            </div>
            <div class="input">
                <label>公司规模：</label>
                <select name="scale">
                    <option value="0">请选择</option>
                    <option value="1">选项一</option>
                </select>
            </div>
            <div class="input">
                <label>招聘内容：</label>
                <div class="text">
                    <textarea name="content" id="content"></textarea>
                </div>
                <div class="clear"></div>
            </div>
            <div class="input">
                <label>公司全称：</label><input type="text" name="username" value="" />
            </div>
            <div class="input">
                <label>联系人：</label><input type="text" name="username" value="" />
            </div>
            <div class="input">
                <label>联系电话：</label><input type="text" name="username" value="" />
            </div>
            <div class="input">
                <label>联系邮箱：</label><input type="text" name="username" value="" />
            </div>
            <div class="btn">
                <input type="submit" name="submit" value="提交信息" />
            </div>
        </form>
    </div>
    
    <script type="text/javascript">
        $(function () {
            var editor = CKEDITOR.replace("content");
        });
    </script>
	<%@include file="../inc/footer.inc"%>
</body>
</html>