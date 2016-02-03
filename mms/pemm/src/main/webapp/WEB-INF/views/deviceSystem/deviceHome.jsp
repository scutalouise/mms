<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>动力环境监控功能系统-【设备状态监控子系统】</title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script type="text/javascript"
	src="${ctx }/static/plugins/artTemplate/dist/template.js"></script>
<!--导入首页启动时需要的相应资源文件(首页相应功能的 js 库、css样式以及渲染首页界面的 js 文件)-->
<script src="${ctx}/static/plugins/easyui/common/index.js"
	type="text/javascript"></script>
<link href="${ctx}/static/plugins/easyui/common/index.css"
	rel="stylesheet" />
<script src="${ctx}/static/plugins/easyui/common/index-startup.js"></script>
<style type="text/css">
a:HOVER {
	text-decoration: none;
}
a{
text-decoration: none;
color: black;
font-weight: 800;
}
</style>
<script type="text/javascript">
function goSystem(){
	window.location.target="_self";
	window.location.href="${ctx}/a";
}
</script>
</head>
<body>
<!-- 容器遮罩 -->
	<div id="maskContainer">
		<div class="datagrid-mask" style="display: block;"></div>
		<div class="datagrid-mask-msg"
			style="display: block; left: 50%; margin-left: -52.5px;">
			正在加载...</div>
	</div>
	<div id="mainLayout" class="easyui-layout hidden"
		data-options="fit: true">
		<div id="northPanel" data-options="region: 'north', border: false"
			style="height: 103px; overflow: hidden;">
			<div id="topbar" class="top-bar" style="height: 75px;">
				<div class="top-bar-left" style="height: 75px">
					<!-- <h1 style="margin-left: 10px; margin-top: 10px;color: #fff">Authority<span style="color: #3F4752">后台管理系统</span></h1> -->
					<a> <img alt="四川省农村信用社" src="${ctx }/static/images/logo.png"
						style="width: 400px; height: 75px"></a>
				</div>
				<div class="top-bar-right" style="text-align: center; width: 200px;">
					<div id="themeSpan">
						<a id="btnHideNorth" class="easyui-linkbutton"
							data-options="plain: true, iconCls: 'layout-button-up'"></a>
					</div>
					<div id="buttonbar" style="margin-top: 24px;">
						<a href="#" id="btnFullScreen" class="easyui-linkbutton"
							title="全屏切换" data-options="plain:true"><img alt="全屏切换"
							src="${ctx }/static/images/Full-Screen-32x32.png"
							style="width: 24px; height: 24px; border: 0px;"></a> <a
							href="#" id="btnExit" class="easyui-linkbutton" title="退出系统"
							data-options="plain:true"><img alt="退出系统"
							src="${ctx }/static/images/Exit-32x32.png"
							style="width: 24px; height: 24px; border: 0px;"></a>
					</div>
				</div>
			</div>
			<div id="toolbar"
				class="panel-header panel-header-noborder top-toolbar">
				<div id="infobar">
					<span class="icon-hamburg-user"
						style="padding-left: 25px; background-position: left center;">
						<shiro:principal property="name" />，您好
						
					</span>
					
					
				</div>

				<div id="buttonbar">
					
					<span style="line-height: 25px;margin-right: 5px;">
						<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-back',plain: true" onclick="goSystem()">返回主系统</a>
					</span>
					<a id="btnShowNorth" class="easyui-linkbutton"
						data-options="plain: true, iconCls: 'layout-button-down'"
						style="display: none;"></a>
				</div>
			</div>
		</div>

	

		<div data-options="region: 'center',fit:true,border:false"  style="overflow:hidden">
			<iframe src="${ctx }/upsStatus"   class="panel-iframe" frameborder="0" style="margin: 0px;"
					width="100%" height="100%"  scrolling="no"></iframe>
		</div>

		
		<div
			data-options="region: 'south', iconCls: 'icon-standard-information', collapsed: false, border: true"
			style="height: 23px; width: inherit; background-color: #E1EDFF;">
			<div
				style="color: #4e5766; margin: 0px auto; text-align: center; font-size: 12px; font-family: 微软雅黑;">
				<p style="color: #3399FF; margin: 2px 0;">
					版权所有©四川阿特申商贸有限公司<span style="padding: 0 20px">|</span>成都申控物联科技有限公司<span
						style="padding: 0 20px">|</span>Version:&nbsp;&nbsp;<span
						style="font-weight: 900;">SK_DHRS_NX0100</span>
				</p>
			</div>
		</div>

	</div>

	

</body>
</html>