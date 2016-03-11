<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>主页</title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

<script type="text/javascript"
	src="${ctx }/static/plugins/echarts/dist/echarts.js"></script>

<script type="text/javascript">
	var good = parseInt("${currentState_good}" == "" ? 0
			: "${currentState_good}");
	var warning = parseInt("${currentState_warning}" == "" ? 0
			: "${currentState_warning}");
	var error = parseInt("${currentState_error}" == "" ? 0
			: "${currentState_error}");
</script>
<script type="text/javascript" src="${ctx }/static/js/home/home.js"></script>
<style type="text/css">
#tooltipul {
	list-style: none;
	margin: 0px;
	padding: 0px;
}

#tooltipul>li {
	padding: 5px;
	float: left;
	width: 170px;
}

#tooltiptitle {
	font-weight: bold;
	padding: 5px;
}

a:HOVER {
	text-decoration: none;
}

a {
	text-decoration: none;
	color: black;
}
</style>
</head>
<body>
	<div class="easyui-layout" data-options="fit: true" id="home_panel">


		<div data-options="region:'south',split:true,border:true"
			style="height: 200px">
			<div id="dg"></div>
		</div>
	</div>

</body>
</html>