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
<link rel="stylesheet" href="styles/assets/css/style-form.css"  type="text/css">

<style>
.formulario-precios {
	padding: 10%;
}

.input-price {
	width: 70%;
	margin: auto;
}
</style>

<style>
@font-face {
	font-family: 'icons';
	src: url('styles/assets/fonts/iconos/iconos.eot?58322891');
	src: url('styles/assets/fonts/iconos/iconos.eot?58322891#iefix')
		format('embedded-opentype'),
		url('styles/assets/fonts/iconos/iconos.woff?58322891') format('woff'),
		url('styles/assets/fonts/iconos/iconos.ttf?58322891')
		format('truetype'),
		url('styles/assets/fonts/iconos/iconos.svg?58322891#fontello')
		format('svg');
	font-weight: normal;
	font-style: normal;
}

.demo-icon {
	font-family: "icons";
	font-style: normal;
	font-weight: normal;
	font-variant: normal;
	text-transform: none;
	font-size: 150%;
}

.size-icon {
	font-family: "package-open";
	font-style: normal;
	font-weight: normal;
	font-variant: normal;
	text-transform: none;
	font-size: 120%;
}

.inner-addon {
	position: relative;
}

.inner-addon .glyphicon {
	position: absolute;
	padding: 10px;
	pointer-events: none;
}

.left-addon .glyphicon {
	left: 0px;
}

.left-addon .glyphicon {
	right: 0px;
}

.left-addon input {
	padding-left: 30px;
}

.left-addon input {
	padding-right: 30px;
}

.formulario-size {
	padding: 10%;
}

.label-formulario-tam {
	text-align: center ! important;
}
</style>


<!-- Form -->
<jstl:if test="${user.isVerified && user.fundTransferPreference != null}">

<div class="container">
	<div class="row formulario-sm">
		<div class="alert alert-info">
			<strong><spring:message code="sizePrice.info" /> </strong>
		</div>
		<form:form action="sizePrice/user/edit.do"
			modelAttribute="sizePriceForm" class="form-horizontal">
			<!-- Hidden Attributes -->
			<form:hidden path="routeId" />
			<form:hidden path="departureTime" />
			<form:hidden path="arriveTime" />
			<form:hidden path="origin" />
			<form:hidden path="destination" />
			<form:hidden path="itemEnvelope" />
			<form:hidden path="vehicle" />

			<div class="form-group">
				<form:label path="priceS"
					class="control-label col-md-4 label-formulario-tam" for="priceS">
					<i class="demo-icon icon-package-1 ">&#xE804;</i>
					<spring:message code="sizePrice.priceS" /><br/>
					<spring:message code="shipment.sizeS" />
				</form:label>
				<div class="col-md-6">
					<div class="inner-addon left-addon input-price">
						<i class="glyphicon glyphicon-euro"></i>
						<form:input path="priceS" class="form-control" id="priceS" min="0.01"
							step="0.01" type="number" />
					</div>
					<form:errors class="error create-message-error" path="priceS" />
				</div>
			</div>

			<div class="form-group">
				<div class="col-md-12">
					<form:label path="priceM"
						class="control-label col-md-4 label-formulario-tam" for="priceM">
						<i class="demo-icon icon-package-1">&#xE803;</i>
						<spring:message code="sizePrice.priceM" /><br/>
						<spring:message code="shipment.sizeM" />
					</form:label>
					<div class="col-md-6">
						<div class="inner-addon left-addon input-price">

							<i class="glyphicon glyphicon-euro"></i>
							<form:input path="priceM" class="form-control" min="0.01"
								step="0.01" type="number" id="priceM" />
						</div>
						<form:errors class="error create-message-error" path="priceM" />
					</div>
				</div>
			</div>
			<div class="form-group">
				<form:label path="priceL"
					class="control-label col-md-4 label-formulario-tam" for="priceL">
					<i class="demo-icon icon-package-1">&#xe802;</i>
					<spring:message code="sizePrice.priceL" /><br/>
					<spring:message code="shipment.sizeL" />
				</form:label>
				<div class="col-md-6">
					<div class="inner-addon left-addon input-price">
						<i class="glyphicon glyphicon-euro"></i>
						<form:input path="priceL" class="form-control" id="priceL" min="0.01"
							step="0.01" type="number" />
					</div>
					<form:errors class="error create-message-error" path="priceL" />
				</div>
			</div>
			<div class="form-group">
				<form:label path="priceXL"
					class="control-label col-md-4 label-formulario-tam" for="priceXL">
					<i class="demo-icon icon-package-1">&#xE805;</i>
					<spring:message code="sizePrice.priceXL" /><br/>
					<spring:message code="shipment.sizeXL" />
				</form:label>
				<div class="col-md-6">

					<div class="inner-addon left-addon input-price">

						<i class="glyphicon glyphicon-euro"></i>
						<form:input path="priceXL" class="form-control" id="priceXL"
							min="0.01" step="0.01" type="number" />
					</div>

					<form:errors class="error create-message-error" path="priceXL" />
				</div>
			</div>

			<!-- Action buttons -->
			<div class="text-center profile-userbuttons">
			 	<div><a><spring:message code="master.page.comissions.must" /></a></div>
				<br />
				
				<button name="save" class="btn button-cancel" onclick="goBack()">
					<span class="glyphicon glyphicon-arrow-left"></span>
					<spring:message code="route.edit.previous" />
				</button>
			
				<button type="submit" name="save" class="btn  btn-primary">
					<span class="glyphicon glyphicon-floppy-disk"></span>
					<spring:message code="sizePrice.save" />
				</button>

				<acme:cancel code="sizePrice.cancel" url="route/user/list.do" />
			</div>
		</form:form>

	</div>
</div>
</jstl:if>

<jstl:if test="${(!user.isVerified && (user.phone == '' || user.dni == '' || user.photo == '' || user.dniPhoto == '')) ||
(!user.isVerified && (user.phone != '' && user.dni != '' && user.photo != '' && user.dniPhoto != '')) ||
(user.fundTransferPreference == null)}">

	<div class="" style="margin-top:25px">

	</div>

</jstl:if>


<jstl:if test="${!user.isVerified && (user.phone == '' || user.dni == '' || user.photo == '' || user.dniPhoto == '')}">

	<div class="container">
		<div class="alert alert-info">
			<strong><spring:message code="user.isVerified" />: <a href="user/user/edit.do" ><spring:message code="user.verify" /></a></strong>
		</div>
	</div>

</jstl:if>
<jstl:if test="${!user.isVerified && (user.phone != '' && user.dni != '' && user.photo != '' && user.dniPhoto != '')}">

	<div class="container" >
		<div class="alert alert-success">
			<strong><spring:message code="user.notVerified.waiting" /></strong>
		</div>
	</div>

</jstl:if>

<jstl:if test="${user.fundTransferPreference == null}">

	<div class="container">
		<div class="alert alert-info">
			<strong><spring:message code="message.error.shipmentOffer.fundTransferPreference" /> <spring:message code="message.error.shipmentOffer.fundTransferPreference.can" />: <a href="fundTransferPreference/user/edit.do" ><spring:message code="message.error.shipmentOffer.fundTransferPreference.edit" /></a></strong>
		</div>
	</div>

	<br/>
</jstl:if>
<script>
function goBack() {
    window.history.back();
}
</script>
