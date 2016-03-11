<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>
<body>
<div>
	<form id="mainform" action="${ctx}/knowledge/details/${action}" method="post">
	<table  class="formTable">
		<tr>
			<td>标题：</td>
			<td>
			<input type="hidden" name="id" value="${id}" data-options="required:false"/>
			<input name="title" type="text" value="${knowledgeDetails.title }" class="easyui-validatebox"  data-options="required:true,validType:['length[0,50]']" />
			</td>
		</tr>
		<tr>
			<td>关键字提取：</td>
			<td>
			<input name="keyword" type="text" value="${knowledgeDetails.keyword }" class="easyui-validatebox"  data-options="required:true,validType:['length[0,50]']" />
			</td>
		</tr>
		<tr>
			<td>知识库分类：</td>
			<td>
				<input id="classificationId" name="classificationId" type="text" value="${knowledgeDetails.classificationId }" class="easyui-validatebox" data-options="required:true"/>
			</td>
		</tr>
		<tr>
			<td>文本内容：</td>
			<td>
			<textarea name="content" class="easyui-validatebox"  data-options="required:true,validType:['length[0,1000]']" style="width: 300px;height: 300px;max-width: 300px;max-height: 300px;" resize: none;>${knowledgeDetails.content }</textarea>
			</td>
		</tr>
	</table>
	</form>
</div>
<script type="text/javascript">
$(function(){
	//知识库分类树
	$('#classificationId').combotree({
		width:180,
		method:'GET',
	    url: '${ctx}/knowledge/classification/json',
	    idField : 'id',
	    textFiled : 'name',
		parentField : 'pid',
	    animate:true,
	    onSelect:function(){
	    	//$("#classificationId").removeClass("validatebox-text validatebox-invalid").unbind('focus').unbind('blur');//移除验证判断；
	    	$("#classificationId").validatebox("remove");
	    }
	});  

	$('#mainform').form({    
	    onSubmit: function(){    
	    	var isValid = $(this).form('validate');
	    	//alert($("#classificationId").combotree('tree').tree('getSelected').id);
	    	//alert(isValid);
			return isValid;	// 返回false终止表单提交
	    },
	    success:function(data){   
	    	if(successTip(data,detailsDatagrid,detailsDialog))
	    		detailsDatagrid.datagrid('reload');
	    }    
	}); 
});

</script>
</body>
</html>