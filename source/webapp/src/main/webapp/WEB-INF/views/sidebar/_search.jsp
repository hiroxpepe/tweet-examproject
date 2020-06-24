<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="search-content">
    <div id="tweet-search"  class="search">
        <input type="text" id="search-text" />
        <input id="search-button"
            class="command-button"
            type="button" 
            value="<fmt:message key="button.search" />"
        />
    </div>
</div>