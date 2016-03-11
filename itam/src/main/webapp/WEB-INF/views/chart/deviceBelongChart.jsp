<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
</head>
<body>
<div id="deviceBelong_tb" style="padding: 5px;height: auto;">
   <div>
    <form id="deviceBelong_searchFrom" action="" method="post" style="margin-left:8px;margin-top:5px;height: 30px;">
       	<input type="text" name="name" class="easyui-validatebox" data-options="width:150,prompt:'设备名称'"/>&nbsp;&nbsp;&nbsp;
        <input type="text" id="deviceType" name="firstDeviceType" class="easyui-validatebox" data-options="width:150,prompt:'设备类型'"/>&nbsp;&nbsp;&nbsp;
       	<input type="text" id="orgId" name="orgId" class="easyui-validatebox" data-options="width:200,prompt:'采购机构'"/>&nbsp;&nbsp;&nbsp;
		<input type="text" id="startDate" name="GTD_purchaseDate" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width:150,prompt:'购买日期(开始)'"/>&nbsp;&nbsp;&nbsp;
	    <input type="text" id="endDate" name="LTD_purchaseDate" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width:150,prompt:'购买日期(截止)'"/>
		<span class="toolbar-item dialog-tool-separator"></span>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="deviceBelong_cx();">查询</a>
	 </form>
     <div>
        <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" iconCls="icon-standard-page-excel" onclick="deviceBelong_exportExcel();">导出Excel</a>
     </div>
  </div>
</div>
<table id="deviceBelong_dg"></table>
<div id="deviceBelong_dlg"></div>
  
<script type="text/javascript">
 var deviceBelong_dg;
 var deviceBelong_dlg;
 
 $(function(){
	 deviceBelong_dg=$("#deviceBelong_dg").datagrid({
		 method:'post',
		 url:'${ctx}/device/chart/deviceBelongJson',
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
		      {field:'name',title:'设备名称',sortable:true,width:50},
		      {field:'identifier',title:'设备唯一码',sortable:true,width:50},
		      {field:'deviceType',title:'设备类型',sortable:true,width:50},
		      {field:'model',title:'设备型号',sortable:true,width:50},
		      {field:'purchaseDate',title:'采购日期',sortable:true,width:50,
		    	  formatter:function(value,row,index){
		    		  return formatDate(value,"yyyy-MM-dd");
		    	  }},
		      {field:'orgName',title:'所属机构',sortable:true,width:50},
		      {field:'quantity',title:'数量',sortable:true,width:50},
		      {field:'managerName',title:'管理员',sortable:true,width:50}
		  ]],
		 toolbar:'#deviceBelong_tb'
	 });
	 
	 initDateFilter("startDate","endDate");
	 
	 $.ajax({
			url:'${ctx}/system/organization/json',
			type : "post",
			dataType : "json",
			success : function(data) {
				var json = {"id":"","orgName":"-- 所属机构 --"};
				data.unshift(json);
				$('#orgId').combotree({
					idField:'id',
					textFiled:'orgName',
					data:data
			    });
			}
	 });
	 
	 $.ajax({
			url:'${ctx}/device/brand/firstDeviceType',
			type : "get",
			dataType : "json",
			success : function(data) {
				var json = {"firstDeviceType":"","name":"-- 设备类型 --"};
				data.unshift(json);
				$('#deviceType').combobox({
					valueField:'firstDeviceType',
					textField:'name',
					data:data
			    });
			}
	 });
 });
 
//导出excel
 function deviceBelong_exportExcel(){
 	$("#deviceBelong_searchFrom")[0].action="${ctx}/device/chart/deviceBelong_exportExcel";
 	$("#deviceBelong_searchFrom")[0].target='_self';
 	$("#deviceBelong_searchFrom").submit();
 }
 
 function deviceBelong_cx(){
	var obj=$("#deviceBelong_searchFrom").serializeObject();
	deviceBelong_dg.datagrid('load',obj); 
}
</script>
</body>
</html>