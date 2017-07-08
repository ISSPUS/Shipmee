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
<link rel="stylesheet"
	href="styles/assets/css/bootstrap-select.min.css">

<!-- Latest compiled and minified JavaScript -->
<script async
	src="styles/assets/js/bootstrap-select.min.js"></script>

<link rel="stylesheet" href="styles/assets/css/lateral-menu.css" type="text/css">
<link rel="stylesheet" href="styles/assets/css/style-list.css" type="text/css">
<script src="scripts/jquery.bootpag.min.js"></script>

<script async type="text/javascript" src="scripts/es.min.js"></script>

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

#select_size .filter-option{
	font-size: 12px;
}

</style>

<div class="blue-barra">
	    <div class="container">
			<div class="row">
				<h3><spring:message code="shipment.shipments" /></h3>
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
							<li class="li-input"><input type="text" id="origin" name="origin"
								class="form-control input-text" value="${origin}" placeholder="" required></li>
							<li class="active"><a><i
									class="glyphicon glyphicon-map-marker img-destination"></i>
									<spring:message code="shipment.destination" />
							</a></li>
							<li class="li-input"><input id="destination" name="destination" type="text"
								class="form-control input-text" value="${destination}" placeholder="" required></li>
							<li class="active"><a target="_blank"> <i
									class="fa fa-calendar"></i> <spring:message code="shipment.date" />
							</a></li>
							<li class="li-input">
								<div class='input-group fondoDesplegable input-text' id='datetimepicker1'>
									<input name="date" style="backgroud-color: white;" type='text'
										class="form-control" value="${form_date}"/> <span class="input-group-addon">
										<span class="glyphicon glyphicon-calendar"></span>
									</span>
								</div>
							</li>
							<li class="active"><a target="_blank"> <i
									class="glyphicon glyphicon-time"></i> <spring:message code="shipment.hour" />
							</a></li>
							<li style="text-align: center" class="li-input">
								<select class="form-control selectpicker input-text fondoDesplegable" name="hour">
								<option value=''><spring:message code="shipment.select.hour" /></option>
									<jstl:forEach begin="0" end="23" varStatus="i">
										<jstl:set var="selected_now" value=""/>
										<jstl:choose>	
											<jstl:when test="${i.index lt 10 }">
												<jstl:set var="hour_i" value="0${i.index}:00" />
											</jstl:when>
											<jstl:otherwise>
												<jstl:set var="hour_i" value="${i.index}:00" />
											</jstl:otherwise>
										</jstl:choose>
										<jstl:if test="${hour_i == form_hour}"><jstl:set var="selected_now" value="selected"/></jstl:if>
										<option ${selected_now}>${hour_i}</option>
										
									</jstl:forEach>
								</select>
							</li>
							<li class="active"><a> <i
									class="glyphicon glyphicon-eye-open"></i><spring:message code="shipment.package" />
							</a></li>
							<li style="padding-bottom: 2%;">
								<div class="form-check form-check-inline input-text">
								<jstl:choose>
									<jstl:when test="${form_envelope == 'open'}">
										<jstl:set var="form_envelope_open" value="checked"/>
									</jstl:when>
									<jstl:otherwise>
										<jstl:set var="form_envelope_open" value=""/>
									</jstl:otherwise>
								</jstl:choose>
								<jstl:choose>
									<jstl:when test="${form_envelope == 'close'}">
										<jstl:set var="form_envelope_close" value="checked"/>
									</jstl:when>
									<jstl:otherwise>
										<jstl:set var="form_envelope_close" value=""/>
									</jstl:otherwise>
								</jstl:choose>
									<label class="form-check-label"> <input
										class="form-check-input" type="checkbox" id="inlineCheckbox1" name="envelope"
										value="open" ${form_envelope_open}> <i class="demo-icon icon-package-1">&#xe800;</i><spring:message code="shipment.open" />
									</label> <label class="form-check-label"> <input
										class="form-check-input" type="checkbox" id="inlineCheckbox2" name="envelope"
										value="close" ${form_envelope_close}> <i class="demo-icon icon-package-1">&#xe801;</i><spring:message code="shipment.closed" />
									</label>
								</div>

							</li>
							<li class="active"><a> <i
									class="glyphicon glyphicon-resize-full"></i> <spring:message code="shipment.itemSize" />
							</a></li>
							
							<spring:message code="shipment.sizeS" var="s" />
							<jstl:if test="${form_itemSize == 'S'}"><jstl:set var="selected_s" value="selected"/></jstl:if>
							<spring:message code="shipment.sizeM" var="m" />
							<jstl:if test="${form_itemSize == 'M'}"><jstl:set var="selected_m" value="selected"/></jstl:if>
							<spring:message code="shipment.sizeL" var="l" />
							<jstl:if test="${form_itemSize == 'L'}"><jstl:set var="selected_l" value="selected"/></jstl:if>
							<spring:message code="shipment.sizeXL" var="xl" />
							<jstl:if test="${form_itemSize == 'XL'}"><jstl:set var="selected_xl" value="selected"/></jstl:if>
							<li style="text-align: center" class="li-input"><div id="select_size"><select
								class="form-control selectpicker input-text fondoDesplegable" name="itemSize">
									<option value=''><spring:message code="shipment.select.sizes" /></option>
									<option value="S" ${selected_s}>"${s }" </option>
									<option value="M" ${selected_m}>"${m }" </option>
									<option value="L" ${selected_l}>"${l }" </option>
									<option value="XL" ${selected_xl}>"${xl }" </option>
							</select></div></li>
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
												
												<a target="_blank" href="https://maps.google.com/maps?q=${javaMethods.normalizeUrl(shipment.origin)}"><i class="glyphicon glyphicon-map-marker img-origin"></i>${shipment.origin}</a>
											
												<i class="glyphicon glyphicon-sort"></i>
											
												<a target="_blank" href="https://maps.google.com/maps?q=${javaMethods.normalizeUrl(shipment.destination)}"> <i
													class="glyphicon glyphicon-map-marker img-destination"></i>${shipment.destination}
												</a>
														
												
												</div>	
						

										

												<i class="glyphicon glyphicon-time"></i> 
												<spring:message code="shipment.departureTime" />: 
												<fmt:formatDate value="${shipment.departureTime}" pattern="dd/MM/yyyy '-' HH:mm" />
												
												
												<br/>
												<i class="glyphicon glyphicon-time"></i> 
												<spring:message code="shipment.maximumArriveTime" />: 
												<fmt:formatDate value="${shipment.maximumArriveTime}" pattern="dd/MM/yyyy '-' HH:mm" />
												
													
											</div>
											<div class="col-lg-3 profile-userbuttons" style="margin-top: 5%;">
											
												<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${shipment.price}" var="formatPrice" />
												<div class="price">${formatPrice}&#8364;</div>
												<button type="button" class="btn button-ok btn-block" style="font-size: 15px;" onclick="location.href = 'shipment/display.do?shipmentId=${shipment.id}';"><spring:message code="route.details" />&nbsp;<i class="glyphicon glyphicon-chevron-right"></i></button>	
											

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







	</div>
