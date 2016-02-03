<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<title>告警等级管理</title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script type="text/javascript"
	src="${ctx }/static/js/alarmLevel/alarmLevel.js"></script>
<style type="text/css">
.formTable td {
	line-height: 30px;
}

.formTable {
	margin: 0px;
	padding: 10px;
}
</style>
</head>
<body>
	<div id="tb" style="padding: 5px; height: auto">
		<shiro:hasPermission name="sys:alarmLevel:add">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">添加</a> 
			<span class="toolbar-item dialog-tool-separator"></span> 
		</shiro:hasPermission>
		<shiro:hasPermission name="sys:alarmLevel:delete">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-options="disabled:false" onclick="del()">删除</a> 
			<span class="toolbar-item dialog-tool-separator"></span> 
		</shiro:hasPermission>
		<shiro:hasPermission name="sys:alarmLevel:update">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd()">修改</a> 
			<span class="toolbar-item dialog-tool-separator"></span>
		</shiro:hasPermission>
	</div>
	<table id="dg"></table>
	<div id="dlg"></div>
</body>
</html>