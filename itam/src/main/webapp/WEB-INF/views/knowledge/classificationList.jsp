<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body style="font-family: '微软雅黑'">
<div id="classificationToolBar" style="padding:5px;height:auto">
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
<table id="classificationTreegrid"></table>
<div id="classificationDialog"></div> 
<script type="text/javascript">
var classificationTreegrid;
var classificationDialog;
var parentId;
$(function(){   
	classificationTreegrid=$('#classificationTreegrid').treegrid({  
		method: "get",
	    url:'${ctx}/knowledge/classification/json', 
	    fit : true,
		fitColumns : true,
		border : false,
		idField : 'id',
		treeField:'name',
		parentField : 'pid',
		animate:true, 
		rownumbers:true,
		singleSelect:true,
		striped:true,
	    columns:[[    
	        {field:'id',title:'id',hidden:true,width:100},    
	        {field:'name',title:'知识库分类',width:100},
	        {field:'code',title:'知识库代码',width:100},
	    ]],
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    toolbar:'#classificationToolBar',
	    dataPlain: true
	});
	
});

//弹窗增加
function add() {
	//父级权限
	var row = classificationTreegrid.treegrid('getSelected');
	if(row){
		parentId=row.id;
	}
	
	classificationDialog=$('#classificationDialog').dialog({    
	    title: '添加知识库',    
	    width: 450,    
	    height: 200,    
	    closed: false,    
	    cache: false,
	    maximizable:true,
	    resizable:true,
	    href:'${ctx}/knowledge/classification/create',
	    modal: true,
	    buttons:[{
			text:'确认',
			handler:function(){
				$("#mainform").submit();
			}
		},{
			text:'取消',
			handler:function(){
					classificationDialog.panel('close');
				}
		}]
	});
}

//删除
function del(){
	var row = classificationTreegrid.treegrid('getSelected');
	if(rowIsNull(row)) return;
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/knowledge/classification/delete/"+row.id,
				success: function(data){
					if(successTip(data,classificationTreegrid))
			    		classificationTreegrid.treegrid('reload');
						classificationTreegrid.treegrid('clearSelections');
				}
			});
		} 
	});
}

//修改
function upd(){
	var row = classificationTreegrid.treegrid('getSelected');
	if(rowIsNull(row)) return;
	//父级pid
	parentId=row.pid;
	classificationDialog=$("#classificationDialog").dialog({   
	    title: '修改知识库',    
	    width: 450,    
	    height: 200,    
	    href:'${ctx}/knowledge/classification/update/'+row.id,
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
					classificationDialog.panel('close');
				}
		}]
	});
}
</script>
</body>
</html>