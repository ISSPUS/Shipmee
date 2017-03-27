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
				<spring:message code="message.message" />
			</h3>
		</div>
		<!-- /row -->
	</div>
</div>

<div class="container">

	<div class="row inbox " style="margin-top: 2%;">
		<div class="col-md-3">
			<div class="panel panel-default">
				<div class="panel-body inbox-menu">
					<a href="message/actor/create.do" class="btn btn-danger btn-block"><spring:message
							code="message.create" /></a>
					<ul>
						<li
							<jstl:if test="${infoMessages eq 'messages.received'}">
													
								class="active"
							</jstl:if>>
							<a href="message/actor/received.do?page=1"><i
								class="fa fa-inbox"></i> <spring:message
									code="messages.received" /> <span class="label label-danger">${fn:length(messages)}</span></a>
						</li>

						<li
							<jstl:if test="${infoMessages eq 'messages.sent'}">
													
								class="active"
							</jstl:if>>

							<a href="message/actor/sent.do?page=1"><i class="fa fa-inbox"></i>
								<spring:message code="messages.sent" /> <span
								class="label label-danger">${fn:length(messages)}</span></a>
						</li>
					</ul>
				</div>
			</div>
		</div>

		<div class="col-md-9">
			<div class="table-container">
				<table class="table table-filter">
					<tbody>
						<jstl:if test="${not empty messages}">
							<jstl:forEach items="${messages}" var="messageRow">

								<tr data-status="pagado">
									<td>
										<div class="media">

											<jstl:choose>
												<jstl:when test="${infoMessages eq 'messages.received'}">
													<a href="#" class="pull-left"> <img
														src="${messageRow.sender.photo}" class="media-photo"></a>

												</jstl:when>
												<jstl:otherwise>
													<a href="#" class="pull-left"> <img
														src="${messageRow.recipient.photo}" class="media-photo"></a>


												</jstl:otherwise>
											</jstl:choose>


											<div class="media-body">
												<span class="media-meta pull-right"> <fmt:formatDate
														type="both" dateStyle="medium" timeStyle="medium"
														value="${messageRow.moment}" /></span>
												<h4 class="title">
													<jstl:choose>
														<jstl:when test="${infoMessages eq 'messages.received'}">
															<a href="user/profile.do?userId=${messageRow.sender.id}"><jstl:out
																	value="${messageRow.sender.userAccount.username }" /></a>

														</jstl:when>
														<jstl:otherwise>
															<a
																href="user/profile.do?userId=${messageRow.recipient.id}"><jstl:out
																	value="${messageRow.recipient.userAccount.username }" /></a>

														</jstl:otherwise>
													</jstl:choose>
												</h4>

												<span class="pendiente"> <jstl:out
														value="${messageRow.subject}" />
												</span>

												<p class="summary">
													<jstl:out value="${messageRow.body}"></jstl:out>
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


		<jstl:if test="${fn:length(messages) ==0}">
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
