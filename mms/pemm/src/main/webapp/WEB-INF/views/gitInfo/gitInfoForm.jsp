<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>主机监控表单</title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>


 
</head>
<body>
	<div>
		<form id="mainform" action="${ctx }/gitInfo/${action}" method="post"
			style="width: 100%;">
			<table class="formTable" cellpadding="3"
				style="width: 100%; padding: 10 0 10 0">
				<tr>
					<td>IP地址</td>
					<td><input type="hidden" name="id" id='gitInfoId' value="${gitInfo.id }" />
						<input type="hidden" name="version" value="${gitInfo.version }">
						<input type="hidden" name="currentStatus" value="${gitInfo.currentStatus }">
						<input type="text" name="ip" value="${gitInfo.ip }"
						class="easyui-validatebox"
						data-options="required:true,validType:['checkIp','validIp[$(\'#gitInfoId\').val()]']"></td>
					<td>名称</td>
					<td><input type="text" name="name" value="${gitInfo.name }" data-options="required:true,validType:['maxLength[20]']"
						class="easyui-validatebox"></td>
				</tr>
				<tr>

					<td>厂商</td>
					<td><input type="text" name="vendor"
						value="${gitInfo.vendor }" class="easyui-validatebox" data-options="validType:['maxLength[50]']"></td>
					<td>品牌</td>
					<td><input type="text" name="brand" value="${gitInfo.brand }"
						class="easyui-validatebox" data-options="validType:['maxLength[20]']"></td>
				</tr>
				<tr>
					<td>购买时间</td>
					<td><input class="easyui-datebox" name="buyTime"
						value="${gitInfo.buyTime}"></td>
				
					<td>所属机构</td>
					<td><input type="hidden" value="${gitInfo.organizationName}"
						name="organizationName" id="organizationName" />
						<input type="text"
						name="organizationId" value="${gitInfo.organizationId }"
						data-options="required:true" id="organizationId"></td>
					
				</tr>
				<tr>
					<td>维护人员</td>
					<td>
					<input type="hidden" name="managerName" value="${gitInfo.managerName }" id="managerName" >
					<input type="text" name="managerId" id="managerId"
						value="${gitInfo.managerId }" data-options="required:true"></td>
					<td>是否启用</td>
					<td><input type="radio" value="1" name="enabled"
						style="margin-top: -3px;">是 &nbsp;&nbsp; <input
						type="radio" value="0" name="enabled" style="margin-top: -3px;">否
					</td>

				</tr>
				<tr>
					<td>描述</td>
					<td colspan="3"><textarea name="remark"
							class="easyui-validatebox" data-options="validType:['maxLength[500]']" style="height: 60px; width: 360px;">${gitInfo.remark }</textarea></td>

				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
	
		$(function() {
		
			var action = "${action}";
			if (action == "create") {
				$("input[name='enabled'][value=1]").attr("checked", true);

			} else if (action == "update") {
				$("input[name='enabled'][value=${gitInfo.enabled}]").attr(
						"checked", true);
				$('#managerId').combogrid({
					url : ctx + "/gitInfo/userjson/${gitInfo.organizationId }"
				});
			}

			//提交表单
			$('#mainform').form({
				onSubmit : function() {
					var isValid = $(this).form('validate');
					return isValid; // 返回false终止表单提交
				},
				success : function(data) {
					successTip(data, dg, d);
				}
			});
			$("#organizationId").combotree({
				url : ctx + "/system/organization/json",
				idField : 'id',
				textFiled : 'orgName',
				parentField : 'pid',
				method : 'GET',
				animate : true,
				onSelect : function(node) {
				    $("#organizationName").val(node.text);
					$('#managerId').combogrid({
						url : ctx + "/gitInfo/userjson/"+node.id
					});
				},
				onLoadSuccess : function() {

					if ("${gitInfo.id==null}" == "true") {
						$("#organizationId").combotree("setValue", organizationId);
					}
				}
			});
			$("#managerId").combogrid({
				panelWidth : 500,

				idField : 'id',
				textField : 'name',
				fitColumns : true,
				striped : true,
				editable : false,
				pagination : true,//是否分页  
				rownumbers : true,//序号  
				collapsible : false,//是否可折叠的  
				fit : true,//自动大小  
				pageSize : 10,//每页显示的记录条数，默认为10  
				pageList : [ 10, 20, 50, 100 ],//可以设置每页记录条数的列表  
				method : 'get',
				columns : [ [ {
					field : 'name',
					title : '用户名称',
					width : 150
				}, {
					field : 'gender',
					title : '性别',
					width : 150
				}, {
					field : 'email',
					title : '邮箱',
					width : 150
				}, {
					field : 'phone',
					title : '电话',
					width : 150
				} ] ],
				onShowPanel : function() {
					
				},
				onSelect : function(rowIndex,rowData) {
					
				$("#managerName").val(rowData.name);
				
				}

			});
			
			
			

		});
	</script>
</body>
</html>