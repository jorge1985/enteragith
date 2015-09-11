<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String basepath = request.getContextPath();
%>
<%@include file="../variable.jsp"%>
<%@include file="jsp/opp_header.jsp"%>

<%@include file="jsp/opp_topbar.jsp"%>

<div class="row" id="publi-content">
	<div class="large-12 columns">
		<h1>Publica tu anuncio</h1>
	</div>
	
	<div id="msg_alert" class="row">
		<img id="msg_alert_progress" src="<%=resPath %>images/progress.gif">
		<span>Message</span>
	</div>
	
	<div class="small-6 small-centered columns selecciona">
		<label for="tipo">Selecciona el tipo de publicación</label> 
		<select name="tipo" id="tipo">
			<option value="" selected>Selecciona</option>
			<option value="automovil">Automóvil</option>
			<option value="inmueble">Inmueble</option>
			<option value="varios">Varios</option>
		</select>
	</div>
	<img src="<%=resPath %>images/preloader.gif" class="preloader-image" alt="">
	<div id="form-loader" class="large-12 columns"></div>
</div>

<div id="condiciones" class="reveal-modal" data-reveal>
	<h3>OPORTUNIDADES BANCOMER</h3>
	<div class="scroll">
		<p>
			Bienvenido a Oportunidades Bancomer, una página de compra-venta
			diseñada especialmente para los empleados del Grupo Financiero BBVA
			Bancomer.
		</p>

		<p>
			A través de Oportunidades Bancomer, tu como empleado contarás
			con un medio seguro, sencillo y eficiente para la oferta y demanda de
			autos, casas, departamentos, terrenos, así como de otros tipos de
			bienes de uso personal o familiar, como son aparatos eléctricos,
			electrónicos, video juegos, muebles, mascotas entre otros.
		</p>

		<p>Es importante considerar lo siguiente:</p>

		<ul>
			<li>Las publicaciones serán permitidas sólo a los empleados del
				Grupo que tengan acceso a la TV IP BBVA Bancomer , ya sea desde su
				lugar de trabajo o bien desde donde cuenten con acceso a Internet.</li>
			<li>El empleado sólo podrá publicar para venta bienes muebles o
				inmuebles de uso personal o familiar, llenando correcta y
				completamente el formato preestablecido en el sistema.</li>
			<li>Asimismo, está prohibido la publicación de cualquier tipo de
				bienes o servicios que deriven de una actividad personal de índole
				comercial formal, de nosotros como empleados del Grupo y que
				pudieran derivar en situaciones de conflictos de intereses , de
				acuerdo a lo establecido en nuestro Código de Conducta Capítulo 6,
				Integridad Personal, puntos 6.2 al 6.6; y a lo considerado en los
				puntos 6.20 al 6.22, del Desarrollo de Otras Actividades.</li>
			<li>El uso de este medio es personalizado e intransferible ya
				que para publicar una oferta el sistema te pedirá tu número de
				registro, nombre y correo externo.</li>
			<li>El empleado es el responsable de darse de alta en el sistema
				de publicación, para lo cual deberá llenar correcta y completamente
				todos los campos solicitados en el formato de alta. De igual forma,
				es el responsable del uso que se le de a datos personales, como
				registro, password, etc.</li>
			<li>El empleado podrá dar de alta sus publicaciones en cualquier
				día y hora que lo desee, desde su lugar de oficina o bien desde
				cualquier lugar donde cuente con Internet y estas serán publicadas
				en automático.</li>
			<li>Los números telefónicos de contacto para el logro de la
				venta, deberán ser particulares de cada empleado o bien la extension
				interna de su area de trabajo dentro del Grupo.</li>
			<li>Las publicaciones permanecerán en Oportunidades Bancomer por
				un lapso de tiempo máximo de 30 días naturales. Si al término de
				este tiempo no se ha vendido el bien y el empleado desea que se siga
				anunciando, será necesario que lo vuelva a dar de alta.</li>
			<li>Las publicaciones no deberán contener por ninguna razón
				mensajes con fines publicitarios o descripción de otro producto que
				no sean los estipulados por el propio sistema.</li>
			<li>Las publicaciones hechas por dichos empleados serán
				revisadas periódicamente por el área de Comunicación Organizacional
				a fin de identificar cualquier anomalía o error en ellas.</li>
			<li>El uso indebido de este medio se considerará como una falta
				grave y causará sanciones conforme a la Normativa Interna del Grupo
				y a la Ley Federal del Trabajo.</li>
		</ul>
	</div>

	<div class="small-6 small-centered columns confirm-wrapper">
		<a class="close-reveal-modal button round tiny">Continuar <i class="fa fa-angle-right fa-fw"></i></a>
	</div>
</div>
<div id="resultsModal" class="reveal-modal" data-reveal></div>

<div id="uploadModal" class="reveal-modal" data-reveal>
</div>

<form id="form_move_myads" method="post" action="<%=basePath%>opportunities/myads.html">
</form>

<input type="hidden" id="basePath" value="<%=basePath%>">
<input type="hidden" id="resPath" value="<%=resPath%>">

<input type="hidden" id="publish_kind" value="">

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
<script src="<%=resPath%>js/opp_add.js"></script>
<script src="<%=resPath%>js/opp_app.js"></script>

<%@include file="jsp/opp_footer.jsp"%>