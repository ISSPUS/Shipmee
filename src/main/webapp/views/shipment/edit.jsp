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

<style>
.date .dropdown-menu {
	background-color: white ! important;
}

.formulario {
	padding: 10%;
}
/* enable absolute positioning */
.inner-addon {
	position: relative;
}

/* style glyph */
.inner-addon .glyphicon {
	position: absolute;
	padding: 10px;
	pointer-events: none;
}

/* align glyph */
.left-addon .glyphicon {
	left: 0px;
}

.right-addon .glyphicon {
	right: 0px;
}

/* add padding  */
.left-addon input {
	padding-left: 30px;
}

.right-addon input {
	padding-right: 30px;
}
</style>
<div class="container">
	<div class="row formulario">
		<form:form action="shipment/user/edit.do"
			modelAttribute="shipmentForm" method="post" class="form-horizontal"
			role="form">

			<form:hidden path="shipmentId" />

			<div class="form-group">
				<form:label path="origin" class="control-label col-md-2"
					for="recipient">
					<spring:message code="shipment.origin" />
				</form:label>
				<div class="col-md-8">
					<form:input path="origin" class="form-control" id="origin" />
					<form:errors class="error create-message-error" path="origin" />
				</div>
			</div>

			<div class="form-group">
				<form:label path="origin" class="control-label col-md-2"
					for="destination">
					<spring:message code="shipment.destination" />
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
					<spring:message code="shipment.departureTime" />
				</form:label>
				<div class="col-md-8">

					<div class='input-group date input-text' id='datetimepicker1'>
						<form:input id="date" path="departureTime" name="departureTime"
							type="text" class="form-control" />
						<span class="input-group-addon"> <span
							class="glyphicon glyphicon-calendar"></span>
						</span>
					</div>
					<form:errors class="error create-message-error"
						path="departureTime" />
				</div>
			</div>
			<div class="form-group">
				<form:label path="maximumArriveTime" class="control-label col-md-2"
					for="maximumArriveTime">
					<spring:message code="shipment.maximumArriveTime" />
				</form:label>
				<div class="col-md-8">

					<div class='input-group date input-text' id='datetimepicker2'>
						<form:input path="maximumArriveTime" name="maximumArriveTime"
							type="text" class="form-control" />
						<span class="input-group-addon"> <span
							class="glyphicon glyphicon-calendar"></span>
						</span>
					</div>
					<form:errors class="error create-message-error"
						path="maximumArriveTime" />
				</div>
			</div>
			<div class="form-group">
				<form:label path="itemSize" class="control-label col-md-2"
					for="itemSize">
					<spring:message code="shipment.itemSize" />
				</form:label>
				<div class="col-md-8">

					<spring:message code="shipment.sizeS" var="s" />
					<spring:message code="shipment.sizeM" var="m" />
					<spring:message code="shipment.sizeL" var="l" />
					<spring:message code="shipment.sizeXL" var="xl" />

					<form:select id="shipment" class="form-control" path="ItemSize">
						<form:option value="" label="----" />
						<form:option value="S" label="${s }" />
						<form:option value="M" label="${m }" />
						<form:option value="L" label="${l }" />
						<form:option value="XL" label="${xl }" />
					</form:select>
					<form:errors path="itemSize" cssClass="error" />
				</div>
			</div>
			<div class="form-group">
				<form:label path="price" class="control-label col-md-2" for="price">
					<spring:message code="shipment.price" />
				</form:label>
				<div class="col-md-8">
					<div class="inner-addon left-addon input-price">
						<i class="glyphicon glyphicon-euro"></i>
						<form:input path="price" class="form-control" id="price" min="0"
							step="0.10" type="number" />
					</div>
					<form:errors class="error create-message-error" path="price" />
				</div>
			</div>
			<div class="form-group">
				<form:label path="itemName" class="control-label col-md-2"
					for="itemName">
					<spring:message code="shipment.itemName" />
				</form:label>
				<div class="col-md-8">
					<form:input path="itemName" class="form-control" id="itemName" />
					<form:errors class="error create-message-error" path="itemName" />
				</div>
			</div>
			<div class="form-group">
				<form:label path="itemPicture" class="control-label col-md-2"
					for="itemPicture">
					<spring:message code="shipment.itemPicture" />
				</form:label>
				<div class="col-md-8">
					<form:input path="itemPicture" class="form-control"
						id="itemPicture" />
					<form:errors class="error create-message-error" path="itemPicture" />
				</div>
			</div>
			<div class="form-group">
				<form:label path="itemEnvelope" class="control-label col-md-2"
					for="itemEnvelope">
					<spring:message code="shipment.itemEnvelope" />
				</form:label>
				<div class="col-md-8">

					<spring:message code="shipment.open" var="open" />
					<spring:message code="shipment.closed" var="closed" />
					<spring:message code="shipment.both" var="both" />
					<form:select id="shipment" class="form-control" path="ItemEnvelope">
						<form:option value="" label="----" />
						<form:option value="${open }" label="${open }" />
						<form:option value="${closed }" label="${closed }" />
						<form:option value="${both }" label="${both }" />
					</form:select>
					<form:errors path="itemEnvelope" cssClass="error" />
				</div>
			</div>
			<!-- Action buttons -->
			<acme:submit name="save" code="shipment.save" />

			<jstl:if test="${shipmentForm.shipmentId != 0}">
				<button type="submit" name="delete" class="btn btn-danger"
					onclick="return confirm('<spring:message code="shipment.confirm.delete" />')">
					<spring:message code="shipment.delete" />
				</button>
			</jstl:if>
			<acme:cancel code="shipment.cancel" url="shipment/user/list.do" />

		</form:form>


	</div>
</div>


<script type="text/javascript">
	$(function() {
		$('#datetimepicker1').datetimepicker({
			format : 'DD-MM-YYYY  HH:mm'
		});

	});
	$(function() {
		$('#datetimepicker2').datetimepicker({
			format : 'DD-MM-YYYY  HH:mm'
		});

	});
</script>