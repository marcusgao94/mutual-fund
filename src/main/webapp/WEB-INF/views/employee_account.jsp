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

	<h3>Employee View Account</h3>

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
               
				<input type="submit" name="button" value="Change Password"/>
				
 
             
              </div><!--table-responsive close-->
            </div><!--col-md-6 close-->
            
            
            <div class="col-md-6">
            <div class="table-responsive responsiv-table">
              <table class="table bio-table">
                  <tbody>
                 <tr>
                     <td>Address</td>
                     <td>${employee_account.addr_line1} ${employee_account.addr_line2}</td>
                 </tr>
                 <tr>
                     <td>State</td>
                     <td>${employee_account.state}</td>
                  </tr>
                  <tr>
                     <td>City</td>
                     <td>${employee_account.city}</td>
                  </tr>
                  <tr>
                     <td>ZIP</td>
                     <td>${employee_account.zip}</td>
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



          
          <h4>Date of the last trading day: ？？？ </h4>
          
          
		<input type="submit" name="button" value="transationday"/>
		<input type="submit" name="button" value="Create an customer account"/>
		<input type="submit" name="button" value="deposite fund"/>
		
				
      
		
             
        

<div>
	<c:import url="bottom.jsp"/>
</div>

</body>
</html>
