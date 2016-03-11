<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设备领用</title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script type="text/javascript" src="${ctx }/static/js/obtain/obtainDeviceList.js"></script>
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
	<div class="easyui-layout" data-options="fit:true">
		<div
			data-options="region:'west',title:'网点',iconCls:'icon-hamburg-world',split:true, minWidth: 200, maxWidth: 400,width:220">
			<ul id="organizationTree"></ul>
		</div>
		<div data-options="region:'center'">
			<div class="easyui-tabs" id="tab_devices" data-options="tabWidth:112,tabHeight:50,fit:true">
				<div id="host" title="主机设备" data-options="refreshable:false,iconCls:'',border:false,href:'${ctx }/device/obtain/obtainHostDeviceList/organization'"></div>
				<div id="network" title="网络设备" data-options="refreshable:false,iconCls:'',border:false,href:'${ctx }/device/obtain/obtainNetWorkDeviceList/organization'"></div>
				<div id="unintelligent" title="非智能设备" data-options="refreshable:false,iconCls:'',border:false,href:'${ctx}/device/obtain/obtainUnintelligentDeviceList/organization'"></div>
				<div id="collection" title="采集设备" data-options="refreshable:false,iconCls:'',border:false,href:'${ctx }/device/obtain/obtainCollectionDeviceList/organization'"></div>
				<div id="pe" title="动环设备" data-options="refreshable:false,iconCls:'',border:false,href:'${ctx }/device/obtain/obtainPeDeviceList/organization'"></div>
			</div>
			
		</div>
	</div>

</body>
</html>