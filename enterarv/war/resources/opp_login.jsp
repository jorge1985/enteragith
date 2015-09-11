<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String basepath = request.getContextPath();
%>
<%@include file="../variable.jsp"%>
<%@include file="jsp/opp_header.jsp"%>

<%@include file="jsp/opp_topbar.jsp"%>

<div class="row" id="content-login">
	<div class="large-12 columns">
		<h1><%=registry.getStringOfLanguage("opport.title.login", language) %></h1>
		
		<div class="row" style="background-color: #f9f9f9;">
			<div class="large-4 medium-6 small-10 small-centered columns" id="login-form-wrapper">
				<form id="login-form" action="<%=basePath %>opportunities/check.html" method="post" >
					<div class="small-12" style="margin-bottom: 10px;">
						<h4 style="color: red; text-align: center;"><%=message %></h4>
					</div>
					<div class="small-12">
						<label><i class="fa fa-user fa-1x"></i> <%=registry.getStringOfLanguage("login.username", language) %></label> 
						<input type="text" id="username" name="username" placeholder="<%=registry.getStringOfLanguage("login.username", language) %>" tabindex="1" value="${username }" />
					</div>
					<div class="small-12">
						<label><i class="fa fa-lock fa-1x"></i> <%=registry.getStringOfLanguage("login.password", language) %></label> 
						<input type="password" id="password" name="password" placeholder="********" tabindex="2"  value="${password }" />
					</div>
		
					<div class="small-12 actions">
						<a href="javascript:home()" class="button tiny" tabindex="3"><%=registry.getStringOfLanguage("btn.login", language) %> <i class="fa fa-angle-right fa-1x"></i></a> 
						<br>
						<input id="remember" name="remember" type="checkbox" checked="checked">
						<label for="remember"><%=registry.getStringOfLanguage("login.remember", language) %></label> 
						<br>
						<a href="#" data-reveal-id="lostPasswordModal" class="login-tools"><%=registry.getStringOfLanguage("login.lost", language) %></a>
					</div>
				</form>
			</div>
		</div>
		
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

<input type="hidden" id="resPath" value="<%=resPath%>">
	<input type="hidden" id="basePath" value="<%=basePath %>" />
	<input type="hidden" id="username_empty_msg" value="<%=registry.getStringOfLanguage("placeholder.name", language) %>" />

<!--[if lte IE 9]>
	<script src="<%=resPath%>bower_components/REM-unit-polyfill/js/rem.min.js"></script>
	<script src="<%=resPath%>bower_components/jquery-legacy/jquery.min.js"></script>
<![endif]-->

	<script src="<%=resPath%>bower_components/jquery/dist/jquery.min.js"></script>
	<script src="<%=resPath%>bower_components/velocity/velocity.min.js"></script>
	<script src="<%=resPath%>bower_components/foundation/js/foundation.min.js"></script>
	<script src="<%=resPath%>js/opp_login.js"></script>
	<script src="<%=resPath%>js/opp_app.js"></script>
		
<%@include file="jsp/opp_footer.jsp"%>