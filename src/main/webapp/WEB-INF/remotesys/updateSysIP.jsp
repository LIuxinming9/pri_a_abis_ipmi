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
				<i class="Hui-iconfont">&#xe67f;</i>  系统管理 <span class="c-gray en">&gt;</span> 监控对象 
			</nav>
			<div class='box-content'>
			
				<form class='form form-horizontal validate-form'
					style='margin-bottom: 0;'
					action="${pageContext.request.contextPath}/remotesys/saveSysIP"
					method="post">
					<div id="infoDiv" class="page-container">
						<table
							class="table table-border table-bordered table-hover table-bg table-sort">
							<thead>
								<tr class="text-c">
									<shiro:hasPermission name="/delete">
									<th width="50px">序号</th>
									<th width="250px">服务器名字</th>
									<th width="250px">服务器品牌</th> 
									<th width="250px">服务器位置</th> 
									<th width="250px">服务器ip地址</th>
									<th width="250px">用户名</th>
									<th width="250px">密码</th>
									<th width="250px">备注</th>
									<th width="250px">删除服务器</th>
									</shiro:hasPermission>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${serversip}" var="item"
									varStatus="status">
									<tr class="text-c">
										<shiro:hasPermission name="/delete">
										<td class="center"><input type="text"  readonly="readonly"
											class="input-text"
											style="width: 100%; margin: -10px; padding: -10px; height: 100%; border-width: 0px; text-align: center;"
											id="id" name="id"
											value="${item.id}"></td>
										<td class="center"><input type="text"
											class="input-text"
											style="width: 100%; margin: -10px; padding: -10px; height: 100%; border-width: 0px; text-align: center;"
											id="name" name="name"
											value="${item.name}"></td>
										<td class="center"><input type="text"
											class="input-text"
											style="width: 100%; margin: -10px; padding: -10px; height: 100%; border-width: 0px; text-align: center;"
											id="brand" name="brand"
											value="${item.brand}"></td>
										<td class="center"><input type="text"
											class="input-text"
											style="width: 100%; margin: -10px; padding: -10px; height: 100%; border-width: 0px; text-align: center;"
											id="location" name="location"
											value="${item.location}"></td>
										<td class="center"><input type="text"
											class="input-text"
											style="width: 100%; margin: -10px; padding: -10px; height: 100%; border-width: 0px; text-align: center;"
											id="hostname" name="hostname"
											value="${item.hostname}"></td>
										<td class="center"><input type="text"
											class="input-text"
											style="width: 100%; margin: -10px; padding: -10px; height: 100%; border-width: 0px; text-align: center;"
											id="username" name="username"
											value="${item.username}"></td>
										<td class="center"><input type="text"
											class="input-text"
											style="width: 100%; margin: -10px; padding: -10px; height: 100%; border-width: 0px; text-align: center;"
											id="password" name="password"
											value="${item.password}"></td>
										<td class="center"><input type="text"
											class="input-text"
											style="width: 100%; margin: -10px; padding: -10px; height: 100%; border-width: 0px; text-align: center;"
											id="note" name="note"
											value="${item.note}"></td>
										<td class="center">
                                          	<a href="${pageContext.request.contextPath}/remotesys/delSysIP?id=${item.id}&hostname=${item.hostname}" 
                                          	onclick="return window.confirm('确认删除服务器[${item.hostname}]吗?')" class="btn btn-mini radius"><i class='icon-remove'></i></a>	
                                         </td>
                                        </shiro:hasPermission> 
								</c:forEach>
							</tbody>
						</table>
					</div>
					<shiro:hasPermission name="/delete">
					<div>
						<a href="${pageContext.request.contextPath}/remotesys/addSysIP" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 增加服务器</a>
						<button id="updateSysIP" class='btn btn-primary'
							type='submit' onclick="return window.confirm('保存成功')">
							<i class='icon-save'></i> 保存
						</button>
					</div>
					</shiro:hasPermission>
				</form>
			</div>
		</div>
	</div>
	</section>
</div>
<script type="text/javascript">
$('.table-sort').dataTable({
	"aaSorting": [[ 0, "asc" ]],//默认第几个排序
	"pagingType": "full_numbers",
	"bStateSave": true,//状态保存
	"pading":false,
	"aoColumnDefs": [
		  //{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
		  {"orderable":false,"aTargets":[0]}// 制定列不参与排序
	],
	"bLengthChange": true,
	"bFilter": false,
	"bPaginate": true,
	"bInfo":true,
	"aLengthMenu": [[10, 25, 50, -1], ["10条", "25条", "50条", "全部"]],
	"oLanguage": {
		"sLengthMenu": "每页显示 _MENU_ 记录",
		"sZeroRecords": "没有找到符合条件的数据",
        "sProcessing": "加载中，请稍候...",
        "sInfo": "当前第 _START_ - _END_ 条　共计 _TOTAL_ 条",
        "sInfoEmpty": "没有记录",
        "oPaginate": {
            "sFirst": "首页",
            "sPrevious": "前一页",
            "sNext": "后一页",
            "sLast": "尾页"
        }
    },
    "bSort": false,
    "bAutoWidth": true,
    "sScrollX": "90%",
    "sScrollXInner": "100%",
    "bScrollCollapse": true
});
</script>
</body>
</html>
