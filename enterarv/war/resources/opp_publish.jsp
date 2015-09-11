<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String basepath = request.getContextPath();
%>
<%@include file="../variable.jsp"%>
<%@include file="jsp/opp_header.jsp"%>

<%@include file="jsp/opp_topbar.jsp"%>

<div class="row" id="content-publish">
	<div class="large-12 columns">
		<h1><%=registry.getStringOfLanguage("opport.title.publish", language) %></h1>
		
<div id="msg_alert" class="row">
	<img id="msg_alert_progress" src="<%=resPath %>images/progress.gif">
	<span>Message</span>
</div>
		
		<div class="row publish" id="publish_kind001">
		<form id="car_form" method="post" action="#" data-abide>

			<div class="row">
				<div class="large-4 medium-4 columns">
					<label><%=registry.getStringOfLanguage("opport.car.brand", language) %> 
					<select id="car_brand" data-invalid="" aria-invalid="true" required="required">
					<option value="">Selecciona</option>
					<c:forEach var="item" items="${brand_list }">
						<c:choose>
						<c:when test="${brand_id==item.id }">
							<option selected value="${item.id}">${item.name }</option>
						</c:when>
						<c:otherwise>
							<option value="${item.id}">${item.name }</option>
						</c:otherwise>
						</c:choose>
					</c:forEach>
					</select>
					</label>
					<small class="error"><%=registry.getStringOfLanguage("validate.value", language) %></small>
				</div>
				<div class="large-4 medium-4 columns">
					<label><%=registry.getStringOfLanguage("opport.car.model", language) %> 
					<input id="car_model" type="text" placeholder="" required="required" maxlength="200" value="${model }" />
					</label>
					<small class="error"><%=registry.getStringOfLanguage("validate.value", language) %></small>
				</div>
				<div class="large-4 medium-4 columns">
					<label><%=registry.getStringOfLanguage("opport.car.price", language) %> 
					<input id="car_price" type="number" placeholder="" min="0"  step="any" required="required" maxlength="15" value="${price }" />
					</label>
					<small class="error"><%=registry.getStringOfLanguage("validate.value", language) %></small>
				</div>
			</div>
			
			<div class="row">
				<div class="large-6 medium-6 columns">
					<label><%=registry.getStringOfLanguage("opport.car.state", language) %> 
					<select id="car_state">
					<option value="">Selecciona</option>
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
					<small class="error"><%=registry.getStringOfLanguage("validate.value", language) %></small>
				</div>
				<div class="large-6 medium-6 columns">
					<label><%=registry.getStringOfLanguage("opport.car.city", language) %>
					<select id="car_city">
					<option value="">Selecciona</option>
					</select>
					</label>
					<small class="error"><%=registry.getStringOfLanguage("validate.value", language) %></small>
				</div>
			</div>
			
			<div class="row">
				<div class="large-4 medium-4 columns">
					<label><%=registry.getStringOfLanguage("opport.car.anio", language) %> 
					<select id="car_today" required="required" data-invalid="" aria-invalid="true">
					<option value="">Selecciona</option>
					</select> 
					</label>
					<small class="error"><%=registry.getStringOfLanguage("validate.value", language) %></small>
				</div>
				<div class="large-4 medium-4 columns">
					<label><%=registry.getStringOfLanguage("opport.car.transmision", language) %> 
					<select id="car_transmission" data-invalid="" aria-invalid="true" required="required">
					<option value="">Selecciona</option>

					
					<c:forEach var="item" items="${transmission_list }">
						<c:choose>
						<c:when test="${transmission==item.code }">
							<option value="${item.code}" selected>${item.value }</option>
						</c:when>
						<c:otherwise>
							<option value="${item.code}">${item.value }</option>
						</c:otherwise>
						</c:choose>
					</c:forEach>
					</select>
					</label>
					<small class="error"><%=registry.getStringOfLanguage("validate.value", language) %></small>
				</div>
				<div class="large-4 medium-4 columns">
					<label><%=registry.getStringOfLanguage("opport.car.mileage", language) %> 
					<input id="car_mileage" type="text" placeholder=""  value="${mileage }"  maxlength="20" />
					</label>
					<small class="error"><%=registry.getStringOfLanguage("validate.value", language) %></small>
				</div>
			</div>
