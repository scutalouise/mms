<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>UPS放电日志</title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script type="text/javascript" src="${ctx }/static/js/log/dischargeLog.js"></script>
</head>
<body>
	<div id="tb" style="padding:5px;height: auto;">
		<shiro:hasPermission name="sys:dischargeLog:delete">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a> 
			<span class="toolbar-item dialog-tool-separator"></span> 
		</shiro:hasPermission>
	</div>
	<table id="dg"></table>
	<div id="dlg"></div>
</body>
</html>