
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<link rel="stylesheet" href="styles/assets/css/datetimepicker.min.css" />
<script async type="text/javascript" src="scripts/moment.min.js"></script>
<script async type="text/javascript" src="scripts/datetimepicker.min.js"></script>

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
					<form action="passwordRecovery/reset.do" method="POST">
					<input name="passwordResetToken" type="hidden" value="${passwordResetToken}">
					<p><spring:message code="password.recovery.reset.header" /></p>
					<div class="input-group">
						<span class="input-group-addon"> <i
							class="glyphicon glyphicon-lock"></i></span>
							<input class="form-control" name="password" type="password" placeholder="<spring:message code="password.recovery.password.placeholder" />" required>
					</div>
					<br />
					<div class="input-group">
						<span class="input-group-addon"> <i
							class="glyphicon glyphicon-lock"></i></span>
							<input class="form-control" name="confirmPassword" type="password" placeholder="<spring:message code="password.recovery.rpassword.placeholder" />" required>
					</div>
					<br />
					<div class="text-center">
						<button type="submit" name="send" class="btn btn-theme">
							<spring:message code="password.recovery.recover" />
						</button>
					</div>
					</form>
			</div>
		</div>

	</div> 
</div>
</body>
	
	