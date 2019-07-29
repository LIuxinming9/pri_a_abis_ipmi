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
	/* onclick="getname(this, this.form.nameType);" */
	nameTypes = new Object();
	nameTypes['SystemEvent']=new Array('石家庄', '张家口市', '承德市', '秦皇岛市', '唐山市', '廊坊市', '保定市', '沧州市', '衡水市', '邢台市', '邯郸市');
	nameTypes['EventLoggingDisabled']=new Array('太原市', '大同市', '朔州市', '阳泉市', '长治市', '晋城市', '忻州地区', '吕梁地区', '晋中市', '临汾地区', '运城地区');
	function getname(queryType,nameType) 
	{
		var qt, nt;
	    var i;

	    qt=queryType.value;
	    nt=nameType.value;

	    nameType.length=1;

	    if(qt=='all') return;
	    if(typeof(nameTypes[qt])=='undefined') return;

	    for(i=1; i<nameTypes[qt].length; i++)
	    {
	       nameType.options[i] = new Option();
	       nameType.options[i].text = nameTypes[qt][i];
	       nameType.options[i].value = nameTypes[qt][i];
	    }

	}
	
	function changename() {
		var queryType = $("#queryType option:selected").val();//获取下拉列表中的选中项
		var IPType = $("#IPType option:selected").val();
	$("#nameType > option:gt(0)").each(function(){//避免option累加
	$("#nameType").empty();
		});
		$.post("${pageContext.request.contextPath}/sel/changename",{queryType:queryType,IPType:IPType},function(data){
		var nameType = "";
			nameType += "<option value='" + "全部" + "'>" + "全部" + "</option>";
		for(var i=0;i<data.length;i++){
			nameType += "<option value='" + data[i].sel_name + "' title='" + data[i].mapsel_name + "'>" + data[i].mapsel_name + "</option>";
		}
		$("#nameType").append(nameType);
		},"json");
		}
	
	function changetype() {
		
		var IPType = $("#IPType option:selected").val();//获取下拉列表中的选中项
	$("#queryType > option:gt(0)").each(function(){//避免option累加
	$("#queryType").empty();
		});
		$.post("${pageContext.request.contextPath}/sel/changetype",{IPType:IPType},function(data){
		var queryType = "";
			queryType += "<option value='" + "全部" + "' onclick='changename();'>" + "全部" + "</option>";
		for(var i=0;i<data.length;i++){
			queryType += "<option value='" + data[i].type + "' title='" + data[i].maptype + "' onclick='changename();'>" + data[i].maptype + "</option>";
		}
		$("#queryType").append(queryType);
		},"json");
	}
	
	function changeInfo() {
		var infoType = $("#infoType option:selected").val();//获取下拉列表中的选中项
	$("#IPType > option:gt(0)").each(function(){//避免option累加
	$("#IPType").empty();
		});
	$.post("${pageContext.request.contextPath}/sensorwarnset/changeInfo",{infoType:infoType},function(data){
		var IPType = "";
		IPType += "<option value='" + "全部" + "' onclick='changetype();'>" + "全部" + "</option>";
		for(var i=0;i<data.length;i++){
			IPType += "<option value='" + data[i].valueId + "' title='" + data[i].value + "' onclick='changetype();'>" + data[i].value + "</option>";
		}
		$("#IPType").append(IPType);
		},"json");
	}
	
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
						<i class="Hui-iconfont">&#xe67f;</i> 信息统计 <span class="c-gray en">&gt;</span> 服务器日志 
					</nav>
	            	<div class="page-container">
	            		<div class="text-c"> 
	            				<span class="select-box inline" style="padding-left:10px;">
		            			日志生成时间范围：
								<input type="text" onfocus="WdatePicker({ dateFmt: 'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 00:00:00'})" id="datemin" name="datemin" value="${datemin}" class="input-text Wdate" style="width:160px;">
								-
								<input type="text" onfocus="WdatePicker({ dateFmt: 'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 23:59:59'})" id="datemax" name="datemax" value="${datemax}" class="input-text Wdate" style="width:160px;">
								</span>
								<span class="select-box inline" style="padding-left:20px;">
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
								</div> 
								<div class="text-c" id="typeDiv">
								<span class="select-box inline" style="padding-left:10px;">
								日志类型：
								<select id="queryType" name="queryType" onmousedown="if(this.options.length>10){this.size=10}" 
								onblur="this.size=1" onchange="this.size=1" class="select" style="width:150px;position:absolute;z-index:1;">
								</select>
								</span>
								<span class="select-box inline" style="padding-left:170px;">
								日志名：
								<select id="nameType" name="nameType" onmousedown="if(this.options.length>10){this.size=10}" 
								onblur="this.size=1" onchange="this.size=1" class="select" style="width:150px;position:absolute;z-index:1;">
								</select>
								</span>
								<span style="padding-left:170px;"></span>
								<button id="queryBtn" type="button" class="btn btn-success radius" onclick="queryData();"><i class="Hui-iconfont">&#xe665;</i> 数据查询</button>
								<span style="padding-left:20px;"></span>
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
					                   <th width="100px">时间</th>
					                   <th width="100px">服务器名字</th>
					                   <th width="100px">服务器ip地址</th>
					                   <!-- <th width="100px">服务器名字</th> -->
					                   <th width="150px">日志生成时间</th>
					                   <th width="200px">日志类别</th>
					                   <th width="200px">日志名</th>
					                </tr>
					            </thead>
					            <tbody>
								<c:forEach items="${info}" var="item" varStatus="status">
					         		<tr class="text-c">
					         			<td class="center">${status.index+1}</td>
					                    <td class="center"><fmt:formatDate value="${item.start_time}" pattern="yyyy年MM月dd日  HH:mm:ss" /></td>
					                    <td class="center">${sysnamemap.get(item.IP)}</td>
					                    <td class="center">${item.IP}</td>
					                    <%-- <td class="center">${item.IP}</td> --%>
					                    <td class="center">${item.timestamp}</td>
					                    <td class="center">${item.sensorType}</td>
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
    "sScrollX": "90%",
    "sScrollXInner": "100%",
    "bScrollCollapse": true
});
</script>
</body>
</html>
