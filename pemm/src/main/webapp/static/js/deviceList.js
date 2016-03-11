var dg;
var acdlg;

var organizationId;
$(function() {
	$("#device_tabs").tabs({
		onSelect : function(title) {
			$('#dg').datagrid('clearSelections');
			var tab = $(this).tabs('getSelected');
			var type = tab.panel('options').id;
			dg.datagrid('load', {
				deviceType : type,
				organizationId : organizationId
			});

			if (type == "AC") {

				$("#acConfig").css({
					visibility : "visible"
				});
			} else {
				$("#acConfig").css({
					visibility : "hidden"
				});
			}
		}
	});
	dg = $("#dg").datagrid({
		method : "post",
		url : ctx + "/device/json",
		queryParams : {
			filter_EQI_status : '0',
			filter_EQI_organizationId : organizationId
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
		singleSelect : true,
		columns : [ [ {
			field : 'id',
			checkbox : true
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
			title : "所属主机",
			sortable : true,
			width : 50,
			formatter : function(value, rowData, rowIndex) {

				return value.name
			}
		}, {
			field : "deviceInterfaceType",
			title : "设备类型",
			sortable : true,
			width : 50,
			formatter : function(v) {
				if (v != null) {
					return v.name;
				}

			}

		}, {
			field : "name",
			title : "名称",
			sortable : true,
			width : 100
		}, {
			field : "alarmTemplateName",
			title : "模板名称",
			sortable : true,
			width : 60,
			formatter : function(v) {
				return v;
			}
		}, {
			field : "managerName",
			title : "管理员",
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
			formatter : function(value) {
				if (value == 1) {
					return "是";
				} else if (v == 0) {
					return "否";
				}
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
		toolbar : '#tb',onSelect:function(rowIndex,rowData){
			if(rowData.deviceInterfaceType!=null){
			if(rowData.deviceInterfaceType.name=="空调"){
				$("#acConfig").css({
					visibility : "visible"
				});
			}else{
				$("#acConfig").css({
					visibility : "hidden"
				});
			}
			}
		}

	});
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
						organizationId : node.id
					});
					organizationId = node.id;

				}
			});
});
function loadMask() {
	$("<div class='datagrid-mask'></div>").css({
		display : "block",
		width : "100%",
		height : $(window).height()
	}).appendTo("body");
	$("<div class='datagrid-mask-msg'></div>").html("正在扫描，请稍候。。。").appendTo(
			"body").css({
		display : "block",
		fontSize : "12px",
		left : ($(document.body).outerWidth(true) - 190) / 2,
		top : ($(window).height() - 45) / 2
	});
}
function displayLoadMask() {
	$(".datagrid-mask").remove();
	$(".datagrid-mask-msg").remove();
}

function scan() {
	loadMask();
	$.ajax({
		type : "get",
		url : ctx + "/device/scan",
		data : {
			organizationId : organizationId
		},
		success : function(result) {
			displayLoadMask();
			if (result.count > 0) {
				parent.$.messager.confirm('提示', '扫描到' + result.count
						+ '个设备，是否确认添加？', function(data) {
					if (data) {
						loadMask();
						$.ajax({
							type : "post",
							url : ctx + "/device/create",
							data : {
								gitInfoIdList : result.gitInfoIdList,
								deviceIndexList : result.deviceIndexList
							},
							success : function(res) {
								displayLoadMask();
								successTip(res, dg);
							}
						});
					}

				})
			} else {
				parent.$.messager.show({
					title : "提示",
					msg : "没有扫描到设备！",
					position : "bottomRight"
				});
			}
		}
	});
}
function del() {
	var ids = "";
	var rows = $('#dg').datagrid('getSelections');
	if (rows.length < 1) {
		rowIsNull();
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
				url : ctx + "/device/delete",
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
		title : "修改设备信息",
		width : 500,
		height : 410,
		href : ctx + "/device/update/" + row.id,
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
function cog() {
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

	acdlg=$("#acdlg").dialog({
		title : "空调配置",
		width : 280,
		height : "auto",
		href:ctx+"/acConfig/acConfigForm/"+row.id,
		buttons : [ {
			text : "确认",
			handler : function() {
				$("#acMainform").submit();
			}
		}, {
			text : "取消",
			handler : function() {
				acdlg.panel('close');
			}
		} ]
	});

}