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
	if(query){
		$.ajax({
	        type:"POST",
	        url:"${pageContext.request.contextPath}/sensor/sensorfaquery", 
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
     			<div id="buttonDiv" class="mt-20" style="margin-left:-240px;">
     				<button id="chartBtn" type="button" class="btn btn-success radius" onclick="chartData();"><i class="Hui-iconfont">&#xe665;</i> 表格</button>
     			</div>
			<div id="chartresultDiv" class="mt-20" style="margin-top:10px;margin-left:-240px;">
					<div class='mt-20'>
				        <div id="networkLine" class="chart" style="width: 100%;max-height:450px;" ></div>
				        <input type="hidden" id="networkLineOption" value='${networkLineOption}'/> 
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
$(document).ready(function () 
		{
			var networkLineOption = $("#networkLineOption").val();
	    	var networkLine = echarts.init(document.getElementById('networkLine'));
	    	networkLine.setOption($.parseJSON(networkLineOption));
			
		});
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
