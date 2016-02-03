<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
 
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>动态环境数据采集监控平台</title>
    <link rel="shortcut icon" href="${ctx}/static/images/favicon.ico">
    <link rel="stylesheet" href="${ctx }/static/monitorPlatform/css/main.css" type="text/css" media="screen" />
    <link rel="stylesheet" href="${ctx }/static/monitorPlatform/css/list.css" type="text/css" media="screen" />
    <script type="text/javascript" src="${ctx }/static/monitorPlatform/js/jquery.js"></script>

    <link href="${ctx }/static/monitorPlatform/css/metro-list.css" rel="stylesheet">
    <link href="${ctx }/static/monitorPlatform/css/metro-icons.css" rel="stylesheet">

    <link href="${ctx }/static/monitorPlatform/css/docs.css" rel="stylesheet">
    <script src="${ctx }/static/monitorPlatform/js/metro.js"></script>
<script type="text/javascript">
function loadBranchList(searchValue){
	var stateEnum="${stateEnum}"=="all"?"":"${stateEnum}"
	 $.post("${ctx}/monitorPlatform/loadList",{stateEnum:stateEnum,searchValue:searchValue}, function(data) {
		$("#accordion").html(data);
	});
}
function isPC() {
	    var userAgentInfo = navigator.userAgent;
	    var Agents = ["Android", "iPhone",
	                "SymbianOS", "Windows Phone",
	                "iPad", "iPod"];
	    var flag = true;
	    for (var v = 0; v < Agents.length; v++) {
	        if (userAgentInfo.indexOf(Agents[v]) > 0) {
	            flag = false;
	            break;
	        }
	    }
	    return flag;
	}
$(function(){
	if(isPC()){
		$("#system").show();
	}else{
		$("#system").hide();
	}
	loadBranchList();
});
function searchList(){
	var searchValue=$("#searchValue").val();
	loadBranchList(searchValue);
}
function exit() {
    if (confirm('确定退出')) { 
    	location.href = '${ctx}/m/logout';
    }
}
function monitormain() {
	location.href = "${ctx}/monitorPlatform/index";
}
function system() {
	location.href = "${ctx}/a"
}
</script>
</head>
<body>
    <div class="header">
        <div id="switcher">
            <div class="center">
                <div class="headlines">
                    <ul class="left-side">
                        <li class="logo">
                            <img src="${ctx }/static/monitorPlatform/images/0_nav_nongxinlogo.png" />
                        </li>
                    </ul>

                    <ul class="right-side">
                        <li>
							<div class="widget_blue">
								<div style="padding-top: 15px">
									<a href="javascript:exit()">退出</a>
								</div>
							</div>
						</li>
						<li style="margin-right: 5px" id="system">
							<div class="widget_darkgreen">
								<div style="padding-top: 15px">
									<a href="javascript:system()">后台</a>
								</div>
							</div>
						</li>
						<li style="margin-right: 5px">
							<div class="widget_blue">
								<div style="padding-top: 15px;">
									<a href="javascript:monitormain()">主页</a>
								</div>
							</div>
						</li>
						<li>
							<h1 style="margin: 0px;">
								admin<img src="${ctx }/static/monitorPlatform/images/user_2.png"
									style="width: 50px; height: 50px; vertical-align: middle; margin-left: 10px; margin-right: 10px" />
							</h1>
						</li>
                    </ul>
                </div>
            </div>

        </div>
    </div>
    <div class="overflow-y" style="height:100%;">
        <div class="metro-layout" style="width:100%;">
            <div class="magnifyingglass">
                <input type="text" style="border-radius:5px;padding: 0px 10px 0px 10px;" id="searchValue"/>
                <a href="javascript:searchList()"><img src="${ctx }/static/monitorPlatform/images/magnifyingglass.png" style="" /></a>
            </div>
            <div style="text-align:center;height:100px;width:100%;text-align:center">
                <div class="list_left">
                    <div class="navigation">
                        <div class="navigation_left">
                            <a href="${ctx }/monitorPlatform/index">
                                <img  src="${ctx }/static/monitorPlatform/images/left_round.png" />
                                <span>首页</span>
                            </a>
                        </div>
                        <div class="navigation_right">
                        	    正常<img src="${ctx }/static/monitorPlatform/images/green.png" />
                      	              预警<img src="${ctx }/static/monitorPlatform/images/red.png" />
                       	              异常<img src="${ctx }/static/monitorPlatform/images/yellow.png" />
                        </div>
                    </div>
                   <div id="accordion" style="margin-top:20px;"></div>
                </div>
            </div>
        </div>
        <div data-role="dialog" id="dialog" class="padding20" data-close-button="true" data-overlay="true" data-overlay-color="op-dark" data-overlay-click-close="true">
            <p>
                <div class="list_right">
                   
                    <div id="ips" class="ips">
                       
                           
                    </div>
                </div>
            </p>
        </div>
        <div style="width:100%;height:150px;float:left;">&nbsp;</div>
    </div>
    <footer class="footer">
   <div style="margin-top: 15px;">版权所有©成都申控物联科技有限公司 | Version: SK_DHRS_NX0100</div>
	</footer>
</body>
</html>



<script type="text/javascript">
    $(window).load(function () {
    });
    function showDialog(orgId) {
    	var loadHtml="";
    	$.ajax({
    		type:"post",
    		async : false,
    		url:"${ctx}/monitorPlatform/gitInfoList",
    		data:{
    			organizationIds:orgId
    		},success:function(data){
    			
    			for(var i=0;i<data.length;i++){
    				
    				loadHtml+='<div id="box_'+data[i].id+'" class="box_1 widget4x2 widget_lightgreen_1">'
			    				 +'<div class="box_1_left">'
				                     +'<a href="javascript:selectIP('+data[i].id+')">'
				                        +'<div><img src="${ctx}/static/monitorPlatform/images/icon/UPS.png" /></div>'
				                        +'<div class="box_1_left_font">'
				                           +'<span>IP：'+data[i].ip+'<br />名称：'+data[i].name+'</span>'
				                        +'</div>'
				                      +'</a>'
				                  +'</div>'
				                  +'<div class="box_1_right"><a href="${ctx}/monitorPlatform/details/${stateEnum}/'+data[i].id+'"><img src="${ctx}/static/monitorPlatform/images/right_round.png"  /></a></div>'
				             +'</div>';
    			}
    		}
    	});
    	$("#ips").html(loadHtml);
        var dialog = $("#dialog").data('dialog');
        if (!dialog.element.data('opened')) {
            dialog.open();
        } else {
            dialog.close();
        }
    }
    function selectIP(id) {
        var a = $('#box_' + id + '').hasClass("widget_darkgreen"); 
        if ($('#box_' + id + '').hasClass("widget_darkgreen")) {
            $('#box_' + id + '').removeClass("widget_darkgreen");
            $('#box_' + id + '').addClass("widget_lightgreen_1");
        } else {
            $('#box_' + id + '').addClass("widget_darkgreen");
            $('#box_' + id + '').removeClass("widget_lightgreen_1");
        }
    }

</script>