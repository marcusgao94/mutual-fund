<%--
  Created by Eclipse IDEA.
  User: JevWang
  Date: 17/1/23
  Time: 下午6:32
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Employee view account</title>
	<style>
		.error {
			color: red;
		}
	</style>
</head>
<body>
<div>
	<c:import url="/header"/>
</div>

<div class="container">
	<form:form method="post" modelAttribute="searchForm">
		<table class="table">
			<tr>
				<td>Search User Name:</td>
				<td>
					<input type="text" name="userName" value="${searchForm.userName}" autofocus/>
				</td>
				<td><form:errors path="userName" cssClass="error"/></td>
			</tr>
			<tr>
				<td>
					<input class="btn btn-default" type="submit" name="button" value="Search"/>
				</td>
			</tr>
		</table>
	</form:form>
</div>


<div>
	<c:import url="bottom.jsp"/>
</div>

</body>
</html>
