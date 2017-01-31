<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<body>
<header role="banner" id="top" class="navbar navbar-static-top">
	<nav role="navigation" class="navbar navbar-default">
		<div class="container">

			<div class="navbar-header">
				<button data-target=".navbar-collapse" data-toggle="collapse"
						class="navbar-toggle" type="button">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<div class="animbrand"><a href="<c:url value="/home" />" class="navbar-brand"><span style = "font-weight:bold">Singularity</span></a>
				</div>
			</div>

			<div class="collapse navbar-collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li class="">
						<a href="<c:url value="/home" />">
							<span class="glyphicon glyphicon-home"></span> Home
						</a>
					</li>

					<li class="dropdown">
						<a data-toggle="dropdown" class="dropdown-toggle"
						   href="#">
							<span class="glyphicon glyphicon glyphicon-usd"></span>
							Investing & Trading <b class="caret"></b>
						</a>
						<ul class="dropdown-menu">
							<li class="">
								<a href="<c:url value="/buy_fund" />">
									<span class="glyphicon glyphicon-usd"></span>
									Buy Funds
								</a>
							</li>
							<li class="">
								<a href="<c:url value="/sell_fund" />">
									<span class="glyphicon glyphicon-usd"></span>
									Sell Funds
								</a>
							</li>
						</ul>
					</li>


					<li class="dropdown">
						<a data-toggle="dropdown" class="dropdown-toggle"
						   href="#">
							<span class="glyphicon glyphicon-search"></span>
							Research Funds <b class="caret"></b>
						</a>
						<ul class="dropdown-menu">
							<li class="">
								<a href="<c:url value="/customer_researchfund"/>">
									<span class="glyphicon glyphicon-search"></span>
									View Fund Analysis
								</a>
							</li>
						</ul>
					</li>
				</ul>

				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown">
						<a data-toggle="dropdown" class="dropdown-toggle">
							<small>Welcome,</small>
							${user.userName} <b class="caret"></b>
						</a>
						<ul class="dropdown-menu">
							<c:if test="${user.type == -1}">
								<li>
									<a href="<c:url value="/employee_login" />">
										<i class="glyphicon glyphicon-user"></i>
										Login as employee
									</a>
								</li>
								<li>
									<a href="<c:url value="/customer_login" />">
										<i class="glyphicon glyphicon-user"></i>
										Login as customer
									</a>
								</li>
							</c:if>
							<c:if test="${user.type == 0}">
								<li>
									<a href="<c:url value="/customer_viewaccount" />">
										<i class="glyphicon glyphicon-user"></i>
										View My Account
									</a>
								</li>
							</c:if>
							<c:if test="${user.type == 1}">
								<li>
									<a href="<c:url value="/employee_register" />">
										<span class="glyphicon glyphicon-user"></span>
										Create an Employee
									</a>
								</li>
								<li>
									<a href="<c:url value="/customer_register" />">
										<span class="glyphicon glyphicon-user"></span>
										Create A Customer
									</a>
								</li>
								<li>
									<a href="<c:url value="/employee_searchcustomer" />">
										<i class="glyphicon glyphicon-user"></i>
										View Customer Account
									</a>
								</li>
								<li>
									<a href="<c:url value="/employee_changecuspassword" />">
										<i class="glyphicon glyphicon-user"></i>
										Reset Customer Password
									</a>
								</li>
								<li>
									<a href="<c:url value="/employee_createfund" />">
										<i class="glyphicon glyphicon-user"></i>
										Create Fund
									</a>
								</li>
								<li>
									<a href="<c:url value="/deposit_check" />">
										<i class="glyphicon glyphicon-user"></i>
										Deposit Check
									</a>
								</li>
								
								<li>
									<a href="<c:url value="/transitionday" />">
										<i class="glyphicon glyphicon-user"></i>
										Transition Day
									</a>
								</li>
								
							</c:if>
							<c:if test="${user.type != -1}">
								<li>
									<a href="<c:url value="/logout" />">
										<i class="glyphicon glyphicon-off"></i>
										Logout
									</a>
								</li>
							</c:if>
						</ul>
					</li>
				</ul>
			</div>
		</div>
	</nav>
</header>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	  href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet"
	  href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">

<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>
