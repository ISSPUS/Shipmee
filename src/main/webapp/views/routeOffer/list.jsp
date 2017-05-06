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
	href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css"
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
					<div class="img-perfil-offer col-xs-4 col-sm-3 col-lg-2">
						<jstl:if test="${routeOfferRow.user.photo == null}">
							<img
								src="https://www.beautifulpeople.com/cdn/beautifulpeople/images/default_profile/signup_female.png"
								class="img-thumbnail  profile-offer-img ">						
						</jstl:if>
						<jstl:if test="${routeOfferRow.user.photo != null}">
							<img
								src="${routeOfferRow.user.photo}"
								class="img-thumbnail  profile-offer-img ">					
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
						<div class="col-xs-12">
							<h5 class="offer-profile-info">Ciudad:Sevilla</h5>
						</div>
						<div class="col-xs-12">
							<h5 class="offer-profile-info">
								<jstl:if test="${routeOfferRow.acceptedByCarrier}">
									<p>
										<b><spring:message code="routeOffer.accepted" /></b>
									</p>
								</jstl:if>
								<jstl:if test="${routeOfferRow.rejectedByCarrier}">
									<p>
										<b><spring:message code="routeOffer.rejected" /></b>
									</p>
								</jstl:if>
								<jstl:if
									test="${!routeOfferRow.rejectedByCarrier && !routeOfferRow.acceptedByCarrier}">
									<p>
										<b><spring:message code="routeOffer.pending" /></b>
									</p>
								</jstl:if>

							</h5>
						</div>
					</div>
					<div class="col-xs-2 col-sm-3"
						style="padding-top: 1.5%; font-size: 190%; text-align: center; vertical-align: middle;">
						<span class="badge badge-shipmentoffer-price"><jstl:out
								value="${routeOfferRow.amount}" />&#8364;</span>
					</div>
					<div class="botones col-xs-10 col-sm-2 col-lg-3">
						<div class="col-xs-12"
							style="text-align: center; padding-top: 2%;">
							<jstl:if
								test="${!routeOfferRow.rejectedByCarrier && !routeOfferRow.acceptedByCarrier && currentUser.id != routeOfferRow.user.id}">
								<div class="col-xs-6 col-sm-12">
									<button type="button"
										class="btn btn-primary btn-shipmentOffer-actions"
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

						</div>
					</div>
				</div>
				<hr>
				<div class="row description-offer">
					<div class="descipcion-offer-inside col-xs-12"
						style="font-family: sans-serif; font-size: 130%;">
						<jstl:out value="${routeOffer.description}" />
					</div>
				</div>
			</div>
		</div>

	</jstl:forEach>

<div id="pagination" style="margin: auto;float: none" class="copyright">

	<script>
		$('#pagination').bootpag({
			total : <jstl:out value="${total_pages}"></jstl:out>,
			page : <jstl:out value="${p}"></jstl:out>,
			maxVisible : 5,
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
	<center>
		<h2>
			<spring:message code="routeOffer.anything" />
		</h2>
	</center>
</jstl:if>

