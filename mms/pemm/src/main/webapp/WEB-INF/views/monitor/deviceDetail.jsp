<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="${ctx }/static/plugins/easyui/jquery/jquery-2.1.1.min.js"></script>
<link href="${ctx }/static/css/monitor/deviceView.css" rel="stylesheet">
<link href="${ctx }/static/css/monitor/deviceDetail.css"
	rel="stylesheet">
<script type="text/javascript"
	src="${ctx }/static/plugins/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="${ctx }/static/plugins/bootstrap/css/bootstrap.min.css">

<script type="text/javascript">
	var ctx = "${ctx}";
	var gitIdStr="${gitIds}"
</script>
<script type="text/javascript" src="${ctx }/static/js/monitor/deviceDetail.js"></script>
<script type="text/javascript">
	$(function() {
	if("${gitInfoList[0].serverState}"=="unconnect"){
		$("#connect").hide();
		$("#unconnect").show();
	}else{
		$("#connect").show();
		$("#unconnect").hide();
	}
		windowDeviceResize();
		$(window).resize(function() {
			windowDeviceResize();

		});
		$("#bakcDeviceView").click(function(){
			$("#bakcBranch").css({"margin-left":"125px"});
			$("#bakcDeviceView").hide();
			$.get(ctx + "/system/monitor/deviceDetail",{gitIds:gitIdStr}, function(data) {
				$("#view_div").html(data);
			});
		});
	});
	function windowDeviceResize() {
		$("#content").height($(window).height() - 130);

		$("#device_left").height($("#content").height());
		$("#view_div").height($("#content").height());
		$("#view_div").width($(window).width() - 490);
		
		   
		$(".ups_border").width(function(){
			$(this).width($("#view_div").width() - 90);
		});
		$(".other_content_div").each(function(){
			var left=($("#view_div").width()-45-$(this).width())/2;
			left=left>0?left:0;
			$(this).css({"margin-left":left});
		});

	}
	function upsDetail(gitInfoId){
		$("#bakcBranch").css({"margin-left":"60px"});
		$("#bakcDeviceView").show();
		$.get(ctx+"/system/monitor/deviceInformationList",{gitInfoId:gitInfoId},function(data){
			$("#view_div").html(data);
		});
	}

	
	
