<%-- 
    Document   : conf
    Created on : Sep 16, 2024, 7:52:06 PM
    Author     : Nekha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Confirmation Page</title>
    </head>
    <body>
        <h1>Confirmation Page</h1>
        <ul>
            <li>${requestScope.customer.customerId}</li>
            <li>${requestScope.customer.firstName}</li>
            <li>${requestScope.customer.lastName}</li>
            <li>${requestScope.customer.email}</li>
            <li>${requestScope.customer.storeId}</li>
            <li>${requestScope.customer.addressId}</li>
            <li>${requestScope.customer.createDate}</li>
            <li>${requestScope.customer.active}</li>
            
        </ul>
    </body>
</html>
