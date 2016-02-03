var dg;
var d;
$(function() {

	dg = $("#dg")
			.datagrid(
					{
						method : "get",
						url : ctx + "/system/scheduleJob/json",
						
						fit : true,
						fitColumns : true,
						border : false,
						idField : "id",
						striped : true,
						pagination : true,
						rownumbers : true,
						pageNumber : 1,
						pageSize : 20,
						pageList : [ 10, 20, 30, 40, 50 ],
						singleSelect : true,
						columns : [ [
								{
									field : 'name',
									title : '名称',
									width : 100
								},
								{
									field : 'status',
									title : '状态',
									width : 30
								},
								{
									field : "description",
									title : "描述",
									width : 200
								}
//								,
//								{
//									field : "operation",
//									title : "操作",
//									fit : true,
//									align : 'center',
//									formatter : function(v, record) {
//
//										return "<a href='javascript:selectDischargeDevice(\""
//												+ record.name
//												+ "\",\""
//												+ record.group
//												+ "\")'>选择放电设备</a>";
//									}
//								}
								] ],
						enableHeaderClickMenu : false,
						enableHeaderContextMenu : false,
						enableRowContextMenu : false,
						toolbar : '#tb',
						dataPlain : true
					});
});
var ups_dlg;
var scheduleName;
var scheduleGroup;
function selectDischargeDevice(name, group) {
	scheduleName=name;
	scheduleGroup=group;

	if(ups_dlg==undefined){
	ups_dlg = $("#ups_dlg").dialog({
		title : "已选择的设备",
		width : 600,
		height : 400,
		href:ctx+"/system/dischargeTask/selectedUpsDeviceList",
		maximizable : true,
		modal : true
	});
	}else{
		ups_dlg.dialog("open");
		ups_dlg.dialog("refresh");
	}

}
function add() {

	if (d == undefined) {
		d = $("#dlg").dialog({
			title : "添加放电计划",
			width : 540,
			height : "auto",
			href : ctx + "/system/dischargeTask/addForm",
			maximizable : true,
			modal : true,
			id : "dlgContent",
			top : ($(window).height() - 320) / 2,
			buttons : [ {
				text : "确认",
				handler : function() {
					$("#mainform").submit();
				}
			}, {
				text : '取消',
				handler : function() {
					d.panel('close');
				}
			} ]
		});
	}else{
		d.dialog("refresh",ctx + "/system/dischargeTask/addForm");
		d.dialog("open");
	
	}

}
function del(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'post',
				url:ctx+"/system/dischargeTask/delScheduleJob",
				data:{
					scheduleName:row.name,
					scheduleGroup:row.group
				},
				success: function(data){
					if(data=='success'){
						dg.datagrid('reload');
						parent.$.messager.show({ title : "提示",msg: "操作成功！", position: "bottomRight" });
					}else{
						parent.$.messager.alert(data);
					}  
				}
			});
		}
	});
}
var selectDischargeTask;
function upd(){
	selectDischargeTask = dg.datagrid("getSelected");
	 if (rowIsNull(selectDischargeTask)) {
		 return;
	}
	
if(d==undefined){
	 d = $("#dlg").dialog({
			title : "添加放电计划",
			width : 540,
			height : "auto",
			href : ctx + "/system/dischargeTask/updateForm",
			maximizable : true,
			modal : true,
			id : "dlgContent",
			top : ($(window).height() - 320) / 2,
			buttons : [ {
				text : "确认",
				handler : function() {
					$("#mainform").submit();
				}
			}, {
				text : '取消',
				handler : function() {
					d.panel('close');
				}
			} ]
		});
}else{
	d.dialog("refresh",ctx + "/system/dischargeTask/updateForm");
	d.dialog("open");
}
}
function startDischargeTask(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	parent.$.messager.confirm('提示', '确定要恢复任务？', function(data){
		if (data){
			$.ajax({
				type:'post',
				url:ctx+"/system/dischargeTask/startDischargeTask",
				data:{
					scheduleName:row.name,
					scheduleGroup:row.group
				},
				success: function(data){
					if(data=='success'){
						dg.datagrid('reload');
						parent.$.messager.show({ title : "提示",msg: "操作成功！", position: "bottomRight" });
					}else{
						parent.$.messager.alert(data);
					}  
				}
			});
		}
	});
}
function stopDischargeTask(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	parent.$.messager.confirm('提示', '确定要暂停任务？', function(data){
		if (data){
			$.ajax({
				type:'post',
				url:ctx+"/system/dischargeTask/stopDischargeTask",
				data:{
					scheduleName:row.name,
					scheduleGroup:row.group
				},
				success: function(data){
					if(data=='success'){
						dg.datagrid('reload');
						parent.$.messager.show({ title : "提示",msg: "操作成功！", position: "bottomRight" });
					}else{
						parent.$.messager.alert(data);
					}  
				}
			});
		}
	});
}
function selectDischargeTask(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	selectDischargeDevice(row.name,row.group);
}