<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<div>
	<form id="mainform" action="${ctx }/device/brand/${action}" method="post">
      <table class="formTable" style="border-spacing:10px;">        
         <tr>
            <td>一级设备类型：</td>
            <td>
               <input name="firstDeviceType" id="firstDeviceType" class="easyui-combobox" value="${brand.firstDeviceType }" data-options="width:150,required:'required',editable:false"/>
            </td>           
         </tr>
         
         <tr>
            <td>二级设备类型：</td>
            <td>
              <input name="secondDeviceType" id="secondDeviceType" class="easyui-combobox" value="${brand.secondDeviceType }" data-options="width:150,required:'required',editable:false"/>
            </td>           
         </tr>
         
         <tr>
            <td>品牌名：</td>
            <td>
              <input name="id" type="hidden" value="${id }"/>
              <input name="version" type="hidden" value="${brand.version }"/>
              <input name="name" id="brandName" class="easyui-validatebox" value="${brand.name }"data-options="width:150,required:'required',validType:['validName','length[2,10]']"/><br/>
            </td>           
         </tr>
         
         <tr>
            <td>是否启用：</td>
            <td>
              <label for="enable"><input type="radio" id="enable" name="enable" value="ENABLED"/>是</label>
			  <label for="disable"><input type="radio" id="disable" name="enable" value="DISABLED"/>否</label>
			  <!-- <select name="enable" id="enable" class="easyui-combobox" style="width: 150px;">
					<option value="ENABLED">启用</option>
					<option value="DISABLED">禁用</option>
			  </select>  -->
            </td>           
         </tr>
         
          <tr>
            <td>是否初始化数据：</td>
            <td>
              <label for="yes"><input type="radio" id="yes" name="isInitial" value="INIT"/>是</label>
			  <label for="no"><input type="radio" id="no" name="isInitial" value="UNINIT"/>否</label>
			  <!-- <select name="isInitial" id="isInitial" class="easyui-combobox" style="width: 150px;">
					<option value="INIT">是</option>
					<option value="UNINIT">否</option>
			  </select> -->
            </td>           
         </tr>
          
         <tr>
            <td>描述：</td>
            <td>
              <textarea rows="2" cols="19" name="otherNote" class="easyui-validatebox">${brand.otherNote }</textarea>
            </td>           
         </tr>
      </table>
   </form>
</div>
<script type="text/javascript">
	var action="${action}";
	
	if(action=="add"){
		  $("input[name='enable'][value=ENABLED]").attr("checked",true);
		  $("input[name='isInitial'][value=UNINIT]").attr("checked",true);
	}
	if(action=="update"){
		  $("input[name='enable'][value=${brand.enable}]").attr("checked",true);
		  $("input[name='isInitial'][value=${brand.isInitial}]").attr("checked",true);
		  $('#secondDeviceType').combobox({
			  method:"get",
			  url:'${ctx}/device/brand/secondDeviceType?firstDeviceType=${brand.firstDeviceType}'
		  });
	}
 
  $.extend($.fn.validatebox.defaults.rules, {
      validName: {
	         validator: function(value){
	            var flag=false;
	            $.ajax({ 
	            	  url: '${ctx}/device/brand/checkBrandName', 
	            	  data: {'id':$("input[name='id']").val(),'brandName':$('#brandName').val(),'secondDeviceType':$('#secondDeviceType').combobox('getValue')},
	  				  async : false, 
	  				  dateType : 'json',
	  				  type:'post',
	            	  success:function(data){
	            		     flag=data;
	            		  }
	            });
	              return flag;
	          },
	          message: "品牌名已存在"
        }
}); 
	
  $(function(){ 
	  $('#firstDeviceType').combobox({
		  method:"get",
		  url:'${ctx}/device/brand/firstDeviceType',
		  valueField:'firstDeviceType',
		  textField:'name',
		  onSelect:function(data){
			  var firstDeviceType=data.firstDeviceType;
			  $('#secondDeviceType').combobox({
				  method:"get",
				  url:'${ctx}/device/brand/secondDeviceType?firstDeviceType='+firstDeviceType
			 }).combobox('clear');
		  }
	  });
	  $('#secondDeviceType').combobox({
	      valueField:'secondDeviceType',
	      textField:'name'
	  });
  });
  
  $('#mainform').form({
		  onSubmit:function(){
			  var valid=$(this).form('validate');
			  return valid;
		   },
		  success:function(data) {
			    if(data=="success"){
			       parent.$.messager.show({ title : "提示",msg: "操作成功！", position: "bottomRight" });
			       if(dg!=null)
						dg.datagrid('reload');
				   if(dlg!=null)
						dlg.panel('close');
			    }else{
			       parent.$.messager.alert('提示','数据已被绑定不能修改','info');
			    }
		  }
  });
  
</script>
</body>
</html>