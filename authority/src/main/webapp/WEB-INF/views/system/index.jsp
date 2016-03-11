<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>

<title>IT综合运维管理平台</title>

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
function inDeviceSystem(url){
	window.location.target="_self";
	window.location.href="${ctx}/"+url;
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
							href="#" id="btnDeviceView" class="easyui-linkbutton" title="设备试图"
							data-options="plain:true"><img alt="设备试图"
							src="${ctx }/static/plugins/easyui/icons/icon-hamburg/32x32/collaboration.png"
							style="width: 24px; height: 24px; border: 0px;"></a>
					<a
							href="#" id="btnBackMain" class="easyui-linkbutton" title="返回主页"
							data-options="plain:true"><img alt="返回主页"
							src="${ctx }/static/plugins/easyui/icons/icon-hamburg/32x32/homeBack.png"
							style="width: 24px; height: 24px; border: 0px;"></a>
					<a href="#" id="btnUpdatePwd" class="easyui-linkbutton"
							title="修改密码" data-options="plain:true"><img alt="修改密码"
							src="${ctx }/static/plugins/easyui/icons/icon-hamburg/32x32/lock.png"
							style="width: 24px; height: 24px; border: 0px;"></a> 
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
					<!--    <span>更换皮肤：</span>
                    <select id="themeSelector"></select> -->
					<!--  <a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_set'" iconCls="icon-standard-cog">系统</a>  
                    <div id="layout_north_set">
						<div id="btnFullScreen" data-options="iconCls:'key'">全屏切换</div>
						<div id="btnExit" data-options="iconCls:'logout'">退出系统</div>
					</div>  -->
				
					<span id="DS"></span>					
					<a id="btnShowNorth" class="easyui-linkbutton"
						data-options="plain: true, iconCls: 'layout-button-down'"
						style="display: none;"></a>
				</div>
			</div>
		</div>

		<div
			data-options="region: 'west', title: '菜单导航栏', iconCls: 'icon-standard-map', split: true, minWidth: 200, maxWidth: 400"
			style="width: 220px; padding: 1px;">
			<div id="myMenu" class="easyui-accordion"
				data-options="fit:true,border:false">
				<script id="menu" type="text/html">
			{{each data as p_permission}}
				{{if (p_permission.pid==null)}}
   				 <div title="{{p_permission.name }}" style="padding: 5px;" data-options="border:false,iconCls:'{{p_permission.icon }}'">
					<div>
					{{each data as c_permission}}	
						{{if (c_permission.pid==p_permission.id)}}
						<a id="btn" class="easyui-linkbutton" data-options="plain:true,iconCls:'{{c_permission.icon }}'" style="width:98%;margin-bottom:5px;" onclick="window.mainpage.mainTabs.addModule('{{c_permission.name}}','{{c_permission.url }}','{{c_permission.icon }}')">{{c_permission.name}}</a>
						{{/if}}	
					{{/each}}
					</div>
				</div>
				{{/if}}	
			{{/each}}
			</script>
			</div>
		</div>

		<div data-options="region: 'center'">
			<div id="mainTabs_tools" class="tabs-tool">
				<table>
					<tr>
						<td><a id="mainTabs_jumpHome"
							class="easyui-linkbutton easyui-tooltip" title="跳转至主页选项卡"
							data-options="plain: true, iconCls: 'icon-hamburg-home'"></a></td>
						<td><div class="datagrid-btn-separator"></div></td>
						<td><a id="mainTabs_toggleAll"
							class="easyui-linkbutton easyui-tooltip" title="展开/折叠面板使选项卡最大化"
							data-options="plain: true, iconCls: 'icon-standard-arrow-out'"></a></td>
						<td><div class="datagrid-btn-separator"></div></td>
						<td><a id="mainTabs_refTab"
							class="easyui-linkbutton easyui-tooltip" title="刷新当前选中的选项卡"
							data-options="plain: true, iconCls: 'icon-standard-arrow-refresh'"></a></td>
						<td><div class="datagrid-btn-separator"></div></td>
						<td><a id="mainTabs_closeTab"
							class="easyui-linkbutton easyui-tooltip" title="关闭当前选中的选项卡"
							data-options="plain: true, iconCls: 'icon-standard-application-form-delete'"></a></td>
					</tr>
				</table>
			</div>
			<div id="mainTabs" class="easyui-tabs"
				data-options="fit: true, border: false, showOption: true, enableNewTabMenu: true, tools: '#mainTabs_tools', enableJumpTabMenu: true">
				<div id="homePanel" style="overflow: hidden;"
					data-options="title: '主页', iconCls: 'icon-hamburg-home',content:'<iframe src=\'${ctx }/system/home\' width=\'100%\' height=\'100%\' frameborder=0 scrolling=\'auto\' marginwidth=0 marginheight=0></iframe>'">
					
					
					
				</div>
			</div>
		</div>

		<!-- <div
			data-options="region: 'east', title: '日历', iconCls: 'icon-standard-date', split: true, minWidth: 160, maxWidth: 500"
			style="width: 220px;">
			<div id="eastLayout" class="easyui-layout" data-options="fit: true">
				<div data-options="region: 'north', split: false, border: false"
					style="height: 220px;">
					<div class="easyui-calendar"
						data-options="fit: true, border: false"></div>
				</div>
				<div id="linkPanel"
					data-options="region: 'center', border: false, title: '通知', iconCls: 'icon-hamburg-link', tools: [{ iconCls: 'icon-hamburg-refresh', handler: function () { window.link.reload(); } }]">

				</div>
			</div>
		</div> -->

		<div
			data-options="region: 'south', iconCls: 'icon-standard-information', collapsed: false, border: true"
			style="height: 23px; width: inherit; background-color: #E1EDFF;">
			<div
				style="color: #4e5766; margin: 0px auto; text-align: center; font-size: 12px; font-family: 微软雅黑;">
				<p style="color: #3399FF; margin: 2px 0;">
					${adminPath }版权所有©成都申控物联科技有限公司<span
						style="padding: 0 20px">|</span>Version:&nbsp;&nbsp;<span
						style="font-weight: 900;">SK_DHRS_NX0100</span>
				</p>
			</div>
		</div>

	</div>
<div id="dlg"></div>  
	<script>
	/* $.ajax({
		async : false,
		type : 'get',
		url : "${ctx}/system/permission/i/json",
		data:{
			code:"DS"
		},
		success : function(data) {
			var html="";
			for(var i=0;i<data.length;i++){
				html+="<a href='javascript:void(0)' onclick=\"inDeviceSystem('"+data[i].url+"')\" class='easyui-linkbutton' data-options=\"iconCls:'"+data[i].icon+"',plain: true\">"+data[i].name+"</a>";
			}
			
				
				$("#DS").html(html);
			
			
		}
	}); */

		$.ajax({
			async : false,
			type : 'get',
			url : "${ctx}/system/permission/i/json",
			data:{
				code:"DH"
			},
			success : function(data) {
				var menuData = {
					data : data
				};
				var html = template('menu', menuData);
				$('#myMenu').html(html);
			}
		});

		$('.easyui-linkbutton').on('click', function() {
			$('.easyui-linkbutton').linkbutton({
				selected : false
			});
			$(this).linkbutton({
				selected : true
			});
		});
	</script>

</body>
</html>
