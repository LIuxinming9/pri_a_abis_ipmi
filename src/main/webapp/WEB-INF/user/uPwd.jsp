<%@ page import="java.sql.*" language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<html>
<head>
    <title>服务器通用带外网管软件系统</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport' />   
    <jsp:include page="/template/link.jsp" />
    
</head>
<body class='contrast-blue '>
<header>
    <jsp:include page="/template/header.jsp" />
</header>
<div id='wrapper'>
	<jsp:include page="/template/left.jsp" />
	<script type="text/javascript">
		function clearShowResult()
		{
			$("#showResult").html("");
		}
		function checkIsOpwd() 
		{
		    var oPwd = $.trim($("#oPwd").val());
		    if(oPwd.length > 0){
		    	$.ajax({
			        type:"POST",   
			        url:"${pageContext.request.contextPath}/user/isOpwd", 
			        data:"oPwd="+oPwd, 
			        success : function(msg) {
			        	if(msg=="success"){
			    		 	$("#showResult").html("<img src='../images/validform/iconpic-right.png'>");
			    		 	$('#updatePwd').attr("disabled",false);
						} else {
							$("#showResult").html("<img src='../images/validform/iconpic-error.png'><font color='red'>原密码有误！</font>");
							$('#updatePwd').attr("disabled",true);
						} 
			       } 
			    });
		    }
		    
		}
	</script>
	<section id='content'>
	    <div class='container-fluid'>
	        <div class='row-fluid' id='content-wrapper'>
	            <div class='span12'>
	            	<div class='row-fluid' style="margin-top:20px;">
	                    <div class='span12 box'>
	                        <div class='box-header blue-background'>
	                            <div class='title'>修改密码</div>
	                            <div class='actions'>
	                                
	                            </div>
	                        </div>
	                        <shiro:hasRole name="admin">
	                        <div class='box-content'>
	                            <form class='form form-horizontal validate-form' style='margin-bottom: 0;' 
	                            	action="${pageContext.request.contextPath}/user/savePwd" method="post" >	                                
	                                <div class='control-group'>
	                                    <label class='control-label' for='oPwd'>原密码</label>
	                                    <div class='controls'>
	                                        <input data-rule-minlength='6' data-rule-password='true' data-rule-required='true' 
	                                        	 onblur="checkIsOpwd();" onfocus="clearShowResult();"
	                                        	id='oPwd' name='oPwd' placeholder='请输入原密码...' type='password' /> <span id="showResult"></span>
	                                    </div>
	                                </div>
	                                <div class='control-group'>
	                                    <label class='control-label' for='password'>密码</label>
	                                    <div class='controls'>
	                                        <input data-rule-minlength='6' data-rule-password='true' data-rule-required='true' 
	                                        	id='password' name='password' placeholder='请输入密码...' type='password' />
	                                    </div>
	                                </div>
	                                <div class='control-group'>
	                                    <label class='control-label' for='password_confirmation'>确认密码</label>
	                                    <div class='controls'>
	                                        <input data-rule-equalto='#password' data-rule-required='true' 
	                                        	id='password_confirmation' name='password_confirmation' placeholder='请再次输入密码...' type='password' />
	                                    </div>
	                                </div>
	                                <div class='form-actions' style='margin-bottom:0'>
	                                    <button id="updatePwd" class='btn btn-primary' type='submit' onclick="return window.confirm('修改密码成功后，需要重新登录系统！')">
	                                        <i class='icon-save'></i> 保存
	                                    </button>
	                                    <span style="padding-left:20px;"></span>
	                                    <a href="javascript:history.back(-1)" class="btn btn-warning"><i class='icon-reply'></i> 取消</a>
	                                </div>
	                            </form>
	                        </div>
	                        </shiro:hasRole>
	                    </div>
                	</div>
	            </div>
	        </div>
	    </div>
	</section>
</div>

</body>
</html>
