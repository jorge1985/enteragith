<%@page import="com.youandbbva.enteratv.Registry"%>
<%@page import="com.youandbbva.enteratv.beans.SessionHandler"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String resPath_varios_result = request.getContextPath()+"/" + "resources/";
	Registry reg_varios_result = Registry.getInstance();
	SessionHandler handler_varios_result = SessionHandler.getInstance();
	String language_varios_result = handler_varios_result.getLanguage(session);
%>

<table id="varios" class="display" cellspacing="0" cellpadding="0" width="100%">
	<thead>
	<tr>
		<th><%=reg_varios_result.getStringOfLanguage("opport.table.location", language_varios_result) %></th>
		<th><%=reg_varios_result.getStringOfLanguage("opport.srv.table.varios", language_varios_result) %></th>
		<th><%=reg_varios_result.getStringOfLanguage("opport.srv.table.article", language_varios_result) %></th>
		<th><%=reg_varios_result.getStringOfLanguage("opport.table.price", language_varios_result) %></th>
	</tr>
	</thead>
</table>

<script type="text/javascript" src="<%=resPath_varios_result%>js/opp_results_varios.js"></script>