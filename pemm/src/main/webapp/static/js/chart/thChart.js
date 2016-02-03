var lastDate;
var type = "temperature";
$(function() {
	var time = $("#showTime").combobox("getValue");
	initChart(deviceId, "temperature", time);
	$("#tab_th").tabs({
		onSelect : function(title) {
			if (title == "温度") {
				type = "temperature";
			} else if (title == "湿度") {
				type = "humidity";
			}
			time = $("#showTime").combobox("getValue");
			initChart(deviceId, type, time);
		}
	});
	// 选择查看日期
	$("#showTime").combobox({
		onChange : function(value) {
			initChart(deviceId, type, value);
		}
	});
	// 是否刷新
	$("#isRefresh").combobox({
		onChange : function(value) {
			if (value == "0") {
				// 实时刷新
				collect();
			} else {
				// 关闭刷新
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
			[ 'echarts', 'echarts/chart/bar', 'echarts/chart/line' ],
			function(ec) {
				var title = "温度曲线图";
				var name = [];
				var unit = "℃";
				var value = [];
				// 初始化图形
				chart = ec.init($("#main")[0]);
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
				var categories = [];
				$.ajaxSettings.async = false;
				// 加载数据
				$.ajax({
					url : ctx + "/chart/thJson",
					data : {
						deviceId : deviceId,
						time : time
					},
					success : function(result) {
						var json = eval(result.data);
						if (json.length > 0) {

						}
						if (type == "temperature") {
							title = "温度曲线图";
							name[0] = "温度";
							unit = "℃";
						} else if (type == "humidity") {
							title = "湿度曲线图";
							name[0] = "湿度";
							unit = "%";
						}
						for (var i = 0; i < json.length; i++) {
							if (type == "temperature") {
								value[i] = json[i].temperature;

							} else if (type == "humidity") {
								value[i] = json[i].humidity;
							}
							categories[i] = formatDate(json[i].collectTime,
									'yyyy-MM-dd HH:mm:ss');
						}
						lastDate = result.lastDate;
					}
				});
				var option = {
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

						x : 'right',// 水平安放位置，默认为全图右对齐，可选为：'center' ¦
						// 'left'
						// ¦
						// 'right'¦ {number}（x坐标，单位px）
						y : 20, // 垂直安放位置，默认为全图顶端，可选为： 'top' ¦ 'bottom' ¦
						// 'center' ¦ {number}（y坐标，单位px）
						color : [ '#1e90ff', '#22bb22', '#4b0082', '#d2691e' ],
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
										html += '<tr>' + '<td>' + axisData[i]
												+ '</td>' + '<td>'
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
						data : value,
						itemStyle : {
							normal : {
								color : 'red'
							}
						}

					} ]
				};
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
		$.ajaxSettings.async = false;
		// 加载数据
		$.ajax({
			url : ctx + "/chart/thNewJson",
			data : {
				deviceId : deviceId,
				startDate : lastDate
			},
			method : "get",
			success : function(result) {
				var json = eval(result.data);
				for (var i = 0; i < json.length; i++) {
					var lastData;
					if (type == "temperature") {
						lastData = json[i].temperature;

					} else if (type == "humidity") {
						lastData = json[i].humidity;
					}
					var axisData = formatDate(json[i].collectTime,
							'yyyy-MM-dd HH:mm:ss');
					// 动态数据接口 addData
					chart.addData([

					[ 0, // 系列索引
					lastData, // 新增数据
					false, // 新增数据是否从队列头部插入
					false, // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
					axisData // 坐标轴标签
					] ]);

				}
				lastDate = result.lastDate;
			}

		});
	}, 10000);

}
function closeInterval() {
	clearInterval(interval);
}

function exportExcel() {
	window.location.href = ctx + "/system/excel/exportThData/" + deviceId + "/"
			+ $("#showTime").combobox("getValue");
}