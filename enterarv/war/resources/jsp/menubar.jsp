<%@page import="com.youandbbva.enteratv.beans.SessionHandler"%>
<%@page import="com.youandbbva.enteratv.Registry"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<%
	String basePath_Menubar = request.getContextPath()+"/";
	String resPath_Menubar = basePath_Menubar + "resources/";
	Registry registry_Menubar = Registry.getInstance();
	SessionHandler handler_Menubar = SessionHandler.getInstance();
	String language_Menubar = handler_Menubar.getLanguage(session);
%>

<div id="menu-container" class="small-2 medium-4 large-3 columns">
	<div id="main-menu" class="hide-for-small">
		<div class="row">
			<div class="large-12 columns nav-wrapper">
				<ul class="side-nav">
					<li>
						<input type="checkbox" id="menu-field1" class="toggle">
						<label for="menu-field1"><a href="<%=basePath_Menubar %>dashboard/dashboard.html"><i class="fi-thumbnails"></i> <%=registry_Menubar.getStringOfLanguage("menu.dashboard", language_Menubar) %></a></label>
					</li>
					<li>
						<input type="checkbox" id="menu-field2" class="toggle">
						<label for="menu-field2"><i class="fi-list-bullet"></i> <%=registry_Menubar.getStringOfLanguage("menu.category", language_Menubar) %></label>
						<div class="submenu-wrapper" id="main-sub2">
							<div class="top-submenu"></div>
							<ul>
								<li><a href="<%=basePath_Menubar %>category/families.html"> <%=registry_Menubar.getStringOfLanguage("menu.family", language_Menubar) %></a></li>
								<li><a href="<%=basePath_Menubar %>category/channels.html"> <%=registry_Menubar.getStringOfLanguage("menu.channel", language_Menubar) %></a></li>
							</ul>
						</div>
					</li>
					<li>
						<input type="checkbox" id="menu-field3" class="toggle">
						<label for="menu-field3"><a href="<%=basePath_Menubar %>media/manager.html"><i class="fi-photo"></i> <%=registry_Menubar.getStringOfLanguage("menu.media_manager", language_Menubar) %></a></label>
					</li>
					<li>
						<input type="checkbox" id="menu-field4" class="toggle">
						<label for="menu-field4"><i class="fi-page"></i> <%=registry_Menubar.getStringOfLanguage("menu.content", language_Menubar) %></label>

						<div class="submenu-wrapper" id="main-sub4">
							<div class="top-submenu"></div>
							<ul>
								<li><a href="<%=basePath_Menubar %>content/add.html"> <%=registry_Menubar.getStringOfLanguage("menu.add_new", language_Menubar) %></a></li>
								<li><a href="<%=basePath_Menubar %>content/active.html"> <%=registry_Menubar.getStringOfLanguage("menu.active", language_Menubar) %></a></li>
								<li><a href="<%=basePath_Menubar %>content/expired.html"> <%=registry_Menubar.getStringOfLanguage("menu.expired", language_Menubar) %></a></li>
								<li><a href="<%=basePath_Menubar %>content/recycle.html"> <%=registry_Menubar.getStringOfLanguage("menu.recycle_bin", language_Menubar) %></a></li>
							</ul>
						</div>
					</li>
					<li>
						<input type="checkbox" id="menu-field5" class="toggle">
						<label for="menu-field5"><a href="<%=basePath_Menubar %>widget/banner.html"><i class="fi-layout"></i> <%=registry_Menubar.getStringOfLanguage("menu.banner", language_Menubar) %></a></label>
					</li>
					<li>
						<input type="checkbox" id="menu-field6" class="toggle">
						<label for="menu-field6"><a href="<%=basePath_Menubar %>system/users.html"><i class="fi-torsos-all"></i> <%=registry_Menubar.getStringOfLanguage("menu.user", language_Menubar) %></a></label>
					</li>
					<li>
						<input type="checkbox" id="menu-field7" class="toggle">
						<label for="menu-field7"> <i class="fi-comments"></i> <%=registry_Menubar.getStringOfLanguage("menu.log", language_Menubar) %></label>

						<div class="submenu-wrapper" id="main-sub7">
							<div class="top-submenu"></div>
							<ul>
								<li><a href="<%=basePath_Menubar %>log/access.html"> <%=registry_Menubar.getStringOfLanguage("menu.log.access", language_Menubar) %></a></li>
								<li><a href="<%=basePath_Menubar %>log/channel.html"> <%=registry_Menubar.getStringOfLanguage("menu.log.channel", language_Menubar) %></a></li>
								<li><a href="<%=basePath_Menubar %>log/content.html"> <%=registry_Menubar.getStringOfLanguage("menu.log.content", language_Menubar) %></a></li>
								<li><a href="<%=basePath_Menubar %>log/advanced.html"> <%=registry_Menubar.getStringOfLanguage("menu.log.advanced", language_Menubar) %></a></li>
							</ul>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</div>
	
	<div id="mobile-menu" class="show-for-small-only">
		<ul class="side-nav">
			<li>
				<a href="<%=basePath_Menubar %>dashboard/dashboard.html" title="<%=registry_Menubar.getStringOfLanguage("menu.dashboard", language_Menubar) %>"><i class="fi-thumbnails size-24"></i></a>
			</li>
			<li>
				<a href="#" title="<%=registry_Menubar.getStringOfLanguage("menu.category", language_Menubar) %>" data-dropdown="mobile-menu-categories" aria-controls="mobile-menu-categories" aria-expanded="false"><i class="fi-list-bullet size-24"></i></a>
				<ul id="mobile-menu-categories" data-dropdown-content class="f-dropdown" aria-hidden="true" tabindex="-1">
					<li><a href="<%=basePath_Menubar %>category/families.html"><%=registry_Menubar.getStringOfLanguage("menu.family", language_Menubar) %></a></li>
					<li><a href="<%=basePath_Menubar %>category/channels.html"><%=registry_Menubar.getStringOfLanguage("menu.channel", language_Menubar) %></a></li>
				</ul>
			</li>
			<li>
				<a href="<%=basePath_Menubar %>media/manager.html" title="<%=registry_Menubar.getStringOfLanguage("menu.media_manager", language_Menubar) %>"><i class="fi-photo size-24"></i></a>
			</li>
			<li>
				<a href="#" title="<%=registry_Menubar.getStringOfLanguage("menu.content", language_Menubar) %>" data-dropdown="mobile-menu-content" aria-controls="mobile-menu-categories" aria-expanded="false"><i class="fi-page size-24"></i></a>
				<ul id="mobile-menu-content" data-dropdown-content class="f-dropdown" aria-hidden="true" tabindex="-1">
					<li><a href="<%=basePath_Menubar %>content/add.html"><%=registry_Menubar.getStringOfLanguage("menu.add_new", language_Menubar) %></a></li>
					<li><a href="<%=basePath_Menubar %>content/active.html"><%=registry_Menubar.getStringOfLanguage("menu.active", language_Menubar) %></a></li>
					<li><a href="<%=basePath_Menubar %>content/expired.html"><%=registry_Menubar.getStringOfLanguage("menu.expired", language_Menubar) %></a></li>
					<li><a href="<%=basePath_Menubar %>content/recycle.html"><%=registry_Menubar.getStringOfLanguage("menu.recycle_bin", language_Menubar) %></a></li>
				</ul>
			</li>
			<li>
				<a href="<%=basePath_Menubar %>widget/banner.html" title="<%=registry_Menubar.getStringOfLanguage("menu.widget", language_Menubar) %>"><i class="fi-layout size-24"></i></a>
			</li>
			<li>
				<a href="<%=basePath_Menubar %>system/users.html" title="<%=registry_Menubar.getStringOfLanguage("menu.user", language_Menubar) %>"><i class="fi-torsos-all size-24"></i></a>
			</li>
			<li>
				<a href="#" title="<%=registry_Menubar.getStringOfLanguage("menu.log", language_Menubar) %>" data-options="align:right" data-dropdown="mobile-menu-logs"><i class="fi-comments size-24"></i></a>
				<ul id="mobile-menu-logs" data-dropdown-content class="f-dropdown">
					<li><a href="<%=basePath_Menubar %>log/access.html"><%=registry_Menubar.getStringOfLanguage("menu.log.access", language_Menubar) %></a></li>
					<li><a href="<%=basePath_Menubar %>log/channel.html"><%=registry_Menubar.getStringOfLanguage("menu.log.channel", language_Menubar) %></a></li>
					<li><a href="<%=basePath_Menubar %>log/content.html"><%=registry_Menubar.getStringOfLanguage("menu.log.content", language_Menubar) %></a></li>
					<li><a href="<%=basePath_Menubar %>log/advanced.html"><%=registry_Menubar.getStringOfLanguage("menu.log.advanced", language_Menubar) %></a></li>
				</ul>
			</li>
			
			
		</ul>
	</div>
</div>
