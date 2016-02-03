<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>   
<div id="alarmTemplateList_toolbar" style="padding:5px;height:auto">
        <div>
        	<form id="alarmTemplateList_searchFrom" action="">
		        <input type="text" id="alarmTemplateList_startDate" name="filter_GTD_createDate" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width:150,prompt: '开始日期(创建)'" />
		        - <input type="text" id="alarmTemplateList_endDate" name="filter_LTD_createDate" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width:150,prompt: '结束日期(创建)'"/>
		        <span class="toolbar-item dialog-tool-separator"></span>
		        <a href="javascript(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="cx()">查询</a>
			</form>
        </div> 
</div>
	<table id="alarmTemplateList_datagrid"></table>
<script type="text/javascript">
var alarmTemplateList_datagrid;	//角色datagrid
var alarmTemplateSelect_datagrid;
//要根据不同的设备类型，分别去判断，对应选中的记录，是哪一个alarmTemplate被选中；
if(tabIndex == 0){
	alarmTemplateSelect_datagrid = hostDevice_datagrid;
} else if(tabIndex == 1){
	alarmTemplateSelect_datagrid = networkDevice_datagrid;
} else if(tabIndex == 2){
	alarmTemplateSelect_datagrid = unintelligentDevice_datagrid;
} else if(tabIndex == 3){
	alarmTemplateSelect_datagrid = collectionDevice_datagrid;
} else if(tabIndex == 4){
	alarmTemplateSelect_datagrid = peDevice_datagrid;
}
$(function(){   
	alarmTemplateList_datagrid=$('#alarmTemplateList_datagrid').datagrid({    
		method: "post",                                                                    
		url:'${ctx}/system/alarmTemplate/json',                                                    
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
			if(alarmTemplateSelect_datagrid != undefined && alarmTemplateSelect_datagrid.datagrid('getSelected').alarmTemplateId != null){
				$('#alarmTemplateList_datagrid').datagrid('selectRecord',alarmTemplateSelect_datagrid.datagrid('getSelected').alarmTemplateId)
			}
		}, 
	    columns:[[                                                                        
	        {field:'id',title:'id',hidden:true,sortable:true,width:100},                  
	        {field:'name',title:'告警模板名',sortable:true,width:100},    
	        {field:'enabled',title:'是否启用',sortable:true,width:100,formatter:function(value){
	        	if(value == 0){
					return "启用";
				}else{
					return "禁用";
				}
	        }}, 
	        {field:'remark',title:'描述',sortable:true,width:100}     
	    ]],                                                                               
	    toolbar:'#alarmTemplateList_toolbar'
	});
	
	initDateFilter("alarmTemplateList_startDate","alarmTemplateList_endDate");
});


//创建查询对象并查询
function cx(){
	var obj=$("#alarmTemplateList_searchFrom").serializeObject();
	deviceUser_datagrid.datagrid('load',obj); 
}

//保存设备的告警模板
function saveAlarmTemplate(parentDataGrid,path){
	var row = alarmTemplateList_datagrid.datagrid('getSelected');//获取选中的记录
	var parent_row = parentDataGrid.datagrid('getSelected');
	if(rowIsNull(row)) return;
	$.post(
		path,
		{
			id:parent_row.id,
			alarmTemplateId:row.id
		},
		function(data){
			successTip(data,parentDataGrid,null);
		}
	)
}
</script>
</body>
</html>