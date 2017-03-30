<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<link rel="stylesheet" href="styles/assets/css/style-edits-offers.css"  type="text/css">
<link rel="stylesheet" href="styles/assets/css/style-form.css"  type="text/css">

<div class="blue-barra" style="padding-top: 0.75%;padding-bottom: 0.75%;">
	<div class="container">
		<div class="row">
			<h3>
			<spring:message code="shipmentOffer.create.for" />
			<a
				href="shipment/display.do?shipmentId=${shipmentOffer.shipment.id}">
				<jstl:out value="${shipmentOffer.shipment.itemName}" />
			</a>
			</h3>
		</div>
		<!-- /row -->
	</div>
</div>
<div class="container">
	<div class="row formulario">
		<form:form action="shipmentOffer/user/edit.do" modelAttribute="shipmentOffer" method="post" class="form-horizontal"
			role="form">

			<!-- Hidden Attributes -->
		<form:hidden path="id" />
		<form:hidden path="shipment" />
		<form:hidden path="user" />
		<form:hidden path="acceptedBySender" />
		<form:hidden path="rejectedBySender" />

			<div class="form-group">
				<form:label path="amount" class="control-label col-md-2"
					for="amount">
					<spring:message code="shipmentOffer.amount" />
				</form:label>
				<div class="col-md-8">
					<div class="inner-addon left-addon input-price">
						<i class="glyphicon glyphicon-euro"></i>
						<form:input path="amount" class="form-control" id="amount" min="0"
							step="0.10" type="number" />
					</div>
					<form:errors class="error create-message-error" path="amount" />
				</div>
			</div>
			<div class="form-group">
				<form:label path="description" class="control-label col-md-2"
					for="itemPicture">
					<spring:message code="shipmentOffer.description" />
				</form:label>
				<div class="col-md-8">
					<form:textarea path="description" class="form-control"
						id="description" />
					<form:errors class="error create-message-error" path="description" />
				</div>
			</div>
			<div class="form-group text-center profile-userbuttons"">
				<!-- Action buttons -->
				<acme:submit name="save" code="shipmentOffer.save"/>
				
				<acme:cancel code="shipmentOffer.cancel" url="shipment/display.do?shipmentId=${shipmentOffer.shipment.id}"/>
			</div>

		</form:form>


	</div>

</div>