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
	
	<script type="text/javascript">
		$( document ).ready(function() {	
			$("#btnCreate").on('click',function(){
				var data = {
						'name':$('#nameInput').val(),
						'template':CKEDITOR.instances.editor1.getData()
				}
				console.log(data);
				$.ajax({
				 	data:JSON.stringify(data),
				    url: '${pageContext.request.contextPath}/create',
				    type :'POST',
				    contentType : 'application/json',
				    success : function(data){	
				  
				    },
				    error:function (jqXHR, textStatus, error){
				    	alert('CallBack error');
				    }
				});
			});
		});
	</script>
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
 				<form <%-- action="${ pageContext.request.contextPath }/setTemplate"  method="POST" --%>>
					<div class="form-group">
						<label for="name">Template Name :</label>
						<input type="text" name="name" id="nameInput" class="form-control" placeholder="Template Name">
					</div>
					<div class="form-group">
						<label for="template">Template :</label>
						<textarea id="editor1" name="template"></textarea>
					</div>
					<Button type="button" class="btn btn-info" id="btnCreate">Submit</Button>
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