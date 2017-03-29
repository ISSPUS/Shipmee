<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<link rel="stylesheet" href="styles/assets/css/datetimepicker.min.css" />
<script type="text/javascript" src="scripts/moment.js"></script>
<script type="text/javascript" src="scripts/datetimepicker.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/css/bootstrap-select.min.css">

<!-- Latest compiled and minified JavaScript -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/js/bootstrap-select.min.js"></script>

<!-- (Optional) Latest compiled and minified JavaScript translation files -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/js/i18n/defaults-*.min.js"></script>

<link rel="stylesheet" href="styles/assets/css/lateral-menu.css" type="text/css">
<link rel="stylesheet" href="styles/assets/css/style-details.css" type="text/css">

<style>
@font-face {
	font-family: 'icons';
	src: url('styles/assets/fonts/iconos/iconos.eot?58322891');
	src: url('styles/assets/fonts/iconos/iconos.eot?58322891#iefix')
		format('embedded-opentype'),
		url('styles/assets/fonts/iconos/iconos.woff?58322891') format('woff'),
		url('styles/assets/fonts/iconos/iconos.ttf?58322891')
		format('truetype'),
		url('styles/assets/fonts/iconos/iconos.svg?58322891#fontello')
		format('svg');
	font-weight: normal;
	font-style: normal;
}

.alerta-info h5 {
	color: black ! important;
}

.alerta-info {
	margin-top: 5%;
}

.panel-default {
	margin: 0 auto;
	float: None;
	margin-bottom: 1%;
	
}

.caja{
margin-bottom: 5%;
}

.alerts-buttons{
margin-top: 32px;
    margin-bottom: 5%;
}

</style>

<div class="blue-barra">
	<div class="container">
		<div class="row">
			<h3>
				<spring:message code="alert.alerts" />
			</h3>
		</div>
		<!-- /row -->
	</div>
</div>


<div class="container caja">

	<div class="row">
	<div class="col-xs-4 col-sm-3 col-md-2 col-lg-2" style="margin: 0 auto; float: none; margin-bottom: 2%; margin-top: 2%;">
		<div class="text-center profile-userbuttons">
			<button class="btn button-view" style="font-size: 20px;" onclick="location.href = 'alert/user/create.do';"><span class="fa fa-plus-circle"></span> <spring:message code="alert.new" /></button>
			</div>
	</div>
</div>       
	

	<jstl:choose>
		<jstl:when test="${not empty alerts}">
			<jstl:forEach items="${alerts}" var="alert">
				<div class="row">
					
					
					<div
						class=" col-xs-12 col-sm-10 col-md-6 col-lg-6 table-container panel panel-default">
						<div class="row">

							<div class="col-xs-12 col-sm-6 col-md-8">

								<div class="alerta-info">
									<h4>
										<a>${alert.type}</a> <fmt:formatDate value="${alert.date}" pattern="dd/MM/yyyy '-' HH:mm" />
									</h4>
									<h5>
										<spring:message code="route.origin" />
										:
										<jstl:out value="${alert.origin}" />
									</h5>
									<h5>
										<spring:message code="route.destination" />
										:
										<jstl:out value="${alert.destination}" />
									</h5>
								</div>
							</div>
							<div class="col-xs-12 col-sm-2 col-md-4 col-lg-3"
								style="text-align: center;">
								<div class="text-center profile-userbuttons alerts-buttons">
									<button class="btn button-ok" onclick="location.href = 'alert/user/edit.do?alertId=${alert.id}';">
										<span class="fa fa-pencil"></span>
										<spring:message code="alert.edit" />
									</button>
								</div>
								
							</div>
						</div>
					</div>

				</div>

			</jstl:forEach>
		</jstl:when>
		<jstl:otherwise>
			<p>
				<spring:message code="vehicle.results" />
			</p>
		</jstl:otherwise>
	</jstl:choose>
</div>


