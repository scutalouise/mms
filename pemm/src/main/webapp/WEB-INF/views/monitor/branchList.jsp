<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网点列表</title>
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
	src="${ctx }/static/js/monitor/branchList.js">
	
</script>
<script type="text/javascript">
$(function(){
	$(".organizationCheckBox").click(function(){
		var a=$(this)[0];
		ids = new Array();
	$("input[name='organizationCheckBox']:checked").each(function(i) {
		ids[i] = $(this).val();
	});
	if (ids.length > 0) {
		$("#branch_center_content").width($(window).width() - 400);
		$("#deviceList").fadeIn();

	} else {
		$("#branch_center_content").width($(window).width());
		$("#deviceList").fadeOut();
	}
    
	if (a.checked == false) {
		$("#deviceList").children().remove("#device_" + a.value);
	} else {

		loadDeviceList(a.value);
	}

	});
});
</script>
</head>
<body>
	<c:if test="${fn:length(areaInfoList)<=0}">
		<hr width="98%" style="margin: 0px 0px 20px 0px">
		<div style="text-align: center; font: left;">没有相应网点</div>

	</c:if>
	<c:forEach items="${areaInfoList }" var="areaInfo">
		<c:if
			test="${fn:length(areaInfo.goodOrganizations)>0||fn:length(areaInfo.warningOrganizations)>0 ||fn:length(areaInfo.errorOrganizations)>0 }">
			<hr width="98%" style="margin: 0px 0px 20px 0px">
			<table style="width: 100%;">
				<tr>
					<td style="width: 280px" valign="top">
						<div class="content_area">
							<div class="areaIcon"></div>
							<div class="areaName">${areaInfo.areaName }</div>
						</div>
					</td>
					<td>

						<div class="content_organization">
							<c:if test="${fn:length(areaInfo.goodOrganizations)>0 }">
								<div class="branch_div">
									<div class="branch_good">正常</div>
									<div class="branch_view">
										<c:forEach items="${areaInfo.goodOrganizations }"
											var="organization">
											<div class="organization">
												<input type="checkbox" name="organizationCheckBox"
													value="${organization.id }" class="organizationCheckBox"
													style="float: left; margin-top: 9px;">
												<div class="organizationName">网点名称：<a class="organization_a" href="javascript:void(0)" title="${organization.orgName }">
												<c:if test="${fn:length(organization.orgName)>10}">
												${fn:substring(organization.orgName,0,10)}...
												</c:if>
												<c:if test="${fn:length(organization.orgName)<=10}">
												${organization.orgName }
												</c:if></a>
												</div>
											</div>
										</c:forEach>
										<c:if test="${fn:length(areaInfo.goodOrganizations )==0}">
											<div
												style="line-height: 72px; color: #CCCCCE; font-size: 18px; font-weight: 800">无网点</div>
										</c:if>
									</div>
								</div>
								<hr class="organization_hr">
							</c:if>
							<c:if test="${fn:length(areaInfo.warningOrganizations)>0 }">
								<div class="branch_div">
									<div class="branch_warning">报警</div>
									<div class="branch_view">
										<c:forEach items="${areaInfo.warningOrganizations }"
											var="organization">
											<div class="organization">
												<input type="checkbox" name="organizationCheckBox"
													value="${organization.id }" class="organizationCheckBox"
													style="float: left; margin-top: 9px;">
												<div style="float: left;">网点名称：<a class="organization_a" href="javascript:void(0)" title="${organization.orgName }">
												<c:if test="${fn:length(organization.orgName)>10}">
												${fn:substring(organization.orgName,0,10)}...
												</c:if>
												<c:if test="${fn:length(organization.orgName)<=10}">
												${organization.orgName }
												</c:if></a></div>
											</div>
										</c:forEach>
										<c:if test="${fn:length(areaInfo.warningOrganizations )==0}">
											<div
												style="line-height: 72px; color: #CCCCCE; font-size: 18px; font-weight: 800">无网点</div>
										</c:if>
									</div>
								</div>
								<hr class="organization_hr">
							</c:if>
							<c:if test="${fn:length(areaInfo.errorOrganizations)>0 }">
								<div class="branch_div">
									<div class="branch_error">异常</div>
									<div class="branch_view">
										<c:forEach items="${areaInfo.errorOrganizations }"
											var="organization">
											<div class="organization">
												<input type="checkbox" name="organizationCheckBox"
													value="${organization.id }" class="organizationCheckBox"
													style="float: left; margin-top: 9px;">
												<div class="organizationName">网点名称：<a class="organization_a" href="javascript:void(0)" title="${organization.orgName }">
												<c:if test="${fn:length(organization.orgName)>10}">
												${fn:substring(organization.orgName,0,10)}...
												</c:if>
												<c:if test="${fn:length(organization.orgName)<=10}">
												${organization.orgName }
												</c:if></a></div>
											</div>
										</c:forEach>
										<c:if test="${fn:length(areaInfo.errorOrganizations )==0}">
											<div
												style="line-height: 72px; color: #CCCCCE; font-size: 18px; font-weight: 800">无网点</div>
										</c:if>
									</div>
								</div>
							</c:if>

						</div>
					</td>
				</tr>

			</table>

		</c:if>

		<c:if
			test="${fn:length(areaInfo.goodOrganizations)<=0 &&fn:length(areaInfo.warningOrganizations)<=0 &&fn:length(areaInfo.errorOrganizations)<=0 }">
			<hr width="98%" style="margin: 0px 0px 20px 0px">
			<div style="text-align: center; font: left;">没有相应网点</div>
		</c:if>

	</c:forEach>
</body>
</html