<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="../variable.jsp"%>
<%@include file="jsp/header.jsp"%>

<%@include file="jsp/topbar.jsp"%>

<div id="content-wrapper">

	<%@include file="jsp/menubar.jsp"%>

	<div class="small-10 medium-8 large-9 columns">
		<div id="content">
			<h1 class="main-title"><%=registry.getStringOfLanguage("user.title", language)%></h1>

			<div id="users">

				<div id="msg_alert">
					<img id="msg_alert_progress" src="<%=resPath%>images/progress.gif">
					<span>Message</span>
				</div>

				<div class="row left">
					<div class="large-12 columns right">
						<a href="javascript:updateUser('add', '')"
							class="medium button right"><%=registry.getStringOfLanguage("btn.add_user", language)%></a>
						<span class="right">&nbsp;</span> <a
							href="javascript:importUser()" class="medium button right"><%=registry.getStringOfLanguage("btn.import_user", language)%></a>
					</div>
				</div>

				<div class="row left">
					<div class="large-12 columns">

						<table id="user_table" class="display" cellspacing="0"
							cellpadding="0" width="100%">
							<thead>
								<tr>
									<th><%=registry.getStringOfLanguage("user.table.number",
					language)%></th>
									<th><%=registry.getStringOfLanguage("user.table.role", language)%></th>
									<th>Nombre</th>
									<th>Appaterno</th>
									<th>Apmaterno</th>
									<th><%=registry
					.getStringOfLanguage("user.table.email", language)%></th>
									<th>Musuario</th>
									<th><%=registry.getStringOfLanguage("user.table.status",
					language)%></th>
									<th><%=registry.getStringOfLanguage("user.table.action",
					language)%></th>
								</tr>
							</thead>
						</table>

					</div>
				</div>

			</div>
		</div>
	</div>
</div>

