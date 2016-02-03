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
               <input name="phoneNumber" id="phoneNumber" class="easyui-numberbox" value="${phoneNumber }" data-options="width:120,validType:['length[11,13]']"/>
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
  <%--  <embed id="ejatoolsPrinter" style="display:none" type="application/x-vnd.jatoolsPrinter"
         pluginspage="${ctx}//printer/jatoolsPrinter.exe" width="0" height="0"/>
   <object id="factory" style="display:none" codeBase="${ctx}/smsx.cab#Version=6,4,438,06"
            height="0" width="0" classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814"></object>
   <object id="webBrowser" style="display:none" height="0" width="0" classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2"></object> --%>
            
   <!-- 打印开始 -->
   <div id='page1' style="text-align:center;position:relative;top: 18px;" width='150px' height='150px'>
      <img id="img" src='${ctx}/QRimages/${path }' width='150px' height='150px' style='transform:rotate(-90deg)'/>
   </div>
   <!-- 打印结束 -->
<script type="text/javascript">

function updateQR(){
	$('#mainform').form('submit',{
		  url:'${ctx }/device/twoDimentionCode/createQR?rand='+Math.random(),
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
			    	  topMargin:20,    //上边距
			          leftMargin:10,   //左边距
			          bottomMargin:0,  //下边距
			          rightMargin:10}, //右边距
	        documents:document, //打印文件
	        copyrights: '杰创软件拥有版权  www.jatools.com' //必须声明，版权所有
	    }; 
	    
	    $("img").attr({width:115,height:115}); //二维码打印长宽
	    
	    if(how=="print"){
	    	jatoolsPrinter.print(myDoc,false);
	    	timeout(parent_dialog);
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

function printTwoDimentionCode1(identifier){
     if(browser=="IE"){
    	//$('<object name="printWB" style="display:none" classid="clsid:8856F961-340A-11D0-A96B-00C04FD705A2" width="0" height="0"></object>').prependTo('#dvData');
        // $('<object name="printWB" style="display:none" classid="clsid:B43D3361-D075-4BE2-87FE-057188254255" width="0" height="0" codebase="jatoolsPrinter.cab#version=8,6,0,0"></object>').prependTo('#dvData');
    	 var new_window=com_window();
    	 var dvData=new_window.document.getElementById("dvData");
         var img=new_window.document.getElementById("img");
         dvData.style.top = 5;
         img.width = 102;
  	     img.height = 102;
  	     img.style.transform="rotate(90deg)";
  	    // document.all.printWB.ExecWB(7,1) ;
  	     callback(identifier);
  	     
     }else if(browser=="Chrome"){
    	 $('<input type="button" id="preview" value="预览"/>').appendTo('#dvData');
    	 $('<input type="button" id="back" value="返回"/>').appendTo('#dvData');
    	 var new_window=com_window();
    	 var dvData=new_window.document.getElementById("dvData");
    	 var preview=new_window.document.getElementById("preview");
         var back=new_window.document.getElementById("back");
         var img=new_window.document.getElementById("img");
    	 preview.onclick=function(){
   	    	 dvData.style.top = 5;
   	    	 preview.style.display = "none";
   	 	     back.style.display = "none";
   	 	     img.width = 95;
   	 	     img.height = 95;
   	 	     img.style.transform="rotate(90deg)";
   	 	     new_window.print();
   	 	     new_window.close();
   	 	     callback(identifier);
   	    };
   	    back.onclick=function(){
   			 new_window.close(); 	   
   		};
     }else if(browser=="Firefox"){
         var obj=$("#dvData").html();
  	     var new_window=window.open("打印二维码","_blank");
         new_window.document.write(obj);
         new_window.document.close();
         callback(identifier);
	     
     }else if(browser=="Opera" || browser=="Safari"){
    	 alert("浏览器不支持");
     }
 } 
 
 function com_window(){
	 var bodyhtml = window.document.body.innerHTML;
	 var startArea = "<!-- 打印开始 -->";  
	 var endArea = "<!-- 打印结束 -->";  
	 var printhtml = bodyhtml.substring(bodyhtml.indexOf(startArea)+13,bodyhtml.indexOf(endArea));
	 var new_window = window.open("打印二维码","_blank");
	 //new_window.document.write("<html><head>");
	 //new_window.document.write("<sc" + "ript type='text/javascript' src='/device/static/js/jquery-1.11.1.min.js'></sc" + "ript>");
	 //new_window.document.write("</head>"); 
	 //new_window.document.write("<body>");
	 new_window.document.write(printhtml);
	 new_window.document.close();
	 //new_window.document.write("</body></html>");
	 return new_window;
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