</div>



<script type="text/javascript">

function initialize() {

	var input = document.getElementById('origin');
	var input2 = document.getElementById('destination');
	var options = {
		types: ['(cities)'],
		componentRestrictions: {country: 'es'}
	};
	var autocomplete = new google.maps.places.Autocomplete(input, options);
	var autocomplete = new google.maps.places.Autocomplete(input2, options);
	}
	
	
	google.maps.event.addDomListener(window, 'load', initialize);



	$(function() {
		language = getCookie("language");
		$('#datetimepicker1').datetimepicker({
			viewMode : 'days',
			locale: language,
			format : 'DD/MM/YYYY'
		});
	});

	// https://stackoverflow.com/a/9709425
	// the selector will match all input controls of type :checkbox
	// and attach a click event handler 
	$("input:checkbox").on('click', function() {
	  // in the handler, 'this' refers to the box clicked on
	  var $box = $(this);
	  if ($box.is(":checked")) {
	    // the name of the box is retrieved using the .attr() method
	    // as it is assumed and expected to be immutable
	    var group = "input:checkbox[name='" + $box.attr("name") + "']";
	    // the checked state of the group/box on the other hand will change
	    // and the current value is retrieved using .prop() method
	    $(group).prop("checked", false);
	    $box.prop("checked", true);
	  } else {
	    $box.prop("checked", false);
	  }
	});
</script>
