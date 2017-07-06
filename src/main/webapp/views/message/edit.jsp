<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

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

<script async src="scripts/jquery.bootpag.min.js"></script>
<link rel="stylesheet" href="styles/assets/css/lateral-menu.css"
	type="text/css">
<link rel="stylesheet" href="styles/assets/css/style-list.css"
	type="text/css">
<link rel="stylesheet" href="styles/assets/css/style-messages.css"
	type="text/css">
<link
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css"
	rel="stylesheet">
<link href="https://select2.github.io/dist/css/select2.min.css"
	rel="stylesheet">
<script src="https://select2.github.io/dist/js/select2.full.js"></script>


<div class="blue-barra">
	<div class="container">
		<div class="row">
			<h3>
				<spring:message code="message.new" />

			</h3>
		</div>
		<!-- /row -->
	</div>
</div>

<div class="container">

	<div class="row inbox " style="margin-top: 2%;">
		<div class="col-md-4">
			<div class="panel panel-default">
				<div class="panel-body inbox-menu">
					<a href="message/actor/create.do" class="btn btn-danger btn-block"><spring:message
							code="message.create" /></a>
					<ul>
						<li><a href="message/actor/received.do?page=1"><i
								class="fa fa-inbox"></i> <spring:message
									code="messages.received" /> <span class="label label-danger">${total_received}</span></a></li>
						<li><a href="message/actor/sent.do?page=1"><i
								class="fa fa-inbox"></i> <spring:message code="messages.sent" />
								<span class="label label-danger">${total_sent}</span></a></li>

					</ul>
				</div>
			</div>
		</div>

		<div class="col-md-8">
			<form:form action="message/actor/edit.do" method="post"
				modelAttribute="messageFormService" class="form-horizontal" role="form">

				<form:hidden path="sender" />
				<form:hidden path="moment" />

				<div class="form-group">
					<form:label path="recipient" class="control-label col-md-2"
						for="recipient">
						<spring:message code="message.recipient"/>
					</form:label>
					<div class="col-md-8">
						<spring:message code="message.placeholder.recipient" var="pRecipient"/>
						
						<form:input path="recipient" class="form-control" id="recipient" required="required" placeholder="${pRecipient}" />
						<form:errors class="error create-message-error" path="recipient" />
					</div>
				</div>

				<div class="form-group">
					<form:label path="subject" class="control-label col-md-2"
						for="subject">
						<spring:message code="message.subject" />
					</form:label>
					<div class="col-md-8">
						<form:input path="subject" class="form-control noresize"
							id="subject" required="required"/>
						<form:errors class="error create-message-error" path="subject" />
					</div>
				</div>
				<div class="form-group">
					<form:label path="body" class="control-label col-md-2" for="body">
						<spring:message code="message.body" />
					</form:label>
					<div class="col-md-8">
						<form:textarea rows="3" path="body" class="form-control noresize"
							id="body" required="required"/>
						<form:errors class="error create-message-error" path="body" />
					</div>
				</div>
				
				<jstl:if test="${messageError != null}">
					<div class="error" style="text-align: center;">
						<spring:message code="${messageError}"/>
						<br/><br/>
					</div>
				</jstl:if>

				<div class="form-group text-center profile-userbuttons">
					<div class="col-md-12">
						<button type="submit" name="save" class="btn  btn-primary">
							<span class="glyphicon glyphicon-send"></span>
							<spring:message code="message.send" />
						</button>
						<acme:cancel code="message.cancel" url="/message/actor/received.do?page=1" />
					</div>
				</div>

			</form:form>
		</div>



	</div>


</div>