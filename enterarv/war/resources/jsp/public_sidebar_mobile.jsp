<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%
	String basePath_sidebar_mobile = request.getContextPath()+"/"; 
	String resPath_sidebar_mobile = basePath_sidebar_mobile + "resources/";
%>

<div class="small-3 columns tools">
	<a href="#" class="a-button"><i class="fa fa-angle-down fa-3x"></i>
	</a>
	<div class="site-tools" id="mobile-site-tools">
		<ul>
			<li><a href="javascript:open_search()"><span class="tool-item-wrapper"><img src="<%=resPath_sidebar_mobile%>images/icons/magnify-glass.svg"></span></a></li>
			<li><a href="<%=basePath_sidebar_mobile%>public/galerias.html"><span class="tool-item-wrapper"><img src="<%=resPath_sidebar_mobile%>images/icons/camera.svg"></span></a></li>
<%-- 			<li><a href="<%=basePath_sidebar_mobile%>user/logout.html"><span class="tool-item-wrapper"><img src="<%=resPath_sidebar_mobile%>images/icons/exit.svg"></span></a></li> --%>
		</ul>
	</div>
</div>
