var tab;
var dg;
var d;
$(function() {
	if (isUps == "true") {
		$("#tab")
				.tabs(
						{
							onSelect : function(title) {
								if (title == "历史状态") {
									var tab_device = $('#tab_device').tabs(
											'getSelected');
									var tab_title = tab_device.panel('options').title;

									var index = tab_title.substring(tab_title
											.indexOf("-") + 1, tab_title
											.indexOf("】"));
									dg = $("#dg")
											.datagrid(
													{
														method : "get",
														url : ctx
																+ "/upsStatus/upsStatusList",
														queryParams : {
															filter_EQI_status : '1',
															gitInfoId : gitInfoId,
															index : index
														},
														fit : true,
														fitColumns : true,
														border : true,
														idField : "id",
														striped : true,
														pagination : true,
														rownumbers : true,
														pageNumber : 1,
														pageSize : 20,
														pageList : [ 10, 20,
																30, 40, 50 ],
														singleSelect : false,

														columns : [ [
																{
																	field : "id",
																	checkbox : true

																},
																{
																	field : "name",
																	title : "设备名称",
																	sortable : true,
																	width : 50,
																	hidden : true
																},
																{
																	field : "interfaceType",
																	title : "接口类型",
																	sortable : true,
																	width : 50,
																	hidden : true,
																	formatter : function(
																			value) {
																		var result = "";
																		switch (value) {
																		case 0:
																			result = "RS232";
																			break;
																		case 1:
																			result = "RS485";
																			break;
																		case 2:
																			result = "zigbee";

																		}
																		return result;
																	}
																},
																{
																	field : "communicationStatus",
																	title : "通讯状态",
																	sortable : true,
																	width : 50,
																	hidden : true,
																	formatter : function(
																			value) {

																		var result = "";
																		switch (value) {
																		case 0:
																			result = "正常";
																			break;
																		case 1:
																			result = "UPS无响应";
																			break;
																		case 2:
																			result = "UPS未识别";

																		}
																		return result;

																	}
																},
																{
																	field : "dischargePatterns",
																	title : "放电模式",
																	sortable : true,
																	width : 50,
																	hidden : true,
																	formatter : function(
																			value) {
																		var result = "";
																		switch (value) {
																		case 0:
																			result = "不支持";
																			break;
																		case 1:
																			result = "UPS内置";
																			break;
																		case 2:
																			result = "电柜";

																		}
																		return result;
																	}
																},
																{
																	field : "upsType",
																	title : "UPS类型",
																	sortable : true,
																	width : 50,
																	hidden : true,
																	formatter : function(
																			value) {
																		var result = "";
																		switch (value) {
																		case 0:
																			result = "未知";
																			break;
																		case 1:
																			result = "2相";
																			break;
																		case 2:
																			result = "三进三出";
																			break;
																		case 3:
																			result = "三进一";

																		}
																		return result;
																	}
																},
																{
																	field : "modelNumber",
																	title : "UPS型号",
																	sortable : true,
																	hidden : true,
																	width : 50
																},
																{
																	field : "brand",
																	title : "厂商品牌",
																	sortable : true,
																	hidden : true,
																	width : 50
																},
																{
																	field : "versionNumber",
																	title : "版本号",
																	sortable : true,
																	hidden : true,
																	width : 50
																},
																{
																	field : "rateVoltage",
																	title : "额定电压",
																	sortable : true,
																	width : 50,
																	hidden : true,
																	formatter : function(
																			value) {
																		return value
																				+ "v";
																	}
																},
																{
																	field : "ratedCurrent",
																	title : "额定电流",
																	sortable : true,
																	width : 50,
																	hidden : true,
																	formatter : function(
																			value) {
																		return value
																				+ "A";
																	}
																},
																{
																	field : "batteryVoltage",
																	title : "电池电压",
																	sortable : true,
																	width : 50,
																	hidden : true,
																	formatter : function(
																			value) {
																		return value
																				+ "V";
																	}
																},
																{
																	field : "ratedFrequency",
																	title : "额定频率",
																	sortable : true,
																	width : 50,
																	hidden : true,
																	formatter : function(
																			value) {
																		return value
																				+ "Hz";
																	}
																},
																{
																	field : "power",
																	title : "功率",
																	sortable : true,
																	width : 30,
																	formatter : function(
																			value) {
																		return value
																				+ "KVA";
																	}
																},
																{
																	field : "upsStatus",
																	title : "UPS状态",
																	sortable : true,
																	hidden : true,
																	width : 50
																},
																{
																	field : "frequency",
																	title : "*频率",
																	sortable : true,
																	width : 30,
																	formatter : function(
																			value) {
																		return value
																				+ "Hz";
																	}
																},
																{
																	field : "internalTemperature",
																	title : "机内温度",
																	sortable : true,
																	hidden : false,
																	width : 50,
																	formatter : function(
																			value) {
																		return value
																				+ "℃";
																	}
																},
																{
																	field : "bypassVoltage",
																	title : "*旁路电压",
																	sortable : true,
																	width : 30,
																	hidden : true

																},
																{
																	field : "bypassFrequency",
																	title : "旁路频率",
																	sortable : true,
																	width : 30,
																	formatter : function(
																			value) {
																		return value
																				+ "Hz";
																	}
																},
																{
																	field : "inputVoltage",
																	title : "输入电压",
																	sortable : true,
																	width : 50,
																	formatter : function(
																			value) {
																		return value;
																	}

																},
																{
																	field : "outputVoltage",
																	title : "输出电压",
																	sortable : true,
																	width : 50,
																	formatter : function(
																			value) {
																		return value;
																	}
																},
																{
																	field : "errorVoltage",
																	title : "故障电压",
																	sortable : true,
																	width : 30,
																	formatter : function(
																			value) {
																		return value
																				+ "V"
																	}
																},
																{
																	field : "upsLoad",
																	title : "负载",
																	sortable : true,
																	width : 50,
																	formatter : function(
																			value) {
																		return value;
																	}
																},
																{
																	field : "outputFrenquency",
																	title : "*输出频率",
																	sortable : true,
																	width : 30,
																	formatter : function(
																			value) {
																		return value
																				+ "Hz";

																	}

																},
																{
																	field : "singleVoltage",
																	title : "单节电压",
																	sortable : true,
																	width : 30,
																	formatter : function(
																			value) {
																		return value
																				+ "V";
																	}
																},
																{
																	field : "totalVoltage",
																	title : "*总电压",
																	sortable : true,
																	width : 30,
																	formatter : function(
																			value) {
																		return value
																				+ "V"

																	}
																},
																{
																	field : "electricQuantity",
																	title : "充电量",
																	sortable : true,
																	width : 50,
																	hidden : true,
																	formatter : function(
																			value) {
																		return value
																				+ "%"
																	}
																},
																{
																	field : "passCurrent",
																	title : "*充/放电电流",
																	sortable : true,
																	width : 50,
																	hidden : true,
																	formatter : function(
																			value) {
																		return value
																				+ "A";
																	}
																},
																{
																	field : "remainingTime",
																	title : "*剩余时间",
																	sortable : true,
																	width : 40,
																	hidden : true,
																	formatter : function(
																			value) {
																		return value
																				+ "分钟"
																	}
																},
																{
																	field : "collectTime",
																	title : "获取时间",
																	sortable : true,
																	width : 50,
																	formatter : function(
																			value,
																			rowData,
																			rowIndex) {
																		return formatDate(
																				value,
																				"yyyy-MM-dd HH:mm:ss")
																	}

																},
																{
																	field : "detail",
																	title : "详情",
																	width : 20,
																	align : "center",
																	formatter : formatter
																}

														] ],
														enableHeaderClickMenu : true,
														enableHeaderContextMenu : true,
														enableRowContextMenu : false,
														toolbar : '#tb',
														onLoadSuccess : function() {
															createTooltip();

														}

													});
								}
							}
						});

		$("#tab_device").tabs(
				{
					onSelect : function(title) {

						if (title.indexOf("UPS") > 0) {

							var index = title.substring(title.indexOf("-") + 1,
									title.indexOf("】"));
							dg.datagrid("reload", {
								gitInfoId : gitInfoId,
								index : index
							});
						}
					}
				});
	} else {
		$("#tab").tabs("close", "历史状态")
	}

});

