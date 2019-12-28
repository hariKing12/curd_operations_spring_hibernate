<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>List Of Customers</title>
  <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/Css/style.css">
</head>
<body>
  
    <div id="wrapper">
       <div id="header">
         <h2>CRM - Customer Relational Management</h2>
       </div>
    </div>
    
     <div id="container">
        <div id="content">
        
        <input class="add-button" type="button" value="Add Customer" 
              onclick="window.location.href='showFormForAdd'">
              
         <form:form action="search" method="GET">
         Search Customer : <input type="text" name="searchName">
         <input type="submit" value="Search" class="add-button">
         </form:form>
        
         <table>
           <tr>
             <th>First Name</th>
             <th>Last name</th>
             <th>Email</th>
             <th>Action</th>
           </tr>
           <c:forEach var="tempCustomers" items="${customers}">
           
           <!-- Update Link -->
            <c:url var="updateLink" value="/customer/showFormForUpdate">
             <c:param name="customerId" value="${tempCustomers.id}"></c:param>
             </c:url>
             
             <!--  Delete Link -->
             <c:url var="deleteLink" value="/customer/delete">
             <c:param name="customerId" value="${tempCustomers.id}"></c:param>
             </c:url>
             
            <tr>
              <td>${tempCustomers.firstName}</td>
              <td>${tempCustomers.lastname}</td>
              <td>${tempCustomers.email}</td>
              <td><a href="${updateLink}">Update</a>
              |
              <a href="${deleteLink}" onclick="if(!(confirm('Are you sure want to delete this Customer?'))) return false">Delete</a>
              </td>
            </tr>
           </c:forEach>
         </table>
        
        <form method="POST" action="uploadFile" enctype="multipart/form-data">
		File to upload: <input type="file" name="file"><br /> 
		Name: <input type="text" name="name"><br /> <br /> 
		<input type="submit" value="Upload"> Press here to upload the file!
	</form>	
        
        </div>
     </div>
   
</body>
</html>