<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="../variable.jsp"%>
<%@include file="jsp/header.jsp"%>

	<%@include file="jsp/topbar.jsp"%>

	<div id="content-wrapper">
	
	<%@include file="jsp/menubar.jsp"%>
		
		<div class="small-10 medium-8 large-9 columns">
			<div id="content">
				<h1 class="main-title"><%=registry.getStringOfLanguage("dashboard.title", language) %></h1>

				<div id="dashboard">
					<div class="row left">
						<div class="large-4 small-12 columns">
							<div class="panel indicator">
								<div class="row">
									<div class="large-12 columns">
										<h2>
											<i class="fi-calendar indicator-icon"></i><%=registry.getStringOfLanguage("dashboard.visit_today", language) %>
										</h2>
									</div>
									<div class="small-6 columns result">
										<h3 id="total_visitor_today">0</h3>
										<h4><%=registry.getStringOfLanguage("dashboard.total", language) %></h4>
									</div>
									<div class="small-6 columns result">
										<h3 id="unique_visitor_today">0</h3>
										<h4><%=registry.getStringOfLanguage("dashboard.unique", language) %></h4>
									</div>
								</div>
							</div>
						</div>
						<div class="large-4 small-12 columns">
							<div class="panel indicator">
								<div class="row">
									<div class="large-12 columns">
										<h2>
											<i class="fi-calendar indicator-icon"></i><%=registry.getStringOfLanguage("dashboard.current_month", language) %>
										</h2>
									</div>
									<div class="small-6 columns result">
										<h3 id="total_visitor_month">0</h3>
										<h4><%=registry.getStringOfLanguage("dashboard.total", language) %></h4>
									</div>
									<div class="small-6 columns result">
										<h3 id="unique_visitor_month">0</h3>
										<h4><%=registry.getStringOfLanguage("dashboard.unique", language) %></h4>
									</div>
								</div>
							</div>
						</div>
						<div class="large-4 small-12 columns">
							<div class="panel indicator">
								<div class="row">
									<div class="large-12 columns">
										<h2>
											<i class="fi-clock indicator-icon"></i><%=registry.getStringOfLanguage("dashboard.visit_historic", language) %>
										</h2>
									</div>
									<div class="small-6 columns result">
										<h3 id="total_visitor_historic">0</h3>
										<h4><%=registry.getStringOfLanguage("dashboard.total", language) %></h4>
									</div>
									<div class="small-6 columns result">
										<h3 id="outside_visitor_historic">0</h3>
										<h4><%=registry.getStringOfLanguage("dashboard.outside", language) %></h4>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="row left">
						<div class="large-4 small-12 columns">
							<div class="panel indicator">
								<div class="row">
									<div class="large-12 columns">
										<h2>
											<i class="fi-torsos-all indicator-icon"></i><%=registry.getStringOfLanguage("dashboard.system_user", language) %>
										</h2>
									</div>
									<div class="small-6 columns result">
										<h3 id="total_system_user">0</h3>
										<h4><%=registry.getStringOfLanguage("dashboard.total", language) %></h4>
									</div>
									<div class="small-6 columns result">
										<h3 id="active_system_user">0</h3>
										<h4><%=registry.getStringOfLanguage("dashboard.active", language) %></h4>
									</div>
								</div>
							</div>
						</div>
						<div class="large-4 small-12 columns">
							<div class="panel indicator">
								<div class="row">
									<div class="large-12 columns">
										<h2>
											<i class="fi-results indicator-icon"></i><%=registry.getStringOfLanguage("dashboard.channel", language) %>
										</h2>
									</div>
									<div class="small-12 columns result">
										<h3 id="total_channels">0</h3>
										<h4><%=registry.getStringOfLanguage("dashboard.total", language) %></h4>
									</div>

								</div>
							</div>
						</div>
						<div class="large-4 small-12 columns">
							<div class="panel indicator">
								<div class="row">
									<div class="large-12 columns">
										<h2>
											<i class="fi-page indicator-icon"></i><%=registry.getStringOfLanguage("dashboard.content", language) %>
										</h2>
									</div>
									<div class="small-12 columns result">
										<h3 id="active_contents">0</h3>
										<h4><%=registry.getStringOfLanguage("dashboard.active", language) %></h4>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>


			</div>
		</div>
	</div>

	<script src="<%=resPath%>bower_components/jquery/dist/jquery.min.js"></script>
	<script src="<%=resPath%>bower_components/velocity/velocity.min.js"></script>
	<script src="<%=resPath%>bower_components/foundation/js/foundation.min.js"></script>
	<script src="<%=resPath%>js/app.js"></script>
	
	<script src="<%=resPath%>js/dashboard.js"></script>

<%@include file="jsp/footer.jsp"%>