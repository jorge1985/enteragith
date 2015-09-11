<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="../variable.jsp"%>
<%@include file="jsp/header.jsp"%>

	<%@include file="jsp/topbar.jsp"%>

	<div id="content-wrapper">

	<%@include file="jsp/menubar.jsp"%>
		
		<div class="small-10 medium-8 large-9 columns">
			<div id="content">
				<h1 class="main-title"><%=registry.getStringOfLanguage("options.title", language) %></h1>

				<div id="options">

<div id="msg_alert">
	<img id="msg_alert_progress" src="<%=resPath %>images/progress.gif">
	<span>Message</span>
</div>
					
					<div class="row left">
						<div class="large-6 columns">
							<fieldset>
								<legend><%=registry.getStringOfLanguage("options.ips", language) %></legend>
								<div class="row collapse">
							        <div class="small-9 columns">
							          <input type="text" id="allow_ip" placeholder="<%=registry.getStringOfLanguage("placeholder.value", language) %>" maxlength="100" />
							        </div>
							        <div class="small-3 columns">
							          <a href="javascript:add('ip')" class="button medium postfix"><%=registry.getStringOfLanguage("btn.add", language) %></a>
							        </div>						
								</div>
								<div class="row">
									<div class="small-12 columns">
										<select id="allow_ips" size="10" style="height: 300px;">
										</select>
									</div>
								</div>
							</fieldset>							
						</div>
						
						<div class="large-6 columns">
							<fieldset>
								<legend><%=registry.getStringOfLanguage("options.host", language) %></legend>
								<div class="row collapse">
							        <div class="small-9 columns">
							          <input type="text" id="allow_host" placeholder="<%=registry.getStringOfLanguage("placeholder.value", language) %>" maxlength="100" />
							        </div>
							        <div class="small-3 columns">
							          <a href="javascript:add('host')" class="button postfix"><%=registry.getStringOfLanguage("btn.add", language) %></a>
							        </div>						
								</div>
								<div class="row">
									<div class="small-12 columns">
										<select id="allow_hosts" size="10" style="height: 300px;">
										</select>
									</div>
								</div>
							</fieldset>							
						</div>
					</div>
					
					<div class="row left">
						<div class="large-12 columns">
							<label><%=registry.getStringOfLanguage("options.online", language) %></label> 
							<input type="radio" name="online" value="001" id="onlineYes" />
							<label for="onlineYes"><%=registry.getStringOfLanguage("yes", language) %></label> 
							<input type="radio" name="online" value="002" id="onlineNo" checked="checked" />
							<label for="onlineNo"><%=registry.getStringOfLanguage("no", language) %></label>
						</div>
					</div>
					
					<div class="row left" style="margin-top: 30px;">
						<div class="large-12 columns">
							<button type="button" class="medium" id="btnSubmit"><%=registry.getStringOfLanguage("btn.submit", language) %></button>							
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
	<script src="<%=resPath%>js/app.js"></script>
	
	<script src="<%=resPath%>js/options.js"></script>

<%@include file="jsp/footer.jsp"%>