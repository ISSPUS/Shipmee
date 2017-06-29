<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<link rel="stylesheet" href="/styles/assets/css/bootstrap.min.css"  type="text/css">
<link rel="stylesheet" href="/styles/assets/css/lateral-menu.css" type="text/css">



    <style>
    body {
		background: rgba(73,155,234,1);
		background: -moz-linear-gradient(-45deg, rgba(73,155,234,1) 0%, rgba(73,155,234,1) 0%, rgba(56,68,82,1) 100%);
		background: -webkit-gradient(left top, right bottom, color-stop(0%, rgba(73,155,234,1)), color-stop(0%, rgba(73,155,234,1)), color-stop(100%, rgba(56,68,82,1)));
		background: -webkit-linear-gradient(-45deg, rgba(73,155,234,1) 0%, rgba(73,155,234,1) 0%, rgba(56,68,82,1) 100%);
		background: -o-linear-gradient(-45deg, rgba(73,155,234,1) 0%, rgba(73,155,234,1) 0%, rgba(56,68,82,1) 100%);
		background: -ms-linear-gradient(-45deg, rgba(73,155,234,1) 0%, rgba(73,155,234,1) 0%, rgba(56,68,82,1) 100%);
		background: linear-gradient(135deg, rgba(73,155,234,1) 0%, rgba(73,155,234,1) 0%, rgba(56,68,82,1) 100%);
		filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#499bea', endColorstr='#384452', GradientType=1 );
        background-size: cover;
        -moz-background-size: cover;
        -webkit-background-size: cover;
        -o-background-size: cover;
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
        margin: 0;
        font-size: 250px;
    }
     h2 {
        margin: 0;
        font-size: 52px;
    font-weight: bold;
    }
    p {
        font-size: 17px;
        margin-top: 25px;
    }
    p a.btn {
        margin: 0 5px;
    }
    h1 .ion {
        vertical-align: -5%;
        margin-right: 5px;
    }
    </style>


<div class="container">
	<div style="text-align: center;">
		<div style="margin-bottom: 3%; font-family: console;">
			<h1>403</h1>
			<h2>ERROR</h2>
		</div>

		<img src="/images/furgoStop.svg" style="width: 30%;">
		<p><spring:message code="error403.sentence" /></p>

		<div class="profile-userbuttons" style="margin: 2%;">

			<button type="submit" class="btn btn-primary"
				onclick = "javascript:location.href='/'"
				style="margin-bottom: 10px;">
				<i class="glyphicon glyphicon-home"></i>
				<spring:message code="error403.home" />
			</button>
			<button type="submit" class="btn btn-primary"
				onclick="history.back();" style="margin-bottom: 10px;">
				<i class="glyphicon glyphicon-arrow-left"></i>
				<spring:message code="error403.back" />
			</button>

		</div>
		

	</div>
</div>

