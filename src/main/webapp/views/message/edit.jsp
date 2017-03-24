<%--
 * edit.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
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


<div class="sub-menu-messages">
	<ul class="nav nav-pills nav-justified sub-menu-messages-options"
		style="">
		<li role="presentation"><a
			href="message/user/received.do?page=1"> <spring:message
					code="messages.received" />
		</a></li>
		<li role="presentation"><a href="message/user/sent.do?page=1">
				<spring:message code="messages.sent" />
		</a></li>
		<li role="presentation" class="active"><a href="message/user/create.do"> <spring:message
					code="message.create" />
		</a></li>
	</ul>
</div>

<br />
<br />
<div class="container">
	<form:form action="message/user/edit.do" method="post"
		modelAttribute="messageForm" class="form-horizontal" role="form">

		<form:hidden path="sender" />
		<form:hidden path="moment" />


		<div class="form-group">
			<form:label path="recipient" class="control-label col-md-2"
				for="recipient">
				<spring:message code="message.recipient" />
			</form:label>
			<div class="col-md-8">
				<form:input path="recipient" class="form-control" id="recipient" />
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
					id="subject" />
				<form:errors class="error create-message-error" path="subject" />
			</div>
		</div>
		<div class="form-group">
			<form:label path="body" class="control-label col-md-2" for="body">
				<spring:message code="message.body" />
			</form:label>
			<div class="col-md-8">
				<form:textarea rows="5" path="body" class="form-control noresize"
					id="body" />
				<form:errors class="error create-message-error" path="body" />
			</div>
		</div>

		<div class="form-group">
			<div class="col-md-10">

				<button type="submit" name="save" class="btn btn-primary btn-create-message"
					style="float: right">
					<spring:message code="message.send" />
				</button>
				<button type="button"  onclick="javascript: relativeRedir('/message/user/received.do?page=1')" name="cancel" class="btn btn-primary btn-create-message"
					style="float: right">
					<spring:message code="message.cancel" />
				</button>
		</div>
</div>

</form:form>
</div>

<br />

<script>

function setDefaultUsername() {
	var username = location.search.split('username=')[1] ? location.search.split('username=')[1] : '';
	
	document.getElementById("recipient").value = username;
}

window.onload = setDefaultUsername();

</script>