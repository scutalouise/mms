var ids = new Array();
function loadBranchList(searchValue){
	 $.post(ctx + "/system/monitor/branchList",{stateEnum:checkStateEnum,searchValue:searchValue}, function(data) {
		$("#branch_list").html(data);
		$("#branch_center_content").width($(window).width());
		$("#deviceList").fadeOut();
		$("#deviceList").html('<div id="deviceView"><button type="button" class="btn btn-default" onclick="preview();">设备视图预览</button></div>');
	});
}
$(function() {
	$("#branch_center_content").width($(window).width());
	loadBranchList();
	$("#search_icon").click(function() {
		var value = $("#search_input").val().trim();
		if (value != "") {
			loadBranchList(value);
		};

	});
	windowResize();
	$(window).resize(function() {
		windowResize();

	});

});
function windowResize() {
	/*
	 * $(".content_organization").each(function(){
	 * $(this).width($(window).width()-620); });
	 */
	/*
	 * $(".branch_view").each(function(){
	 * 
	 * $(this).width($(".branch_div").width()-144); });
	 */
	if (ids.length > 0) {

		$("#branch_center_content").width($(window).width() - 400);
		

	} else {

		$("#branch_center_content").width($(window).width());

	}

	$("#content").height($(window).height() - 130);

	$("#branch_center_content").height($("#content").height());

	$("#deviceList").height($(document).height() - 130);
	$('#devicewarning').modal("hide").css({
		"margin-top" : ($(window).height()) / 2 - 170,

	});
	$("#search_div").width($("#branch_center_content").width()*0.6);
	$("#search_input").width($("#search_input_div").width() - 40);
}

function selected(a) {

	ids = new Array();
	$("input[name='organizationCheckBox']:checked").each(function(i) {
		ids[i] = $(this).val();
	});
	if (ids.length > 0) {
		$("#branch_center_content").width($(window).width() - 400);
		$("#deviceList").fadeIn();

	} else {
		$("#branch_center_content").width($(window).width());
		$("#deviceList").fadeOut();
	}
    
	if (a.checked == false) {
		$("#deviceList").children().remove("#device_" + a.value);
	} else {

		loadDeviceList(a.value);
	}

}
function loadDeviceList(id) {

	$.ajax({
				type : "post",
				url : ctx + "/system/monitor/gitInfoList",
				data : {
					organizationIds : id
				},
				success : function(result) {
					var jqueryHtml;
					if (result.length > 0) {

						jqueryHtml = "<div  id='device_"
								+ result[0].organizationId
								+ "' class='deivceContent'><div class='device_organizationName'>网点名称：【"
								+ result[0].organizationName + "】</div>";
						jqueryHtml += "<hr class='git_hr'></hr>"
						for (var i = 0; i < result.length; i++) {
							jqueryHtml += "<div class='git_content_div'><div class='git_img'><input name='gitCheckBox' type='checkbox' value='"
									+ result[i].id
									+ "'><img  src='"
									+ ctx
									+ "/static/images/monitor/git.gif'/></div><div class='git_content'>Ip："
									+ result[i].ip
									+ "<br>网关名称："
									+ result[i].name
									+ "</div><div class='git_details'><a href='javascript:previewDetail("
									+ result[i].id + ")'>查看详情</a></div></div>"
						}
						jqueryHtml += "</div>";
					}
					var deviceContent = $(jqueryHtml);

					// deviceContent.prependTo($("#deviceList"));
					$("#deviceList").append(jqueryHtml);
				}
			});

	// $.ajax({
	// type : "post",
	// url : ctx + "/system/monitor/gitInfoList",
	// data : {
	// organizationIds : ids
	// },
	// success : function(result) {
	// var jqueryHtml;
	// for (var i = 0; i < ids.length; i++) {
	// jqueryHtml = "<div id='device_" + ids[i]
	// + "' class='deivceContent'>";
	// for (var j = 0; j < result.length; j++) {
	// if (ids[i] == result[j].organizationId) {
	// jqueryHtml += "网点名称：【" + result[j].organizationName
	// + "】";
	//
	// // deviceContent.appendTo($("#deviceList"));
	//
	// }
	// }
	// jqueryHtml += "</div>";
	// }
	// var deviceContent = $(jqueryHtml);
	// deviceContent.appendTo($("#deviceList"));
	//
	// }
	// });
}
function preview() {
	var gitIds = new Array();
	$("input[name='gitCheckBox']:checked").each(function(i) {
		gitIds[i] = $(this).val();
	});
	if (gitIds.length > 0) {
		$.get(ctx + "/system/monitor/deviceView", {
			gitIds : gitIds + ""
		}, function(data) {
			$("#content").html(data);
		});
	} else {
		$("#devicewarning").modal("show");

	}

}
function previewDetail(gitId_Str) {
	$.get(ctx + "/system/monitor/deviceView", {
		gitIds : gitId_Str
	}, function(data) {
		$("#content").html(data);
	});
}