<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<link href='https://fonts.googleapis.com/css?family=Open+Sans'
	rel='stylesheet' type='text/css'>
<link
	href="styles/assets/css/font-awesome.min.css"
	rel="stylesheet">

<link rel="stylesheet" href="styles/assets/css/style-lists-offers.css"  type="text/css">

<div class="blue-barra"
	style="padding-top: 0.75%; padding-bottom: 0.75%;">
	<div class="container">
		<div class="row">
			<h3>
				<spring:message code="routeOffer.list.for" />
				<a href="route/display.do?routeId=${route.id}">
					<jstl:out value="${route.origin}" />
					<jstl:if test="${route != null}"> -> </jstl:if>
					<jstl:out value="${route.destination}" />
				</a>
			</h3>
		</div>
		<!-- /row -->
	</div>
</div>
<script src="scripts/jquery.bootpag.min.js"></script>

<div class="container">
	<jstl:forEach items="${routeOffers}" var="routeOfferRow">
		<div class="row"
			style="margin-top: 2%; margin-bottom: 2%; margin-right: 0 !important;">
			<div class="col-xs-12 col-lg-9 offer-shipment"
				style="float: none; margin: 0 auto;">
				<div class="row perfil-info-offer">
					<div class="img-perfil-offer col-xs-4 col-sm-2 col-lg-2">
						<jstl:if test="${routeOfferRow.user.photo == null}">
							<img
								src="https://www.beautifulpeople.com/cdn/beautifulpeople/images/default_profile/signup_female.png"
								class="img-thumbnail  profile-offer-img" style="margin-top: 21%;">						
						</jstl:if>
						<jstl:if test="${routeOfferRow.user.photo != null}">
							<img
								src="${routeOfferRow.user.photo}"
								class="img-thumbnail  profile-offer-img" style="margin-top: 21%;">					
						</jstl:if>
					</div>
					<div class="data-perfil col-xs-8 col-sm-4">
						<div class="col-xs-12">
							<h4>
								<spring:message code="shipmentOffer.list.by" />
								<a href="user/profile.do?userId=${routeOfferRow.user.id}">
									<jstl:out value="${routeOfferRow.user.name}" />
								</a>
							</h4>
						</div>
						<div class="col-xs-12 frontera">
							<h5 class="offer-profile-info"><spring:message code="route.origin" />: <a>${routeOfferRow.route.origin}</a></h5>
							<h5 class="offer-profile-info"><spring:message code="route.destination" />: <a>${routeOfferRow.route.destination}</a></h5>
						</div>
						
						<jstl:set var="actPayPalObject" value="${null}"/>
						
						<jstl:forEach var="a" items="${paypalObjects}">
								<jstl:if test="${a.feePayment.routeOffer.id == routeOfferRow.id}">
									<jstl:set var="actPayPalObject" value="${a}"/>
								</jstl:if>
						</jstl:forEach>
						
						<div class="col-xs-12">
							<h5 class="offer-profile-info">
								<jstl:if test="${empty actPayPalObject or actPayPalObject.payStatus ne 'CREATED'}">
									<jstl:if test="${routeOfferRow.acceptedByCarrier}">
										<p>
											<b><spring:message code="routeOffer.status" />: <span style="color: #58e19d"><spring:message code="routeOffer.accepted" /></span></b>
										</p>
									</jstl:if>
									<jstl:if test="${routeOfferRow.rejectedByCarrier}">
										<p>
											<b><spring:message code="routeOffer.status" />: <span style="color: #ff8686"><spring:message code="routeOffer.rejected" /></span></b>
										</p>
									</jstl:if>
									<jstl:if
										test="${!routeOfferRow.rejectedByCarrier && !routeOfferRow.acceptedByCarrier}">
										<p>
											<b><spring:message code="routeOffer.status" />: <span style="color: #ffb66d"><spring:message code="routeOffer.pending" /></span></b>
										</p>
									</jstl:if>
								</jstl:if>
								<jstl:if test="${not (empty actPayPalObject or actPayPalObject.payStatus ne 'CREATED')}">
										<p>
											<b><spring:message code="routeOffer.status" />: <span style="color: #ffb66d"><spring:message code="routeOffer.pending.payment" /></span></b>
										</p>
								</jstl:if>
								
								<jstl:if test="${routeOfferRow.shipment != null}">
									<b><spring:message code="routeOffer.shipment" />: <a href="shipment/display.do?shipmentId=${routeOfferRow.shipment.id}">
									<jstl:out value="${routeOfferRow.shipment.itemName}" />
								</a></b>
								</jstl:if>


							</h5>
						</div>
					</div>
					<div class="col-xs-11 col-sm-3"
						style="padding-top: 1.5%;text-align: center; vertical-align: middle;">
						<div style="font-size: 190%;">
						<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${routeOfferRow.amount}" var="formatAmount" />
						<jstl:choose>
							<jstl:when test="${!routeOfferRow.rejectedByCarrier && !routeOfferRow.acceptedByCarrier && currentUser.id != routeOfferRow.user.id}">
							<span class="badge badge-shipmentoffer-price" style="background-color: #0b6a92;">
								<jstl:out value="${formatAmount}" />&#8364;</span>
							</jstl:when>
							<jstl:otherwise>
								<span class="badge badge-shipmentoffer-price"><jstl:out
								value="${formatAmount}" />&#8364;</span>
							</jstl:otherwise>
						</jstl:choose>
						</div>
						<div><a><spring:message code="master.page.comissions" /></a></div>
						
					</div>

					<div class="botones col-xs-11 col-sm-2 col-lg-3" style="padding-top: 1.5%;">
						<div class="profile-userbuttons"
							style="text-align: center;">
							
							<jstl:if
								test="${!routeOfferRow.rejectedByCarrier && !routeOfferRow.acceptedByCarrier && currentUser.id != routeOfferRow.user.id}">
								<jstl:if test="${empty actPayPalObject or actPayPalObject.payStatus != 'CREATED'}">
									<div class="col-xs-6 col-sm-12">
										<button type="button"
											class="btn btn-success btn-shipmentOffer-actions"
											onclick="location.href = 'routeOffer/user/accept.do?routeOfferId=${routeOfferRow.id}';">
											<spring:message code="routeOffer.accept" />
										</button>
									</div>
									<div class="col-xs-6 col-sm-12" style="text-align: center;">
										<button type="button"
											class="btn btn-danger btn-shipmentOffer-actions"
											onclick="location.href = 'routeOffer/user/deny.do?routeOfferId=${routeOfferRow.id}';">
											<spring:message code="routeOffer.deny" />
										</button>
									</div>
								</jstl:if>
							</jstl:if>
						</div>
					</div>
				</div>
				
				<jstl:if test="${currentUser.id == routeOfferRow.user.id && not (empty actPayPalObject or actPayPalObject.payStatus ne 'CREATED')}">

				<div class="alert alert-warning">
					<strong><spring:message code="feePayment.message.failurePayPal1" />.</strong> <spring:message code="feePayment.message.failurePayPal2" />:<br>
					- <a href="user/payPal/returnPayment.do?trackingId=${actPayPalObject.trackingId}"><spring:message code="feePayment.message.failurePayPal.sentence1.link" /></a>, <spring:message code="feePayment.message.failurePayPal.sentence1" />.<br>
					- <a href="feepayment/user/cancelPaymentInProgress.do?feePaymentId=${actPayPalObject.feePayment.id}"><spring:message code="feePayment.message.failurePayPal.sentence2.link" /></a>, <spring:message code="feePayment.message.failurePayPal.sentence2" />.
				</div>
			
				</jstl:if>
				
				
				<jstl:if test="${routeOfferRow.description != null}">
				<hr>
				</jstl:if>
				<div class="row description-offer">
					<div class="descipcion-offer-inside col-xs-12"
						style="font-family: sans-serif; font-size: 14px;">
						<jstl:out value="${routeOfferRow.description}" />
					</div>
				</div>
				<jstl:choose>
					<jstl:when test="${routeOfferRow.description != null}">
						<hr>
					</jstl:when>
					<jstl:otherwise>
						<br>
					</jstl:otherwise>
				</jstl:choose>
			</div>
		</div>

	</jstl:forEach>

<div id="pagination" style="margin: auto;float: none" class="copyright pagination-center">

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

<jstl:if test="${fn:length(routeOffers) == 0}">
	<div class="container" style="margin-top:25px">
		<div class="alert alert-info">
			<strong><spring:message code="routeOffer.anything" /></strong>
		</div>
	</div>
</jstl:if>

