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
			<h3>
				<spring:message code="complaint.complaints" />
			</h3>
		</div>
		<!-- /row -->
	</div>
</div>


<div class="container">

	<div class="row profile">
		<div class="col-md-9">
			<div class="profile-content">

				<div class="panel panel-default">
					<div class="panel-body">

						<div class="table-container">
							<table class="table table-filter">
								<tbody>


									<jstl:choose>
										<jstl:when test="${not empty complaints}">
											<jstl:forEach items="${complaints}" var="complaint">

												<tr>

													<td>


														<div class="row">

															<div class="info-salida col-lg-6"
																style="margin-bottom: 2%; font-size: 16px;">

																<spring:message code="complaint.creator" />
																:
																<jstl:out
																	value="${complaint.creator.userAccount.username}" />


																<br />
																<spring:message code="complaint.involved" />
																:
																<jstl:out
																	value="${complaint.involved.userAccount.username}" />

																<br />
																<spring:message code="complaint.explanation" />
																:
																<jstl:out value="${complaint.explanation}" />

															</div>

															<div class="btn-group btn-group-justified">

																<div class="col-lg-3" style="margin-top: 5%;">
																	<spring:message code="complaint.omit" var="omit"/>
																	<spring:message code="complaint.omitted" var="omitted"/>
																	<button type="button"
																		class="btn btn-success btn-md btn-block"
																		onclick="location.href = 'complaint/moderator/manage.do?complaintId=${complaint.id}&type=${omitted }';">
																		<jstl:out value="${omit}"/>
																		&nbsp;<i class="glyphicon glyphicon-thumbs-up"></i>
																	</button>
																</div>

																<div class="col-lg-3" style="margin-top: 5%;">
																	<spring:message code="complaint.mild" var="mild"/>
																	<button type="button"
																		class="btn btn-warning btn-md btn-block"
																		onclick="location.href = 'complaint/moderator/manage.do?complaintId=${complaint.id}&type=${mild }';">
																		<jstl:out value="${mild}"/>
																		&nbsp;<i class="glyphicon glyphicon-thumbs-down"></i>
																	</button>
																</div>

																<div class="col-lg-3" style="margin-top: 5%;">
																	<spring:message code="complaint.serious" var="serious"/>
																	<button type="button"
																		class="btn btn-danger btn-md btn-block"
																		onclick="location.href = 'complaint/moderator/manage.do?complaintId=${complaint.id}&type=${serious }';">
																		<jstl:out value="${serious}"/>
																		&nbsp;<i class="glyphicon glyphicon-warning-sign"></i>
																	</button>
																</div>

															</div>

														</div>



													</td>
												</tr>
											</jstl:forEach>
										</jstl:when>
										<jstl:otherwise>
											<p>
												<spring:message code="complaint.results" />
											</p>
										</jstl:otherwise>
									</jstl:choose>
								</tbody>
							</table>


						</div>




					</div>
				</div>
			</div>

		</div>

	</div>
</div>