<%-- 
			<div class="row">
				<div class="large-12 medium-12 columns">
					<label><%=registry.getStringOfLanguage("opport.car.employee_num", language) %> 
					<input id="car_employee" type="text" placeholder="" value="${employee_num }" maxlength="30" />
					</label>
					<small class="error"><%=registry.getStringOfLanguage("validate.value", language) %></small>
				</div>
			</div>
 --%>
			<div class="row">
				<div class="large-6 medium-6 columns">
					<label><%=registry.getStringOfLanguage("opport.car.color", language) %> 
					<input id="car_color" type="text" placeholder="" value="${color }" maxlength="50" />
					</label>
					<small class="error"><%=registry.getStringOfLanguage("validate.value", language) %></small>
				</div>
				<div class="large-6 medium-6 columns">
					<label><%=registry.getStringOfLanguage("opport.car.door", language) %> 
					<select id="car_door" data-invalid="" aria-invalid="true" required="required">
						<option value="">Selecciona</option>
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
					<small class="error"><%=registry.getStringOfLanguage("validate.value", language) %></small>
				</div>
			</div>
			
			<div class="row">
				<div class="large-2 medium-2 columns">
					<a href="javascript:selectImage()" class="button tiny" style="background-color: #999;"><%=registry.getStringOfLanguage("opp.select_image", language) %></a>
				</div>
				<div class="large-4 medium-4 columns">
					<img src="" path="${file }" id="car_file">
				</div>
				<div class="large-4 medium-4 columns">
				</div>
			</div>

			<div class="row">
				<div class="large-12 medium-12 columns">
					<label><%=registry.getStringOfLanguage("opport.car.obs", language) %>
					<textarea rows="" cols="" id="car_obs">
					</textarea> 
					</label>
					<small class="error"><%=registry.getStringOfLanguage("validate.value", language) %></small>
				</div>
			</div>

			<div class="row">
				<div class="large-6 medium-6 columns">
					<label><%=registry.getStringOfLanguage("opport.car.telephone", language) %> 
					<input id="car_telephone" type="text" placeholder="" value="${telephone }" maxlength="50" />
					</label>
					<small class="error"><%=registry.getStringOfLanguage("validate.value", language) %></small>
				</div>
				<div class="large-6 medium-6 columns">
					<label><%=registry.getStringOfLanguage("opport.car.mobilephone", language) %> 
					<input id="car_mobilephone" type="text" placeholder="" value="${mobilephone }"  maxlength="50" />
					</label>
					<small class="error"><%=registry.getStringOfLanguage("validate.value", language) %></small>
				</div>
			</div>
			
			<!-- <button type="button" class="medium" id="btnResetCar"><%=registry.getStringOfLanguage("btn.reset", language) %></button> -->
			<button type="submit" class="medium" id="btnSubmitCar" ><%=registry.getStringOfLanguage("btn.submit", language) %></button>
			
		</form>
		</div>

		<div class="row publish" id="publish_kind002">
		<form id="fur_form" method="post" action="#" data-abide>

			<div class="row">
 				<div class="large-4 medium-4 columns">
					<label><%=registry.getStringOfLanguage("opport.fur.type", language) %> 
					<select id="fur_property" data-invalid="" aria-invalid="true" required="required">
					<option value="">Selecciona</option>

					
					
					<c:forEach var="item" items="${property_list }">
						<c:choose>
						<c:when test="${property==item.code }">
							<option selected value="${item.code}">${item.value }</option>
						</c:when>
						<c:otherwise>
							<option value="${item.code}">${item.value }</option>
						</c:otherwise>
						</c:choose>
					</c:forEach>
					</select>
					</label>
					<small class="error"><%=registry.getStringOfLanguage("validate.value", language) %></small>
				</div>
				<div class="large-4 medium-4 columns">
					<label><%=registry.getStringOfLanguage("opport.fur.building", language) %> 
					<input id="fur_building" type="text" placeholder="" required="required" maxlength="200" value="${model }" />
					</label>
					<small class="error"><%=registry.getStringOfLanguage("validate.value", language) %></small>
				</div>
				<div class="large-4 medium-4 columns">
					<label><%=registry.getStringOfLanguage("opport.fur.price", language) %> 
					<input id="fur_price" type="number" placeholder=""  min="0"  step="any" required="required" maxlength="15" value="${price }" />
					</label>
					<small class="error"><%=registry.getStringOfLanguage("validate.value", language) %></small>
				</div>
			</div>

			<div class="row">
				<div class="large-6 medium-6 columns">
					<label><%=registry.getStringOfLanguage("opport.fur.state", language) %> 
					<select id="fur_state">
					<option value="">Selecciona</option>
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
					<small class="error"><%=registry.getStringOfLanguage("validate.value", language) %></small>
				</div>
				<div class="large-6 medium-6 columns">
					<label><%=registry.getStringOfLanguage("opport.fur.city", language) %>
					<select id="fur_city">
					<option value="">Selecciona</option>
					</select>
					</label>
					<small class="error"><%=registry.getStringOfLanguage("validate.value", language) %></small>
				</div>
			</div>
