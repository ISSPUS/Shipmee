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

</style>

	<div class="blue-barra">
	    <div class="container">
			<div class="row">
				<h3><spring:message code="cgu.title" /></h3>
			</div><!-- /row -->
	    </div>
	</div>
	
	<div class="container mtb">
	
		<div class="row">
			<h2><spring:message code="cgu.article1.title"/></h2><br>
			<spring:message code="cgu.article1.body1"/><br>
			<spring:message code="cgu.article1.body2"/><br>
			<spring:message code="cgu.article1.body3"/><br>
		</div>
		<div class="row">
			<h2><spring:message code="cgu.article2.title"/></h2><br>
			<spring:message code="cgu.article2.body1"/><br>
			<spring:message code="cgu.article2.body2"/><br>
			<spring:message code="cgu.article2.body3"/><br>
			<spring:message code="cgu.article2.body4"/><br>
			<spring:message code="cgu.article2.body5"/><br>
			<spring:message code="cgu.article2.body6"/><br>
			<spring:message code="cgu.article2.body7"/><br>
			<spring:message code="cgu.article2.body8"/><br>
			<spring:message code="cgu.article2.body9"/><br>
			<spring:message code="cgu.article2.body10"/><br>
			<spring:message code="cgu.article2.body11"/><br>
			<spring:message code="cgu.article2.body12"/><br>
			<spring:message code="cgu.article2.body13"/><br>
			<spring:message code="cgu.article2.body14"/><br>
			<spring:message code="cgu.article2.body15"/><br>
			<spring:message code="cgu.article2.body16"/><br>
			<spring:message code="cgu.article2.body17"/><br>
			<spring:message code="cgu.article2.body18"/><br>
			<spring:message code="cgu.article2.body19"/><br>
			<spring:message code="cgu.article2.body20"/><br>
			<spring:message code="cgu.article2.body21"/><br>
			<spring:message code="cgu.article2.body22"/><br>
			<spring:message code="cgu.article2.body23"/><br>
			<spring:message code="cgu.article2.body24"/><br>
			<spring:message code="cgu.article2.body25"/><br>
		</div>

		<div class="row">		
			<h2><spring:message code="cgu.article3.title"/></h2><br>
			
			<h3><spring:message code="cgu.article3.1.title"/></h3><br>
			<spring:message code="cgu.article3.1.body1"/><br>
			
			<h3><spring:message code="cgu.article3.2.title"/></h3><br>
			<spring:message code="cgu.article3.2.body1"/><br>
			<spring:message code="cgu.article3.2.body2"/><br>
			<spring:message code="cgu.article3.2.body3"/><br>
			<spring:message code="cgu.article3.2.body4"/><br>
			<spring:message code="cgu.article3.2.body5"/><br>
		</div>

		<div class="row">
			<h3><spring:message code="cgu.article3.3.title"/></h3><br>
			<spring:message code="cgu.article3.3.body1"/><br>
			<spring:message code="cgu.article3.3.body2"/><br>
		</div>

		<div class="row">			
			<h2><spring:message code="cgu.article4.title"/></h2><br>
			
			<h3><spring:message code="cgu.article4.1.title"/></h3><br>
			<spring:message code="cgu.article4.1.body1"/><br>
			<spring:message code="cgu.article4.1.body2"/><br>
			<spring:message code="cgu.article4.1.body3"/><br>
			<spring:message code="cgu.article4.1.body4"/><br>
			<spring:message code="cgu.article4.1.body5"/><br>
			<spring:message code="cgu.article4.1.body6"/><br>
			<spring:message code="cgu.article4.1.body7"/><br>
			
			<h3><spring:message code="cgu.article4.2.title"/></h3><br>
			<spring:message code="cgu.article4.2.body1"/><br>
			<spring:message code="cgu.article4.2.body2"/><br>
			<spring:message code="cgu.article4.2.body3"/><br>
			<spring:message code="cgu.article4.2.body4"/><br>
			<spring:message code="cgu.article4.2.body5"/><br>
			<spring:message code="cgu.article4.2.body6"/><br>
			<spring:message code="cgu.article4.2.body7"/><br>
			<spring:message code="cgu.article4.2.body8"/><br>
			<spring:message code="cgu.article4.2.body9"/><br>
			<spring:message code="cgu.article4.2.body10"/><br>

			<h3><spring:message code="cgu.article4.3.title"/></h3><br>
			<spring:message code="cgu.article4.3.body1"/><br>
			<spring:message code="cgu.article4.3.body2"/><br>
			
			<h3><spring:message code="cgu.article4.4.title"/></h3><br>
			<spring:message code="cgu.article4.4.body1"/><br>
			<spring:message code="cgu.article4.4.body2"/><br>
			
			<h3><spring:message code="cgu.article4.5.title"/></h3><br>
			<spring:message code="cgu.article4.5.body1"/><br>
		</div>

		<div class="row">
			<h2><spring:message code="cgu.article5.title"/></h2><br>
			<spring:message code="cgu.article5.body1"/><br>
			
			<h3><spring:message code="cgu.article5.1.title"/></h3><br>
			<spring:message code="cgu.article5.1.body1"/><br>
			
			<h3><spring:message code="cgu.article5.2.title"/></h3><br>
			<spring:message code="cgu.article5.2.body1"/><br>

			<h3><spring:message code="cgu.article5.3.title"/></h3><br>
			<spring:message code="cgu.article5.3.body1"/><br>
			<spring:message code="cgu.article5.3.body2"/><br>
			<spring:message code="cgu.article5.3.body2"/><br>
		</div>

		<div class="row">
			<h2><spring:message code="cgu.article6.title"/></h2><br>
			<spring:message code="cgu.article6.body1"/><br>
			<spring:message code="cgu.article6.body2"/><br>
			<spring:message code="cgu.article6.body3"/><br>
		</div>

		<div class="row">
			<h2><spring:message code="cgu.article7.title"/></h2><br>
			<spring:message code="cgu.article7.body1"/><br>
		</div>

		<div class="row">
			<h2><spring:message code="cgu.article8.title"/></h2><br>
			
			<h3><spring:message code="cgu.article8.1.title"/></h3><br>
			<spring:message code="cgu.article8.1.body1"/><br>
			<spring:message code="cgu.article8.1.body2"/><br>
			<spring:message code="cgu.article8.1.body3"/><br>
			<spring:message code="cgu.article8.1.body4"/><br>
			<spring:message code="cgu.article8.1.body5"/><br>
			<spring:message code="cgu.article8.1.body6"/><br>
			<spring:message code="cgu.article8.1.body7"/><br>
			<spring:message code="cgu.article8.1.body8"/><br>
			<spring:message code="cgu.article8.1.body9"/><br>
			<spring:message code="cgu.article8.1.body10"/><br>
			<spring:message code="cgu.article8.1.body11"/><br>
			
			<h3><spring:message code="cgu.article8.2.title"/></h3><br>
			<spring:message code="cgu.article8.2.body1"/><br>
			<spring:message code="cgu.article8.2.body2"/><br>
			<spring:message code="cgu.article8.2.body3"/><br>
			<spring:message code="cgu.article8.2.body4"/><br>
			<spring:message code="cgu.article8.2.body5"/><br>
			<spring:message code="cgu.article8.2.body6"/><br>
			<spring:message code="cgu.article8.2.body7"/><br>
			<spring:message code="cgu.article8.2.body8"/><br>
			<spring:message code="cgu.article8.2.body9"/><br>
			<spring:message code="cgu.article8.2.body10"/><br>
			<spring:message code="cgu.article8.2.body11"/><br>
			<spring:message code="cgu.article8.2.body12"/><br>
			<spring:message code="cgu.article8.2.body13"/><br>
		</div>
			
		<div class="row">	
			<h2><spring:message code="cgu.article9.title"/></h2><br>
			<spring:message code="cgu.article9.body1"/><br>
		</div>

		<div class="row">		
			<h2><spring:message code="cgu.article10.title"/></h2><br>
			
			<h3><spring:message code="cgu.article10.1.title"/></h3><br>
			<spring:message code="cgu.article10.1.body1"/><br>
			<spring:message code="cgu.article10.1.body2"/><br>
			<spring:message code="cgu.article10.1.body3"/><br>
			<spring:message code="cgu.article10.1.body4"/><br>
			<spring:message code="cgu.article10.1.body5"/><br>
			
			<h3><spring:message code="cgu.article10.2.title"/></h3><br>
			<spring:message code="cgu.article10.2.body1"/><br>
			<spring:message code="cgu.article10.2.body2"/><br>
			<spring:message code="cgu.article10.2.body3"/><br>
		</div>
			
		<div class="row">	
			<h2><spring:message code="cgu.article11.title"/></h2><br>
			<spring:message code="cgu.article11.body1"/><br>
			<spring:message code="cgu.article11.body2"/><br>
			<spring:message code="cgu.article11.body3"/><br>
			<spring:message code="cgu.article11.body4"/><br>
			<spring:message code="cgu.article11.body5"/><br>
			<spring:message code="cgu.article11.body6"/><br>
			<spring:message code="cgu.article11.body7"/><br>
			<spring:message code="cgu.article11.body8"/><br>
			<spring:message code="cgu.article11.body9"/><br>
			<spring:message code="cgu.article11.body10"/><br>
		</div>

		<div class="row">
			<h2><spring:message code="cgu.article12.title"/></h2><br>
			<spring:message code="cgu.article12.body1"/><br>
			<spring:message code="cgu.article12.body2"/><br>
		</div>

		<div class="row">
			<h2><spring:message code="cgu.article13.title"/></h2><br>
			<spring:message code="cgu.article13.body1"/><br>
			<spring:message code="cgu.article13.body2"/><br>
		</div>
		
		<div class="row">
			<h2><spring:message code="cgu.article14.title"/></h2><br>
			<spring:message code="cgu.article14.body1"/><br>
			<spring:message code="cgu.article14.body2"/><br>
			<spring:message code="cgu.article14.body3"/><br>
		</div>
		
		<div class="row">
			<h2><spring:message code="cgu.version"/></h2><br>
		</div>

	</div>
	
	
	
	
