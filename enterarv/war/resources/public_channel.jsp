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
						<a class="have-submenu" href="#">${item.name }<i class="fa fa-angle-right fa-fw"></i></a>
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
					<h3><%=registry.getStringOfLanguage("public.content.list", language) %></h3>
					<div class="row" style="margin-left: 20px; margin-top: 20px;">
						<table id="content_table" class="display" cellspacing="0" cellpadding="0" width="100%">
						    <thead>
						        <tr>
						            <th width="70%"><%=registry.getStringOfLanguage("public.table.title", language) %></th>
						        	<%-- <th><%=registry.getStringOfLanguage("public.table.type", language) %></th> --%>
						        	<th><%=registry.getStringOfLanguage("public.table.date", language) %></th>
						        </tr>
						    </thead>
						</table>
						
					</div>
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
		<div class="menucol">
			<ul></ul>
		</div>
		<div class="menucol">
			<ul></ul>
		</div>
	</div>
</div>

<input type="hidden" id="basePath" value="<%=basePath %>">
<div id="search-modal" class="reveal-modal medium" data-reveal>
</div>

<form id="form_move_content" method="get" action="<%=basePath %>public/content.html">
	<input type="hidden" name="content_id" id="edit_content_id" value="" />
	<input type="hidden" name="channel_id" id="edit2_channel_id" value="${channel_id }" />
	<input type="hidden" name="content_blog" id="edit_content_blog" value="" />
</form>

<form id="form_move_channel" method="post" action="<%=basePath %>public/channel.html">
	<input type="hidden" name="channel_id" id="edit_channel_id" value="${channel_id }" />
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

<script src="<%=resPath%>bower_components/foundation/js/foundation.min.js"></script>
<script src="<%=resPath%>js/jquery/jquery.dataTables.min.js"></script>
<script src="<%=resPath%>js/foundation/dataTables.foundation.min.js"></script>
	<script src="<%=resPath%>js/foundation/dataTables.responsive.js"></script>
<script src="<%=resPath%>js/public_channel.js"></script>

<%@include file="jsp/public_footer.jsp"%>