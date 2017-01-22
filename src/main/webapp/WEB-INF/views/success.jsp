<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Registration Confirmation Page</title>
</head>
<body>

<div>
	<jsp:include page="header.jsp"/>
</div>

<div class="container">

	message : ${success}
	<br/>
	<br/>
	Go back to <a href="/home">Home</a>
</div>
<div>
	<jsp:include page="bottom.jsp"/>
</div>

</body>
</html>