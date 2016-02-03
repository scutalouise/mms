<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>
<body>
<div>
	<form id="mainform" action="${ctx}/system/role/${action}" method="post">
	<table  class="formTable">
		<tr>
			<td>角色名：</td>
			<td>
			<input type="hidden" name="id" value="${id }" id="id"/>
			<input id="name" name="name" type="text" value="${role.name }" class="easyui-validatebox"  data-options="width: 150,required:'required',validType:['length[1,20]','checkRoleName[\'id\',\'name\']']"/>
			</td>
		</tr>
		<tr>
			<td>角色编码：</td>
			<td><input id="roleCode" name="roleCode" type="text" value="${role.roleCode }" class="easyui-validatebox"  data-options="width: 150,required:'required'"/></td>
		</tr>
		<tr>
			<td>排序：</td>
			<td><input id="sort" name="sort" type="text" value="${role.sort}" class="easyui-numberbox" data-options="width: 150" /></td>
		</tr>
		<tr>
			<td>描述：</td>
			<td><textarea rows="3" cols="41" name="description"  style="font-size: 12px;font-family: '微软雅黑'" class="easyui-validatebox" data-options="validType:['length[0,500]']">${role.description}</textarea></td>
		</tr>
	</table>
	</form>
</div>
<script type="text/javascript">
//验证角色名是否存在
$.extend($.fn.validatebox.defaults.rules, {
	checkRoleName: {
        validator: function (value, param) {
        	var flag = false;
        	var result = $.ajax({url:"${ctx}/system/role/checkRoleName",
        						data:{roleId:$("#" + param[0]).val(),roleName:$("#" + param[1]).val()},
        						async:false,
        						cache:false,
        						type:"post"}).responseText;
        	return result == "true";
        },
        message: "角色名已经存在"
    }
	
});


var action="${action}";
if(action=='update'){
	$('#roleCode').attr("readonly",true);
	$("#roleCode").css("background","#eee");
}else{
	//角色编码存在验证
	$.fn.validatebox.defaults.rules.remote.message = "角色编码已存在";
	$('#roleCode').validatebox({    
	    required: true,    
	    validType:{
	    	length:[1,12],
	    	remote:["${ctx}/system/role/checkRoleCode","roleCode"]
	    }//,
	   // invalidMessage:"角色编码已存在"
	});  
}

$(function(){
	$('#mainform').form({    
	    onSubmit: function(){    
	    	var isValid = $(this).form('validate');
			return isValid;	// 返回false终止表单提交
	    },    
	    success:function(data){   
	    	successTip(data,dg,d);
	    }    
	}); 
});

</script>
</body>
</html>