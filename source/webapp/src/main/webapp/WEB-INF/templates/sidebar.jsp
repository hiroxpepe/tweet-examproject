<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<div class="block sidebar-content">
    
    <c:if test="${not empty tweetForm.userId}">
        <%-- insert the profile content --%>
        <tiles:insertTemplate 
            template="/WEB-INF/views/sidebar/_profile.jsp"
        />
        
        <%-- insert the calendar content --%>
        <tiles:insertTemplate 
            template="/WEB-INF/views/sidebar/_calendar.jsp"
        />
        
        <%-- insert the search content --%>
        <tiles:insertTemplate 
            template="/WEB-INF/views/sidebar/_search.jsp"
        />
        
        <%-- insert the tagcrowd content --%>
        <tiles:insertTemplate 
            template="/WEB-INF/views/sidebar/_tagcrowd.jsp"
        />
        
        <%-- insert the recent content --%>
        <tiles:insertTemplate 
            template="/WEB-INF/views/sidebar/_recent.jsp"
        />
    </c:if>
    
</div>