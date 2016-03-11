<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>   
<div id="toolbar" style="padding:5px;height:auto">
	<div>
       	<form id="searchFrom" action="">
    	    <input type="text" name="filter_LIKES_phone" class="easyui-validatebox" data-options="width:150,prompt: '电话'"/>
	        <input type="text" id="startDate" name="filter_GTD_createDate" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width:150,prompt: '开始日期(创建)'" />
	        - <input type="text" id="endDate" name="filter_LTD_createDate" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width:150,prompt: '结束日期(创建)'"/>
	        <span class="toolbar-item dialog-tool-separator"></span>
	        <a href="javascript(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="queryPurchase()">查询</a>
		</form>
	</div>
</div>
<table id="devicePurchase_datagrid"></table>
<script type="text/javascript">
 	var devicePurchase_datagrid;
 	var filter;
 	switch(tabIndex){
	 	case 0:filter = "HOSTDEVICE";break;
	 	case 1:filter = "NETWORKDEVICE";break;
	 	case 2:filter = "UNINTELLIGENTDEVICE";break;
	 	case 3:filter = "COLLECTDEVICE";break;
	 	case 4:filter = "PEDEVICE";break;
 	}
 	
	$(function(){
		devicePurchase_datagrid=$('#devicePurchase_datagrid').datagrid({    
			method: "get",
			url:'${ctx}/device/devicePurchase/purchase', 
 			queryParams:{
				'filter_EQ_FirstDeviceType_di.firstDeviceType':filter
			}, 
		    fit : true,
			fitColumns : true,
			border : false,
			idField : 'id',
			pagination:true,
			rownumbers:true,
			pageNumber:1,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			striped:true,
			singleSelect:true,
			toolbar:'#toolbar',
		    columns:[[    
		        {field:'id',title:'id',hidden:true,sortable:true,width:100},    
 		        {field:'name',title:'设备名字',sortable:true,width:100},
  		        {field:'purchaseDate',title:'购买日期',sortable:true,width:100,formatter: function(value,row,index){
 			        	return formatDate(value,"yyyy-MM-dd")
 		        }},
 		        {field:'warrantyDate',title:'保修日期(截止)',sortable:true,width:100,formatter: function(value,row,index){
 		        	return formatDate(value,"yyyy-MM-dd")
 		        }}, 
 		        {field:'orgName',title:'采购单位',sortable:true,width:100,tooltip: true}
		    ]],
		    onDblClickRow:function (){
		    	getSelectPurchase();
		    	devicePurchase_dialog.panel('close');
		    }
		});
	});

//查询购买，采购记录
function queryPurchase(){
	var obj=$("#searchFrom").serializeObject();
	devicePurchase_datagrid.datagrid('load',obj); 
} 
	
//确定设备的采购记录
function getSelectPurchase(){
	//所选的采购记录，只能选择一条;
	var row = devicePurchase_datagrid.datagrid('getSelected');
	if(rowIsNull(row)) return;
	$("#purchaseId").val(row['id']);
	$("#purchaseName").val(row['name']);
//	$("input[name='maintainWay'][value='"+ row['maintainWay']['maintainWay'] +"']").attr("checked",true);//默认采购记录中选择的运维方式选中
//	$("input[name='maintainWay'][value='OUTER']").attr("checked",true);//默认采购记录中选择的运维方式选中
	$("input[name='maintainWay']").each(function(a){
		if($(this).val() == row['maintainWay']['maintainWay']){
			$(this)[0].checked = true;
		}else{
			$(this)[0].checked = false;
		}
	});
	//首先进行判断，所选择的采购记录中记录的数量是否已经被领取完了。。。
	$.ajax({
  		type:'get',
  		url:"${ctx}/device/devicePurchase/obtainCountCompare/"+ $("#purchaseId").val(),
  		success: function(data){
  			if(data.result == "success"){//判断下已经领取的设备数量是否超过采购记录登记的数量，如果小于采购记录的数量则可以进行下一步选择操作，否则给出提示：
			  	$.ajax({
			  		type:'get',
			  		url:"${ctx}/device/devicePurchase/purchaseDetail/"+ $("#purchaseId").val(),
			  		success: function(data){
			  			/**
			  			 *1.默认采购记录中选择的运维方式选中；2.如果默认为外部运维方式，加载外部运维组织，并提供给客户可选；3.如果默认为内部运维方式，将运维组织选项隐藏掉，不要；
			  			 */
			  			//console.info(data[0]);
			  			var maintainWayTemp = data[0].maintainWay.maintainWay;
			  			var maintainOrgIdTemp = data[0].maintainOrgId;
			  			console.info("从后台查询到的当前的运维方式："+maintainWayTemp);
			  			$("input[name='maintainWay'][value="+ maintainWayTemp +"]").attr("checked",true);//默认采购记录中选择的运维方式选中
			  			if(maintainWayTemp == "OUTER"){
			  				$('#maintainOrgId').combotree({
			  					method:'get',
			  					url:'${ctx}/device/supplyMaintainOrg/json',
			  					idField:'id',
			  					textFiled:'orgName',
			  				    required:true,
			  				    onLoadSuccess:function(data){
			  				    	$('#maintainOrgId').combotree("setValue",maintainOrgIdTemp);//加载完之后选中哪个值；并且允许修改
			  				    },
			  				    onSelect:function(data){
			  				    	$('#maintainOrgName').val(data.text);
			  				    }
			  				 })
			  			}else if(maintainWayTemp == "INNER"){
							$('#maintainOrgId').combotree({ disabled: true });
  							$('#maintainOrgName').attr({ disabled: true });
			  			}
			  		}
			  	}); 
  		}else{
	  			//将采购位置置空以便验证不通过！
				$("#purchaseName").val("");
				$("#purchaseId").val("");
  				parent.$.messager.show({ title : "提醒",msg: data.message, position: "bottomRight" });
  		}
  		}
  	});
		
	$('#mainform').form('validate');
} 
</script>
</body>
</html>