<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@page import="com.youandbbva.enteratv.beans.SessionHandler"%>
<%@page import="com.youandbbva.enteratv.Registry"%>

<%
%>

<%
	Registry registry_Search = Registry.getInstance();
	SessionHandler handler_Search = SessionHandler.getInstance();
	String language_Search = handler_Search.getLanguage(session);
	String basePath_Search = request.getContextPath()+"/";
	String resPath_Search = basePath_Search + "resources/";
%>

<div class="row">
	<div class="small-12 columns">
		<input type="text" name="search" id="search_text_p" placeholder="<%=registry_Search.getStringOfLanguage("public.search.placeholder", language_Search) %>">
	</div>
	<div class="small-4 columns">
		<a href="javascript:get_search_result()"><img src="<%=resPath_Search %>images/icons/magnify-glass.svg" alt=""></a>
	</div>
</div>

<div class="row" id="search-results">
	<div class="large-16 columns">
		<h5>
			<%=registry_Search.getStringOfLanguage("public.search.desc", language_Search) %> <span id="lbl_search"></span>
		</h5>
		<div style="display: none" id="search-not-found"><%=registry_Search.getStringOfLanguage("public.search.notfound", language_Search) %></div>
		<ul id="search_result_p">
		</ul>
	</div>
</div>

<div class="row nav">
	<div class="small-8 columns">
		<a id="search_prev_p" class="pagination disabled" href="javascript:prev_search_result()"><i class="fa fa-angle-left fa-lg"></i> <%=registry_Search.getStringOfLanguage("public.search.prev", language_Search) %></a>
	</div>
	<div class="small-8 columns">
		<a id="search_next_p" class="right pagination disabled" href="javascript:next_search_result()"><%=registry_Search.getStringOfLanguage("public.search.next", language_Search) %> <i class="fa fa-angle-right fa-lg"></i></a>
	</div>
</div>

<a class="close-reveal-modal">&#215;</a>

<form id="form_search_move_content" method="get" action="<%=basePath_Search %>public/content.html">
	<input type="hidden" name="content_id" id="edit_search_content_id" value="" />
	<input type="hidden" name="channel_id" id="edit_search_channel_id" value="" />
	<input type="hidden" name="content_blog" id="edit_search_content_blog" value="" />
</form>
<form id="form_search_move_gallery" method="get" action="<%=basePath_Search %>public/galeria.html">
	<input type="hidden" name="content_id" id="edit_search_content_id2" value="" />
	<input type="hidden" name="channel_id" id="edit_search_channel_id2" value="" />
	<input type="hidden" name="content_blog" id="edit_search_content_blog2" value="" />
</form>

<input type="hidden" id="search_page_total" value="0" />
<input type="hidden" id="search_page_current" value="0" />

<script src="<%=resPath_Search%>js/popup_search.js"></script>
