<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="../variable.jsp"%>
<%@include file="jsp/header.jsp"%>

	<%@include file="jsp/topbar.jsp"%>

	<div id="content-wrapper">

	<%@include file="jsp/menubar.jsp"%>
		
		<div class="small-10 medium-8 large-9 columns">
			<div id="content">
				<h1 class="main-title">${content_type}&nbsp;<%=registry.getStringOfLanguage("content.title.deatil", language) %></h1>

<div id="msg_alert">
	<img id="msg_alert_progress" src="<%=resPath %>images/progress.gif">
	<span>Message</span>
</div>

				<div id="edit_contents">

					<div class="row left" id="content_detail">
						<div class="large-12 columns">
							<div class="row describe">
								<span class="label describe"><%=registry.getStringOfLanguage("content.table.title", language) %>&nbsp;</span>
								<span class="large">${content.name }</span>
							</div>
							<div class="row describe">
								<span class="label describe"><%=registry.getStringOfLanguage("content.table.date", language) %>&nbsp;</span>
								<span class="large">${content.validityStart }&nbsp;~&nbsp;${content.validityEnd }</span>
							</div>							
							<div class="row describe">
								<span class="label describe"><%=registry.getStringOfLanguage("content.table.tag", language) %>&nbsp;</span>
								<c:forEach var="item" items="${tags}">
									<span class="label round">${item.value }</span>
								</c:forEach>
							</div>							
							<div class="row describe">
								<div class="large-8 columns" style="padding-left: 0px;">
									<span class="label describe"><%=registry.getStringOfLanguage("content.table.channel", language) %>&nbsp;</span>
									<div>
									<c:forEach var="item" items="${channel}">
										<span class="large" style="margin-left: 30px;">&nbsp;${item.fullName }</span><br>
									</c:forEach>
									</div>
								</div>
								<div class="large-4 columns" style="padding-left: 0px;">
									<span class="label describe"><%=registry.getStringOfLanguage("content.table.security", language) %>&nbsp;</span>
									<div>
									<c:forEach var="item" items="${channel}">
										<span class="large" style="margin-left: 60px;">&nbsp;${item.securityLevelName }</span><br>
									</c:forEach>
									</div>
								</div>
							</div>							
							<div class="row describe">
													
								<span class="label describe"><%=registry.getStringOfLanguage("content.table.status", language) %>&nbsp;</span>
								<c:if test="${content.active=='1' }">
									<span class="label success"><%=registry.getStringOfLanguage("content.status.active", language) %></span>
								</c:if>
								<c:if test="${content.showIn=='1' || content.showIn=='3' }">
									<span class="label"><%=registry.getStringOfLanguage("content.status.featured", language) %></span>
								</c:if>
								<c:if test="${content.status=='D'}">
									<span class="label alert"><%=registry.getStringOfLanguage("content.status.deleted", language) %></span>
								</c:if>
