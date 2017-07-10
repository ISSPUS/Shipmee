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

<jsp:useBean id="javaMethods" class="utilities.ViewsMethods" />


<link rel="stylesheet" href="styles/assets/css/datetimepicker.min.css" />
<script async type="text/javascript" src="scripts/moment.min.js"></script>
<script async type="text/javascript" src="scripts/datetimepicker.min.js"></script>
<script async src="scripts/jquery.bootpag.min.js"></script>
<link rel="stylesheet"
	href="styles/assets/css/bootstrap-select.min.css">

<!-- Latest compiled and minified JavaScript -->
<script
	src="styles/assets/js/bootstrap-select.min.js"></script>

<link rel="stylesheet" href="styles/assets/css/lateral-menu.css"
	type="text/css">
<link rel="stylesheet" href="styles/assets/css/style-list.css"
	type="text/css">
<link rel="stylesheet" href="styles/assets/css/style-details.css"
	type="text/css">
<!-- Variables necesarias

-shipments:lista de envios del usuario con id que se pasa en la ur
-user: usuario al que pertenecen los envios
 -->

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
			<h3>
				<spring:message code="shipments.from" /><a href="user/profile.do?userId=${user.id}"><jstl:out value="${user.name}" /></a>
			</h3>
		</div>
		<!-- /row -->
	</div>
</div>


<div class="container">

	<div class="row profile">
		<div class="col-md-3">


			<div class="span3 well text-center"
				style="margin-top: 2px; background-color: white;">
				<a href="user/profile.do?userId=${user.id}"> <jstl:choose>
						<jstl:when test="${not empty user.photo}">
							<jstl:set var="imageUser" value="${user.photo}" />
						</jstl:when>
						<jstl:otherwise>
							<jstl:set var="imageUser" value="images/anonymous.png" />
						</jstl:otherwise>
					</jstl:choose> <img src="${imageUser}" name="aboutme" width="140" height="140"
					border="0" class="img-circle">


				</a>
				<h3>
					<a>${user.name}</a>
				</h3>
				<div class="profile-userbuttons">
				
					<button type="button" class="btn button-view btn-sm"
						onclick="location.href = 'user/profile.do?userId=${user.id}';">
						<spring:message code="user.view" />
					</button>
					
					<jstl:if test="${(user.id != shipmentId) && shipmentId != 0 && (user.id != currentUser.id)}">
						<button type="button" class="btn button-delete-lax btn-sm"
							onclick="location.href = 'complaint/user/create.do?userId=${user.id}';">
							<spring:message code="user.report" />
						</button>
					</jstl:if>
										
				</div>
			</div>

		</div>
		<div class="col-md-9">
			<jstl:if test="${(user.id == currentUser.id) || (user.id == shipmentId)}">
			<div class="row">
				<div class="col-xs-12 col-sm-6 col-md-4 col-lg-4"
					style="margin: 0 auto; float: none; margin-bottom: 2%; margin-top: 2%;">
					<div class="text-center profile-userbuttons">
						<button class="btn button-view" style="font-size: 20px;"
							onclick="location.href = 'shipment/user/create.do';">
							<span class="fa fa-plus-circle"></span>
							<spring:message code="shipment.create" />
						</button>
					</div>
				</div>
			</div>
			</jstl:if>
			
			<div class="profile-content">

				<div class="panel panel-default">
					<div class="panel-body">

						<div class="table-container">
							<table class="table table-filter">
								<tbody>


									<jstl:choose>
										<jstl:when test="${not empty shipments}">
											<jstl:forEach items="${shipments}" var="shipment">

												<tr>

													<td>


														<div class="row">

															<div class="col-lg-3 text-center">
																<a href="shipment/display.do?shipmentId=${shipment.id}">
																	<img src="${shipment.itemPicture}" class="media-photo-shipment">
																</a>
															</div>

															<div class="info-salida col-lg-6"
																style="margin-bottom: 2%; font-size: 16px;">
																<div class="cabecera">
																	<div class="title">
																		<h4><a href="shipment/display.do?shipmentId=${shipment.id}">${shipment.itemName}</a></h4>
																	</div>

																	<a target="_blank" href="https://maps.google.com/maps?q=${javaMethods.normalizeUrl(shipment.origin)}"><i class="glyphicon glyphicon-map-marker img-origin"></i>${shipment.origin}</a>
											
																	<i class="glyphicon glyphicon-sort"></i>
											
																	<a target="_blank" href="https://maps.google.com/maps?q=${javaMethods.normalizeUrl(shipment.destination)}"> <i
																	class="glyphicon glyphicon-map-marker img-destination"></i>${shipment.destination}
																	</a>


																</div>




																<i class="glyphicon glyphicon-time"></i>
																<spring:message code="shipment.departureTime" />
																:
																<fmt:formatDate value="${shipment.departureTime}"
																	pattern="dd/MM/yyyy '-' HH:mm" />


																<br /> <i class="glyphicon glyphicon-time"></i>
																<spring:message code="shipment.maximumArriveTime" />
																:
																<fmt:formatDate value="${shipment.maximumArriveTime}"
																	pattern="dd/MM/yyyy '-' HH:mm" />


															</div>
															<div class="col-lg-3 profile-userbuttons"
																style="margin-top: 5%;">
																<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${shipment.price}" var="formatPrice" />
																<div class="price">${formatPrice}&#8364;</div>
																<button type="button" class="btn button-ok btn-block"
																	style="font-size: 15px;"
																	onclick="location.href = 'shipment/display.do?shipmentId=${shipment.id}';">
																	<spring:message code="route.details" />
																	&nbsp;<i class="glyphicon glyphicon-chevron-right"></i>
																</button>


															</div>
														</div>



													</td>
												</tr>
											</jstl:forEach>
											
										</jstl:when>
										<jstl:otherwise>
											<div class="alert alert-info">
												<strong><spring:message code="shipment.results" /></strong>
											</div>
										</jstl:otherwise>
									</jstl:choose>
								</tbody>
							</table>


						</div>




					</div>
				</div>
			</div>

		</div>
		
		<div id="pagination" class="copyright pagination-center">
			<script>
				$('#pagination').bootpag({
					total : <jstl:out value="${total_pages}"></jstl:out>,
					page : <jstl:out value="${p}"></jstl:out>,
					maxVisible : 3,
					leaps : true,
					firstLastUse : true,
					first : '<',
		            last: '>',
					wrapClass : 'pagination',
					activeClass : 'active',
					disabledClass : 'disabled',
					nextClass : 'next',
					prevClass : 'prev',
					lastClass : 'last',
					firstClass : 'first'
				}).on('page', function(event, num) {
					window.location.href = "${urlPage}" + num + "";
					page = 1
				});
			</script>
		
		</div>

	</div>
</div>

