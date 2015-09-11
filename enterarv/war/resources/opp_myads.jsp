<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String basepath = request.getContextPath();
%>
<%@include file="../variable.jsp"%>
<%@include file="jsp/opp_header.jsp"%>

<%@include file="jsp/opp_topbar.jsp"%>

<%
int userid_body= 0;
boolean isPublish_body=false;
UserInfo user_body = handler.getOpportUserInfo(session);
if (user_body!=null && user_body.getUserId()!=0){
	userid_body = user_body.getUserId();
	isPublish_body = Utils.canPublish(userid_body);
}
%>
<div class="row" id="content-mis-anuncios">
	<div class="large-12 columns">
		<h1><%=registry.getStringOfLanguage("opport.title.myads", language) %></h1>
		<div class="panel">
			<h3>
				<img src="<%=resPath%>images/automovil.svg" alt=""> <%=registry.getStringOfLanguage("opport.car", language) %> 
<% 
	if (userid_body==0 || !isPublish_body){
%>

<% 
	}	else{
%>		
			<a href="<%=basePath %>opportunities/add.html" class="button round tiny right"><i class="fa fa-plus fa-lg"></i> <%=registry.getStringOfLanguage("opport.btn.add", language) %></a>				 
<% 
 	}
%>

			</h3>
			
			<table id="automovil" class="display" cellspacing="0" cellpadding="0" width="100%">
				<thead>
				<tr>
					<th><%=registry.getStringOfLanguage("opport.car.table.car", language) %></th>
					<th><%=registry.getStringOfLanguage("opport.table.price", language) %></th>
					<th><%=registry.getStringOfLanguage("opport.car.table.mileage", language) %></th>
					<th><%=registry.getStringOfLanguage("opport.car.table.transmission", language) %></th>
					<th><%=registry.getStringOfLanguage("opport.table.location", language) %></th>
					<th><%=registry.getStringOfLanguage("opport.table.action", language) %></th>
				</tr>
				</thead>
			</table>
		</div>

		<div class="panel">
			<h3>
				<img src="<%=resPath%>images/inmueble.svg" alt=""> <%=registry.getStringOfLanguage("opport.furniture", language) %> 
<%
	if (userid_body==0 || !isPublish_body){
%>			
<%
	}	else{
%>			
				<a href="<%=basePath %>opportunities/add.html" class="button round tiny right"><i class="fa fa-plus fa-lg"></i> <%=registry.getStringOfLanguage("opport.btn.add", language) %></a>
<%
	}
%>
			</h3>
			<table id="inmueble" class="display" cellspacing="0" cellpadding="0" width="100%">
				<thead>
				<tr>
					<th><%=registry.getStringOfLanguage("opport.table.location", language) %></th>
					<th><%=registry.getStringOfLanguage("opport.table.price", language) %></th>
					<th><%=registry.getStringOfLanguage("opport.fur.table.property", language) %></th>
					<th><%=registry.getStringOfLanguage("opport.fur.table.building", language) %></th>
					<th><%=registry.getStringOfLanguage("opport.fur.table.rooms", language) %></th>
					<th><%=registry.getStringOfLanguage("opport.fur.table.plants", language) %></th>
					<th><%=registry.getStringOfLanguage("opport.table.action", language) %></th>
				</tr>
				</thead>
			</table>
		</div>

		<div class="panel">
			<h3>
				<img src="<%=resPath%>images/varios.svg" alt=""> <%=registry.getStringOfLanguage("opport.several", language) %> 
<%
	if (userid_body == 0 || !isPublish_body){
%>			
<%
	}	else{
%>			
				<a href="<%=basePath %>opportunities/add.html" class="button round tiny right"><i class="fa fa-plus fa-lg"></i> <%=registry.getStringOfLanguage("opport.btn.add", language) %></a>
<%
	}
%>
			</h3>
			<table id="varios" class="display" cellspacing="0" cellpadding="0" width="100%">
				<thead>
				<tr>
					<th><%=registry.getStringOfLanguage("opport.table.location", language) %></th>
					<th><%=registry.getStringOfLanguage("opport.srv.table.varios", language) %></th>
					<th><%=registry.getStringOfLanguage("opport.srv.table.article", language) %></th>
					<th><%=registry.getStringOfLanguage("opport.table.price", language) %></th>
					<th><%=registry.getStringOfLanguage("opport.table.action", language) %></th>
				</tr>
				</thead>
			</table>
		</div>
	</div>
</div>

<form id="frmPublish" method="post" action="<%=basePath%>opportunities/publish.html">
	<input type="hidden" name="type" id="publish_type" value="">
	<input type="hidden" name="kind" id="publish_kind" value="">
	<input type="hidden" name="publish_id" id="publish_id" value="">
</form>

<input type="hidden" id="basePath" value="<%=basePath%>">
<input type="hidden" id="resPath" value="<%=resPath%>">

<!--[if lte IE 9]>
	<script src="<%=resPath%>bower_components/REM-unit-polyfill/js/rem.min.js"></script>
	<script src="<%=resPath%>bower_components/jquery-legacy/jquery.min.js"></script>
<![endif]-->

<script src="<%=resPath%>bower_components/jquery/dist/jquery.min.js"></script>
<script src="<%=resPath%>bower_components/velocity/velocity.min.js"></script>
<script src="<%=resPath%>bower_components/foundation/js/foundation.min.js"></script>
<script src="<%=resPath%>js/jquery/jquery.dataTables.min.js"></script>
<script src="<%=resPath%>js/foundation/dataTables.foundation.min.js"></script>
<script src="<%=resPath%>js/foundation/dataTables.responsive.js"></script>
<script src="<%=resPath%>js/opp_myads.js"></script>
<script src="<%=resPath%>js/opp_app.js"></script>

<%@include file="jsp/opp_footer.jsp"%>