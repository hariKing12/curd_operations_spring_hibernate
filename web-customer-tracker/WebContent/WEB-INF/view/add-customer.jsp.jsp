<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Save Customer</title>
  <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/Css/style.css">
  <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/Css/add-customer-style.css">

</head>
<body>
  <div id="wrapper">
       <div id="header">
         <h2>CRM - Customer Relational Management</h2>
       </div>
    </div>
    
    <h3>Save Customer</h3>
   <form:form action="saveCustomer" modelAttribute="customer" method="POST">
     <table>
      <tbody>
        <tr>
          <td><label>First Name</label></td>
          <td><form:input path="firstName"/></td>
        </tr>
      </tbody>
     </table>
   </form:form>

</body>
</html>