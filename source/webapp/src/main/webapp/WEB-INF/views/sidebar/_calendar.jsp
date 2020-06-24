<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="calendar-content">
    <div id="tweet-calendar" class="calendar"></div>
    <c:if test="${not empty calendarYear}">
        <input type="hidden" id="calendar-year" value="${calendarYear}"></input>
    </c:if>
    <c:if test="${empty calendarYear}">
        <input type="hidden" id="calendar-year" value=""></input>
    </c:if>
    <c:if test="${not empty calendarMonth}">
        <input type="hidden" id="calendar-month" value="${calendarMonth}"></input>
    </c:if>
    <c:if test="${empty calendarMonth}">
        <input type="hidden" id="calendar-month" value=""></input>
    </c:if>
</div>