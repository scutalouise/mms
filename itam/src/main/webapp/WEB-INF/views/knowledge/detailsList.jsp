<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>知识库分类</title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script type="text/javascript" src="${ctx }/static/js/obtain/obtainDeviceList.js"></script>
<style type="text/css">
.formTable td {
	line-height: 30px;
}
.formTable {
	margin: 0px;
	padding: 10px;
}
</style>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div
			data-options="region:'west',title:'知识库分类',iconCls:'icon-hamburg-world',split:true, minWidth: 200, maxWidth: 400,width:220">
			<ul id="classificationTree"></ul>
		</div>
		<div data-options="region:'center'">
			<div id="detailsToolBar" style="padding:5px;height:auto">
			    <div>
	    			<form id="detailsSearchFrom" action="">
						<input type="text" name="filter_LIKES_keyword" class="easyui-validatebox" data-options="width:150,prompt: '包含的关键字'"/>
						<input type="text" name="filter_LIKES_title" class="easyui-validatebox" data-options="width:150,prompt: '标题'"/>
				        <input type="text" id="startDate" name="filter_GTD_commitTime" class="easyui-my97" datefmt="yyyy-MM-dd HH:mm:ss" data-options="width:150,prompt: '构建起始日期'" />
				        - <input type="text" id="endDate" name="filter_LTD_commitTime" class="easyui-my97" datefmt="yyyy-MM-dd HH:mm:ss" data-options="width:150,prompt: '构建结束日期'"/>
				        <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
					</form>
			     </div> 
			     <div>
			         	<%-- <shiro:hasPermission name="sys:perm:add"> --%>
				    	<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-add" onclick="add();">添加</a>
				    	<span class="toolbar-item dialog-tool-separator"></span>
				    	<%-- </shiro:hasPermission>
				        <shiro:hasPermission name="sys:perm:delete"> --%>
				        <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-remove" onclick="del()">删除</a>
				        <span class="toolbar-item dialog-tool-separator"></span>
				       <%--  </shiro:hasPermission>
				        <shiro:hasPermission name="sys:perm:update"> --%>
				        <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-edit" onclick="upd()">修改</a>
				        <span class="toolbar-item dialog-tool-separator"></span>
				        <%-- </shiro:hasPermission> --%>
			     </div>
			</div>
			<table class="easyui-datagrid" id="detailsDatagrid"></table>
			<div id="detailsDialog"></div> 
		</div>
	</div>
<script type="text/javascript">
var detailsDatagrid;
var classificationId;//选中的知识库分类
var detailsDialog;//在处理增删改查；
$(function(){
	$("#classificationTree").tree(
			{
				method : "get",
				url : ctx + "/knowledge/classification/tree",
				onBeforeExpand : function(node, params) {
					$(this).tree("options").url = ctx
							+ "/knowledge/classification/tree?pid=" + node.id
				},
				onSelect : function(node) {
					classificationId = node.id;
					//$("#tab_devices").tabs("refresh", tabIndex);// 刷新下知识库部分；
					//处理知识库的详情部分:选择了一个知识库分类时的动作；
					detailsDatagrid=$('#detailsDatagrid').datagrid({  
						method: "get",
					    url:'${ctx}/knowledge/details/json', 
					    queryParams:{filter_EQI_classificationId: classificationId},
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
					    columns:[[    
					        {field:'id',title:'id',hidden:true,width:100},    
					        {field:'keyword',title:'关键字',width:100},
					        {field:'title',title:'标题',width:100},
					        {field:'commitTime',title:'构建时间',width:120,sortable:true,formatter: function(value,row,index){
					        	return formatDate(value,"yyyy-MM-dd HH:mm:ss")
					        }},
					        {field:'updateTime',title:'上一次更新时间',width:120, sortable:true,formatter: function(value,row,index){
					        	return formatDate(value,"yyyy-MM-dd HH:mm:ss")
					        }}
					    ]],
					    enableHeaderClickMenu: false,
					    enableHeaderContextMenu: false,
					    enableRowContextMenu: false,
					    toolbar:'#detailsToolBar',
					    dataPlain: true
					});
				}
			});
	
	//处理知识库的详情部分:默认加载的部分；
	detailsDatagrid=$('#detailsDatagrid').datagrid({  
		method: "get",
	    url:'${ctx}/knowledge/details/json', 
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
	    columns:[[    
	        {field:'id',title:'id',hidden:true,width:100},    
	        {field:'keyword',title:'关键字',width:100},
	        {field:'title',title:'标题',width:100},
	        {field:'commitTime',title:'构建时间',width:120,sortable:true,formatter: function(value,row,index){
	        	return formatDate(value,"yyyy-MM-dd HH:mm:ss")
	        }},
	        {field:'updateTime',title:'上一次更新时间',width:120, sortable:true,formatter: function(value,row,index){
	        	return formatDate(value,"yyyy-MM-dd HH:mm:ss")
	        }}
	    ]],
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    toolbar:'#detailsToolBar',
	    dataPlain: true
	});
	
});


//弹窗增加
function add() {
	detailsDialog=$('#detailsDialog').dialog({    
	    title: '添加知识库',    
	    width: 450,    
	    height: 520,    
	    closed: false,    
	    cache: false,
	    maximizable:true,
	    resizable:true,
	    href:'${ctx}/knowledge/details/create',
	    modal: true,
	    buttons:[{
			text:'确认',
			handler:function(){
				$("#mainform").submit();
			}
		},{
			text:'取消',
			handler:function(){
					detailsDialog.panel('close');
				}
		}]
	});
}

//删除
function del(){
	var row = detailsDatagrid.datagrid('getSelected');
	if(rowIsNull(row)) return;
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/knowledge/details/delete/"+row.id,
				success: function(data){
					if(successTip(data,detailsDatagrid))
						detailsDatagrid.datagrid('reload');
				}
			});
		} 
	});
}

//修改
function upd(){
	var row = detailsDatagrid.datagrid('getSelected');
	if(rowIsNull(row)) return;
	detailsDialog=$("#detailsDialog").dialog({   
	    title: '修改知识库记录详情',    
	    width: 450,    
	    height: 520,    
	    href:'${ctx}/knowledge/details/update/'+row.id,
	    maximizable:true,
	    modal:true,
	    buttons:[{
			text:'确认',
			handler:function(){
				$("#mainform").submit();
			}
		},{
			text:'取消',
			handler:function(){
					detailsDialog.panel('close');
				}
		}]
	});
}
//查询；
function cx(){
	var obj=$("#detailsSearchFrom").serializeObject();
	obj.filter_EQI_classificationId = classificationId;
	detailsDatagrid.datagrid('load',obj); 
}
</script>
</body>
</html>