<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String basepath = request.getContextPath();
%>
<%@include file="../variable.jsp"%>
<%@include file="jsp/opp_header.jsp"%>

<%@include file="jsp/opp_topbar.jsp"%>

<div class="row adjudicado" id="content">

	<div class="large-12 columns">
		<h1>Membresía a empleados</h1>
	</div>

	<div class="large-12 columns">
		<a href="javascript:void(1)" class="button disabled">Membresía a empleados</a> 
		<a href="<%=basePath %>opportunities/clientes_referidos.html" class="button">Clientes referidos</a> 
		<a href="http://www.bancomer.com/inmuebles/inmuebles-disponibles.jsp?nivel1=personas" class="button" target="_blank">Busca tu inmueble</a>
	</div>

	<div class="large-12 columns">
		<p>Con la finalidad de generar programas que ofrezcan beneficios
			a los empleados activos de BBVA Bancomer1, la Dirección de Activos
			No Financieros (DANF) pone a su disposición la venta de inmuebles*,
			donde podrán gozar de los siguientes:</p>


		<h3>BENEFICIOS</h3>
		<table>
			<tbody>
				<tr>
					<td>Descuento</td>
					<td>El 30% sobre el valor publicado en bancomer.com</td>
				</tr>
				<tr>
					<td>Forma de compra</td>
					<td>• Crédito Hipotecario RRHH (conforme a Políticas) <br>
						• Infonavit (vivienda) <br> • Cofinanciamiento
						(Banco/Infonavit) <br> • Fovissste <br> • Recursos
						propios <br></td>
				</tr>
				<tr>
					<td>Producto</td>
					<td>Aplica para todo tipo de inventario DISPONIBLE
						(viviendas, locales comerciales, terrenos, etc). <br>
						Consulta el inventario de propiedades para seleccionar la que más
						cumpla con tus necesidades, a través de: <br> • Intranet:
						Servicios Aplicativos/Inmuebles / Buscador Inmuebles Disponibles<br>
						• Bancomer.com: Inmuebles/Inmuebles Disponibles/buscador de
						inmuebles disponibles
					</td>
				</tr>
				<tr>
					<td>Enganche</td>
					<td>Con Crédito Hipotecario RRHH de acuerdo a Política de
						RRHH (Membresía BBVA Bancomer) <br> Con recursos propios:
						cantidad acordada con el Área de Ventas de la DANF.
					</td>
				</tr>
				<tr>
					<td>Penalización</td>
					<td>En crédito: Sin penalización <br> De contado:
						$5,000.00
					</td>
				</tr>
				<tr>
					<td>Avalúo</td>
					<td>Con Crédito Hipotecario de RRHH, el costo del avalúo
						queda de la siguiente manera:<br> • Para el Equipo Directivo
						y colectivo de mandos superiores de BBVA Bancomer Operadora y
						BBVA Bancomer Servicios: <strong>50% descuento</strong>.<br>
						• Para los demás colectivos de Operadora: <strong>Gratuito</strong><br>
						• Para la compra con créditos diferentes al de RRHH:<br> El
						costo del avalúo será de acuerdo a Membresía BBVA Bancomer. <br>
						(Para ti dRRHH/ Membresía BBVA Bancomer/ Otros Productos/
						Avalúos)
					</td>
				</tr>
				<tr>
					<td>Traslado de <br>beneficios
					</td>
					<td>Extensivo a los siguientes familiares: <br> •
						Cónyuge<br> • Hijos o hijastros mayores de edad que dependan
						económicamente del empleado.<br> • Progenitores (tanto del
						empleado como del cónyuge)
					</td>
				</tr>
				<tr>
					<td>No. de inmuebles <br> por empleado
					</td>
					<td>Este beneficio aplica por la compra de hasta 2 inmuebles
						por año, ya sea por el empleado o por sus familiares.<br> En
						caso de que la compra de los inmuebles sea por parte del
						empleado, éstos deberán quedar a su nombre.
					</td>
				</tr>
			</tbody>
		</table>

		<p>* Inmuebles propiedad de BBVA Bancomer, derivados de sus
			procesos de recuperación de crédito</p>


		<h3>
			<sup>1</sup>Empresas del Grupo susceptibles al descuento
		</h3>
		<table>
			<tbody>
				<td>Adquira México, SA de CV</td>
				<td>Multiasistencia Operadora, SA de CV</td>
				</tr>
				<tr>
					<td>Anida Servicios Inmobiliarios, SA de CV</td>
					<td>Multiasistencia Servicios, SA de CV</td>
				</tr>
				<tr>
					<td>Aplica Tecnología Avanzada Operadora, SA de CV</td>
					<td>Multiasistencia, SA de CV</td>
				</tr>
				<tr>
					<td>Aplica Tecnología Avanzada Servicios, SA de CV</td>
					<td>Premexsa, SA de CV</td>
				</tr>
				<tr>
					<td>BBVA Bancomer Operadora, SA de CV</td>
					<td>Seguros BBVA Bancomer, SA de CV Gpo. Financiero BBVA
						Bancomer</td>
				</tr>
				<tr>
					<td>BBVA Bancomer Servicios Administrativos, SA de CV</td>
					<td>Servicios Corporativos Bancomer, SA de CV</td>
				</tr>
				<tr>
					<td>BBVA Bancomer, SA</td>
					<td>Servicios Corporativos de Seguros, SA de CV (Pensiones)</td>
				</tr>
				<tr>
					<td>Casa de Bolsa BBVA Bancomer, SA de CV</td>
					<td>Servicios Corporativos de Seguros, SA de CV (Seguros)</td>
				</tr>
				<tr>
					<td>Contratación de Personal, SA de CV</td>
					<td>Servicios Externos de Apoyo Empresarial, SA de CV</td>
				</tr>
				<tr>
					<td>Corporativo Vitamédica, SA de CV</td>
					<td>Servicios Vitamédica, SA de CV</td>
				</tr>
				<tr>
					<td>Futuro Familiar, SA de CV</td>
					<td>Solium Operadora, SA de CV</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>Solium de México, SA de CV</td>
				</tr>
			</tbody>
		</table>


		<h3>RESTRICCIONES</h3>

		<table>
			<tbody>
				<tr>
					<td>Dirección Activos No Financieros</td>
					<td>Jurídico Recuperación de Crédito</td>
				</tr>
				<tr>
					<td>Unidades de Recuperación</td>
					<td>Unidad de Avalúos México</td>
				</tr>
				<tr>
					<td>Jurídico Regularización Activos No Financieros</td>
					<td>Anida Servicios Inmobiliarios</td>
				</tr>

			</tbody>
		</table>

		<p>Deberá proceder de acuerdo a lo estipulado en el código
			COR-CODCOND-005/14 "Venta de Inmuebles a Empleados de del GFB
			publicado en:</p>

		<p>HPD: Nuestro Banco/ Centro de Conocimiento/Normatividad/
			Normatividad Institucional/ Corporativos BBVA /Reglamento
			Corporativo/ COR-CODCOND-005/14,</p>

		<p>Para adquirir un inmueble perteneciente a la DANF con
			descuento de empleado, éste deberá seguir los siguientes pasos:</p>

		<ul>
			<li>Consultar las propiedades de Inmuebles Bancomer (DANF) a
				través de: bancomer.com/Inmuebles y Avalúos/Inmuebles
				Disponibles/Buscador de Inmuebles Disponibles</li>
			<li>Anotar parámetros de búsqueda del inmueble</li>
			<li>Seleccionar el folio de la propiedad deseada para ver el
				detalle del inmueble.</li>
			<li>Contactar al Ejecutivo de Ventas asignado de Bancomer, para
				concertar cita y visitar físicamente el inmueble.</li>
			<li>El Ejecutivo de Ventas, asignará a un broker inmobiliario
				autorizado por la DANF, para que acompañe al empleado y realicen la
				visita del inmueble.</li>
			<li>Una vez realizada la visita y si el inmueble es del interés
				del empleado, éste deberá comunicarse con el Ejecutivo de Ventas y
				notificárselo a fin de iniciar el trámite de compra.</li>
			<li>El Ejecutivo de Ventas le proporcionará vía correo
				electrónico los formatos que deberá requisitar para el trámite de
				compra del inmueble (Formato de Oferta y Formato Conoce a tu
				Cliente), así mismo le solicitará entregarle junto con los formatos
				mencionados, la siguiente documentación:
				<ul>
					<li>Copia identificación oficial (anverso y reverso)</li>
					<li>Copia del comprobante oficial del domicilio</li>
					<li>Si el inmueble lo va a adquirir a través de crédito
						hipotecario de RH: Copia del Certificado Hipotecario (el cual
						deberá tramitar de acuerdo a Políticas de RRHH.)</li>
					<li>Si el inmueble lo va a adquirir a través de crédito de
						Infonavit o Fovisste: Carta de Precalificación</li>
				</ul>
			</li>
			<li>Recibida la documentación anterior, el Ejecutivo de Ventas
				tramitará la autorización de venta de acuerdo a políticas internas
				de la DANF y en un lapso máximo de 5 días hábiles posteriores a la
				recepción de la documentación anterior, se le notificará por
				teléfono o vía correo electrónico al empleado si fue aceptada su
				oferta.</li>
			<li>Dentro de los cinco días hábiles siguientes en que fuera
				aceptada la oferta se llevará a cabo la firma del contrato de
				"Promesa de Compra Venta", para lo cual se le solicitará al
				empleado la cantidad que deberá entregar a la DANF como "enganche",
				de acuerdo a lo siguiente: Para la adquisición del inmueble con
				crédito: De acuerdo a política de RRHH (Membresía Bancomer) Para la
				adquisición del inmueble con recursos propios: Cantidad pactada por
				el Area de Ventas de la DANF.</li>
			<li>Una vez firmado el Contrato de Compra venta, el Ejecutivo
				de Ventas realiza proceso interno de la DANF para continuar con el
				trámite de venta.</li>
			<li>Se formaliza la operación con la firma de Escrituras y
				liquidación del saldo del precio pactado.</li>
		</ul>

		<h3>CONSIDERACIONES GENERALES</h3>

		<ul>
			<li>La DANF será la única Dirección responsable de autorizar el
				descuento.</li>
			<li>Al solicitar este descuento, el precio de venta de
				cualquier propiedad NO ES NEGOCIABLE</li>
			<li>El beneficio es por inmueble NO por empleado.</li>
			<li>El descuento NO APLICA PARA INMUEBLES EN PROCESO DE
				REGULARIZACIÓN NI PARA OTROS PROGRAMAS DE VENTA PUBLICADOS POR LA
				DANF.</li>
			<li>Los únicos canales a través de los cuales los empleados
				pueden realizar sus peticiones de información y compra son; el
				correo electrónico o vía telefónica, por lo que las peticiones
				presentadas fuera de estos canales no tendrán validez.</li>
			<li>La recepción de ofertas de un inmueble, será a partir de
				que éste tenga precio y aparezca publicado en b.com</li>
			<li>El envío de ofertas será únicamente a través de correo
				electrónico y llevarán un orden cronológico de recepción.</li>
			<li>En ningún momento podrá acudir personalmente el empleado
				con el Ejecutivo de Ventas a entregar su oferta.</li>
			<li>Al recibir una propuesta de compra de empleado, el
				Ejecutivo de Ventas de la DANF deberá validar e informar al
				empleado si existen otras "Propuestas de compra de inmuebles"
				previas de "otros empleados", con el fin de aclarar el orden con la
				que serán atendidas y en su caso, notificará al empleado el momento
				en que su propuesta sea autorizada.</li>
			<li>El criterio a utilizar en el caso que concurran ofertas a
				través de orden cronológico, subasta, sorteo de clientes y
				empleados, se debe dar preferencia siempre a la condición más
				ventajosa para la entidad, es decir, la del cliente.</li>
			<li>El plazo que disponen los empleados para realizar la
				petición de compra en firme y abonar el precio pactado como
				"enganche o anticipo" del precio del inmueble, será de 15 días
				hábiles a partir de ser autorizada la propuesta por la DANF.</li>
			<li>En caso de que el "Empleado solicitante" no firme el
				Contrato de Promesa de Compra-Venta y no entregue la garantía antes
				mencionada, éste pierde el derecho de adquirir el inmueble.</li>
			<li>El pago del "enganche o anticipo" no le dá derecho al
				empleado a la toma de posesión del inmueble (parcial o total) hasta
				que esté totalmente pagado y escriturado.</li>
			<li>Será por cuenta del empleado el pago del ISR por
				adquisición del inmueble, a la Notaria.</li>
			<li>En caso de que por causas externas a la Dirección de Activo
				No Financieros un inmueble no pueda ser vendido, la DANF devolverá
				el 100% del anticipo al empleado y no existirá ninguna penalización
				para esta Dirección aún cuando el trámite se haya iniciado o
				inclusive se haya proporcionado el anticipo correspondiente.</li>
		</ul>

		<p>Para cualquier duda contactar a:</p>

		<table>
			<tbody>
				<tr>
					<td><strong>División Centro</strong><br> Sede: Centro
						Bancomer D.F.</td>
					<td><strong>Paloma Jiménez Moreno</strong><br> Ext.
						14368<br> p.jimenez3@bbva.com</td>
				</tr>
				<tr>
					<td><strong>División Occidente</strong><br> Sede:
						Guadalajara, Jal.</td>
					<td>León F. Rodríguez Mauri<br> Tel. 01 (333) 669 4497<br>
						Ext. 54497<br> leon.rodriguez@bbva.com
					</td>
				</tr>
				<tr>
					<td><strong>División Golfo - Sureste</strong><br> Sede:
						Puebla, Pue.</td>
					<td>Nallely Cartas Bonilla<br> Tel. 01 (222) 229 6024<br>
						nallely.cartas@bbva.com
					</td>
				</tr>
				<tr>
					<td><strong>División Norte</strong><br> Sede: Monterrey,
						N.L.</td>
					<td>Lauro Contreras Salas<br> Tel. 01 (81) 8047 9728<br>
						lauro.contreras@bbva.bancomer.com
					</td>
				</tr>
				<tr>
					<td><strong>División Noroeste</strong><br> Sede:
						Tijuana, B.C.</td>
					<td>Pedro Loya Almada<br> Tel. 01 (664) 687 9935<br>
						Ext. 79935<br> pedro.loya@bbva.com
					</td>
				</tr>
			</tbody>
		</table>

		<p>
			Contacto: <a href="mailto:inmueblesbancomer@bbva.bancomer.com">inmueblesbancomer@bbva.bancomer.com</a>
		</p>

		<p>Consulta tu área de RH para tu solicitud de crédito
			hipotecario.</p>

	</div>


</div>

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