<%@page import="com.youandbbva.enteratv.Registry"%>
<%@page import="com.youandbbva.enteratv.beans.SessionHandler"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String resPath_automovil_result = request.getContextPath()+"/" + "resources/";
	Registry reg_automovil_result = Registry.getInstance();
	SessionHandler handler_automovil_result = SessionHandler.getInstance();
	String language_automovil_result = handler_automovil_result.getLanguage(session);
%>

<table id="automovil" class="display" cellspacing="0" cellpadding="0" width="100%">
	<thead>
	<tr>
		<th><%=reg_automovil_result.getStringOfLanguage("opport.car.table.car", language_automovil_result) %></th>
		<th><%=reg_automovil_result.getStringOfLanguage("opport.table.price", language_automovil_result) %></th>
		<th><%=reg_automovil_result.getStringOfLanguage("opport.car.table.mileage", language_automovil_result) %></th>
		<th><%=reg_automovil_result.getStringOfLanguage("opport.car.table.transmission", language_automovil_result) %></th>
		<th><%=reg_automovil_result.getStringOfLanguage("opport.table.location", language_automovil_result) %></th>
	</tr>
	</thead>
</table>

<script type="text/javascript" src="<%=resPath_automovil_result%>js/opp_results_automovil.js"></script>