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
              <input id="identifier" name="identifier"  class="easyui-combobox" data-options="width:150,editable:false,required:'required'"/>
            </td>           
         </tr>
         
         <tr>
			<td>问题类型：</td>
			<td>
				<input id="problemType" name="problemTypeId"  class="easyui-combobox" data-options="width:150,editable:false,required:'required'"/>
			</td>
		</tr>
		
		<tr>
			<td>是否加入知识库：</td>
			<td>
				<input id="enableKnowledge" name="enableKnowledge" class="easyui-combobox" data-options="width:150,editable:false,required:'required'"/>
			</td>
		</tr>
         
         <tr>
			<td>上报渠道：</td>
			<td>
				<input id="reportWay" name="reportWay"  class="easyui-combobox" data-options="width:150,editable:false,required:'required'"/>
			</td>
		</tr>
		
		<tr>
            <td>上报人：</td>
            <td>
              <input name="reportUser" id="reportUser" class="easyui-validatebox"  data-options="width:150"/>
            </td>           
		</tr>
		
		<tr>
            <td>上报人联系方式：</td>
            <td>
              <input name="reportUserContact" id="reportUserContact" class="easyui-validatebox" data-options="width:150"/>
            </td>           
		</tr>
   
         <tr>
            <td>描述：</td>
            <td>
                <textarea name="description" class="easyui-validatebox" cols="19" rows="3" ></textarea>
            </td>           
         </tr>
         
      </table>
   </form>
   <script type="text/javascript">
		$(function(){
			 $('#problemType').combobox({
				  method:"get",
				  url:'${ctx}/maintenance/problemType/json',
				  valueField:'id',
				  textField:'name'
			  });
			 
			 $('#identifier').combobox({
				  method:"get",
				  url:'${ctx}/maintenance/problem/devices',
				  valueField:'identifier',
				  textField:'name'
			  });
			 
			 $('#enableKnowledge').combobox({
				  valueField:'value',
				  textField:'name',
				  data : [{
					  value : "true",
					  name : "是"
				  }, {
					  value : "false",
					  name : "否"
				  }]
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