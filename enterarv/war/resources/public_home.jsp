<%@page import="com.youandbbva.enteratv.Constants"%>
<%@page import="org.apache.commons.codec.digest.DigestUtils"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="../variable.jsp"%>
<%@include file="jsp/public_header.jsp"%>


<%
	UserInfo user_body = handler.getFrontUserInfo(session);
	int userid_body;
	String sessionid_body = "";
	if (user_body != null && user_body.getUserId() != 0) {
		userid_body = user_body.getUserId();
		sessionid_body = DigestUtils.md5Hex(userid_body
				+ Constants.SESSION_HASH);
	}
%>
<div class="row" id="mobile-menu">
	<div class="small-16 columns show-for-small-only">

		<%@include file="jsp/public_sidebar_mobile.jsp"%>

		<div class="small-13 columns menu">
			<a href="#" class="mm-button"><h2><%=registry.getStringOfLanguage("public.menu", language)%></h2>
				<i class="fa fa-angle-down fa-lg"></i></a>

			<nav class="main-menu small-13 columns">
				<ul>
					<c:forEach var="item" items="${family }">
						<li><a class="have-submenu" href="#">${item.name }<i
								class="fa fa-angle-right fa-fw"></i></a>
							<ul id="category_mobile_${item.id }" class="public_menu"
								pos="${item.id }">
							</ul></li>
					</c:forEach>
				</ul>
			</nav>
		</div>
	</div>
</div>

<div class="row">
	<%@include file="jsp/public_topbar.jsp"%>

	<section class=" large-15 medium-15 small-20 columns"
		id="content-wrapper">
		<div class="row" data-equalizer>
			<div class="large-8 medium-8 small-16 columns video-slider-wrapper"
				data-equalizer-watch>
				<div class="panel slider">
					<div class="row">
						<div class="large-15 small-centered columns">
							<div class="slider-for">
								<%
									String video1 = (String) request.getAttribute("Video1");	
									String video2 = (String) request.getAttribute("Video2");
									String video3 = (String) request.getAttribute("Video3");
									String video4 = (String) request.getAttribute("Video4");
									String video5 = (String) request.getAttribute("Video5");
									
									boolean navegador = (Boolean) request.getAttribute("navegador");
									
									int video_index = 0;
									
									String agent = Utils.checkNull(request.getHeader("User-Agent"));
									
									if (Utils.isMobile(agent)) {
								%>
								${slider_for }
								<%
									} 
									else if( navegador )
									{
								%>
								
												<%=video1%>
												
 								
								<%
									} 
									else 
									{
								%>
								
								<c:forEach var="item" items="${latest_video}">
 									<div> 
 										<h3> 
 											<div id="content_video_<%=video_index%>"> 
 												<img src="http://placehold.it/530x255&text=Slide+5">
												
 											</div> 
 										</h3> 
 									</div> 
 									<% 
										video_index++;
 									%> 
 								</c:forEach> 
								<%
									} 
								%>
							</div>
							<% if (!navegador) { %>
							<div class="slider-nav">${slider_nav }</div>
							<%} %>
						</div>
					</div>
				</div>
			</div>

			<div class="large-4 medium-4 small-16 columns" id="temas"
				data-equalizer-watch>
				<div class="temas-title"><%=registry.getStringOfLanguage("public.latest_channel",
					language)%>
					<i class="fa fa-angle-down fa-lg"></i>
				</div>

				<c:forEach var="item" items="${most_visited_channel }">
					<div class="tema">
						<a href="javascript:channel('${item.id }')">${item.no }.
							${item.name }</a>
					</div>
				</c:forEach>
			</div>

			<div class="large-4 medium-4 small-16 columns" id="home-side-banners"
				data-equalizer-watch>${banner_sidebar }</div>
		</div>

		${banner_bottom }
	</section>
</div>

<input type="hidden" id="resPath" value="<%=resPath%>">

<div id="menu-modal" class="reveal-modal xlarge" data-reveal>
	<div class="row">
		<div class="menucol">
			<h4 class="menu-title"><%=registry.getStringOfLanguage("public.menu_dialog",
					language)%></h4>
			<ul></ul>
		</div>
		<div class="menucol">
			<ul></ul>
		</div>
		<div class="menucol">
			<ul></ul>
		</div>
	</div>
</div>

<input type="hidden" id="basePath" value="<%=basePath%>">
<div id="search-modal" class="reveal-modal medium" data-reveal></div>


<%
	video_index = 0;
	if (!Utils.isMobile(agent)) {
%>
<c:forEach var="item" items="${latest_video }">
	<div class="temp_variable" index="<%=video_index%>">
		<input type="hidden" id="content_video_path_<%=video_index%>"
			value="${item.video }"> <input type="hidden"
			id="content_image_path_<%=video_index%>" value="${item.image }">
	</div>
	<%
		video_index++;
	%>
</c:forEach>
<%
	}
%>

<form id="form_move_content" method="get"
	action="<%=basePath%>public/content.html">
	<input type="hidden" name="content_id" id="edit_content_id" value="" />
	<input type="hidden" name="content_blog" id="edit_content_blog"
		value="" />
</form>

<form id="form_move_channel" method="post"
	action="<%=basePath%>public/channel.html">
	<input type="hidden" name="channel_id" id="edit_channel_id" value="" />
</form>



<%-- <form id="form_move_opport" method="post" action="<%=basePath %>opportunities/home.html">
	<input type="hidden" name="user_id" value="<%=userid_body %>" />
	<input type="hidden" name="session_id" value="<%=sessionid_body %>" />
</form>
 --%>
<!--[if lt IE 9]>
      <script src="<%=resPath%>bower_components/REM-unit-polyfill/js/rem.min.js"></script>
      <script src="<%=resPath%>bower_components/jquery-legacy/jquery.min.js"></script>
<![endif]-->




<!--[if !IE]> -->
<script src="<%=resPath%>bower_components/jquery/dist/jquery.min.js"></script>
<!-- <![endif]-->

<script
	src="<%=resPath%>bower_components/foundation/js/foundation.min.js"></script>
<script
	src="<%=resPath%>bower_components/slick-carousel/slick/slick.min.js"></script>
<script src="<%=resPath%>js/swfobject.js"></script>
<script src="<%=resPath%>js/public_menu.js"></script>
<script src="<%=resPath%>js/public_home.js"></script>

<%@include file="jsp/public_footer.jsp"%>