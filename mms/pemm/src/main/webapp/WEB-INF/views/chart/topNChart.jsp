<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>topN排序报表</title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script type="text/javascript"
	src="${ctx }/static/plugins/echarts/dist/echarts.js"></script>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js"
	type="text/javascript"></script>
<script type="text/javascript"
	src="${ctx }/static/js/chart/topNChart.js"></script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div
			data-options="region:'west',title:'组织机构',iconCls:'icon-hamburg-world',split:true,minWidth:200,maxWidth:400,width:220">
			<ur id="organizationTree"></ur>
		</div>
		<div data-options="region:'center'">
			<div id="tb" style="padding: 5px; height: auto">
				<form id="searchFrom" action="" method="post">
					<label>开始时间：</label> <input type="text" name="beginDate"
						class="easyui-my97" id="beginDate" datefmt="yyyy-MM-dd"
						data-options="width:150,prompt: '起始日期'" />&nbsp;&nbsp; <label>结束时间：</label>
					<input type="text" name="endDate" class="easyui-my97"
						datefmt="yyyy-MM-dd" data-options="width:150,prompt: '结束日期'"
						id="endDate" />
						 <span class="toolbar-item dialog-tool-separator"></span>
						 <label>top:</label>
						 <input id="top" class="easyui-numberspinner" data-options="value:5" style="width: 80px;">
					<a href="javascript(0)" class="easyui-linkbutton"
						iconCls="icon-standard-chart-bar" plain="true" onclick="chart()">生成报表</a>
				</form>
			</div>

			<div id="main" style="height: 100%; padding-bottom: 20px;"
				class="easyui-panel" data-options="fit:true,border:false"></div>
		</div>
	</div>

</body>
</html>