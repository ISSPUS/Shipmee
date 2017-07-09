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
<script type="text/javascript" src="scripts/moment.min.js"></script>
<script type="text/javascript" src="scripts/datetimepicker.min.js"></script>
<link rel="stylesheet" href="styles/assets/css/lateral-menu.css" type="text/css">
<link rel="stylesheet" href="styles/assets/css/style-form.css"  type="text/css">
<script type="text/javascript" src="scripts/es.min.js"></script>

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


<div class="blue-barra">
	<div class="container">
			<div class="row">
				<h3><spring:message code="shipment.new.shipment" /></h3>
			</div><!-- /row -->
	</div>
</div>

<div class="container">
	<div class="row formulario-sm">
		<form:form action="shipment/user/edit.do"
			modelAttribute="shipmentForm" method="post" class="form-horizontal"
			role="form" enctype="multipart/form-data">

			<form:hidden path="shipmentId" />
			
			<div class="text-center modal-content" style="padding:1%; border-color:#f1f3fa;">
				<div>
					<span title="<spring:message code="user.required"/>" class="glyphicon glyphicon-record" style="color:#d9534f;"></span>
					<span><spring:message code="user.required.info"/></span>
				</div>
			</div> <br/>

			<div class="form-group">
				<form:label path="origin" class="control-label col-md-3"
					for="recipient">
					<spring:message code="shipment.origin" /> <span title="<spring:message code="user.required" />" class="glyphicon glyphicon-record" style="color:#d9534f;"></span>
				</form:label>
				<div class="col-md-7">
					<form:input path="origin" class="form-control" id="origin" required="required"/>
					<form:errors class="error create-message-error" path="origin" />
				</div>
			</div>

			<div class="form-group">
				<form:label path="origin" class="control-label col-md-3"
					for="destination">
					<spring:message code="shipment.destination" /> <span title="<spring:message code="user.required" />" class="glyphicon glyphicon-record" style="color:#d9534f;"></span>
				</form:label>
				<div class="col-md-7">
					<form:input path="destination" class="form-control"
						id="destination" required="required"/>
					<form:errors class="error create-message-error" path="destination" />
				</div>
			</div>
			<div class="form-group">
				<form:label path="departureTime" class="control-label col-md-3"
					for="departureTime">
					<spring:message code="shipment.departureTime" /> <span title="<spring:message code="user.required" />" class="glyphicon glyphicon-record" style="color:#d9534f;"></span>
				</form:label>
				<div class="col-md-7">

					<div class='input-group date input-text' id='datetimepicker1'>
						<form:input id="date" path="departureTime" name="departureTime"
							type="text" class="form-control" required="required"/>
						<span class="input-group-addon"> <span
							class="glyphicon glyphicon-calendar"></span>
						</span>
					</div>
					<form:errors class="error create-message-error"
						path="departureTime" />
				</div>
			</div>
			<div class="form-group">
				<form:label path="maximumArriveTime" class="control-label col-md-3"
					for="maximumArriveTime">
					<spring:message code="shipment.maximumArriveTime" /> <span title="<spring:message code="user.required" />" class="glyphicon glyphicon-record" style="color:#d9534f;"></span>
				</form:label>
				<div class="col-md-7">

					<div class='input-group date input-text' id='datetimepicker2'>
						<form:input path="maximumArriveTime"  name="maximumArriveTime"
							type="text" class="form-control" required="required"/>
						<span class="input-group-addon"> <span
							class="glyphicon glyphicon-calendar"></span>
						</span>
					</div>
					<form:errors class="error create-message-error"
						path="maximumArriveTime" />
				</div>
			</div>
			<div class="form-group">
				<form:label path="itemSize" class="control-label col-md-3"
					for="itemSize">
					<spring:message code="shipment.itemSize" /> <span title="<spring:message code="user.required" />" class="glyphicon glyphicon-record" style="color:#d9534f;"></span>
				</form:label>
				<div class="col-md-7">

					<spring:message code="shipment.sizeS" var="s" />
					<spring:message code="shipment.sizeM" var="m" />
					<spring:message code="shipment.sizeL" var="l" />
					<spring:message code="shipment.sizeXL" var="xl" />

					<form:select id="shipment" class="form-control" path="ItemSize" required="required">
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
				<form:label path="price" class="control-label col-md-3" for="price">
					<spring:message code="shipment.price" /> <span title="<spring:message code="user.required" />" class="glyphicon glyphicon-record" style="color:#d9534f;"></span>
				</form:label>
				<div class="col-md-3">
					<div class="inner-addon left-addon input-price">
						<i class="glyphicon glyphicon-euro"></i>
						<form:input path="price" class="form-control" id="price" min="0.01"
							step="0.01" type="number" required="required"/>
					</div>
				</div>
				<div class="col-md-6">
					<div><a><spring:message code="master.page.comission.must" /></a></div>
				</div>
			</div>
			<div class="form-group">
				<form:label path="itemName" class="control-label col-md-3"
					for="itemName">
					<spring:message code="shipment.itemName" /> <span title="<spring:message code="user.required" />" class="glyphicon glyphicon-record" style="color:#d9534f;"></span>
				</form:label>
				<div class="col-md-7">
					<form:input path="itemName" class="form-control" id="itemName" required="required"/>
					<form:errors class="error create-message-error" path="itemName" />
				</div>
			</div>
			<div class="form-group">
				<form:label path="imagen" class="control-label col-md-3"
					for="imagen">
					<spring:message code="shipment.itemPicture" /> 
					<jstl:if test="${shipmentForm.shipmentId == 0}">
						<span title="<spring:message code="user.required" />" class="glyphicon glyphicon-record" style="color:#d9534f;"></span>
					</jstl:if>
				</form:label>
				<div class="col-md-7">
					<form:input  type="file" path="imagen" accept=".jpg,.jpeg,.png" class="form-control btn btn-default btn-file"
						id="itemPicture" required="${shipmentForm.shipmentId == 0 ? 'required' : ''}"/>
					<form:errors class="error create-message-error" path="imagen" />
				</div>
			</div>
			<div class="form-group">
				<form:label path="itemEnvelope" class="control-label col-md-3"
					for="itemEnvelope">
					<spring:message code="shipment.itemEnvelope" /> <span title="<spring:message code="user.required" />" class="glyphicon glyphicon-record" style="color:#d9534f;"></span>
				</form:label>
				<div class="col-md-7">

					<spring:message code="shipment.open" var="open" />
					<spring:message code="shipment.closed" var="closed" />
					<spring:message code="shipment.both" var="both" />
					<form:select id="shipment" class="form-control" path="ItemEnvelope" required="required">
						<form:option value="" label="----" />
						<form:option value="Open" label="${open }" />
						<form:option value="Closed" label="${closed }" />
						<form:option value="Both" label="${both }" />
					</form:select>
					<form:errors path="itemEnvelope" cssClass="error" />
				</div>
			</div>
			<!-- Action buttons -->
			
			<div class="text-center profile-userbuttons">
			<button type="submit" name="save" class="btn  btn-primary">
				<span class="glyphicon glyphicon-floppy-disk"></span>
				<spring:message code="shipment.save" />
			</button>

			<jstl:if test="${shipmentForm.shipmentId != 0}">
				<button type="submit" name="delete" class="btn btn-danger"
					onclick="return confirm('<spring:message code="shipment.confirm.delete" />')">
					<spring:message code="shipment.delete" />
				</button>
			</jstl:if>
			<jstl:if test="${shipmentForm.shipmentId == 0}">
				<acme:cancel code="shipment.cancel" url="shipment/user/list.do" />
			</jstl:if>
			<jstl:if test="${shipmentForm.shipmentId != 0}">
				<acme:cancel code="shipment.cancel" url="shipment/display.do?shipmentId=${shipmentForm.shipmentId}" />
			</jstl:if>
			</div>
		</form:form>


	</div>
