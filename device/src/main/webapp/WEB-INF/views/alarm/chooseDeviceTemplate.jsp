<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择设备模板</title>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<div id="template_tb">

<form id="searchForm" >
<table>
	<tr>
	<td>
 <input type="hidden" name="filter_EQ_StatusEnum_status" value="NORMAL">
 <input type="hidden" name="filter_EQ_TemplateTypeEnum_templateType" value="DEVICEALARM">
<input type="text" name="filter_LIKES_name" class="easyui-validatebox" data-options="width:150,prompt: '模板名称'"/>
  <a href="javascript(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="cx()">查询</a>
</td>
<td id="templateName"></td>
	</tr>
</table>

</form>

</div>
<div id="template_dg"></div>
<script type="text/javascript">
var templateId;
$(function(){
	$("#template_dg").datagrid({
		method:"post",
		url:ctx+"/system/alarmTemplate/json",
		queryParams : {
			filter_EQ_StatusEnum_status : 'NORMAL',
			filter_EQ_TemplateTypeEnum_templateType:"DEVICEALARM"
		},
		fit:true,
		fitColumns:true,
		border : false,
		idField : "id",
		sriped : true,
		pagination : true,
		rownumbers : true,
		pageNumber : 1,
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50 ],
		singleSelect : true,
		columns : [ [ {
			field : 'id',
			checkbox : true//,
			//hidden:true
		}, {
			field : "name",
			title : "模板名称",
			sortable : true,
			width : 50
		}, {
			field : "remark",
			title : "描述",
			sortable : true,
			width : 100
		}]],
		enableHeaderClickMenu : true,
		enableHeaderContextMenu : true,
		enableRowContextMenu : false,
		toolbar : '#template_tb',
		onLoadSuccess:function(data){
			$.ajax({
				type:"get",
				url:ctx+"/system/alarmTemplate/findOrgAlarmTemplate",
				data:{
					orgId:orgRow.id
				},success:function(data){
			
					if(data.id!=undefined){
						
						$("#templateName").html("选中的模板名称:"+data.alarmTemplateName);
						$("#template_dg").datagrid('selectRecord',data.id); 
					}
				}
			})
			
		},
		onSelect:function(rowIndex,rowData){
			templateId=rowData.id;
			
			$("#templateName").html("选中的模板名称:"+rowData.name);
					
		}
	});
});
function cx(){
	var obj=$("#searchForm").serializeObject();
	$("#template_dg").datagrid('reload',obj); 
}
</script>

</body>
</html>