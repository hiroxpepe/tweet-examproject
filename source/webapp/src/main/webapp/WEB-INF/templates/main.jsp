<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta http-equiv="Content-Language" content="en">
        <link rel="shortcut icon" href="<c:url value="/docroot/images/icon.ico" />" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/docroot/styles/jquery-ui.custom.css" />" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/docroot/styles/exmp-english.css" />" />
        <script language="javascript" type="text/javascript" src="<c:url value="/docroot/scripts/jquery.js" />"></script>
        <script language="javascript" type="text/javascript" src="<c:url value="/docroot/scripts/jquery-ui.custom.min.js" />"></script>
        <script language="javascript" type="text/javascript" src="<c:url value="/docroot/scripts/jquery.caret.min.js" />"></script>
        <script language="javascript" type="text/javascript" src="<c:url value="/docroot/scripts/jquery.json.js" />"></script>
        <script language="javascript" type="text/javascript" src="<c:url value="/docroot/scripts/jquery.cookie.js" />"></script>
        <script language="javascript" type="text/javascript" src="<c:url value="/docroot/scripts/jquery.highlight-4.js" />"></script>
        <script language="javascript" type="text/javascript" src="<c:url value="/docroot/scripts/moment.min.js" />"></script>
        <script language="javascript" type="text/javascript" src="<c:url value="/docroot/scripts/exmp-english.min.js" />"></script>
        <title><fmt:message key="site.title" /></title>
    </head>
    <body>
        <div class="container">
            <div class="menu">
                <tiles:insertAttribute name="menu" />
            </div>
            <div class="header">
                <tiles:insertAttribute name="header" />
            </div>
            <div class="content">
                <tiles:insertAttribute name="content" />
            </div>
            <div class="sidebar">
                <tiles:insertAttribute name="sidebar"/>
            </div>
            <div class="footer">
                <tiles:insertAttribute name="footer" />
            </div>
        </div>
    </body>
</html>