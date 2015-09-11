<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<select id="varios_2" name="varios_2">
	<option value="">Selecciona</option>
	<option value="all">- TODOS -</option>
	<option value="1" >Animales y Mascotas</option>
	<option value="2" >Antigüedades</option>
	<option value="3" >Arte, Libros y Colecciones</option>
	<option value="4" >Artículos Deportivos</option>
	<option value="5" >Artículos para el Hogar</option>
	<option value="6" >Cómputo</option>
	<option value="7" >Electrónica y Telefonía</option>
	<option value="8" >Joyeria</option>
	<option value="9" >Máquinas y Herramientas </option>
	<option value="10">Muebles</option>
	<option value="11" >Video Juegos</option>
	<option value="12" >Otros</option>
	
	
<%-- <c:forEach var="item" items="${varios_list }"> --%>
<%-- 	<option value="${item.code}">${item.value }</option> --%>
<%-- </c:forEach> --%>
</select>