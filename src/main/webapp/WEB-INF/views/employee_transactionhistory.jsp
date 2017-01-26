<%--
  Created by Eclipse IDEA.
  User: jev
  Date: 17/1/23
  Time: 9:32
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Employee transaction history</title>
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
		<table>
			<tr>
				<td> <form:errors path="" cssClass="error" /> </td>
			</tr>
			<tr>
				<td>Search User Name:</td>
				<td><input type="text" name="userName"
						   value="${searchForm.userName}" autofocus/>
				</td>
				<td><form:errors path="userName" cssClass="error"/></td>
			</tr>

			<tr>
				<th colspan="2">
					<input type="submit" name="button" value="search"/>
				</th>
			</tr>

		</table>
	</form:form>
</div>



<div class="container">

	<h3>Transaction History</h3>
	
	
<h4> Pending </h4>
<table>
	<thead>
		<tr>
			<th>ID</th>
			<th>Name</th>
			<th>Shares</th>
			<th>Amount</th>
          	<th>Operations</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="employee_pendingtransaction" items="${employee_pendingTransaction}">
			<tr>
				<td>
	 				${employee_pendingtransaction.id}
				</td>
				<td>
	 				${employee_pendingtransaction.fund.name}
				</td>
				<td>
	 				${employee_pendingtransaction.shares}
				</td>
				<td>
	 				${employee_pendingtransaction.amount}
				</td>
				<td>
	 				${employee_pendingtransaction.type}
				</td>
			</tr>
		</c:forEach>
	<tbody>
</table>

<h4> Finished </h4>
<table>
	<thead>
		<tr>
			<th>ID</th>
			<th>Name</th>
			<th>Shares</th>
			<th>Amount</th>
          	<th>Operations</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="employee_finishtransaction" items="${employee_finishTransaction}">
			<tr>
				<td>
	 				${employee_finishtransaction.id}
				</td>
				<td>
	 				${employee_finishtransaction.fund.name}
				</td>
				<td>
	 				${employee_finishtransaction.shares}
				</td>
				<td>
	 				${employee_finishtransaction.amount}
				</td>
				<td>
	 				${employee_finishtransaction.type}
				</td>
			</tr>
		</c:forEach>
	<tbody>
</table>


</div>
<div>
	<c:import url="bottom.jsp"/>
</div>

</body>
</html>
