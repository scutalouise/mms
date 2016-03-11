<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>
<body>
<div>
	<form id="mainform" action="${ctx}/knowledge/classification/${action}" method="post">
	<table  class="formTable">
		<tr>
			<td>分类名称：</td>
			<td>
			<input type="hidden" name="id" value="${id}" data-options="required:false"/>
			<input name="name" type="text" value="${knowledgeClassification.name }" class="easyui-validatebox"  data-options="required:true,validType:['length[0,50]']" />
			</td>
		</tr>
		<tr>
			<td>上级分类：</td>
			<td>
				<input id="pid" name="pid" type="text" value="${knowledgeClassification.pid }" class="easyui-validatebox" />
			</td>
		</tr>
		<tr>
			<td>编码：</td>
			<td>
			<input name="code" type="text" value="${knowledgeClassification.code }" class="easyui-validatebox"  data-options="validType:'Number'" />
			</td>
		</tr>
	</table>
	</form>
</div>
<script type="text/javascript">
$(function(){
	//上级菜单
	$('#pid').combotree({
		width:180,
		method:'GET',
	    url: '${ctx}/knowledge/classification/json',
	    idField : 'id',
	    textFiled : 'name',
		parentField : 'pid',
	    animate:true
	});  
	$('#mainform').form({    
	    onSubmit: function(){    
	    	var isValid = $(this).form('validate');
			return isValid;	// 返回false终止表单提交
	    },
	    success:function(data){   
	    	if(successTip(data,classificationTreegrid,classificationDialog))
	    		classificationTreegrid.treegrid('reload');
	    }    
	}); 
});
</script>
</body>
</html>