<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>UPS历史记录</title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script type="text/javascript" src="${ctx}/static/js/upsStatusList.js"></script>
<style type="text/css">
html, body {
	height: 100%;
	margin: 0px;
}

</style>
</head>
<body style="margin: 0px">
	<div id="tb">
		<div>
			<a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-remove" plain="true" data-options="disabled:false"
				onclick="del()">删除</a> <span
				class="toolbar-item dialog-tool-separator"></span> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true" onclick="view()">查看</a>
		</div>
	</div>
	<div id="dg"></div>
	<div id="dlg"></div>
</body>
</html>