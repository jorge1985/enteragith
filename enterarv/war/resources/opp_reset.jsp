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
		<h1><%=registry.getStringOfLanguage("opport.title.reset", language) %></h1>
		
		<div class="row" style="background-color: #f9f9f9;">
			<div class="large-4 medium-6 small-10 small-centered columns" id="login-form-wrapper">
				<form id="login-form" action="<%=basePath %>opportunities/savePassword.html" method="post" >
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
		
	</div>
</div>
	
<input type="hidden" id="resPath" value="<%=resPath%>">
	
	<input type="hidden" id="basePath" value="<%=basePath %>" />
	<input type="hidden" id="username_empty_msg" value="<%=registry.getStringOfLanguage("placeholder.name", language) %>" />
	<input type="hidden" id="password_empty_msg" value="<%=registry.getStringOfLanguage("placeholder.password", language) %>" />
	<input type="hidden" id="password_same_msg" value="<%=registry.getStringOfLanguage("validate.same_password", language) %>" />

<!--[if lte IE 9]>
	<script src="<%=resPath%>bower_components/REM-unit-polyfill/js/rem.min.js"></script>
	<script src="<%=resPath%>bower_components/jquery-legacy/jquery.min.js"></script>
<![endif]-->

	<script src="<%=resPath%>bower_components/jquery/dist/jquery.min.js"></script>
	<script src="<%=resPath%>bower_components/velocity/velocity.min.js"></script>
	<script src="<%=resPath%>bower_components/foundation/js/foundation.min.js"></script>
	<script src="<%=resPath%>js/opp_reset.js"></script>
	<script src="<%=resPath%>js/opp_app.js"></script>
	
<%@include file="jsp/opp_footer.jsp"%>