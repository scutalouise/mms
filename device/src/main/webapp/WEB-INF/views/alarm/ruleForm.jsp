<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>规则窗口</title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>
<body>
	<div>
		<form:form id="rule_mainform"
			action="${ctx }/system/alarmRule/${action }" method="post"
			style="width:100%;" modelAttribute="alarmRule">
			<table class="formTable" cellpadding="3"
				style="width: 100%; padding: 10 0 10 0">
				<%-- <tr>
				<td width="80px">报警条件:</td>
					<td><form:input path="alarmCondition.name"
							id="alarmConditionName" class="easyui-validatebox"/> <form:hidden id="alarmConditionId"
						path="alarmCondition.id"/></td>
									</tr> --%>
				<tr>
				<td width="80px">设备类型:</td>
					<td><input type="text" id="rule_alarmDeviceType" class="easyui-validatebox">
					<form:hidden id="alarmConditionId"
						path="alarmCondition.id"/>
					</td>
				
					<td width="80px">告警类型:</td>
					<td>
						<form:hidden path="id" /> 
						<form:hidden path="status" value="NORMAL" />
						<form:hidden path="version" />
						<input type="text" id="rule_alarmOptionType" class="easyui-validatebox">
					</td>
					
				</tr>
				<tr>
					<td>报警类型:</td>
					<td><form:select path="alarmRuleType" id="alarmRuleType"
							style="width: 135px;">
							<c:forEach items="${alarmRuleTypes }" var="alarmRuleType">
								<option value="${alarmRuleType }">${alarmRuleType.name }</option>
							</c:forEach>
						</form:select></td>
					<td>规则比较:</td>
					<td><form:select path="operationType" id="operationType"
							style="width: 135px;">
							<c:forEach items="${operationTypes }" var="operationType">
								<option value="${operationType }">${operationType.description }</option>
							</c:forEach>
						</form:select></td>
				</tr>
				<tr >
					<td>规则类型:</td>
					<td>
					<select class="easyui-combobox" name="state" id="state" style="width:135px;">
						<option value="warning">告警</option>
						<option value="error">异常</option>
					</select>
					</td>
					<td><label id="value_label">告警值:</label></td>
					<td><label id="input_label"><form:input path="value" id="value" class="easyui-numberspinner"
							style="width:135px;" /></label></td>
				</tr>
				<tr id="tr_intervalValue">
					<td>最小值:</td>
					<td><form:input path="minValue" id="minValue"  class="easyui-numberspinner"
							style="width:135px;" /></td>
					<td>最大值:</td>
					<td><form:input path="maxValue" id="maxValue" class="easyui-numberspinner"
							style="width:135px;" /></td>
				</tr>
				<tr>
					<td>消息内容:</td>
					<td colspan="3">
					<form:textarea path="remark" 
					class="easyui-validatebox" style="height: 60px; width: 386px;" data-options="required:true"></form:textarea></td>
				</tr>
			</table>
		</form:form>
	</div>
	<script type="text/javascript">
	var dialog_window = $("#dlgContent").parent();

	var dialog_window_shadow = dialog_window.next(".window-shadow");
	function dialogTop() {
		var top = ($(window).height() - $("#dlgContent").parent().height()) / 2;
		dialog_window.css("top", top);
		dialog_window_shadow.css("top", top);

	}
		$(function() {
			$("#value,#maxValue,#minValue").numberspinner({
				
				 missingMessage:"该输入项为必输项"
			});
			$("#maxValue").numberspinner({
				validType:"maxValue['#minValue']",
			 missingMessage:"该输入项为必输项"
		});
			$("#minValue").numberspinner({
				validType:"minValue['#maxValue']",
				 missingMessage:"该输入项为必输项"
			});
			
			var action = "${action}";
			$("#alarmRuleType").combobox({
				panelHeight : 100,
				editable : false
			});
			$("#state").combobox({
				panelHeight:60,
				editable:false,
				onChange:function(value){
					if(value=="warning"){
						$("#value_label").html("告警值");
					}else{
						$("#value_label").html("异常值");
					}
				}
			});
			$("#operationType").combobox({
				panelHeight : 150,
				editable : false,
				onChange : function(newValue, oldValue) {
					if (newValue != "BELONGTO" && newValue != "NOTBELONGTO") {
						$("#tr_intervalValue").hide();
						$("#value_label").show();
						$("#input_label").show();
					
						$("#value").numberspinner({
							required:true
						});
						
						$("#minValue").numberspinner({
							required:false
						});
						$("#maxValue").numberspinner({
							required:false
						});
						$("#minValue").numberspinner("setValue",null);
						$("#maxValue").numberspinner("setValue",null);
					} else {
						$("#value_label").hide();
						$("#input_label").hide();
						$("#tr_intervalValue").show();
						
						$("#value").numberspinner({
							required:false
						});
						$("#value").numberspinner("setValue",null);
						$("#minValue").numberspinner({
							validType:"minValue['#maxValue']",
							required:true
						});
						$("#maxValue").numberspinner({
							validType:"maxValue['#minValue']",
							required:true
						});
						
					}
					dialogTop();
				}
			});
		//	$("#rule_deviceInterfaceType").val(checkedDeviceInterfaceType);
			var deviceInterfaceTypeText="";
			
			/* if(checkedDeviceInterfaceType=="UPS"){
				deviceInterfaceTypeText="UPS";
			}else if(checkedDeviceInterfaceType=="TH"){
				deviceInterfaceTypeText="温湿度";
			}else if(checkedDeviceInterfaceType=="WATER"){
				deviceInterfaceTypeText="水浸";
			}else if(checkedDeviceInterfaceType=="SMOKE"){
				deviceInterfaceTypeText="烟感";
			} */
		
			
			$("#rule_deviceInterfaceTypeText").val(deviceInterfaceTypeText);
			$("#rule_deviceInterfaceTypeText").attr("readonly", "readonly");
			$("#rule_deviceInterfaceTypeText").css('background', '#eee')
			/* $("#alarmConditionId").combobox({
				url : '${ctx}/system/alarmCondition/getCondition',
				queryParams : {
					filter_EQI_alarmTemplateId : checkedTemplateId,
				},
				method : "get",
				width : 135,
				editable : false,
				valueField : 'id',
				textField : 'name',
				required : true,
				panelHeight : 150
			}); */
			//$("#alarmConditionId").combobox("setValue", checkedConditionId);
		
			$("#rule_alarmDeviceType").val(checkedConditionData.alarmDeviceType.name);
			$("#rule_alarmOptionType").val(checkedConditionData.alarmOptionType.name);
			$("#alarmConditionId").val(checkedConditionId);
			$("#alarmConditionName").val(checkedConditionName);
			$("#rule_alarmDeviceType,#rule_alarmOptionType").attr("readonly", "readonly");
			$("#rule_alarmDeviceType,#rule_alarmOptionType").css('background', '#eee')
			if (action == "update") {
				$("#state").combobox("setValue","${alarmRule.state}");
				$("#operationType").combobox("setValue",
						"${alarmRule.operationType}");
				$("#alarmRuleType").combobox("setValue",
				"${alarmRule.alarmRuleType}");
				
			}
			if ($("#operationType").combobox("getValue") != "BELONGTO"
					&& $("#operationType").combobox("getValue") != "NOTBELONGTO") {
				$("#tr_intervalValue").hide();
				
				
				$("#value").numberspinner("options").required=true;
				$("#minValue").numberspinner("options").required=false;
				$("#maxValue").numberspinner("options").required=false;
			} else {
				
				
				$("#tr_intervalValue").show();
				$("#minValue").numberspinner("options").required=true;
				$("#maxValue").numberspinner("options").required=true;
				$("#value").numberspinner("options").required=false;
			}

			//提交表单
			$('#rule_mainform').form({
				onSubmit : function() {

					var isValid = $(this).form('validate');
					return isValid; // 返回false终止表单提交
				},
				success : function(data) {

					successTip(data, rule_dg, rule_d);
				}
			});
		});
	</script>
</body>
</html>