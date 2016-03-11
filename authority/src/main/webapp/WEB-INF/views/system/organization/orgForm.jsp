<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>
<body>
<div>
	<form id="mainform" action="${ctx}/system/organization/${action}" method="post">
	<table  class="formTable">
		<tr>
			<td>机构名称：</td>
			<td>
			<input type="hidden" name="id" value="${id}" data-options="required:false"/>
			<input name="orgName" type="text" value="${organization.orgName }" class="easyui-validatebox"  data-options="required:true,validType:['length[0,50]']" />
			</td>
		</tr>
		<tr>
			<td>上级机构：</td>
			<td>
				<input id="pid" name="pid" type="text" value="${organization.pid }" class="easyui-validatebox" />
			</td>
		</tr>
		<tr>
			<td>所在区域：</td>
			<td>
				<input id="areaId" name="areaId" type="text" value="${organization.areaId }" data-options="required:true"/>
			</td>
		</tr>
		<tr>
			<td>机构代码：</td>
			<td>
			<input name="orgCode" type="text" value="${organization.orgCode }" class="easyui-validatebox"  data-options="required:true,validType:['length[0,12]']" />
			</td>
		</tr>
		<tr>
			<td>机构分类：</td>
			<td>
			<input name="orgType" type="text" value="${organization.orgType }" class="easyui-validatebox"  data-options="required:true,validType:['length[0,12]']" />
			</td>
		</tr>
		<tr>
			<td>机构层级：</td>
			<td>
			<input name="orgLevel" type="text" value="${organization.orgLevel }" class="easyui-validatebox"  data-options="required:false,validType:['length[0,3]','integer']" />
			</td>
		</tr>
		<tr>
			<td>排序码：</td>
			<td>
			<input name="orgSort" type="text" value="${organization.orgSort }" class="easyui-validatebox"  data-options="required:false,validType:['length[0,3]','integer']" />
			</td>
		</tr>
		<tr>
			<td>经度：</td>
			<td>
			<input name="longitude" type="text" value="${organization.longitude }" class="easyui-validatebox"  data-options="validType:'Number'" />
			</td>
		</tr>
		<tr>
			<td>纬度：</td>
			<td>
			<input name="latitude" type="text" value="${organization.latitude }" class="easyui-validatebox"  data-options="validType:'Number'" />
			</td>
		</tr>
		<tr>
			<td>地址：</td>
			<td>
			<input name="address" type="text" value="${organization.address }" class="easyui-validatebox"  data-options="validType:['length[0,50]']" />
			</td>
		</tr>
		<tr>
			<td>联系方式：</td>
			<td>
			<input name="contact" type="text" value="${organization.contact }" class="easyui-validatebox"  data-options="validType:['length[0,50]']" />
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
		method:'post',
	    url: '${ctx}/system/organization/json',
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
	    	if(successTip(data,dg,d))
	    		dg.treegrid('reload');
	    }    
	}); 
});

</script>
</body>
</html>