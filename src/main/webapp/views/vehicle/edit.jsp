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
		<form:form action="vehicle/user/edit.do" modelAttribute="vehicle" method="post"
			class="form-horizontal" role="form">

			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="user" />
			<form:hidden path="deleted" />

			<div class="form-group">
				<form:label path="brand" class="control-label col-md-2"
					for="brand">
					<spring:message code="vehicle.brand" />
				</form:label>
				<div class="col-md-8">
					<form:input path="brand" class="form-control" id="brand" />
					<form:errors class="error create-message-error" path="brand" />
				</div>
			</div>

			<div class="form-group">
				<form:label path="model" class="control-label col-md-2"
					for="model">
					<spring:message code="vehicle.model" />
				</form:label>
				<div class="col-md-8">
					<form:input path="model" class="form-control" id="model" />
					<form:errors class="error create-message-error" path="model" />
				</div>
			</div>
			
			<div class="form-group">
				<form:label path="color" class="control-label col-md-2"
					for="color">
					<spring:message code="vehicle.color" />
				</form:label>
				<div class="col-md-8">
					<form:input path="color" class="form-control" id="color" />
					<form:errors class="error create-message-error" path="color" />
				</div>
			</div>
			
			<div class="form-group">
				<form:label path="picture" class="control-label col-md-2"
					for="picture">
					<spring:message code="vehicle.picture" />
				</form:label>
				<div class="col-md-8">
					<form:input path="picture" class="form-control" id="picture" />
					<form:errors class="error create-message-error" path="picture" />
				</div>
			</div>
						<!-- Action buttons -->
			<acme:submit name="save" code="vehicle.save" />

			<jstl:if test="${vehicle.id != 0}">
				<acme:submit_confirm name="delete" code="vehicle.delete"
					codeConfirm="vehicle.confirm.delete" />
			</jstl:if>

			<acme:cancel code="vehicle.cancel" url="vehicle/user/list.do" />
			
		</form:form>


	</div>
</div>