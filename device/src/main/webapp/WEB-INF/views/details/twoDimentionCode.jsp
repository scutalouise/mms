<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/views/include/easyui.jsp" %>
</head>
<style type="text/css">
</style>
<body>
   <form id="mainform" action="" method="post">
      <table class="formTable">        
         <tr>
            <td>报修电话：</td>
            <td>
               <input name="id" type="hidden" value="${id }">
               <input name="identifier" type="hidden" value="${identifier }">
               <input name="phoneNumber" id="phoneNumber" class="easyui-validatebox" value="${phoneNumber }" data-options="width:130,validType:['phoneNumber']"/>
            </td>
            <td>
               <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="updateQR();">修改</a>
            </td>            
         </tr> 
      </table>
   </form>
   <!-- 插入打印控件 -->
   <object id="jatoolsPrinter" style="display:none" codebase="${ctx}/printer/jatoolsPrinter.cab#version=8,6,1,0"
         classid="clsid:B43D3361-D075-4BE2-87FE-057188254255" width="0" height="0"></object>          
   <!-- 打印开始 -->
   <div id='page1' style="text-align:center;position:relative;top: 18px;" width='150px' height='150px'>
      <img id="img" src='${ctx}/QRimages/${path }' width='150px' height='150px' style='transform:rotate(-90deg)'/>
   </div>
   <!-- 打印结束 -->
<script type="text/javascript">

function updateQR(){
	$('#mainform').form('submit',{
		  url:'${ctx }/device/twoDimentionCode/updateQR?rand='+Math.random(),
		  onSubmit:function(){
			  var isValid=$(this).form('validate');
			  return isValid;
		  }, 
		  success:function(data){ 
			var obj = JSON.parse(data); 
			$('#img').attr("src",'${ctx}/QRimages/'+obj.path);
			successTip(obj.msg);
		  } 
	}); 
}

//验证电话号码
$.extend($.fn.validatebox.defaults.rules, {
	phoneNumber: {
        validator: function (value) {
            return /((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/.test(value);
        },
        message: '请输入正确的电话号码，如：028-6487256,15005059587'
    }
})

//区别浏览器
function myBrowser(){
    var userAgent = window.navigator.userAgent; 
    if (userAgent.indexOf("Opera") > -1) {
        return "Opera"; //判断是否Opera浏览器
    }
    if (userAgent.indexOf("Firefox") > -1) {
        return "Firefox";//判断是否Firefox浏览器
    } 
    if (userAgent.indexOf("Chrome") > -1){
       return "Chrome";//判断是否Chrome浏览器
    }
    if (userAgent.indexOf("Safari") > -1) {
        return "Safari";//判断是否Safari浏览器
    } 
    if (!!window.ActiveXObject || "ActiveXObject" in window) {
    	return "IE";//判断是否IE浏览器
    }; 
}

var browser=myBrowser();

function printTwoDimentionCode(parent_dialog,identifier,how){
	if(browser=="IE"){
	    var myDoc = {
	        settings:{printer:'Argox MP-2140 PPLB', //打印机
			          orientation:1,   //1:纵打 2：横打
			    	  topMargin:15,    //上边距
			          leftMargin:10,   //左边距
			          bottomMargin:0,  //下边距
			          rightMargin:10}, //右边距
	        documents:document, //打印文件
	        copyrights: '杰创软件拥有版权  www.jatools.com' //必须声明，版权所有
	    }; 
	    
	   // $("img").attr({width:115,height:115}); //二维码打印长宽
	    
	    if(how=="print"){
	    	jatoolsPrinter.print(myDoc,true);
	    	timeout(parent_dialog);
	    	callback(identifier);
	    }
	    if(how=="preview"){
	    	jatoolsPrinter.printPreview(myDoc);
	    	timeout(parent_dialog);
	    }
	}else{
		parent.$.messager.alert('提示','浏览器不支持','info');
	}
} 

function timeout(parent_dialog){
	setTimeout(function(){
		parent_dialog.panel('close');
     }, 500);
}
 
function callback(identifier){
	 if(true){
         $.ajax({
    		type:'post',
    		url:'${ctx}/device/twoDimentionCode/callBack/'+identifier,
    		success:function(data){
    			successTip(data);
    		}
         });
     }
}

</script>
</body>
</html>