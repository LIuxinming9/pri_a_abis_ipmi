<%@ page import="java.sql.*" language="java"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<html>
<head>
	<title>服务器通用带外网管软件系统</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport' />
	<jsp:include page="/template/link.jsp" />
	<link href='<%=basePath%>/css/WdatePicker.css' media='all' rel='stylesheet' type='text/css' />
	<script src='<%=basePath%>/js/WdatePicker.js' type='text/javascript'></script>
</head>

<body class='contrast-blue '>
<header>
    <jsp:include page="/template/header.jsp" />
</header>
	<div id='wrapper'>
	<jsp:include page="/template/left.jsp" />
	<section id='content'>
	<div class='row-fluid'>
		<div class='span12 box'>
			<nav class="breadcrumb">
				<i class="Hui-iconfont">&#xe67f;</i>  信息统计 <span class="c-gray en">&gt;</span> 传感器记录 <span class="c-gray en">&gt;</span> 手动填写信息 
			</nav>
			<div class='box-content'>
			
				<form class='form form-horizontal validate-form'
					style='margin-bottom: 0;'
					action="${pageContext.request.contextPath}/snmp/addName"
					method="post">
					<div id="resultDiv" class="page-container">
						<table
							class="table table-border table-bordered table-bg mt-20">
							<thead>
								<tr class="text-c">
									<shiro:hasPermission name="/delete">
									<th width="50px">序号</th>
									<th width="250px">服务器ip地址</th>
									<th width="250px">新增信息</th>
									<th width="250px">读值</th>
									<th width="250px">删除数据</th>
									</shiro:hasPermission>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${snmpList}" var="item" varStatus="status">
									<tr class="text-c">
										<shiro:hasPermission name="/delete">
										<td class="center"><input type="text"
											class="input-text"
											style="width: 100%; margin: -10px; padding: -10px; height: 100%; border-width: 0px; text-align: center;"
											id="id" name="id"
											value="${item.id}"></td>
										<td class="center"><input type="text"
											class="input-text"
											style="width: 100%; margin: -10px; padding: -10px; height: 100%; border-width: 0px; text-align: center;"
											id="hostname" name="hostname"
											value="${item.hostname}"></td>
										<td class="center"><input type="text"
											class="input-text"
											style="width: 100%; margin: -10px; padding: -10px; height: 100%; border-width: 0px; text-align: center;"
											id="name" name="name"
											value="${item.name}"></td>
										<td class="center"><input type="text"
											class="input-text"
											style="width: 100%; margin: -10px; padding: -10px; height: 100%; border-width: 0px; text-align: center;"
											id="value" name="value"
											value="${item.value}"></td>
										<td class="center">
                                          	<a href="${pageContext.request.contextPath}/snmp/delName?id=${item.id}" 
                                          	onclick="return window.confirm('确认删除数据[${item.name}]吗?')" class="btn btn-mini radius"><i class='icon-remove'></i></a>	
                                         </td>
                                        </shiro:hasPermission> 
                                     </tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<shiro:hasPermission name="/delete">
					<div>
						<button id="updateSysIP" class='btn btn-primary'
							type='submit' onclick="return window.confirm('增加数据成功')">
							<i class="Hui-iconfont">&#xe600;</i> 新增数据
						</button>
						<a href="${pageContext.request.contextPath}/snmp/saveName" 
						class="btn btn-primary radius"><i class='icon-save'></i> 保存数据</a>
					</div>
					</shiro:hasPermission>
				</form>
			</div>
		</div>
	</div>
	</section>
</div>
</body>
</html>
