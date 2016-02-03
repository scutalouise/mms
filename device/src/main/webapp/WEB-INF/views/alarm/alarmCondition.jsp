<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>告警条件</title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>
<body>
	<div id="condition_tb">
		<div>
			<shiro:hasPermission name="sys:alarmCondition:add">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addCondition()">
					<a href="javascript:addCondition()" class="condition_a">添加</a>
				</a>
				<span class="toolbar-item dialog-tool-separator"></span> 
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:alarmCondition:delete">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-options="disabled:false" onclick="delCondition()">
					<a href="javascript:delCondition()" class="condition_a">删除</a>
				</a>
				<span class="toolbar-item dialog-tool-separator"></span> 
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:alarmCondition:update">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updateCondition()">
					<a href="javascript:updateCondition()" class="condition_a">修改</a>
				</a>
				<span class="toolbar-item dialog-tool-separator"></span> 
			</shiro:hasPermission>
		</div>
	</div>
	<div id="condition_dg"></div>
	<div id="condition_dlg"></div>
	<script type="text/javascript">
		var checkedConditionName;
		var checkedConditionId;
		var checkedAlarmOptionType;
		var checkedConditionData;
		var condition_dg;
		var condition_d;
		$(function() {
			condition_dg = $("#condition_dg").datagrid({
				method : "post",
				fit : true,
				fitColumns : true,
				border : false,
				idField : "id",
				sriped : true,
				pagination : true,
				rownumbers : true,
				pageNumber : 1,
				pageSize : 20,
				pageList : [ 10, 20, 30, 40, 50 ],
				singleSelect : true,
				columns : [ [ {
					field : 'id',
					checkbox : true//,hidden:true
				},{
					field:"name",title:"条件名称",
					sortable:true,
					width:10
				}, {
					field : "alarmDeviceType",
					title : "设备类型",
					sortable : true,
					width : 10,
					formatter:function(v){
						if(v!=null){
							return v.name;
						}
					}
				}, {
					field : "alarmOptionType",
					title : "告警操作类型",
					sortable : true,
					width : 10,
					formatter:function(v,record){
						
						return v.name;
						
					}
				}, {
					field : "alarmTemplate",
					title : "所属模板",
					sortable : true,
					width : 10,
					formatter:function(v){
						return v.name;
					}
				}, {
					field : "alarmLevel",
					title : "报警等级",
					sortable : true,
					width : 10,
					formatter : function(v) {
						return v.name;
					}
				}, {
					field : "stayTime",
					title : "异常持续报警时间",
					sortable : true,
					width : 10
				}, {
					field : "repeatCount",
					title : "重复报警次数",
					sortable : true,
					width : 10
				}, {
					field : "intervalTime",
					title : "重复报警间隔时间",
					sortable : true,
					width : 10
				}, {
					field : "noticeAfter",
					title : "报警消失后是否通知",
					sortable : true,
					width : 10,
					align:"center",
					formatter : function(value) {
						if (value == 0) {
							return "是";
						} else {
							return "否";
						}
					}
				}, {
					field : "enabled",
					title : "是否启用",
					sortable : true,
					width : 10,
					align:"center",
					formatter : function(value) {
						if (value == 0) {
							return "启用";
						} else {
							return "禁用";
						}
					}
				} ] ],
				enableHeaderClickMenu : true,
				enableHeaderContextMenu : true,
				enableRowContextMenu : false,
				toolbar : '#condition_tb',
				onSelect:function(rowIndex,rowData){
					checkedConditionName=rowData.name;
					checkedConditionId=rowData.id;
					checkedAlarmOptionType=rowData.alarmOptionType;
					checkedConditionData=rowData;
					rule_dg.datagrid("options").url=ctx+"/system/alarmRule/json",
					
					rule_dg.datagrid("reload",{
						status : 'NORMAL',
						alarmConditionId:rowData.id
					}); 
				}
			});
		});
		
	</script>
</body>
</html>