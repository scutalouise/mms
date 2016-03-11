<%@page import="com.agama.authority.utils.Global"%>
<%@page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
request.setAttribute("error", error);
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	 <meta charset="utf-8">

    <title><%=Global.getConfig("projectName.zh_CN") %></title>
     <link rel="shortcut icon" href="${ctx}/static/images/favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="${ctx }/static/css/style.css">
    <link rel="stylesheet" type="text/css" href="${ctx }/static/css/metro.css">
    <script src="${ctx }/static/js/jquery.min.js"></script>
    <script type="text/javascript">
    function subLogin(){
    	
		if($("#username").val()==""){
		
			$(".login_main_errortip").html("用户名不能为空");
			$("#username").focus();
			return false;
		}else if($("#password").val()==""){
			$(".login_main_errortip").html("密码不能为空");
			$("#password").focus();
			return false;
		}else{
			return true;
		}
		
	}
    
   
    </script>
       <style type="text/css">
   .login_main_errortip{
   color: red;
   padding: 2px;
  
   text-align: center;
   font-weight: 800;
   margin-bottom: 5px;
   }
   </style>
</head>
<body style="background: url('${ctx }/static/images/bj_11.jpg') top center no-repeat;background-size:100% 100%;">
    

    <div class="top-banner metro-layout" >

        <div class="header">
            <div class="logintitle">
               
                <div class="logintitle_mid"><h1><%=Global.getConfig("projectName.zh_CN") %></h1></div>
                <div class="logintitle_bottom" style="color: black;"><%=Global.getConfig("projectName.en_US") %></div>
            </div>
        </div>
        <div class="bottom" style="border-radius:10px;">
            <form id="loginForm" action="${ctx}/it/login" method="post" style="padding: 10px;" onsubmit="return subLogin()">
	            <div class="form header_body" style="text-align:right;">
	                 <div class="login_main_errortip">&nbsp;</div>
	                <div class="form-item">
	                    <span>用户名：</span><input type="text" name="username" id="username" placeholder="请输入用户名" autocomplete="off" style="border-radius:5px;">
	                </div>
	                <div class="form-item">
	                    <span>密&nbsp;码：</span><input type="password" name="password" id="password" placeholder="请输入密码" autocomplete="off" style="border-radius:5px;">
	                </div>
	                <div class="button-panel">
	                    <input type="submit" class="button" title="Sign In" value="登 录">
	                </div>
          	  </div>
            </form>
        </div>
    </div>
   
    <c:choose>
	
		<c:when test="${error eq 'org.apache.shiro.authc.UnknownAccountException'}">
		
		
			<script>
		
				$(".login_main_errortip").html("用户名或密码不正确，请重试");
			</script>
		</c:when>
		<c:when test="${error eq 'org.apache.shiro.authc.IncorrectCredentialsException'}">
			<script>
				$(".login_main_errortip").html("用户名或密码不正确，请重试");
			</script>
		</c:when>
	</c:choose>
</body>
</html>
<script type="text/javascript">
   
</script>