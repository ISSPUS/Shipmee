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
<link rel="stylesheet" href="styles/assets/css/style-form.css"  type="text/css">

<style>
.date .dropdown-menu {
	background-color: white ! important;
}

.formulario {
	padding: 10%;
}
</style>


<div class="container">
	<div class="row formulario-sm">
		<div class="col-xs-12 col-sm-12 col-md-8 col-lg-8"
			style="margin: 0 auto; float: none;">
			<form:form enctype="multipart/form-data"  action="feepayment/user/create.do" modelAttribute="feePaymentForm"
				method="post" class="form-horizontal" role="form">

				<form:hidden path="id" />
				<form:hidden path="sizePriceId" />
				<form:hidden path="description" />
				<form:hidden path="amount" />
				<form:hidden path="offerId" />
				<form:hidden path="type" />

				<div class="form-group">
					<form:label path="creditCard.holderName" class="control-label col-md-2" for="holderName">
						<spring:message code="feePayment.holderName" />
					</form:label>
					<div class="col-md-10">
						<form:input path="creditCard.holderName" class="form-control" id="holderName" required="true"/>
						<form:errors class="error create-message-error" path="creditCard.holderName" />
					</div>
				</div>
				
				<div class="form-group">
					<form:label path="creditCard.brandName" class="control-label col-md-2" for="brandName">
						<spring:message code="feePayment.brandName" />
					</form:label>
					<div class="col-md-10">
						<form:input path="creditCard.brandName" class="form-control" id="brandName" required="true"/>
						<form:errors class="error create-message-error" path="creditCard.brandName" />
					</div>
				</div>
				
				<div class="form-group">
					<form:label path="creditCard.number" class="control-label col-md-2" for="number">
						<spring:message code="feePayment.number" />
					</form:label>
					<div class="col-md-10">
						<form:input path="creditCard.number" class="form-control" id="number" required="true"/>
						<form:errors class="error create-message-error" path="creditCard.number" />
					</div>
				</div>
				
				<div class="form-group">
					<form:label path="creditCard.expirationMonth" class="control-label col-md-2" for="expirationMonth">
						<spring:message code="feePayment.expirationMonth" />
					</form:label>
					<div class="col-md-10">
						<form:input path="creditCard.expirationMonth" class="form-control" id="expirationMonth" required="true"/>
						<form:errors class="error create-message-error" path="creditCard.expirationMonth" />
					</div>
				</div>
				
				<div class="form-group">
					<form:label path="creditCard.expirationYear" class="control-label col-md-2" for="expirationYear">
						<spring:message code="feePayment.expirationYear" />
					</form:label>
					<div class="col-md-10">
						<form:input path="creditCard.expirationYear" class="form-control" id="expirationYear" required="true"/>
						<form:errors class="error create-message-error" path="creditCard.expirationYear" />
					</div>
				</div>
				
				<div class="form-group">
					<form:label path="creditCard.cvvCode" class="control-label col-md-2" for="cvvCode">
						<spring:message code="feePayment.cvvCode" />
					</form:label>
					<div class="col-md-10">
						<form:input path="creditCard.cvvCode" class="form-control" id="cvvCode" required="true"/>
						<form:errors class="error create-message-error" path="creditCard.cvvCode" />
					</div>
				</div>
				
				<div class="form-group text-center profile-userbuttons">
					<button type="submit" name="save" class="btn  btn-primary">
						<span class="glyphicon glyphicon-floppy-disk"></span>
						<spring:message code="feePayment.save" />

					</button>

					<acme:cancel code="feePayment.cancel" url="vehicle/user/list.do" />
				</div>
				<!-- Action buttons -->


			</form:form>
		</div>

	</div>
</div>