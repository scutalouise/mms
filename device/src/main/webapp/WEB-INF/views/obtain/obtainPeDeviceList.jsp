<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>动环设备领用</title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>
<body>
	<div id="peToolBar">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="obtainPeDevice('obtain')">领用设备</a>
		<span class="toolbar-item dialog-tool-separator"></span>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="backPeDevice()">退回设备</a>
		<span class="toolbar-item dialog-tool-separator"></span>
		
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cog" data-options="disabled:true" plain="true" id="peUsed" onclick="peUsed()">设备使用</a>
		 <span class="toolbar-item dialog-tool-separator"></span>
		
		
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cog" plain="true"  id="relevanceCollection" onclick="relevanceCollection()">挂接动环设备</a>
		<span class="toolbar-item dialog-tool-separator" id="relevanceCollection_separator"></span> 
	
		
	</div>
	<div id="pe_dg"></div>
	<div id="pe_dlg_div">
	<div id="pe_dlg"></div>
	</div>
	<div id="pe_usedOrganization_dlg"></div>
	<div id="relevanceCollection_dlg"></div>
	<script type="text/javascript">
	var pe_dg;
	var pe_dlg;
		$(function(){
			$("#relevanceCollection,#relevanceCollection_separator").css({
				visibility : "hidden"
			});
			
			var url='${ctx}/device/peDevice/json?type=obtain';
			if("${type}"!="personal"){
				if(orgId!=null){
					url+="&orgId="+orgId;
				}
				url+="&way=organization";
			}else{
			
				url+="&way=personal";
			}
			pe_dg=$("#pe_dg").datagrid({
				method: "post",
			    url:url,
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
			        {field:'manufactureDate',title:'生产日期',sortable:true,width:100,formatter: function(value,row,index){
			        	return formatDate(value,"yyyy-MM-dd")
			        }},
			       
			       
			        {field:'model',title:'型号',sortable:true,width:100},
			       
			        {
						field:"deviceUsedStateValue",
						title:"设备状态",
						align:"center",
						sortable:true,
						width:100,
						formatter:function(v){
							if(v!=null){
								if(v=="PUTINSTORAGE"){
									return "入库";
								}else if(v=="AUDIT"){
									return "待审核";
								}else if(v=="TURNOVER"){
									return "周转";
								}else if(v=="USED"){
									return "使用";
								}
							}
						}
					}
			    ]],
			    headerContextMenu: [
			        {
			            text: "冻结该列", disabled: function (e, field) { return pe_dg.datagrid("getColumnFields", true).contains(field); },
			            handler: function (e, field) { pe_dg.datagrid("freezeColumn", field); }
			        },
			        {
			            text: "取消冻结该列", disabled: function (e, field) { return pe_dg.datagrid("getColumnFields", false).contains(field); },
			            handler: function (e, field) { pe_dg.datagrid("unfreezeColumn", field); }
			        }
			    ],
			    enableHeaderClickMenu: true,
			    enableHeaderContextMenu: true,
			    enableRowContextMenu: false,
			    toolbar:'#peToolBar',
			    onSelect:function(rowIndex,rowData){
			    	$("#peUsed").linkbutton("enable");
			    	if(rowData.deviceUsedStateValue!="AUDIT"){
		    			
			    		$("#relevanceCollection,#relevanceCollection_separator").css({
							visibility : "visible"
						})
			    	}else{
			    		$("#relevanceCollection,#relevanceCollection_separator").css({
							visibility : "hidden"
						})
			    	}
			    	
			    }
			});
		});
		
		
		function obtainPeDevice(type) {
			if("${type}"!="personal"){
				if(orgId==null){
					parent.$.messager.show({ title : "提示",msg: "请选择需要领用设备的网点！", position: "bottomRight" });
					return ;
				}
			}
			
			pe_dlg=$("#pe_dlg").dialog({
				title : "添加主机设备",
				width : 600,
				height : 400,
				href:"${ctx}/device/peDevice/choosePeDeviceList",
				maximizable:true,
			    modal:true,
			    onClose:function(){
			    	appendAndRemoveDialog("pe_dlg");
			    },
				buttons : [{
					text:'取消',
					handler:function(){
						pe_dlg.panel('close');
					}
				}, {
					text : "确定",
					handler : function() {
						saveObtainPeDevice(type);
						pe_dlg.panel('close');
						pe_dg.datagrid('reload'); 
					}
				} ]
			});
		}
		
		function backPeDevice(){
			var peDeviceIdList=[];
			
			//所选的的主机设备
			var data=$("#pe_dg").datagrid('getSelections');
		
			for(var i=0;i<data.length;i++){
			
				peDeviceIdList.push(data[i].id);
			}
			if(data.length>0){
				parent.$.messager.confirm('提示', '是否确定将设备退回？', function(data){
					if (data){
						$.ajax({
							async:false,
							type:"post",
							data:JSON.stringify(peDeviceIdList),
							contentType:'application/json;charset=utf-8',	//必须
							url:"${ctx}/device/peDevice/backPeDevice",
							success:function(data){
								if(data=='success'){
									parent.$.messager.show({ title : "提示",msg: "操作成功！", position: "bottomRight" });
									pe_dg.datagrid('reload'); 
								}else{
									$.easyui.messager.alert(data);
								}
								
							}
							
							
							
						})
					}
				});
			}
			
			
		}
		
		
		function saveObtainPeDevice(type){
			var peDeviceIdList=[];
			//所选的的主机设备
			var data=$("#choosePe_dg").datagrid('getSelections');
			
			for(var i=0;i<data.length;i++){
			
				peDeviceIdList.push(data[i].id);
			}
			var url="${ctx}/device/peDevice/obtainPeDevice/${type}/"+type;
			if("${type}"!="personal"){
				url+="?orgId="+orgId;
			}
			$.ajax({
				async:false,
				type:"post",
				data:JSON.stringify(peDeviceIdList),
				contentType:'application/json;charset=utf-8',	//必须
				url:url,
				success:function(data){
					if(data=='success'){
						parent.$.messager.show({ title : "提示",msg: "操作成功！", position: "bottomRight" });
						pe_dg.datagrid('reload'); 
					}else{
						$.easyui.messager.alert(data);
					}
				}
				
				
				
			})
			
		}
		
		function relevanceCollection(){
			
			var row=$("#pe_dg").datagrid('getSelected');
			var relevanceCollection_dlg=$("#relevanceCollection_dlg").dialog({
				title : "挂接设备",
				width : 400,
				height : 300,
				href:"${ctx}/device/peDevice/relevanceCollection/${type}/"+row.id,
				maximizable:true,
			    modal:true,
			    
				buttons : [{
					text:'取消',
					handler:function(){
						relevanceCollection_dlg.panel('close');
					}
				}, {
					text : "确定",
					handler : function() {
						$("#relevanceCollectionForm").submit();
						relevanceCollection_dlg.panel('close');
						pe_dg.datagrid('reload'); 
					}
				} ]
				
			});
			
		}
		function peUsed(){
			var row=$("#pe_dg").datagrid('getSelected');
			selectDeviceName=row.name;
			selectDeviceId=row.id;
			$("#pe_usedOrganization_dlg").dialog({
				width:300,
				height:"auto",
				title:"选择网点机构",
				href:ctx+"/device/obtain/usedOrganizationForm/PEDEVICE",
				maximizable:true,
				modal:true,
				buttons:[{
					text:"确认",
					handler:function(){
						$("#deviceForm").submit();
					
					}
				},{
					text:"取消",
					handler:function(){
						$("#pe_usedOrganization_dlg").panel('close')
					}
				}]
				
			});
		}
	</script>
</body>
</html>