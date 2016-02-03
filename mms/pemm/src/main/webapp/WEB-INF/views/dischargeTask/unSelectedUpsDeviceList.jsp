<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>
<body>
<!-- <div id="unselected_tb">
<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-add" plain="true" onclick="selectDevice()">选择</a> <span
			class="toolbar-item dialog-tool-separator"></span> 
</div> -->
<table id="unselected_dg"></table>
<div id="unselected_dlg"></div>
<script type="text/javascript" src="${ctx }/static/js/dischargeTask/unSelectedUpsDeviceList.js">

</script>
</body>
</html>