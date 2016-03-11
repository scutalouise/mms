<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>topN排序报表</title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script type="text/javascript" src="${ctx }/static/plugins/echarts/dist/echarts.js"></script>
<script type="text/javascript" src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<!-- <div
			data-options="region:'west',title:'组织机构',iconCls:'icon-hamburg-world',split:true,minWidth:200,maxWidth:400,width:250">
			<ur id="organizationTree"></ur>
		</div> -->
		<div data-options="region:'center'">
			<div id="tb" style="padding: 5px; height: auto">
				<form id="searchFrom" action="" method="post">
				    <label>设备品牌:</label>
				    <input type="text" name="brandId" class="easyui-combobox" id="brandId" data-options="width:150"/>&nbsp;&nbsp; 
				    <label>设备类型:</label>
				    <input type="text" name="deviceType" class="easyui-combobox" id="deviceType" data-options="width:150"/>&nbsp;&nbsp; 
					<label>服务时常:</label>
					<select name="sort" id="sort" class="easyui-combobox" data-options="width: 150">
						<option value="desc">长</option>
						<option value="asc">短</option> 
				    </select>&nbsp;&nbsp; 
				    <label>起始日期:</label>
					<input type="text" name="beginDate" class="easyui-my97" id="beginDate" datefmt="yyyy-MM-dd"
						data-options="width:150" />&nbsp;&nbsp; 
					<label>截止日期:</label>
					<input type="text" name="endDate" class="easyui-my97" id="endDate" datefmt="yyyy-MM-dd"
						data-options="width:150" />&nbsp;&nbsp; 
					<label>TopN:</label>
				    <select name="top" id="top" class="easyui-combobox" data-options="width: 150">
						<option value="5">5</option>
						<option value="10">10</option> 
						<option value="15">15</option>
				    </select>
					<a href="javascript(0)" class="easyui-linkbutton"
						iconCls="icon-standard-chart-bar" plain="true" onclick="chart()">生成图形报表</a>
				</form>
			</div>

			<div id="main" style="height: 100%; padding-bottom: 20px;"
				class="easyui-panel" data-options="fit:true,border:false"></div>
	   </div>
	</div>
 <script type="text/javascript">
  
 $(function(){
	 
	 $.ajax({
		    url:'${ctx}/device/brand/firstDeviceType',
			type : "get",
			dataType : "json",
			success : function(data) {
				var json = {"firstDeviceType":"","name":"-- 请选择 --"};
				data.unshift(json);
			    $('#deviceType').combobox({
				   editable:false,
				   valueField:'firstDeviceType',
				   textField:'name',
				   data:data,
				   onLoadSuccess:function(data){
				      $('#deviceType').combobox("setValue",data[0].firstDeviceType);
				   },
		       });
			}
	  });
	
	 $.ajax({
			url:'${ctx}/device/brand/all',
			type : "get",
			dataType : "json",
			success : function(data) {
				var json = {"id":"","name":"-- 请选择 --"};
				data.unshift(json);
			    $('#brandId').combobox({
				   editable:false,
				   valueField:'id',
				   textField:'name',
				   data:data,
				   onLoadSuccess:function(data){
				      $('#brandId').combobox("setValue",data[0].id);
				   },
		       });
			}
	  });
	  
	  initChart();
	  initDateFilter("beginDate","endDate");
 })
 
 function initChart(){
	 require.config({
			paths : {
				echarts : '${ctx}/static/plugins/echarts/dist'
			}
		});	
		
		require([ 'echarts', 'echarts/chart/bar'], // 柱状图
		
	    function(ec) {
			
	        var myChart = ec.init($('#main')[0]);
			//myChart.showLoading({text : '正在努力加载中...'});
			
			  // x坐标
	    var categories = [];
		// 数据
		var values = [];
	  
	    $.ajax({
			async : false,
			type: 'post',
			url : "${ctx}/device/chart/maintenanceTopNDataJson",
			data : {
				deviceType: $("#deviceType").combobox('getValue'),
				brandId: $("#brandId").combobox('getValue'),
				sort: $("#sort").combobox('getValue'),
				top: $("#top").combobox('getValue'),
				beginDate : $("#beginDate").my97("getValue"),
				endDate : $("#endDate").my97("getValue")
			},
			success : function(json) {
				for (var i = 0; i < json.length; i++) {
					values[i] = json[i].time;
					categories[i]=json[i].maintenanceName+"【"+json[i].brandName+"】"+"【"+json[i].deviceType+"】";
				}
				myChart.hideLoading();
			  }
		   });
	
	        var option = {
				calculable : true,// 是否启用拖拽重计算特性，默认关闭
				animation : true,
				title : {
					y : 20,
					x : 20,
					text : "设备对应运维商解决时长"
				},
				tooltip : {
					trigger : 'axis'
				},
				legend : {
					y : 30,
					data : [ '解决时长（小时）' ]
				},
				grid : {
					y2 : 80
				},
				xAxis : [ {
					type : 'category',
					data : categories
	
				}],
				yAxis : [ {
					type : 'value'
				}],
				series : [ {
					name : '解决时长（小时）',
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
	        
	        myChart.setOption(option);
	  })
 }
 
 
 function chart() {
	 initChart();
 }
 
    </script>
</body>
</html>