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

<link rel="stylesheet" href="styles/assets/css/lateral-menu.css"
	type="text/css">
<link rel="stylesheet" href="styles/assets/css/style-list.css"
	type="text/css">

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

	<!-- Menu informacion de las quejas -->
	<security:authorize access="hasRole('ADMIN')">

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
									<div class="huge">0</div>
									<div>
										<spring:message code="feePayment.accepted" />
										!
									</div>
								</div>
							</div>
						</div>
						<a href="feepayment/administrator/list.do?type=Accepted&page=1">
							<div class="panel-footer">
								<span class="pull-left"><spring:message code="complaint.details" /></span> <span
									class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
								<div class="clearfix"></div>
							</div>
						</a>
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
									<div class="huge">0</div>
									<div>
										<spring:message code="feePayment.pending" />
										!
									</div>
								</div>
							</div>
						</div>
						<a href="feepayment/administrator/list.do?type=Pending&page=1">
							<div class="panel-footer">
								<span class="pull-left"><spring:message code="complaint.details" /></span> <span
									class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
								<div class="clearfix"></div>
							</div>
						</a>
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
									<div class="huge">1</div>
									<div>
										<spring:message code="feepayment.rejected" />
										!
									</div>
								</div>
							</div>
						</div>
						<a href="feepayment/administrator/list.do?type=Rejected&page=1">
							<div class="panel-footer">
								<span class="pull-left"><spring:message code="complaint.details" /></span> <span
									class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
								<div class="clearfix"></div>
							</div>
						</a>
					</div>
				</div>
			</div>
		</div>
	</security:authorize>
	<!-- Listado de quejas -->

	<jstl:choose>
		<jstl:when test="${not empty feePayments}">
			<jstl:forEach items="${feePayments}" var="feePayment">
				<div
					class=" col-xs-12 col-sm-10 col-md-7 col-lg-7 table-container panel panel-default"
					style="margin-top: 1%; margin-bottom: 1%">
					<div class="row">
						<div class="col-xs-12">
							<div class="col-xs-6">
								<div class="span3 text-center">
									<h4>
										<a><spring:message code="feePayment.purchaser" />
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
										<a><spring:message code="feePayment.carrier" />
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
						<div class="col-xs-12">
							<p>${feePayment.amount}</p>
						</div>
						<security:authorize access="hasRole('ADMIN')">
							<div class="col-xs-12">
								<div class="info-moderator-${feePayment.type}">
									<spring:message code="feePayment.pending" var="mild" />
									<div style="margin-bottom: 0.5%">
										<h5>
											<jstl:choose>
												<jstl:when test="${feePayment.type == 'Accepted' }">
													<i class="glyphicon glyphicon-thumbs-up"></i>
												</jstl:when>
												<jstl:when test="${feePayment.type == 'Pending'   }">
													<i class="glyphicon glyphicon-thumbs-down"></i>
												</jstl:when>
												<jstl:when test="${feePayment.type == 'Rejected'}">
													<i class="glyphicon glyphicon-warning-sign"></i>
												</jstl:when>
											</jstl:choose>
										</h5>
									</div>
								</div>
							</div>
						</security:authorize>
						<div class="col-xs-12">
							<security:authorize access="hasRole('USER')">
								<div class="btn-group btn-group-justified"
									style="margin-bottom: 2% ! important">

									<div class="col-lg-4">
										<spring:message code="feePayment.accept" var="accept" />
										<button type="button" class="btn btn-success btn-md btn-block"
											onclick="location.href = 'feepayment/user/manage.do?feepaymentId=${feePayment.id}&type=Accepted';">
											<jstl:out value="${accept}" />
											&nbsp;<i class="glyphicon glyphicon-ok-circle"></i>
										</button>
									</div>

									<div class="col-lg-4">
										<spring:message code="feePayment.reject" var="reject" />
										<button type="button" class="btn btn-danger btn-md btn-block"
											onclick="location.href = 'feepayment/user/manage.do?feepaymentId=${feePayment.id}&type=Rejected';">
											<jstl:out value="${reject}" />
											&nbsp;<i class="glyphicon glyphicon-remove-circle"></i>
										</button>
									</div>

								</div>
							</security:authorize>
						</div>
					</div>
				</div>
			</jstl:forEach>
		</jstl:when>
		<jstl:otherwise>
			<p>
				<spring:message code="feePayment.results" />
			</p>
		</jstl:otherwise>
	</jstl:choose>
</div>