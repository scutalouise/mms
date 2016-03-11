<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body style="font-family: '微软雅黑'">
<div id="supplyMaintainOrgToolbar" style="padding:5px;height:auto">
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
        <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-edit" onclick="brandList()">供应品牌</a>
        <span class="toolbar-item dialog-tool-separator"></span>
         <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-cog" onclick="configTemplate()">选择模板</a>
        <span class="toolbar-item dialog-tool-separator"></span>
        <%-- </shiro:hasPermission> --%>
        <a href="javascript:void(0)" class="easyui-menubutton" plain="true" data-options="menu:'#exportExcel',iconCls:'icon-standard-page-excel'">Excel</a>
     </div> 
     <div id="exportExcel">
		<div data-options="iconCls:'icon-standard-page-excel'">导入Excel</div>
		<div data-options="iconCls:'icon-standard-page-excel'">导出Excel</div>
	 </div>
</div>
<table id="supplyMaintainOrgDataGrid"></table>
<div id="supplyMaintainOrgDialog"></div> 

<script type="text/javascript">
var supplyMaintainOrgDataGrid;
var supplyMaintainOrgDialog;
var permissionDg;
var parentPermId;
$(function(){   
	supplyMaintainOrgDataGrid=$('#supplyMaintainOrgDataGrid').treegrid({  
	method: "get",
    url:'${ctx}/device/supplyMaintainOrg/json', 
    fit : true,
	fitColumns : true,
	border : false,
	idField : 'id',
	treeField:'orgName',
	parentField : 'pid',
	animate:true, 
	rownumbers:true,
	singleSelect:true,
	striped:true,
    columns:[[    
        {field:'id',title:'id',hidden:true,width:100},    
        {field:'orgName',title:'机构名称',width:100},
        {field:'director',title:'负责人',width:100},
        {field:'orgCode',title:'机构代码',width:100},
        {field:'telephone',title:'电话',width:100},
        {field:'email',title:'邮件',width:100}
    ]],
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false,
    toolbar:'#supplyMaintainOrgToolbar',
    dataPlain: true
	});
	
});

//弹窗增加
function add() {
	//父级权限
	var row = supplyMaintainOrgDataGrid.treegrid('getSelected');
	if(row){
		parentPermId=row.id;
	}
	
	supplyMaintainOrgDialog=$('#supplyMaintainOrgDialog').dialog({    
	    title: '添加菜单',    
	    width: 450,    
	    height: 455,    
	    closed: false,    
	    cache: false,
	    maximizable:true,
	    resizable:true,
	    href:'${ctx}/device/supplyMaintainOrg/create',
	    modal: true,
	    onClose:function (){
	    	appendAndRemoveHost("supplyMaintainOrgDialog");
	    },
	    buttons:[{
			text:'确认',
			handler:function(){
				$("#mainform").submit();
			}
		},{
			text:'取消',
			handler:function(){
				supplyMaintainOrgDialog.panel('close');
				}
		}]
	});
}

//删除
function del(){
	var row = supplyMaintainOrgDataGrid.treegrid('getSelected');
	if(rowIsNull(row)) return;
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/device/supplyMaintainOrg/delete/"+row.id,
				success: function(data){
					if(successTip(data,supplyMaintainOrgDataGrid))
			    		supplyMaintainOrgDataGrid.treegrid('reload');
						supplyMaintainOrgDataGrid.treegrid('clearSelections');
				}
			});
			//supplyMaintainOrgDataGrid.datagrid('reload'); //grid移除一行,不需要再刷新
		} 
	});

}

//修改
function upd(){
	var row = supplyMaintainOrgDataGrid.treegrid('getSelected');
	if(rowIsNull(row)) return;
	//父级权限
	parentPermId=row.pid;
	supplyMaintainOrgDialog=$("#supplyMaintainOrgDialog").dialog({   
	    title: '修改菜单',    
	    width: 450,    
	    height: 455,    
	    href:'${ctx}/device/supplyMaintainOrg/update/'+row.id,
	    maximizable:true,
	    modal:true,
	    onClose:function (){
	    	appendAndRemoveHost("supplyMaintainOrgDialog");
	    },
	    buttons:[{
			text:'确认',
			handler:function(){
				$("#mainform").submit();
			}
		},{
			text:'取消',
			handler:function(){
				supplyMaintainOrgDialog.panel('close');
				}
		}]
	});
}
//var nowIcon;
//var icon_dlg;
//供应商供应的品牌选择；多选
function brandList(){
	var row = supplyMaintainOrgDataGrid.treegrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.supplyOrg == 0){//不是供应商
		parent.$.messager.show({ title : "提示",msg: "非供应商,运维组织不与设备品牌关联！", position: "bottomRight" });
		return true;
	}
	if(row.supplyOrg == 1){//供应商，提供多选
		supplyMaintainOrgDialog=$("#supplyMaintainOrgDialog").dialog({   
		    title: '品牌选择菜单',    
		    width: 750,    
		    height: 655,    
		    href:'${ctx}/device/supplyMaintainOrg/'+ row.id +'/brands/',
		    maximizable:true,
		    modal:true,
		    onClose:function (){
		    	appendAndRemoveHost("supplyMaintainOrgDialog");
		    },
		    buttons:[{
				text:'确认',
				handler:function(){
					getSelectRows();
					$("#mainform").submit();
					supplyMaintainOrgDialog.panel('close');
				}
			},{
				text:'取消',
				handler:function(){
					supplyMaintainOrgDialog.panel('close');
					}
			}]
		});
	}
}

/**
 * 处理dialog,使用同一个div混淆；
 */
function appendAndRemoveHost(divId){
	$("#" +divId + "").dialog("destroy").remove(); //直接摧毁、移除
	$("<div id='"+ divId +"'></div> ").appendTo($('body'))//新加入一个
}
</script>
</body>
</html>