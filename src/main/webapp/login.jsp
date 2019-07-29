<%@ page import="java.sql.*" language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link href="<%=basePath%>/css/login.css" rel="stylesheet" type="text/css" charset="utf-8"/>
<link href="<%=basePath%>/css/ui.css" rel="stylesheet" type="text/css" charset="utf-8"/>
<link href="<%=basePath%>/css/iconfont/iconfont.css" rel="stylesheet" type="text/css" charset="utf-8"/>
<title>IPMI带外网管系统</title>
<style type="text/css">

</style>
</head>
<body>
	<div class="header"></div>
	<div class="loginWraper">
		<div id="loginform" class="loginBox">
			<form action="${pageContext.request.contextPath}/user/login" method="post">
				
				<div class="row cl">
				  <div class="formControls col-xs-8 col-xs-offset-3">
				  </div>
				</div>
		        <div class="row cl">
			        <label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60d;</i></label>
			        <div class="formControls col-xs-8">
			          <input id="username" name="username" type="text" placeholder="账号" class="input-text size-L">
			        </div>
		        </div>
		        <div class="row cl">
				  <div class="formControls col-xs-8 col-xs-offset-3">
				  </div>
				</div>
				<div class="row cl">
				  <label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60e;</i></label>
				  <div class="formControls col-xs-8">
				    <input id="password" name="password" type="password" placeholder="密码" class="input-text size-L">
				  </div>
				</div>
				<div class="row cl">
				  <div class="formControls col-xs-8 col-xs-offset-3">
				  </div>
				</div>
				<div class="row cl">
				  <div class="formControls col-xs-8 col-xs-offset-3" style="padding-left:116px;">
				    <input name="loginBtn" type="submit" class="btn btn-success radius size-L login" value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;">
				  </div>
				</div>
				<div class="row cl">
				  <div class="formControls col-xs-8 col-xs-offset-3" style="color:#ff0000;font-size:14px;">
				    ${error}
				  </div>
				</div>
			</form>
  		</div>
	</div>
	<div class="footer" style="color:#000000">© 广州市广源电子科技有限公司</div>
</body>
</html>