</div>


<script type="text/javascript">

function initialize() {

	var input = document.getElementById('origin');
	var input2 = document.getElementById('destination');
	var options = {
		types: ['(cities)'],
		componentRestrictions: {country: 'es'}
	};
	var autocomplete = new google.maps.places.Autocomplete(input, options);
	var autocomplete = new google.maps.places.Autocomplete(input2, options);
	}
	
	
	google.maps.event.addDomListener(window, 'load', initialize);

	$('#origin').attr('placeholder', '');
	$('#destination').attr('placeholder', '');

	
	$(function() {
		language = getCookie("language");
		$('#datetimepicker1').datetimepicker({
				format : 'DD-MM-YYYY  HH:mm',
				locale: language
		});

		$('#datetimepicker2').datetimepicker({
			format : 'DD-MM-YYYY  HH:mm',
			locale: language
		});

	});
	

	var inputImage = document.getElementById('itemPicture');
	inputImage.onchange = function(e){ 
    	if (document.contains(document.getElementById("itemPicture.errors"))) {
            document.getElementById("itemPicture.errors").remove();
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
	        	inputImage.insertAdjacentHTML('afterend', '<span id="itemPicture.errors" class="error">'+mssg+'</span>');
	            this.value='';
	    }
	};

	
	
	
	
</script>