<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>动态环境数据采集监控平台</title>
<link rel="shortcut icon" href="${ctx}/static/images/favicon.ico">
<link rel="stylesheet"
	href="${ctx }/static/monitorPlatform/css/main.css" type="text/css"
	media="screen" />
<link href="${ctx }/static/monitorPlatform/css/metro-list.css"
	rel="stylesheet">
<script type="text/javascript"
	src="${ctx }/static/monitorPlatform/js/jquery.js"></script>
<script type="text/javascript"
	src="${ctx }/static/plugins/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="${ctx }/static/plugins/bootstrap/css/bootstrap.min.css">
<script type="text/javascript" src="${ctx }/static/monitorPlatform/js/dateUtils.js">
</script>
<script type="text/javascript">
function exit() {
    if (confirm('您确定要退出？')) { 
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
$(function(){
	if(isPC()){
		$("#system").show();
	}else{
		$("#system").hide();
	}
	
	$.ajax({
		type:"post",
		
		url:"${ctx}/monitorPlatform/alarmLogAll",
		success:function(data){
			var tbodyHtml="";
			if(data.length==0){
				tbodyHtml="<tr><td colspan='5' style='text-align:center'>无数据</td></tr>"
			}
			for(var i=0;i<data.length;i++){
				var alarmLog=data[i];
				var deviceTypeValue="";
				var deviceTypeIndex=alarmLog.deviceTypeIndex;
			
				if(deviceTypeIndex==0){
					deviceTypeValue="UPS设备";
				}else if(deviceTypeIndex==1){
					deviceTypeValue="温湿度传感器"
				}else if(deviceTypeIndex==2){
					deviceTypeValue="水浸设备";
				}else if(deviceTypeIndex==3){
					deviceTypeValue="烟雾传感器";
				}
				if(alarmLog.alarmType==1){
					tbodyHtml+="<tr class='warning'>";
				}else{
					tbodyHtml+="<tr class='danger'>";
				}
				tbodyHtml+="<td>"+deviceTypeValue+"</td>";
				tbodyHtml+="<td>"+alarmLog.deviceName+"</td>";
				tbodyHtml+="<td>"+alarmLog.organizationName+"</td>";
				tbodyHtml+="<td>"+formatDate(alarmLog.collectTime,"yyyy-MM-dd hh:mm:ss")+"</td>";
				tbodyHtml+="<td>"+alarmLog.content+"</td>";
				tbodyHtml+="</tr>";
			}
			$("#alarmLog_tbody").html(tbodyHtml);
			
			
		}
	});
});
</script>
</head>
<body>
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
		<div style="margin-bottom: 10px;">
			<a href="${ctx }/monitorPlatform/index"> <img style="height: 40px;width: 40px;"
				src="${ctx }/static/monitorPlatform/images/left_round.png" /> <span>首页</span>
			</a>
		</div>
		<div>
			<table class="table">
				<caption style="padding: 0px;">
					<div class="widget_lightblue" data-role="droptarget"
						style="color: #fff; padding: 5px">设备异常记录</div>
				</caption>
				<thead>
					<tr>
						<th>设备类型</th>
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

	</div>
	<footer class="footer">
		<div style="margin-top: 15px;">版权所有©成都申控物联科技有限公司 | Version: SK_DHRS_NX0100</div>
	</footer>

</body>
</html>