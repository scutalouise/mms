var dg;
$(function() {
	dg = $("#dg").datagrid({
		method : "get",
		url : ctx + "/upsStatus/upsStatusList",
		queryParams : {
			filter_EQI_status : '1'
		},
		fit : true,
		fitColumns : true,
		border : true,
		idField : "id",
		striped : true,
		pagination : true,
		rownumbers : true,
		pageNumber : 1,
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50 ],
		singleSelect : false,
		columns : [ [ {
			field : "id",
			checkbox : true

		}, {
			field : "name",
			title : "设备名称",
			sortable : true,
			width : 50
		}, {
			field : "interfaceType",
			title : "接口类型",
			sortable : true,
			width : 50
		} ] ],
		enableHeaderClickMenu : true,
		enableHeaderContextMenu : true,
		enableRowContextMenu : false,
		toolbar : '#tb'

	});

});