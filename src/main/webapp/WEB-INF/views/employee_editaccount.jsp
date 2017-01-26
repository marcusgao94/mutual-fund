<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>

<!DOCTYPE html>
<html>
<head>
	<title>employee view account</title>
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

	<h3>Save Account</h3>


          <h4>Date of the last trading day: ${lastTransitonDay} </h4>
          
          
		<a href="<c:url value="/transitionday"/>"><input type="submit" name="button" value="Set Transation Day"/></a>
		<a href="<c:url value="/customer_register"/>"><input type="submit" name="button" value="Create an customer account"/></a>
		<a href="<c:url value="/deposite_check"/>"><input type="submit" name="button" value="Deposite Check"/></a>

<div class="container">
    <div class="row-fluid">
      <div class="col-sm-12">
        <h2>${employee_account.userName}</h2>
            <h3>Basic Info</h3>

            <div class="col-md-6">

              <table>
                 
                    <tr>
                       <td>ID</td>
                       <td>${employee_account.id}</td>
                    </tr>
                 <tr>
                    <td>FirstName</td>
                    <td>${employee_account.firstName}</td>
                 </tr>
                 <tr>
                    <td>LastName</td>
                   <td>${employee_account.lastName}</td>
                 </tr>
                 <tr>
                    <td>UserName</td>
                    <td>${employee_account.userName}</td>
                </tr>
                
             	</table>
               
				<a href="<c:url value="/employee_changepassword"/>"> <input type="submit" name="button" value="Change Password"/> </a>

              </div>
            </div>
            
            
            <div class="col-md-6">
            <div class="table-responsive responsiv-table">
              <table class="table bio-table">
                  <tbody>
                 <tr>
                     <td>Address</td>
                      <td><input type = "text" name = "address" value= ""/></td>
                 </tr>
                 <tr>
                     <td>State</td>
                     <td><input type = "text" name = "state" value= ""/></td>
                  </tr>
                  <tr>
                     <td>City</td>
                      <td><input type = "text" name = "city" value= ""/></td>
                  </tr>
                  <tr>
                     <td>ZIP</td>
                      <td><input type = "text" name = "zip" value= ""/></td>
                  </tr>
                </tbody>
              </table>
              <div class="text-right">
  <button type="button" class="btn btn-primary">Save</button>
</div>
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
