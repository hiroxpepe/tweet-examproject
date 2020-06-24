<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<div class="block index-content">
    
    <form:form  id="tweet-form" modelAttribute="tweetForm">
        <form:hidden id="user-id" path="userId" />
        <form:hidden id="screen-name" path="screenName" />
        <form:hidden id="locale" path="locale" />
        
        <div id="input-content-wrapper" class="content-wrapper">
            <input type="hidden" id="reply-status-id" value="">
            <input type="hidden" id="reply-user-name" value="">
            
            <div class="block">
                <fmt:message key="index.label.hangul" />
            </div>
            <div class="block">
                <textarea id="tweet"></textarea>
            </div>
            
            <c:if test="${not empty tweetForm.userId}">
                <div class="block tweet-command-block">
                    <input 
                        id="tweet-button"
                        class="command-button"
                        type="button"
                        value="<fmt:message key="button.tweet" />"
                    />
                </div>
            </c:if>
        </div>
            
        <c:if test="${not empty tweetForm.userId}">
            <%-- insert the tab content template. --%>
            <tiles:insertTemplate 
            template="/WEB-INF/views/content/_tabContent.jsp"
            />
        </c:if>
        
    </form:form>
</div>