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

</style>

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
									<div class="col-xs-7 col-sm-9">
																			
										
										<h5 class="titulos"><spring:message code="shipment.places" /></h5>
										
										<div class="col-xs-7 col-sm-9 row titles-details">
										<i class="glyphicon glyphicon-map-marker"></i>&nbsp;<spring:message code="shipment.origin" />:<span class="titles-info">${shipment.origin}</span>&nbsp;&nbsp;<i
									class="glyphicon glyphicon-flag"></i>&nbsp;<spring:message code="shipment.destination" />:<span class="titles-info">${shipment.destination}</span></div>
										
										
									</div>
			
						
										
										

										<div class="row col-xs-7 col-sm-9">
										<h5 class="titulos"><spring:message code="shipment.moments" /></h5>
											<div class="info-salida col-sm-12 ">

												<i class="glyphicon glyphicon-plane"></i> 
												<spring:message code="shipment.departureTime" />: <span class="titles-info">${departureTime}</span><i class="glyphicon glyphicon-time"></i><span class="titles-info">${departureTime_hour}</span>
												<br/>
												<i class="glyphicon glyphicon-plane"></i> 
												<spring:message code="shipment.maximumArriveTime" />: <span class="titles-info">${maximumArriveTime}</span><i class="glyphicon glyphicon-time"></i><span class="titles-info">${maximumArriveTime_hour}</span>

											</div>
										</div>
										
										<div class="row info1 col-xs-11 col-sm-9">
										<h5 class="titulos"><spring:message code="shipment.characteristics" /></h5>
											<div class="col-xs-11">
												<i class="demo-icon icon-package-1">&#xe800;&nbsp;</i><spring:message code="shipment.itemEnvelope" />: 
												<span class="titles-info">${shipment.itemEnvelope}</span>
											
											<br/>
											
												<i class="demo-icon icon-package-1">&#xe802;&nbsp;</i><spring:message code="shipment.itemSize" />: 
												<span class="titles-info">${shipment.itemSize}</span>
											</div>
											
										</div>
										
										<div class="row info1 col-xs-7 col-sm-9">
										<h5 class="titulos">Photos</h5>
													
											

										</div>
										<div class="row info1 col-xs-10 col-sm-12">

							<a href="#aboutModal" data-toggle="modal" data-target="#myModal"><img
								class="imagen-envio" src="${shipment.itemPicture}"></a>


							<!-- Modal -->
							<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
								aria-labelledby="myModalLabel" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">

										<div class="modal-body">


											<img src="${shipment.itemPicture}">

										</div>

									</div>
								</div>
							</div>



						</div>
										
										<div class="row info1 col-xs-7 col-sm-9">
										<h5 class="titulos"><spring:message code="shipment.price" /></h5>
											<div class="col-sm-12">
												<i class="glyphicon glyphicon-euro">&nbsp;</i><spring:message code="shipment.price" />: 
												<span class="titles-info-price">${shipment.price}&#8364;</span>
												<br/>

											</div>
	
											
										</div>
										

									
								</div>
								<div class="rfecha separador-final"></div>



								<div class="row info1 col-xs-12 col-sm-12 text-center" style="margin-bottom:1%;">
											
										<jstl:if test="${shipment.creator != user}">
											
											<input type=submit class="btn-sm btn-llevar btn btn-success ok"
											value= "<spring:message code="shipment.carry" />" onclick="location.href = 'shipment/user/carry.do?shipmentId=${shipment.id}';"></input>
										
										</jstl:if>
										
								</div>
							
								
								
					<div class="profile-userbuttons" style="margin-left: 2%;margin-right: 2%;">
						
						<jstl:if test="${shipment.creator != user}">
						<button type="submit" class="btn button-view btn-primary"
							onclick="location.href = 'shipmentOffer/user/create.do?shipmentId=${shipment.id}';" style="margin-bottom: 10px;">
							<span class="fa fa-plus-circle"></span>
							<spring:message code="offer.new" />
						</button>
						</jstl:if>
						

						<button type="submit" class="btn btn-primary"
							onclick="location.href = 'shipmentOffer/user/list.do?shipmentId=${shipment.id}';" style="margin-bottom: 10px;">
							<span class="fa fa-list"></span> 
							<spring:message code="offer.list" />
						</button>

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
