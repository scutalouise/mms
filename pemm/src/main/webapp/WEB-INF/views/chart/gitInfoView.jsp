<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
$(function(){
	var clientWidth = document.body.clientWidth;
	$("#paneldiv").width(parseInt(clientWidth / 220) * 220);
	
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
a:ACTIVE {
	text-decoration: none;
}
</style>
</head>
<body>
	<div class="easyui-panel" title="主机视图" data-options="fit:true">
		<div style="text-align: center;">
		<div style="display: inline-block; margin-top: 30px;" id="paneldiv">
				<c:forEach items="${gitInfoList }" var="gitInfo">
				
					<div
						class="pull-left panel panel-default device-panel popover-options"
						style="padding: 10px">
						<div>
							<img alt="" src="${ctx}/static/images/git.jpg" width="180px">
						</div>
						IP：${gitInfo.ip}<br /> 名称：${gitInfo.name }<br />

						<div style="text-align: center;">
							<a href="${ctx }/chart/upsChart?gitInfoId=${gitInfo.id}">查看报表 </a>
							
						</div>
						
					</div>


				</c:forEach>

			</div>
		
		</div>

	</div>
</body>
</html>