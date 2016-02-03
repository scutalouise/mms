<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>主页</title>
<script src="${ctx }/static/plugins/easyui/jquery/jquery-2.1.1.min.js"></script>
<script type="text/javascript"
	src="${ctx }/static/plugins/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="${ctx }/static/plugins/bootstrap/css/bootstrap.min.css">
<script type="text/javascript">
	var ctx = "${ctx}";
	var good = parseInt("${currentState_good}" == "" ? 0
			: "${currentState_good}");
	var warning = parseInt("${currentState_warning}" == "" ? 0
			: "${currentState_warning}");
	var error = parseInt("${currentState_error}" == "" ? 0
			: "${currentState_error}");
</script>
<script type="text/javascript"
	src="${ctx }/static/plugins/echarts/dist/echarts.js"></script>

<script type="text/javascript" src="${ctx }/static/js/monitor/main.js"></script>

</head>
<body>
	<div id="top_img">

		<div id="top_content">
			<div id="topCarousel" class="carousel slide">

				<!-- 轮播（Carousel）指标 -->

				<ol class="carousel-indicators" style="margin-bottom: 0px;">

					<li data-target="#topCarousel" data-slide-to="0" title="设备状态统计"
						></li>

					<li data-target="#topCarousel" class="active" data-slide-to="1" title="机构分布"></li>

					<li data-target="#topCarousel" data-slide-to="2" title="设备分布"></li>

				</ol>

				<!-- 轮播（Carousel）项目 -->

				<div class="carousel-inner" id="carousel_content">
					<div class="item">
						<div id="carousel_one">
							<div id="top_img_bg">

								<!-- <div id="top_font"></div> -->
							</div>
						</div>
					</div>
					<div class="item active">
						<!-- style="filter:alpha(opacity=50); -moz-opacity:0.5; -khtml-opacity: 0.5; opacity: 0.5; " -->
						<div class="carousel_content_div"
							style="background-color: #eef1f2; height: 100%;">

							<div id="status_main"></div>
							<div id="alarm_main">
								<ul class="nav nav-tabs" id="alarm_tabs">
									<li onclick=""><a href="#day_main" style="">日</a></li>
									<li onclick=""><a href="#month_main" style="">月</a></li>
									<li onclick=""><a href="#year_main" style="">年</a></li>


								</ul>
								<div class="tab-content" id="alarm_content_div">
									<div class="tab-pane active" id="year_main"></div>
									<div class="tab-pane" id="month_main"></div>
									<div class="tab-pane" id="day_main"></div>
								</div>

							</div>

						</div>
					</div>

					<div class="item">
						<div class="carousel_content_div">
							<div id="map">
								<img alt="" src="${ctx }/static/images/monitor/rongxian.png"
									style="width: 708px; height: 100%;">
							</div>
						</div>
					</div>



				</div>

				<!-- 轮播(Carousel)导航 -->

				<a class="carousel-control left" id="carousel_control_left"
					href="#topCarousel" data-slide="prev"> <!-- &lsaquo; -->
				</a> <a class="carousel-control right" id="carousel_control_right"
					href="#topCarousel" data-slide="next"> <!-- &rsaquo; -->
				</a>


			</div>

		</div>
		<!-- <div id="top_img_bg">
		
			 <div id="top_font">
			
			
			</div> 
		</div> -->
	</div>


	<div id="center_status">
		<div id="center_content">

			<div class="branches_status">
				<div class="branches_status_div">
					<img src="${ctx }/static/images/monitor/good.gif">
				</div>
				<div class="branches_status_div">
					正常网点:${good }家<br> 总机构网点:${total }家<br>
				</div>
				<div class="branches_status_div">
					<div id="branches_button_good">正常</div>
				</div>
			</div>
			<div class="branches_status">
				<div class="branches_status_div">
					<img src="${ctx }/static/images/monitor/warning.png">
				</div>
				<div class="branches_status_div">
					报警网点:${warning }家<br> 总机构网点:${total }家<br>
				</div>
				<div class="branches_status_div">
					<div id="branches_button_warning">预警</div>
				</div>
			</div>
			<div class="branches_status">
				<div class="branches_status_div">
					<img src="${ctx }/static/images/monitor/error.gif">
				</div>
				<div class="branches_status_div">
					异常网点:${error }家<br> 总机构网点:${total }家<br>
				</div>
				<div class="branches_status_div">
					<div id="branches_button_error">异常</div>
				</div>
			</div>

		</div>
		<div id="input_center">
			<center>
				<div id="input_center_input">进入监控中心</div>
			</center>
		</div>
	</div>
	</div>
</body>
</html>