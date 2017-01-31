
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

	<h3>Research Fund</h3>
	
	
	

	<div class="container">
		<div class="row-fluid">
			<div class="col-sm-12">
				
			<form:form method="post" modelAttribute="researchFundForm">
			    
			    <%-- --%>
			    <div class="form-group">
			      <label for="sel1">Select one fund:</label>
			      
			      
			      <select name="ticker" class="form-control" id="sel1">
			      <option>Please select a fund and submit.</option>
			      	<c:forEach var="funds" items="${funds}">
				        <option>${funds.ticker}</option>				        
				     </c:forEach>
			      </select>
			    </div>
			    
			    <input type="submit" value="Submit">
			  </form:form>
				
          <div class="panel panel-default">
          <div class="panel-heading">
            <span itemscope itemtype="http://schema.org/Review">
            <h3 class="panel-title" itemprop="name">Fund Info</h3>
          </div><!--/panel-heading-->
          <div class="panel-body" itemprop="reviewBody">

				<div class="col-md-6">
		<div class="table-responsive responsiv-table">
		              <table class="table bio-table">
		                  <tbody>
						<tr>
							<td>FundTicker</td>
							<td>${a_fund.ticker}</td>
						</tr>
						<tr>
							<td>FundName</td>
							<td>${a_fund.name}</td>
						</tr>
						</tbody>
		
						
					</table>
				</div><!--table-responsive close-->
			</div><!--col-md-6 close-->
			<%--
			<div class="col-md-6">
            <div class="table-responsive responsiv-table">

				<table class="table bio-table">
				<tbody>
					<tr>
						<td>Average Price</td>
						<td>${fund.avgPrice} </td>
					</tr>
					<tr>
						<td>Total Shares Sold</td>
						<td>${fund.shares}</td>
					</tr>
				</tbody>
				</table>

			   </div>
			   </div> 
		       --%>
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
		        // Define the chart to be drawn. ['2017-01-01',  3]
		        var data = new google.visualization.arrayToDataTable([
		            ['Date', 'fundName'],
		            
		            <c:forEach var="fundPriceHistory" items="${fundPriceHistory}">
		            	['2017-01-01',  3],
		            	//var x = ;
		            	/*
		            	var x = <c:out value="${fundPriceHistory.price}" />;
		            	console.log("x = " + x);
		            	alert(x);
		            	[<c:out value="${fundPriceHistory.fundDate.date}" />, x],	        
			     		*/
		            </c:forEach>
		           
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
