<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设备监控表单</title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>


</head>
<body>
	<div>

		<form:form id="mainform" action="${ctx }/device/${action }"
			method="post" style="width: 100%;" modelAttribute="device">
			<table class="formTable" cellpadding="3"
				style="width: 100%; padding: 10 0 10 0">
				<tr>
					<td>所属主机</td>
					<td><form:hidden path="id" /> <form:hidden path="gitInfo.id" />
						<form:input path="gitInfo.ip" id="ip" class="easyui-validatebox"></form:input>

					</td>
					<td>设备名称</td>
					<td><form:input path="name" class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>厂商</td>
					<td><form:input path="vendor" class="easyui-validatebox" /></td>
					<td>品牌</td>
					<td><form:input path="brand" class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>购买时间</td>
					<td><input class="easyui-datebox" name="buyTime"
						value="${gitInfo.buyTime}"></td>
					<td>管理人员</td>
					<td>
					<form:hidden path="managerName" id="managerName"/>
					<form:input type="text" id="managerId" path="managerId"
							class="easyui-validatebox" /></td>
				</tr>

				<tr>
					<td>所属类型</td>
					<td><form:input path="deviceType" class="easyui-validatebox"
							readonly="true" /></td>
					<td>UPS接口</td>
					<td><form:input path="deviceIndex" class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>是否启用</td>
					<td colspan="3"><input type="radio" value="1" name="enabled"
						style="margin-top: -3px;">是 &nbsp;&nbsp; <input
						type="radio" value="0" name="enabled" style="margin-top: -3px;">否
					</td>

				</tr>
				<tr>

					<td>描述</td>
					<td colspan="3"><form:textarea path="remark"
							class="easyui-validatebox" style="height: 60px;width: 360px;" /></td>

				</tr>
			</table>
		</form:form>

	</div>
	<script type="text/javascript">
		var action = "${action}";
		
		if (action == 'create') {
			
			$("input[name='enabled'][value=1]").attr("checked", true);

		} else if (action == 'update') {
			
			$("#ip").attr('readonly','readonly');
			$("#ip").css('background','#eee')
			$("input[name='enabled'][value=${device.enabled}]").attr("checked",
					true);
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

		$("#managerId").combogrid({
			panelWidth : 500,

			idField : 'id',
			textField : 'name',
			fitColumns : true,
			striped : true,
			editable : true,
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
			onSelect:function(rowIndex,rowData){
				$("#managerName").val(rowData.name);
			}

		});
		$('#managerId').combogrid({
			url : ctx + "/gitInfo/userjson/${device.gitInfo.areaInfoId}"
		});
	</script>
</body>
</html>