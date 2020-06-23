<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="block footer-content">
    <div>
        <fmt:message key="button.locale" />:
        <c:url var="englishLocaleUrl" value="/index.html">
            <c:param name="locale" value="" />
        </c:url>
        <a href='<c:out value="${englishLocaleUrl}" />'><fmt:message key="locale.english" /></a>
    </div>
    <div><fmt:message key="site.footer" /></div>
</div>