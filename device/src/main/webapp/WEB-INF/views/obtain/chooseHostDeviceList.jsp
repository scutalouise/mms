<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>未领用的主机设备列表</title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>
<body>
	
	<div id="chooseHost_dg"></div>
	<script type="text/javascript">
		$(function() {
			
			$("#chooseHost_dg").datagrid(
					{
						method : "post",
						url : ctx + "/device/hostDevice/json",
						queryParams : {
							
							type:"obtain",
							deviceUsedState:"PUTINSTORAGE"
						},
						fit : true,
						fitColumns : true,
						border : false,
						idField : 'id',
						striped : true,
						pagination : true,
						rownumbers : true,
						pageNumber : 1,
						pageSize : 20,
						pageList : [ 10, 20, 30, 40, 50 ],
						singleSelect : true,
						columns : [ [ {
							field : 'id',
							title : 'id',
							hidden : true
						}, {
							field : 'name',
							title : '设备名称',
							sortable : true,
							width : 100
						}, {
							field : 'model',
							title : '型号',
							sortable : true,
							width : 100
						} , {
							field : 'manufactureDate',
							title : '生产日期',
							sortable : true,
							width : 100,
							formatter : function(value, row, index) {
								return formatDate(value, "yyyy-MM-dd")
							}
						}] ],
						headerContextMenu : [
								{
									text : "冻结该列",
									disabled : function(e, field) {
										return dg.datagrid("getColumnFields",
												true).contains(field);
									},
									handler : function(e, field) {
										dg.datagrid("freezeColumn", field);
									}
								},
								{
									text : "取消冻结该列",
									disabled : function(e, field) {
										return dg.datagrid("getColumnFields",
												false).contains(field);
									},
									handler : function(e, field) {
										dg.datagrid("unfreezeColumn", field);
									}
								} ],
						enableHeaderClickMenu : true,
						enableHeaderContextMenu : true,
						enableRowContextMenu : false
						

					});
		});
	</script>
</body>
</html>