<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
     <shiro:hasPermission name="maintenance:arrange:add">
    	 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">添加</a>
     </shiro:hasPermission>
     <shiro:hasPermission name="maintenance:arrange:delete">
    	 <span class="toolbar-item dialog-tool-separator"></span>
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del();">删除</a>
     </shiro:hasPermission>
     <shiro:hasPermission name="maintenance:arrange:update">
         <span class="toolbar-item dialog-tool-separator"></span>
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
				  url:'${ctx}/maintenance/arrange/json?filter_EQ_StatusEnum_status=NORMAL',
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
			        	field:'userName',
			        	title:'用户',
			        	sortable:true,
			        	width:100
			        }, {
			        	field:'updateTime',
			        	title:'更新时间',
			        	sortable:true,
			        	width:100,
			        	formatter : function(value, row, index) {
			        		return formatDate(value,"yyyy-MM-dd HH:mm:ss");
			        	}
			        }, {
			        	field:'workDayString',
			        	title:'工作日',
			        	sortable:true,
			        	width:100
			        }, {
			        	field:"workTime",
			        	title:"工作时间",
			        	width:100,
			        	formatter : function(value, row, index) {
			        		return value;
			        	}
			        }, {
			        	field:'enable',
			        	title:'是否有效',
			        	sortable:true,
			        	width:100,
			        	formatter : function(value, row, index) {
			        		return value ? "是" : "否";
			        	}
			        }
			      ]],
			      toolbar:'#tb'
			  });
			
		});
		
		function add(){
			  dlg=$('#dlg').dialog({
				  title:'新增运维排程',
				  iconCls:'icon-add',
				  width:400,
				  height:350,
				  href:'${ctx}/maintenance/arrange/create',
				  modal:true,
				  buttons:[{
					  text:'提交',
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
		
		function del(){
			  var row=dg.datagrid('getSelected');
			  if(rowIsNull(row)) return;
				  parent.$.messager.confirm('提示','您确定要删除该记录？',function(data){
					  if(data){
						  $.ajax({
							  type:'get',
							  url:'${ctx}/maintenance/arrange/delete/'+row.id,
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
		
		function upd(){
			  var row=dg.datagrid('getSelected');
			  if(rowIsNull(row)) return;
				  dlg=$('#dlg').dialog({
					  title:'修改运维排程',
					  width:400,
					  height:350,
					  iconCls:'icon-edit',
					  href:'${ctx}/maintenance/arrange/update/'+row.id,
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	