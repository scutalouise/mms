<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<div>
	<form id="mainform" action="${ctx }/device/devicePurchase/${action}" method="post">
		<table style="border-spacing:5px;margin-left: 10px;margin-top: 5px;">  
			<tr>
				<td>采购名：</td>
				<td>
					<input type="hidden" name="id" value="${id }"/>
					<input type="hidden" name="version" value="${devicePurchase.version }"/>
					<input id="name" name="name" class="easyui-validatebox" data-options="width: 200,required:'required'" value="${devicePurchase.name }" validType="length[2,20]"/> 
				</td>
			</tr>
			<tr>
				<td>数量：</td>
				<td>
				    <input name="quantity" value="${devicePurchase.quantity }" class="easyui-numberbox" data-options="width: 200,required:'required'"/>
				</td>
			</tr>
			<tr>
				<td>采购机构：</td>
				<td>
				    <input name="orgId" id="orgIds" value="${devicePurchase.orgId }" class="easyui-combobox" data-options="width: 200,required:'required',editable:false"/>
				</td>
			</tr>
			<tr>
				<td>设备类型:&nbsp;&nbsp;</td><!-- 原一级设备类型 -->
				<td>
					<input name="firstDeviceType" id="firstDeviceType" class="easyui-combobox" value="${deviceInventory.firstDeviceType }" data-options="width: 200,required:true,editable:false"/>
				</td>
			</tr>
			<tr>	
				<td>设备名称:</td><!-- 原二级设备类型 -->
				<td>
					<input name="secondDeviceType" id="secondDeviceType" class="easyui-combobox" value="${deviceInventory.secondDeviceType }" data-options="width: 200,required:true,editable:false"/>
				</td>
			</tr>
			<tr>	
				<td>品牌:</td>
				<td>
					<input name="brandId" id="brandId" class="easyui-combobox" value="${deviceInventory.brandId }" data-options="width: 200,required:true,editable:false"/>
				</td>
			</tr>
			<tr>
				<td>供应商：</td>
				<td>
				    <input name="supplyId" id="supplyId" class="easyui-combobox" value="${devicePurchase.supplyId }" data-options="width: 200,required:true,editable:false"/>
				</td>
			</tr>
			<tr>
				<td>采购订单号：</td>
				<td>
				    <input name="purchaseOrderNum" id="purchaseOrderNum" class="easyui-validatebox" value="${devicePurchase.purchaseOrderNum }" data-options="width: 200,required:false,editable:false"/>
				</td>
			</tr>
			<tr>
				<td>运维方式:</td>
				<td>
				    <input name="maintainWay" id="maintainWay" value="${devicePurchase.maintainWay }" data-options="width: 200,required:true,editable:false" />
				</td>
			</tr>
			<tr>
				<td>运维组织：</td>
				<td>
				    <input name="maintainOrgId" id="maintainOrgId"  value="${devicePurchase.maintainOrgId }" class="easyui-combobox" data-options="width: 200,required:true,editable:false"/>
				</td>
			</tr>
			<tr>
				<td>购买日期：</td>
				<td>
					<input name="purchaseDate" id="purchaseDate" class="easyui-my97" dateFmt="yyyy-MM-dd" data-options="width: 200" value="<fmt:formatDate value="${devicePurchase.purchaseDate}"/>"/>
				</td>
			</tr>
			<tr>
				<td>保修日期：</td>
				<td>
				    <input name="warrantyDate" id="warrantyDate" class="easyui-my97" dateFmt="yyyy-MM-dd" data-options="width: 200" value="<fmt:formatDate value="${devicePurchase.warrantyDate}"/>"/>
				</td>
			</tr>
			<tr>
				<td>是否新购进：</td>
				<td>
					<input type="radio" id="yes" name="isPurchase" value="1"/><label for="yes">是</label>
					<input type="radio" id="no" name="isPurchase" value="0"/><label for="no">否</label>
				</td>
			</tr>
			<tr>
				<td>描述：</td>
				<td>
				    <textarea rows="3" name="otherNote" class="easyui-validatebox" data-options="width: 200">${devicePurchase.otherNote}</textarea>
				</td>
			</tr>
		</table>
	</form>
</div>

<script type="text/javascript">

var action="${action}";

if(action=="add"){
	  $("input[name='isPurchase'][value=1]").attr('checked',true);
}

