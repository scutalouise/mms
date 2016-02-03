<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
	<div style="padding: 5px">
	<form id="mainform" action="${ctx }/system/user/updatePwd" method="post">
	<table class="formTable">
		<tr>
			<td>原密码：</td>
			<td>
			<input type="hidden" name="id" value="${user.id }"/>
			<input id="oldPassword" name="oldPassword" type="password" class="easyui-validatebox" data-options="width: 150,required:'required',validType:['checkPwd'],novalidate:true" />
			</td>
		</tr>
		<tr>
			<td>新密码：</td>
			<td><input id="plainPassword" name="plainPassword" type="password" class="easyui-validatebox" data-options="width: 150,required:'required',validType:'length[6,20]',novalidate:true"/></td>
		</tr>
		<tr>
			<td>确认密码：</td>
			<td><input id="confirmPassword" name="confirmPassword" type="password" class="easyui-validatebox" data-options="width: 150,required:'required',validType:'equals[$(\'#plainPassword\').val()]',novalidate:true"/></td>
		</tr>
		
	</table>
	</form>
</div>
<script>
$(function(){
	
	$("#oldPassword").keyup(function(){
		$(this).validatebox("options").novalidate=true;
	});
	//提交表单
	$('#mainform').form({    
	    onSubmit: function(){    
	    	var isValid = $(this).form('enableValidation').form('validate');
			return isValid;	// 返回false终止表单提交
	    },    
	    success:function(data){  
	    	if(data=='success'){
				
				$.messager.show({ title : "提示",width:250,msg: "密码修成功", position: "topCenter" });
				
			}
	    	pwdWin.panel('close');
	    }    
	});
	
});
</script>
</body>
</html>