<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
			<td>选择运维人员：</td>
			<td>
				<input name="id" type="hidden" value="${id }"/>
				<input name="enable" type="hidden" value="ASSIGNED"/>
				<input id="resolveUser" name="resolveUserId" value="${problem.resolveUserId}" class="easyui-combobox" data-options="width:150,editable:false"/>
			</td>
		</tr>
         
      </table>
   </form>
   <script type="text/javascript">
		var identifier = "${problem.identifier}";
		$(function(){
			
			$('#resolveUser').combobox({
				  method:"get",
				  url:'${ctx}/maintenance/problem/resolveUser/identifier/' + identifier,
				  valueField:'id',
				  textField:'name'
			  });
			
			 $('#mainform').form({    
				    onSubmit: function(){    
				    	var isValid = $(this).form('validate');
						return isValid;	// 返回false终止表单提交
				    },    
				    success:function(data){   
				    	successTip(data,dg,dlg);
				    	var id = "${id}";
				    	$.ajax({
				    		type:'get',
							url:'${ctx}/maintenance/problem/sendMessage/' + id,
							error : function(){},
							success : function(data){}
				    	});
				    }    
				});  
		})
   </script>
</body>
</html>