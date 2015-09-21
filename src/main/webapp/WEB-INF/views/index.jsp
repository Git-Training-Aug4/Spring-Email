<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<title>Send Email</title>
</head>
<body style="margin: 50px;">
	<h1>Send Email</h1>
	<form action="${pageContext.request.contextPath}/sendEmail" method="post" enctype="multipart/form-data">
		<div>
			<label for="recipient">To :</label>
			<input type="text" name="recipient" id="recipient">
		</div>
		<br/>
		<div>
			<label for="subject">Subject :</label>
			<input type="text" name="subject" id="subject">
		</div>
		<br/>
		<div>
			<label for="message">Message :</label>
			<br/>
		</div>
		<div>
			<textarea rows="10" cols="100" name="message" id="message"></textarea>
		</div>
		
		<div>
			file 1: <input type="file" name="file"/>
		</div>
		<div>
			file 2: <input type="file" name="file"/>
		</div>
		<div>
			<input type="submit" value="Submit" />
		</div>
	</form>
</body>
</html>
