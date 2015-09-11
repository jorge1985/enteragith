<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<select id="inmueble_3_2" name="inmueble_3_2" size="1">
	<option value="">Selecciona</option>
	<option value="all">- TODOS -</option>
<c:forEach var="item" items="${city_list }">
	<option value="${item.id}">${item.name }</option>
</c:forEach>
</select>