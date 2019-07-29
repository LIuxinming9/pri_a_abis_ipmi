<%@ page import="java.sql.*" language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<html>
<head>
    <title>资源管理</title>
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
	<script type="text/javascript">
	
		function queryData() 
		{
			var datemin = $.trim($("#datemin").val());
			var datemax = $.trim($("#datemax").val());
			var IPType = $.trim($("#IPType").val());
			var queryType = $.trim($("#queryType").val());
			var nameType = $.trim($("#nameType").val());
			var query = true;
			if(query){
				$.ajax({
			        type:"POST",
			        url:"${pageContext.request.contextPath}/sel/selquery", 
			        data:{datemin:datemin,datemax:datemax,IPType:IPType,queryType:queryType,nameType:nameType},
			        beforeSend: function () {
			            $("#loading").html("<img src='<%=basePath%>/images/loading.gif' style='max-width:32px;max-height:32px;'/>  正在加载数据，请稍候......");
			            $('#resultDiv').empty();
			        },
			        success : function(msg) {
			        	$('#resultDiv').html(msg);
			        },
			        complete: function () {
			            $("#loading").empty();
			        },
			        error: function (data) {
			            console.info("error: " + data.responseText);
			        }
			    });
			}
		}
		
		function exportData() 
		{
			var datemin = $.trim($("#datemin").val());
			var datemax = $.trim($("#datemax").val());
			var IPType = $.trim($("#IPType").val());
			var queryType = $.trim($("#queryType").val());
			var nameType = $.trim($("#nameType").val());
			var query = true;
			if(query){
				window.open("${pageContext.request.contextPath}/sel/exprotselList?&datemin=" + datemin + "&datemax=" + datemax+ "&queryType=" + queryType+ "&IPType=" + IPType+ "&nameType=" + nameType);	
			}
		}
		
	</script>
	<section id='content'>
	    <div class='container-fluid'>
	        <div class='row-fluid' id='content-wrapper'>
	            <div class='span12'>
	            	<nav class="breadcrumb">
						<i class="Hui-iconfont">&#xe67f;</i>  资源管理 <span class="c-gray en">&gt;</span> 服务器资源 
					</nav>
	            	<div class="page-container">
							<div class="text-c" id="typeDiv">
								<span class="select-box inline" style="padding-left:10px;">
								<!-- <form name="form1" action="" method="get" style="padding-left:10px;"> -->
								服务器IP：
								<select id="IPType" name="IPType" class="select" style="width:120px;">
									<option value="all">全部</option>
									<c:forEach items="${serversip}" var="item" varStatus="status">
									<option value="${item.hostname}">${item.hostname}</option>
									</c:forEach>
								</select>
								</span>
								<span style="padding-left:10px;"></span>
								<button id="queryBtn" type="button" class="btn btn-success radius" onclick="queryData();"><i class="Hui-iconfont">&#xe665;</i> 数据查询</button>
								<span style="padding-left:10px;"></span>
								<button id="exportBtn" type="button" class="btn btn-success radius" onclick="exportData();">导出数据</button>
								 <!-- </form> -->
								<div id="loading" style="padding-top:8px;"></div>
							</div> 
							
	            		<div class="cl pd-5 bg-1 bk-gray"> 
	            			<span class="l"></span>
	            			<span class="r pull-right"></span>
            			</div>
						<div id="resultDiv" class="mt-20" style="margin-top:-20px;">
							<table class="table table-border table-bordered table-hover table-bg table-sort" style="table-layout:fixed;">
								<thead>
									<tr class="text-c">
									   <th width="50px">序号</th>
					                   <th width="100px">服务器ip地址</th>
					                   <th width="100px">CPU</th>
					                   <th width="100px">内存</th>
					                   <th width="100px">CPU风扇</th>
					                   <th width="100px">系统风扇</th>
					                   <th width="100px">5V</th>
					                   <th width="100px">3.3V</th>
					                </tr>
					            </thead>
					            <tbody>
								<c:forEach items="${info}" var="item" varStatus="status">
					         		<tr class="text-c">
					         			<td class="center">${status.index+1}</td>
					                    <td class="center">${item.IP}</td>
					                    <td class="center">${item.timestamp}</td>
					                    <td class="center">${item.sensorType}</td>
					                    <td class="center">${item.event}</td>
					                    <td class="center">${item.event}</td>
					                    <td class="center">${item.event}</td>
					                    <td class="center">${item.event}</td>
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
    "sScrollXInner": "110%",
    "bScrollCollapse": true
});
</script>
</body>
</html>
