<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<div>
	<jsp:include page="header.jsp"/>
</div>
<div class="container">
	<h1> Hello world! </h1>
	<P>
		The time on the server is
		<%
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date();
			out.println(df.format(date));
		%>
	</P>

</div>

<div>
	<jsp:include page="bottom.jsp"/>
</div>

</body>
</html>
