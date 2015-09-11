<%@page import="com.youandbbva.enteratv.Registry"%>
<%@page import="com.youandbbva.enteratv.beans.SessionHandler"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<%
	String resPath_Header = request.getContextPath()+"/" + "resources/";
	Registry reg_Header = Registry.getInstance();
	SessionHandler handler_Header = SessionHandler.getInstance();
	String title_Header = reg_Header.getStringOfApplication("application.title", handler_Header.getLanguage(session));
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!doctype html>
<html class="no-js" lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no" />
<title><%=title_Header %></title>
<link rel="stylesheet" href="<%=resPath_Header%>stylesheets/jquery-ui.min.css" />
<link rel="stylesheet" href="<%=resPath_Header%>stylesheets/app.css" />
<link rel="stylesheet" href="<%=resPath_Header%>stylesheets/style.css" />
<link rel="stylesheet" href="<%=resPath_Header%>stylesheets/dataTables.foundation.css" />
<link rel="stylesheet" href="<%=resPath_Header%>stylesheets/dataTables.responsive.css" />
<link rel="stylesheet" href="<%=resPath_Header%>stylesheets/foundation-datepicker.css" />
<link rel="stylesheet" href="<%=resPath_Header%>stylesheets/lightbox.css" />
<link rel="stylesheet" href="<%=resPath_Header%>stylesheets/myeditor.css" />
<link rel="stylesheet" href="<%=resPath_Header%>stylesheets/pgwmodal.css" />
<link rel="stylesheet" href="<%=resPath_Header%>stylesheets/jquery.fancybox.css" />
<link rel="stylesheet" href="<%=resPath_Header%>stylesheets/jquery.fancybox-buttons.css" />
<link rel="stylesheet" href="<%=resPath_Header%>stylesheets/jquery.fancybox-thumbs.css" />

<script src="<%=resPath_Header%>bower_components/modernizr/modernizr.js"></script>
<script src="<%=resPath_Header%>js/common.js"></script>
<script src="<%=resPath_Header%>js/msg.js"></script>

</head>
<body>