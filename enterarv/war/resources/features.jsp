<%@page import="com.youandbbva.enteratv.Constants"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="../variable.jsp"%>
<%@include file="jsp/header.jsp"%>

	<%@include file="jsp/topbar.jsp"%>

	<div id="content-wrapper">

	<%@include file="jsp/menubar.jsp"%>
		
		<div class="small-10 medium-8 large-9 columns">
			<div id="content">
				<h1 class="main-title"><%=registry.getStringOfLanguage("features.title", language) %></h1>

				<div id="options">

<div id="msg_alert">
	<img id="msg_alert_progress" src="<%=resPath %>images/progress.gif">
	<span>Message</span>
</div>
					
					<div class="row left">
					<div class="large-12 columns">
						<div class="row">
							<div class="row">
								<label><%=registry.getStringOfLanguage("features.template", language) %> 
								<select id="features_template">
									<c:choose>
									<c:when test="${features=='0'}">
										<option value="0" selected><%=registry.getStringOfLanguage("placeholder.select", language) %></option>
									</c:when>
									<c:otherwise>
										<option value="0"><%=registry.getStringOfLanguage("placeholder.select", language) %></option>
									</c:otherwise>
									</c:choose>

<c:forEach begin="1" end="5" var="item">									
									<c:choose>
									<c:when test="${features==item }">
										<option value="${item }" selected><%=registry.getStringOfLanguage("features.template", language) %> ${item }</option>
									</c:when>
									<c:otherwise>
										<option value="${item }"><%=registry.getStringOfLanguage("features.template", language) %> ${item }</option>
									</c:otherwise>
									</c:choose>
</c:forEach>									
								</select>
								</label>
							</div>
							
							<div class=row>
							<fieldset>
								<legend><%=registry.getStringOfLanguage("features.detail", language) %></legend>
								<div class="row">
									<div class="small-12 columns">
										<div class="row collapse">
											<div class="small-6 columns">
											  	<a href="javascript:selectImage()" class="button small" style="background-color: #656565;"><%=registry.getStringOfLanguage("btn.select_image", language) %></a>
										  	</div>
											<div class="small-6 columns">
											  	<a href="javascript:getLatest()" class="button small" style="background-color: #656565;"><%=registry.getStringOfLanguage("btn.new_story", language) %></a>
										  	</div>
										</div>
										<div class="row collapse">
											<label><%=registry.getStringOfLanguage("features.feature_image", language) %></label>
											<img id="features_image" path="" src="" alt="<%=registry.getStringOfLanguage("placeholder.empty", language) %>">
										</div>
										<div class="row collapse" style="margin-top: 10px;">
											<label><%=registry.getStringOfLanguage("features.feature_title", language) %> 
											<input id="features_title" type="text" placeholder="<%=registry.getStringOfLanguage("placeholder.value", language) %>" />
											</label>
										</div>
										<div class="row collapse" style="margin-top: 10px;">
											<label><%=registry.getStringOfLanguage("features.feature_date", language) %> 
											<input id="features_date" type="text" class="date fdatepicker" data-date-format="yyyy-mm-dd" placeholder="<%=registry.getStringOfLanguage("placeholder.date", language) %>" />
											</label>
										</div>
										<div class="row collapse" style="margin-top: 10px;">
											<label><%=registry.getStringOfLanguage("features.feature_content", language) %></label>
											<div>
												<div id="features_editor_toolbar"></div> 
												<textarea id="features_editor">
												</textarea>
											</div>
										</div>
										
										<div class="row collapse" style="margin-top: 20px;">
											<button type="button" class="medium" id="btnSubmit"><%=registry.getStringOfLanguage("btn.submit", language) %></button>
										</div>
									</div>
								</div>
							</fieldset>							
							</div>
						</div>
						
						<div class="row">
							<div class="row collapse">
							<fieldset>
								<legend><%=registry.getStringOfLanguage("features.template", language) %></legend>
								<div class="large-12 columns">
									<div class="row features" id="features0">
										<br><br><br><br><br><br>
										<br><br><br><br><br><br>
									</div>
									
									<div class="row features" id="features1">
										<div class="large-12 columns">
											<div class="features_window row" features="1">
												<div class="row features_padding">
													<img src="" alt="" class="features_image" id="features1_image">
												</div>
												<div class="row features_padding">
													<input type="text" class="features_title" id="features1_title" readonly />
												</div>
												<div class="row features_padding">
													<input type="text" class="features_date" id="features1_date" readonly />
												</div>
												<div class="row  features_padding">
													<div id="features1_editor" class="features_editor">
													</div>
												</div>
											</div>
										</div>
									</div>
										
									<div class="row features" id="features2">
										<div class="row features_window" features="1">
										<div class="large-6 columns">
											<div class="row">
												<div class="row  features_padding">
													<img src="" alt="" class="features_image" id="features2_image">
												</div>
											</div>
										</div>
										<div class="large-6 columns">
											<div class="row">
												<div class="row features_padding">
													<input type="text" class="features_title" id="features2_title" readonly />
												</div>
												<div class="row features_padding">
													<input type="text" class="features_date" id="features2_date" readonly />
												</div>
												<div class="row  features_padding">
													<div id="features2_editor" class="features_editor">
													</div>
												</div>
											</div>
										</div>
										</div>
									</div>

									<div class="row features" id="features3">
										<div class="row features_window" features="1">
										<div class="large-6 columns">
											<div class="row">
												<div class="row features_padding">
													<input type="text" class="features_title" id="features3_title" readonly />
												</div>
												<div class="row features_padding">
													<input type="text" class="features_date" id="features3_date" readonly />
												</div>
												<div class="row  features_padding">
													<div id="features3_editor" class="features_editor">
													</div>
												</div>
											</div>
										</div>
										<div class="large-6 columns">
											<div class="row">
												<div class="row features_padding">
													<img src="" alt="" class="features_image" id="features3_image">
												</div>
											</div>
										</div>
										</div>
									</div>

									<div class="row features" id="features4">
										<div class="large-12 columns features_window" features="1">
											<div class="row">
												<div class="row features_padding">
													<input type="text" class="features_title" id="features4_title" readonly />
												</div>
												<div class="row features_padding">
													<input type="text" class="features_date" id="features4_date" readonly />
												</div>
												<div class="row features_padding">
													<div id="features4_editor" class="features_editor">
													</div>
												</div>
												<div class="row features_padding">
													<img src="" alt="" class="features_image" id="features4_image">
												</div>
											</div>
										</div>
									</div>

									<div class="row features" id="features5">
										<div class="large-6 columns features_window" features="1">
											<div class="row">
												<div class="row features_padding">
													<img src="" alt="" class="features_image" id="features5_1_image">
												</div>
												<div class="row features_padding">
													<input type="text" class="features_title" id="features5_1_title" readonly />
												</div>
												<div class="row features_padding">
													<input type="text" class="features_date" id="features5_1_date" readonly />
												</div>
												<div class="row  features_padding">
													<div id="features5_1_editor" class="features_editor">
													</div>
												</div>
											</div>
										</div>
										<div class="large-6 columns features_window" features="2">
											<div class="row">
												<div class="row features_padding">
													<img src="" alt="" class="features_image" id="features5_2_image">
												</div>
												<div class="row features_padding">
													<input type="text" class="features_title" id="features5_2_title" readonly />
												</div>
												<div class="row features_padding">
													<input type="text" class="features_date" id="features5_2_date" readonly />
												</div>
												<div class="row  features_padding">
													<div id="features5_2_editor" class="features_editor">
													</div>
												</div>
											</div>
										</div>
									</div>

									<br>
								</div>
							</fieldset>							
							</div>
						</div>
					</div>
					</div>
				</div>
			</div>
		</div>
	</div>

