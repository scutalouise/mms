var selectUpsIndex;
var startDate;
var endDate;
$(function() {
	$("#ups_statusList_div").show();
	$("#th_statusList_div").hide();
	$('#deviceTabs a:first').tab('show');
	selectUpsIndex=($('#deviceTabs a:first').tab()[0].innerText).substring(($('#deviceTabs a:first').tab()[0].innerText).indexOf("【")+1,$('#deviceTabs a:first').tab()[0].innerText.length-1);
	
	$('#deviceTabs a').click(
			function(e) {
				e.preventDefault();// 阻止a链接的跳转行为

				$(this).tab('show');
				var title=$(this)[0].text;
				selectUpsIndex=title.substring(title.indexOf("【")+1,title.length-1);
				if(title.indexOf("UPS")>-1){
					startDate=$("#upsStartDateInput").val();
					endDate=$("#upsEndDateInput").val();
					$("#ups_statusList_div").show();
					$("#th_statusList_div").hide();
				$("#ups_statusList").bootstrapTable("refreshOptions",{
					queryParams:queryParams
				});
				$("#ups_statusList").bootstrapTable('refresh');
				}else if(title.indexOf("温湿度")>-1){
					startDate=$("#thStartDateInput").val();
					endDate=$("#thEndDateInput").val()
					$("#ups_statusList_div").hide();
					$("#th_statusList_div").show();
					$("#th_statusList").bootstrapTable("refreshOptions",{
						queryParams:queryParams
					});
				}
				
			});
	winStatusListResize();
	

	$('#upsStartDate').datetimepicker({
	    format: 'yyyy-mm-dd',
	    language:  'zh-CN',
	    autoclose: true,
	    todayBtn: true,
	    pickerPosition: "bottom-right",
	    weekStart: 1,
	   
	    endDate:formatDate(),
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0 
	});
	$('#thStartDate').datetimepicker({
	    format: 'yyyy-mm-dd',
	    language:  'zh-CN',
	    autoclose: true,
	    todayBtn: true,
	    pickerPosition: "bottom-right",
	    weekStart: 1,
	   
	    endDate:formatDate(),
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0 
	});
	
	
	$('#upsEndDate').datetimepicker({
	    format: 'yyyy-mm-dd',
	    language:  'zh-CN',
	    autoclose: true,
	    todayBtn: true, 
	    pickerPosition: "bottom-right",
	    weekStart: 1,
		startDate:getLastMonthYestdy(new Date()),
		
		todayHighlight: 1, 
		startView: 2,
		minView: 2,
		forceParse: 0
	});
	$('#thEndDate').datetimepicker({
	    format: 'yyyy-mm-dd',
	    language:  'zh-CN',
	    autoclose: true,
	    todayBtn: true, 
	    pickerPosition: "bottom-right",
	    weekStart: 1,
		startDate:getLastMonthYestdy(new Date()),
		
		todayHighlight: 1, 
		startView: 2,
		minView: 2,
		forceParse: 0
	});
	$("#upsStartDateInput").val(getLastMonthYestdy(new Date()))
	$("#upsEndDateInput").val(formatDate())
	$('#upsStartDate').datetimepicker().on("changeDate",function(v){
		startDate=$("#upsStartDateInput").val();
		endDate=$("#upsEndDateInput").val();
		$('#upsEndDate').datetimepicker('setStartDate', $("#upsStartDateInput").val());
	});
	$('#upsEndDate').datetimepicker().on("changeDate",function(v){
		startDate=$("#upsStartDateInput").val();
		endDate=$("#upsEndDateInput").val();
		$('#upsStartDate').datetimepicker('setEndDate', $("#upsEndDateInput").val());
	});
	$("#thStartDateInput").val(getLastMonthYestdy(new Date()))
	$("#thEndDateInput").val(formatDate())
	$('#thStartDate').datetimepicker().on("changeDate",function(v){
		startDate=$("#thStartDateInput").val();
		endDate=$("#thEndDateInput").val();
		$('#thEndDate').datetimepicker('setStartDate', $("#thStartDateInput").val());
	});
	$('#thEndDate').datetimepicker().on("changeDate",function(v){
		startDate=$("#thStartDateInput").val();
		endDate=$("#thEndDateInput").val();
		$('#thStartDate').datetimepicker('setEndDate', $("#thEndDateInput").val());
	});
	$("#ups_statusList").bootstrapTable({
		method : "get",
		url : ctx
		+ "/upsStatus/upsStatusList",
		cache: false,
		toolbar:"#upstoolbar",
		queryParams : queryParams,
        striped: true,
        toggle:"table",
        dataType: "json",
		pagination:true,
		pageSize: 10,
		pageList: [10, 25, 50, 100, 200],
		pageNumber:1,
		search: true,
        showColumns: true,
        showToggle:true,
        showRefresh: true,
        minimumCountColumns: 2,
        clickToSelect: false,
        sidePagination:"server",
		columns : [
		         /*  {
			field : "id",
			valign:"middle",
			halign:"center", 
			checkbox : true ,hidden:true
		},*/
		{
			field:"communicationStatus",title:"通讯状态",
			sortable : true,
			width:20, 
			align:"center",
			halign:"center",
			valign:"middle",
			formatter:function(value){
				var result = "";
				switch (value) {
				case 0:
					result = "正常";
					break;
				case 1:
					result = "UPS无响应";
					break;
				case 2:
					result = "UPS未识别";

				}
				return result;
				
			}
		},{
			field : "batteryVoltage",
			title : "电池电压",
			width:20,
			align:"center",
			halign:"center",
			valign:"middle",
			sortable : true,
			formatter : function(
					value) {
				return value
						+ "V";
			}
		},{
			field : "upsStatus",
			title : "UPS状态",
			valign:"middle",
			align:"center",
			halign:"center",
			sortable : true,
			formatter:function(value){
				if(value==0){
					return "正常";
				}else{
					return "异常";
				}
			}
			
		},{
			field : "frequency",
			title : "*频率",
			sortable : true,
			valign:"middle",
			formatter : function(
					value) {
				return value
						+ "Hz";
			}
		},{
			field : "internalTemperature",
			title : "机内温度",
			sortable : true,
			hidden : false,
			valign:"middle",
			formatter : function(
					value) {
				return value
						+ "℃";
			}
		},{
			field : "bypassVoltage",
			title : "*旁路电压",
			sortable : true,
			valign:"middle",
			hidden : true

		},
		{
			field : "bypassFrequency",
			title : "旁路频率",
			sortable : true,
			valign:"middle",
			formatter : function(
					value) {
				return value
						+ "Hz";
			}
		},{
			field : "inputVoltage",
			title : "输入电压",
			sortable : true,
			valign:"middle",
			formatter : function(
					value) {
				return value;
			}

		},{
			field : "outputVoltage",
			title : "输出电压",
			sortable : true,
			valign:"middle",
			formatter : function(
					value) {
				return value;
			}
		},{
			field : "errorVoltage",
			title : "故障电压",
			sortable : true,
			valign:"middle",
			formatter : function(
					value) {
				return value
						+ "V"
			}
		},{
			field : "upsLoad",
			title : "负载",
			sortable : true,
			valign:"middle",
			formatter : function(
					value) {
				return value;
			}
		},{
			field : "outputFrenquency",
			title : "*输出频率",
			sortable : true,
			valign:"middle",
			formatter : function(
					value) {
				return value
						+ "Hz";

			}

		},{
			field : "singleVoltage",
			title : "单节电压",
			sortable : true,
			valign:"middle",
			formatter : function(
					value) {
				return value
						+ "V";
			}
		},{
			field : "electricQuantity",
			title : "充电量",
			sortable : true,
			valign:"middle",
			hidden : true,
			formatter : function(
					value) {
				return value
						+ "%"
			}
		},{
			field : "collectTime",
			title : "获取时间",
			sortable : true,
			valign:"middle",
			width:150,
			formatter : function(
					value,
					rowData,
					rowIndex) {
				if(value!=null){
					var date = new Date(value);
					 return formatDateTime(date);
					
				}
			}

		} ]
	});
	$("#th_statusList").bootstrapTable({
		method : "get",
		url : ctx
		+ "/system/thStatus/thStatusList",
		cache: false,
		toolbar:"#thtoolbar",
		queryParams : queryParams,
        striped: true,
        toggle:"table",
        dataType: "json",
		pagination:true,
		pageSize: 10,
		pageList: [10, 25, 50, 100, 200],
		pageNumber:1,
		search: true,
        showColumns: true,
        showToggle:true,
        showRefresh: true,
        minimumCountColumns: 2,
        clickToSelect: false,
        sidePagination:"server",
		columns : [
		         /*  {
			field : "id",
			valign:"middle",
			halign:"center", 
			checkbox : true ,hidden:true
		},*/
		{
			field:"temperature",title:"温度",
			sortable : true,
			
			align:"center",
			halign:"center",
			valign:"middle",
			formatter:function(value){
				return value+ "℃";
			}
		},{
			field : "humidity",
			title : "湿度",
			sortable : true,
			valign:"middle",
			hidden : true,
			formatter : function(
					value) {
				return value
						+ "%"
			}
		},{
			field : "collectTime",
			title : "获取时间",
			sortable : true,
			valign:"middle",
			
			formatter : function(
					value,
					rowData,
					rowIndex) {
				if(value!=null){
					var date = new Date(value);
					 return formatDateTime(date);
					
				}
			}

		} ]
	});
	
	$(window).resize(function() {
		winStatusListResize();  
		

	});
	

});


