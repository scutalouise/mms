<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设备告警规则</title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>
<body>
	<div id="deviceRule_tb">
		<a href="javascript:void(0)" class="easyui-linkbutton" id="rule_add"
			iconCls="icon-add" plain="true" data-options="disabled:true" onclick="addRule()">添加</a> <span
			class="toolbar-item dialog-tool-separator"></span> <a
			href="javascript:void(0)" class="easyui-linkbutton" id="rule_delete"
			iconCls="icon-remove" plain="true" data-options="disabled:true"
			onclick="delRule()">删除</a> <span
			class="toolbar-item dialog-tool-separator"></span>
			<a href="javascript:void(0)" class="easyui-linkbutton" id="rule_update" iconCls="icon-edit" plain="true" data-options="disabled:true" onclick="updateRule()">
					修改
				</a>
				<span
			class="toolbar-item dialog-tool-separator"></span>
	</div>
	<div id="deviceRule_dg"></div>
	<div id="deviceRule_dlg"></div>
	<script type="text/javascript">
		var deviceRule_dg;
		var deviceRule_dlg;
		$(function() {
			deviceRule_dg = $("#deviceRule_dg").datagrid({
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
					checkbox : true
				},{
					field:"deviceAlarmOption",
					title:"告警规则内容",
					width:100,sortable:true,
					formatter:function(v){
						return v.value;
					}
				},{
					field : "value",
					title : "告警内容",
					width : 100,
					formatter : function(v, rowData) {
						var html = rowData.deviceAlarmOption.value;
						if (rowData.operationType == "GT") {
							html += "大于" + v+rowData.alarmOptionUnit.unitValue;
						} else if (rowData.operationType == "EQ") {
							html += "等于" + v+rowData.alarmOptionUnit.unitValue;
						} else if (rowData.operationType == "GE") {
							html += "大于或等于" + v+rowData.alarmOptionUnit.unitValue;
						} else if (rowData.operationType == "LT") {
							html += "小于" + v+rowData.alarmOptionUnit.unitValue;
						} else if (rowData.operationType == "LE") {
							html += "小于或等于" + v+rowData.alarmOptionUnit.unitValue;
						} else if (rowData.operationType == "BELONGTO") {
							html += "在区间["
									+ rowData.minValue+rowData.alarmOptionUnit.unitValue
									+ "-"
									+ rowData.maxValue+rowData.alarmOptionUnit.unitValue
									+ "]之间"
						} else if (rowData.operationType == "NOTBELONGTO") {
							html = "不在区间["
									+ rowData.minValue+rowData.alarmOptionUnit.unitValue
									+ "-"
									+ rowData.maxValue+rowData.alarmOptionUnit.unitValue
									+ "]之间"
						}
						return html;
					}
				}, {
					field : "remark",
					title : "描述",
					sortable : true,
					width : 150
				} ] ],
				enableHeaderClickMenu : true,
				enableHeaderContextMenu : true,
				enableRowContextMenu : false,
				toolbar : '#deviceRule_tb',
				onSelect:function(){
					$("#rule_delete").linkbutton('enable');   
					$("#rule_update").linkbutton("enable");
				}
			});
		});
		function addRule(){
			
			deviceRule_dlg=$("#deviceRule_dlg").dialog({
				title:"添加设备告警规则",
				width:588,
				height : 330,
				href:ctx+"/system/deviceAlarmRule/addForm",
				maximizable:true,
				modal:true,
				buttons:[{
					text:"确认",
					handler:function(){
						$("#deviceAlarmRule_mainForm").submit();
					}
				},{
					text:"取消",
					handler:function(){
						deviceRule_dlg.panel("close");
					}
				}]
			});
		}
		function delRule(){
			//
			var data=$("#deviceRule_dg").datagrid("getSelections");
			var deviceAlarmRuleIdList=[];
			
			
			
			
			for(var i=0;i<data.length;i++){
				
				deviceAlarmRuleIdList.push(data[i].id);
			}
			
			parent.$.messager.confirm('提示', '删除后无法恢复，您确定要删除？', function(data) {

				if (data) {
					$.ajax({
						type : 'post',
						data:JSON.stringify(deviceAlarmRuleIdList),
						contentType:'application/json;charset=utf-8',	//必须
						url : ctx + "/system/deviceAlarmRule/delete",
						
						success : function(data) {
							successTip(data, deviceRule_dg);
							$('#deviceRule_dg').datagrid('clearSelections'); 
						}
					});
				}
			});
		}
		function updateRule(){
			var row=deviceRule_dg.datagrid("getSelected");
			
			deviceRule_dlg=$("#deviceRule_dlg").dialog({
				title:"修改设备告警规则",
				width:588,
				height : 330,
				href:ctx+"/system/deviceAlarmRule/updateForm/"+row.id,
				maximizable:true,
				modal:true,
				buttons:[{
					text:"确认",
					handler:function(){
						$("#deviceAlarmRule_mainForm").submit();
					}
				},{
					text:"取消",
					handler:function(){
						deviceRule_dlg.panel("close");
					}
				}]
			});
		
			
		}
	</script>
</body>
</html>