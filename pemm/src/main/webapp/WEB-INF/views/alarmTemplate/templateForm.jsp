<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>模板窗口</title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>
<body>
	<div>
		<form:form id="mainform"
			action="${ctx }/system/alarmTemplate/${action }" method="post"
			style="width:100%;" modelAttribute="alarmTemplate">
			<table class="formTable" cellpadding="3"
				style="width: 100%; padding: 10 0 10 0">
				<tr>
					<td>模板名称:</td>
					<td><form:hidden path="version" /> <form:hidden path="id" />
						<form:hidden path="status" value="0"/> <form:input path="name"
							class="easyui-validatebox" data-options="required:true" style="width:250px;"/></td>
				</tr>
				<tr>
					<td>是否启用:</td>
					<td><select name="enabled" id="enabled"
						class="easyui-combobox" data-options="panelHeight:49"
						style="width: 258px;">
							<option value="0">启用</option>
							<option value="1">禁用</option>
					</select></td>
				</tr>
				<tr>
					<td>描述:</td>
					<td><form:textarea path="remark" class="easyui-validatebox"
							style="height: 60px; width: 250px;" /></td>
				</tr>

			</table>
		</form:form>
	</div>
	
	<script type="text/javascript">
	$("#enabled").combobox({
		panelHeight:49
	});
	$(function(){
		var action = "${action}";
		if(action=="add"){
			$("#enabled").combobox("select","0");
		}else if(action=="update"){
			$("#enabled").combobox("select","${alarmTemplate.enabled}");
		}
		//提交表单
		$('#mainform').form({
			onSubmit : function() {
			
				var isValid = $(this).form('validate');
				return isValid; // 返回false终止表单提交
			},
			success : function(data) {
				successTip(data, template_dg, template_d);
			}
		});
	});
	</script>
</body>
</html>