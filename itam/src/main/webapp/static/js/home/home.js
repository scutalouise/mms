var dg
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
	$("#home_panel").layout("add",{
		region : 'center',
		split : true,
		border : true,
		width : panelWidth,
		content : '<div class="easyui-tabs" id="tab_time" data-options="fit:true">'
				+ '<div data-options="title:\'年\',refreshable:false" iconCls=""><div id="year_main" style="height:100%;"></div></div>'
				+ '<div data-options="title:\'月\',refreshable:false" iconCls=""><div id="month_main" style="height:100%;"></div></div>'
				+ '<div data-options="title:\'日\',refreshable:false" iconCls=""><div id="day_main" style="height:100%;"></div></div>'
				+ '</div>'
	});

	
	initLineChart("year_main", ctx + '/system/home/yearLineChart?type=year');
	initChart();
	$("#tab_time").tabs({
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
	
	dg=$('#dg').datagrid({
		  title : "待处理的故障报修",
		  method:"get",
		  url:'/itam/maintenance/handling/problem',
		  fit:true,
		  fitColumns:true,
	      animate:true,
	      striped:true,
	  	  pagination:true,
		  rownumbers:true,
		  pageNumber:1,
		  pageSize : 10,
	      singleSelect:true,
	      columns:[[
	          {
	        	  field:'id',
	        	  title:'id',
	        	  hidden:true
	        }, {
	        	field:'problemType',
	        	title:'问题类型',
	        	sortable:true,
	        	width:100
	        }, {
	        	field:'problemCode',
	        	title:'问题编号',
	        	sortable:true,
	        	width:100
	        }, {
	        	field:'identifier',
	        	title:'设备编号',
	        	sortable:true,
	        	width:100
	        }, {
	        	field:'recordUserName',
	        	title:'登记人',
	        	sortable:true,
	        	width:100
	        }, {
	        	field:'recordTime',
	        	title:'登记时间',
	        	sortable:true,
	        	formatter : function(value, row, index) {
	        		return formatDate(value,"yyyy-MM-dd HH:mm:ss");
	        	}
	        }, {
	        	field:"reportWay",
	        	title:"上报渠道",
	        	width:100,
	        	formatter : function(value, row, index) {
	        		return value.name;
	        	}
	        }, {
	        	field:'enable',
	        	title:'问题状态',
	        	sortable:true,
	        	width:100,
	        	formatter : function(value, row, index) {
	        		return value.name;
	        	}
	        }, {
	        	field:'resolveUserName',
	        	title:'解决人',
	        	sortable:true,
	        	width:100
	        }
	      ]],
	      enableHeaderClickMenu : true,
			enableHeaderContextMenu : true,
			enableRowContextMenu : false,
			onLoadSuccess:function(){
				createTooltip();
			},
			rowStyler:function(index,row){
				if(row.runState==1){
					return "background:#fcf8e3";
				}else{
					return "background:#f2dede";
				}
			}
	  });

});
function formatter(value, row, index) {
	var v="";
	if(value==0){
		v="UPS设备";
	}else if(value==1){
		v="温湿度传感器"
	}else if(value==2){
		v="水浸设备";
	}else if(value==3){
		v="烟雾传感器";
	}
	return '<a data-p='
			+ index
			+ ' class="easyui-tooltip" style="z-index:10000" href="javascript:void(0)">'+v+'</a>';
}
//悬浮效果
function createTooltip(){
	dg.datagrid("getPanel").find(".easyui-tooltip").each(function(){
		var index = parseInt($(this).attr('data-p'));
		$(this)
				.tooltip({
					content : $('<div></div>'),
					onUpdate : function(cc) {
						var row = $('#dg').datagrid(
						'getRows')[index];
						var deviceType="";
						switch(row.deviceTypeIndex){
						case 0:
							deviceType="UPS";
							break;
						}
						var content="<div id='tooltiptitle'>异常信息</div><ul id='tooltipul'>";
						content+="<li>设备类型:"+deviceType+"</li>";
						content+="<li>所属ip:"+row.gitInfoIp+"</li>";
						content+="<li>设备名称:"+row.deviceName+"</li>";
						content+="<li>异常时间:"+formatDate(row.collectTime,"yyyy-MM-dd HH:mm:ss")+"</li>";
						content+="<li style='width:700px'>异常信息:"+row.content+"</li>";
						content+"</ul>";
						cc.panel({
							width : 730,
							content : content
						});
					},
					position : 'right'
				})
	});
}
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