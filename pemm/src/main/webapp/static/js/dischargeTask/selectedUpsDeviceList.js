var selected_dg;
var selected_dlg;
$(function() {

	selected_dg = $("#selected_dg").datagrid({
		method : "post",
		fit : true,
		url : ctx + "/device/upsDeviceJson",
		queryParams : {
			scheduleName : scheduleName,
			scheduleGroup : scheduleGroup,
			type : 0
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
		columns : [ [{
			field:'id',
			checkbox:true
		}, {
			field : "organizationName",
			title : "所属机构",
			sortable : true,
			width : 60,
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

		}  ] ],
		enableHeaderClickMenu : false,
		enableHeaderContextMenu : false,
		enableRowContextMenu : false,
		toolbar : '#selected_tb',
		dataPlain : true
	});
});
function addUpsDevice() {
	if (selected_dlg == undefined) {
		selected_dlg = $("#selected_dlg").dialog({
			title : "选择放电设备",
			href : ctx + "/system/dischargeTask/unSelectedUpsDeviceList",
			width : 500,
			modal : true,
			height : 300,
			buttons:[ {
				text : "确认",
				handler : function() {
					selectDevice();
				}
					
			},{
				text:"取消",handler:function(){
					selected_dlg.panel('close');
				}
			}]

		});
	} else {
		selected_dlg.dialog("open");
	}
}
function removeSelectedDevice() {
	
	var rows = $('#selected_dg').datagrid('getSelections');

	if (rows.length < 1) {
		rowIsNull(null);
		return;
	}
	parent.$.messager.confirm('提示', '确定要将选中的设备从此放电计划中移除？', function(data) {
		if (data) {
			var deviceIds = new Array();

			for (var i = 0; i < rows.length; i++) {
				deviceIds[i] = rows[i].id;

			}
		
			$.ajax({
				type:"post",
				url : ctx + "/system/dischargeTask/delSelectDevice",
				data : {
					deviceIds : deviceIds+""
				},
				success:function(data){
					$('#selected_dg').datagrid("clearSelections");
					successTip(data, selected_dg);
					successTip(data, unselected_dg);
					
				}
			});
		}

	});
}