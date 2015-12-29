<%@page import="com.youandbbva.enteratv.Utils"%>
<%@page import="com.youandbbva.enteratv.domain.UserInfo"%>
<%@page import="com.youandbbva.enteratv.beans.SessionHandler"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%
	String basePath_topbar = request.getContextPath() + "/";
	String resPath_topbar = basePath_topbar + "resources/";
	SessionHandler handler_topbar = SessionHandler.getInstance();
	UserInfo user = handler_topbar.getFrontUserInfo(session);
	String akaname = "";
	String lastname = "";

	if (user != null) {
		akaname = Utils.checkNull(user.getUserName());
		lastname = Utils.checkNull(user.getUserFirstName());
		String lll = Utils.checkNull(user.getUserLastName());
		if (lll.length() > 0)
			lastname += " " + lll;
	}
%>

<header class="small-16 columns">
	<div class="row">
		<div class="left large-4 medium-6 small-8  columns logo-wrapper">
			<span><span><a
					href="<%=basePath_topbar%>public/home.html"><img
						src="<%=resPath_topbar%>images/logo-bbva-tipo.svg" id="site-logo"></a></span></span>
		</div>
		<div class="right large-12 medium-10 small-8 columns"></div>
	</div>
	<div class="row">
		<div class="large-12 medium-10 small-11 columns" id="username">
			<div>
				<span><font color="black">Hola,</font> <%=akaname%></span> <span><%=lastname%></span>
			</div>
		</div>
		<div id="entera-logo">
			<a href="<%=basePath_topbar%>public/home.html"><img
				src="<%=resPath_topbar%>images/logo-enteratv.svg" style="width:232px; height:87px" alt=""></a>
		</div>
	</div>
</header>

<div class="large-1 medium-1 columns show-for-medium-up"
	id="site-tools-wrapper">
	<div class="site-tools" id="site-tools-desktop">
		<ul>
			<li class="strip-holder"><img
				src="<%=resPath_topbar%>images/bbva-color-hstrip.svg" class="strip"></li>
			<li><a href="javascript:open_search()"><span
					class="tool-item-wrapper"><img
						src="<%=resPath_topbar%>images/icons/magnify-glass.svg"></span></a></li>
			<li><a href="#" class="arrow-menu"><span
					class="tool-item-wrapper"><img
						src="<%=resPath_topbar%>images/icons/arrow-right.svg"></span></a></li>
			<c:choose>
				<c:when test="${!empty gallerylink}">
					<li><a href="javascript:gallery('${gallerylink}')"><span
							class="tool-item-wrapper"><img
								src="<%=resPath_topbar%>images/icons/camera.svg"></span></a></li>
				</c:when>
				<c:otherwise>
					<li><a href="<%=basePath_topbar + "public/galerias.html"%>"><span
							class="tool-item-wrapper"><img
								src="<%=resPath_topbar%>images/icons/camera.svg"></span></a></li>
				</c:otherwise>
			</c:choose>
<%-- 			<li><a href="<%=basePath_topbar%>user/logout.html"><span --%>
<!-- 					class="tool-item-wrapper"><img -->
<%-- 						src="<%=resPath_topbar%>images/icons/exit.svg"></span></a></li> --%>
		</ul>
	</div>
</div>
