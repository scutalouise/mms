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
     <shiro:hasPermission name="device:userDeviceType:add">
    	 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">添加</a>
    	 <span class="toolbar-item dialog-tool-separator"></span>
     </shiro:hasPermission>
     <shiro:hasPermission name="device:userDeviceType:delete">
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del();">删除</a>
         <span class="toolbar-item dialog-tool-separator"></span>
     </shiro:hasPermission>
     <shiro:hasPermission name="device:userDeviceType:update">
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
	  dg=$('#dg').treegrid({
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
	      columns:[[
	          {field:'id',title:'id',hidden:true},
	          {field:'name',title:'设备类型',sortable:true,width:100},
	          {field:'otherNote',title:'描述',sortable:true,width:100}
	      ]],
	      toolbar:'#tb'
	  });
  });
  
  //添加弹窗
function add(){
	  dlg=$('#dlg').dialog({
		  title:'添加设备类型',
		  iconCls:'icon-add',
		  width:300,
		  height:300,
		  href:'${ctx}/device/userDeviceType/add',
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
	  var row=dg.treegrid('getSelected');
	  if(rowIsNull(row)) return;
	  parent.$.messager.confirm('提示','删除后无法恢复,您确定要删除？',function(data){
		  if(data){
			  $.ajax({
				  type:'get',
				  url:'${ctx}/device/userDeviceType/delete/'+row.id,
				  success:function(data){
					  if(successTip(data,dg)){
				    		dg.treegrid('reload');
				    		dg.treegrid('clearSelections');
					  }
				  }
			  });
		  }
	  });
  }
  
  //修改弹窗
  function upd(){
	  var row=dg.treegrid('getSelected');
	  if(rowIsNull(row)) return;
	  dlg=$('#dlg').dialog({
		  title:'修改设备类型',
		  width:300,
		  height:300,
		  iconCls:'icon-edit',
		  href:'${ctx}/device/userDeviceType/update/'+row.id,
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