<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/views/include/easyui.jsp" %>
</head>
<body>
	<form id="mainform" action="${ctx }/maintenance/handling/${action}" method="post">
      <table class="formTable">
         <tr>
            <td>设备编号：</td>
            <td>
              <input name="problemId" type="hidden" value="${problem.id }"/>
              ${problem.identifier }
            </td>           
         </tr>
         
         <tr>
			<td>问题类型：</td>
			<td>
				${problemType.name}
			</td>
		</tr>
		
		<tr>
			<td>问题状态：</td>
			<td>
				<select id="enable" name="enable" class="easyui-validatebox" style="width:150px">
					<option value="HANDLING" selected="selected">处理中</option>
					<option value="RESOLVED">已解决</option>
				</select>
			</td>
		</tr>
		
		<tr id="change-td" style="display:none;">
			<td>是否换修：</td>
			<td>
				<select id="change" name="change" class="easyui-combobox" style="width:150px">
					<option value="false" selected="selected">否</option>
					<option value="true">是</option>
				</select>
			</td>
		</tr>
		
		<tr id="change-device" style="display:none;">
			<td>选择设备：</td>
			<td>
				<input name="changeDeviceIdentifier" id="changeDevice" class="easyui-combobox" data-options="width:150,editable:false"/>
			</td>
		</tr>
         
		<!-- <tr>
            <td>附件地址：</td>
            <td>
              <input name="attachment" id="attachment" class="easyui-validatebox" value="" data-options="width:150"/>
            </td>           
		</tr> -->

         <tr>
            <td>描述：</td>
            <td>
                <textarea name="description" class="easyui-validatebox" data-options="width:150" rows="5" ></textarea>
            </td>           
         </tr>
         
      </table>
   </form>
   <script type="text/javascript">
		$(function(){
			 
			$("#enable").combobox({
				onSelect : function(sec) {
					var value = sec.value;
					if (value == "RESOLVED") {
						$("#change-td").show();
					} else {
						$("#change-td").hide();
						$("#change-device").hide();
					}
				}
			});
			
			$("#change").combobox({
				onSelect : function(sec) {
					var value = sec.value;
					if (value == "true") {
						$("#change-device").show();
					} else {
						$("#change-device").hide();
					}
				}
			});
			
			$('#changeDevice').combobox({
				  method:"get",
				  url:'${ctx}/maintenance/handling/obtainUser/devices',
				  valueField:'identifier',
				  textField:'name'
			});
			 
			 $('#mainform').form({    
				    onSubmit: function(){
				    	var device = $("#changeDevice").combobox("getValue");
				    	if ($("#enable").combobox("getValue") == "RESOLVED" && $("#change").combobox("getValue") == "true" 
				    			&& (device == null || device == "")) {
				    		parent.$.messager.alert("警告：","换修必须要选择替换的新设备！");
				    		return false;
				    	} else {
					    	var isValid = $(this).form('validate');
							return isValid;	// 返回false终止表单提交
				    	}
				    },    
				    success:function(data){   
				    	successTip(data,dg,dlg);
				    }    
				});  
		})
   </script>
</body>
</html>