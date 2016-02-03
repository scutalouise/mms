﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
<title>动态环境数据采集监控平台</title>
<link rel="shortcut icon" href="${ctx}/static/images/favicon.ico">
<link rel="stylesheet"
	href="${ctx }/static/monitorPlatform/css/nivo-slider.css"
	type="text/css" media="screen" />
<link rel="stylesheet"
	href="${ctx }/static/monitorPlatform/css/main.css" type="text/css"
	media="screen" />

<script type="text/javascript"
	src="${ctx }/static/monitorPlatform/js/jquery.js"></script>
<link href="${ctx }/static/monitorPlatform/css/metro-list.css"
	rel="stylesheet">
<script type="text/javascript"
	src="${ctx }/static/plugins/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="${ctx }/static/plugins/bootstrap/css/bootstrap.min.css">
<script type="text/javascript" src="${ctx }/static/monitorPlatform/js/dateUtils.js">
</script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=dokoj8FFEtkK3f9wO93Hha6p"></script>
<script type="text/javascript">
	var ctx = "${ctx}";
 
	/* function exit() {
		location.href = '${ctx}/m/logout';
	} */
	function exit() {
        if (confirm('您确定要退出?')) { 
        	location.href = '${ctx}/m/logout';
        }
    }
	function monitormain() {
		location.href = "${ctx}/monitorPlatform/index";
	}
	function system() {
		location.href = "${ctx}/a"
	}
	
	function isPC() {
		    var userAgentInfo = navigator.userAgent;
		    var Agents = ["Android", "iPhone",
		                "SymbianOS", "Windows Phone",
		                "iPad", "iPod"];
		    var flag = true;
		    for (var v = 0; v < Agents.length; v++) {
		        if (userAgentInfo.indexOf(Agents[v]) > 0) {
		            flag = false;
		            break;
		        }
		    }
		    return flag;
		}
	function windowRes(){
		var left=($(".items").width()-$("#items_div").width())/2;
		$(".items").css("padding-left",left);
		
		
		
	}
	function refreshAlarmLog(){
		$.ajax({
			type : "post",
			data : {
				top : 5

			},
			url : "${ctx}/monitorPlatform/topAlarmLog",
			success : function(data) {
				var tbodyHtml = "";
				if (data.length == 0) {
					tbodyHtml = "<tr><td colspan='5' style='text-align:center'>无数据</td></tr>"
				}
				for (var i = 0; i < data.length; i++) {
					var alarmLog = data[i];
					var deviceTypeValue = "";
					var deviceTypeIndex = alarmLog.deviceTypeIndex;

					if (deviceTypeIndex == 0) {
						deviceTypeValue = "UPS设备";
					} else if (deviceTypeIndex == 1) {
						deviceTypeValue = "温湿度传感器"
					} else if (deviceTypeIndex == 2) {
						deviceTypeValue = "水浸设备";
					} else if (deviceTypeIndex == 3) {
						deviceTypeValue = "烟雾传感器";
					}
					
					if (alarmLog.runState == 1) {
						tbodyHtml += "<tr class='warning'>";
					} else {
						tbodyHtml += "<tr class='danger'>";
					}
					tbodyHtml += "<td>" + deviceTypeValue +"</td>";
					tbodyHtml += "<td>" + alarmLog.deviceName + "</td>";
					tbodyHtml += "<td>" + alarmLog.organizationName
							+ "</td>";
					tbodyHtml += "<td>"
							+ formatDate(alarmLog.collectTime,
									"yyyy-MM-dd hh:mm:ss") + "</td>";
					tbodyHtml += "<td>" + alarmLog.content + "</td>";
					tbodyHtml += "</tr>";
				}
				
				$("#alarmLog_tbody").html(tbodyHtml);

			}
		});
	}
	$(function() {
		windowRes();
		$(window).resize(function() {
			windowRes();

		});
		
	
		if(isPC()){
			$("#system").show();
		}else{
			$("#system").hide();
		}
		refreshAlarmLog();
		
		initMap();
	});
	var opts = {
			width : 250,     // 信息窗口宽度
			height: 80,     // 信息窗口高度
			title : "信息窗口" , // 信息窗口标题
			enableMessage:true//设置允许信息窗发送短息
		   };
	var map;
	function initMap(){
		
		//百度地图API功能
		 map = new BMap.Map("map");    // 创建Map实例

		//map.centerAndZoom(new BMap.Point(116.404, 39.915), 11);  // 初始化地图,设置中心点坐标和地图级别
		//map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
		//map.setCurrentCity("成都");          // 设 置地图显示的城市 此项是必须设置的
		var point = new BMap.Point(104.0716500000,30.6638100000);
		map.centerAndZoom(point,15);      // 初始化地图,用城市名设置地图中心点
		map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
		
		
		var data_info = [[104.0716500000,30.6638100000,"地址：成都是天府广场"],
						 [104.0852550000,30.6629630000,"地址：成都市春熙路"]];
		for(var i=0;i<data_info.length;i++){
			var marker = new BMap.Marker(new BMap.Point(data_info[i][0],data_info[i][1]));  // 创建标注
			
			var content = data_info[i][2];
			map.addOverlay(marker);               // 将标注添加到地图中
			//marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
			addClickHandler(content,marker);
		}
	
		
		 // 添加带有定位的导航控件
		var navigationControl = new BMap.NavigationControl({
		    // 靠左上角位置
		    anchor: BMAP_ANCHOR_TOP_LEFT,
		    // LARGE类型
		    type: BMAP_NAVIGATION_CONTROL_LARGE,
		    // 启用显示定位
		    enableGeolocation: true
		});
		map.addControl(navigationControl);
		
		 
		
	}
	function addClickHandler(content,marker){
		marker.addEventListener("click",function(e){
			openInfo(content,e)}
		);
	}
	function openInfo(content,e){
		var p = e.target;
		var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
		var infoWindow = new BMap.InfoWindow(content,opts);  // 创建信息窗口对象 
		map.openInfoWindow(infoWindow,point); //开启信息窗口
	}
