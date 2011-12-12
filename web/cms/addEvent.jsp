<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="f" uri="/WEB-INF/tlds/f.tld"%>
<%@include file="/cms/header.jsp" %>

<form method="post" action="${f:url('/event?addEventSubmit')}">
	<table>
		<tr>
			<th><label for="author">Autor:</label></th>
			<td>
				${person}
			</td>
		</tr>
		<tr>
			<th><label for="name">Jméno:</label></th>
			<td>
				<input type="text" id="name" ${f:text("name")} class="text" />
			</td>
		</tr>
		<tr>
			<th><label for="description">Popis:</label></th>
			<td>
				<input type="text" id="description" ${f:text("description")} class="text" />
			</td>
		</tr>
		<tr>
			<th><label for="place">Místo:</label></th>
			<td>
				<input type="text" id="place" ${f:text("place")} class="text" />
			</td>
		</tr>
		<tr>
			<th><label for="dateFrom">Od:</label></th>
			<td>
				<input type="text" id="dateFrom" ${f:text("dateFrom")} class="text" />
			</td>
		</tr>
		<tr>
			<th><label for="dateTo">Do:</label></th>
			<td>
				<input type="text" id="dateTo" ${f:text("dateTo")} class="text" />
			</td>
		</tr>
		<tr>
			<th><label for="public">Veřejné:</label></th>
			<td>
				<input type="checkbox" id="public" ${f:checkbox("public")} />
			</td>
		</tr>
		<tr>
			<th>&nbsp;</th>
			<td>
				<input type="hidden" ${f:hidden("person")} />
				<input type="submit" name="submit" id="submit" class="default" value="Přidat" /> &nbsp;
				<a href="/event?person=${person}">Zpět</a>
			</td>
		</tr>
	</table>
</form>

<%@include file="/cms/footer.jsp" %>