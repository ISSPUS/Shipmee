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
<script type="text/javascript" src="scripts/moment.min.js"></script>
<script type="text/javascript" src="scripts/datetimepicker.min.js"></script>
<link  rel="stylesheet"
	href="styles/assets/css/bootstrap-select.min.css">

<!-- Latest compiled and minified JavaScript -->
<script async
	src="styles/assets/js/bootstrap-select.min.js"></script>

<!-- (Optional) Latest compiled and minified JavaScript translation files -->

<link  rel="stylesheet" href="styles/assets/css/lateral-menu.css" type="text/css">
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

<div class="blue-barra">
	    <div class="container">
			<div class="row">
				<h3><spring:message code="route.route" /></h3>
			</div><!-- /row -->
	    </div>
	</div>
	
<div class="container">

	<div class="row profile">
		<div class="col-md-3">


			<div class="span3 well text-center"
				style="margin-top: 2px; background-color: white;">
				<a href="user/profile.do?userId=${route.creator.id}">
				
				
				<jstl:choose>
						<jstl:when test="${not empty route.creator.photo}">
							<jstl:set var="imageUser" value="${route.creator.photo}" />
						</jstl:when>
						<jstl:otherwise>
							<jstl:set var="imageUser" value="images/anonymous.png" />
						</jstl:otherwise>
					</jstl:choose>
				<img src="${imageUser}" name="aboutme" width="140" height="140" border="0" class="img-circle">	
					
					
					
					
					
					</a>
				<h3>
					<a>${route.creator.name}</a>
				</h3>
				<div class="profile-userbuttons">
					<button type="button" class="btn button-view btn-sm" onclick="location.href = 'user/profile.do?userId=${route.creator.id}';"><spring:message code="user.view" /></button>
					
					<jstl:if test="${route.creator.id != user.id}">
						<button type="button" class="btn button-delete-lax btn-sm" onclick="location.href = 'complaint/user/create.do?userId=${route.creator.id}';"><spring:message code="user.report" /></button>
					</jstl:if>
									
				</div>
			</div>


		</div>
		
		
		
		<div class="col-md-9">
			<div class="profile-content">
							<div class="row cuadro">
								

								<div class="rfecha separador"></div><span class="cretaion-date media-meta pull-right"><fmt:formatDate value="${route.date}" pattern="dd/MM/yyyy HH:mm" /></span>
								
								<div class="row info-ruta">
										<div class="col-xs-12 col-sm-9">
										<h5 class="titulos"><spring:message code="shipment.places" /></h5>
										
										<div class="row titles-details frontera"><i
									class="glyphicon glyphicon-map-marker"></i>&nbsp;<spring:message code="route.origin" />:<a target="_blank" href="http://maps.google.com/maps?q=${route.origin}"><span class="titles-info">${route.origin}</span></a>&nbsp;&nbsp;<br/><i
									class="glyphicon glyphicon-flag"></i>&nbsp;<spring:message code="route.destination" />:<a target="_blank" href="http://maps.google.com/maps?q=${route.destination}"><span class="titles-info">${route.destination}</span></a><!--<img class="icon-maps" src="images/maps_64dp.png"> --></div>
										
										</div>
						
										<div class="row col-xs-12 col-sm-9">
										<h5 class="titulos"><spring:message code="shipment.moments" /></h5>
											<div class="info-salida col-sm-12" style="width: 100%">
												
												<i class="glyphicon glyphicon-plane"></i> 
												<spring:message code="route.departureTime" />: <span class="titles-info">${departureTime}</span><i class="glyphicon glyphicon-time"></i><span class="titles-info">${departureTime_hour}</span>
												<br/>
												<i class="glyphicon glyphicon-plane"></i> 
												<spring:message code="route.arriveTime" />: <span class="titles-info">${arriveTime}</span><i class="glyphicon glyphicon-time"></i><span class="titles-info">${arriveTime_hour}</span>
												

											</div>
										</div>
										
										
							
									
										<div class="row info1 col-xs-12 col-sm-9">
										<h5 class="titulos"><spring:message code="shipment.characteristics" /></h5>
											<div class="col-xs-11">
											
												<jstl:if test="${not empty route.vehicle}">		
												<div class="vehiclePart">
												<i class="glyphicon glyphicon-road">&nbsp;</i><spring:message code="route.vehicle"/>: 
												<span class="titles-info">${route.vehicle.brand} - ${route.vehicle.model}</span>
												<br/>

												<a href="#aboutModal" data-toggle="modal" data-target="#myModal">
													<img class="imagen-envio" src="${route.vehicle.picture}">
												</a>
				
				
												<!-- Modal -->
												<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
													aria-labelledby="myModalLabel" aria-hidden="true">
													<div class="modal-dialog">
														<div class="modal-content">
				
															<div class="modal-body">
				
				
																<img class="imagen-envio" src="${route.vehicle.picture}">
				
															</div>
				
														</div>
													</div>
												</div>
												</div>
												</jstl:if>
											<i class="demo-icon icon-package-1">&#xe800;&nbsp;</i><spring:message code="route.itemEnvelope" />: 
												<span class="titles-info">
													<jstl:if test="${route.itemEnvelope == 'Both'}">
														<spring:message code="route.both"/>
													</jstl:if>
													<jstl:if test="${route.itemEnvelope == 'Open'}">
														<spring:message code="route.open"/>
													</jstl:if>							
													<jstl:if test="${route.itemEnvelope == 'Closed'}">
														<spring:message code="route.closed"/>
													</jstl:if>
												</span>
												
												<br/>
												
											</div>
											
										</div>
										
										<div class="row info1 col-xs-12 col-sm-9">
										<h5 class="titulos"><spring:message code="shipment.price" /></h5>
											<div class="col-sm-12">
												
											
											</div>
	
											
										</div>
										

									</div>
		


					
					<table class="table table-valores table-bordered table-sm m-0">
                	
                    
                        <tr class="table-cabecera">
                            <th><spring:message code="route.table.choose" /></th>
                            <th><spring:message code="route.table.size" /> 
                            
                            
                            
                            
                            	<!-- FEEDBACK -->
								<a title="InfoSize" data-toggle="modal"
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
											<div class="modal-body">
											
											<div class="separa_imagen"><img src="images/bolsillo.svg" style="width: 10%;"><br>
											<spring:message code="shipment.sizeS"/></div>
											
											<div class="separa_imagen"><img src="images/mochila.svg" style="width: 20%;"><br>
											<spring:message code="shipment.sizeM"/></div>
											
											<div class="separa_imagen"><img src="images/maleta.svg" style="width: 25%;"><br>
											<spring:message code="shipment.sizeL"/></div>
											
											<div class="separa_imagen"><img src="images/maletero2.svg" style="width: 40%;"><br>
											<spring:message code="shipment.sizeXL"/></div>
											
											</div>
											
										</div>
									</div>
								</div>
								
								
								
								
								
								</th>
                            <th><spring:message code="route.table.price" /> </th>
                        </tr>
                   
                    <tbody>
                    <jstl:forEach var="value" items="${sizePrices}">
                        <tr>
                            <td>
                                <label class="custom-control custom-checkbox">
                                    <input type="radio" name="sizes" value="${value.id}" style="display:none" class="custom-control-input" required>
                                    <span class="custom-control-indicator"></span>
                                </label>
                            </td>
                            <td class="tabla-tam">                
                            	<jstl:if test="${value.size == 'S'}"><span title="<spring:message code="shipment.sizeS"/>">${value.size}</span></jstl:if>
								<jstl:if test="${value.size == 'M'}"><span title="<spring:message code="shipment.sizeM"/>">${value.size}</span></jstl:if>
								<jstl:if test="${value.size == 'L'}"><span title="<spring:message code="shipment.sizeL"/>">${value.size}</span></jstl:if>
								<jstl:if test="${value.size == 'XL'}"><span title="<spring:message code="shipment.sizeXL"/>">${value.size}</span></jstl:if>
                            </td>
                            <td class="tabla-precio">${value.price}&#8364;
                        </tr>
					</jstl:forEach>
     
                    </tbody>
 					 </table>
 					 <div id="routePricesMessage" class="text-center"><a><spring:message code="master.page.comissions" /></a></div>
					
					<div class="rfecha separador-final"></div>

								<div class="row info1 col-xs-12 col-sm-12 text-center" style="margin-bottom:1%;">
											
										<jstl:if test="${route.creator != user}">
											<input type=submit class="btn-sm btn btn-success ok"
											value= "<spring:message code="route.contract" />" onclick="contract(${route.id});"></input>
										</jstl:if>
								</div>

					<div class="profile-userbuttons" style="margin-left: 2%;margin-right: 2%;">
						
						<jstl:if test="${route.creator != user}">
						<button type="submit" class="btn button-view btn-primary"
							onclick="location.href = 'routeOffer/user/create.do?routeId=${route.id}';" style="margin-bottom: 10px;">
							<span class="fa fa-plus-circle"></span>
							<spring:message code="offer.new" />
						</button>
						</jstl:if>
						
						<jstl:if test="${route.creator == user && routeOffersIsEmpty}">
						<button type="submit" class="btn button-view btn-primary"
							onclick="location.href = 'route/user/edit.do?routeId=${route.id}';" style="margin-bottom: 10px;">
							<span class="fa fa-plus-circle"></span>
							<spring:message code="route.edit" />
						</button>
						</jstl:if>

						<button type="submit" class="btn btn-primary"
							onclick="location.href = 'routeOffer/user/list.do?routeId=${route.id}';" style="margin-bottom: 10px;">
							<span class="fa fa-list"></span> 
							<spring:message code="offer.list" />
						</button>
					</div>


			<jstl:if test="${route.creator != user && not empty shipments}">
					<div class="col-md-9">
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
										
											<div class="info-salida col-lg-6" style="margin-bottom: 2%; font-size: 16px;">
												<div class="cabecera">
												<div class="title">
													<h4><a href="shipment/display.do?shipmentId=${shipment.id}">${shipment.itemName}</a></h4>
												</div>
												
												<a target="_blank" href="http://maps.google.com/maps?q=${shipment.origin}"><i class="glyphicon glyphicon-map-marker img-origin"></i>${shipment.origin}</a>
											
												<i class="glyphicon glyphicon-sort"></i>
											
												<a target="_blank" href="http://maps.google.com/maps?q=${shipment.destination}"> <i
													class="glyphicon glyphicon-map-marker img-destination"></i>${shipment.destination}
												</a>
														
												
												</div>	
						

										

												<i class="glyphicon glyphicon-plane"></i> 
												<spring:message code="shipment.departureTime" />: 
												<fmt:formatDate value="${shipment.departureTime}" pattern="dd/MM/yyyy '-' HH:mm" />
												
												
												<br/>
												<i class="glyphicon glyphicon-plane"></i> 
												<spring:message code="shipment.maximumArriveTime" />: 
												<fmt:formatDate value="${shipment.maximumArriveTime}" pattern="dd/MM/yyyy '-' HH:mm" />
												
													
											</div>
											<div class="col-lg-3 profile-userbuttons" style="margin-top: 5%;">
											
												<div class="price">${shipment.price}&#8364;</div>	
												<button type="button" class="btn button-ok btn-block" style="font-size: 15px;" onclick="location.href = 'routeOffer/user/create.do?routeId=${route.id}&shipmentId=${shipment.id}';"><spring:message code="route.details" />&nbsp;<i class="glyphicon glyphicon-chevron-right"></i></button>	
											

											</div>
										</div>
											
										
											
										</td>
									</tr>
									</jstl:forEach>
					</jstl:when>
					<jstl:otherwise>
					</jstl:otherwise>
				</jstl:choose>
								</tbody>
							</table>
				<div id="pagination" class="copyright" style="text-align: center;">
					
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
</div></div>

		</div>
		</jstl:if>


				</div>
						
			</div>


		</div>

	</div>
</div>

<script type="text/javascript">
	function contract(routeId){
		//location.href = 'route/user/contract.do?routeId=${route.id}&sizePriceId=${value.id}';"
		var sizePriceId = $('input[name=sizes]:checked').val();
		if(sizePriceId!=undefined){
			window.location = 'route/user/contract.do?routeId='+routeId+'&sizePriceId='+sizePriceId;
			return false;
		}else{
			var node = document.getElementById('routePricesMessage');
			node.innerHTML = "<a><spring:message code='master.page.comissions' /></a><div class='error'><spring:message code='route.sizeprice.select' /></div>";
			return true;
		}
	}
</script>
