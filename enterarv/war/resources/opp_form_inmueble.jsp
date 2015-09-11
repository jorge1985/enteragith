<%@page import="com.youandbbva.enteratv.beans.SessionHandler"%>
<%@page import="com.youandbbva.enteratv.Registry"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
	Registry registry_form_inmueble = Registry.getInstance();
	SessionHandler handler_form_inmueble = SessionHandler.getInstance();
	String language_form_inmueble = handler_form_inmueble
			.getLanguage(session);
%>

<div class="row" id="publish_kind002">
	<form id="fur_form" method="post" action="#" data-abide>

		<div class="row">
			<div class="large-4 medium-4 columns">
				<label><%=registry_form_inmueble.getStringOfLanguage(
					"opport.fur.type", language_form_inmueble)%>
					<select id="fur_property" data-invalid="" aria-invalid="true"
					required="required">
						<option value="" selected>Selecciona</option>
						<option value="1" >CASA</option>
						<option value="2" >DEPARTAMENTO</option>
						<option value="3" >TERRENO</option>
						<option value="4" >CASA EN CONDOMINIO</option>
<%-- 						<c:forEach var="item" items="${property_list }"> --%>
<%-- 							<c:choose> --%>
<%-- 								<c:when test="${property==item.code }"> --%>
<%-- 									<option value="${item.code}">${item.value }</option> --%>
<%-- 								</c:when> --%>
<%-- 								<c:otherwise> --%>
<%-- 									<option value="${item.code}">${item.value }</option> --%>
<%-- 								</c:otherwise> --%>
<%-- 							</c:choose> --%>
<%-- 						</c:forEach> --%>
				</select> </label> <small class="error"><%=registry_form_inmueble.getStringOfLanguage(
					"validate.value", language_form_inmueble)%></small>
			</div>
			<div class="large-4 medium-4 columns">
				<label><%=registry_form_inmueble.getStringOfLanguage(
					"opport.fur.building", language_form_inmueble)%>
					<input id="fur_building" type="text" placeholder=""
					required="required" maxlength="200" value="${model }" /> </label> <small
					class="error"><%=registry_form_inmueble.getStringOfLanguage(
					"validate.value", language_form_inmueble)%></small>
			</div>
			<div class="large-4 medium-4 columns">
				<label><%=registry_form_inmueble.getStringOfLanguage(
					"opport.fur.price", language_form_inmueble)%>
					<input id="fur_price" type="number" min="0" step="any"
					placeholder="" required="required" maxlength="15" value="${price }" />
				</label> <small class="error"><%=registry_form_inmueble.getStringOfLanguage(
					"validate.value", language_form_inmueble)%></small>
			</div>
		</div>

		<div class="row">
			<div class="large-6 medium-6 columns">
				<label><%=registry_form_inmueble.getStringOfLanguage(
					"opport.fur.state", language_form_inmueble)%>
					<select id="fur_state">
						<option value="" selected>Selecciona</option>
						<c:forEach var="item" items="${state_list }">
							<c:choose>
								<c:when test="${state_id==item.id }">
									<option selected value="${item.id}">${item.name }</option>
								</c:when>
								<c:otherwise>
									<option value="${item.id}">${item.name }</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
				</select> </label> <small class="error"><%=registry_form_inmueble.getStringOfLanguage(
					"validate.value", language_form_inmueble)%></small>
			</div>
			<div class="large-6 medium-6 columns">
				<label><%=registry_form_inmueble.getStringOfLanguage(
					"opport.fur.city", language_form_inmueble)%>
					<select id="fur_city">
						<option value="" selected>Selecciona</option>
				</select> </label> <small class="error"><%=registry_form_inmueble.getStringOfLanguage(
					"validate.value", language_form_inmueble)%></small>
			</div>
		</div>
		 
