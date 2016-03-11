<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
</head>
<body>
<div id="deviceAccounts_tb" style="padding: 5px;height: auto;">
   <div>
     <form id="deviceAccounts_searchFrom" action="" method="post">
       
	 </form>
     <div>
        <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" iconCls="icon-standard-page-excel" onclick="deviceAccounts_exportExcel();">导出Excel</a>
     </div>
  </div>
</div>
<table id="deviceAccounts_dg"></table>
<div id="deviceAccounts_dlg"></div>
  
<script type="text/javascript">
 var deviceAccounts_dg;
 var deviceAccounts_dlg;
 
 $(function(){
	 deviceAccounts_dg=$("#deviceAccounts_dg").datagrid({
		 method:'post',
		 url:'${ctx}/device/chart/deviceAccountsJson',
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
		      {field:'purchaseDate',title:'日期',sortable:true,width:50,
		    	  formatter:function(value,row,index){
		    		  return formatDate(value,"yyyy-MM-dd");
		          }},
		      {field:'brandName',title:'品牌名称',sortable:true,width:50},
		      {field:'deviceType',title:'设备名称',sortable:true,width:50},
		      {field:'previousQuantity',title:'上期数',sortable:true,width:50},
		      {field:'inflowQuantity',title:'入库',sortable:true,width:50},
		      {field:'outflowQuantity',title:'出库',sortable:true,width:50},
		      {field:'scrapQuantity',title:'报废',sortable:true,width:50},
		      {field:'otherNote',title:'备注',sortable:true,width:50}
		  ]],
		 toolbar:'#deviceAccounts_tb'
	 });
 });
 
//导出excel
 function deviceAccounts_exportExcel(){
 	$("#deviceAccounts_searchFrom")[0].action="${ctx}/device/chart/deviceAccounts_exportExcel";
 	$("#deviceAccounts_searchFrom")[0].target='_self';
 	$("#deviceAccounts_searchFrom").submit();
 }
 
</script>
</body>
</html>