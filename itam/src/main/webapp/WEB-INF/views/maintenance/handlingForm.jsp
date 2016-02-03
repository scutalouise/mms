<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/views/include/easyui.jsp" %>
</head>
<body>
	<form id="mainform" action="${ctx }/maintenance/handling/${action}" method="post">
      <table class="formTable">
         <tr>
            <td>设备编号：</td>
            <td>
              <input name="problemId" type="hidden" value="${problem.id }"/>
              ${problem.identifier }
            </td>           
         </tr>
         
         <tr>
			<td>问题类型：</td>
			<td>
				${problemType.name}
			</td>
		</tr>
		
		<tr>
			<td>问题状态：</td>
			<td>
				<input id="enable" name="enable" value="${problem.enable}" class="easyui-combobox" data-options="width:150,editable:false,required:'required'"/>
			</td>
		</tr>
         
		<tr>
            <td>附件地址：</td>
            <td>
              <input name="attachment" id="attachment" class="easyui-validatebox" value="" data-options="width:150"/>
            </td>           
		</tr>

         <tr>
            <td>描述：</td>
            <td>
                <textarea name="description" class="easyui-validatebox" cols="20" rows="5" ></textarea>
            </td>           
         </tr>
         
      </table>
   </form>
   <script type="text/javascript">
		$(function(){
			 
			 $('#enable').combobox({
				  method:"get",
				  url:'${ctx}/maintenance/problem/enable/handle/true/search/false',
				  valueField:'problemStatus',
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