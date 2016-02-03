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
	<title>动态环境数据采集监控平台</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
	<link rel="shortcut icon" href="${ctx}/static/images/favicon.ico"/>
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
  	<script type="text/javascript">
		$(function(){
			var height = document.body.clientHeight;
			$(".login_title").css("margin-top",(height-600)/2);
			window.onresize=function(){
				winResize();
			}
		})
		function winResize(){
			var height = document.body.clientHeight;
			$(".login_title").css("margin-top",(height-600)/2);	
		}
		
		function subLogin(){
			if($("#username").val()==""){
				$(".login_main_errortip").html("用户名不能为空");
				$("#username").focus();
				return false;
			}else if($("#password").val()==""){
				$(".login_main_errortip").html("密码不能为空");
				$("#password").focus();
				return false;
			}else if($("#captcha").val()==""){
				$(".login_main_errortip").html("验证码不能为空");
				$("#captcha").focus();
				return false;
			}else{
				return true;
			}
			
		}
	</script>
	<style type="text/css">
		html,body{
			height:100%;
		}
	</style>
</head>
<body style="background-repeat: repeat;overflow-y:hidden;min-width:1280px;min-height: 700px;">
	<div style="width:1280px;min-width:768px;margin:auto;">
		<div class="login_top" >
			<div class="login_title">
				<span style="width:50px;float: left;margin-left: 10px;"><img src="${ctx }/static/images/artisan-b.png" alt="" style="width:48px ;height:50px;"/></span>
				<span style="color:#6F6C6C;float: left;margin-left: 10px;" id="title">动力环境数据采集监控平台</span>
			</div>
		</div>
		<div class="login_main_null" style="height:475px;">
			<div style="float:right;width:600px;">
				<form id="loginForm" action="${ctx}/a/login" method="post"  onsubmit="return subLogin()">
					<div class="login_main" style="width:423px;height:366px;">
						<div class="login_main_top" style="margin-top:40px;"></div>
						<div class="login_main_errortip" style="margin-top:20px;">&nbsp;</div>
						<div class="login_main_ln" >
							<input type="text" id="username" name="username"/>
						</div>
						<div class="login_main_pw">
							<input type="password" id="password" name="password" style="margin-top:20px;"/>
						</div>
						<%-- <div class="login_main_yzm">
							<div>
							<input type="text" id="captcha" name="captcha" />
							<img alt="验证码" src="${ctx}/static/images/kaptcha.jpg"  title="点击更换" id="img_captcha" onclick="javascript:refreshCaptcha();" style="height:45px;width:85px;float:right;margin-right:98px;border:1px solid #B5B5B5; "/>
							</div>
						</div> --%>
						<div class="login_main_remb" >
							<input id="rm" name="rememberMe" type="hidden"/><!-- <label for="rm"><span>记住我</span></label> -->
						</div>
						<div class="login_main_submit" style="margin-top:20px;">
							<button onclick=""></button>
						</div>
					</div>
				</form>
			</div>
			<div style="float:right;width:600px;margin-top:25px;height:450px;">
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
		<div class="login_footer" style="clear:both;height:60px;width:inherit;text-align:center;font-size:13px;font-family: '微软雅黑','宋体',Arial, sans-serif;">
			<p style="color:#6F6C6C;">版权所有©成都申控物联科技有限公司<span style="padding: 0 20px;">|</span >Version:&nbsp;&nbsp;<span style="font-weight:900;">SK_DHRS_NX0100</span></p>
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
