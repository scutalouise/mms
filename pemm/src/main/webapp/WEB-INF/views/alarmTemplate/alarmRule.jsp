<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>告警规则</title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>
<body>
	<div id="rule_tb">
		<div>
			<shiro:hasPermission name="sys:alarmRule:add">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRule()">
					<a class="condition_a" href="javascript:addRule()">添加</a>
				</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:alarmRule:delete">
				<a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-remove" plain="true" data-options="disabled:false"
				onclick="delRule()"><a href="javascript:delRule()" class="condition_a">删除</a></a> <span
				class="toolbar-item dialog-tool-separator"></span> 
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:alarmRule:update">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updateRule()">
					<a href="javascript:updateRule()" class="condition_a">修改</a>
				</a>
			</shiro:hasPermission>
		</div>
	</div>
	<div id="rule_dg"></div>
	<div id="rule_dlg"></div>
	<script type="text/javascript">
		var rule_dg;
		var rule_d;
		$(function() {
			rule_dg = $("#rule_dg")
					.datagrid(
							{
								method : "post",
								fit : true,
								fitColumns : true,
								border : false,
								idField : "id",
								sriped : true,
								pagination : true,
								rownumbers : true,
								pageNumber : 1,
								pageSize : 20,
								pageList : [ 10, 20, 30, 40, 50 ],
								singleSelect : true,
								columns : [ [
										{
											field : 'id',
											checkbox : true
										},
										{
											field : "alarmType",
											title : "告警类型",
											sortable : true,
											width : 100,
											formatter : function(v) {
												var html = "";
												if (v == "COMMUNICATIONSTATUS") {
													html = "通讯状态";
												} else if (v == "BYPASSVOLTAGE") {
													html = "UPS旁路电压";
												} else if (v == "INPUTVOLTAGE") {
													html = "输入电压";
												} else if (v == "OUTPUTVOLTAGE") {
													html = "输出电压";
												} else if (v == "INTERNALTEMPERATURE") {
													html = "机内温度";
												} else if (v == "BATTERYVOLTAGE") {
													html = "电池电压";
												}else if(v=="ELECTRICQUANTITY"){
													html="电池电量";
												}else if(v=="TEMPERATURE"){
													html="温度";
												}else if(v=="HUMIDITY"){
													html="湿度";
												}else if(v=="WATERIMMERSIONSTATE"){
													html="水浸状态";
												}else if(v=="SMOKESTATE"){
													html="烟感状态";
												}else if(v=="LOAD"){
													html="负载";
												}
												return html;
											}
										},
										{
											field : "deviceInterfaceType",
											title : "外设类型",
											sortable : true,
											width : 100,formatter:function(v){
												var html="";
												if(v=="UPS"){
													html="UPS";
												}else if(v=="TH"){
													html="温湿度";
												}else if(v=="WATER"){
													html="水浸";
												}else if(v=="SMOKE"){
													html="烟感";
												}
												return html;
												
											}
										},
										{
											field : "alarmConditionId",
											title : "所属报警条件",
											sortable : true,
											width : 100,
											formatter : function(v) {
												return checkedConditionName;
											}
										},{
											field:"ruleAlarmType",
											title:"规则告警 类型",
											sortable:true,width:100,
											formatter:function(v){
												if(v=="warning"){
													return "告警";
												}else if(v=="error"){
													return "异常";
												}
											}
											
										},
										{
											field : "value",
											title : "触发条件",
											width : 100,
											formatter : function(v, rowData) {
												var html = "";
												if (rowData.operationType == "GT") {
													html = "大于" + v;
												} else if (rowData.operationType == "EQ") {
													html = "等于" + v
												} else if (rowData.operationType == "GE") {
													html = "大于或等于" + v
												} else if (rowData.operationType == "LT") {
													html = "小于" + v
												} else if (rowData.operationType == "LE") {
													html = "小于或等于" + v
												} else if (rowData.operationType == "BELONGTO") {
													html = "在区间["
															+ rowData.minValue
															+ "-"
															+ rowData.maxValue
															+ "]之间"
												} else if (rowData.operationType == "NOTBELONGTO") {
													html = "不在区间["
															+ rowData.minValue
															+ "-"
															+ rowData.maxValue
															+ "]之间"
												}
												return html;
											}
										}, {
											field : "remark",
											title : "描述",
											sortable : true,
											width : 150
										} ] ],
								enableHeaderClickMenu : true,
								enableHeaderContextMenu : true,
								enableRowContextMenu : false,
								toolbar : '#rule_tb'
							});
		});
	</script>
</body>
</html>