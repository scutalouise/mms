<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
</head>
<body class="easyui-layout" style="font-family: '微软雅黑'">   
    <div data-options="region:'center',split:true,border:false,title:'内部用户列表'">
    	<div id="innerUserToolbar" style="padding:5px;height:auto">
		    <div>
	        	<form id="searchFrom" action="">
	       	        <input type="text" name="filter_LIKES_name" class="easyui-validatebox" data-options="width:150,prompt: '昵称'"/>
	       	        <input type="text" name="filter_LIKES_phone" class="easyui-validatebox" data-options="width:150,prompt: '电话'"/>
			        <input type="text" id="startDate" name="filter_GTD_createDate" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width:150,prompt: '开始日期(创建)'" />
			        - <input type="text" id="endDate" name="filter_LTD_createDate" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width:150,prompt: '结束日期(创建)'"/>
			        <span class="toolbar-item dialog-tool-separator"></span>
			        <a href="javascript(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="cx()">查询</a>
				</form>
        	</div> 
		</div>
		<table id="innerUserDatagrid"></table>
    </div>   
    <div data-options="region:'east',split:true,border:false,title:'负责机构列表'" style="width: 425px">
    	<div id="innerOrgToolbar" style="padding:5px;height:auto">
		    <div>
		    	<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="save();">保存授权机构</a>
		    	<span class="toolbar-item dialog-tool-separator"></span>
		        <a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="back()">恢复</a>
		    </div>
		</div>
    	<table id="innerOrgDialog"></table>
    </div>   
 
<script type="text/javascript">
var innerUserDatagrid;	//内部人员datagrid
var innerOrgDialog;	//内部组织机构datagrid
var innerUserOrgs;	//用户负责的组织机构
var innerUserId;	//单击选中的user
$(function(){   
	innerUserDatagrid=$('#innerUserDatagrid').datagrid({    
	method: "POST",
    url:'${ctx}/system/user/json', 
    fit : true,
	fitColumns : true,
	border : false,
	idField : 'id',
	pagination:true,
	rownumbers:true,
	pageNumber:1,
	pageSize : 10,
	pageList : [ 10, 20, 30, 40, 50 ],
	singleSelect:true,
	striped:true,
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
        }},
        {field : 'action',title : '操作',
			formatter : function(value, row, index) {
				return '<a href="javascript:lookP('+row.id+')"><div class="icon-hamburg-lock" style="width:16px;height:16px" title="查看权限"></div></a>';
			}
        }
    ]],
    onSelect:function(){
    	getSeletedRow();
    },
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false,
    toolbar:'#innerUserToolbar'
	});
	
	innerOrgDialog=$('#innerOrgDialog').treegrid({   
		method: "POST",
	    url:'${ctx}/system/organization/json', 
	    fit : true,
		fitColumns : true,
		border : false,
		idField : 'id',
		treeField:'orgName',
		parentField : 'pid',
		iconCls: 'icon',
		animate:true, 
		rownumbers:true,
		striped:true,
		singleSelect:false,//需设置  
	    columns:[[    
			{field:'ck',checkbox:true,hidden:true,width:100},   
	        {field:'id',title:'id',hidden:true,width:100},    
	        {field:'orgName',title:'名称',width:180},
	        {field:'orgCode',title:'机构代码',width:35},
	        {field:'orgType',title:'类型',width:35,tooltip: true}
	    ]],
	    onClickRow:function(row){  
            //级联选择  
            $(this).treegrid('cascadeCheck',{  
                id:row.id, //节点ID  
                deepCascade:true //深度级联  
            });  
        },
        toolbar:'#innerOrgToolbar'
		});
});

//辅助函数，当选中角色的行记录的时候，对应选中其权限
function getSeletedRow(){
	var row = $('#innerUserDatagrid').datagrid("getSelected");
	if(row){
		lookP(row.id);
	}
}

//查看机构
function lookP(innerUserId){
	//清空勾选的机构
	if(innerUserOrgs){
		innerOrgDialog.treegrid('unselectAll');
		innerUserOrgs=[];//清空
	}
	//获取用户负责的网点
	$.ajax({
		async:false,
		type:'get',
		url:"${ctx}/device/innerUser/"+innerUserId+"/OrgIdList",
		success: function(data){
			if(typeof data=='object'){
				innerUserOrgs=data;
				for(var i=0,j=data.length;i<j;i++){
					innerOrgDialog.treegrid('select',data[i]);
				}
			}else{
				$.easyui.messager.alert(data);
			} 
		}
	});
}

//保存修改负责的机构
function save(){
	var row = innerUserDatagrid.datagrid('getSelected');
	if(rowIsNull(row)) return;
	var innerUserId=row.id;
	parent.$.messager.confirm('提示', '确认要保存修改？', function(data){
		if (data){
			var newPermissionList=[];
			var data=innerOrgDialog.treegrid('getSelections');
			for(var i=0,j=data.length;i<j;i++){
				newPermissionList.push(data[i].id);
			}
			if(innerUserId==null) {
				parent.$.messager.show({ title : "提示",msg: "请选择角色！", position: "bottomRight" });
				return;
			}
			$.ajax({
				async:false,
				type:'POST',
				data:JSON.stringify(newPermissionList),
				contentType:'application/json;charset=utf-8',
				url:"${ctx}/device/innerUser/"+innerUserId+"/updateOrg",
				success: function(data){
					successTip(data);
				}
			});
		} 
	});
}

//恢复权限选择
function back(){
	var row = innerUserDatagrid.datagrid('getSelected');
	lookP(row.id);
}
</script>
</body>
</html>