var dg;
var d;
$(function() {
	dg = $("#dg").datagrid({
		method : "post",
		url : ctx + "/system/dischargeLog/json",
		queryParams : {
			filter_EQI_status : 0
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
			field : "device",
			title : "UPS名称",
			sortable : true,
			width : 60,
			formatter : function(v) {

				return v.name;
			}
		}, {
			field : "organizationName",
			title : "所属网点",
			sortable : true,
			width : 60,
			formatter : function(v, row) {

				return row.device.gitInfo.organizationName;
			}
		}, {
			field : "dischargeDate",
			title : "放电时间",
			width : 60,
			sortable : true,
			formatter : function(v) {
				return formatDate(v, "yyyy-MM-dd HH:mm:ss");
			}
		}, {
			field : "dischargeType",
			title : "放电类型",
			width : 60,
			sortable : true,
			align:"center",
			formatter : function(v) {
				
				if (v =="AUTOMATIC" ) {
					return "自动";
				} else {
					return "手动";
				}
			}
		}, {
			field : "userName",
			title : "放电人员",
			width : 60,
			sortable : true,
			align:"center",
			formatter : function(v) {
				if(v!=null){
					return v;
				}else{
					return "系统自动放电"
				}
			}
		} ] ],
		headerContextMenu : [ {
			text : "冻结该列",
			disabled : function(e, field) {
				return dg.datagrid("getColumnFields", true).contains(field);
			},
			handler : function(e, field) {
				dg.datagrid("freezeColumn", field);
			}
		}, {
			text : "取消冻结该列",
			disabled : function(e, field) {
				return dg.datagrid("getColumnFields", false).contains(field);
			},
			handler : function(e, field) {
				dg.datagrid("unfreezeColumn", field);
			}
		} ],
		enableHeaderClickMenu : true,
		enableHeaderContextMenu : true,
		enableRowContextMenu : false,
		toolbar : '#tb'
	});
});
function del(){
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
				url : ctx + "/system/dischargeLog/delete",
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