<h3><fmt:message key="chooseOther"/></h3>
<p>${sessionScope.doubleOrder.order1.carName}</p>
<p>${sessionScope.doubleOrder.order2.carName}</p>
<p><ftm:message key="${sessionScope.doubleOrder.order1.carClass}"/></p>
<p><fmt:message key="passengers"/> ${sessionScope.doubleOrder.order1.passengers}</p>
<p><fmt:message key="location"/>
    <fmt:message key="of"/> ${sessionScope.doubleOrder.order1.locationFrom}
    <fmt:message key="to"/> ${sessionScope.doubleOrder.order1.locationTo},
    <fmt:message key="distance"/> ${sessionScope.orderDistance} <fmt:message key="kilometers"/></p>
<p><fmt:message key="cost"/> ${sessionScope.doubleOrder.fullCost} <ftm:message key="uah"/></p>
<p><fmt:message key="costWithDiscount"/> ${sessionScope.doubleOrder.costWithDiscount} <ftm:message key="uah"/></p>




