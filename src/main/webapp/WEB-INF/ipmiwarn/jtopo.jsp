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
<html>
  <head>
    <meta charset="utf-8">
    <title>绘制拓扑图</title>
    <link rel="icon" href="data:image/ico;base64,aWNv">
    <link href="http://cdn.bootcss.com/twitter-bootstrap/2.0.4/css/bootstrap.min.css" rel="stylesheet">
    <script type="text/javascript" src="<%=basePath%>/js/jtopo-0.4.8-min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/toolbar.js"></script>      
    <link href="http://cdn.bootcss.com/twitter-bootstrap/2.0.4/css/bootstrap-responsive.min.css" rel="stylesheet">
    
  </head>

  <body>
<canvas width="1400" height="700" id="canvas" style=" background-color:#EEEEEE; border:1px solid #444;">
</canvas>
<button id="queryBtn" type="button" class="btn btn-success radius" onclick="queryData();"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
<script type="text/javascript">
function queryData() 
{
	var canvas = document.getElementById('canvas');
	var stage = new JTopo.Stage(canvas);
	var scene = new JTopo.Scene(stage);	
	stage.add(scene);
	var m2;
	var n1;
	var scene1 = new JTopo.Scene(stage);
	stage.add(scene1);
	
	function node(x, y, img,name){
		var node = new JTopo.Node(name);
		node.setImage('<%=basePath%>/images/' + img, true);				
		node.setLocation(x, y);
		node.fontColor = '0,0,0';
		scene.add(node);
		node.dbclick(function(event){
			if(m2 === undefined){
				m2 = nodeMouse(1250, 120, 'avatar.jpg',name);
				for(var n=0;n<50;n++){
					var nn = parseInt(n/10);
					n1 = nodeMouse(60 * nn+1100, 170 + (n%10) * 50, 'icon-jt.png',"编号"+n);
					if(n%10==0){
						linkNode(n1,m2,true);
					}
				}
			}else{
				scene1.remove(m2);
				m2 = nodeMouse(1250, 120, 'avatar.jpg',name);
				for(var n=0;n<50;n++){
					var nn = parseInt(n/10);
					scene1.remove(n1);
					n1 = nodeMouse(60 * nn+1100, 170 + (n%10) * 50, 'icon-jt.png',"编号"+n);
					if(n%10==0){
						linkNode(n1,m2,true);
					}
				}
			}
		});
		return node;
	}
	
	function nodeMouse(x, y, img,name){
		var node = new JTopo.Node(name);
		node.setImage('<%=basePath%>/images/' + img, true);				
		node.setLocation(x, y);
		node.fontColor = '0,0,0';
		scene1.add(node);
		return node;
	}
	
	function linkNode1(nodeA, nodeZ, f){
		var link;
		if(f){
			link = new JTopo.FoldLink(nodeA, nodeZ);
		}else{
			link = new JTopo.Link(nodeA, nodeZ);
		}
		link.direction = 'vertical';
		scene1.add(link);
		return link;
	}
	
	function linkNode(nodeA, nodeZ, f){
		var link;
		if(f){
			link = new JTopo.FoldLink(nodeA, nodeZ);
		}else{
			link = new JTopo.Link(nodeA, nodeZ);
		}
		link.direction = 'vertical';
		scene.add(link);
		return link;
	}
	var root = node(500, 20, 'avatar.jpg',"监控室");
	var i = ${buildingNum};
	var m = ${column};
	var nnn = parseInt(1000/m);
	/* k4为一个房间拥有服务器数量的最大值 
	i为建筑物的数量
	m为服务器列的数量
	nnn为服务器列之间的间距
	kkk为服务器之间的间距
	j为一个建筑中房间的数量
	jzn为建筑物所在位置有多少列服务器
	fk1为房间中服务器的数量
	n为服务器列的初始位置有多少列服务器==以前服务器列的总和+上一个房间服务器列的总和+1
	nn为建筑所在位置有多少列服务器==n+(一个房间服务器列的总和-1)/2
	*/
	var k4 = ${mapCab.get("房间1")};
	if(k4>10){
		var kkk = 25;
	}else{
		var kkk = 50;
	}
	
	if(i>0){
		var j = ${mapHouse.get("建筑0")};
		var jzn = ${(mapBuildingCol.get("建筑0")+1)/2};
		var i1 = node(nnn * jzn, 70, 'avatar.jpg',"建筑名0");
		linkNode(i1,root,true);
		if(j>0){
			var n = 0 + 0 + 1;
			var nn = n + ${(mapCol.get("房间1")-1)/2};
			var j1 = node(nnn * nn, 120, 'avatar.jpg',"房间1");
			linkNode(j1,i1,true);
			var fk1 = ${mapCab.get("房间1")};
			for(var k=0;k<fk1;k++){
				var kk = parseInt(k/20) + n;
				var k1 = node(nnn * kk, 170 + (k%20) * kkk, 'icon-jt.png',"机柜"+k);
				if(k%20==0){
					linkNode(k1,j1,true);
				}
			}
		}
	if(j>1){
			var n = 0 + 2 + 1;
			var nn = n + ${(mapCol.get("房间2")-1)/2};
			var j2 = node(nnn * nn, 120, 'avatar.jpg',"房间2");
			linkNode(j2,i1,true);
			var fk1 = ${mapCab.get("房间2")};
			for(var k=0;k<fk1;k++){
				var kk = parseInt(k/20) + n;
				var k1 = node(nnn * kk, 170 + (k%20) * kkk, 'icon-jt.png',"机柜"+k);
				if(k%20==0){
					linkNode(k1,j2,true);
				}
			}
		}
		/* if(j>2){
			var n = 4 + 2 + 1;
			var nn = n + ${(mapCol.get("房间3")-1)/2};
			var j3 = node(nnn * nn, 120, 'avatar.jpg',"房间3");
			linkNode(j3,i1,true);
			var fk1 = ${mapCab.get("房间3")};
			for(var k=0;k<fk1;k++){
				var kk = parseInt(k/20) + n;
				var k1 = node(nnn * kk, 170 + (k%20) * kkk, 'icon-jt.png',"机柜"+k);
				if(k%20==0){
					linkNode(k1,j3,true);
				}
			}
		} */
	}
	
	if(i>1){
		var j = ${mapHouse.get("建筑1")};
		var jzn = 4 + ${(mapBuildingCol.get("建筑1")+1)/2};
		var i1 = node(nnn * jzn, 70, 'avatar.jpg',"建筑名1");
		linkNode(i1,root,true);
		if(j>0){
			var n = 2 + 2 + 1;
			var nn = n + ${(mapCol.get("房间3")-1)/2};
			var j1 = node(nnn * nn, 120, 'avatar.jpg',"房间1");
			linkNode(j1,i1,true);
			var fk1 = ${mapCab.get("房间3")};
			for(var k=0;k<fk1;k++){
				var kk = parseInt(k/20) + n;
				var k1 = node(nnn * kk, 170 + (k%20) * kkk, 'icon-jt.png',"机柜"+k);
				if(k%20==0){
					linkNode(k1,j1,true);
				}
			}
		}
	if(j>1){
			var n = 4 + 2 + 1;
			var nn = n + ${(mapCol.get("房间4")-1)/2};
			var j2 = node(nnn * nn, 120, 'avatar.jpg',"房间2");
			linkNode(j2,i1,true);
			var fk1 = ${mapCab.get("房间4")};
			for(var k=0;k<fk1;k++){
				var kk = parseInt(k/20) + n;
				var k1 = node(nnn * kk, 170 + (k%20) * kkk, 'icon-jt.png',"机柜"+k);
				if(k%20==0){
					linkNode(k1,j2,true);
				}
			}
		}
		/* if(j>2){
			var n = 6 + 2 + 1;
			var nn = n + ${(mapCol.get("房间3")-1)/2};
			var j3 = node(nnn * nn, 120, 'avatar.jpg',"房间3");
			linkNode(j3,i1,true);
			var fk1 = ${mapCab.get("房间3")};
			for(var k=0;k<fk1;k++){
				var kk = parseInt(k/20) + n;
				var k1 = node(nnn * kk, 170 + (k%20) * kkk, 'icon-jt.png',"机柜"+k);
				if(k%20==0){
					linkNode(k1,j3,true);
				}
			}
		} */
	}
	
	
	
	function hostLink(nodeA, nodeZ){
		var link = new JTopo.FlexionalLink(nodeA, nodeZ);				
		link.shadow = false;
		link.offsetGap = 44;
		scene.add(link);
		return link;
	}
	
	stage.add(scene);

}


</script>
</body>
</html>