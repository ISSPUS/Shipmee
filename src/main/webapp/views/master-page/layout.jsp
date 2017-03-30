
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

<script type="text/javascript" src="scripts/jquery.js"></script>
<script type="text/javascript" src="scripts/jquery-ui.js"></script>
<script type="text/javascript" src="scripts/jmenu.js"></script>

<title><tiles:insertAttribute name="title" ignore="true" /></title>

<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Shipmee</title>

    <!-- Bootstrap core CSS
    ================================================== -->
    <link rel="stylesheet" href="styles/assets/css/bootstrap.css"  type="text/css">

    <!-- Custom styles for this template
    ================================================== -->
    <link rel="stylesheet" href="styles/assets/css/style.css" type="text/css">
    <link rel="stylesheet" href="styles/assets/css/font-awesome.min.css" type="text/css">
    <link rel="stylesheet" href="styles/assets/css/style-self.css"  type="text/css">
    <link rel="stylesheet" href="styles/assets/css/lateral-menu.css" type="text/css">

	 <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="styles/assets/js/bootstrap.min.js"></script>
	<script src="styles/assets/js/retina-1.1.0.js"></script>
	<script src="styles/assets/js/jquery.hoverdir.js"></script>
	<script src="styles/assets/js/jquery.hoverex.min.js"></script>
	<script src="styles/assets/js/jquery.prettyPhoto.js"></script>
  	<script src="styles/assets/js/jquery.isotope.min.js"></script>
  	<script src="styles/assets/js/custom.js"></script>
	

    <!-- Just for debugging purposes. Don't actually copy this line! -->
    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->


<script type="text/javascript">
	$(document).ready(function() {
		$("#jMenu").jMenu();
	});

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
			<span class="message"><spring:message code="${message}" /></span>
		</jstl:if>	
	</div>
	<div>
		<tiles:insertAttribute name="footer" />
	</div>

</body>
</html>