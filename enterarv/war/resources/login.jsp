<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="../variable.jsp"%>

<!doctype html>
<html class="no-js" lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no" />
<title><%=title %></title>
<link rel="stylesheet" href="<%=resPath %>stylesheets/app.css" />
<script src="<%=resPath %>bower_components/modernizr/modernizr.js"></script>
<style>
body {
	background: #f9f9f9;
}
</style>
</head>

<body>
<div class="row">
	<div class="large-4 medium-6 small-10 small-centered columns" id="login-form-wrapper">
		<img src="<%=resPath %>images/logo-bbva-tipo.svg" alt="" class="logo">
		<form id="login-form" action="<%=basePath %>user/home.html" method="post" >
			<div class="small-12" style="margin-bottom: 10px;">
				<h4 style="color: red; text-align: center;"><%=message %></h4>
			</div>
			<div class="small-12">
				<label><i class="fa fa-user fa-1x"></i> <%=registry.getStringOfLanguage("login.musuario", language) %></label> 
				<input type="text" id="username" name="username" placeholder="<%=registry.getStringOfLanguage("login.musuario", language) %>" tabindex="1" value="${username }" />
			</div>
			
<%-- 			<div class="small-12">
				<label><i class="fa fa-lock fa-1x"></i> <%=registry.getStringOfLanguage("login.password", language) %></label> 
				<input type="password" id="password" name="password" placeholder="********" tabindex="2"  value="${password }" />
			</div>
 --%>
			<div class="small-12 actions">
				<a href="javascript:home()" class="button tiny" tabindex="3"><%=registry.getStringOfLanguage("btn.login", language) %> <i class="fa fa-angle-right fa-1x"></i></a> 
<%--  				<br>
				<input id="remember" name="remember" type="checkbox" checked="checked">
				<label for="remember"><%=registry.getStringOfLanguage("login.remember", language) %></label> --%> 
<%-- 				<br>
				<a href="#" data-reveal-id="lostPasswordModal" class="login-tools"><%=registry.getStringOfLanguage("login.lost", language) %></a> --%> 
			</div>
			
<%-- 			<div class="small-12 actions" style="margin-top: 10px;">
				<a href="javascript:forOutside()"><%=registry.getStringOfLanguage("login.visit", language) %></a>
			</div> --%>
		</form>
	</div>
</div>
	
<div id="lostPasswordModal" class="reveal-modal tiny" data-reveal>
	<div class="small-12">
		<label><i class="fa fa-user fa-1x"></i> <%=registry.getStringOfLanguage("login.username", language) %></label> 
		<input type="text" id="user" placeholder="<%=registry.getStringOfLanguage("login.username", language) %>" value="${username }" />
	</div>
	<div class="small-12 right">
		<a href="javascript:send()" class="button tiny right"><%=registry.getStringOfLanguage("btn.send_email", language) %> <i class="fa fa-angle-right fa-1x"></i></a> 
	</div>
	<a class="close-reveal-modal">&#215;</a>
</div>

<form id="frm_visit_site" action="<%=basePath %>user/outside.html" method="post">
</form>
	
	<input type="hidden" id="username_empty_msg" value="<%=registry.getStringOfLanguage("placeholder.musuario", language) %>" />
	
	<script src="<%=resPath %>bower_components/jquery/dist/jquery.min.js"></script>
	<script src="<%=resPath %>bower_components/velocity/velocity.min.js"></script>
	<script src="<%=resPath %>bower_components/foundation/js/foundation.min.js"></script>
	<script src="<%=resPath %>js/login.js"></script>
</body>

</html>