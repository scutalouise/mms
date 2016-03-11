<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
</head>
<body>
<div id="deviceInspect_tb" style="padding: 5px;height: auto;">
   <div>
    <form id="deviceInspect_searchFrom" action="" method="post" style="margin-left:8px;margin-top:5px;height: 30px;">
       	<input type="text" name="filter_LIKE_S_userName" class="easyui-validatebox" data-options="width:150,prompt:'巡检人'"/>&nbsp;&nbsp;&nbsp;
		<select name="filter_EQ_S_inspectStatus" id="status" class="easyui-combobox" data-options="width: 150">
			<option value="">-- 巡检状态 --</option>
			<option value="合格">合格</option>
			<option value="不合格">不合格</option>
	    </select>&nbsp;&nbsp;&nbsp;
       	<input type="text" id="orgId" name="filter_EQ_I_orgId" class="easyui-validatebox" data-options="width:200"/>&nbsp;&nbsp;&nbsp;
		<input type="text" id="startDate" name="filter_GE_D_inspectTime" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width:150,prompt:' 巡检日期(开始)'"/>&nbsp;&nbsp;&nbsp;
	    <input type="text" id="endDate" name="filter_LT_D_inspectTime" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width:150,prompt:'巡检日期(截止)'"/>
		<span class="toolbar-item dialog-tool-separator"></span>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="deviceInspect_cx();">查询</a>
	 </form>
     <div>
        <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" iconCls="icon-standard-page-excel" onclick="deviceInspect_exportExcel();">导出Excel</a>
     </div>
  </div>
</div>
<table id="deviceInspect_dg"></table>
<div id="deviceInspect_dlg"></div>
  
<script type="text/javascript">
 var deviceInspect_dg;
 var deviceInspect_dlg;
 
 $(function(){
	 deviceInspect_dg=$("#deviceInspect_dg").datagrid({
		 method:'post',
		 url:'${ctx}/device/chart/deviceInspectJson',
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
		      {field:'orgName',title:'巡检网点',sortable:true,width:50},
		      {field:'userName',title:'巡检人',sortable:true,width:50},
		      {field:'inspectTime',title:'巡检时间',sortable:true,width:50,
		    	  formatter: function(value,row,index){
			        	return formatDate(value,"yyyy-MM-dd HH:mm:ss")
			  }},
		      {field:'inspectStatus',title:'巡检状态',sortable:true,width:50},
		      {field:'deviceTotal',title:'应巡检总数',sortable:true,width:50},
		      {field:'inspectedTotal',title:'实际巡检总数',sortable:true,width:50},
		      {field:'inexistendTotal',title:'非网点设备数量',sortable:true,width:50},
		      {field:'uncheckedTotal',title:'未巡检设备',sortable:true,width:50}
		  ]],
		 toolbar:'#deviceInspect_tb'
	 });
	 
	 initDateFilter("startDate","endDate");
	 
	 $.ajax({
			url:'${ctx}/system/organization/json',
			type : "post",
			dataType : "json",
			success : function(data) {
				var json = {"id":"","orgName":"-- 巡检网点 --"};
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
 function deviceInspect_exportExcel(){
 	$("#deviceInspect_searchFrom")[0].action="${ctx}/device/chart/deviceInspect_exportExcel";
 	$("#deviceInspect_searchFrom")[0].target='_self';
 	$("#deviceInspect_searchFrom").submit();
 }
 
 function deviceInspect_cx(){
	var obj=$("#deviceInspect_searchFrom").serializeObject();
	deviceInspect_dg.datagrid('load',obj); 
}
</script>
</body>
</html>