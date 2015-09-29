<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报警条件窗口</title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>
<body>
	<div>
		<form:form id="condition_mainform"
			action="${ctx }/system/alarmCondition/${action }" method="post"
			style="width:100%;" modelAttribute="alarmCondition">
			<table class="formTable" cellpadding="3"
				style="width: 100%; padding: 10 0 10 0">
				<tr>
					<td>外设类型:</td>
					<td><form:hidden path="id" />
						<form:hidden path="status" value="0" />
						<form:hidden path="version" /> <select name="deviceType"
						id="deviceType" style="width: 135px;">
							<c:forEach items="${deviceTypes }" var="deviceType">
								<option value="${deviceType }">${deviceType }</option>
							</c:forEach>

					</select></td>
					<td>所属模板:</td>
					<td><form:hidden path="alarmTemplateId" id="templateId" /> <form:input
							class="easyui-validatebox" id="templateName"
							path="alarmTemplateName" /></td>
				</tr>
				<tr>
					<td>报警等级:</td>
					<td><input name="alarmLevel.id" id="alarmLevelId"></td>
					<td>异常持续报警时间:</td>
					<td><input class="easyui-numberspinner" name="stayTime"
						data-options="min:0,max:100,value:0,width:135,editable:false">&nbsp;(分钟)</td>
				</tr>
				<tr>
					<td>重复报警次数:</td>
					<td><input class="easyui-numberspinner" name="repeatCount"
						data-options="min:0,max:100,value:0,width:135,editable:false"></td>
					<td>重复报警间隔时间:</td>
					<td><input class="easyui-numberspinner" name="intervalTime"
						data-options="min:0,max:100,value:0,width:135,editable:false">&nbsp;(小时)</td>
				</tr>
				<tr>
					<td>报警消失后是否通知:</td>
					<td><select name="noticeAfter" id="noticeAfter"
						class="easyui-combobox" data-options="panelHeight:49"
						style="width: 135px;">
							<option value="0">是</option>
							<option value="1">否</option>
					</select></td>
					<td>是否启用：</td>
					<td><select name="enabled" id="enabled"
						class="easyui-combobox" data-options="panelHeight:49"
						style="width: 135px;">
							<option value="0">启用</option>
							<option value="1">禁用</option>
					</select></td>
				</tr>
			</table>
		</form:form>
	</div>
	<script type="text/javascript">
		$(function() {
			$("#deviceType").combobox({
				panelHeight : 100,
				editable : false
			});
			$("#enabled").combobox({
				panelHeight : 49
			});
			$("#noticeAfter").combobox({
				panelHeight : 49
			});
			$("#alarmLevelId").combobox({
				url : '${ctx}/system/alarmLevel/getData',
				method : "get",
				width : 135,
				editable : false,
				valueField : 'id',
				textField : 'name',
				required : true
				

			});
			var action = "${action}";
			if (action == "add") {

				$("#templateId").val(checkedTemplateId);
				$("#templateName").val(checkedTemplateName);
				$("#deviceType").combobox("select", "UPS");
			} else if (action == "update") {
				$("#deviceType").combobox("select",
						"${alarmCondition.deviceType}");
				$("#alarmLevelId").combobox("setValue",
				"${alarmCondition.alarmLevel.id}");
					$("#enabled").combobox("select", "${alarmCondition.enabled}");
				$("#noticeAfter").combobox("select", "${alarmCondition.noticeAfter}");
			}
			$("#templateName").attr('readonly', 'readonly');
			$("#templateName").css('background', '#eee')
			//提交表单
			$('#condition_mainform').form({
				onSubmit : function() {

					var isValid = $(this).form('validate');
					return isValid; // 返回false终止表单提交
				},
				success : function(data) {

					successTip(data, condition_dg, condition_d);
				}
			});

		});
	</script>
</body>
</html>