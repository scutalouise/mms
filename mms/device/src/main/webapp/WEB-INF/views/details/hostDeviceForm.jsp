<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>
<body>
<div>
	<form id="mainform" action="${ctx }/device/hostDevice/${action}" method="post">
		<table class="formTable">
			<tr>
				<td width="100px">主机名字：</td>
				<td>
					<input type="hidden" name="id" value="${id }"/>
					<input name="version" type="hidden" value="${hostDevice.version }"/>
					<input id="name" name="name" class="easyui-validatebox" data-options="width: 150,required:'required',validType:'length[0,50]'" value="${hostDevice.name }"> 
				</td>
			</tr>
			<tr>
				<td>授权码：</td>
				<td><input name="authorizationCode" type="text" value="${hostDevice.authorizationCode }" class="easyui-validatebox" data-options="width: 150,validType:'length[0,50]'"/></td>
			</tr>
			<tr>
				<td>IP地址：</td>
				<td><input name="ip" type="text" value="${hostDevice.ip }" class="easyui-validatebox" data-options="width: 150,validType:'ip'"/></td>
			</tr>
			<tr>
				<td>型号：</td>
				<td><input name="model" type="text" value="${hostDevice.model }" class="easyui-validatebox" data-options="width: 150,validType:'length[0,50]'"/></td>
			</tr>
			<tr>
				<td>操作系统：</td>
				<td><input name="oSType" id="oSType" class="easyui-combobox" value="${hostDevice.oSType }" data-options="width:150,required:'required'"/></td>
			</tr>
			<tr>
				<td>系统位数：</td>
				<td>
					<input type="radio" id="low" name="oSBits" value="32"/><label for="low">32位</label>
					<input type="radio" id="high" name="oSBits" value="64"/><label for="high">64位</label>
				</td>			
			</tr>
			<tr>
				<td>CPU型号：</td>
				<td><input name="cpu" type="text" value="${hostDevice.cpu }" class="easyui-validatebox" data-options="width: 150,validType:'length[0,50]'"/></td>
			</tr>
			<tr>
				<td>硬盘型号：</td>
				<td><input name="harddisk" type="text" value="${hostDevice.harddisk }" class="easyui-validatebox" data-options="width: 150,validType:'length[0,50]'"/></td>
			</tr>
			<tr>
				<td>主板型号：</td>
				<td><input name="motherboard" type="text" value="${hostDevice.motherboard }" class="easyui-validatebox" data-options="width: 150,validType:'length[0,50]'"/></td>
			</tr>
			<tr>
				<td>内存型号：</td>
				<td><input name="memory" type="text" value="${hostDevice.memory }" class="easyui-validatebox" data-options="width: 150,validType:'length[0,50]'"/></td>
			</tr>
			<tr>
				<td>采购批次：</td>
				<td>
					<input type="hidden" id="purchaseId" name="purchaseId" value="${hostDevice.purchaseId }"/>
					<input id="purchaseName" name="purchaseName" type="text" value="${hostDevice.purchaseName }" class="easyui-validatebox" data-options="required:'required',width: 150" onfocus="selectPurchase()" readonly='readonly' />
				</td>
			</tr>
			<tr>
				<td>运维角色：</td>
				<td>
					<input type="hidden" id="roleId" name="roleId" value="${hostDevice.roleId }"/>
					<input id="roleName" name="roleName" type="text" value="${hostDevice.roleName }" class="easyui-validatebox" data-options="required:'required',width: 150" onfocus="roleForDevice()" readonly='readonly' />
				</td>
			</tr>
			
			<tr>
				<td>隶属网点：</td>
				<td><input id="host_organizationId" name="organizationId" type="text" value="${hostDevice.organizationId }" data-options="required:true,width: 150" readonly='readonly'/></td>
			</tr>
			<tr>
				<td>是否禁用：</td>
				<td>
					<input type="radio" name="enable" value="DISABLED"/><label">是</label>
					<input type="radio" name="enable" value="ENABLED"/><label>否</label>
				</td>			
			</tr>
			<tr>
				<td>生产日期：</td>
				<td><input id="manufactureDate" name="manufactureDate" type="text" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width: 150" value="<fmt:formatDate value="${hostDevice.manufactureDate}"/>"/></td>
			</tr>
			<tr>
				<td>保修日期(截至)：</td>
				<td><input name="warrantyDate" type="text" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width: 150" value="<fmt:formatDate value="${hostDevice.warrantyDate}"/>"/></td>
			</tr>
			<tr>
				<td>其他描述：</td>
				<td><textarea rows="3" class="easyui-validatebox" cols="41" name="remark" style="font-size: 12px;font-family: '微软雅黑'" class="easyui-validatebox" data-options="validType:'length[0,200]'">${hostDevice.remark}</textarea></td>
			</tr>
		</table>
	</form>
</div>
<!-- 处理采购的弹出菜单 --> 
<div id="devicePurchase_dialog"></div> 
<!-- 处理运维角色的弹出菜单 --> 
<div id="role_dialog"></div> 
<script type="text/javascript">
var devicePurchase_dialog;
var role_dialog;

var action="${action}";
//初始化的时候，将操作系统从枚举类里初始化
$(function(){
	//操作系统类型，从后台枚举中读取
	$('#oSType').combobox({
		  method:"get",
		  url:'${ctx}/device/details/osEnum',
		  valueField:'osEnum',
		  textField:'osEnum',
		  onSelect:function(data){
			  //console.info(data);
		  }
	});
	//机构树形结构从后台读取；
	//上级菜单
	$('#host_organizationId').combotree({
		width:150,
		method:'GET',
	    url: '${ctx}/system/organization/json',
	    idField : 'id',
	    textFiled : 'orgName',
		parentField : 'pid',
	    animate:true
	});  
	//日期初始化的时候，必须要小于当前时间：
	initDateFilter(null,'manufactureDate');
})


//主机 添加修改有所区分
if(action=='create'){
	$("input[name='oSBits'][value=${oSBits}]").attr("checked",true); 
	$("input[name='enable'][value=${enable}]").attr("checked",true); 
}else if(action=='update'){
	$("input[name='oSBits'][value=${hostDevice.oSBits}]").attr("checked",true);
	$("input[name='enable'][value=${hostDevice.enable}]").attr("checked",true);
}

//提交表单
$('#mainform').form({    
    onSubmit: function(){    
    	var isValid = $(this).form('validate');
		return isValid;	// 返回false终止表单提交
    },    
    success:function(data){   
    	successTip(data,hostDevice_datagrid,hostDevice_dialog);
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

//弹窗设置运维角色
function roleForDevice(){
	$.ajaxSetup({type : 'GET'});
	role_dialog=$("#role_dialog").dialog({   
	    title: '主机设备运维角色设置',    
	    width: 580,    
	    height: 350,  
	    href:'${ctx}/device/details/roles',
	    maximizable:true,
	    modal:true,
	    buttons:[{
			text:'确认',
			handler:function(){
				getDeviceRole();
				role_dialog.panel('close');
			}
		},{
			text:'取消',
			handler:function(){
				role_dialog.panel('close');
			}
		}]
	});
	
}
</script>
</body>
</html>