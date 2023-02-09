<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="/WEB-INF/jspf/header.jspf" %>

<head>
    <meta charset="UTF-8">
    <title><fmt:message key="yourOrder"/></title>
    <link rel="stylesheet" href="css/order.css" type="text/css"/>
    <header>
        <div><h1><a href="/">Super Taxi</a></h1></div>
        <nav>
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

<div class="main-block">
    <h1><fmt:message key="orderSubmitForm"/></h1>
    <form action="controller" method="post">
        <input type="hidden" name="action" value="orderSubmit">
        <c:choose>
            <c:when test="${sessionScope.doubleOrder != null}">
                <%@include file="WEB-INF/jspf/dobuleOrderConfirmation.jsp" %>
            </c:when>
            <c:when test="${sessionScope.orderChoice != null}">
                <%@include file="WEB-INF/jspf/orderConfirmation.jsp" %>
            </c:when>
            <c:when test="${sessionScope.absentUserChoice != null}">
                <%@include file="WEB-INF/jspf/orderOtherConfirmation.jsp" %>
            </c:when>
            <c:otherwise>
                <div style="color:red; text-align: center;">
                    <fmt:message key="noValidCars"/>
                </div>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${requestScope.error != null}">
                <div style="color:red; text-align: center;"><fmt:message key="${requestScope.error}"/></div>
            </c:when>
        </c:choose>
        <c:choose>
            <c:when test="${sessionScope.wait != null}">
                <div style="text-align: center;"><fmt:message key="${sessionScope.wait}"/></div>
            </c:when>
        </c:choose>
        <div style="display: flex">
            <c:choose>
                <c:when test="${sessionScope.wait == null && (sessionScope.orderChoice != null || sessionScope.absentUserChoice != null)}">
                    <button type="submit" class="button" name="cancel">
                        <fmt:message key="cancel"/>
                    </button>
                    <button type="submit" class="button" name="submit">
                        <fmt:message key="submit"/>
                    </button>
                </c:when>
                <c:otherwise>
                    <button style="align-self: center" type="submit" class="button" name="ok">
                        <fmt:message key="ok"/>
                    </button>
                </c:otherwise>
            </c:choose>
        </div>
    </form>
</div>
</body>

</html>