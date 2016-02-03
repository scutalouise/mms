<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
</head>
<body>
<div id="tb" style="padding: 5px;height: auto;">
   <div>
    <form id="searchFrom" action="" style="margin-left:8px;margin-top:5px;height: 30px;">
       	<input type="text" name="filter_LIKES_name" class="easyui-validatebox" data-options="width:150,prompt:'采购名称'"/>&nbsp;&nbsp;&nbsp;
		<input type="text" name="filter_GTD_purchaseDate" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width:150,prompt:'购买日期'"/>
		<span class="toolbar-item dialog-tool-separator"></span>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="cx();">查询</a>
	 </form>
     <div>
       <shiro:hasPermission name="device:devicePurchase:add">
       	   <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">添加</a>
       	   <span class="toolbar-item dialog-tool-separator"></span>
       </shiro:hasPermission>
       <shiro:hasPermission name="device:devicePurchase:update">
           <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd();">修改</a>
       </shiro:hasPermission>
     </div>
  </div>
</div>
<table id="dg"></table>
<div id="dlg"></div>
  

<script type="text/javascript">
 var dg;
 var dlg;
 
 $(function(){
	 dg=$("#dg").datagrid({
		 method:'get',
		 url:'${ctx}/device/devicePurchase/json',
		 fit : true,
	     fitColumns : true,
		 idField:'id',
		 striped:true,
		 pagination:true,
		 rownumbers:true,
		 pageNumber:1,
		 pageSize:10,
		 pageList:[10,20,30,40,50],
		 singleSelect:true,
		 columns:[[
		      {field:'id',title:'id',hidden:true},
		      {field:'name',title:'采购名',sortable:true,width:150},
		      {field:'quantity',title:'数量',sortable:true,width:100},
		      {field:'purchaseDate',title:'购买日期',sortable:true,width:150},
		      {field:'warrantyDate',title:'保修日期',sortable:true,width:150},
		      {field:'orgName',title:'采购机构',sortable:true,width:150},
		      {field:'isPurchase',title:'是否新购进',sortable:true,width:150,
		    	  formatter:function(data){
		    		  if(data=1){
		    		    return "是";
		    		  }else
		    			return "不是";  
		    	  }},
		      {field:'otherNote',title:'其他说明',sortable:true,width:150},
		      {field:'updateTime',title:'更新时间',sortable:true,width:150}
		  ]],
		 toolbar:'#tb'
	 });
 });
 
 //添加弹窗
 function add(){
	 dlg=$('#dlg').dialog({
		 title:'添加采购记录',
		 width:350,
		 height:400,
		 iconCls:'icon-add',
		 href:'${ctx}/device/devicePurchase/add',
		 maximizable:true,
		 modal:true,
		 buttons:[{
			 text:'确认',
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
 };
 
//修改弹窗
 function upd(){
	 var row = dg.datagrid('getSelected');
	 if(rowIsNull(row)) return;
	 dlg=$('#dlg').dialog({
		 title:'修改采购记录',
		 width:350,
		 height:400,
		 iconCls:'icon-edit',
		 href:'${ctx}/device/devicePurchase/update/'+row.id,
		 maximizable:true,
		 modal:true,
		 buttons:[{
			 text:'确认',
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
 };
 
 function cx(){
		var obj=$("#searchFrom").serializeObject();
		dg.datagrid('load',obj); 
}
</script>
</body>
</html>