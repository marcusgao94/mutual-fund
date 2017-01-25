<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Singularity MutualFund</title>
</head>
<body>
<div>
	<c:import url="/header" />
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


	<%--<a href="<c:url value="/request_check" />"><button>request check</button></a>--%>
	<%--<a href="<c:url value="/deposit_check" />"><button>deposit check</button></a>--%>
	<a href="<c:url value="/create_fund" />"><button>create fund</button></a>

</div>


<div>
	<c:import url="bottom.jsp" />
</div>

</body>
</html>