<div id="userModal" class="reveal-modal" data-reveal>
	<form id="user_form" method="post" action="#" data-abide>
		<div class="row left">
			<div class="large-10 columns">
				<div class="row">
					<div class="large-4 medium-4 columns">
						<label><%=registry.getStringOfLanguage("user.musuario", language)%>
							<input id="musuario" type="text"
							placeholder="<%=registry.getStringOfLanguage("placeholder.value",
					language)%>"
							required="required" /> </label> <small class="error"><%=registry.getStringOfLanguage("validate.value", language)%></small>
					</div>
					<div class="large-4 medium-4 columns">
						<label><%=registry.getStringOfLanguage("user.id", language)%>
							<input id="user_id" type="text"
							placeholder="<%=registry.getStringOfLanguage("placeholder.value",
					language)%>"
							required="required" maxlength="20" /> </label> <small class="error"><%=registry.getStringOfLanguage("validate.value", language)%></small>
					</div>
					<%--<div class="large-4 medium-4 columns">
						<label><%=registry.getStringOfLanguage("password", language)%>
							<input id="user_password" type="text"
							placeholder="<%=registry.getStringOfLanguage("placeholder.password",
					language)%>"
							maxlength="32" /> </label> <small class="error"><%=registry.getStringOfLanguage("validate.password",
					language)%></small>
					</div>--%>
				</div>

				<div class="row">
					<div class="large-4 medium-4 columns">
						<label>Nombre <input id="user_name" type="text"
							placeholder="<%=registry
					.getStringOfLanguage("placeholder.name", language)%>"
							maxlength="50" />
						</label> <small class="error"><%=registry.getStringOfLanguage("validate.name", language)%></small>
					</div>
					<div class="large-4 medium-4 columns">
						<label>Appaterno <input id="user_firstname" type="text"
							placeholder="<%=registry
					.getStringOfLanguage("placeholder.name", language)%>"
							maxlength="50" />
						</label> <small class="error"><%=registry.getStringOfLanguage("validate.name", language)%></small>
					</div>
					<div class="large-4 medium-4 columns">
						<label>Apmaterno <input id="user_lastname" type="text"
							placeholder="<%=registry
					.getStringOfLanguage("placeholder.name", language)%>"
							maxlength="50" />
						</label> <small class="error"><%=registry.getStringOfLanguage("validate.name", language)%></small>
					</div>
				</div>

				<div class="row">
					<div class="large-4 medium-4 columns">
						<label><%=registry.getStringOfLanguage("email", language)%>
							<input id="user_email" type="email"
							placeholder="<%=registry.getStringOfLanguage("placeholder.email",
					language)%>"
							maxlength="100" /> </label> <small class="error"><%=registry.getStringOfLanguage("validate.email", language)%></small>
					</div>
					<div class="large-4 medium-4 columns">
						<label>Token <input id="user_token" type="text"
							placeholder="<%=registry.getStringOfLanguage("placeholder.value",
					language)%>"
							maxlength="100" />
						</label> <small class="error"><%=registry.getStringOfLanguage("validate.value", language)%></small>
					</div>
					<div class="large-4 medium-4 columns">
						<label><%=registry.getStringOfLanguage("user.role", language)%>
							<select id="user_level">
								<option value="1">Administrador</option>						
								<option value="2">Usuario</option>		
						</select> </label>
					</div>
				</div>

				<div class="row">
					<div class="large-4 medium-4 columns">
						<label><%=registry.getStringOfLanguage("user.gender", language)%>
							<select id="user_gender">
								<option value="M"><%=registry.getStringOfLanguage("user.male", language)%></option>
								<option value="F"><%=registry.getStringOfLanguage("user.female", language)%></option>
						</select> </label> <small class="error"><%=registry.getStringOfLanguage("validate.select", language)%></small>
					</div>
					<div class="large-4 medium-4 columns">
						<label><%=registry.getStringOfLanguage("user.birthday", language)%>
							<input id="user_birthday" class="date fdatepicker"
							data-date-format="yyyy-mm-d" type="text"
							placeholder="<%=registry
					.getStringOfLanguage("placeholder.date", language)%>"
							value="" maxlength="10" /> </label> <small class="error"><%=registry.getStringOfLanguage("validate.value", language)%></small>
					</div>
					<div class="large-4 medium-4 columns">
						<label><%=registry.getStringOfLanguage("user.employee_code",
					language)%>
							<input id="user_employee" type="text"
							placeholder="<%=registry.getStringOfLanguage("placeholder.value",
					language)%>"
							maxlength="50" /> </label> <small class="error"><%=registry.getStringOfLanguage("validate.value", language)%></small>
					</div>
				</div>

				<%-- 
			<div class="row">
				<div class="large-6 medium-6 columns">
					<label><%=registry.getStringOfLanguage("user.division", language) %> 
					<select id="user_division">
						<option value="all" selected="selected"><%=registry.getStringOfLanguage("user.all_division", language) %></option>
					<c:forEach var="item" items="${divisionlist }">
						<option value="${item.id}">${item.name }</option>
					</c:forEach>
					</select>
					</label>
					<small class="error"><%=registry.getStringOfLanguage("validate.select", language) %></small>
				</div>
				<div class="large-6 medium-6 columns">
					<label><%=registry.getStringOfLanguage("user.sub_division", language) %>
					<select id="user_subdivision">
					</select>
					</label>
				</div>
			</div>

			<div class="row">
				<div class="large-6 medium-6 columns">
					<label><%=registry.getStringOfLanguage("user.geographical", language) %>
					<select id="user_geographical">
						<option value="all" selected="selected"><%=registry.getStringOfLanguage("user.all_geographical", language) %></option>
					<c:forEach var="item" items="${citylist }">
						<option value="${item.id}">${item.name }</option>
					</c:forEach>
					</select>
					</label>
					<small class="error"><%=registry.getStringOfLanguage("validate.select", language) %></small> 
				</div>
				<div class="large-6 medium-6 columns">
					<label><%=registry.getStringOfLanguage("user.city", language) %>
					<select id="user_city">
					</select>
					</label>
					<small class="error"><%=registry.getStringOfLanguage("validate.select", language) %></small> 
				</div>
			</div>

			<div class="row">
				<div class="large-4 columns">
					<label><%=registry.getStringOfLanguage("user.promote", language) %>
					<select id="user_promote">
						<option value="all" selected="selected"><%=registry.getStringOfLanguage("user.all_promote", language) %></option>
					<c:forEach var="item" items="${promotelist }">
						<option value="${item.code}">${item.value }</option>
					</c:forEach>
					</select>
					</label>
				</div>
				<div class="large-4 columns">
					<label><%=registry.getStringOfLanguage("user.jobgrade", language) %>
					<select id="user_jobgrade">
						<option value="all" selected="selected"><%=registry.getStringOfLanguage("user.all_jobgrade", language) %></option>
					<c:forEach var="item" items="${jobgradelist }">
						<option value="${item.code}">${item.value }</option>
					</c:forEach>
					</select>
					</label>
				</div>
				<div class="large-4 columns">
					<label><%=registry.getStringOfLanguage("user.payowner", language) %>
					<select id="user_payowner">
						<option value="all" selected="selected"><%=registry.getStringOfLanguage("user.all_payowner", language) %></option>
					<c:forEach var="item" items="${payownerlist }">
						<option value="${item.code}">${item.value }</option>
					</c:forEach>
					</select>
					</label>
				</div>
			</div>

			<div class="row">
				<div class="large-12 columns">
					<label><%=registry.getStringOfLanguage("user.people_manager", language) %></label> 
					<input type="radio" name="people_manager" value="001" id="peopleOnly">
					<label for="peopleOnly"><%=registry.getStringOfLanguage("user.people_only", language) %></label> 
					<input type="radio" name="people_manager" value="002" id="peopleAll" checked="checked">
					<label for="peopleAll"><%=registry.getStringOfLanguage("user.people_all", language) %></label>
				</div>
			</div>

			<div class="row">
				<div class="large-12 columns">
					<label><%=registry.getStringOfLanguage("user.newhire", language) %></label> 
					<input type="radio" name="newhire" value="001" id="newhireYes">
					<label for="newhireYes"><%=registry.getStringOfLanguage("yes", language) %></label> 
					<input type="radio" name="newhire" value="002" id="newhireNo" checked="checked">
					<label for="newhireNo"><%=registry.getStringOfLanguage("no", language) %></label>
				</div>
			</div>
 --%>

				<div class="row">
					<div class="large-4 columns">
						<label><%=registry.getStringOfLanguage("user.direccion", language)%>
							<select id="user_direccion">
								<option value="all"><%=registry.getStringOfLanguage("user.all_direccion",
					language)%></option>
								<c:forEach var="item" items="${direccionlist }">
									<option value="${item.id}">${item.value }</option>
								</c:forEach>
						</select> </label>
					</div>
					<div class="large-4 columns">
						<label><%=registry.getStringOfLanguage("user.empresa", language)%>
							<select id="user_empresa">
								<option value="all"><%=registry
					.getStringOfLanguage("user.all_empresa", language)%></option>
								<c:forEach var="item" items="${empresalist }">
									<option value="${item.id}">${item.value }</option>
								</c:forEach>
						</select> </label>
					</div>
					<div class="large-4 columns">
						<label><%=registry.getStringOfLanguage("user.ciudad", language)%>
							<select id="user_ciudad">
								<option value="all"><%=registry.getStringOfLanguage("user.all_ciudad", language)%></option>
								<c:forEach var="item" items="${ciudadlist }">
									<option value="${item.id}">${item.value }</option>
								</c:forEach>
						</select> </label>
					</div>
				</div>

				<div class="row">
					<div class="large-4 medium-4 columns">
						<label><%=registry.getStringOfLanguage("user.location", language)%>
							<input id="user_location" type="text"
							placeholder="<%=registry.getStringOfLanguage("placeholder.value",
					language)%>"
							value="" maxlength="200" /> </label> <small class="error"><%=registry.getStringOfLanguage("validate.value", language)%></small>
					</div>
					<div class="large-4 medium-4 columns">
						<label><%=registry.getStringOfLanguage("user.admission", language)%>
							<input id="user_admission" class="date fdatepicker"
							data-date-format="yyyy-mm-d" type="text"
							placeholder="<%=registry
					.getStringOfLanguage("placeholder.date", language)%>"
							value="" maxlength="10" /> </label> <small class="error"><%=registry.getStringOfLanguage("validate.value", language)%></small>
					</div>
					<div class="large-4 medium-4 columns">
						<label><%=registry.getStringOfLanguage("user.jobarea_code",
					language)%>
							<input id="user_jobarea" type="text"
							placeholder="<%=registry.getStringOfLanguage("placeholder.value",
					language)%>"
							maxlength="50" /> </label> <small class="error"><%=registry.getStringOfLanguage("validate.value", language)%></small>
					</div>
				</div>

				<div class="row">
					<div class="large-3 medium-3 columns">
						<label><%=registry.getStringOfLanguage("user.nombramiento",
					language)%>
							<input id="user_nombramiento" type="text"
							placeholder="<%=registry.getStringOfLanguage("placeholder.value",
					language)%>"
							value="" maxlength="50" /> </label> <small class="error"><%=registry.getStringOfLanguage("validate.value", language)%></small>
					</div>
					<div class="large-3 medium-3 columns">
						<label><%=registry.getStringOfLanguage("user.horario", language)%>
							<input id="user_horario" type="text"
							placeholder="<%=registry.getStringOfLanguage("placeholder.value",
					language)%>"
							maxlength="100" /> </label> <small class="error"><%=registry.getStringOfLanguage("validate.value", language)%></small>
					</div>
					<div class="large-3 medium-3 columns">
						<label><%=registry.getStringOfLanguage("user.entered", language)%>
							<input id="user_entered" type="text"
							placeholder="<%=registry.getStringOfLanguage("placeholder.value",
					language)%>"
							maxlength="10" /> </label> <small class="error"><%=registry.getStringOfLanguage("validate.value", language)%></small>
					</div>
					<div class="large-3 medium-3 columns">
						<label><%=registry.getStringOfLanguage("user.arbol", language)%>
							<input id="user_arbol" type="text"
							placeholder="<%=registry.getStringOfLanguage("placeholder.value",
					language)%>"
							maxlength="100" /> </label> <small class="error"><%=registry.getStringOfLanguage("validate.value", language)%></small>
					</div>
				</div>

				<div class="row">
					<div class="large-12 columns">
						<label><%=registry.getStringOfLanguage("user.active", language)%></label>
						<input type="radio" name="active" value="1" id="activeYes"
							checked="checked"> <label for="activeYes"><%=registry.getStringOfLanguage("yes", language)%></label>
						<input type="radio" name="active" value="0" id="activeNo">
						<label for="activeNo"><%=registry.getStringOfLanguage("no", language)%></label>
					</div>
				</div>
			</div>

			<div class="large-2 columns">&nbsp;</div>
		</div>

		<div class="row">
			<div class="large-12 columns right">
				<button type="submit" class="medium right" id="btnSubmit"><%=registry.getStringOfLanguage("btn.submit", language)%></button>
				<span class="right">&nbsp;</span>
				<button type="reset" class="medium right" id="btnReset"><%=registry.getStringOfLanguage("btn.reset", language)%></button>
			</div>
		</div>

	</form>

	<input type="hidden" id="type" value="add" /> <input type="hidden"
		id="edit_user_id" value="" /> <input type="hidden"
		id="subdivision_empty_msg"
		value="<%=registry.getStringOfLanguage("validate.division",
					language)%>" />
	<input type="hidden" id="city_empty_msg"
		value="<%=registry.getStringOfLanguage("validate.city", language)%>" />
	<input type="hidden" id="placeholder_select"
		value="<%=registry.getStringOfLanguage("placeholder.select",
					language)%>" />

	<a class="close-reveal-modal">&#215;</a>
