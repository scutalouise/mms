<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>放电计划</title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js"
	type="text/javascript"></script>

</head>
<body>
	<div>
		<form id="mainform" style="width: 100%;"
			action="${ctx}/system/dischargeTask/${action}" method="post">
			<table class="formTable" cellpadding="3"
				style="width: 100%; padding: 10 0 10 0">
				<tr>
					<td width="51">计划名称</td>
					<td colspan="3"><input type="hidden" id="type" name="type"><input type="text" name="name" id="name"
						class="easyui-validatebox" data-options="required:true,validType:['validScheduleName[\'upsDischargeJob\',\'type\']','maxLength[20]']"
						style="width: 402px;" /></td>

				</tr>
				<tr>
					<!-- <td>执行频率:</td>
				<td><select name="frequency" id="frequency"
					style="width: 158px;">
						<option value="year">每年执行</option>
						<option value="month">每月执行</option>

						<option value="week">每周执行</option>
						<option value="day">每天执行</option>
				</select></td> -->
					<td>按年执行</td>
					<td><select id="year_select" style="width: 158px;">
							<option value="0">不指定</option>
							<option value="1">指定年份</option>
					</select></td>
					<td id="year_select_label" width="51">执行年份</td>
					<td id="year_select_input"><input class="easyui-my97"
						id="startYear" name="startYear" required="true" datefmt="yyyy"
						style="width: 72px;">&nbsp;-&nbsp;<input id="endYear"
						name="endYear" class="easyui-my97" required="true" datefmt="yyyy"
						style="width: 72px;"></td>

				</tr>
				<tr>
					<td>按月执行</td>
					<td colspan="3"><select id="month_select"
						style="width: 158px;">
							<option value="0">不指定</option>
							<option value="1">指定月份</option>
					</select></td>
				</tr>
				<tr id="month_select_tr">
					<td>选择月份</td>
					<td colspan="3"><input type="checkbox" name="month" value="1">1
						<input type="checkbox" name="month" value="2">2 <input
						type="checkbox" name="month" value="3">3 <input
						type="checkbox" name="month" value="4">4 <input
						type="checkbox" name="month" value="5">5 <input
						type="checkbox" name="month" value="6">6 <input
						type="checkbox" name="month" value="7">7 <input
						type="checkbox" name="month" value="8">8 <input
						type="checkbox" name="month" value="9">9 <input
						type="checkbox" name="month" value="10">10 <input
						type="checkbox" name="month" value="11">11 <input
						type="checkbox" name="month" value="12">12</td>
				</tr>
				<tr>
				<tr>
					<td>执行方式</td>
					<td><select id="type_select" style="width: 158px;"><option
								value="0">按日执行</option>
							<option value="1">按周执行</option></select></td>
					<td id="select_type">按日执行</td>
					<td>
						<div id="week_select_div">
							<select id="week_select" style="width: 158px;">
								<option value="0">不指定</option>
								<option value="1">指定星期</option>
							</select>
						</div>
						<div id="day_select_div">
							<select id="day_select" style="width: 158px;">
								<option value="0">不指定</option>
								<option value="1">指定天</option>
							</select>
						</div>
					</td>
				</tr>
				<tr id="week_select_tr">
					<td>选择星期</td>
					<td colspan="3"><input type="checkbox" name="week" value="1">星期一
						<input type="checkbox" name="week" value="2">星期二 <input
						type="checkbox" name="week" value="3">星期三 <input
						type="checkbox" name="week" value="4">星期四 <input
						type="checkbox" name="week" value="5">星期五 <input
						type="checkbox" name="week" value="6">星期六 <input
						type="checkbox" name="week" value="7">星期日</td>
				</tr>
				</tr>

				<tr id="day_select_tr">
					<td>选择天</td>
					<td colspan="3"><input type="checkbox" name="day" value="1">01
						<input type="checkbox" name="day" value="2">02 <input
						type="checkbox" name="day" value="3">03 <input
						type="checkbox" name="day" value="4">04 <input
						type="checkbox" name="day" value="5">05 <input
						type="checkbox" name="day" value="6">06 <input
						type="checkbox" name="day" value="7">07 <input
						type="checkbox" name="day" value="8">08 <input
						type="checkbox" name="day" value="9">09 <input
						type="checkbox" name="day" value="10">10 <input
						type="checkbox" name="day" value="11">11<br> <input
						type="checkbox" name="day" value="12">12 <input
						type="checkbox" name="day" value="13">13 <input
						type="checkbox" name="day" value="14">14 <input
						type="checkbox" name="day" value="15">15 <input
						type="checkbox" name="day" value="16">16 <input
						type="checkbox" name="day" value="17">17 <input
						type="checkbox" name="day" value="18">18 <input
						type="checkbox" name="day" value="19">19 <input
						type="checkbox" name="day" value="20">20 <input
						type="checkbox" name="day" value="21">21 <input
						type="checkbox" name="day" value="22">22<br> <input
						type="checkbox" name="day" value="23">23 <input
						type="checkbox" name="day" value="24">24 <input
						type="checkbox" name="day" value="25">25 <input
						type="checkbox" name="day" value="26">26 <input
						type="checkbox" name="day" value="27">27 <input
						type="checkbox" name="day" value="28">28 <input
						type="checkbox" name="day" value="29">29 <input
						type="checkbox" name="day" value="30">30 <input
						type="checkbox" name="day" value="31">31</td>
				</tr>
				<tr>
					<td>执行时间</td>
					<td colspan="3"><input id="time" data-options="required:true"
						style="width: 158px;" value="00:00"></td>
				</tr>
				<tr>
					<td>计划说明</td>
					<td colspan="3"><input type="hidden" name="cronExpression"
						id="cron"> <textarea name="description" id="description"
							class="easyui-validatebox" style="height: 60px; width: 402px;" />
					</td>
				</tr>
			</table>

		</form>
	</div>
	<script type="text/javascript">
		var weekLabel = [ "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日" ];
		var dialog_window = $("#dlgContent").parent();

		var dialog_window_shadow = dialog_window.next(".window-shadow");
		function dialogTop() {
			var top = ($(window).height() - $("#dlgContent").parent().height()) / 2;
			dialog_window.css("top", top);
			dialog_window_shadow.css("top", top);

		}
		function getDescription() {
			var description = "计划任务:";
			var year_select = $("#year_select").combobox("getValue");
			var startYear = $("#startYear").my97("getValue");
			var endYear = $("#endYear").my97("getValue");
			var month_select = $("#month_select").combobox("getValue");
			var months = new Array();
			$("input[name='month']:checked").each(function(i) {
				months[i] = $(this).val();
			});
			//执行方式,按日还是按星期
			var type_select = $("#type_select").combobox("getValue");
			var day_select = $("#day_select").combobox("getValue");
			var days = new Array();
			$("input[name='day']:checked").each(function(i) {
				days[i] = $(this).val();
			});
			var week_select = $("#week_select").combobox("getValue");
			var weeks = new Array();
			$("input[name='week']:checked").each(function(i) {
				weeks[i] = $(this).val();
			});
			var time = $("#time").timespinner("getValue");
			var hours = $("#time").timespinner("getHours");
			var minutes = $("#time").timespinner("getMinutes");
			var seconds = $("#time").timespinner("getSeconds");
			var cron = "";
			var cronYear = "*";
			var cronMonth = "*";
			var cronWeek = "*";
			var cronDay = "*";
			var cronHours = "*";
			var cronMinute = "*";
			var cronSeconds = "0";
			if (year_select == 1) {
				if (startYear != "" && endYear != "") {
					$("#startYear").my97('reduce');
					$("#endYear").my97('reduce');
					description += (startYear + "年-" + endYear + "年");
					cronYear = startYear + "-" + endYear;
				}

			} else {
				cronYear = "*"
			}
			if (month_select == 1 && months.length<12&&months.length>0) {
				if (year_select == 0) {
					description += "每年";
				}
				description += months + "月";
				cronMonth = months;
			} else {
				if (day_select != 0 && week_select != 0) {
					description += "每月";
				}
				cronMonth = "*";
			}
			if (type_select == 0) {

				if (day_select == 1 && days.length<31&&days.length>0) {
					if (month_select == 0) {
						description += "每月";
					}
					description += days + "日";
					cronDay = days;
				} else {
					cronDay = "*";
				}
				cronWeek = "?"
			} else {

				if (week_select == 1 && weeks.length<7&&weeks.length>0) {
					description += "每周";
					for (var i = 0; i < weeks.length; i++) {
						description += weekLabel[i] + ",";
					}
					description = description.substring(0,
							description.length - 1);
					cronWeek = weeks;
				} else {
					cronWeek = "*";
				}

				cronDay = "?"
			}
			if (day_select == 0 && week_select == 0) {
				description += "每日"
			}
			if (minutes == 0 && seconds == 0) {
				description += (hours + "时");
				cronHours = hours;
			} else if (seconds == 0) {
				description += (hours + "时" + minutes + "分");

			}
			cronHours = hours;
			cronMinute = minutes;
			cron = (cronSeconds + " " + cronMinute + " " + cronHours + " "
					+ cronDay + " " + cronMonth + " " + cronWeek + " " + cronYear);
			$("#cron").val(cron);
			description += "开始放电";

			return description;
		}
		$("#time").timespinner({
			showSeconds : false,
			editable : true,
			onChange : function(v) {
			
				if(v==""){
					$("#time").timespinner("setValue","00:00");
				}
				setDescription();
			}
		});

		$("#frequency").combobox({
			panelHeight : 90,
			onSelect : function(v) {

			}
		});
		$("#year_select").combobox({
			panelHeight : 50,
			onSelect : function(v) {

				if (v.value == 0) {
					$("#year_select_label").hide();
					$("#year_select_input").hide()

					$("#startYear").my97("setValue", "");
					$("#endYear").my97("setValue", "");
					setDescription();

					$("#startYear").my97('remove'); //删除校验

					$("#endYear").my97('remove'); //删除校验

				} else {

					$("#startYear").my97('reduce');
					$("#endYear").my97('reduce');
					$("#year_select_label").show();
					$("#year_select_input").show();

				}
			}
		});

		$("#month_select").combobox({
			panelHeight : 50,
			onSelect : function(v) {
				if (v.value == 0) {
					$("#month_select_tr").hide();
					$("input[name='month']:checked").attr("checked", false);
					setDescription();

				} else {
					$("#month_select_tr").show();

				}
				dialogTop();
			}
		});
		$("#week_select").combobox({
			panelHeight : 50,
			onSelect : function(v) {
				if (v.value == 0) {
					$("#week_select_tr").hide();
					$("input[name='week']:checked").attr("checked", false);
					setDescription();
				} else {
					$("#week_select_tr").show();
				}
				dialogTop();
			}
		});
		$("#day_select").combobox({
			panelHeight : 50,
			onSelect : function(v) {
				if (v.value == 0) {
					$("#day_select_tr").hide();
					$("input[name='day']:checked").attr("checked", false);
					setDescription();
				} else {
					$("#day_select_tr").show();
				}
				dialogTop();
			}
		});
		$("#type_select").combobox({
			panelHeight : 50,
			onSelect : function(v) {
				$("#select_type").html(v.text);
				$("#week_select").combobox("setValue", 0);
				$("#day_select").combobox("setValue", 0);
				setDescription();
				if (v.value == 0) {
					$("#day_select_div").show();
					$("#week_select_div").hide();
					$("#week_select_tr").hide();
					$("input[name='week']:checked").attr("checked", false);
				} else {
					$("#day_select_div").hide();
					$("#week_select_div").show();
					$("#day_select_tr").hide();
					$("input[name='day']:checked").attr("checked", false);
				}
			}
		});
		function initSelectYearFilter(beginDate, endDate) {
			$("#" + beginDate).my97({

				onShowPanel : function() {
					var end_Date = $("#" + endDate).my97("getValue");
					if (end_Date.length > 0) {
						$("#" + beginDate).my97({
							maxDate : end_Date
						});
					}

				},
				onHidePanel : function() {
					var begin_Date = $("#" + beginDate).my97("getValue");

					if (begin_Date.length > 0) {
						$("#" + endDate).my97({
							minDate : begin_Date
						});
					}
					var end_Date = $("#" + endDate).my97("getValue");
					if (begin_Date.length > 0 && end_Date.length > 0) {
						setDescription()
					}
				}

			});
			$("#" + endDate).my97({

				onHidePanel : function() {
					var end_Date = $("#" + endDate).my97("getValue");

					if (end_Date.length > 0) {
						$("#" + beginDate).my97({
							maxDate : end_Date
						});
					}
					var begin_Date = $("#" + beginDate).my97("getValue");
					if (begin_Date.length > 0 && end_Date.length > 0) {
						setDescription()
					}
				}

			});

		}
		function setDescription() {

			var description = getDescription();

			$("#description").text(description);
		}
		$(function() {

			var action = "${action}";
			$("#type").val(action);
			$("input[name='month']").click(function() {
				setDescription();
			});
			$("input[name='week']").click(function() {
				setDescription();
			});
			$("input[name='day']").click(function() {
				setDescription();
			});
			initSelectYearFilter("startYear", "endYear");
			if (action == "add") {

				$("#year_select_label").hide();
				$("#year_select_input").hide();
				$("#month_select_tr").hide();
				$("#week_select_tr").hide();
				$("#day_select_tr").hide();
				$("#day_select_div").show();
				$("#week_select_div").hide();
				$("#select_type").html("按日执行");

				
				setDescription();
				$("#startYear").my97('remove'); //删除校验

				$("#endYear").my97('remove'); //删除校验
			} else if (action == "update") {
			
				$("#name").attr('readonly','readonly');
				$("#name").css('background','#eee');
				$("#name").val(selectDischargeTask.name);

				$("#description").val(selectDischargeTask.description);
				var cronExpression = selectDischargeTask.cronExpression;
				var crons = cronExpression.split(" ");
				//处理年展示
				if (crons[6] == "*") {
					$("#year_select_label").hide();
					$("#year_select_input").hide();
					$("#year_select").combobox("setValue", 0);
					$("#startYear").my97('remove'); //删除校验

					$("#endYear").my97('remove'); //删除校验
				} else {
					$("#startYear").my97('reduce');
					$("#endYear").my97('reduce');
					$("#year_select_label").show();
					$("#year_select_input").show();
					$("#year_select").combobox("setValue", 1);
					var years = crons[6].split("-");

					$("#startYear").my97("setValue", years[0]);
					$("#endYear").my97("setValue", years[1]);
				}
				if (crons[5] == "?") {
					$("#type_select").combobox("setValue", 0);
					$("#week_select_div").hide();
					$("#day_select_div").show();

					$("#week_select_tr").hide();
					if (crons[3] == "*") {
						$("#day_select").combobox("setValue", 0);
						$("#day_select_tr").hide();
					} else {
						$("#day_select").combobox("setValue", 1);
						var checkedDays = crons[3].split(",");
						for (var i = 0; i < checkedDays.length; i++) {
							$(
									"input[name='day'][value='"
											+ checkedDays[i] + "']").attr(
									"checked", true);
						}
					}
				} else {
					$("#type_select").combobox("setValue", 1);
					$("#week_select").combobox("setValue", 1)
					$("#week_select_div").show();
					$("#day_select_div").hide();
					$("#day_select_tr").hide();
					$("#week_select_tr").show();
					var checkedWeeks = crons[5].split(",");
					for (var i = 0; i < checkedWeeks.length; i++) {
						$("input[name='week'][value='" + checkedWeeks[i] + "']")
								.attr("checked", true);
					}
				}
				//处理月展示
				if (crons[4] == "*") {
					$("#month_select").combobox("setValue", 0);
					$("#month_select_tr").hide();
				} else {
					$("#month_select").combobox("setValue", 1);

					var checkedMonths = crons[4].split(",");
					for (var i = 0; i < checkedMonths.length; i++) {
						$(
								"input[name='month'][value='"
										+ checkedMonths[i] + "']").attr(
								"checked", true);
					}

				}
				$("#time").timespinner("setValue", crons[2] + ":" + crons[1]);

			}

		});

		//提交表单
		$('#mainform').form({
			onSubmit : function() {
				setDescription();
				var isValid = $(this).form('validate');

				return isValid; // 返回false终止表单提交
			},
			success : function(data) {
				successTip(data, dg, d);
			}
		});
	</script>
</body>
</html>