function formatDate(){
	var date=new Date();
	var year=date.getFullYear();   
    var month=date.getMonth()+1;
    month = month < 10 ? "0" + month : month;
    var day=date.getDate();
    var week=date.getDay();
    day = day < 10 ? "0" + day : day;
    var h = date.getHours(); 
    h = h < 10 ? "0" + h : h;
    var m = date.getMinutes();
    m = m < 10 ? "0" + m : m;
    var s = date.getSeconds();
    s = s < 10 ? "0" + s : s;
   return year+"-"+month+"-"+day;
}
function formatDateTime(date){
	
	
    var year=date.getFullYear();   
    var month=date.getMonth()+1;
    month = month < 10 ? "0" + month : month;
    var day=date.getDate();
    var week=date.getDay();
    day = day < 10 ? "0" + day : day;
    var h = date.getHours(); 
    h = h < 10 ? "0" + h : h;
    var m = date.getMinutes();
    m = m < 10 ? "0" + m : m;
    var s = date.getSeconds();
    s = s < 10 ? "0" + s : s;
   return year+"-"+month+"-"+day+" "+h + ":" + m + ":" + s;
   
}
function winStatusListResize(){
	
	$("#statusList_div").height($(window).height()-$("#deviceTabs").height()-240);
	$("#ups_statusList").height($("#statusList_div").height()-30);
	$("#th_statusList").height($("#statusList_div").height()-30);
}
function queryParams(params) {

	return {
	 page: params.offset,

	 rows: params.limit,

		filter_EQI_status : '1',
		gitInfoId : gitInfoId,
		index : selectUpsIndex,
	startDate:startDate,
	endDate:endDate,
	 sort: params.sort,
	 order: params.order

	};

	}
function searchUpsStatus(){
	$("#ups_statusList").bootstrapTable("refreshOptions",{
		queryParams:queryParams
	});
	$("#ups_statusList").bootstrapTable('refresh');
}
function searchThStatus(){ 
	$("#th_statusList").bootstrapTable("refreshOptions",{
		queryParams:queryParams
	});
	$("#th_statusList").bootstrapTable('refresh');
}
function exportUpsStatusToExcel(){
	window.location.href=ctx+"/system/excel/exportUpsStatus/"+$("#upsStartDateInput").val()+"/"+$("#upsEndDateInput").val()+"/"+selectUpsIndex+"/"+gitInfoId
}
function exportThStatusToExcel(){
	window.location.href=ctx+"/system/excel/exportThStatus/"+$("#thStartDateInput").val()+"/"+$("#thEndDateInput").val()+"/"+selectUpsIndex+"/"+gitInfoId
}
