<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="/WEB-INF/tlds/f.tld"%>
<%@include file="/cms/header.jsp" %>

<c:if test="${empty event}">
	<p class="notice">Tato událost neexistuje</p>
</c:if>
<c:if test="${not empty event}">
	<table class="grid">
		<tr>
			<th>Jméno</th>
			<td>${f:h(event.name)}</td>
		</tr>
		<tr>
			<th>Popis</th>
			<td>${f:h(event.description)}</td>
		</tr>
		<tr>
			<th>Autor</th>
			<td>
				<c:set var="personUrl" value="event?person=${f:h(event.author)}"/>
				<a href="${f:url(personUrl)}">${f:h(event.author)}</a>
			</td>
		</tr>
		<tr>
			<th>Od</th>
			<td>${f:h(event.dateFromString)}</td>
		</tr>
		<tr>
			<th>Do</th>
			<td>${f:h(event.dateToString)}</td>
		</tr>
		<tr>
			<th>Místo</th>
			<td>${f:h(event.place)}</td>
		</tr>
		<tr>
			<th>Veřejné</th>
			<td>${f:h(event.publicString)}</td>
		</tr>
	</table>
</c:if>

<%@include file="/cms/footer.jsp" %>