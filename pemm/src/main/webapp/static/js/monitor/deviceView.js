var ip;
$(function(){

	windowResize();
	$(window).resize(function() {
		//windowResize();

	});
	
	 
	loadDeviceDetail();
	$("#bakcBranch").click(function(){
		
		$.get(ctx + "/system/monitor/branchView",{gitIds:gitIdStr}, function(data) {
			
			$("#content").html(data);
		});
	});
	$("input[name='selectedDevice']").click(function(){
		
		loadDeviceDetail();
	});

});
function loadDeviceDetail(){
	var serverState=0;
	$("input[name='selectedDevice']:checked").each(function(){
		var inputValue=$(this).val().split("_");
		gitIdStr=inputValue[0];
		serverState=inputValue[1];
		ip=inputValue[2];
	}); 

//	if(serverState!="unconnect"){
	$.get(ctx + "/system/monitor/deviceDetail",{gitIds:gitIdStr}, function(data) {
		$("#view_div").html(data);
	});
//	}else{
//		$("#view_div").html("<center style='margin-top:50px;color:#CCD7DB;font-size:28px;font-weight:800'>采集器链接丢失，请检查网络!</center>")
//	}

}
function windowResize() {
	$("#content").height($(window).height() - 130);
	
	$("#device_left").height($("#content").height());
	$("#view_div").height($("#content").height());
	$("#view_div").width($(window).width()-490); 
	 
	
	  

	

}