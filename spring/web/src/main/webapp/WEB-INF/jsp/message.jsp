<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${not empty title}">
    <c:set value="negative" var="resultClass"></c:set>
    <data-message>
        <c:if test="${title.equals('Success')}">
            <c:set value="positive" var="resultClass"></c:set>
        </c:if>
        <div class="ui ${resultClass} message">
            <i class="close icon"></i>
            <div class="header">
                ${title}
            </div>
            <p>
                ${message}
            </p>
        </div>
    </data-message>
</c:if>