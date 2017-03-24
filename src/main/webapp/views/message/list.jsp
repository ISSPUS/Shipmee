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

<style>
</style>

<div class="sub-menu-messages">
	<ul class="nav nav-pills nav-justified sub-menu-messages-options"
		style="">
		<li role="presentation"
			<jstl:if test="${infoMessages eq 'messages.received'}">
				class="active"
			</jstl:if>>
			<a href="message/user/received.do?page=1"> <spring:message
					code="messages.received" />
		</a>
		</li>
		<li role="presentation"
			<jstl:if test="${infoMessages eq 'messages.sent'}">
				class="active"
			</jstl:if>><a
			href="message/user/sent.do?page=1" > <spring:message
					code="messages.sent" />
		</a></li>
		<li role="presentation"><a href="message/user/create.do"> <spring:message
					code="message.create" />
		</a></li>
	</ul>
</div>



<div class="container">


	<jstl:forEach items="${messages}" var="messageRow">

		<!-- /row -->
		<div class="row">

			<div>
				<div class="panel panel-default">
					<div class="panel-heading">

						<h4 class="messages-subject">
							<jstl:out value="${messageRow.subject}" />
						</h4>
						<jstl:choose>
							<jstl:when test="${infoMessages eq 'messages.received'}">
								<spring:message code="message.sender" />: 
								<a href="user/profile.do?userId=${messageRow.sender.id}"><jstl:out value="${messageRow.sender.userAccount.username }" /></a>

							</jstl:when>
							<jstl:otherwise>
								<spring:message code="message.recipient" />: 
								<a href="user/profile.do?userId=${messageRow.recipient.id}"><jstl:out value="${messageRow.recipient.userAccount.username }" /></a>

							</jstl:otherwise>
						</jstl:choose>

						<br />
						<fmt:formatDate value="${messageRow.moment}"
							pattern="dd-MM-yyyy HH:mm" />
						<br />

					</div>
					<div class="panel-body">
						<p class="text-message-body">
							<jstl:out value="${messageRow.body}"></jstl:out>
						</p>
					</div>
					<!-- /panel-body -->
				</div>
				<!-- /panel panel-default -->
			</div>
			<br />

			<!-- /col-sm-5 -->
		</div>
		<!-- /row -->
	</jstl:forEach>
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
