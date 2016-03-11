<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/views/include/easyui.jsp" %>
</head>
<body>
<div>
	<form id="mainform" action="${ctx }/maintenance/arrange/${action}" method="post">
    <table  class="formTable">
         <tr>
            <td>选择用户：</td>
            <td>
				<input name="id" type="hidden" value="${arrange.id }"/>
              <input id="userId" name="userId" value="${arrange.userId}" class="easyui-combobox" data-options="width:230,editable:false,required:'required'"/>
              <input name="userName" id="userName" type="hidden" value="${arrange.userName }"/>
            </td>           
         </tr>
         
         <tr>
			<td>是否启用：</td>
			<td>
				<input id="enable" name="enable" value="${arrange.enable}" class="easyui-combobox" data-options="width:230,editable:false,required:'required'"/>
			</td>
		</tr>
         
         <tr>
			<td>工作日：</td>
			<td>
				<input id="workDay" name="workDay"  class="easyui-combobox" data-options="width:230,editable:false,required:'required'"/>
			</td>
		</tr>
		
		<tr>
            <td>工作时间：</td>
            <td>
              <input id="startTime" class="easyui-timespinner" value="${startTime }" data-options="width:100,required:'required'"/>
              &nbsp;-&nbsp;
              <input id="endTime" class="easyui-timespinner" value="${endTime }" data-options="width:100,editable:false,required:'required'"/>
				<input name="workTime" id="workTime" type="hidden" value="${arrange.workTime }"/>
            </td>           
		</tr>
   
         <tr>
            <td>备注：</td>
            <td>
                <textarea name="remark" class="easyui-validatebox" data-options="width:230" rows="5" >${arrange.remark }</textarea>
            </td>           
         </tr>
    </table>
   	</form>
</div>
   <script type="text/javascript">
		$(function(){
						
			 $('#userId').combobox({
				  method:"get",
				  url:'${ctx}/maintenance/arrange/userList',
				  valueField:'id',
				  textField:'name'
			  });
			 
			 $('#enable').combobox({
				  valueField:'value',
				  textField:'name',
				  data : [{
					  value : "true",
					  name : "是"
				  }, {
					  value : "false",
					  name : "否"
				  }]
			  });
			 
			 $('#workDay').combobox({
				  panelHeight:'auto',
				  multiple:true,
				  method:"get",
				  url:'${ctx}/maintenance/arrange/weekEnum',
				  valueField:'value',
				  textField:'name',
				  onLoadSuccess : function(){
					  var workDay = "${workDay}";
					  if (workDay != null && workDay != "") {
						  var json = JSON.parse(workDay);
						  $('#workDay').combobox("setValues",json);
					  }
						  
				  }
			  });
			 
			 $('#mainform').form({    
				    onSubmit: function(){ 
				    	$("#userName").val($("#userId").combobox("getText"));
				    	getTime();
				    	var isValid = $(this).form('validate');
						return isValid;	// 返回false终止表单提交
				    },    
				    success:function(data){   
				    	successTip(data,dg,dlg);
				    }    
				});  
		});
		
		
		function getTime(){
			var start = $("#startTime").timespinner("getValue");
			var end = $("#endTime").timespinner("getValue");
			if (start < end) {
				$("#workTime").val(start + "-" + end);
			} else {
				parent.$.messager.alert("警告：","开始时间必须小于结束时间！");
			}
				
		}
		
   </script>
</body>
</html>