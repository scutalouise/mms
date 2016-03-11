<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/views/include/easyui.jsp" %>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/raty/jquery.raty.js" type="text/javascript"></script>
<link href="${ctx}/static/css/formui.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div id="tb" style="padding: 5px;height: auto;">
   <div>
	<form id="searchForm" action="">
       	        <input type="text" id="identifierFind" name="problemCode" class="easyui-validatebox" data-options="width:150,prompt: '问题编号'"/>
       	        <input type="text" id="problemTypeFind" name="problemTypeId" class="easyui-validatebox" data-options="width:150,prompt: '问题类型'"/>
       	        <input type="text" id="enableFind" name="enable" class="easyui-validatebox" data-options="width:150,prompt: '状态'"/>
		        <input type="text" id="startDate" name="recordTime" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width:150,prompt: '开始日期(登记)'" />
		        - <input type="text" id="endDate" name="recordEndTime" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width:150,prompt: '结束日期(登记)'"/>
		        <span class="toolbar-item dialog-tool-separator"></span>
		        <a href="javascript(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="cx()">查询</a>
		</form>
     <shiro:hasPermission name="maintenance:problem:add">
    	 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">添加</a>
    	 <span class="toolbar-item dialog-tool-separator"></span>
     </shiro:hasPermission>
     <shiro:hasPermission name="maintenance:problem:delete">
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del();">删除</a>
         <span class="toolbar-item dialog-tool-separator"></span>
     </shiro:hasPermission>
     <shiro:hasPermission name="maintenance:problem:update">
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd();">修改</a>
    	 <span class="toolbar-item dialog-tool-separator"></span>
    	 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="assign();">分配问题</a>
    	 <span class="toolbar-item dialog-tool-separator"></span>
    	 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="shutdown();">关闭问题</a>
     </shiro:hasPermission>
         <span class="toolbar-item dialog-tool-separator"></span>
    	 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="view();">处理历史记录</a>
   </div>
</div>

   <table id="dg"></table>
   <div id="dlg"></div>
   <div id="closedlg"></div>