</script>
<style type="text/css">
.table td {
	text-align: left;
	
}
th{
             white-space: nowrap;
     }
     #map {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
	

</style>
</head>

<body class="metro-layout">

	<div class="header">
		<div id="switcher">
			<div class="center">
				<div class="headlines">
					<ul class="left-side">
						<li class="logo"><img
							src="${ctx }/static/monitorPlatform/images/0_nav_nongxinlogo.png" />
						</li>
					</ul>

					<ul class="right-side">
						
						
						
						<li>
							<div class="widget_blue">
								<div style="padding-top: 15px">
									<a href="javascript:exit()">退出</a>
								</div>
							</div>
						</li>
						<li style="margin-right: 5px" id="system">
							<div class="widget_darkgreen">
								<div style="padding-top: 15px">
									<a href="javascript:system()">后台</a>
								</div>
							</div>
						</li>
						<li style="margin-right: 5px">
							<div class="widget_blue">
								<div style="padding-top: 15px;">
									<a href="javascript:monitormain()">主页</a>
								</div>
							</div>
						</li>
						<li>
							<h1 style="margin: 0px;">
								admin<img src="${ctx }/static/monitorPlatform/images/user_2.png"
									style="width: 50px; height: 50px; vertical-align: middle; margin-left: 10px; margin-right: 10px" />
							</h1>
						</li>
						
					</ul>
				</div>
			</div>

		</div>
	</div>
	<div style="overflow-y: auto; height: 100%">
		<div class="clearfix_top">
			<div id="map"></div>
		</div>
		<div class="clearfix">
			<div id="example" style="height: 330px; border: 1px solid #DDDDDD;font-size: 12px;">
				<table class="table">
					<caption style="padding: 0px;">
						<div class="widget_lightblue" data-role="droptarget"
							style="color: #fff; padding: 5px">
							设备异常记录 <a href="javascript:refreshAlarmLog();" title="刷新" style="float: right"> <img
								src="${ctx }/static/monitorPlatform/images/refresh.png"
								style="width: 15px; height: 15px;" class="grid_refresh" />
							</a> <a href="${ctx }/monitorPlatform/alarmLog"
								style="margin-right: 30px; float: right;"> 更多 </a>
						</div>
					</caption>
					<thead>
						<tr>
							<th style="list-style: ">设备类型</th>
							<th>设备名称</th>
							<th>所属网点</th>
							<th>异常时间</th>
							<th>异常内容</th>
						</tr>
					</thead>
					<tbody id="alarmLog_tbody">
						<tr></tr>

					</tbody>
				</table>



			</div>
			<div class="items">
			<div id="items_div" style="float: left;">
				<a href="${ctx }/monitorPlatform/list/good">
					<div class="box widget4x4 widget_green">
						<div class="monitortype">
							<img
								src="${ctx }/static/monitorPlatform/images/approval_shield.png" />
							${good }家
						</div>
						<div class="monitortypefont">正常网点</div>
					</div>
				</a> 
				<a href="${ctx }/monitorPlatform/list/error">
					<div class="box widget4x4 widget_red">
						<div class="monitortype">
							<img
								src="${ctx }/static/monitorPlatform/images/delete_shield.png" />
							${error }家
						</div>
						<div class="monitortypefont">异常网点</div>
					</div>
				</a> <a href="${ctx }/monitorPlatform/list/warning">
					<div class="division">&nbsp;</div>
					<div class="box widget4x4 widget_yellow">
						<div class="monitortype">
							<img
								src="${ctx }/static/monitorPlatform/images/warning_shield.png" />
							${warning }家
						</div>
						<div class="monitortypefont">预警网点</div>
					</div>
				</a> <a href="${ctx }/monitorPlatform/list/all">
					<div class="box widget4x4 widget_darkblue">
						<div class="monitortype" style="padding-top: 55px; height: 61%">
							<font>总机构网点</font> ${total }家
						</div>
						<div
							style="font-size: 24px; font-weight: bold; text-align: center; padding-top: 20px">进入监控中心</div>
					</div>
				</a>
				</div>
			</div>
		</div> 
		<div style="width: 10%; margin-bottom: 200px">&nbsp;</div>
	</div>
	<footer class="footer">
		<div style="margin-top: 15px;">版权所有©成都申控物联科技有限公司 | Version: SK_DHRS_NX0100</div>
	</footer>
	
</body>

</html>


