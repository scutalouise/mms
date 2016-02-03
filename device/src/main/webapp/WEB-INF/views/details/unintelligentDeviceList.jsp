<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
</head>
<body>
<!---------------------------------------- toolsBar Start ---------------------------------------->	
	<div id="unintelligentDevice_toolBar" style="padding:5px;height:auto">
		<div>
	       	<form id="unintelligent_searchFrom" action="">
	      	        <input type="text" name="filter_LIKES_name" class="easyui-validatebox" data-options="width:150,prompt: '非智能设备名字'"/>
	      	        <input type="text" name="filter_LIKES_model" class="easyui-validatebox" data-options="width:150,prompt: '型号'"/>
		        <input type="text" id="unintelligent_startDate" name="filter_GTD_createDate" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width:150,prompt: '开始日期(创建)'" />
		        - <input type="text" id="unintelligent_endDate" name="filter_LTD_createDate" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width:150,prompt: '结束日期(创建)'"/>
		        <span class="toolbar-item dialog-tool-separator"></span>
		        <a href="javascript(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="queryUnintelligent()">查询</a>
			</form>
			
	       		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addUnintelligent();">添加</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-options="disabled:false" onclick="delUnintelligent()">删除</a>
	        	<span class="toolbar-item dialog-tool-separator"></span>
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updateUnintelligent()">修改</a>
	            <span class="toolbar-item dialog-tool-separator"></span>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="userForUnintelligentDevice()">网点管理员</a>
	            <span class="toolbar-item dialog-tool-separator"></span>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="roleForUnintelligentDevice()">设备运维角色</a>
	            <span class="toolbar-item dialog-tool-separator"></span>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="userDefineTypeUnintelligent()">自定义分类</a>
	            <span class="toolbar-item dialog-tool-separator"></span>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-print" plain="true" onclick="unintelligent_QR()">二维码</a>
	       </div> 
	</div>
<!---------------------------------------- toolsBar End ---------------------------------------->	
<!---------------------------------------- MainTable Start ---------------------------------------->
<table id="unintelligentDevice_datagrid" name="forSelectedDataGrid"></table> 
<div id="unintelligentDevice_dialog"></div>  		
<!---------------------------------------- MainTable End ---------------------------------------->
<script type="text/javascript">
var unintelligentDevice_datagrid;
var unintelligentDevice_dialog;
$(function(){   
	unintelligentDevice_datagrid=$('#unintelligentDevice_datagrid').datagrid({    
		method: "post",
	    url:'${ctx}/device/unintelligentDevice/json', 
	    queryParams:{filter_EQI_organizationId:orgId},
	    fit : true,
		fitColumns : true,
		border : false,
		idField : 'id',
		striped:true,
		pagination:true,
		rownumbers:true,
		pageNumber:1,
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50 ],
		singleSelect:true,
	    columns:[[    
	        {field:'id',title:'id',hidden:true},    
	        {field:'identifier',title:'识别码',sortable:true,width:100},    
	        {field:'manufactureDate',title:'生产日期',sortable:true,width:100,formatter: function(value,row,index){
	        	return formatDate(value,"yyyy-MM-dd")
	        }},
	        {field:'enable',title:'是否可用',sortable:true,width:50,
	        	formatter : function(value, row, index) {
	       			return value=='ENABLED'?'启用':'禁用';
	        	}
	        },
	        {field:'name',title:'名字',sortable:true,width:100},
	        {field:'model',title:'型号',sortable:true,width:100},
	        {field:'updateTime',title:'最近更新时间',sortable:true,width:100}
	    ]],
	    headerContextMenu: [
	        {
	            text: "冻结该列", disabled: function (e, field) { return dg.datagrid("getColumnFields", true).contains(field); },
	            handler: function (e, field) { dg.datagrid("freezeColumn", field); }
	        },
	        {
	            text: "取消冻结该列", disabled: function (e, field) { return dg.datagrid("getColumnFields", false).contains(field); },
	            handler: function (e, field) { dg.datagrid("unfreezeColumn", field); }
	        }
	    ],
	    enableHeaderClickMenu: true,
	    enableHeaderContextMenu: true,
	    enableRowContextMenu: false,
	    toolbar:'#unintelligentDevice_toolBar'
	});
	
	initDateFilter("unintelligent_startDate","unintelligent_endDate");
});

//二维码
function unintelligent_QR(){ 
	row=unintelligentDevice_datagrid.datagrid('getSelected');
	if(rowIsNull(row)) return;
	unintelligentDevice_dialog=$("#unintelligentDevice_dialog").dialog({
		title:'二维码预览与打印',
		width: 300,    
	    height: 300,
	    top:100,
	    iconCls:'icon-print',
		href:'${ctx}/device/twoDimentionCode?id='+row.id+'&identifier='+row.identifier+'&rand='+Math.random(),
		maxmizable:true,
		modal:true,
		onClose:function(){
	    	appendAndRemoveUnintelligent("unintelligentDevice_dialog");
	    },
		buttons:[{
			text:'打印',
			handler:function(){
				printTwoDimentionCode(unintelligentDevice_dialog,row.identifier,"print");
			}
		},{
			text:'预览',
			handler:function(){
				printTwoDimentionCode(unintelligentDevice_dialog,row.identifier,"preview");
			}
		},{
			text:'取消',
			handler:function(){
				unintelligentDevice_dialog.panel('close');
			}
		}]
	});
}

