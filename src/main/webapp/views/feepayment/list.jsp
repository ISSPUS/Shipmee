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
<script async type="text/javascript" src="scripts/moment.min.js"></script>
<script async type="text/javascript" src="scripts/datetimepicker.min.js"></script>
<link rel="stylesheet"
	href="styles/assets/css/bootstrap-select.min.css">

<!-- Latest compiled and minified JavaScript -->
<script async
	src="styles/assets/js/bootstrap-select.min.js"></script>

<link rel="stylesheet" href="styles/assets/css/lateral-menu.css"
	type="text/css">
<link rel="stylesheet" href="styles/assets/css/style-list.css"
	type="text/css">
<script async src="scripts/jquery.bootpag.min.js"></script>

<!-- variables necesarias para la vista 




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

.info-moderator-Mild {
	vertical-align: middle;
	text-align: center;
	color: white !important;
	border: 0.5px solid #ed9c28;
	background: #ed9c28;
	border-radius: 10px;
}

.info-moderator-Omitted {
	vertical-align: middle;
	text-align: center;
	color: white !important;
	border: 0.5px solid #47a447;
	background: #47a447;
	border-radius: 10px;
}

.info-moderator-Serious {
	vertical-align: middle;
	text-align: center;
	color: white !important;
	border: 0.5px solid #d9534f;
	background: #d9534f;
	border-radius: 10px;
}

.well {
	padding: 0 !important;
}

.panel-default {
	margin: 0 auto;
	float: None;
}

/*NUEVO*/
.huge {
	font-size: 40px;
}

.panel-green {
	border-color: #5cb85c;
}

.panel-green>.panel-heading {
	border-color: #5cb85c;
	color: white;
	background-color: #5cb85c;
}

.panel-green>a {
	color: #5cb85c;
}

.panel-green>a:hover {
	color: #3d8b3d;
}

.panel-red {
	border-color: #d9534f;
}

.panel-red>.panel-heading {
	border-color: #d9534f;
	color: white;
	background-color: #d9534f;
}

.panel-red>a {
	color: #d9534f;
}

.panel-red>a:hover {
	color: #b52b27;
}

.panel-yellow {
	border-color: #f0ad4e;
}

.panel-yellow>.panel-heading {
	border-color: #f0ad4e;
	color: white;
	background-color: #f0ad4e;
}

.panel-yellow>a {
	color: #f0ad4e;
}

.panel-yellow>a:hover {
	color: #df8a13;
}

.panel-red>.panel-heading {
	border-color: #d9534f;
	color: white;
	background-color: #d9534f;
}

.panel-red>a {
	color: #d9534f;
}

.panel-red>a:hover {
	color: #d9534f;
}

.price{
font-size: 225%;
    color: #ffae66;
    font-weight: bold;

}
</style>

<div class="blue-barra">
	<div class="container">
		<div class="row">
			<h3>
				<spring:message code="feePayment.feePayments" />
			</h3>
		</div>
		<!-- /row -->
	</div>
</div>