</script>
</head>
<body>
	<center>
	
		<div id="unconnect" style="margin-top:50px;color:#CCD7DB;font-size:28px;font-weight:800">
		采集器链接丢失，请检查网络!
		</div>
		<div id="connect">
			<c:forEach items="${gitInfoList }" var="gitInfo">
				<div
					style="font-size: 16px; font-weight: 800; margin-left: 10px; margin: 10px;text-align: left;">网点名称：【${gitInfo.name }】<a href="javascript:upsDetail('${gitInfo.id }')" class="btn btn-default">查看详情</a>&nbsp;<a href="javascript:closeAlarm('${gitInfo.ip }')" class='btn btn-default'>关闭报警</a></div>
				<hr class="git_hr" style="width: 100%; margin-bottom: 10px;">
				<c:forEach items="${gitInfo.upsStatusList }" var="upsStatus" varStatus="varStatus">
				
					<div class="ups_border">
					<div style="text-align: left;">${upsStatus.deviceName }</div>
						<div class="ups_detail">

							<div class="ups_detail_left">
								<div class="inputVoltage">
									<table>
										<tr>
											<td align="left" style="padding-left: 10px;" >
											<span id="inputVoltage_${varStatus.index }">
											<c:if
													test="${upsStatus.upsType==2||upsStatus.upsType==3}">
									  A相输入电压：${fn:split(upsStatus.inputVoltage,"/")[0] }<br>
									  B相输入电压：${fn:split(upsStatus.inputVoltage,"/")[1] }<br>
									  C相输入电压：${fn:split(upsStatus.inputVoltage,"/")[2] }
									</c:if> <c:if test="${upsStatus.upsType!=2&&upsStatus.upsType!=3}">
									输入电压：${fn:split(upsStatus.inputVoltage,"/")[0] }
									</c:if>
									</span><br/>
									
									输入频率：<span id="frequency_${varStatus.index }">${upsStatus.frequency }</span>Hz
		
									</td>
										</tr>
									</table>
								</div>
								<div class="upsInfo">
									<table>
										<tr>
											<td  align="left" style="padding-left: 20px;" >
												
												机内温度：<span id="upsInfo_${varStatus.index }">${upsStatus.internalTemperature }</span>℃<br>

												
											</td>
											</td>
										</tr>
									</table>
								</div>
								<div class="batteryStatus">
									<table>
										<tr>
											<td align="left" style="padding-left: 20px;" >
											额定电流：<span id="ratedCurrent_${varStatus.index }">${upsStatus.ratedCurrent }</span>A<br>
											电池电量：<span id="electricQuantity_${varStatus.index }">${upsStatus.electricQuantity}</span>%<br>
											电池状态：<span id="batteryVoltageStatus_${varStatus.index }"><c:if test="${upsStatus.batteryVoltageStatus==1 }">
											<span style="color: red;">低</span>
											</c:if> <c:if test="${upsStatus.batteryVoltageStatus!=1 }">
											<span style="color: white;">正常</span>
											</c:if>
											</span>
											<br>
											UPS状态：<span id="currentState_${varStatus.index }"><c:if test="${upsStatus.currentState!=0 }">
											<span style="color: red;">故障</span>
											</c:if> <c:if test="${upsStatus.currentState==0 }">
											<span style="color: white;">正常</span>
											</c:if></span><br>
													运行状态：<span id="runningStatus_${varStatus.index }"><c:if
														test="${upsStatus.runningStatus==1 }">
														<span style="color: red;">旁路</span>
													</c:if> <c:if test="${upsStatus.runningStatus!=1 }">
														<span style="color: white;">正常</span>
													</c:if></span>
											</td>
										</tr>
									</table>
								</div>
							</div>
							<div class="ups_detail_center">

								<div class="bypassVoltage">
									<table>
										<tr>
											<td align="left" style="padding-left: 20px;" >
											<span id="bypassVoltage_${varStatus.index }">
											<c:if
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

												</c:if> <c:if test="${upsStatus.upsType!=2&&upsStatus.upsType !=3}">
													<c:if test="${upsStatus.bypassVoltage==null}">
													旁路电压：0V<br>

													</c:if>
													<c:if test="${upsStatus.bypassVoltage!=null}">
													旁路电压：${fn:split(upsStatus.bypassVoltage,"/")[0] }
									  				
									   			 </c:if>

												</c:if>
												</span>
												</td>
										</tr>
									</table>
								</div>
							</div>
							<div class="ups_detail_right">
								<div class="outputVoltage">
									<table>
										<tr>
											<td align="left" style="padding-left: 20px;" >
											<span id="outputVoltage_${varStatus.index }">
											<c:if
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
												</c:if> <c:if test="${upsStatus.upsType!=2&&upsStatus.upsType!=3}">
													<c:if test="${upsStatus.outputVoltage==null}">
												输出电压：0V<br>

													</c:if>
													<c:if test="${upsStatus.outputVoltage!=null}">
											  输出电压：${fn:split(upsStatus.outputVoltage,"/")[0] }
											</c:if>
												</c:if>
												</span>
												</td>
										</tr>
									</table>
								</div>
								<div class="outputFrenquency">
									<table>
										<tr>
											<td align="left" style="padding-left: 20px;" >
									
												输出频率：<span id="outputFrenquency_${varStatus.index }">${upsStatus.outputFrenquency }</span>Hz

												
							
												</td>
										</tr>
									</table>
								</div>
								<div class="load">
									<table>
										<tr>
											<td align="left" style="padding-left: 20px;" >
											<span id="upsLoad_${varStatus.index }">
											<c:if
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
												</c:if> <c:if test="${upsStatus.upsType!=2&&upsStatus.upsType!=3}">
													<c:if test="${upsStatus.upsLoad==null}">
												负载：0%<br>

													</c:if>
													<c:if test="${upsStatus.upsLoad!=null}">
											  负载：${fn:split(upsStatus.upsLoad,"/")[0] }
											</c:if>
												</c:if>
												</span>
												</td>
										</tr>
									</table>
								</div>
							</div>

						</div>
						<div class="ups_detail_bottom">
							<div style="height: 40px;float: left;width: 70px;margin-left:58px;padding-top:10px;cursor: pointer;" onclick="discharge(${upsStatus.deviceId})">放电</div>
							<div style="height: 40px;float: left;width:70px;margin-left: 26px;padding-top:10px;cursor: pointer;" onclick="closeUps(${upsStatus.deviceId})">关机</div>
						</div>
					</div>
				</c:forEach>

				<div class="other">

					<div class="other_content_div"> 
						<c:forEach items="${gitInfo.thStatusList }" var="thStatus" varStatus="varThStatus">
							<div class="other_div">
								<div class="other_title">温湿度信息</div>
								<div class="other_content">
									<div class="other_content_img">
										<img src="${ctx }/static/images/monitor/th.png" />
									</div>
									<div class="th_content_detail">
										名称：
										
										${thStatus.name }
										
										<br> 温度：<span id="temperature_${varThStatus.index }">${thStatus.temperature }</span>℃ <br>
										湿度：<span id="humidity_${varThStatus.index }">${thStatus.humidity }</span>%
									</div>
								</div>
							</div>
						</c:forEach> 
						<c:forEach items="${gitInfo.waterStatusList }" var="waterStatus" varStatus="varWaterStatus">
							<div class="other_div">
								<div class="other_title">水浸信息</div>
								<div class="other_content">
									<div class="other_content_img">
										<img src="${ctx }/static/images/monitor/water.png" />
									</div>
									<div class="inputSwitch_content_detail">
										名称：${waterStatus.name }<br>
										状态：<span id="waterStatus_${varWaterStatus.index }"><c:if test="${waterStatus.currentState==0}">正常</c:if><c:if test="${waterStatus.currentState!=0 }"><span style="color: red">异常</span></c:if></span><br>

									</div>
								</div>
							</div>
						</c:forEach>
						<c:forEach items="${gitInfo.smokeStatusList }" var="smokeStatus" varStatus="varSmokeStatus">
							<div class="other_div">
								<div class="other_title">烟感信息</div>
								<div class="other_content">
									<div class="other_content_img">
										<img src="${ctx }/static/images/monitor/smoke.png" />
									</div>
									<div class="inputSwitch_content_detail">
										名称：${smokeStatus.name }<br>
										状态：<span id="smokeStatus_${varSmokeStatus.index }"><c:if test="${smokeStatus.currentState==0}">正常</c:if><c:if test="${smokeStatus.currentState!=0 }"><span style="color: red">异常</span></c:if></span><br>

									</div>
								</div>
							</div>
						</c:forEach>
					</div>



				</div>
				<div style="height: 40px; float: left;">&nbsp;</div>

			</c:forEach>
		</div>
	</center>
</body>
</html>