<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="../variable.jsp"%>
<%@include file="jsp/header.jsp"%>

<%@include file="jsp/topbar.jsp"%>

	<div id="content-wrapper">

	<%@include file="jsp/menubar.jsp"%>
		
		<div class="small-10 medium-8 large-9 columns">
			<div id="content">
				<h1 class="main-title"><%=registry.getStringOfLanguage("channel.title", language) %></h1>

				<div id="channels">

<div id="msg_alert">
	<img id="msg_alert_progress" src="<%=resPath %>images/progress.gif">
	<span>Message</span>
</div>
					
					<div class="row left">
						<div class="large-6 columns">
							<form id="channel_form" method="post" action="#" data-abide>
							<div class="row">
								<div class="large-12 columns">
									<label><%=registry.getStringOfLanguage("channel.channel_name", language) %> 
									<input id="channel_name" type="text" placeholder="<%=registry.getStringOfLanguage("placeholder.name", language) %>" required="required" maxlength="100" />
									</label>
									<small class="error"><%=registry.getStringOfLanguage("validate.name", language) %></small>
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<label><%=registry.getStringOfLanguage("channel.family", language) %> 
									<select id="channel_family" required="required" data-invalid="" aria-invalid="true">
										<option value=""><%=registry.getStringOfLanguage("placeholder.select", language) %></option>
									<c:forEach var="item" items="${familylist }">
										<option value="${item.id }">${item.name }</option>
									</c:forEach>
									</select>
									</label>
									<small class="error"><%=registry.getStringOfLanguage("validate.select", language) %></small>
								</div>
							</div>
							<div class="row">
								<div class="large-6 medium-6 columns">
									<label><%=registry.getStringOfLanguage("email", language) %> 
									<input id="channel_email" type="email" placeholder="<%=registry.getStringOfLanguage("placeholder.email", language) %>" maxlength="100"/>
									</label>
								</div>
								<div class="large-6 medium-6 columns">
									<label><%=registry.getStringOfLanguage("password", language) %> 
									<input id="channel_password" type="text" placeholder="<%=registry.getStringOfLanguage("placeholder.password", language) %>" maxlength="32" />
									</label>
								</div>
							</div>
							
							<div class="row">
								<div class="large-12 columns">
									<label><%=registry.getStringOfLanguage("channel.parent", language) %> 
									<select id="channel_parent">
										<option value=""></option>
									</select>
									</label>
								</div>
							</div>
							
							<div class="row">
								<div class="large-6 medium-6 columns">
									<label><%=registry.getStringOfLanguage("channel.access", language) %></label> 
									<input type="radio" name="access" value="1" id="accessPublic" checked="checked">
									<label for="accessPublic"><%=registry.getStringOfLanguage("channel.public", language) %></label> 
									<input type="radio" name="access" value="2" id="accessPrivate">
									<label for="accessPrivate"><%=registry.getStringOfLanguage("channel.private", language) %></label>
								</div>
								<div class="large-6 medium-6 columns">
									<label><%=registry.getStringOfLanguage("channel.security_level", language) %>
									<select id="channel_security" data-invalid="" aria-invalid="true" required="required" >
 										<option value="0">Seleccionar</option>
 										<option value="1">Level 1</option>
										<option value="2">Level 2</option>
										<option value="3">Level 3</option>
										<option value="4">Level 4</option>
									
									</select>
									</label> 
									<small class="error"><%=registry.getStringOfLanguage("validate.select", language) %></small>
								</div>
							</div>

							<div class="row">
								<div class="large-12 columns">
									<label><%=registry.getStringOfLanguage("channel.direccion", language) %>
									<a href="javascript:clear_direccion()" class="button tiny secondary" style="float: right; padding: 5px; margin:0; ">Deselect <i class="fa fa-times"></i></a> 
									<select id="channel_direccion" multiple="multiple" size="10" style="height: 250px;">
									<c:forEach var="item" items="${direccionlist }">
										<option value="${item.id}">${item.value }</option>
									</c:forEach>
									</select>
									</label>
									<small class="error"><%=registry.getStringOfLanguage("validate.select", language) %></small>
								</div>
							</div>

							<div class="row">
								<div class="large-12 columns">
									<label><%=registry.getStringOfLanguage("channel.ciudad", language) %>
									<a href="javascript:clear_ciudad()" class="button tiny secondary" style="float: right; padding: 5px; margin:0; ">Deselect <i class="fa fa-times"></i></a> 
									<select id="channel_ciudad" multiple="multiple" size="10" style="height: 250px;">
									<c:forEach var="item" items="${ciudadlist }">
										<option value="${item.id}">${item.value }</option>
									</c:forEach>
									</select>
									</label>
									<small class="error"><%=registry.getStringOfLanguage("validate.select", language) %></small>
								</div>
							</div>

							<div class="row">
								<div class="large-12 columns">
									<label><%=registry.getStringOfLanguage("channel.empresa", language) %>
									<a href="javascript:clear_empresa()" class="button tiny secondary" style="float: right; padding: 5px; margin:0; ">Deselect <i class="fa fa-times"></i></a> 
									<select id="channel_empresa" multiple="multiple" size="10" style="height: 250px;">
									<c:forEach var="item" items="${empresalist }">
										<option value="${item.id}">${item.value }</option>
									</c:forEach>
									</select>
									</label>
									<small class="error"><%=registry.getStringOfLanguage("validate.select", language) %></small>
								</div>
							</div>

