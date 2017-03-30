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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<link href='http://fonts.googleapis.com/css?family=Open+Sans'
	rel='stylesheet' type='text/css'>
<link
	href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css"
	rel="stylesheet">

    <link rel="stylesheet" href="styles/assets/css/style-lists-offers.css"  type="text/css">

<jstl:if test="${!empty ratings.content && userReceivedId > 0}">
	<div class="blue-barra"
		style="padding-top: 0.75%; padding-bottom: 0.75%;">
		<div class="container">
			<div class="row">
				<h3>
				<jstl:if test="${userReceivedId > 0}">
					<spring:message code="rating.list.for" />
					<a href="user/profile.do?userId=${ratings.content[0].user.id}">
						<jstl:out value="${ratings.content[0].user.name}" />
						<jstl:out value="${ratings.content[0].user.surname}" />
						(<jstl:out value="${ratings.content[0].user.userAccount.username}" />)
					</a>
				</jstl:if>
				</h3>
				<h3>
				<jstl:if test="${authorId > 0}">
					<spring:message code="rating.list.by" />
					<a href="user/profile.do?userId=${ratings.content[0].author.id}">
						<jstl:out value="${ratings.content[0].author.name}" />
						<jstl:out value="${ratings.content[0].author.surname}" />
						(<jstl:out value="${ratings.content[0].author.userAccount.username}" />)
					</a>
				</jstl:if>
				</h3>
			</div>
			<!-- /row -->
		</div>
	</div>
</jstl:if>


<jstl:if test="${empty ratings.content}">
	<center>
		<h2>
			<spring:message code="rating.anything" />
		</h2>
	</center>
</jstl:if>

<jstl:if test="${currentActorId != userReceivedId && userReceivedId > 0 && currentActorId > 0}">
	<center>
		<div class="profile-userbuttons">
			<button type="button" class="btn button-profile btn-sm" onclick="location.href = 'rating/user/create.do?userReceivedId=${userReceivedId}';">
				<spring:message code="rating.create" />
			</button>
		</div>
	</center>
</jstl:if>

<div class="container">
	<jstl:forEach items="${ratings.content}" var="ratingRow">
		<div class="row"
			style="margin-top: 2%; margin-bottom: 2%; margin-right: 0 !important;">
			<div class="col-xs-12 col-lg-9 offer-shipment"
				style="float: none; margin: 0 auto;">
				<div class="row perfil-info-offer">
					<div class="img-perfil-offer col-xs-4 col-sm-3 col-lg-2">
						<img
							src="${ratingRow.author.photo}"
							class="img-thumbnail  profile-offer-img ">
					</div>
					<div>
						<p><b><spring:message code="rating.author" /> : </b><jstl:out value="${ratingRow.author.userAccount.username}" /></p>
					</div>
					<div>
						<p><b><spring:message code="rating.user" /> : </b><jstl:out value="${ratingRow.user.userAccount.username}" /></p>
					</div>
					<div>
						<p><b><spring:message code="rating.value" /> : </b><jstl:out value="${ratingRow.value}" /></p>
					</div>
					<div>
						<p><b><spring:message code="rating.comment" /> : </b><jstl:out value="${ratingRow.comment}" /></p>
					</div>
					<div>
						<p><b><spring:message code="rating.createdDate" /> : </b><jstl:out value="${ratingRow.createdDate}" /></p>
					</div>

 				</div>
			</div>
		</div>

	</jstl:forEach>

</div>

<div id="pagination" class="copyright">

	<script>
		$('#pagination').bootpag({
			total : <jstl:out value="${total_pages}"></jstl:out>,
			page : <jstl:out value="${p}"></jstl:out>,
			maxVisible : 5,
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
		}).on('page', function(event, num) {
			window.location.href = "${urlPage}" + num + "";
			page = 1
		});
	</script>

</div>