<%-- 								<c:if test="${content.status=='A' && content.active=='1'}"> --%>
<%-- 									<span class="label secondary"><%=registry.getStringOfLanguage("content.status.none", language) %></span> --%>
<%-- 								</c:if> --%>
							</div>
							
							<div class="row describe right">
								<a href="javascript:editContent()" class="button medium right"><%=registry.getStringOfLanguage("btn.edit", language) %></a>
							</div>
						</div>
					</div>
					
					<div class="row left content_detail" id="content_faqs">
						<div class="large-12 columns">
							<div class="large-6 columns">
								<form id="content_faqs_form" method="post" action="#" data-abide>
								<div class="row">
									<label><%=registry.getStringOfLanguage("content.input.question", language) %> 
									<input id="content_faqs_question" type="text" placeholder="<%=registry.getStringOfLanguage("placeholder.value", language) %>" required="required" />
									</label>
									<small class="error"><%=registry.getStringOfLanguage("validate.question", language) %></small>
								</div>		
													
								<div class="row">
									<label><%=registry.getStringOfLanguage("content.input.answer", language) %>
									<textarea rows="10" cols="10" id="content_faqs_answer" placeholder="<%=registry.getStringOfLanguage("placeholder.value", language) %>" required="required" ></textarea> 
									</label>
									<small class="error"><%=registry.getStringOfLanguage("validate.answer", language) %></small>
								</div>		
								
								<button type="button" class="medium" id="btnResetFAQs"><%=registry.getStringOfLanguage("btn.reset", language) %></button>
								<button type="submit" class="medium" id="btnSubmitFAQs"><%=registry.getStringOfLanguage("btn.submit", language) %></button>
								</form>
								
								<input type="hidden" id="content_faqs_id" value="" />
								<input type="hidden" id="content_faqs_type" value="" />
							</div>
							
							<div class="large-6 columns">
								<div class="row describe collapse">
									<ul id="content_faqs_list" style="margin-top: 30px; list-style: none;">
									</ul>
								</div>							
							</div>
						</div>
					</div>

					<div class="row left content_detail" id="content_poll">
						<div class="large-12 columns">
							<form id="content_poll_form" method="post" action="#" data-abide>
							<div class="row">
								<div class="large-6 columns">
									<div class="row">
										<label><%=registry.getStringOfLanguage("content.input.question", language) %> 
										<input id="content_poll_question" type="text" placeholder="<%=registry.getStringOfLanguage("placeholder.value", language) %>" required="required" />
										</label>
										<small class="error"><%=registry.getStringOfLanguage("validate.question", language) %></small>
									</div>		
	
									<div class="row">
										<input id="content_poll_open_question" type="checkbox" />
										<label for="content_poll_open_question"><%=registry.getStringOfLanguage("content.input.open_question", language) %></label> 
									</div>		
															
									<div class="row">
										<label id="label_poll_answer"><%=registry.getStringOfLanguage("content.input.answer", language) %></label>
										<div class="large-12 columns" id="content_poll_answer">
										</div>
									</div>		
	
									<div class="row" id="content_poll_result" style="margin-top: 20px;">
										<div class="large-12 columns">
											<label><%=registry.getStringOfLanguage("content.input.result", language) %></label>
											<div class="row left">
												<div class="large-12 columns" style="display: inline-flex;">
													<canvas id="myChart" width="200" height="200"></canvas>
													<div id="myChart_legend">
													</div>
												</div>
											</div>										
										</div>
									</div>		
								</div>
								
								<div class="large-6 columns">
									<div class="row describe collapse">
										<ul id="content_poll_list" style="margin-top: 30px; list-style: none;">
										</ul>
									</div>							
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<a href="javascript:showPollResult()" id="btnResultPoll" class="button medium" style="background-color: #c0c0c0;"><%=registry.getStringOfLanguage("btn.show_result", language) %></a>
									<a href="javascript:addPollAnswer()" id="btnAddPoll" class="button medium" style="background-color: #c0c0c0;"><%=registry.getStringOfLanguage("btn.add_answer", language) %></a>
									<button type="button" class="medium" id="btnResetPoll"><%=registry.getStringOfLanguage("btn.reset", language) %></button>
									<button type="submit" class="medium" id="btnSubmitPoll"><%=registry.getStringOfLanguage("btn.submit", language) %></button>
								</div>
							</div>
							</form>
							
							<input type="hidden" id="content_poll_id" value="" />
							<input type="hidden" id="content_poll_type" value="" />
							
							<input type="hidden" id="content_poll_answer_msg" value="<%=registry.getStringOfLanguage("content.input.answer", language) %>" />
							<input type="hidden" id="content_poll_answer_placeholder_msg" value="<%=registry.getStringOfLanguage("placeholder.value", language) %>" />
							<input type="hidden" id="content_poll_answer_validate_msg" value="<%=registry.getStringOfLanguage("validate.answer", language) %>" />
						</div>
					</div>

					<div class="row left content_detail" id="content_file">
						<div class="large-12 columns">
							<div class="row describe">
								<form id="content_file_form" method="post" action="#" data-abide>
								<div class="row">
									<label><%=registry.getStringOfLanguage("content.input.description", language) %> 
									<textarea rows="10" cols="10" id="content_file_description" placeholder="<%=registry.getStringOfLanguage("placeholder.value", language) %>" required="required" ></textarea> 
									</label>
									<small class="error"><%=registry.getStringOfLanguage("validate.description", language) %></small>
								</div>		

								<div class="row describe">
									<a href="javascript:selectFile('media')" class="button medium" style="background-color: #656565;"><%=registry.getStringOfLanguage("btn.select_file", language) %></a>
								</div>		
														
								<div class="row describe">
									<ul id="content_file_list" style="margin-top: 10px; list-style: none;">
									</ul>
								</div>		
								
								<br>
								
								<button type="button" class="medium" id="btnResetFile"><%=registry.getStringOfLanguage("btn.reset", language) %></button>
								<button type="submit" class="medium" id="btnSubmitFile"><%=registry.getStringOfLanguage("btn.submit", language) %></button>
								</form>
								
								<input type="hidden" id="content_file_validate_msg" value="<%=registry.getStringOfLanguage("validate.file", language) %>" />
							</div>
						</div>
					</div>

					<div class="row left content_detail" id="content_gallery">
						<div class="large-12 columns">
							<div class="row describe">
								<form id="content_gallery_form" method="post" action="#" data-abide>
								<div class="row">
									<label><%=registry.getStringOfLanguage("content.input.description", language) %> 
									<textarea rows="10" cols="10" id="content_gallery_detail" placeholder="<%=registry.getStringOfLanguage("placeholder.value", language) %>" required="required" ></textarea> 
									</label>
									<small class="error"><%=registry.getStringOfLanguage("validate.description", language) %></small>
								</div>		

								<div class="row describe">
									<a href="javascript:selectGallery('media')" class="button medium" style="background-color: #656565;"><%=registry.getStringOfLanguage("btn.select_image", language) %></a>
								</div>		
														
								<div class="row describe">
									<div class="container">
										<div class="image-row">
											<div class="image-set" id="content_gallery_list">
											</div>
										</div>
									</div>
								</div>		
								
								<br>
								
								<button type="button" class="medium" id="btnResetGallery"><%=registry.getStringOfLanguage("btn.reset", language) %></button>
								<button type="submit" class="medium" id="btnSubmitGallery"><%=registry.getStringOfLanguage("btn.submit", language) %></button>
								</form>
								
								<input type="hidden" id="content_gallery_validate_msg" value="<%=registry.getStringOfLanguage("validate.file", language) %>" />
							</div>
						</div>
					</div>
					
					<div class="row left content_detail" id="content_news">
						<div class="large-12 columns">
							<div class="row describe">
								<div class="row">
									<label><%=registry.getStringOfLanguage("content.input.content", language) %></label>
									<div>
										<div id="content_news_toolbar"></div>
										<textarea id="content_news_editor" name="content">
										</textarea>
									</div>
								</div>		

								<div class="row describe" style="margin-top: 50px;">
									  <a href="javascript:selectNews('media')" class="button medium" style="background-color: #656565;"><%=registry.getStringOfLanguage("btn.select_thumbnail", language) %></a>
								</div>		
														
								<div class="row">
									<div class="container">
										<div class="image-row">
											<img id="content_news_image" src="" key="" alt="">
										</div>
									</div>
								</div>		
								
								<br>
								
								<button type="button" class="medium" id="btnResetNews"><%=registry.getStringOfLanguage("btn.reset", language) %></button>
								<button type="submit" class="medium" id="btnSubmitNews"><%=registry.getStringOfLanguage("btn.submit", language) %></button>
								
								<input type="hidden" id="content_news_validate_input_msg" value="<%=registry.getStringOfLanguage("validate.content", language) %>" />
								<input type="hidden" id="content_news_validate_msg" value="<%=registry.getStringOfLanguage("validate.thumbnail", language) %>" />
							</div>
						</div>
					</div>
					
					<div class="row left content_detail" id="content_video">
						<div class="large-12 columns">
							<div class="row describe">
								<div class="row describe">
									<a href="javascript:selectVideo('media','video')" class="button medium" style="background-color: #656565;"><%=registry.getStringOfLanguage("btn.select_video", language) %></a>
									&nbsp;
									<a href="javascript:selectVideo('media','image')" class="button medium" style="background-color: #656565;"><%=registry.getStringOfLanguage("btn.select_thumbnail", language) %></a>
								</div>		
														
								<div class="row">
									<div class="large-6 columns">
										<div id="content_video_container">
									    	<!-- <video id="content_video_video" key="" src="" allowfullscreen data-src="" controls></video> -->
									  	</div>
								  	</div>									
								</div>		

								<div class="row describe">
									<img id="content_video_image" src="" key="" alt="">
								</div>		
								
								<br>
								
								<button type="button" class="medium" id="btnResetVideo"><%=registry.getStringOfLanguage("btn.reset", language) %></button>
								<button type="submit" class="medium" id="btnSubmitVideo"><%=registry.getStringOfLanguage("btn.submit", language) %></button>
								
								<input type="hidden" id="validate_no_result_msg" value="<%=registry.getStringOfLanguage("validate.result", language) %>" />
								<input type="hidden" id="content_video_validate_msg" value="<%=registry.getStringOfLanguage("validate.video_image", language) %>" />
								
							</div>
						</div>
					</div>
					

				</div>
			</div>
		</div>
	</div>

