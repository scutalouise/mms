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
		<table style="border-spacing:5px;margin-left: 10px;">  
			<tr>
				<td>采购名：</td>
				<td>
					<input type="hidden" name="id" value="${id }"/>
					<input type="hidden" name="version" value="${devicePurchase.version }"/>
					<input id="name" name="name" class="easyui-validatebox" data-options="width: 150,required:'required'" value="${devicePurchase.name }" validType="length[2,20]"/> 
				</td>
			</tr>
			<tr>
				<td>数量：</td>
				<td>
				    <input name="quantity" value="${devicePurchase.quantity }" class="easyui-numberbox" data-options="width: 150,required:'required'"/>
				</td>
			</tr>
			<tr>
				<td>一级设备类型:&nbsp;&nbsp;</td>
				<td>
					<input name="firstDeviceType" id="firstDeviceType" class="easyui-combobox" value="${deviceInventory.firstDeviceType }" data-options="width: 150,required:true,editable:false"/>
					            <%-- <option value="">--请选择--</option>
							<c:forEach items="${firstDeviceType }" var="firstDevice">
								<option value="${firstDevice }">${firstDevice.name }</option>
							</c:forEach> --%>
				</td>
			</tr>
			<tr>	
				<td>二级设备类型:</td>
				<td>
					<input name="secondDeviceType" id="secondDeviceType" class="easyui-combobox" value="${deviceInventory.secondDeviceType }" data-options="width: 150,required:true,editable:false"/>
				</td>
			</tr>
			<tr>	
				<td>品牌:</td>
				<td>
				    <input name="brandName" id="brandName" type="hidden" value="${deviceInventory.brandName }"/>
					<input name="brandId" id="brandId" class="easyui-combobox" value="${deviceInventory.brandId }" data-options="width: 150,required:true,editable:false"/>
				</td>
			</tr>
			<tr>
				<td>采购机构：</td>
				<td>
				    <input name="orgName" id="orgName"  type="hidden" value="${devicePurchase.orgName }"/>
				    <input name="orgId" id="orgId"  value="${devicePurchase.orgId }" class="easyui-combobox" data-options="width: 150,required:'required',editable:false"/>
				</td>
			</tr>
			<tr>
				<td>购买日期：</td>
				<td>
					<input name="purchaseDate" id="purchaseDate" class="easyui-my97" dateFmt="yyyy-MM-dd" data-options="width: 150" value="<fmt:formatDate value="${devicePurchase.purchaseDate}"/>"/>
				</td>
			</tr>
			<tr>
				<td>保修日期：</td>
				<td>
				    <input name="warrantyDate" id="warrantyDate" class="easyui-my97" dateFmt="yyyy-MM-dd" minDate="%y-{%M+12}-%d" data-options="width: 150" value="<fmt:formatDate value="${devicePurchase.warrantyDate}"/>"/>
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
				    <textarea rows="2" cols="19" name="otherNote" class="easyui-validatebox">${devicePurchase.otherNote}</textarea>
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
	  $('#secondDeviceType').combobox({
		  method:"get",
		  url:'${ctx}/device/brand/secondDeviceType?firstDeviceType=${deviceInventory.firstDeviceType }'
	 });
	  
	  $('#brandId').combobox({
		  method:"get",
		  url:'${ctx}/device/brand/brandCollection?secondDeviceType=${deviceInventory.secondDeviceType }'
	 });
}

$(function(){
	$('#orgId').combotree({
		method:'get',
		url:'${ctx}/system/organization/json',
		idField:'id',
		textFiled:'orgName',
	    animate:true,
	    onSelect:function(data){
	    	$('#orgName').val(data.text);
	    } 
	});
    
	 $('#firstDeviceType').combobox({
		  method:"get",
		  url:'${ctx}/device/brand/firstDeviceType',
		  valueField:'firstDeviceType',
		  textField:'name',
		  onSelect:function(data){
			  firstDeviceType=data.firstDeviceType;
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
	    	  secondDeviceType=data.secondDeviceType;
	    	  $('#brandId').combobox({
				  method:"get",
				  url:'${ctx}/device/brand/brandCollection?secondDeviceType='+secondDeviceType,
			      onSelect:function(data){
			    	  $('#brandName').val(data.name);
			      }
			 }).combobox('clear');
	       }
	 });
	 $('#brandId').combobox({
	      valueField:'id',
	      textField:'name'
	 });
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