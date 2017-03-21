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
<div class="container">
	<div class="row formulario-size">
		<div class="alert alert-info">
			<strong><spring:message code="sizePrice.info" /> </strong>
		</div>
		<form:form action="sizePrice/user/edit.do"
			modelAttribute="sizePriceForm" class="form-horizontal">
			<!-- Hidden Attributes -->
			<form:hidden path="routeId" />

			<div class="form-group">
				<form:label path="priceS"
					class="control-label col-md-4 label-formulario-tam" for="priceS">
					<i class="demo-icon icon-package-1 ">&#xE804;</i>
					<spring:message code="sizePrice.priceS" />
				</form:label>
				<div class="col-md-6">
					<div class="inner-addon left-addon input-price">
						<i class="glyphicon glyphicon-euro"></i>
						<form:input path="priceS" class="form-control" id="priceS" min="0"
							step="0.10" type="number" />
					</div>
					<form:errors class="error create-message-error" path="priceS" />
				</div>
			</div>

			<div class="form-group">
				<div class="col-md-12">
					<form:label path="priceM"
						class="control-label col-md-4 label-formulario-tam" for="priceM">
						<i class="demo-icon icon-package-1">&#xE803;</i>
						<spring:message code="sizePrice.priceM" />
					</form:label>
					<div class="col-md-6">
						<div class="inner-addon left-addon input-price">

							<i class="glyphicon glyphicon-euro"></i>
							<form:input path="priceM" class="form-control" min="0"
								step="0.10" type="number" id="priceM" />
						</div>
						<form:errors class="error create-message-error" path="priceM" />
					</div>
				</div>
			</div>
			<div class="form-group">
				<form:label path="priceL"
					class="control-label col-md-4 label-formulario-tam" for="priceL">
					<i class="demo-icon icon-package-1">&#xe802;</i>
					<spring:message code="sizePrice.priceL" />
				</form:label>
				<div class="col-md-6">
					<div class="inner-addon left-addon input-price">
						<i class="glyphicon glyphicon-euro"></i>
						<form:input path="priceL" class="form-control" id="priceL" min="0"
							step="0.10" type="number" />
					</div>
					<form:errors class="error create-message-error" path="priceL" />
				</div>
			</div>
			<div class="form-group">
				<form:label path="priceXL"
					class="control-label col-md-4 label-formulario-tam" for="priceXL">
					<i class="demo-icon icon-package-1">&#xE805;</i>
					<spring:message code="sizePrice.priceXL" />
				</form:label>
				<div class="col-md-6">

					<div class="inner-addon left-addon input-price">

						<i class="glyphicon glyphicon-euro"></i>
						<form:input path="priceXL" class="form-control" id="priceXL"
							min="0" step="0.10" type="number" />
					</div>

					<form:errors class="error create-message-error" path="priceXL" />
				</div>
			</div>

			<!-- Action buttons -->
			<acme:submit name="save" code="sizePrice.save" />

			<jstl:if test="${sizePriceForm.sizePriceFormId != 0}">
				<button type="submit" name="delete" class="btn btn-danger"
					onclick="return confirm('<spring:message code="sizePrice.confirm.delete" />')">
					<spring:message code="sizePrice.delete" />
				</button>
			</jstl:if>
			<acme:cancel code="sizePrice.cancel" url="route/user/list.do" />

		</form:form>

	</div>
</div>