<%-- 
			<div class="row">
				<div class="large-12 medium-12 columns">
					<label><%=registry.getStringOfLanguage("opport.fur.employee_num", language) %> 
					<input id="fur_employee" type="text" placeholder="" value="${employee_num }" maxlength="30" />
					</label>
					<small class="error"><%=registry.getStringOfLanguage("validate.value", language) %></small>
				</div>
			</div>
 --%>
			<div class="row">
				<div class="large-6 medium-6 columns">
					<label><%=registry.getStringOfLanguage("opport.fur.meter", language) %> 
					<input id="fur_meter" type="text" placeholder=""  value="${mileage }"  maxlength="20" />
					</label>
					<small class="error"><%=registry.getStringOfLanguage("validate.value", language) %></small>
				</div>
				<div class="large-6 medium-6 columns">
					<label><%=registry.getStringOfLanguage("opport.fur.serve_type", language) %> 
					<select id="fur_serve" data-invalid="" aria-invalid="true" required="required">
						<option value="">Selecciona</option>
						
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
					</select>
					</label>
					<small class="error"><%=registry.getStringOfLanguage("validate.value", language) %></small>
				</div>
			</div>

			<div class="row">
				<div class="large-4 medium-4 columns">
					<label><%=registry.getStringOfLanguage("opport.fur.amueblado", language) %> 
					<select id="fur_amueblado" data-invalid="" aria-invalid="true" required="required">
						<option value="">Selecciona</option>
						
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
					</select>
					</label>
					<small class="error"><%=registry.getStringOfLanguage("validate.value", language) %></small>
				</div>
				<div class="large-4 medium-4 columns">
					<label><%=registry.getStringOfLanguage("opport.fur.plants", language) %> 
					<input id="fur_plants" type="text" placeholder=""  value="${plants }" required="required"  maxlength="10" />
					</label>
					<small class="error"><%=registry.getStringOfLanguage("validate.value", language) %></small>
				</div>
				<div class="large-4 medium-4 columns">
					<label><%=registry.getStringOfLanguage("opport.fur.rooms", language) %> 
					<input id="fur_rooms" type="text" placeholder=""  value="${rooms }" required="required"  maxlength="10" />
					</label>
					<small class="error"><%=registry.getStringOfLanguage("validate.value", language) %></small>
				</div>
			</div>

			<div class="row">
				<div class="large-2 medium-2 columns">
					<a href="javascript:selectImage()" class="button tiny" style="background-color: #999;"><%=registry.getStringOfLanguage("opp.select_image", language) %></a>
				</div>
				<div class="large-4 medium-4 columns">
					<img src="" path="${file }" id="fur_file">
				</div>
				<div class="large-4 medium-4 columns">
				</div>
			</div>

			<div class="row">
				<div class="large-6 medium-6 columns">
					<label><%=registry.getStringOfLanguage("opport.fur.telephone", language) %> 
					<input id="fur_telephone" type="text" placeholder="" value="${telephone }" maxlength="50" />
					</label>
					<small class="error"><%=registry.getStringOfLanguage("validate.value", language) %></small>
				</div>
				<div class="large-6 medium-6 columns">
					<label><%=registry.getStringOfLanguage("opport.fur.mobilephone", language) %> 
					<input id="fur_mobilephone" type="text" placeholder="" value="${mobilephone }"  maxlength="50" />
					</label>
					<small class="error"><%=registry.getStringOfLanguage("validate.value", language) %></small>
				</div>
			</div>
			
			<!-- <button type="button" class="medium" id="btnResetFur"><%=registry.getStringOfLanguage("btn.reset", language) %></button> -->
			<button type="submit" class="medium" id="btnSubmitFur" ><%=registry.getStringOfLanguage("btn.submit", language) %></button>
			
		</form>
		</div>
		
		<div class="row publish" id="publish_kind003">
		<form id="srv_form" method="post" action="#" data-abide>
			<div class="row">
 				<div class="large-4 medium-4 columns">
					<label><%=registry.getStringOfLanguage("opport.srv.varios", language) %> 
					<select id="srv_varios" data-invalid="" aria-invalid="true" required="required">
					<option value="">Selecciona</option>
					

					
					
					
					<c:forEach var="item" items="${varios_list }">
						<c:choose>
						<c:when test="${varios==item.code }">
							<option selected value="${item.code}">${item.value }</option>
						</c:when>
						<c:otherwise>
							<option value="${item.code}">${item.value }</option>
						</c:otherwise>
						</c:choose>
					</c:forEach>
					</select>
					</label>
					<small class="error"><%=registry.getStringOfLanguage("validate.value", language) %></small>
				</div>
				<div class="large-4 medium-4 columns">
					<label><%=registry.getStringOfLanguage("opport.srv.article", language) %> 
					<input id="srv_article" type="text" placeholder="" required="required" maxlength="200" value="${model }" />
					</label>
					<small class="error"><%=registry.getStringOfLanguage("validate.value", language) %></small>
				</div>
				<div class="large-4 medium-4 columns">
					<label><%=registry.getStringOfLanguage("opport.srv.price", language) %> 
					<input id="srv_price" type="number" placeholder=""  min="0"  step="any" required="required" maxlength="15" value="${price }" />
					</label>
					<small class="error"><%=registry.getStringOfLanguage("validate.value", language) %></small>
				</div>
			</div>
			
			<div class="row">
				<div class="large-6 medium-6 columns">
					<label><%=registry.getStringOfLanguage("opport.srv.state", language) %> 
					<select id="srv_state">
					<option value="">Selecciona</option>
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
					<small class="error"><%=registry.getStringOfLanguage("validate.value", language) %></small>
				</div>
				<div class="large-6 medium-6 columns">
					<label><%=registry.getStringOfLanguage("opport.srv.city", language) %>
					<select id="srv_city">
					<option value="">Selecciona</option>
					</select>
					</label>
					<small class="error"><%=registry.getStringOfLanguage("validate.value", language) %></small>
				</div>
			</div>
