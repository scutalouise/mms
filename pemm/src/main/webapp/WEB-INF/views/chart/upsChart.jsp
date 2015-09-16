<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>UPS统计报表</title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script type="text/javascript"
	src="${ctx }/static/plugins/echarts/dist/echarts.js"></script>
<script type="text/javascript" src="${ctx }/static/js/chart/upsChart.js"></script>

</head>
<body>

	<div id="tab-tools">
		<a href="javascript:void(0)" class="easyui-linkbutton" style=""
			data-options="plain:true,iconCls:'icon-back'"
			onclick="history.go(-1)">返回</a>
	</div>

	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',border:false" style="height: 32px">
			<div class="easyui-tabs"
				data-options="fit:true,tools:'#tab-tools',border:false">
				<c:forEach items="${deviceList }" var="deviceList">
					<div title="【UPS-${deviceList.deviceIndex }】" iconCls=""
						data-options='refreshable:false'></div>
				</c:forEach>
			</div>
		</div>
		<div data-options="region:'center',border:false">
			<div class="easyui-layout" data-options="fit:true,border:false">
				<div data-options="region:'west',border:false" style="width: 83px">
					<div class="easyui-tabs"
						data-options="tabPosition:'left',fit:true,headerWidth:80">
						<div title="输入电压" iconCls="" data-options='refreshable:false'>
							<div></div>
						</div>
						<div title="输出电压" iconCls="" data-options='refreshable:false'>


						</div>
						<div title="输出频率" iconCls="" data-options='refreshable:false'></div>
						<div title="负载容量" iconCls="" data-options='refreshable:false'></div>
						<div title="电池电压" iconCls="" data-options='refreshable:false'></div>
					</div>
				</div>
			</div>

		</div>
	</div>
</body>
</html>