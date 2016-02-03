<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>
<body>
<div id="selected_tb">
<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-add" plain="true" onclick="addUpsDevice()">添加</a> <span
			class="toolbar-item dialog-tool-separator"></span> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-remove" plain="true" data-options="disabled:false" onclick="removeSelectedDevice()"
			>删除</a> <span
			class="toolbar-item dialog-tool-separator"></span>
</div>
<table id="selected_dg"></table>
<div id="selected_dlg"></div>
<script type="text/javascript" src="${ctx }/static/js/dischargeTask/selectedUpsDeviceList.js">

</script>
</body>
</html>