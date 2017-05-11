
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<link rel="stylesheet" href="styles/assets/css/datetimepicker.min.css" />
<script type="text/javascript" src="scripts/moment.min.js"></script>
<script type="text/javascript" src="scripts/datetimepicker.min.js"></script>

<head>

</head>
<body>
<div class="container containerLogin">

	<div id="loginbox"
		class="mainbox col-md-4 col-md-offset-4 col-sm-6 col-sm-offset-3">


		<div class="panel panel-default">
			<div class="panel-heading">
				<div class="panel-title text-center"><spring:message code="password.recovery" /></div>
			</div>

			<div class="panel-body">
				<h4 style="text-align: center;"><spring:message code="${resultMessage}" /></h4>
			</div>
		</div>

	</div> 
</div>
</body>
	
	