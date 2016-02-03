<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="upsIndexs" value=""/>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设备信息列表</title>
<script src="${ctx }/static/plugins/easyui/jquery/jquery-2.1.1.min.js"></script>
<link href="${ctx }/static/css/monitor/deviceInformationList.css"
	rel="stylesheet">
<script type="text/javascript"
	src="${ctx }/static/plugins/bootstrap/js/bootstrap.min.js"></script>
	   <link rel="apple-touch-icon" href="../bootstrap/twitter-bootstrap-v2/docs/examples/images/apple-touch-icon.png">
<link rel="stylesheet"
	href="${ctx }/static/plugins/bootstrap/css/bootstrap.min.css">
<script type="text/javascript"
	src="${ctx }/static/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript"
	src="${ctx }/static/plugins/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>

<link rel="stylesheet"
	href="${ctx }/static/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css">

<link rel="stylesheet"
	href="${ctx }/static/plugins/bootstrap-table/bootstrap-table.css">
<script type="text/javascript"
	src="${ctx }/static/plugins/bootstrap-table/bootstrap-table.js"></script>

<script type="text/javascript"
	src="${ctx }/static/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript">
	var ctx = "${ctx}";
	var gitInfoId="${gitInfoId}";
</script>
<script type="text/javascript" src="${ctx }/static/js/dateutils.js"></script>
<script type="text/javascript"
	src="${ctx }/static/js/monitor/deviceInformationList.js"></script>
</head>
<body>
	<div style="margin-left: 10px; margin-right: 10px;">
	 
	
		<div id="deviceTabs">
			<ul class="nav nav-tabs">
				<c:forEach items="${upsDeviceList }" var="upsDevice">
					<c:set var="upsIndex" value="${upsDevice.deviceIndex }"></c:set>

					<li><a href="#ups_main_${upsDevice.deviceIndex }" style="">UPS【${upsDevice.deviceIndex }】</a></li>
				</c:forEach>
				<c:forEach items="${thDeviceList }" var="thDevice">
				

					<li><a href="#th_main_${thDevice.deviceIndex }" style="">温湿度【${thDevice.deviceIndex }】</a></li>
				
				</c:forEach>
				
			</ul>
			
		</div>
		<div id="statusList_div">
		
        <div id="ups_statusList_div">
        <div id="upstoolbar">
            <div class="form-inline" role="form">
                <div class="form-group">
                  
                  
                    <span>查询日期：</span>
                <div class="input-group date form_date col-md-5" id="upsStartDate" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input2">
                    <input class="form-control" style="width: 100px;" id="upsStartDateInput" size="16" type="text" readonly>
                   
					<span class="input-group-addon"><span class="glyphicon glyphicon-calendar" ></span></span>
                </div>
				<input type="hidden" id="dtp_input2" value="" /><br/>
          
                </div>
                <div class="form-group">
                    <span>-</span>
                     <div class="input-group date form_date col-md-6" id="upsEndDate" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input3">
                    <input class="form-control" style="width: 100px;" id="upsEndDateInput" size="16" type="text" readonly>
                   
					<span class="input-group-addon"><span class="glyphicon glyphicon-calendar" ></span></span>
                </div>
				<input type="hidden" id="dtp_input3" value="" /><br/>
                </div>
                
                <button onclick="searchUpsStatus()" type="button" class="btn btn-default"><i class="glyphicon glyphicon-search"></i>查询</button>
            <button id="exportExcel" onclick="exportUpsStatusToExcel()" type="button" class="btn btn-default"><i class="glyphicon glyphicon-arrow-right"></i>导出Excel</button>
            
            </div>
        </div>
		<table id="ups_statusList" >

		</table>
		</div>
		<div id="th_statusList_div">
		<div id="thtoolbar">
            <div class="form-inline" role="form">
                <div class="form-group">
                  
                  
                    <span>查询日期：</span>
                <div class="input-group date form_date col-md-5" id="thStartDate" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input4">
                    <input class="form-control" style="width: 100px;" id="thStartDateInput" size="16" type="text" readonly>
                   
					<span class="input-group-addon"><span class="glyphicon glyphicon-calendar" ></span></span>
                </div>
				<input type="hidden" id="dtp_input4" value="" /><br/>
          
                </div>
                <div class="form-group">
                    <span>-</span>
                     <div class="input-group date form_date col-md-6" id="thEndDate" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input5">
                    <input class="form-control" style="width: 100px;" id="thEndDateInput" size="16" type="text" readonly>
                   
					<span class="input-group-addon"><span class="glyphicon glyphicon-calendar" ></span></span>
                </div>
				<input type="hidden" id="dtp_input5" value="" /><br/>
                </div>
                
                <button onclick="searchThStatus()" type="button" class="btn btn-default"><i class="glyphicon glyphicon-search"></i>查询</button>
            <button id="exportExcel" onclick="exportThStatusToExcel()" type="button" class="btn btn-default"><i class="glyphicon glyphicon-arrow-right"></i>导出Excel</button>
            
            </div>
        </div>
		<table id="th_statusList"></table>
		</div>
		</div>
	</div>

</body>
</html>