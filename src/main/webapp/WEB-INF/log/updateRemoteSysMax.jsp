<%@ page import="java.sql.*" language="java"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<html>
<head>
<title>修改远程服务器参数</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta
	content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no'
	name='viewport' />
<jsp:include page="/template/link.jsp" />

</head>
<body class='contrast-blue '>
	<div id='wrapper'>
	<div class='row-fluid' style="margin-top: 20px;">
		<div class='span12 box'>
			<div class='box-header blue-background'>
				<div class='title'>修改服务器信息</div>
				<div class='actions'></div>
			</div>
			<div class='box-content'>
				<form class='form form-horizontal validate-form'
					style='margin-bottom: 0;'
					action="${pageContext.request.contextPath}/saveRemoteSysMax"
					method="post">
					<div id="infoDiv" class="page-container">
						<table
							class="table table-border table-bordered table-bg mt-20">
							<thead>
								<tr class="text-c">
									<th width="50px">序号</th>
									<th width="250px">服务器名字</th>
									<th width="250px">服务器ip地址</th>
									<th width="250px">告警信息类别</th>
									<th width="250px">告警部件名字</th>
									<th width="250px">告警预定值</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${remotesysmaxlist}" var="item"
									varStatus="status">
									<tr class="text-c">
										<td class="center">${status.index+1}</td>
										<td class="center">${remotesysmaxlist.get(status.index).name}</td>
										<td class="center"><input type="text"
											class="input-text"
											style="width: 100%; margin: -10px; padding: -10px; height: 100%; border-width: 0px; text-align: center;"
											id="ip" name="ip"
											value="${remotesysmaxlist.get(status.index).ip}"></td>
										<td class="center"><input type="text"
											class="input-text"
											style="width: 100%; margin: -10px; padding: -10px; height: 100%; border-width: 0px; text-align: center;"
											id="mem_max" name="mem_max"
											value="${remotesysmaxlist.get(status.index).mem_max}"></td>
										<td class="center"><input type="text"
											class="input-text"
											style="width: 100%; margin: -10px; padding: -10px; height: 100%; border-width: 0px; text-align: center;"
											id="file_max" name="file_max"
											value="${remotesysmaxlist.get(status.index).file_max}"></td>
										<td class="center"><input type="text"
											class="input-text"
											style="width: 100%; margin: -10px; padding: -10px; height: 100%; border-width: 0px; text-align: center;"
											id="cpu_max" name="cpu_max"
											value="${remotesysmaxlist.get(status.index).cpu_max}"></td>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div>
						<button id="updateRemoteSysMax" class='btn btn-primary'
							type='submit' onclick="return window.confirm('保存成功')">
							<i class='icon-save'></i> 保存
						</button>
						<span style="padding-left: 20px;"></span> <a
							href="javascript:history.back(-1)" class="btn btn-warning"><i
							class='icon-reply'></i> 退出</a>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>

</body>
</html>
