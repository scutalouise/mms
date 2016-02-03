var dg;
var d;
var organizationId;
$(function() {
	dg = $("#dg").datagrid({
		method : "post",
		url : ctx + "/gitInfo/json",
		queryParams : {
			filter_EQI_status : '0',
			filter_EQI_organizationId : organizationId
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
			field : "organizationName",
			title : "所属机构",sortable:true,
			width:60
		}, {
			field : "name",
			title : "名称",
			sortable : true,

			width : 100
		}, {
			field : "ip",
			title : "IP地址",
			sortable : true,
			align : "center",
			width : 80
		}, {
			field : "managerName",
			title : "管理人员",
			sortable : true,
			align : "center",
			width : 60
		}, {
			field : "vendor",
			title : "厂商",
			sortable : true,
			width : 100
		}, {
			field : "brand",
			title : "品牌",
			sortable : true,
			width : 100
		}, {
			field : "buyTime",
			title : "购买时间",
			sortable : true,
			align : "center",
			width : 70,
			formatter : function(value, rowData, rowIndex) {
				return formatDate(value, "yyyy-MM-dd")
			}
		}, {
			field : "enabled",
			title : "是否启用",
			sortable : true,
			align : "center",
			width : 40,
			formatter : function(value, rowData, rowIndex) {
				var html = "";
				if (value == 1) {
					html = "启用";
				} else {
					html = "禁用";
				}
				return html;
			}
		}, {
			field : "remark",
			title : "描述",
			sortable : true,
			width : 150
		}, {
			field : "detail",
			title : "操作",
			width : 20,
			align : "center",
			formatter : formatter
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
		toolbar : '#tb',
		onLoadSuccess : function() {
			createTooltip();

		}
	});
	// 上级菜单
	$("#organizationTree").tree(
			{
				method : "get",
				url : ctx + "/system/organization/tree",
				onBeforeExpand : function(node, params) {
					$(this).tree("options").url = ctx
							+ "/system/organization/tree?pid=" + node.id
				},
				onSelect : function(node) {
					dg.datagrid("reload", {

						filter_EQI_status : '0',
						filter_EQI_organizationId : node.id
					});
					organizationId = node.id;
				}
			});
	initDateFilter("beginDate", "endDate");
});

function formatter(value, row, index) {
	return '<span data-p='
			+ index
			+ ' class="easyui-tooltip" style="z-index:10000"><a href="javascript:void(0)">详情</a></span>';
}

function createTooltip() {
	dg
			.datagrid('getPanel')
			.find('.easyui-tooltip')
			.each(
					function() {
						var index = parseInt($(this).attr('data-p'));
						$(this)
								.tooltip(
										{
											content : $('<div></div>'),
											onUpdate : function(cc) {
												var row = $('#dg').datagrid(
														'getRows')[index];

												var content = '<div id="tooltiptitle">内容信息</div>';
												content += "<table  cellpadding='5'>"
														+ "<tr>"
														+ "<td class='gitInfo_title'>IP地址：</td><td>"
														+ row.ip
														+ "</td>"
														+ "<td class='gitInfo_title'>名称：</td><td>"
														+ row.name
														+ "</td>"
														+ "<td class='gitInfo_title'>厂商：</td><td>"
														+ row.vendor
														+ "</td>"
														+ "<td class='gitInfo_title'>品牌：</td><td>"
														+ row.brand
														+ "</td>"
														+ "</tr>"
														+ "<tr>"
														+ "<td class='gitInfo_title'>购买时间：</td><td>"
														+ (row.buyTime != null ? formatDate(
																row.buyTime,
																"yyyy-MM-dd")
																: "")
														+ "</td>"
														+ "<td class='gitInfo_title'>所属机构：</td><td>"
														+ row.organizationName
														+ "</td>"
														+

														"<td class='gitInfo_title'>管理员：</td><td>"
														+ row.managerName
														+ "</td>"
														+ "<td class='gitInfo_title'>状态：</td><td>"
														+ (row.enabled == 1 ? "启用"
																: "禁用")
														+ "</td>"
														+ "</tr>"
														+ "<tr>"
														+ "<td class='gitInfo_title'>描述：</td><td colspan='7'>"
														+ row.remark
														+ "</td>"
														+ "</tr>" + "</table>"
												cc.panel({
													width : 730,
													content : content
												});
											},
											position : 'left'
										});
					});
}
function add() {

	d = $("#dlg").dialog({
		title : '添加主机设备',
		width : 500,
		height : 325,
		href : ctx + '/gitInfo/create',
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
				url : ctx + "/gitInfo/delete",
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

function update() {
	var row = dg.datagrid("getSelected");
	// if (rowIsNull(row)) {
	// return;
	// }

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
		title : '修改主机设备',
		width : 500,
		height : 325,
		href : ctx + '/gitInfo/update/' + row.id,
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
function cx() {
	var obj = $("#searchFrom").serializeObject();
	dg.datagrid('load', obj);
}