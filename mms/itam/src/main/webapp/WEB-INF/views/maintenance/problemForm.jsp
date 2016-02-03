<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
            <td>设备编号：</td>
            <td>
              <input name="id" type="hidden" value="${id }"/>
              ${problem.identifier }
            </td>           
         </tr>
         
         <tr>
			<td>问题类型：</td>
			<td>
				<input id="problemType" name="problemTypeId" value="${problem.problemTypeId}" class="easyui-combobox" data-options="width:150,editable:false,required:'required'"/>
			</td>
		</tr>
		
		<tr>
			<td>问题状态：</td>
			<td>
				<input id="enable" name="enable" value="${problem.enable}" class="easyui-combobox" data-options="width:150,editable:false,required:'required'"/>
			</td>
		</tr>
         
         <tr>
			<td>上报渠道：</td>
			<td>
				<input id="reportWay" name="reportWay" value="${problem.reportWay}" class="easyui-combobox" data-options="width:150,editable:false,required:'required'"/>
			</td>
		</tr>
		
		<tr>
            <td>上报人：</td>
            <td>
              <input name="reportUser" id="reportUser" class="easyui-validatebox" value="${problem.reportUser }" data-options="width:150"/>
            </td>           
		</tr>
		
		<tr>
            <td>上报人联系方式：</td>
            <td>
              <input name="reportUserContact" id="reportUserContact" class="easyui-validatebox" value="${problem.reportUserContact }" data-options="width:150"/>
            </td>           
		</tr>
   
         <tr>
            <td>描述：</td>
            <td>
                <textarea name="description" class="easyui-validatebox" cols="19" rows="3" >${problem.description}</textarea>
            </td>           
         </tr>
         
      </table>
   </form>
   <script type="text/javascript">
		var identifier = "${problem.identifier}";
		$(function(){
			 $('#problemType').combobox({
				  method:"get",
				  url:'${ctx}/maintenance/problemType/identifier/' + identifier,
				  valueField:'id',
				  textField:'name'
			  });
			 
			 $('#enable').combobox({
				  method:"get",
				  url:'${ctx}/maintenance/problem/enable/handle/false/search/false',
				  valueField:'problemStatus',
				  textField:'name'
			  });
			 
			 $('#reportWay').combobox({
				  method:"get",
				  url:'${ctx}/maintenance/problem/reportway',
				  valueField:'reportWay',
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