$(function() {
	$("#areaInfoTree").tree(
			{
				method : "get",
				url : ctx + "/system/area/tree",
				onBeforeExpand : function(node, params) {
					$(this).tree("options").url = ctx
							+ "/system/area/tree?pid=" + node.id
				},
				onSelect : function(node) {
					
					areaInfoId = node.id; 
					$("#gitInfoList").attr("src",ctx+"/chart/gitInfoView?areaInfoId="+node.id);

				}
			});

});