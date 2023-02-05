<%@include file="/WEB-INF/jspf/header.jspf" %>

<head>
    <meta charset="UTF-8">
    <title><fmt:message key="mainTitle"/></title>
    <link rel="stylesheet" href="css/index.css" type="text/css"/>
</head>
<header>
    <div><h1><a href="/">Taxi Service</a></h1></div>
    <nav>
        <c:choose>
            <c:when test="${sessionScope.user != null && sessionScope.user.role.user}">
                <a href="/controller?action=makeOrder"><fmt:message key="order"/></a>
            </c:when>
            <c:when test="${sessionScope.user != null && sessionScope.user.role.admin}">
                <a href="/controller?action=statistics"><fmt:message key="statistics"/></a>
            </c:when>
        </c:choose>
        <space></space>
        <c:choose>
            <c:when test="${sessionScope.user == null}">
                <a href="/controller?action=login"><fmt:message key="login"/></a>
            </c:when>
            <c:otherwise>
                <a href="/controller?action=logout"><fmt:message key="logout"/></a>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${requestScope.lang == 'en'}">
                <a href="javascript:setLang('ua')"><fmt:message key="lang"/></a>
            </c:when>
            <c:otherwise>
                <a href="javascript:setLang('en')"><fmt:message key="lang"/></a>
            </c:otherwise>
        </c:choose>
    </nav>
</header>

<script type="text/javascript">
    function setLang(lang) {
        document.cookie = "lang=" + lang + ";";
        location.reload();
    }
</script>

<body>
<p>
<div>

</div>
</p>
</body>
</html>
