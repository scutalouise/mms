<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>主机监控管理</title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

<script type="text/javascript" src="${ctx }/static/js/validation.js"></script>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js"
	type="text/javascript"></script>


<script type="text/javascript" src="${ctx }/static/js/gitInfoList.js"></script>
<style type="text/css">
.formTable td {
	line-height: 30px;
}

.formTable {
	margin: 0px;
	padding: 10px;
}
.gitInfo_title{
text-align: right;
width: 100px;
}

#tooltiptitle {
	font-weight: bold;
	padding: 5px;
}
</style>
</head>
<body>
	<div class="easyui-layout" data-options="fit: true">
		<div
			data-options="region: 'west', title: '区域导航', iconCls: 'icon-standard-map', split: true, minWidth: 200, maxWidth: 400"
			style="width: 220px; padding: 1px;">
			<!-- 区域树 -->
			<ul id="areaInfoTree"></ul>

		</div>
		<div data-options="region: 'center'">
			<div id="tb" style="padding: 5px; height: auto">
				<form id="searchFrom" action="" method="post">
					<input type="text" name="filter_LIKES_name"
						class="easyui-validatebox" data-options="width:150,prompt: '名称'" />
					<input type="text" name="filter_LIKES_vendor"
						class="easyui-validatebox" data-options="width:150,prompt: '厂商'" />
						<input type="hidden" name="filter_EQI_status"
						value="0"/>
					<label>购买日期</label> <input type="text" name="filter_GED_buyTime"
						class="easyui-my97" id="beginDate" datefmt="yyyy-MM-dd"
						data-options="width:150,prompt: '起始日期'"/> - <input type="text"
						name="filter_LED_buyTime" class="easyui-my97" datefmt="yyyy-MM-dd"
						data-options="width:150,prompt: '结束日期'" id="endDate"/> <span
						class="toolbar-item dialog-tool-separator"></span> <a
						href="javascript(0)" class="easyui-linkbutton"
						iconCls="icon-search" plain="true" onclick="cx()">查询</a>
				</form>
				<div>
					<shiro:hasPermission name="sys:gitInfo:add">
						<a href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-add" plain="true" onclick="add();">添加</a>
						<span class="toolbar-item dialog-tool-separator"></span>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:gitInfo:delete">
						<a href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-remove" plain="true" data-options="disabled:false"
							onclick="del()">删除</a>
						<span class="toolbar-item dialog-tool-separator"></span>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:gitInfo:update">
						<a href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-edit" plain="true" onclick="update()">修改</a>
					</shiro:hasPermission>
				</div>


			</div>
			<div id="dg"></div>
			<div id="dlg"></div>
		</div>

	</div>

</body>
</html>
