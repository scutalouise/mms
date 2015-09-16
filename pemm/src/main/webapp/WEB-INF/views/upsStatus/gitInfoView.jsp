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
		$("#paneldiv").width(parseInt(clientWidth / 220) * 220);
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
		$("#paneldiv").width(parseInt(clientWidth / 220) * 220);
	}
</script>
<style type="text/css">
.device-panel {
	width: 200px;
	height: 180px;
	margin: 10px;
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
</style>
</head>
<body>
	<div class="easyui-panel" title="主机视图" data-options="fit: true">
		<div style="text-align: center;">
			<div style="display: inline-block; margin-top: 30px;" id="paneldiv">
				<c:forEach items="${gitInfoList }" var="gitInfo" varStatus="status">
					<c:set var="isDevice" value="false"></c:set>
					<div
						class="pull-left panel panel-default device-panel popover-options"
						style="padding: 10px">
						<div onmousemove="" class="pop" href="javascript:void(0)"
							title="【${gitInfo.location }】外设列表" data-placement="bottom"
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
										<td>${deviceStateRecord.deviceType}</td>
										<td>${deviceStateRecord.count }</td>
										<td>${deviceStateRecord.currentState==0?'正常':'异常'}</td>
								</c:if>
							</c:forEach>
							</tr>
						</table>">
							<img alt="" src="${ctx}/static/images/git.jpg" width="180px">
						</div>
						IP：${gitInfo.ip}<br /> 名称：${gitInfo.name }<br />

						<div style="text-align: center;">

							<c:if test="${isDevice==true }">
								<a
									href="${ctx }/upsStatus/upsStatusView?gitInfoId=${gitInfo.id}">查看详情
								</a>
							</c:if>
							<c:if test="${isDevice==false }">
							没有挂接设备
							</c:if>
						</div>

					</div>
					<script type="text/javascript">
						if ("${isDevice}" == "false") {
							$(".device-panel")["${status.index}"].outerHTML = "";
						}
					</script>

				</c:forEach>

			</div>
		</div>
	</div>
</body>
</html>