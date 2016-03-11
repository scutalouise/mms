<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<link rel="shortcut icon" href="${ctx}/static/images/favicon.ico">

<!-- easyui皮肤 -->
<link href="${ctx}/static/plugins/easyui/jquery-easyui-theme/<c:out value="${cookie.themeName.value}" default="default"/>/easyui.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/plugins/easyui/jquery-easyui-theme/icon.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/plugins/easyui/icons/icon-all.css" rel="stylesheet" type="text/css" />
<!-- ztree样式 -->
<link href="${ctx}/static/plugins/ztree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/plugins/easyui/jquery/jquery-1.11.1.min.js"></script>

<script src="${ctx}/static/plugins/easyui/jquery-easyui-1.3.6/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jquery-easyui-1.3.6/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>

<!-- jquery扩展 -->
<script src="${ctx}/static/plugins/easyui/release/jquery.jdirk.min.js"></script>

<!-- easyui扩展 -->
<link href="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.progressbar.js"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.slider.js"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.linkbutton.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.validatebox.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.combo.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.combobox.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.menu.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.searchbox.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.panel.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.window.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.dialog.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.layout.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.tree.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.datagrid.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.treegrid.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.combogrid.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.combotree.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.tabs.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.theme.js" type="text/javascript"></script>

<!--<script src="${ctx}/static/plugins/easyui/release/jeasyui.extensions.all.min.js"></script>-->

<script src="${ctx}/static/plugins/easyui/icons/jeasyui.icons.all.js" type="text/javascript"></script>
<!--<script src="${ctx}/static/plugins/easyui/release/jeasyui.icons.all.min.js"></script>-->
    
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.icons.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.gridselector.js" type="text/javascript"></script>

<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jquery.toolbar.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jquery.comboicons.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jquery.comboselector.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jquery.portal.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jquery.my97.js" type="text/javascript"></script>    
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.ty.js"></script>

<!-- ztree扩展 -->
<script src="${ctx}/static/plugins/ztree/js/jquery.ztree.all-3.5.min.js"></script>
<script src="${ctx}/static/plugins/ztree/js/jquery.ztree.exhide-3.5.min.js"></script>

<link rel="stylesheet" href="${ctx }/static/plugins/easyui/common/other.css"></link>
<script type="text/javascript" src="${ctx }/static/js/dateutils.js"></script>
<script type="text/javascript" src="${ctx }/static/js/validation.js"></script>
<style type="text/css">
iframe.panel-iframe {
height: 100%;
}
</style>
<script>
var ctx = "${ctx}";
//全局的AJAX访问，处理AJAX清求时SESSION超时
$.ajaxSetup({
	cache: false, //关闭AJAX相应的缓存
    contentType:"application/x-www-form-urlencoded;charset=utf-8",
    complete:function(XMLHttpRequest,textStatus){
          //通过XMLHttpRequest取得响应头，sessionstatus           
          var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus"); 
          if(sessionstatus=="timeout"){
        	  /* alert("会话超时,请重新登陆！"); */
               //跳转的登录页面
               window.location.replace('${ctx}/m/login');
       		}	
    }
});

/**
 * 日期段初始化方法
 */
function initDateFilter(beginDate,endDate){
	if(beginDate != null && beginDate != undefined){
		$("#"+beginDate).my97({
			maxDate:'%y-%M-%d',
			onShowPanel : function() {
				var end_Date = $("#"+endDate).my97("getValue");
				if (end_Date.length > 0) {
					$("#"+beginDate).my97({
						maxDate : end_Date
					});
				}
			
			},
			onHidePanel : function() {
				var begin_Date = $("#"+beginDate).my97("getValue");
	
				if (begin_Date.length > 0) {
					$("#"+endDate).my97({
						minDate : begin_Date
					});
				}
			}
	
		});
	}

	if(endDate != null && endDate != undefined){
		$("#"+endDate).my97({
			maxDate:'%y-%M-%d',
			onHidePanel : function() {
				var end_Date = $("#"+endDate).my97("getValue");
	
				if (end_Date.length > 0) {
					$("#"+beginDate).my97({
						maxDate : end_Date
					});
				}
			}
	
		});
	}
}


/**
 * 日期段初始化方法
 *	%y	当前年	%M	当前月	%d	当前日	%ld	本月最后一天	%H	当前时	%m	当前分	%s	当前秒
 *	{}	运算表达式,如:{%d+1}:表示明天
 */
function dateFilter(beginDate,endDate,fmt){
	$("#"+beginDate).my97({
		maxDate:fmt,
		onShowPanel : function() {
			var end_Date = $("#"+endDate).my97("getValue");
			if (end_Date.length > 0) {
				$("#"+beginDate).my97({
					maxDate : end_Date
				});
			}
		
		},
		onHidePanel : function() {
			var begin_Date = $("#"+beginDate).my97("getValue");

			if (begin_Date.length > 0) {
				$("#"+endDate).my97({
					minDate : begin_Date
				});
			}
		}

	});
	$("#"+endDate).my97({
		maxDate:fmt,
		onHidePanel : function() {
			var end_Date = $("#"+endDate).my97("getValue");

			if (end_Date.length > 0) {
				$("#"+beginDate).my97({
					maxDate : end_Date
				});
			}
		}

	});
 }	
	/**
	 * 日期段初始化方法
	 */
	function initDate(beginDate,endDate){
		if(beginDate != null && beginDate != undefined){
			$("#"+beginDate).my97({
				maxDate:'%y-%M-%d',
				onShowPanel : function() {
					var end_Date = $("#"+endDate).my97("getValue");
					if (end_Date.length > 0) {
						$("#"+beginDate).my97({
							maxDate : end_Date
						});
					}
				
				},
				onHidePanel : function() {
					var begin_Date = $("#"+beginDate).my97("getValue");
					if (begin_Date.length > 0) {
						$("#"+endDate).my97({
							minDate : begin_Date
						});
					}
				}
		
			});
		}

		if(endDate != null && endDate != undefined){
			$("#"+endDate).my97({
				onHidePanel : function() {
					var end_Date = $("#"+endDate).my97("getValue");
		
					if (end_Date.length > 0) {
						$("#"+beginDate).my97({
							maxDate : end_Date
						});
					}
				} 
		
			});
		}
	}

</script>
<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
