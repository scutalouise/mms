<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/views/include/easyui.jsp" %>
</head>
<body>
	<form id="mainform" action="${ctx }/maintenance/problem/${action}" method="post">
      <table class="formTable">
         <tr>
            <td>选择设备：</td>
            <td>
              <input id="identifier_fi" name="identifier"  class="easyui-combogrid" data-options="width:150,editable:false,required:'required'"/>
            </td>           
         </tr>
         
         <tr>
			<td>问题类型：</td>
			<td>
				<input id="problemType" name="problemTypeId"  class="easyui-combobox" data-options="width:150,editable:false,required:'required'"/>
			</td>
		</tr>
         
         <tr>
			<td>上报渠道：</td>
			<td>
				<select id="reportWay" name="reportWay" class="easyui-combobox" style="width:150px;">
						<option value="PHONE" selected="selected">电话报修</option>
						<option value="DICTATION">口头报备</option>
					</select>
			</td>
		</tr>
		
		<tr>
            <td>上报人：</td>
            <td>
              <input name="reportUser" id="reportUser" class="easyui-validatebox"  data-options="width:150"/>
            </td>           
		</tr>
		
		<tr>
            <td>上报人联系方式：</td>
            <td>
              <input name="reportUserContact" id="reportUserContact" class="easyui-validatebox" data-options="width:150"/>
            </td>           
		</tr>
   
         <tr>
            <td>描述：</td>
            <td>
                <textarea name="description" class="easyui-validatebox" data-options="width:150" rows="3" ></textarea>
            </td>           
         </tr>
         
      </table>
   </form>
	<div id="tbd" style="padding: 5px;height: auto;">
			<input type="text" id="organizationId" name="organizationId" class="easyui-validatebox" data-options="width:150,prompt: '所属网点'"/>
			<input type="text" id="deviceType" name="deviceType" class="easyui-validatebox" data-options="width:150,prompt: '设备类型'"/>
			<span class="toolbar-item dialog-tool-separator"></span>
		    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="cxDevice()">查询</a>
	</div>
   <script type="text/javascript">
		$(function(){
			 $('#identifier_fi').combogrid({
				 panelWidth : 500,
				  idField:'identifier',
				  textField:'name',
				  fitColumns : true,
					striped : true,
					pagination : true,//是否分页  
					rownumbers : true,//序号  
					editable : true,
					collapsible : false,//是否可折叠的  
					fit : true,//自动大小  
					pageSize : 10,//每页显示的记录条数，默认为10  
					method : 'get',
					columns : [ [ {
						field : 'name',
						title : '设备名称',
						width : 150
					}, {
						field : 'identifier',
						title : '设备编号',
						width : 150
					}, {
						field : 'hallName',
						title : '所属网点',
						width : 150
					}, {
						field : 'firstDeviceType',
						title : '设备类型',
						width : 150,
						formatter : function(value, row, index) {
			        		return value.name;
			        	}
					} ] ],
					toolbar:'#tbd',
					onClickRow : function(index, row) {
					  var ide = row.identifier;
					  $('#problemType').combobox({
						  method:"get",
						  url:'${ctx}/maintenance/problemType/identifier/' + ide
					 }).combobox('clear');
				  }
			  });
			 $('#identifier_fi').combogrid({
				 url:'${ctx}/maintenance/problem/devices'
			 });
			 
			 $('#problemType').combobox({
				  valueField:'id',
				  textField:'name'
			  });
			 
			 $('#organizationId').combotree({
					method:'post',
					url:'${ctx}/system/organization/json',
					idField:'id',
					textFiled:'orgName',
				    animate:true
				});
			 
			 $('#deviceType').combobox({
				  method:"get",
				  url:'${ctx}/device/brand/firstDeviceType',
				  valueField:'firstDeviceType',
				  textField:'name'
			 });
			 
			 $('#mainform').form({    
				    onSubmit: function(){    
				    	var isValid = $(this).form('validate');
						return isValid;	// 返回false终止表单提交
				    },    
				    success:function(data){   
				    	successTip(data,dg,dlg);
				    }    
				});  
		})
		
		function cxDevice(){
			var orgId = $("#organizationId").combobox("getValue");
			var deviceType = $("#deviceType").combobox("getValue");
			var cg = $('#identifier_fi').combogrid({
				 method : "get",
				 queryParams : {"filter_organizationId" : orgId, "filter_firstDeviceType" : deviceType},
				 url:'${ctx}/maintenance/problem/devices'
			 });
		}
		
   </script>
</body>
</html>