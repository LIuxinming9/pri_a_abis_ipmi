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
<div id="resultDiv" class="mt-20" style="margin-top:-20px;">
<form class='form form-horizontal validate-form'
	style='margin-bottom: 0;'
	action="${pageContext.request.contextPath}/remotesys/saveSysIP"
	method="post">
	<div id="infoDiv" class="mt-20">
		<table
			class="table table-border table-bordered table-hover table-bg  table-sort">
			<thead>
				<tr class="text-c">
					<shiro:hasPermission name="/delete">
					<th width="50px">序号</th>
					<th width="250px">OID</th>
					<th width="250px">OID描述</th>
					<th width="200px">加入选定服务器</th>
					<th width="250px">将OID从选定服务器删除</th>
					</shiro:hasPermission>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list}" var="item" varStatus="status">
					<tr class="text-c">
						<shiro:hasPermission name="/delete">
						<td class="center">${status.index+1}</td>
						<td class="center"><input type="text"
							class="input-text"
							style="width: 100%; margin: -10px; padding: -10px; height: 100%; border-width: 0px; text-align: center;"
							id="oid" name="oid"
							value="${item.oid}"></td>
						<td class="center"><input type="text"
							class="input-text"
							style="width: 100%; margin: -10px; padding: -10px; height: 100%; border-width: 0px; text-align: center;"
							id="chinese_des" name="chinese_des"
							value="${item.chinese_des}"></td>
						<c:if test="${idList.size()>0}">
						<c:choose>
						    <c:when test="${idList.contains(item.id)}">
						    	<td class="center">已添加</td>
						    </c:when>
						    <c:otherwise>
						    	<td class="center">
	                            	<a href="${pageContext.request.contextPath}/snmpSet/insert?id=${item.id}&hostname=${idlist.get(0).hostname}" class="btn btn-mini radius">
	                            	<i class='icon-plus'></i></a>
		                        </td>
						    </c:otherwise>
						</c:choose>
						<td class="center">
                         	<a href="${pageContext.request.contextPath}/snmpSet/delByIp?id=${item.id}&hostname=${idlist.get(0).hostname}" class="btn btn-mini radius"><i class='icon-remove'></i></a>	
                        </td>
                        </c:if>
                       </shiro:hasPermission> 
				</c:forEach>
			</tbody>
		</table>
	</div>
</form>
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
    "sScrollX": "100%",
    "sScrollXInner": "100%",
    "bScrollCollapse": false
});
</script>