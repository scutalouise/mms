<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
</head>
<body>
<div id="devicePurchase_tb" style="padding: 5px;height: auto;">
   <div>
    <form id="devicePurchase_searchFrom" action="" method="post" style="margin-left:8px;margin-top:5px;height: 30px;">
       	<input type="text" name="name" class="easyui-validatebox" data-options="width:150,prompt:'采购名称'"/>&nbsp;&nbsp;&nbsp;
       	<input type="text" id="orgId" name="orgId" class="easyui-validatebox" data-options="width:200,prompt:'采购机构'"/>&nbsp;&nbsp;&nbsp;
		<input type="text" id="startDate" name="GTD_purchaseDate" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width:150,prompt:'购买日期(开始)'"/>&nbsp;&nbsp;&nbsp;
	    <input type="text" id="endDate" name="LTD_purchaseDate" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width:150,prompt:'购买日期(截止)'"/>
		<span class="toolbar-item dialog-tool-separator"></span>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="devicePurchase_cx();">查询</a>
	 </form>
     <div>
        <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" iconCls="icon-standard-page-excel" onclick="devicePurchase_exportExcel();">导出Excel</a>
     </div>
  </div>
</div>
<table id="devicePurchase_dg"></table>
<div id="devicePurchase_dlg"></div>
  
<script type="text/javascript">
 var devicePurchase_dg;
 var devicePurchase_dlg;
 
 $(function(){
	 devicePurchase_dg=$("#devicePurchase_dg").datagrid({
		 method:'post',
		 url:'${ctx}/device/devicePurchase/json',
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
		      {field:'name',title:'采购名称',sortable:true,width:150},
		      {field:'purchaseDate',title:'采购日期',sortable:true,width:150},
		      {field:'orgName',title:'采购机构',sortable:true,width:200},
		      {field:'quantity',title:'采购数量',sortable:true,width:100},
		      {field:'otherNote',title:'备注',sortable:true,width:150}
		  ]],
		 toolbar:'#devicePurchase_tb'
	 });
	 
	 initDateFilter("startDate","endDate");

	 $.ajax({
			url:'${ctx}/system/organization/json',
			type : "post",
			dataType : "json",
			success : function(data) {
				var json = {"id":"","orgName":"-- 采购机构 --"};
				data.unshift(json);
				$('#orgId').combotree({
					idField:'id',
					textFiled:'orgName',
					data:data
			    });
			}
	 });
 });
 
//导出excel
 function devicePurchase_exportExcel(){
 	$("#devicePurchase_searchFrom")[0].action="${ctx}/device/chart/devicePurchase_exportExcel";
 	$("#devicePurchase_searchFrom")[0].target='_self';
 	$("#devicePurchase_searchFrom").submit();
 }
 
 function devicePurchase_cx(){
	var obj=$("#devicePurchase_searchFrom").serializeObject();
	devicePurchase_dg.datagrid('load',obj); 
}
</script>
</body>
</html>