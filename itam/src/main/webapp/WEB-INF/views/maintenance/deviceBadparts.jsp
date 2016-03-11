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
     <shiro:hasPermission name="maintenance:badDevice:update">
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd();">标记坏件</a>
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
				  url:'${ctx}/maintenance/badDevice/json?filter_deviceUsedState=WAITREPAIR',
				  fit:true,
				  fitColumns:true,
			      animate:true,
			      striped:true,
			  	  pagination:true,
				  rownumbers:true,
				  pageNumber:1,
				  pageSize : 20,
				  pageList : [ 10, 20, 30, 40, 50 ],
			      singleSelect:false,
			      columns : [ [ {
						field : 'id',
						checkbox:true
					}, {
						field : 'name',
						title : '设备名称',
						width : 150
					}, {
						field : 'identifier',
						title : '设备编号',
						width : 150
					}, {
						field : 'hallName',
						title : '所属网点',
						width : 150
					}, {
						field : 'firstDeviceType',
						title : '一级设备类型',
						width : 150,
						formatter : function(value, row, index) {
			        		return value.name;
			        	}
					} ] ],
			      toolbar:'#tb'
			  });
			
		});
				
		function upd(){
			var rows=dg.datagrid('getSelections');
			if (rows.length > 0) {
				  parent.$.messager.confirm('提示','您确定要将这些设备标记为坏件吗？',function(data){
					  if(data){
							var ids = [];
							for (var i = 0; i < rows.length; i++) {
								ids.push(rows[i].identifier);
							}
						  $.ajax({
							  type:'post',
							  url:'${ctx}/maintenance/badDevice/update',
							  data : JSON.stringify(ids),
							  contentType : 'application/json;charset=utf-8',
							  success:function(data){
								  if(data=='success'){
										parent.$.messager.show({ title : "提示",msg: "操作成功！", position: "bottomRight" });
										dg.datagrid('reload');
								 }
							  }
						  });
					  }
				  });
				
			} else {
				parent.$.messager.show({ title : "提示",msg: "请选择行数据！", position: "bottomRight" });
				return;
			}
		  }
	
	</script>
</body>
</html>
	
	
	
	
	
	
	
	
	
	
	
	
	
	