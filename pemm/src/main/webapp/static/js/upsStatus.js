
$(function() {
	

	$("#areaInfoTree").tree({
	
		url : ctx + "/system/area/tree",
		onBeforeExpand : function(node, params) {
			$(this).tree("options").url = ctx + "/system/area/tree?pid=" + node.id

		},
		onSelect : function(node) {

			$("#deviceList").attr("src",ctx+"/upsStatus/gitInfoView?areaInfoId="+node.id);
		
		}
	});

});