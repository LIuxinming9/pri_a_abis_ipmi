<%@ page import="java.sql.*" language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<div id="resultsubsmallDiv" class="mt-20" style="margin-top:-20px;">
	<table style="width:50%;height:100%;vertical-align:top;">
		<tr>
			<td class="center" width="30%" style="vertical-align:top;">
		  <table class="table table-border table-bordered table-hover table-bg table-sort3" style="table-layout:fixed;">
			<thead>
				<tr class="text-c">
				   <th width="50px">序号</th>
				   <th width="100px">资源名</th>
		           <th width="100px">已有的部件名</th>
		        </tr>
		    </thead>
		     <tbody>
				<c:forEach items="${hardname}" var="item" varStatus="status">
		     		<tr class="text-c">
		     			<td class="center">${status.index+1}</td>
		     			<td class="center">${map.get(entity_id)}</td>
		                <td class="center">${map.get(item)}</td>
		            </tr>
		        </c:forEach>
		      </tbody>
		   </table>
			</td>
		</tr>
	</table>
</div>
<script type="text/javascript">

var editor2;
editor2 = $('.table-sort2').dataTable({
	"aaSorting": [[ 0, "asc" ]],//默认第几个排序
	"pagingType": "full_numbers",
	"bStateSave": false,//状态保存
	"pading":false,
	"bLengthChange": true,
	"bFilter": false,
	"bPaginate": false,
	"bInfo":false,
	"bSort": true,
	//"sScrollY": "300px",
	//"bScrollInfinite": true,
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
