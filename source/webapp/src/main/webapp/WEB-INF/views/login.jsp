<%@ page contentType="text/html" pageEncoding="utf-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="block">
    <div class="login-content">
        <p class="login-description">
            <fmt:message key="index.oauth.message" />
        </p>
        <p>　</p>
        <c:if test="${not empty authorizationURL}">
            <div>
                <a href="${authorizationURL}" target="_blank">Click the link and a new auth window will open.<br/>Please get the PIN code from the window And input below.</a>
            </div>
            <p>　</p>
            <div>
                <form:form id="auth-form" action="/oauth.html" modelAttribute="authForm">
                    <form:input
                      id="input-pin"
                      path="pin"
                      placeholder="Please enter the PIN code from Twitter."
                    />
                    <p>　</p>
                    <input
                        id="auth-button"
                        class="command-anchor"
                        type="submit"
                        value="<fmt:message key="index.label.oauth" />"
                    />
                </form:form>
            </div>
        </c:if>
        <c:if test="${empty authorizationURL}">
            <p>
                <a class="command-anchor" href="<c:url value="/oauth.html"/>">
                    <fmt:message key="index.label.oauth" />
                </a>
            </p>
        </c:if>
    </div>
    <div>${requestToken}</div>

</div>