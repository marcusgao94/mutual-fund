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
    <div class="row">
    <br>
        <div class="col-xs-9 col-xs-offset-1">
        <div class="input-group">
                <div class="input-group-btn search-panel">
                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                      <span id="search_concept">Filter by</span> <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu" role="menu">
                      <li><a href="#ID">ID</a></li>
                      <li class="divider"></li>
                      <li><a href="#FIRST NAME">First Name</a></li>
                      <li class="divider"></li>
                      <li><a href="#LAST NAME">Last Name</a></li>
                    </ul>
                </div>
                <input type="hidden" name="search_param" value="all" id="search_param">
                <input type="text" class="form-control" name="x" placeholder="Search term...">
                <span class="input-group-btn">
                    <button class="btn btn-default" type="button"><span class="glyphicon glyphicon-search"></span></button>
                </span>
            </div>
        </div>
  </div>
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
