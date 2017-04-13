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
<link rel="stylesheet" href="styles/assets/css/lateral-menu.css" type="text/css">
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
			<h3>
				<spring:message code="alert.new.alert" />
			</h3>
		</div>
		<!-- /row -->
	</div>
</div>

<div class="container">
	<div class="row formulario-sm">
		<form:form action="alert/user/edit.do" method="post"
			modelAttribute="alert" class="form-horizontal" role="form">

			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="user" />
			
			<div class="form-group">
				<form:label path="origin" class="control-label col-md-2"
					for="origin"> 
					<spring:message code="alert.origin" />
				</form:label>
				<div class="col-md-8">
					<form:input path="origin" class="form-control" id="origin" />
					<form:errors class="error create-message-error" path="origin" />
				</div>
			</div>

			<div class="form-group">
				<form:label path="destination" class="control-label col-md-2"
					for="destination">
					<spring:message code="alert.destination" />
				</form:label>
				<div class="col-md-8">
					<form:input path="destination" class="form-control"
						id="destination" />
					<form:errors class="error create-message-error" path="destination" />
				</div>
			</div>
			<div class="form-group">
				<form:label path="date" class="control-label col-md-2"
					for="date">
					<spring:message code="alert.date" />
				</form:label>
				<div class="col-md-8">

					<div class='input-group date input-text' id='datetimepicker1'>
						<form:input path="date" name="fecha"
							style="backgroud-color: white ! important;width:99% ! important" type="text"
							class="form-control" />
						 <span class="input-group-addon"> <span
							class="glyphicon glyphicon-calendar"></span>
						</span>
					</div>
					<form:errors class="error create-message-error"
						path="date" />
				</div>
			</div>
			<div class="form-group">
				<form:label path="type" class="control-label col-md-2"
					for="type">
					<spring:message code="alert.type" />
				</form:label>
				<div class="col-md-8">

					<spring:message code="alert.type.route" var="route" />
					<spring:message code="alert.type.shipment" var="shipment" />
					<spring:message code="route.both" var="both" />

					<form:select id="type" class="form-control" path="type">
						<form:option value="" label="----" />
						<form:option value="Route" label="${route}" />
						<form:option value="Shipment" label="${shipment}" />
					</form:select>
					<form:errors path="type" cssClass="error" />
				</div>
			</div>
			
						<!-- Action buttons -->
			<div class="text-center profile-userbuttons">
			<button type="submit" name="save" class="btn  btn-primary">
				<span class="fa fa-plus-circle"></span>
				<spring:message code="alert.save" />
			</button>

			<jstl:if test="${alert.id != 0}">
				<acme:submit_confirm name="delete" code="alert.delete"
					codeConfirm="alert.confirm.delete" />
			</jstl:if>

			<acme:cancel code="alert.cancel" url="alert/user/list.do" />
			</div>
		</form:form>


	</div>
</div>


<script type="text/javascript">
	$(function() {
		$('#datetimepicker1').datetimepicker({format: 'DD/MM/YYYY'});

	});
</script>
