<%-- 
    Document   : login
    Created on : 18-Feb-2016, 10:05:47
    Author     : ingim
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h3>Login</h3>
        <form action="j_security_check">
            <input type="text" name="j_username" placeholder="Username">
            <input type="password" name="j_password" placeholder="Password">
            <input type="submit" value="Login">
        </form>
    </body>
</html>
