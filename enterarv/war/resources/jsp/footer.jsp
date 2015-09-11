<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String basePath_Footer = request.getContextPath()+"/";
	String resPath_Footer = basePath_Footer + "resources/";
%>

<form id="form_move_search" method="post" action="<%=basePath_Footer %>system/search.html">
	<input type="hidden" name="search" id="edit_search_id" value="" />
</form>

<form id="form_move_home" method="post" action="<%=basePath_Footer %>public/home.html" target="_blank">
</form>

	<script type="text/javascript" src="<%=resPath_Footer%>js/footer.js"></script>
</body>
</html>
