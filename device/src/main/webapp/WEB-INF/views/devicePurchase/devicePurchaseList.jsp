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
    	<input type="text" name="purchaseOrderNum" class="easyui-validatebox" data-options="width:150,prompt:'采购订单号'"/>&nbsp;&nbsp;&nbsp;
       	<input type="text" name="name" class="easyui-validatebox" data-options="width:150,prompt:'采购名称'"/>&nbsp;&nbsp;&nbsp;
       	<input type="text" id="orgId" name="orgId" class="easyui-validatebox" data-options="width:200,prompt:'采购机构'"/>&nbsp;&nbsp;&nbsp;
		<input type="text" id="startDate" name="GTD_purchaseDate" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width:150,prompt:'购买日期(开始)'"/>&nbsp;&nbsp;&nbsp;
	    <input type="text" id="endDate" name="LTD_purchaseDate" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width:150,prompt:'购买日期(截止)'"/>
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
		 method:'post',
		 url:'${ctx}/device/devicePurchase/json',
		 fit : true,
	     fitColumns : true,
		 idField:'id',
		 striped:true,
		 pagination:true,
		 rownumbers:true,
		 pageNumber:1,
		 pageSize:20,
		 pageList:[10,20,30,40,50],
		 singleSelect:true,
		 columns:[[
		      {field:'id',title:'id',hidden:true},
		      {field:'purchaseOrderNum',title:'采购订单号',sortable:true,width:80},
		      {field:'name',title:'采购名称',sortable:true,width:120},
		      {field:'firstDeviceType',title:'设备类型',sortable:true,width:120,formatter:function(a,b,c){
		    	  return a.name;
		      }},
		      {field:'secondDeviceType',title:'设备名字',sortable:true,width:120,formatter:function(a,b,c){
		    	  return a.name;
		      }},
		      {field:'brandName',title:'品牌',sortable:true,width:150},
		      {field:'orgName',title:'采购机构',sortable:true,width:180},
		      {field:'quantity',title:'数量',sortable:true,width:80},
		      {field:'purchaseDate',title:'购买日期',sortable:true,width:100},
		      {field:'warrantyDate',title:'保修日期',sortable:true,width:100},
		      {field:'isPurchase',title:'是否新购进',sortable:true,width:100,
		    	  formatter:function(value,row,index){
		    		  return value=1?"是":"不是";  
		    	  }},
		   	  {
		    	field:'action',title:'详情', 	 
		    		formatter : function(value, row, index) {
						return '<a href="javascript:details('+row.id+')"><div class="icon-hamburg-cost" style="width:16px;height:16px" title="查看详情"></div></a>';
					}
		      }
		    /*{field:'updateTime',title:'更新时间',sortable:true,width:150} */
		  ]],
		 toolbar:'#tb'
	 });
	 
	 initDateFilter("startDate","endDate");
	 
	 $.ajax({
			url:'${ctx}/system/organization/json',
			type : "post",
			dataType : "json",
			success : function(data) {
				var json = {"id":"","orgName":"-- 采购机构 --"};
				data.unshift(json);
				$('#orgId').combotree({
					idField:'id',
					textFiled:'orgName',
					data:data
			    });
			}
		});
 });
 
 //添加弹窗
 function add(){
	 dlg=$('#dlg').dialog({
		 title:'添加采购记录',
		 width:400,
		 height:540,
		 iconCls:'icon-add',
		 href:'${ctx}/device/devicePurchase/add',
		 maximizable:true,
		 modal:true,
		 onClose:function(){
			 appendAndRemovePurchase("dlg");
		    },
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
		 width:400,
		 height:540,
		 iconCls:'icon-edit',
		 href:'${ctx}/device/devicePurchase/update/'+row.id,
		 maximizable:true,
		 modal:true,
		 onClose:function(){
			 appendAndRemovePurchase("dlg");
		    },
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
 
 //查询
 function cx(){
		var obj=$("#searchFrom").serializeObject();
		dg.datagrid('load',obj);
}
 
 /**
  * 处理dialog,使用同一个div混淆；
  */
 function appendAndRemovePurchase(divId){
 	$("#" +divId + "").dialog("destroy").remove(); //直接摧毁、移除
 	$("<div id='"+ divId +"'></div> ").appendTo($('body'))//新加入一个
 } 
 //详情
 function details(idValue){
	 dlg=$('#dlg').dialog({
		 title:'采购记录详情',
		 width:400,
		 height:540,
		 iconCls:'icon-edit',
		 href:'${ctx}/device/devicePurchase/details/'+idValue,
		 maximizable:true,
		 modal:true,
		 onClose:function(){
			 appendAndRemovePurchase("dlg");
		    }
	 });
 }
</script>
</body>
</html>