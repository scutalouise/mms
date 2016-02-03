$(function() {
	$("#organizationTree").tree(
			{
				method : "get",
				url : ctx + "/system/organization/tree",
				onBeforeExpand : function(node, params) {
					$(this).tree("options").url = ctx
							+ "/system/organization/tree?pid=" + node.id
				},
				onSelect : function(node) {
					
					
					$("#gitInfoList").attr("src",ctx+"/chart/gitInfoView?organizationId="+node.id);

				}
			});

});