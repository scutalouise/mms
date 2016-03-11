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
	<div id="peDevice_toolBar" style="padding:5px;height:auto">
		<div>
	       	<form id="pe_searchFrom" action="">
	      	        <input type="text" name="filter_LIKES_name" class="easyui-validatebox" data-options="width:150,prompt: '动环设备名'"/>
	      	        <input type="text" name="filter_LIKES_model" class="easyui-validatebox" data-options="width:150,prompt: '型号'"/>
		        <input type="text" id="pe_startDate" name="filter_GTD_createDate" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width:150,prompt: '开始日期(创建)'" />
		        - <input type="text" id="pe_endDate" name="filter_LTD_createDate" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width:150,prompt: '结束日期(创建)'"/>
		        <span class="toolbar-item dialog-tool-separator"></span>
		        <a href="javascript(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="queryPe()">查询</a>
			</form>
			
	       		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addPe();">添加</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-options="disabled:false" onclick="delPe()">删除</a>
	        	<span class="toolbar-item dialog-tool-separator"></span>
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updatePe()">修改</a>
	            <span class="toolbar-item dialog-tool-separator"></span>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="userForPeDevice()">网点管理员</a>
	            <span class="toolbar-item dialog-tool-separator"></span>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="userDefineTypePe()">自定义分类</a>
	            <span class="toolbar-item dialog-tool-separator"></span>
     	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="alarmTemplatePe()">告警模板</a>
	            <span class="toolbar-item dialog-tool-separator"></span>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-print" plain="true" onclick="pe_QR()">二维码</a>
	       		
	       		<span class="toolbar-item dialog-tool-separator"></span>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-drive-burn" id="peScrapped" plain="true" onclick="peScrapped()">设备报废</a>
	       		
	       </div> 
	</div>
<!---------------------------------------- toolsBar End ---------------------------------------->	
<!---------------------------------------- MainTable Start ---------------------------------------->
<table id="peDevice_datagrid" name="forSelectedDataGrid"></table> 
<div id="peDevice_dialog"></div>  		
<!---------------------------------------- MainTable End ---------------------------------------->
<script type="text/javascript">
var peDevice_datagrid;
var peDevice_dialog;
$(function(){  
	$("#peScrapped").css({
		visibility : "hidden"
	});
	peDevice_datagrid=$('#peDevice_datagrid').datagrid({    
		method: "post",
	    url:'${ctx}/device/peDevice/json', 
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
	        {field:'name',title:'设备名称',sortable:true,width:100}, 
	        {field:'identifier',title:'识别码',sortable:true,width:100},    
	        {field:'manufactureDate',title:'采购日期',sortable:true,width:100,formatter: function(value,row,index){
	        	return formatDate(value,"yyyy-MM-dd")
	        }},
	        {field:'enable',title:'是否可用',sortable:true,width:50,
	        	formatter : function(value, row, index) {
	       			return value=='ENABLED'?'启用':'禁用';
	        	}
	        },
	        
	        {field:'model',title:'型号',sortable:true,width:100},
	        {field:'deviceUsedState',title:"设备状态",sortable:true,width:50,align:"center",formatter:function(v){
	        	if(v!=null){
	        		return v.value;
	        	}
	        }},
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
	    toolbar:'#peDevice_toolBar',
	    onSelect:function(rowIndex,rowData){
	    	if(rowData.deviceUsedState.value=="坏件"){
	    		$("#peScrapped").css({
					visibility : "visible"
				});
	    	}else{
	    		$("#peScrapped").css({
					visibility : "hidden"
				});
	    	}
	    }
	});
	
	initDateFilter("pe_startDate","pe_endDate");
});

//二维码
function pe_QR(){ 
	row=peDevice_datagrid.datagrid('getSelected');
	if(rowIsNull(row)) return;
	peDevice_dialog=$("#peDevice_dialog").dialog({
		title:'二维码预览与打印',
		width: 300,    
	    height: 300,
	    top:100,
	    iconCls:'icon-print',
		href:'${ctx}/device/twoDimentionCode?id='+row.id+'&identifier='+row.identifier+'&rand='+Math.random(),
		maxmizable:true,
		modal:true,
		onClose:function(){
			appendAndRemovePe("peDevice_dialog");
	    },
	    buttons:[{
			text:'打印',
			handler:function(){
				printTwoDimentionCode(peDevice_dialog,row.identifier,"print");
			}
		},{
			text:'预览',
			handler:function(){
				printTwoDimentionCode(peDevice_dialog,row.identifier,"preview");
			}
		},{
			text:'取消',
			handler:function(){
				peDevice_dialog.panel('close');
			}
		}]
	});
}

//创建查询对象并查询
function queryPe(){
	var obj=$("#pe_searchFrom").serializeObject();
	peDevice_datagrid.datagrid('load',obj); 
}

//弹窗添加动环设备
function addPe() {
	peDevice_dialog=$("#peDevice_dialog").dialog({   
		    title: '添加动环设备',    
		    width: 450,    
		    height: 460,    
		    href:'${ctx}/device/peDevice/create',
		    maximizable:true,
		    modal:true,
		    onClose:function(){
		    	appendAndRemovePe("peDevice_dialog");
		    },
		    buttons:[{
				text:'确认',
				handler:function(){
					$("#mainform").submit(); 
				}
			},{
				text:'取消',
				handler:function(){
					peDevice_dialog.panel('close');
				}
			}]
	});
}

