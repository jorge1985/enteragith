<%@page import="com.youandbbva.enteratv.beans.SessionHandler"%>
<%@page import="com.youandbbva.enteratv.Registry"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String basePath = request.getContextPath()+"/";
	String resPath = basePath + "resources/"; 
	
	Registry registry = Registry.getInstance();
	SessionHandler handler = SessionHandler.getInstance();
	String title = registry.getStringOfApplication("application.title", handler.getLanguage(session));
	
	String message = handler.getFlashMessage(session);
	handler.setFlashMessage(session, "");
	
	String language = handler.getLanguage(session);
%>

<%
%>