</div>

<div id="importModal" class="reveal-modal" data-reveal></div>

<form id="frm_user_reload" action="<%=basePath%>system/users.html"
	method="post"></form>

<input type="hidden" id="basePath" value="<%=basePath%>" />
<input type="hidden" id="resPath" value="<%=resPath%>" />

<input type="hidden" id="status_active_msg"
	value="<%=registry.getStringOfLanguage("user.status.active",
					language)%>" />
<input type="hidden" id="status_manager_msg"
	value="<%=registry.getStringOfLanguage("user.status.manager",
					language)%>" />
<input type="hidden" id="status_newhire_msg"
	value="<%=registry.getStringOfLanguage("user.status.newhire",
					language)%>" />
<input type="hidden" id="status_none_msg"
	value="<%=registry
					.getStringOfLanguage("user.status.none", language)%>" />

<input type="hidden" id="role_administrator_value"
	value="<%=registry.getStringOfLanguage("role.admin", language)%>" />
<input type="hidden" id="role_user_value"
	value="<%=registry.getStringOfLanguage("role.user", language)%>" />

<script src="<%=resPath%>bower_components/jquery/dist/jquery.min.js"></script>
<script src="<%=resPath%>bower_components/velocity/velocity.min.js"></script>
<script
	src="<%=resPath%>bower_components/foundation/js/foundation.min.js"></script>
<script src="<%=resPath%>bower_components/jquery-ui/jquery-ui.min.js"></script>
<script src="<%=resPath%>js/foundation/foundation-datepicker.js"></script>
<script src="<%=resPath%>js/jquery/jquery.dataTables.min.js"></script>
<script src="<%=resPath%>js/foundation/dataTables.foundation.min.js"></script>
<script src="<%=resPath%>js/foundation/dataTables.responsive.js"></script>

<script src="<%=resPath%>js/app.js"></script>
<script src="<%=resPath%>js/users.js"></script>

<%@include file="jsp/footer.jsp"%>
