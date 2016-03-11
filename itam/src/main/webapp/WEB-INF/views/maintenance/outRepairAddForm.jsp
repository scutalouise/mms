<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/views/include/easyui.jsp" %>
</head>
<body>
	<form id="device_mainform" action="${ctx }/maintenance/outsideRepair/${action}" method="post">
      <table class="formTable">
         
         <tr>
			<td>返修厂家：</td>
			<td>
				<input type="hidden" name="ids" value="${ids }"/>
				<input id="firm" name="firm"  class="easyui-combobox" data-options="width:150,editable:false,required:'required'"/>
			</td>
		</tr>
         
         <tr>
			<td>返修收件人：</td>
			<td>
				<input id="receiver" name="repairReceiver"  class="easyui-combobox" data-options="width:150,editable:false,required:'required'"/>
			</td>
		</tr>
   
         <tr>
            <td>返修标注：</td>
            <td>
                <textarea name="repairRemark" class="easyui-validatebox" data-options="width:150" rows="3" ></textarea>
            </td>           
         </tr>
      </table>
   </form>
   <script type="text/javascript">
		$(function(){
			$('#firm').combobox({
				  method:"get",
				  url:'${ctx}/device/supplyMaintainOrg/json',
				  valueField:'id',
				  textField:'orgName',
				  onSelect:function(data){
					  var orgId = data.id;
					  $("#receiver").combobox({
						 method : "get",
						 required:'required',
						 url : "${ctx}/device/supplyMaintainOrg/users/orgId/" + orgId
					  }).combobox("clear");
				  }
			  });
			
			$("#receiver").combobox({
				valueField:'id',
				textField:'name'
			  });
			
			$('#device_mainform').form({    
			    onSubmit: function(){    
			    	var isValid = $(this).form('validate');
					return isValid;	// 返回false终止表单提交
			    },    
			    success:function(data){   
			    	successTip(data,device_dg,device_dlg);
			    	repair_dg.datagrid('reload');
			    }    
			}); 
			
		});
   </script>
</body>
</html>