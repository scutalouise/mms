<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>
<body>
<div>
	<form id="mainform" action="${ctx }/device/peDevice/${action}" method="post">
		<table class="formTable">
			<tr>
				<td width="100px">动环设备名字：</td>
				<td>
					<input type="hidden" name="id" value="${id }"/>
					<input name="version" type="hidden" value="${peDevice.version }"/>
					<input id="name" name="name" class="easyui-validatebox" data-options="width: 150,required:'required',validType:'length[0,50]'" value="${peDevice.name }"> 
				</td>
			</tr>
			<tr>
				<td>型号：</td>
				<td><input name="model" type="text" value="${peDevice.model }" class="easyui-validatebox" data-options="width: 150,validType:'length[0,50]'"/></td>
			</tr>
	        <tr>
				<td>设备类型选择：</td>
				<td>
					<input id="deviceType" name="dhDeviceType" type="text" value="${peDevice.dhDeviceType }" class="easyui-combobox" data-options="width:150,editable:false"/>
				</td>
			</tr>
	         <tr>
				<td>设备类型接口：</td>
				<td>
					<input id="deviceInterfaceType" name="dhDeviceInterfaceType" type="text" value="${peDevice.dhDeviceInterfaceType }" class="easyui-combobox" data-options="width:150,editable:false"/>
				</td>
			</tr>
			<tr>
				<td>设备接口序号：</td>
				<td><input name="dhDeviceIndex" type="text" value="${peDevice.dhDeviceIndex }" class="easyui-validatebox" data-options="width: 150,validType:'integer'"/></td>
			</tr>
			<tr>
				<td>采购批次：</td>
				<td>
					<input type="hidden" id="purchaseId" name="purchaseId" value="${peDevice.purchaseId }"/>
					<input id="purchaseName" name="purchaseName" type="text" value="${peDevice.purchaseName }" class="easyui-validatebox" data-options="required:'required',width: 150" onfocus="selectPurchase()" readonly='readonly'/>
				</td>
			</tr>
			<tr>
				<td>运维方式：</td>
				<td>
					<input type="radio" name="maintainWay" value="INNER"/><label">内部运维&nbsp;&nbsp;&nbsp;&nbsp;</label>
					<input type="radio" name="maintainWay" value="OUTER"/><label>外部运维</label>
				</td>			
			</tr>
			<tr>
				<td>运维组织：</td>
				<td>
				    <input name="maintainOrgName" id="maintainOrgName"  type="hidden" value="${hostDevice.maintainOrgName }"/>
				    <input name="maintainOrgId" id="maintainOrgId"  value="${hostDevice.maintainOrgId }" class="easyui-combobox" data-options="width: 200,editable:false"/>
				</td>
			</tr>
			<tr>
				<td>是否禁用：</td>
				<td>
					<input type="radio" name="enable" value="DISABLED"/><label">是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
					<input type="radio" name="enable" value="ENABLED"/><label>否</label>
				</td>			
			</tr>
			<tr>
				<td>采购日期：</td>
				<td><input id='manufactureDate' name="manufactureDate" type="text" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width: 150" value="<fmt:formatDate value="${peDevice.manufactureDate}"/>"/></td>
			</tr>
			<tr>
				<td>保修日期(截止)：</td>
				<td><input name="warrantyDate" type="text" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width: 150" value="<fmt:formatDate value="${peDevice.warrantyDate}"/>"/></td>
			</tr>
			<tr>
				<td>其他描述：</td>
				<td><textarea rows="3" class="easyui-validatebox" cols="41" name="remark" style="font-size: 12px;font-family: '微软雅黑'" class="easyui-validatebox" data-options="validType:'length[0,200]'">${peDevice.remark}</textarea></td>
			</tr>
		</table>
	</form>
