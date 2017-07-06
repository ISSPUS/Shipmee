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

<link rel="stylesheet" href="styles/assets/css/style-edits-offers.css"  type="text/css">
<link rel="stylesheet" href="styles/assets/css/style-form.css"  type="text/css">


<div class="blue-barra" style="padding-top: 0.75%;padding-bottom: 0.75%;">
	<div class="container">
		<div class="row">
			<h3>
			<spring:message code="routeOffer.create.for" />
			
			<a href="route/display.do?routeId=${routeOffer.route.id}">
				<jstl:out value="${routeOffer.route.origin}" />
				->
				<jstl:out value="${routeOffer.route.destination}" />
			</a>
			</h3>
		</div>
		<!-- /row -->
	</div>
</div>

<div class="container">
	<div class="row formulario">
		<form:form action="routeOffer/user/edit.do"
			modelAttribute="routeOffer" method="post" class="form-horizontal"
			role="form">
			<form:hidden path="id" />
			<form:hidden path="route" />
			<form:hidden path="user" />
			<form:hidden path="acceptedByCarrier" />
			<form:hidden path="rejectedByCarrier" />
			<form:hidden path="shipment"/>

			<div class="form-group">
				<form:label path="amount" class="control-label col-md-2"
					for="amount">
					<spring:message code="routeOffer.amount" />
				</form:label>
				<div class="col-md-8">
					<div class="inner-addon left-addon input-price">
						<i class="glyphicon glyphicon-euro"></i>
						<form:input path="amount" class="form-control" id="amount" min="0.01"
							step="0.01" type="number" required="required"/>
					</div>
					<form:errors class="error create-message-error" path="amount" />
				</div>
			</div>
			<div class="form-group">
				<form:label path="description" class="control-label col-md-2"
					for="itemPicture">
					<spring:message code="routeOffer.description" />
				</form:label>
				<div class="col-md-8">
					<form:textarea path="description" class="form-control"
						id="description" required="required"/>
					<form:errors class="error create-message-error" path="description" />
				</div>
			</div>
			<div class="form-group text-center profile-userbuttons">
				<!-- Action buttons -->
				<button type="submit" name="save" class="btn  btn-primary">
					<span class="glyphicon glyphicon-floppy-disk"></span>
					<spring:message code="routeOffer.save" />
				</button>

				<acme:cancel code="routeOffer.cancel" url="route/display.do?routeId=${routeOffer.route.id}" />

			</div>
			
		</form:form>


	</div>

</div>

