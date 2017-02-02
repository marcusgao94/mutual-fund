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
		<div class="col-sm-8">
			<div class="input-group">
				<div class="input-group-btn search-panel">
					<%-- <form:form method="post" modelAttribute="searchForm"> --%>
					<!-- <table class="table">
						<tr>
							<td>Search by User Name:</td> -->
					<%-- <td>
						<input type="text" name="userName" value="${searchForm.userName}" autofocus/>
					</td>
					<td><form:errors path="userName" cssClass="error"/></td> --%>
					<form:form method="post" modelAttribute="searchForm">
						<%-- --%>
						<div class="form-group">
							<div class="col-sm-7">
								<label for="sel1">Select User Name:</label>
								<select name="userName" class="form-control" id="sel1">
									<option value="">Please select a name and then press the button</option>
									<c:forEach var="customer" items="${customerList}">
										<option>${customer.userName}</option>
									</c:forEach>
								</select>
								<form:errors path="userName" cssClass="error"/>
							</div>
							<!--  <input type="submit" value="Submit"> -->
							<div class="col-sm-4">
			      				<span class="input-group-btn">
                    			<button class="btn btn-default" type="submit"><span
										class="glyphicon glyphicon-search"></span></button>
                				</span>
							</div>
						</div>
					</form:form>


					<!-- <td>
									<span class="input-group-btn">
						<button class="btn btn-default" type="submit"><span class="glyphicon glyphicon-search"></span></button>
					</span>
					</td> -->
					<%-- 	</tr>
						<tr>
						</tr>
					</table>
				</form:form> --%>

				</div>
			</div>
		</div>
	</div>
</div>


<div>
	<c:import url="bottom.jsp"/>
</div>

</body>
</html>
