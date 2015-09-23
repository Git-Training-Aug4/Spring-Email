<%@page import="com.email.ckeditor.Config"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>
    
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Create Template</title>
	<script type="text/javascript" src="${ pageContext.request.contextPath }/static/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="${ pageContext.request.contextPath }/static/bootstrap3/js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="${ pageContext.request.contextPath }/static/bootstrap3/css/bootstrap.min.css">
</head>
<body>
	<div class="container">
		
		<div class="row">
			<div class="col-sm-12">
				<div class="page-header">
					<h1>Create Template</h1>
				</div>
			</div>
		</div>
		
		<div class="row">
			<div class="col-sm-7">
				<form action="${ pageContext.request.contextPath }/setTemplate" method="POST">
					<div class="form-group">
						<label for="name">Template Name :</label>
						<input type="text" class="form-control" placeholder="Template Name">
					</div>
					<div class="form-group">
						<label for="template">Template :</label>
						<textarea id="editor1" name="template"></textarea>
					</div>
					<input type="submit" class="btn btn-info" value="Submit" />
				</form>
				<ckeditor:replace replace="editor1" basePath="${ pageContext.request.contextPath }/static/ckeditor/" />
				<%-- <ckeditor:replace replace="editor1" basePath="${ pageContext.request.contextPath }/static/ckeditor/" 
					config="<%= Config.createConfig() %>" events="<%= Config.createEventHandlers() %>" /> --%>
			</div>
			<div class="col-sm-5">
				<div style="background-color: #F2F0AE;height: 50px;"></div>
			</div>
		</div>
	
	</div>	

</body>
</html>