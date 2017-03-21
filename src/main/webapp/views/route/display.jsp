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
				<h3><spring:message code="route.route" /></h3>
			</div><!-- /row -->
	    </div>
	</div>
	
<div class="container">

	<div class="row profile">
		<div class="col-md-3">
			<div class="profile-sidebar">

				<div class="profile-usermenu">
					<form method="get" action="shipment/search.do">

						<ul class="nav">

							<li class="active"><a> <i
									class="glyphicon glyphicon-map-marker img-origin"></i> <spring:message code="shipment.origin" />
							</a></li>
							<li class="li-input"><input type="text" name="origin"
								class="form-control input-text" value="${origin}" required></li>
							<li class="active"><a><i
									class="glyphicon glyphicon-map-marker img-destination"></i>
									<spring:message code="shipment.destination" />
							</a></li>
							<li class="li-input"><input name="destination" type="text"
								class="form-control input-text" value="${destination}" required></li>
							<li class="active"><a href="" target="_blank"> <i
									class="glyphicon glyphicon-plane"></i> <spring:message code="shipment.date" />
							</a></li>
							<li class="li-input">
								<div class='input-group fondoDesplegable input-text' id='datetimepicker1'>
									<input name="date" style="backgroud-color: white;" type='text'
										class="form-control" /> <span class="input-group-addon">
										<span class="glyphicon glyphicon-calendar"></span>
									</span>
								</div>
							</li>
							<li class="active"><a href="" target="_blank"> <i
									class="glyphicon glyphicon-time"></i> <spring:message code="shipment.hour" />
							</a></li>
							<li style="text-align: center" class="li-input">
								<select class="selectpicker input-text fondoDesplegable" name="hour">
								<option selected="selected" disabled value=''><spring:message code="shipment.select.hour" /></option>
									<jstl:forEach begin="0" end="23" varStatus="i">
										<jstl:choose>	
											<jstl:when test="${i.index lt 10 }">
												<option>0${i.index}:00</option>
											</jstl:when>
											<jstl:otherwise>
												<option>${i.index}:00</option>
											</jstl:otherwise>
										</jstl:choose>
									</jstl:forEach>
								</select>
							</li>
							<li class="active"><a href="#"> <i
									class="glyphicon glyphicon-eye-open"></i><spring:message code="shipment.package" />
							</a></li>
							<li style="padding-bottom: 2%;">
								<div class="form-check form-check-inline input-text">
									<label class="form-check-label"> <input
										class="form-check-input" type="checkbox" id="inlineCheckbox1" name="envelope"
										value="open"> <i class="demo-icon icon-package-1">&#xe800;</i><spring:message code="shipment.open" />
									</label> <label class="form-check-label"> <input
										class="form-check-input" type="checkbox" id="inlineCheckbox2" name="envelope"
										value="close"> <i class="demo-icon icon-package-1">&#xe801;</i><spring:message code="shipment.closed" />
									</label>
								</div>

							</li>
							<li class="active"><a> <i
									class="glyphicon glyphicon-resize-full"></i> <spring:message code="shipment.itemSize" />
							</a></li>
							<li style="text-align: center" class="li-input"><select
								class="selectpicker input-text fondoDesplegable" name="itemSize">
									<option selected="selected" disabled value=''><spring:message code="shipment.select.sizes" /></option>
									<option value="xs">XS</option>
									<option value="s">S</option>
									<option value="m">M</option>
									<option value="l">L</option>
									<option value="xl">XL</option>
							</select></li>
							<li class="active"><button type="submit"
									class="btnSearch btn-lg btnSample btn-block btn-success">
									<spring:message code="welcome.search" /> <span class="glyphicon glyphicon-search"></span>
								</button></li>
						</ul>
					</form>

				</div>
				<!-- END MENU -->
			</div>
		</div>
		
		
		
		<div class="col-md-9">
			<div class="profile-content">
							<div class="row ruta">
								<div class="row rtitulo">
									<div class="rtitulo col-sm-12 text-center ">
										<h4 class="titulo">${route.creator.name}</h4>
									</div>
								</div>
								
								<div class="rfecha separador"></div><span class="cretaion-date media-meta pull-right"><fmt:formatDate value="${route.date}" pattern="dd/MM/yyyy HH:mm" /></span>
								
								<div class="row info-ruta">
										<div class="col-xs-7 col-sm-9">
										<h5 class="titulos"><spring:message code="shipment.places" /></h5>
										
										<div class="row titles-details"><i
									class="glyphicon glyphicon-map-marker"></i>&nbsp;<spring:message code="route.origin" />:<span class="titles-info">${route.origin}</span>&nbsp;&nbsp;<i
									class="glyphicon glyphicon-flag"></i>&nbsp;<spring:message code="route.destination" />:<span class="titles-info">${route.destination}</span></div>
										
										</div>
						
										<div class="row col-xs-7 col-sm-9">
										<h5 class="titulos"><spring:message code="shipment.moments" /></h5>
											<div class="info-salida col-sm-12 ">
												
												<i class="glyphicon glyphicon-plane"></i> 
												<spring:message code="route.departureTime" />: <span class="titles-info">${departureTime}</span><i class="glyphicon glyphicon-time"></i><span class="titles-info">${departureTime_hour}</span>
												<br/>
												<i class="glyphicon glyphicon-plane"></i> 
												<spring:message code="route.arriveTime" />: <span class="titles-info">${arriveTime}</span><i class="glyphicon glyphicon-time"></i><span class="titles-info">${arriveTime_hour}</span>
												

											</div>
										</div>
										
										
										<div class="prueba"><div class="col-xs-12">
												<img class="img-responsive center-block imagen-profile"
													width="100" height="100" src="images/anonymous.png">
													<spring:message code="route.profile" /> <a href="#">${route.creator.name}</a>
											</div></div>
									
										<div class="row info1 col-xs-7 col-sm-9">
										<h5 class="titulos"><spring:message code="shipment.characteristics" /></h5>
											<div class="col-xs-6">
											
												<i class="glyphicon glyphicon-road">&nbsp;</i>Vehicle: 
												<span class="titles-info">${route.vehicle.brand} - ${route.vehicle.model}</span>
												<br/>
												<i class="demo-icon icon-package-1">&#xe800;&nbsp;</i><spring:message code="route.itemEnvelope" />: 
												<span class="titles-info">${route.itemEnvelope}</span>
												
												<br/>
												
												<i class="demo-icon icon-package-1">&#xe802;&nbsp;</i><spring:message code="route.size" />:
												<jstl:forEach var="value" items="${sizePrices}">
													<span class="titles-info">${value.size}</span>
												</jstl:forEach>
												
											</div>
											
										</div>
										
										<div class="row info1 col-xs-7 col-sm-9">
										<h5 class="titulos"><spring:message code="shipment.price" /></h5>
											<div class="col-sm-12">
												
											
											</div>
	
											
										</div>
										

									</div>
		


					
					<table class="table table-valores table-bordered table-sm m-0">
                	
                    
                        <tr class="table-cabecera">
                            <th><spring:message code="route.table.choose" /></th>
                            <th><spring:message code="route.table.size" /></th>
                            <th><spring:message code="route.table.price" /></th>
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
                            <td class="tabla-tam">${value.size}</td>
                            <td class="tabla-precio">${value.price}&#8364;
                            
                            <security:authorize access="hasRole('USER')">
                            	<a href="routeOffer/user/create.do?routeId=${route.id}"><span title="<spring:message code="route.offer" />"><i class="glyphicon glyphicon-resize-small contraoferta-icon"></i></span></a>
                            </security:authorize></td>
                         
                        </tr>
					</jstl:forEach>
                       
                    </tbody>
                </table>
					

					
					<div class="rfecha separador-final"></div>



								<div class="row info1 col-xs-12 col-sm-12 text-center">
											
											<input type=submit class="btn-sm btn-llevar btn btn-success ok"
											value= "<spring:message code="route.contract" />" onclick="contract(${route.id});"></input>

								</div>
								<div class="text-center"><a href="routeOffer/user/list.do?routeId=${route.id}"><spring:message code="route.offers" /><i class="glyphicon glyphicon-chevron-right"></i></a></div>
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
			return true;
		}
	}
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
