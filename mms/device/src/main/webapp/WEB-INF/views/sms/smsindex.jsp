
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>短信配置</title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script type="text/javascript" src="${ctx }/static/js/sms/smsindex.js"></script>
<style type="text/css">
.formTable td {
	line-height: 30px;
}

.formTable {
	margin: 0px;
	padding: 10px;
}

body {
	font-size: 12px;
}
</style>
</head>
<body>
	<center>
		<div>
			<form:form id="mainform" action="${ctx }/system/sms/save"
				method="post" style="width: 100%;" modelAttribute="sms">
				<fieldset style="margin: 20px;">
					<legend
						style="font-size: 16px; font-weight: bold; margin-left: 20px;"
						align="left">短信配置</legend>
					<div>
						<form:hidden path="id" />
						<table class="formTable" cellpadding="3"
							style="width: 100%; padding: 10 0 10 0">
							<tr>
								<td width="150" align="right">短信猫端口编号:</td>
								<td align="left"><form:input path="gatewayId"
										class="easyui-validatebox" style="width:200px;" data-options="required:true,validType:['maxLength[20]']"/></td>
								<td width="150" align="right">串口名称:</td>
								<td align="left"><form:input path="comPort"
										class="easyui-validatebox" style="width:200px;" data-options="required:true,validType:['maxLength[20]']"/></td>
							</tr>
							<tr>
								<td align="right">串口波特率:</td>
								<td align="left"><select name="baudRate" id="baudRate"
									class="easyui-combobox" data-options="panelHeight:60"
									style="width: 208px;">
										<option value="9600">9600</option>
										<option value="19200">19200</option>
										<option value="38400">38400</option>
										<option value="57600">57600</option>
										<option value="115200">115200</option>
										<option value="128000">128000</option>
								</select></td>
								<td align="right">制造商:</td>
								<td align="left"><form:input path="manufacturer"
										class="easyui-validatebox" style="width:200px;" data-options="required:true,validType:['maxLength[20]']"/></td>

							</tr>
							<tr>
								<td align="right">设备型号:</td>
								<td align="left"><form:input path="model"
										class="easyui-validatebox" style="width:200px" data-options="required:true,validType:['maxLength[20]']"/></td>
								<td align="right">是否启用:</td>
								<td align="left"><select name="enabled" id="enabled"
									class="easyui-combobox" data-options="panelHeight:49"
									style="width: 208px;">
										<option value="0">启用</option>
										<option value="1">禁用</option>
								</select></td>
							</tr>
						</table>

					</div>
				</fieldset>
			</form:form>
			<div style="margin: 20px; text-align: right;">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-save'" onclick="save()">保存</a>
			</div>
			<form:form method="post" id="soundform"
				action="${ctx }/system/sendConfig/soundSave" style="width: 100%;"
				modelAttribute="soundConfig">
				<fieldset style="margin: 20px">
					<legend
						style="font-size: 16px; font-weight: bold; margin-left: 20px;"
						align="left">报警器配置</legend>
					<form:hidden path="id" />


					<table class="formTable" cellpadding="3"
						style="width: 100%; padding: 10 0 10 0">
						<tr>
							<td align="right" width="150">报警器接口:</td>
							<td align="left"><select name="interfaceType"
								id="interfaceType" class="easyui-combobox"
								data-options="panelHeight:60" style="width: 208px;">
									<option value="21">UPS1开关量输出</option>
									<option value="22">UPS2开关量输出</option>

							</select></td>
							<td align="right" width="150">是否启用:</td>
							<td align="left"><select name="enabled" id="soundEnabled"
								class="easyui-combobox" data-options="panelHeight:49"
								style="width: 208px;">
									<option value="0">启用</option>
									<option value="1">禁用</option>
							</select></td>
						</tr>
					</table>
				</fieldset>
			</form:form>
			<div style="margin: 20px; text-align: right;">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-save'" onclick="soundSave()">保存</a>
			</div>
		</div>
	</center>
	<script type="text/javascript">
		$(function() {
			if ("${sms.id}" == "") {
				$("#enabled").combobox("select", "0");
				$("#baudRate").combobox("select", "9600");
			} else {
				$("#enabled").combobox("select", "${sms.enabled}");
				$("#baudRate").combobox("select", "${sms.baudRate}");
			}
			if ("${soundConfig.id}" == "") {
				$("#soundEnabled").combobox("select", "0");
			} else {
				$("#soundEnabled").combobox("select", "${soundConfig.enabled}");
				$("#interfaceType").combobox("select",
						"${soundBean.interfaceType}");
			}
			//提交表单
			$('#mainform').form({
				onSubmit : function() {

					var isValid = $(this).form('validate');
					return isValid; // 返回false终止表单提交
				},
				success : function(data) {
					parent.$.messager.show({
						title : "提示",
						msg : "操作成功！",
						position : "bottomRight"
					});
				}
			});
			//提交表单
			$('#soundform').form({

				onSubmit : function() {
					var isValid = $(this).form('validate');
					return isValid; // 返回false终止表单提交
				},
				success : function(data) {
					parent.$.messager.show({
						title : "提示",
						msg : "操作成功！",
						position : "bottomRight"
					});
				}
			});
		});
	</script>
</body>
</html>