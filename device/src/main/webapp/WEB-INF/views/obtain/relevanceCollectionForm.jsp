<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>动环设备配置</title>
<%@include file="/WEB-INF/views/include/easyui.jsp" %>

</head>
<body>
	<form id="relevanceCollectionForm"  action="${ctx }/device/peDevice/saveRelevanceCollection" method="post">
		<table class="formTable">
			<tr>
				<td style="width:100px;">动环设备名称:</td>
				<td>
					<input type="hidden" name="id" value="${peDevice.id }">
					<input name="version" type="hidden" value="${peDevice.version }"/>
					
					
				
					
					
					<input id="name" name="name" class="easyui-validatebox" data-options="width: 150" readonly="readonly" style="background: #eee" value="${peDevice.name }">
					 
				</td>
				
			</tr>
			<tr>
			<td style="width:100px;">采集器：</td>
				<td>
					<input id="collectionId" name="collectDeviceId">
				</td>
			</tr>
			 <tr>
				<td>挂接接口类型：</td>
				<td>
					<input id="deviceType" name="dhDeviceType" type="text" value="${peDevice.dhDeviceType }" class="easyui-combobox" data-options="width:150,editable:false"/>
				</td>
			</tr>
	         <tr>
				<td>挂接设备类型：</td>
				<td>
					<input id="deviceInterfaceType" name="dhDeviceInterfaceType" type="text" value="${peDevice.dhDeviceInterfaceType }" class="easyui-combobox" data-options="width:150,editable:false"/>
				</td>
			</tr>
			<tr>
				<td>设备接口序号：</td>
				<td><input name="dhDeviceIndex" type="text" value="${peDevice.dhDeviceIndex }" class="easyui-validatebox" data-options="width: 150,validType:'integer'"/></td>
			</tr>
		</table>
	</form>
	<script type="text/javascript">
		$(function(){
			var url="";
			if("${type}"=="personal"){
			
				if("${peDevice.obtainUserId}"!=""){
					url="${ctx}/device/obtain/getCollectionListByObtainUserId/${peDevice.obtainUserId}";
				}
			}else{
				url="${ctx}/device/obtain/getCollectionListByOrgId/${peDevice.organizationId}";
			}
			$("#collectionId").combobox({
				url:url,
				method:"get",
				width:150,
				editable:false,
				valueField : 'id',
				textField : 'name',
				required : true
				
				
			});
			$("#collectionId").combobox("select","${peDevice.collectDeviceId}");
			
			$('#deviceType').combobox({
				  method:"get",
				  url:'${ctx}/device/peDevice/dhDeviceType',
				  valueField:'deviceType',
				  textField:'value',
				  onSelect:function(data){
					  var deviceType=data.deviceType;
					  $('#deviceInterfaceType').combobox({
						  method:"get",
						  url:'${ctx}/device/peDevice/deviceInterfaceType?deviceType='+deviceType
					 }).combobox('clear');
				  }
			 });
			 $('#deviceInterfaceType').combobox({
			     valueField:'deviceInterfaceType',
			     textField:'name'
			 });
			 $('#relevanceCollectionForm').form({
					onSubmit : function() {
					
						var isValid = $(this).form('validate');
						return isValid; // 返回false终止表单提交
					},
					success : function(data) {
						successTip(data, pe_dg, pe_dlg);
					}
				});
		});
	</script>
</body>
</html>