<div id="mediaManagerModal" class="reveal-modal" data-reveal>
</div>

<div id="forumModal" class="reveal-modal small" data-reveal>
	<div class="large-12 columns">
		<div class="row">
			<div class="large-2 columns">
				&nbsp;
			</div>
			<div class="large-2 columns">
				<label for="media_forum_url" class="right inline"><%=registry.getStringOfLanguage("media.url", language) %></label>
			</div>
			<div class="large-6 columns">
				<input id="media_forum_url" type="text" placeholder="<%=registry.getStringOfLanguage("placeholder.url", language) %>" value="" />
			</div>
			<div class="large-2 columns">
				&nbsp;
			</div>
		</div>			
		<div class="row">
			<div class="large-2 columns">
				&nbsp;
			</div>
			<div class="large-2 columns">
				<label for="media_forum_width" class="right inline"><%=registry.getStringOfLanguage("media.width", language) %></label>
			</div>
			<div class="large-6 columns">
				<input id="media_forum_width" type="text" placeholder="<%=registry.getStringOfLanguage("placeholder.value", language) %>" value="" />
			</div>
			<div class="large-2 columns">
				&nbsp;
			</div>
		</div>			
		<div class="row">
			<div class="large-2 columns">
				&nbsp;
			</div>
			<div class="large-2 columns">
				<label for="media_forum_height" class="right inline"><%=registry.getStringOfLanguage("media.height", language) %></label>
			</div>
			<div class="large-6 columns">
				<input id="media_forum_height" type="text" placeholder="<%=registry.getStringOfLanguage("placeholder.value", language) %>" value="" />
			</div>
			<div class="large-2 columns">
				&nbsp;
			</div>
		</div>			
		<div class="row right" style="margin-top: 20px;">
			<a href="javascript:addForum()" class="button medium right"><%=registry.getStringOfLanguage("btn.add", language) %></a>
		</div>			
	</div>
	<a class="close-reveal-modal">&#215;</a>
</div>

	<input type="hidden" id="basePath" value="<%=basePath %>" />
	<input type="hidden" id="resPath" value="<%=resPath %>" />

	<input type="hidden" id="alert_select_template" value="<%=registry.getStringOfLanguage("features.alert.template", language) %>" />
	<input type="hidden" id="alert_select_window" value="<%=registry.getStringOfLanguage("features.alert.window", language) %>" />

	<input type="hidden" id="validate_url_msg" value="<%=registry.getStringOfLanguage("validate.url", language) %>" />
	<input type="hidden" id="validate_length_msg" value="<%=registry.getStringOfLanguage("validate.length", language) %>" />

	<script src="<%=resPath%>bower_components/jquery/dist/jquery.min.js"></script>
	<script src="<%=resPath%>bower_components/velocity/velocity.min.js"></script>
	<script src="<%=resPath%>bower_components/foundation/js/foundation.min.js"></script>
	<script src="<%=resPath%>bower_components/jquery-ui/jquery-ui.min.js"></script>
	<script src="<%=resPath%>js/foundation/foundation-datepicker.js"></script>
	<script src="<%=resPath%>js/myeditor.js"></script>
	<script src="<%=resPath%>js/app.js"></script>
	
	<script src="<%=resPath%>js/features.js"></script>

<%@include file="jsp/footer.jsp"%>