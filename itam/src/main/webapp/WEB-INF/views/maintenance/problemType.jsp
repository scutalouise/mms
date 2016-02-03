<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/views/include/easyui.jsp" %>
</head>
<body>
<div id="tb" style="padding: 5px;height: auto;">
   <div>
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
		  method:"get",
		  url:'${ctx}/maintenance/problemType/json',
		  fit:true,
		  fitColumns:true,
	      animate:true,
	      striped:true,
	      rownumbers:true,
	      singleSelect:true,
	      columns:[[
	          {
	        	  field:'id',
	        	  title:'id',
	        	  hidden:true
	        }, {
	        	field:'name',
	        	title:'设备名称',
	        	sortable:true,
	        	width:100
	        }, {
	        	field:'deviceType',
	        	title:'设备类型',
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
  
  //修改弹窗
  function upd(){
	  var row=dg.datagrid('getSelected');
	  if(rowIsNull(row)) return;
	  var initial = row.initial;
	  if (initial) {
		  parent.$.messager.alert("警告：","该记录为系统初始化数据，不能进行删除！");
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
	
	
	
	
	
	