<div class="container">

	<!-- Menu informacion de las feePayments -->
	<security:authorize access="hasAnyRole('ADMIN', 'USER')">

		<div class="row" style="margin-top: 2%">
			<div class="center-block">
				<div class="col-lg-4 col-md-4 cuadro">
					<div class="panel panel-green">
						<div class="panel-heading">
							<div class="row">
								<div class="col-xs-3">
									<i class="fa fa-thumbs-up fa-5x"></i>
								</div>
								<div class="col-xs-9 text-right">
									<div class="huge"><jstl:out value="${allAccepted}"/></div>
									<div>
										<spring:message code="feePayment.accepted" />
									</div>
								</div>
							</div>
						</div>
						<security:authorize access="hasRole('ADMIN')">
						<a href="feepayment/administrator/list.do?type=Accepted&page=1">
							<div class="panel-footer">
								<span class="pull-left"><spring:message code="complaint.details" /></span> <span
									class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
								<div class="clearfix"></div>
							</div>
						</a>
						</security:authorize>
						
						<security:authorize access="hasRole('USER')">
						<a href="feepayment/user/list.do?type=Accepted&page=1">
							<div class="panel-footer">
								<span class="pull-left"><spring:message code="complaint.details" /></span> <span
									class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
								<div class="clearfix"></div>
							</div>
						</a>
						</security:authorize>
					</div>
				</div>
				<div class="col-lg-4 col-md-4 cuadro">
					<div class="panel panel-yellow">
						<div class="panel-heading">
							<div class="row">
								<div class="col-xs-3">
									<i class="fa fa-clock-o fa-5x"></i>
								</div>
								<div class="col-xs-9 text-right">
									<div class="huge"><jstl:out value="${allPending}"/></div>
									<div>
										<spring:message code="feePayment.pending" />
									</div>
								</div>
							</div>
						</div>
						<security:authorize access="hasRole('ADMIN')">
						<a href="feepayment/administrator/list.do?type=Pending&page=1">
							<div class="panel-footer">
								<span class="pull-left"><spring:message code="complaint.details" /></span> <span
									class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
								<div class="clearfix"></div>
							</div>
						</a>
						</security:authorize>
						
						<security:authorize access="hasRole('USER')">
						<a href="feepayment/user/list.do?type=Pending&page=1">
							<div class="panel-footer">
								<span class="pull-left"><spring:message code="complaint.details" /></span> <span
									class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
								<div class="clearfix"></div>
							</div>
						</a>
						</security:authorize>
					</div>
				</div>
				<div class="col-lg-4 col-md-4 cuadro">
					<div class="panel panel-red">
						<div class="panel-heading">
							<div class="row">
								<div class="col-xs-3">
									<i class="fa fa-thumbs-down fa-5x"></i>
								</div>
								<div class="col-xs-9 text-right">
									<div class="huge"><jstl:out value="${allDenied}"/></div>
									<div>
										<spring:message code="feepayment.rejected" />
									</div>
								</div>
							</div>
						</div>
						<security:authorize access="hasRole('ADMIN')">
						<a href="feepayment/administrator/list.do?type=Rejected&page=1">
							<div class="panel-footer">
								<span class="pull-left"><spring:message code="complaint.details" /></span> <span
									class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
								<div class="clearfix"></div>
							</div>
						</a>
						</security:authorize>
						
						<security:authorize access="hasRole('USER')">
						<a href="feepayment/user/list.do?type=Rejected&page=1">
							<div class="panel-footer">
								<span class="pull-left"><spring:message code="complaint.details" /></span> <span
									class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
								<div class="clearfix"></div>
							</div>
						</a>
						</security:authorize>
					</div>
				</div>
			</div>
		</div>
	</security:authorize>
	<!-- Listado de feePayments -->

	<jstl:choose>
		<jstl:when test="${not empty feePayments}">
			<jstl:forEach items="${feePayments}" var="feePayment">
				<div
					class=" col-xs-12 col-sm-10 col-md-7 col-lg-7 table-container panel panel-default"
					style="margin-top: 1%; margin-bottom: 1%">
					<div class="row">
					<security:authorize access="hasAnyRole('ADMIN', 'USER')">
							<div class="col-xs-12">
								<div class="info-moderator-${feePayment.type}">
									<spring:message code="feePayment.pending" var="mild" />
									<div style="margin-bottom: 0.5%;float: right;">
										
											<jstl:choose>
												<jstl:when test="${feePayment.type == 'Accepted' }">
													<span style="color:#5cb85c"><i class="fa fa-thumbs-up"></i></span>
												</jstl:when>
												<jstl:when test="${feePayment.type == 'Pending'   }">
													<span style="color:#f0ad4e"><i class="fa fa-clock-o"></i></span>
												</jstl:when>
												<jstl:when test="${feePayment.type == 'Rejected'}">
													<span style="color:#d9534f"><i class="fa fa-thumbs-down"></i></span>
												</jstl:when>
											</jstl:choose>
										
									</div>
								</div>
							</div>
						</security:authorize>
						<div class="col-xs-12">
						
							<div class="span3 text-center">
								<jstl:if test="${feePayment.shipmentOffer != null}">
									<h4>
										<a href="shipment/display.do?shipmentId=${feePayment.shipmentOffer.shipment.id}"><spring:message code="shipmentOffer.shipment" />
											${feePayment.shipmentOffer.shipment.itemName}</a></h4>
											<fmt:formatDate value="${feePayment.shipmentOffer.shipment.departureTime}" pattern="dd/MM/yyyy '-' HH:mm" /> - <fmt:formatDate value="${feePayment.shipmentOffer.shipment.maximumArriveTime}" pattern="dd/MM/yyyy '-' HH:mm" />
								</jstl:if>
								<jstl:if test="${feePayment.routeOffer != null}">
									<h4>
										<a href="route/display.do?routeId=${feePayment.routeOffer.route.id}"><spring:message code="route.route" />
											${feePayment.routeOffer.route.origin} - ${feePayment.routeOffer.route.destination}<br/></a></h4>
											<fmt:formatDate value="${feePayment.routeOffer.route.departureTime}" pattern="dd/MM/yyyy '-' HH:mm" /> - <fmt:formatDate value="${feePayment.routeOffer.route.arriveTime}" pattern="dd/MM/yyyy '-' HH:mm" />
									
								</jstl:if>
							</div>
							
							<br/>
						
							<div class="col-xs-6">
								<div class="span3 text-center">									
									<h4>
										<a href="user/profile.do?userId=${feePayment.purchaser.id}"><spring:message code="feePayment.purchaser" />
											${feePayment.purchaser.userAccount.username}</a>
									</h4>
									<a href="user/profile.do?userId=${feePayment.purchaser.id}"> <jstl:choose>
											<jstl:when test="${not empty feePayment.purchaser.photo}">
												<jstl:set var="imageUser" value="${feePayment.purchaser.photo}" />
											</jstl:when>
											<jstl:otherwise>
												<jstl:set var="imageUser" value="images/anonymous.png" />
											</jstl:otherwise>
										</jstl:choose> <img src="${imageUser}" name="aboutme" width="70" height="70"
										border="0" class="img-circle">
									</a>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="span3 text-center">
									<h4>
										<a href="user/profile.do?userId=${feePayment.carrier.id}"><spring:message code="feePayment.carrier" />
											${feePayment.carrier.userAccount.username}</a>
									</h4>
									<a href="user/profile.do?userId=${feePayment.carrier.id}"> <jstl:choose>
											<jstl:when test="${not empty feePayment.carrier.photo}">
												<jstl:set var="imageUserEnvolved"
													value="${feePayment.carrier.photo}" />
											</jstl:when>
											<jstl:otherwise>
												<jstl:set var="imageUserEnvolved"
													value="images/anonymous.png" />
											</jstl:otherwise>
										</jstl:choose> <img src="${imageUserEnvolved}" name="aboutme" width="70"
										height="70" border="0" class="img-circle">
									</a>
								</div>
							</div>
						</div>
						<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${feePayment.amount}" var="formatAmount" />
						
						<jstl:choose>
							<jstl:when test="${feePayment.type == 'Accepted'}">
								<div class="col-xs-12 price" style="color: #5cb85c">
									<span>${formatAmount}&#8364;</span>
								</div>
							</jstl:when>
							<jstl:when test="${feePayment.type == 'Pending'}">
								<div class="col-xs-12 price" style="color: #f0ad4e">
									<span>${formatAmount}&#8364;</span>
								</div>
							</jstl:when>
							<jstl:when test="${feePayment.type == 'Rejected'}">
								<div class="col-xs-12 price" style="color: #d9534f">
									<span>${formatAmount}&#8364;</span>
								</div>
							</jstl:when>
						</jstl:choose>
						
						<div class="text-center"><a><spring:message code="master.page.comissions" /></a></div>
						

						<jstl:if test="${feePayment.type == 'Pending'}">
						<div class="col-xs-12 text-center profile-userbuttons">
							<security:authorize access="hasRole('USER')">
								<div class="text-center btn-group btn-group-justified">
									
									<div class="text-center profile-userbuttons">
						
							<spring:message code="feePayment.accept" var="accept" />
							<button type="button" class="btn btn-success btn-md "
								onclick="location.href = 'feepayment/user/manage.do?feepaymentId=${feePayment.id}&type=Accepted';">
								<jstl:out value="${accept}" />
								&nbsp;<i class="glyphicon glyphicon-ok-circle"></i>
							</button>

							<spring:message code="feePayment.reject" var="reject" />
							<button type="button" class="btn btn-danger btn-md"
								onclick="location.href = 'feepayment/user/manage.do?feepaymentId=${feePayment.id}&type=Rejected';">
								<jstl:out value="${reject}" />
								&nbsp;<i class="glyphicon glyphicon-remove-circle"></i>
							</button>
									</div>
								</div>
							</security:authorize>
						</div>
						
						</jstl:if>
					</div>
				</div>
			</jstl:forEach>
		</jstl:when>
		<jstl:otherwise>
						<jstl:choose>
							<jstl:when test="${feePaymentsType == 'Accepted'}">
								<div class="container" style="margin-top: 25px">
									<div class="alert alert-info">
										<strong><spring:message code="feePayment.results.accepted" /></strong>
									</div>
								</div>
							</jstl:when>
							<jstl:when test="${feePaymentsType == 'Pending'}">
								<div class="container" style="margin-top: 25px">
									<div class="alert alert-info">
										<strong><spring:message code="feePayment.results.pending" /></strong>
									</div>
								</div>
							</jstl:when>
							<jstl:when test="${feePaymentsType == 'Rejected'}">
								<div class="container" style="margin-top: 25px">
									<div class="alert alert-info">
										<strong><spring:message code="feePayment.results.rejected" /></strong>
									</div>
								</div>
							</jstl:when>
						</jstl:choose>

		</jstl:otherwise>
	</jstl:choose>
	
			<div id="pagination" class="copyright" style="
    text-align: center;">

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
