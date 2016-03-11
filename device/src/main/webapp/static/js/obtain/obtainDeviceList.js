var orgId;
var tabIndex = 0;
var selectDeviceName;
var selectDeviceId;
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
					orgId = node.id;
					$("#tab_devices").tabs("refresh", tabIndex);// 刷新下
				}
			});

	$("#tab_devices").tabs({
		onSelect : function(title, index) {
			tabIndex = index;
		}
	});
});
/**
 * 处理dialog,使用同一个div混淆；
 */
function appendAndRemoveDialog(divId){
	$("#" +divId + "").dialog("destroy").remove(); //直接摧毁、移除
	$("<div id='"+ divId +"'></div> ").appendTo($('#'+divId+"_div"))//新加入一个
}