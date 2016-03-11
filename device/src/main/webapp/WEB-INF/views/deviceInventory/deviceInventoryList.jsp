<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<div id="tb" style="padding: 5px;height: auto;">
</div>
<table id="dg"></table>
<div id="dlg"></div>

<script type="text/javascript">
 var dg;
 var dlg;
 
 $(function(){
	 dg=$("#dg").datagrid({
		 method:'get',
		 url:'${ctx}/device/deviceInventory/json',
		 fit : true,
	     fitColumns : true,
		 idField:'id',
		 striped:true,
		 pagination:true,
		 rownumbers:true,
		 pageNumber:1,
		 pageSize:20,
		 pageList:[10,20,30,40,50],
		 singleSelect:true,
		 columns:[[
		      {field:'id',title:'id',hidden:true},
		      {field:'brandName',title:'品牌名',sortable:true,width:150},
		      {field:'firstDeviceType',title:'一级设备类型',sortable:true,width:150,
		    	  formatter:function(data){
		    		  return data.name;
		    	  }},
		      {field:'secondDeviceType',title:'二级设备类型',sortable:true,width:150,
		    	  formatter:function(data){
		    		  return data.name;
		    	  }},
		      {field:'quantity',title:'总数量',sortable:true,width:100},
		      {field:'freeQuantity',title:'可用数量',sortable:true,width:150},
		      {field:'scrapQuantity',title:'报废数量',sortable:true,width:150},
		      {field:'otherNote',title:'其他说明',sortable:true,width:150}
		  ]],
		 toolbar:'#tb'
	 });
 });
 
</script>
</body>
</html>