</div>
<!-- 处理采购的弹出菜单 --> 
<div id="devicePurchase_dialog"></div> 
<script type="text/javascript">
var devicePurchase_dialog;
var action="${action}";
//初始化的时候，将操作系统从枚举类里初始化
$(function(){
	//内部外部运维方式的事件处理：
	$("input[name='maintainWay']").change(function(){
		$("input[name='maintainWay']").each(function(a){
			//alert($(this).val());
			if($(this)[0].checked == true){
				if($(this).val() == 'INNER'){
					$('#maintainOrgId').combobox({ disabled: true });
					$('#maintainOrgName').combobox({ disabled: true });
				}else{
					$.ajax({
				 		type:'get',
				 		url:"${ctx}/device/devicePurchase/purchaseDetail/"+ $("#purchaseId").val(),
				 		success: function(data){
				 			var maintainOrgIdTemp = data[0].maintainOrgId;
			 				$('#maintainOrgId').combotree({
			 					method:'get',
			 					url:'${ctx}/device/supplyMaintainOrg/json',
			 					idField:'id',
			 					textFiled:'orgName',
			 				    required:true,
			 				    onLoadSuccess:function(data){
			 				    	$('#maintainOrgId').combotree("setValue",maintainOrgIdTemp);//加载完之后选中哪个值；并且允许修改
			 				    },
			 				    onSelect:function(data){
			 				    	$('#maintainOrgName').val(data.text);
			 				    }
			 				 })
			 				//parent.$.messager.show({ title : "错误",msg: "您所选择的采购记录中没有确定运维方式", position: "bottomRight" });
			 				$('#maintainOrgId').combotree({ disabled: true });
							$('#maintainOrgName').attr({ disabled: true });
				 		   }
				 		})
				};
			}
		});
	}) 
	
	//ajax处理动环设备类型与接口选择的联动；
	 $('#deviceType').combobox({
		  method:"get",
		  url:'${ctx}/device/peDevice/dhDeviceType',
		  valueField:'deviceType',
		  textField:'value',
		  onSelect:function(data){
			  var deviceType=data.deviceType;
			  $('#deviceInterfaceType').combobox({
				  method:"get",
				  url:'${ctx}/device/peDevice/deviceInterfaceType?deviceType='+deviceType
			 }).combobox('clear');
		  }
	 });
	 $('#deviceInterfaceType').combobox({
	     valueField:'deviceInterfaceType',
	     textField:'name'
	 });
	 //日期的处理
	 initDateFilter(null,'manufactureDate');
})

//动环 添加修改有所区分
if(action=='create'){
	$("input[name='enable'][value=${enable}]").attr("checked",true); 
}else if(action=='update'){
	$("input[name='enable'][value=${peDevice.enable}]").attr("checked",true);
	$('#deviceInterfaceType').combobox({
		method:"get",
		url:'${ctx}/device/peDevice/deviceInterfaceType?deviceType=${peDevice.dhDeviceType}'
	});
	var maintainWayTemp = "${peDevice.maintainWay}";
	if(maintainWayTemp==null ||maintainWayTemp == ""){
		parent.$.messager.show({ title : "错误",msg: "当前设备没有设置相应的运维方式！", position: "bottomRight" });
	}else{
		$("input[name='maintainWay'][value='"+maintainWayTemp+"']").attr("checked",true);
		if(maintainWayTemp == "OUTER"){
				$('#maintainOrgId').combotree({
					method:'get',
					url:'${ctx}/device/supplyMaintainOrg/json',
					idField:'id',
					textFiled:'orgName',
				    required:true,
				    onLoadSuccess:function(data){
				    	$('#maintainOrgId').combotree("setValue","${peDevice.maintainOrgId}");//加载完之后选中哪个值；并且允许修改
				    },
				    onSelect:function(data){
				    	$('#maintainOrgName').val(data.text);
				    }
				 })
			}else if(maintainWayTemp == "INNER"){
				$('#maintainOrgId').combotree({ disabled: true });
				$('#maintainOrgName').attr({ disabled: true });
		}
	}
}

//提交表单
$('#mainform').form({    
    onSubmit: function(){
    	var outerRadio = $("input[name='maintainWay'][value='OUTER']");
    	if(outerRadio[0].checked == true && $('#maintainOrgId').combotree('getValue') == ''){//选中外部运维方式却没有选择一个运维机构
    		parent.$.messager.show({ title : "错误",msg: "您选择外部运维却没有选择外部运维的组织！", position: "bottomRight" });
    		return false;
    	}
    	var isValid = $(this).form('validate');
		return isValid;	// 返回false终止表单提交
    },    
    success:function(data){   
    	successTip(data,peDevice_datagrid,peDevice_dialog);
    }    
});    

//对采购记录选择；
function selectPurchase(){
	$.ajaxSetup({type : 'GET'});
	//此处设置的devicePurchase_dialog，便于在采购窗口双击时，获取到采购的Panel并进行其他的操作；
	devicePurchase_dialog=$("#devicePurchase_dialog").dialog({   
		    title: '采购记录选择',    
		    width: 580,    
		    height: 420,  
		    href:'${ctx}/device/devicePurchase/purchaseList',
		    maximizable:true,
		    modal:true,
		    buttons:[{
				text:'确认',
				handler:function(){
					//将选中的值赋值给主机新增窗口中的采购记录；
					getSelectPurchase();
					devicePurchase_dialog.panel('close');
				}
			},{
				text:'取消',
				handler:function(){
					devicePurchase_dialog.panel('close');
				}
			}]
	});
}
</script>
</body>
</html>