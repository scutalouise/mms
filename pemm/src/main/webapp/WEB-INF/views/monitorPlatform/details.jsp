<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>动态环境数据采集监控平台</title>
<link rel="shortcut icon" href="${ctx}/static/images/favicon.ico">
<link rel="stylesheet"
	href="${ctx }/static/monitorPlatform/css/main.css" type="text/css"
	media="screen" />
<link rel="stylesheet"
	href="${ctx }/static/monitorPlatform/css/details.css" type="text/css"
	media="screen" />
<link href="${ctx }/static/monitorPlatform/css/metro-list.css"
	rel="stylesheet">
<script type="text/javascript"
	src="${ctx }/static/monitorPlatform/js/jquery.js"></script>
<script type="text/javascript"
	src="${ctx }/static/plugins/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="${ctx }/static/plugins/bootstrap/css/bootstrap.min.css">
<script type="text/javascript">
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
		if ("${gitInfoList[0].serverState}" == "unconnect") {
			$("#connect").hide();
			$("#unconnect").show();
		} else {
			$("#connect").show();
			$("#unconnect").hide();
		}
		windowOtherDeviceResize();
		$(window).resize(function() {
			windowOtherDeviceResize();

		});
		
	});
	function monitormain() {
		location.href = "${ctx}/monitorPlatform/index";
	}
	function system() {
		location.href = "${ctx}/a"
	}
	function exit() {
        if (confirm('确定退出')) { 
        	location.href = '${ctx}/m/logout';
        }
    }
	function windowOtherDeviceResize(){
		$("#other_content_div").each(function(){
			var left=($(".website").width()-$(this).width())/2;
			left=left>0?left:0;
			$(this).css({"margin-left":left});
		});
	}
	function refresh() {

		$.ajax({
					type : "post",
					url : "${ctx}/upsStatus/getLatestData",
					data : {
						gitInfoId : "${gitInfoId}"
					},
					success : function(data) {
						var gitInfo = data.gitInfo;
						if (gitInfo.serverState == "connect") {
							$("#unconnect").hide();
							$("#connect").show();
							var upsData = data.upsStatusList;
							var thData = data.thStatusList;
							var warterData = data.waterStatusList;
							var smokeData = data.smokeStatusList;
							for (var i = 0; i < upsData.length; i++) {
								var inputVoltageHtml = "";
								var bypassVoltageHtml = "";
								var outputVoltageHtml = "";
								var outputFrenquencyHtml = "";
								var upsLoadHtml = "";
								var frequencyHtml = "";
								var inputVoltages = upsData[i].inputVoltage
										.split("/");

								if (upsData[i].upsType == 2
										|| upsData[i].upsType == 3) {
									inputVoltageHtml += ("A相输入电压："
											+ inputVoltages[0] + "<br>");
									inputVoltageHtml += ("B相输入电压："
											+ inputVoltages[1] + "<br>");
									inputVoltageHtml += ("C相输入电压：" + inputVoltages[2]);

									if (upsData[i].bypassVoltage != null) {
										var bypassVoltages = upsData[i].bypassVoltage
												.split("/");
										bypassVoltageHtml += ("A相旁路电压："
												+ bypassVoltages[0] + "<br>");
										bypassVoltageHtml += ("B相旁路电压："
												+ bypassVoltages[1] + "<br>");
										bypassVoltageHtml += ("C相旁路电压：" + bypassVoltages[2]);
									} else {
										bypassVoltageHtml += "A相旁路电压：0V<br>";
										bypassVoltageHtml += "B相旁路电压：0V<br>";
										bypassVoltageHtml += "C相旁路电压：0V";
									}
									if (upsData[i].outputVoltage != null) {
										var outputVoltages = upsData[i].outputVoltage
												.split("/");
										outputVoltageHtml += ("A相输出电压："
												+ outputVoltages[0] + "<br>");
										outputVoltageHtml += ("B相输出电压："
												+ outputVoltages[1] + "<br>");
										outputVoltageHtml += ("C相输出电压：" + outputVoltages[2]);
									} else {
										outputVoltageHtml += "A相输出电压：0V<br>";
										outputVoltageHtml += "B相输出电压：0V<br>";
										outputVoltageHtml += "C相输出电压：0V";
									}

									if (upsData[i].upsLoad != null) {
										var upsLoads = upsData[i].upsLoad
												.split("/");
										upsLoadHtml += ("A相负载：" + upsLoads[0] + "<br>");
										upsLoadHtml += ("B相负载：" + upsLoads[1] + "<br>");
										upsLoadHtml += ("C相负载：" + upsLoads[2] + "<br>");
									} else {
										upsLoadHtml += ("A相负载：0%<br>");
										upsLoadHtml += ("B相负载：0%<br>");
										upsLoadHtml += ("C相负载：0%<br>");
									}
								} else {
									if (upsData[i].bypassVoltage != null) {
										bypassVoltageHtml = "<div style='margin-top:16px;'>旁路电压："
												+ upsData[i].bypassVoltage
														.split("/")[0]
												+ "</div>"
									} else {
										bypassVoltageHtml = "<div style='margin-top:16px;'>旁路电压：0.0V</div>"
									}
									if (upsData[i].outputVoltage != null) {
										outputVoltageHtml = "<div style='margin-top:16px;'>输出电压："
												+ upsData[i].outputVoltage
														.split("/")[0]
												+ "</div>"
									} else {
										outputVoltageHtml = "<div style='margin-top:18px;'>输出电压：0.0V</div>"
									}

									if (upsData[i].upsLoad != null) {

										upsLoadHtml = ("<div style='margin-top:16px;'>负载："
												+ upsData[i].upsLoad.split("/")[0] + "<br></div>");

									} else {
										upsLoadHtml = "<div style='margin-top:16px;'>负载：0%<br></div>";

									}
									inputVoltageHtml = "<div style='margin-top:10px;'>输入电压："
											+ inputVoltages[0] + "</div>";
								}
								$("#remainingTime_" + i).html(
										upsData[i].remainingTime);
								$("#frequency_" + i).html(
										parseFloat(upsData[i].frequency)
												.toFixed(1));
								$("#inputVoltage_" + i).html(inputVoltageHtml);
								$("#bypassVoltage_" + i)
										.html(bypassVoltageHtml);
								$("#outputVoltage_" + i)
										.html(outputVoltageHtml);
								$("#outputFrenquency_" + i).html(
										parseFloat(upsData[i].outputFrenquency)
												.toFixed(1));
								$("#upsLoad_" + i).html(upsLoadHtml);
								$("#upsInfo_" + i).html(
										parseFloat(
												upsData[i].internalTemperature)
												.toFixed(1));
								// 额定电流
								$("#ratedCurrent_" + i).html(
										parseFloat(upsData[i].ratedCurrent)
												.toFixed(1));
								// 电池电量
								$("#electricQuantity_" + i).html(
										upsData[i].electricQuantity);
								//电池电压
								$("#batteryVoltage_"+i).html(upsData[i].batteryVoltage);
								/* // 电池状态
								var batteryVoltageStatusHtml = "";
								if (upsData[i].batteryVoltageStatus == 1) {
									batteryVoltageStatusHtml = '<span style="color: red;">低</span>';
								} else {
									batteryVoltageStatusHtml = '<span style="color: black;">正常</span>';
								}
								$("#batteryVoltageStatus_" + i).html(
										batteryVoltageStatusHtml); */
								// UPS状态
								var currentStateHtml = "";
								if (upsData[i].currentState != 0) {
									currentStateHtml = '<span style="color: red;">异常</span>';
								} else {
									currentStateHtml = '<span style="color: black;">正常</span>';
								}
								$("#currentState_" + i).html(currentStateHtml);
								// 运行状态
								var runningStatusHtml = "";
								if (upsData[i].runningStatus == 1) {
									runningStatusHtml = '<span style="color: red;">旁路</span>';
								} else {
									runningStatusHtml = '<span style="color: black;">正常</span>';
								}
								$("#runningStatus_" + i)
										.html(runningStatusHtml);

							}
							for (var i = 0; i < thData.length; i++) {
								var temperature = parseFloat(thData[i].temperature).toFixed(1);
								var humidity = parseFloat(thData[i].humidity).toFixed(1);
								var temperatureState = thData[i].temperatureState;
								var humidityState = thData[i].humidityState;
								
								var temperatureHtml="<span";
								if(temperatureState=="error"){
									temperatureHtml+=" style='color:red'";
								}if(temperatureState=="warning"){
									temperatureHtml+=" style='color:yellow'";
								}
								temperatureHtml+=">"+temperature+"℃</span>";
								var humidityHtml="<span";
									if(humidityState=="error"){
										humidityHtml+=" style='color:red'";
									}if(humidityState=="warning"){
										humidityHtml+=" style='color:yellow'";
									}
									humidityHtml+=">"+humidity+"%</span>";
								$("#temperature_" + i).html(temperatureHtml);
								$("#humidity_" + i).html(humidityHtml);
							}
							for (var i = 0; i < warterData.length; i++) {
								var waterStatus = warterData[i].currentState;
								$("#waterStatus_" + i)
										.html(
												waterStatus == 0 ? "正常"
														: "<span style='color:red;'>异常</span>");
							}
							for (var i = 0; i < smokeData.length; i++) {
								var smokeStatus = smokeData[i].currentState;
								$("#smokeStatus_" + i)
										.html(
												smokeStatus == 0 ? "正常"
														: "<span style='color:red;'>异常</span>");
							}

						} else {
							$("#connect").hide();
							$("#unconnect").show();
						}
					}
				});
	}
	var interval = setInterval("refresh()", 10000);
	
	function closeAlarm(gitInfoIp){
		$.ajax({
			type : "post",
			url : "${ctx}/system/monitor/closeAlarm",
			data : {
				ip:gitInfoIp
			},
			success:function(data){
				if(data){
					alert("操作成功！");
				}
			}
		});
	}
	function discharge(deviceId){
		var r=confirm("是否确认放电?")
		  if (r==true)
		    {
		    $.ajax({
				method:"post",
				url:"${ctx}/system/dischargeTask/discharge",
				data:{deviceId:deviceId},success:function(data){
					if(data){
						alert("操作成功，正在放电！");
					}
				}
			});
		    }
		 

		
	}
	function closeUps(deviceId){
		var r=confirm("是否确认关闭UPS?");
		if(r){
			$.ajax({
				method:"post",
				url:ctx+"/device/upsClose",
				data:{deviceId:deviceId},success:function(data){
					if(data){
						alert("操作成功,UPS将在1分钟后关机！");
					}
				}
			});
		}
	}

	
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

	<div class="overflow-y">
		<div class="metro-layout" style="width: 100%;">
			<div style="text-align: center; width: 100%; text-align: center">

				<div class="website">
					<div style="border-bottom: 1px solid #536270; height: 50px;">
						<div style="float: left; margin-left: 10px;">
							<a href="${ctx }/monitorPlatform/list/${stateEnum}"> <img
								src="${ctx }/static/monitorPlatform/images/left_round.png"
								style="width: 40px; height: 40px; vertical-align: middle" /> <span
								class="website_span">机构网点</span>
							</a>
						</div>
					</div>

					<c:forEach items="${gitInfoList }" var="gitInfo">

						<div
							style="font-size: 20px; font-weight: bold; margin-top: 10px; color: #536270">所属网点：${gitInfo.organizationName}&nbsp;&nbsp;设备名称：${gitInfo.name}
							&nbsp;&nbsp;IP：${gitInfo.ip}</div>

						<div id="unconnect"
							style="margin-top: 50px; color: #CCD7DB; font-size: 28px; font-weight: 800">
							采集器链接丢失，请检查网络!</div>
						<div id="connect">
							<div
								style="font-size: 16px; font-weight: 800; margin-left: 10px; margin: 10px; text-align: center;">
								<a href="javascript:closeAlarm('${gitInfo.ip }')"
									class='btn btn-default'>关闭报警</a>
							</div>

							<c:forEach items="${gitInfo.upsStatusList }" var="upsStatus"
								varStatus="varStatus">
								<div class="listarea">
									<div class="widget_lightgrey listarea_right"
										style="font-size: 14px;">


										<div class="box_1 widget2x4 ">
											<div class="listarea_1_left">
												<div class="listarea_1_leftdiv">
													<span id="inputVoltage_${varStatus.index }"> <c:if
															test="${upsStatus.upsType==2||upsStatus.upsType==3}">
													  A相输入电压：${fn:split(upsStatus.inputVoltage,"/")[0] }<br>
									 				  B相输入电压：${fn:split(upsStatus.inputVoltage,"/")[1] }<br>
									 				  C相输入电压：${fn:split(upsStatus.inputVoltage,"/")[2] }
									            </c:if> <c:if
															test="${upsStatus.upsType!=2&&upsStatus.upsType!=3}">
															<div style="margin-top: 10px;">
																输入电压：${fn:split(upsStatus.inputVoltage,"/")[0] }</div>
														</c:if>
													</span> 输入频率：<span id="frequency_${varStatus.index }">${upsStatus.frequency }</span>Hz
												</div>
												<div>
													<img
														src="${ctx }/static/monitorPlatform/images/left118.png" />
												</div>
											</div>
											<div class="listarea_1_left">
												<div class="listarea_1_leftdiv">
													<div
														style="font-size: 26px; font-weight: bold; padding-top: 10px;">
														<span id="upsInfo_${varStatus.index }">${upsStatus.internalTemperature }</span>℃
													</div>
													<div>机内温度</div>
												</div>
												<div>
													<img
														src="${ctx }/static/monitorPlatform/images/left118.png" />
												</div>
											</div>
											<div class="listarea_1_left">
												<div class="listarea_1_leftdiv">
													<span id="bypassVoltage_${varStatus.index }"> <c:if
															test="${upsStatus.upsType==2||upsStatus.upsType ==3}">
															<c:if test="${upsStatus.bypassVoltage==null}">
																A相旁路电压：0V<br>
																B相旁路电压：0V<br>
																C相旁路电压：0V<br>
															</c:if>
															<c:if test="${upsStatus.bypassVoltage!=null}">
													 A相旁路电压：${fn:split(upsStatus.bypassVoltage,"/")[0] }<br>
									  				 B相旁路电压：${fn:split(upsStatus.bypassVoltage,"/")[1] }<br>
													 C相旁路电压：${fn:split(upsStatus.bypassVoltage,"/")[2] }
									   			    </c:if>

														</c:if> <c:if
															test="${upsStatus.upsType!=2&&upsStatus.upsType !=3}">
															<div style="margin-top: 16px;">
																<c:if test="${upsStatus.bypassVoltage==null}">
													
													旁路电压：0V<br>

																</c:if>
																<c:if test="${upsStatus.bypassVoltage!=null}">
													旁路电压：${fn:split(upsStatus.bypassVoltage,"/")[0] }
									  				
									   			 </c:if>
															</div>

														</c:if>
													</span>
												</div>
												<div>
													<img
														src="${ctx }/static/monitorPlatform/images/left118.png" />
												</div>
											</div>
										</div>



										<div class="box_1" style="width: 200px">
											<div style="margin-right: 10px">
												<div style="margin: 20px 5px 10px 0;">
													<img
														src="${ctx }/static/monitorPlatform/images/icon/UPS.png"
														style="width: 80px; height: 80px" /> <br /> 1号UPS
												</div>
												<div class="listarea_1computer">
													<div style="margin: 0px;">
														额定电流:<span id="ratedCurrent_${varStatus.index }">${upsStatus.ratedCurrent }</span>A
													</div>
													<div style="">
														电池电量:<span id="electricQuantity_${varStatus.index }">${upsStatus.electricQuantity}</span>%
													</div>
													<div style="">
													电池电压:<span id="batteryVoltage_${varStatus.index }">${upsStatus.batteryVoltage}</span>V
													</div>
													<%-- <div style="">
														电池状态:<span id="batteryVoltageStatus_${varStatus.index }"><c:if
																test="${upsStatus.batteryVoltageStatus==1 }">
																<span style="color: red;">低</span>
															</c:if> <c:if test="${upsStatus.batteryVoltageStatus!=1 }">
																<span style="color: black;">正常</span>
															</c:if></span>
													</div> --%>
													<div style="">
														UPS状态:<span id="currentState_${varStatus.index }"><c:if
																test="${upsStatus.currentState!=0 }">
																<span style="color: red;">异常</span>
															</c:if> <c:if test="${upsStatus.currentState==0 }">
																<span style="color: black;">正常</span>
															</c:if></span>
													</div>
													<div>
														运行状态:<span id="runningStatus_${varStatus.index }"><c:if
																test="${upsStatus.runningStatus==1 }">
																<span style="color: red;">旁路</span>
															</c:if> <c:if test="${upsStatus.runningStatus!=1 }">
																<span style="color: black;">正常</span>
															</c:if></span>
													</div>
												</div>
											</div>
										</div>
										<div class="box_1 widget2x4 " style="margin-left: -8px">
											<div style="margin-right: 10px">
												<div class="listarea_1_left">
													<div>
														<img
															src="${ctx }/static/monitorPlatform/images/left118.png"
															style="float: left" />
													</div>
													<div class="listarea_1_rightdiv">
														<span id="outputVoltage_${varStatus.index }"> <c:if
																test="${upsStatus.upsType==2||upsStatus.upsType ==3}">
																<c:if test="${upsStatus.outputVoltage==null}">
													A相输出电压：0V<br>
													B相输出电压：0V<br>
													C相输出电压：0V
													</c:if>
																<c:if test="${upsStatus.outputVoltage!=null}">
									  			 	A相输出电压 ${fn:split(upsStatus.outputVoltage,"/")[0] }<br>
									  			    B相输出电压 ${fn:split(upsStatus.outputVoltage,"/")[1] }<br>
									  			    C相输出电压 ${fn:split(upsStatus.outputVoltage,"/")[2] }<br>
																</c:if>
															</c:if> <c:if
																test="${upsStatus.upsType!=2&&upsStatus.upsType!=3}">
																<div style="margin-top: 18px;">
																	<c:if test="${upsStatus.outputVoltage==null}">
															输出电压：0V<br>
																	</c:if>
																	<c:if test="${upsStatus.outputVoltage!=null}">
														 	输出电压：${fn:split(upsStatus.outputVoltage,"/")[0] }
														</c:if>
																</div>
															</c:if>
														</span>
													</div>
												</div>
												<div class="listarea_1_left">
													<div>
														<img
															src="${ctx }/static/monitorPlatform/images/left118.png"
															style="float: left" />
													</div>
													<div class="listarea_1_rightdiv">
														<div style="margin-top: 15px;">
															输出频率：<span id="outputFrenquency_${varStatus.index }">${upsStatus.outputFrenquency }</span>Hz
														</div>

													</div>
												</div>
												<div class="listarea_1_left">
													<div>
														<img
															src="${ctx }/static/monitorPlatform/images/left118.png"
															style="float: left" />
													</div>
													<div class="listarea_1_rightdiv">
														<span id="upsLoad_${varStatus.index }"> <c:if
																test="${upsStatus.upsType==2||upsStatus.upsType ==3}">
																<c:if test="${upsStatus.upsLoad==null}">
												A相负载：0%<br>
												B相负载：0%<br>
												C相负载：0%<br>
																</c:if>
																<c:if test="${upsStatus.upsLoad!=null}">
									  			 A相负载：${fn:split(upsStatus.upsLoad,"/")[0] }<br>
									  			 B相负载：${fn:split(upsStatus.upsLoad,"/")[1] }<br>
									  			 C相负载：${fn:split(upsStatus.upsLoad,"/")[2] }<br>
																</c:if>
															</c:if> <c:if
																test="${upsStatus.upsType!=2&&upsStatus.upsType!=3}">
																<div style="margin-top: 16px;">
																	<c:if test="${upsStatus.upsLoad==null}">
												负载：0%<br>

																	</c:if>
																	<c:if test="${upsStatus.upsLoad!=null}">
											  负载：${fn:split(upsStatus.upsLoad,"/")[0] }
											</c:if>
																</div>
															</c:if>
														</span>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div class="listarea_1footer">
										<div style="text-align: center; width: 100%; float: left">
											<img style="margin-right: 20%"
												src="${ctx }/static/monitorPlatform/images/detail_1.png"
												onclick="discharge(${upsStatus.deviceId})" /> <img
												src="${ctx }/static/monitorPlatform/images/detail_2.png"
												onclick="closeUps(${upsStatus.deviceId})" />
										</div>
									</div>
								</div>

							</c:forEach>
							<div style="float: left; margin-top: 30px;"
								id="other_content_div">

								<c:forEach items="${gitInfo.thStatusList }" var="thStatus"
									varStatus="varThStatus">

									<div
										style="width: 230px; height: 125px; border: 2px solid #cccaca; padding: 5px; margin-right: 20px; float: left; margin-top: 10px;">
										<div style="font-weight: bold">温湿度信息</div>
										<div style="margin-top: 10px">
											<div style="float: left">
												<img src="${ctx }/static/monitorPlatform/images/icon/th.png"
													style="width: 80px; height: 80px; margin-right: 10px" />
											</div>
											<div
												style="text-align: left; padding-top: 5px; font-size: 14px;">
												名称：${thStatus.name } <br/>温度：
												<span id="temperature_${varThStatus.index }">
												<c:if test="${thStatus.temperatureState=='error'}"><span style="color:red"></c:if>
												<c:if test="${thStatus.temperatureState=='warning'}"><span style="color:yellow"></c:if>
												${thStatus.temperature }℃</span>
												</span>
												 <br />
												湿度：
												<span id="humidity_${varThStatus.index }">
												<c:if test="${thStatus.humidityState=='error'}"><span style="color:red"></c:if>
												<c:if test="${thStatus.humidityState=='warning'}"><span style="color:yellow"></c:if>
												${thStatus.humidity }%</span>
												</span>
											</div>
										</div>
									</div>
								</c:forEach>

								<c:forEach items="${gitInfo.waterStatusList }" var="waterStatus"
									varStatus="varWaterStatus">

									<div
										style="width: 230px; height: 125px; border: 2px solid #cccaca; padding: 5px; margin-right: 20px; float: left; margin-top: 10px;">
										<div style="font-weight: bold">水浸信息</div>
										<div style="margin-top: 10px">
											<div style="float: left">
												<img
													src="${ctx }/static/monitorPlatform/images/icon/water.png"
													style="width: 80px; height: 80px; margin-right: 10px" />
											</div>
											<div
												style="text-align: left; padding-top: 20px; font-size: 14px;">
												名称：${waterStatus.name } <br /> 状态：<span
													id="waterStatus_${varWaterStatus.index }"><c:if
														test="${waterStatus.currentState==0}">正常</c:if> <c:if
														test="${waterStatus.currentState!=0 }">
														<span style="color: red">异常</span>
													</c:if></span><br>
											</div>
										</div>
									</div>
								</c:forEach>
								<c:forEach items="${gitInfo.smokeStatusList }" var="smokeStatus"
									varStatus="varSmokeStatus">

									<div
										style="width: 230px; height: 125px; border: 2px solid #cccaca; padding: 5px; margin-right: 20px; float: left; margin-top: 10px;">
										<div style="font-weight: bold">烟雾信息</div>
										<div style="margin-top: 10px">
											<div style="float: left">
												<img
													src="${ctx }/static/monitorPlatform/images/icon/smoke.png"
													style="width: 80px; height: 80px; margin-right: 10px" />
											</div>
											<div
												style="text-align: left; padding-top: 20px; font-size: 14px;">
												名称：${smokeStatus.name } <br /> 状态：<span
													id="smokeStatus_${varSmokeStatus.index }"><c:if
														test="${smokeStatus.currentState==0}">正常</c:if> <c:if
														test="${smokeStatus.currentState!=0 }">
														<span style="color: red">异常</span>
													</c:if></span><br>
											</div>
										</div>
									</div>
								</c:forEach>

							</div>
							
							
							<!-- 空调 -->
							
							
							<div style="clear: left;padding-top: 30px;">
								<!-- <div style="border:2px solid #cccaca;border-radius:5px;">
								11111
								</div> -->
								<c:forEach></c:forEach>
							
							</div>
							
							
						</div>
					</c:forEach>





				</div>

			</div>
		</div>
		<div style="width: 100%; float: left; height: 250px">&nbsp;</div>
	</div>
	<footer class="footer">
		<div style="margin-top: 15px;">版权所有©成都申控物联科技有限公司 | Version: SK_DHRS_NX0100</div>
	</footer>
</body>
</html>

