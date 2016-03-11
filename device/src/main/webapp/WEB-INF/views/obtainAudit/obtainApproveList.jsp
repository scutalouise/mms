<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设备领用审批</title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div class="easyui-tabs" id="tab_devices" data-options="tabWidth:112,tabHeight:50,fit:true">
				<div id="host" title="主机设备" data-options="refreshable:false,iconCls:'',border:false,href:'${ctx }/device/obtain/auditHostDeviceList?type=${param.type }'"></div>
				<div id="network" title="网络设备" data-options="refreshable:false,iconCls:'',border:false,href:'${ctx }/device/obtain/auditNetWorkDeviceList?type=${param.type }'"></div>
				<div id="unintelligent" title="非智能设备" data-options="refreshable:false,iconCls:'',border:false,href:'${ctx }/device/obtain/auditUnintelligentDeviceList?type=${param.type }'"></div>
				<div id="collection" title="采集设备" data-options="refreshable:false,iconCls:'',border:false,href:'${ctx }/device/obtain/auditCollectionDeviceList?type=${param.type }'"></div>
				<div id="pe" title="动环设备" data-options="refreshable:false,iconCls:'',border:false,href:'${ctx }/device/obtain/auditPeDeviceList?type=${param.type }'"></div>
			</div>
	</div>
</body>
</html>