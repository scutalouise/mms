<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
</head>
<body>
<div id="deviceMaintenance_tb" style="padding: 5px;height: auto;">
   <div>
    <form id="deviceMaintenance_searchFrom" action="" method="post" style="margin-left:8px;margin-top:5px;height: 30px;">
       	<input type="text" name="name" class="easyui-validatebox" data-options="width:150,prompt:'设备名称'"/>&nbsp;&nbsp;&nbsp;
        <input type="text" id="problemTypeId" name="problemTypeId" class="easyui-validatebox" data-options="width:150,prompt:'问题类型'"/>&nbsp;&nbsp;&nbsp;
        <input type="text" id="problemStatus" name="problemStatus" class="easyui-validatebox" data-options="width:150,prompt:'问题状态'"/>&nbsp;&nbsp;&nbsp;
       	<input type="text" id="orgId" name="orgId" class="easyui-validatebox" data-options="width:200,prompt:'所属机构'"/>&nbsp;&nbsp;&nbsp;
		<input type="text" id="startDate" name="GTD_recordTime" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width:150,prompt:'上报时间(开始)'"/>&nbsp;&nbsp;&nbsp;
	    <input type="text" id="endDate" name="LTD_recordTime" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width:150,prompt:'上报时间(截止)'"/>
		<span class="toolbar-item dialog-tool-separator"></span>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="deviceMaintenance_cx();">查询</a>
	 </form>
     <div>
        <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" iconCls="icon-standard-page-excel" onclick="deviceMaintenance_exportExcel();">导出Excel</a>
     </div>
  </div>
</div>
<table id="deviceMaintenance_dg"></table>
<div id="deviceMaintenance_dlg"></div>
  
<script type="text/javascript">
 var deviceMaintenance_dg;
 var deviceMaintenance_dlg;
 
 $(function(){
	 deviceMaintenance_dg=$("#deviceMaintenance_dg").datagrid({
		 method:'post',
		 url:'${ctx}/device/chart/deviceMaintenanceJson',
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

		      {field:'name',title:'设备名称',sortable:true,width:50},
		      {field:'identifier',title:'设备识别码',sortable:true,width:50},
		      {field:'recordTime',title:'上报时间',sortable:true,width:50,
		    	  formatter:function(value, row, index) {
		        		return formatDate(value,"yyyy-MM-dd HH:mm:ss");
		          }},
		      {field:'resolveTime',title:'解决时间',sortable:true,width:50,
	        	  formatter:function(value, row, index) {
		        		return formatDate(value,"yyyy-MM-dd HH:mm:ss");
		          }},
		      {field:'orgName',title:'所属机构',sortable:true,width:80},
		      {field:'problemType',title:'问题类型',sortable:true,width:50},
		      {field:'description',title:'问题描述',sortable:true,width:50},
		      {field:'enable',title:'问题状态',sortable:true,width:50},
		      {field:'recordUserName',title:'登记人',sortable:true,width:50},
		      {field:'resolveUserName',title:'处理人',sortable:true,width:50},
		      {field:'reportWay',title:'上报渠道',sortable:true,width:50}
		  ]],
		 toolbar:'#deviceMaintenance_tb'
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
			url:'${ctx}/maintenance/problemType/list',
			type : "get",
			dataType : "json",
			success : function(data) {
				var json = {"id":"","name":"-- 问题类型 --"};
				data.unshift(json);
				$('#problemTypeId').combobox({
					valueField:'id',
					textField:'name',
					data:data
			    });
			}
	 });
	
	 $('#problemStatus').combobox({
		  method:"get",
		  url:'${ctx}/maintenance/problem/enable/handle/false/search/true',
		  valueField:'problemStatus',
		  textField:'name'
	  });
 });
 
//导出excel
 function deviceMaintenance_exportExcel(){
 	$("#deviceMaintenance_searchFrom")[0].action="${ctx}/device/chart/deviceMaintenance_exportExcel";
 	$("#deviceMaintenance_searchFrom")[0].target='_self';
 	$("#deviceMaintenance_searchFrom").submit();
 }
 
 function deviceMaintenance_cx(){
	var obj=$("#deviceMaintenance_searchFrom").serializeObject();
	deviceMaintenance_dg.datagrid('load',obj); 
}
</script>
</body>
</html>