<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/views/include/easyui.jsp" %>
</head>
<body>
<div id="tb" style="padding: 5px;height: auto;">
   <div>
   <form id="searchForm" action="">
   		<input type="text" id="typeName" name="filter_LIKES_name" class="easyui-validatebox" data-options="width:150,prompt: '问题名称'"/>
       	<input type="text" id="deviceType" name="filter_EQ_FirstDeviceType_deviceType" class="easyui-combobox" data-options="width:150,prompt: '设备类型'"/>
		<span class="toolbar-item dialog-tool-separator"></span>
		<a href="javascript(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="cx()">查询</a>
	</form>
     <shiro:hasPermission name="maintenance:problemType:add">
    	 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">添加</a>
    	 <span class="toolbar-item dialog-tool-separator"></span>
     </shiro:hasPermission>
     <shiro:hasPermission name="maintenance:problemType:delete">
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del();">删除</a>
         <span class="toolbar-item dialog-tool-separator"></span>
     </shiro:hasPermission>
     <shiro:hasPermission name="maintenance:problemType:update">
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
		  method:"post",
		  url:'${ctx}/maintenance/problemType/json?filter_EQ_StatusEnum_status=NORMAL',
		  fit:true,
		  fitColumns:true,
	      animate:true,
	      striped:true,
	      rownumbers:true,
	      pagination:true,
	      pageNumber:1,
		  pageSize : 20,
	      singleSelect:true,
	      columns:[[
	          {
	        	  field:'id',
	        	  title:'id',
	        	  hidden:true
	        }, {
	        	field:'name',
	        	title:'问题类型名称',
	        	sortable:true,
	        	width:100
	        }, {
	        	field:'deviceType',
	        	title:'适用设备类型',
	        	sortable:true,
	        	width:100,
	        	formatter : function(value, row, index) {
	        		return value.name;
	        	}
	        }, {
	        	field:"initial",
	        	title:"是否初始化",
	        	width:100,
	        	formatter : function(value, row, index) {
	        		var str = "";
	        		if (value) {
	        			str = "是";
	        		} else {
	        			str = "否";
	        		}
	        		return str;
	        	}
	        }, {
	        	field:'otherNote',
	        	title:'描述',
	        	sortable:true,
	        	width:400
	        }
	      ]],
	      toolbar:'#tb'
	  });
	  
	  $('#deviceType').combobox({
		  method:"get",
		  url:'${ctx}/maintenance/problemType/deviceType',
		  valueField:'value',
		  textField:'name'
	  });
	  
  });
  
  //添加弹窗
function add(){
	  dlg=$('#dlg').dialog({
		  title:'添加问题类型',
		  iconCls:'icon-add',
		  width:350,
		  height:300,
		  href:'${ctx}/maintenance/problemType/create',
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
	  var initial = row.initial;
	  if (initial) {
		  parent.$.messager.alert("警告：","该记录为系统初始化数据，不能进行删除！");
	  } else {
		  parent.$.messager.confirm('提示','您确定要删除该记录？',function(data){
			  if(data){
				  $.ajax({
					  type:'get',
					  url:'${ctx}/maintenance/problemType/delete/'+row.id,
					  success:function(data){
						  if(data=='success'){
								parent.$.messager.show({ title : "提示",msg: "操作成功！", position: "bottomRight" });
								dg.datagrid('reload');
						 }
					  }
				  });
			  }
		  });
		  
	  }
  }
  
  function cx() {
		var type = $("#typeName").val();
		var deviceType = $("#deviceType").combobox("getValue");
		dg.datagrid('load',{
			"filter_EQ_FirstDeviceType_deviceType" : deviceType,
			"filter_LIKES_name" : type
		});
  }
  
  //修改弹窗
  function upd(){
	  var row=dg.datagrid('getSelected');
	  if(rowIsNull(row)) return;
	  var initial = row.initial;
	  if (initial) {
		  parent.$.messager.alert("警告：","该记录为系统初始化数据，不能进行修改！");
	  } else {
		  dlg=$('#dlg').dialog({
			  title:'修改问题类型',
			  width:350,
			  height:300,
			  iconCls:'icon-edit',
			  href:'${ctx}/maintenance/problemType/update/'+row.id,
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
	}
</script>
</body>
</html>
	
	
	
	
	
	