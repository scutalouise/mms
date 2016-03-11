<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
     <div id="uploadDiv">
	     <form id="uploadMainform" action="${ctx }/system/user/import" method="post" enctype="multipart/form-data">
	     	    <input type="file" name="file" id="file_upload"/>
	     	    <input type="submit" value="上传" >
	     </form>
     </div>
</body>
</html>