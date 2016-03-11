<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<%@include file="/WEB-INF/views/include/easyui.jsp" %>
</head>
<body>
	<table class="tbform">
         <tr>
            <td class="tdl">名称类型：</td>
            <td class="td_detail">
              ${problem.problemType }
            </td>           
			<td class="tdl">问题编号：</td>
			<td class="td_detail">
				${problem.problemCode }
			</td>
         </tr>
         
         <tr>
			<td class="tdl">设备名称：</td>
			<td class="td_detail">
				${problem.deviceName }
			</td>
			<td class="tdl">设备编号：</td>
			<td class="td_detail">
				${problem.identifier }
			</td>
		</tr>
		<tr>
			<td class="tdl">所属网点：</td>
			<td class="td_detail">
				${problem.orgName }
			</td>
			<td class="tdl">登记人：</td>
			<td class="td_detail">
				${problem.recordUserName }
			</td>
		</tr>
		<tr>
			<td class="tdl">登记时间：</td>
			<td class="td_detail" id="recordTime">
				${problem.recordTime }
			</td>
			<td class="tdl">上报人：</td>
			<td class="td_detail">
				${problem.reportUser }
			</td>
		</tr>
		<tr>
			<td class="tdl">上报渠道：</td>
			<td class="td_detail">
				${problem.reportWay.text }
			</td>
			<td class="tdl">上报人联系方式：</td>
			<td class="td_detail">
				${problem.reportUserContact }
			</td>
		</tr>
		<tr>
			<td class="tdl">解决人：</td>
			<td class="td_detail">
				${problem.resolveUserName }
			</td>
			<td class="tdl">解决时间：</td>
			<td class="td_detail" id="resolveTime">
				${problem.resolveTime }
			</td>
		</tr>
		<tr>
			<td class="tdl">问题评分：</td>
			<td class="td_detail">
				<div id="stars"></div>
			</td>
			<td class="tdl">是否加入知识库：</td>
			<td class="td_detail" id="knowledge">
				
			</td>
		</tr>
		<tr>
			<td class="tdl">响应状态：</td>
			<td class="td_detail" id="response">
				
			</td>
			<td class="tdl">问题状态：</td>
			<td class="td_detail">
				${problem.enable.text }
			</td>
		</tr>
		<tr>
			<td class="tdl">描述：</td>
			<td colspan="3" class="td_detail">
				${problem.description }
			</td>
		</tr>
         
         
      </table>
<script type="text/javascript">
	$(function(){
		var message = "${message}";
		if (message == "failure") {
			$("body").html("<h1>数据获取异常！</h1>");
		} else if (message == "success"){
			var recordTime = "${problem.recordTime }";
			var resolveTime = "${problem.resolveTime }";
			var knowledge = "${problem.enableKnowledge }";
			var response = "${problem.responsed }";
			var score = "${problem.score }";
			if (recordTime != null && recordTime != "") {
				$("#recordTime").html(recordTime.substring(0,recordTime.length - 2));
			}
			if (resolveTime != null && resolveTime != "") {
				$("#resolveTime").html(resolveTime.substring(0,recordTime.length - 2));
			}
			if (knowledge == "true" || knowledge == true) {
				$("#knowledge").html("是");
			} else {
				$("#knowledge").html("否");
			}
			if (response == "true" || response == true) {
				$("#response").html("是");
			} else {
				$("#response").html("否");
			}
			$("#stars").raty({
				score : score,
				readOnly : true,
				path : "/itam/static/plugins/raty/img",
				hints : ["很不满意","不满意","一般","满意","很满意"]
			});
		} else {
			alert(message);
		}
	});
</script>
</body>
</html>