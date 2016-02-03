<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设备试图查看</title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script type="text/javascript"
	src="${ctx }/static/plugins/artTemplate/dist/template.js"></script>
<!--导入首页启动时需要的相应资源文件(首页相应功能的 js 库、css样式以及渲染首页界面的 js 文件)-->

<script src="${ctx}/static/plugins/easyui/common/index.js"
	type="text/javascript"></script>
<link href="${ctx}/static/plugins/easyui/common/index.css"
	rel="stylesheet" />
<script src="${ctx}/static/plugins/easyui/common/index-startup.js"></script>

</head>
<body>
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
				<div class="top-bar-right" style="text-align: center; width: 290px;">
					<div id="themeSpan">
						<a id="btnHideNorth" class="easyui-linkbutton"
							data-options="plain: true, iconCls: 'layout-button-up'"></a>
					</div>
					<!-- <div id="buttonbar"	style="margin-top:24px;">
	                    <a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_set'" iconCls="icon-standard-cog">系统</a>  
	                    <div id="layout_north_set">
							<div id="btnFullScreen" data-options="iconCls:'key'">全屏切换</div>
							<div id="btnExit" data-options="iconCls:'logout'">退出系统</div>
						</div>
	                </div> -->
					<div id="buttonbar" style="margin-top: 24px;">
					<a
							href="#" id="btnBackMain" class="easyui-linkbutton" title="返回主页"
							data-options="plain:true"><img alt="返回主页"
							src="${ctx }/static/plugins/easyui/icons/icon-hamburg/32x32/homeBack.png"
							style="width: 24px; height: 24px; border: 0px;"></a>
					<a
							href="#" id="btnBackHome" class="easyui-linkbutton" title="返回后台"
							data-options="plain:true"><img alt="返回后台"
							src="${ctx }/static/plugins/easyui/icons/icon-woocons/32x32/system-terminal.png"
							style="width: 24px; height: 24px; border: 0px;"></a>
					
					<a href="#" id="btnUpdatePwd" class="easyui-linkbutton"
							title="修改密码" data-options="plain:true"><img alt="修改密码"
							src="${ctx }/static/plugins/easyui/icons/icon-hamburg/32x32/lock.png"
							style="width: 24px; height: 24px; border: 0px;"></a> 
						<a href="#" id="btnFullScreen" class="easyui-linkbutton"
							title="全屏切换" data-options="plain:true"><img alt="全屏切换"
							src="${ctx }/static/images/Full-Screen-32x32.png"
							style="width: 24px; height: 24px; border: 0px;"></a> <a
							href="#" id="deviceBtnExit" class="easyui-linkbutton" title="退出系统"
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
				
					<span id="DS"></span>					
					<a id="btnShowNorth" class="easyui-linkbutton"
						data-options="plain: true, iconCls: 'layout-button-down'"
						style="display: none;"></a>
				</div>
			</div>
		</div>
		<div region="center" data-options="content:'<iframe src=\'${ctx }/upsStatus/gitInfoView\' width=\'100%\' height=\'100%\' frameborder=0 scrolling=\'auto\' marginwidth=0 marginheight=0></iframe>'">
		
		</div>
		<div
			data-options="region: 'south', iconCls: 'icon-standard-information', collapsed: false, border: true"
			style="height: 23px; width: inherit; background-color: #E1EDFF;">
			<div
				style="color: #4e5766; margin: 0px auto; text-align: center; font-size: 12px; font-family: 微软雅黑;">
				<p style="color: #3399FF; margin: 2px 0;">
					版权所有©成都申控物联科技有限公司<span
						style="padding: 0 20px">|</span>Version:&nbsp;&nbsp;<span
						style="font-weight: 900;">SK_DHRS_NX0100</span>
				</p>
			</div>
		</div>
	</div>
<div id="dlg"></div>  
</body>
</html>