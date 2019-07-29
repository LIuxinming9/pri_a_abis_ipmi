<%@ page import="java.sql.*" language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts/echarts.min.js" charset=“utf-8”></script>
<div id='wrapper' >
<script type="text/javascript">
function chartData() 
{
	var IPType = $.trim($("#IPType").val());
	var nameType = $.trim($("#nameType").val());
	var datemin = $.trim($("#datemin").val());
	var datemax = $.trim($("#datemax").val());
	var query = true;
	if(datemin.length==0 || datemax.length==0) {
        alert('时间范围不可为空');
        query = false;
	}
	if(IPType=="all") {
        alert('服务器IP不可为空');
        query = false;
	}
	if(nameType=="all") {
        alert('传感器名字不可为空');
        query = false;
	}
	if(query){
		$.ajax({
	        type:"POST",
	        url:"${pageContext.request.contextPath}/sensor/sensortechartDiv", 
	        data:{datemin:datemin,datemax:datemax,IPType:IPType,nameType:nameType},
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
	</script>
	<section id='content'>
       		<div class="text-c" > 
       			<div class="cl pd-5 bg-1 bk-gray"> 
        			<span class="l"></span> 
        			<span class="r pull-right"></span> 
      			</div>
      			<div id="buttonDiv" class="mt-20" style="margin-top:10px;margin-left:-240px;">
      				<button id="chartBtn" type="button" class="btn btn-success radius" onclick="chartData();"><i class="Hui-iconfont">&#xe665;</i> 折线图</button>
      			</div>
			<div id="resultDiv" class="mt-20" style="margin-top:10px;margin-left:-240px;">
				<table class="table table-border table-bordered table-hover table-bg table-sort" style="table-layout:fixed;">
					<thead>
						<tr class="text-c">
						   <th width="50px">序号</th>
		                   <th width="150px">时间</th>
		                   <th width="100px">服务器名字</th>
		                   <th width="100px">服务器ip地址</th>
		                   <th width="100px">传感器类型</th>
		                   <th width="250px">传感器名字</th>
		                   <th width="100px">传感器读数</th>
		                   <!-- <th width="250px">传感器状态</th> -->
		                </tr>
		            </thead>
		            <tbody>
					<c:forEach items="${info}" var="item" varStatus="status">
		         		<tr class="text-c">
		         			<td class="center">${status.index+1}</td>
		                    <td class="center"><fmt:formatDate value="${item.start_time}" pattern="yyyy年MM月dd日  HH:mm:ss" /></td>
		                    <td class="center">${sysnamemap.get(item.IP)}</td>
		                    <td class="center">${item.IP}</td>
		                    <td class="center">${map.get(item.sensor_type)}</td>
		                    <td class="center">${map.get(item.name)}</td>
		                    <td class="center">${item.current_num} 度</td>
		                    <%-- <td class="center">${item.state}</td> --%>
		                </tr>
		            </c:forEach>
			       </tbody>
			   </table>
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
