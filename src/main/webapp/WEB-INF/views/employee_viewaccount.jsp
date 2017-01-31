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
	    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">

    <link href="../css/searchresults.css" rel="stylesheet" type="text/css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
	
</head>


<body>
<div>
	<c:import url="/header"/>
</div>

<div class="container">
    <div class="row">
    <br>
        <div class="col-xs-5">
        <div class="input-group">
                <div class="input-group-btn search-panel">
<form:form method="post" modelAttribute="searchForm">
		<table class="table">
			<tr>
				<td>Search User Name:</td>
				<td>
					<input type="text" name="userName" value="${searchForm.userName}" autofocus/>
				</td>
				<td><form:errors path="userName" cssClass="error"/></td>
				<td>
				                <span class="input-group-btn">
                    <button class="btn btn-default" type="submit"><span class="glyphicon glyphicon-search"></span></button>
                </span>

				</td>
			</tr>
			<tr>
			</tr>
		</table>
	</form:form>

        </div>
  </div>
</div>
</div>
</div>

<div class="container">
    <div class="row-fluid">
      <div class="col-sm-12">
        <h2>${employee_customeraccount.firstName} ${employee_customeraccount.lastName}</h2>
          <div class="panel panel-default">
          <div class="panel-heading">
            <span itemscope itemtype="http://schema.org/Review">
            <h3 class="panel-title" itemprop="name">Basic Info</h3>
          </div><!--/panel-heading-->
          <div class="panel-body" itemprop="reviewBody">

            <div class="col-md-6">

            <div class="table-responsive responsiv-table">
              <table class="table bio-table">
                  <tbody>
                    <tr>
                       <td>ID</td>
                       <td> ${employee_customeraccount.id}</td>
                    </tr>
                 <tr>
                    <td>FirstName</td>
                    <td> ${employee_customeraccount.firstName}</td>
                 </tr>
                 <tr>
                    <td>LastName</td>
                    <td> ${employee_customeraccount.lastName}</td>
                 </tr>
                 <tr>
                    <td>UserName</td>
                    <td> ${employee_customeraccount.userName}</td>
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
                     <td> ${employee_customeraccount.addr_line1} ${employee_customeraccount.addr_line2}</td>
                 </tr>
                 <tr>
                     <td>State</td>
                     <td> ${employee_customeraccount.state}</td>
                  </tr>
                  <tr>
                     <td>City</td>
                     <td> ${employee_customeraccount.city}</td>
                  </tr>
                  <tr>
                     <td>ZIP</td>
                     <td> ${employee_customeraccount.zip}</td>
                  </tr>
                </tbody>
              </table>
              <div class="text-right">
<!--   <button type="button" class="btn btn-primary">Edit</button> -->
				<a href="<c:url value="/employee_changecuspassword?un=${employee_customeraccount.userName}" />">
					<button class="btn btn-primary">
						Reset password
					</button>
				</a>
								<a href="<c:url value="/deposit_check?un=${employee_customeraccount.userName}" />">
					<button class="btn btn-primary">
						Deposit Check
					</button>
				</a>
				<a href="<c:url value="/employee_searchtransaction?un=${employee_customeraccount.userName}" />">
					<button class="btn btn-primary">
						View Transaction History
					</button>
				</a>
              </div>
            </div>
            </div>
            </div>
            </div>
            </div>
            </div>

                          <div class="col-sm-12">
                  <div class="panel panel-default">
                  <div class="panel-heading">
                    <span itemscope itemtype="http://schema.org/Review">
                    <h3 class="panel-title" itemprop="name">Account Info</h3>
                  </div><!--/panel-heading-->
                  <div class="panel-body" itemprop="reviewBody">
                    <h4>Date of the last trading day: ${date} </h4>
                    <h4>Cash Balance: ${employee_customeraccount.cash} </h4>
                    <div class="col-md-6">

                    <div class="table-responsive responsiv-table">
                      <table class="table bio-table">
                          <thead>
                            <tr>
                               <td>Name of the fund</td>
                               <td>Shares</td>
                               <td>Price</td>
                               <td>Value</td>
                            </tr>
                         </thead>
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
                      </div><!--table-responsive close-->
                    </div><!--col-md-6 close-->
<!-- pie chart -->
<div class="col-md-4">
  <head>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
      google.charts.load('current', {'packages':['corechart']});
      google.charts.setOnLoadCallback(drawChart);
      function drawChart() {
        // Define the chart to be drawn.
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Element');
        data.addColumn('number', 'Percentage');
        data.addRows([
          ['Fund1', 0.1],
          ['Fund2', 0.2],
          ['Fund3', 0.3],
          ['Fund4', 0.1],
          ['Fund5', 0.3]
        ]);
        var options = {
            title: 'My Fund Collection'
          };
        // Instantiate and draw the chart.
   var chart = new google.visualization.PieChart(document.getElementById('pieChart'));
   chart.draw(data, options);
  }

    </script>
  </head>
  <body>
    <div id="pieChart" style="width: 530px; height: 300px;"></div>
  </body>

  </div>
                    </div>
                    </div>
                    </div>

<%-- <div class="container">

			<h3>Account Info</h3>
			<h4>Date of the last trading day: ${date} </h4>
			<h4>Cash: ${employee_customeraccount.cash} </h4>

	<div class="row">
		<div class="col-md-12">
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
	</div> --%>
</div>
<div>
	<c:import url="bottom.jsp"/>
</div>

</body>
</html>