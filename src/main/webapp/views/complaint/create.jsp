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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Form -->
<link rel="stylesheet" href="styles/assets/css/datetimepicker.min.css" />
<script type="text/javascript" src="scripts/moment.js"></script>
<script type="text/javascript" src="scripts/datetimepicker.min.js"></script>
<link rel="stylesheet" href="styles/assets/css/lateral-menu.css"
	type="text/css">
	
<style>
.date .dropdown-menu{

	background-color:white ! important;
}
.formulario {
	padding: 10%;
}
</style>
<div class="container">
	<div class="row formulario">
		<form:form action="complaint/user/create.do" modelAttribute="complaint" method="post"
			class="form-horizontal" role="form">

			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="type" />
			<form:hidden path="moderator" />
			<form:hidden path="creator" />
			<form:hidden path="involved" />

			<div class="form-group">
				<form:label path="explanation" class="control-label col-md-2"
					for="explanation">
					<spring:message code="complaint.explanation" />
				</form:label>
				<div class="col-md-8">
					<form:textarea path="explanation" class="form-control" id="explanation" />
					<form:errors class="error create-message-error" path="explanation" />
				</div>
			</div>

						<!-- Action buttons -->
			<acme:submit_confirm name="create" code="complaint.create"
					codeConfirm="complaint.confirm.create" />

			<acme:cancel code="complaint.cancel" url="" />
			
		</form:form>


	</div>
</div>