<script type="text/javascript">
	var dg;
	var dlg;
	var cdlg
	$(function(){
				
		dg=$('#dg').datagrid({
			  method:"get",
			  url:'${ctx}/maintenance/problem/json?sort=recordTime&order=desc',
			  fit:true,
			  fitColumns:true,
		      animate:true,
		      striped:true,
		  	  pagination:true,
			  rownumbers:true,
			  pageNumber:1,
			  pageSize : 20,
			  pageList : [ 10, 20, 30, 40, 50 ],
		      singleSelect:true,
		      columns:[[
		          {
		        	  field:'id',
		        	  title:'id',
		        	  hidden:true
		        }, {
		        	field:'problemCode',
		        	title:'问题编号',
		        	sortable:true,
		        	width:100
		        }, {
		        	field:'problemType',
		        	title:'问题类型',
		        	width:100
		        }, {
		        	field:'deviceName',
		        	title:'设备名称',
		        	width:100
		        }, {
		        	field:'orgName',
		        	title:'所属网点',
		        	width:100
		        }, {
		        	field:'recordUserName',
		        	title:'登记人',
		        	width:100
		        }, {
		        	field:'recordTime',
		        	title:'登记时间',
		        	sortable:true,
		        	formatter : function(value, row, index) {
		        		return formatDate(value,"yyyy-MM-dd HH:mm:ss");
		        	}
		        }, {
		        	field:"reportWay",
		        	title:"上报渠道",
		        	width:100,
		        	formatter : function(value, row, index) {
		        		return value.name;
		        	}
		        }, {
		        	field:'enable',
		        	title:'问题状态',
		        	sortable:true,
		        	width:100,
		        	formatter : function(value, row, index) {
		        		return value.name;
		        	}
		        }, {
		        	field:'resolveUserName',
		        	title:'解决人',
		        	width:100
		        }, {
		        	field:'resolveTime',
		        	title:'解决时间',
		        	sortable:true,
		        	width:100,
		        	formatter : function(value, row, index) {
		        		return formatDate(value,"yyyy-MM-dd HH:mm:ss");
		        	}
		        }
		      ]],
		      toolbar:'#tb',
		      onDblClickRow : function(index, row) {
		    	  var id = row.id;
		    	  details(id);
		      }
		  });
		
		$.ajax({
			url:'${ctx}/maintenance/problemType/list',
			type : "get",
			dataType : "json",
			success : function(data) {
				var json = {"id":"","name":"—全部—"};
				data.push(json);
				data.reverse();
				$('#problemTypeFind').combobox({
					  valueField:'id',
					  textField:'name',
					  data : data
				  });
			}
		});
		
		$('#enableFind').combobox({
			  method:"get",
			  url:'${ctx}/maintenance/problem/enable/handle/false/search/true',
			  valueField:'problemStatus',
			  textField:'name'
		  });
		
		
		initDateFilter("startDate","endDate");
	});
	
	 //添加弹窗
	function add(){
		  dlg=$('#dlg').dialog({
			  title:'新增问题记录',
			  iconCls:'icon-add',
			  width:400,
			  height:350,
			  href:'${ctx}/maintenance/problem/create',
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
	 
	 function details(id) {
		 dlg=$('#dlg').dialog({
			  title:'问题记录详情',
			  width:550,
			  height:450,
			  href:'${ctx}/maintenance/problem/details/' + id,
			  modal:true,
			  buttons:[
			   {
				  text:'关闭',
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
			  parent.$.messager.confirm('提示','您确定要删除该记录？',function(data){
				  if(data){
					  $.ajax({
						  type:'get',
						  url:'${ctx}/maintenance/problem/delete/'+row.id,
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
	  
	  //修改弹窗
	  function upd(){
		  var row=dg.datagrid('getSelected');
		  if(rowIsNull(row)) return;
			  dlg=$('#dlg').dialog({
				  title:'修改问题记录',
				  width:400,
				  height:350,
				  iconCls:'icon-edit',
				  href:'${ctx}/maintenance/problem/update/'+row.id,
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
	  
	  function cx(){
			var obj=$("#searchForm").serializeObject();
			dg.datagrid('load',obj); 
		}
	  
	  function assign(){
		  var row=dg.datagrid('getSelected');
		  if(rowIsNull(row)) return;
		  var enable = row.enable.problemStatus;
		  if (enable == "NEW" || enable == "CALLBACK" || enable == "RESOLVED") {
			  dlg=$('#dlg').dialog({
				  title:'问题分配',
				  width:350,
				  height:200,
				  iconCls:'icon-edit',
				  href:'${ctx}/maintenance/problem/assign/'+row.id,
				  modal:true,
				  buttons:[{
						text:'保存',
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
		  } else {
			  parent.$.messager.alert("警告：","只有新建、打回或已解决的问题才能进行分配！");
		  }
			  
	  }
	  
	//删除
	  function shutdown(){
		  var row=dg.datagrid('getSelected');
		  if(rowIsNull(row)) return;
		  var enable = row.enable.problemStatus;
		  if (enable == "NEW" || enable == "CALLBACK") {
			  parent.$.messager.confirm('提示','问题关闭后不能恢复，您确定要关闭该问题吗？',function(data){
				  if(data){
					  $.ajax({
						  type:'post',
						  url:'${ctx}/maintenance/problem/update',
						  data : {"id" : row.id, "enable" : "CLOSED"},
						  success:function(data){
							  if(data=='success'){
									parent.$.messager.show({ title : "提示",msg: "操作成功！", position: "bottomRight" });
									dg.datagrid('reload');
							 }
						  }
					  });
				  }
			  });
		  } else if (enable == "RESOLVED") {
			  cdlg=$('#closedlg').dialog({
				  title:'提示：',
				  width:350,
				  height:250,
				  modal:true,
				  href:'${ctx}/maintenance/problem/shutdown/'+row.id,
				  buttons:[{
						text:'确定',
						handler:function(){
							$('#closeForm').submit(); 
						}
					},{
						text:'取消',
						handler:function(){
							 cdlg.panel('close');
					    }
					}]
				});
		  } else {
			  parent.$.messager.alert("警告：","只有新建、打回或已解决的问题才能关闭！");
		  }
	  }
	
	  function view(){
		  var row=dg.datagrid('getSelected');
		  if(rowIsNull(row)) return;
			  dlg=$('#dlg').dialog({
				  title:'问题处理过程记录',
				  width:800,
				  height:500,
				  iconCls:'icon-search',
				  href:'${ctx}/maintenance/handling/list/problemId/'+row.id,
				  modal:true,
				  buttons:[{
						text:'关闭',
						handler:function(){
							 dlg.panel('close');
					    }
					}]
				});
		}
	
</script>

</body>
</html>
	
	
	
	
	
	
	
	
	
	
	
	
	
	