if(action=="update"){
	  $("input[name='isPurchase'][value=${devicePurchase.isPurchase }]").attr('checked',true);
	 
	  if ("${devicePurchase.maintainWay }" == "INNER"){
		  $('#maintainOrgId').combotree({
				method:'post',
				url:'${ctx}/system/organization/json',
				idField:'id',
				textFiled:'orgName'
		  });
      }else{
    	  $('#maintainOrgId').combotree({
				method:'get',
				url:'${ctx}/device/supplyMaintainOrg/json',
				idField:'id',
				textFiled:'orgName'
          });
      }
	  
	  $('#secondDeviceType').combobox({
		  method:"get",
		  url:'${ctx}/device/brand/secondDeviceType?firstDeviceType=${deviceInventory.firstDeviceType }'
	 });
	  
	  $('#brandId').combobox({
		  method:"get",
		  url:'${ctx}/device/brand/brandCollection?secondDeviceType=${deviceInventory.secondDeviceType }'
	 });
	  
	 $('#supplyId').combobox({
		  method:"get",
		  url:'${ctx}/device/supplyMaintainOrg/supplyMaintainOrgList?brandId=${devicePurchase.brandId }'
	 });
}
		
$(function(){
	
	$('#orgIds').combotree({
		method:'post',
		url:'${ctx}/system/organization/json',
		idField:'id',
		textFiled:'orgName',
	    animate:true
	});
    
	 $('#firstDeviceType').combobox({
		  method:"get",
		  url:'${ctx}/device/brand/firstDeviceType',
		  valueField:'firstDeviceType',
		  textField:'name',
		  onSelect:function(data){
			  var firstDeviceType=data.firstDeviceType;
			  $('#secondDeviceType').combobox({
				  method:"get",
				  url:'${ctx}/device/brand/secondDeviceType?firstDeviceType='+firstDeviceType
			 }).combobox('clear');
		  }
	  });
	 
	 $('#secondDeviceType').combobox({
	      valueField:'secondDeviceType',
	      textField:'name',
	      onSelect:function(data){
	    	  var secondDeviceType=data.secondDeviceType;
	    	  $('#brandId').combobox({
				  method:"get",
				  url:'${ctx}/device/brand/brandCollection?secondDeviceType='+secondDeviceType,
			 }).combobox('clear');
	       }
	 });
	 
	 $('#brandId').combobox({
		  valueField:'id',
	      textField:'name',
	      onSelect:function(data){
	    	  var brandId=data.id;
	    	  $('#supplyId').combobox({
				  method:"get",
				  url:'${ctx}/device/supplyMaintainOrg/supplyMaintainOrgList?brandId='+brandId
			 }).combobox('clear');
	      }
	 });
	 
	 $('#supplyId').combobox({
	      valueField:'id',
	      textField:'orgName'
	 });
	  
	 $("#maintainWay").combobox({
		    method:'get',
			url:'${ctx}/device/devicePurchase/maintainWay',
			valueField:'maintainWay',
		    textField:'name',
			onSelect : function(v) {
				if (v.maintainWay == "INNER") {
					$('#maintainOrgId').combotree({
						method:'post',
						url:'${ctx}/system/organization/json',
						idField:'id',
						textFiled:'orgName',
					    animate:true,
					    required:false,
					    onLoadSuccess:function(data){
					    	if(action=="update"){
					    		$('#maintainOrgId').combotree("setValue","${devicePurchase.maintainOrgId }");
					    	}else{
					    		$('#maintainOrgId').combotree("setValue",$('#orgIds').combotree('getValue'));
					    	}
					    }
				    });
				} else if(v.maintainWay == "OUTER"){
					$('#maintainOrgId').combotree({
						method:'get',
						url:'${ctx}/device/supplyMaintainOrg/json',
						idField:'id',
						textFiled:'orgName',
					    animate:true,
					    required:true,
					    onLoadSuccess:function(data){
					    	if(action=="update"){
					    		$('#maintainOrgId').combotree("setValue","${devicePurchase.maintainOrgId }");
					    	}else{
					    		$('#maintainOrgId').combotree("setValue",$('#supplyId').combobox('getValue'));
					    	}
					    }
					 });
				} 
			}
	});
	 
	initDate("purchaseDate","warrantyDate");
});

  //提交表单
  $('#mainform').form({
		  onSubmit:function(){
			  var valid=$(this).form('validate');
			  return valid;
		   },
		  success:function(data) {
				successTip(data, dg,dlg);
		  }
  });  
</script>
</body>
</html>