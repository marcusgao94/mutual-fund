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
<div class="container">

	<h3>Customer View Account</h3>

<div class="container">
    <div class="row-fluid">
      <div class="col-sm-12">
        <h2>${customer_account.username}</h2>
            <h3>Basic Info</h3>

            <div class="col-md-6">

              <table>
                  <tbody>
                    <tr>
                       <td>ID</td>
                       <td>${customer_account.id}</td>
                    </tr>
                 <tr>
                    <td>FirstName</td>
                    <td>${customer_account.firstname}</td>
                 </tr>
                 <tr>
                    <td>LastName</td>
                    <td>${customer_account.lastname}</td>
                 </tr>
                 <tr>
                    <td>UserName</td>
                    <td>customer_account.username0</td>
                </tr>
                </tbody>
              </table>
              </div><!--table-responsive close-->
            </div><!--col-md-6 close-->
            <div class="col-md-6">

            <div class="table-responsive responsiv-table">
              <table class="table bio-table">
                  <tbody>
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
                </tbody>
              </table>
              <div class="text-right">
  <button type="button" class="btn btn-primary">Edit</button>
</div>
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
                    <h4>${customer_account.cash} </h4>
            <div class="col-md-6">

              <table>
              	<thead>
		<tr>
			<th>Name of fund</th>
			<th>Shares</th>
			<th>value</th>
		</tr>
	</thead>
	<tbody>
                 <c:forEach var="customer_position" items="${customer_Position}">
			<tr>
				<td>
	 				${customer_position.fund.name}
				</td>
				<td>
	 				${customer_position.share}
				</td>
				<td>
	 				price?
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
