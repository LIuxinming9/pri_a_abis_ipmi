<%@ page import="java.sql.*" language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<html>
<head>
    <title><spring:message code="systemTitle"/></title>
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
	<section id='content'>
	    <div class='container-fluid'>
	        <div class='row-fluid' id='content-wrapper'>
	            <div class='span12'>
					<div class="panel panel-default">
                        <div class="panel-heading"> 
                        	 <nav class="breadcrumb">
								<i class="Hui-iconfont">&#xe67f;</i> 系统管理 <span class="c-gray en">&gt;</span> 用户管理 <span class="c-gray en">&gt;</span> 用户列表
							</nav>
                             <ul class='nav pull-right'>
                             	<li><a href="${pageContext.request.contextPath}/user/add" class="btn btn-success">创建用户</a></li>
                             </ul>
                        </div>
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            <th>序号</th>
                                            <th>用户名称</th>
                                            <th>登录账号</th>
                                            <th>创建日期</th>
                                            <th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach items="${list}" var="item" varStatus="status">
	                                    	<c:choose>
					                           <c:when test="${(status.index+1)/2==1}">
					                              <tr class="odd gradeA">
		                                              <td class="center">${status.index+1}</td>
		                                              <td class="center">${item.username}</td>
		                                              <td class="center">${item.account}</td>
		                                              <td class="center"><fmt:formatDate value="${item.createtime}" pattern="yyyy年MM月dd日" /></td>
		                                              <td class="center" style="padding-top:12px;">
		                                              	<a href="${pageContext.request.contextPath}/user/del/${item.id}" title="删除"
		                                              		onclick="return window.confirm('确认删除用户[${item.account}]吗?')"
		                                              		class="btn box-remove btn-mini btn-link"><i class='icon-remove'></i></a>
	                                              	  </td>
	                                              </tr>
					                           </c:when>
					                           <c:otherwise>
					                               <tr class="even gradeA">
		                                              <td class="center">${status.index+1}</td>
		                                              <td class="center">${item.username}</td>
		                                              <td class="center">${item.account}</td>
		                                              <td class="center"><fmt:formatDate value="${item.createtime}" pattern="yyyy年MM月dd日" /></td>
		                                              <td class="center" style="padding-top:12px;">
		                                              	<a href="${pageContext.request.contextPath}/user/del/${item.id}" title="删除" 
		                                              		onclick="return window.confirm('确认删除用户[${item.account}]吗?')"
		                                              		class="btn box-remove btn-mini btn-link"><i class='icon-remove'></i></a>
	                                              	  </td>
	                                              </tr>
					                           </c:otherwise>
					                        </c:choose>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            
                        </div>
                    </div>
                    <!--End Advanced Tables -->
	            </div>
	        </div>
	    </div>
	</section>
</div>
</body>
</html>
