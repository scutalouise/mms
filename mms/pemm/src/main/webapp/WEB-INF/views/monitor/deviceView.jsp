<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="organizationId" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设备视图预览</title>
<script src="${ctx }/static/plugins/easyui/jquery/jquery-2.1.1.min.js"></script>
<link href="${ctx }/static/css/monitor/deviceView.css" rel="stylesheet">
<script type="text/javascript"
	src="${ctx }/static/plugins/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="${ctx }/static/plugins/bootstrap/css/bootstrap.min.css">

<script type="text/javascript">
	var ctx = "${ctx}";
	var gitIdStr="${gitIds}";
	
	
</script>
<script type="text/javascript"
	src="${ctx }/static/js/monitor/deviceView.js">
	
</script>
</head>
<body>
	<table style="width: 100%; height: 100%;" cellpadding="0" cellspacing="0">
		<tr>
			<td width="450px"  valign="top">
				<div id="device_left" style="width: 450px;overflow: auto;overflow-x:hidden; ">
					<div id="back_div">
						<div id="bakcBranch">返回机构网点</div>
							<div id="bakcDeviceView">返回设备视图</div>
						
					</div>
					<c:forEach items="${organizationList }" var="organization">
						<div class="device_orgName">网点名称：【${organization.orgName }】
						</div>
						<hr class='git_hr'></hr>
						<c:forEach items="${gitInfoList }" var="gitInfo" varStatus="i">
							<c:if test="${gitInfo.organizationId==organization.id }">
								<div class="device_content">
									<div class="device_img">
										<input type="radio" onclick="loadDeviceDetail()" value="${gitInfo.id }_${gitInfo.serverState}_${gitInfo.ip}"
											<c:if test="${i.index==0 }">checked="checked" </c:if> name="selectedDevice" style="margin-top: 25px;"> <img
											src="${ctx }/static/images/monitor/git.gif" height="69"
											width="130"/>
									</div>
									<div class="device_detail">
										<div>IP：${gitInfo.ip }</div>
										<div>动环名称：${gitInfo.name }</div>
										<div class='git_details'>
											<a href='javascript:loadDeviceDetail()'>查看详情</a>
										</div>
									</div>

								</div>
							</c:if>
						</c:forEach>
					</c:forEach>
					<div style="height: 20px;float: left;"></div>
					
				</div>
			</td>
			<td valign="top" style="">

				<div id="view_div">
					
				</div>
			
			</td>
		</tr>
	</table>
</body>
</html>