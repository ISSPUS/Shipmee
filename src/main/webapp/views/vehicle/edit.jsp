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
<script async type="text/javascript" src="scripts/moment.min.js"></script>
<script async type="text/javascript" src="scripts/datetimepicker.min.js"></script>
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
				<spring:message code="vehicle.edit.vehicle" />
			</h3>
		</div>
		<!-- /row -->
	</div>
</div>

<div class="container">
	<div class="row formulario-sm">
		<div class="col-xs-12 col-sm-12 col-md-8 col-lg-8"
			style="margin: 0 auto; float: none;">
			
			<form:form enctype="multipart/form-data"  action="vehicle/user/edit.do" modelAttribute="vehicleForm"
				method="post" class="form-horizontal" role="form">

				<form:hidden path="vehicleId" />
				
				<div class="text-center modal-content" style="padding:1%; border-color:#f1f3fa;">
					<div>
						<span title="<spring:message code="user.required"/>" class="glyphicon glyphicon-record" style="color:#d9534f;"></span>
						<span><spring:message code="user.required.info"/></span>
					</div>
				</div> <br/>

				<div class="form-group">
					<form:label path="brand" class="control-label col-md-2" for="brand">
						<spring:message code="vehicle.brand" /> <span title="<spring:message code="user.required" />" class="glyphicon glyphicon-record" style="color:#d9534f;"></span>
					</form:label>
					<div class="col-md-10">
						<form:input path="brand" class="form-control" id="brand" required="true"/>
						<form:errors class="error create-message-error" path="brand" />
					</div>
				</div>

				<div class="form-group">
					<form:label path="model" class="control-label col-md-2" for="model">
						<spring:message code="vehicle.model" /> <span title="<spring:message code="user.required" />" class="glyphicon glyphicon-record" style="color:#d9534f;"></span>
					</form:label>
					<div class="col-md-10">
						<form:input path="model" class="form-control" id="model" required="true"/>
						<form:errors class="error create-message-error" path="model" />
					</div>
				</div>

				<div class="form-group">
					<form:label path="color" class="control-label col-md-2" for="color">
						<spring:message code="vehicle.color" /> <span title="<spring:message code="user.required" />" class="glyphicon glyphicon-record" style="color:#d9534f;"></span>
					</form:label>
					<div class="col-md-10">
						<form:input path="color" class="form-control" id="color" required="true"/>
						<form:errors class="error create-message-error" path="color" />
					</div>
				</div>

				<div class="form-group">
					<form:label path="picture" class="control-label col-md-2"
						for="picture">
						<spring:message code="vehicle.picture" /> 
						<jstl:if test="${vehicleForm.vehicleId == 0}">
							<span title="<spring:message code="user.required" />" class="glyphicon glyphicon-record" style="color:#d9534f;"></span>
						</jstl:if>
					</form:label>
					<div class="col-md-10">
						<form:input path="picture" type="file" accept=".jpg,.jpeg,.png" class="form-control" id="picture" required="${vehicleForm.vehicleId == 0 ? 'required' : ''}"/>
						<form:errors class="error create-message-error" path="picture" />
					</div>
				</div>
				
				<div class="form-group text-center profile-userbuttons">
					<button type="submit" name="save" class="btn  btn-primary">
						<span class="glyphicon glyphicon-floppy-disk"></span>
						<spring:message code="vehicle.save" />

					</button>

					<jstl:if test="${vehicleForm.vehicleId != 0}">
						<acme:submit_confirm name="delete" code="vehicle.delete"
							codeConfirm="vehicle.confirm.delete" />
					</jstl:if>

					<acme:cancel code="vehicle.cancel" url="vehicle/user/list.do" />
				</div>
				<!-- Action buttons -->


			</form:form>
		</div>

	</div>
</div>

<script type="text/javascript">
	var inputImage = document.getElementById('picture');
	inputImage.onchange = function(e){ 
		if (document.contains(document.getElementById("picture.errors"))) {
            document.getElementById("picture.errors").remove();
		}
		extension = this.value.split(".");
	    var nameFile = extension[extension.length-1];
	    switch(nameFile)
	    {
	        case 'jpg':
	        case 'jpeg':
	        case 'png':
	        	break;
	        default:
	        	var mssg ='<spring:message code="message.error.imageUpload.incompatibleType" />';
	        	inputImage.insertAdjacentHTML('afterend', '<span id="picture.errors" class="error">'+mssg+'</span>');
	            this.value='';
	    }
	};
</script>
