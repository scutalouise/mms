<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'west',title:'组织机构',iconCls:'icon-hamburg-world',split:true, minWidth: 200, maxWidth: 400,width:220">
		<ul id="organizationTree"></ul>
	</div>
	<div data-options="region:'center'">
		<!------------------------------- Tabs列表 ------------------------------->	
		<div id="tab_devices" class="easyui-tabs" data-options="refreshable:false,tabWidth:112,tabHeight:50,fit:true">
			<div id="host" title="主机设备"  data-options="refreshable:false,iconCls:'',border:false,href:'${ctx }/device/hostDevice'"></div>
			<div id="network" title="网络设备" data-options="refreshable:false,iconCls:'',border:false,href:'${ctx }/device/networkDevice'"></div>
			<div id="unintelligent" title="非智能设备" data-options="refreshable:false,iconCls:'',border:false,href:'${ctx }/device/unintelligentDevice'"></div>
			<div id="collector" title="采集设备" data-options="refreshable:false,iconCls:'',border:false,href:'${ctx }/device/collectionDevice'"></div>
			<div id="pe" title="动环设备" data-options="refreshable:false,iconCls:'',border:false,href:'${ctx }/device/peDevice'"></div>
		</div>
	</div>
</div>
<script type="text/javascript">
var	 tabIndex = 0;
var  orgId = '';
	$(function(){
		$("#organizationTree").tree(
				{
					method : "get",
					url : "${ctx}/system/organization/tree",
					onBeforeExpand : function(node, params) {
						$(this).tree("options").url = "${ctx}/system/organization/tree?pid=" + node.id
					},
					onSelect : function(node) {
						//当选中了一个tab里的组织时，查询出对应的tab,对应的组织包含的相应的设备；
						orgId = node.id;
						$("#tab_devices").tabs("refresh",tabIndex);//刷新下
					}
				});
  		$("#tab_devices").tabs({
  				onSelect : function(title,index){
  					tabIndex = index;
  			}
  		});
	})
</script>

</body>
</html>