function formatter(value, row, index) {
	return '<span data-p='
			+ index
			+ ' class="easyui-tooltip" style="z-index:10000"><a href="javascript:void(0)">详情</a></span>';
}

function createTooltip() {
	dg
			.datagrid('getPanel')
			.find('.easyui-tooltip')
			.each(
					function() {
						var index = parseInt($(this).attr('data-p'));
						$(this)
								.tooltip(
										{
											content : $('<div></div>'),
											onUpdate : function(cc) {
												var row = $('#dg').datagrid(
														'getRows')[index];

												var interfaceType = "";
												switch (row.interfaceType) {
												case 0:
													interfaceType = "RS232";
													break;
												case 1:
													interfaceType = "RS485";
													break;
												case 2:
													interfaceType = "zigbee";
												}

												var content = '<div id="tooltiptitle">内容信息</div><ul id="tooltipul">';
												content += '<li>设备名称: '
														+ row.name + '</li>';
												content += '<li>接口类型: '
														+ interfaceType
														+ '</li>';

												var communicationStatus = "";
												switch (row.communicationStatus) {
												case 0:
													communicationStatus = "正常";
													break;
												case 1:
													communicationStatus = "UPS无响应";
													break;
												case 2:
													communicationStatus = "UPS未识别";

												}

												content += '<li>通讯状态: '
														+ communicationStatus
														+ '</li>';

												var dischargePatterns = "";
												switch (row.dischargePatterns) {
												case 0:
													dischargePatterns = "不支持";
													break;
												case 1:
													dischargePatterns = "UPS内置";
													break;
												case 2:
													dischargePatterns = "电柜";

												}
												content += '<li>放电模式: '
														+ dischargePatterns
														+ '</li>';

												var upsType = "";
												switch (row.upsType) {
												case 0:
													upsType = "未知";
													break;
												case 1:
													upsType = "2相";
													break;
												case 2:
													upsType = "三进三出";
													break;
												case 3:
													upsType = "三进一";

												}

												content += '<li>UPS类型: '
														+ upsType + '</li>';
												content += '<li>型号: '
														+ (row.modelNumber != null ? row.modelNumber
																: "未知")
														+ '</li>';
												content += '<li>厂商品牌: '
														+ (row.brand != null ? row.brand
																: "未知")
														+ '</li>';
												content += '<li>版本号: '
														+ (row.versionNumber != null ? row.versionNumber
																: "未知")
														+ '</li>';

												content += '<li>额定电压: '
														+ row.rateVoltage + "V"
														+ '</li>';

												content += '<li>额定电流: '
														+ row.ratedCurrent
														+ "A" + '</li>';
												content += '<li>额定频率: '
														+ row.ratedFrequency
														+ "Hz" + '</li>';
												content += '<li>电池电压: '
														+ parseFloat(row.batteryVoltage)
														/ 10 + "V" + '</li>';
												content += '<li>功率: '
														+ row.power + "KVA"
														+ '</li>';

												content += '<li>UPS状态: '
														+ row.upsStatus
														+ '</li>';

												content += '<li>*频率: '
														+ row.frequency + "Hz"
														+ '</li>';
												content += '<li>机内温度: '
														+ row.internalTemperature
														+ "℃" + '</li>';

												content += '<li>*旁路电压: '
														+ (row.bypassVoltage != null ? row.bypassVoltage
																: "") + '</li>';

												content += '<li>旁路频率: '
														+ row.bypassFrequency
														+ "Hz" + '</li>';

												content += '<li>输入电压: '
														+ row.inputVoltage
														 + '</li>';

												content += '<li>输出电压: '
														+ row.outputVoltage
														+ '</li>';

												content += '<li>故障电压: '
														+ row.errorVoltage
														+ "V" + '</li>';

												content += '<li>负载: '
														+ row.upsLoad
														+ '</li>';

												content += '<li>*输出频率: '
														+ row.outputFrenquency
														+ "Hz" + '</li>';

												content += '<li>单节电压: '
														+ row.singleVoltage
														+ "V" + '</li>';
												var totalVoltage;
												if (row.totalVoltage != null) {
													totalVoltage = row.totalVoltage
															+ "V";
												}
												content += '<li>*总电压: '
														+ totalVoltage
														+ '</li>';
												content += '<li>充电量: '
														+ row.electricQuantity
														+ "%" + '</li>';

												content += '<li>*充/放电电流: '
														+ row.passCurrent + "A"
														+ '</li>';
												content += '<li>*剩余时间: '
														+ row.remainingTime
														+ "分钟" + '</li>';
												content += '<li style="width:200px;">获取时间: '
														+ formatDate(
																row.collectTime,
																"yyyy-MM-dd HH:mm:ss");
												+'</li>';
												content += '</ul>';

												cc.panel({
													width : 730,
													content : content
												});
											},
											position : 'left'
										});
					});
}

