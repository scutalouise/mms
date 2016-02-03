<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>   
<div id="roleList_toolbar" style="padding:5px;height:auto">
        <div>
        	<form id="roleList_searchFrom" action="">
		        <input type="text" id="roleList_startDate" name="filter_GTD_createDate" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width:150,prompt: '开始日期(创建)'" />
		        - <input type="text" id="roleList_endDate" name="filter_LTD_createDate" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width:150,prompt: '结束日期(创建)'"/>
		        <span class="toolbar-item dialog-tool-separator"></span>
		        <a href="javascript(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="cx()">查询</a>
			</form>
        </div> 
</div>
	<table id="roleList_datagrid"></table>
<script type="text/javascript">
var roleList_datagrid;	//角色datagrid
var roleSelect_datagrid;
//要根据不同的设备类型，分别去判断，对应选中的记录，是哪一个role被选中；
if(tabIndex == 0){
	roleSelect_datagrid = hostDevice_datagrid;
} else if(tabIndex == 1){
	roleSelect_datagrid = networkDevice_datagrid;
} else if(tabIndex == 2){
	roleSelect_datagrid = unintelligentDevice_datagrid;
} else if(tabIndex == 3){
	roleSelect_datagrid = collectionDevice_datagrid;
} else if(tabIndex == 4){
	roleSelect_datagrid = peDevice_datagrid;
}
$(function(){   
	roleList_datagrid=$('#roleList_datagrid').datagrid({    
		method: "get",                                                                    
		url:'${ctx}/system/role/json',                                                    
	    fit : true,                                                                       
		fitColumns : true,                                                                
		border : false,                                                                   
		idField : 'id',                                                                   
		pagination:true,                                                                  
		rownumbers:true,                                                                  
		pageNumber:1,
		singleSelect:true,
		pageSize : 10,                                                                    
		pageList : [ 10, 20, 30, 40, 50 ],                                                
		striped:true,
		onLoadSuccess:function(data){//所有数据加载成功的时候，选中已经存在的id行记录；
			if(roleSelect_datagrid != undefined && roleSelect_datagrid.datagrid('getSelected').roleId != null){
				//$('#roleList_datagrid').datagrid('selectRecord',roleSelect_datagrid.datagrid('getSelected').roleId)
			}
		}, 
	    columns:[[                                                                        
	        {field:'id',title:'id',hidden:true,sortable:true,width:100},                  
	        {field:'name',title:'角色名称',sortable:true,width:100},                      
	        {field:'roleCode',title:'角色编码',sortable:true,width:100},                  
	        {field:'description',title:'描述',sortable:true,width:100,tooltip: true}      
	    ]],
	    onDblClickRow:function (){
	    	getDeviceRole();
	    	role_dialog.panel('close');
	    },
	    toolbar:'#roleList_toolbar'
	});
	
	initDateFilter("roleList_startDate","roleList_endDate");
});


//创建查询对象并查询
function cx(){
	var obj=$("#roleList_searchFrom").serializeObject();
	deviceUser_datagrid.datagrid('load',obj); 
}

//确定设备的运维角色
function getDeviceRole(){
	//所选的运维角色，只能选择一条;
	var row = roleList_datagrid.datagrid('getSelected');
	if(rowIsNull(row)) return;
	$("#roleId").val(row['id']);
	$("#roleName").val(row['name']);
	$('#mainform').form('validate');
}
</script>
</body>
</html>