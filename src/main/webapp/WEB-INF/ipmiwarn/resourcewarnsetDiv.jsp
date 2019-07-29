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
<shiro:hasPermission name="/delete">
<div id="resultDiv" class="mt-20" style="margin-top:-20px;">
	<table class="table table-border table-bordered table-hover table-bg table-sort" style="table-layout:fixed;">
		<thead>
			<tr class="text-c">
				<th width="50px">序号</th>
                   <th width="100px">服务器名字</th>
                   <th width="100px">服务器ip地址</th>
                   <th width="200px">告警部件名字</th>
                   <th width="100px">删除告警部件</th>
			</tr>
		</thead>
		 <tbody>
	<c:forEach items="${sensorwarnsetlist}" var="item" varStatus="status">
		<tr class="text-c">
			<td class="center">${status.index+1}</td>
			<td class="center">${sysnamemap.get(item.IP)}</td>
			<td class="center"><input type="text"
				class="input-text" readonly="readonly"
				style="width: 100%; margin: -10px; padding: -10px; height: 100%; border-width: 0px; text-align: center;"
				id="IP" name="IP"
				value="${item.IP}"></td>
                  <td class="center"><input type="hidden"
				class="input-text" readonly="readonly"
				style="width: 100%; margin: -10px; padding: -10px; height: 100%; border-width: 0px; text-align: center;"
				id="entity_id" name="entity_id"
				value="${item.entity_id}">${map.get(item.entity_id)}</td>
			<td class="center">
                            	<a href="${pageContext.request.contextPath}/resourcewarnset/delSysIP?IP=${item.IP}&entity_id=${item.entity_id}" class="btn btn-mini radius">
                            	<i class='icon-remove'></i></a>
                           </td>
                     </tr>
	</c:forEach>
     </tbody>
	</table>
</div>
</shiro:hasPermission>
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
    "sScrollX": "100%",
    "sScrollXInner": "100%",
    "bScrollCollapse": false
});
</script>
