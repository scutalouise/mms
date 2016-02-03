<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

</head>
<body>

	<div class="listarea">
		<div class="grid" style="width: 100%">
			<div class="cell">
				<div class="accordion" data-role="accordion"
					data-close-any="true">
					<c:if test="${fn:length(areaInfoList)>0 }">
						<c:forEach items="${areaInfoList }" begin="0"
							end="${fn:length(areaInfoList)%2>0?(fn:length(areaInfoList))/2:(fn:length(areaInfoList)/2-1)}"
							var="areaInfo">
							<div class="frame">
								<div class="heading">${areaInfo.areaName }</div>
								<div class="content">
									<c:forEach items="${areaInfo.errorOrganizations }"
										var="errorOrganization">
										<a href="javascript:showDialog(${errorOrganization.id })"><div
												class="box widget1x1 widget_red">
												<img
													src="${ctx }/static/monitorPlatform/images/house170.png" /><br />
												<span>${errorOrganization.orgName }</span>
											</div></a>
									</c:forEach>
									<c:forEach items="${areaInfo.warningOrganizations }"
										var="warningOrganizations">
										<a href="javascript:showDialog(${warningOrganizations.id })"><div
												class="box widget1x1 widget_yellow">
												<img
													src="${ctx }/static/monitorPlatform/images/house170.png" /><br />
												<span>${warningOrganizations.orgName }</span>
											</div></a>
									</c:forEach>
									<c:forEach items="${areaInfo.goodOrganizations }"
										var="goodOrganization">
										<a href="javascript:showDialog(${goodOrganization.id })"><div
												class="box widget1x1 widget_green">
												<img
													src="${ctx }/static/monitorPlatform/images/house170.png" /><br />
												<span>${goodOrganization.orgName }</span>
											</div></a>
									</c:forEach>
								</div>
							</div>
						</c:forEach>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	<div class="listarea">
		<div class="grid" style="width: 100%">
			<div class="cell">
				<div class="accordion" data-role="accordion" data-close-any="true">
				
					<c:forEach items="${areaInfoList }"
						begin="${fn:length(areaInfoList)%2>0?(fn:length(areaInfoList))/2+1:(fn:length(areaInfoList)/2)}"
						var="areaInfo">
						<div class="frame">
							<div class="heading">${areaInfo.areaName }</div>
							<div class="content">
								<c:forEach items="${areaInfo.errorOrganizations }"
									var="errorOrganization">
									<a href="javascript:showDialog(${errorOrganization.id })"><div
											class="box widget1x1 widget_red">
											<img src="${ctx }/static/monitorPlatform/images/house170.png" /><br />
											<span>${errorOrganization.orgName }</span>
										</div></a>
								</c:forEach>
								<c:forEach items="${areaInfo.warningOrganizations }"
									var="warningOrganizations">
									<a href="javascript:showDialog(${warningOrganizations.id })"><div
											class="box widget1x1 widget_yellow">
											<img src="${ctx }/static/monitorPlatform/images/house170.png" /><br />
											<span>${warningOrganizations.orgName }</span>
										</div></a>
								</c:forEach>
								<c:forEach items="${areaInfo.goodOrganizations }"
									var="goodOrganization">
									<a href="javascript:showDialog(${goodOrganization.id })"><div
											class="box widget1x1 widget_green">
											<img src="${ctx }/static/monitorPlatform/images/house170.png" /><br />
											<span>${goodOrganization.orgName }</span>
										</div></a>
								</c:forEach>
							</div>
						</div>
					</c:forEach>

				</div>
			</div>
		</div>
	</div>


</body>
</html>