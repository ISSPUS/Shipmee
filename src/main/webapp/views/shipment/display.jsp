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

<jsp:useBean id="javaMethods" class="utilities.ViewsMethods" />


<link rel="stylesheet" href="styles/assets/css/datetimepicker.min.css" />
<script async type="text/javascript" src="scripts/moment.min.js"></script>
<script async type="text/javascript" src="scripts/datetimepicker.min.js"></script>
<link rel="stylesheet"
	href="styles/assets/css/bootstrap-select.min.css">

<!-- Latest compiled and minified JavaScript -->
<script async
	src="styles/assets/js/bootstrap-select.min.js"></script>

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


.separa_imagen{
	margin-bottom:5%;

}

</style>

<security:authorize access="isAnonymous()" var="isAnonymous" />
<security:authorize access="hasRole('USER')" var="isUser" />


<div class="blue-barra">
	    <div class="container">
			<div class="row">
				<h3><spring:message code="shipment.shipment" /></h3>
			</div><!-- /row -->
	    </div>
	</div>


<div class="container">

	<div class="row profile">
		<div class="col-md-3">
			
		<div class="span3 well text-center"
				style="margin-top: 2px; background-color: white;">
				
				<a href="user/profile.do?userId=${shipment.creator.id}"> 
					<jstl:choose>
						<jstl:when test="${not empty shipment.creator.photo}">
							<jstl:set var="imageUser" value="${shipment.creator.photo}" />
						</jstl:when>
						<jstl:otherwise>
							<jstl:set var="imageUser" value="images/anonymous.png" />
						</jstl:otherwise>
					</jstl:choose> 
					<img src="${imageUser}" name="aboutme" width="140" height="140"border="0" class="img-circle">

				</a>
				<h3>
					<a>${shipment.creator.name}</a>
				</h3>
				<div class="profile-userbuttons">
					<button type="button" class="btn button-view btn-sm" onclick="location.href = 'user/profile.do?userId=${shipment.creator.id}';"><spring:message code="user.view" /></button>
					<jstl:if test="${shipment.creator.id != user.id}">
						<button type="button" class="btn button-delete-lax btn-sm" onclick="location.href = 'complaint/user/create.do?userId=${shipment.creator.id}';"><spring:message code="user.report" /></button>
					</jstl:if>

				</div>
			</div>
		</div>
		
		
		
		
		
		
		<div class="col-md-9">
			<div class="profile-content">
							<div class="row cuadro">
								<div class="row rtitulo">
									<div class="rtitulo col-sm-12 text-center ">
										<h4 class="titulo">${shipment.itemName}</h4>
									</div>
								</div>
								
								<div class="rfecha separador"></div><span class="cretaion-date media-meta pull-right"><fmt:formatDate value="${shipment.date}" pattern="dd/MM/yyyy HH:mm" /></span>
								
								<div class="row info-ruta">
									<div class="col-xs-12 col-sm-9">

										<h5 class="titulos"><spring:message code="shipment.places" />&nbsp;<img src="images/maps_64dp.png" style="width: 22px;"></h5>
										
										<div class="col-xs-7 col-sm-9 row titles-details frontera" style="width: 100%">
										<i class="glyphicon glyphicon-map-marker"></i>&nbsp;<spring:message code="shipment.origin" />:<a target="_blank" href="https://maps.google.com/maps?q=${javaMethods.normalizeUrl(shipment.origin)}"><span class="titles-info">${shipment.origin}</span></a>&nbsp;&nbsp;<br/>
										<i class="glyphicon glyphicon-flag"></i>&nbsp;<spring:message code="shipment.destination" />:<a target="_blank" href="https://maps.google.com/maps?q=${javaMethods.normalizeUrl(shipment.destination)}"><span class="titles-info">${shipment.destination}</span></a><!--<img class="icon-maps" src="images/maps_64dp.png">-->
										</div>
										
										
									</div>
			
						
										
										

										<div class="row col-xs-12 col-sm-9">
										<h5 class="titulos"><spring:message code="shipment.moments" /></h5>
											<div class="info-salida col-sm-12 ">

												<i class="fa fa-calendar"></i> 
												<spring:message code="shipment.departureTime" />: <span class="titles-info">${departureTime}</span><i class="glyphicon glyphicon-time"></i><span class="titles-info">${departureTime_hour}</span>
												<br/>
												<i class="fa fa-calendar"></i> 
												<spring:message code="shipment.maximumArriveTime" />: <span class="titles-info">${maximumArriveTime}</span><i class="glyphicon glyphicon-time"></i><span class="titles-info">${maximumArriveTime_hour}</span>

											</div>
										</div>
										
										<div class="row info1 col-xs-12 col-sm-9">
										<h5 class="titulos"><spring:message code="shipment.characteristics" /></h5>
											<div class="col-xs-12">
												<i class="demo-icon icon-package-1">&#xe800;&nbsp;</i><spring:message code="shipment.itemEnvelope" />: 
												<span class="titles-info">
													<jstl:if test="${shipment.itemEnvelope == 'Both'}">
														<spring:message code="shipment.both"/>
													</jstl:if>
													<jstl:if test="${shipment.itemEnvelope == 'Open'}">
														<spring:message code="shipment.open"/>
													</jstl:if>							
													<jstl:if test="${shipment.itemEnvelope == 'Closed'}">
														<spring:message code="shipment.closed"/>
													</jstl:if>
												</span>
											
											<br/>
											
												<i class="demo-icon icon-package-1">&#xe802;&nbsp;</i><spring:message code="shipment.itemSize" />: 
												<span class="titles-info">
													<jstl:if test="${shipment.itemSize == 'S'}"><spring:message code="shipment.sizeS"/></jstl:if>
													<jstl:if test="${shipment.itemSize == 'M'}"><spring:message code="shipment.sizeM"/></jstl:if>
													<jstl:if test="${shipment.itemSize == 'L'}"><spring:message code="shipment.sizeL"/></jstl:if>
													<jstl:if test="${shipment.itemSize == 'XL'}"><spring:message code="shipment.sizeXL"/></jstl:if>
												</span>
								
								<a data-toggle="modal"
								data-target="#infoSize" href="#infoSize" style="z-index: 5;"><span class="glyphicon glyphicon-info-sign"></span></a> 
								
								<!-- Modal -->
								<div class="modal fade" id="infoSize" tabindex="-1"
									role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
									<div class="modal-dialog">
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal">&times;</button>
												<h4 class="modal-title text-center">
													<spring:message code="route.sizes.window" />
												</h4>
											</div>
											<div class="modal-body" style="text-align: center">
											
											<div class="separa_imagen"><img src="images/bolsillo.svg" style="width: 10%;"><br>
											<strong><spring:message code="shipment.sizeS"/></strong></div>
											
											<div class="separa_imagen"><img src="images/mochila.svg" style="width: 20%;"><br>
											<strong><spring:message code="shipment.sizeM"/></strong></div>
											
											<div class="separa_imagen"><img src="images/maleta.svg" style="width: 25%;"><br>
											<strong><spring:message code="shipment.sizeL"/></strong></div>
											
											<div class="separa_imagen"><img src="images/maletero2.svg" style="width: 40%;"><br>
											<strong><spring:message code="shipment.sizeXL"/></strong></div>
											
											</div>
											
										</div>
									</div>
								</div>
							</div>
											
										</div>
										
										<div class="row info1 col-xs-12 col-sm-9">
										<h5 class="titulos"><spring:message code="shipment.itemPicture" /></h5>
													
											

										</div>
										<div class="row info1 col-xs-10 col-sm-12">

							<a href="#aboutModal" data-toggle="modal" data-target="#myModal"><img
								class="imagen-envio" src="${shipment.itemPicture}"></a>


							<!-- Modal -->
							<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
								aria-labelledby="myModalLabel" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal">&times;</button>
											<h4 class="modal-title text-center">
												<spring:message code="shipment.itemPicture" />
											</h4>
										</div>
										<div class="modal-body" style="text-align: center;">
											<img src="${shipment.itemPicture}">
										</div>

									</div>
								</div>
							</div>



						</div>
										
										<div class="row info1 col-xs-12 col-sm-9">
										<h5 class="titulos"><spring:message code="shipment.price" /></h5>
											<div class="col-sm-12">
												<!-- <i class="glyphicon glyphicon-euro">&nbsp;</i><spring:message code="shipment.price" />: -->
												<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${shipment.price}" var="formatPrice" />
												<span class="titles-info-price">${formatPrice}&#8364;</span>
												<a><spring:message code="master.page.comissions" /></a>
												<br/>
											</div>
	
											
										</div>
										

									
								</div>
								<div class="rfecha separador-final"></div>


								<div class="row info1 col-xs-12 col-sm-12 text-center" style="margin-bottom:1%;">
										<jstl:if test="${isAnonymous}">

											<a href="security/login.do"><spring:message code="message.error.shipment.authenticate" /></a>

										</jstl:if>
										<jstl:if test="${shipment.creator != user && user.isVerified && shipment.carried == null  && user.fundTransferPreference != null}">
											
											<input type=submit class="btn-sm btn-llevar btn btn-success ok"
											value= "<spring:message code="shipment.carry" />" onclick="location.href = 'shipment/user/carry.do?shipmentId=${shipment.id}';"></input>
										
										</jstl:if>
										<jstl:if test="${isUser && shipment.creator != user && !user.isVerified && shipment.carried == null && (user.phone == '' || user.dni == '' || user.photo == '' || user.dniPhoto == '')}">
											
											<a href="user/user/edit.do"><spring:message code="message.error.shipmentOffer.verifiedCarrier.extended" /></a>
										
										</jstl:if>
										<jstl:if test="${isUser && shipment.creator != user && !user.isVerified && shipment.carried == null  && (user.phone != '' && user.dni != '' && user.photo != '' && user.dniPhoto != '')}">

											<a><spring:message code="user.notVerified.waiting" /></a>

										</jstl:if>
										<jstl:if test="${isUser && shipment.creator != user && user.fundTransferPreference == null && shipment.carried == null}">
										<br/>
										<a href="fundTransferPreference/user/edit.do">
											<spring:message code="message.error.shipmentOffer.fundTransferPreference" />
										</a>
								
										</jstl:if>
								</div>
							
								
								
					<div class="profile-userbuttons" style="margin-left: 2%;margin-right: 2%;">
						<jstl:if test="${isUser && shipment.creator != user && user.isVerified && shipment.carried == null && user.fundTransferPreference != null}">
						<button type="submit" class="btn button-view"
							onclick="location.href = 'shipmentOffer/user/create.do?shipmentId=${shipment.id}';" style="margin-bottom: 10px;">
							<span class="fa fa-bullhorn"></span>
							<spring:message code="offer.new" />
						</button>
						</jstl:if>
						
						<jstl:if test="${isUser && shipment.creator == user && shipmentOffersIsEmpty && shipment.carried == null}">
						<button type="submit" class="btn button-view"
							onclick="location.href = 'shipment/user/edit.do?shipmentId=${shipment.id}';" style="margin-bottom: 10px;">
							<span class="fa fa-pencil-square-o"></span>
							<spring:message code="shipment.edit" />
						</button>
						</jstl:if>
						
						<security:authorize access="hasAnyRole('ADMIN')" var="isAdmin" />
						<jstl:if test="${!isAdmin}">
							<button type="submit" class="btn btn-primary"
								onclick="location.href = 'shipmentOffer/user/list.do?shipmentId=${shipment.id}';" style="margin-bottom: 10px;">
								<span class="fa fa-list"></span>
								<spring:message code="offer.list" />
							</button>
						</jstl:if>
					</div>
								
							</div>
							
							
							
						
			</div>


		</div>

	</div>
</div>



<script type="text/javascript">
	$(function() {
		$('#datetimepicker1').datetimepicker({
			viewMode : 'days',
			format : 'DD/MM/YYYY'
		});
	});
	
	
      $(function () {
          $('#datetimepicker2').datetimepicker();
      });
</script>
