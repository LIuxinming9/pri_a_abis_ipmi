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
	<title>服务器通用带外网管软件系统 </title>
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
			var nameType = $.trim($("#nameType").val());
			var IPType = $.trim($("#IPType").val());
			var query = true;
			if(query){
				$.ajax({
			        type:"POST",
			        url:"${pageContext.request.contextPath}/chassiswarnset/query", 
			        data:{IPType:IPType,nameType:nameType},
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
		
		function changeInfo() {
			var infoType = $("#infoType option:selected").val();//获取下拉列表中的选中项
		$("#IPType > option:gt(0)").each(function(){//避免option累加
		$("#IPType").empty();
			});
		$.post("${pageContext.request.contextPath}/sensorwarnset/changeInfo",{infoType:infoType},function(data){
			var IPType = "";
			IPType += "<option value='" + "全部" + "' >" + "全部" + "</option>";
			for(var i=0;i<data.length;i++){
				IPType += "<option value='" + data[i].valueId + "' title='" + data[i].value + "'>" + data[i].value + "</option>";
			}
			$("#IPType").append(IPType);
			},"json");
		}
		
		function exportData() 
		{
			var queryType = $.trim($("#queryType").val());
			var nameType = $.trim($("#nameType").val());
			var IPType = $.trim($("#IPType").val());
			var query = true;
			if(query){
				window.open("${pageContext.request.contextPath}/chassiswarnset/exprotList?queryType=" + queryType + "&nameType=" + nameType + "&IPType=" + IPType);	
			}
		}
	</script>
	<section id='content'>
		<div class='container-fluid'>
	        <div class='row-fluid' id='content-wrapper'>
		<div class='span12'>
			<nav class="breadcrumb">
				<i class="Hui-iconfont">&#xe67f;</i>  告警管理 <span class="c-gray en">&gt;</span> 告警列表<span class="c-gray en">&gt;</span> 机箱告警列表 
			</nav>
			<div class='page-container'>
				<div class="text-c"> 
					<span class="select-box inline" style="padding-left:10px;">
					服务器查询方式：
					<select id="infoType" name="infoType" class="select" style="width:150px;">
						<option value="全部" onclick="changeInfo();">请选择</option>
						<option value="name" onclick="changeInfo();">服务器名字</option>
						<option value="brand" onclick="changeInfo();">服务器品牌</option>
						<option value="hostname" onclick="changeInfo();">服务器IP</option>
					</select>
					</span>
					<span class="select-box inline" style="padding-left:20px;">
					服务器查询结果：
					<select id="IPType" name="IPType" onmousedown="if(this.options.length>10){this.size=10}" 
					onblur="this.size=1" onchange="this.size=1" class="select" style="width:150px;position:absolute;z-index:1;">
					</select>
					</span>
					<span class="select-box inline" style="padding-left:170px;">
					告警部件名字：
					<select id="nameType" name="nameType" class="select" style="width:200px;">
						<option value="全部">全部</option>
						<option value="is_power_fault">电源是否有故障</option>
						<option value="cooling_fault_detected">风扇是否有故障</option>
						<option value="drive_fault_detected">驱动器是否有故障</option>
						<option value="is_chassis_intrusion_active">机箱是否被入侵</option>
						<option value="is_interlock">是否检测到联锁</option>
						<option value="is_power_overload">系统是否因功率过载而关闭</option>
						<option value="is_power_on">电源是否已开启</option>
						<option value="ac_failed">AC是否失败</option>
						<option value="was_power_fault">上次断电是否因为电源故障</option>
						<option value="was_interlock">上次断电是否因为电源联锁</option>
					</select>
					</span>
					<span style="padding-left:20px;"></span>
					<button id="queryBtn" type="button" class="btn btn-success radius" onclick="queryData();"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
					<span style="padding-left:20px;"></span>
					<button id="exportBtn" type="button" class="btn btn-success radius" onclick="exportData();">导出数据</button>
				 	<div id="loading" style="padding-top:8px;"></div>
				</div> 
				<shiro:hasPermission name="/delete">
				<form class='form form-horizontal validate-form' style='margin-bottom: 0;' action="${pageContext.request.contextPath}/chassiswarnset/save"
					method="post">
					<div id="resultDiv" class="mt-20" style="margin-top:-20px;">
						<table class="table table-border table-bordered table-hover table-bg table-sort" style="table-layout:fixed;">
							<thead>
								<tr class="text-c">
									<th width="50px">序号</th>
				                   <th width="100px">服务器名字</th>
				                   <th width="100px">服务器ip地址</th>
				                   <th width="200px">告警部件名字</th>
				                   <th width="100px">删除</th>
								</tr>
							</thead>
							 <tbody>
								<c:forEach items="${setlist}" var="item" varStatus="status">
									<tr class="text-c">
										<td class="center">${status.index+1}</td>
										<td class="center">${sysnamemap.get(item.IP)}</td>
										<td class="center"><input type="text"
											class="input-text" readonly="readonly"
											style="width: 100%; margin: -10px; padding: -10px; height: 100%; border-width: 0px; text-align: center;"
											id="IP" name="IP"
											value="${item.IP}"></td>
					                    <td class="center"><input type="text"
											class="input-text" readonly="readonly"
											style="width: 100%; margin: -10px; padding: -10px; height: 100%; border-width: 0px; text-align: center;"
											id="name" name="name"
											value="${map.get(item.name)}"></td>
										<td class="center">
		                                 	<a href="${pageContext.request.contextPath}/chassiswarnset/del?IP=${item.IP}&name=${item.name}" class="btn btn-mini radius">
		                                 	<i class='icon-remove'></i></a>
		                                </td>
				                        </tr>
								</c:forEach>
			      		 	</tbody>
						</table>
					</div>
				</form>
				</shiro:hasPermission>
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
    "bSort": false,
    "bAutoWidth": false,
    "sScrollX": "90%",
    "sScrollXInner": "100%",
    "bScrollCollapse": false
});
</script>
</body>
</html>
