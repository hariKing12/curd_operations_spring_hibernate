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
   <form:hidden path="id"/>
     <table>
      <tbody>
        <tr>
          <td><label>First Name</label></td>
          <td><form:input path="firstName"/></td>
        </tr>
        <tr>
          <td><label>Last Name</label></td>
          <td><form:input path="lastname"/></td>
        </tr>
        <tr>
          <td><label>Email</label></td>
          <td><form:input path="email"/></td>
        </tr>
        <tr>
          <td><label></label></td>
          <td><input type="submit" value="Save" class="save"/></td>
        </tr>
      </tbody>
     </table>
   </form:form>
   
   <div style="clear;both;">
   <p>
     <a href="${pageContext.request.contextPath}/customer/list">Back to List</a>
   </p>
   </div>

</body>
</html>