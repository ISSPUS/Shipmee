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
				<spring:message code="fundTransferPreference.edit.fundTransferPreference" />
			</h3>
		</div>
		<!-- /row -->
	</div>
</div>

<div class="container">
	<div class="row formulario-sm">
		<div class="col-xs-12 col-sm-12 col-md-8 col-lg-8"
			style="margin: 0 auto; float: none;">
			<form:form enctype="multipart/form-data"  action="fundTransferPreference/user/edit.do" modelAttribute="fundTransferPreferenceForm"
				method="post" class="form-horizontal" role="form">

				<div class="form-group">
					<form:label path="country" class="control-label col-md-2" for="country">
						<spring:message code="fundTransferPreference.country" />
					</form:label>
					<div class="col-md-10">
						<form:input path="country" class="form-control" id="country" required="true"/>
						<form:errors class="error create-message-error" path="country" />
					</div>
				</div>
				
				<div class="form-group">
					<form:label path="accountHolder" class="control-label col-md-2" for="accountHolder">
						<spring:message code="fundTransferPreference.accountHolder" />
					</form:label>
					<div class="col-md-10">
						<form:input path="accountHolder" class="form-control" id="accountHolder" required="true"/>
						<form:errors class="error create-message-error" path="accountHolder" />
					</div>
				</div>
				
				<div class="form-group">
					<form:label path="bankName" class="control-label col-md-2" for="bankName">
						<spring:message code="fundTransferPreference.bankName" />
					</form:label>
					<div class="col-md-10">
						<form:input path="bankName" class="form-control" id="bankName" required="true"/>
						<form:errors class="error create-message-error" path="bankName" />
					</div>
				</div>
				
				<div class="form-group">
					<form:label path="IBAN" class="control-label col-md-2" for="IBAN">
						<spring:message code="fundTransferPreference.IBAN" />
					</form:label>
					<div class="col-md-10">
						<form:input path="IBAN" class="form-control" id="IBAN" required="true"/>
						<form:errors class="error create-message-error" path="IBAN" />
					</div>
				</div>
				
				<div class="form-group">
					<form:label path="BIC" class="control-label col-md-2" for="BIC">
						<spring:message code="fundTransferPreference.BIC" />
					</form:label>
					<div class="col-md-10">
						<form:input path="BIC" class="form-control" id="BIC" required="true"/>
						<form:errors class="error create-message-error" path="BIC" />
					</div>
				</div>

				
				<div class="form-group text-center profile-userbuttons">
					<button type="submit" name="save" class="btn  btn-primary">
						<span class="glyphicon glyphicon-floppy-disk"></span>
						<spring:message code="fundTransferPreference.save" />
					</button>


					<acme:cancel code="fundTransferPreference.cancel" url="vehicle/user/list.do" />
				</div>
				<!-- Action buttons -->


			</form:form>
		</div>

	</div>
</div>