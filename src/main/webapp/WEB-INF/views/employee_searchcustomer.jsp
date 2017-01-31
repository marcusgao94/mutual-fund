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
<<<<<<< HEAD
				                <span class="input-group-btn">
                    <button class="btn btn-default" type="submit"><span class="glyphicon glyphicon-search"></span></button>
                </span>
=======
					<input class="btn btn-default" type="submit" name="button" value="Search"/>
>>>>>>> b4c9afd93784d6cecbbca5a1868b98911e787076
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


<div>
	<c:import url="bottom.jsp"/>
</div>

</body>
</html>
