<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报警等级管理表单</title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
	<div>
		<form:form id="mainform" action="${ctx }/system/alarmLevel/${action }"
			method="post" style="width:100%;" modelAttribute="alarmLevel">
			<table class="formTable" cellpadding="3"
				style="width: 100%; padding: 10 0 10 0">

				<tr>
					<td width="100px">名称：</td>
					<td style="width: 120px;"><form:hidden path="version" /> <form:hidden
							path="id" />
						<form:hidden path="status" /> <form:input path="name" id="name"
							class="easyui-validatebox" data-options="required:true,validType:['maxLength[20]']" /></td>
					<td width="100px">报警等级序号：</td>
					<td width="120px"><form:input path="alarmSort"
							style="width:135px;" class="easyui-numberspinner"
							data-options="min:1,value:1,editable:true,required:true" missingMessage="该输入项为必输项" /></td>
				</tr>
				<tr>
					<td>是否短信报警：</td>
					<td><select name="isSms" id="isSms" style="width: 135px;">
							<option value="YES">是</option>
							<option value="NO">否</option>

					</select></td>
					<td>是否邮件报警：</td>
					<td><select name="isEmail" id="isEmail" style="width: 135px;">
							<option value="YES">是</option>
							<option value="NO" selected="selected">否</option>
					</select></td>
				</tr>
				<tr>

					<td>是否声音报警：</td>
					<td><select name="isSound" id="isSound" style="width: 135px;">
							<option value="YES">是</option>
							<option value="NO">否</option>
					</select></td>
					<td>是否启用：</td>
					<td><select name="enabled" id="enabled"
						class="easyui-combobox" data-options="panelHeight:49"
						style="width: 135px;">
							<option value="ENABLED">启用</option>
							<option value="DISABLED">禁用</option>
					</select></td>
				</tr>
				<tr>
					<td>描述：</td>
					<td colspan="3"><textarea name="remark" data-options="validType:['maxLength[500]']"
							class="easyui-validatebox" style="height: 60px; width: 405px;">${alarmLevel.remark }</textarea></td>

				</tr>

			</table>

		</form:form>
	</div>
	<script type="text/javascript">
		$(function() {
			$("#isEmail").combobox({
				panelHeight : 49,
				editable:false
			});
			$("#isSms").combobox({
				panelHeight : 49,
				editable:false
			});
			$("#isSound").combobox({
				panelHeight : 49,
				editable:false
			});
			$("#enabled").combobox({
				panelHeight : 49,
				editable:false
			});

			var action = "${action}";
			if (action == "add") {
				$("#isEmail").combobox("select", "NO");
				$("#isSound").combobox("select", "NO");
				$("#isSms").combobox("select", "NO");
				$("#enabled").combobox("select", "ENABLED");

			} else if (action == "update") {

				$("#isEmail").combobox("select", "${alarmLevel.isEmail}");
				$("#isSound").combobox("select", "${alarmLevel.isSound}");
				$("#isSms").combobox("select", "${alarmLevel.isSms}");
				$("#enabled").combobox("select", "${alarmLevel.enabled}");

			}

			//提交表单
			$('#mainform').form({
				onSubmit : function() {

					var isValid = $(this).form('validate');
					return isValid; // 返回false终止表单提交
				},
				success : function(data) {
					successTip(data, dg, d);
				}
			});

		});
	</script>
</body>
</html>