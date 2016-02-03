var organizationId;
$(function() {
	$("#beginDate").my97("setValue", getLastMonthYestdy(new Date()));
	$("#endDate").my97("setValue", new Date().format("yyyy-MM-dd"));
	
	initDateFilter("beginDate", "endDate");
	$("#organizationTree").tree(
			{
				method : "get",
				url : ctx + "/system/organization/tree",
				onBeforeExpand : function(node, params) {
					$(this).tree("options").url = ctx
							+ "/system/organization/tree?pid=" + node.id
				},
				onSelect : function(node) {
					organizationId = node.id;
					initChart(organizationId);
				}
			});
	initChart(organizationId);

});
function initChart(organizationId) {
	
	require.config({
		paths : {
			echarts : ctx + '/static/plugins/echarts/dist'
		}
	});	
	
	require([ 'echarts', 'echarts/chart/bar'// 柱状图
	], function(ec) {
	
		var chart = ec.init($("#main")[0]);// 初始化加载
		chart.showLoading({
			text : '正在努力加载中...'
		});

		// x坐标
		var categories = [];
		// 数据
		var values = [];
		$.ajax({
			async : false,
			url : ctx + "/chart/topNDataJson",
			data : {
				
				organizationId : organizationId,
				top:$("#top").val(),
				beginDate : $("#beginDate").my97("getValue"),
				endDate : $("#endDate").my97("getValue")
			},
			success : function(json) {
				
				for (var i = 0; i < json.length; i++) {
					values[i] = json[i].num;
					categories[i]=json[i].name+"【"+json[i].organizationName+"】";

				}
				chart.hideLoading();
			}
		});
		
		var option = {
			calculable : true,// 是否启用拖拽重计算特性，默认关闭
			animation : true,
			title : {
				y : 20,
				x : 20,
				text : "TOPN报警次数统计"
			},
			tooltip : {
				trigger : 'axis'
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
				type : 'bar',
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
function chart() {
	
	initChart(organizationId);

}