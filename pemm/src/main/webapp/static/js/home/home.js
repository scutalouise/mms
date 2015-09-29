$(function() {
	var panelWidth = $(document).width() / 2;

	$("#home_panel")
			.layout(
					"add",
					{
						region : 'west',
						split : true,
						border : true,

						width : panelWidth,
						content : "<div  id='status_main' style='height: 100%;'></div>"
					});
	$("#home_panel")
			.layout(
					"add",
					{
						region : 'center',
						split : true,
						border : true,

						width : panelWidth,
						content : '<div class="easyui-tabs" id="tab_time" data-options="fit:true">'
								+ '<div data-options="title:\'年\',refreshable:false" iconCls="">'
								+ '<div id="year_main" style="height:100%;"></div></div>'
								+ '<div data-options="title:\'月\',refreshable:false" iconCls=""><div id="month_main" style="height:100%;"></div></div>'
								+ '<div data-options="title:\'日\',refreshable:false" iconCls=""><div id="day_main" style="height:100%;"></div></div>'
								+ '</div>'
					});

	
	initLineChart("year_main", ctx + '/system/home/yearLineChart?type=year');
	initChart();
	$("#tab_time").tabs(
			{
				onSelect : function(title) {
					if (title == "年") {
						initLineChart("year_main", ctx
								+ '/system/home/yearLineChart?type=year');
					} else if (title == "月") {
						initLineChart("month_main", ctx
								+ '/system/home/yearLineChart?type=month');
					} else {
						initLineChart("day_main", ctx
								+ '/system/home/yearLineChart?type=day');
					}
				}
			});

	var dg = $("#dg").datagrid({

		title : "设备异常记录",
		method : "post",
		url : ctx + "/system/alarmLog/json",
		
		fit : true,
		fitColumns : true,
		border : false,
		idField : "id",
		striped : true,
		pagination : false, 
		rownumbers : true,
		pageNumber : 1,
		pageSize : 10, 
		columns : [ [ {
			field : "id",
			checkbox : true,
			hidden : true
		}, {
			field : "deviceTypeIndex",
			title : "设备类型",
			width : 50,
			sortable : true,formatter:function(v){
				if(v==0){
					return "UPS";
				}
			}
		}, {
			field : "deviceName",
			title : "设备名称",
			width:50,
			sortable:true
		}, {
			field : "gitInfoIp",
			title : "所属IP",
			width:50,
			sortable:true
		}, {
			field : "collectTime",
			title : "异常时间",
			width:50,
			sortable:true,formatter:function(v){
				if(v!=null){
					return formatDate(v,"yyyy-MM-dd HH:mm:ss")
				}
			}
		}, {
			field : "content",
			title : "异常内容",
			width:80,
			sortable:true
		}  ] ],
		headerContextMenu : [ {
			text : "冻结该列", 
			disabled : function(e, field) {
				return dg.datagrid("getColumnFields", true).contains(field);
			},
			handler : function(e, field) {
				dg.datagrid("freezeColumn", field);
			}
		}, {
			text : "取消冻结该列",
			disabled : function(e, field) {
				return dg.datagrid("getColumnFields", false).contains(field);
			},
			handler : function(e, field) {
				dg.datagrid("unfreezeColumn", field);
			}
		} ],
		enableHeaderClickMenu : true,
		enableHeaderContextMenu : true,
		enableRowContextMenu : false
		
	});

});

function initLineChart(main, url) {

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

				var chart = ec.init($("#" + main)[0]);
				chart.showLoading({
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
				chart.setOption(option);

			});
}

function initChart() {

	// 路径配置
	require.config({
		paths : {
			echarts : ctx + '/static/plugins/echarts/dist'
		}
	});

	require([ 'echarts', 'echarts/chart/pie', 'echarts/chart/funnel',
			'echarts/chart/line'// 加载曲线图模块
	], function(ec) {

		var chart = ec.init($("#status_main")[0]);
		chart.showLoading({
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

		chart.hideLoading();
		chart.setOption(option);
	});
}