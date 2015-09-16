<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script type="text/javascript">
	var gitInfoId = "${param.gitInfoId}";
</script>
<script type="text/javascript" src="${ctx }/static/js/upsStatusView.js"></script>
<script type="text/javascript">
	var isUps = "${fn:length(upsStatusList)>0}";
</script>
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
}

.newState {
	margin: 20px;
	padding: 10px;
}

.upsStatus table {
	width: 100%;
	padding: 10px;
}

.upsStatus {
	margin: 20px;
}

.upsStatus .title {
	width: 100px;
	text-align: right;
	font-weight: bold;
}
</style>
</head>
<body>
	<div id="tab-tools">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			style="margin-top: 15px;"
			data-options="plain:true,iconCls:'icon-back'"
			onclick="history.go(-1)">返回</a>
	</div>
	<div class="easyui-tabs" id="tab"
		data-options="tabPosition : 'top',fit : true,tabWidth : 100,border : false,
		tabHeight : 60,tools:'#tab-tools'">
		<div title="当前状态" iconCls='icon-berlin-donate'
			data-options='refreshable:false' tabWidth="100">
			<div class="newState">
				<c:if test="${fn:length(upsStatusList)>0}">
					<fieldset>
						<legend>UPS状态信息</legend>
						<c:forEach items="${upsStatusList }" var="upsStatus">
							<div class="upsStatus">
								<fieldset>
									<legend>【UPS-${upsStatus.deviceIndex }】状态</legend>

									<table cellpadding="5">
										<tr>
											<td rowspan="9" style="width: 180px;"><img alt=""
												src="${ctx }/static/images/ups.png">
												<div style="text-align: center; padding: 5px;">
													<shiro:hasPermission name="sys:device:upsClose">
														<a class="easyui-linkbutton"
															onclick="upsOperation('${param.gitInfoId}','1.3.6.1.4.1.34651.2.1.1.60.${upsStatus.deviceIndex }',1)">放电</a>
													</shiro:hasPermission>
													&nbsp;&nbsp;
													<shiro:hasPermission name="sys:device:upsDischarge ">
														<a class="easyui-linkbutton"
															onclick="upsOperation('${param.gitInfoId}','1.3.6.1.4.1.34651.2.1.1.62.${upsStatus.deviceIndex }',1)">关机</a>
													</shiro:hasPermission>
												</div></td>
											<td class="title">设备名称:</td>
											<td>${upsStatus.name }</td>
											<td class="title">接口类型:</td>
											<td><c:if test="${upsStatus.interfaceType==0 }">RS232</c:if>
												<c:if test="${upsStatus.interfaceType==1 }">RS485</c:if> <c:if
													test="${upsStatus.interfaceType==2 }">zigbee</c:if> <c:if
													test="${upsStatus.interfaceType!=0&&upsStatus.interfaceType!=1&&upsStatus.interfaceType!=2 }">其他</c:if>
											</td>
											<td class="title">通讯状态:</td>
											<td><c:if test="${upsStatus.communicationStatus==0 }">正常</c:if>
												<c:if test="${upsStatus.communicationStatus==1 }">UPS无响应</c:if>
												<c:if test="${upsStatus.communicationStatus==2 }">UPS未识别</c:if>
											</td>
											<td class="title">放电模式:</td>
											<td><c:if test="${upsStatus.dischargePatterns==0 }">不支持</c:if>
												<c:if test="${upsStatus.dischargePatterns==1 }">UPS内置</c:if>
												<c:if test="${upsStatus.dischargePatterns==2 }">电柜</c:if></td>
										</tr>
										<tr>
											<td class="title">UPS类型:</td>
											<td><c:if test="${upsStatus.upsType==0 }">未知</c:if> <c:if
													test="${upsStatus.upsType==1 }">2相</c:if> <c:if
													test="${upsStatus.upsType==2 }">三进三出</c:if> <c:if
													test="${upsStatus.upsType==3 }">三进一</c:if></td>
											<td class="title">UPS型号:</td>
											<td>${upsStatus.modelNumber!=null?upsStatus.modelNumber:"未知" }</td>
											<td class="title">厂商品牌:</td>
											<td>${upsStatus.brand!=null?upsStatus.brand:"未知" }</td>
											<td class="title">版本号:</td>
											<td>${upsStatus.versionNumber!=null?upsStatus.versionNumber:"未知" }</td>

										</tr>
										<tr>
											<td class="title">额定电压:</td>
											<td>${upsStatus.rateVoltage}V</td>
											<td class="title">额定电流:</td>
											<td>${upsStatus.ratedCurrent}A</td>
											<td class="title">电池电压:</td>
											<td>${upsStatus.batteryVoltage}V</td>
											<td class="title">额定频率:</td>
											<td>${upsStatus.ratedFrequency}Hz</td>

										</tr>
										<tr>
											<td class="title">功率:</td>
											<td>${upsStatus.power}KVA</td>
											<td class="title">UPS状态:</td>
											<td><c:if test="${upsStatus.upsStatus==1 }">
													<span style="color: red;">故障</span>
												</c:if> <c:if test="${upsStatus.runningStatus!=1 }">
													<span style="color: green;">正常</span>
												</c:if></td>
											<td class="title">*频率:</td>
											<td>${upsStatus.frequency}Hz</td>
											<td class="title">机内温度:</td>
											<td>${upsStatus.internalTemperature}℃</td>

										</tr>
										<tr>
											<td class="title">*旁路电压:</td>
											<td>${upsStatus.bypassVoltage }</td>
											<td class="title">旁路频率:</td>
											<td>${upsStatus.bypassFrequency}Hz</td>
											<td class="title">输入电压:</td>
											<td>${upsStatus.inputVoltage}</td>
											<td class="title">输出电压:</td>
											<td>${upsStatus.outputVoltage}</td>

										</tr>
										<tr>
											<td class="title">故障电压:</td>
											<td>${upsStatus.errorVoltage }V</td>
											<td class="title">负载:</td>
											<td>${upsStatus.upsLoad}</td>
											<td class="title">*输出频率:</td>
											<td>${upsStatus.outputFrenquency}Hz</td>
											<td class="title">单节电压:</td>
											<td>${upsStatus.singleVoltage}V</td>

										</tr>
										<tr>
											<td class="title">*总电压:</td>
											<td>${upsStatus.totalVoltage }V</td>
											<td class="title">充电量:</td>
											<td>${upsStatus.electricQuantity}%</td>
											<td class="title">*充/放电电流:</td>
											<td>${upsStatus.passCurrent}A</td>
											<td class="title">*剩余时间:</td>
											<td>${upsStatus.remainingTime}分钟</td>

										</tr>
										<tr>
											<td class="title">市电电压:</td>
											<td><c:if test="${upsStatus.cityVoltageStatus==1 }">
													<span style="color: red;">异常</span>
												</c:if> <c:if test="${upsStatus.cityVoltageStatus!=1 }">
													<span style="color: green;">正常</span>
												</c:if></td>
											<td class="title">电池电压:</td>
											<td><c:if test="${upsStatus.batteryVoltageStatus==1 }">
													<span style="color: red;">低</span>
												</c:if> <c:if test="${upsStatus.batteryVoltageStatus!=1 }">
													<span style="color: green;">正常</span>
												</c:if></td>
											<td class="title">运行状态:</td>
											<td><c:if test="${upsStatus.runningStatus==1 }">
													<span style="color: red;">旁路</span>
												</c:if> <c:if test="${upsStatus.runningStatus!=1 }">
													<span style="color: green;">正常</span>
												</c:if></td>
											<td class="title">UPS模式:</td>
											<td><c:if test="${upsStatus.patternsStatus==1 }">
													<span style="color: green;">后备式</span>
												</c:if> <c:if test="${upsStatus.patternsStatus==0 }">
													<span style="color: green;">在线式</span>
												</c:if></td>

										</tr>

										<tr>

											<td class="title">是否测试:</td>
											<td><c:if test="${upsStatus.testStatus==1 }">
													<span style="color: green;">测试中</span>
												</c:if> <c:if test="${upsStatus.testStatus!=1 }">
													<span style="color: green;">未测试</span>
												</c:if></td>
											<td class="title">关机状态:</td>
											<td><c:if test="${upsStatus.shutdownStatus==1 }">
													<span style="color: green;">已关机</span>
												</c:if> <c:if test="${upsStatus.shutdownStatus!=1 }">
													<span style="color: green;">未关机</span>
												</c:if></td>
											<td class="title">蜂鸣器</td>
											<td colspan="3"><c:if
													test="${upsStatus.buzzerStatus==1 }">
													<span style="color: green;">开</span>
												</c:if> <c:if test="${upsStatus.buzzerStatus!=1 }">
													<span style="color: green;">关</span>
												</c:if></td>

										</tr>

										<%-- <tr>
										<td class="title">获取时间:</td>
										<td colspan="7"><fmt:formatDate
												value="${upsStatus.collectTime }"
												pattern="yyyy-MM-dd HH:mm:ss" /></td>


									</tr> --%>
									</table>
								</fieldset>
							</div>
						</c:forEach>
					</fieldset>
				</c:if>
			</div>

		</div>
		<div title="历史状态" data-options='refreshable:false' tabWidth="100"
			iconCls="icon-berlin-delicious">
			<div class="easyui-layout" data-options="fit:true">
				<div data-options="region:'north',border:false" style="height: 32px">
					<div class="easyui-tabs" id="tab_device"
						data-options="refreshable:false,tabPosition : 'top',fit:true"
						style="height: 100%">
						<c:forEach items="${upsStatusList }" var="upsStatus">

							<div title="【UPS-${upsStatus.deviceIndex }】历史记录"
								id="${upsStatus.deviceIndex  }" iconCls=""
								data-options='refreshable:false' onclick="select()"></div>
						</c:forEach>
					</div>

				</div>
				<div data-options="region:'center',border:false">
					<div id="tb">
						<div>
							<shiro:hasPermission name="sys:upsStatus:delete">
								<a href="javascript:void(0)" class="easyui-linkbutton"
									iconCls="icon-remove" plain="true"
									data-options="disabled:false" onclick="del()">删除</a>
							</shiro:hasPermission>
						</div>
					</div>
					<div id="dg"></div>
					<div id="dlg"></div>
				</div>
			</div>


		</div>
	</div>

</body>
</html>