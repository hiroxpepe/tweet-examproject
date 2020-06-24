<%@ page contentType="text/html" pageEncoding="utf-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="block">
    <div class="login-content">
        <p class="login-description">
            <fmt:message key="index.oauth.message" />
        </p>
        <p>　</p>
        <p>
            <a class="command-anchor" href="<c:url value="/oauth.html"/>">
                <fmt:message key="index.label.oauth" />
            </a>
        </p>
    </div>
</div>