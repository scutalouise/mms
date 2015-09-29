var areaInfoId;

$(function() {
	$("#beginDate").my97("setValue", getLastMonthYestdy(new Date()));
	$("#endDate").my97("setValue", new Date().format("yyyy-MM-dd"));
	
	initDateFilter("beginDate", "endDate");
	
	$("#areaInfoTree").tree(
			{
				method : "get",
				url : ctx + "/system/area/tree",
				onBeforeExpand : function(node, params) {
					$(this).tree("options").url = ctx
							+ "/system/area/tree?pid=" + node.id
				},
				onSelect : function(node) {
					areaInfoId = node.id;
					initChart(areaInfoId);
				}
			});
	initChart(areaInfoId);

	// 路径配置

});
function initChart(areaInfoId) {

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
				var chart = ec.init($("#main")[0]);

				chart.showLoading({
					text : '正在努力加载中...'
				});
				var categories = [];
				var values = [];

				$.ajaxSettings.async = false;
				// 加载数据
				$.ajax({
					url : ctx + '/chart/json',
					data : {
						areaInfoId : areaInfoId,
						beginDate : $("#beginDate").my97("getValue"),
						endDate : $("#endDate").my97("getValue")
					},
					success : function(json) {
						for (var i = 0; i < json.length; i++) {
							values[i] = json[i].num;
							categories[i] = json[i].collectTime;
						}
						chart.hideLoading();
					}
				});

				var option = {

					// backgroundColor : '#CCFFFF', //背景色
					calculable : true,// 是否启用拖拽重计算特性，默认关闭
					animation : true,
					title : {
						y : 20,
						x : 20,
						text : "设备告警次数统计"
					},

					tooltip : {
						trigger : 'axis'
					},
					toolbox : {
						show : true,

						x : 'right', // 水平安放位置，默认为全图右对齐，可选为：'center' ¦ 'left'
										// ¦
						// 'right'¦ {number}（x坐标，单位px）
						y : 20, // 垂直安放位置，默认为全图顶端，可选为： 'top' ¦ 'bottom' ¦
						// 'center' ¦ {number}（y坐标，单位px）
						color : [ '#1e90ff', '#22bb22', '#4b0082', '#d2691e' ],
						backgroundColor : 'rgba(0,0,0,0)', // 工具箱背景颜色
						borderColor : '#ccc', // 工具箱边框颜色
						borderWidth : 0, // 工具箱边框线宽，单位px，默认为0（无边框）
					//	padding : 5, // 工具箱内边距，单位px，默认各方向内边距为5，
						showTitle : true,
						show : true,
						feature : {
							mark : {
								show : true
							},
							dataView : {
								show : true,
							    lang : ['<span style="margin-left:10px;">数据视图</span>', '关闭', '刷新'],
								readOnly : false,
								optionToContent : function(opt) {
									var axisData = opt.xAxis[0].data;
									var series = opt.series;
									var html = "<center><table  id='dataView_table' style='width:98%;text-align:center' cellspacing='0' cellpadding='5'>";
									html += "<thead id='dataView_thead'><tr><td>时间</td><td>"
											+ series[0].name
											+ "</td></tr></thead>";
									html += "<tbody>";
									for (var i = 0, l = axisData.length; i < l; i++) {
										html += '<tr>' + '<td>' + axisData[i]
												+ '</td>' + '<td>'
												+ series[0].data[i] + '</td>'

												+ '</tr>';
									}
									html += "</tbody>";
									html += "</table></center>";
									return html;
								}
							},
							magicType: {
				                show : true,
				                title : {
				                    line : '动态类型切换-折线图',
				                    bar : '动态类型切换-柱形图',
				                    stack : '动态类型切换-堆积',
				                    tiled : '动态类型切换-平铺'
				                },
				                type : ['line', 'bar']
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
						data : [ '异常次数' ]
					},
					grid : {

						y2 : 80

					},
					xAxis : [ {
						type : 'category',
						data : categories

					} ],
					yAxis : [ {
						type : 'value'
					} ],
					series : [ {
						name : '异常次数',
						type : 'line',
						smooth :true,
						showAllSymbol : true,
						symbolSize : 0,
						data : values,
						itemStyle:{
			                  normal:{color:'red'}
			              }

					} ]
				};

				// 为echarts对象加载数据
				chart.setOption(option);

			});

}

function chart() {
	initChart(areaInfoId);

}