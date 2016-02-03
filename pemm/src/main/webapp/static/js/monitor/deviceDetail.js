function refresh() {

	$.ajax({
				type : "post",
				url : ctx + "/upsStatus/getLatestData",
				data : {
					gitInfoId : gitIdStr
				},
				success : function(data) {
					var gitInfo=data.gitInfo;
					if(gitInfo.serverState=="connect"){
						$("#unconnect").hide();
						$("#connect").show();
					var upsData = data.upsStatusList;
					var thData=data.thStatusList;
					var warterData=data.waterStatusList;
					var smokeData=data.smokeStatusList;
					for (var i = 0; i < upsData.length; i++) {
						var inputVoltageHtml = "";
						var bypassVoltageHtml = "";
						var outputVoltageHtml = "";
						var outputFrenquencyHtml = "";
						var upsLoadHtml = "";
						var frequencyHtml="";
						var inputVoltages = upsData[i].inputVoltage.split("/");

						if (upsData[i].upsType == 2 || upsData[i].upsType == 3) {
							inputVoltageHtml += ("A相输入电压：" + inputVoltages[0] + "<br>");
							inputVoltageHtml += ("B相输入电压：" + inputVoltages[1] + "<br>");
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
								var upsLoads = upsData[i].upsLoad.split("/");
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
								bypassVoltageHtml = "旁路电压："
										+ upsData[i].bypassVoltage.split("/")[0]
							} else {
								bypassVoltageHtml = "旁路电压：0.0V"
							}
							if (upsData[i].outputVoltage != null) {
								outputVoltageHtml = "输出电压："
										+ upsData[i].outputVoltage.split("/")[0]
							} else {
								outputVoltageHtml = "输出电压：0.0V"
							}
							
							if (upsData[i].upsLoad != null) {

								upsLoadHtml = ("负载："
										+ upsData[i].upsLoad.split("/")[0] + "<br>");

							} else {
								upsLoadHtml = "负载：0%<br>";

							}
							inputVoltageHtml = "输入电压：" + inputVoltages[0];
						}
						$("#remainingTime_"+i).html(upsData[i].remainingTime);
						$("#frequency_"+i).html(parseFloat(upsData[i].frequency).toFixed(1));
						$("#inputVoltage_" + i).html(inputVoltageHtml);
						$("#bypassVoltage_" + i).html(bypassVoltageHtml);
						$("#outputVoltage_" + i).html(outputVoltageHtml);
						$("#outputFrenquency_" + i).html(parseFloat(upsData[i].outputFrenquency).toFixed(1));
						$("#upsLoad_" + i).html(upsLoadHtml);
						$("#upsInfo_" + i).html(parseFloat(upsData[i].internalTemperature).toFixed(1));
						// 额定电流
						$("#ratedCurrent_" + i).html(parseFloat(upsData[i].ratedCurrent).toFixed(1));
						// 电池电量
						$("#electricQuantity_" + i).html(
								upsData[i].electricQuantity);
						$("#batteryVoltage_"+i).html(upsData[i].batteryVoltage);
						// 电池状态
						/*var batteryVoltageStatusHtml = "";
						if (upsData[i].batteryVoltageStatus == 1) {
							batteryVoltageStatusHtml = '<span style="color: red;">低</span>';
						} else {
							batteryVoltageStatusHtml = '<span style="color: white;">正常</span>';
						}
						$("#batteryVoltageStatus_" + i).html(
								batteryVoltageStatusHtml);*/
						// UPS状态
						var currentStateHtml = "";
						if (upsData[i].currentState != 0) {
							currentStateHtml = '<span style="color: red;">异常</span>';
						} else {
							currentStateHtml = '<span style="color: white;">正常</span>';
						}
						$("#currentState_" + i).html(currentStateHtml);
						// 运行状态
						var runningStatusHtml = "";
						if (upsData[i].runningStatus == 1) {
							runningStatusHtml = '<span style="color: red;">旁路</span>';
						} else {
							runningStatusHtml = '<span style="color: white;">正常</span>';
						}
						$("#runningStatus_" + i).html(runningStatusHtml);

					}
					for (var i = 0; i <thData.length; i++) {
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
						
						var humidityHtml="<span"
						if(humidityState=="error"){
								humidityHtml+=" style='color:red'";
						}if(humidityState=="warning"){
								humidityHtml+=" style='color:yellow'";
						}
						humidityHtml+=">"+humidity+"%</span>";
						$("#temperature_" + i).html(temperatureHtml);
						$("#humidity_" + i).html(humidityHtml);
					}
					for(var i=0;i<warterData.length;i++){
						var waterStatus=warterData[i].currentState;
						$("#waterStatus_"+i).html(waterStatus==0?"正常":"<span style='color:red;'>异常</span>");
					}
					for(var i=0;i<smokeData.length;i++){
						var smokeStatus=smokeData[i].currentState;
						$("#smokeStatus_"+i).html(smokeStatus==0?"正常":"<span style='color:red;'>异常</span>");
					}
					
					}else{
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
		url : ctx + "/system/monitor/closeAlarm",
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
			url:ctx+"/system/dischargeTask/discharge",
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
function acOperation(ip,index,command){
	$.ajax({
		method:"post",
		url:ctx+"/system/monitor/closeOrOpenOfAc",
		data:{ip:ip,index:index,command:command},
		success:function(data){
			if(data){
				alert("操作成功!");
			}
		}
	})
}
function acCloseOrOpen(ip,index,command){
	if(command==1){
	var r=confirm("确定要关闭空调吗?")
	if(r){
		acOperation(ip,index,command);
	}
	}else{
		acOperation(ip,index,command);
	}
}
