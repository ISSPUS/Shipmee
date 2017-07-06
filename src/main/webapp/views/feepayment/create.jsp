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
</style>
<style>
.date .dropdown-menu {
	background-color: white ! important;
}

.paymentWrap {
	margin-left: -50px;
	margin-top: 30px;
	margin-bottom: 50px;
}

.paymentWrap .paymentBtnGroup {
	max-width: 500px;
	margin: auto;
}

.paymentWrap .paymentBtnGroup .paymentMethod {
	padding: 40px;
	box-shadow: none;
	position: relative;
}

.paymentWrap .paymentBtnGroup .paymentMethod.active {
	outline: none !important;
}

.paymentWrap .paymentBtnGroup .paymentMethod.active .method {
	border-color: #4cd264;
	outline: none !important;
	box-shadow: 0px 3px 22px 0px #7b7b7b;
}

.paymentWrap .paymentBtnGroup .paymentMethod .method {
	position: absolute;
	right: 0px;
	top: -10px;
	bottom: -10px;
	left: 40px;
	background-size: contain;
	background-position: center;
	background-repeat: no-repeat;
	border: 2px solid transparent;
	transition: all 0.5s;
}

.paymentWrap .paymentBtnGroup .paymentMethod .method.visa {
	background-image: url("images/paypal_logo.png");
}

.paymentWrap .paymentBtnGroup .paymentMethod .method.master-card {
	background-image: url("images/credit-card.png");
}


.paymentWrap .paymentBtnGroup .paymentMethod .method:hover {
	border-color: #4cd264;
	outline: none !important;
}

.opaco{
	opacity: 0.5;
}

.formulario-sm {
    padding: 0%;
}


.payPaypal{
	display: block;
    margin: 0 auto;
    width: 150px;

}

.image-p:hover{
	filter: brightness(110%);
}

.image-p{
	margin-top: 10px;
    margin-bottom: 10px;

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
	<div class="row">
		<div style="margin: 15px;" class="paymentCont">
			<div class="headingWrap">
				<h3 class="headingTop text-center">
					<spring:message code="feePayment.sentence" />
				</h3>
			</div>
			<div class="paymentWrap ">
				<div class="btn-group paymentBtnGroup btn-group-justified">
					<label class="btn paymentMethod partePaypal"> <span
						class="method visa"></span> <input name="options" class="pago"
						type="radio" value="paypal" style="visibility: hidden;" />
					</label> <label class="btn paymentMethod parteTarjeta"> <span
						class="method master-card" style="margin-left: -18px;"></span> <input checked="checked"
						class="pago" name="options" type="radio" value="creditcard"
						style="visibility: hidden;" />
					</label>
				</div>
			</div>

		</div>

	</div>
</div>




<div id="div2" style="display: none;">
	<div style="margin-bottom: 3%;">
		<a class="payPaypal" href="https://paypal.com/myaccount"
			target="_blank"> <jstl:if test="${feePaymentForm.type > 2}">
				<a class="payPaypal"
					href="user/payPal/pay.do?type=${feePaymentForm.type}&id=${feePaymentForm.offerId}&sizePriceId=${feePaymentForm.sizePriceId}&amount=${feePaymentForm.amount}&description=${feePaymentForm.description}&shipmentId=${feePaymentForm.shipmentId}">
			</jstl:if> <jstl:if test="${feePaymentForm.type < 3}">
				<a class="payPaypal"
					href="user/payPal/pay.do?type=${feePaymentForm.type}&id=${feePaymentForm.id}&sizePriceId=${feePaymentForm.sizePriceId}&amount=${feePaymentForm.amount}&description=${feePaymentForm.description}&shipmentId=${feePaymentForm.shipmentId}">
			</jstl:if> <img class="image-p"
			src="images/pay_paypal.png"></a>
	</div>
</div>

<div id="div1" style="display:none;">
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
				<form:hidden path="shipmentId" />

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
							path="creditCard.brandName" required="required">
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
						<form:errors class="error create-message-error" path="creditCard.number"/>
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
						<form:input path="creditCard.expirationYear" class="form-control" id="expirationYear" required="true" type="number"/>
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
						<form:input path="creditCard.cvvCode" class="form-control" id="cvvCode" required="true" maxlength="3"  type="number"/>
						<form:errors class="error create-message-error" path="creditCard.cvvCode" />
					</div>
					
					<!-- Modal -->
					<div class="modal fade" id="myModal2" tabindex="-1" role="dialog"
						aria-labelledby="myModalLabel2" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content text-center">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">&times;</button>
										<h4 class="modal-title text-center">
											<spring:message code="feePayment.cvvCode" />
										</h4>
									</div>
									<div style="padding: 3%;">
										<img src="images/cvv_code.png" />
									</div>
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

					<acme:cancel code="feePayment.cancel" url="" />
					<a href="#aboutModal" data-toggle="modal" data-target="#myModal">
				<span class="glyphicon glyphicon-info-sign"></span> <spring:message code="user.help" /></a>


			<!-- Modal -->
						<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
							aria-labelledby="myModalLabel" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content text-center">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">&times;</button>
										<h4 class="modal-title text-center">
											<spring:message code="user.help" />
										</h4>
									</div>
									<div style="padding: 3%;">
										<img src="images/info_tarjeta.png" />
									</div>
								</div>
							</div>

						</div>
					</div>
				<!-- Action buttons -->


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
	if("${message}" || $(".error")[0]){
		$("#div1").css("display", "block");
        $("#div2").css("display", "none");
        $(".paymentMethod").click(function(evento){
        	$(".partePaypal").addClass("opaco");
        	$(".parteTarjeta").removeClass("opaco");
        });
	}
    $(".pago").click(function(evento){
    	
        var valor = $(this).val();
        
        if(valor == 'creditcard'){
            $("#div1").css("display", "block");
            $("#div2").css("display", "none");
            $(".paymentMethod").click(function(evento){
            	$(".partePaypal").addClass("opaco");
            	$(".parteTarjeta").removeClass("opaco");
            });
        }else{
            $("#div1").css("display", "none");
            $("#div2").css("display", "block");
			$(".paymentMethod").click(function(evento){
				$(".parteTarjeta").addClass("opaco");
				$(".partePaypal").removeClass("opaco");
            });
        }
	});
	
});
</script>