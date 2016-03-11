<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/views/include/easyui.jsp" %>
</head>
<body>
<form id="closeForm" action="${ctx }/maintenance/problem/shutdown" method="post">
	<h3 align="center">问题关闭后不能恢复，请慎重操作！</h3>
		<table class="formTable" style="font-size:12px;">
			<tr>
				<td style="font-size:14px;">运维结果评分：</td>
				<td>
					<div id="star"></div>
					<input type="hidden" id="score" name="score" value="3"/>
				</td>
			</tr>
			<tr>
				<td>是否加入知识库：</td>
				<td>
					<input type="hidden" name="id" value="${id }"/>
					<input type="hidden" name="enable" value="CLOSED"/>
					<select id="knowledge" name="enableKnowledge" class="easyui-validatebox" style="width:150px;">
						<option value="false" selected="selected">否</option>
						<option value="true">是</option>
					</select>
				</td>
			</tr>
			<tr id="type-tr" style="display: none;">
				<td>知识库分类：</td>
				<td>
					<input id="classificationId" name="classificationId"  class="easyui-combotree" data-options="width:150,editable:false"/>
				</td>
			</tr>
		</table>
</form>
	<script type="text/javascript">
		$(function(){
			$("#star").raty({
				score : 3,
				path : "/itam/static/plugins/raty/img",
				hints : ["很不满意","不满意","一般","满意","很满意"],
				click : function(score, evt) {
					$("#score").val(score);
				}
			});
			
			$("#knowledge").combobox({
				onSelect : function(sec) {
					var value = sec.value;
					if (value == "true" || value == true) {
						$("#type-tr").show();
					} else {
						$("#type-tr").hide();
					}
				}
			})
			
	$('#classificationId').combotree({
		width:150,
		method:'GET',
	    url: '${ctx}/knowledge/classification/json',
	    idField : 'id',
	    textFiled : 'name',
		parentField : 'pid',
	    animate:true,
	    onSelect:function(){
	    	$("#classificationId").validatebox("remove");
	    }
	});  
			
			$('#closeForm').form({    
			    onSubmit: function(){
			    	var ktype = $('#classificationId').combotree("getValue");
			    	if ($("#knowledge").combobox("getValue") == "true" && (ktype == null || ktype == "")) {
			    		parent.$.messager.alert("警告：","必须要选择知识库类型！");
			    		return false;
			    	} else {
				    	var isValid = $(this).form('validate');
						return isValid;	// 返回false终止表单提交
			    	}
			    },    
			    success:function(data){   
			    	successTip(data,dg,cdlg);
			    }    
			});  
		});
	</script>
</body>
</html>