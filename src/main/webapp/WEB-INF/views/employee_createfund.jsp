<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Create Fund</title>
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

	<h3>Create Fund</h3>

	<div class="container">
		<div class="row-fluid">

			<div class="col-md-6">

				<form:form method="post" modelAttribute="createFundForm">
					<table>
						<tbody>
						<tr>
							<td>Fund Ticker:</td>
							<td><input type="text" name="fundTicker" placeholder=""
									   value="${createFundForm.fundTicker}"/></td>
							<td><form:errors path="fundTicker" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Fund Name:</td>
							<td><input type="text" name="fundName"
									   value="${createFundForm.fundName}"/>
							</td>
							<td><form:errors path="fundName" cssClass="error" /></td>
						</tr>
						
						<tr>
							<td>

							<br/>
							</td>
						</tr>
						
						<tr>
							<td>
								<input type="submit" class="btn btn-primary" value="submit" />

							</td>

							<td>
								<a href="<c:url value="/home" />">
									<button class="btn btn-default">Cancel</button>
								</a>
							</td>
				</tr>
						</tbody>
					</table>
				</form:form>
			</div>
		</div>
	</div>
	</div>
	
	<div>
	<c:import url="bottom.jsp"/>
	</div>
	
</body>
</html>