<%-- 
			<div class="row">
				<div class="large-12 medium-12 columns">
					<label><%=registry.getStringOfLanguage("opport.srv.employee_num", language) %> 
					<input id="srv_employee" type="text" placeholder="" value="${employee_num }" maxlength="30" />
					</label>
					<small class="error"><%=registry.getStringOfLanguage("validate.value", language) %></small>
				</div>
			</div>
 --%>
			<div class="row">
				<div class="large-12 medium-12 columns">
					<label><%=registry.getStringOfLanguage("opport.srv.desc", language) %>
					<textarea rows="" cols="" id="srv_desc">
					</textarea> 
					</label>
					<small class="error"><%=registry.getStringOfLanguage("validate.value", language) %></small>
				</div>
			</div>

			<div class="row">
				<div class="large-2 medium-2 columns">
					<a href="javascript:selectImage()" class="button tiny" style="background-color: #999;"><%=registry.getStringOfLanguage("opp.select_image", language) %></a>
				</div>
				<div class="large-4 medium-4 columns">
					<img src="" path="${file }" id="srv_file">
				</div>
				<div class="large-4 medium-4 columns">
				</div>
			</div>

			<div class="row">
				<div class="large-6 medium-6 columns">
					<label><%=registry.getStringOfLanguage("opport.srv.telephone", language) %> 
					<input id="srv_telephone" type="text" placeholder="" value="${telephone }" maxlength="50" />
					</label>
					<small class="error"><%=registry.getStringOfLanguage("validate.value", language) %></small>
				</div>
				<div class="large-6 medium-6 columns">
					<label><%=registry.getStringOfLanguage("opport.srv.mobilephone", language) %> 
					<input id="srv_mobilephone" type="text" placeholder="" value="${mobilephone }"  maxlength="50" />
					</label>
					<small class="error"><%=registry.getStringOfLanguage("validate.value", language) %></small>
				</div>
			</div>
		
			<!-- <button type="button" class="medium" id="btnResetSrv"><%=registry.getStringOfLanguage("btn.reset", language) %></button> -->
			<button type="submit" class="medium" id="btnSubmitSrv" ><%=registry.getStringOfLanguage("btn.submit", language) %></button>
		</form>			
		</div>
	</div>
