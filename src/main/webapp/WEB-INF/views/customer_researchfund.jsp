
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Customer research fund</title>
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
				<h2>${customer_account.userName}</h2>
				
			<form:form method="post" modelAttribute="ResearchFundForm">
			    <div class="form-group">
			      <label for="sel1">Select one fund:</label>
			      <select class="form-control" id="sel1">
			        <option>fundA</option>
			        <option>fundB</option>
			        <option>fundC</option>
			        <option>fundD</option>
			      </select>
			    </div>
			  </form:form>
				
				
				<h3>Fund Info</h3>

				<div class="col-md-6">

					<table>
						<tbody>
						<tr>
							<td>FundTicker</td>
							<td>${fund.ticker}</td>
						</tr>
						<tr>
							<td>FundName</td>
							<td>${fund.name}</td>
						</tr>
						</tbody>
					</table>
				</div><!--table-responsive close-->
			</div><!--col-md-6 close-->
			<div class="col-md-6">

				<table class="table bio-table">
					<tr>
						<td>Average Price</td>
						<td>${fund.avgPrice} </td>
					</tr>
					<tr>
						<td>Total Shares Sold</td>
						<td>${fund.shares}</td>
					</tr>

				</table>

			</div>
		</div>
	</div>
</div>

<div class="container">
	<div class="row-fluid">
		<div class="col-sm-12">
		



		<div class="col-md-4">
		  <head>
		    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
		    <script type="text/javascript">
		      google.charts.load('current', {'packages':['corechart']});
		      google.charts.setOnLoadCallback(drawChart);
		      function drawChart() {
		        // Define the chart to be drawn.
		        var data = new google.visualization.arrayToDataTable([
		            ['Date', 'fundName'],
		            ['2017-01-01',  3],
		            ['2017-01-02',  6],
		            ['2017-01-03',  9],
		            ['2017-01-04',  2]
		          ]);
		
		        var options = {
		            title: 'Fund Price History',
		            curveType: 'function',
		            legend: { position: 'bottom' }
		          };
		        // Instantiate and draw the chart.
		   var chart = new google.visualization.LineChart(document.getElementById('curve_chart'));
		   chart.draw(data, options);
		  }
		
		    </script>
		  </head>
		</div>	
		
		  <body>
    			<div id="curve_chart" style="width: 1030px; height: 600px;"></div>
  		</body>
		

		</div>
	</div>
</div>

<div>
	<c:import url="bottom.jsp"/>
</div>

</body>
</html>
