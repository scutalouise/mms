<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>系统配置中心—系统设置</title>
    <link href="static/css/system_3.css" rel="stylesheet" type="text/css">
     <style type="text/css">
    	.valcls{
    		color:red;
    		height:22px;
    		margin:0;
    		padding:0;
    	}
    	.tabcontent{
    		min-height:480px;
    		height:auto;
    	}
    </style>
    <script type="text/javascript" src="static/plugins/jquery-1.9.1.min.js"></script>
    <script type="text/javascript">
    	$(function(){
    		$.ajax({
    			url : "/das/config/systemConfig",
    			type : "get",
    			dataType : "json",
    			success : function(data) {
    				var address = data.centerAddress;
    				if (address != null && address != "") {
    					$("input[name='address']").val(address);
    				} else {
    					$("input[name='address']").val("");
    				}
    				var code = data.systemCode;
    				if (code != null && code != "") {
    					$("input[name='code']").val(code);
    				} else {
    					$("input[name='code']").val("");
    				}
    			}
    		});
    		
    		$.ajax({
    			url : "/das/config/properties/keys/independence",
    			type : "get",
    			dataType : "json",
    			success : function(data) {
    				$("input:radio[value='" + data.independence + "']").attr("checked","checked");
    			}
    		});
    		
    		$("#save_1").click(function(){
    			var address = $("input[name='address']").val();
    			if (address != "") {
			    	$("#kb_1").text("");
    				var independence = $("input:radio:checked").val();
    				$.ajax({
    					url : "/das/config/systemConfig/centerAddress",
    					type : "post",
    					data : {"centerAddress" : address, "independence" : independence},
    					dataType : "json",
    					success : function(data){
    						alert(data.message);
    					}
    				});
    			} else {
    				$("#kb_1").text("所填项不能为空");
    			}
    		});
    		
    		$("#save_2").click(function(){
    			var code = $("input[name='code']").val();
    			if (code != "") {
    				if (/^\d{15}$/.test(code)) {
					   	$("#kb_2").text("");
					   	$.ajax({
					   		url : "/das/config/systemConfig/systemCode",
					   		type : "post",
					   		data : {"systemCode" : code},
					   		dataType : "json",
					   		success : function(data){
					   			alert(data.message);
					   		}
					   	});
    				} else{
    					$("#kb_2").text("编号必须为15位数字");
    				}
    			} else {
    				$("#kb_2").text("所填项不能为空");
    			}
    		});
    		
    		$("#save_3").click(function(){
    			var value = $("input:radio:checked").val();
	    		$.ajax({
	    			url : "/das/config/properties/independence/value/" + value,
	    			type : "put",
	    			dataType : "json",
	    			success : function(data){
	    				$("#kb_2").text("");
	    				alert(data.message);
	    			}
	    		});
    		});
    		
    	});
    	
    	function saveAddress(address){
    		$.ajax({
				url : "/das/config/systemConfig/centerAddress",
				type : "post",
				data : {"centerAddress" : address},
				dataType : "json",
				success : function(data){
					alert(data.message);
				}
			});
    	}
    	
    </script>
</head>
<body>
<!--LOGO区域-->
<nav id="nav_1" class="navbar navbar-default navbar-fixed-top">
    <div class="container-fluid">
        <div class="row">
            <div class="col-xs-7 col-xs-offset-1">
                <img id="img_1" src="static/images/sy/2_nav_logo.png">
            </div>
            <div class="col-xs-5">
            </div>
            <div class="nav_1_bj">
                <div class="col-sm-offset-5 col-xs-12 col-sm-6">
                    <div class="nav_1_1">
                        <img src="static/images/xx/0_dh_t.png">
                        <a href="/das/loginOut">退出系统</a>
                    </div>
                    <div class="nav_1_1">
                        <img src="static/images/xx/0_dh_yh.png">
                        <a>当前登陆：admin</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</nav>
