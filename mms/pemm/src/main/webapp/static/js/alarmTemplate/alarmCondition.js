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

			
			condition_d = $("#condition_dlg").dialog({
				title : "添加报警条件",
				width : 602,
				height : 290,
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
							$('#condition_dg').datagrid('clearSelections'); 
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
				width : 602,
				height : 290,
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