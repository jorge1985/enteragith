<%@page import="com.youandbbva.enteratv.beans.SessionHandler"%>
<%@page import="com.youandbbva.enteratv.Registry"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
	Registry registry_form_automovil = Registry.getInstance();
	SessionHandler handler_form_automovil = SessionHandler.getInstance();
	String language_form_automovil = handler_form_automovil.getLanguage(session);
%>
		
<div class="row" id="publish_kind001">
<form id="car_form" method="post" action="#" data-abide>
	<div class="row">
		<div class="large-4 medium-4 columns">
			<label><%=registry_form_automovil.getStringOfLanguage("opport.car.brand", language_form_automovil) %> 
			<select id="car_brand" data-invalid="" aria-invalid="true" required="required">
			<option value="" selected>Selecciona</option>
			<c:forEach var="item" items="${brand_list }">
				<option value="${item.id}">${item.name }</option>
			</c:forEach>
			</select>
			</label>
			<small class="error"><%=registry_form_automovil.getStringOfLanguage("validate.value", language_form_automovil) %></small>
		</div>
		<div class="large-4 medium-4 columns">
			<label><%=registry_form_automovil.getStringOfLanguage("opport.car.model", language_form_automovil) %> 
			<input id="car_model" type="text" placeholder="" required="required" maxlength="200" value="" />
			</label>
			<small class="error"><%=registry_form_automovil.getStringOfLanguage("validate.value", language_form_automovil) %></small>
		</div>
		<div class="large-4 medium-4 columns">
			<label><%=registry_form_automovil.getStringOfLanguage("opport.car.price", language_form_automovil) %> 
			<input id="car_price" type="number" placeholder=""  min="0"  step="any" required="required" maxlength="15" value="" />
			</label>
			<small class="error"><%=registry_form_automovil.getStringOfLanguage("validate.value", language_form_automovil) %></small>
		</div>
	</div>
	
	<div class="row">
		<div class="large-6 medium-6 columns">
			<label><%=registry_form_automovil.getStringOfLanguage("opport.car.state", language_form_automovil) %> 
			<select id="car_state">
			<option value="" selected>Selecciona</option>
			<c:forEach var="item" items="${state_list }">
				<option value="${item.id}">${item.name }</option>
			</c:forEach>
			</select>
			</label>
			<small class="error"><%=registry_form_automovil.getStringOfLanguage("validate.value", language_form_automovil) %></small>
		</div>
		<div class="large-6 medium-6 columns">
			<label><%=registry_form_automovil.getStringOfLanguage("opport.car.city", language_form_automovil) %>
			<select id="car_city">
			<option value="" selected>Selecciona</option>
			</select>
			</label>
			<small class="error"><%=registry_form_automovil.getStringOfLanguage("validate.value", language_form_automovil) %></small>
		</div>
	</div>
	
	<div class="row">
		<div class="large-4 medium-4 columns">
			<label><%=registry_form_automovil.getStringOfLanguage("opport.car.anio", language_form_automovil) %>
			<select id="car_today" required="required" data-invalid="" aria-invalid="true">
			<option value="" selected>Selecciona</option>
			</select> 
			<small class="error"><%=registry_form_automovil.getStringOfLanguage("validate.value", language_form_automovil) %></small>
		</div>
		<div class="large-4 medium-4 columns">
			<label><%=registry_form_automovil.getStringOfLanguage("opport.car.transmision", language_form_automovil) %> 
			<select id="car_transmission" data-invalid="" aria-invalid="true" required="required">
			<option value="" selected>Selecciona</option>
			<option value="2" >ESTANDAR</option>
			<option value="3" >AUTOMATIC</option>
<%-- 			<c:forEach var="item" items="${transmission_list }"> --%>
<%-- 				<option value="${item.code}">${item.value }</option> --%>
<%-- 			</c:forEach> --%>
			</select>
			</label>
			<small class="error"><%=registry_form_automovil.getStringOfLanguage("validate.value", language_form_automovil) %></small>
		</div>
		<div class="large-4 medium-4 columns">
			<label><%=registry_form_automovil.getStringOfLanguage("opport.car.mileage", language_form_automovil) %> 
			<input id="car_mileage" type="text" placeholder=""  value=""  maxlength="20" />
			</label>
			<small class="error"><%=registry_form_automovil.getStringOfLanguage("validate.value", language_form_automovil) %></small>
		</div>
	</div>
