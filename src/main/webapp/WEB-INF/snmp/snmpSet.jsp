<%@ page import="java.sql.*" language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
	    <div class='container-fluid'>
	        <div class='row-fluid' id='content-wrapper'>
	            <div class='span12'>
	            	<nav class="breadcrumb">
						<i class="Hui-iconfont">&#xe67f;</i> 系统管理 <span class="c-gray en">&gt;</span> OID设置 
					</nav>
	            	<div class="page-container">
	            		<form class='form form-horizontal validate-form' style='margin-bottom: 0;' 
	                            	action="${pageContext.request.contextPath}/snmpSet/query" method="post" >
		            		<div class="text-c"> 
								<input type="text" class="input-text" style="width:250px" placeholder="输入服务器名字或IP或OID..." id="keyword" name="keyword" value="${keyword}">
								<button type="submit" class="btn btn-success radius" id="" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
								<a href="${pageContext.request.contextPath}/snmpSet/add" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 增加OID</a>
								<a href="${pageContext.request.contextPath}/snmpSet/del" class="btn btn-primary radius">绑定/删除OID</a>
							</div>
						</form>          		
	            		<div class="cl pd-5 bg-1 bk-gray mt-20"> 
	            			<span class="l"></span> 
	            			<span class="r pull-right">共有数据：<strong>${list.size()}</strong> 条</span> 
            			</div>
            			<shiro:hasRole name="admin">
						<div class="mt-20" style="margin-top:-50px;">
							<table class="table table-border table-bordered table-hover table-bg table-sort">
								<thead>
									<tr class="text-c">
										<th width="50px">序号</th>
										<th width="250px">服务器名字</th>
										<th width="250px">服务器ip地址</th>
										<th width="250px">OID</th>
										<th width="250px">OID描述</th>
                                    </tr>
                                </thead>
                                <tbody>
									<c:forEach items="${list}" var="item" varStatus="status">
                                   		<tr class="text-c">
                                            <td class="center">${status.index+1}</td>
											<td class="center">${sysnamemap.get(item.hostname)}</td>
											<td class="center">${item.hostname}</td>
											<td class="center">${item.oid}</td>
											<td class="center">${item.chinese_des}</td>
                                        </tr>
                                    </c:forEach>
                               </tbody>
                           </table>
						</div>
						</shiro:hasRole>						
	            	</div>
	            </div>
	        </div>
	    </div>
	</section>
</div>
<style>
.dataTables_wrapper .dataTables_scroll .dataTables_scrollBody > table.dataTable {
margin-top:-20px;
} 
</style>
<script type="text/javascript">
$('.table-sort').dataTable({
	"aaSorting": [[ 0, "asc" ]],//默认第几个排序
	"pagingType": "full_numbers",
	"bStateSave": false,//状态保存
	"pading":false,
    "bSort": true,
    "bAutoWidth": true,
    "sScrollX": "100%",
    "sScrollXInner": "100%",
    "bScrollCollapse": true,
	"aoColumnDefs": [
	  {"orderable":false,"aTargets":[0,4]}// 不参与排序的列
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
    }
});
</script>
</body>
</html>
