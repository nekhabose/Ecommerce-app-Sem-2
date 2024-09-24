<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%-- 
    Document   : customer
    Created on : Sep 16, 2024, 2:18:58 PM
    Author     : Nekha
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create a New Customer</title>
    </head>
    <body>
        <h1>New Customer Form</h1>
        
       
         <c:if test="${not empty requestScope.violations}">
            <h2>Please fix the following errors with your input:</h2>
            <table border=1>
                <tr>
                    <th>Field</th>
                    <th>Error</th>
                </tr>
                <c:forEach var="violation" items="${requestScope.violations}">
                    <tr>
                        <td><c:out value="${violation.propertyPath}" /></td>
                        <td><c:out value="${violation.message}" /></td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
            <br/><!-- comment -->
        <form method="post" action="/nbose1-fp/customer">
            <div>
            <label for="custId"> Customer ID Number </label>
            <input type="text"  id="custId" name="custId" value="${requestScope.customer.customerId}"/>
            </div>
            
            <div>
            <label for="firstName"> Customer First Name  </label>
            <input type="text"  id="firstName" name="firstName" value="${requestScope.customer.firstName}"/>
            </div>
            
            <div>
            <label for="lastName"> Customer last Name </label>
            <input type="text"  id="lastName" name="lastName" value="${requestScope.customer.lastName}"/>
            </div>
            
            <div>
            <label for="email"> Customer Email Address  </label>
            <input type="email"  id="email" name="email" value="${requestScope.customer.email}"/>
            </div>
            
            <div>
            <label for="addressId"> Customer Address ID  </label>
            <input type="text"  id="addressId" name="addressId" value="${requestScope.customer.addressId}"/>
            </div>
            
            <div>
            <label for="activeInd">Active?</label>
            <input type="checkbox"  id="activeInd" name="activeInd" ${requestScope.customer.active ? 'checked' : ''}/>
            </div>
            
            <div>
            <label for="storeId"> Customer Store ID  </label>
            <select id="storeId" name="storeId">
                <option value="1" ${requestScope.customer.storeId == 1 ? 'selected' : ''}>Store #1</option>
                <option value="2" ${requestScope.customer.storeId eq 2 ? 'selected' : ''}>Store #2</option>
            </select>
            </div>
            
            <div>
            <label for="createDate"> Customer Created Date  </label>
            <input type="date"  id="createDate" name="createDate" value="${requestScope.customer.createDate}"/>
            </div>
            
            <button type="submit"> Create Customer</button>
            
        </form>
    </body>
</html>
