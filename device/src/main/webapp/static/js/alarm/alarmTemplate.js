var checkedTemplateName;
var checkedTemplateId;
var template_dg;
var template_d;
$(function() {

	var north_height = $(document).height() / 2;
	$("#right_panel").layout("add", {
		region : "north",
		height : north_height,
		title : "报警条件",
		split : true,
		href : ctx + "/system/alarmCondition"
	});
	$("#right_panel").layout("add", {
		title : "报警规则",
		region : "center",
		href : ctx + "/system/alarmRule"
	});

	template_dg = $("#template_dg").datagrid({
		method : "post",
		url : ctx + "/system/alarmTemplate/json",
		queryParams : {
			filter_EQ_StatusEnum_status : 'NORMAL',
			filter_EQ_TemplateTypeEnum_templateType:"MONITORINGALARM" //告警类型为监控告警
		},
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
			checkbox : true//,
			//hidden:true
		}, {
			field : "name",
			title : "模板名称",
			sortable : true,
			width : 50
		}, {
			field : "enabled",
			title : "是否启用",
			sortable : true,
			width : 30,formatter:function(v){
				if(v=="ENABLED"){
					return "启用";
				}else{
					return "禁用";
				}
			}
		}, {
			field : "remark",
			title : "描述",
			sortable : true,
			width : 100
		} ] ],
		enableHeaderClickMenu : true,
		enableHeaderContextMenu : true,
		enableRowContextMenu : false,
		toolbar : '#template_tb',
		onSelect:function(rowIndex, rowData){
			checkedTemplateName = rowData.name;
			checkedTemplateId = rowData.id;
			condition_dg.datagrid("options").url=ctx + "/system/alarmCondition/json";
			condition_dg.datagrid("reload",{
				status : 'NORMAL',
				alarmTemplateId:rowData.id
			});
		}
	});
});
function addTemplate() {

	template_d = $("#template_dlg").dialog({
		title : "添加报警模板",
		width : 400,
		height : 250,
		href : ctx + "/system/alarmTemplate/addForm/MONITORINGALARM",
		maximizable : true,
		modal : true,
		buttons : [ {
			text : "确认",
			handler : function() {
				$("#mainform").submit();
			}
		}, {
			text : '取消',
			handler : function() {
				template_d.panel('close');
			}
		} ]
	});
}
function updateTemplate() {
	var row = template_dg.datagrid("getSelected");
	var rows = $('#template_dg').datagrid('getSelections');
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
	template_d = $("#template_dlg").dialog({
		title : "修改报警模板",
		width : 400,
		height : 250,
		href : ctx + "/system/alarmTemplate/updateForm/"+row.id,
		maximizable : true,
		modal : true,
		buttons : [ {
			text : "确认",
			handler : function() {
				$("#mainform").submit();
			}
		}, {
			text : '取消',
			handler : function() {
				template_d.panel('close');
			}
		} ]
	})
}

function delTemplate(){
	var ids = "";
	var rows = $('#template_dg').datagrid('getSelections');
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
				url : ctx + "/system/alarmTemplate/delete",
				data : {
					ids : ids
				},
				success : function(data) {
					successTip(data, template_dg);
					$('#template_dg').datagrid('clearSelections'); 
				}
			});
		}
	});
}