<!--中间主体部分 -->
<div id="main">
    <div class="container-fluid" id="main_1">
        <div class="row">
            <!--侧边主菜单-->
            <div
                class="col-xs-offset-1 col-sm-offset-1 col-md-offset-1 col-lg-offset-1  col-xs-3  col-sm-3  col-md-3  col-lg-3 "
                id="main_left">
                <div class=" col-sm-offset-2 col-md-offset-2 col-lg-offset-4  col-xs-9 col-sm-8 col-md-7 col-lg-7">
                    <img src="static/images/xx/1_xx_yh.png" class="m_left_1">
                </div>
                <div class="col-xs-12  col-sm-12 col-md-12 col-lg-12">
                    <p><a href="/das/index" class="btn btn-block  btn-md  btn-success"
                          role="button">用户管理中心&nbsp;&nbsp;&raquo;</a>
                    </p>
                </div>
                <div class=" col-sm-offset-2 col-md-offset-2 col-lg-offset-4  col-xs-9 col-sm-8 col-md-7 col-lg-7">
                    <img src="static/images/xx/1_xx_pz.png" class="m_left_2">
                </div>
                <div class="col-xs-12  col-sm-12 col-md-12 col-lg-12">
                    <p><a href="/das/system1" class="btn btn-block  btn-md  btn-success"
                          role="button">系统配置中心&nbsp;&nbsp;&raquo;</a></p>
                </div>
            </div>
            <!--中间的主体部分-->
            <div class="col-xs-7  col-sm-7  col-md-7  col-lg-7 " id="main_right">
                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" id="main_right_1">
                    <!--设置导航-->
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                        <ul class="nav nav-pills nav-justified">
                            <li role="presentation"><a href="/das/system1">设置数据交互时间</a></li>
                            <li role="presentation"><a href="/das/system2">设置数据巡检时间</a></li>
                            <li role="presentation" class="active"><a href="/das/system3">系统配置</a></li>
                        </ul>
                    </div>
                </div>
                <!--设置中间-->
                <div class="col-xs-12 tabcontent" id="main_right_2">
                    <div class=" col-md-offset-1 col-lg-offset-3  col-xs-12 col-sm-12 col-md-12 col-lg-6" id="tb_1">
                        <a><img class="media-object" src="static/images/xx/4_xx_dingshipz_1.png"></a>
                    </div>
                    <!--设置主体内容-->
                    <div class="col-xs-12">
                        <div class="form-group" id="main_right_3">
                            <div class="col-xs-6 col-sm-6 col-md-6  col-lg-4" id="xg_1">
                                <p class="text-right">设置运维中心访问地址：</p>
                            </div>
                            <div class=" col-xs-6 col-sm-6  col-md-6  col-lg-4">
                                <input type="text" class="form-control" placeholder="请输入运维中心访问地址" name="address">
                            </div>
                            <div
                                class="col-xs-offset-6  col-sm-offset-6 col-md-offset-0  col-lg-offset-0 col-xs-3 col-sm-3  col-md-3  col-lg-2">
                                <button class="btn  btn-info" id="save_1">保存</button>
                            </div>
                            <div class="col-xs-12 col-sm-12  col-md-12  col-lg-12 valcls " id="kb_1" align="center">
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-12" id="main-right_4">
                        <div class="form-group">
                            <div class="col-xs-6 col-sm-6 col-md-6  col-lg-4" id="xg_2">
                                <p class="text-right">数据采集器编号配置：</p>
                            </div>
                            <div class=" col-xs-6 col-sm-6  col-md-6  col-lg-4">
                                <input type="text" class="form-control" placeholder="数据采集器编号配置" name="code">
                            </div>
                            <div
                                class="col-xs-offset-6  col-sm-offset-6 col-md-offset-0  col-lg-offset-0 col-xs-3 col-sm-3  col-md-3  col-lg-2">
                                <button id="save_2" class="btn  btn-info">保存</button>
                            </div>
                            <div class="col-xs-12 col-sm-12  col-md-12  col-lg-12 valcls" id="kb_2" align="center">
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-12" id="main-right_5">
                        <div class="form-group">
                            <div class="col-xs-6 col-sm-6 col-md-6  col-lg-4" id="xg_3">
                                <p class="text-right">选择系统独立性配置：</p>
                            </div>
                            <div class="col-xs-4 col-sm-3  col-md-3  col-lg-2">
                                <div class="radio-inline"><label><input type="radio" checked="checked"
                                                                        name="inlineRadioOptions" value="false">集成部署</label></div>
                            </div>
                            <div class="col-xs-4 col-sm-3  col-md-3  col-lg-2">
                                <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="true">独立部署
                                </label></div>
                            </div>
                            <div
                                class="col-xs-offset-6  col-sm-offset-6 col-md-offset-0  col-lg-offset-0 col-xs-3 col-sm-3  col-md-3  col-lg-2">
                                <button id="save_3" class="btn  btn-info">保存</button>
                            </div>
                            <div class="col-xs-12 col-sm-12  col-md-12  col-lg-12" id="kb_3">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<nav id="nav_2" class="navbar navbar-default navbar-fixed-bottom">
    <div class="container">
        <ul>
            <li>
                <a>Version: SK_DHRS_NX0100</a>
            </li>
            <li>
                <a>版权所有© 四川阿特申商贸有限公司</a> |
                <a>四川申控物联科技有限公司</a>
            </li>
        </ul>
    </div>
</nav>
</body>
</html>