<div id="mediaManagerModal" class="reveal-modal" data-reveal>
</div>

<div id="mediaVideoModal" class="reveal-modal" data-reveal>
	<div class="row left">
		<ul id="video_list">
		</ul>
	</div>
	<a class="close-reveal-modal">&#215;</a>
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

	<form id="form_move" method="post" action="<%=basePath %>content/add.html">
		<input type="hidden" name="type" value="edit" />
		<input type="hidden" name="content_id" id="edit_content_id" value="${content_id }" />
	</form>
	
	<input type="hidden" id="content_type" value="${content.type }" />
	
	<input type="hidden" id="validate_url_msg" value="<%=registry.getStringOfLanguage("validate.url", language) %>" />
	<input type="hidden" id="validate_length_msg" value="<%=registry.getStringOfLanguage("validate.length", language) %>" />
	
	<script src="<%=resPath%>bower_components/jquery/dist/jquery.min.js"></script>
	<script src="<%=resPath%>bower_components/velocity/velocity.min.js"></script>
	<script src="<%=resPath%>bower_components/foundation/js/foundation.min.js"></script>
	<script src="<%=resPath%>bower_components/jquery-ui/jquery-ui.min.js"></script>
	<script src="<%=resPath%>js/swfobject.js"></script>
	<script src="<%=resPath%>js/chart/Chart.min.js"></script>
	<script src="<%=resPath%>js/myeditor.js"></script>
	
	<script src="<%=resPath%>js/lightbox/lightbox.min.js"></script>
	<script src="<%=resPath%>js/app.js"></script>

	<script src="<%=resPath%>js/edit_content.js"></script>
	
<%@include file="jsp/footer.jsp"%>