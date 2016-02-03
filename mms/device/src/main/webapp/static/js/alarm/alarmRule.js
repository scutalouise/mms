/**
 * 添加规则
 */
function addRule(){
	
	var row = condition_dg.datagrid("getSelected");
	var rows = $('#condition_dg').datagrid('getSelections');
	if (rows.length < 1) {
		rowIsNull(null);
		return;
	} else if (rows.length > 1) {
		parent.$.messager.show({
			title : "提示",
			msg : "只能选择一条记录！",
			position : "bottomRight"
		});
		return;
	}
	if(rule_d==undefined){
	rule_d=$("#rule_dlg").dialog({
		title:"添加报警规则",
		width:548,
		height : "auto",
		id : "dlgContent",
		href:ctx+"/system/alarmRule/addForm?alarmOptionType="+checkedAlarmOptionType.alarmOptionType,
		maximizable:true,
		modal:true,
		buttons:[{
			text:"确认",
			handler:function(){
				
				$("#rule_mainform").submit();
			}
		},{
			text : '取消',
			handler : function() {
				rule_d.panel('close');
			}
		}]
	});
	}else{
		rule_d.dialog("refresh",ctx+"/system/alarmRule/addForm?alarmOptionType="+checkedAlarmOptionType.alarmOptionType);
		rule_d.dialog("open");
	}
}
function updateRule(){
	var row=rule_dg.datagrid("getSelected");
	var rows = $('#rule_dg').datagrid('getSelections');
	if (rows.length < 1) {
		rowIsNull(null);
		return;
	} else if (rows.length > 1) {
		parent.$.messager.show({
			title : "提示",
			msg : "只能选择一条记录！",
			position : "bottomRight"
		});
		return;
	}
	if(rule_d==undefined){
	rule_d=$("#rule_dlg").dialog({
		title:"修改报警规则",
		width:548,
		height : "auto",
		id : "dlgContent",
		href:ctx+"/system/alarmRule/updateForm/"+row.id+"/"+checkedAlarmOptionType.alarmOptionType,
		maximizable:true,
		modal:true,
		buttons:[{
			text:"确认",
			handler:function(){
				
				$("#rule_mainform").submit();
			}
		},{
			text : '取消',
			handler : function() {
				rule_d.panel('close');
			}
		}]
	});
	}else{
		rule_d.dialog("refresh",ctx+"/system/alarmRule/updateForm/"+row.id+"/"+checkedAlarmOptionType.alarmOptionType);
		rule_d.dialog("open");
	}
}
function delRule(){

	var ids="";
	var rows = $('#rule_dg').datagrid('getSelections');
	if (rows.length < 1) {
		rowIsNull(null);
		return;
	}
	parent.$.messager.confirm('提示', '删除后无法恢复，您确定要删除？', function(data) {

		if (data) {
			for (var i = 0; i < rows.length; i++) {
				var row = rows[i];
				if (i < rows.length - 1) {
					ids += "'" + row.id + "',";
				} else {
					ids += "'" + row.id + "'"
				}
			}
			$.ajax({
				type : 'get',
				url : ctx + "/system/alarmRule/delete",
				data : {
					ids : ids
				},
				success : function(data) {
					successTip(data, rule_dg);
					$('#rule_dg').datagrid('clearSelections');
				}
			});
		}
	});
}