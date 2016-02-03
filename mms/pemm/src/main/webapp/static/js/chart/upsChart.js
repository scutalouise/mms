var lastDate;
var type = "inputVoltage";

$(function() {
	var time = $("#showTime").combobox("getValue");

	initChart(deviceId, "inputVoltage", time);

	$("#tab_ups").tabs({
		onSelect : function(title) {
			if (title == "输入电压") {
				type = "inputVoltage";
			} else if (title == "输出电压") {
				type = "outputVoltage";
			} else if (title == "负载容量") {
				type = "upsLoad";
			} else if (title == "电池电压") {
				type = "batteryVoltage";
			}
			time = $("#showTime").combobox("getValue");

			initChart(deviceId, type, time)
		}
	});
	$("#showTime").combobox({
		onChange : function(value) {
			initChart(deviceId, type, value);
		}
	});
	$("#isRefresh").combobox({
		onChange : function(value) {
			if (value == "0") {
				collect();
			} else {
				closeInterval();
			}
		}
	});

});

function initChart(deviceId, type, time) {

	// 路径配置
	require.config({
		paths : {
			echarts : ctx + '/static/plugins/echarts/dist'
		}
	});

	require(
			[ 'echarts', 'echarts/chart/bar', 'echarts/chart/line'// 加载曲线图模块
			],
			function(ec) {
				var title = "输入电压曲线图";
				var name = [];
				var unit = "V";
				chart = ec.init($("#main")[0]);
				// chart.showLoading({
				// text : '正在努力加载中...'
				// });
				var loadingTicket;
				var effectIndex = -1;
				var effect = [ 'spin', 'bar', 'ring', 'whirling',
						'dynamicLine', 'bubble' ];
				effectIndex = ++effectIndex % effect.length;
				chart.showLoading({
					text : effect[effectIndex],
					effect : effect[effectIndex],
					textStyle : {
						fontSize : 20
					}
				});
				// chart.hideLoading();
				var categories = [];
				var values1 = [];
				var values2 = [];
				var values3 = [];
				var upsType;
				$.ajaxSettings.async = false;
				// 加载数据
				$.ajax({
					url : ctx + '/chart/upsJson',
					data : {
						deviceId : deviceId,
						time : time

					},
					success : function(result) {
						var json = eval(result.data);
						if (json.length > 0) {
							upsType = json[0].upsType;
						}

						if (type == "outputVoltage") {
							title = "输出电压曲线图";
							if (upsType == 2) {
								name[0] = "模块1输出电压";
								name[1] = "模块2输出电压";
								name[2] = "模块3输出电压";
							} else {
								name[0] = "输出电压";
							}
							unit = "V";
						} else if (type == "inputVoltage") {
							title = "输入电压曲线图";
							if (upsType == 2 || upsType == 3) {
								name[0] = "模块1输入电压";
								name[1] = "模块2输入电压";
								name[2] = "模块3输入电压";
							} else {
								name[0] = "输入电压";
							}

							unit = "V";
						} else if (type == "upsLoad") {
							title = "负载容量曲线图";
							if (upsType == 2 || upsType == 3) {
								name[0] = "模块1负载容量";
								name[1] = "模块2负载容量";
								name[2] = "模块3负载容量";
							} else {
								name[0] = "负载容量";
							}
							unit = "%";
						} else if (type == "batteryVoltage") {
							title = "电池电压曲线图";
							name[0] = "电池电压";
							unit = "V";
						}

						for (var i = 0; i < json.length; i++) {

							if (type == "inputVoltage") {
								var inputVoltage = json[i].inputVoltage;
								values1[i] = inputVoltage.substring(0,
										inputVoltage.indexOf("V"));
								values2[i] = inputVoltage.substring(
										inputVoltage.indexOf("V/") + 2,
										inputVoltage.lastIndexOf("V/"));
								values3[i] = inputVoltage.substring(
										inputVoltage.lastIndexOf("V/") + 2,
										inputVoltage.length - 1);

							} else if (type == "outputVoltage") {

								var outputVoltage = json[i].outputVoltage;

								values1[i] = outputVoltage.substring(0,
										outputVoltage.indexOf("V"));
								values2[i] = outputVoltage.substring(
										outputVoltage.indexOf("V/") + 2,
										outputVoltage.lastIndexOf("V/"));
								values3[i] = outputVoltage.substring(
										outputVoltage.lastIndexOf("V/") + 2,
										outputVoltage.length - 1);

							} else if (type == "upsLoad") {
								var upsLoad = json[i].upsLoad;

								values1[i] = upsLoad.substring(0, upsLoad
										.indexOf("%"));
								values2[i] = upsLoad.substring(upsLoad
										.indexOf("%/") + 2, upsLoad
										.lastIndexOf("%/"));
								values3[i] = upsLoad.substring(upsLoad
										.lastIndexOf("%/") + 2,
										upsLoad.length - 1);

							} else if (type == "batteryVoltage") {
								values1[i] = json[i].batteryVoltage;
							}
							categories[i] = formatDate(json[i].collectTime,
									'yyyy-MM-dd HH:mm:ss');
						}
						lastDate = result.lastDate;

					}
				});
				var option;

				if (name.length == 3) {
					option = {

						// backgroundColor : '#CCFFFF', //背景色
						calculable : true,// 是否启用拖拽重计算特性，默认关闭
						animation : true,
						title : {
							y : 20,
							x : 20,
							text : title
						},

						tooltip : {
							trigger : 'axis'
						// ,
						// formatter : function(params) {
						// return params[0].name + "<br/>"
						// + params[0].seriesName + ":"
						// + params[0].value + unit;
						// }
						},
						toolbox : {
							show : true,

							x : 'right', // 水平安放位置，默认为全图右对齐，可选为：'center' ¦
							// 'left'
							// ¦
							// 'right'¦ {number}（x坐标，单位px）
							y : 20, // 垂直安放位置，默认为全图顶端，可选为： 'top' ¦ 'bottom' ¦
							// 'center' ¦ {number}（y坐标，单位px）
							color : [ '#1e90ff', '#22bb22', '#4b0082',
									'#d2691e' ],
							backgroundColor : 'rgba(0,0,0,0)', // 工具箱背景颜色
							borderColor : '#ccc', // 工具箱边框颜色
							borderWidth : 0, // 工具箱边框线宽，单位px，默认为0（无边框）
							// padding : 5, // 工具箱内边距，单位px，默认各方向内边距为5，
							showTitle : true,
							show : true,
							feature : {
								mark : {
									show : true
								},
								dataView : {
									show : true,
									lang : [
											'<span style="margin-left:10px;">数据视图</span>',
											'关闭', '刷新' ],
									readOnly : false,
									optionToContent : function(opt) {
										var axisData = opt.xAxis[0].data;
										var series = opt.series;
										var html = "<center><table  id='dataView_table' style='width:98%;text-align:center;' cellspacing='0' cellpadding='5'>";
										html += "<thead id='dataView_thead'><tr><td>时间</td><td>"
												+ series[0].name
												+ "</td><td>"
												+ series[1].name
												+ "</td><td>"
												+ series[2].name
												+ "</td></tr></thead>";
										html += "<tbody>";
										for (var i = 0, l = axisData.length; i < l; i++) {
											html += '<tr>' + '<td>'
													+ axisData[i] + '</td>'
													+ '<td>'
													+ series[0].data[i] + unit
													+ '</td>' + '<td>'
													+ series[1].data[i] + unit
													+ '</td>' + '<td>'
													+ series[2].data[i] + unit
													+ '</td>' + '</tr>';
										}
										html += "</tbody>";
										html += "</table></center>";

										return html;
									}
								},
								magicType : {
									show : true,
									title : {
										line : '动态类型切换-折线图',
										bar : '动态类型切换-柱形图',
										stack : '动态类型切换-堆积',
										tiled : '动态类型切换-平铺'
									},
									type : [ 'line', 'bar' ]
								},
								restore : {
									show : true
								},
								saveAsImage : {
									show : true
								}
							}
						},
						calculable : true,
						dataZoom : {

							show : true,
							realtime : true,

							start : 0,
							end : 100
						},
						legend : {
							y : 30,
							data : name
						},
						grid : {

							y2 : 80

						},
						xAxis : [ {
							type : 'category',
							data : categories

						} ],
						yAxis : [ {
							type : 'value',
							axisLabel : {
								formatter : '{value} ' + unit
							}
						} ],
						series : [ {
							name : name[0],
							type : 'line',
							smooth : true,
							showAllSymbol : true,
							symbolSize : 0,
							data : values1,
							itemStyle : {
								normal : {
									color : 'red'
								}
							}

						}, {
							name : name[1],
							type : 'line',
							smooth : true,
							showAllSymbol : true,
							symbolSize : 0,
							data : values2,
							itemStyle : {
								normal : {
									color : 'yellow'
								}
							}

						}, {
							name : name[2],
							type : 'line',
							smooth : true,
							showAllSymbol : true,
							symbolSize : 0,
							data : values3,
							itemStyle : {
								normal : {
									color : 'blue'
								}
							}

						} ]
					};
				} else {
					option = {

						// backgroundColor : '#CCFFFF', //背景色
						calculable : true,// 是否启用拖拽重计算特性，默认关闭
						animation : true,
						title : {
							y : 20,
							x : 20,
							text : title
						},

						tooltip : {
							trigger : 'axis',
							formatter : function(params) {
								return params[0].name + "<br/>"
										+ params[0].seriesName + ":"
										+ params[0].value + unit;
							}
						},
						toolbox : {
							show : true,

							x : 'right', // 水平安放位置，默认为全图右对齐，可选为：'center' ¦
							// 'left'
							// ¦
							// 'right'¦ {number}（x坐标，单位px）
							y : 20, // 垂直安放位置，默认为全图顶端，可选为： 'top' ¦ 'bottom' ¦
							// 'center' ¦ {number}（y坐标，单位px）
							color : [ '#1e90ff', '#22bb22', '#4b0082',
									'#d2691e' ],
							backgroundColor : 'rgba(0,0,0,0)', // 工具箱背景颜色
							borderColor : '#ccc', // 工具箱边框颜色
							borderWidth : 0, // 工具箱边框线宽，单位px，默认为0（无边框）
							// padding : 5, // 工具箱内边距，单位px，默认各方向内边距为5，
							showTitle : true,
							show : true,
							feature : {
								mark : {
									show : true
								},
								dataView : {
									show : true,
									lang : [
											'<span style="margin-left:10px;">数据视图</span>',
											'关闭', '刷新' ],
									readOnly : false,
									optionToContent : function(opt) {
										var axisData = opt.xAxis[0].data;
										var series = opt.series;
										var html = "<center><table  id='dataView_table' style='width:98%;text-align:center;' cellspacing='0' cellpadding='5'>";
										html += "<thead id='dataView_thead'><tr><td>时间</td><td>"
												+ series[0].name
												+ "</td><td></tr></thead>";
										html += "<tbody>";
										for (var i = 0, l = axisData.length; i < l; i++) {
											html += '<tr>' + '<td>'
													+ axisData[i] + '</td>'
													+ '<td>'
													+ series[0].data[i] + unit
													+ '</td>' + '</tr>';
										}
										html += "</tbody>";
										html += "</table></center>";

										return html;
									}
								},
								magicType : {
									show : true,
									title : {
										line : '动态类型切换-折线图',
										bar : '动态类型切换-柱形图',
										stack : '动态类型切换-堆积',
										tiled : '动态类型切换-平铺'
									},
									type : [ 'line', 'bar' ]
								},
								restore : {
									show : true
								}
								,
//								excelImport:{
//									show:true,
//									title:"excel导出",
//									icon:ctx+"/static/images/export_excel.png",
//									onclick:function(){
//										exportExcel
//										window.location.href=ctx+"/system/excel/exportOutputVoltage/"+deviceId+"/"+$("#showTime").combobox("getValue");
//										
//									}
//								},
								
								saveAsImage : {
									show : true
								}
							}
						},
						calculable : true,
						dataZoom : {

							show : true,
							realtime : true,

							start : 0,
							end : 100
						},
						legend : {
							y : 30,
							data : name
						},
						grid : {

							y2 : 80

						},
						xAxis : [ {
							type : 'category',
							data : categories

						} ],
						yAxis : [ {
							type : 'value',
							axisLabel : {
								formatter : '{value} ' + unit
							}
						} ],
						series : [ {
							name : name[0],
							type : 'line',
							smooth : true,
							showAllSymbol : true,
							symbolSize : 0,
							data : values1,
							itemStyle : {
								normal : {
									color : 'red'
								}
							}

						} ]
					};
				}
				window.onresize = chart.resize;
				// clearTimeout(loadingTicket);
				// loadingTicket = setTimeout(function (){
				chart.hideLoading();
				chart.setOption(option);
				// },2200);

				collect();

			});

}
var interval;
function collect() {
	interval = setInterval(function() {
		var values;
		var values;
		var values;
		var upsType;
		$.ajaxSettings.async = false;

		// 加载数据
		$.ajax({
			url : ctx + '/chart/upsNewJson',
			data : {
				deviceId : deviceId,
				startDate : lastDate

			},
			method : "get",
			success : function(result) {
				var json = eval(result.data);
				if (json.length > 0) {
					upsType = json[0].upsType;
				}
				for (var i = 0; i < json.length; i++) {

					if (type == "inputVoltage") {
						var inputVoltage = json[i].inputVoltage;
						values1 = inputVoltage.substring(0, inputVoltage
								.indexOf("V"));
						values2 = inputVoltage.substring(inputVoltage
								.indexOf("V/") + 2, inputVoltage
								.lastIndexOf("V/"));
						values3 = inputVoltage
								.substring(inputVoltage.lastIndexOf("V/") + 2,
										inputVoltage.length - 1);

					} else if (type == "outputVoltage") {

						var outputVoltage = json[i].outputVoltage;

						values1 = outputVoltage.substring(0, outputVoltage
								.indexOf("V"));
						values2 = outputVoltage.substring(outputVoltage
								.indexOf("V/") + 2, outputVoltage
								.lastIndexOf("V/"));
						values3 = outputVoltage.substring(outputVoltage
								.lastIndexOf("V/") + 2,
								outputVoltage.length - 1);

					} else if (type == "upsLoad") {
						var upsLoad = json[i].upsLoad;

						values1 = upsLoad.substring(0, upsLoad.indexOf("%"));
						values2 = upsLoad.substring(upsLoad.indexOf("%/") + 2,
								upsLoad.lastIndexOf("%/"));
						values3 = upsLoad.substring(
								upsLoad.lastIndexOf("%/") + 2,
								upsLoad.length - 1);

					} else if (type == "batteryVoltage") {
						values1 = json[i].batteryVoltage;
					}

					var axisData = formatDate(json[i].collectTime,
							'yyyy-MM-dd HH:mm:ss');
					if (upsType == 2||upsType==3) {
						// 动态数据接口 addData
						chart.addData([

						[ 0, // 系列索引
						values1, // 新增数据
						false, // 新增数据是否从队列头部插入
						false, // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
						axisData // 坐标轴标签
						], [ 1, // 系列索引
						values2, // 新增数据
						false, // 新增数据是否从队列头部插入
						false, // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
						axisData // 坐标轴标签
						], [ 2, // 系列索引
						values3, // 新增数据
						false, // 新增数据是否从队列头部插入
						false, // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
						axisData // 坐标轴标签
						] ]);

					} else {
						chart.addData([

						[ 0, // 系列索引
						values1, // 新增数据
						false, // 新增数据是否从队列头部插入
						false, // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
						axisData // 坐标轴标签
						] ]);
					}

				}
				lastDate = result.lastDate

			}
		});

	}, 10000);
}
function closeInterval() {
	clearInterval(interval);
}
function exportExcel(){
window.location.href=ctx+"/system/excel/exportUpsData/"+deviceId+"/"+$("#showTime").combobox("getValue");
}