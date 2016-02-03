<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>数据采集系统首页</title>
    <link href="static/css/index.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="static/plugins/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="static/plugins/jquery.form.js"></script>
    <script type="text/javascript">
    	$(function(){
    		
    		$("#loginForm").submit(function(){
    			var userName = $("#exampleInputName2").val();
    			var password = $("#inputPassword3").val();
    			var options = {
    				url : "/das/login/user",
    				type : "POST",
    				data : {"userName":userName,"password" : password},
    				beforeSubmit : function(formData, jqForm, options){
    					if (userName.trim() == ""){
    						$("#errorMsg").text("请输入用户名！");
    						return false;
    					}
    					if (password == ""){
    						$("#errorMsg").text("请输入密码！");
    						return false;
    					} 
    					return true;
    				},
    				success : function(response, status){
    					
    					var message = response.message;
    					if (message == "success") {
    						window.location.href="/das/index";
    					} else {
    						$("#errorMsg").text(message);
    					}
    				},
    				dataType : "json"
    			}
    			$(this).ajaxSubmit(options);
    		});
    			
    	});
    	
    
    </script>
</head>
<body>
<!--LOGO区域-->
<nav id="nav_1" class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <img id="img_1" src="static/images/sy/2_nav_logo.png" class="img-responsive">
    </div>
</nav>
<!--中间主体部分 -->
<div id="main">
    <div class="container-fluid" id="main_1">
        <div class="row">
            <div
                class="col-xs-5  col-sm-5  col-md-5  col-lg-4 col-xs-offset-1 col-sm-offset-1 col-md-offset-2 col-lg-offset-3"
                id="main_left">
                <img src="static/images/sy/1_t.png" class="img-responsive">
            </div>
            <div class="col-xs-4 col-sm-4  col-md-4  col-lg-3  col-sm-offset-1 col-md-offset-1 col-lg-offset-1"
                 id="main_right">
                <div id="yh_1">
                    <form class="form-horizontal" id="loginForm" onsubmit="return false">
                        <div class="col-xs-10 col-xs-offset-2">
                            <ul class="media-list">
                                <li class="media">
                                    <div class="media-left">
                                        <a>
                                            <img class="media-object" src="static/images/sy/3_yonghu.png">
                                        </a>
                                    </div>
                                    <div class="media-body">
                                        <h3 class="media-heading" id="yh_denglu">管理登陆</h3>
                                    </div>
                                </li>
                            </ul>
                        </div>
                        <div class="form-group" style="margin:0;padding:0;">
                            <a class="col-xs-offset-1 col-xs-9 col-sm-9 control-label" style="font-size:10px;color:red;padding:0;height:20px;" id="errorMsg">
                            </a>
                        </div>
                        <div class="form-group">
                            <a class="col-xs-offset-1 col-xs-3 col-sm-3 control-label" id="yh_2">用户名</a>

                            <div class=" col-xs-6 col-sm-6" id="yh_2_2">
                                <input type="text" class="form-control" id="exampleInputName2"
                                       placeholder="请输入用户名">
                            </div>
                        </div>
                        <div class="form-group">
                            <a
                               class="col-xs-offset-1 col-xs-3 col-sm-3 control-label">密　码</a>

                            <div class=" col-xs-6 col-sm-6">
                                <input type="password" class="form-control" id="inputPassword3"
                                       placeholder="请输入密码">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-xs-offset-4  col-sm-offset-4 col-xs-4 col-sm-6" id="yh_3">
                                <button type="submit" class="btn btn-sm btn-block  btn-success">确认登陆</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<!--页脚主体区域-->
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