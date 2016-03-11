<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设备报警条件表单</title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
 <form:form action="${ctx }/system/deviceAlarmCondition/${action }" id="deviceAlarmCondition_mainForm" modelAttribute="deviceAlarmCondition">
 	<table class="formTable" cellpadding="3" style="width: 100%; padding: 10 0 10 0">
 		<tr>
 			<td style="width: 100px;">
 				所属模板:
 			</td>
 			<td>
 				<form:hidden path="id"/>
 				<form:hidden path="status" value="NORMAL"/>
				<form:hidden path="version"/>
				<form:hidden path="alarmTemplate.id" id="templateId"/>
 				<form:input class="easyui-validatebox"  path="alarmTemplate.name" id="templateName" style="width:150px;"/>
 			</td>
 			
 		</tr>
 		<tr>
 			<td>条件名称:</td>
 			<td>
 				<form:input class="easyui-validatebox" path="name" style="width:150px;"/>
 			</td>
 		</tr>
 		<tr>
 			<td>报警等级:</td>
			<td><form:input path="alarmLevel.id" id="alarmLevelId"/></td>
		
 		</tr>
 		<tr>
 		<td>设备类型:</td>
			<td>
				<select id="alarmDeviceType" name="alarmDeviceType" style="width: 150px;">
					<c:forEach items="${alarmDeviceTypes }" var="alarmDeviceType">
						<option value="${alarmDeviceType }">${alarmDeviceType.name}</option>
					</c:forEach>
				</select>
			</td>
 		</tr>
 		<tr>
 				<td>告警设备:</td>
			<td>
				<input name="alarmDevice" class="easyui-validatebox" id="alarmDevice">
			</td>
 		</tr>
 		<tr>
 		 <td>是否启用：</td>
		<td><select name="enabled" id="enabled"
						class="easyui-combobox" data-options="panelHeight:49"
						style="width: 150px;">
							<option value="ENABLED">启用</option>
							<option value="DISABLED">禁用</option>
		</select></td> 
 		</tr>
 		
 	</table>
 </form:form>
 
 <script type="text/javascript">
 $(function(){
	 $("#alarmLevelId").combobox({
		url : '${ctx}/system/alarmLevel/getData',
		method : "get",
		width : 158,
		editable : false,
		valueField : 'id',
		textField : 'name',
		required : true
		

	});
	$("#enabled").combobox({
		panelHeight : 49,
		width : 158
	});
	$("#alarmDeviceType").combobox({
		panelHeight:100,
		editable:false,
		width : 158,
		onSelect:function(v){
			$("#alarmDevice").combobox("options").url=ctx+"/system/deviceAlarmCondition/getAlarmDeviceListByAlarmDeviceType/"+v.value;
			$("#alarmDevice").combobox("reload");
		}
	});
	var alarmDeviceComboboxUrl=ctx + "/system/deviceAlarmCondition/getAlarmDeviceListByAlarmDeviceType/"+$("#alarmDeviceType").combobox("getValue");
	
	$("#alarmDevice").combobox({
		url : alarmDeviceComboboxUrl,
		valueField:'value',
		textField:'name',
		panelHeight:100,
		width:158,
		editable:false,
		onLoadSuccess:function(record){
			if("${deviceAlarmCondition.alarmDeviceType}"!=$("#alarmDeviceType").combobox("getValue")){
				if(record.length!=0){
					$(this).combobox("select",record[0].value);
				}else{
					$(this).combobox("clear");
				}
			}else{
				$(this).combobox("select","${deviceAlarmCondition.alarmDevice}");
			}
		}
	});
	var action="${action}";
	if(action=="add"){
		$("#templateId").val(checkedTemplateId);
		$("#templateName").val(checkedTemplateName);
	}else{
		
		$("#alarmLevelId").combobox("setValue","${deviceAlarmCondition.alarmLevel.id}");
		$("#enabled").combobox("select", "${deviceAlarmCondition.enabled}");
		$("#alarmDeviceType").combobox("select","${deviceAlarmCondition.alarmDeviceType}");

	}
	$("#templateName").attr('readonly', 'readonly');
	$("#templateName").css('background', '#eee')
	$("#deviceAlarmCondition_mainForm").form({
		onSubmit:function(){
			var isValid = $(this).form('validate');
			return isValid; // 返回false终止表单提交
		},
		success:function(data){
			successTip(data, condition_dg, condition_dlg);
		}
	});
 });
 </script>
</body>
</html>