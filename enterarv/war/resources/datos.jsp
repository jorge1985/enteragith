<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@include file="variable.jsp" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%

 UserService userService = UserServiceFactory.getUserService();
 User user = userService.getCurrentUser();
 String correo = user.getEmail();
 String dominio = user.getAuthDomain();
 System.out.println("CURRENT USER::::"+user.getEmail());
 String logoutURL= userService.createLogoutURL(request.getRequestURI());
// String basePath = "localhost:8888";
%>
<script src="/js/jquery.js"></script>
  <script>
  
  $(document).ready(function(){
	  
			 $.ajax({
			      url: 'https://bbva-gnameindexer-sp.appspot.com/a/<%= user.getAuthDomain()%>/s/search/jsonP?searchText=<%= user.getEmail()%>',
  			      dataType: "jsonp",
  			      contentType: 'application/json',
  			      success: function (data) {
  			          var rows = JSON.stringify(data);
  			          for (var i in data.results) {
	  			          var Identificador=data.results[i].id;
	  			          var Nombre=data.results[i].name;
	  			          var IdEmpleado=data.results[i].employeeId;
	  			          var CCorreo=data.results[i].id;
	  			          Actividad="usuario";
// 	  			          alert("Nombre: "+Nombre);
  			      	  }
  			          $.ajax({
	  			        	type: "GET",
	  			        	url:'/datos',
	  			        	data: {'Nombre' : Nombre, 'Id' : IdEmpleado, 'Correo' : CCorreo}  
  			      	  })
  			      	  .done(function (data) {

			          });
  			  } 
		});
  });
  	</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- Original -->
<%-- <meta http-equiv="refresh" content="0; url=<%=basePath %>user/login.html" /> --%>
<!-- Agregado por Towa -->
<!-- <meta http-equiv="refresh" content="0; url=/logint"/> -->
<title><%=title %></title>
</head>
<body>



<script language="Javascript">
   	 setTimeout("location.href='/user/home.html'", 1000);
 </script>

</body>
</html>