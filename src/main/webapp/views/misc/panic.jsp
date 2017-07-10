
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<script type="text/javascript" src="/scripts/scripts-selft.js"></script>
<script type="text/javascript" src="/scripts/jquery.min.js"></script>
<script type="text/javascript" src="/scripts/jquery-ui.min.js"></script>
<script type="text/javascript" src="/scripts/jmenu.min.js"></script>
    <link rel="stylesheet" href="/styles/assets/css/lateral-menu.css" type="text/css">

<meta name="description"
	content="Shipmee te ofrece la posibilidad de enviar o transportar paquetes conectando con otros usuarios.">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Shipmee</title>

<!-- Bootstrap core CSS
    ================================================== -->
<link rel="stylesheet" href="/styles/assets/css/bootstrap.min.css"  type="text/css">
<link rel="stylesheet" href="/styles/assets/css/lateral-menu.css" type="text/css">

<style>
body {
	background: rgba(73, 155, 234, 1);
	font-family: 'Source Sans Pro', sans-serif;
	color: white;
}

.well {
	margin: 20% auto;
	text-align: center;
	padding: 25px;
	max-width: 600px;
	border: 0px;
	background: rgba(0, 0, 0, 0.6);
}

h1 {
	font-size: 150px;
}

h2 {
	font-weight: bold;
	font-size: 75px;
}

p {
	font-size: 25px;
	margin-top: 25px;
	color:white;
}

p a.btn {
	margin: 0 5px;
}

h1 .ion {
	vertical-align: -5%;
	margin-right: 5px;
}

@media only screen
and (min-device-width : 250px)
and (max-device-width : 480px) {
.furgo-svg {
		width: 60%!important;
	}
}
</style>
<body>
	<div class="container">
		<div class="row">
			<div style="font-family: console; text-align: center;">
				<h2><spring:message code="error.internal.server" /></h2>
			</div>
		</div>
		<div class="row" style="font-family: console; text-align: center;">

			<img class="furgo-svg" src="/images/furgoFallo.svg"
				style="width: 30%;">

		</div>
		
		<div class="row" style="text-align: center;">

			<div class="profile-userbuttons" style="margin: 2%;">

				<button type="submit" class="btn btn-primary"
					onclick="javascript:location.href='/'" style="margin-bottom: 10px;">
					<i class="glyphicon glyphicon-home"></i> <spring:message code="error403.home" />
				</button>
				<button type="submit" class="btn btn-primary"
					onclick="history.back();" style="margin-bottom: 10px;">
					<i class="glyphicon glyphicon-arrow-left"></i> <spring:message code="error403.back" />
				</button>
				
				

			</div>
		</div>

	</div>


</body>


