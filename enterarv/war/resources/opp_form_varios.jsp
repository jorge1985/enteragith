<%@page import="com.youandbbva.enteratv.beans.SessionHandler"%>
<%@page import="com.youandbbva.enteratv.Registry"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
	Registry registry_form_varios = Registry.getInstance();
	SessionHandler handler_form_varios = SessionHandler.getInstance();
	String language_form_varios = handler_form_varios.getLanguage(session);
%>
		
<div class="row" id="publish_kind003">
<form id="srv_form" method="post" action="#" data-abide>
	<div class="row">
			<div class="large-4 medium-4 columns">
			<label><%=registry_form_varios.getStringOfLanguage("opport.srv.varios", language_form_varios) %> 
			<select id="srv_varios" data-invalid="" aria-invalid="true" required="required">
			<option value="" selected>Selecciona</option>


			<c:forEach var="item" items="${varios_list }">
				<c:choose>
				<c:when test="${varios==item.code }">
					<option value="${item.code}">${item.value }</option>
				</c:when>
				<c:otherwise>
					<option value="${item.code}">${item.value }</option>
				</c:otherwise>
				</c:choose>
			</c:forEach>
			</select>
			</label>
			<small class="error"><%=registry_form_varios.getStringOfLanguage("validate.value", language_form_varios) %></small>
		</div>
		<div class="large-4 medium-4 columns">
			<label><%=registry_form_varios.getStringOfLanguage("opport.srv.article", language_form_varios) %> 
			<input id="srv_article" type="text" placeholder="" required="required" maxlength="200" value="${model }" />
			</label>
			<small class="error"><%=registry_form_varios.getStringOfLanguage("validate.value", language_form_varios) %></small>
		</div>
		<div class="large-4 medium-4 columns">
			<label><%=registry_form_varios.getStringOfLanguage("opport.srv.price", language_form_varios) %> 
			<input id="srv_price" type="number" placeholder=""  min="0"  step="any" required="required" maxlength="15" value="${price }" />
			</label>
			<small class="error"><%=registry_form_varios.getStringOfLanguage("validate.value", language_form_varios) %></small>
		</div>
	</div>
	
	<div class="row">
		<div class="large-6 medium-6 columns">
			<label><%=registry_form_varios.getStringOfLanguage("opport.srv.state", language_form_varios) %> 
			<select id="srv_state">
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
			</select>
			</label>
			<small class="error"><%=registry_form_varios.getStringOfLanguage("validate.value", language_form_varios) %></small>
		</div>
		<div class="large-6 medium-6 columns">
			<label><%=registry_form_varios.getStringOfLanguage("opport.srv.city", language_form_varios) %>
			<select id="srv_city">
			<option value="" selected>Selecciona</option>
			</select>
			</label>
			<small class="error"><%=registry_form_varios.getStringOfLanguage("validate.value", language_form_varios) %></small>
		</div>
	</div>
<%-- 
	<div class="row">
		<div class="large-12 medium-12 columns">
			<label><%=registry_form_varios.getStringOfLanguage("opport.srv.employee_num", language_form_varios) %> 
			<input id="srv_employee" type="text" placeholder="" value="000111" maxlength="30" />
			</label>
			<small class="error"><%=registry_form_varios.getStringOfLanguage("validate.value", language_form_varios) %></small>
		</div>
	</div>
 --%>
	<div class="row">
		<div class="large-12 medium-12 columns">
			<label><%=registry_form_varios.getStringOfLanguage("opport.srv.desc", language_form_varios) %>
			<textarea rows="" cols="" id="srv_desc">
			</textarea> 
			</label>
			<small class="error"><%=registry_form_varios.getStringOfLanguage("validate.value", language_form_varios) %></small>
		</div>
	</div>

	<div class="row">
		<div class="large-2 medium-2 columns">
			<a href="javascript:selectImage()" class="button tiny" style="background-color: #999;"><%=registry_form_varios.getStringOfLanguage("opp.select_image", language_form_varios) %></a>
		</div>
		<div class="large-4 medium-4 columns">
			<img src="" path="${file }" id="srv_file">
		</div>
		<div class="large-4 medium-4 columns">
		</div>
	</div>

	<div class="row">
		<div class="large-6 medium-6 columns">
			<label><%=registry_form_varios.getStringOfLanguage("opport.srv.telephone", language_form_varios) %> 
			<input id="srv_telephone" type="text" placeholder="" value="${telephone }" maxlength="50" />
			</label>
			<small class="error"><%=registry_form_varios.getStringOfLanguage("validate.value", language_form_varios) %></small>
		</div>
		<div class="large-6 medium-6 columns">
			<label><%=registry_form_varios.getStringOfLanguage("opport.srv.mobilephone", language_form_varios) %> 
			<input id="srv_mobilephone" type="text" placeholder="" value="${mobilephone }"  maxlength="50" />
			</label>
			<small class="error"><%=registry_form_varios.getStringOfLanguage("validate.value", language_form_varios) %></small>
		</div>
	</div>

	<!-- <button type="button" class="medium" id="btnResetSrv"><%=registry_form_varios.getStringOfLanguage("btn.reset", language_form_varios) %></button> -->
	<button type="submit" class="medium" id="btnSubmitSrv" ><%=registry_form_varios.getStringOfLanguage("btn.submit", language_form_varios) %></button>
</form>			
</div>

<script type="text/javascript">
	$(document).foundation();
</script>