<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设备受用网点表单</title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>
<body>
	<form action="${ctx }/device/obtain/saveOrganizationId" id="deviceForm" method="post">
		<table class="formTable">
			<tr>
				<td>设备名称:</td>
				<td><input type="text" class="easyui-validatebox" name="deviceName" style="width:152px;"/>
				<input type="hidden"  name="id">
				<input type="hidden" value="${deviceType }" name="deviceType">
				</td>
				
			</tr>
			<tr>
				<td>选择机构:</td>
				<td>
				<input type="text" class="easyui-validatebox" name="organizationId" name="organizationId" style="width:160px">
				</td>
			</tr>
		</table>
	</form>
	<script type="text/javascript">
		$(function(){
			$("input[name='organizationId']").combotree({
				method:'get',
				url:'${ctx}/system/organization/json',
				idField:'id',
				textFiled:'orgName',
			    animate:true
			});
			$("input[name='deviceName']").val(selectDeviceName);
			$("input[name='id']").val(selectDeviceId);
			$("input[name='deviceName']").attr('readonly', 'readonly');
			$("input[name='deviceName']").css('background', '#eee');
			var dg;
			var dlg;
			if("${deviceType}"=="HOSTDEVICE"){
				dg=host_dg;
				dlg=$("#host_usedOrganization_dlg");
			}else if("${deviceType}"=="NETWORKDEVICE"){
				dg=netWork_dg;
				dlg=$("#netWork_usedOrganization_dlg");
			}else if("${deviceType}"=="UNINTELLIGENTDEVICE"){
				
				dg=unintelligent_dg;
				dlg=$("#unintelligent_usedOrganization_dlg");
			}else if("${deviceType}"=="COLLECTDEVICE"){
				dg=collection_dg;
				dlg=$("#collection_usedOrganization_dlg");
			}else if("${deviceType}"=="PEDEVICE"){
				dg=pe_dg;
				dlg=$("#pe_usedOrganization_dlg");
			}
			//提交表单
			$('#deviceForm').form({    
			    onSubmit: function(){    
			    	var isValid = $(this).form('validate');
					return isValid;	// 返回false终止表单提交
			    },    
			    success:function(data){   
			    	successTip(data, dg, dlg);
			    }    
			});
		});
	</script>
</body>
</html>