<%-- 							
							<div class="row">
								<div class="large-12 columns">
									<label><%=registry.getStringOfLanguage("channel.division", language) %> 
									<select id="channel_division" multiple="multiple" size="10" style="height: 250px;">
										<option value="all"><%=registry.getStringOfLanguage("channel.all_division", language) %></option>
									<c:forEach var="item" items="${divisionlist }">
										<option value="${item.id}">${item.name }</option>
									</c:forEach>
									</select>
									</label>
									<small class="error"><%=registry.getStringOfLanguage("validate.select", language) %></small>
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<label><%=registry.getStringOfLanguage("channel.sub_division", language) %>
									<div class="row" id="channel_subdivision" style="margin-bottom: 20px;">
										<span style="margin-left: 20px;">Select division.</span>
									</div> 
									</label>
								</div>
							</div>

							<div class="row">
								<div class="large-12 columns">
									<label><%=registry.getStringOfLanguage("channel.geographical", language) %>
									<select id="channel_geographical" multiple="multiple" size="10" style="height: 250px;">
										<option value="all"><%=registry.getStringOfLanguage("channel.all_geographical", language) %></option>
									<c:forEach var="item" items="${citylist }">
										<option value="${item.id}">${item.name }</option>
									</c:forEach>
									</select>
									</label>
									<small class="error"><%=registry.getStringOfLanguage("validate.select", language) %></small> 
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<label><%=registry.getStringOfLanguage("channel.sub_geographical", language) %>
									<div class="row" id="channel_city" style="margin-bottom: 20px;">
										<span style="margin-left: 20px;">Select city.</span>
									</div> 
									</label> 
								</div>
							</div>

							<div class="row">
								<div class="large-12 columns">
									<label><%=registry.getStringOfLanguage("channel.promote", language) %> </label>
									<c:forEach var="item" items="${promotelist }">
										<input class="channel_promote" pos="${item.code }" type="checkbox" id="channel_promote${item.code }" value="${item.code }" />
										<label for="channel_promote${item.code }">${item.value }</label>
									</c:forEach>
								</div>
							</div>

							<div class="row">
								<div class="large-12 columns">
									<label><%=registry.getStringOfLanguage("channel.jobgrade", language) %> </label>
									<c:forEach var="item" items="${jobgradelist }">
										<input class="channel_jobgrade" pos="${item.code }" type="checkbox" id="channel_jobgrade${item.code }" value="${item.code }" />
										<label for="channel_jobgrade${item.code }">${item.value }</label>
									</c:forEach>
								</div>
							</div>

							<div class="row">
								<div class="large-12 columns">
									<label><%=registry.getStringOfLanguage("channel.payowner", language) %> </label>
									<c:forEach var="item" items="${payownerlist }">
										<input class="channel_payowner" pos="${item.code }" type="checkbox" id="channel_payowner${item.code }" value="${item.code }" />
										<label for="channel_payowner${item.code }">${item.value }</label>
									</c:forEach>
								</div>
							</div>

							<div class="row">
								<div class="large-12 columns">
									<label><%=registry.getStringOfLanguage("channel.people_manager", language) %></label> 
									<input type="radio" name="people_manager" value="001" id="peopleOnly">
									<label for="peopleOnly"><%=registry.getStringOfLanguage("channel.people_only", language) %></label> 
									<input type="radio" name="people_manager" value="002" id="peopleAll" checked="checked">
									<label for="peopleAll"><%=registry.getStringOfLanguage("channel.people_all", language) %></label>
								</div>
							</div>

							<div class="row">
								<div class="large-12 columns">
									<label><%=registry.getStringOfLanguage("channel.newhire", language) %></label> 
									<input type="radio" name="newhire" value="001" id="newhireYes">
									<label for="newhireYes"><%=registry.getStringOfLanguage("yes", language) %></label> 
									<input type="radio" name="newhire" value="002" id="newhireNo" checked="checked">
									<label for="newhireNo"><%=registry.getStringOfLanguage("no", language) %></label>
								</div>
							</div>
 --%>						
							<button type="reset" class="medium" id="btnReset"><%=registry.getStringOfLanguage("btn.reset", language) %></button>
							<button type="submit" class="medium" id="btnAddNew"><%=registry.getStringOfLanguage("btn.add_new", language) %></button>
							<button type="submit" class="medium" id="btnSubmit"><%=registry.getStringOfLanguage("btn.submit", language) %></button>
							
							</form>

							<input type="hidden" id="type" value="add" />
							<input type="hidden" id="channel_id" value="" />
							<input type="hidden" id="subdivision_empty_msg" value="<%=registry.getStringOfLanguage("validate.division", language) %>" />
							<input type="hidden" id="city_empty_msg" value="<%=registry.getStringOfLanguage("validate.city", language) %>" />
							<input type="hidden" id="placeholder_select" value="<%=registry.getStringOfLanguage("placeholder.select", language) %>" />
						</div>
						
						<div class="large-6 columns">
							<small><%=registry.getStringOfLanguage("channel.description", language) %></small>							
							<dl id="channel_tree" class="accordion" data-accordion>
							</dl>
						</div>
					</div>
				</div>


			</div>
		</div>
	</div>

	<script src="<%=resPath%>bower_components/jquery/dist/jquery.min.js"></script>
	<script src="<%=resPath%>bower_components/velocity/velocity.min.js"></script>
	<script src="<%=resPath%>bower_components/foundation/js/foundation.min.js"></script>
	<script src="<%=resPath%>bower_components/jquery-ui/jquery-ui.min.js"></script>
	<script src="<%=resPath%>bower_components/nestedSortable/jquery.ui.nestedSortable.js"></script>

	<script src="<%=resPath%>js/app.js"></script>
	<script src="<%=resPath%>js/channels.js"></script>

<%@include file="jsp/footer.jsp"%>	