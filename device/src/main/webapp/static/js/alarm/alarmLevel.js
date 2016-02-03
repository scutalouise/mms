var dg;
var d;
$(function() {
	dg = $("#dg").datagrid({
		method : "post",
		url : ctx + "/system/alarmLevel/json",
		queryParams : {
			filter_EQE_status : 'NORMAL'
		},

		fit : true,
		fitColumns : true,
		border : false,
		idField : "id",
		striped : true,
		pagination : true,
		rownumbers : true,
		pageNumber : 1,
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50 ],
		singleSelect : false,
		columns : [ [ {
			field : 'id',
			checkbox : true
		}, {
			field : "name",
			title : "名称",
			width : 100

		}, {
			field : "alarmSort",
			title : "告警等级序号",
			align : "center",
			width : 50
		}, {
			field : "isEmail",
			title : "是否邮件报警",
			align : "center",
			width : 50,
			formatter : function(v) {
				
				if (v == "YES") {
					return "是";
				} else {
					return "否";
				}
			}
		}, {
			field : "isSms",
			title : "是否短信报警",
			align : "center",
			width : 50,
			formatter : function(v) {
				if (v == "YES") {
					return "是";
				} else {
					return "否";
				}
			}
		}, {
			field : "isSound",
			title : "是否声音报警",
			align : "center",
			width : 50,
			formatter : function(v) {
				if (v == "YES") {
					return "是";
				} else {
					return "否";
				}
			}
		}, {
			field : "enabled",
			title : "状态",
			align : "center",
			width : 50,
			formatter : function(v) {
				if (v == "ENABLED") {
					return "启用"
				} else {
					return "禁用"
				}
			}
		}, {
			field : "remark",
			title : "描述",
			width : 200
		} ] ],
		enableHeaderClickMenu : true,
		enableHeaderContextMenu : true,
		enableRowContextMenu : false,
		toolbar : '#tb'
	});

});

function add() {
	d = $("#dlg").dialog({
		title : "添加报警等级",
		width : 590,
		height : 286,
		href : ctx + "/system/alarmLevel/addForm",
		maximizable : true,
		buttons : [ {
			text : '确认',
			handler : function() {
				$("#mainform").submit();
			}
		}, {
			text : '取消',
			handler : function() {
				d.panel('close');
			}
		} ]
	});
}
function del() {
	var ids = "";
	var rows = $('#dg').datagrid('getSelections');
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
				url : ctx + "/system/alarmLevel/delete",
				data : {
					ids : ids
				},
				success : function(data) {
					successTip(data, dg);
					$('#dg').datagrid('clearSelections');
				}
			});
		}
	});

}
function upd() {
	var row = dg.datagrid("getSelected");
	var rows = $('#dg').datagrid('getSelections');
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
	d = $("#dlg").dialog({
		title : '修改报警等级',
		width : 590,
		height : 286,
		href : ctx + '/system/alarmLevel/updateForm/' + row.id,
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
				d.panel('close');
			}
		} ]
	});
}