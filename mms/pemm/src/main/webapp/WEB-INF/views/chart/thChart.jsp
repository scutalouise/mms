<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>温湿度统计报表</title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script type="text/javascript"
	src="${ctx }/static/plugins/echarts/dist/echarts.js"></script>
<script type="text/javascript">
	var deviceId = "${deviceId}";
</script>
<script type="text/javascript" src="${ctx }/static/js/chart/thChart.js"></script>
<style type="text/css">
#dataView_thead tr {
	background-color: #A3A3A3;
}

#dataView_table {
	margin-left: -6px;
}

html, body {
	height: 100%;
	margin: 0px;
}
</style>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'west',border:false" style="width: 83px">
			<!-- 选项卡选项(左边) -->
			<div class="easyui-tabs"
				data-options="tabPosition:'left',fit:true,headerWidth:80"
				id="tab_th">
				<div title="温度" iconCls="" data-options='refreshable:false'></div>
				<div title="湿度" iconCls="" data-options='refreshable:false'></div>
			</div>
		</div>
		<div data-options="region:'center',border:false">
			<div class="easyui-layout" data-options="fit:true">
				<div data-options="region:'north',border:false">
					<div id="tb" style="padding: 5px; height: auto">

						<label>显示时间：</label> <select class="easyui-combobox" id="showTime"
							style="width: 150px;" data-options="panelHeight:150">
							<option value="3">3小时</option>
							<option value="10">10小时</option>
							<option value="24">24小时</option>
							<option value="168">7天</option>
							<option value="240">10天</option>
							<option value="720">30天</option>
						</select> <span class="toolbar-item dialog-tool-separator"></span> <label>是否实时刷新：</label>
						<select class="easyui-combobox" id="isRefresh"
							style="width: 150px;" data-options="panelHeight:50">
							<option value="0">是</option>
							<option value="1">否</option>
						</select>
							<span class="toolbar-item dialog-tool-separator"></span>
						 <a href="javascript(0)" class="easyui-linkbutton"
										iconCls="icon-export-excel" plain="true"
										onclick="exportExcel()">导出温湿度数据</a>
						

					</div>
				</div>
				<div data-options="region:'center',border:false">
					<div id="main" style="padding-bottom: 20px;" class="easyui-panel"
						data-options="fit:true,border:false">
						
						</div>
				</div>

			</div>
		</div>
	</div>

</body>
</html>