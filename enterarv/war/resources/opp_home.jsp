<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String basepath = request.getContextPath();
%>
<%@include file="../variable.jsp"%>
<%@include file="jsp/opp_header.jsp"%>

<%@include file="jsp/opp_topbar.jsp"%>

<div class="row" id="wizard">
	<form action="" method="GET">
		<div class="large-3 medium-3 columns paso1">
			<div class="panel">
				<h3>
					<i class="fa fa-angle-right fa-fw"></i> <%=registry.getStringOfLanguage("opport.step", language) %> <span>1</span>
				</h3>
				
				<h5><%=registry.getStringOfLanguage("opport.step1.desc", language) %></h5>
				<a href="#" id="inmueble_btn" class="button round opport"><img src="<%=resPath%>images/inmueble.svg" alt=""><span><%=registry.getStringOfLanguage("opport.step.fur", language) %></span></a> 
				<a href="#" id="automovil_btn" class="button round opport"><img src="<%=resPath%>images/automovil.svg" alt=""><span><%=registry.getStringOfLanguage("opport.step.car", language) %></span></a> 
				<a href="<%=basePath %>opportunities/inmueble_a_membresia.html" target="_blank" id="inmueble_adjudicado_btn" class="button round"><img src="<%=resPath%>images/inmueble.svg" alt=""><span><%=registry.getStringOfLanguage("opport.step.fur", language) %> <%=registry.getStringOfLanguage("opport.step.award", language) %></span></a>
				<a href="#" id="automovil_adjudicado_btn" class="button round opport"><img src="<%=resPath%>images/automovil.svg" alt=""><span><%=registry.getStringOfLanguage("opport.step.car", language) %> <%=registry.getStringOfLanguage("opport.step.award", language) %></span></a>
				<a href="#" id="varios_btn" class="button round opport"><img src="<%=resPath%>images/varios.svg" alt=""><span><%=registry.getStringOfLanguage("opport.step.srv", language) %></span></a>
			</div>
		</div>
		<div class="large-3 medium-3 columns paso2">
			<div class="panel">
				<h3>
					<i class="fa fa-angle-right fa-fw"></i> <%=registry.getStringOfLanguage("opport.step", language) %> <span>2</span>
				</h3>
				<h5><%=registry.getStringOfLanguage("opport.step2.desc", language) %></h5>
				<img src="<%=resPath%>images/preloader.gif" class="preloader-image" alt="">
				<div class="loader"></div>
			</div>
		</div>
		<div class="large-3 medium-3 columns paso3">
			<div class="panel">
				<h3>
					<i class="fa fa-angle-right fa-fw"></i> <%=registry.getStringOfLanguage("opport.step", language) %> <span>3</span>
				</h3>
				<h5><%=registry.getStringOfLanguage("opport.step3.desc", language) %></h5>
				<img src="<%=resPath%>images/preloader.gif" class="preloader-image" alt="">
				<div class="loader"></div>
			</div>
		</div>
		<div class="large-3 medium-3 columns paso4">
			<div class="panel">
				<h3>
					<i class="fa fa-angle-right fa-fw"></i> <%=registry.getStringOfLanguage("opport.step", language) %> <span>4</span>
				</h3>
				<h5><%=registry.getStringOfLanguage("opport.step4.desc", language) %></h5>
				<a href="#" class="button round submit"><%=registry.getStringOfLanguage("btn.search", language) %> <i class="fa fa-angle-right fa-lg"></i></a>
			</div>
		</div>
	</form>
</div>

<div class="row" id="results">
	<div class="large-12 columns">
		<h3>
			<i class="fa fa-angle-right fa-fw"></i> <%=registry.getStringOfLanguage("opport.result", language) %>
		</h3>
		<img src="<%=resPath%>images/preloader.gif" class="preloader-image" alt="">
		<div class="loader"></div>
	</div>
</div>
<div id="resultsModal" class="reveal-modal" data-reveal></div>

<input type="hidden" id="basePath" value="<%=basePath%>">
<input type="hidden" id="resPath" value="<%=resPath%>">

<!--[if lte IE 9]>
	<script src="<%=resPath%>bower_components/REM-unit-polyfill/js/rem.min.js"></script>
	<script src="<%=resPath%>bower_components/jquery-legacy/jquery.min.js"></script>
<![endif]-->

<script src="<%=resPath%>bower_components/jquery/dist/jquery.min.js"></script>
<script src="<%=resPath%>bower_components/velocity/velocity.min.js"></script>
<script src="<%=resPath%>bower_components/foundation/js/foundation.min.js"></script>
<script src="<%=resPath%>js/jquery/jquery.dataTables.min.js"></script>
<script src="<%=resPath%>js/foundation/dataTables.foundation.min.js"></script>
<script src="<%=resPath%>js/foundation/dataTables.responsive.js"></script>
<script src="<%=resPath%>js/opp_home.js"></script>
<script src="<%=resPath%>js/opp_app.js"></script>

<%@include file="jsp/opp_footer.jsp"%>