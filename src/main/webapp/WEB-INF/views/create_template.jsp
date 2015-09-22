<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>
    
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Create Template</title>
</head>
<body>
	<h1>Create Template</h1>
	<form action="${ pageContext.request.contextPath }/setTemplate" method="POST">
		<p>
			<label for="editor1">Editor 1:</label>
			<textarea cols="80" id="editor1" name="editor1" rows="10"></textarea>
		</p>
		<p>
			<input type="submit" value="Submit" />
		</p>
	</form>
	<ckeditor:replace replace="editor1" basePath="${ pageContext.request.contextPath }/static/ckeditor/" />


</body>
</html>