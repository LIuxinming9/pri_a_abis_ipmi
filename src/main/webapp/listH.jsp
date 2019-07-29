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
    <title>历史告警</title>
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
			var queryType = $.trim($("#queryType").val());
			var query = true;
			if(datemin.length==0 || datemax.length==0) {
                alert('时间范围不可为空');
                query = false;
			}
			if(query){
				$.ajax({
			        type:"POST",
			        url:"${pageContext.request.contextPath}/alarm/listH/query", 
			        data:"datemin=" + datemin + "&datemax=" + datemax + "&queryType=" + queryType,
			        beforeSend: function () {
			            // 禁用按钮防止重复提交
			            $("#queryBtn").attr({ disabled: "disabled" });
			            $("#loading").html("<img src='<%=basePath%>/images/loading.gif' style='max-width:32px;max-height:32px;'/>  正在加载数据，请稍候......");
			            $('#resultDiv').empty();
			        },
			        success : function(msg) {
			        	$('#resultDiv').html(msg);
			        },
			        complete: function () {
			            $("#queryBtn").removeAttr("disabled");
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
			var queryType = $.trim($("#queryType").val());
			var query = true;
			if(datemin.length==0 || datemax.length==0) {
                alert('时间范围不可为空');
                query = false;
			}
			if(query){
				window.open("${pageContext.request.contextPath}/alarm/exprotList?&datemin=" + datemin + "&datemax=" + datemax+ "&queryType=" + queryType);	
			}
		}

	</script>
	<section id='content'>
	    <div class='container-fluid'>
	        <div class='row-fluid' id='content-wrapper'>
	            <div class='span12'>
	            	<nav class="breadcrumb">
						<i class="Hui-iconfont">&#xe67f;</i>  告警管理 <span class="c-gray en">&gt;</span> 历史告警 
						<a
							href="${pageContext.request.contextPath}/updateRemoteSysMax"
							class="btn btn-primary radius" style="float: right"><i
							class="Hui-iconfont"></i>修改服务器信息</a>
					</nav>
	            	<div class="page-container">
	            		<form class='form form-horizontal validate-form' style='margin-bottom: 0;' 
	                            	action="${pageContext.request.contextPath}/gb/biz/trainCheck" method="post" >
		            		<div class="text-c"> 
		            			时间范围：
								<input type="text" onfocus="WdatePicker({ dateFmt: 'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 00:00:00'})" id="datemin" name="datemin" value="${datemin}" class="input-text Wdate" style="width:160px;">
								-
								<input type="text" onfocus="WdatePicker({ dateFmt: 'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 23:59:59'})" id="datemax" name="datemax" value="${datemax}" class="input-text Wdate" style="width:160px;">
								<span class="select-box inline" style="padding-left:30px;">
								类型：
								<select id="queryType" name="queryType" class="select" style="width:120px;">
									<option value="0">全部</option>
									<option value="1">信令</option>
									<option value="2">业务</option>
								</select>
								</span>
								<span style="padding-left:10px;"></span>
								<button id="queryBtn" type="button" class="btn btn-success radius" onclick="queryData();"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
								<span style="padding-left:10px;"></span>
								<button id="exportBtn" type="button" class="btn btn-success radius" onclick="exportData();">导出数据</button>
								<div id="loading" style="padding-top:8px;"></div>
							</div> 
						</form>          		
	            		<div class="cl pd-5 bg-1 bk-gray mt-20"> 
	            			<span class="l"></span> 
	            			<span class="r pull-right"></span> 
            			</div>
						<div id="resultDiv" class="mt-20" style="margin-top:-50px;">
							
						</div>						
	            	</div>
	            </div>
	        </div>
	    </div>
	</section>
</div>
</body>
</html>
