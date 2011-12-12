<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="/WEB-INF/tlds/f.tld"%>
<%@include file="/cms/header.jsp" %>

<c:if test="${empty events}">
	<p class="notice">Nebyli přidány žádné události tohoto uživatele</p>
	<a href="${f:url("event")}">Seznam uživatlů</a>
</c:if>
<c:if test="${not empty events}">
	<c:set var="eventAddUrl" value="event?addevent=${f:h(person)}"/>
	<p><a href="${f:url(eventAddUrl)}">Přidat událost</a></p>

	<table class="grid">
		<tr>
			<th>Jméno</th>
			<th>Popis</th>
			<th>
				<a href="${f:url("event")}">Autor</a>
			</th>
			<th>Od</th>
			<th>Do</th>
			<th>Místo</th>
			<th>Veřejné</th>
			<th>Vkce</th>
		</tr>
		<c:forEach var="event" items="${events}" varStatus="rowCounter">
			<tr <c:if test="${rowCounter.count % 2 == 0}">class="alt"</c:if>>
				<td>
					<c:set var="eventUrl" value="event?event=${f:h(event.stringKey)}"/>
					<a href="${f:url(eventUrl)}">${f:h(event.name)}</a>
				</td>
				<td>${f:h(event.description)}</td>
				<td>${f:h(event.author)}</td>
				<td>${f:h(event.dateFromString)}</td>
				<td>${f:h(event.dateToString)}</td>
				<td>${f:h(event.place)}</td>
				<td>${f:h(event.publicString)}</td>
				<td>
					<c:set var="deleteUrl" value="event?delete=${f:h(event.stringKey)}&person=${person}"/>
					<a href="${f:url(deleteUrl)}">Smazat</a>
				</td>
			</tr>
		</c:forEach>
	</table>
</c:if>

<%@include file="/cms/footer.jsp" %>