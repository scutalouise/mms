<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设备监控管理</title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js"
	type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/static/js/deviceList.js"></script>
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
<body style="margin: 0px;">
	<div class="easyui-layout" data-options="fit: true">

		<div
			data-options="region: 'west', title: '组织机构', iconCls: 'icon-hamburg-world', split: true, minWidth: 200, maxWidth: 400"
			style="width: 220px; padding: 1px;">
			<!-- 区域树 -->
			<ul id="organizationTree"></ul>

		</div>
		<div data-options="region: 'center'">
			<div id="tb">
				<div>

					<shiro:hasPermission name="sys:device:add">
						<a href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-search" plain="true" onclick="scan();">扫描</a>
						<span class="toolbar-item dialog-tool-separator"></span>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:device:delete">
						<a href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-remove" plain="true" data-options="disabled:false"
							onclick="del()">删除</a>
						<span class="toolbar-item dialog-tool-separator"></span>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:device:update">
						<a href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-edit" plain="true" onclick="update()">修改</a>
					</shiro:hasPermission>
				</div>

			</div>
			
			<div id="dg">
				
			</div>
			<div id="dlg"></div>
		</div>
	</div>

</body>
</html>