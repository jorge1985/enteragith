<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="../variable.jsp"%>
<%@include file="jsp/header.jsp"%>

	<%@include file="jsp/topbar.jsp"%>

	<div id="content-wrapper">

	<%@include file="jsp/menubar.jsp"%>
		
		<div class="small-10 medium-8 large-9 columns">
			<div id="content">
				<h1 class="main-title"><%=registry.getStringOfLanguage("family.title", language) %></h1>

				<div id="families">

<div id="msg_alert">
	<img id="msg_alert_progress" src="<%=resPath %>images/progress.gif">
	<span>Message</span>
</div>
					
					<div class="row left">
						<div class="large-6 columns">
							<form id="family_form" method="post" action="#" data-abide>
							<div class="row">
								<label><%=registry.getStringOfLanguage("family.family_name", language) %>
								<input type="text" id="family" name="family" placeholder="<%=registry.getStringOfLanguage("placeholder.name", language) %>" required="required" maxlength="100" />
								</label>
								<small class="error"><%=registry.getStringOfLanguage("validate.name", language) %></small>
							</div>

							<div class="row">
								<label><%=registry.getStringOfLanguage("family.visible", language) %></label> 
								<input type="radio" name="visible" value="1" id="visibleYes" checked="checked">
								<label for="visibleYes"><%=registry.getStringOfLanguage("yes", language) %></label> 
								<input type="radio" name="visible" value="2" id="visibleNo">
								<label for="visibleNo"><%=registry.getStringOfLanguage("no", language) %></label>
							</div>

								<button type="reset" class="medium" id="btnReset"><%=registry.getStringOfLanguage("btn.reset", language) %></button>
								<button type="submit" class="medium" id="btnSubmit"><%=registry.getStringOfLanguage("btn.submit", language) %></button>
							</form>
							
							<input type="hidden" id="type" value="add" />
							<input type="hidden" id="family_id" value="" />
						</div>
						
						<div class="large-6 columns">
							<small><%=registry.getStringOfLanguage("family.comment", language) %></small>
							<ol class="sortable" id="families-tree"></ol>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="<%=resPath%>bower_components/jquery/dist/jquery.min.js"></script>
	<script src="<%=resPath%>bower_components/velocity/velocity.min.js"></script>
	<script src="<%=resPath%>bower_components/foundation/js/foundation.min.js"></script>
	<script src="<%=resPath%>bower_components/jquery-ui/jquery-ui.min.js"></script>
	<script src="<%=resPath%>bower_components/nestedSortable/jquery.ui.nestedSortable.js"></script>
	<script src="<%=resPath%>js/app.js"></script>

	<script src="<%=resPath%>js/families.js"></script>
	
<%@include file="jsp/footer.jsp"%>