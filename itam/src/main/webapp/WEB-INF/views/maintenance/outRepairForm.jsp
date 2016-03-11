<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/views/include/easyui.jsp" %>
</head>
<body>
	<form id="repair_mainform" action="${ctx }/maintenance/outsideRepair/${action}" method="post">
      <table class="formTable">
         <tr>
			<td>返修结果：</td>
			<td>
				<input type="hidden" name="id" value="${outsideRepair.id }"/>
				<select id="result" name="repairResult" class="easyui-combobox" style="width:150px;">
						<option value="true">返修完成，设备入库</option>
						<option value="false">返修失败，设备报废</option>
					</select>
			</td>
		</tr>
   
         <tr>
            <td>返修结果描述：</td>
            <td>
                <textarea name="returnRemark" class="easyui-validatebox" data-options="width:150" rows="3" ></textarea>
            </td>           
         </tr>
         
      </table>
   </form>
<script type="text/javascript">
	$(function(){
		$('#repair_mainform').form({    
		    onSubmit: function(){    
		    	var isValid = $(this).form('validate');
				return isValid;	// 返回false终止表单提交
		    },    
		    success:function(data){   
		    	successTip(data,repair_dg,repair_dlg);
		    }    
		}); 
		
	});		

</script>
</body>
</html>