<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>   
<div id="toolbar" style="padding:5px;height:auto">
	<div>
       	<form id="searchFrom" action="">
    	    <input type="text" name="filter_LIKES_phone" class="easyui-validatebox" data-options="width:150,prompt: '电话'"/>
	        <input type="text" id="startDate" name="filter_GTD_createDate" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width:150,prompt: '开始日期(创建)'" />
	        - <input type="text" id="endDate" name="filter_LTD_createDate" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width:150,prompt: '结束日期(创建)'"/>
	        <span class="toolbar-item dialog-tool-separator"></span>
	        <a href="javascript(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="queryPurchase()">查询</a>
		</form>
	</div>
</div>
<table id="devicePurchase_datagrid"></table>
<script type="text/javascript">
 	var devicePurchase_datagrid;
	$(function(){
		devicePurchase_datagrid=$('#devicePurchase_datagrid').datagrid({    
			method: "get",
			url:'${ctx}/device/devicePurchase/purchase', 
		    fit : true,
			fitColumns : true,
			border : false,
			idField : 'id',
			pagination:true,
			rownumbers:true,
			pageNumber:1,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			striped:true,
			singleSelect:true,
			toolbar:'#toolbar',
		    columns:[[    
		        {field:'id',title:'id',hidden:true,sortable:true,width:100},    
 		        {field:'name',title:'设备名字',sortable:true,width:100},
  		        {field:'purchaseDate',title:'购买日期',sortable:true,width:100,formatter: function(value,row,index){
 			        	return formatDate(value,"yyyy-MM-dd")
 		        }},
 		        {field:'warrantyDate',title:'保修日期(截止)',sortable:true,width:100,formatter: function(value,row,index){
 		        	return formatDate(value,"yyyy-MM-dd")
 		        }}, 
 		        {field:'orgName',title:'采购单位',sortable:true,width:100,tooltip: true}
		    ]],
		    onDblClickRow:function (){
		    	getSelectPurchase();
		    	devicePurchase_dialog.panel('close');
		    }
		});
	});

//查询购买，采购记录
function queryPurchase(){
	var obj=$("#searchFrom").serializeObject();
	devicePurchase_datagrid.datagrid('load',obj); 
} 
	
//确定设备的采购记录
function getSelectPurchase(){
	//所选的采购记录，只能选择一条;
	var row = devicePurchase_datagrid.datagrid('getSelected');
	if(rowIsNull(row)) return;
	$("#purchaseId").val(row['id']);
	$("#purchaseName").val(row['name']);
	$('#mainform').form('validate');
} 
</script>
</body>
</html>