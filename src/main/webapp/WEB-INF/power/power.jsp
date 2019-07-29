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
	<script type="text/javascript">
	
	function queryData() 
	{
		var IPType = $.trim($("#IPType").val());
		var query = true;
		if(query){
			$.ajax({
		        type:"POST",
		        url:"${pageContext.request.contextPath}/power/query", 
		        data:{IPType:IPType},
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
	
	function powerup() 
	{
		var IPType = $.trim($("#IPType").val());
		var query = true;
		var query1 = false;
		var query2 = false;
		var query3 = false;
		if(IPType=="all") {
               alert('请选择一个具体的IP');
               query = false;
		}
		if(query){
			var query1 = confirm('第一次确认，是否开启服务器?');
		}
		if(query1){
			var query2 = confirm('第二次确认，是否开启服务器?');
		}
		if(query2){
			var query3 = confirm('第三次确认，是否开启服务器?');
		}
		if(query3){
			$.ajax({
		        type:"POST",
		        url:"${pageContext.request.contextPath}/power/powerup", 
		        data:{IPType:IPType},
		        beforeSend: function () {
		            $("#loading").html("<img src='<%=basePath%>/images/loading.gif' style='max-width:32px;max-height:32px;'/>  服务器正在开启，请稍候......");
		        },
		        success : function(msg) {
		        	alert("服务器已开启");
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
	
	function powerdown() 
	{
		var IPType = $.trim($("#IPType").val());
		var query = true;
		var query1 = false;
		var query2 = false;
		var query3 = false;
		if(IPType=="all") {
               alert('请选择一个具体的IP');
               query = false;
		}
		if(query){
			var query1 = confirm('第一次确认，是否关闭服务器?');
		}
		if(query1){
			var query2 = confirm('第二次确认，是否关闭服务器?');
		}
		if(query2){
			var query3 = confirm('第三次确认，是否关闭服务器?');
		}
		if(query3){
			$.ajax({
		        type:"POST",
		        url:"${pageContext.request.contextPath}/power/powerdown", 
		        data:{IPType:IPType},
		        beforeSend: function () {
		            $("#loading").html("<img src='<%=basePath%>/images/loading.gif' style='max-width:32px;max-height:32px;'/>  服务器正在关闭，请稍候......");
		        },
		        success : function(msg) {
		        	alert("服务器已关闭");
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
	
	function hardreset() 
	{
		var IPType = $.trim($("#IPType").val());
		var query = true;
		var query1 = false;
		var query2 = false;
		var query3 = false;
		if(IPType=="all") {
               alert('请选择一个具体的IP');
               query = false;
		}
		if(query){
			var query1 = confirm('第一次确认，是否硬重启服务器?');
		}
		if(query1){
			var query2 = confirm('第二次确认，是否硬重启服务器?');
		}
		if(query2){
			var query3 = confirm('第三次确认，是否硬重启服务器?');
		}
		if(query3){
			$.ajax({
		        type:"POST",
		        url:"${pageContext.request.contextPath}/power/hardreset", 
		        data:{IPType:IPType},
		        beforeSend: function () {
		            $("#loading").html("<img src='<%=basePath%>/images/loading.gif' style='max-width:32px;max-height:32px;'/>  服务器正在重启，请稍候......");
		        },
		        success : function(msg) {
		        	alert("服务器已硬重启");
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
		IPType += "<option value='" + "全部" + "' onclick='queryData();'>" + "请选择" + "</option>";
		for(var i=0;i<data.length;i++){
			IPType += "<option value='" + data[i].valueId + "' title='" + data[i].value + "' onclick='queryData();'>" + data[i].value + "</option>";
		}
		$("#IPType").append(IPType);
		},"json");
	}
		
	</script>
	<section id='content'>
	    <div class='container-fluid'>
	        <div class='row-fluid' id='content-wrapper'>
	            <div class='span12'>
	            	<nav class="breadcrumb">
						<i class="Hui-iconfont">&#xe67f;</i>  应急处置
					</nav>
	            	<div class="page-container">
					<div class="text-c"> 
						<span class="select-box inline" style="padding-left:10px;">
						服务器查询方式：
						<select id="infoType" name="infoType" class="select" style="width:150px;">
							<option value="全部" onclick="changeInfo();">请选择</option>
							<option value="name" onclick="changeInfo();">服务器名字</option>
							<option value="hostname" onclick="changeInfo();">服务器IP</option>
						</select>
						</span>
						<span class="select-box inline" style="padding-left:20px;">
						服务器查询结果：
						<select id="IPType" name="IPType" onmousedown="if(this.options.length>10){this.size=10}" 
						onblur="this.size=1" onchange="this.size=1" class="select" style="width:150px;position:absolute;z-index:1;">
						</select>
						</span>
					</div>
					<shiro:hasPermission name="/delete">
					<div id=resultDiv class="page-container">
						<table class="table table-border table-bordered table-bg mt-20">
							<thead>
								<tr>
									<th colspan="4" scope="col">服务器信息</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td width="15%">服务器名字</td>
									<td width="35%"><span id="lbServerName"></span></td>
									<td width="15%">服务器IP地址 </td>
									<td></td>
								</tr>
								<tr>
									<td>服务器品牌</td>
									<td></td>
									<td>服务器位置 </td>
									<td></td>
								</tr>
								<tr>
									<td>产品名称</td>
									<td></td>
									<td>系统板生产日期</td>
									<td></td>
								</tr>
								<tr>
									<td>产品序列号</td>
									<td></td>
									<td>系统板序列号 </td>
									<td></td>
								</tr>
								<tr>
									<td>产品型号</td>
									<td></td>
									<td>系统板零件号 </td>
									<td></td>
								</tr>
								<tr>
									<td>服务器实时告警类别 </td>
									<td></td>
									<td>服务器实时告警级别 </td>
									<td></td>
								</tr>
								<tr>
									<td>服务器实时告警总数 </td>
									<td></td>
									<td>服务器历史告警总数 </td>
									<td></td>
								</tr>
								<tr>
									<td>电源是否有故障 </td>
									<td></td>
									<td>电源是否已开启 </td>
									<td></td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="text-c" style="margin-top:20px;"> 
						<span class="select-box inline" style="padding-left:0px;">
						<button id="powerup" type="button" class="btn btn-success radius" onclick="powerup();"> 开启</button>
						</span>
						<span class="select-box inline" style="padding-left:25px;">
						<button id="powerdown" type="button" class="btn btn-success radius" onclick="powerdown();"> 关闭</button>
						</span>
						<span class="select-box inline" style="padding-left:25px;">
						<button id="hardreset" type="button" class="btn btn-success radius" onclick="hardreset();"> 硬重启</button>
						</span>
					</div>
					</shiro:hasPermission>
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
</body>
</html>
