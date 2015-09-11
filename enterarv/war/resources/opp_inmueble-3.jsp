<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
	String resPath_inmueble_3 = request.getContextPath()+"/" + "resources/";
%>

<select id="inmueble_3" name="inmueble_3" size="1">
<option value="">Selecciona</option>
<option value="all">- TODOS -</option>
<c:forEach var="item" items="${state_list }">
	<option value="${item.id}">${item.name }</option>
</c:forEach>
</select>

<img src="<%=resPath_inmueble_3 %>images/preloader.gif" class="preloader-image-inside" alt="" style="display: none">
<div class="loader_inside"></div>