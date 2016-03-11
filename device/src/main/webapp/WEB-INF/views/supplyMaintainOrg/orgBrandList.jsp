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
				filter_EQ_StatusEnum_status : 'NORMAL',
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
	      singleSelect:false,
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
	        	  }}
	        	  /*,
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
	        	 */		  
	      ]],
	      onLoadSuccess:function(){
	      	//获取用户拥有角色,选中
	      	$.ajax({
	      		async:false,
	  			type:'get',
	  			url:"${ctx}/device/supplyMaintainOrg/${orgId}/orgBrand",
	  			success: function(data){
	  				if(data){
	  					for(var i=0,j=data.length;i<j;i++){
	  						dg.datagrid('selectRecord',data[i]);
	  					}
	  				} 
	  			}
	  		});
	      	
	      },
	      toolbar:'#tb'
	  });
  });

  //处理选中了品牌之后的操作；
  function getSelectRows(){
	  var row = $("#dg").datagrid("getSelected");
	  if(rowIsNull(row)) return;
	  var rows = $("#dg").datagrid("getSelections");
	  var ids = new Array();
	  for(var i=0 ;i< rows.length; i++){
		  ids[i] = rows[i].id;
	  }
	  console.info(ids);
	  //获取角色拥有权限
		$.ajax({
			async:false,
			type:'post',
			url:"${ctx}/device/supplyMaintainOrg/brands/${orgId}",
			data:JSON.stringify(ids),
			contentType:'application/json;charset=utf-8',
			success: function(data){
				if(data=='success'){
					parent.$.messager.show({ title : "提示",msg: "操作成功！", position: "bottomRight" })
				}else{
					$.easyui.messager.alert(data);
				} 
			}
		});
  }
  
</script>
</body>
</html>