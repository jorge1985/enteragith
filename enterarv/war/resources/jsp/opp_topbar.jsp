<%@page import="org.apache.commons.codec.digest.DigestUtils"%>
<%@page import="com.youandbbva.enteratv.Utils"%>
<%@page import="com.youandbbva.enteratv.Constants"%>
<%@page import="com.youandbbva.enteratv.domain.UserInfo"%>
<%@page import="com.youandbbva.enteratv.beans.SessionHandler"%>
<%@page import="com.youandbbva.enteratv.Registry"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%
	String basePath_Topbar = request.getContextPath()+"/";
	String resPath_Topbar = basePath_Topbar + "resources/";
	Registry registry_Topbar = Registry.getInstance();
	SessionHandler handler_Topbar = SessionHandler.getInstance();

	String language_Topbar = handler_Topbar.getLanguage(session);
	UserInfo user_Topbar = handler_Topbar.getOpportUserInfo(session);
	int userid_Topbar = 0;
	String sessionid_Topbar="";
	boolean isPublish_Topbar=false;
	if (user_Topbar!=null && user_Topbar.getUserId()!=0){
		userid_Topbar = user_Topbar.getUserId();
		isPublish_Topbar = Utils.canPublish(userid_Topbar);
		sessionid_Topbar=DigestUtils.md5Hex(userid_Topbar+Constants.SESSION_HASH);
	}
%>

<header class="row">
	<div class="large-6 medium-6 small-7 columns" id="logo-bbva">
		<a href="http://enteratv-bbva.appspot.com/public/home.html"><img src="<%=resPath_Topbar%>images/logo-bbva.svg" class="logo" alt=""></a>
	</div>
	<div class="large-6 medium-6 small-5 columns" id="logo-oportunidades">
		<div class="right">
			<span><img src="<%=resPath_Topbar%>images/logo-oportunidades.svg" class="logo" alt=""></span>
		</div>
	</div>
</header>

<div class="row tools-menu" style="min-height: 40px;">
	<div class="small-2 columns">
		<a href="<%=basePath_Topbar %>opportunities/home.html?user_id=<%=userid_Topbar %>&session_id=<%=sessionid_Topbar %>" class="home-link"><img src="<%=resPath_Topbar%>images/home.svg" alt=""></a>
	</div>
	<div class="small-10  columns">
		<div class="right">
<%
	if (userid_Topbar == 0){
%>			

<%
	}	else{
%>
			<a href="<%=basePath_Topbar %>opportunities/myads.html" class="button round tiny"><i class="fa fa-list fa-1x"></i> <%=registry_Topbar.getStringOfLanguage("opport.menu.myads", language_Topbar) %></a>
<%
	if (!isPublish_Topbar){
%>

<%
	}else {
%>
			<a href="<%=basePath_Topbar %>opportunities/add.html" class="button round tiny"><i class="fa fa-magic fa-1x"></i> <%=registry_Topbar.getStringOfLanguage("opport.menu.login", language_Topbar) %></a>
<%
	}
	}
%>
		</div>
	</div>
</div>
