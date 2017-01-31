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
	<div class="row">

		<form:form method="post" action="/employee_searchcustomer" modelAttribute="searchForm">
			<table class="table">
				<tr>
					<td>Search User Name:</td>
					<td><input type="text" name="userName"
							   value="${searchForm.userName}" autofocus/>
					</td>
					<td><form:errors path="userName" cssClass="error"/></td>
				</tr>

				<tr>
					<th colspan="2">
						<input type="submit" class="btn btn-default" name="button" value="search"/>
					</th>
				</tr>

			</table>
		</form:form>
	</div>

	<div class="row">
		<div class="col-xs-12">
			<h3>Basic Info</h3>

			<div class="col-xs-6">
				<table class="table">
					<tbody>
					<tr>
						<td>ID</td>
						<td>${employee_customeraccount.id}</td>
					</tr>
					<tr>
						<td>FirstName</td>
						<td>${employee_customeraccount.firstName}</td>
					</tr>
					<tr>
						<td>LastName</td>
						<td>${employee_customeraccount.lastName}</td>
					</tr>
					<tr>
						<td>UserName</td>
						<td>${employee_customeraccount.userName}</td>
					</tr>
					<tr>
						<td>Address</td>
						<td>${employee_customeraccount.addr_line1} ${employee_customeraccount.addr_line2}</td>
					</tr>
					<tr>
						<td>State</td>
						<td>${employee_customeraccount.state}</td>
					</tr>
					<tr>
						<td>City</td>
						<td>${employee_customeraccount.city}</td>
					</tr>
					<tr>
						<td>ZIP</td>
						<td>${employee_customeraccount.zip}</td>
					</tr>
					</tbody>
				</table>
			</div>
<%-- <<<<<<< HEAD
            
            </div>
            </div>
            </div>
            </div>
            </div>

<div class="container">
    <div class="row-fluid">
      <div class="col-sm-12">
            <h3>Account Info</h3>
                    <h4>Date of the last trading day: xxxx </h4>
                    <h4>Cash: ${employee_customeraccount.cash} </h4>
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
					<c:forEach var="employee_cpv" items="${employee_customerpositionvalue}">
						<tr>
							<td>
									${employee_cpv.fund.name}
							</td>
							<td>
									${employee_cpv.shares}
							</td>
							<td>
									${employee_cpv.price}
							</td>
							<td>
									${employee_cpv.value}
							</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
       </div>
       </div>
       </div>
       </div>      
=======
 --%>		
 </div>
	</div>
	<div class="row">
		<div class="col-xs-6">
			<div class="text-right">
				<!--   <button type="button" class="btn btn-primary">Edit</button> -->
				<%--<input type="button" value="Change Password"--%>
				<%--onclick="window.location='customer_changepassword.jsp';">--%>
				<a href="<c:url value="/employee_changecuspassword?un=${employee_customeraccount.userName}" />">
					<button class="btn btn-default">
						Reset password
					</button>
				</a>
				
				<a href="<c:url value="/deposite_check?un=${employee_customeraccount.userName}" />">
					<button class="btn btn-default">
						Deposit Check
					</button>
				</a>
				
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-6">
			<h3>Account Info</h3>
			<h4>Date of the last trading day: xxxx </h4>
			<h4>Cash: ${employee_customeraccount.cash} </h4>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-6">
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
				<c:forEach var="employee_cpv" items="${employee_customerpositionvalue}">
					<tr>
						<td>
								${employee_cpv.fund.name}
						</td>
						<td>
								${employee_cpv.shares}
						</td>
						<td>
								${employee_cpv.price}
						</td>
						<td>
								${employee_cpv.value}
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>
<!-- >>>>>>> 97b05aa2f92f6cd044814a29becf337b52eed535 -->

<div>
	<c:import url="bottom.jsp"/>
</div>

</body>
</html>
