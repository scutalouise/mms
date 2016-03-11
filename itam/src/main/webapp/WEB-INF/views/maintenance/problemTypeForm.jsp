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
	<form id="mainform" action="${ctx }/maintenance/problemType/${action}" method="post">
      <table class="formTable" style="border-spacing:10px;">
      
         <tr>
            <td>问题类型名称：</td>
            <td>
              <input name="id" type="hidden" value="${id }"/>
              <input name="name" id="name" class="easyui-validatebox" value="${problemType.name }" data-options="width:150,required:'required'"/><br/>
            </td>           
         </tr>
         
         <tr>
			<td>适用设备类型：</td>
			<td>
				<input id="firstDeviceType" name="deviceType" value="${problemType.deviceType}" class="easyui-combobox" data-options="width:150,editable:false,required:'required'"/>
			</td>
		</tr>
   
         <tr>
            <td>描述：</td>
            <td>
                <textarea name="otherNote" class="easyui-validatebox" data-options="width:150" rows="3" >${problemType.otherNote}</textarea>
            </td>           
         </tr>
        
      </table>
   </form>
   <script type="text/javascript">
		$(function(){
			 $('#firstDeviceType').combobox({
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
   </script>
</body>
</html>