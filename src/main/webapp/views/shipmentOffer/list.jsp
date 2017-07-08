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
<script async src="scripts/jquery.bootpag.min.js"></script>

<div class="blue-barra" style="padding-top: 0.75%;padding-bottom: 0.75%;">
	<div class="container">
		<div class="row">
			<h3>
			<spring:message code="shipmentOffer.list.for" />
			<a
				href="shipment/display.do?shipmentId=${shipmentId}">
				<jstl:out value="${shipment.itemName}" />
			</a>
			</h3>
		</div>
		<!-- /row -->
	</div>
</div>
<div class="container">


	<jstl:forEach items="${shipmentOffers}" var="shipmentOfferRow">
		<div class="row"
			style="margin-top: 2%; margin-bottom: 2%; margin-right: 0 !important;">
			<div class="col-xs-12 col-lg-9 offer-shipment"
				style="float: none; margin: 0 auto;">
				<div class="row perfil-info-offer">
					<div class="img-perfil-offer col-xs-4 col-sm-2 col-lg-2">
						<jstl:if test="${shipmentOfferRow.user.photo == null}">
							<img
								src="https://www.beautifulpeople.com/cdn/beautifulpeople/images/default_profile/signup_female.png"
								class="img-thumbnail  profile-offer-img" style="margin-top: 21%;">						
						</jstl:if>
						<jstl:if test="${shipmentOfferRow.user.photo != null}">
							<img
								src="${shipmentOfferRow.user.photo}"
								class="img-thumbnail  profile-offer-img" style="margin-top: 21%;">					
						</jstl:if>
					</div>
					<div class="data-perfil col-xs-8 col-sm-4">
						<div class="col-xs-12">
							<h4>
								<spring:message code="shipmentOffer.list.by" />
								<a href="user/profile.do?userId=${shipmentOfferRow.user.id}">
									<jstl:out value="${shipmentOfferRow.user.name}" />
								</a>
							</h4>
						</div>
						<div class="col-xs-12 frontera">
							<h5 class="offer-profile-info"><spring:message code="shipment.origin" />: <a>${shipmentOfferRow.shipment.origin}</a></h5>
							<h5 class="offer-profile-info"><spring:message code="shipment.destination" />: <a>${shipmentOfferRow.shipment.destination}</a></h5>
						</div>
						
						<jstl:set var="actPayPalObject" value="${null}"/>
						
						<jstl:forEach var="a" items="${paypalObjects}">
								<jstl:if test="${a.feePayment.shipmentOffer.id == shipmentOfferRow.id}">
									<jstl:set var="actPayPalObject" value="${a}"/>
								</jstl:if>
						</jstl:forEach>

						
						<div class="col-xs-12">
							<h5 class="offer-profile-info">
							<jstl:if test="${empty actPayPalObject or actPayPalObject.payStatus ne 'CREATED'}">
								<jstl:if test="${shipmentOfferRow.acceptedBySender}">
									<p>
										<b><spring:message code="shipmentOffer.status" />: <span style="color: #58e19d"><spring:message code="shipmentOffer.accepted" /></span></b>
									</p>
								</jstl:if>
								<jstl:if test="${shipmentOfferRow.rejectedBySender}">
									<p>
										<b><spring:message code="shipmentOffer.status" />: <span style="color: #ff8686"><spring:message code="shipmentOffer.rejected" /></span></b>
									</p>
								</jstl:if>
								<jstl:if
									test="${!shipmentOfferRow.rejectedBySender && !shipmentOfferRow.acceptedBySender}">
									<p>
										<b><spring:message code="shipmentOffer.status" />: <span style="color: #ffb66d"><spring:message code="shipmentOffer.pending" /></span></b>
									</p>
								</jstl:if>
								</jstl:if>
								<jstl:if test="${not (empty actPayPalObject or actPayPalObject.payStatus ne 'CREATED')}">
										<p>
											<b><spring:message code="routeOffer.status" />: <span style="color: #ffb66d"><spring:message code="routeOffer.pending.payment" /></span></b>
										</p>
								</jstl:if>
							</h5>
						</div>
					</div>
					<div class="col-xs-11 col-sm-3"
						style="padding-top: 1.5%; text-align: center; vertical-align: middle;">
						<div style="font-size: 190%;">
						<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${shipmentOfferRow.amount}" var="formatAmount" />
						<jstl:choose>
							<jstl:when test="${!shipmentOfferRow.rejectedBySender && !shipmentOfferRow.acceptedBySender && currentUser.id != shipmentOfferRow.user.id}">
							<span class="badge badge-shipmentoffer-price" style="background-color: #0b6a92;"><jstl:out
								value="${formatAmount}" />&#8364;</span>
							
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
							<jstl:if test="${!shipmentOfferRow.rejectedBySender && !shipmentOfferRow.acceptedBySender && currentUser.id != shipmentOfferRow.user.id}">
								<jstl:if test="${!(not empty actPayPalObject && actPayPalObject.payStatus == 'CREATED')}">
									<div class="col-xs-6 col-sm-12">
										<button type="button"
											class="btn btn-success btn-shipmentOffer-actions"
											onclick="location.href = 'shipmentOffer/user/accept.do?shipmentOfferId=${shipmentOfferRow.id}';">
											<spring:message code="shipmentOffer.accept" />
										</button>
									</div>
									<div class="col-xs-6 col-sm-12" style="text-align: center;">
										<button type="button"
											class="btn btn-danger btn-shipmentOffer-actions"
											onclick="location.href = 'shipmentOffer/user/deny.do?shipmentOfferId=${shipmentOfferRow.id}';">
											<spring:message code="shipmentOffer.deny" />
										</button>
									</div>
								</jstl:if>
							</jstl:if>
						</div>
					</div>
				</div>
				
				
				<jstl:if test="${(!shipmentOfferRow.rejectedBySender && !shipmentOfferRow.acceptedBySender && currentUser.id != shipmentOfferRow.user.id) && not (empty actPayPalObject or actPayPalObject.payStatus ne 'CREATED')}">

				<div class="alert alert-warning">
					<strong><spring:message code="feePayment.message.failurePayPal1" />.</strong> <spring:message code="feePayment.message.failurePayPal2" />:<br>
					- <a href="user/payPal/returnPayment.do?trackingId=${actPayPalObject.trackingId}"><spring:message code="feePayment.message.failurePayPal.sentence1.link" /></a>, <spring:message code="feePayment.message.failurePayPal.sentence1" />.<br>
					- <a href="feepayment/user/cancelPaymentInProgress.do?feePaymentId=${actPayPalObject.feePayment.id}"><spring:message code="feePayment.message.failurePayPal.sentence2.link" /></a>, <spring:message code="feePayment.message.failurePayPal.sentence2.shipment" />.
				</div>
			
				</jstl:if>
				
				
				<jstl:if test="${shipmentOfferRow.description != null}">
				<hr>
				</jstl:if>
				<div class="row description-offer">
					<div class="descipcion-offer-inside col-xs-12"
						style="font-family: sans-serif; font-size: 14px;">
						<jstl:if test="${shipmentOfferRow.description == 'shipmentOffer.description.after.accept' }">
							<spring:message code="${shipmentOfferRow.description}" />
						</jstl:if>
						<jstl:if test="${shipmentOfferRow.description != 'shipmentOffer.description.after.accept' }">
							<jstl:out value="${shipmentOfferRow.description}" />
						</jstl:if>
					</div>
				</div>
				<jstl:choose>
					<jstl:when test="${shipmentOfferRow.description != null}">
						<hr>
					</jstl:when>
					<jstl:otherwise>
						<br>
					</jstl:otherwise>
				</jstl:choose>
			</div>
		</div>
	</jstl:forEach>

	<!--   
	   -->
	<jstl:if test="${fn:length(shipmentOffers) == 0}">
		<div class="container" style="margin-top:25px">
			<div class="alert alert-info">
				<strong><spring:message code="shipmentOffer.anything" /></strong>
			</div>
		</div>
	</jstl:if>

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