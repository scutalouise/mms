<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
request.setAttribute("error", error);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>后台管理系统</title>
	<meta http-equiv="X-UA-Compatible" content="IE=8"> 
	<link rel="shortcut icon" href="${ctx}/static/images/favicon.ico">
	<script src="${ctx}/static/plugins/easyui/jquery/jquery-1.11.1.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/css/bglogin.css" />
	<script>
	var captcha;
	function refreshCaptcha(){  
	    document.getElementById("img_captcha").src="${ctx}/static/images/kaptcha.jpg?t=" + Math.random();  
	}  
	</script>
	<!-- 处理在登录时，session失效，登录页面嵌套在页面框架里 -->
	<script	language="javascript">   
      if (top != window)   
      top.location.href = window.location.href;   
  	</script> 
</head>
<body style="background-repeat: repeat;">
	<div style="width:1280px;min-width:768px;margin:auto;">
		<div class="login_top" 	style="height:150px;">
			<div class="login_title" style="height:50px;">
				<span style="width:50px;"><img src="${ctx }/static/images/artisan-b.png" alt="" style="width:48px ;height:48px;position:relative;bottom:-6px;"/></span>
				<span style="width:400px;position:relative;left:-20px;">动力环境监控系统</span>
			</div>
		</div>
		<div class="login_main_null" style="height:650px;">
			<div style="float:right;width:600px;margin-top:50px;">
				<form id="loginForm" action="${ctx}/a/login" method="post">
					<div class="login_main" style="width:423px;height:366px;">
						<div class="login_main_top"></div>
						<div class="login_main_errortip">&nbsp;</div>
						<div class="login_main_ln">
							<input type="text" id="username" name="username" value="admin"/>
						</div>
						<div class="login_main_pw">
							<input type="password" id="password" name="password" value="123456"/>
						</div>
						<div class="login_main_yzm">
							<div>
							<input type="text" id="captcha" name="captcha"/>
							<img alt="验证码" src="${ctx}/static/images/kaptcha.jpg" title="点击更换" id="img_captcha" onclick="javascript:refreshCaptcha();" style="height:45px;width:85px;float:right;margin-right:98px;"/>
							</div>
						</div>
						<div class="login_main_remb">
							<input id="rm" name="rememberMe" type="hidden"/><!-- <label for="rm"><span>记住我</span></label> -->
						</div>
						<div class="login_main_submit">
							<button onclick=""></button>
						</div>
					</div>
				</form>
			</div>
			<div style="float:right;width:600px;margin-top:75px;;height:500px;">
				<div style="height:140px">
					<div class="login_main_right rectangle" style="width:100px;background:#2ABADD;text-align:center"><img src="${ctx}/static/images/01.png" alt="" style="width:80px;height:80px;margin:auto;"/></div>
					<div class="login_main_right rectangle" style="width:100px;margin-right:15px;background:#DE9804;text-align:center"><img src="${ctx}/static/images/02.png" alt="" style="width:80px;height:80px;"/></div>
					<div class="login_main_right rectangle" style="width:230px;margin-right:15px;background:#8EC21F;text-align:center"><img src="${ctx}/static/images/03.png" alt="" style="width:80px;height:80px;margin:auto 0;"/></div>
				</div>
				<div style="margin-top:10px;height:140px">
					<div class="login_main_right rectangle" style="width:100px;"></div>
					<div class="login_main_right rectangle" style="width:100px;margin-right:15px;background:#21B3A8;text-align:center"><img src="${ctx}/static/images/10.png" alt="" style="width:80px;height:80px;margin:auto;"/></div>
					<div class="login_main_right rectangle" style="width:100px;margin-right:15px;background:#2ABADD;text-align:center"><img src="${ctx}/static/images/05.png" alt="" style="width:80px;height:80px;margin:auto;"/></div>
				</div>
				<div style="margin-top:10px;height:140px">
					<div class="login_main_right rectangle" style="width:100px;/* background:#818BEC; */"><%-- <img src="${ctx}/static/images/08.png" alt="" style="width:80px;height:80px;margin:auto; "/> --%></div>
					<div class="login_main_right rectangle" style="width:240px;margin-right:15px;background:#818BEC;text-align:center;padding-top: 30px;  height: 80px"><span style="font-family: '黑体';font-size: 24px;color:#fff;vertical-align:50%;">机房与UPS动力环境监控管理专家</span></div>
					<div class="login_main_right rectangle" style="width:100px;margin-right:15px;background:#01A300;text-align:center;"><img src="${ctx}/static/images/07.png" alt="" style="width:80px;height:80px;margin:auto; "/></div>
				</div>
			</div>
		</div>
		<div class="login_footer" style="clear:both;height:60px;margin-top:50px;width:inherit;text-align:center;position:absolute;font-size:13px;font-family: '微软雅黑','宋体',Arial, sans-serif;">
			<p style="color:#fff;">版权所有©四川阿特申商贸有限公司<span style="padding: 0 20px">|</span>成都申控物联科技有限公司<span style="padding: 0 20px;">|</span >Version:&nbsp;&nbsp;<span style="font-weight:900;">0.0.0.1</span></p>
		</div>
	</div>
	<c:choose>
		<c:when test="${error eq 'com.agama.authority.system.utils.CaptchaException'}">
			<script>
				$(".login_main_errortip").html("验证码错误，请重试");
			</script>
		</c:when>
		<c:when test="${error eq 'org.apache.shiro.authc.UnknownAccountException'}">
			<script>
				$(".login_main_errortip").html("帐号或密码错误，请重试");
			</script>
		</c:when>
		<c:when test="${error eq 'org.apache.shiro.authc.IncorrectCredentialsException'}">
			<script>
				$(".login_main_errortip").html("用户名不存在，请重试");
			</script>
		</c:when>
	</c:choose>
</body>
</html>
