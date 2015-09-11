<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="../variable.jsp"%>
<%@include file="jsp/public_header.jsp"%>


<div class="row" id="mobile-menu">
	<div class="small-16 columns show-for-small-only">

		<%@include file="jsp/public_sidebar_mobile.jsp"%>
		
		<div class="small-13 columns menu">
			<a href="#" class="mm-button"><h2><%=registry.getStringOfLanguage("public.menu", language) %></h2> <i class="fa fa-angle-down fa-lg"></i></a>

			<nav class="main-menu small-13 columns">
				<ul>
 				<c:forEach var="item" items="${family }">
					<li>
						<div>
							<a href="#" class="family_item">${item.name }</a>
							<a class="mobile_submenu" href="#"><i class="fa fa-angle-right fa-fw"></i></a>
						</div>
						<ul id="category_mobile_${item.id }" class="public_menu" pos="${item.id }">
						</ul>
					</li>					
				</c:forEach>
				</ul>
			</nav>
		</div>
		
	</div>
</div>

<div class="row">
	<%@include file="jsp/public_topbar.jsp"%>

	<section class=" large-15 medium-15 small-16 columns" id="content-wrapper">
		<div class="row">
			<div class="large-11 medium-11 small-16 columns">
				<div id="content">
		          <h3 style="text-align:center;font-size:80px">404</h3>
		          <h3 style="text-align:center">Page not Found</h3>
				</div>
			</div>
			
			<aside class="large-5 medium-5 small-16 columns">
				<div id="temas">
					<div class="temas-title"><%=registry.getStringOfLanguage("public.latest_channel", language) %> <i class="fa fa-angle-down fa-lg"></i></div>
					
					<c:forEach var="item" items="${most_visited_channel }">
						<div class="tema">
							<a href="javascript:channel('${item.id }')">${item.no }. ${item.name }</a>
						</div>
					</c:forEach>
				</div>
				
				<div class="spacer"></div>
				
				<div id="side-banners">
					${banner_sidebar }
				</div>
				
				<div class="spacer"></div>
				
			</aside>
		</div>
		
		<!-- Banner Bottom -->
		${banner_bottom }
	</section>
</div>

<input type="hidden" id="resPath" value="<%=resPath %>">

<div id="menu-modal" class="reveal-modal xlarge" data-reveal>
	<div class="row">
		<div class="menucol">
			<h4 class="menu-title"><%=registry.getStringOfLanguage("public.menu_dialog", language) %></h4>
			<ul></ul>
		</div>
	</div>
</div>

<form id="form_move_channel" method="post" action="<%=basePath %>public/channel.html">
	<input type="hidden" name="channel_id" id="edit_channel_id" value="" />
</form>

<form id="form_move_home" method="post" action="<%=basePath %>public/home.html">
</form>

<!--[if lt IE 9]>
      <script src="<%=resPath%>bower_components/REM-unit-polyfill/js/rem.min.js"></script>
      <script src="<%=resPath%>bower_components/jquery-legacy/jquery.min.js"></script>
<![endif]-->

<!--[if !IE]> -->
<script src="<%=resPath%>bower_components/jquery/dist/jquery.min.js"></script>
<!-- <![endif]-->

	<script src="<%=resPath%>bower_components/velocity/velocity.min.js"></script>
	<script src="<%=resPath%>bower_components/foundation/js/foundation.min.js"></script>
	<script src="<%=resPath%>bower_components/jquery-ui/jquery-ui.min.js"></script>
	<script src="<%=resPath%>js/error.js"></script>

<%@include file="jsp/public_footer.jsp"%>