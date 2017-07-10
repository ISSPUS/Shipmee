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
.formulario-sm {
    padding: 1%;
}
</style>

<style>
@import('https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.0/css/bootstrap.min.css') 

.funkyradio div {
  clear: both;
  overflow: hidden;
}

.funkyradio label {
  width: 100%;
  border-radius: 3px;
  border: 1px solid #D1D3D4;
  font-weight: normal;
}

.funkyradio input[type="radio"]:empty,
.funkyradio input[type="checkbox"]:empty {
  display: none;
}

.funkyradio input[type="radio"]:empty ~ label,
.funkyradio input[type="checkbox"]:empty ~ label {
  position: relative;
  line-height: 2.5em;
  text-indent: 3.25em;
  margin-top: 2em;
  cursor: pointer;
  -webkit-user-select: none;
     -moz-user-select: none;
      -ms-user-select: none;
          user-select: none;
}

.funkyradio input[type="radio"]:empty ~ label:before,
.funkyradio input[type="checkbox"]:empty ~ label:before {
  position: absolute;
  display: block;
  top: 0;
  bottom: 0;
  left: 0;
  content: '';
  width: 2.5em;
  background: #D1D3D4;
  border-radius: 3px 0 0 3px;
}

.funkyradio input[type="radio"]:hover:not(:checked) ~ label,
.funkyradio input[type="checkbox"]:hover:not(:checked) ~ label {
  color: #888;
}

.funkyradio input[type="radio"]:hover:not(:checked) ~ label:before,
.funkyradio input[type="checkbox"]:hover:not(:checked) ~ label:before {
  content: '\2714';
  text-indent: .9em;
  color: #C2C2C2;
}

.funkyradio input[type="radio"]:checked ~ label,
.funkyradio input[type="checkbox"]:checked ~ label {
  color: #777;
}

.funkyradio input[type="radio"]:checked ~ label:before,
.funkyradio input[type="checkbox"]:checked ~ label:before {
  content: '\2714';
  text-indent: .9em;
  color: #333;
  background-color: #ccc;
}

.funkyradio input[type="radio"]:focus ~ label:before,
.funkyradio input[type="checkbox"]:focus ~ label:before {
  box-shadow: 0 0 0 3px #999;
}

.funkyradio-default input[type="radio"]:checked ~ label:before,
.funkyradio-default input[type="checkbox"]:checked ~ label:before {
  color: #333;
  background-color: #ccc;
}

.funkyradio-primary input[type="radio"]:checked ~ label:before,
.funkyradio-primary input[type="checkbox"]:checked ~ label:before {
  color: #fff;
  background-color: #337ab7;
}

.funkyradio-success input[type="radio"]:checked ~ label:before,
.funkyradio-success input[type="checkbox"]:checked ~ label:before {
  color: #fff;
  background-color: #5cb85c;
}

.funkyradio-danger input[type="radio"]:checked ~ label:before,
.funkyradio-danger input[type="checkbox"]:checked ~ label:before {
  color: #fff;
  background-color: #d9534f;
}

.funkyradio-warning input[type="radio"]:checked ~ label:before,
.funkyradio-warning input[type="checkbox"]:checked ~ label:before {
  color: #fff;
  background-color: #f0ad4e;
}

.funkyradio-info input[type="radio"]:checked ~ label:before,
.funkyradio-info input[type="checkbox"]:checked ~ label:before {
  color: #fff;
  background-color: #5bc0de;
}

input:valid ~ label {
    top: -20px;
    font-size: 14px;
    color: #aaa;
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
	<div class="row">
		<div style="margin: 15px;" class="">
			<div class="headingWrap" style="margin-bottom:2%">
				<h3 class="headingTop text-center">
					<spring:message code="fundTransferPreference.sentence" />
				</h3>
			</div>
			<div class="paymentWrap col-md-4" style="margin: 0 auto;float: none;">


				<div class="funkyradio">
					<div class="funkyradio-primary">
						<input name="options" class="pago" type="radio" value="paypal"
							id="radio2"/> <label for="radio2"><span
							class="glyphicon glyphicon-euro"></span> <spring:message code="fundTransferPreference.paypal" /></label>
					</div>


					<div class="funkyradio-primary">
						<input class="pago" name="options" type="radio" value="creditcard"
							id="radio3" /> <label for="radio3"><i
							class="fa fa-credit-card" aria-hidden="true"></i> <spring:message code="fundTransferPreference.bankAccount" /></label>
					</div>
				</div>

			</div>

		</div>

	</div>
</div>



<div id="div2" style="display: none;">
<div class="container">
	<div class="row formulario-sm">
		<div class="col-xs-12 col-sm-12 col-md-8 col-lg-8"
			style="margin: 0 auto; float: none;">
			<form:form enctype="multipart/form-data"  action="fundTransferPreference/user/edit.do" modelAttribute="fundTransferPreferenceForm"
				method="post" class="form-horizontal" role="form">
				
				<form:hidden path="country"/>
				<form:hidden path="accountHolder"/>
				<form:hidden path="bankName"/>
				<form:hidden path="IBAN"/>
				<form:hidden path="BIC"/>
				
				<div class="form-group">
					<form:label path="paypalEmail" class="control-label col-md-2" for="paypalEmail">
						<spring:message code="fundTransferPreference.paypalEmail" />
					</form:label>
					<div class="col-md-10">
						<form:input path="paypalEmail" class="form-control" id="paypalEmail" type="email" required="true"/>
						<form:errors class="error create-message-error" path="paypalEmail" />
					</div>
				</div>
				
				<!-- Action buttons -->
				<div class="form-group text-center profile-userbuttons">
					<button type="submit" name="savePaypal" class="btn  btn-primary">
						<span class="glyphicon glyphicon-floppy-disk"></span>
						<spring:message code="fundTransferPreference.save.paypal" />
					</button>


					<acme:cancel code="fundTransferPreference.cancel" url="/user/profile.do" />
				</div>
				
				</form:form>
		</div>

	</div>
</div>
</div>
<div id="div1" style="display:none;">

<div class="container">
	<div class="row formulario-sm">
		<div class="col-xs-12 col-sm-12 col-md-8 col-lg-8"
			style="margin: 0 auto; float: none;">
			<form:form enctype="multipart/form-data"  action="fundTransferPreference/user/edit.do" modelAttribute="fundTransferPreferenceForm"
				method="post" class="form-horizontal" role="form">
				<form:hidden path="paypalEmail"/>
				
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
				
				

				<!-- Action buttons -->
				<div class="form-group text-center profile-userbuttons">
					<button type="submit" name="saveBankAccount" class="btn  btn-primary">
						<span class="glyphicon glyphicon-floppy-disk"></span>
						<spring:message code="fundTransferPreference.save.creditCard" />
					</button>


					<acme:cancel code="fundTransferPreference.cancel" url="/user/profile.do" />
				</div>
				


			</form:form>
		</div>

	</div>
</div>

</div>

<script>

$(function(){
	$('div.product-chooser').not('.disabled').find('div.product-chooser-item').on('click', function(){
		$(this).parent().parent().find('div.product-chooser-item').removeClass('selected');
		$(this).addClass('selected');
		$(this).find('input[type="radio"]').prop("checked", true);
		
	});
});


$(document).ready(function(){
    $(".pago").click(function(evento){
    	
        var valor = $(this).val();
        
        if(valor == 'creditcard'){
            $("#div1").css("display", "block");
            $("#div2").css("display", "none");
            
        }else{
            $("#div1").css("display", "none");
            $("#div2").css("display", "block");
			
        }
	});
	
});
</script>