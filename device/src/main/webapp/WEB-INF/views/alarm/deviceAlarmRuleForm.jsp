<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设备告警规则表单</title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<form:form action="${ctx }/system/deviceAlarmRule/${action}"   id="deviceAlarmRule_mainForm" method="post" style="width:100%;" modelAttribute="deviceAlarmRule">
	<table class="formTable" cellpadding="3" style="width: 100%; padding: 10 0 10 0">
		<tr>
			<td style="width:100px;">
				告警条件:
			</td>
			<td>
				<form:hidden path="id"/>
				<form:hidden path="status" value="NORMAL"/>
				<form:hidden path="version"/>
				<form:hidden path="deviceAlarmCondition.id" id="deviceAlarmConditionId"/>
				<form:input class="easyui-validatebox"  path="deviceAlarmCondition.name" id="conditionName" style="width:135px;"/>
			</td>
			<td  style="width:100px;">是否启用：</td>
					<td><select name="enabled" id="enabled"
						class="easyui-combobox" data-options="panelHeight:49"
						style="width: 135px;">
							<option value="ENABLED">启用</option>
							<option value="DISABLED">禁用</option>
					</select></td> 
		</tr>
		<tr>
			<td>
				告警内容:
			</td>
			<td>
			<form:select path="deviceAlarmOption" id="deviceAlarmOption" style="width:135px;">
				<c:forEach items="${deviceAlarmOptions }" var="deviceAlarmOption">
					<option value="${deviceAlarmOption }">${deviceAlarmOption.value }</option>
				</c:forEach>
			</form:select>
			</td>
			<td style="width: 100px;">
				告警内容单位:
			</td>
			<td>
				<input id="alarmOptionUnit" name="alarmOptionUnit">
			</td>
		</tr>
		<tr>
			
			<td>规则比较:</td>
			<td><form:select path="operationType" id="operationType"
					style="width: 135px;">
					<c:forEach items="${operationTypes }" var="operationType">
						<option value="${operationType }">${operationType.description }</option>
					</c:forEach>
				</form:select>
			</td>
			<td>规则类型:</td>
			<td>
			<select class="easyui-combobox" name="state" data-options="panelHeight:90" id="state" style="width:142px;">
				<option value="warning">告警</option>
				<option value="error">异常</option>
			</select>
			</td>
			
		</tr>
		
		<tr id="tr_intervalValue">
					<td>最小值:</td>
					<td><form:input path="minValue" id="minValue"  class="easyui-numberspinner"
							style="width:142px;" /></td>
					<td>最大值:</td>
					<td><form:input path="maxValue" id="maxValue" class="easyui-numberspinner"
							style="width:142px;" /></td>
				</tr>
		<tr id="tr_value">
			<td><label id="value_label">告警值:</label></td>
			<td colspan="3"><label id="input_label"><form:input path="value" id="value" class="easyui-numberspinner"
								style="width:142px;" /></label>
			</td>
		</tr>
		<tr>
					<td>消息内容:</td>
					<td colspan="3"><form:textarea path="remark" 
							class="easyui-validatebox" style="height: 60px; width: 410px;" data-options="required:true"></form:textarea></td>
				</tr>
		
		
	</table>
</form:form>
<script type="text/javascript">
	$(function(){
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
		$("#alarmLevelId").combobox({
			url : '${ctx}/system/alarmLevel/getData',
			method : "get",
			width : 142,
			editable : false,
			valueField : 'id',
			textField : 'name',
			required : true
			

		});
		$("#deviceAlarmOption").combobox({
			panelHeight:100,
			width:142,
			editable:false,
			onSelect:function(v){
				$("#alarmOptionUnit").combobox("options").url=ctx+"/system/deviceAlarmRule/getAlarmOptionUnitByAlarmOption/"+v.value;
				$("#alarmOptionUnit").combobox("reload");
			}
			
		});
		var alarmOptionUnitUrl=ctx + "/system/deviceAlarmRule/getAlarmOptionUnitByAlarmOption/"+$("#deviceAlarmOption").combobox("getValue");

		$("#alarmOptionUnit").combobox({
			url : alarmOptionUnitUrl,
			valueField:'alarmOptionUnit',
			textField:'value',
			panelHeight:100,
			width:142,
			editable:false,
			onLoadSuccess:function(record){
				if("${deviceAlarmRule.deviceAlarmOption}"!=$("#deviceAlarmOption").combobox("getValue")){
					if(record.length!=0){
						
						$(this).combobox("select",record[0].alarmOptionUnit);
					}else{
						$(this).combobox("clear");
					}
				}else{
					$(this).combobox("select","${deviceAlarmRule.alarmOptionUnit}");
				}
			}
		});
		$("#enabled").combobox({
			panelHeight : 49,
			width:142
		});
		$("#operationType").combobox({
			panelHeight : 150,
			width:142,
			editable : false,
			onChange : function(newValue, oldValue) {
				if (newValue != "BELONGTO" && newValue != "NOTBELONGTO") {
					$("#tr_intervalValue").hide();
					$("#tr_value").show();
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
				}else{
					$("#tr_intervalValue").show();
					$("#tr_value").hide();
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
			}
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
		
		
	var action="${action}";
	
	
	
		if(action=="add"){
			$("#deviceAlarmConditionId").val(checkedConditionId);
			$("#conditionName").val(checkedConditionName);
			
		}else{
			
			$("#state").combobox("setValue","${deviceAlarmRule.state}");
			$("#operationType").combobox("setValue",
					"${deviceAlarmRule.operationType}");
			
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
		
		
		$("#deviceAlarmRule_mainForm").form({
			onSubmit:function(){
				var isValid = $(this).form('validate');
				return isValid; // 返回false终止表单提交
			},
			success:function(data){
				successTip(data, deviceRule_dg, deviceRule_dlg);
			}
		});
	});
</script>
</body>
</html>