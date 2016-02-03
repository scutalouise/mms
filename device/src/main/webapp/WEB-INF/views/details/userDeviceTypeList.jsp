<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/views/include/easyui.jsp" %>
</head>
<body>
<div id="userDeviceTypeList_toolbar" style="padding: 5px;height: auto;">
   <div>
   </div>
</div>
   <table id="device_userDefineType_datagrid"></table>
<script type="text/javascript">
  var device_userDefineType_datagrid;
  var userDeviceTypeSelect_datagrid;
//要根据不同的设备类型，分别去判断，对应选中的记录，是哪一个userDeviceType被选中；
if(tabIndex == 0){
	userDeviceTypeSelect_datagrid = hostDevice_datagrid;
} else if(tabIndex == 1){
	userDeviceTypeSelect_datagrid = networkDevice_datagrid;
} else if(tabIndex == 2){
	userDeviceTypeSelect_datagrid = unintelligentDevice_datagrid;
} else if(tabIndex == 3){
	userDeviceTypeSelect_datagrid = collectionDevice_datagrid;
} else if(tabIndex == 4){
	userDeviceTypeSelect_datagrid = peDevice_datagrid;
}
$(function(){
	  device_userDefineType_datagrid=$('#device_userDefineType_datagrid').treegrid({
		  method:"get",
		  url:'${ctx}/device/userDeviceType/json',
		  fit:true,
		  fitColumns:true,
	      idField:'id',
	      treeField:'name',
	      parentField:'pid',
	      animate:true,
	      striped:true,
	      rownumbers:true,
	      singleSelect:true,
	      onLoadSuccess:function(data){//所有数据加载成功的时候，选中已经存在的id行记录；
	    	  if(userDeviceTypeSelect_datagrid != undefined && userDeviceTypeSelect_datagrid.datagrid('getSelected').userDeviceTypeId != null){
		    	  $('#device_userDefineType_datagrid').treegrid('select',userDeviceTypeSelect_datagrid.datagrid('getSelected').userDeviceTypeId);
	    	  }
			}, 
	      columns:[[
	          {field:'id',title:'id',hidden:true},
	          {field:'name',title:'设备类型',sortable:true,width:100},
	          {field:'otherNote',title:'描述',sortable:true,width:100}
	      ]],
	      toolbar:'#userDeviceTypeList_toolbar'
	  });
	  
	  //initDateFilter("userDeviceTypeList_startDate","userDeviceTypeList_endDate")
  });
  
//保存用户选择的自定义类型
function saveUserDefineTypes(parentDataGrid,path){
		var row = device_userDefineType_datagrid.datagrid('getSelected');//获取选中的记录
		var parent_row = parentDataGrid.datagrid('getSelected');
		if(rowIsNull(row)) return;
		$.post(
			path,
			{
				id:parent_row.id,
				userDeviceTypeId:row.id
			},
			function(data){
				successTip(data,parentDataGrid,null);
			}
		)
  }
  
</script>
</body>
</html>