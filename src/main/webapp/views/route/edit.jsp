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
<link rel="stylesheet" href="styles/assets/css/style-form.css"  type="text/css">

<style>
.date .dropdown-menu{

	background-color:white ! important;
}
.formulario {
	padding: 10%;
}
</style>

<div class="blue-barra">
	    <div class="container">
			<div class="row">
				<h3><spring:message code="route.new.route" /></h3>
			</div><!-- /row -->
	    </div>
	</div>
<div class="container">
	<div class="row formulario-sm">
		<form:form action="route/user/edit.do" modelAttribute="routeForm" method="post"
			class="form-horizontal" role="form">

			<form:hidden path="routeId" />

			<div class="form-group">
				<form:label path="origin" class="control-label col-md-2"
					for="recipient">
					<spring:message code="route.origin" />
				</form:label>
				<div class="col-md-8">
					<form:input path="origin" class="form-control" id="origin" />
					<form:errors class="error create-message-error" path="origin" />
				</div>
			</div>

			<div class="form-group">
				<form:label path="origin" class="control-label col-md-2"
					for="destination">
					<spring:message code="route.destination" />
				</form:label>
				<div class="col-md-8">
					<form:input path="destination" class="form-control"
						id="destination" />
					<form:errors class="error create-message-error" path="destination" />
				</div>
			</div>
			<div class="form-group">
				<form:label path="departureTime" class="control-label col-md-2"
					for="departureTime">
					<spring:message code="route.departureTime" />
				</form:label>
				<div class="col-md-8">

					<div class='input-group date input-text' id='datetimepicker1'>
						<form:input path="departureTime" name="fecha"
							style="backgroud-color: white ! important" type="text"
							class="form-control" />
						 <span class="input-group-addon"> <span
							class="glyphicon glyphicon-calendar"></span>
						</span>
					</div>
					<form:errors class="error create-message-error"
						path="departureTime" />
				</div>
			</div>
			<div class="form-group">
				<form:label path="arriveTime" class="control-label col-md-2"
					for="arriveTime">
					<spring:message code="route.arriveTime" />
				</form:label>
				<div class="col-md-8">

					<div class='input-group date input-text' id='datetimepicker2'>
						<form:input path="arriveTime" name="fecha"
							style="backgroud-color: white" type="text"
							class="form-control" />
						 <span class="input-group-addon"> <span
							class="glyphicon glyphicon-calendar"></span>
						</span>
					</div>
					<form:errors class="error create-message-error" path="arriveTime" />
				</div>
			</div>
			<div class="form-group">
				<form:label path="itemEnvelope" class="control-label col-md-2"
					for="itemEnvelope">
					<spring:message code="route.itemEnvelope" />
				</form:label>
				<div class="col-md-8">

					<spring:message code="route.open" var="open" />
					<spring:message code="route.closed" var="closed" />
					<spring:message code="route.both" var="both" />

					<form:select id="itemEnvelope" class="form-control" path="itemEnvelope">
						<form:option value="" label="----" />
						<form:option value="${open }" label="${open }" />
						<form:option value="${closed }" label="${closed }" />
						<form:option value="${both }" label="${both }" />
					</form:select>
					<form:errors path="itemEnvelope" cssClass="error" />
				</div>
			</div>
			<div class="form-group">
				<form:label path="vehicle" class="control-label col-md-2"
					for="vehicle">
					<spring:message code="route.vehicle" />
				</form:label>
				<div class="col-md-8">

					<spring:message code="route.open" var="open" />
					<spring:message code="route.closed" var="closed" />
					<spring:message code="route.both" var="both" />

					<form:select id="vehicle" class="form-control" path="vehicle">
						<form:option value="" label="----" />
						<c:forEach items="${vehicles}" var="vehicle">
    						<form:option value="${vehicle.id }" label="${vehicle.brand} - ${ vehicle.model}" />
						</c:forEach>
					</form:select>
					<form:errors path="vehicle" cssClass="error" />
				</div>
			</div>
						<!-- Action buttons -->
			<div class="text-center profile-userbuttons">
			<acme:submit name="save" code="route.save" />

			<jstl:if test="${routeForm.routeId != 0}">
				<acme:submit_confirm name="delete" code="route.delete"
					codeConfirm="route.confirm.delete" />
			</jstl:if>

			<acme:cancel code="route.cancel" url="route/user/list.do" />
			</div>
		</form:form>


	</div>
</div>


<script type="text/javascript">
	$(function() {
		$('#datetimepicker1').datetimepicker({format: 'DD-MM-YYYY  HH:mm'});

	});
	$(function() {
		$('#datetimepicker2').datetimepicker({format: 'DD-MM-YYYY  HH:mm'});

	});
</script>