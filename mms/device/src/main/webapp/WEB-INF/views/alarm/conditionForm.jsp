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
				<td>
					告警设备
				</td>
				<td>
				<form:hidden path="id" />
						<form:hidden path="status" value="NORMAL" />
						<form:hidden path="version" /> 
						<select  id="alarmDeviceType" name="alarmDeviceType" style="width: 135px;">
							<c:forEach items="${alarmDeviceTypes}" var="alarmDeviceType">
								<option value="${alarmDeviceType }">${alarmDeviceType.name}</option>
							</c:forEach>
						</select>
				
				</td>
				<td>
					告警操作类型:
				</td>
				<td>
					<input name="alarmOptionType" id="alarmOptionType">
				</td>
				</tr>
				<tr>
				
				<td>报警条件名称:</td>
				<td>
					<form:input path="name" class="easyui-validatebox" data-options="required:true"/>
				</td>
				<td>所属模板:</td>
					<td><form:hidden path="alarmTemplate.id" id="templateId" />
					 <form:input
							class="easyui-validatebox" id="templateName"
							path="alarmTemplate.name" />
					</td>
				</tr>
				<tr>
				
					<td>报警等级:</td>
					<td><input name="alarmLevel.id" id="alarmLevelId"></td>
					<td>异常持续报警时间:</td>
					<td>
					<label id="other_stayTime">
						<input class="easyui-numberspinner" name="stayTime" id="stayTime"
							data-options="min:0,max:100,value:0,width:135,editable:true,required:true" missingMessage="该输入项为必输项">&nbsp;(分钟)</label>
								<label id="switchInput_stayTime">
						<input name="stayTime" class="easyui-validatebox" data-options="disabled:true" value="0"/>&nbsp;(分钟)</label>
					
					</td>
					</tr>
				<tr>
				
					
				
					<td>重复报警次数:</td>
					<td><input class="easyui-numberspinner" name="repeatCount" id="repeatCount"
						data-options="min:0,max:100,value:0,width:135,editable:true,required:true"  missingMessage="该输入项为必输项"></td>
					<td>重复报警间隔时间:</td>
					<td><input class="easyui-numberspinner" name="intervalTime" id="intervalTime"
						data-options="min:0,max:100,value:0,width:135,editable:true,required:true"  missingMessage="该输入项为必输项">&nbsp;(小时)</td>
				
					</tr>
				<tr>
				
					<td>报警消失后是否通知:</td>
					<td>
						<select name="noticeAfter" id="noticeAfter"
							class="easyui-combobox" 
							data-options="panelHeight:49"
							style="width: 135px;">
							<option value="0">是</option>
							<option value="1">否</option>
						</select>
					</td>
					 <td>是否启用：</td>
					<td><select name="enabled" id="enabled"
						class="easyui-combobox" data-options="panelHeight:49"
						style="width: 135px;">
							<option value="ENABLED">启用</option>
							<option value="DISABLED">禁用</option>
					</select></td> 
				</tr>
				
			</table>
		</form:form>
	</div>
	<script type="text/javascript">
		$(function() {
			
			$("#alarmOptionType").combobox({
				url : ctx + "/system/alarmCondition/getAlarmOptionTypeListByAlarmDeviceType/PEDEVICE",
				valueField:'value',
				textField:'name',
				panelHeight:100,
			
				editable:false,
				onLoadSuccess:function(record){
					
					if ("${alarmCondition.alarmDeviceType}" != $("#alarmDeviceType").combobox("getValue")) {
						if(record.length!=0){
							$(this).combobox("select",record[0].value);
						}else{
							$(this).combobox("clear");
						}
					}else{
						$(this).combobox("select","${alarmCondition.alarmOptionType}");
					}
				},
				onSelect:function(v){
				
					if(v!=undefined){
						if(v.name=="水浸"||v.name=="烟感"){
							
							$("#other_stayTime").hide();
							$("#switchInput_stayTime").show();
							
						}else{
							$("#other_stayTime").show();
							$("#switchInput_stayTime").hide();
							
						}
					}
				}
				
				
			});
			
			$("#alarmDeviceType").combobox({
				panelHeight : 100,
				editable : false,
				onSelect:function(v){
				
					
						$("#alarmOptionType").combobox("options").url=ctx + "/system/alarmCondition/getAlarmOptionTypeListByAlarmDeviceType/"+v.value;
						$("#alarmOptionType").combobox("reload");
						
						
					
					
					
					
					$("#stayTime").numberspinner({
						value:0
					});
				}
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
				
				 $("#other_stayTime").show();
				$("#switchInput_stayTime").hide(); 
				$("#templateId").val(checkedTemplateId);
				$("#templateName").val(checkedTemplateName);
				
			} else if (action == "update") {

				$("#alarmDeviceType").combobox("select","${alarmCondition.alarmDeviceType}");
				
				$("#alarmOptionType").combobox("select","${alarmCondition.alarmOptionType}");
				
				if("${alarmCondition.alarmOptionType}"=="WATER"||"${alarmCondition.alarmOptionType}"=="SMOKER"){
					
					$("#other_stayTime").hide();
					$("#switchInput_stayTime").show();
					
				}else{
					$("#other_stayTime").show();
					$("#switchInput_stayTime").hide();
					
				}
				
				$("#alarmLevelId").combobox("setValue","${alarmCondition.alarmLevel.id}");
				 $("#enabled").combobox("select", "${alarmCondition.enabled}");
				$("#noticeAfter").combobox("select", "${alarmCondition.noticeAfter}");
				$("#stayTime").numberspinner({
					value:"${alarmCondition.stayTime}"
				});
				
				$("#repeatCount").numberspinner({
					value:"${alarmCondition.repeatCount}"
				});
				$("#intervalTime").numberspinner({
					value:"${alarmCondition.intervalTime}"
				});
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