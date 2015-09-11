<%@page import="com.youandbbva.enteratv.Registry"%>
<%@page import="com.youandbbva.enteratv.beans.SessionHandler"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String basePath_detail = request.getContextPath()+"/" ;
	String resPath_detail = basePath_detail + "resources/";
	Registry reg_detail = Registry.getInstance();
	SessionHandler handler_detail = SessionHandler.getInstance();
	String language_detail = handler_detail.getLanguage(session);
%>

<div class="row">
	<div class="large-5 columns">
		<img src="<%=basePath_detail%>serve?blob-key=${file}">
	</div>
	
	<div class="large-7 columns" id="details-wrapper">
		<img src="<%=resPath_detail %>images/logo-oportunidades.svg" class="logo" alt=""><br>
		<small class="date">${public_date }</small> <br>
		<ul class="details">

		<c:if test="${type=='001' }">
			<li>${brand_name } ${today } </li>
			<li>${model } </li>
			<li>$ ${price }</li>
			
			<c:if test="${mileage!='' }">
			<li><%=reg_detail.getStringOfLanguage("opport.car.mileage", language_detail) %>: ${mileage }</li>
			</c:if>
			
			<c:if test="${transmission_name!='' }">
			<li><%=reg_detail.getStringOfLanguage("opport.car.transmision", language_detail) %>: ${transmission_name }</li>
			</c:if>

			<c:if test="${color!='' }">
			<li><%=reg_detail.getStringOfLanguage("opport.car.color", language_detail) %>: ${color }</li>
			</c:if>
			
			<c:if test="${doors!='' }">
			<li><%=reg_detail.getStringOfLanguage("opport.car.door", language_detail) %>: ${doors }</li>
			</c:if>
			
			<c:if test="${obs!='' }">
			<li><%=reg_detail.getStringOfLanguage("opport.car.obs", language_detail) %>: ${obs }</li>
			</c:if>
			
			<li><%=reg_detail.getStringOfLanguage("opport.car.location", language_detail) %>: ${location }</li>
		</c:if>

		<c:if test="${type=='002' }">
			<li>${serve_type }</li>
			<li>${property_name}, ${model}</li>
			<li>$ ${price }</li>
			<li><%=reg_detail.getStringOfLanguage("opport.fur.meter", language_detail) %>: ${mileage }</li>
			<li><%=reg_detail.getStringOfLanguage("opport.fur.rooms", language_detail) %>: ${rooms }</li>
			<li><%=reg_detail.getStringOfLanguage("opport.fur.plants", language_detail) %>: ${plants }</li>
			<li><%=reg_detail.getStringOfLanguage("opport.fur.amueblado", language_detail) %>: ${amueblado }</li>
			
			<li><%=reg_detail.getStringOfLanguage("opport.fur.location", language_detail) %>: ${location }</li>
		</c:if>

		<c:if test="${type=='003' }">
			<li>${varios_name} / ${model }</li>
			
			<c:if test="${obs!='' }">
			<li><%=reg_detail.getStringOfLanguage("opport.srv.desc", language_detail) %>: ${obs }</li>
			</c:if>

			<li><%=reg_detail.getStringOfLanguage("opport.srv.location", language_detail) %>: ${location }</li>
		</c:if>

			<c:if test="${telephone!='' }">
			<li><%=reg_detail.getStringOfLanguage("opport.fur.telephone", language_detail) %>: ${telephone }</li>
			</c:if>
			<c:if test="${mobilephone!='' }">
			<li><%=reg_detail.getStringOfLanguage("opport.fur.mobilephone", language_detail) %>: ${mobilephone }</li>
			</c:if>
		</ul>
	</div>
</div>

<a class="close-reveal-modal">&#215;</a>
