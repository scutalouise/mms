<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设备选项卡</title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script type="text/javascript">
	$(function() {

	});
</script>
</head>
<body>
	<div id="tab-tools">
		<a href="javascript:void(0)" class="easyui-linkbutton" style=""
			data-options="plain:true,iconCls:'icon-back'"
			onclick="history.go(-1)">返回</a>
	</div>
	<div class="easyui-tabs" id="tab_device"
		data-options="fit:true,tools:'#tab-tools',border:false">
		<c:forEach items="${upsDeviceList }" var="device">
			<div title="【UPS-${device.deviceIndex }】" iconCls=""
				data-options="refreshable:false,content:'<iframe src=\'${ctx }/chart/upsChart?deviceId=${device.id }\'  height=\'100%\' width=\'100%\' frameborder=0></iframe>'">
			</div>
		</c:forEach>
		<c:forEach items="${thDeviceList }" var="device">
		   <div title="【TH-${device.deviceIndex }】" iconCls="" data-options="refreshable:false,content:'<iframe src=\'${ctx }/chart/thChart?deviceId=${device.id }\'  height=\'100%\' width=\'100%\' frameborder=0></iframe>'">
		   
		   </div>
		</c:forEach>
	</div>
</body>
</html>