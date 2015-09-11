<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="../variable.jsp"%>
<%@include file="jsp/header.jsp"%>

	<%@include file="jsp/topbar.jsp"%>

	<div id="content-wrapper">

	<%@include file="jsp/menubar.jsp"%>
		
		<div class="small-10 medium-8 large-9 columns">
			<div id="content">
				<h1 class="main-title"><%=registry.getStringOfLanguage("content.title.add", language) %></h1>

				<div id="add_contents">

<div id="msg_alert">
	<img id="msg_alert_progress" src="<%=resPath %>images/progress.gif">
	<span>Message</span>
</div>
					
					<div class="row left">
						<div class="large-6 columns">
							<form id="content_form" method="post" action="#" data-abide>
							<div class="row">
								<div class="large-12 columns">
									<label><%=registry.getStringOfLanguage("content.input.title", language) %> 
									<input id="content_title" type="text" placeholder="<%=registry.getStringOfLanguage("placeholder.title", language) %>" required="required" maxlength="500" />
									</label>
									<small class="error"><%=registry.getStringOfLanguage("validate.title", language) %></small>
								</div>
							</div>
							
							<div class="row">
								<div class="large-12 columns">
									<label><%=registry.getStringOfLanguage("content.input.show_in", language) %> </label> 
									<input type="radio" id="show_destacado" name="show_in" value="1"  checked="checked"> Destacado    
									<input type="radio" id="show_soloCanal" name="show_in" value="2">Solo canal       
									<input type="radio" id="show_ambos" name="show_in" value="3"> Ambos<br>    
								</div>
							</div>

							<div class="row">
								<div class="large-12 columns">
									<label><%=registry.getStringOfLanguage("content.input.content_type", language) %>
									<select id="content_featured" required="required" data-invalid="" aria-invalid="true">
										<option value=""><%=registry.getStringOfLanguage("placeholder.select", language) %></option> 
										<option value="2">Video</option>
										<option value="5">Noticias</option>
										<option value="3">Galeria de imagenes</option>
										<option value="4">Archivos adjuntos</option>
										
									</select>
									</label>
									<small class="error"><%=registry.getStringOfLanguage("validate.select", language) %></small>
								</div>
							</div>
							
							<div class="row">
								<div class="large-12 columns">
									<label><%=registry.getStringOfLanguage("content.input.tag", language) %></label>
									<div id="content_tag_list">
									</div>
								</div>
							</div>
							<div class="row" style="margin-top: 5px;">
								<div class="large-8 columns">
									<div class="ui-widget">
										<label for="content_tag">
										<input id="content_tag" type="text" placeholder="<%=registry.getStringOfLanguage("placeholder.tag", language) %>" autocomplete="off"/>
										</label>
									</div>
								</div>
								<div class="large-4 columns" style="padding-left:0px">
									<small>Press Enter to add tag</small>
								</div>
							</div>
							
							<div class="row">
								<div class="large-12 columns">
									<label><%=registry.getStringOfLanguage("content.input.validity", language) %></label> 
									<div class="row">
										<div class="large-5 columns">
											<input id="content_validity_start" class="date fdatepicker" data-date-format="yyyy-mm-dd" type="text" placeholder="<%=registry.getStringOfLanguage("placeholder.date", language) %>" value="" required="required" maxlength="10" />
										</div>
										<div class="large-1 columns">
											<label style="text-align: center; font-size: 20px; font-weight: 900;">~</label>
										</div>
										<div class="large-5 columns">
											<input id="content_validity_end" class="date fdatepicker" data-date-format="yyyy-mm-dd" type="text" placeholder="<%=registry.getStringOfLanguage("placeholder.date", language) %>" value="" required="required" maxlength="10" />
										</div>
										<div class="large-1 columns">
										&nbsp;
										</div>
									</div>
								</div>
							</div>

							<div class="row">
								<div class="large-12 columns">
									<label><%=registry.getStringOfLanguage("content.input.active", language) %></label> 
									<input type="radio" name="active" value="1" id="activeYes" checked="checked">
									<label for="activeYes"><%=registry.getStringOfLanguage("yes", language) %></label> 
									<input type="radio" name="active" value="2" id="activeNo">
									<label for="activeNo"><%=registry.getStringOfLanguage("no", language) %></label>
								</div>
							</div>

							<button type="button" class="medium" id="btnReset"><%=registry.getStringOfLanguage("btn.reset", language) %></button>
							<button type="submit" class="medium" id="btnSubmit"><%=registry.getStringOfLanguage("btn.submit", language) %></button>
							
							</form>
							
							<input type="hidden" id="content_id" value="${content_id }" />
							<input type="hidden" id="content_type" value="${type }" />
							
							<input type="hidden" id="channel_empty_msg" value="<%=registry.getStringOfLanguage("validate.channel", language) %>" />
							<input type="hidden" id="date_empty_msg" value="<%=registry.getStringOfLanguage("validate.date", language) %>" />
							<input type="hidden" id="tag_empty_msg" value="<%=registry.getStringOfLanguage("validate.tag", language) %>" />
							
						</div>
						
						<div class="large-6 columns">
							<small><%=registry.getStringOfLanguage("content.input.channel", language) %></small>
							<dl id="channel_tree">
							</dl>
						</div>
					</div>
					
				</div>
				
			</div>
		</div>
	</div>

	<input type="hidden" id="basePath" value="<%=basePath %>" />
	<input type="hidden" id="resPath" value="<%=resPath %>" />
	
	<input type="hidden" id="availableTags" value='${tags }' />

	<form id="form_move" method="post" action="<%=basePath %>content/edit.html">
		<input type="hidden" name="content_id" id="edit_content_id" value="" />
	</form>
	
	<script src="<%=resPath%>bower_components/jquery/dist/jquery.min.js"></script>
	<script src="<%=resPath%>bower_components/velocity/velocity.min.js"></script>
	<script src="<%=resPath%>bower_components/foundation/js/foundation.min.js"></script>
	<script src="<%=resPath%>bower_components/jquery-ui/jquery-ui.min.js"></script>
	<script src="<%=resPath%>js/foundation/foundation-datepicker.js"></script>
	<script src="<%=resPath%>js/jquery/jquery.mjs.nestedSortable.js"></script>
	<script src="<%=resPath%>js/app.js"></script>

	<script src="<%=resPath%>js/add_content.js"></script>
	
<%@include file="jsp/footer.jsp"%>