<!-- 	<div class="row"> -->
<!-- 		<div class="large-12 medium-12 columns"> -->
<%-- 			<label><%=registry_form_inmueble.getStringOfLanguage("opport.fur.employee_num", language_form_inmueble) %>  --%>
<!-- 			<input id="fur_employee" type="text" placeholder="" value="000111" maxlength="30" /> -->
<!-- 			</label> -->
<%-- 			<small class="error"><%=registry_form_inmueble.getStringOfLanguage("validate.value", language_form_inmueble) %></small> --%>
<!-- 		</div> -->
<!-- 	</div> -->

		<div class="row">
			<div class="large-6 medium-6 columns">
				<label><%=registry_form_inmueble.getStringOfLanguage(
					"opport.fur.meter", language_form_inmueble)%>
					<input id="fur_meter" type="text" placeholder=""
					value="${mileage }" maxlength="20" /> </label> <small class="error"><%=registry_form_inmueble.getStringOfLanguage(
					"validate.value", language_form_inmueble)%></small>
			</div>
			<div class="large-6 medium-6 columns">
				<label><%=registry_form_inmueble.getStringOfLanguage(
					"opport.fur.serve_type", language_form_inmueble)%>
					<select id="fur_serve" data-invalid="" aria-invalid="true"
					required="required">
						<option value="" selected>Selecciona</option>
						<c:choose>
							<c:when test="${serve_type=='VENTA' }">
								<option selected value="VENTA">VENTA</option>
							</c:when>
							<c:otherwise>
								<option value="VENTA">VENTA</option>
							</c:otherwise>
						</c:choose>

						<c:choose>
							<c:when test="${serve_type=='RENTA' }">
								<option selected value="RENTA">RENTA</option>
							</c:when>
							<c:otherwise>
								<option value="RENTA">RENTA</option>
							</c:otherwise>
						</c:choose>
				</select> </label> <small class="error"><%=registry_form_inmueble.getStringOfLanguage(
					"validate.value", language_form_inmueble)%></small>
			</div>
		</div>

		<div class="row">
			<div class="large-4 medium-4 columns">
				<label><%=registry_form_inmueble.getStringOfLanguage(
					"opport.fur.amueblado", language_form_inmueble)%>
					<select id="fur_amueblado" data-invalid="" aria-invalid="true"
					required="required">
						<option value="" selected>Selecciona</option>

						<c:choose>
							<c:when test="${amueblado=='SI' }">
								<option selected value="SI">SI</option>
							</c:when>
							<c:otherwise>
								<option value="SI">SI</option>
							</c:otherwise>
						</c:choose>

						<c:choose>
							<c:when test="${amueblado=='NO' }">
								<option selected value="NO">NO</option>
							</c:when>
							<c:otherwise>
								<option value="NO">NO</option>
							</c:otherwise>
						</c:choose>
				</select> </label> <small class="error"><%=registry_form_inmueble.getStringOfLanguage(
					"validate.value", language_form_inmueble)%></small>
			</div>
			<div class="large-4 medium-4 columns">
				<label><%=registry_form_inmueble.getStringOfLanguage(
					"opport.fur.plants", language_form_inmueble)%>
					<input id="fur_plants" type="text" placeholder=""
					value="${plants }" required="required" maxlength="10" /> </label> <small
					class="error"><%=registry_form_inmueble.getStringOfLanguage(
					"validate.value", language_form_inmueble)%></small>
			</div>
			<div class="large-4 medium-4 columns">
				<label><%=registry_form_inmueble.getStringOfLanguage(
					"opport.fur.rooms", language_form_inmueble)%>
					<input id="fur_rooms" type="text" placeholder="" value="${rooms }"
					required="required" maxlength="10" /> </label> <small class="error"><%=registry_form_inmueble.getStringOfLanguage(
					"validate.value", language_form_inmueble)%></small>
			</div>
		</div>

		<div class="row">
			<div class="large-2 medium-2 columns">
				<a href="javascript:selectImage()" class="button tiny"
					style="background-color: #999;"><%=registry_form_inmueble.getStringOfLanguage(
					"opp.select_image", language_form_inmueble)%></a> 
			</div>
			<div class="large-4 medium-4 columns">
				<img src="" path="${file }" id="fur_file">
			</div>
			<div class="large-4 medium-4 columns"></div>
		</div>

		<div class="row">
			<div class="large-6 medium-6 columns">
				<label><%=registry_form_inmueble.getStringOfLanguage(
					"opport.fur.telephone", language_form_inmueble)%>
					<input id="fur_telephone" type="text" placeholder=""
					value="${telephone }" maxlength="50" /> </label> <small class="error"><%=registry_form_inmueble.getStringOfLanguage(
					"validate.value", language_form_inmueble)%></small>
			</div>
			<div class="large-6 medium-6 columns">
				<label><%=registry_form_inmueble.getStringOfLanguage(
					"opport.fur.mobilephone", language_form_inmueble)%>
					<input id="fur_mobilephone" type="text" placeholder=""
					value="${mobilephone }" maxlength="50" /> </label> <small class="error"><%=registry_form_inmueble.getStringOfLanguage(
					"validate.value", language_form_inmueble)%></small>
			</div>
		</div>

		<!-- <button type="button" class="medium" id="btnResetFur"><%=registry_form_inmueble.getStringOfLanguage("btn.reset",
					language_form_inmueble)%></button> -->
		<button type="submit" class="medium" id="btnSubmitFur"><%=registry_form_inmueble.getStringOfLanguage("btn.submit",
					language_form_inmueble)%></button>

	</form>
</div>

<script type="text/javascript">
	$(document).foundation();
</script>