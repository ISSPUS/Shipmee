
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<!DOCTYPE html>
<html>
<head>

<base
	href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link rel="shortcut icon" href="favicon.ico"/>

<script type="text/javascript" src="scripts/scripts-selft.js"></script>
<script type="text/javascript" src="scripts/jquery.min.js"></script>
<script type="text/javascript" src="scripts/jquery-ui.min.js"></script>
<script type="text/javascript" src="scripts/jmenu.min.js"></script>
<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAdAr_iNZqmWFlGDqD6q5JFcG-sN7J6RpU&libraries=places&language=es&components=country:es"></script>

<meta name="description" content="Shipmee te ofrece la posibilidad de enviar o transportar paquetes conectando con otros usuarios.">
<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Shipmee</title>

    <!-- Bootstrap core CSS
    ================================================== -->
    <link rel="stylesheet" href="styles/assets/css/bootstrap.min.css"  type="text/css">
    <script src="styles/assets/js/bootstrap.min.js"></script>

    <!-- Custom styles for this template
    ================================================== -->
	<link rel='stylesheet' type='text/css' href='styles/assets/css/prettyPhoto.css'>
	<link rel='stylesheet' type='text/css' href='styles/assets/css/hoverex-all.css'>
    <link rel="stylesheet" href="styles/assets/css/style.css" type="text/css">
    <link rel="stylesheet" href="styles/assets/css/font-awesome.min.css" type="text/css">
    <link rel="stylesheet" href="styles/assets/css/style-self.css"  type="text/css">
    <link rel="stylesheet" href="styles/assets/css/lateral-menu.css" type="text/css">
    <link rel="stylesheet" href="styles/common.css" type="text/css">


<script type="text/javascript">
	function askSubmission(msg, form) {
		if (confirm(msg))
			form.submit();
	}
</script>

</head>

<body>

	<div>
		<tiles:insertAttribute name="header" />
	</div>
	<div>
		
		<tiles:insertAttribute name="body" />	
		<jstl:if test="${message != null}">
			<br />
			<div class="mensaje_error_servidor">
					<strong><span class="message"><spring:message code="${message}" /></span></strong>
			</div>
			<br />
		</jstl:if>	
	</div>
	<div>
		<tiles:insertAttribute name="footer" />
	</div>

</body>
</html>
