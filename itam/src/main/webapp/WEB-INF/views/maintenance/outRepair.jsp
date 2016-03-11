<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/views/include/easyui.jsp" %>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
</head>
<body>

<div class="easyui-layout" data-options="fit:true">
		<!-- Left Panel -->
		<div data-options="region:'west',split:false,width:400,title:'待外修设备'">
			<div id="device_tb">
				<div>
     				<shiro:hasPermission name="maintenance:outsideRepair:add">
    	 				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">发起外修</a>
    					 <span class="toolbar-item dialog-tool-separator"></span>
					</shiro:hasPermission>
				</div>
			</div>
			<div id="device_dg"></div>
			<div id="device_dlg"></div>
		</div>
		<div data-options="region:'center',split:false,title:'外修记录'">
			<div id="repair_tb">
				<div>
				     <shiro:hasPermission name="maintenance:outsideRepair:update">
				         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd();">编辑</a>
				         <span class="toolbar-item dialog-tool-separator"></span>
				     </shiro:hasPermission>
				     <shiro:hasPermission name="maintenance:outsideRepair:delete">
				         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del();">删除</a>
				     </shiro:hasPermission>
			   </div>
			</div>
			<div id="repair_dg"></div>
			<div id="repair_dlg"></div>
		</div>
	</div>

   <script type="text/javascript">
	var device_dg;
	var device_dlg;
	var repair_dg;
	var repair_dlg;
	
	$(function(){
		
		device_dg = $("#device_dg").datagrid({
			  method:"get",
			  url:'${ctx}/maintenance/outsideRepair/device/list',
			  fit:true,
			  fitColumns:true,
		      animate:true,
		      striped:true,
		  	  pagination:true,
			  rownumbers:true,
			  idField : "id",
			  pageNumber:1,
			  pageSize : 20,
		      singleSelect:false,
		      columns:[[
		          {
		        	  field:'id',
		        	  checkbox:true
		        }, {
		        	field:'name',
		        	title:'设备名称',
		        	width:100
		        }, {
		        	field:'identifier',
		        	title:'设备编号',
		        	width:100
		        }
		      ]],
		      toolbar:'#device_tb'
		  });
		
		
		repair_dg=$('#repair_dg').datagrid({
			  method:"get",
			  url:'${ctx}/maintenance/outsideRepair/json',
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
		        	field:'deviceName',
		        	title:'返修设备',
		        	sortable:true,
		        	width:100
		        }, {
		        	field:'firmName',
		        	title:'返修厂家',
		        	width:100
		        }, {
		        	field:'repairReceiverName',
		        	title:'返修收件人',
		        	width:100
		        }, {
		        	field:'repairTime',
		        	title:'登记时间',
		        	sortable:true,
		        	formatter : function(value, row, index) {
		        		return formatDate(value,"yyyy-MM-dd HH:mm:ss");
		        	}
		        }, {
		        	field:'repairResult',
		        	title:'返修结果',
		        	width:100,
		        	formatter : function(value, row, index) {
		        		var str = "";
		        		if (value == "true") {
		        			str =  "返修完成，设备入库";
		        		} else if (value == "false") {
		        			str =  "返修失败，设备报废";
		        		} else {
		        			str = "外修中";
		        		}
		        		return str;
		        	}
		        }
		      ]],
		      toolbar:'#repair_tb'
		  });
		
	});
	
	function add(){
		var rows=device_dg.datagrid('getSelections');
		if (rows.length > 0) {
			var ids = "";
			for (var i = 0; i < rows.length; i++) {
				ids += rows[i].identifier + ",";
			}
			device_dlg=$('#device_dlg').dialog({
				  title:'登记外修记录',
				  iconCls:'icon-add',
				  width:300,
				  height:250,
				  href:'${ctx}/maintenance/outsideRepair/create/' + ids,
				  modal:true,
				  buttons:[{
					  text:'确认',
					  handler:function(){
						  $('#device_mainform').submit();
					  }
				   },
				   {
					  text:'取消',
					  handler:function(){
						  device_dlg.panel('close');
					  }
				    }]
			  })
		} else {
			parent.$.messager.show({ title : "提示",msg: "请选择行数据！", position: "bottomRight" });
			return;
		}
	}
	
	function del() {
		var row=repair_dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		  parent.$.messager.confirm('提示','您确定要删除该记录？',function(data){
			  if(data){
				  $.ajax({
					  type:'get',
					  url:'${ctx}/maintenance/outsideRepair/delete/'+row.id,
					  success:function(data){
						  if(data=='success'){
								parent.$.messager.show({ title : "提示",msg: "操作成功！", position: "bottomRight" });
								repair_dg.datagrid('reload');
						 }
					  }
				  });
			  }
		  });
	}
	
	function upd() {
		var row=repair_dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		var result = row.repairResult;
		if (result != null && result != "") {
			parent.$.messager.alert("警告：","设备外修流程完毕，无法修改记录！");
		} else {
			repair_dlg=$('#repair_dlg').dialog({
				  title:'编辑',
				  iconCls:'icon-edit',
				  width:300,
				  height:250,
				  href:'${ctx}/maintenance/outsideRepair/update/' + row.id,
				  modal:true,
				  buttons:[{
					  text:'确认',
					  handler:function(){
						  $('#repair_mainform').submit();
					  }
				   },
				   {
					  text:'取消',
					  handler:function(){
						  repair_dlg.panel('close');
					  }
				    }]
			  });
		}
			
	}
	
	
	</script>
</body>
</html>