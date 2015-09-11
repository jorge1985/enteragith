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
			<form id="login-form" action="<%=basePath %>user/savePassword.html" method="post" >
				<div class="small-12">
					<label><i class="fa fa-lock fa-1x"></i> <%=registry.getStringOfLanguage("login.password", language) %></label> 
					<input type="password" id="password" name="password" placeholder="********" tabindex="1"  value="" />
				</div>
				<div class="small-12">
					<label><i class="fa fa-lock fa-1x"></i> <%=registry.getStringOfLanguage("login.confirm_password", language) %></label> 
					<input type="password" id="password2" name="password2" placeholder="********" tabindex="2"  value="" />
				</div>

				<div class="small-12 actions">
					<a href="javascript:save()" class="button tiny" tabindex="3"><%=registry.getStringOfLanguage("btn.submit", language) %> <i class="fa fa-angle-right fa-1x"></i></a>
					<input type="hidden" id="username" name="username" value="${user_id }" />
				</div>
			</form>
		</div>
	</div>
	
	<input type="hidden" id="password_empty_msg" value="<%=registry.getStringOfLanguage("placeholder.password", language) %>" />
	<input type="hidden" id="password_same_msg" value="<%=registry.getStringOfLanguage("validate.same_password", language) %>" />
	
	<script src="<%=resPath %>bower_components/jquery/dist/jquery.min.js"></script>
	<script src="<%=resPath %>bower_components/velocity/velocity.min.js"></script>
	<script src="<%=resPath %>bower_components/foundation/js/foundation.min.js"></script>
	<script src="<%=resPath %>js/reset.js"></script>
</body>

</html>