<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="../variable.jsp"%>
<%@include file="jsp/header.jsp"%>

	<%@include file="jsp/topbar.jsp"%>

	<div id="content-wrapper">

	<%@include file="jsp/menubar.jsp"%>
		
		<div class="small-10 medium-8 large-9 columns">
			<div id="content">
				<h1 class="main-title"><%=registry.getStringOfLanguage("content.title.expired", language) %></h1>

				<div id="expired_contents">

<div id="msg_alert">
	<img id="msg_alert_progress" src="<%=resPath %>images/progress.gif">
	<span>Message</span>
</div>
					
					<div class="row left">
						<div class="large-12 columns">

<table id="content_table" class="display" cellspacing="0" cellpadding="0" width="100%">
    <thead>
        <tr>
            <th><%=registry.getStringOfLanguage("content.table.title", language) %></th>
            <th><%=registry.getStringOfLanguage("content.table.type", language) %></th>
            <th><%=registry.getStringOfLanguage("content.table.tag", language) %></th>
            <th><%=registry.getStringOfLanguage("content.table.channel", language) %></th>
            <th><%=registry.getStringOfLanguage("content.table.date", language) %></th>
            <th><%=registry.getStringOfLanguage("content.table.status", language) %></th>
            <th><%=registry.getStringOfLanguage("content.table.action", language) %></th>
        </tr>
    </thead>
</table>
						
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<form id="form_move" method="post" action="<%=basePath %>content/edit.html">
		<input type="hidden" name="content_id" id="edit_content_id" value="" />
		<input type="hidden" name="content_type" id="edit_content_type" value="" />
	</form>

<form id="form_move_content" method="get" action="<%=basePath %>public/content.html" target="_blank">
	<input type="hidden" name="content_id" id="edit2_content_id" value="" />
	<input type="hidden" name="channel_id" id="edit2_channel_id" value="" />
	<input type="hidden" name="content_blog" id="edit2_content_blog" value="" />
	<input type="hidden" name="access_from" value="cms" />
</form>

	<input type="hidden" id="status_active_msg" value="<%=registry.getStringOfLanguage("content.status.active", language) %>" />
	<input type="hidden" id="status_featured_msg" value="<%=registry.getStringOfLanguage("content.status.featured", language) %>" />
	<input type="hidden" id="status_deleted_msg" value="<%=registry.getStringOfLanguage("content.status.deleted", language) %>" />
	<input type="hidden" id="status_none_msg" value="<%=registry.getStringOfLanguage("content.status.none", language) %>" />

	<script src="<%=resPath%>bower_components/jquery/dist/jquery.min.js"></script>
	<script src="<%=resPath%>bower_components/velocity/velocity.min.js"></script>
	<script src="<%=resPath%>bower_components/foundation/js/foundation.min.js"></script>
	<script src="<%=resPath%>bower_components/jquery-ui/jquery-ui.min.js"></script>
	<script src="<%=resPath%>js/jquery/jquery.dataTables.min.js"></script>
	<script src="<%=resPath%>js/foundation/dataTables.foundation.min.js"></script>
	<script src="<%=resPath%>js/foundation/dataTables.responsive.js"></script>
	<script src="<%=resPath%>js/app.js"></script>

	<script src="<%=resPath%>js/expired_content.js"></script>
	
<%@include file="jsp/footer.jsp"%>