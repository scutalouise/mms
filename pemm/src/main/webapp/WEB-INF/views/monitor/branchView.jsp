<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网点视图</title>
<link rel="stylesheet" href="${ctx }/static/css/monitor/branchView.css">
<script src="${ctx }/static/plugins/easyui/jquery/jquery-2.1.1.min.js"></script>
<script type="text/javascript"
	src="${ctx }/static/plugins/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="${ctx }/static/plugins/bootstrap/css/bootstrap.min.css">
<script type="text/javascript">
	var ctx = "${ctx}";
</script>
<script type="text/javascript"
	src="${ctx }/static/js/monitor/branchView.js">
	
</script>
</head>
<body>

	<!-- 模态框（Modal） -->
	<div class="modal fade" id="devicewarning" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog" style="width: 350px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">x</button>
					提示
				</div>
				<div class="modal-body">请选择预览的设备！</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">确定</button>
				</div>
			</div>
		</div>

	</div>

	<div id="branch_center_content" style="height: 100%;">
		<center>
			<div id="search_div">
			<div id="search_input_div">
				<input id="search_input">
				<div id="search_icon"></div>
				</div>
				<div id="status_detail">
					当前网点状态：<span style="color: #8cc499;">正常</span>|<span
						style="color: #edd37a;">告警</span>|<span style="color: #ca8289">异常</span>
				</div>
			</div>

		</center>
		<div id="branch_list">
		
		</div>
		<div style="height: 50px;"></div>
	</div>


	<div id="deviceList">
		<div id="deviceView">
			<button type="button" class="btn btn-default" onclick="preview();">设备视图预览</button>
		</div>
	</div>

</body>
</html>