<%-- 
	<div class="row">
		<div class="large-12 medium-12 columns">
			<label><%=registry_form_automovil.getStringOfLanguage("opport.car.employee_num", language_form_automovil) %> 
			<input id="car_employee" type="text" placeholder="" value="000111" maxlength="30" />
			</label>
			<small class="error"><%=registry_form_automovil.getStringOfLanguage("validate.value", language_form_automovil) %></small>
		</div>
	</div>
 --%>
	<div class="row">
		<div class="large-6 medium-6 columns">
			<label><%=registry_form_automovil.getStringOfLanguage("opport.car.color", language_form_automovil) %> 
			<input id="car_color" type="text" placeholder="" value="" maxlength="50" />
			</label>
			<small class="error"><%=registry_form_automovil.getStringOfLanguage("validate.value", language_form_automovil) %></small>
		</div>
		<div class="large-6 medium-6 columns">
			<label><%=registry_form_automovil.getStringOfLanguage("opport.car.door", language_form_automovil) %> 
			<select id="car_door" data-invalid="" aria-invalid="true" required="required">
				<option value="" selected>Selecciona</option>
				<c:choose>
				<c:when test="${doors=='2' }">
					<option selected value="2">2</option>
				</c:when>
				<c:otherwise>
					<option value="2">2</option>
				</c:otherwise>
				</c:choose>
				
				<c:choose>
				<c:when test="${doors=='3' }">
					<option selected value="3">3</option>
				</c:when>
				<c:otherwise>
					<option value="3">3</option>
				</c:otherwise>
				</c:choose>
				
				<c:choose>
				<c:when test="${doors=='4' }">
					<option selected value="4">4</option>
				</c:when>
				<c:otherwise>
					<option value="4">4</option>
				</c:otherwise>
				</c:choose>
				
				<c:choose>
				<c:when test="${doors=='5' }">
					<option selected value="5">5</option>
				</c:when>
				<c:otherwise>
					<option value="5">5</option>
				</c:otherwise>
				</c:choose>
				
			</select>
			</label>
			<small class="error"><%=registry_form_automovil.getStringOfLanguage("validate.value", language_form_automovil) %></small>
		</div>
	</div>
	
	<div class="row">
		<div class="large-2 medium-2 columns">
			<a href="javascript:selectImage()" class="button tiny" style="background-color: #999;"><%=registry_form_automovil.getStringOfLanguage("opp.select_image", language_form_automovil) %></a>
		</div>
		<div class="large-4 medium-4 columns">
			<img src="" path="${file }" id="car_file">
		</div>
		<div class="large-4 medium-4 columns">
		</div>
	</div>

	<div class="row">
		<div class="large-12 medium-12 columns">
			<label><%=registry_form_automovil.getStringOfLanguage("opport.car.obs", language_form_automovil) %>
			<textarea rows="" cols="" id="car_obs">
			</textarea> 
			</label>
			<small class="error"><%=registry_form_automovil.getStringOfLanguage("validate.value", language_form_automovil) %></small>
		</div>
	</div>

	<div class="row">
		<div class="large-6 medium-6 columns">
			<label><%=registry_form_automovil.getStringOfLanguage("opport.car.telephone", language_form_automovil) %>
			<input id="car_telephone" type="text" placeholder="" value="" maxlength="50" />
			</label>
			<small class="error"><%=registry_form_automovil.getStringOfLanguage("validate.value", language_form_automovil) %></small>
		</div>
		<div class="large-6 medium-6 columns">
			<label><%=registry_form_automovil.getStringOfLanguage("opport.car.mobilephone", language_form_automovil) %>
			<input id="car_mobilephone" type="text" placeholder="" value=""  maxlength="50" />
			</label>
			<small class="error"><%=registry_form_automovil.getStringOfLanguage("validate.value", language_form_automovil) %></small>
		</div>
	</div>
	
	<!-- <button type="button" class="medium" id="btnResetCar"><%=registry_form_automovil.getStringOfLanguage("btn.reset", language_form_automovil) %></button> -->
	<button type="submit" class="medium" id="btnSubmitCar" ><%=registry_form_automovil.getStringOfLanguage("btn.submit", language_form_automovil) %></button>
</form>
</div>

<script type="text/javascript">
	$(document).foundation();
</script>