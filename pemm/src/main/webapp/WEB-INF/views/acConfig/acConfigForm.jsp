<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>空调配置表单</title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
	<form action="${ctx }/acConfig/update" id="acMainform" style="width: 100%" method="post">
		<input value="${acConfig.id }" name="id" type="hidden">
		<input value="${acConfig.version }" name="version" type="hidden">
		<input value="${deviceId}" name="deviceId" type="hidden">
		<table class="formTable" cellpadding="3"
			style="width: 100%; padding: 10 0 10 0">
			<tr>
				<td>温度范围</td>
				<td>
					<input type="text" id="minTemperature" value="${acConfig.minTemperature }" name="minTemperature" data-options="required:true,precision:2" class="easyui-numberbox"  style="width: 60px">&nbsp;至
					<input type="text" id="maxTemperature" value="${acConfig.maxTemperature }" name="maxTemperature" data-options="required:true,precision:2" class="easyui-numberbox"  style="width: 60px">&nbsp;℃
				</td>
			</tr>
			<tr>
				<td>湿度范围</td>
				<td>
					<input type="text" id="minHumidity" value="${acConfig.minHumidity }" name="minHumidity" data-options="required:true,precision:2" class="easyui-numberbox"  style="width: 60px">&nbsp;至
					<input type="text" id="maxHumidity" value="${acConfig.maxHumidity }" name="maxHumidity" data-options="required:true,precision:2" class="easyui-numberbox"  style="width: 60px">&nbsp;%	
				</td>
			</tr>
			<tr>
				<td>是否启用</td>
				<td>
					<select name="enabled" id="enabled" 
						style="width: 139px;">
							<option value="ENABLED">启用</option>
							<option value="DISABLED">禁用</option>
					</select>
				</td>
			</tr>
		</table>
	</form>
	<script type="text/javascript">
		$("#minTemperature").numberbox({
			validType:"minValue['#maxTemperature','不能大于或等于最高温度']"
			
		});
		$("#maxTemperature").numberbox({
			validType:"maxValue['#minTemperature','不能小或等于最高温度']"
			
		});
		$("#minHumidity").numberbox({
			validType:"minValue['#maxTemperature','不能大于或等于最高湿度']"
			
		});
		$("#maxHumidity").numberbox({
			validType:"maxValue['#minTemperature','不能小于或等于最高湿度']"
			
		});
		$("#enabled").combobox({
			panelHeight : 49,
			editable:false
		});
		if("${acConfig.enabled}"!=""){
			$("#enabled").combobox("select", "${acConfig.enabled}");
		}else{
			$("#enabled").combobox("select", "ENABLED");
		}
		//提交表单
		$('#acMainform').form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
				return isValid; // 返回false终止表单提交
			},
			success : function(data) {
				successTip(data, dg, acdlg);
			}
		});
	</script>
</body>
</html>