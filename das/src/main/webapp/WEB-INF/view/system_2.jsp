<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>系统配置中心—设置数据巡检时间</title>
    <link href="static/css/system_2.css" rel="stylesheet" type="text/css">
    <style type="text/css">
    	.tabcontent{
    		min-height:480px;
    		height:auto;
    	}
    </style>
    <script type="text/javascript" src="static/plugins/jquery-1.9.1.min.js"></script>
    <script type="text/javascript">
    	$(function(){
    		
    		$.ajax({
    			url : "/das/config/properties/quartzCron/key/hostTrigger",
    			type : "get",
    			dataType : "json",
    			success : function(data) {
    				var value = data.hostTrigger;
    				$("input:radio[value='" + value + "']").attr("checked","checked");
    			}
    		});
    		
    		$("#submit").click(function(){
    			var value = $("input:radio:checked").val();
    			$.ajax({
    				url : "/das/config/properties/quartzCron/key/hostTrigger/value/" + value,
    				type : "put",
    				dataType : "json",
    				error : function(){
    					alert("请求异常！");
    				},
    				success : function(data){
    					if (data.message == "success") {
    						alert("配置成功");
    					} else {
    						alert(data.message);
    					}
    				}
    			});
    		});
    	});
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
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                        <ul class="nav nav-pills nav-justified">
                            <li role="presentation" ><a href="/das/system1">设置数据交互时间</a></li>
                            <li role="presentation" class="active"><a href="/das/system2">设置数据巡检时间</a></li>
                            <li role="presentation"><a href="/das/system3">系统配置</a></li>
                        </ul>
                    </div>
                </div>
                <div class="col-xs-12 tabcontent" id="main_right_2">
                    <div class="col-xs-10 col-xs-offset-2" id="tb_1">
                        <ul class="media-list">
                            <li class="media">
                                <div class="media-left">
                                    <a><img class="media-object"  src="static/images/xx/3_xx_xunjian.png"></a>
                                </div>
                                <div class="media-body">
                                    <div class="alert alert-success" role="alert" id="tb_2">
                                        <strong>请设置数据巡检时间（分钟）</strong></div>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <div
                        class="col-xs-offset-1  col-sm-offset-2 col-xs-10 col-sm-10  col-md-offset-0  col-md-6 col-lg-offset-1 col-lg-5">
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="1">01 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="2">02 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="3">03 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="4">04 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="5">05 </label></div>
                    </div>
                    <div
                        class="col-xs-offset-1  col-sm-offset-2 col-md-offset-0  col-xs-10 col-sm-10 col-md-6 col-lg-offset-1 col-lg-5">
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="6">06 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="7">07 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="8">08 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="9">09 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="10">10 </label></div>
                    </div>
                    <div
                        class="col-xs-offset-1  col-sm-offset-2  col-md-offset-0 col-xs-10  col-md-offset-0 col-sm-10 col-md-6 col-lg-offset-1 col-lg-5">
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions"  value="11">11 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions"  value="12">12 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions"  value="13">13 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions"  value="14">14 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions"  value="15">15 </label></div>
                    </div>
                    <div
                        class="col-xs-offset-1  col-sm-offset-2  col-md-offset-0  col-xs-10 col-sm-10 col-md-6 col-lg-offset-1 col-lg-5">
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="16">16 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="17">17 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="18">18 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="19">19 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="20">20 </label></div>
                    </div>
                    <div
                        class="col-xs-offset-1  col-sm-offset-2  col-md-offset-0  col-xs-10 col-sm-10 col-md-6 col-lg-offset-1 col-lg-5">
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="21">21 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="22">22 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="23">23 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="24">24 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="25">25 </label></div>
                    </div>
                    <div
                        class="col-xs-offset-1  col-sm-offset-2 col-md-offset-0   col-xs-10 col-sm-10 col-md-6 col-lg-offset-1 col-lg-5">
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="26">26 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="27">27 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="28">28 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="29">29 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="30">30 </label></div>
                    </div>
                    <div
                        class="col-xs-offset-1  col-sm-offset-2  col-md-offset-0 col-xs-10 col-sm-10 col-md-6 col-lg-offset-1 col-lg-5">
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="31">31 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="32">32 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="33">33 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="34">34 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="35">35 </label></div>
                    </div>
                    <div
                        class="col-xs-offset-1  col-sm-offset-2  col-md-offset-0  col-xs-10 col-sm-10 col-md-6 col-lg-offset-1 col-lg-5">
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="36">36 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="37">37 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="38">38 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="39">39 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="40">40 </label></div>
                    </div>
                    <div
                        class="col-xs-offset-1  col-sm-offset-2 col-md-offset-0  col-xs-10 col-sm-10 col-md-6 col-lg-offset-1 col-lg-5">
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="41">41 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="42">42 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="43">43 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="44">44 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="45">45 </label></div>
                    </div>
                    <div
                        class="col-xs-offset-1  col-sm-offset-2 col-md-offset-0  col-xs-10 col-sm-10 col-md-6 col-lg-offset-1 col-lg-5">
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="46">46 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="47">47 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="48">48 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="49">49 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="50">50 </label></div>
                    </div>
                    <div
                        class="col-xs-offset-1  col-sm-offset-2 col-md-offset-0  col-xs-10 col-sm-10 col-md-6 col-lg-offset-1 col-lg-5">
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="51">51 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="52">52 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="53">53 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="54">54 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="55">55 </label></div>
                    </div>
                    <div
                        class="col-xs-offset-1  col-sm-offset-2 col-md-offset-0  col-xs-10 col-sm-10 col-md-6 col-lg-offset-1 col-lg-5">
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="56">56 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="57">57 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="58">58 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="59">59 </label></div>
                        <div class="radio-inline"><label><input type="radio" name="inlineRadioOptions" value="60">60 </label></div>
                    </div>
                    <div class="col-xs-offset-4  col-sm-offset-4 col-xs-8 col-sm-8 col-md-8 col-lg-offset-5 col-lg-4" id="qr">
                        <button id="submit" class="btn  btn-info">确认保存</button>
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