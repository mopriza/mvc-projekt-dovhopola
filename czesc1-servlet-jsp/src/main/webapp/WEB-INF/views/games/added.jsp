<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/fragments/header.jspf" %>

<h2>Zapisano</h2>
<p>Ostatnio dodana gra: <strong>${sessionScope.lastTitle}</strong></p>
<p>W tej sesji dodano: ${sessionScope.addedCount}
    ${sessionScope.addedCount == 1 ? 'grę' : 'gier'}.</p>
<a href="${pageContext.request.contextPath}/games/add">Dodaj kolejną</a>

<%@ include file="/WEB-INF/views/fragments/footer.jspf" %>