function upsOperation(gitInfoId, oidStr, instruction) {
	$.ajax({
		type : "get",
		url : ctx + "/device/upsOperation",
		data : {
			gitInfoId : gitInfoId,
			oidStr : oidStr,
			instruction : instruction

		},
		success : function(result) {
			parent.successTip(result);
		}
	});

}

function del() {
	// var row = dg.datagrid('getSelected');
	// if (rowIsNull(row))
	// return;
	// parent.parent.$.messager.confirm('提示', '删除后无法恢复,您确定要删除？', function(data)
	// {
	// if (data) {
	// $.ajax({
	// type : 'get',
	// url : ctx + "/upsStatus/delete/" + row.id,
	// success : function(data) {
	// parent.successTip(data, dg);
	// }
	// });
	// }
	// });

	var ids = "";
	var rows = $('#dg').datagrid('getSelections');
	if (rows.length < 1) {
		rowIsNull();
		return;
	}
	parent.parent.$.messager.confirm('提示', '删除后无法恢复，您确定要删除？', function(data) {

		if (data) {
			for (var i = 0; i < rows.length; i++) {
				var row = rows[i];
				if (i < rows.length - 1) {
					ids += "'" + row.id + "',";
				} else {
					ids += "'" + row.id + "'"
				}
			}
			$.ajax({
				type : 'get',
				url : ctx + "/upsStatus/delete",
				data : {
					ids : ids
				},
				success : function(data) {
					parent.successTip(data, dg);
				}
			});
		}
	});
}

