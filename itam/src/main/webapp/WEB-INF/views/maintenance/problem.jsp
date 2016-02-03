<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/views/include/easyui.jsp" %>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
</head>
<body>
<div id="tb" style="padding: 5px;height: auto;">
   <div>
		<form id="searchForm" action="">
       	        <input type="text" id="identifierFind" name="filter_LIKES_identifier" class="easyui-validatebox" data-options="width:150,prompt: '设备编号'"/>
       	        <input type="text" id="problemTypeFind" name="filter_EQI_problemTypeId" class="easyui-validatebox" data-options="width:150,prompt: '问题类型'"/>
       	        <input type="text" id="enableFind" name="filter_EQ_ProblemStatusEnum_enable" class="easyui-validatebox" data-options="width:150,prompt: '状态'"/>
		        <input type="text" id="startDate" name="filter_GTD_recordTime" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width:150,prompt: '开始日期(登记)'" />
		        - <input type="text" id="endDate" name="recordEndTime" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width:150,prompt: '结束日期(登记)'"/>
		        <span class="toolbar-item dialog-tool-separator"></span>
		        <a href="javascript(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="cx()">查询</a>
		        <!-- <span class="toolbar-item dialog-tool-separator"></span>
		        <a href="javascript(0)" class="easyui-linkbutton" iconCls="icon-mini-refresh" plain="true" onclick="reset()">重置</a> -->
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
			  url:'${ctx}/maintenance/problem/json?filter_EQE_status=NORMAL',
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
		        	field:'problemType',
		        	title:'问题类型',
		        	sortable:true,
		        	width:100
		        }, {
		        	field:'identifier',
		        	title:'设备编号',
		        	sortable:true,
		        	width:100
		        }, {
		        	field:'recordUserName',
		        	title:'登记人',
		        	sortable:true,
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
		        	sortable:true,
		        	width:100
		        }
		      ]],
		      toolbar:'#tb'
		  });
		
		$('#problemTypeFind').combobox({
			  method:"get",
			  url:'${ctx}/maintenance/problemType/json',
			  valueField:'id',
			  textField:'name'
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
			  height:400,
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
				  height:400,
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
	  
	  /* function reset(){
		  $("#identifierFind").val("");
		  $("#problemTypeFind").combobox().clear;
		  $("#enableFind").combobox().clear;
		  $("#startDate").my97("setValue","");
		  $("#endDate").my97("setValue","");
	  } */
	
</script>

</body>
</html>
	
	
	
	
	
	
	
	
	
	
	
	
	
	