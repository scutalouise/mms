<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>告警条件</title>

<script type="text/javascript"
	src="${ctx }/static/js/alarmTemplate/alarmCondition.js"></script>
</head>
<body>
	<div id="condition_tb">
		<div>

			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-add" plain="true" onclick="addCondition()">添加</a> <span
				class="toolbar-item dialog-tool-separator"></span> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-remove" plain="true" data-options="disabled:false"
				onclick="delCondition()">删除</a> <span
				class="toolbar-item dialog-tool-separator"></span> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true" onclick="updateCondition()">修改</a>
		</div>
	</div>
	<div id="condition_dg"></div>
	<div id="condition_dlg"></div>
	<script type="text/javascript">
		var checkedTemplateName;
		var checkedTemplateId;
		var condition_dg;
		var condition_d;
		$(function() {
			condition_dg = $("#condition_dg").datagrid({
				method : "post",
				url : ctx + "/system/alarmCondition/json",
				queryParams : {
					filter_EQI_status : '0'
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
				singleSelect : false,
				columns : [ [ {
					field : 'id',
					checkbox : true
				}, {
					field : "deviceType",
					title : "设备类型",
					sortable : true,
					width : 10
				}, {
					field : "alarmTemplateName",
					title : "所属模板",
					sortable : true,
					width : 10
				}, {
					field : "alarmLevel",
					title : "报警等级",
					sortable : true,
					width : 10,
					formatter : function(v) {
						return v.name;
					}
				}, {
					field : "stayTime",
					title : "异常持续报警时间",
					sortable : true,
					width : 10
				}, {
					field : "repeatCount",
					title : "重复报警次数",
					sortable : true,
					width : 10
				}, {
					field : "intervalTime",
					title : "重复报警间隔时间",
					sortable : true,
					width : 10
				}, {
					field : "noticeAfter",
					title : "报警消失后是否通知",
					sortable : true,
					width : 10,
					align:"center",
					formatter : function(value) {
						if (value == 0) {
							return "是";
						} else {
							return "否";
						}
					}
				}, {
					field : "enabled",
					title : "是否启用",
					sortable : true,
					width : 10,
					align:"center",
					formatter : function(value) {
						if (value == 0) {
							return "启用";
						} else {
							return "禁用";
						}
					}
				} ] ],
				enableHeaderClickMenu : true,
				enableHeaderContextMenu : true,
				enableRowContextMenu : false,
				toolbar : '#condition_tb'
			});
		});
		function addCondition() {
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

			checkedTemplateName = row.name;
			checkedTemplateId = row.id;
			condition_d = $("#condition_dlg").dialog({
				title : "添加报警条件",
				width : 600,
				height : 250,
				href : ctx + "/system/alarmCondition/addForm",
				maximizable : true,
				modal : true,
				buttons : [ {
					text : "确认",
					handler : function() {
						$("#condition_mainform").submit();
					}
				}, {
					text : '取消',
					handler : function() {
						condition_d.panel('close');
					}
				} ]
			});

		}
		function delCondition() {
			var ids = "";
			var rows = $('#condition_dg').datagrid('getSelections');
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
						url : ctx + "/system/alarmCondition/delete",
						data : {
							ids : ids
						},
						success : function(data) {
							successTip(data, condition_dg);
						}
					});
				}
			});
		}
		function updateCondition() {
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
			condition_d = $("#condition_dlg").dialog({
				title : "修改告警条件",
				width : 600,
				height : 250,
				href : ctx + "/system/alarmCondition/updateForm/" + row.id,
				maximizable : true,
				modal : true,
				buttons : [ {
					text : "确认",
					handler : function() {
						$("#condition_mainform").submit();
					}
				}, {
					text : '取消',
					handler : function() {
						condition_d.panel('close');
					}
				} ]
			})
		}
	</script>
</body>
</html>