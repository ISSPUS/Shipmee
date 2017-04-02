<%--
 * list.jsp
 *
 * Copyright (C) 2015 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

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

<script src="scripts/jquery.bootpag.min.js"></script>
<link rel="stylesheet" href="styles/assets/css/lateral-menu.css"
	type="text/css">
<link rel="stylesheet" href="styles/assets/css/style-list.css"
	type="text/css">

<link rel="stylesheet" href="styles/assets/css/style-list.css"
	type="text/css">


<link rel="stylesheet" href="styles/assets/css/style-messages.css"
	type="text/css">

<div class="blue-barra" >
	<div class="container">
		<div class="row">
			<h3>
				<spring:message code="user.list" />
			</h3>
		</div>
		<!-- /row -->
	</div>
</div>

<div class="container">

		<div class="col-md-9">
			<div class="table-container">
				<table class="table table-filter">
					<tbody>
						<jstl:if test="${not empty users.content}">
							<jstl:forEach items="${users.content}" var="userRow">

								<tr data-status="pagado">
									<td>
										<div class="media">
											<a href="#" class="pull-left"> <img
												src="${userRow.photo}" class="media-photo"></a>

											<div class="media-body">
												<span class="media-meta pull-right"> <fmt:formatDate
														type="both" dateStyle="medium" timeStyle="medium"
														value="${userRow.birthDate}" /></span>
												<h4 class="title">
													<a href="user/profile.do?userId=${usereRow.id}"><jstl:out
															value="${userRow.userAccount.username }" /></a>
												</h4>

												<span class="pendiente"> <jstl:out
														value="${userRow.name}" /> <jstl:out
														value="${userRow.surname}" />
												</span>

												<p class="summary">
													<jstl:out value="${userRow.dni}"></jstl:out>
													
													<jstl:set var="isModerator" scope="session" value="FALSE"/>
													<jstl:forEach items="${userRow.userAccount.authorities}" var="actRow">
														<jstl:if test="${actRow.authority eq 'MODERATOR'}">
															<jstl:set var="isModerator" scope="session" value="TRUE"/>
														</jstl:if>
													</jstl:forEach>
																										
													<br />
													<jstl:if test="${!isModerator}">
														<a href="user/administrator/turnIntoModerator.do?userId=${userRow.id}">
															<spring:message code="user.turnIntoModerator" />
														</a>
													</jstl:if>
													<jstl:if test="${isModerator}">
														<a href="user/administrator/unturnIntoModerator.do?userId=${userRow.id}">
															<spring:message code="user.unturnIntoModerator" />
														</a>
													</jstl:if>
												</p>
											</div>
										</div>
									</td>
								</tr>

							</jstl:forEach>
						</jstl:if>

					</tbody>
				</table>
			</div>
		</div>


		<jstl:if test="${fn:length(users.content) ==0}">
			<center>
				<h2>
					<spring:message code="messages.anything" />
				</h2>
			</center>
		</jstl:if>

		<div id="pagination" class="copyright">

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


</div>
