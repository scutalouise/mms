<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<%-- <%@ taglib prefic="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %> --%>
</head>
<body>
<div>
	<form id="mainform" >
		<table style="border-spacing:5px;margin-left: 10px;margin-top: 5px;">  
			<tr>
				<td>采购名：</td>
				<td>
					<input type="hidden" name="id" value="${id }"/>
					<input type="hidden" name="version" value="${devicePurchase.version }"/>
					<label>${devicePurchase.name }</label>
				</td>
			</tr>
			<tr>
				<td>数量：</td>
				<td>
				    <label>${devicePurchase.quantity }</label>
				</td>
			</tr>
			<tr>
				<td>采购机构：</td>
				<td>
				 <label>${devicePurchase.orgName }</label
				</td>
			</tr>
			<tr>
				<td>设备类型:&nbsp;&nbsp;</td><!-- 原一级设备类型 -->
				<td>
				 <label>${devicePurchase.firstDeviceType }</label
				</td>
			</tr>
			<tr>	
				<td>设备名称:</td><!-- 原二级设备类型 -->
				<td>
				 <label>${devicePurchase.secondDeviceType }</label
				</td>
			</tr>
			<tr>	
				<td>品牌:</td>
				<td>
				 <label>${devicePurchase.brandName }</label
				</td>
			</tr>
			<tr>
				<td>供应商：</td>
				<td>
				 <label>${devicePurchase.supplyName }</label
				</td>
			</tr>
			<tr>
				<td>采购订单号：</td>
				<td>
				 <label>${devicePurchase.purchaseOrderNum }</label
				</td>
			</tr>
			<tr>
				<td>运维方式:</td>
				<td>
				 <label>${devicePurchase.maintainWay }</label
				</td>
			</tr>
			<tr>
				<td>运维组织：</td>
				<td>
				 <label>${devicePurchase.maintainOrgName }</label
				</td>
			</tr>
			<tr>
				<td>购买日期：</td>
				<td>
				 <label><fmt:formatDate value="${devicePurchase.purchaseDate }" pattern="yyyy-MM-dd"/></label
				</td>
			</tr>
			<tr>
				<td>保修日期：</td>
				<td>
				 <label><fmt:formatDate value="${devicePurchase.warrantyDate }" pattern="yyyy-MM-dd"/></label
				</td>
			</tr>
			<tr>
				<td>是否新购进：</td>
				<td>
				 <label><c:if test="${devicePurchase.isPurchase == 1}">是</c:if>
				 		<c:if test="${devicePurchase.isPurchase == 0}">否</c:if>
				 </label
				</td>
			</tr>
			<tr>
				<td>描述：</td>
				<td>
				 	<label>${devicePurchase.otherNote }</label
				</td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>