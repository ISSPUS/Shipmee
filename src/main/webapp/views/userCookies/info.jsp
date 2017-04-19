<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<style>

.hline{

margin-bottom: 10%;

}

.mtb {
    margin-top: 50px;
    margin-bottom: 80px;
}
  
}

</style>

	<div class="blue-barra">
	    <div class="container">
			<div class="row">
				<h3><spring:message code="cookies.polices" /></h3>
			</div><!-- /row -->
	    </div>
	</div>
	
	<div class="container mtb">
	
		
		<h2><spring:message code="cookies.title"/></h2><br>
		
		
		<p> <spring:message code="cookies.text"/>			
		</p>
	
	</div>
	
	
	
	
