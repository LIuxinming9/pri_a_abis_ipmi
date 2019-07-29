<%@ page import="java.sql.*" language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
	</script>
	<section id='content'>
	    <div class='container-fluid'>
	        <div class='row-fluid' id='content-wrapper'>
	            <div class='span12'>
	            	<nav class="breadcrumb">
						<i class="Hui-iconfont">&#xe67f;</i> 系统管理 <span class="c-gray en">&gt;</span> SNMP-SET
					</nav>
	                        <shiro:hasRole name="admin">
	                        <div class='box-content'>
	                            <form class='form form-horizontal validate-form' style='margin-bottom: 0;' action="${pageContext.request.contextPath}/setValueByOid/setValueByOid" method="post" modelAttribute="snmp" >
	                            	<div class='control-group'>
	                                    <label class='control-label' for='oid'>服务器IP/名字</label>
	                                    <div class='controls'>
	                                        <input data-rule-minlength='2' data-rule-required='true' 
	                                        	id='hostname' name='hostname' placeholder='请输入服务器IP或名字' type='text' /><span id="showResult"></span>
	                                    </div>
	                                </div>
	                                <div class='control-group'>
	                                    <label class='control-label' for='oid'>OID</label>
	                                    <div class='controls'>
	                                        <input data-rule-minlength='2' data-rule-required='true' 
	                                        	id='oid' name='oid' placeholder='...' type='text' /><span id="showResult"></span>
	                                    </div>
	                                </div>
	                                <div class='control-group'>
	                                    <label class='control-label' for='oid'>数据类型</label>
	                                    <div class='controls'>
	                                        <input data-rule-minlength='2' data-rule-required='true' 
	                                        	id='type' name='type' placeholder='数字/非数字' type='text' /><span id="showResult"></span>
	                                    </div>
	                                </div>
	                                <div class='control-group'>
	                                    <label class='control-label' for='chinese_des'>新数据</label>
	                                    <div class='controls'>
	                                        <input  data-rule-required='true' 
	                                        	id='value' name='value' placeholder='...' type='text' />
	                                    </div>
	                                </div>
	                                <div class='form-actions' style='margin-bottom:0'>
	                                    <button id="saveUser" class='btn btn-primary' type='submit'>
	                                        	 发送
	                                    </button>
	                                </div>
	                            </form>
	                        </div>
	                        </shiro:hasRole>
	                    </div>
                	</div>
	            </div>
	</section>
</div>

</body>
</html>
