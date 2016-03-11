<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
</head>
<body>
<div id="userList_toolbar" style="padding:5px;height:auto">
        <div>
        	<form id="userList_searchFrom" action="">
       	        <input type="text" name="filter_LIKES_name" class="easyui-validatebox" data-options="width:150,prompt: '昵称'"/>
       	        <input type="text" name="filter_LIKES_phone" class="easyui-validatebox" data-options="width:150,prompt: '电话'"/>
		        <input type="text" id="userList_startDate" name="filter_GTD_createDate" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width:150,prompt: '开始日期(创建)'" />
		        - <input type="text" id="userList_endDate" name="filter_LTD_createDate" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width:150,prompt: '结束日期(创建)'"/>
		        <span class="toolbar-item dialog-tool-separator"></span>
		        <a href="javascript(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="cx()">查询</a>
			</form>
        </div> 
  </div>
<table id="deviceUser_datagrid"></table> 
<script type="text/javascript">
var deviceUser_datagrid;
var userSelect_datagrid;
//要根据不同的设备类型，分别去判断，对应选中的记录，是哪一个user被选中；
if(tabIndex == 0){
	userSelect_datagrid = hostDevice_datagrid;
} else if(tabIndex == 1){
	userSelect_datagrid = networkDevice_datagrid;
} else if(tabIndex == 2){
	userSelect_datagrid = unintelligentDevice_datagrid;
} else if(tabIndex == 3){
	userSelect_datagrid = collectionDevice_datagrid;
} else if(tabIndex == 4){
	userSelect_datagrid = peDevice_datagrid;
}
//console.info("userSelect_datagrid:"+userSelect_datagrid);
$(function(){
	deviceUser_datagrid=$('#deviceUser_datagrid').datagrid({    
		method: "post",
	    url:'${ctx}/system/user/json', 
	    fit : true,
		fitColumns : true,
		border : false,
		idField : 'id',
		striped:true,
		pagination:true,
		rownumbers:true,
		pageNumber:1,
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		singleSelect:true,//selectRecord
		onLoadSuccess:function(data){//所有数据加载成功的时候，选中已经存在的管理员id行记录；
			//console.info($("#tab_devices").tabs(tabIndex));
			if(userSelect_datagrid != undefined && userSelect_datagrid.datagrid('getSelected').managerId != null){
				$('#deviceUser_datagrid').datagrid('selectRecord',userSelect_datagrid.datagrid('getSelected').managerId);//列表中，选中当前设备的对应设备管理员
			}
		}, 
	    columns:[[    
	        {field:'id',title:'id',hidden:true},    
	        {field:'loginName',title:'用户名',sortable:true,width:100},    
	        {field:'name',title:'昵称',sortable:true,width:100},
	        {field:'gender',title:'性别',sortable:true,
	        	formatter : function(value, row, index) {
	       			return value==1?'男':'女';
	        	}
	        },
	        {field:'email',title:'email',sortable:true,width:100},
	        {field:'phone',title:'电话',sortable:true,width:100},
	        {field:'loginCount',title:'登录次数',sortable:true},
	        {field:'previousVisit',title:'上一次登录',sortable:true,formatter: function(value,row,index){
	        	return formatDate(value,"yyyy-MM-dd HH:mm:ss")
	        }}
	    ]],
	    headerContextMenu: [
	        {
	            text: "冻结该列", disabled: function (e, field) { return deviceUser_datagrid.datagrid("getColumnFields", true).contains(field); },
	            handler: function (e, field) { deviceUser_datagrid.datagrid("freezeColumn", field); }
	        },
	        {
	            text: "取消冻结该列", disabled: function (e, field) { return deviceUser_datagrid.datagrid("getColumnFields", false).contains(field); },
	            handler: function (e, field) { deviceUser_datagrid.datagrid("unfreezeColumn", field); }
	        }
	    ],
	    enableHeaderClickMenu: true,
	    enableHeaderContextMenu: true,
	    enableRowContextMenu: false,
	    toolbar:'#userList_toolbar'
	});
	
	initDateFilter("userList_startDate","userList_endDate");
});


//创建查询对象并查询
function cx(){
	var obj=$("#userList_searchFrom").serializeObject();
	deviceUser_datagrid.datagrid('load',obj); 
}

//将选中的设备管理用户保存；
function saveDeviceUser(parentDataGrid,path){
	var row = deviceUser_datagrid.datagrid('getSelected');//获取选中的记录
	var parent_row = parentDataGrid.datagrid('getSelected');
	if(rowIsNull(row)) return;
	$.post(
		path,
		{
			id:parent_row.id,
			managerId:row.id
		},
		function(data){
			successTip(data,parentDataGrid,null);
		}
	)
} 


</script>
</body>
</html>