<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>
<body>
<div>
	<form id="mainform" action="${ctx}/device/supplyMaintainOrg/${action}" method="post">
	<table  class="formTable">
		<tr>
			<td>机构名称：</td>
			<td>
			<input type="hidden" name="id" value="${id}" data-options="required:false"/>
			<input name="orgName" type="text" value="${supplyMaintainOrg.orgName }" class="easyui-validatebox"  data-options="required:true,validType:['length[0,50]']" />
			</td>
		</tr>
		<tr>
			<td>上级机构：</td>
			<td>
				<input id="pid" name="pid" type="text" value="${supplyMaintainOrg.pid }" class="easyui-validatebox" />
			</td>
		</tr>
		<tr>
			<td>所在区域：</td>
			<td>
				<input id="areaId" name="areaId" type="text" value="${supplyMaintainOrg.areaId }" data-options="required:true"/>
			</td>
		</tr>
		<tr>
			<td>机构代码：</td>
			<td>
			<input name="orgCode" type="text" value="${supplyMaintainOrg.orgCode }" class="easyui-validatebox"  data-options="required:true,validType:['length[0,12]']" />
			</td>
		</tr>
		<tr>
			<td>机构分类：</td>
			<td>
			<input name="orgType" type="text" value="${supplyMaintainOrg.orgType }" class="easyui-validatebox"  data-options="required:true,validType:['length[0,12]']" />
			</td>
		</tr>
		<tr>
			<td>是否供应商：</td>
			<td>
			<input type="radio" id="yes" name="supplyOrg" value="1"/><label for="yes">是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
			<input type="radio" id="no" name="supplyOrg" value="0"/><label for="no">否</label>
			</td>
		</tr>
		
		<tr>
			<td>机构层级：</td>
			<td>
			<input name="orgLevel" type="text" value="${supplyMaintainOrg.orgLevel }" class="easyui-validatebox"  data-options="required:true,validType:['length[0,3]','integer']" />
			</td>
		</tr>
		<tr>
			<td>排序码：</td>
			<td>
			<input name="orgSort" type="text" value="${supplyMaintainOrg.orgSort }" class="easyui-validatebox"  data-options="required:true,validType:['length[0,3]','integer']" />
			</td>
		</tr>
		<tr>
			<td>负责人：</td>
			<td>
			<input name="director" type="text" value="${supplyMaintainOrg.director }" class="easyui-validatebox"  data-options="validType:'Number'" />
			</td>
		</tr>
		<tr>
			<td>联系电话：</td>
			<td>
			<input name="telephone" type="text" value="${supplyMaintainOrg.telephone }" class="easyui-validatebox"  data-options="validType:'Number'" />
			</td>
		</tr>
		<tr>
			<td>Email：</td>
			<td>
			<input name="email" type="text" value="${supplyMaintainOrg.email }" class="easyui-validatebox"  data-options="validType:'Number'" />
			</td>
		</tr>
		<tr>
			<td>传真：</td>
			<td>
			<input name="fax" type="text" value="${supplyMaintainOrg.fax }" class="easyui-validatebox"  data-options="validType:'Number'" />
			</td>
		</tr>
		<tr>
			<td>服务内容与条件：</td>
			<td><textarea rows="3" cols="41" name="serviceContent"  style="font-size: 12px;font-family: '微软雅黑'" class="easyui-validatebox" data-options="validType:['length[0,250]']">${supplyMaintainOrg.serviceContent }</textarea></td>
		</tr>
	</table>
	</form>
</div>
<script type="text/javascript">
$(function(){
	var action="${action}";
	//用户 添加修改区分
	if(action=='create'){
		$("input[name='supplyOrg'][value=0]").attr("checked",true); 
	}else if(action=='update'){
		$("input[name='supplyOrg'][value=${supplyMaintainOrg.supplyOrg}]").attr("checked",true);
	}
	//上级菜单
	$('#pid').combotree({
		width:180,
		method:'GET',
	    url: '${ctx}/device/supplyMaintainOrg/json',
	    idField : 'id',
	    textFiled : 'orgName',
		parentField : 'pid',
	    animate:true
	});  
	//区域菜单
	$('#areaId').combotree({
		width:180,
		method:'GET',
	    url: '${ctx}/system/area/json',
	    idField : 'id',
	    textFiled : 'areaName',
		parentField : 'pid',
	    animate:true
	    });
	$('#mainform').form({    
	    onSubmit: function(){    
	    	var isValid = $(this).form('validate');
	    	console.log(isValid);
			return isValid;	// 返回false终止表单提交
	    },    
	    success:function(data){   
	    	if(successTip(data,supplyMaintainOrgDataGrid,supplyMaintainOrgDialog))
	    		supplyMaintainOrgDataGrid.treegrid('reload');
	    }    
	}); 
});

</script>
</body>
</html>