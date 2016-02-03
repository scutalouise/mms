
$(function() {
	

	$("#organizationTree").tree({
	
		url : ctx + "/system/organization/tree",
		onBeforeExpand : function(node, params) {
			$(this).tree("options").url = ctx + "/system/organization/tree?pid=" + node.id

		},
		onSelect : function(node) {

			$("#deviceList").attr("src",ctx+"/upsStatus/gitInfoView?organizationId="+node.id);
		
		}
	});

});