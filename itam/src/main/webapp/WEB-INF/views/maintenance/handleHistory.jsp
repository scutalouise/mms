<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
	<head>
		<title></title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/WEB-INF/views/include/easyui.jsp" %>
		<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	</head>
	<body>
		<table id="dgp"></table>
		<script type="text/javascript">
		var dgp;
		$(function(){
			var problemId = "${problemId}";
			dgp=$('#dgp').datagrid({
				  method:"get",
				  url:'${ctx}/maintenance/handling/historyList/problemId/' + problemId,
				  fit:true,
				  fitColumns:true,
			      animate:false,
			      striped:true,
			      enableHeaderClickMenu: false,
			      enableHeaderContextMenu: false,
			  	  pagination:false,
			      sortable : false,
				  rownumbers:true,
			      columns:[[
			          {
			        	  field:'id',
			        	  title:'id',
			        	  hidden:true
			        }, {
			        	field:'handleUserName',
			        	title:'处理人',
			        	sortable:true,
			        	width:100
			        }, {
			        	field:'handleTime',
			        	title:'处理时间',
			        	sortable:true,
			        	formatter : function(value, row, index) {
			        		return formatDate(value, "yyyy-MM-dd HH:mm:ss");
			        	}
			        }, {
			        	field:'enable',
			        	title:'状态',
			        	sortable:true,
			        	formatter : function(value, row, index) {
			        		return value.name;
			        	}
			        }, {
			        	field:'description',
			        	title:'备注',
			        	sortable:true,
			        	width : 600,
			        	formatter : function(value, row, index) {
			        		var html = "<a title='" + value + "'>" + value + "</a>"
			        		return html;
			        	}
			        }
			      ]]
			  });
		});
		</script>
	</body>
</html>