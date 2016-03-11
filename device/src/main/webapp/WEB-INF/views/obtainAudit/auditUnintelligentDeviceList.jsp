<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>非智能设备审核列表</title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
	<div id="unintelligentToolbar">
	<c:if test="${param.type eq ''}">
		<shiro:hasPermission name="sys:auditDevice:audit">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="audit(1)">审核通过</a>
		<span class="toolbar-item dialog-tool-separator"></span>
		</shiro:hasPermission>
		<shiro:hasPermission name="sys:auditDevice:audit">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="audit(0)">审核不通过</a>
		<span class="toolbar-item dialog-tool-separator"></span>
		</shiro:hasPermission>
	</c:if>
	<c:if test="${param.type eq 'waitRepair'}">
		<shiro:hasPermission name="sys:auditDevice:putInstorage">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cog" plain="true" onclick="operation('putInstorage')">入库</a>
		<span class="toolbar-item dialog-tool-separator"></span>
		</shiro:hasPermission>
		
		<%-- <shiro:hasPermission name="sys:auditDevice:badParts">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cog" plain="true" onclick="operation('badparts')">坏件</a>
		<span class="toolbar-item dialog-tool-separator"></span>
		</shiro:hasPermission> --%>
	</c:if>
	<c:if test="${param.type eq 'badParts' }">
		<shiro:hasPermission name="sys:auditDevice:scrap">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cog" plain="true" onclick="scrap()">报废</a>
		<span class="toolbar-item dialog-tool-separator"></span>
		</shiro:hasPermission>
		<shiro:hasPermission name="sys:auditDevice:waitExternalMaintenance">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cog" plain="true" onclick="operation('waitExternalMaintenance')">待外修</a>
		<span class="toolbar-item dialog-tool-separator"></span>
		</shiro:hasPermission>
	</c:if>
	</div>
	<div id="unintelligent_dg"></div>
<script type="text/javascript">
	var unintelligent_dg;
	$(function(){
		var type="audit";
		if("${param.type}"!=""){
			type="${param.type}";
		}
		unintelligent_dg=$("#unintelligent_dg").datagrid({
			method: "post",
		    url:'${ctx}/device/unintelligentDevice/json', 
		    queryParams:{type:type},
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
					title:"设状态",
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
							}else if(v=="WAITREPAIR"){
								return "待修";
							}
						}
					}
				},{
					field:"obtainType",
					title:"领用方式",
					width:100,
					align:'center',formatter:function(v,rowData,rowIndex){
						if(rowData.obtainUserId!=null){
							return "个人领用";
						}else{
							return "网点领用"
						}
					}
				},{
					field:"obtainUserId",
					title:"领用单位/个人",
					width:100,
					align:"center",
					formatter:function(v,rowData,rowIndex){
						
						if(rowData.obtainUserName!=null){
							return rowData.obtainUserName;
						}else{
							return rowData.organizationName;
						}
					}
				}
		    ]],
		    headerContextMenu: [
		        {
		            text: "冻结该列", disabled: function (e, field) { return unintelligent_dg.datagrid("getColumnFields", true).contains(field); },
		            handler: function (e, field) { unintelligent_dg.datagrid("freezeColumn", field); }
		        },
		        {
		            text: "取消冻结该列", disabled: function (e, field) { return unintelligent_dg.datagrid("getColumnFields", false).contains(field); },
		            handler: function (e, field) { unintelligent_dg.datagrid("unfreezeColumn", field); }
		        }
		    ],
		    enableHeaderClickMenu: true,
		    enableHeaderContextMenu: true,
		    enableRowContextMenu: false,
		    toolbar:'#unintelligentToolbar'
		});
	});
	function audit(type){
		var unintelligentDeviceIdList=[];
		var data=$("#unintelligent_dg").datagrid("getSelections");
		for(var i=0;i<data.length;i++){
			unintelligentDeviceIdList.push(data[i].id);
		}

		$.ajax({
			type:"post",
			data:JSON.stringify(unintelligentDeviceIdList),
			contentType:'application/json;charset=utf-8',	//必须
			url:"${ctx}/device/unintelligentDevice/unintelligentDeviceAudit/"+type,
			success:function(data){
				if(data=='success'){
					parent.$.messager.show({ title : "提示",msg: "操作成功！", position: "bottomRight" });
					unintelligent_dg.datagrid('reload'); 
				}else{
					$.easyui.messager.alert(data);
				}
			}
		});
	}
	function operation(type){
		var unintelligentDeviceIdList=[];
		var data=$("#unintelligent_dg").datagrid("getSelections");
		for(var i=0;i<data.length;i++){
			unintelligentDeviceIdList.push(data[i].id);
		}

		$.ajax({
			type:"post",
			data:JSON.stringify(unintelligentDeviceIdList),
			contentType:'application/json;charset=utf-8',	//必须
			url:"${ctx}/device/unintelligentDevice/"+type,
			success:function(data){
				if(data=='success'){
					parent.$.messager.show({ title : "提示",msg: "操作成功！", position: "bottomRight" });
					unintelligent_dg.datagrid('reload'); 
				}else{
					$.easyui.messager.alert(data);
				}
			}
		});
	}
	
	function scrap(){
		var unintelligentDeviceIdList=[];
		var data=$("#unintelligent_dg").datagrid("getSelections");
		for(var i=0;i<data.length;i++){
			unintelligentDeviceIdList.push(data[i].id);
		}
		parent.$.messager.confirm('提示', '确定要报废设备吗？', function(data){
			if (data){
				$.ajax({
					type:"post",
					data:JSON.stringify(unintelligentDeviceIdList),
					contentType:'application/json;charset=utf-8',	//必须
					url:"${ctx}/device/unintelligentDevice/scrap",
					success:function(data){
						if(data=='success'){
							parent.$.messager.show({ title : "提示",msg: "操作成功！", position: "bottomRight" });
							unintelligent_dg.datagrid('reload'); 
						}else{
							$.easyui.messager.alert(data);
						}
					}
				});
			}});
	}
</script>
</body>
</html>