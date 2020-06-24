<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="block permalink-content">
    
    <form:form  id="tweet-form" modelAttribute="tweetForm">
        <form:hidden id="user-id" path="userId" />
        <form:hidden id="screen-name" path="screenName" />
        <form:hidden id="locale" path="locale" />
    </form:form>
    
    <div class="permalink-header">
        <c:if test="${not empty statusId}">
            <div class="permalink-status-id">${statusId}</div>
        </c:if>
        <c:if test="${not empty day}">
            <div class="permalink-date">${year} - ${month} - ${day}</div>
        </c:if>
        <c:if test="${not empty word}">
            <div class="permalink-word">${word}</div>
        </c:if>
    </div>
    <div class="permalink-wrapper">
        <table>
            <c:if test="${empty tweetModelList}">
                <p class="permalink-nothing">There is nothing about ${word}..</p>
            </c:if>
            <c:forEach var="tweetModel" items="${tweetModelList}" varStatus="status">
            <tr>
                <td>
                    <p class="permalink-text">
                        <c:out value="${tweetModel.text}" />
                    </p>
                    <p class="permalink-detail">
                        <%-- create values for day link. --%>
                        <fmt:formatDate var="createdLink" value="${tweetModel.created}" pattern="yyyy/MM/dd" />
                        <c:url var="dayLinkUrl" value="${siteDomain}tweet/${tweetModel.userName}/${createdLink}.html" />
                        <fmt:formatDate var="createdDay" value="${tweetModel.created}" pattern="yyyy-MM-dd E" />
                        <fmt:formatDate var="createdTime" value="${tweetModel.created}" pattern=" HH:mm:ss" />
                        
                        <%-- show detail. --%>
                        <span>by <c:out value="${tweetModel.userName}" /></span>
                        <span> at <a href='${dayLinkUrl}'>${createdDay}</a>${createdTime} UTC</span>
                    </p>
                <td>
            <tr>
            </c:forEach>
        </table>
    </div>
</div>