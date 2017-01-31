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
	<title>Customer view account</title>
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
<!-- <div class="container"> -->

	

	<div class="container">
	<h3>${customer_account.userName}'s Account</h3>
		<div class="row-fluid">
			<div class="col-sm-12">
				<h3>Basic Info</h3>

				<div class="col-md-6">
					<table class="table bio-table">
						<tr>
							<td>ID</td>
							<td>${customer_account.id}</td>
						</tr>
						<tr>
							<td>FirstName</td>
							<td>${customer_account.firstName}</td>
						</tr>
						<tr>
							<td>LastName</td>
							<td>${customer_account.lastName}</td>
						</tr>
						<tr>
							<td>UserName</td>
							<td>${customer_account.userName}</td>
						</tr>
					<!-- </table>
				</div>table-responsive close
			</div>col-md-6 close
			
			
			
			
			<div class="col-md-6">
				<table class="table bio-table"> -->
					<tr>
						<td>Address</td>
						<td>${customer_account.addr_line1} ${customer_account.addr_line2}</td>
					</tr>
					<tr>
						<td>State</td>
						<td>${customer_account.state}</td>
					</tr>
					<tr>
						<td>City</td>
						<td>${customer_account.city}</td>
					</tr>
					<tr>
						<td>ZIP</td>
						<td>${customer_account.zip}</td>
					</tr>
				</table>
				
				<div class="text-right">
					<a href="<c:url value="/customer_changepassword" />">
						<button class="btn btn-default">change password</button>
					</a>
					<a href="<c:url value="/customer_transactionhistory" />">
						<button class="btn btn-default">View Transaction History</button>
					</a>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="container">
	<div class="row-fluid">
		<div class="col-sm-12">
			<h3>Account Info</h3>
			<h4> Date of the last trading day:</h4>
			<h4> Cash: ${customer_account.cash}</h4>
					<a href="<c:url value="request_check" />">
						<button class="btn btn-default">Request Check</button>
					</a>
			<div class="col-md-6">

				<table class="table">
					<thead>
					<tr>
						<th>Name of fund</th>
						<th>Shares</th>
						<th>Price</th>
						<th>Value</th>
					</tr>
					</thead>
					<tbody>
					<c:forEach var="customer_pv" items="${customerPosition}">
						<tr>
							<td>
									${customer_pv.fund.name}
							</td>
							<td>
									${customer_pv.shares}
							</td>
							<td>
									${customer_pv.price}
							</td>
							<td>
									${customer_pv.value}
							</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>

<div>
	<c:import url="bottom.jsp"/>
</div>

</body>
</html>
