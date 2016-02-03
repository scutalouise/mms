<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>UPS状态信</title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script type="text/javascript" src="${ctx }/static/js/upsStatus.js"></script>
<script type="text/javascript" src="${ctx}/static/js/upsStatusList.js"></script>

<style type="text/css">
html, body {
	height: 100%;
	margin: 0px;
}
</style>

</head>
<body>

	<div class="easyui-layout" data-options="fit: true">

		<div
			data-options="region: 'west', title: '主机机构', iconCls: 'icon-hamburg-world', split: true, minWidth: 200, maxWidth: 400"
			style="width: 220px; padding: 1px;">
			<ul id="organizationTree"></ul>
		</div>
		<div data-options="region: 'center'" style="overflow:hidden">

			
				<iframe frameborder="0" id="deviceList" style="margin: 0px;"
					width="100%" height="100%" src="${ctx }/upsStatus/gitInfoView"></iframe>

			
		</div>
	</div>








</body>
</html>