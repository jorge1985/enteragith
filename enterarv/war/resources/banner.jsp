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
				<h1 class="main-title"><%=registry.getStringOfLanguage("banner.title", language) %></h1>

				<div id="options">

<div id="msg_alert">
	<img id="msg_alert_progress" src="<%=resPath %>images/progress.gif">
	<span>Message</span>
</div>
					
					<div class="row left" style="margin-top: 10px;">
						<h3><%=registry.getStringOfLanguage("banner.home", language) %></h3>
						<hr>
						<h5><%=registry.getStringOfLanguage("banner.bottom", language) %></h5>
						<div class="large-6 columns">
							<div class="row">
								<div class="large-12 columns">
								
								<c:forEach begin="1" end="4" var="item">									
									<div class="row banner-window">
										<h6>Banner ${item }</h6>
										<hr>
										<div class="large-4 columns">
											<img id="banner_image${item }" pos="${item }" class="banner-image" style="width: 100%;" src="" before="" alt="<%=registry.getStringOfLanguage("placeholder.empty", language) %>">
										</div>
										<div class="large-8 columns">
											<div class="row">
										        <div class="small-4 columns">
										        	<label class="right inline"><%=registry.getStringOfLanguage("banner.banner_image", language) %></label>
										        </div>
										        <div class="small-8 columns">
													<a href="javascript:selectImage('${item }')" class="button small" style="width:100%;"><%=registry.getStringOfLanguage("media.title", language) %></a>
													<a href="javascript:resetImage('${item }')" class="button small" style="width:100%;"><%=registry.getStringOfLanguage("btn.reset", language) %></a>
										        </div>
											</div>
											<div class="row">
										        <div class="small-4 columns">
													<label for="banner_link${item }" class="right inline"><%=registry.getStringOfLanguage("banner.link", language) %></label> 
										        </div>
										        <div class="small-8 columns">
													<input id="banner_link${item }" pos="${item }" class="banner-link" type="text" placeholder="<%=registry.getStringOfLanguage("placeholder.url", language) %>" />
										        </div>
											</div>
											<div class="row">
										        <div class="small-4 columns">
													<label for="banner_target${item }" class="right inline"><%=registry.getStringOfLanguage("banner.target", language) %></label> 
										        </div>
										        <div class="small-8 columns">
													<select id="banner_target${item }" pos="${item }" class="banner-target">
													<option value="1">Blank</option>
													<option value="2">Self</option>
													</select>
										        </div>
											</div>
										</div>
									</div>
								</c:forEach>
									
								</div>
							</div>
						</div>
						
						<div class="large-6 columns">
							<div class="row">
								<div class="large-12 columns">
								
								<c:forEach begin="5" end="8" var="item">									
									<div class="row banner-window">
										<h6>Banner ${item }</h6>
										<hr>
										<div class="large-4 columns">
											<img id="banner_image${item }" pos="${item }" class="banner-image" style="width: 100%;" src="" before="" alt="<%=registry.getStringOfLanguage("placeholder.empty", language) %>">
										</div>
										<div class="large-8 columns">
											<div class="row">
										        <div class="small-4 columns">
										        	<label class="right inline"><%=registry.getStringOfLanguage("banner.banner_image", language) %></label>
										        </div>
										        <div class="small-8 columns">
													<a href="javascript:selectImage('${item }')" class="button small" style="width:100%;"><%=registry.getStringOfLanguage("media.title", language) %></a>
													<a href="javascript:resetImage('${item }')" class="button small" style="width:100%;"><%=registry.getStringOfLanguage("btn.reset", language) %></a>
										        </div>
											</div>
											<div class="row">
										        <div class="small-4 columns">
													<label for="banner_link${item }" class="right inline"><%=registry.getStringOfLanguage("banner.link", language) %></label> 
										        </div>
										        <div class="small-8 columns">
													<input id="banner_link${item }" pos="${item }" class="banner-link" type="text" placeholder="<%=registry.getStringOfLanguage("placeholder.url", language) %>" />
										        </div>
											</div>
											<div class="row">
										        <div class="small-4 columns">
													<label for="banner_target${item }" class="right inline"><%=registry.getStringOfLanguage("banner.target", language) %></label> 
										        </div>
										        <div class="small-8 columns">
													<select id="banner_target${item }" pos="${item }" class="banner-target">
													<option value="1">Blank</option>
													<option value="2">Self</option>
													</select>
										        </div>
											</div>
										</div>
									</div>
								</c:forEach>
									
								</div>
							</div>
						</div>
					</div>

					<div class="row left" style="margin-top: 10px;">
						<hr>
						<h5><%=registry.getStringOfLanguage("banner.sidebar", language) %></h5>
						<div class="large-6 columns">
							<div class="row">
								<div class="large-12 columns">
								
								<c:forEach begin="9" end="10" var="item">									
									<div class="row banner-window">
										<h6>Banner ${item-8 }</h6>
										<hr>
										<div class="large-4 columns">
											<img id="banner_image${item }" pos="${item }" class="banner-image" style="width: 100%;" src="" before="" alt="<%=registry.getStringOfLanguage("placeholder.empty", language) %>">
										</div>
										<div class="large-8 columns">
											<div class="row">
										        <div class="small-4 columns">
										        	<label class="right inline"><%=registry.getStringOfLanguage("banner.banner_image", language) %></label>
										        </div>
										        <div class="small-8 columns">
													<a href="javascript:selectImage('${item }')" class="button small" style="width:100%;"><%=registry.getStringOfLanguage("media.title", language) %></a>
													<a href="javascript:resetImage('${item }')" class="button small" style="width:100%;"><%=registry.getStringOfLanguage("btn.reset", language) %></a>
										        </div>
											</div>
											<div class="row">
										        <div class="small-4 columns">
													<label for="banner_link${item }" class="right inline"><%=registry.getStringOfLanguage("banner.link", language) %></label> 
										        </div>
										        <div class="small-8 columns">
													<input id="banner_link${item }" pos="${item }" class="banner-link" type="text" placeholder="<%=registry.getStringOfLanguage("placeholder.url", language) %>" />
										        </div>
											</div>
											<div class="row">
										        <div class="small-4 columns">
													<label for="banner_target${item }" class="right inline"><%=registry.getStringOfLanguage("banner.target", language) %></label> 
										        </div>
										        <div class="small-8 columns">
													<select id="banner_target${item }" pos="${item }" class="banner-target">
													<option value="1">Blank</option>
													<option value="2">Self</option>
													</select>
										        </div>
											</div>
										</div>
									</div>
								</c:forEach>
									
								</div>
							</div>
						</div>
						
						<div class="large-6 columns">
							<div class="row">
								<div class="large-12 columns">
								
								<c:forEach begin="11" end="11" var="item">									
									<div class="row banner-window">
										<h6>Banner ${item-8 }</h6>
										<hr>
										<div class="large-4 columns">
											<img id="banner_image${item }" pos="${item }" class="banner-image" style="width: 100%;" src="" before="" alt="<%=registry.getStringOfLanguage("placeholder.empty", language) %>">
										</div>
										<div class="large-8 columns">
											<div class="row">
										        <div class="small-4 columns">
										        	<label class="right inline"><%=registry.getStringOfLanguage("banner.banner_image", language) %></label>
										        </div>
										        <div class="small-8 columns">
													<a href="javascript:selectImage('${item }')" class="button small" style="width:100%;"><%=registry.getStringOfLanguage("media.title", language) %></a>
													<a href="javascript:resetImage('${item }')" class="button small" style="width:100%;"><%=registry.getStringOfLanguage("btn.reset", language) %></a>
										        </div>
											</div>
											<div class="row">
										        <div class="small-4 columns">
													<label for="banner_link${item }" class="right inline"><%=registry.getStringOfLanguage("banner.link", language) %></label> 
										        </div>
										        <div class="small-8 columns">
													<input id="banner_link${item }" pos="${item }" class="banner-link" type="text" placeholder="<%=registry.getStringOfLanguage("placeholder.url", language) %>" />
										        </div>
											</div>
											<div class="row">
										        <div class="small-4 columns">
													<label for="banner_target${item }" class="right inline"><%=registry.getStringOfLanguage("banner.target", language) %></label> 
										        </div>
										        <div class="small-8 columns">
													<select id="banner_target${item }" pos="${item }" class="banner-target">
													<option value="1">Blank</option>
													<option value="2">Self</option>
													</select>
										        </div>
											</div>
										</div>
									</div>
								</c:forEach>
									
								</div>
							</div>
						</div>
					</div>

					<div class="row left" style="margin-top: 30px;">
						<h3><%=registry.getStringOfLanguage("banner.internal", language) %></h3>
						<hr>
						<h5><%=registry.getStringOfLanguage("banner.bottom", language) %></h5>
						<div class="large-6 columns">
							<div class="row">
								<div class="large-12 columns">
								
								<c:forEach begin="12" end="13" var="item">									
									<div class="row banner-window">
										<h6>Banner ${item-11 }</h6>
										<hr>
										<div class="large-4 columns">
											<img id="banner_image${item }" pos="${item }" class="banner-image" style="width: 100%;" src="" before="" alt="<%=registry.getStringOfLanguage("placeholder.empty", language) %>">
										</div>
										<div class="large-8 columns">
											<div class="row">
										        <div class="small-4 columns">
										        	<label class="right inline"><%=registry.getStringOfLanguage("banner.banner_image", language) %></label>
										        </div>
										        <div class="small-8 columns">
													<a href="javascript:selectImage('${item }')" class="button small" style="width:100%;"><%=registry.getStringOfLanguage("media.title", language) %></a>
													<a href="javascript:resetImage('${item }')" class="button small" style="width:100%;"><%=registry.getStringOfLanguage("btn.reset", language) %></a>
										        </div>
											</div>
											<div class="row">
										        <div class="small-4 columns">
													<label for="banner_link${item }" class="right inline"><%=registry.getStringOfLanguage("banner.link", language) %></label> 
										        </div>
										        <div class="small-8 columns">
													<input id="banner_link${item }" pos="${item }" class="banner-link" type="text" placeholder="<%=registry.getStringOfLanguage("placeholder.url", language) %>" />
										        </div>
											</div>
											<div class="row">
										        <div class="small-4 columns">
													<label for="banner_target${item }" class="right inline"><%=registry.getStringOfLanguage("banner.target", language) %></label> 
										        </div>
										        <div class="small-8 columns">
													<select id="banner_target${item }" pos="${item }" class="banner-target">
													<option value="1">Blank</option>
													<option value="2">Self</option>
													</select>
										        </div>
											</div>
										</div>
									</div>
								</c:forEach>
									
								</div>
							</div>
						</div>
						
						<div class="large-6 columns">
							<div class="row">
								<div class="large-12 columns">
								
								<c:forEach begin="14" end="15" var="item">									
									<div class="row banner-window">
										<h6>Banner ${item-11 }</h6>
										<hr>
										<div class="large-4 columns">
											<img id="banner_image${item }" pos="${item }" class="banner-image" style="width: 100%;" src="" before="" alt="<%=registry.getStringOfLanguage("placeholder.empty", language) %>">
										</div>
										<div class="large-8 columns">
											<div class="row">
										        <div class="small-4 columns">
										        	<label class="right inline"><%=registry.getStringOfLanguage("banner.banner_image", language) %></label>
										        </div>
										        <div class="small-8 columns">
													<a href="javascript:selectImage('${item }')" class="button small" style="width:100%;"><%=registry.getStringOfLanguage("media.title", language) %></a>
													<a href="javascript:resetImage('${item }')" class="button small" style="width:100%;"><%=registry.getStringOfLanguage("btn.reset", language) %></a>
										        </div>
											</div>
											<div class="row">
										        <div class="small-4 columns">
													<label for="banner_link${item }" class="right inline"><%=registry.getStringOfLanguage("banner.link", language) %></label> 
										        </div>
										        <div class="small-8 columns">
													<input id="banner_link${item }" pos="${item }" class="banner-link" type="text" placeholder="<%=registry.getStringOfLanguage("placeholder.url", language) %>" />
										        </div>
											</div>
											<div class="row">
										        <div class="small-4 columns">
													<label for="banner_target${item }" class="right inline"><%=registry.getStringOfLanguage("banner.target", language) %></label> 
										        </div>
										        <div class="small-8 columns">
													<select id="banner_target${item }" pos="${item }" class="banner-target">
													<option value="1">Blank</option>
													<option value="2">Self</option>
													</select>
										        </div>
											</div>
										</div>
									</div>
								</c:forEach>
									
								</div>
							</div>
						</div>
					</div>

					<div class="row left" style="margin-top: 10px;">
						<hr>
						<h5><%=registry.getStringOfLanguage("banner.sidebar", language) %></h5>
						<div class="large-6 columns">
							<div class="row">
								<div class="large-12 columns">
								
								<c:forEach begin="16" end="17" var="item">									
									<div class="row banner-window">
										<h6>Banner ${item-15 }</h6>
										<hr>
										<div class="large-4 columns">
											<img id="banner_image${item }" pos="${item }" class="banner-image" style="width: 100%;" src="" before="" alt="<%=registry.getStringOfLanguage("placeholder.empty", language) %>">
										</div>
										<div class="large-8 columns">
											<div class="row">
										        <div class="small-4 columns">
										        	<label class="right inline"><%=registry.getStringOfLanguage("banner.banner_image", language) %></label>
										        </div>
										        <div class="small-8 columns">
													<a href="javascript:selectImage('${item }')" class="button small" style="width:100%;"><%=registry.getStringOfLanguage("media.title", language) %></a>
													<a href="javascript:resetImage('${item }')" class="button small" style="width:100%;"><%=registry.getStringOfLanguage("btn.reset", language) %></a>
										        </div>
											</div>
											<div class="row">
										        <div class="small-4 columns">
													<label for="banner_link${item }" class="right inline"><%=registry.getStringOfLanguage("banner.link", language) %></label> 
										        </div>
										        <div class="small-8 columns">
													<input id="banner_link${item }" pos="${item }" class="banner-link" type="text" placeholder="<%=registry.getStringOfLanguage("placeholder.url", language) %>" />
										        </div>
											</div>
											<div class="row">
										        <div class="small-4 columns">
													<label for="banner_target${item }" class="right inline"><%=registry.getStringOfLanguage("banner.target", language) %></label> 
										        </div>
										        <div class="small-8 columns">
													<select id="banner_target${item }" pos="${item }" class="banner-target">
													<option value="1">Blank</option>
													<option value="2">Self</option>
													</select>
										        </div>
											</div>
										</div>
									</div>
								</c:forEach>
									
								</div>
							</div>
						</div>
						
						<div class="large-6 columns">
							<div class="row">
								<div class="large-12 columns">
								
								<c:forEach begin="18" end="19" var="item">									
									<div class="row banner-window">
										<h6>Banner ${item-15 }</h6>
										<hr>
										<div class="large-4 columns">
											<img id="banner_image${item }" pos="${item }" class="banner-image" style="width: 100%;" src="" before="" alt="<%=registry.getStringOfLanguage("placeholder.empty", language) %>">
										</div>
										<div class="large-8 columns">
											<div class="row">
										        <div class="small-4 columns">
										        	<label class="right inline"><%=registry.getStringOfLanguage("banner.banner_image", language) %></label>
										        </div>
										        <div class="small-8 columns">
													<a href="javascript:selectImage('${item }')" class="button small" style="width:100%;"><%=registry.getStringOfLanguage("media.title", language) %></a>
													<a href="javascript:resetImage('${item }')" class="button small" style="width:100%;"><%=registry.getStringOfLanguage("btn.reset", language) %></a>
										        </div>
											</div>
											<div class="row">
										        <div class="small-4 columns">
													<label for="banner_link${item }" class="right inline"><%=registry.getStringOfLanguage("banner.link", language) %></label> 
										        </div>
										        <div class="small-8 columns">
													<input id="banner_link${item }" pos="${item }" class="banner-link" type="text" placeholder="<%=registry.getStringOfLanguage("placeholder.url", language) %>" />
										        </div>
											</div>
											<div class="row">
										        <div class="small-4 columns">
													<label for="banner_target${item }" class="right inline"><%=registry.getStringOfLanguage("banner.target", language) %></label> 
										        </div>
										        <div class="small-8 columns">
													<select id="banner_target${item }" pos="${item }" class="banner-target">
													<option value="1">Blank</option>
													<option value="2">Self</option>
													</select>
										        </div>
											</div>
										</div>
									</div>
								</c:forEach>
									
								</div>
							</div>
						</div>
					</div>
					
					<div class="row left" style="margin-top: 10px;">
						<hr>
						<button type="button" class="medium" id="btnSubmit"><%=registry.getStringOfLanguage("btn.submit", language) %></button>					
					</div>
					
					<div class="row">
						<br>
					</div>
					
				</div>
			</div>
		</div>
	</div>

<div id="mediaManagerModal" class="reveal-modal" data-reveal>
</div>

	<input type="hidden" id="basePath" value="<%=basePath %>" />
	<input type="hidden" id="resPath" value="<%=resPath %>" />
	
	<script src="<%=resPath%>bower_components/jquery/dist/jquery.min.js"></script>
	<script src="<%=resPath%>bower_components/velocity/velocity.min.js"></script>
	<script src="<%=resPath%>bower_components/foundation/js/foundation.min.js"></script>
	<script src="<%=resPath%>bower_components/jquery-ui/jquery-ui.min.js"></script>
	<script src="<%=resPath%>js/app.js"></script>
	
	<script src="<%=resPath%>js/banner.js"></script>

<%@include file="jsp/footer.jsp"%>