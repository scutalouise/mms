<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/views/include/easyui.jsp" %>
</head>
<body>
<div id="tb" style="padding: 5px;height: auto;">
   <div>
     <shiro:hasPermission name="device:brand:add">
    	 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">添加</a>
    	 <span class="toolbar-item dialog-tool-separator"></span>
     </shiro:hasPermission>
     <shiro:hasPermission name="device:brand:delete">
    	 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del();">删除</a>
    	 <span class="toolbar-item dialog-tool-separator"></span>
     </shiro:hasPermission>
     <shiro:hasPermission name="device:brand:update">
    	 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd();">修改</a>
     </shiro:hasPermission>
   </div>
</div>
   <table id="dg"></table>
   <div id="dlg"></div>
<script type="text/javascript">
  var dg;
  var dlg;
  $(function(){
	  dg=$('#dg').datagrid({
		  method:"get",
		  url:'${ctx}/device/brand/json',
		  queryParams:{
				filter_EQE_status : 'NORMAL',
			},
		  fit:true,
		  fitColumns:true,
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
	          {field:'name',title:'品牌名',sortable:true,width:100},
	          {field:'firstDeviceType',title:'一级设备类型',sortable:true,width:100,
	        	  formatter:function(data){
	        		  return data.name;
	        	  }},
	          {field:'secondDeviceType',title:'二级设备类型',sortable:true,width:100,
	        	  formatter:function(data){
	        		  return data.name;  
	        	  }},
	          {field:'enable',title:'是否启用',sortable:true,width:100,
	        		  formatter:function(data){
	        			  if(data=='ENABLED'){
	        				  return "启用";
	        			  }else
	        			      return "禁用";
	        		  }},
	          {field:'isInitial',title:'是否初始化数据',sortable:true,width:100,
	        			  formatter:function(data){
	        				  if(data=='INIT'){
	        					  return "系统初始化数据";
	        				  }else
	        					  return "";
	        			  }},
	          {field:'otherNote',title:'描述',sortable:true,width:100}
	      ]],
	      toolbar:'#tb'
	  });
  });
  
  //添加弹窗
function add(){
	  dlg=$('#dlg').dialog({
		  title:'添加品牌',
		  iconCls:'icon-add',
		  width:330,
		  height:330,
		  href:'${ctx}/device/brand/add',
		  modal:true,
		  buttons:[{
			  text:'确认',
			  handler:function(){
				  $('#mainform').submit();
			  }
		   },
		   {
			  text:'取消',
			  handler:function(){
				  dlg.panel('close');
			  }
		    }]
	  })
  }
  
  //删除
  function del(){
	  var row=dg.datagrid('getSelected');
	  if(rowIsNull(row)) return;
	  parent.$.messager.confirm('提示','删除后无法恢复,您确定要删除？',function(data){
		  if(data){
			  $.ajax({
				  type:'get',
				  url:'${ctx}/device/brand/delete/'+row.id,
				  success:function(data){
					  if(data=='success'){
							parent.$.messager.show({ title : "提示",msg: "操作成功！", position: "bottomRight" });
							dg.datagrid('reload');
						}else{
							parent.$.messager.alert('提示','数据已被绑定不能删除','info');
						}
				  }
			  });
		  }
	  });
  }
  
  //修改弹窗
  function upd(){
	  var row=dg.datagrid('getSelected');
	  if(rowIsNull(row)) return;
	  dlg=$('#dlg').dialog({
		  title:'修改品牌',
		  width:330,
		  height:330,
		  iconCls:'icon-edit',
		  href:'${ctx}/device/brand/update/'+row.id,
		  modal:true,
		  buttons:[{
				text:'修改',
				handler:function(){
					$('#mainform').submit(); 
				}
			},{
				text:'取消',
				handler:function(){
					 dlg.panel('close');
					}
			}]
		});
	}
</script>
</body>
</html>