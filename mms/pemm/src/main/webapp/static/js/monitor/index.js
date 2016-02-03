
$(function() {
	loadMain();
	
	//changeIndexHeight();

	
	
	setInterval("showTime()", 1000);
	

});

/*function changeIndexHeight() {
	
	$("#content").css({
		position : "absolute",
		top : 90,
		left : 0 
	});
	
	$("#content").height($(window).height() - 130);
	var height = $("#content").height() - $("#center_status").height()+40;
	$("#top_content").height(height);
	$("#top_img").height(height);
	
	var top_title_bottom = $(window).height() - $("#top_logo").height()
			- $("#top_title").height();
	$("#top_title").css({
		bottom : top_title_bottom
	});
	$("#top_logo").css({
		bottom : top_title_bottom + 30
	});

	
	$('#loginConfirm').modal("hide").css({
		"margin-top": ($(window).height())/2-170,
	


	});
	
}*/
function logout(){
	$('#loginConfirm').modal("show");
	$("#logoutOk").click(function(){
		
		location.href=ctx+'/m/logout';
	});
	$("#logoutCancel").click(function(){
		$('#loginConfirm').modal("hide");
	});
}
function backSystem(){
	location.href=ctx+"/a";
}
function loadMain(){
	$.get(ctx+"/system/monitor/main",function(data){
		$("#content").html(data);
	});
}


/**
 * 显示系统时间
 */
function showTime() {
	 var weekNames = new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六");  
    var date = new Date();
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
    document.getElementById("time").innerHTML = ""+h + ":" + m + ":" + s;
    document.getElementById("date").innerHTML=year+"年"+month+"月"+day+"日"+" "+weekNames[week];
}