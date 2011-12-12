<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="/WEB-INF/tlds/f.tld"%>
<%@include file="/cms/header.jsp" %>

<c:if test="${empty persons}">
	<p class="notice">Nebyli přidáni žádní uživatelé</p>
</c:if>
<c:if test="${not empty persons}">
	<table class="grid">
		<tr>
			<th>Uživatel</th>
			<th>Administrátor</th>
		</tr>
		<c:forEach var="person" items="${persons}" varStatus="rowCounter">
			<tr <c:if test="${rowCounter.count % 2 == 0}">class="alt"</c:if>>
				<td>
					<c:set var="eventUrl" value="event?person=${f:h(person.user)}"/>
					<a href="${f:url(eventUrl)}">${f:h(person.user)}</a>
				</td>
				<td>${f:h(person.adminString)}</td>
			</tr>
		</c:forEach>
	</table>
</c:if>

<%@include file="/cms/footer.jsp" %>