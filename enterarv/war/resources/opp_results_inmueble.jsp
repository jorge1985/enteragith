<%@page import="com.youandbbva.enteratv.Registry"%>
<%@page import="com.youandbbva.enteratv.beans.SessionHandler"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String resPath_inmueble_result = request.getContextPath()+"/" + "resources/";
	Registry reg_inmueble_result = Registry.getInstance();
	SessionHandler handler_inmueble_result = SessionHandler.getInstance();
	String language_inmueble_result = handler_inmueble_result.getLanguage(session);
%>

<table id="inmueble" class="display" cellspacing="0" cellpadding="0" width="100%">
	<thead>
	<tr>
		<th><%=reg_inmueble_result.getStringOfLanguage("opport.table.location", language_inmueble_result) %></th>
		<th><%=reg_inmueble_result.getStringOfLanguage("opport.table.price", language_inmueble_result) %></th>
		<th><%=reg_inmueble_result.getStringOfLanguage("opport.fur.table.property", language_inmueble_result) %></th>
		<th><%=reg_inmueble_result.getStringOfLanguage("opport.fur.table.building", language_inmueble_result) %></th>
		<th><%=reg_inmueble_result.getStringOfLanguage("opport.fur.table.rooms", language_inmueble_result) %></th>
		<th><%=reg_inmueble_result.getStringOfLanguage("opport.fur.table.plants", language_inmueble_result) %></th>
	</tr>
	</thead>
</table>

<script type="text/javascript" src="<%=resPath_inmueble_result%>js/opp_results_inmueble.js"></script>