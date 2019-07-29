<%@ page import="java.sql.*" language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts/echarts.min.js" charset=“utf-8”></script>
<div id="resultDiv" class="mt-20" style="margin-top:-20px;">
	<table class="table table-border table-bordered table-hover table-bg table-sort1" style="table-layout:fixed;">
		<thead>
			<tr class="text-c">
			   <th width="50px">序号</th>
                  <th width="200px">时间</th>
                  <th width="100px">服务器名字</th>
                  <th width="100px">服务器ip地址</th>
                  <th width="100px">资源名</th>
                  <th width="100px">卡槽数量</th>
                  <th width="100px">硬件数量</th>
                  <th width="100px">可补充数量</th>
               </tr>
           </thead>
           <tbody>
		<c:forEach items="${resource_name}" var="item" varStatus="status">
        		<tr class="text-c" >
        			<td class="center">${status.index+1}</td>
                   <td class="center"><fmt:formatDate value="${item.start_time}" pattern="yyyy年MM月dd日  HH:mm:ss" /></td>
                   <td class="center">${sysnamemap.get(item.IP)}</td>
                   <td class="center">${item.IP}</td>
                   <td class="center">${map.get(item.entity_id)}</td>
                   <td class="center">${item.cardnum}</td>
                   <td title="点击即可显示已有硬件名" style="background-color:LightGreen;" class="center" onclick="showSubList('${item.entity_id}','${item.IP}');">${item.hardnum}</td>
                   <td title="点击即可显示可补充硬件名" style="background-color:Lightblue;" class="center" onclick="showSubenptyList('${item.entity_id}','${item.IP}');">${item.emptynum}</td>
               </tr>
           </c:forEach>
       </tbody>
   </table>
</div>
<div id="resultSubDiv" class="mt-20" style="vertical-align:top;margin-top:-50px;">
	
</div>
<div id="resultSubsmallDiv" class="mt-20" style="vertical-align:top;margin-top:-50px;">
					
</div>	
<script type="text/javascript">
$('.table-sort1').dataTable({
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

function showSubList(entity_id,IP) 
{
	var IPType = $.trim($("#IPType").val());
	var nameType = $.trim($("#nameType").val());
	$.ajax({
        type:"POST",
        url:"${pageContext.request.contextPath}/resource/resourcesubDiv",
        data:{entity_id:entity_id,IP:IP,nameType:nameType,IPType:IPType},
        success : function(msg) {
        	$('#resultSubDiv').html(msg);
        },
        error: function (data) {
            console.info("error: " + data.responseText);
        }
    });
}

function showSubenptyList(entity_id,IP) 
{
	var IPType = $.trim($("#IPType").val());
	var nameType = $.trim($("#nameType").val());
	$.ajax({
        type:"POST",
        url:"${pageContext.request.contextPath}/resource/resourcesubenptyDiv",
        data:{entity_id:entity_id,IP:IP,nameType:nameType,IPType:IPType},
        success : function(msg) {
        	$('#resultSubDiv').html(msg);
        },
        error: function (data) {
            console.info("error: " + data.responseText);
        }
    });
}
</script>
