<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>放电计划列表</title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js"
	type="text/javascript"></script>

<script type="text/javascript"
	src="${ctx }/static/js/dischargeTask/dischargeTaskList.js"></script>
<style type="text/css">
.formTable td {
	line-height: 20px;
}

.formTable {
	margin: 0px;
	padding: 10px;
}
</style>
</head>
<body>
	<div id="tb" style="padding: 5px; height: auto;">
		<shiro:hasPermission name="sys:dischargeTask:add">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add()">添加</a> 
			<span class="toolbar-item dialog-tool-separator"></span> 
		</shiro:hasPermission>
		<shiro:hasPermission name="sys:dischargeTask:delScheduleJob">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-options="disabled:false" onclick="del()">删除</a> 
			<span class="toolbar-item dialog-tool-separator"></span> 
		</shiro:hasPermission>
		<shiro:hasPermission name="sys:dischargeTask:update">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd()">修改</a> 
			<span class="toolbar-item dialog-tool-separator"></span> 
		</shiro:hasPermission>
		<shiro:hasPermission name="sys:dischargeTask:startDischargeTask">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-clock" plain="true" onclick="startDischargeTask();">启动放电计划</a>
			<span class="toolbar-item dialog-tool-separator"></span>
		</shiro:hasPermission>
		<shiro:hasPermission name="sys:dischargeTask:stopDischargeTask">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-clock-pause" plain="true" onclick="stopDischargeTask();">暂停放电计划</a>
			<span class="toolbar-item dialog-tool-separator"></span>
		</shiro:hasPermission>
		<shiro:hasPermission name="sys:dischargeTask:selectedUpsDeviceList">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-server" plain="true" onclick="selectDischargeTask();">选择放电设备</a>
			<span class="toolbar-item dialog-tool-separator"></span>
		</shiro:hasPermission>
	</div>


	<table id="dg"></table>
	<div id="dlg"></div>
	<div id="ups_dlg"></div>
</body>
</html>