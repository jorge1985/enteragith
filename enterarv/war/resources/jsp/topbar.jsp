<%@page import="com.youandbbva.enteratv.Utils"%>
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
	
	String username="";
	UserInfo user = handler_Topbar.getUserInfo(session);
	if (user!=null){
		username = Utils.checkNull(user.getUserName());
		String firstname = Utils.checkNull(user.getUserFirstName());
		String lastname = Utils.checkNull(user.getUserLastName());
		if (firstname.length()>0){
			username += " " + firstname;
		}
		if (lastname.length()>0){
			username += " " + lastname;
		}
	}
%>

	<div id="topbar">
		<div class="small-12 medium-4 large-3 columns">
			<img src="<%=resPath_Topbar%>images/logo-bbva-tipo_white.svg" id="main-logo" style="width: 90%; color: #fff;">
		</div>
		<div class="small-5 medium-4 large-6 columns">
			<a href="<%=basePath_Topbar %>public/home.html" class="preview" target="_blank">
				<i class="fi-layout size-24"></i><span><%=registry_Topbar.getStringOfLanguage("preview", language_Topbar) %></span>
			</a>
		</div>
		<div class="small-7 medium-4 large-3 columns">
			<a href="" class="search-wrapper" data-dropdown="search-drop" aria-controls="search-drop" aria-expanded="false"><i class="fi-magnifying-glass size-24 search"></i></a>
			<ul id="search-drop" data-dropdown-content class="f-dropdown" aria-hidden="true" tabindex="-1">
				<input type="text" id="global_search" placeholder="<%=registry_Topbar.getStringOfLanguage("type_enter", language_Topbar) %>" value="">
			</ul>
			
			<a href="" class="userinfo-wrapper" data-dropdown="user-drop" aria-controls="user-drop" aria-expanded="false">
				<i class="fi-torso size-24 user"></i>
				<span><%=username %></span>
				<i class="fi-arrow-down size-12 arrow"></i>
			</a>

			<ul id="user-drop" data-dropdown-content class="f-dropdown" aria-hidden="true" tabindex="-1">
				<li>
					<a href="<%=basePath_Topbar%>system/options.html"><i class="fi-clock"></i><%=registry_Topbar.getStringOfLanguage("options", language_Topbar) %></a>
				</li>
<!-- 				<li> -->
<%-- 					<a href="<%=basePath_Topbar%>user/logout.html"><i class="fi-clock"></i><%=registry_Topbar.getStringOfLanguage("logout", language_Topbar) %></a> --%>
<!-- 				</li> -->
			</ul>
		</div>
	</div>
