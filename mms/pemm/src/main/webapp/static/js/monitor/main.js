var deviceStatuschart;
var alarmChart;
var checkStateEnum="";
function loadBranchView(stateEnum){
	$.get(ctx + "/system/monitor/branchView",{stateEnum:stateEnum}, function(data) {
		$("#content").html(data);
	});
}
$(function() {
	$("#input_center_input").click(function() {
		loadBranchView();

	});
	$("#branches_button_good").click(function(){
		loadBranchView("good");
		checkStateEnum="good";
		
	});
	$("#branches_button_warning").click(function(){
		loadBranchView("warning");
		checkStateEnum="warning";
		
	});
	$("#branches_button_error").click(function(){
		loadBranchView("error");
		checkStateEnum="error";
		
	});
	
	changeHeight();
	deviceStatusChart();
	alarmNumChart("day_main", ctx + '/system/home/yearLineChart?type=day');
	$('#alarm_tabs a:first').tab('show');// 初始化显示哪个tab
	$('#alarm_tabs a').click(
			function(e) {
				e.preventDefault();// 阻止a链接的跳转行为

				$(this).tab('show');// 显示当前选中的链接及关联的content
				var title = $(this)[0].text;

				if (title == "年") {

					alarmNumChart("year_main", ctx
							+ "/system/home/yearLineChart?type=year");

				} else if (title == "月") {

					alarmNumChart("month_main", ctx
							+ "/system/home/yearLineChart?type=month");

				} else if (title == "日") {
					alarmNumChart("day_main", ctx
							+ "/system/home/yearLineChart?type=day");

				}
			});
	$("#topCarousel").carousel(0);

	$(window).resize(function() {
		changeHeight();
		deviceStatuschart.resize();
		alarmChart.resize();
	});
});
function changeHeight() {

	$("#content").css({
		position : "absolute",
		top : 90,
		left : 0
	});

	$("#content").height($(window).height() - 130);
	var height = $("#content").height() - $("#center_status").height() + 40;
	$("#top_content").height(height);
	$("#top_img").height(height);

	var top_title_bottom = $(window).height() - $("#top_logo").height()
			- $("#top_title").height();
	$("#top_title").css({
		bottom : top_title_bottom
	});
	$("#top_logo").css({
		bottom : top_title_bottom + 30
	});

	$("#center_status").css({
		top : 0
	});

	$("#carousel_content").height($("#top_img").height());
	$("#topCarousel").height($("#top_img").height());
	$("#carousel_one").height($("#carousel_content").height() - 60);
	$(".carousel_content_div").each(function() {
		$(this).height($("#carousel_content").height() - 60);
	});
	$("#carousel_content").width($(window).width() - (70 * 2));

	$("#carousel_control_left,#carousel_control_right").css({
		"margin-top" : $("#top_img").height() / 2 - 30
	})

	$('#loginConfirm').modal("hide").css({
		"margin-top" : ($(window).height()) / 2 - 170,

	});
	// 报警日统计
	$("#alarm_content_div > div").height(
			$("#carousel_content").height() - 60 - 26);
	$("#map").height($("#carousel_content").height() - 60);
}
function deviceStatusChart() {

	// 路径配置
	require.config({
		paths : {
			echarts : ctx + '/static/plugins/echarts/dist'
		}
	});
	require([ 'echarts', 'echarts/chart/pie', 'echarts/chart/funnel',
			'echarts/chart/line'// 加载曲线图模块
	], function(ec) {

		deviceStatuschart = ec.init($("#status_main")[0]);

		deviceStatuschart.showLoading({
			text : '正在努力加载中...'
		});
		var option = {
			title : {
				text : '设备状态统计',
				x : 'center'
			},
			tooltip : {
				trigger : 'item',
				formatter : "{a} <br/>{b} : {c} ({d}%)"
			},
			legend : {
				orient : 'vertical',
				x : 'left',
				data : [ '正常数量', '警告数量', '异常数量' ]

			},
			toolbox : {
				show : true,
				feature : {

					dataView : {
						show : true,
						readOnly : false
					},
					magicType : {
						show : true,
						type : [ 'pie', 'funnel' ],
						option : {
							funnel : {
								x : '25%',
								width : '50%',
								funnelAlign : 'left',
								max : 1548
							}
						}
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
			series : [ {
				name : '设备统计',
				type : 'pie',
				radius : '55%',
				center : [ '50%', '60%' ],
				data : [ {
					value : good,
					name : '正常数量',
					itemStyle : {
						normal : {
							color : 'green'
						}
					}

				}, {
					value : warning,
					name : '警告数量',
					itemStyle : {
						normal : {
							color : 'yellow'
						}
					}
				}, {
					value : error,
					name : '异常数量',
					itemStyle : {
						normal : {
							color : 'red'
						}
					}
				}

				]
			} ]
		};

		deviceStatuschart.hideLoading();
		deviceStatuschart.setOption(option);

	});

}
function alarmNumChart(main, url) {

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

				alarmChart = ec.init($("#" + main)[0]);
				alarmChart.showLoading({
					text : '正在努力加载中...'
				});
				var categories = [];
				var values = [];

				$.ajaxSettings.async = false;
				// 加载数据
				$.ajax({
					url : url,

					success : function(json) {

						for (var i = 0; i < json.length; i++) {
							values[i] = json[i].num;
							categories[i] = json[i].collectTime;
						}
						alarmChart.hideLoading();
					}
				});

				var option = {

					// backgroundColor : '#CCFFFF', //背景色
					calculable : true,// 是否启用拖拽重计算特性，默认关闭
					animation : true,
					title : {
						// y : 20,
						// x : 20,
						x : 'center',
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
					legend : {
						y : 30,
						data : [ '异常次数' ]
					},
					grid : {

						y2 : 60

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
						smooth : true,
						showAllSymbol : true,
						symbolSize : 0,
						data : values,
						itemStyle : {
							normal : {
								color : 'red'
							}
						}

					} ]
				};

				// 为echarts对象加载数据
				alarmChart.setOption(option);

			});
}