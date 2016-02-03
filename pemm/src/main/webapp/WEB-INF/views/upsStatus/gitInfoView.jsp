<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<title>显示所有设备</title>

<script type="text/javascript"
	src="${ctx }/static/plugins/bootstrap/js/bootstrap.min.js"></script>
<link href="${ctx}/static/plugins/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />

<script type="text/javascript">
	$(function() {
		var clientWidth = document.body.clientWidth;
		$("#paneldiv").width(parseInt(clientWidth / 140) * 140);
		$(".popover-options .pop").popover({
			html : true,
			trigger : "hover"
		});
		$(".popover-options pop").on('shown.bs.popover', function() {
			// 执行一些动作...
		})
	});
	window.onresize = function() {
		var clientWidth = document.body.clientWidth;
		$("#paneldiv").width(parseInt(clientWidth / 140) * 140);
	}
	function showDetail(isDevice,gitId){
		if(isDevice){
			window.location.href="${ctx }/system/monitor/gitToDeviceDetail?gitIds="+gitId;
		}
	}
	function showDevice(div){
		showDetail($("#"+div.id).children(".isDevice").val(),$("#"+div.id).children(".gitInfoId").val());
	}
</script>
<style type="text/css">
.device-panel {
	
	height:100px;
	margin: 5px;
}

.poptable {
	font-size: 12px;
	width: 100%;
}
.pop {
	cursor: pointer;
}
a:ACTIVE {
	text-decoration: none;
}
.device-panel:HOVER {
	background: #F0F0F0;
}
</style>
</head>
<body>
	<div class="easyui-panel" title="" data-options="fit: true">
		<div style="text-align: center;">
			<div style="display: inline-block; margin-top: 	10px;" id="paneldiv">
				<c:forEach items="${gitInfoList }" var="gitInfo" varStatus="status">
					<c:set var="isDevice" value="false"></c:set>
					
					<div
						class="pull-left panel panel-default device-panel popover-options"
						style="padding: 10px;" onclick="showDevice(this)" id="device_panel_${gitInfo.id }">
						<div onmousemove="" class="pop" href="javascript:void(0)"
							title="【${gitInfo.organizationName }】外设列表" data-placement="bottom"
							data-container="body" data-toggle="popover"
							data-content="
							<table class='poptable'>
							<tr>
								<td>类型</td>
								<td>数量</td>
								<td>状态</td>
							</tr>

							<c:forEach items="${deviceStateRecordList }"
								var="deviceStateRecord">
								<c:if test="${deviceStateRecord.gitInfoId eq gitInfo.id }">
								<c:set var="isDevice" value="true"></c:set>
									<tr>
										<td>
									<c:if test="${deviceStateRecord.deviceInterfaceType eq 'UPS'}">
									UPS
									</c:if>
									<c:if test="${deviceStateRecord.deviceInterfaceType eq 'TH'}">
									温湿度
									</c:if>
									<c:if test="${deviceStateRecord.deviceInterfaceType eq 'WATER'}">
									水浸
									</c:if>
									<c:if test="${deviceStateRecord.deviceInterfaceType eq 'SMOKE'}">
									烟感
									</c:if>
									<c:if test="${deviceStateRecord.deviceInterfaceType eq 'AC'}">
									空调
									</c:if>
										</td>
										<td>${deviceStateRecord.count }</td>
										<td>${deviceStateRecord.currentState=="error"?"异常":(deviceStateRecord.currentState=="warning"?"告警":"正常")}</td>
										
									
										
									
										
										
								</c:if>
							</c:forEach>
							</tr>
							</table>">
							<center>
							<c:if test='${gitInfo.serverState=="unconnect" }'>
							<span style="color: red;">链接丢失</span>
							</c:if>
							<c:if test='${gitInfo.serverState!="unconnect" }'>
						<c:set var="currentState" value="${gitInfo.currentState}"></c:set>
					        <c:if test='${currentState=="good" }'>
					        <span style="color: green;">正常</span>
					        </c:if>
					         <c:if test='${currentState=="warning" }'>
					        <span style="color: #FDD116;">报警</span>
					        </c:if>
					         <c:if test='${currentState=="error" }'>
					        <span style="color: red;">异常</span>
					        </c:if>
					        </c:if></br>
					       
							<img alt="" src="${ctx}/static/images/git.png"  width="80px"></center>
						</div>
						IP：${gitInfo.ip}<br /> 名称：${gitInfo.name }<br />

						 <input class="isDevice" type="hidden" value="${isDevice }">
					        <input class="gitInfoId" type="hidden" value="${gitInfo.id }">

					</div>
					<script type="text/javascript">
						if ("${isDevice}" == "false") {
							$(".device-panel")["${status.index}"].outerHTML = "";
						}
					</script>

				</c:forEach>

			</div>
		</div><div style="height: 100px;float: left;"></div>
	</div>
	
</body>
</html>