var unselected_dg;
var unselected_dlg;
$(function() {

	unselected_dg = $("#unselected_dg").datagrid({
		method : "post",
		fit : true,
		url : ctx + "/device/upsDeviceJson",
		queryParams : {
			scheduleName : scheduleName,
			scheduleGroup : scheduleGroup,
			type : 1
		},
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
		fit : true,
		columns : [ [ {
			field : 'id',
			checkbox : true
		}, {
			field : "organizationName",
			title : "所属机构",
			sortable : true,
			width : 40,
			formatter : function(value, rowData) {
				return rowData.gitInfo.organizationName;
			}
		}, {
			field : "gitInfo",
			title : "所属主机名称",
			sortable : true,
			formatter : function(value, rowData, rowIndex) {

				return value.name
			}
		}, {
			field : "gitInfo",
			title : "所属主机IP",
			sortable : true,
			width : 40,
			formatter : function(value, rowData, rowIndex) {

				return value.ip
			}
		}, {
			field : "name",
			title : "设备名称",
			sortable : true,
			width : 50

		} ] ],
		enableHeaderClickMenu : false,
		enableHeaderContextMenu : false,
		enableRowContextMenu : false,
		//toolbar : '#unselected_tb',
		dataPlain : true
	});
});
function selectDevice() { 
	var ids = "";
	var rows = $('#unselected_dg').datagrid('getSelections');

	if (rows.length < 1) {
		rowIsNull(null);
		return;
	}
	parent.$.messager.confirm('提示', '确定要将选中的设备添加到此放电计划中吗？', function(data) {

		if (data) {
			var deviceIds=new Array();
			
			for (var i = 0; i < rows.length; i++) {
				deviceIds[i]=rows[i].id;
				
			}
		
			$.ajax({
				type : 'post',
				url : ctx + "/system/dischargeTask/addSelectDevice",
				data : {
					deviceIds : deviceIds,
					scheduleName:scheduleName,
					scheduleGroup:scheduleGroup
				},
				success : function(data) {
					successTip(data, unselected_dg);
					selected_dlg.panel("close");
					$('#unselected_dg').datagrid("clearSelections");
					successTip(data, selected_dg);
				}
			});
		}
	});

}
