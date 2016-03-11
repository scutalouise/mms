<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>业务告警条件</title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>
<body>
<div id="condition_tb">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" id="condition_add" plain="true" data-options="disabled:true" onclick="addCondition()">
		添加
	</a>
	<span class="toolbar-item dialog-tool-separator"></span> 
	
	
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" id="condition_del" plain="true" data-options="disabled:true" onclick="delCondition()">
		删除
	</a>
	<span class="toolbar-item dialog-tool-separator"></span>
	
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" id="condition_update" plain="true" data-options="disabled:true"  onclick="updateCondition()">
		修改
	</a>
	<span class="toolbar-item dialog-tool-separator"></span> 
	
</div>

<div id="condition_dg"></div>

<div id="condition_dlg"></div>
<script type="text/javascript">
	var condition_dg;
	var condition_dlg;
	var checkedConditionName;
	var checkedConditionId;
	$(function(){
		
		condition_dg=$("#condition_dg").datagrid({
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
			columns : [[
			           {
			        	   field : 'id',
							checkbox : true  
			           } ,{
			        	   field:"name",
							title:"条件名称",
							sortable:true,
							width:100
			           } ,{
							field:"alarmDevice",
							title:"告警设备",
							sortable:true,
							width:100,
							formatter:function(v){
								return v.name;
							}
						},{
							field:"alarmLevel",
							title:"告警等级",
							width:100,
							sortable:true,formatter:function(v){
								return v.name;
							}
						},{
							field : "enabled",
							title : "是否启用",
							sortable : true,
							width : 100,
							align:"center",
							formatter : function(value) {
								if (value == 0) {
									return "启用";
								} else {
									return "禁用";
								}
							}
						}
			        ]],
			        enableHeaderClickMenu : true,
					enableHeaderContextMenu : true,
					enableRowContextMenu : false,
					toolbar : '#condition_tb',
					onSelect:function(rowIndex,rowData){
						$("#condition_del").linkbutton('enable');   
						$("#condition_update").linkbutton("enable");
						$("#rule_add").linkbutton("enable");
						checkedConditionName=rowData.name;
						checkedConditionId=rowData.id;
						deviceRule_dg.datagrid("options").url=ctx+"/system/deviceAlarmRule/json",
						deviceRule_dg.datagrid("reload",{
							"filter_EQI_deviceAlarmCondition.id" : rowData.id,
							filter_EQ_StatusEnum_status : 'NORMAL'
						})

					}
		});
	});
	function appendAndRemoveHost(divId){
		$("#" +divId + "").dialog("destroy").remove(); //直接摧毁、移除
		$("<div id='"+ divId +"'></div> ").appendTo($('body'))//新加入一个
	}
	function addCondition(){
		
		var row = template_dg.datagrid("getSelected");
		condition_dlg=$("#condition_dlg").dialog({
			title : "添加报警条件",
			width : 350,
			height : 330,
			href : ctx + "/system/deviceAlarmCondition/addForm",
			maximizable : true,
			modal : true,
			onClose:function(){
		    	appendAndRemoveDialog("condition_dlg");
		    },
			buttons : [ {
				text : "确认",
				handler : function() {
					$("#deviceAlarmCondition_mainForm").submit();
				}
			}, {
				text : '取消',
				handler : function() {
					condition_dlg.panel('close');
				}
			} ]
		});
	}
	function updateCondition(){
		var row=condition_dg.datagrid("getSelected");
		condition_dlg=$("#condition_dlg").dialog({
			title:"添加业务告警条件",
			width : 350,
			height : 330,
			href:ctx+"/system/deviceAlarmCondition/updateForm/"+row.id,
			maximizable:true,
			modal:true,
			buttons:[{
				text:"确认",
				handler:function(){
					$("#deviceAlarmCondition_mainForm").submit();
				}
			},{
				text:"取消",
				handler:function(){
					condition_dlg.panel("close");
				}
			}]
		});
	}
	function delCondition(){
		var data=$("#condition_dg").datagrid("getSelections");
		var deviceAlarmConditionIdList=[];
		
		
		
		
		for(var i=0;i<data.length;i++){
			
			deviceAlarmConditionIdList.push(data[i].id);
		}
		
		parent.$.messager.confirm('提示', '删除后无法恢复，您确定要删除？', function(data) {

			if (data) {
				$.ajax({
					type : 'post',
					data:JSON.stringify(deviceAlarmConditionIdList),
					contentType:'application/json;charset=utf-8',	//必须
					url : ctx + "/system/deviceAlarmCondition/delete",
					
					success : function(data) {
						successTip(data, condition_dg);
						$('#condition_dg').datagrid('clearSelections'); 
					}
				});
			}
		});
	}
</script>
</body>
</html>