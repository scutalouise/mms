<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>数据采集系统_用户管理中心</title>
    <link href="static/css/user.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="static/plugins/jquery-1.9.1.min.js"></script>
    <script type="text/javascript">
    	$(function(){
    		
    		$.ajax({
    			url : "/das/user/loginInfo",
    			type : "get",
    			dataType : "json",
    			success : function(data) {
    				$("#login-user").html("当前用户： "+data.userName);
    				$("#login-ip").html("当前登陆IP： " + data.loginIp);
    				$("#login-total").html("共计登陆次数： " + data.totalLogin + "次");
    				$("#lastLogin").html("上次登录时间： " + data.lastLoginTime);
    			}
    		});
    		
    		$("#submit").click(function(){
    			var oldpwd = $("#oldpwd").val();
    			var newpwd = $("#newpwd").val();
    			var repwd = $("#repwd").val();
    			validate(oldpwd,newpwd,repwd);
    		})
    	})
    	
    	function updatePassword(oldpwd,newpwd){
    		$.ajax({
    			url : "/das/user/oldPassword/" + oldpwd + "/newPassword/" + newpwd,
    			type : "put",
    			dataType : "json",
    			error : function(){
    				alert("请求异常！");
    			},
    			success : function(data){
    				var msg = data.message;
    				if (msg == "success") {
    					alert("密码修改成功");
    				} else if(msg == "no session"){
    					window.location.href = "/das/login";
    				} else if(msg == "error password") {
    					$("#oldpwdval").html("原始密码不正确");
    				} else {
    					alert(msg);
    				} 
    			}
    		});
    	}
    	
    	function validate(oldpwd,newpwd,repwd){
    		var val = false;
    		if(oldpwd == ""){
    			$("#oldpwdval").html("不能为空");
    		} else {
    			$("#oldpwdval").html("");
    			if (newpwd.length >= 8 && newpwd.length <= 20){
    				if (/\d+/.test(newpwd) && /[A-Za-z]+/.test(newpwd) && /^[0-9A-Za-z]{8,20}$/.test(newpwd)) {
    					if (newpwd != oldpwd) {
	    					$("#newpwdval").html("");
	    					if (newpwd == repwd) {
	    						$("#repwdval").html("");
	        					updatePassword(oldpwd,newpwd);
	        				} else {
	        					$("#repwdval").html("确认密码不一致");
	        				}
    					} else {
    						$("#newpwdval").html("新密码不能与旧密码一致");
    					}
    				} else {
	    				$("#newpwdval").html("必须为英文与数字组合");
    				}
    			} else {
    				$("#newpwdval").html("密码长度为8到20位");
    			}
    			
    		}
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
                <div class="col-xs-offset-5 col-xs-6">
                    <div class="nav_1_1">
                        <img src="static/images/xx/0_dh_t.png">
                        <a href="/das/loginOut">退出系统</a>
                    </div>
                    <div class="nav_1_1">
                        <img src="static/images/xx/0_dh_yh.png">
                        <a id="login-user"></a>
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
                <div class="col-xs-12" id="main_right_1">
                    <div
                        class="col-xs-offset-4 col-sm-offset-4 col-md-offset-4  col-lg-offset-5 col-xs-7 col-sm-7 col-md-7 col-lg-5">
                        <h5 id="login-ip"></h5>
                    </div>
                    <div
                        class="col-xs-offset-4 col-sm-offset-4 col-md-offset-4  col-lg-offset-5 col-xs-7 col-sm-7 col-md-7 col-lg-5">
                        <h5 id="login-total"></h5>
                    </div>
                    <div
                        class="col-xs-offset-4 col-sm-offset-4 col-md-offset-4  col-lg-offset-5 col-xs-7 col-sm-7 col-md-7 col-lg-5">
                        <h5 id="lastLogin"></h5>
                    </div>
                    <div
                        class="col-xs-offset-4 col-sm-offset-4 col-md-offset-4  col-lg-offset-5 col-xs-7 col-sm-7 col-md-7 col-lg-5">
                        <h3></h3>
                    </div>
                </div>
                <div id="pwd_">
                    <div class="col-xs-offset-1 col-sm-offset-1 col-md-offset-1 col-lg-offset-1 col-xs-offset-1 col-xs-10 col-sm-10 col-md-10 col-lg-10"
                        id="pwd_1">
                        <div class="col-xs-offset-4 col-sm-offset-4 col-md-offset-4 col-lg-offset-5 col-xs-6 ">
                            <a>
                                <img class="media-object" src="static/images/xx/2_xx_xiugaimima_.png">
                            </a>
                        </div>
                        <div class="col-xs-offset-4 col-sm-offset-4 col-md-offset-4 col-lg-offset-5 col-xs-6 ">
                            <a>
                                <p class="bottom-left"><h4>修改密码</h4></p>
                            </a>
                        </div>
                        <div class="col-xs-12" id="pwd_2">
                            <div class="col-md-offset-1 col-lg-offset-1 col-xs-12 col-sm-12 col-md-11 col-lg-11">
                                <div class="form-group">
                                    <div class="col-xs-6 col-sm-5 col-md-5  col-lg-3" id="pwd_xg_1">
                                        <p class="text-right">原始密码</p>
                                    </div>
                                    <div class="  col-xs-6 col-sm-6  col-md-6  col-lg-5">
                                        <input type="password" class="form-control" placeholder="请输入原始密码" id="oldpwd">
                                    </div>
                                    <div class="  col-xs-6 col-sm-6  col-md-6  col-lg-4" >
                                        <label style="margin:0;padding:0;color:red;height:22px;" id="oldpwdval"></label>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-offset-1 col-lg-offset-1 col-xs-12 col-sm-12 col-md-11 col-lg-11">
                                <div class="form-group">
                                    <div class="col-xs-6 col-sm-5 col-md-5  col-lg-3" id="pwd_xg_2">
                                        <p class="text-right">新&nbsp;密&nbsp;码</p>
                                    </div>
                                    <div class="  col-xs-6 col-sm-6  col-md-6  col-lg-5">
                                        <input type="password" class="form-control" placeholder="请输入新密码" id="newpwd">
                                    </div>
                                    <div class="  col-xs-6 col-sm-6  col-md-6  col-lg-4" >
                                        <label style="margin:0;padding:0;color:red;height:22px;" id="newpwdval"></label>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-offset-1 col-lg-offset-1 col-xs-12 col-sm-12 col-md-11 col-lg-11 ">
                                <div class="form-group">
                                    <div class="col-xs-6 col-sm-5 col-md-5  col-lg-3" id="pwd_xg_3">
                                        <p class="text-right">确认密码</p>
                                    </div>
                                    <div class="  col-xs-6 col-sm-6  col-md-6  col-lg-5">
                                        <input type="password" class="form-control" placeholder="请再次输入新密码" id="repwd">
                                    </div>
                                    <div class="  col-xs-6 col-sm-6  col-md-6  col-lg-4" >
                                        <label style="margin:0;padding:0;color:red;height:22px;" id="repwdval"></label>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-offset-1 col-lg-offset-1 col-xs-12 col-sm-12 col-md-11 col-lg-11">
                                <div class="form-group">
                                    <div
                                        class="col-xs-offset-2 col-sm-offset-2 col-md-offset-2 col-lg-offset-2 col-xs-6 col-sm-6  col-md-6  col-lg-6"
                                        id=" pwd_xg_4">
                                        <button class="btn btn-sm btn-block  btn-success" id="submit">提交</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
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