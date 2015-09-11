<%@page import="com.youandbbva.enteratv.Utils"%>
<%@page import="com.youandbbva.enteratv.domain.UserInfo"%>
<%@page import="com.youandbbva.enteratv.Registry"%>
<%@page import="com.youandbbva.enteratv.beans.SessionHandler"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<%
	String resPath_Header = request.getContextPath()+"/" + "resources/";
	Registry reg_Header = Registry.getInstance();
	SessionHandler handler_Header = SessionHandler.getInstance();
	String title_Header = reg_Header.getStringOfApplication("application.title", handler_Header.getLanguage(session));
	
	UserInfo user = handler_Header.getOpportUserInfo(session);
	String akaname="";
	String lastname="";

	if (user!=null){
		akaname=Utils.checkNull(user.getUserName());
		lastname=Utils.checkNull(user.getUserLastName());
		if (lastname.length()>0)
			lastname = " &amp; " + lastname;
	}
	
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!doctype html>
<html class="no-js" lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title><%=title_Header %></title>
<link rel="stylesheet" href="<%=resPath_Header%>stylesheets/app_opp.css" />
<link rel="stylesheet" href="<%=resPath_Header%>stylesheets/dataTables.foundation.css" />
<link rel="stylesheet" href="<%=resPath_Header%>stylesheets/dataTables.responsive.css" />
<link rel="stylesheet" href="<%=resPath_Header%>stylesheets/foundation-datepicker.css" />
<link rel="stylesheet" href="<%=resPath_Header%>stylesheets/style_opp.css" />

<script src="<%=resPath_Header%>bower_components/modernizr/modernizr.js"></script>
<script src="<%=resPath_Header%>js/common.js"></script>

   <!--[if lt IE 9]>
     <script src="<%=resPath_Header%>bower_components/html5shiv/dist/html5shiv.min.js"></script>
     <script src="<%=resPath_Header%>bower_components/nwmatcher/src/nwmatcher.js"></script>
     <script src="<%=resPath_Header%>bower_components/selectivizr/selectivizr.js"></script>
     <script src="<%=resPath_Header%>bower_components/respond/dest/respond.min.js"></script>
   <![endif]-->
</head>
<body>