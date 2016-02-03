<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
</head>
<body>
<!---------------------------------------- toolsBar Start ---------------------------------------->	
	<div id="recycle_toolBar" style="padding:5px;height:auto">
		<div>
	       	<form id="recycle_searchFrom" action="">
	      	        <input type="text" name="filter_LIKES_tableName" class="easyui-validatebox" data-options="width:150,prompt: '删除的表名(暂时不能用)'"/>
	      	        <input type="text" name="filter_LIKES_userName" class="easyui-validatebox" data-options="width:150,prompt: '执行删除的人名字(暂时不能用)'"/>
		        <input type="text" id="rcycle_startDate" name="filter_GTD_opTime" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width:150,prompt: '开始日期(删除暂时不能用)'" />
		        - <input type="text" id="rcycle_endDate" name="filter_LTD_opTime" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width:150,prompt: '结束日期(删除暂时不能用)'"/>
		        <span class="toolbar-item dialog-tool-separator"></span>
		        <a href="javascript(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="queryHost()">查询</a>
			</form>
	       </div> 
	</div>
<!---------------------------------------- toolsBar End ---------------------------------------->	
<!---------------------------------------- MainTable Start ---------------------------------------->
<table id="recycle_datagrid" name="forSelectedDataGrid"></table> 
<!---------------------------------------- MainTable End ---------------------------------------->
<script type="text/javascript">
var recycle_datagrid;
$(function(){   
	recycle_datagrid=$('#recycle_datagrid').datagrid({    
		method: "post",
	    url:'${ctx}/recycle/json',
	    fit : true,
		fitColumns : true,
		border : false,
		idField : 'id',
		striped:true,
		pagination:true,
		rownumbers:true,
		pageNumber:1,
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50 ],
		singleSelect:true,
	    columns:[[    
	        {field:'id',title:'id',hidden:true},    
	        {field:'userName',title:'操作用户',sortable:true,width:60},    
	        {field:'opTime',title:'删除的时间',sortable:true,width:80,formatter: function(value,row,index){
	        	return formatDate(value,"yyyy-MM-dd HH-mm-ss")
	        }},
	        {field:'recoveryString',title:'是否已经还原',sortable:true,
	        	formatter : function(value, row, index) {
	       			return value=='YES'?'已还原':'未还原';
	        	}
	        },
	        {field:'content',title:'删除内容简介',sortable:true,width:120},
	        {field:'tableName',title:'表名',sortable:true,width:100},
	        {field:'tableRecordId',title:'主键',sortable:true,width:80,},
	        {field:'updateTime',title:'执行还原时间',sortable:true,width:80,formatter: function(value,row,index){
	        	return formatDate(value,"yyyy-MM-dd HH:mm:ss")
	        }}
	    ]],
	    headerContextMenu: [
	        {
	            text: "冻结该列", disabled: function (e, field) { return dg.datagrid("getColumnFields", true).contains(field); },
	            handler: function (e, field) { dg.datagrid("freezeColumn", field); }
	        },
	        {
	            text: "取消冻结该列", disabled: function (e, field) { return dg.datagrid("getColumnFields", false).contains(field); },
	            handler: function (e, field) { dg.datagrid("unfreezeColumn", field); }
	        }
	    ],
	    enableHeaderClickMenu: true,
	    enableHeaderContextMenu: true,
	    enableRowContextMenu: false,
	    toolbar:'#recycle_toolBar'
	});
	initDateFilter("rcycle_startDate","rcycle_endDate");
});


//创建查询对象并查询
function queryHost(){
	var obj=$("#recycle_searchFrom").serializeObject();
	recycle_datagrid.datagrid('load',obj); 
}
</script>
</body>
</html>