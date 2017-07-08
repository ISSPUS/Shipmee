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

complaints: lista de quejas
len_omitted: total de quejas omitidas
len_mild: total de quejas  leves
len_serious: total de quejas graves


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
				<spring:message code="complaint.complaints" />
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
									<i class="fa fa-angle-down fa-5x"></i>
								</div>
								<div class="col-xs-9 text-right">
									<div class="huge"><jstl:out value="${allOmitted}"></jstl:out></div>
									<div>
										<spring:message code="complaint.omitted" />
									</div>
								</div>
							</div>
						</div>
						<a href="complaint/administrator/list.do?type=Omitted&page=1">
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
									<i class="fa fa-angle-up fa-5x"></i>
								</div>
								<div class="col-xs-9 text-right">
									<div class="huge"><jstl:out value="${allMild}"></jstl:out></div>
									<div>
										<spring:message code="complaint.mild" />
									</div>
								</div>
							</div>
						</div>
						<a href="complaint/administrator/list.do?type=Mild&page=1">
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
									<i class="fa fa-angle-double-up fa-5x"></i>
								</div>
								<div class="col-xs-9 text-right">
									<div class="huge"><jstl:out value="${allSerious}"></jstl:out></div>
									<div>
										<spring:message code="complaint.serious" />
									</div>
								</div>
							</div>
						</div>
						<a href="complaint/administrator/list.do?type=Serious&page=1">
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
		<jstl:when test="${not empty complaints}">
			<jstl:forEach items="${complaints}" var="complaint">
				<div
					class=" col-xs-12 col-sm-10 col-md-7 col-lg-7 table-container panel panel-default"
					style="margin-top: 1%; margin-bottom: 1%">
					<div class="row">
						<div class="col-xs-12">
							<div class="col-xs-6">
								<div class="span3 text-center">
									<h4>
										<a href="user/profile.do?userId=${complaint.creator.id}"><spring:message code="complaint.creator" />
											${complaint.creator.userAccount.username}</a>
									</h4>
									<a href="user/profile.do?userId=${complaint.creator.id}"> <jstl:choose>
											<jstl:when test="${not empty complaint.creator.photo}">
												<jstl:set var="imageUser" value="${complaint.creator.photo}" />
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
										<a href="user/profile.do?userId=${complaint.involved.id}"><spring:message code="complaint.involved" />
											${complaint.involved.userAccount.username}</a>
									</h4>
									<a href="user/profile.do?userId=${complaint.involved.id}"> <jstl:choose>
											<jstl:when test="${not empty complaint.involved.photo}">
												<jstl:set var="imageUserEnvolved"
													value="${complaint.involved.photo}" />
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
							<p>${complaint.explanation}</p>
						</div>
						<security:authorize access="hasRole('ADMIN')">
							<div class="col-xs-12">
								<div class="info-moderator-${complaint.type}" style="margin-bottom: 15px;">
									<spring:message code="complaint.mild" var="mild" />
									<div style="margin-bottom: 0.5%">
										<h5>
											<jstl:choose>
												<jstl:when test="${complaint.type == 'Omitted' }">
													<i class="glyphicon glyphicon-thumbs-up"></i>
												</jstl:when>
												<jstl:when test="${complaint.type == 'Mild'   }">
													<i class="glyphicon glyphicon-thumbs-down"></i>
												</jstl:when>
												<jstl:when test="${complaint.type == 'Serious'}">
													<i class="glyphicon glyphicon-warning-sign"></i>
												</jstl:when>
											</jstl:choose>
											<spring:message code="moderate.by" />
											<a style="color: white ! important;"
												href="user/profile.do?userId=${complaint.moderator.id}"><strong><jstl:out
														value="${complaint.moderator.userAccount.username}" /></strong></a>
										</h5>
									</div>
								</div>
							</div>
						</security:authorize>
						<div class="col-xs-12">
							<security:authorize access="hasRole('MODERATOR')">
								<div class="btn-group btn-group-justified"
									style="margin-bottom: 2% ! important">

									<div class="col-lg-4">
										<spring:message code="complaint.omit" var="omit" />
										<button type="button" class="btn btn-success btn-md btn-block"
											onclick="location.href = 'complaint/moderator/manage.do?complaintId=${complaint.id}&type=Omitted';">
											<jstl:out value="${omit}" />
											&nbsp;<i class="glyphicon glyphicon-thumbs-up"></i>
										</button>
									</div>

									<div class="col-lg-4">
										<spring:message code="complaint.mild" var="mild" />
										<button type="button" class="btn btn-warning btn-md btn-block"
											onclick="location.href = 'complaint/moderator/manage.do?complaintId=${complaint.id}&type=Mild';">
											<jstl:out value="${mild}" />
											&nbsp;<i class="glyphicon glyphicon-thumbs-down"></i>
										</button>
									</div>

									<div class="col-lg-4">
										<spring:message code="complaint.serious" var="serious" />
										<button type="button" class="btn btn-danger btn-md btn-block"
											onclick="location.href = 'complaint/moderator/manage.do?complaintId=${complaint.id}&type=Serious';">
											<jstl:out value="${serious}" />
											&nbsp;<i class="glyphicon glyphicon-warning-sign"></i>
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
			<div class="container" style="margin-top: 25px">
				<div class="alert alert-info">
					<strong><spring:message code="complaint.results" /></strong>
				</div>
			</div>

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
	
	<jstl:if test="${messageError != null}">
		<div class="error" style="text-align: center;">
			<spring:message code="${messageError}"/>
			<br/><br/>
		</div>
	</jstl:if>
</div>
