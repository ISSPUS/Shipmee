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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script async src="scripts/jquery.bootpag.min.js"></script>

<link rel="stylesheet" href="styles/assets/css/lateral-menu.css"
	type="text/css">

<link rel="stylesheet" href="styles/assets/css/style-messages.css"
	type="text/css">

<link rel="stylesheet" href="styles/assets/css/style-list.css"
	type="text/css">

<style>

.inbox .inbox-menu ul{
	margin-top: 0px !important;
}
.panel-default {
	margin-top: 3% ;
	float: None;
	margin-bottom: 1%;
	
}

</style>


<div class="blue-barra" >
	<div class="container">
		<div class="row ">
			<h3>
				<spring:message code="user.list" />
			</h3>
		</div>
		<!-- /row -->
	</div>
</div>

<div class="container">
	<div class="row inbox">
		<div class="col-md-4">
			<div class="panel panel-default">
				<div class="panel-body inbox-menu">
					<div class="text-center" style="margin-bottom: 5%"><b><spring:message code="user.filter" />:</b></div>
					<ul>
						<li
							<jstl:if test="${type eq 'verified'}">
													
								class="active"
							</jstl:if>>
							<a href="user/administrator/list.do?isVerified=1"><i
								class="fa fa-check-circle" style="color: #3e87fb;"></i> <spring:message
									code="users.verified" /> <i style="color: #428bca; float: right;" class="glyphicon glyphicon-chevron-right"></i></a>
						</li>

											<li
							<jstl:if test="${type eq 'noverified'}">
													
								class="active"
							</jstl:if>>
							<a href="user/administrator/list.do?isVerified=0"><i
								class="fa fa-times-circle" style="color:#a92424;"></i> <spring:message
									code="users.noVerified" /> <i style="color: #428bca; float: right;" class="glyphicon glyphicon-chevron-right"></i></a>
						</li>
						<li
							<jstl:if test="${type eq 'moderator'}">
													
								class="active"
							</jstl:if>>
							<a href="user/administrator/list.do?isModerator=1"><i
								class="fa fa-gavel" aria-hidden="true" style="color:#f95300;" ></i> <spring:message
									code="users.moderator" /> <i style="color: #428bca; float: right;" class="glyphicon glyphicon-chevron-right"></i></a>
						</li>
						<li
							<jstl:if test="${type eq 'nomoderator'}">
													
								class="active"
							</jstl:if>>
							<a href="user/administrator/list.do?isModerator=0"><i
								class="fa fa-times-circle" aria-hidden="true" style="color:#f95300;" ></i> <spring:message
									code="users.noModerator" /> <i style="color: #428bca; float: right;" class="glyphicon glyphicon-chevron-right"></i></a>
						</li>
						<li
							<jstl:if test="${type eq 'all'}">
													
								class="active"
							</jstl:if>>
							<a href="user/administrator/list.do"><i
								class="fa fa-users"></i> <spring:message
									code="users.all" /> <i style="color: #428bca; float: right;" class="glyphicon glyphicon-chevron-right"></i></a>
						</li>
					</ul>
				</div>
			</div>
		</div>
	
	
		<div class="col-md-8 ">
		<div class="profile-content">
					
				
					<div class="panel-body panel">
			<div class="table-container">
				<table class="table table-filter">
					<tbody>
						<jstl:if test="${not empty users.content}">
							<jstl:forEach items="${users.content}" var="userRow">

								<tr data-status="pagado">
									<td>
										<div class="media">
											<a href="#" class="pull-left"> <img
												src="${userRow.photo}" class="media-photo"></a>

												<jstl:set var="isModerator" scope="session" value="FALSE" />
												<jstl:forEach items="${userRow.userAccount.authorities}"
													var="actRow">
													<jstl:if test="${actRow.authority eq 'MODERATOR'}">
														<jstl:set var="isModerator" scope="session" value="TRUE" />
													</jstl:if>
												</jstl:forEach>

												<div class="media-body">
												<span class="media-meta pull-right"> <fmt:formatDate
														type="both" dateStyle="medium" timeStyle="medium"
														value="${userRow.birthDate}" /></span>
												<h4 class="title">
													<jstl:if test="${isModerator}">
															<i class="fa fa-gavel" aria-hidden="true" style="color:#f95300;" title="<spring:message code="user.moderator" />"></i>


														</jstl:if>

														<jstl:if test="${userRow.isVerified}">
														
															<i class="fa fa-check-circle" aria-hidden="true"
																style="color: #3e87fb;"
																title="<spring:message code="user.verified" />"></i>
																
														</jstl:if>
														<a href="user/profile.do?userId=${userRow.id}"><jstl:out
															value="${userRow.userAccount.username }" /></a>

												
												</h4>

												<span class="pendiente"> <jstl:out
														value="${userRow.name}" /> <jstl:out
														value="${userRow.surname}" />
												</span>

												<p class="summary">
													NIF: <jstl:out value="${userRow.dni}"></jstl:out>
													
													
																										
													<br />
													<jstl:if test="${!userRow.isVerified && userRow.dniPhoto != '' && userRow.photo != 'images/anonymous.png' && userRow.phone != '' && userRow.dni != ''}">
														<a href="user/administrator/verifyUser.do?userId=${userRow.id}"> 
															<spring:message code="user.verifyUser" />
														</a>
													</jstl:if>
													<jstl:if test="${userRow.isVerified}">
														<a href="user/administrator/unverifyUser.do?userId=${userRow.id}">
															<spring:message code="user.unverifyUser" />
														</a>
													</jstl:if>
													<jstl:if test="${!isModerator && userRow.isVerified}">
														<br />
														<a href="user/administrator/turnIntoModerator.do?userId=${userRow.id}">
															<spring:message code="user.turnIntoModerator" />
														</a>
													</jstl:if>
													<jstl:if test="${isModerator}">
														<br />
														<a href="user/administrator/unturnIntoModerator.do?userId=${userRow.id}"> 
															<spring:message code="user.unturnIntoModerator" />
														</a>
													</jstl:if>
												</p>
											</div>
										</div>
									</td>
								</tr>

							</jstl:forEach>
													
						</jstl:if>
									
					</tbody>
				</table>
						<jstl:if test="${not empty users.content}">

							<div class="col-xs-12" style="text-align: center">

								<div id="pagination" class="copyright">

									<script>
										$('#pagination')
												.bootpag(
														{
															total : <jstl:out value="${total_pages}"></jstl:out>,
															page : <jstl:out value="${p}"></jstl:out>,
															maxVisible : 3,
															leaps : true,
															firstLastUse : true,
															first : '<',
							            last: '>',
															wrapClass : 'pagination',
															activeClass : 'active',
															disabledClass : 'disabled',
															nextClass : 'next',
															prevClass : 'prev',
															lastClass : 'last',
															firstClass : 'first'
														})
												.on(
														'page',
														function(event, num) {
															currentLocation = window.location.href;
															if(currentLocation.indexOf('?')> -1){
																if(currentLocation.indexOf('page=')> -1){
																	var actualLocation = currentLocation.replace(/page=.*/g,"page="+ num);
																	window.location.href = actualLocation;
																}else{
																	window.location.href = currentLocation+"&page="
																	+ num + "";
																}
															}else{
																window.location.href = currentLocation+"?page="
																	+ num + "";
															}
															
															page = 1
														});
									</script>

								</div>
							</div>
						</jstl:if>

						<jstl:if test="${fn:length(users.content) ==0}">
							<div class="alert alert-info">
								<strong><spring:message code="user.wihtout.results" /></strong>
							</div>
						</jstl:if>

					</div>
					
					
		</div></div>
	</div>

</div>

	
	</div>


