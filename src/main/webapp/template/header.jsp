<%@ page import="java.sql.*" language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

	<div class='navbar'>
        <div class='navbar-inner'>
            <div class='container-fluid'> 
                <a class='brand' href='#'>
<!--                     <i class='icon-heart-empty'></i> -->
                    <span class='hidden-phone'>服务器带外网管系统</span>
                </a>              
                <!-- <a class='toggle-nav btn pull-left' style="margin-top:8px;" href='#'>
                    <i class='icon-reorder'></i>
                </a> -->
                <ul class='nav pull-right'>                                      
                    <li class='dropdown dark user-menu'>
                        <a class='dropdown-toggle' data-toggle='dropdown' href='#'>
                            <img alt='当前用户：' height='23' src='<%=basePath%>/images/avatar.jpg' width='23' />
                            <span class='user-name hidden-phone'>${user.username}</span>
                            <b class='caret'></b>
                        </a>
                        <ul class='dropdown-menu'>
                            <li>
                                <a href='${pageContext.request.contextPath}/user/uPwd'>
                                    <i class='icon-cog'></i> 修改密码
                                </a>
                            </li>
                            <li class='divider'></li>
                            <li>
                                <a href='${pageContext.request.contextPath}/user/logout'>
                                    <i class='icon-signout'></i> 退出
                                </a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </div>