function refresh() {
	$.ajax({
				type : "post",
				url : ctx + "/upsStatus/getLatestData",
				data : {
					gitInfoId : gitInfoId
				},
				success : function(data) {
                    var upsData=data.upsStatusList;
					for (var i = 0; i < upsData.length; i++) {
						$("#rateVoltage_" + i).html(
								(upsData[i].rateVoltage).toFixed(1) + "V");
						$("#ratedCurrent_" + i).html(
								(upsData[i].ratedCurrent).toFixed(1) + "A");
						$("#batteryVoltage_" + i).html(
								(upsData[i].batteryVoltage));
						$("#power_" + i).html(
								(upsData[i].power).toFixed(1) + "KVA");
						$("#upsStatus_" + i)
								.html(
										upsData[i].upsStatus == 1 ? "<span style='color: red;'>故障</span>"
												: "<span style='color: green;'>正常</span>");
						$("#internalTemperature_" + i).html(
								(upsData[i].internalTemperature).toFixed(1) + "℃");
						$("#bypassFrequency_" + i).html(
								(upsData[i].bypassFrequency).toFixed(1) + "Hz");
						$("#inputVoltage_" + i).html(
								(upsData[i].inputVoltage));
						$("#outputVoltage_" + i).html(
								(upsData[i].outputVoltage));
						$("#errorVoltage_" + i).html(
								(upsData[i].errorVoltage).toFixed(1) + "V");
						$("#upsLoad_" + i).html(
								(upsData[i].upsLoad));
						$("#singleVoltage_" + i).html(
								(upsData[i].singleVoltage).toFixed(1) + "V");
						$("#electricQuantity_" + i).html(
								(upsData[i].electricQuantity).toFixed(1) + "%");
						$("#cityVoltageStatus_" + i)
								.html(
										(upsData[i].cityVoltageStatus == 1 ? '<span style="color: red;">异常</span>'
												: '<span style="color: green;">正常</span>'));
						$("#batteryVoltageStatus_" + i)
								.html(
										(upsData[i].batteryVoltageStatus == 1 ? '<span style="color: red;">低</span>'
												: '<span style="color: green;">正常</span>'));
						$("#runningStatus_" + i)
								.html(
										(upsData[i].runningStatus == 1 ? '<span style="color: red;">旁路</span>'
												: '<span style="color: green;">正常</span>'));
						$("#testStatus_" + i)
								.html(
										(upsData[i].testStatus == 1 ? '<span style="color: green;">测试中</span>'
												: '<span style="color: green;">未测试</span>'));
						$("#shutdownStatus_" + i)
								.html(
										(upsData[i].shutdownStatus == 1 ? '<span style="color: red;">已关机</span>'
												: '<span style="color: green;">未关机</span>'));
						$("#buzzerStatus_" + i)
								.html(
										(upsData[i].buzzerStatus == 1 ? '<span style="color: green;">开</span>'
												: '<span style="color: red;">关</span>'));
						$("#linkState_" + i)
								.html(
										(upsData[i].linkState == 1 ? '<span style="color: green;">正常</span>'
												: '<span style="color: red;">丢失</span>'));
					}
					var thStatusData=data.thStatusList;
					for (var i = 0; i < thStatusData.length; i++) {
						$("#temperature_"+i).html(thStatusData[i].temperatur);
						$("#humidity_"+i).html(thStatusData[i].humidity);
					}
					var waterStatusData=data.waterStatusList;
					for (var i = 0; i < waterStatusData.length; i++) {
						var currentStatusHtml='<span style="color: green;">正常</span>';
						
						if(waterStatusData[i].currentState==1){
							currentStatusHtml='<span style="color: yellow;">警告</span>';
						}else if(waterStatusData[i].currentState==2){
							currentStatusHtml='<span style="color: red;">异常</span>';
						}
						$("#waterCurrentStatus_"+i).html(currentStatusHtml);
					
					}
					var smokeStatusData=data.smokeStatusList;
					for (var i = 0; i < smokeStatusData.length; i++) {
						var currentStatusHtml='<span style="color: green;">正常</span>';
						if(smokeStatusData[i].currentState==1){
							currentStatusHtml='<span style="color: yellow;">警告</span>';
						}else if(smokeStatusData[i].currentState==2){
							currentStatusHtml='<span style="color: red;">异常</span>';
						}
						$("#smokeCurrentStatus_"+i).html(currentStatusHtml);
					
					}
					
				}
			});
}
var interval = setInterval("refresh()", 10000);
