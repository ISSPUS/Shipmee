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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Form -->
<link rel="stylesheet" href="styles/assets/css/datetimepicker.min.css" />
<script type="text/javascript" src="scripts/moment.js"></script>
<script type="text/javascript" src="scripts/datetimepicker.min.js"></script>
<link rel="stylesheet" href="styles/assets/css/lateral-menu.css"
	type="text/css">
<link rel="stylesheet" href="styles/assets/css/style-messages.css"
	type="text/css">
	
<style>
.date .dropdown-menu {
	background-color: white ! important;
}

.formulario {
	padding: 10%;
}
</style>

<div class="blue-barra">
	<div class="container">
		<div class="row">
			<h3>
				<spring:message code="vehicle.edit.vehicle" />
			</h3>
		</div>
		<!-- /row -->
	</div>
</div>

<div class="container">
	<div class="row formulario">
		<div class="col-xs-12 col-sm-12 col-md-8 col-lg-8"
			style="margin: 0 auto; float: none;">
			<form:form action="vehicle/user/edit.do" modelAttribute="vehicle"
				method="post" class="form-horizontal" role="form">

				<form:hidden path="id" />
				<form:hidden path="version" />
				<form:hidden path="user" />
				<form:hidden path="deleted" />

				<div class="form-group">
					<form:label path="brand" class="control-label col-md-2" for="brand">
						<spring:message code="vehicle.brand" />
					</form:label>
					<div class="col-md-10">
						<form:input path="brand" class="form-control" id="brand" />
						<form:errors class="error create-message-error" path="brand" />
					</div>
				</div>

				<div class="form-group">
					<form:label path="model" class="control-label col-md-2" for="model">
						<spring:message code="vehicle.model" />
					</form:label>
					<div class="col-md-10">
						<form:input path="model" class="form-control" id="model" />
						<form:errors class="error create-message-error" path="model" />
					</div>
				</div>

				<div class="form-group">
					<form:label path="color" class="control-label col-md-2" for="color">
						<spring:message code="vehicle.color" />
					</form:label>
					<div class="col-md-10">
						<form:input path="color" class="form-control" id="color" />
						<form:errors class="error create-message-error" path="color" />
					</div>
				</div>

				<div class="form-group">
					<form:label path="picture" class="control-label col-md-2"
						for="picture">
						<spring:message code="vehicle.picture" />
					</form:label>
					<div class="col-md-10">
						<form:input path="picture" class="form-control" id="picture" />
						<form:errors class="error create-message-error" path="picture" />
					</div>
				</div>
				<div class="col-md-12" style="text-align: center">
					<button type="submit" name="save" class="btn  btn-primary">
						<span class="glyphicon glyphicon-floppy-disk"></span>
						<spring:message code="vehicle.save" />

					</button>

					<jstl:if test="${vehicle.id != 0}">

						<button type="submit" name="delete" class="btn btn-ok"
							onclick="return confirm('<spring:message code="vehicle.confirm.delete" />')">
							<span class="glyphicon glyphicon-trash"></span>
							<spring:message code="vehicle.delete" />
						</button>

					</jstl:if>

					<acme:cancel code="vehicle.cancel" url="vehicle/user/list.do" />
				</div>
				<!-- Action buttons -->


			</form:form>
		</div>

	</div>
</div>