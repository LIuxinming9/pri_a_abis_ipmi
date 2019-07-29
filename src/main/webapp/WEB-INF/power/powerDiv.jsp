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
<div id="resultDiv" class="page-container">
	<table class="table table-border table-bordered table-bg mt-20">
		<thead>
			<tr>
				<th colspan="4" scope="col">服务器信息</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td width="15%">服务器名字</td>
				<td width="35%"><span id="lbServerName"></span>${powerInfo.serviceName}</td>
				<td width="15%">服务器IP地址 </td>
				<td>${powerInfo.serviceIP}</td>
			</tr>
			<tr>
				<td>服务器品牌</td>
				<td>${powerInfo.serviceManufacturer}</td>
				<td>服务器位置 </td>
				<td>${powerInfo.serviceLocation}</td>
			</tr>
			<tr>
				<td>产品名称</td>
				<td>${powerInfo.productInfoProductName}</td>
				<td>系统板生产日期</td>
				<td>${powerInfo.boardInfoMfgDate}</td>
			</tr>
			<tr>
				<td>产品序列号</td>
				<td>${powerInfo.productInfoSerialNumber}</td>
				<td>系统板序列号 </td>
				<td>${powerInfo.boardInfoSerialNumber}</td>
			</tr>
			<tr>
				<td>产品型号</td>
				<td>${powerInfo.productInfoModelNumber}</td>
				<td>系统板零件号 </td>
				<td>${powerInfo.boardInfoPartNumber}</td>
			</tr>
			<tr>
				<td>服务器实时告警类别 </td>
				<td>${powerInfo.warnCurrentCategory}</td>
				<td>服务器实时告警级别 </td>
				<td>${powerInfo.warnCurrentLevel}</td>
			</tr>
			<tr>
				<td>服务器实时告警总数 </td>
				<td>${powerInfo.warnCurrentSum}</td>
				<td>服务器历史告警总数 </td>
				<td>${powerInfo.warnHistorySum}</td>
			</tr>
			<tr>
				<td>电源是否有故障 </td>
				<td>${powerInfo.isWarn}</td>
				<td>电源是否已开启 </td>
				<td>${powerInfo.isStart}</td>
			</tr>
		</tbody>
	</table>
</div>