//创建查询对象并查询
function queryUnintelligent(){
	var obj=$("#unintelligent_searchFrom").serializeObject();
	unintelligentDevice_datagrid.datagrid('load',obj); 
}

//弹窗添加非智能设备
function addUnintelligent() {
	unintelligentDevice_dialog=$("#unintelligentDevice_dialog").dialog({   
		    title: '添加非智能设备',    
		    width: 450,    
		    height: 360,    
		    href:'${ctx}/device/unintelligentDevice/create',
		    maximizable:true,
		    modal:true,
		    onClose:function(){
		    	appendAndRemoveUnintelligent("unintelligentDevice_dialog");
		    },
		    buttons:[{
				text:'确认',
				handler:function(){
					$("#mainform").submit(); 
				}
			},{
				text:'取消',
				handler:function(){
					unintelligentDevice_dialog.panel('close');
				}
			}]
	});
}

//删除
function delUnintelligent(){
	var row = unintelligentDevice_datagrid.datagrid('getSelected');
	if(rowIsNull(row)) return;
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/device/unintelligentDevice/delete/"+row.id,
				success: function(data){
					successTip(data,unintelligentDevice_datagrid);
					unintelligentDevice_datagrid.treegrid('clearSelections');
				}
			});
		} 
	});
}

//弹窗修改
function updateUnintelligent(){
	var row = unintelligentDevice_datagrid.datagrid('getSelected');
	if(rowIsNull(row)) return;
	unintelligentDevice_dialog=$("#unintelligentDevice_dialog").dialog({   
	    title: '修改非智能设备',    
	    width: 450,    
	    height: 360,    
	    href:'${ctx}/device/unintelligentDevice/update/'+row.id,
	    maximizable:true,
	    modal:true,
	    onClose:function(){
	    	appendAndRemoveUnintelligent("unintelligentDevice_dialog");
	    },
	    buttons:[{
			text:'修改',
			handler:function(){
				$('#mainform').submit(); 
			}
		},{
			text:'取消',
			handler:function(){
					unintelligentDevice_dialog.panel('close');
				}
		}]
	});
}

//弹窗设置网点管理员
function userForUnintelligentDevice(){
	var row = unintelligentDevice_datagrid.datagrid('getSelected');
	if(rowIsNull(row)) return;
	$.ajaxSetup({type : 'GET'});
		unintelligentDevice_dialog=$("#unintelligentDevice_dialog").dialog({   
		    title: '非智能设备管理员设置',    
		    width: 720,    
		    height: 420,  
		    href:'${ctx}/device/details/users', 
		    maximizable:true,
		    modal:true,
		    onClose:function(){
		    	appendAndRemoveUnintelligent("unintelligentDevice_dialog");
		    },
		    buttons:[{
				text:'确认',
				handler:function(){
					saveDeviceUser(unintelligentDevice_datagrid,"${ctx}/device/unintelligentDevice/setDeviceUser");//执行保存的步骤
					unintelligentDevice_dialog.panel('close');
				}
			},{
				text:'取消',
				handler:function(){
					unintelligentDevice_dialog.panel('close');
				}
			}]
		});
}

//弹窗设置运维角色
function roleForUnintelligentDevice(){
	var row = unintelligentDevice_datagrid.datagrid('getSelected');
	if(rowIsNull(row)) return;
	$.ajaxSetup({type : 'GET'});
	unintelligentDevice_dialog=$("#unintelligentDevice_dialog").dialog({   
	    title: '非智能设备运维角色设置',    
	    width: 580,    
	    height: 350,  
	    href:'${ctx}/device/details/roles',
	    maximizable:true,
	    modal:true,
	    onClose:function(){
	    	appendAndRemoveUnintelligent("unintelligentDevice_dialog");
	    },
	    buttons:[{
			text:'确认',
			handler:function(){
				saveDeviceRole(unintelligentDevice_datagrid,"${ctx}/device/unintelligentDevice/setDeviceRole");//执行保存运维角色的操作
				unintelligentDevice_dialog.panel('close');
			}
		},{
			text:'取消',
			handler:function(){
				unintelligentDevice_dialog.panel('close');
			}
		}]
	});
}

//弹窗设置自定义分类
function userDefineTypeUnintelligent(){
	var row = unintelligentDevice_datagrid.datagrid('getSelected');
	if(rowIsNull(row)) return;
	$.ajaxSetup({type : 'GET'});
	unintelligentDevice_dialog=$("#unintelligentDevice_dialog").dialog({   
	    title: '非智能设备自定义分类设置',    
	    width: 580,    
	    height: 400,  
	    href:'${ctx}/device/details/userDeviceType',
	    maximizable:true,
	    modal:true,
	    onClose:function(){
	    	appendAndRemoveUnintelligent("unintelligentDevice_dialog");
	    },
	    buttons:[{
			text:'确认',
			handler:function(){
				saveUserDefineTypes(unintelligentDevice_datagrid,"${ctx}/device/unintelligentDevice/setUserDeviceType");//执行保存用户自定义设备的操作
				unintelligentDevice_dialog.panel('close');
			}
		},{
			text:'取消',
			handler:function(){
				unintelligentDevice_dialog.panel('close');
			}
		}]
	});
}
/**
 * 处理div混淆；
 */
function appendAndRemoveUnintelligent(divId){
	$("#" +divId + "").dialog("destroy").remove(); //直接摧毁、移除
	$("<div id='"+ divId +"'></div> ").appendTo($('body'))//新加入一个
}
</script>
</body>
</html>