//删除
function delPe(){
	var row = peDevice_datagrid.datagrid('getSelected');
	if(rowIsNull(row)) return;
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/device/peDevice/delete/"+row.id,
				success: function(data){
					successTip(data,peDevice_datagrid);
					peDevice_datagrid.treegrid('clearSelections');
				}
			});
		} 
	});
}

//弹窗修改
function updatePe(){
	var row = peDevice_datagrid.datagrid('getSelected');
	if(rowIsNull(row)) return;
	peDevice_dialog=$("#peDevice_dialog").dialog({   
	    title: '修改动环设备',    
	    width: 450,    
	    height: 460,    
	    href:'${ctx}/device/peDevice/update/'+row.id,
	    maximizable:true,
	    modal:true,
	    onClose:function(){
	    	appendAndRemovePe("peDevice_dialog");
	    },
	    buttons:[{
			text:'修改',
			handler:function(){
				$('#mainform').submit(); 
			}
		},{
			text:'取消',
			handler:function(){
					peDevice_dialog.panel('close');
				}
		}]
	});
}

//弹窗设置网点管理员
function userForPeDevice(){
	var row = peDevice_datagrid.datagrid('getSelected');
	if(rowIsNull(row)) return;
	$.ajaxSetup({type : 'GET'});
		peDevice_dialog=$("#peDevice_dialog").dialog({   
		    title: '动环设备管理员设置',    
		    width: 720,    
		    height: 420,  
		    href:'${ctx}/device/details/users', 
		    maximizable:true,
		    modal:true,
		    onClose:function(){
		    	appendAndRemovePe("peDevice_dialog");
		    },
		    buttons:[{
				text:'确认',
				handler:function(){
					saveDeviceUser(peDevice_datagrid,"${ctx}/device/peDevice/setDeviceUser");//执行保存的步骤
					peDevice_dialog.panel('close');
				}
			},{
				text:'取消',
				handler:function(){
					peDevice_dialog.panel('close');
				}
			}]
		});
}

//弹窗设置自定义分类
function userDefineTypePe(){
	var row = peDevice_datagrid.datagrid('getSelected');
	if(rowIsNull(row)) return;
	$.ajaxSetup({type : 'GET'});
	peDevice_dialog=$("#peDevice_dialog").dialog({   
	    title: '动环设备自定义分类设置',    
	    width: 580,    
	    height: 400,  
	    href:'${ctx}/device/details/userDeviceType',
	    maximizable:true,
	    modal:true,
	    onClose:function(){
	    	appendAndRemovePe("peDevice_dialog");
	    },
	    buttons:[{
			text:'确认',
			handler:function(){
				saveUserDefineTypes(peDevice_datagrid,"${ctx}/device/peDevice/setUserDeviceType");//执行保存用户自定义设备的操作
				peDevice_dialog.panel('close');
			}
		},{
			text:'取消',
			handler:function(){
				peDevice_dialog.panel('close');
			}
		}]
	});
}

/**
 * 告警模板选择
 */
function alarmTemplatePe(){
	var row = peDevice_datagrid.datagrid('getSelected');
	if(rowIsNull(row)) return;
	$.ajaxSetup({type : 'GET'});
	peDevice_dialog=$("#peDevice_dialog").dialog({   
	    title: '告警模板设置',    
	    width: 580,    
	    height: 400,  
	    href:'${ctx}/device/details/alarmTemplate',
	    maximizable:true,
	    modal:true,
	    onClose:function(){
	    	appendAndRemovePe("peDevice_dialog");
	    },
	    buttons:[{
			text:'确认',
			handler:function(){
				saveAlarmTemplate(peDevice_datagrid,"${ctx}/device/peDevice/setAlarmTemplate");//执行保存运维角色的操作
				peDevice_dialog.panel('close');
			}
		},{
			text:'取消',
			handler:function(){
				peDevice_dialog.panel('close');
			}
		}]
	});
}


/**
 * 处理div混淆；
 */
function appendAndRemovePe(divId){
	$("#" +divId + "").dialog("destroy").remove(); //直接摧毁、移除
	$("<div id='"+ divId +"'></div> ").appendTo($('body'))//新加入一个
}


function peScrapped(){
	var peDeviceIdList=[];
	
	//所选的的主机设备
	var data=peDevice_datagrid.datagrid('getSelections');
	if(data.length==0){
		parent.$.messager.show({ title : "提示",msg: "请选择需要报废的设备！", position: "topCenter" });
		return;
	}
	for(var i=0;i<data.length;i++){
		/* if(data[i].obtainState.id==0){
			parent.$.messager.show({ title : "提示",msg: "设备需要退回后才能报废！", position: "topCenter" });
			return;
		} */
		peDeviceIdList.push(data[i].id);
	}
	parent.$.messager.confirm('提示', '确定要报废设备吗？', function(data){
		if (data){
			$.ajax({
				type:"post",
				data:JSON.stringify(peDeviceIdList),
				contentType:'application/json;charset=utf-8',	//必须
				url:"${ctx}/device/peDevice/scrappedPeDevice",
				success:function(data){
					if(data=='success'){
						parent.$.messager.show({ title : "提示",msg: "操作成功！", position: "bottomRight" });
						$("#peScrapped").css({
							visibility : "hidden"
						});
						peDevice_datagrid.datagrid('reload'); 
					}else{
						$.easyui.messager.alert(data);
					}
					
				}
			});
		}
	});
}
</script>
</body>
</html>