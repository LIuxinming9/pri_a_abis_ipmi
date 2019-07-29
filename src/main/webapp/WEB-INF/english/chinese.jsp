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
				<i class="Hui-iconfont">&#xe67f;</i>  系统管理 <span class="c-gray en">&gt;</span> 英文翻译 
			</nav>
			<div class='box-content'>
				<form class='form form-horizontal validate-form' style='margin-bottom: 0;' 
	                            	action="${pageContext.request.contextPath}/chinese/query" method="post" >
            		<div class="text-c"> 
						<input type="text" class="input-text" style="width:250px" placeholder="输入英文名或中文翻译..." id="keyword" name="keyword" value="${keyword}">
						<button type="submit" class="btn btn-success radius" id="" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
					</div>
				</form>  
				<form class='form form-horizontal validate-form'
					style='margin-bottom: 0;'
					action="${pageContext.request.contextPath}/chinese/save"
					method="post">
					<shiro:hasPermission name="/delete">
					<div id="infoDiv" class="page-container">
						<table class="table table-border table-bordered table-hover table-bg table-sort" style="table-layout:fixed;">
							<thead>
								<tr class="text-c">
									<th width="50px">序号</th>
									<th width="250px">英文名</th>
									<th width="250px">中文翻译</th>
									<th width="250px">删除</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${list}" var="item"
									varStatus="status">
									<tr class="text-c">
										<td class="center">${status.index+1}</td>
										<td class="center"><input type="text"
											class="input-text" readonly="readonly"
											style="width: 100%; margin: -10px; padding: -10px; height: 100%; border-width: 0px; text-align: center;"
											id="english" name="english"
											value="${item.english.trim()}"></td>
										<td class="center"><input type="text"
											class="input-text"
											style="width: 100%; margin: -10px; padding: -10px; height: 100%; border-width: 0px; text-align: center;"
											id="chinese" name="chinese"
											value="${item.chinese.trim()}"></td>
										<td class="center">
                                          	<a href="${pageContext.request.contextPath}/chinese/del?english=${item.english.trim()}" class="btn btn-mini radius"><i class='icon-remove'></i></a>	
                                         </td>
								</c:forEach>
							</tbody>
						</table>
					</div>
					</shiro:hasPermission>
					<shiro:hasPermission name="/delete">
					<div>
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
