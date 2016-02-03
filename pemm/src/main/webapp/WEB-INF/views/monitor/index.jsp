<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>监控中心</title>
<link rel="shortcut icon" href="${ctx}/static/images/favicon.ico">
<script src="${ctx }/static/plugins/easyui/jquery/jquery-2.1.1.min.js"></script>
<%-- <script type="text/javascript" 
	src="${ctx }/static/plugins/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="${ctx }/static/plugins/bootstrap/css/bootstrap.min.css"> --%>
<link rel="stylesheet" href="${ctx }/static/css/monitor/index.css">
<script type="text/javascript">
	var ctx = "${ctx}";

</script>


<script type="text/javascript" src="${ctx }/static/js/monitor/index.js"></script>
</head>
<body>
	

	<div id="top_logo">
		<img alt="" src="${ctx }/static/images/monitor/top_logo.png">
		<div id="top_time">
		<div id="time">
		
		</div>
		<div id="date"></div>
		</div>
	</div>
	<div id="top_title">
		<div id="title">

			<div class="title_icon">
				<img alt="" src="${ctx }/static/images/monitor/login_user.png">
			</div>
			<div id="login_user_icon">
				当前用户：
				<shiro:principal property="name" />
			</div>
			<a href="javascript:void(0)" onclick="loadMain()">
				<div class="title_icon">
					<img alt=""
						src="${ctx }/static/plugins/easyui/jquery-easyui-theme/icons/home_page.png">
				</div>
				<div id="gomain">&nbsp;返回主页</div>
			</a>
			<a href="javascript:void(0)" onclick="backSystem()">
				<div class="title_icon">
					<img alt=""
						src="${ctx }/static/plugins/easyui/jquery-easyui-theme/icons/back.png">
				</div>
				
				<div id="login_user_icon">&nbsp;返回后台</div>
			</a> <a href="javascript:void(0)" onclick="logout()">
				<div class="title_icon">
					<img alt="" src="${ctx }/static/images/Exit-32x32.png" width="14px"
						height="14px" style="margin: 1px; margin-right: 4px;">
				</div>
				<div id="login_user_icon">退出系统</div>
			</a>
		</div>

	</div>
	<div id="content" style="width: 100%;">
		
	</div>
	<!-- 按钮触发模态框 -->

	<!-- 模态框（Modal） -->
	<div class="modal fade" id="loginConfirm" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog" style="width: 350px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">x</button>
					提示
				</div>
				<div class="modal-body">您确定要退出该系统吗？</div>
				<div class="modal-footer">
					<button type="button" class="btn" id="logoutOk">确认</button>
					<button type="button" class="btn" id="logoutCancel">取消</button>
				</div>
			</div>
		</div>

	</div> 
	<div id="bottom">
	<div
				style="color: #4e5766; margin: 0px auto; text-align: center; font-size: 12px; font-family: 微软雅黑;">
				<p style="color: black; margin: 2px 0;">Version:&nbsp;&nbsp;<span
						style="font-weight: 900;">SK_DHRS_NX0100</span></p>
				<p style="color: black; margin: 2px 0;">
				
					版权所有©四川阿特申商贸有限公司<span style="padding: 0 10px">|</span>成都申控物联科技有限公司
				</p>
			</div>
	</div>

</body>
</html>