</div>

<div id="uploadModal" class="reveal-modal" data-reveal>
</div>

<input type="hidden" id="basePath" value="<%=basePath %>">
<input type="hidden" id="resPath" value="<%=resPath %>">

<input type="hidden" id="city_id" value="${city_id }">
<input type="hidden" id="obs" value="${obs }">
<input type="hidden" id="value_today" value="${today }">

<input type="hidden" id="publish_kind" value="${kind }">
<input type="hidden" id="publish_type" value="${type }">
<input type="hidden" id="publish_id" value="${publish_id }">


<form id="form_move_myads" method="post" action="<%=basePath%>opportunities/myads.html">
</form>

<input type="hidden" id="validate_file_empty_msg" value="<%=registry.getStringOfLanguage("validate.image", language) %>">

<!--[if lte IE 9]>
	<script src="<%=resPath%>bower_components/REM-unit-polyfill/js/rem.min.js"></script>
	<script src="<%=resPath%>bower_components/jquery-legacy/jquery.min.js"></script>
<![endif]-->

<script src="<%=resPath%>bower_components/jquery/dist/jquery.min.js"></script>
<script src="<%=resPath%>bower_components/velocity/velocity.min.js"></script>
<script src="<%=resPath%>bower_components/foundation/js/foundation.min.js"></script>
<script src="<%=resPath%>js/foundation/foundation-datepicker.js"></script>
<script src="<%=resPath%>bower_components/jquery-ui/jquery-ui.min.js"></script>
<script src="<%=resPath%>js/jquery/jquery.form.js"></script>
<script src="<%=resPath%>js/opp_publish.js"></script>
<script src="<%=resPath%>js/opp_app.js"></script>

<%@include file="jsp/opp_footer.jsp"%>