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

<div class="blue-barra">
	<div class="container">
		<div class="row">
			<h3>
				<spring:message code="feePayment.feePayments" />
			</h3>
		</div>
		<!-- /row -->
	</div>
</div>
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
					<form:label path="creditCard.brandName"
						class="control-label col-md-2" for="brandName">
						<spring:message code="feePayment.brandName" />
					
					</form:label>
					<div class="col-md-10">
						<form:select id="brandName" class="form-control"
							path="creditCard.brandName">
							<form:option value="" label="----" />
							<form:option value="American Express">American Express</form:option>
							<form:option value="Diners Club">Diners Club</form:option>
							<form:option value="Discover">Discover</form:option>
							<form:option value="enRoute">enRoute</form:option>
							<form:option value="JCB">JCB</form:option>
							<form:option value="MasterCard">MasterCard</form:option>
							<form:option value="Visa">Visa</form:option>
							<form:option value="Voyager">Voyager</form:option>
						</form:select>
						<form:errors class="error create-message-error"
							path="creditCard.brandName" />

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
						<form:input path="creditCard.expirationMonth" class="form-control" id="expirationMonth" min="1" step="1" max="12" type="number" required="true"/>
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
						<spring:message code="feePayment.cvvCode" /><a href="#aboutModal" data-toggle="modal" data-target="#myModal2">
							<span class="glyphicon glyphicon-info-sign"></span>
						</a>
					</form:label>
					<div class="col-md-10">
						<form:input path="creditCard.cvvCode" class="form-control" id="cvvCode" required="true" maxlength="3"/>
						<form:errors class="error create-message-error" path="creditCard.cvvCode" />
					</div>
					
					<!-- Modal -->
					<div class="modal fade" id="myModal2" tabindex="-1" role="dialog"
						aria-labelledby="myModalLabel2" aria-hidden="true">
						<div class="modal-dialog">

							<div class="modal-content text-center" style="padding: 3%;">
								<img src="http://www.creditcards.com/credit-card-news/images/cc-security-codes-back-2.png" />
								<a href="https://www.teamline.cc/static/html/csv.html" target="_blank">Info</a>
							</div>
						</div>

					</div>
					
				</div>
				
				<div class="form-group text-center profile-userbuttons">
					<button type="submit" name="save" class="btn  btn-primary">
						<span class="glyphicon glyphicon-floppy-disk"></span>
						<spring:message code="feePayment.save" />

					</button>

					<acme:cancel code="feePayment.cancel" url="vehicle/user/list.do" />
					<a href="#aboutModal" data-toggle="modal" data-target="#myModal">
				<span class="glyphicon glyphicon-info-sign"></span> <spring:message code="user.help" /></a>


			<!-- Modal -->
			<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
				
					<div class="modal-content text-center" style="padding: 3%;">
								<img src="http://blog.unibulmerchantservices.com/wp-content/uploads/2011/06/How-to-Authenticate-Credit-Cards-in-Face-to-Face-Transactions.png" />
							</div>
				</div>

			</div>
				</div>
				<!-- Action buttons -->


			</form:form>
		</div>

	</div>
</div>