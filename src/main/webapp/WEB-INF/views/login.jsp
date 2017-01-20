<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Customer Login</title>
    </head>

    <body>

        <h5>Customer Login</h5>
        <table>
        <tr>
            <td>User Name:</td>
            <td>
                <input type = "text" name = "username" value = "" autofocus/>
            </td>
        </tr>

        <tr>
            <td>Password:</td>
            <td>
                <input type = "password" name ="employee-password" value = ""/>
            </td>
        </tr>

        <tr>
            <td>
                <label><input type="checkbox"> Remember me</label>
            </td>

        </tr>

        <tr>
            <th colspan = "2">
                <input type = "submit" name = "button" value = "login"/>
            </th>
        </tr>

    </table>
    </body>

</html>
