<h3><fmt:message key="chooseOther"/></h3>
<p>${sessionScope.orderChoice.carName}</p>
<p><ftm:message key="${sessionScope.orderChoice.carClass}"/></p>
<p><fmt:message key="passengers"/> ${sessionScope.orderChoice.passengers}</p>
<p><fmt:message key="location"/> <fmt:message key="of"/> ${sessionScope.orderChoice.locationFrom} <fmt:message
        key="to"/> ${sessionScope.orderChoice.locationTo}, <fmt:message
        key="distance"/> ${sessionScope.orderDistance} <fmt:message key="kilometers"/></p>
<p><fmt:message key="cost"/> ${sessionScope.orderChoice.cost} <ftm:message key="uah"/></p>
<p><fmt:message key="costWithDiscount"/> ${sessionScope.orderChoice.costWithDiscount} <ftm:message key="uah"/></p>



