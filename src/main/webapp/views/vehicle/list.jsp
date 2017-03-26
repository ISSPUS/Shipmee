<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

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

<link rel="stylesheet" href="styles/assets/css/lateral-menu.css"
	type="text/css">
<link rel="stylesheet" href="styles/assets/css/style-list.css" type="text/css">


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

</style>

<div class="blue-barra">
	    <div class="container">
			<div class="row">
				<h3><spring:message code="vehicle.vehicles" /></h3>
			</div><!-- /row -->
	    </div>
	</div>


<div class="container">

	<div class="row profile">
		<div class="col-md-9">
			<div class="profile-content">
					
					<div class="panel panel-default">
					<div class="panel-body">
						
						<div class="table-container">
					<table class="table table-filter">
								<tbody>
								
								
								<jstl:choose>
					<jstl:when test="${not empty vehicles}">
						<jstl:forEach items="${vehicles}" var="vehicle">
								
									<tr>
										
										<td>
											
											
										<div class="row">
										
											<div class="col-lg-3 text-center">

												<img src="${vehicle.picture}" class="media-photo-shipment">
													
											</div>
										
											<div class="info-salida col-lg-6" style="margin-bottom: 2%; font-size: 16px;">
												<div class="cabecera">
												<div class="title">
													<h4><a>${vehicle.brand}</a></h4>
												</div>
												</div>	
						

										

												<spring:message code="vehicle.model" />: 
												<jstl:out value="${vehicle.model}"/>
												
												
												<br/>
												<spring:message code="vehicle.color" />: 
												<jstl:out value="${vehicle.color}"/>												
													
											</div>
											
											<div class="col-lg-3" style="margin-top: 5%;">				
												<button type="button" class="btn btn-primary btn-md btn-block" onclick="location.href = 'vehicle/user/edit.do?vehicleId=${vehicle.id}';"><spring:message code="vehicle.edit" />&nbsp;<i class="glyphicon glyphicon-chevron-right"></i></button>
											</div>
											
										</div>
											
										
											
										</td>
									</tr>
									</jstl:forEach>
					</jstl:when>
					<jstl:otherwise>
						<p><spring:message code="vehicle.results" /></p>
					</jstl:otherwise>
				</jstl:choose>
								</tbody>
							</table>


				</div>




			</div>
</div></div>

		</div>




<button type="button" class="btn btn-primary btn-md btn-block" onclick="location.href = 'vehicle/user/create.do';"><spring:message code="vehicle.add" />&nbsp;<i class="glyphicon glyphicon-chevron-right"></i></button>


	</div>
</div>
