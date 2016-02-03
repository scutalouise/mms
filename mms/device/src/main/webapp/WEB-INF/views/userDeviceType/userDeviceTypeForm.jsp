<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<div>
	<form id="mainform" action="${ctx }/device/userDeviceType/${action}" method="post">
      <table class="formTable" style="border-spacing:10px;">
         <tr>
            <td>设备类型名：</td>
            <td>
              <input name="id" type="hidden" value="${id }"/>
              <input name="version" type="hidden" value="${userDeviceType.version }"/>
              <input name="name" id="name" class="easyui-validatebox" value="${userDeviceType.name }" data-options="width:150,required:'required'"/><br/>
            </td>           
         </tr>
         
         <tr>
			<td>上级设备类型：</td>
			<td>
				<input id="pid" name="pid" type="text" value="${userDeviceType.pid }" class="easyui-combobox" data-options="width:150,editable:false"/>
			</td>
		</tr>
   
         <tr>
            <td>描述：</td>
            <td>
                <textarea name="otherNote" class="easyui-validatebox" cols="19" rows="3" >${userDeviceType.otherNote}</textarea>
            </td>           
         </tr>
         
      </table>
   </form>
</div>
<script type="text/javascript">
var action="${action}";

   $(function(){
	  $('#pid').combotree({
		  method:'get',
		  url:'${ctx}/device/userDeviceType/json',
		  idField : 'id',
		  textFiled:'name',
		  parentField:'pid',
		  animate:true
	  })
  }); 
  
  $('#mainform').form({
	  onSubmit:function(){
		  var valid=$(this).form('validate');
		  return valid;
	  },
      success: function(data){
    	  successTip(data,dg,dlg);
    	  dg.treegrid('reload');
      }
  })
</script>
</body>
</html>