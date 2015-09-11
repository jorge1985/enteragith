<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<%
	String basePath_footer = request.getContextPath()+"/";
	String resPath_footer =  basePath_footer + "resources/";
%>

	<form id="frm_move_search" method="post" action="<%=basePath_footer%>public/channel.html">
		<input type="hidden" name="kind" value="search">
	</form>
	
<script type="text/javascript">
	function goto_search(){
		$("#frm_move_search").submit();
	}
</script>
	
</body>
</html>
