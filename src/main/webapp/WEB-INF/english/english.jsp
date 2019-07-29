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
    <title>实时告警</title>
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
						<i class="Hui-iconfont">&#xe67f;</i>  告警管理 <span class="c-gray en">&gt;</span> 传感器参考数据 
					</nav> 
	            	<div class="page-container">
	            	<form class='form form-horizontal validate-form' style='margin-bottom: 0;' 
	                            	action="${pageContext.request.contextPath}/english/query" method="post" >
	            		<div class="text-c"> 
							<input type="text" class="input-text" style="width:250px" placeholder="输入英文名..." id="keyword" name="keyword" value="${keyword}">
							<button type="submit" class="btn btn-success radius" id="" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
						</div>
					</form> 
	            		<div class="cl pd-5 bg-1 bk-gray"> 
	            			<span class="l"></span> 
	            			<span class="r pull-right"></span>
            			</div>
						<div id="resultDiv" class="mt-20" style="margin-top:-20px;width:600px;">
							<table class="table table-border table-bordered table-hover table-bg table-sort" style="table-layout:fixed;">
								<thead>
									<tr class="text-c">
									   <th width="50px">序号</th>
					                   <th width="200px">英文名</th>
					                   <th width="200px">加入翻译</th>
					                </tr>
					            </thead>
					            <tbody>
								<c:forEach items="${list}" var="item" varStatus="status">
					         		<tr class="text-c">
					         			<td class="center">${status.index+1}</td>
					                    <td class="center">${item}</td>
					                    <c:choose>
										    <c:when test="${elist.contains(item)}">
										    	<td class="center">已添加</td>
										    </c:when>
										    <c:otherwise>
										    	<td class="center">
					                             	<a href="${pageContext.request.contextPath}/english/add?word=${item}" class="btn btn-mini radius">
					                             	<i class='icon-plus'></i></a>
				                            	</td>
										    </c:otherwise>
										</c:choose>
					                </tr>
					            </c:forEach>
						       </tbody>
						   </table>
						</div>					
	            	</div>
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
    "bSort": true,
    "bAutoWidth": true,
    "sScrollX": "100%",
    "sScrollXInner": "90%",
    "bScrollCollapse": true
});
</script>
</body>
</html>
